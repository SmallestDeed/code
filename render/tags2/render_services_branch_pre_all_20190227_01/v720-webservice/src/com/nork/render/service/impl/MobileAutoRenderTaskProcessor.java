package com.nork.render.service.impl;

import com.nork.common.exception.GeneratePanoramaException;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoRenderTask;
import com.nork.render.service.IAutoRenderService;
import com.nork.system.model.ResRenderPic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.nork.common.constant.SystemCommonConstant.REDIS_TASK_LIST;
import static com.nork.common.constant.SystemCommonConstant.REDIS_TASK_REPLACE_LIST;

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
            //单空间方案
            createSingleHouseTask(resRenderPic,autoRenderTask);

        } catch (GeneratePanoramaException e) {
            return new ResponseEnvelope(e.isFlag(), e.getMessage());
        }
        String token = resRenderPic.getToken();
        String platformCode = resRenderPic.getPlatformCode();
        Boolean flag = false;
        Integer taskId = designPlanAutoRenderService.add(autoRenderTask);
        autoRenderTask.setId(taskId);
        Integer result = designPlanAutoRenderService.addRedisLists(autoRenderTask);
        boolean status = backFillTaskId(autoRenderTask.getOrderNumber(), taskId, token, platformCode);

        if (result > 0 && status) {
            Long taskReplaceListSize = redisService.getSizeOfList(REDIS_TASK_REPLACE_LIST);
            logger.error("创建单空间装进我家渲染任务，taskReplaceListSize="+taskReplaceListSize+",taskListSize="+result);
            Long allTaskListSize = taskReplaceListSize + result;
            flag = true;
            return new ResponseEnvelope<>(flag, allTaskListSize,"创建渲染任务成功！","");
        }
        flag = false;
        logger.error("B端装进我家创建渲染任务失败===>result:" + result + ",status:" + status);
        return new ResponseEnvelope<>(flag, "创建渲染任务失败！");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEnvelope handleAutoReplaceProcessor(ResRenderPic resRenderPic) {
        logger.debug("Process in MobileAutoRenderTaskProcessor.handleAutoReplaceProcessor method parameter resRenderPic:{}", resRenderPic.toString());
        // 返回消息体
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        Boolean flag = false;
        AutoRenderTask autoRenderTask = null;
        String token = resRenderPic.getToken();
        String platformCode = resRenderPic.getPlatformCode();
        try {
            autoRenderTask = getAutoRenderTask(resRenderPic);
            //单空间方案
            createSingleHouseTask(resRenderPic,autoRenderTask);

        } catch (GeneratePanoramaException e) {
            return new ResponseEnvelope(e.isFlag(), e.getMessage());
        }

        try {
            // 处理保存自动渲染任务数据
            Integer taskId = designPlanAutoRenderService.handleAutoRenderTask(autoRenderTask, resRenderPic, resRenderPic.getOrder());
            autoRenderTask.setTaskId(taskId);
            // 自动渲染任务添加至Redis缓存
            Integer result = designPlanAutoRenderService.addRedisReplaceList(autoRenderTask);
            boolean status = backFillTaskId(autoRenderTask.getOrderNumber(), taskId, token, platformCode);
            if (result > 0 && status) {
                Long taskListSize = redisService.getSizeOfList(REDIS_TASK_LIST);
                logger.error("创建单空间替换渲染任务，taskReplaceListSize="+result+",taskListSize="+taskListSize);
                Long allTaskListSize = taskListSize + result;
                flag = true;
                return new ResponseEnvelope<>(flag, allTaskListSize, "创建渲染任务成功！", "");
            }
            flag = false;
            logger.error("移动端替换渲染创建渲染任务失败===>result:" + result + ",status:" + status);
            return new ResponseEnvelope<>(flag, "创建渲染任务失败！");

        } catch (GeneratePanoramaException e) {
            logger.error("Process in MobileAutoRenderTaskProcessor.handleAutoReplaceProcessor method happend GeneratePanoramaException e:", e);
            responseEnvelope.setSuccess(false);
            responseEnvelope.setMessage(e.getMessage());
        }
        return responseEnvelope;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEnvelope handleFullHouseAutoRenderProcessor(ResRenderPic resRenderPic) throws GeneratePanoramaException {
        return handleFullHouseAutoRenderTask(resRenderPic);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEnvelope handleFullHouseAutoReplaceProcessor(ResRenderPic resRenderPic) throws GeneratePanoramaException {
        return handleFullHouseAutoReplaceTask(resRenderPic);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEnvelope handleFullHouseAutoRenderNewProcessor(ResRenderPic resRenderPic) throws GeneratePanoramaException{

        return handleFullHousePlanAutoRenderNewTask(resRenderPic);

    }

}
