package com.nork.base.excetion;

/**
 * 自定义异常
 *
 * @author huangsongbo
 *
 */
public class BizException extends Exception {

    private static final long serialVersionUID = 1L;

    public BizException(String message) {
        super(message);
    }

}


