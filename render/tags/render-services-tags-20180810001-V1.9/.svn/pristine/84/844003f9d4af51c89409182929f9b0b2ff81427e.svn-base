package com.nork.render.service.impl;

import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.AutoRenderTaskConstant;
import com.nork.render.service.IAutoRenderService;
import com.nork.render.service.IAutoRenderTaskApi;
import com.nork.system.model.ResRenderPic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/12
 */
@Service("autoRenderTaskApi")
public class AutoRenderTaskApiImpl implements IAutoRenderTaskApi{
    private final  static Logger log = LoggerFactory.getLogger(AutoRenderTaskApiImpl.class);

    @Autowired
    private IAutoRenderService mobileAutoRenderTaskProcessor;

    @Autowired
    private IAutoRenderService webAutoRenderTaskProcessor;

    @Override
    public ResponseEnvelope createAutoRenderTask(ResRenderPic resRenderPic) {
        log.debug("Process in AutoRenderTaskApiImpl.createAutoRenderTask method parameter resRenderPic:{}", resRenderPic.toString());
        ResponseEnvelope mess = null;

        if(null == resRenderPic.getTaskType()){
            log.error("taskType is null");
            return  new ResponseEnvelope(false, "未知任务类型");
        }
        if(null == resRenderPic.getPlatformCode()){
        	log.error("platformCode is null");
        	return  new ResponseEnvelope(false, "未知平台类型");
        }

        if(null == resRenderPic.getTaskSource()){
            log.error("taskType is null");
            return  new ResponseEnvelope(false, "未知来源");
        }

        IAutoRenderService autoRenderService = getAutoRenderService(resRenderPic);
        if(null == autoRenderService){
            return new ResponseEnvelope(false, "系统暂不支持此来源");
        }

        if( resRenderPic.getTaskType().intValue() == AutoRenderTaskConstant.TASK_TYPE_AUTO_RENDER){
            // 装进我家接口服务
            mess = autoRenderService.handleAutoRenderProcessor(resRenderPic);
        }else if( resRenderPic.getTaskType().intValue() == AutoRenderTaskConstant.TASK_TYPE_REPLACE){
            // 替换渲染服务
            mess = autoRenderService.handleAutoReplaceProcessor(resRenderPic);
        }else{
            // 未知类型
            mess = new ResponseEnvelope(false, "未知任务类型");
        }

        return mess;
    }

    private IAutoRenderService getAutoRenderService(ResRenderPic resRenderPic) {
        IAutoRenderService autoRenderService = null;
        switch (resRenderPic.getTaskSource()){
            case AutoRenderTaskConstant.TASK_SOURCE_WEB:
                autoRenderService =  webAutoRenderTaskProcessor;
                break;
            case AutoRenderTaskConstant.TASK_SOURCE_APP:
                autoRenderService =  mobileAutoRenderTaskProcessor;
                break;
            default:
                log.warn("not has request sourceType:"+ resRenderPic.getTaskSource());
                break;
        }
        return autoRenderService;
    }
}
