package com.tajne.test.svbackend.domain.service.output;

import lombok.Data;
import java.util.List;

@Data
public class AccountCashTransactionListDto {

    private List<AccountCashTransactionDto> list;
}
