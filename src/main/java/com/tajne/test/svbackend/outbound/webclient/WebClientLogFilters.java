package com.tajne.test.svbackend.outbound.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class WebClientLogFilters {

    private WebClientLogFilters() {
        throw new IllegalStateException("Utility class");
    }

    public static List<ExchangeFilterFunction> prepareFilters() {
        return Arrays.asList(logRequest(), logResponse());
    }

    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
//            if (log.isTraceEnabled())
            {
                StringBuilder sb = new StringBuilder("Request Headers: \n")
                        .append(clientRequest.method())
                        .append(" ")
                        .append(clientRequest.url());
                clientRequest
                        .headers()
                        .forEach((name, values) -> values.forEach(value -> sb
                                .append("\n")
                                .append(name)
                                .append(":")
                                .append(value)));

                log.debug(sb.toString());
            }
            return Mono.just(clientRequest);
        });
    }

    private static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
//            if (log.isTraceEnabled())
            {
                StringBuilder sb = new StringBuilder("Response Status and Headers: \n")
                        .append("Status: ")
                        .append(clientResponse.rawStatusCode());
                clientResponse
                        .headers()
                        .asHttpHeaders()
                        .forEach((key, value1) -> value1.forEach(value -> sb
                                .append("\n")
                                .append(key)
                                .append(":")
                                .append(value)));

                log.debug(sb.toString());
            }
            return Mono.just(clientResponse);
        });
    }
}
