package com.sandu.exception;

/**
 * @Author chenqiang
 * @Description 重复提交异常
 * @Date 2018/6/26 0026 20:14
 * @Modified By
 */
public class DuplicateSubmitException extends RuntimeException {

    public DuplicateSubmitException(String msg) {
        super(msg);
    }

    public DuplicateSubmitException(String msg, Throwable cause){
        super(msg,cause);
    }
}
