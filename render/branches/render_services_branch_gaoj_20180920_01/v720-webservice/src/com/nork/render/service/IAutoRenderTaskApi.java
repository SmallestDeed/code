package com.nork.render.service;

import com.nork.common.model.ResponseEnvelope;
import com.nork.system.model.ResRenderPic;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/12
 */
public interface IAutoRenderTaskApi {

    ResponseEnvelope createAutoRenderTask(ResRenderPic resRenderPic);

    /**
     * 全屋方案一次性渲染逻辑
     * @param resRenderPic
     * @return
     */
    ResponseEnvelope createFullHouseAutoRenderTask(ResRenderPic resRenderPic);

    /**
     * 全屋方案制作新逻辑
     * @param resRenderPic
     * @return
     */
    ResponseEnvelope createFullHouseNewTask(ResRenderPic resRenderPic);
}
