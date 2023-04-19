package com.tajne.test.svbackend.domain.service;

import com.tajne.test.svbackend.domain.exception.CashAccountBalanceException;
import com.tajne.test.svbackend.domain.service.output.AccountCashBalanceDto;
import com.tajne.test.svbackend.outbound.gbsbanking.GbsBankingService;
import com.tajne.test.svbackend.outbound.gbsbanking.dto.CashAccountBalanceDto;
import com.tajne.test.svbackend.outbound.gbsbanking.dto.CashAccountBalanceResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCashService {

    private final GbsBankingService gbsBankingService;

    public AccountCashBalanceDto getCashAccountBalance(Long accountId) throws CashAccountBalanceException {
        try {
            CashAccountBalanceResponseDto response =  gbsBankingService.getCashAccountBalance(accountId);
            CashAccountBalanceDto cashAccountBalanceDto = response.getPayload();
            AccountCashBalanceDto accountCashBalanceDto = new AccountCashBalanceDto();
            accountCashBalanceDto.setAvailableBalance(cashAccountBalanceDto.getAvailableBalance());
            accountCashBalanceDto.setCurrency(cashAccountBalanceDto.getCurrency());
            return accountCashBalanceDto;
        } catch (Exception e) {
            log.warn("{} caught while retrievng account balance for {}: {}",
                    e.getClass().getName(), accountId, e);
            throw new CashAccountBalanceException("Internal Error: " + e.getMessage(), e);
        }
    }


}
