package com.sandu.web.designplan.controller;

import com.github.pagehelper.PageInfo;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.service.BrandService;
import com.sandu.api.product.model.bo.SolutionProductListBO;
import com.sandu.api.product.output.SolutionProductListVO;
import com.sandu.api.queue.SyncMessage;
import com.sandu.api.queue.service.QueueService;
import com.sandu.api.solution.input.*;
import com.sandu.api.solution.model.DesignPlanRecommended;
import com.sandu.api.solution.model.bo.*;
import com.sandu.api.solution.model.po.*;
import com.sandu.api.solution.output.*;
import com.sandu.api.solution.service.DesignPlanRecommendedService;
import com.sandu.api.solution.service.PlanDecoratePriceService;
import com.sandu.api.solution.service.biz.DesignPlanBizService;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.SysUserRole;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.BaseQuery;
import com.sandu.common.LoginContext;
import com.sandu.common.ReturnData;
import com.sandu.commons.ListCopyUtils;
import com.sandu.constant.Constants;
import com.sandu.constant.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.common.ReturnData.builder;
import static com.sandu.constant.Punctuation.COMMA;
import static com.sandu.constant.ResponseEnum.*;
import static com.sandu.util.Commoner.isEmpty;
import static com.sandu.util.Commoner.isNotEmpty;

/**
 * @author by bvvy
 */
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/v1/designplan")
@Api(tags = "OneKeyDesignPlan", description = "一键方案")
public class DesignPlanController extends BaseController {

    private Logger log = LoggerFactory.getLogger(DesignPlanController.class);

    @Resource
    private DesignPlanRecommendedService designPlanRecommendedService;

    @Resource
    private DesignPlanBizService designPlanBizService;

    @Resource
    private BrandService brandService;

    @Resource
    private QueueService queueService;
    @Autowired
    private SysRoleService sysRoleService;

    private static final String PLAN_ROLE_CODE = "Schemecharge";

    @Resource
    private PlanDecoratePriceService planDecoratePriceService;

    @RequiresPermissions({"solution:view"})
    @ApiOperation(value = "一键方案列表", response = DesignPlanVO.class)
    @GetMapping("/onekey/list")
    public ReturnData listOneKeyDesignPlan(DesignPlanRecommendedQuery query) {
        PageInfo<DesignPlanBO> datas = designPlanBizService.listOnekeyDesignPlan(query);

        if (isEmpty(datas.getList())) {
            return ReturnData.builder().code(NOT_CONTENT).message("没有数据了");
        }
        List<DesignPlanVO> vos = datas.getList().stream().map(it -> new DesignPlanVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(datas.getList(), vos);
        PageInfo<DesignPlanVO> voPages = new PageInfo<>(vos);
        voPages.setTotal(datas.getTotal());
        return ReturnData.builder().data(voPages).code(SUCCESS).message("成功");
    }

    @RequiresPermissions({"solution:view"})
    @ApiOperation(value = "普通方案列表", response = DesignPlanVO.class)
    @GetMapping("/common/list")
    public ReturnData listCommonDesignPlan(DesignPlanRecommendedQuery query) {
        PageInfo<DesignPlanBO> datas = designPlanBizService.listCommonDesignPlan(query);
        if (isEmpty(datas.getList())) {
            return ReturnData.builder().code(NOT_CONTENT).message("没有数据了");
        }
        List<DesignPlanVO> vos = datas.getList().stream().map(it -> new DesignPlanVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(datas.getList(), vos);
        PageInfo<DesignPlanVO> voPages = new PageInfo<>(vos);
        voPages.setTotal(datas.getTotal());
        return ReturnData.builder().data(voPages).code(SUCCESS).message("成功");
    }

    @RequiresPermissions({"product:manage"})
    @ApiOperation(value = "公开/取消公开方案")
    @PutMapping("/secrecy")
    public ReturnData changeProductSecrecy(@Valid @RequestBody DesignControlUpdate productControlUpdate,
                                           BindingResult validResult) {
        ReturnData data = builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        Integer secrecy = productControlUpdate.getSecrecyFlag();
        boolean flag = designPlanBizService.batchChangePlanSecrecy(productControlUpdate.getPlanIds(), secrecy);
        if (!flag) {
            return builder().code(ERROR).success(false).message("操作失败...");
        }
//        sycMessageDoSend(SyncMessage.ACTION_UPDATE, productControlUpdate.getProductIds());
        return data.success(true).code(SUCCESS);
    }


    @RequiresPermissions({"solution:view"})
    @ApiOperation(value = "根据id获取一键方案", response = DesignPlanVO.class)
    @GetMapping("/{planId}/{companyId}")
    public ReturnData getDesignPlan(@PathVariable long planId, @PathVariable Integer companyId) {
        DesignPlanBO designPlanBO = designPlanRecommendedService.getBaseInfo(planId, companyId);
        if (isNotEmpty(designPlanBO)) {
            DesignPlanVO vo = new DesignPlanVO();
            BeanUtils.copyProperties(designPlanBO, vo);
            return ReturnData.builder().data(vo).code(SUCCESS);
        }
        return ReturnData.builder().code(NOT_FOUND);
    }

    @ApiOperation(value = "根据id获取一键方案组合详情子方案信息", response = DesignPlanVO.class)
    @GetMapping("/comboDetail/{planId}/{companyId}")
    public ReturnData getDesignPlanComboDetail(@PathVariable long planId, @PathVariable Integer companyId) {
        List<DesignPlanBO> designPlanBOList = designPlanRecommendedService.getBaseInfos(planId, companyId);
        if (designPlanBOList != null && designPlanBOList.size() > 0) {
            List<DesignPlanVO> vos = designPlanBOList.stream().map(it -> new DesignPlanVO()).collect(Collectors.toList());
            ListCopyUtils.copyListProps(designPlanBOList, vos);
            return ReturnData.builder().data(vos).code(SUCCESS);
        }
        return ReturnData.builder().code(NOT_FOUND);
    }

    @RequiresPermissions({"solution:view"})
    @ApiOperation(value = "根据id获取一键方案", response = DesignPlanVO.class)
    @GetMapping("/{planId}")
    public ReturnData getDesignPlan(@PathVariable long planId) {
        DesignPlanBO designPlanBO = designPlanRecommendedService.getBaseInfo(planId);
        if (isNotEmpty(designPlanBO)) {
            DesignPlanVO vo = new DesignPlanVO();
            BeanUtils.copyProperties(designPlanBO, vo);
            return ReturnData.builder().data(vo).code(SUCCESS);
        }
        return ReturnData.builder().code(NOT_FOUND);
    }

    @RequiresPermissions({"solution:edit"})
    @ApiOperation(value = "编辑一键方案", response = ReturnData.class)
    @PutMapping
    public ReturnData updateDesignPlan(@Valid @RequestBody DesignPlanUpdate designPlanUpdate, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        designPlanRecommendedService.updateDesignPlanRecommended(designPlanUpdate);
        List<Integer> planidList = Arrays.stream(designPlanUpdate.getPlanId().toString().split(COMMA)).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        sycMessageDoSend(SyncMessage.ACTION_UPDATE, planidList);
        return ReturnData.builder().code(SUCCESS).message("成功");
    }

    @RequiresPermissions({"solution:del"})
    @DeleteMapping
    @ApiOperation("删除一键方案")
    public ReturnData deleteDesignPlan(@RequestParam String planIds) {
        Arrays.stream(planIds.split(COMMA)).forEach(id -> {
            designPlanRecommendedService.deleteDesignPlanRecommended(Long.parseLong(id));
            designPlanBizService.removePlanBrand(Integer.valueOf(id));
            planDecoratePriceService.deleteByRecommendId(Integer.valueOf(id));
            designPlanBizService.removeLog(Integer.valueOf(id), 1);
        });
        if (StringUtils.isNoneBlank(planIds)) {
            List<Integer> planidList = Arrays.stream(planIds.split(COMMA)).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
            List<Integer> planId = designPlanRecommendedService.selectMainSubPlanList(planidList);
            sycMessageDoSend(SyncMessage.ACTION_DELETE, planId);
        }
        return ReturnData.builder().code(SUCCESS);
    }

    @GetMapping("/{planId}/diagram")
    @ApiOperation(value = "获取一键方案效果图", response = DesignPlanEffectDiagramMergeVO.class)
    public ReturnData getEffectDiagram(@PathVariable Long planId) {
        DesignPlanEffectDiagramMergeBO designPlanEffectDiagramBO = designPlanRecommendedService.getEffectDiagram(planId);
        DesignPlanEffectDiagramMergeVO designPlanEffectDiagramMergeVO = new DesignPlanEffectDiagramMergeVO();
        BeanUtils.copyProperties(designPlanEffectDiagramBO, designPlanEffectDiagramMergeVO);
        return ReturnData.builder().data(designPlanEffectDiagramMergeVO).code(SUCCESS).message("成功");
    }

    @RequiresPermissions({"solution:manage"})
    @PostMapping("/allot")
    @ApiOperation("分配一键方案")
    public ReturnData allot(@Valid @RequestBody DesignPlanAllotPO designPlanAllot, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        designPlanRecommendedService.allot(designPlanAllot);
        return ReturnData.builder().code(SUCCESS).message("分配成功");
    }

    @RequiresPermissions({"solution:manage"})
    @PostMapping("/allot/batch")
    @ApiOperation("批量分配一键方案")
    public ReturnData allot(@Valid @RequestBody List<DesignPlanAllotPO> designPlanAllots, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        for (DesignPlanAllotPO designPlanAllot : designPlanAllots) {
            designPlanRecommendedService.allot(designPlanAllot);
        }
        return ReturnData.builder().code(SUCCESS).message("分配成功");
    }

    @RequiresPermissions({"solution:delivery"})
    @PostMapping("/deliver")
    @ApiOperation("交付一键方案")
    public ReturnData deliver(@Valid @RequestBody DesignPlanDeliveryPO designPlanDelivery, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        List<Integer> ids = designPlanBizService.deliver(designPlanDelivery, false);
        return ReturnData.builder().code(SUCCESS).message("交付成功");
    }

    @RequiresPermissions({"solution:delivery"})
    @PostMapping("/deliver/batch")
    @ApiOperation("批量交付一键方案")
    public ReturnData delivers(@Valid @RequestBody DesignPlanDeliveryBatchPO designPlanDelivery, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        //数据校验
        for (Integer planId : designPlanDelivery.getDesignPlanId()) {
            DesignPlanRecommended result = designPlanRecommendedService.getDesignPlanRecommended(planId);
            if (!("diy").equals(result.getPlanSource()) && !("").equals(result.getPlanSource())
                    && !("huxing").equals(result.getPlanSource()) && result.getPlanSource() != null) {
                return ReturnData.builder().code(ERROR).message("交付方案来源只能为内部方案");
            }
        }
        //循环批量交付的方案id进行方案交付
        for (Integer planId : designPlanDelivery.getDesignPlanId()) {
            //数据转换
            DesignPlanDeliveryPO designPlanDeliveryPO = new DesignPlanDeliveryPO();
            designPlanDeliveryPO.setReceiveCompanyIds(designPlanDelivery.getReceiveCompanyIds());
            designPlanDeliveryPO.setDelivererId(designPlanDelivery.getDelivererId());
            designPlanDeliveryPO.setDeliveryCompanyId(designPlanDelivery.getDeliveryCompanyId());
            designPlanDeliveryPO.setReceiveBrandIds(designPlanDelivery.getReceiveBrandIds());
            designPlanDeliveryPO.setDesignPlanId(planId);
            designPlanBizService.deliver(designPlanDeliveryPO, true);
        }
        return ReturnData.builder().code(SUCCESS).message("交付成功");
    }

    @GetMapping("/products")
    @ApiOperation(value = "获取产品清单", response = SolutionProductListVO.class)
    public ReturnData products(DesignPlanProductQuery query) {
        PageInfo<SolutionProductListBO> pageInfo = designPlanBizService.listDesignPlanProducts(query);
        if (isNotEmpty(pageInfo.getList())) {
            List<SolutionProductListVO> vos = pageInfo.getList().stream().map(it -> new SolutionProductListVO()).collect(Collectors.toList());
            ListCopyUtils.copyListProps(pageInfo.getList(), vos);
            return ReturnData.builder().data(vos).total(pageInfo.getTotal()).code(SUCCESS).message("成功");
        } else {
            return ReturnData.builder().data(pageInfo.getList()).total(pageInfo.getTotal()).code(NOT_CONTENT);
        }
    }

    @PostMapping("/toggle/display")
    @ApiOperation("切换是否显示")
    public ReturnData toggleDisplay(@Valid @RequestBody DesignPlanProductDisplayPO displayBO, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        designPlanRecommendedService.toggleDisplay(displayBO);
        return ReturnData.builder().code(SUCCESS);
    }

    @RequiresPermissions({"biz:solution:view"})
    @GetMapping("/onekey/channel/list")
    @ApiOperation(value = "渠道列表", response = DesignPlanVO.class)
    public ReturnData onekeyChannelList(DesignPlanRecommendedQuery query) {
        PageInfo<DesignPlanBO> datas = designPlanBizService.listOnekeyChannelDesignPlan(query);
        if (isEmpty(datas.getList())) {
            return ReturnData.builder().code(NOT_CONTENT).message("没有数据了").list(datas.getList());
        }
        List<DesignPlanVO> vos = datas.getList().stream().map(it -> new DesignPlanVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(datas.getList(), vos);
        PageInfo<DesignPlanVO> voPages = new PageInfo<>(vos);
        voPages.setTotal(datas.getTotal());
        return ReturnData.builder().data(voPages).code(SUCCESS).message("成功");
    }

    @RequiresPermissions({"client:solution:view"})
    @GetMapping("/onekey/online/list")
    @ApiOperation(value = "线上列表", response = DesignPlanVO.class)
    public ReturnData onekeyOnlineList(DesignPlanRecommendedQuery query) {
        PageInfo<DesignPlanBO> datas = designPlanBizService.listOnekeyOnlineDesignPlan(query);
        if (isEmpty(datas.getList())) {
            return ReturnData.builder().code(NOT_CONTENT).message("没有数据了").list(datas.getList());
        }
        List<DesignPlanVO> vos = datas.getList().stream().map(it -> new DesignPlanVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(datas.getList(), vos);
        PageInfo<DesignPlanVO> voPages = new PageInfo<>(vos);
        voPages.setTotal(datas.getTotal());
        return ReturnData.builder().data(voPages).code(SUCCESS).message("成功");
    }

    @RequiresPermissions({"biz:solution:view"})
    @GetMapping("/common/channel/list")
    @ApiOperation(value = "渠道列表", response = DesignPlanVO.class)
    public ReturnData commonChannelList(DesignPlanRecommendedQuery query) {
        PageInfo<DesignPlanBO> datas = designPlanBizService.listCommonChannelDesignPlan(query);
        if (isEmpty(datas.getList())) {
            return ReturnData.builder().code(NOT_CONTENT).message("没有数据了").data(datas.getList());
        }
        List<DesignPlanVO> vos = datas.getList().stream().map(it -> new DesignPlanVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(datas.getList(), vos);
        PageInfo<DesignPlanVO> voPages = new PageInfo<>(vos);
        voPages.setTotal(datas.getTotal());
        return ReturnData.builder().data(voPages).code(SUCCESS).message("成功");
    }

    @RequiresPermissions({"client:solution:exhibition"})
    @PostMapping("/online/pushaway")
    @ApiOperation("线上上下架")
    public ReturnData onlinePushaway(@Valid @RequestBody DesignPlanPushAwayPO designPlanPushAwayPO, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        designPlanRecommendedService.onlinePushaway(designPlanPushAwayPO);
        return ReturnData.builder().code(SUCCESS);
    }

    @RequiresPermissions({"client:solution:exhibition"})
    @PostMapping("/online/pushaway/batch")
    @ApiOperation("线上批量上下架")
    public ReturnData onlineBatchPushaway(@Valid @RequestBody List<DesignPlanPushAwayPO> designPlanPushAwayPOs, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        designPlanPushAwayPOs.forEach(designPlanPushAwayPO -> {
            designPlanRecommendedService.onlinePushaway(designPlanPushAwayPO);
        });
        return ReturnData.builder().code(SUCCESS);
    }

    @RequiresPermissions({"biz:solution:exhibition"})
    @PostMapping("/channel/pushaway")
    @ApiOperation("渠道上下架")
    public ReturnData pushaway(@Valid @RequestBody DesignPlanChannelShelfPO shelfPO, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        designPlanBizService.onekeyChannelShelf(shelfPO);
        return ReturnData.builder().code(SUCCESS);
    }

    @RequiresPermissions({"biz:solution:exhibition"})
    @PostMapping("/channel/pushaway/batch")
    @ApiOperation("渠道批量上下架")
    public ReturnData pushaway(@Valid @RequestBody List<DesignPlanChannelShelfPO> shelfPOs, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        shelfPOs.forEach(shelfPO -> {
            designPlanBizService.onekeyChannelShelf(shelfPO);
        });
        return ReturnData.builder().code(SUCCESS);
    }

    @RequiresPermissions({"solution:exhibition"})
    @ApiOperation(value = "内容库方案上下架")
    @PutMapping("/library/push")
    public ReturnData putlibraryDesignPlan(@Valid @RequestBody DesignPlanUpDown update, BindingResult validResult) {
        if (validResult.hasErrors()) {
            return processValidError(validResult, ReturnData.builder());
        }
        //校验风格
//        for (Integer planId : update.getPlanIds()) {
//            DesignPlanBO result = designPlanRecommendedService.getBaseInfo(planId.longValue());
//            if (result.getDesignStyleId() == null) {
//                return ReturnData.builder().code(ERROR).message("请选择方案的风格再上架");
//            }
//        }
        try {
            for (int i = 0; i < update.getPlanIds().size(); i++) {
                Map<String, Object> resultMap = designPlanBizService.putlibraryDesignPlan(update.getPlanIds().get(i) + "", update.getPlatformIds(), update.getDesignPlanType());
                if (resultMap.containsKey("2")) {
                    return ReturnData.builder().code(ERROR).message((String) resultMap.get("2"));
                }
            }
        } catch (Exception e) {

        }
        //返回主方案和子方案
        List<Integer> planIds = designPlanRecommendedService.selectMainSubPlanList(update.getPlanIds());
        sycMessageDoSend(SyncMessage.ACTION_UPDATE, planIds);
        return ReturnData.builder().code(SUCCESS);
    }


    @PostMapping("/pic/default")
    @ApiOperation("设置默认图片")
    public ReturnData setDefaultDiagram(@Valid @RequestBody DesignPicUpdate designPicUpdate, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        designPlanBizService.setOneKeyPlanDefaultDiagram(designPicUpdate);
        sycMessageDoSend(SyncMessage.ACTION_UPDATE, Arrays.asList(designPicUpdate.getPlanId()));
        return ReturnData.builder().code(SUCCESS);
    }

    @ApiOperation(value = "获取已经交付的公司", response = ReturnData.class)
    @GetMapping("/delivery/{companyId}/{planId}/delivered")
    public ReturnData deliveredCompanyIds(@PathVariable Integer companyId, @PathVariable Integer planId) {
        List<Integer> deliveredCompanyIds = designPlanBizService.deliveredCompanys(companyId, planId);
        return ReturnData.builder().data(deliveredCompanyIds).code(SUCCESS);
    }


    @ApiOperation(value = "方案交付信息", response = DesignPlanDeliverVO.class)
    @GetMapping("/delivered/{planId}/log")
    public ReturnData deliveredLog(BaseQuery baseQuery, @PathVariable Integer planId) {

        PageInfo<DesignPlanDeliverBO> deliveredLogs = designPlanBizService.deliveredLog(planId, baseQuery);
        if (isEmpty(deliveredLogs.getList())) {
            return ReturnData.builder().list(deliveredLogs.getList()).total(deliveredLogs.getTotal()).code(NOT_CONTENT);
        }
        List<DesignPlanDeliverVO> vos = deliveredLogs.getList().stream().map(it -> new DesignPlanDeliverVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(deliveredLogs.getList(), vos);
        return ReturnData.builder().list(vos).total(deliveredLogs.getTotal()).code(SUCCESS);
    }

    @RequiresPermissions({"solution:openshare:view"})
    @GetMapping("/onekey/share/list")
    @ApiOperation(value = "分享方案列表", response = DesignPlanVO.class)
    public ReturnData shareOnekeyDesignPlanList(@Valid ShareDesignPlanQuery query, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        query.setUserId(loginUser.getId());

        PageInfo<DesignPlanBO> designPlanBOS = designPlanBizService.listOnekeyShareDesignPlan(query);
        if (isEmpty(designPlanBOS)) {
            return ReturnData.builder().list(designPlanBOS.getList()).total(designPlanBOS.getTotal()).code(NOT_CONTENT);
        }

        /*Double totalIncome = designPlanBizService.queryCompanyTotalIncome(query.getCompanyId());
        boolean b = this.validRoles(loginUser.getId());

        if (totalIncome != null) {
            totalIncome = new BigDecimal(totalIncome.toString()).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("totalIncome", totalIncome);
        map.put("flag", b);
*/
        List<DesignPlanVO> vos = designPlanBOS.getList().stream().map(it -> new DesignPlanVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(designPlanBOS.getList(), vos);
        return ReturnData.builder().list(vos).total(designPlanBOS.getTotal()).code(SUCCESS);
    }

    @RequiresPermissions({"solution:openshare:view"})
    @GetMapping("/common/share/list")
    @ApiOperation(value = "分享方案列表", response = DesignPlanVO.class)
    public ReturnData shareCommonDesignPlanList(@Valid ShareDesignPlanQuery query, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        PageInfo<DesignPlanBO> designPlanBOS = designPlanBizService.listCommonShareDesignPlan(query);
        if (isEmpty(designPlanBOS)) {
            return ReturnData.builder().list(designPlanBOS.getList()).total(designPlanBOS.getTotal()).code(NOT_CONTENT);
        }
        List<DesignPlanVO> vos = designPlanBOS.getList().stream().map(it -> new DesignPlanVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(designPlanBOS.getList(), vos);
        return ReturnData.builder().list(vos).total(designPlanBOS.getTotal()).code(SUCCESS);
    }

    @RequiresPermissions({"solution:openshare:use"})
    @PostMapping("/share/copy")
    @ApiOperation("拷贝一个分享方案")
    public ReturnData copy(@Valid @RequestBody CopyShareDesignPlanPO copyShareDesignPlanPO) {
        // designPlanBizService.copyDesignPlanToCompany(copyShareDesignPlanPO);

        // Modified by songjianming@sanduspace.cn on 2018/12/21
        // 商家后台使用共享方案提示信息优化(http://jira.3du-inc.net/browse/CMS-667)
        // 返回复制好的方案ID，让前端跳转到修改页面
        Long newPlanId = designPlanBizService.copyDesignPlanToCompany2(copyShareDesignPlanPO);

        return ReturnData.builder().code(SUCCESS).data(newPlanId);
    }

    @ApiOperation(value = "获取全部公司并放回已经交付的状态", response = CompanyWithDeliveredVO.class)
    @GetMapping("/{companyId}/{planId}/with/delivered")
    public ReturnData listCompanyWithDelivered(@PathVariable Integer companyId, @PathVariable Integer planId) {
        List<CompanyWithDeliveredBO> companyWithDeliveredBOS = designPlanBizService.listCompanyWithDelivered(companyId, planId);
        List<CompanyWithDeliveredVO> vos = companyWithDeliveredBOS.stream().map(it -> new CompanyWithDeliveredVO()).collect(Collectors.toList());
        List<Brand> allBrand = brandService.findAllBrand(0, 5000);
        ListCopyUtils.copyListProps(companyWithDeliveredBOS, vos);
        List<BrandWithDeliveredVO> brandVOs = new LinkedList<>();
        vos.forEach(company -> {
            List<BrandWithDeliveredVO> brandWithCompanyVOs = allBrand.stream()
                    .filter(brand -> company.getCompanyId().equals(brand.getCompanyId()))
                    .map(brand -> BrandWithDeliveredVO.builder()
                            .brandCompanyId(brand.getCompanyId())
                            .brandId(brand.getId().intValue())
                            .brandName(brand.getBrandName())
                            .delivered(company.getDelivered())
                            .build())
                    .collect(Collectors.toList());
            brandVOs.addAll(brandWithCompanyVOs);
        });
        brandVOs.sort(Comparator.comparing(BrandWithDeliveredVO::getDelivered).reversed());
        Map<String, List> ret = new HashMap<>();
        ret.put("companyInfo", vos);
        ret.put("brandInfo", brandVOs);
        return ReturnData.builder().data(ret).code(SUCCESS);
    }

    @ApiOperation(value = "方案富文本编辑")
    @PutMapping("/config")
    public ReturnData configDesignPlan(@RequestBody @Valid DesignPlanConfig config, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        try {
            designPlanBizService.configDesignPlan(config);
        } catch (Exception e) {
            return ReturnData.builder().success(false).code(ResponseEnum.NOT_CONTENT).message(e.getMessage());
        }
        return ReturnData.builder().success(true).code(SUCCESS);
    }

    @ApiOperation(value = "方案富文本详情获取")
    @GetMapping("/config/{id}")
    public ReturnData configDesignPlan(@PathVariable("id") Integer id) {
        String content = designPlanBizService.showDesignPlanConfig(id);
        return ReturnData.builder().data(content).success(true).code(SUCCESS);
    }


    @PostMapping("/onekey/share/editPlanPrice")
    @ApiOperation("修改方案价格")
    public Object editPlanPrice(@RequestParam("planId") Long planId, @RequestParam(value = "planPrice", required = false) Double planPrice, @RequestParam("chargeType") Integer chargeType) {

        if (planId == null) {
            return ReturnData.builder().data(0).code(PARAM_ERROR).message("planId is null");
        }

        if (chargeType == null) {
            return ReturnData.builder().data(0).code(PARAM_ERROR).message("chargeType is null");
        }

       /* LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        //检查用户是否具有方案公开管理员权限
        boolean flag = this.validRoles(loginUser.getId());

        if (!flag) {
            return ReturnData.builder().data(0).code(FORBIDDEN).message("您没有设置价格的权限,请联系管理员");
        }*/
        try {
            int row = designPlanBizService.editPlanPrice(planId, planPrice, chargeType);
            List<Integer> ids = Arrays.asList(planId.intValue());
            sycMessageDoSend(SyncMessage.ACTION_UPDATE, ids);
        } catch (Exception e) {
            log.error("系统错误", e);
            return ReturnData.builder().data(0).code(ERROR).message("系统异常");
        }
        return ReturnData.builder().data(1).code(SUCCESS).message("设置价格成功!!!");
    }


    @PostMapping("/onekey/share/editSalePrice")
    @ApiOperation("修改方案价格")
    public Object editSalePrice(@RequestParam("planId") Long planId, @RequestParam(value = "salePrice", required = false) Double salePrice, @RequestParam("salePriceChargeType") Integer salePriceChargeType) {

        if (planId == null) {
            return ReturnData.builder().data(0).code(PARAM_ERROR).message("planId is null");
        }

        if (salePriceChargeType == null) {
            return ReturnData.builder().data(0).code(PARAM_ERROR).message("chargeType is null");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        //检查用户是否具有方案公开管理员权限
        /*boolean flag = this.validRoles(loginUser.getId());

        if (!flag) {
            return ReturnData.builder().data(0).code(FORBIDDEN).message("您没有设置方案售卖价格的权限,请联系管理员");
        }*/
        try {
            int row = designPlanBizService.editSalePrice(planId, salePrice,salePriceChargeType);
            List<Integer> ids = Arrays.asList(planId.intValue());
            //sycMessageDoSend(SyncMessage.ACTION_UPDATE, ids);
        } catch (Exception e) {
            log.error("系统错误", e);
            return ReturnData.builder().data(0).code(ERROR).message("系统异常");
        }
        return ReturnData.builder().data(1).code(SUCCESS).message("设置价格成功!!!");
    }

    @PostMapping("/company/income/list")
    @ApiOperation("企业收益列表")
    public ReturnData companyDesignPlanIncomeList(@Valid CompanyIncomeQuery query, BindingResult br) {

        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        //检查用户是否具有方案公开管理员权限
        boolean b = validRoles(loginUser.getId());
        if (!b) {
            return ReturnData.builder().code(FORBIDDEN).message("您没有设置价格的权限,请联系管理员").msgId(query.getMsgId()).success(Boolean.FALSE);
        }

        try {
            PageInfo<CompanyDesignPlanIncomeBO> pageInfo = designPlanBizService.listCompanyIncome(query);

            if (isEmpty(pageInfo)) {
                return ReturnData.builder().list(pageInfo.getList()).total(pageInfo.getTotal()).code(NOT_CONTENT).success(Boolean.FALSE).message("ok");
            }

            CompanyDesignPlanIncomeAggregatedBO cdpao = designPlanBizService.getCompanyDesignPlanIncomeAggregated(query.getCompanyId());

            List<CompanyDesignPlanIncomeVO> vos = pageInfo.getList().stream().map(it -> new CompanyDesignPlanIncomeVO()).collect(Collectors.toList());
            ListCopyUtils.copyListProps(pageInfo.getList(), vos);
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("CompanyDesignPlanIncomeAggregated", cdpao);
            returnMap.put("list", vos);
            return ReturnData.builder().data(returnMap).total(pageInfo.getTotal()).code(SUCCESS).msgId(query.getMsgId()).success(Boolean.TRUE).message("ok");
        } catch (Exception e) {
            log.error("系统错误", e);
            return ReturnData.builder().code(ERROR).message("系统错误").msgId(query.getMsgId()).success(Boolean.FALSE);
        }
    }

    @GetMapping("/getRederPic")
    public Object getDesignPlanRenderPic(@RequestParam(value = "planId",required = true) Integer planId){

        if(Objects.isNull(planId)){
            return ReturnData.builder().success(false).message("planId is null");
        }
        try {
            Map<String,String> resultMap =  designPlanBizService.getRenderPicResult(planId);
            return ReturnData.builder().success(false).data(resultMap);
        } catch (Exception e) {
            log.error("系统错误",e);
            return ReturnData.builder().success(false).message("系统错误");
        }
    }


    /**
     * 检验用户权限
     *
     * @return
     */
    private boolean validRoles(Long userId) {
        SysRole sysRole = sysRoleService.getRoleByCode(PLAN_ROLE_CODE);
        Set<SysUserRole> sysUserRoles = sysRoleService.getUserRolesByUserId(userId);
        Set<Long> roleIds = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
        return roleIds.contains(sysRole.getId());
    }


    private void sycMessageDoSend(Integer messageAction, List<Integer> ids) {
        List<Map> content = ids.stream().map(item -> {
            HashMap<String, Integer> tmp = new HashMap<>(1);
            tmp.put("id", item);
            tmp.put("planTableType", SyncMessage.PLAN_TYPE_RECOMMENDED);
            return tmp;
        }).collect(Collectors.toList());
        SyncMessage message = new SyncMessage();
        message.setAction(messageAction);
        message.setMessageId("S-" + System.currentTimeMillis());
        message.setModule(SyncMessage.MODULE_SOLUTION_RECOMMEND);
        message.setPlatformType(Constants.PLATFORM_CODE_MERCHANT_MANAGE);
        message.setObject(content);
        queueService.send(message);
    }

    @GetMapping("test/trans")
    public String transResourceForPathError() {
        designPlanBizService.transResourceForPathError();

        return "done";
    }

}
