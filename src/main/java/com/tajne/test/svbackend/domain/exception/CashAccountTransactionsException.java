package com.tajne.test.svbackend.domain.exception;

public class CashAccountTransactionsException extends Exception {

    public CashAccountTransactionsException(String message) {
        super(message);
    }

    public CashAccountTransactionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
