package com.tajne.test.svbackend.outbound.gbsbanking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CashAccountDto {

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("iban")
    private String iban;

    @JsonProperty("abiCode")
    private String abiCode;

    @JsonProperty("cabCode")
    private String cabCode;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("internationalCin")
    private String internationalCin;

    @JsonProperty("nationalCin")
    private String nationalCin;

    @JsonProperty("account")
    private String account;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("holderName")
    private String holderName;

    @JsonProperty("activatedDate")
    private LocalDate activatedDate;

    @JsonProperty("currency")
    private String currency;

}
