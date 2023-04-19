package com.tajne.test.svbackend.inbound.gbsbanking.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountCashBalanceOutput {
    @JsonProperty("availableBalance")
    private Double availableBalance;

    @JsonProperty("currency")
    private String currency;
}
