package com.tajne.test.svbackend.outbound.gbsbanking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CashAccountTransactionListDto {

    @JsonProperty("list")
    List<CashAccountTransactionDto> list;
}
