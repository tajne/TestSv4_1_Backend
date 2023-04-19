package com.tajne.test.svbackend.domain.service.output;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountCashBalanceDto {
    private LocalDate date;
    private Double balance;
    private Double availableBalance;
    private String currency;
}
