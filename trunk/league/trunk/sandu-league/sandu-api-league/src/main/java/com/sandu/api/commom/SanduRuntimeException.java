package com.sandu.api.commom;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/4/20
 * @since : sandu_yun_1.0
 */
public class SanduRuntimeException extends RuntimeException{

    public SanduRuntimeException() {
        super();
    }

    public SanduRuntimeException(String msg){
        super(msg);
    }

    public SanduRuntimeException(String msg, Throwable ex){
        super(msg, ex);
    }

    public SanduRuntimeException(Throwable ex) {
        super(ex);
    }


    public SanduRuntimeException(String message, Throwable cause, String msgKey) {
        this(message, cause);
    }


    public SanduRuntimeException(String message, Throwable cause, String msgKey, Object... msgArguments) {
        this(message, cause);

    }
}
