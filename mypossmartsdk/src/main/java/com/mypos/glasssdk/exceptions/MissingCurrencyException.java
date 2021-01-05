package com.mypos.glasssdk.exceptions;

public class MissingCurrencyException extends IllegalArgumentException {

    public MissingCurrencyException(String s) {
        super(s);
    }
}
