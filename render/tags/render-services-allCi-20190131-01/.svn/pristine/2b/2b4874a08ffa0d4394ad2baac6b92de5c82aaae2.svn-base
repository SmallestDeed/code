package com.nork.render.service.impl;

import com.nork.common.exception.BizException;
import com.nork.common.exception.GeneratePanoramaException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoRenderTask;
import com.nork.render.model.vo.FullHouseRenderTaskVO;
import com.nork.render.model.input.RenderPayInput;
import com.nork.render.service.IAutoRenderService;
import com.nork.system.model.ResRenderPic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.nork.common.constant.SystemCommonConstant.REDIS_TASK_LIST;
import static com.nork.common.constant.SystemCommonConstant.REDIS_TASK_REPLACE_LIST;

/**
 * Description: 添加运营网站渲染任务
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/8
 */
@Service("webAutoRenderTaskProcessor")
public class WebAutoRenderTaskProcessor extends AbstractAutoRenderProcessor implements IAutoRenderService {

    /**
     * step1 ：构造自动渲染任务
     * step2 ：构造订单信息
     * step3 ：添加任务
     * step4 ：添加缓存
     * step5 ：维护订单与任务的关系（回填任务ID至订单信息中）
     *
     * @param resRenderPic
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEnvelope handleAutoRenderProcessor(ResRenderPic resRenderPic) {
        logger.debug("Process in WebAutoRenderTaskProcessor.handleAutoRenderProcessor method parameter resRenderPic:{}", resRenderPic.toString());
        AutoRenderTask autoRenderTask = null;
        String token = resRenderPic.getToken();
        String platformCode = resRenderPic.getPlatformCode();
        try {
            // 构造渲染任务对象
            autoRenderTask = getAutoRenderTask(resRenderPic);
            // modified by zhangchengda
            // 2018/9/28 10:28
            // 暂时解决B端替换渲染报错问题
            createSingleHouseTask(resRenderPic, autoRenderTask);
        } catch (GeneratePanoramaException e) {
            return new ResponseEnvelope(e.isFlag(), e.getMessage());
        }

        Boolean flag = false;
        logger.error("插入task任务 ===> groupPrimaryId="+autoRenderTask.getGroupPrimaryId());
        Integer taskId = designPlanAutoRenderService.add(autoRenderTask);
        autoRenderTask.setId(taskId);
        Integer result = designPlanAutoRenderService.addRedisLists(autoRenderTask);
        //回填任务ID
        boolean status = backFillTaskId(autoRenderTask.getOrderNumber(), taskId, token, platformCode);
        if (result > 0 && status) {
            Long taskReplaceListSize = redisService.getSizeOfList(REDIS_TASK_REPLACE_LIST);
            logger.error("创建单空间装进我家渲染任务，taskReplaceListSize="+taskReplaceListSize+",taskListSize="+result);
            Long allTaskListSize = taskReplaceListSize + result;
            flag = true;
            return new ResponseEnvelope<>(flag, new FullHouseRenderTaskVO(null, null, taskId, allTaskListSize),"创建渲染任务成功！","");
        }
        flag = false;
        logger.error("运营网站装进我家创建渲染任务失败===>result:" + result + ",status:" + status);
        return new ResponseEnvelope<>(flag, "创建渲染任务失败！");
    }

    /**
     * step1 : 校验数据完整性构造自动渲染任务
     * step2 ：校验并组装订单数据对象
     * step3 ：保存订单信息
     * step4 ：订单信息设置缓存
     * step5 ：修改用户账户余额并回填任务绑定至订单信息
     *
     * @param resRenderPic
     * @return
     */
    @SuppressWarnings("null")
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEnvelope handleAutoReplaceProcessor(ResRenderPic resRenderPic) {
        logger.debug("Process in WebAutoRenderTaskProcessor.handleAutoReplaceProcessor method parameter resRenderPic:{}", resRenderPic.toString());
        String orderNo = resRenderPic.getOrderNo();
        ResponseEnvelope responseEnvelope = new ResponseEnvelope<>();
        String token = resRenderPic.getToken();
        String platformCode = resRenderPic.getPlatformCode();
        Integer result = null;
        //生成替换渲染任务start
        AutoRenderTask autoRenderTask = null;
        Integer taskId = null;
        try {
            autoRenderTask = getAutoRenderTask(resRenderPic);
            // modified by zhangchengda
            // 2018/9/28 10:28
            // 暂时解决B端替换渲染报错问题
            createSingleHouseTask(resRenderPic, autoRenderTask);
        } catch (GeneratePanoramaException e) {
            return new ResponseEnvelope(e.isFlag(), e.getMessage());
        }


        try {
            // 处理保存自动渲染任务数据
            taskId = designPlanAutoRenderService.handleAutoRenderTask(autoRenderTask, resRenderPic, orderNo);
            autoRenderTask.setId(taskId);
            // 自动渲染任务添加至Redis缓存
            result = designPlanAutoRenderService.addRedisReplaceList(autoRenderTask);

        } catch (GeneratePanoramaException e) {
            logger.error("Process in MobileAutoRenderTaskProcessor.handleAutoReplaceProcessor method happend GeneratePanoramaException e:", e);
            responseEnvelope.setSuccess(false);
            responseEnvelope.setMessage(e.getMessage());
        }

        boolean status = backFillTaskId(autoRenderTask.getOrderNumber(), taskId, token, platformCode);
        if (result > 0 && status) {
            Long taskListSize = redisService.getSizeOfList(REDIS_TASK_LIST);
            logger.error("创建单空间替换渲染任务，taskReplaceListSize="+result+",taskListSize="+taskListSize);
            Long allTaskListSize = taskListSize + result;
            responseEnvelope.setObj(new FullHouseRenderTaskVO(null, null, taskId, allTaskListSize));
            responseEnvelope.setMessage("添加渲染任务成功！");
            responseEnvelope.setSuccess(true);
        } else {
            responseEnvelope.setMessage("添加渲染任务失败！");
            responseEnvelope.setSuccess(false);
            logger.error("运营网站替换渲染创建渲染任务失败===>result:" + result + ",status:" + status);
        }
        //请求成功标志
        return responseEnvelope;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEnvelope handleFullHouseAutoRenderProcessor(ResRenderPic resRenderPic) throws GeneratePanoramaException {
        return handleFullHouseAutoRenderTask(resRenderPic);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEnvelope handleFullHouseAutoReplaceProcessor(ResRenderPic resRenderPic) throws GeneratePanoramaException{
        return handleFullHouseAutoReplaceTask(resRenderPic);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEnvelope handleFullHouseAutoRenderNewProcessor(ResRenderPic resRenderPic) throws GeneratePanoramaException{

        return handleFullHousePlanAutoRenderNewTask(resRenderPic);

    }





    /**
     * 回填任务Id
     * @param orderNo
     * @param taskId
     * @return
     */
   /* private  String backFillTaskId( String orderNo,String taskId,String token){
//       	String orderNo = "20171216103938278008";
//       	String taskId = "132546";
       	String url = "http://192.168.3.36:30011/v1/web/pay/payOrder/updatePayoOrder?orderNo="+orderNo+"&taskId="+taskId;
       	Map<String,String> params=new HashMap<String,String>();
       	params.put("orderNo", orderNo);
    	params.put("taskId", taskId);
    	try {
    		String result = Utils.doPostMethod(url, params,token);
    		if (result != null) {
    			return result;
    		}
    	} catch (Exception e) {
    		return "回填任务ID失败";
    	}
    	return null;
       }*/

    @Override
    public AutoRenderTask createHDRenderTask(RenderPayInput payInput, String orderNo, LoginUser loginUser) throws BizException {
        return super.createHDRenderTask(payInput, orderNo, loginUser);
    }
}
