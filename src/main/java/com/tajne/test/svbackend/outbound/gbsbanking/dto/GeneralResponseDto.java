package com.tajne.test.svbackend.outbound.gbsbanking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public abstract class GeneralResponseDto<T> {
    @JsonProperty("status")
    private String status;

    @JsonProperty("error")
    private List<ErrorResponseDto> error;

    @JsonProperty("payload")
    private T payload;
}
