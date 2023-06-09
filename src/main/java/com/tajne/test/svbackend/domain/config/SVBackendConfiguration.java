package com.tajne.test.svbackend.domain.config;

import com.tajne.test.svbackend.outbound.webclient.WebClientLogFilters;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@RequiredArgsConstructor
public class SVBackendConfiguration  extends ApplicationObjectSupport {

    @Value("${logging.level.reactor.netty.http.client:NONE}")
    private String loggingLevel;

    @Value("${web-client-service.maxInMemorySize:10485760}")
    private int maxInMemorySize;

    @Bean
    public WebClient gbsBankingWebClient() {

        WebClient.Builder builder = WebClient.builder();
        if ("DEBUG".equals(loggingLevel)) {
            builder.clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)));
        }

        return builder
                .filters(exchangeFilterFunctions -> exchangeFilterFunctions.addAll(WebClientLogFilters.prepareFilters()))
                .codecs(c -> c.defaultCodecs().maxInMemorySize(maxInMemorySize))
                .build();
    }
}
