package com.nork.render.service;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/12
 */
public interface IAutoRenderFactory {

    /**
     * 适配处理器
     * @param sourceType （1：web 2：mobile）
     * @return
     */
    public IAutoRenderService generateProcessor(Integer sourceType);
}
