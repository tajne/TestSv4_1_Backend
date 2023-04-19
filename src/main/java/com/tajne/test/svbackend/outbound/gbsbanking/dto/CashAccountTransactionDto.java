package com.tajne.test.svbackend.outbound.gbsbanking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CashAccountTransactionDto {

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("operationId")
    private String operationId;

    @JsonProperty("accountingDate")
    private LocalDate accountingDate;

    @JsonProperty("valueDate")
    private LocalDate valueDate;


    @JsonProperty("type")
    private CashAccountTransactionTypeDto type;


    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("currency")
    private String currency;


    @JsonProperty("description")
    private String description;
}
