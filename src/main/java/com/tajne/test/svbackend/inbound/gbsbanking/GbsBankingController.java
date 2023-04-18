package com.tajne.test.svbackend.inbound.gbsbanking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tajne.test.svbackend.outbound.gbsbanking.GbsBankingService;
import com.tajne.test.svbackend.outbound.gbsbanking.dto.CashAccountResponseDto;
import com.tajne.test.svbackend.outbound.gbsbanking.dto.CashAccountBalanceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/gbs-banking")
public class GbsBankingController {

    private final GbsBankingService gbsBankingService;

    @GetMapping(value="/account-cash/{accountId}/balance", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String AccountCashBalance(@PathVariable("accountId") Long accountId) {
        log.debug("AccountCashBalance called for accountId:={}", accountId);

        try {
            CashAccountResponseDto<CashAccountBalanceDto> response = gbsBankingService.getCashAccountBalance(accountId);
            CashAccountBalanceDto cashAccountBalanceDto = response.getPayload();
            return String.format("%f %s",cashAccountBalanceDto.getAvailableBalance(), cashAccountBalanceDto.getCurrency());
        } catch (JsonProcessingException exc) {
            log.error("{} exception caught: {}", exc.getClass().getName(), exc.getMessage(), exc );
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage(), exc);
        }
    }
}
