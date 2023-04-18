package com.tajne.test.svbackend.outbound.gbsbanking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tajne.test.svbackend.outbound.RequestResponseSerializer;
import com.tajne.test.svbackend.outbound.gbsbanking.dto.CashAccountResponseDto;
import com.tajne.test.svbackend.outbound.gbsbanking.dto.CashAccountBalanceDto;
import com.tajne.test.svbackend.outbound.gbsbanking.dto.CashAccountDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GbsBankingService {

    private final WebClient webClient;


    @Value("${api.gbs-banking-service.timeout:5000}")
    private Long gbsBankingTimeout;
    @Value("${api.gbs-banking-service.url}")
    private String gbsBankingUrl;

    @Value("${api.gbs-banking-service.api-key}")
    private String gbsBankingApiKey;

    private final RequestResponseSerializer requestResponseSerializer;

    @Data
    private static class CallActivationParam {

        private String apiPath;
        private MultiValueMap<String, String> queryParams;
    }

   private <T> Mono<?> resultHandling(ClientResponse clientResponse, Class<T> responseType) {
        if ( clientResponse.statusCode().isError() ) {
            try {
                log.error("ERROR - Response Status: {}", clientResponse.statusCode());
            } catch(Exception e) {
                log.error("Error generating Response Status code...", e);
            }
            return clientResponse.createException().flatMap(Mono::error);
        }
        return clientResponse.bodyToMono(responseType);
    }

    private <T> CashAccountResponseDto<T> executeGetCall(CallActivationParam callActivationParam, Class<T> responseType) throws JsonProcessingException {

        MultiValueMap<String, String> headersParamMap = new LinkedMultiValueMap<>();
        headersParamMap.add("Auth-Schema", "S2S");
        headersParamMap.add("Api-Key", gbsBankingApiKey);
        headersParamMap.add("Content-type", MediaType.APPLICATION_JSON_VALUE);

        String monoResponse = (String) webClient.get()
                .uri(gbsBankingUrl,
                        uriBuilder -> uriBuilder.path(callActivationParam.getApiPath())
                                .queryParams(callActivationParam.getQueryParams())
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .headers(h -> h.addAll(headersParamMap))
                .exchange()
                .timeout(Duration.ofMillis(gbsBankingTimeout), Mono.error(new TimeoutException()))
                .flatMap( clientResponse -> resultHandling(clientResponse, String.class)).block();
        log.info("Response body: {} ", monoResponse);
        JsonNode jsonResult = new ObjectMapper().readValue(monoResponse, JsonNode.class);
        CashAccountResponseDto<T> res = new CashAccountResponseDto<>();
        res.setStatus(jsonResult.get("status").textValue());
        res.setPayload(requestResponseSerializer.objConversion(jsonResult.get("payload"), responseType));
        return res;
    }

    private final String GET_CASH_ACCOUNT_ENDPOINT = "/api/gbs/banking/v4.0/accounts/%d";
    public CashAccountResponseDto<CashAccountDto> getCashAccount(Long accountId) throws JsonProcessingException {

        CallActivationParam callActivationParam = new CallActivationParam();
        callActivationParam.setApiPath(String.format(GET_CASH_ACCOUNT_ENDPOINT, accountId));
        return executeGetCall(callActivationParam, CashAccountDto.class);
    }

    private final String GET_CASH_ACCOUNT_BALANCE_ENDPOINT = GET_CASH_ACCOUNT_ENDPOINT + "/balance";
    public CashAccountResponseDto<CashAccountBalanceDto> getCashAccountBalance(Long accountId) throws JsonProcessingException {

        CallActivationParam callActivationParam = new CallActivationParam();
        callActivationParam.setApiPath(String.format(GET_CASH_ACCOUNT_BALANCE_ENDPOINT, accountId));
        return executeGetCall(callActivationParam, CashAccountBalanceDto.class);
    }
}
