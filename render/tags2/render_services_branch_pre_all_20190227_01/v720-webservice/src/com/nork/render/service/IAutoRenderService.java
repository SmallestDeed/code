package com.nork.render.service;

import com.nork.common.exception.BizException;
import com.nork.common.exception.GeneratePanoramaException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoRenderTask;
import com.nork.render.model.input.RenderPayInput;
import com.nork.system.model.ResRenderPic;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/8
 */
public interface IAutoRenderService {

    /**
     * 单空间方案装进我家
     * @param resRenderPic
     * @return
     */
    ResponseEnvelope handleAutoRenderProcessor(ResRenderPic resRenderPic);

    /**
     * 单空间方案替换渲染
     * @param resRenderPic
     * @return
     */
    ResponseEnvelope handleAutoReplaceProcessor(ResRenderPic resRenderPic);

    /**
     * 全屋方案装进我家
     * @param resRenderPic
     * @return
     */
    ResponseEnvelope handleFullHouseAutoRenderProcessor(ResRenderPic resRenderPic) throws GeneratePanoramaException;

    /**
     * 全屋方案替换渲染
     * @param resRenderPic
     * @return
     */
    ResponseEnvelope handleFullHouseAutoReplaceProcessor(ResRenderPic resRenderPic) throws GeneratePanoramaException;

    /**
     * 全屋方案新逻辑
     * @param resRenderPic
     * @return
     */
    ResponseEnvelope handleFullHouseAutoRenderNewProcessor(ResRenderPic resRenderPic) throws GeneratePanoramaException;

    /**
     * 创建PC端高清渲染任务
     * @author: chenm
     * @date: 2019/1/18 15:50
     * @param payInput
     * @param orderNo
     * @param loginUser
     * @return: com.nork.design.model.AutoRenderTask
     */
    AutoRenderTask createHDRenderTask(RenderPayInput payInput, String orderNo, LoginUser loginUser) throws GeneratePanoramaException, BizException;

}
