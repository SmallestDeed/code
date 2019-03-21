package com.nork.render.service;

import com.nork.common.model.ResponseEnvelope;
import com.nork.system.model.ResRenderPic;

import java.util.List;

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

    ResponseEnvelope createFullHouseAutoRenderTask(ResRenderPic resRenderPic);

    ResponseEnvelope createFullHouseNewTask(ResRenderPic resRenderPic);

    boolean addRenderTaskStateList(List<Integer> taskIdList);
}
