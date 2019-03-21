package com.sandu.web.grouppurchase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sandu.api.company.model.BaseCompanyMiniProgramConfig;
import com.sandu.api.company.service.BaseCompanyMiniProgramConfigService;
import com.sandu.api.grouppurchase.GroupPurchaseBizException;
import com.sandu.api.grouppurchase.GroupPurchaseConstance;
import com.sandu.api.grouppurchase.GroupPurchaseOpenEndException;
import com.sandu.api.grouppurchase.bo.GroupPurchaseOrderBO;
import com.sandu.api.grouppurchase.bo.UserPurchaseListBO;
import com.sandu.api.grouppurchase.input.*;
import com.sandu.api.grouppurchase.model.GroupPurchaseActivity;
import com.sandu.api.grouppurchase.model.GroupPurchaseOpen;
import com.sandu.api.grouppurchase.model.GroupPurchaseOpenDetail;
import com.sandu.api.grouppurchase.model.MallBaseOrder;
import com.sandu.api.grouppurchase.output.*;
import com.sandu.api.grouppurchase.service.GroupPurchaseOpenDetailService;
import com.sandu.api.grouppurchase.service.GroupPurchaseOpenService;
import com.sandu.api.grouppurchase.service.biz.GroupPurchaseActivityBizService;
import com.sandu.api.grouppurchase.service.biz.GroupPurchaseBizService;
import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.common.util.JsonObjectUtil;
import com.sandu.common.util.Utils;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.config.ScheduleConfig;
import com.sandu.constant.Punctuation;
import com.sandu.pay.order.metadata.*;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.web.BaseController;
import com.sandu.web.servicepurchase.HttpResult;
import com.sandu.web.servicepurchase.HttpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.sandu.api.grouppurchase.GroupPurchaseConstance.PAY_STATUS_FAILED;
import static com.sandu.api.grouppurchase.GroupPurchaseConstance.PAY_STATUS_SUCCESS;

/**
 * @author Sandu
 * @ClassName GroupPurchaseController
 * @date 2018/11/6
 */

@Slf4j
@RestController
@Api(tags = "groupPurchase", description = "拼团活动")
@RequestMapping("/v1/group/purchase")
public class GroupPurchaseController extends BaseController {

    private final static String PAY_METHOD_WX = "wxScanCodePay";
    private final static String PAY_METHOD_WX_MINI = "miniProPay";
    private final static String PAY_METHOD_ZF = "aliScanCodePay";


    private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    @Autowired
    private ScheduleConfig scheduleConfig;

    @Resource
    private GroupPurchaseBizService groupPurchaseBizService;

    @Resource
    private GroupPurchaseActivityBizService groupPurchaseActivityBizService;

    @Resource
    private GroupPurchaseOpenService groupPurchaseOpenService;

    @Resource
    private GroupPurchaseOpenDetailService groupPurchaseOpenDetailService;

    @Resource
    private PayOrderService payOrderService;

    private final TemplateMsgService templateMsgService;

    private final HttpService httpService;

    private final SysUserService sysUserService;

    private final BaseCompanyMiniProgramConfigService miniProgramConfigService;

    @Value("${wxpay.ip}")
    private String wxPayIp;

    @Value("${service.pay.url}")
    private String payUrl;

    @Value("${service.pay.groupPurchase.pay.notifyUrl}")
    private String notifyUrl;

    @Autowired
    public GroupPurchaseController(TemplateMsgService templateMsgService, HttpService httpService, SysUserService sysUserService, BaseCompanyMiniProgramConfigService miniProgramConfigService) {
        this.templateMsgService = templateMsgService;
        this.httpService = httpService;
        this.sysUserService = sysUserService;
        this.miniProgramConfigService = miniProgramConfigService;
    }


    @ApiOperation(value = "新建activity", response = ResponseEnvelope.class)
    @PostMapping
    public ResponseEnvelope createActivity(@Valid @RequestBody GroupPurchaseActivityAdd input, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }

        int ActivityId = groupPurchaseActivityBizService.create(input);

        if (ActivityId > 0) {
            return new ResponseEnvelope<>(true, "创建成功!", ActivityId);
        }

        return new ResponseEnvelope<>(false, "输入数据有误,创建活动失败");
    }


    @ApiOperation(value = "查询activity列表", response = GroupPurchaseActivityVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer")
    })
    @GetMapping(value = "/list")
    public ResponseEnvelope queryActivityList(@Valid GroupPurchaseActivityQuery query, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }

        final PageInfo<GroupPurchaseActivity> results = groupPurchaseActivityBizService.query(query);
        log.debug("Result: {}", results);
        if (results != null && results.getTotal() > 0) {
            final List<GroupPurchaseActivityVO> groupPurchaseActivityVOS = Lists.newArrayList();
            results.getList().stream().forEach(groupPurchaseActivity -> {
                GroupPurchaseActivityVO output = new GroupPurchaseActivityVO();
                BeanUtils.copyProperties(groupPurchaseActivity, output);
                output.setActivityId(groupPurchaseActivity.getId());
                output.setActivityStatus(groupPurchaseActivity.getActivityStatus().intValue());
                groupPurchaseActivityVOS.add(output);
            });
            return new ResponseEnvelope(true, results.getTotal(), groupPurchaseActivityVOS);
        }

        return new ResponseEnvelope<>(false, "暂无数据！");
    }

    @ApiOperation(value = "修改活动状态", response = ResponseEnvelope.class)
    @PutMapping("/changeActivityStatus")
    public ResponseEnvelope changeActivityStatus(String id, String activityStatus) {
        //修改状态
        int result = groupPurchaseActivityBizService.updateStatus(id, activityStatus);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "成功!");
        }
        return new ResponseEnvelope<>(false, "失败!");
    }

    @ApiOperation(value = "删除活动", response = ResponseEnvelope.class)
    @DeleteMapping
    public ResponseEnvelope deleteActivity(String id) {

        int result = groupPurchaseActivityBizService.deleteActivity(id);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "成功!");
        }

        return new ResponseEnvelope<>(false, "失败!");
    }

    @ApiOperation(value = "获取活动详情", response = GroupPurchaseActivityVO.class)
    @GetMapping(value = "/getActivityDetail/{id}")
    public ResponseEnvelope getByCompanyshopofflineId(@PathVariable int id) {
        if (id <= 0) {
            return new ResponseEnvelope<>(false, "输入id有误");
        }

        GroupPurchaseActivity activity = groupPurchaseActivityBizService.getByActivityId(id);
        if (activity == null) {
            return new ResponseEnvelope<>(false, "不存在该数据");
        }

        GroupPurchaseActivityVO output = new GroupPurchaseActivityVO();
        BeanUtils.copyProperties(activity, output);
        output.setActivityId(activity.getId());
        output.setActivityStatus(activity.getActivityStatus().intValue());

        return new ResponseEnvelope<>(true, output);
    }

    @ApiOperation(value = "编辑活动", response = ResponseEnvelope.class)
    @PutMapping
    public ResponseEnvelope editActivity(@Valid @RequestBody GroupPurchaseActivityUpdate update, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        // 修改数据
        int result = groupPurchaseActivityBizService.updateActivity(update);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "修改成功!");
        } else {
            return new ResponseEnvelope<>(false, "修改失败!");
        }
    }


    @ApiOperation(value = "用户参加的拼团活动", response = UserPurchaseListBO.class)
    @GetMapping("/purchase/mine/list")
    public ResponseEnvelope listUserPurchase(@Valid GroupPurchaseOpenQuery query, BindingResult validResult) {
        ResponseEnvelope<UserPurchaseListBO> result = new ResponseEnvelope<>();
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, result);
        }
        List<UserPurchaseListBO> list;
        try {
            list = groupPurchaseBizService.listPurchaseByUserId(query);
            result.setDatalist(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setSuccess(false);
        }
        result.setSuccess(true);
        return result;
    }


    @ApiOperation(value = "查看参团详情", response = UserPurchaseListBO.class)
    @GetMapping("/purchase/details/{purchaseId}")
    public ResponseEnvelope getPurchaseDetailsInfo(@PathVariable Long purchaseId) {
        ResponseEnvelope<UserPurchaseListBO> result = new ResponseEnvelope<>();
        UserPurchaseListBO bo = groupPurchaseBizService.getPurchaseDetail(purchaseId);
        result.setSuccess(true);
        result.setObj(bo);
        return result;
    }

    @ApiOperation(value = "根据SPU_ID获取商品详情", response = GroupPurchaseGoodsDetailVO.class)
    @GetMapping("/goods/detail")
    public ResponseEnvelope<GroupPurchaseGoodsDetailVO> getGoodsDetail(Long id, Long activityId, Long purchaseOpenId) {
        if (id == null || id <= 0) {
            log.error("参数错误：id => {} 不能为空！", id);
            return new ResponseEnvelope<>(false, "参数错误：id 不能为空！");
        }

        if (activityId == null || activityId <= 0) {
            log.error("参数错误：activityId => {} 不能为空！", activityId);
            return new ResponseEnvelope<>(false, "参数错误：activityId 不能为空！");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        return groupPurchaseBizService.getGoodsDetail(id, activityId, purchaseOpenId, loginUser);
    }

    @ApiOperation(value = "根据SPU_ID获取商品属性", response = GoodsSkuVO.class)
    @GetMapping("/goods/attr")
    public ResponseEnvelope<GoodsSkuVO> getGoodsAttr(Long id, Long activityId) {
        if (id == null || id <= 0) {
            log.error("参数错误：id => {} 不能为空！", id);
            return new ResponseEnvelope<>(false, "参数错误：id 不能为空！");
        }

        if (activityId == null || activityId <= 0) {
            log.error("参数错误：activityId 不能为空！");
            return new ResponseEnvelope<>(false, "参数错误：activityId 不能为空！");
        }

        return groupPurchaseBizService.getGoodsAttr(id, activityId);
    }

    @ApiOperation(value = "根据SPU_ID获取商品SKU", response = GoodsSkuVO.class)
    @GetMapping("/goods/sku")
    public ResponseEnvelope<GoodsSkuVO> getSkuInfoByAttribute(GoodsSkuQuery query) {
        if (query == null || query.getSpuId() == null || query.getSpuId() <= 0) {
            log.error("参数错误：query 不能为空！");
            return new ResponseEnvelope<>(false, "参数错误：query 不能为空！");
        }

        if (query.getActivityId() == null || query.getActivityId() <= 0) {
            log.error("参数错误：activityId 不能为空！");
            return new ResponseEnvelope<>(false, "参数错误：activityId 不能为空！");
        }

        return groupPurchaseBizService.getSkuInfoByAttrs(query);
    }

    @ApiOperation(value = "根据活动id获取凑团列表", response = GroupPurchaseGatherVO.class)
    @GetMapping("/gather/list")
    public ResponseEnvelope<GroupPurchaseGatherVO> listGather(GroupPurchaseGatherQuery query) {
        if (query == null) {
            log.error("参数错误：query 不能为空！");
            return new ResponseEnvelope<>(false, "参数错误：query 不能为空！");
        }

        if (query.getActivityId() == null || query.getActivityId() <= 0) {
            log.error("参数错误：activityId 不能为空！");
            return new ResponseEnvelope<>(false, "参数错误：query 不能为空！");
        }

        query.setLimit(query.getLimit() == null || query.getLimit() > 2 ? 2 : query.getLimit());
        query.setOffset(query.getOffset() == null ? 0 : query.getOffset());

        return groupPurchaseBizService.listGather(query);
    }

    @ApiOperation(value = "根据活动id获取开团详情", response = GroupPurchaseOpenVO.class)
    @GetMapping(value = "/getActivityOpenDetail")
    public ResponseEnvelope getOpenByActivityId(@Valid GroupPurchaseOpenQuery query, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }

        PageInfo<GroupPurchaseOpen> results = groupPurchaseOpenService.getOpenByActivityId(query);
        log.debug("Result: {}", results);
        if (results != null && results.getTotal() > 0) {
            final List<GroupPurchaseOpenVO> groupPurchaseOpenVOS = Lists.newArrayList();
            results.getList().stream().forEach(groupPurchaseOpen -> {
                GroupPurchaseOpenVO output = new GroupPurchaseOpenVO();
                BeanUtils.copyProperties(groupPurchaseOpen, output);
                output.setId(groupPurchaseOpen.getId());
                groupPurchaseOpenVOS.add(output);
            });
            return new ResponseEnvelope(true, results.getTotal(), groupPurchaseOpenVOS);
        }

        return new ResponseEnvelope<>(false, "暂无数据！");

    }

    @ApiOperation(value = "根据开团id获取开团详情信息", response = GroupPurchaseOpenDetailVO.class)
    @GetMapping(value = "/getOpenDetailByOpenId")
    public ResponseEnvelope getOpenDetailByOpenId(@Valid GroupPurchaseOpenQuery query, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }

        PageInfo<GroupPurchaseOpenDetail> results = groupPurchaseOpenDetailService.getOpenDetailByOpenId(query);
        log.debug("Result: {}", results);
        if (results != null && results.getTotal() > 0) {
            final List<GroupPurchaseOpenDetailVO> groupPurchaseOpenDetailVOS = Lists.newArrayList();
            results.getList().forEach(purchaseOpenDetail -> {
                GroupPurchaseOpenDetailVO output = new GroupPurchaseOpenDetailVO();
                BeanUtils.copyProperties(purchaseOpenDetail, output);
                output.setId(purchaseOpenDetail.getId());
                output.setTelephone(Long.valueOf(purchaseOpenDetail.getTelephone()));
                groupPurchaseOpenDetailVOS.add(output);
            });
            return new ResponseEnvelope<>(true, results.getTotal(), groupPurchaseOpenDetailVOS);
        }

        return new ResponseEnvelope<>(false, "暂无数据！");

    }

    @GetMapping("/getActivitySku")
    public ResponseEnvelope getActivitySku(GoodsSkuQuery query) {
        if (query == null) {
            log.error("参数错误：query 不能为空！");
            return new ResponseEnvelope<>(false, "参数错误：query 不能为空！");
        }
        List<GoodsSkuVO> vo = groupPurchaseBizService.getActivitySku(query);

        if (vo != null) {
            return new ResponseEnvelope(true, vo);
        }
        return new ResponseEnvelope(false, "未找到SKU信息");

    }

    @ApiOperation(value = "新建activitySku", response = ResponseEnvelope.class)
    @PostMapping("/saveActivitySku")
    public ResponseEnvelope createActivitySku(@Valid @RequestBody GroupPurchaseGoodsAdd input, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }

        int activityId = groupPurchaseBizService.createActivitySku(input);

        if (activityId > 0) {
            return new ResponseEnvelope<>(true, "创建成功!", activityId);
        }

        return new ResponseEnvelope<>(false, "输入数据有误,创建活动失败");
    }

    @ApiOperation(value = "修改activitySku", response = ResponseEnvelope.class)
    @PutMapping("/updateActivitySku")
    public ResponseEnvelope updateActivitySku(@Valid @RequestBody GroupPurchaseGoodsUpdate input, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }

        int activityId = groupPurchaseBizService.updateActivitySku(input);

        if (activityId > 0) {
            return new ResponseEnvelope<>(true, "更新成功!", activityId);
        }

        return new ResponseEnvelope<>(false, "输入数据有误,更新活动失败");
    }

    @ApiOperation(value = "获取活动sku信息", response = GroupPurchaseGoodsVO.class)
    @GetMapping(value = "/getActivitySkuDetail")
    public ResponseEnvelope getActivitySkuDetail(Integer purchaseActivityId, Integer spuId) {

        List<GroupPurchaseGoodsVO> goods = groupPurchaseBizService.getActivitySkuDetail(purchaseActivityId, spuId);
        if (goods == null) {
            return new ResponseEnvelope<>(false, "不存在该数据");
        }

        return new ResponseEnvelope<>(true, goods);
    }

    @GetMapping("pay/cancel/{orderNo}")
    public ResponseEnvelope cancelOrder(@PathVariable String orderNo) {
        ResponseEnvelope<Object> result = new ResponseEnvelope<>();
        log.info("###########取消订单 start###########");
        log.info("TraceOrderNo : {}", orderNo);
        try {
            groupPurchaseBizService.callBackSyncPurchaseData(orderNo, GroupPurchaseConstance.PAY_STATUS_CANCEL);
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setMessage("取消订单失败...");
//            return
        }
        log.info("###########取消订单 end ###########");
        result.setSuccess(true);
        return result;
    }


    @GetMapping("/checked/{purchaseOpenId}")
    public ResponseEnvelope checkPurchaseStatus(@PathVariable Long purchaseOpenId) {
        ResponseEnvelope<Object> objectResponseEnvelope = new ResponseEnvelope<>();
        boolean flag = false;
        try {
            flag = groupPurchaseBizService.checkOpenStatus(purchaseOpenId);
        } catch (GroupPurchaseBizException e) {
            objectResponseEnvelope.setMessage(e.getMessage());
        }
        objectResponseEnvelope.setObj(flag);
        objectResponseEnvelope.setSuccess(true);
        return objectResponseEnvelope;
    }

    @PostMapping("/pay")
    public ResponseEnvelope doPay(@Valid @RequestBody GroupPurchaseOrderBO bo, BindingResult result, HttpServletRequest request) {
        ResponseEnvelope response = new ResponseEnvelope();
        response.setSuccess(true);
        if (result.hasErrors()) {
            return processValidError(result, response);
        }

        HttpResult httpResult = null;
        try {
            MallBaseOrder order = groupPurchaseBizService.initGroupPurchaseOrder(bo);
            SysUser sysUser = sysUserService.get(bo.getUserId().intValue());


//            order.setOrderAmount(BigDecimal.ONE);
            order.setOrderAmount(order.getOrderAmount().multiply(new BigDecimal(100)));
            //生成流水号 traceNo
            PayOrder payOrder = this.createPayOrder(order, sysUser, 10);
            Map<String, String> requestParam = setPayParams(order, payOrder.getTradeNo());

            List<BasicHeader> basicHeaders = Arrays.asList(new BasicHeader("Platform-Code", "--mini--"),
                    new BasicHeader("Authorization", request.getHeader("Authorization")));

            httpResult = httpService.doPost(payUrl, requestParam, basicHeaders);

            String body = httpResult.getBody();

            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> resultMap = mapper.readValue(body, new TypeReference<HashMap<String, String>>() {
            });
            resultMap.put("purchaseOpenId", order.getGroupPurchaseOpenId() + "");
            resultMap.put("orderNo", order.getOrderCode() + "");
            String prepayId = "";
            if (resultMap.get("package") != null) {
                int index = resultMap.get("package").indexOf("prepay_id=");
                if (index != -1) {
                    prepayId = resultMap.get("package").substring(index + 10);
                }
            }
            //更新payTraceNo
            modifyMallOrderPayInfo(payOrder.getId(), prepayId, payOrder.getTradeNo());

            response.setObj(resultMap);
        } catch (GroupPurchaseOpenEndException e) {
            response.setSuccess(false);
            response.setMsgId("-1");
            log.error("error:{}", e);
        } catch (GroupPurchaseBizException e) {
            response.setSuccess(false);
            response.setMessage("生成订单失败, 原因: " + e.getMessage());
            log.error("error:{}", e);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("系统异常");
            log.error("error:{}", e);
        }
        log.info("rest pay result:{}", httpResult);
        return response;
    }



    @PostMapping("/refund/callBack")
    public void refundCallBack(HttpServletRequest request, HttpServletResponse response) {
        log.info("微信小程序退款回调。。。");
        try {
            Map<String, String> paramsMap = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                paramsMap.put(name, valueStr);
            }
            boolean payStatus = "SUCCESS".equals(request.getParameter("resultCode"));
            String resultMsg = paramsMap.get("resultMsg");
            String internalRefundNo = paramsMap.get("internalRefundNo");
            log.info("小程序订单支付回调参数:{}", paramsMap.toString());
            //获取退款请求流水
            PayOrder payOrder = this.getPayOrder(internalRefundNo);

            //更新退款流水状态
            this.modifyMallOrderRefundInfo(payOrder.getId(), payStatus ? PayState.REFUND_SUCCESS : PayState.REFUND_FAIL, resultMsg);

            response.getWriter().print("SUCCESS");
        } catch (Exception ex) {
            log.error("微信订单退款回调异常信息为：", ex.getMessage());
        }
    }

    @PostMapping("/callBack")
    public void callBack(HttpServletRequest request) {
        Map<String, String> requestParams = getRequestParams(request);
        boolean payStatus = "SUCCESS".equals(requestParams.get("resultCode"));
        String tradeNo = requestParams.get("intenalTradeNo");
        String payTradeNo = requestParams.get("payTradeNo");
        String resultMsg = requestParams.get("resultMsg");

        log.info("###########支付回调 start###########");
        log.info("TraceOrderNo : {}, result :{}", tradeNo, requestParams);
        requestParams.forEach((k, v) -> {
            System.out.println(String.format("%s --------------> %s", k, v));
        });

        PayOrder payOrder = new PayOrder();
        payOrder.setTradeNo(tradeNo);
        payOrder = getPayOrderByOptions(payOrder);
        if (payOrder == null) {
            throw new RuntimeException("获取流水号失败");
        }

        groupPurchaseBizService.callBackSyncPurchaseData(payOrder.getOrderNo(), payStatus ? PAY_STATUS_SUCCESS : PAY_STATUS_FAILED);

        //修改支付流水状态为支付成功
        //同步流水号

        this.modifyMallOrderPayInfo(payOrder.getId(), payStatus ? PayState.SUCCESS : PayState.PAY_ERROR, payTradeNo, resultMsg);

        log.info("###########支付回调 end ###########");

    }

    private PayOrder getPayOrderByOptions(PayOrder payOrder) {
        if (payOrder == null) {
            return null;
        }
        List<PayOrder> list = payOrderService.getList(payOrder);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;

    }

    @PostMapping("/pay/test")
    public Object testPay(@Valid @RequestBody GroupPurchaseOrderBO bo, BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, new ResponseEnvelope());
        }
        log.info("------{}------", bo);
        MallBaseOrder order = groupPurchaseBizService.initGroupPurchaseOrder(bo);
        groupPurchaseBizService.callBackSyncPurchaseData(order.getOrderCode(), PAY_STATUS_SUCCESS);

        return true;
    }


    @ApiOperation("生成小程序码")
    @GetMapping("/mini/code")
    public ResponseEnvelope initMiniCode(MiniParam param, HttpServletResponse response) throws Exception {

        String url = param.getUrl() +
                "&purchaseOpenId=" + param.getPurchaseOpenId() +
                "&groupId=" + param.getGroupId() +
                "&isAssemble=" + param.getIsAssemble();
        Integer userId = param.getUserId();

        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            throw new RuntimeException("获取用户信息失败,userId: " + userId);
        }

        String appId = sysUser.getAppId();
        BaseCompanyMiniProgramConfig config = miniProgramConfigService.getMiniProgramConfig(appId);
        if (config == null || StringUtils.isBlank(config.getAppSecret())) {
            log.error("appid错误或者服务器未配置secret:" + appId);
            throw new RuntimeException("appid错误或者服务器未配置secret");
        }
        String accessToken = templateMsgService.getAccessToken(appId, config.getAppSecret());


        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("path", url);

        String paramJson = JsonObjectUtil.bean2Json(requestParam);
        log.debug("param :{}-------{}", requestParam, paramJson);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity("https://api.weixin.qq.com/wxa/getwxacode?access_token=" + accessToken, paramJson, byte[].class);

        responseEntity.getHeaders().forEach((k, v) -> {
            response.setHeader(k, v.get(0));
        });
        String contentType = response.getHeader("content-type");
        if (contentType.endsWith("jpeg")) {
            response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + Punctuation.DOT + "jpeg");
        }
        response.getOutputStream().write(responseEntity.getBody());
        response.getOutputStream().flush();

        return null;
    }


    @GetMapping("test/refund")
    public Object testRefund() {
//        List<GroupPurchaseOpenDetail> details = groupPurchaseBizService.syncGroupPurchaseStatus();


        scheduleConfig.groupPurchaseEndSyncData();
//        log.info("退款订单Ids:{}", details.stream().map(GroupPurchaseOpenDetail::getId).toArray());
        return "success";
    }


    private PayOrder createPayOrder(MallBaseOrder order, SysUser user, Integer platformId) {
        PayOrder payOrder = new PayOrder();
        payOrder.setUserId(user.getId().intValue());
        payOrder.setProductId(null);
        payOrder.setProductName("团购商品-支付");
        payOrder.setProductType("groupPurchase");
        payOrder.setOrderNo(order.getOrderCode());
        payOrder.setTotalFee(order.getOrderAmount().intValue());
        payOrder.setPayType(PayType.WXPAY);
        payOrder.setPayState(PayState.PAYING);
        payOrder.setOpenId(user.getOpenId());
        payOrder.setOrderDate(new Date());
        payOrder.setTradeType(TradeType.MINIPAY);
        payOrder.setFinanceType(FinanceType.OUT);
        payOrder.setBizType(BizType.BUY);
        payOrder.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        payOrder.setCreator(user.getMobile());
        payOrder.setGmtCreate(new Date());
        payOrder.setGmtModified(new Date());
        payOrder.setModifier(user.getMobile());
        payOrder.setIsDeleted(0);
        payOrder.setPlatformId(platformId);
        payOrder.setTradeNo(this.getTradeNo(user.getId().intValue()));
        payOrderService.add(payOrder);
        return payOrder;
    }

    private Map<String, String> setPayParams(MallBaseOrder order, String tradeNo) {
        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("intenalTradeNo", tradeNo);
        requestParam.put("tradeDesc", "拼团购买商品");
//        log.info("支付金额:{}", order.getOrderAmount());
        requestParam.put("totalFee", order.getOrderAmount().longValue() + "");
//        requestParam.put("totalFee", 1 + "");
        requestParam.put("payMethod", PAY_METHOD_WX_MINI);
        requestParam.put("ip", wxPayIp);
        requestParam.put("notifyUrl", notifyUrl);
        requestParam.put("operator", order.getUserId().toString());
        requestParam.put("source", order.getOrderSource().toString());
        log.info("支付参数:{}", requestParam);
        return requestParam;
    }

    private PayOrder getPayOrder(String refundNo) {
        if (com.sandu.common.util.StringUtils.isBlank(refundNo)) {
            throw new RuntimeException("订单号异常...");
        }
        PayOrder payOrder = new PayOrder();
        payOrder.setIsDeleted(0);
        payOrder.setRefundNo(refundNo);
        List<PayOrder> list = payOrderService.getList(payOrder);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            throw new RuntimeException("获取流水订单失败, refundNo :" + refundNo);
        }
    }

    private void modifyMallOrderPayInfo(Integer payOrderId, String prepayId, String tradeNo) {
        if (payOrderId != null) {
            PayOrder payOrder = new PayOrder();
            payOrder.setId(payOrderId);
            payOrder.setPrepayId(prepayId);
            payOrder.setTradeNo(tradeNo);
            payOrderService.update(payOrder);
        }
    }

    private void modifyMallOrderPayInfo(Integer payOrderId, String payState, String payTradeNo, String info) {
        if (payOrderId != null) {
            PayOrder payOrder = new PayOrder();
            payOrder.setId(payOrderId);
            payOrder.setPayState(payState);
            payOrder.setPayTradeNo(payTradeNo);
            payOrder.setRemark(info);
            payOrder.setGmtModified(new Date());

            log.info("update orderPay :{}", payOrder);
            payOrderService.update(payOrder);
        }
    }

    private void modifyMallOrderRefundInfo(Integer payOrderId, String payState, String info) {
        if (payOrderId != null) {
            PayOrder payOrder = new PayOrder();
            payOrder.setId(payOrderId);
            payOrder.setPayState(payState);
            payOrder.setRemark(info);
            payOrder.setGmtModified(new Date());
            payOrderService.update(payOrder);
        }
    }

    private String getTradeNo(Integer userId) {
        String nowStr = df.format(new Date());
        return nowStr + (10000000000L - userId);
    }


    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            paramsMap.put(name, valueStr);
        }
        return paramsMap;
    }

    @Data
    public static class MiniParam {
        private Integer userId;
        private String url;
        private Integer purchaseOpenId;     
        private Integer groupId;
        private Boolean isAssemble;
    }

    public static void main(String[] args) throws IOException {
        String token = "16_R4xg_LJ_gV4I_6jCIGhweFIWeOUnwg8dxIonHjWQp3fKMNeJ4XKxyH5dyxYLZI6d4j8J0l7P_W2eqwwDB4-_RboccjXUSy9lxbU_uLuufjBNtD40I44IsT_WAib0kYoRSTVP8cgkHL--UDk0MQPbAIASWV";
        String path = "/pages/my-assemble/my-assemble";
        String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + token;

        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("path", path);
        String json = JsonObjectUtil.bean2Json(requestParam);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url, json, byte[].class);
        byte[] body = null;
        body = responseEntity.getBody();
        List<String> types = responseEntity.getHeaders().get("Content-Type");
        if (types != null) {
            String subtype = MediaType.parseMediaType(types.get(0)).getSubtype();
            if (subtype.equalsIgnoreCase("json")) {
                System.out.println(new String(body));
            } else {
                String fileName = "E:\\wxPic\\" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + ".jpeg";
                File file = new File(fileName);
                FileOutputStream os = new FileOutputStream(file);
                os.write(body);
            }

        }

    }
}
