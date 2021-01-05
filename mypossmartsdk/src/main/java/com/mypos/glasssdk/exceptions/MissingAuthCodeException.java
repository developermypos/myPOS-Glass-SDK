package com.mypos.glasssdk.exceptions;

public class MissingAuthCodeException extends IllegalArgumentException {

    public MissingAuthCodeException(String s) {
        super(s);
    }
}
