package com.tajne.test.svbackend.domain.exception;

public class CashAccountBalanceException  extends Exception {

    public CashAccountBalanceException(String message) {
        super(message);
    }

    public CashAccountBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
