package com.tajne.test.svbackend.inbound.gbsbanking;

import com.tajne.test.svbackend.domain.exception.CashAccountBalanceException;
import com.tajne.test.svbackend.domain.exception.CashAccountTransactionsException;
import com.tajne.test.svbackend.domain.service.AccountCashService;
import com.tajne.test.svbackend.domain.service.output.AccountCashBalanceDto;
import com.tajne.test.svbackend.domain.service.output.AccountCashTransactionListDto;
import com.tajne.test.svbackend.inbound.gbsbanking.output.AccountCashBalanceOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/gbs-banking")
public class GbsBankingController {

    private final AccountCashService accountCashService;

    @GetMapping(value="/account-cash/{accountId}/balance", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AccountCashBalanceOutput accountCashBalance(@PathVariable("accountId") Long accountId) {
        log.debug("AccountCashBalance called for accountId:={}", accountId);
        try {
            AccountCashBalanceDto response = accountCashService.getCashAccountBalance(accountId);
            AccountCashBalanceOutput output = new AccountCashBalanceOutput();
            output.setAvailableBalance(response.getAvailableBalance());
            output.setCurrency(response.getCurrency());
            return output;
        } catch (CashAccountBalanceException exc) {
            log.error("{} exception caught: {}", exc.getClass().getName(), exc.getMessage(), exc );
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage(), exc);
        }
    }

    @GetMapping(value="/account-cash/{accountId}/transactions", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AccountCashTransactionListDto accountCashTransactions(@PathVariable(name="accountId") Long accountId,
                                          @RequestParam(name="from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                          @RequestParam(name="to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        log.debug("AccountCashTransactions called for accountId:={} to get data from date {} to date {}", accountId, fromDate, toDate);

        if(fromDate.after(toDate)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid date interval");
        }
        try {
            return accountCashService.getCashAccountTransactionList(accountId, fromDate, toDate);
        } catch (CashAccountTransactionsException exc) {
            log.error("{} exception caught: {}", exc.getClass().getName(), exc.getMessage(), exc );
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage(), exc);
        }
    }

}
