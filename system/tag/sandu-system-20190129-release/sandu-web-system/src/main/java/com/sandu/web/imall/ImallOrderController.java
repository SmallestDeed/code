package com.sandu.web.imall;

import com.github.pagehelper.PageInfo;
import com.sandu.api.imallOrder.model.ImallOrder;
import com.sandu.api.imallOrder.model.ImallOrderAddress;
import com.sandu.api.imallOrder.model.ImallOrderPO;
import com.sandu.api.imallOrder.output.ImallOrderVO;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.ReturnData;
import com.sandu.api.imallOrder.service.ImallOrderBizService;
import com.sandu.commons.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.scene.input.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.sandu.constant.ResponseEnum.*;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/26 15:35
 */
@Api(value = "积分订单", tags = "积分订单", description = "积分订单操作")
@RestController
@RequestMapping(value = "/v1/imallOrder")
public class ImallOrderController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ImallOrderController.class);
    @Autowired
    private ImallOrderBizService imallOrderBizService;

    @ApiOperation(value = "查询列表", response = ImallOrderVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "startDate", value = "创建时间", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "创建时间", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "buyerNickName", value = "用户名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "consignee", value = "收件人", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "收件人电话", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "imallOrder", value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序方式", paramType = "query", dataType = "String")
    })
    @GetMapping(value = "/list")
    @RequiresPermissions({"points:mall:order:view"})
    public ReturnData queryList(ImallOrderPO query, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        /**去掉字符串前后空格 add by WangHL start**/
        if (null!=query.getOrderNo()){
            query.setOrderNo(query.getOrderNo().trim());
        }
        if (null!=query.getBuyerNickName()){
            query.setBuyerNickName(query.getBuyerNickName().trim());
        }
        if (null!=query.getConsignee()){
            query.setConsignee(query.getConsignee().trim());
        }
        if (null!=query.getMobile()){
            query.setMobile(query.getMobile().trim());
        }
        /**去掉字符串前后空格 add by WangHL  end**/
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        if (null!=query.getStartDate()){
            String startDate = query.getStartDate();
            startDate = startDate.replace("Z", " UTC");
            Date dateStart = null;
            try {
                dateStart=format1.parse(startDate);
                String format = format2.format(dateStart);
                query.setStartDate(format);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (null!=query.getEndDate()){
            String endDate = query.getEndDate();
            endDate = endDate.replace("Z", " UTC");
            Date dateEnd = null;
            try {
                dateEnd=format1.parse(endDate);
                String format = format2.format(dateEnd);
                query.setEndDate(format);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        PageInfo<ImallOrderVO> results = imallOrderBizService.queryList(query);

        if (results.getTotal() > 0) {
            /*final List<GiftVO> gifts = Lists.newArrayList();
            results.getList().stream().forEach(gift -> {
                GiftVO out = new GiftVO();
                out.setId(gift.getId());
                out.setCode(gift.getCode());
                out.setExplain(gift.getExplain());
                out.setIntroduce(gift.getIntroduce());
                out.setIsPutaway(gift.getIsPutaway());
                out.setAmount(gift.getAmount());
                out.setPrice(gift.getPrice());
                out.setIntegral(gift.getIntegral());
                out.setExpressFee(gift.getExpressFee());
                out.setCreated(gift.getCreated());
                gifts.add(out);
            });*/

            return data.code(SUCCESS).list(results.getList()).total(results.getTotal());
        }

        return data.code(NOT_CONTENT).message("暂无数据");
    }

    @ApiOperation(value = "获取订单信息", response = ReturnData.class)
    @RequestMapping(value = {"/getImallOrder"},method=RequestMethod.GET)
    @RequiresPermissions({"points:mall:order:view"})
    public ReturnData getImallOrder(int id) {
        ReturnData data = ReturnData.builder();
        try {
            ImallOrder order = imallOrderBizService.getImallOrderById(id);
            return data.code(SUCCESS).data(order);
        }catch (Exception e) {
            return data.code(ERROR).message("异常");
        }
    }

    @ApiOperation(value = "订单确认", response = ReturnData.class)
    @RequestMapping(value = {"/affirm"},method=RequestMethod.GET)
    @RequiresPermissions({"points:mall:order:shipments"})
    public ReturnData affirm(int orderId) {
        ReturnData data = ReturnData.builder();
        LoginUser loginUser= LoginContext.getLoginUser(LoginUser.class);
        String modifier=loginUser.getNickName();
        String result=imallOrderBizService.affirm(orderId,modifier);
        if ("succ".equals(result)){
            return data.code(SUCCESS);
        }else{
            return data.code(ERROR).message(result);
        }
    }

    @ApiOperation(value = "订单完成", response = ReturnData.class)
    @RequestMapping(value = {"/complete"},method=RequestMethod.GET)
    @RequiresPermissions({"points:mall:order:shipments"})
    public ReturnData complete(int orderId) {
        ReturnData data = ReturnData.builder();
        LoginUser loginUser= LoginContext.getLoginUser(LoginUser.class);
        String modifier=loginUser.getNickName();
        String result=imallOrderBizService.complete(orderId,modifier);
        if ("succ".equals(result)){
            return data.code(SUCCESS);
        }else{
            return data.code(ERROR).message(result);
        }
    }



    @ApiOperation(value = "订单发货", response = ReturnData.class)
    @RequestMapping(value = {"/send"},method=RequestMethod.GET)
    @RequiresPermissions({"points:mall:order:shipments"})
    public ReturnData send(int orderId,String expressCompay,String expressNo) {
        ReturnData data = ReturnData.builder();
        if (null!=expressCompay&&expressCompay.length()>30){
            return data.code(ERROR).message("物流公司长度在30字符以内");
        }
        if(null!=expressNo&&expressNo.length()>30){
            return data.code(ERROR).message("物流单号长度在30字符以内");
        }
        LoginUser loginUser= LoginContext.getLoginUser(LoginUser.class);
        String modifier=loginUser.getNickName();
        String result=imallOrderBizService.send(orderId,expressCompay,expressNo,modifier);
        if ("succ".equals(result)){
            return data.code(SUCCESS);
        }else{
            return data.code(ERROR).message(result);
        }
    }

    @ApiOperation(value = "修改配送信息", response = ReturnData.class)
    @RequestMapping(value = {"/updateAddress"},method=RequestMethod.POST)
    public ReturnData updateAddress(@RequestBody ImallOrderAddress address) {
        ReturnData data = ReturnData.builder();
        LoginUser loginUser= LoginContext.getLoginUser(LoginUser.class);
        String modifier=loginUser.getNickName();
        logger.info("修改配送信息---接收参数:{}",address);
        address.setCreator(modifier);
        logger.info("修改配送信息---接收参数:{}",address);
        String result=imallOrderBizService.updateAddress(address);
        if ("succ".equals(result)){
            return data.code(SUCCESS);
        }else{
            return data.code(ERROR).message(result);
        }
    }

    @ApiOperation(value = "退款", response = ReturnData.class)
    @RequestMapping(value = {"/refundment"},method=RequestMethod.GET)
    public ReturnData refundment(int orderId) {
        ReturnData data = ReturnData.builder();
        LoginUser loginUser= LoginContext.getLoginUser(LoginUser.class);
        String modifier=loginUser.getNickName();
        String result=imallOrderBizService.refundment(orderId,modifier);
        if ("succ".equals(result)){
            return data.code(SUCCESS);
        }else{
            return data.code(ERROR).message(result);
        }
    }

    @ApiOperation(value = "订单汇总状态数量", response = ReturnData.class)
    @RequestMapping(value = {"/collectStatusCount"},method=RequestMethod.GET)
    @RequiresPermissions({"points:mall:order:view"})
    public ReturnData collectStatusCount() {
        ReturnData data = ReturnData.builder();

        List<Map<String,Integer>> result=imallOrderBizService.collectStatusCount();
        if (result!=null&&result.size()>0){
            return data.code(SUCCESS).list(result);
        }else{
            return data.code(NOT_CONTENT).message("无数据");
        }
    }

}
