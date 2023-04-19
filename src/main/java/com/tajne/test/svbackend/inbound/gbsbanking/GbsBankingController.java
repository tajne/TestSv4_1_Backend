package com.tajne.test.svbackend.inbound.gbsbanking;

import com.tajne.test.svbackend.domain.exception.CashAccountBalanceException;
import com.tajne.test.svbackend.domain.service.AccountCashService;
import com.tajne.test.svbackend.domain.service.output.AccountCashBalanceDto;
import com.tajne.test.svbackend.inbound.gbsbanking.output.AccountCashBalanceOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/gbs-banking")
public class GbsBankingController {

    private final AccountCashService accountCashService;

    @GetMapping(value="/account-cash/{accountId}/balance", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AccountCashBalanceOutput AccountCashBalance(@PathVariable("accountId") Long accountId) {
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
}
