package com.tajne.test.svbackend.outbound.gbsbanking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CashAccountResponseDto<T> {
    @JsonProperty("status")
    private String status;

    @JsonProperty("payload")
    private T payload;
}
