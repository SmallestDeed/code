package com.sandu.common.exception;

import com.sandu.common.enums.ErrorCodeEnum;

/**
 * 元数据异常
 *
 * @date 20171213
 * @auth pengxuangang
 */
public class MetaDataException extends Exception {
    /**
     * 异常码
     */
    protected int code;

    private static final long serialVersionUID = 3160241586346324994L;

    public MetaDataException() {
    }

    public MetaDataException(Throwable cause) {
        super(cause);
    }

    public MetaDataException(String message) {
        super(message);
    }

    public MetaDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetaDataException(int code, String message) {
        super(message);
        this.code = code;
    }

    public MetaDataException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }

    public MetaDataException(ErrorCodeEnum codeEnum, Object... args) {
        super(String.format(codeEnum.msg(), args));
        this.code = codeEnum.code();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
