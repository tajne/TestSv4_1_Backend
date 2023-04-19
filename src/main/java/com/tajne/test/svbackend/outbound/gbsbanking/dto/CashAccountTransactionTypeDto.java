package com.tajne.test.svbackend.outbound.gbsbanking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CashAccountTransactionTypeDto {

    @JsonProperty("enumeration")
    private String enumeration;

    @JsonProperty("value")
    private String value;
}
