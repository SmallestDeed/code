package com.sandu.search.exception;

public class GroupProductIndexException extends Exception{

    public GroupProductIndexException() {
    }

    public GroupProductIndexException(String message) {
        super(message);
    }

    public GroupProductIndexException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupProductIndexException(Throwable cause) {
        super(cause);
    }

    public GroupProductIndexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
