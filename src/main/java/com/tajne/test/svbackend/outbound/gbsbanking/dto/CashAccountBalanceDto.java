package com.tajne.test.svbackend.outbound.gbsbanking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CashAccountBalanceDto {
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("balance")
    private Double balance;
    @JsonProperty("availableBalance")
    private Double availableBalance;

    @JsonProperty("currency")
    private String currency;


}
