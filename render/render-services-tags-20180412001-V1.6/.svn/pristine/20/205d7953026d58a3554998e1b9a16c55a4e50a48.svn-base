package com.nork.render.service.impl;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.exception.GeneratePanoramaException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoRenderTask;
import com.nork.render.service.IAutoRenderService;
import com.nork.system.model.ResRenderPic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:添加移动端渲染任务
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/8
 */
@Service("mobileAutoRenderTaskProcessor")
public class MobileAutoRenderTaskProcessor extends AbstractAutoRenderProcessor implements IAutoRenderService {
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEnvelope handleAutoRenderProcessor(ResRenderPic resRenderPic) {
       logger.debug("Process in MobileAutoRenderTaskProcessor.handleAutoRenderProcessor method parameter resRenderPic:{}", resRenderPic.toString());
        AutoRenderTask autoRenderTask = null;
        try {
            // 构造渲染任务对象
            autoRenderTask = getAutoRenderTask(resRenderPic);
        } catch (GeneratePanoramaException e) {
            return new ResponseEnvelope(e.isFlag(), e.getMessage());
        }
        String token = resRenderPic.getToken();
        String platformCode = resRenderPic.getPlatformCode();
        Boolean flag = false;
        Integer taskId = designPlanAutoRenderServiceImpl.add(autoRenderTask);
        autoRenderTask.setId(taskId);
        Integer result = designPlanAutoRenderServiceImpl.addRedisLists(autoRenderTask);
        boolean status = backFillTaskId(autoRenderTask.getOrderNumber(), taskId,token,platformCode);
        
        if (result > 0 && status ) {
            flag =true;
            return new ResponseEnvelope<>(flag,"创建渲染任务成功！");
        }
        flag =false;
        logger.error("移动端装进我家创建渲染任务失败===>result:"+result+",status:"+status );
        return new ResponseEnvelope<>(flag,"创建渲染任务失败！");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEnvelope handleAutoReplaceProcessor(ResRenderPic resRenderPic) {
        logger.debug("Process in MobileAutoRenderTaskProcessor.handleAutoReplaceProcessor method parameter resRenderPic:{}", resRenderPic.toString());
        // 返回消息体
        ResponseEnvelope message = new ResponseEnvelope();
        Boolean flag = false;
        AutoRenderTask autoRenderTask = null;
        String token = resRenderPic.getToken();
        String platformCode = resRenderPic.getPlatformCode();
        try {
            autoRenderTask = getAutoRenderTask(resRenderPic);
        } catch (GeneratePanoramaException e) {
            return new ResponseEnvelope(e.isFlag(), e.getMessage());
        }

        try {
            // 处理保存自动渲染任务数据
           Integer taskId =  designPlanAutoRenderServiceImpl.handleAutoRenderTask(autoRenderTask, resRenderPic, resRenderPic.getOrder());
            // 自动渲染任务添加至Redis缓存
           Integer result =  designPlanAutoRenderServiceImpl.addRedisReplaceList(autoRenderTask);
           boolean status = backFillTaskId(autoRenderTask.getOrderNumber(), taskId,token,platformCode);
           if (result > 0 && status) {
               flag =true;
               return new ResponseEnvelope<>(flag,"创建渲染任务成功！");
           }
           flag =false;
            logger.error("移动端替换渲染创建渲染任务失败===>result:"+result+",status:"+status );
           return new ResponseEnvelope<>(flag,"创建渲染任务失败！");

        } catch (GeneratePanoramaException e) {
            logger.error("Process in MobileAutoRenderTaskProcessor.handleAutoReplaceProcessor method happend GeneratePanoramaException e:", e);
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }
        return message;
    }

}
