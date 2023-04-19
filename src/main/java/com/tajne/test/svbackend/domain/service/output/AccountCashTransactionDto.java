package com.tajne.test.svbackend.domain.service.output;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountCashTransactionDto {

    private String transactionId;

    private String operationId;

    private LocalDate accountingDate;

    private LocalDate valueDate;


    private AccountCashTransactionTypeDto type;


    private Double amount;

    private String currency;

    private String description;
}
