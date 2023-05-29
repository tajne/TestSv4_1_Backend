package com.tajne.test.svbackend.domain.service;

import com.tajne.test.svbackend.domain.exception.CashAccountBalanceException;
import com.tajne.test.svbackend.domain.exception.CashAccountTransactionsException;
import com.tajne.test.svbackend.domain.service.output.AccountCashBalanceDto;
import com.tajne.test.svbackend.domain.service.output.AccountCashTransactionListDto;
import com.tajne.test.svbackend.util.RequestResponseSerializer;
import com.tajne.test.svbackend.outbound.gbsbanking.GbsBankingService;
import com.tajne.test.svbackend.outbound.gbsbanking.dto.CashAccountBalanceDto;
import com.tajne.test.svbackend.outbound.gbsbanking.dto.CashAccountBalanceResponseDto;
import com.tajne.test.svbackend.outbound.gbsbanking.dto.CashAccountTransactionListDto;
import com.tajne.test.svbackend.outbound.gbsbanking.dto.CashAccountTransactionListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCashService {

    private final GbsBankingService gbsBankingService;

    private final RequestResponseSerializer requestResponseSerializer;

    public AccountCashBalanceDto getCashAccountBalance(Long accountId) throws CashAccountBalanceException {
        try {
            CashAccountBalanceResponseDto response =  gbsBankingService.getCashAccountBalance(accountId);
            CashAccountBalanceDto cashAccountBalanceDto = response.getPayload();
            return requestResponseSerializer.objConversion(cashAccountBalanceDto, AccountCashBalanceDto.class);
        } catch (Exception e) {
            log.warn("{} caught while retrieving account balance for {}: {}",
                    e.getClass().getName(), accountId, e);
            throw new CashAccountBalanceException("Internal Error: " + e.getMessage(), e);
        }
    }


    public AccountCashTransactionListDto getCashAccountTransactionList(Long accountId, Date fromDate, Date toDate) throws CashAccountTransactionsException {
        try {
            CashAccountTransactionListResponseDto response =  gbsBankingService.getCashAccountTransactionList(accountId, fromDate, toDate);
            CashAccountTransactionListDto cashAccountTransactionListDto = response.getPayload();
            return requestResponseSerializer.objConversion(cashAccountTransactionListDto, AccountCashTransactionListDto.class);
        } catch (Exception e) {
            log.warn("{} caught while retrieving transaction list for {}: {}",
                    e.getClass().getName(), accountId, e);
            throw new CashAccountTransactionsException("Internal Error: " + e.getMessage(), e);
        }
    }
}
