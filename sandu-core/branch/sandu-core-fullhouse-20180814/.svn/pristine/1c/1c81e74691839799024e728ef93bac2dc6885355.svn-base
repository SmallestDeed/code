package com.sandu.api.base.common;

import org.springframework.validation.BindingResult;


/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * <p>
 * 基础Controller
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/13 17:18
 */

public class BaseController {

    /**
     * 获取校验的错误消息
     *
     * @param validResult 校验结果
     * @return 错误消息
     */
	protected ResponseEnvelope processValidError(BindingResult validResult) {
        StringBuilder error = new StringBuilder();
        validResult.getAllErrors().forEach(e -> {
            error.append(e.getDefaultMessage());
            error.append("; ");
        });
        return new ResponseEnvelope<>(false, error.toString());
    }


}
