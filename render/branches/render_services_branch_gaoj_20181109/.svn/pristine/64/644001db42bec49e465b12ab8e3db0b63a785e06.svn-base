package com.nork.render.service;

import com.nork.common.exception.GeneratePanoramaException;
import com.nork.common.model.ResponseEnvelope;
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
}
