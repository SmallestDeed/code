package com.sandu.api.fullhouse.common.exception;

public class BizException extends Exception {
    public BizException(){
        super();
    }
    public BizException(String message){
        super(message);
    }
}
