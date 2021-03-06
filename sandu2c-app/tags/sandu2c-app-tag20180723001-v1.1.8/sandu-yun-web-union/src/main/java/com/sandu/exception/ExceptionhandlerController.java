package com.sandu.exception;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 5:39 2018/6/13 0013
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/6/13 0013PM 5:39
 */

import com.sandu.common.model.ResponseEnvelope;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
class ExceptionhandlerController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionhandlerController.class);

    /**
     * 处理实体字段校验不通过异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEnvelope validationError(MethodArgumentNotValidException ex) {
        logger.error("raised MethodArgumentNotValidException : " + ex);
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();

        for (FieldError error : fieldErrors) {
            builder.append( error.getDefaultMessage()+"\n");
        }
        return new ResponseEnvelope(false,builder.toString());
    }


}