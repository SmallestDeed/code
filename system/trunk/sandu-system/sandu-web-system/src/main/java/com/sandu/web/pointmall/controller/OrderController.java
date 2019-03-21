package com.sandu.web.pointmall.controller;

import com.sandu.api.pointmall.model.*;
import com.sandu.api.pointmall.model.vo.GiftSingleVo;
import com.sandu.api.pointmall.model.vo.ImallOrderDetailVo;
import com.sandu.api.pointmall.model.vo.OrderGiftDetailVo;
import com.sandu.api.pointmall.service.GiftService;
import com.sandu.api.pointmall.service.OrderService;
import com.sandu.api.pointmall.service.UserPointService;
import com.sandu.common.LoginContext;
import com.sandu.common.util.Utils;
import com.sandu.commons.LoginUser;
import com.sandu.api.base.matadata.ResultCode;
import com.sandu.api.base.matadata.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Api(value = "礼品订单", tags = "GiftOrder")
@RestController
@RequestMapping(value = "/v1/point/order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @Autowired
    private GiftService giftService;

    @Autowired
    private UserPointService userPointService;

    /***
     * 获取我的礼品订单列表
     * @param uid 用户ID
     * @return
     */
    @RequestMapping(value = {"/myOrder"}, method = RequestMethod.GET)
    @ApiOperation(value = "获取我的礼品订单列表", response = ResultMessage.class)
    public ResultMessage myOrder(int uid)
    {
        ResultMessage message = new ResultMessage();

        List<OrderGiftDetailVo> list = orderService.getOrderGiftDetailVoList(uid);
        message.setData(list);
        message.setCode(ResultCode.Success);

        return message;
    }

    /***
     * 更新订单状态，可用于订单确认，退款等关于订单状态的操作，后期完善业务逻辑
     * @param id 注意此为订单的id，不是orderOn
     * @param status 要更新的状态值
     * @return
     */
    @RequestMapping(value = {"/updateOrderStatus"}, method = RequestMethod.GET)
    @ApiOperation(value = "更新订单状态", response = ResultMessage.class)
    public ResultMessage updateOrderStatus(int id,int status)
    {
        ResultMessage message = new ResultMessage();
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        logger.info("当前用户------LoginUser:{}",loginUser);
        int i = orderService.updateOrderStatus(id,status);
        if(i>0){
            /**修改成功，添加操作记录 add by WangHL start**/
            ImallOrderLog imallOrderLog = new ImallOrderLog();
            imallOrderLog.setCreator(loginUser.getNickName()!=null?loginUser.getNickName():"");
            imallOrderLog.setModifier(loginUser.getNickName()!=null?loginUser.getNickName():"");
            imallOrderLog.setContent("[用户修改订单状态]");
            imallOrderLog.setIsDeleted(0);
            imallOrderLog.setOrderId((long)id);
            imallOrderLog.setCreatorType(0);
            int a = orderService.inserImallOrderLogSelective(imallOrderLog);
            /**修改成功，添加操作记录 add by WangHL start**/
            message.setCode(ResultCode.Success);
        }else{
            message.setCode(ResultCode.Fail);
        }
        return message;
    }

    /***
     * 下单，传入orderVo  json对象
     * @param orderVo
     * @return
     */
    @Transactional
    @PostMapping(value="/insertOrder",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新订单状态", response = ResultMessage.class)
    public ResultMessage insertOrder(@RequestBody ImallOrderDetailVo orderVo) {
        ResultMessage message = new ResultMessage();
        if (orderVo == null) {
            message.setCode(ResultCode.Fail);
            message.setMessage("参数为空");
        }
        logger.info("下单----数据库查询礼品id：{}", orderVo.giftsId);
        logger.info("下单----前端传递 应扣减少礼品数：{}", orderVo.totalCount);
        GiftSingleVo giftSingleVo = giftService.getGiftAndImgsById(orderVo.giftsId);
        logger.info("下单----数据库查询结果 礼品：{}", giftSingleVo.toString());
        if (giftSingleVo.getInventory() > 0) {
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            logger.info("当前用户------LoginUser:{}", loginUser);

            String orderNo = getOrderNo(new Date(), 0, "yyyyMMddHHmmss");
            ImallOrder imallOrder = new ImallOrder();
            imallOrder.setBuyerId(orderVo.getUid());
            imallOrder.setOrderNo(orderNo);
            imallOrder.setTotalPoint(orderVo.totalPoint);
            imallOrder.setTotalCount(orderVo.totalCount);
            imallOrder.setBuyerNickName(orderVo.getCreator());
            imallOrder.setStatus(1);//待确认
            imallOrder.setPaymentStatus(1);//已付款
            imallOrder.setPaymentPoint(orderVo.paymentPoint);
            imallOrder.setPaymentDate(new Date());
            imallOrder.setShipmentStatus(0);//待发货
            imallOrder.setExpressFee(orderVo.expressFee);
            imallOrder.setExpressCompay(orderVo.expressCompay);
            imallOrder.setExpressNo(orderVo.expressNo);
            imallOrder.setRemark(orderVo.remark);
            imallOrder.setCreator(orderVo.getCreator());
            imallOrder.setModifier(orderVo.getCreator());
            imallOrder.setIsDeleted(0);
            /**添加系统编码 add by WangHL**/
            imallOrder.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
            int i = orderService.insertSelective(imallOrder);

            ImallOrder imallOrder1 = orderService.selectOrderId(orderNo);

            ImallOrderAddress imallOrderAddress = new ImallOrderAddress();
            imallOrderAddress.setAddress(orderVo.address);
            imallOrderAddress.setArea(orderVo.area);
            imallOrderAddress.setCity(orderVo.city);
            imallOrderAddress.setConsignee(orderVo.consignee);
            imallOrderAddress.setCreator(orderVo.getCreator());
            imallOrderAddress.setOrderId((long) imallOrder1.getId());
            imallOrderAddress.setCountry(orderVo.country);
            imallOrderAddress.setProvince(orderVo.province);
            imallOrderAddress.setZipcode(orderVo.zipcode);
            imallOrderAddress.setMobile(orderVo.mobile);
            imallOrderAddress.setModifier(orderVo.getCreator());
            imallOrderAddress.setIsDeleted(0);
            /**添加系统编码 add by WangHL**/
            imallOrderAddress.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
            int y = orderService.insertImallOrderAddressSelective(imallOrderAddress);


            // 更新礼品库存
            giftService.updateInventory(giftSingleVo.getInventory() - orderVo.totalCount, orderVo.giftsId);

            ImallOrderItem imallOrderItem = new ImallOrderItem();
            imallOrderItem.setOrderId((long) imallOrder1.getId());
            imallOrderItem.setItemId(orderVo.giftsId);
            imallOrderItem.setItemName(giftSingleVo.getName());
            imallOrderItem.setItemDescription(giftSingleVo.getExplain());
            imallOrderItem.setItemOriginalPrice(giftSingleVo.getPrice());
            imallOrderItem.setItemPoint(giftSingleVo.getPoint());
            imallOrderItem.setItemCount(orderVo.totalCount);
            imallOrderItem.setCreator(orderVo.getCreator());
            imallOrderItem.setModifier(orderVo.getCreator());
            imallOrderItem.setIsDeleted(0);

            int z = orderService.inserImallOrderItemSelective(imallOrderItem);

            ImallOrderLog imallOrderLog = new ImallOrderLog();
            imallOrderLog.setCreator(orderVo.getCreator());
            imallOrderLog.setModifier(orderVo.getCreator());
            imallOrderLog.setIsDeleted(0);
            imallOrderLog.setOrderId((long) imallOrder1.getId());
            imallOrderLog.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
            /**添加 log 表操作人员类型值 0：客户；1：客服 add by WangHL start**/
            imallOrderLog.setCreatorType(0);
            /**添加 log 表操作人员类型值 0：客户；1：客服 add by WangHL end**/
            int a = orderService.inserImallOrderLogSelective(imallOrderLog);

            UserPointInout userPointInout = new UserPointInout();
            userPointInout = userPointService.selectLastPoint(orderVo.uid);
            logger.info("下单----数据库查询结果 用户积分：{}", userPointInout.toString());
            logger.info("下单----数据库查询结果 用户积分：{}", userPointInout.getEndPoint());
            logger.info("下单----前端传递 应扣除积分：{}", orderVo.getPaymentPoint());
            UserPointInout userPointInout2 = new UserPointInout();
            userPointInout2.setBuyerId(orderVo.uid);
            userPointInout2.setInOutAmount(orderVo.paymentPoint * -1);
            userPointInout2.setEndPoint(userPointInout.getEndPoint() - orderVo.paymentPoint);
            userPointInout2.setOrderId(imallOrder1.getId());
            userPointInout2.setDescribes("兑换礼品，扣除积分");
            userPointInout2.setCreator(orderVo.getCreator());

            userPointService.insertSelective(userPointInout2);

            ImallGiftInout imallGiftInout = new ImallGiftInout();
            imallGiftInout.setCount(orderVo.totalCount);
            imallGiftInout.setGiftId(orderVo.giftsId);
            imallGiftInout.setOrderId(imallOrder1.getId());
            imallGiftInout.setOrderId(imallOrder1.getId());
            imallGiftInout.setPrice(giftSingleVo.getPrice());
            imallGiftInout.setInventory(giftSingleVo.getInventory() - orderVo.totalCount);
            imallGiftInout.setPoint(giftSingleVo.getPoint());
            imallGiftInout.setCreator(orderVo.getCreator());
            giftService.insertGiftInoutDaoSelective(imallGiftInout);

            message.setCode(ResultCode.Success);
            message.setMessage("下单成功");
            return message;
        } else {
            message.setCode(ResultCode.Fail);
            message.setMessage("下单失败,礼品数量不足");
            return message;
        }
    }



    /**
     * 获取订单号
     * @param d 制定日期
     * @param day 前后变化天数
     * @param format 日期格式
     * @return
     */
    public String getOrderNo(Date d, int day, String format) {
        String Code = null;
        String date = null;
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        Date dateTime = now.getTime();
        if(StringUtils.isNotBlank(format)){
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.format(dateTime);
        }

        String no = String.valueOf((int)((Math.random()*9+1)*1000));

        Code = date + no;
        return Code;
    }
}
