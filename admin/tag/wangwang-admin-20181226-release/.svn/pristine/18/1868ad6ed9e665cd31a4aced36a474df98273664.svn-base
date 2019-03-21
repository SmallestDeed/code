package com.sandu.web.designplan.controller;

import com.github.pagehelper.PageInfo;
import com.nork.common.model.LoginUser;
import com.sandu.api.queue.SyncMessage;
import com.sandu.api.queue.service.QueueService;
import com.sandu.api.solution.input.CompanyShopDesignPlanVo;
import com.sandu.api.solution.input.DesignPlanRecommendedQuery;
import com.sandu.api.solution.input.FullHouseDesignPlanQuery;
import com.sandu.api.solution.model.bo.DesignPlanBO;
import com.sandu.api.solution.model.bo.FullHouseDesignPlanBO;
import com.sandu.api.solution.output.DesignPlanVO;
import com.sandu.api.solution.output.FullHouseDesignPlanVO;
import com.sandu.api.solution.output.FullHousePlanVO;
import com.sandu.api.solution.service.DesignPlanRecommendedService;
import com.sandu.api.solution.service.FullHouseDesignPlanService;
import com.sandu.api.solution.service.biz.DesignPlanBizService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.ReturnData;
import com.sandu.commons.ListCopyUtils;
import com.sandu.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sandu.constant.ResponseEnum.*;
import static com.sandu.util.Commoner.isEmpty;
import static com.sandu.util.Commoner.isNotEmpty;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 15:33 2018/8/8
 */

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/v1/storeplan")
@Api(tags = "storePlanManage", description = "店铺方案管理")
public class StorePlanController extends BaseController {

    @Resource
    private DesignPlanRecommendedService designPlanRecommendedService;

    @Resource
    private DesignPlanBizService designPlanBizService;

    @Resource
    private FullHouseDesignPlanService fullHouseDesignPlanService;

    @Resource
    private QueueService queueService;

    @ApiOperation(value = "一键方案列表", response = DesignPlanVO.class)
    @GetMapping("/onekey/list")
    public ReturnData listOneKeyDesignPlan(DesignPlanRecommendedQuery query) {
        PageInfo<DesignPlanBO> datas = designPlanBizService.onkeyPlanStoreList(query);
        if (isEmpty(datas.getList())) {
            return ReturnData.builder().code(NOT_CONTENT).message("没有数据了");
        }
        List<DesignPlanVO> vos = datas.getList().stream().map(it -> new DesignPlanVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(datas.getList(), vos);
        PageInfo<DesignPlanVO> voPages = new PageInfo<>(vos);
        voPages.setTotal(datas.getTotal());
        return ReturnData.builder().data(voPages).code(SUCCESS).message("成功");
    }

    @ApiOperation(value = "普通方案列表", response = DesignPlanVO.class)
    @GetMapping("/common/list")
    public ReturnData listCommonDesignPlan(DesignPlanRecommendedQuery query) {
        PageInfo<DesignPlanBO> datas = designPlanBizService.commonPlanStoreList(query);
        if (isEmpty(datas.getList())) {
            return ReturnData.builder().code(NOT_CONTENT).message("没有数据了");
        }
        List<DesignPlanVO> vos = datas.getList().stream().map(it -> new DesignPlanVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(datas.getList(), vos);
        PageInfo<DesignPlanVO> voPages = new PageInfo<>(vos);
        voPages.setTotal(datas.getTotal());
        return ReturnData.builder().data(voPages).code(SUCCESS).message("成功");
    }

    @ApiOperation(value = "根据id获取一键方案", response = DesignPlanVO.class)
    @GetMapping("/{planId}/{companyId}")
    public ReturnData getDesignPlan(@PathVariable long planId, @PathVariable Integer companyId) {
        List<DesignPlanBO> designPlanBOs = designPlanRecommendedService.getBaseInfos(planId, companyId);
        if (designPlanBOs != null && designPlanBOs.size() > 0) {
            List<DesignPlanVO> vos = designPlanBOs.stream().map(it -> new DesignPlanVO()).collect(Collectors.toList());
            ListCopyUtils.copyListProps(designPlanBOs, vos);
            return ReturnData.builder().data(vos).code(SUCCESS).message("成功");
        }
        return ReturnData.builder().code(NOT_CONTENT).message("没有数据了");
    }

    @ApiOperation(value = "发布或取消发布一键方案或普通方案")
    @PostMapping("/plan/publishPlan")
    public ReturnData publishDesignPlan(@RequestBody CompanyShopDesignPlanVo companyShopDesignPlanVo) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (null == loginUser || loginUser.getId() == 0) {
            return ReturnData.builder().code(UNAUTHORIZED).message("请先登录");
        }
        if (null == companyShopDesignPlanVo) {
            return ReturnData.builder().code(PARAM_ERROR).message("参数缺失");
        }

        List<Integer> planIds = new ArrayList<>();
        companyShopDesignPlanVo.getCompanyShopDesignPlanAddList().forEach(list ->
                planIds.add(list.getPlanId())
        );

        Integer count = 0;
        //取消发布或发布
        if (companyShopDesignPlanVo.getIsContainsId() == 1 && companyShopDesignPlanVo.getIsDeleted() == 1) {
            count = designPlanRecommendedService.updateCompanyShopDesignPlan(companyShopDesignPlanVo.getCompanyShopDesignPlanAddList(),
                    loginUser.getName(), companyShopDesignPlanVo.getIsDeleted(),companyShopDesignPlanVo.getShopType());
            sycMessageDoSend(SyncMessage.ACTION_DELETE, planIds,
                    SyncMessage.PLAN_TYPE_RECOMMENDED);
            if (null != count && count > 0) {
                return ReturnData.builder().data(count).code(SUCCESS).message("取消发布");
            }
            //新增发布
        } else if (companyShopDesignPlanVo.getIsDeleted() == 0 && companyShopDesignPlanVo.getIsContainsId() == 0) {
            count = designPlanRecommendedService.addCompanyShopDesignPlan(companyShopDesignPlanVo.getCompanyShopDesignPlanAddList(),
                    loginUser.getName(),companyShopDesignPlanVo.getShopType());
            sycMessageDoSend(SyncMessage.ACTION_ADD, planIds,
                    SyncMessage.PLAN_TYPE_RECOMMENDED);
            if (null != count && count > 0) {
                return ReturnData.builder().data(count).code(SUCCESS).message("成功发布");
            }
        } else if (companyShopDesignPlanVo.getIsContainsId() == 1 && companyShopDesignPlanVo.getIsDeleted() == 0) {
            count = designPlanRecommendedService.addCompanyShopDesignPlan(companyShopDesignPlanVo.getCompanyShopDesignPlanAddList(),
                    loginUser.getName(),companyShopDesignPlanVo.getShopType());
            sycMessageDoSend(SyncMessage.ACTION_ADD, planIds,
                    SyncMessage.PLAN_TYPE_RECOMMENDED);
            if (null != count && count > 0) {
                return ReturnData.builder().data(count).code(SUCCESS).message("成功发布");
            }
        }

        return ReturnData.builder().code(NOT_CONTENT).message("没有数据了");
    }

    @ApiOperation(value = "全屋方案列表", response = FullHousePlanVO.class)
    @GetMapping("/fullHouse/list")
    public ReturnData listFullHousePlan(FullHouseDesignPlanQuery query) {
        PageInfo<FullHouseDesignPlanBO> datas = fullHouseDesignPlanService.selectStoreFullHousePlan(query);

        if (isEmpty(datas.getList())) {
            return ReturnData.builder().code(NOT_CONTENT).message("没有数据了");
        }
        List<FullHouseDesignPlanVO> vos = datas.getList().stream().map(it -> new FullHouseDesignPlanVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(datas.getList(), vos);
        List<FullHousePlanVO> results = new ArrayList<>(vos.size());
        for (FullHouseDesignPlanVO fullHouseVO : vos) {
            FullHousePlanVO vo = convertBean(fullHouseVO);
            results.add(vo);
        }
        PageInfo<FullHousePlanVO> voPages = new PageInfo<>(results);
        voPages.setTotal(datas.getTotal());
        return ReturnData.builder().data(voPages).code(SUCCESS).message("成功");
    }

    @ApiOperation(value = "根据id获取全屋方案", response = FullHousePlanVO.class)
    @GetMapping("/fullHouse/{planId}/{companyId}")
    public ReturnData getFullHousePlan(@PathVariable Integer planId, @PathVariable Integer companyId) {
        FullHouseDesignPlanBO designPlanBO = fullHouseDesignPlanService.getBaseInfo(planId, companyId);
        if (isNotEmpty(designPlanBO)) {
            FullHouseDesignPlanVO vo = new FullHouseDesignPlanVO();
            BeanUtils.copyProperties(designPlanBO, vo);
            FullHousePlanVO planVo = convertBean(vo);
            return ReturnData.builder().data(planVo).code(SUCCESS);
        }
        return ReturnData.builder().code(NOT_FOUND);
    }

    @ApiOperation(value = "根据全屋方案id获取组合方案信息", response = DesignPlanVO.class)
    @GetMapping("/detail/{planId}/{companyId}")
    public ReturnData getDetailDesignPlan(@PathVariable Integer planId, @PathVariable Integer companyId) {
        List<DesignPlanBO> designPlanBOList = fullHouseDesignPlanService.getDetailDesignPlan(planId, companyId);
        if (designPlanBOList != null && designPlanBOList.size() > 0) {
            List<DesignPlanVO> vos = designPlanBOList.stream().map(it -> new DesignPlanVO()).collect(Collectors.toList());
            ListCopyUtils.copyListProps(designPlanBOList, vos);
            return ReturnData.builder().data(vos).code(SUCCESS);
        }
        return ReturnData.builder().code(NOT_FOUND);
    }

    @ApiOperation(value = "发布或取消发布全屋方案")
    @PostMapping("/fullHouse/publish")
    public ReturnData publishFullHousePlan(@RequestBody CompanyShopDesignPlanVo companyShopDesignPlanVo) {

        List<Integer> planIds = new ArrayList<>();
        companyShopDesignPlanVo.getCompanyShopDesignPlanAddList().forEach(list ->
                planIds.add(list.getPlanId())
        );

        Integer count;

        if (companyShopDesignPlanVo.getIsContainsId() == 1) {
            //取消发布
            count = fullHouseDesignPlanService.cancelPublish(companyShopDesignPlanVo.getCompanyShopDesignPlanAddList());
            sycMessageDoSend(SyncMessage.ACTION_DELETE, planIds,
                    SyncMessage.PLAN_TYPE_FULLHOUSE);
        } else {
            //发布
            count = fullHouseDesignPlanService.publish(companyShopDesignPlanVo.getCompanyShopDesignPlanAddList());
            sycMessageDoSend(SyncMessage.ACTION_ADD, planIds,
                    SyncMessage.PLAN_TYPE_FULLHOUSE);
        }
        if (count > 0) {
            return ReturnData.builder().code(SUCCESS).message("成功");
        }
        return ReturnData.builder().code(ERROR).message("失败");
    }

    /**
     * 数据转换
     *
     * @return
     */
    private FullHousePlanVO convertBean(FullHouseDesignPlanVO fullHouseVO) {
        FullHousePlanVO vo = new FullHousePlanVO();
        vo.setPlanId(fullHouseVO.getId());
        vo.setPlanCode(fullHouseVO.getPlanCode());
        vo.setPlanName(fullHouseVO.getPlanName());
        vo.setIsPublish(fullHouseVO.getIsPublish());
        vo.setPicId(fullHouseVO.getPlanPicId());
        vo.setPicPath(fullHouseVO.getPlanPicPath());
        vo.setBrandId(fullHouseVO.getBrandId());
        vo.setBrandName(fullHouseVO.getBrandName());
        vo.setCompanyId(fullHouseVO.getCompanyId());
        vo.setDeliverStatus(fullHouseVO.getDeliverStatus());
        vo.setSourceType(fullHouseVO.getSourceType());
        vo.setOrigin(fullHouseVO.getSourceName());
        vo.setDesignStyleId(fullHouseVO.getPlanStyleId());
        vo.setDesignStyleName(fullHouseVO.getPlanStyleName());
        vo.setCompleteDate(fullHouseVO.getGmtCreate());
        vo.setDesignerId(fullHouseVO.getUserId());
        vo.setDesigner(fullHouseVO.getUserName());
        vo.setRemark(fullHouseVO.getRemark());
        vo.setPlanDesc(fullHouseVO.getPlanDescribe());
        vo.setDecoratePriceInfoList(fullHouseVO.getDecoratePriceInfoList());
        vo.setChargeType(fullHouseVO.getChargeType());
        vo.setPlanPrice(fullHouseVO.getPlanPrice());
        return vo;
    }

    private void sycMessageDoSend(Integer messageAction, List<Integer> ids, Integer planType) {
        List<Map> content = ids.stream().map(item -> {
            HashMap<String, Integer> tmp = new HashMap<>(1);
            tmp.put("id", item);
            tmp.put("planTableType", planType);
            return tmp;
        }).collect(Collectors.toList());
        SyncMessage message = new SyncMessage();
        message.setAction(messageAction);
        message.setMessageId("P-" + System.currentTimeMillis());
        message.setModule(SyncMessage.MODULE_SOLUTION_RECOMMEND);
        message.setPlatformType(Constants.PLATFORM_CODE_MERCHANT_MANAGE);
        message.setObject(content);
        queueService.send(message);
    }

}
