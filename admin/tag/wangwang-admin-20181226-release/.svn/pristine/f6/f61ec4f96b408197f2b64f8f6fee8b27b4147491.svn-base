package com.sandu.web.designplan.controller;

import com.github.pagehelper.PageInfo;
import com.sandu.api.product.model.BaseProductStyle;
import com.sandu.api.product.output.BaseProductStyleVO;
import com.sandu.api.product.service.biz.ProductBizService;
import com.sandu.api.queue.SyncMessage;
import com.sandu.api.queue.service.QueueService;
import com.sandu.api.solution.input.*;
import com.sandu.api.solution.model.FullHouseDesignPlan;
import com.sandu.api.solution.model.bo.DesignPlanBO;
import com.sandu.api.solution.model.bo.FullHouseDesignPlanBO;
import com.sandu.api.solution.model.po.CopyShareDesignPlanPO;
import com.sandu.api.solution.model.po.DesignPlanDeliveryBatchPO;
import com.sandu.api.solution.model.po.DesignPlanDeliveryPO;
import com.sandu.api.solution.output.DesignPlanVO;
import com.sandu.api.solution.output.FullHouseDesignPlanVO;
import com.sandu.api.solution.service.FullHouseDesignPlanService;
import com.sandu.api.solution.service.PlanDecoratePriceService;
import com.sandu.api.solution.service.biz.DesignPlanBizService;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.SysUserRole;
import com.sandu.api.user.model.SysUserRoleGroup;
import com.sandu.api.user.service.SysRoleGroupService;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.ReturnData;
import com.sandu.commons.ListCopyUtils;
import com.sandu.constant.Constants;
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
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 17:37 2018/8/22
 */

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/v1/fullHouse")
@Api(tags = "FullHouseDesignPlan", description = "全屋方案")
public class FullHouseDesignPlanController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(FullHouseDesignPlanController.class);

    @Resource
    private FullHouseDesignPlanService fullHouseDesignPlanService;

    @Resource
    private DesignPlanBizService designPlanBizService;

    @Resource
    private PlanDecoratePriceService planDecoratePriceService;

    @Resource
    private QueueService queueService;

    @Autowired
    private SysRoleGroupService sysRoleGroupService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private ProductBizService productBizService;

    private static final String PLAN_ROLE_CODE = "Schemecharge";

    @ApiOperation(value = "全屋方案列表", response = FullHouseDesignPlanVO.class)
    @GetMapping("/list")
    public ReturnData fullHouseDesignPlan(FullHouseDesignPlanQuery query) {
        PageInfo<FullHouseDesignPlanBO> datas = fullHouseDesignPlanService.selectListSelective(query);

        if (isEmpty(datas.getList())) {
            return ReturnData.builder().code(NOT_CONTENT).message("没有数据了");
        }
        List<FullHouseDesignPlanVO> vos = datas.getList().stream().map(it -> new FullHouseDesignPlanVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(datas.getList(), vos);
        PageInfo<FullHouseDesignPlanVO> voPages = new PageInfo<>(vos);
        voPages.setTotal(datas.getTotal());
        return ReturnData.builder().data(voPages).code(SUCCESS).message("成功");
    }

    @ApiOperation(value = "全屋方案上下架")
    @PutMapping("/push")
    public ReturnData putFullHouseDesignPlan(@Valid @RequestBody DesignPlanUpDown update, BindingResult validResult) {
        if (validResult.hasErrors()) {
            return processValidError(validResult, ReturnData.builder());
        }
        //校验风格
//        for(Integer planId : update.getPlanIds()) {
//            FullHouseDesignPlanBO result = fullHouseDesignPlanService.getBaseInfo(planId,null);
//            if(result.getPlanStyleId() == null) {
//                return ReturnData.builder().code(ERROR).message("请选择方案的风格再上架");
//            }
//        }
        try {
            for (int i = 0; i < update.getPlanIds().size(); i++) {
                Map<String, Object> resultMap = fullHouseDesignPlanService.putFullHouseDesignPlan(update.getPlanIds().get(i) + "", update.getPlatformIds());
                if (resultMap.containsKey("1")) {
                    return ReturnData.builder().code(ERROR).message((String) resultMap.get("1"));
                }
            }
        } catch (Exception e) {

        }
        sycMessageDoSend(SyncMessage.ACTION_UPDATE, update.getPlanIds());
        return ReturnData.builder().code(SUCCESS).message("操作成功");
    }

    @ApiOperation(value = "根据id获取全屋方案", response = FullHouseDesignPlanVO.class)
    @GetMapping("/{planId}/{companyId}")
    public ReturnData getDesignPlan(@PathVariable Integer planId, @PathVariable Integer companyId) {
        FullHouseDesignPlanBO designPlanBO = fullHouseDesignPlanService.getBaseInfo(planId, companyId);
        if (isNotEmpty(designPlanBO)) {
            FullHouseDesignPlanVO vo = new FullHouseDesignPlanVO();
            BeanUtils.copyProperties(designPlanBO, vo);
            return ReturnData.builder().data(vo).code(SUCCESS);
        }
        return ReturnData.builder().code(NOT_CONTENT).message("没有数据了");
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
        return ReturnData.builder().code(NOT_CONTENT).message("没有数据了");
    }

    @ApiOperation(value = "编辑全屋方案", response = ReturnData.class)
    @PutMapping
    public ReturnData updateDesignPlan(@Valid @RequestBody FullHouseDesignPlanUpdate designPlanUpdate, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        int result = fullHouseDesignPlanService.updateFullHouseDetail(designPlanUpdate);
        if (result > 0) {
            //同步到es
            List<Integer> planIds = new ArrayList<>();
            planIds.add(designPlanUpdate.getId());
            sycMessageDoSend(SyncMessage.ACTION_UPDATE, planIds);
            return ReturnData.builder().code(SUCCESS).message("成功");
        }
        return ReturnData.builder().code(ERROR).message("失败");
    }

    @DeleteMapping
    @ApiOperation("删除全屋方案")
    public ReturnData deleteDesignPlan(@RequestParam String planIds) {
        Arrays.stream(planIds.split(COMMA)).forEach(id -> {
            fullHouseDesignPlanService.deletePlanById(Long.parseLong(id));
            planDecoratePriceService.deleteByFullHouseId(Integer.valueOf(id));
            designPlanBizService.removeLog(Integer.valueOf(id), 2);
        });
        if(StringUtils.isNoneBlank(planIds)) {
            List<Integer> planidList = Arrays.stream(planIds.split(COMMA)).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
            sycMessageDoSend(SyncMessage.ACTION_DELETE, planidList);
        }
        return ReturnData.builder().code(SUCCESS);
    }

    @PostMapping("/deliver/batch")
    @ApiOperation("批量交付全屋方案")
    public ReturnData delivers(@Valid @RequestBody DesignPlanDeliveryBatchPO FullHousePlanDelivery, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        //数据校验
        for (Integer planId : FullHousePlanDelivery.getDesignPlanId()) {
            FullHouseDesignPlan result = fullHouseDesignPlanService.getFullHousePlanById(planId);
            if (3 == result.getSourceType() || 4 == result.getSourceType()) {
                return ReturnData.builder().code(ERROR).message("交付方案来源只能为内部方案或装进我家");
            }
        }
        //循环批量交付的方案id进行方案交付
        for (Integer planId : FullHousePlanDelivery.getDesignPlanId()) {
            //数据转换
            DesignPlanDeliveryPO designPlanDeliveryPO = new DesignPlanDeliveryPO();
            designPlanDeliveryPO.setReceiveCompanyIds(FullHousePlanDelivery.getReceiveCompanyIds());
            designPlanDeliveryPO.setDelivererId(FullHousePlanDelivery.getDelivererId());
            designPlanDeliveryPO.setDeliveryCompanyId(FullHousePlanDelivery.getDeliveryCompanyId());
            designPlanDeliveryPO.setReceiveBrandIds(FullHousePlanDelivery.getReceiveBrandIds());
            designPlanDeliveryPO.setDesignPlanId(planId);
            fullHouseDesignPlanService.deliver(designPlanDeliveryPO, true);
        }
        return ReturnData.builder().code(SUCCESS).message("交付成功");
    }

    @PostMapping("/deliver")
    @ApiOperation("交付全屋方案")
    public ReturnData deliver(@Valid @RequestBody DesignPlanDeliveryPO designPlanDelivery, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        fullHouseDesignPlanService.deliver(designPlanDelivery, false);
        return ReturnData.builder().code(SUCCESS).message("交付成功");
    }

    @ApiOperation(value = "公开/取消公开方案")
    @PutMapping("/secrecy")
    public ReturnData changeProductSecrecy(@Valid @RequestBody DesignControlUpdate openParam,
                                           BindingResult validResult) {
        ReturnData data = builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        boolean flag = fullHouseDesignPlanService.batchChangePlanSecrecy(openParam.getPlanIds(), openParam.getSecrecyFlag());
        if (!flag) {
            return builder().code(ERROR).success(false).message("操作失败...");
        }
        return data.success(true).code(SUCCESS);
    }

    @GetMapping("/share/list")
    @ApiOperation(value = "分享方案列表", response = FullHouseDesignPlanVO.class)
    public ReturnData shareCommonDesignPlanList(@Valid FullHouseDesignPlanQuery query, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        query.setUserId(loginUser.getId());
        PageInfo<FullHouseDesignPlanBO> designPlanBOS = fullHouseDesignPlanService.shareDesignPlan(query);
        if (isEmpty(designPlanBOS)) {
            return ReturnData.builder().list(designPlanBOS.getList()).total(designPlanBOS.getTotal()).code(NOT_CONTENT);
        }
        List<FullHouseDesignPlanVO> vos = designPlanBOS.getList().stream().map(it -> new FullHouseDesignPlanVO()).collect(Collectors.toList());
        ListCopyUtils.copyListProps(designPlanBOS.getList(), vos);
        return ReturnData.builder().list(vos).total(designPlanBOS.getTotal()).code(SUCCESS);
    }

    @PostMapping("/share/copy")
    @ApiOperation("拷贝一个分享方案")
    public ReturnData copy(@Valid @RequestBody CopyShareDesignPlanPO copyShareDesignPlanPO) {
        // fullHouseDesignPlanService.copyDesignPlanToCompany(copyShareDesignPlanPO);

        // Modified by songjianming@sanduspace.cn on 2018/12/20
        // 商家后台使用共享方案提示信息优化(http://jira.3du-inc.net/browse/CMS-667)
        Integer newPlanId = fullHouseDesignPlanService.copyDesignPlanToCompany2(copyShareDesignPlanPO);

        return ReturnData.builder().code(SUCCESS).data(newPlanId);
    }

    @ApiOperation(value = "获取方案风格", response = BaseProductStyleVO.class)
    @GetMapping("styleList")
    public ReturnData initCategoryTree1() {
        ReturnData data = ReturnData.builder();
        List<BaseProductStyle> ret = fullHouseDesignPlanService.styleList();
        if (ret.isEmpty()) {
            return data.success(false).code(NOT_CONTENT).message("为查询到相关数据...");
        }
        List vos = new ArrayList();
        for (BaseProductStyle tmp : ret) {
            BaseProductStyleVO vo = new BaseProductStyleVO();
            vo.setId(tmp.getId().intValue());
            vo.setName(tmp.getName());
            vos.add(vo);
        }
        return data.success(true).code(SUCCESS).data(vos);
    }

    @ApiOperation(value = "方案富文本编辑")
    @PutMapping("/config")
    public ReturnData configDesignPlan(@RequestBody @Valid DesignPlanConfig config, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        fullHouseDesignPlanService.configDesignPlan(config);
        return ReturnData.builder().success(true).code(SUCCESS);
    }

    @ApiOperation(value = "绍福测试")
    @GetMapping("/wushaofu")
    public ReturnData wushaofu() {
        productBizService.fixedProductToSpu();;
        return null;
    }

    @ApiOperation(value = "方案富文本详情获取")
    @GetMapping("/config/{id}")
    public ReturnData configDesignPlan(@PathVariable("id") Integer id) {
        String content = fullHouseDesignPlanService.showDesignPlanConfig(id);
        return ReturnData.builder().data(content).success(true).code(SUCCESS);
    }


    @PostMapping("/editSalePrice")
    @ApiOperation("修改全屋方案售卖价格")
    public Object editSalePrice(@RequestParam("id") Long id, @RequestParam(value = "salePrice", required = false) Double salePrice, @RequestParam("salePriceChargeType") Integer salePriceChargeType) {

        if (id == null) {
            return ReturnData.builder().data(0).code(PARAM_ERROR).message("id is null");
        }

        if (salePriceChargeType == null) {
            return ReturnData.builder().data(0).code(PARAM_ERROR).message("salePriceChargeType is null");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
       /* //检查用户是否具有方案公开管理员权限
        boolean flag = this.validRole(loginUser.getId(),PLAN_ROLE_CODE);

        if (!flag) {
            return ReturnData.builder().data(0).code(FORBIDDEN).message("您没有设置方案售卖价格的权限,请联系管理员");
        }*/
        try {
            int row = fullHouseDesignPlanService.editSalePrice(id, salePrice, salePriceChargeType);
            List<Integer> ids = Arrays.asList(id.intValue());
            //sycMessageDoSend(SyncMessage.ACTION_UPDATE, ids);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return ReturnData.builder().data(0).code(ERROR).message("系统异常");
        }
        return ReturnData.builder().data(1).code(SUCCESS).message("设置方案售卖价格成功!!!");
    }

    @PostMapping("/editPlanPrice")
    @ApiOperation("修改全屋方案版权费")
    public Object editPlanPrice(@RequestParam("id") Long id, @RequestParam(value = "planPrice", required = false) Double planPrice, @RequestParam("chargeType") Integer chargeType) {

        if (id == null) {
            return ReturnData.builder().data(0).code(PARAM_ERROR).message("id is null");
        }

        if (chargeType == null) {
            return ReturnData.builder().data(0).code(PARAM_ERROR).message("salePriceChargeType is null");
        }

       /* LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        //检查用户是否具有方案公开管理员权限
        boolean flag = this.validRole(loginUser.getId(),PLAN_ROLE_CODE);

        if (!flag) {
            return ReturnData.builder().data(0).code(FORBIDDEN).message("您没有设置方案版权费用的权限,请联系管理员");
        }*/
        try {
            int row = fullHouseDesignPlanService.editPlanPrice(id, planPrice, chargeType);
            List<Integer> ids = Arrays.asList(id.intValue());
            sycMessageDoSend(SyncMessage.ACTION_UPDATE, ids);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return ReturnData.builder().data(0).code(ERROR).message("系统异常");
        }
        return ReturnData.builder().data(1).code(SUCCESS).message("设置方案版权价格成功!!!");
    }


    private void sycMessageDoSend(Integer messageAction, List<Integer> ids) {
        List<Map> content = ids.stream().map(item -> {
            HashMap<String, Integer> tmp = new HashMap<>(1);
            tmp.put("id", item);
            tmp.put("planTableType",SyncMessage.PLAN_TYPE_FULLHOUSE);
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

    private Boolean validRole(Long userId, String value) {
        //获取用户的角色组
        Set<SysUserRoleGroup> userRoleGroup = sysRoleGroupService.getUserRoleGroupByUserId(userId);
        if (Objects.nonNull(userRoleGroup) && !userRoleGroup.isEmpty()) {
            //获取用户的角色组
            Set<Long> roleGroupIds = userRoleGroup.stream().map(SysUserRoleGroup::getRoleGroupId).collect(Collectors.toSet());
            Set<Long> roleIds = sysRoleService.batchRoleByRoleRroupIds(roleGroupIds);
            boolean roleVaild = checkUserRole(roleIds, value);
            //false时先不返回
            if (roleVaild) {
                return roleVaild;
            }
        }
        //用户角色关联校验
        Set<SysUserRole> userRoles = sysRoleService.getUserRolesByUserId(userId);
        Set<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
        return checkUserRole(roleIds, value);
    }

    public boolean checkUserRole(Set<Long> roleIds, String roleCode) {
        if (Objects.nonNull(roleIds) && !roleIds.isEmpty()) {
            //获取角色组下的角色
            List<SysRole> sysRoles = sysRoleService.getRolesByRoleIds(roleIds);
            Set<String> collect = sysRoles.stream().map(SysRole::getCode).collect(Collectors.toSet());
            if (collect.contains(roleCode)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}
