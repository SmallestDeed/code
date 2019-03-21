package com.nork.mobile.controller;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.collections.Lists;
import com.nork.design.dao.DesignPlanAutoRenderMapper;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.mobile.model.MobileProductReplace;
import com.nork.mobile.model.search.MobileRenderRecord;
import com.nork.mobile.service.MobilePayRenderService;
import com.nork.pay.metadata.ProductType;
import com.nork.render.model.RenderTypeCode;
import com.sandu.common.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.UnknownHostException;
import java.util.List;

/**
 * 移动端消费的 支付积分的controller
 *
 * @author yangz
 * @CreateDate 2017年9月4日15:31:28
 */
@Controller
@RequestMapping("/{style}/mobile/pay")
public class MobilePayRenderController {

    @Autowired
    private MobilePayRenderService mobilePayRenderService;
    @Autowired
    private DesignPlanAutoRenderMapper designPlanAutoRenderMapper;

    /**
     * 判断一个推荐方案是否在移动端经过渲染
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/whetherRender")
    @ResponseBody
    public Object whetherRender(@RequestBody MobileRenderRecord record) {
        AutoRenderTask model = new AutoRenderTask();
        model.setOperationUserId(record.getUserId());
        model.setTemplateId(record.getTemplateId());
        model.setPlanId(record.getRecommendedPlanId());
        model.setIsDeleted(new Integer(0));
        // 查询该用户的所有替换记录和方案名称的list 这里只会查出刚刚插入的任务，未开始渲染的，开始了的会被删除
        List<AutoRenderTask> list = designPlanAutoRenderMapper.getALLReplaceRecordByUserId(model);
        model.setState(new Integer(1));//设置查询条件，只查询渲染成功的作为判断是否渲染过的依据
        List<AutoRenderTask> list2 = designPlanAutoRenderMapper.getAllReplaceRecordByUserId2(model);
        // 合并两个list
        list.addAll(list2);
        if (Lists.isNotEmpty(list)) {
            for (AutoRenderTask state : list) {
                if (ProductType.PHOTO.equals(state.getRenderTypesStr())) {
                    state.setRenderingType(RenderTypeCode.COMMON_PICTURE_LEVEL);
                    state.setTaskType(state.getRenderPic());
                } else if (ProductType.ROAM720.equals(state.getRenderTypesStr())) {
                    state.setRenderingType(RenderTypeCode.COMMON_720_LEVEL);
                    state.setTaskType(state.getRender720());
                } else if (ProductType.VIDEO.equals(state.getRenderTypesStr())) {
                    state.setRenderingType(RenderTypeCode.COMMON_VIDEO);
                    state.setTaskType(state.getRenderVideo());
                } else if (ProductType.ROAMN720.equals(state.getRenderTypesStr())) {
                    state.setRenderingType(RenderTypeCode.ROAM_720_LEVEL);
                    state.setTaskType(state.getRenderN720());
                }
            }
        }

        return new ResponseEnvelope<>(list.size(), list);
    }


    /**
     * 一键替换 生成订单、消费记录和渲染记录的接口
     *
     * @param model
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/replaceRecord")
    @ResponseBody
    public Object replaceRecord(@RequestBody MobileProductReplace model) throws UnknownHostException {

        return mobilePayRenderService.replaceRecord(model);
    }

    /**
     * 查询该用户的所有替换记录 add by yangzhun
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/getMyReplaceRecord")
    @ResponseBody
    public Object getMyReplaceRecord(@RequestBody AutoRenderTask model) {

        model.setIsDeleted(new Integer(0));
        /*List<AutoRenderTaskState> list = designPlanAutoRenderMapper
				.getALLReplaceRecordByUserId(model);
		return new ResponseEnvelope<AutoRenderTaskState>(list);*/
        return mobilePayRenderService.getALLReplaceRecordByUserId(model);
    }


    /**
     * 移动端删除我的任务和设计 add by yangz
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/deteleMyTaskAndDesign")
    @ResponseBody
    public Object deteleMyTaskAndDesign(@RequestBody AutoRenderTaskState model) {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope<>(false,"请登录");
        }
        model.setOperationUserId(loginUser.getId());

        return mobilePayRenderService.deteleMyTaskAndDesign(model);
    }

}
