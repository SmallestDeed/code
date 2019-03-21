package com.sandu.exception;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.sandu.common.ReturnData;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.util.http.HttpContextUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 * <p>
 * 2018年3月20日
 */

@Slf4j
@RestControllerAdvice
public class WebExceptionResolver {

    @ExceptionHandler(value = RuntimeException.class)
    public ReturnData<?> throwJsonError(Exception e) {
        ReturnData<?> response = this.builder();

        // 自定义的业务异常
        if (e instanceof AppException) {
            AppException ex = (AppException) e;
            response.status(ex.isFlag()).code(ex.getResponseEnum()).message(ex.getResponseEnum().getMessage());
        } else if (e instanceof BizException) {
            // thorw new BizException();
            response.status(false).message(Objects.toString(e.getMessage(), ResponseEnum.ERROR.getMessage()));
        } else if (e instanceof MaxUploadSizeExceededException) {
            // 文件大小超出限制 50Mb
            response.status(false).message(ResponseEnum.FILE_SIZE_LIMIT.getMessage());
        } else {
            response.status(false).message(ResponseEnum.ERROR.getMessage());
        }

        log.error("请求URL => {}", HttpContextUtils.getRequestURI());
        log.error("请求参数 => {}", HttpContextUtils.getRequestParams());
        log.error("############################ 出现异常 ############################", e);

        return response;
    }

    private ReturnData<?> builder() {
        ReturnData<?> response = ReturnData.builder();
        String msgId = HttpContextUtils.getHttpServletRequest().getParameter("msgId");
        if (StringUtils.isEmpty(msgId)) {
            // msgId is null
            return response.msgId(msgId).status(true).code(ResponseEnum.MSGID_IS_NULL).message(ResponseEnum.MSGID_IS_NULL.getMessage());
        }
        return response.msgId(msgId).status(true).code(ResponseEnum.SUCCESS).message(ResponseEnum.SUCCESS.getMessage());
    }
}
