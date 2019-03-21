package com.sandu.exception;

import lombok.NoArgsConstructor;

/**
 * throw new BizException
 */

@NoArgsConstructor
public class BizException extends RuntimeException {

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable throwable) {
        super(throwable);
    }

    public BizException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
