package com.sandu.exception;

import com.sandu.common.constant.ResponseEnum;

public class SignatureException extends AppException {

    private static final long serialVersionUID = 1597932550219380223L;

    public SignatureException(boolean flag, ResponseEnum responseEnum) {
        super(flag, responseEnum);
    }

    public SignatureException(boolean flag, ResponseEnum responseEnum, Throwable throwable) {
        super(flag, responseEnum, throwable);
    }
}
