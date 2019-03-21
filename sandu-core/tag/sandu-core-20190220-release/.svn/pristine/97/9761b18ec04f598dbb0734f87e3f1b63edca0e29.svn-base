package com.sandu.interceptor;

import com.sandu.api.base.common.ResponseEnvelope;
import com.sandu.api.base.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final String CLASS_LOG_PREFIX = "[异常处理]";

    @ExceptionHandler(BizException.class)
    public ResponseEnvelope handlerBizException(BizException e) {
        log.warn(CLASS_LOG_PREFIX + e.getMessage(), e);
        ResponseEnvelope res = new ResponseEnvelope(false, e.getMessage());
        res.setStatusCode(e.getCode() + "");
        return res;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEnvelope handlerRuntimeException(RuntimeException e) {
        log.error(CLASS_LOG_PREFIX + e.getMessage(), e);
        ResponseEnvelope res = new ResponseEnvelope(false, "系统异常");
        return res;
    }
}
