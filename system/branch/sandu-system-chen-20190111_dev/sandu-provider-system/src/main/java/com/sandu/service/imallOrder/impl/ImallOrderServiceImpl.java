package com.sandu.service.imallOrder.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.area.output.BaseAreaListVO;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.gift.model.Gift;
import com.sandu.api.gift.model.GiftInout;
import com.sandu.api.gift.model.GiftInoutStatus;
import com.sandu.api.imallOrder.model.*;
import com.sandu.api.imallOrder.output.ImallOrderVO;
import com.sandu.api.imallOrder.service.ImallOrderService;
import com.sandu.api.imallOrderShipment.model.ImallOrderShipment;
import com.sandu.api.imallOrderShipment.model.ImallOrderShipmentAddress;
import com.sandu.api.imallOrderShipment.model.ImallOrderShipmentItem;
import com.sandu.api.imallOrderShipment.model.ImallOrderShipmentLog;
import com.sandu.api.pointmall.service.UserPointService;
import com.sandu.common.util.Utils;
import com.sandu.service.gift.dao.GiftDao;
import com.sandu.service.imallOrder.dao.ImallOrderDao;
import com.sandu.service.imallOrderShipment.dao.ImallOrderShipmentAddressDao;
import com.sandu.service.imallOrderShipment.dao.ImallOrderShipmentDao;
import com.sandu.service.imallOrderShipment.dao.ImallOrderShipmentItemDao;
import com.sandu.service.imallOrderShipment.dao.ImallOrderShipmentLogDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("imallOrderService")
public class ImallOrderServiceImpl implements ImallOrderService {

    @Autowired
    private ImallOrderDao imallOrderDao;

    @Autowired
    private GiftDao giftDao;

    /**
     * 发货单
     */
    @Autowired
    private ImallOrderShipmentDao shipmentDao;

    /**
     * 发货单明细
     */
    @Autowired
    private ImallOrderShipmentItemDao shipmentItemDao;

    /**
     * 发货单地址
     */
    @Autowired
    private ImallOrderShipmentAddressDao shipmentAddressDao;

    /**
     * 发货单日志
     */
    @Autowired
    private ImallOrderShipmentLogDao shipmentLogDao;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private UserPointService userPointService;

    /**
     * 查询订单分页
     * @param query
     * @return
     */
    @Override
    public PageInfo<ImallOrderVO> findAll(ImallOrderPO query) {
        PageHelper.startPage(query.getPage(), query.getLimit(),query.getOrderBy());
        List<ImallOrderVO> results = imallOrderDao.findAll(query);
        return new PageInfo<ImallOrderVO>(results);
    }

    /**
     * 订单明细
     * @param id
     * @return
     */
    @Override
    public ImallOrder getImallOrderById(int id) {
        //查询订单信息
        ImallOrder order=imallOrderDao.getImallOrderById(id);
        //查询订单地址信息
        List<ImallOrderAddress> Address = imallOrderDao.getImallOrderAddress(id);
        if (Address.size()>0){
            ImallOrderAddress imallOrderAddress =Address.get(0);
            //将地区编码转换成具体的省市区
            if (null!=imallOrderAddress){
                BaseAreaListVO province = baseAreaService.queryAreaByCode(imallOrderAddress.getProvince() + "");
                if (null!=province){
                    imallOrderAddress.setProvinceName(province.getAreaName());
                }
                BaseAreaListVO city = baseAreaService.queryAreaByCode(imallOrderAddress.getCity() + "");
                if (null!=city){
                    imallOrderAddress.setCityName(city.getAreaName());
                }
                BaseAreaListVO area = baseAreaService.queryAreaByCode( imallOrderAddress.getArea() + "");
                if (null!=area){
                    imallOrderAddress.setAreaName(area.getAreaName());
                }
         }
        }
        order.setAddressList(Address);
        //查询订单明细信息
        order.setItemList(imallOrderDao.getImallOrderItem(id));

        //查询订单操作日志信息
        order.setLogList(imallOrderDao.getImallOrderLog(id));
        return order;
    }

    /**
     * 确认
     * @param orderId
     * @param modifier
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public String affirm(int orderId, String modifier) {
        ImallOrder order=imallOrderDao.getImallOrderById(orderId);
        //状态为待确认
        if (order.getStatus()==ImallOrderStatus.ORDER_WAITAFFIRM){
            //修改状态为确认
            imallOrderDao.updateImallOrderStatus(orderId,ImallOrderStatus.ORDER_WAITSEND,modifier);
            //减库存
            /*List<ImallOrderItem> itemList=imallOrderDao.getImallOrderItem(orderId);
            for (int i=0;i<itemList.size();i++){
                imallOrderDao.updateGiftInventoryPlus(itemList.get(i).getItemId(),-itemList.get(i).getItemCount());
            }*/
            //记录操作日志
            ImallOrderLog log=new ImallOrderLog();
            log.setOrderId(orderId);
            log.setContent("【商家确认】 "+modifier+"审核订单，订单无问题");
            log.setCreator(modifier);
            log.setCreatorType(ImallOrderLogStatus.CREATORTYPE_SERVICE);
            log.setModifier(modifier);
            /**添加 log 表操作人员类型值 0：客户；1：客服 add by WangHL end**/
            log.setCreatorType(1);
            log.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
            /**添加 log 表操作人员类型值 0：客户；1：客服 add by WangHL start**/
            imallOrderDao.insertImallOrderLog(log);
            return "succ";
        }else{
            return "订单已确认，请选择其他操作";
        }

    }

    @Override
    public String complete(int orderId, String modifier) {
        ImallOrder order=imallOrderDao.getImallOrderById(orderId);
        if (order.getStatus()==ImallOrderStatus.ORDER_SEND){
            //修改状态为确认
            imallOrderDao.updateImallOrderStatus(orderId,ImallOrderStatus.ORDER_COMPLETE,modifier);
            //记录操作日志
            ImallOrderLog log=new ImallOrderLog();
            log.setOrderId(orderId);
            log.setContent("【商家】 "+modifier+"强制完成订单");
            log.setCreator(modifier);
            log.setCreatorType(ImallOrderLogStatus.CREATORTYPE_SERVICE);
            log.setModifier(modifier);
            /**添加 log 表操作人员类型值 0：客户；1：客服 add by WangHL end**/
            log.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
            /**添加 log 表操作人员类型值 0：客户；1：客服 add by WangHL start**/
            imallOrderDao.insertImallOrderLog(log);
            return "succ";
        }else{
            return "订单已完成，请选择其他订单操作";
        }
    }

    /**
     * 发货
     * @param orderId
     * @param expressCompay
     * @param expressNo
     * @param modifier
     * @return
     */
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public String send(int orderId, String expressCompay, String expressNo, String modifier) {
        try {
            ImallOrder order=imallOrderDao.getImallOrderById(orderId);
            //状态
            if (order.getStatus()==ImallOrderStatus.ORDER_WAITSEND) {
                //发货
                imallOrderDao.updateImallOrderExpress(orderId, ImallOrderStatus.ORDER_SEND, expressCompay, expressNo, modifier);

                //记录操作日志
                ImallOrderLog log = new ImallOrderLog();
                log.setOrderId(orderId);
                log.setContent("【商家发货】 " + modifier + "填写物流信息，物流公司：" + expressCompay + " 运单号：" + expressNo);
                log.setCreator(modifier);
                log.setCreatorType(ImallOrderLogStatus.CREATORTYPE_SERVICE);
                log.setModifier(modifier);
                /**添加 log 表操作人员类型值 0：客户；1：客服 add by WangHL end**/
                log.setCreatorType(1);
                log.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                /**添加 log 表操作人员类型值 0：客户；1：客服 add by WangHL start**/
                imallOrderDao.insertImallOrderLog(log);


                //发货表
                ImallOrderShipment shipment=new ImallOrderShipment();
                shipment.setShipmentNo("");
                shipment.setPlatformType(0);
                shipment.setOrderId(order.getId());
                shipment.setOrderNo(order.getOrderNo());
                shipment.setBuyerId(order.getBuyerId());
                shipment.setBuyerNickName(order.getBuyerNickName());
                shipment.setSellerId(0);
                shipment.setSellerNickName("");
                shipment.setTotalWeight(0);
                shipment.setTotalQty(order.getTotalCount());
                shipment.setShippingAmount(order.getExpressFee());
                shipment.setStatus(0);
                shipment.setRemark("");
                shipment.setCarrierCode(expressNo);
                shipment.setCreator(modifier);
                shipment.setModifier(modifier);
                shipment.setIsDeleted(0);
                shipment.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                shipmentDao.insertOrderShipment(shipment);

                //发货明细
                List<ImallOrderItem> itemList=imallOrderDao.getImallOrderItem(orderId);
                for (int i=0;i<itemList.size();i++){
                    ImallOrderItem item=itemList.get(i);
                    ImallOrderShipmentItem shipmentItem=new ImallOrderShipmentItem();
                    shipmentItem.setShipmentId(shipment.getId());
                    shipmentItem.setItemId(String.valueOf(item.getItemId()));
                    shipmentItem.setItemSku(item.getItemSku());
                    shipmentItem.setItemName(item.getItemName());
                    shipmentItem.setItemDescription(item.getItemDescription());
                    shipmentItem.setItemOriginalPrice(item.getItemOriginalPrice());
                    shipmentItem.setItemOriginalPoint(item.getItemPoint());
                    shipmentItem.setItemPoint(item.getItemPoint());
                    shipmentItem.setCreator(modifier);
                    shipmentItem.setModifier(modifier);
                    shipmentItem.setIsDeleted(0);
                    shipmentItem.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                    shipmentItemDao.insertImallOrderShipmentItem(shipmentItem);
                }
                List<ImallOrderAddress> list = imallOrderDao.getImallOrderAddress(order.getId());

                ImallOrderAddress address=list.get(0);
                //发货单地址
                ImallOrderShipmentAddress shipmentAddress=new ImallOrderShipmentAddress();
                shipmentAddress.setShipmentId(shipment.getId());
                shipmentAddress.setCountry(address.getCountry());
                shipmentAddress.setProvince(address.getProvince());
                shipmentAddress.setCity(address.getCity());
                shipmentAddress.setArea(address.getArea());
                shipmentAddress.setAddress(address.getAddress());
                shipmentAddress.setZipcode(address.getZipcode());
                shipmentAddress.setRecvName(address.getConsignee());
                shipmentAddress.setMobile(address.getMobile());
                shipmentAddress.setCreator(modifier);
                shipmentAddress.setModifier(modifier);
                shipmentAddress.setIsDeleted(0);
                shipmentAddress.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                shipmentAddressDao.insertImallOrderShipmentAddress(shipmentAddress);

                //发货单日志
                ImallOrderShipmentLog shipmentLog=new ImallOrderShipmentLog();
                shipmentLog.setShipmentId(shipment.getId());
                shipmentLog.setContent("【发货】"+modifier);
                shipmentLog.setCreator(modifier);
                shipmentLog.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                shipmentLog.setModifier(modifier);
                shipmentLogDao.insertOrderShipmentLog(shipmentLog);
                return "succ";
            }else{
                return "statusChange";
            }
        }catch (Exception e){
            return "fail:"+e.getMessage();
        }
    }

    /**
     * 修改收货地址
     * @param model
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public String updateAddress(ImallOrderAddress model) {
        imallOrderDao.deleteImallOrderAddress(model.getOrderId(),model.getCreator());//删除作废地址
        model.setGmtCreate(new Date());
        model.setGmtModified(new Date());
        imallOrderDao.insertImallOrderAddress(model);//添加新地址
        //记录操作日志
        ImallOrderLog log=new ImallOrderLog();
        log.setOrderId(model.getOrderId());
        log.setContent("【修改地址】 "+model.getCreator()+"修改收货地址");
        log.setCreator(model.getCreator());
        log.setCreatorType(ImallOrderLogStatus.CREATORTYPE_SERVICE);
        log.setModifier(model.getCreator());
        /**添加 log 表操作人员类型值 0：客户；1：客服 add by WangHL end**/
        log.setCreatorType(1);
        log.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
        /**添加 log 表操作人员类型值 0：客户；1：客服 add by WangHL start**/
        imallOrderDao.insertImallOrderLog(log);
        return "succ";
    }

    /**
     * 退积分
     * @param orderId
     * @param modifier
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public String refundment(int orderId, String modifier) {
        ImallOrder orderData=imallOrderDao.getImallOrderById(orderId);
        //状态为待确认
        if (orderData.getStatus()==ImallOrderStatus.ORDER_WAITAFFIRM) {
            //修改退积分状态
            imallOrderDao.updateRefundment(orderId, modifier);
            //修改订单状态为取消
            imallOrderDao.updateImallOrderStatus(orderId, ImallOrderStatus.ORDER_CLOSE, modifier);

            //退库存
            List<ImallOrderItem> itemList = imallOrderDao.getImallOrderItem(orderId);
            for (int i = 0; i < itemList.size(); i++) {
                ImallOrderItem item=itemList.get(i);
                Gift giftData=giftDao.getById(item.getItemId());
                GiftInout inout=new GiftInout();
                inout.setGiftId(item.getItemId());
                inout.setOperType(GiftInoutStatus.OPER_ORDER);
                inout.setOrderId(orderId);
                inout.setPrice(item.getItemOriginalPrice());
                inout.setPoint(item.getItemPoint());
                inout.setCount(item.getItemCount());
                inout.setInventory(giftData.getInventory()+item.getItemCount());
                inout.setCreator(modifier);
                inout.setRemark("【积分订单退货】增加库存"+item.getItemCount());
                giftDao.addImallGiftInout(inout);//添加记录
                giftDao.addGiftInventory(item.getItemId(),item.getItemCount());//修改库存
            }

            //退积分操作【待完善更新会员剩余积分和结存积分】
            UserPointInout pointInout=new UserPointInout();
            int buyerId = orderData.getBuyerId();
            com.sandu.api.pointmall.model.UserPointInout userPointInout = userPointService.selectLastPoint(buyerId);
            pointInout.setBuyerId(orderData.getBuyerId());
            pointInout.setInOutAmount(orderData.getPaymentPoint());

            pointInout.setEndPoint(userPointInout.getEndPoint()+orderData.getPaymentPoint());//无法获取剩余积分
            pointInout.setOrderId(orderData.getId());
            pointInout.setDescribes("【积分订单退积分】增加积分"+orderData.getPaymentPoint());
            pointInout.setCreator(modifier);
            imallOrderDao.addPointInout(pointInout);

            //记录操作日志
            ImallOrderLog log = new ImallOrderLog();
            log.setOrderId(orderId);
            log.setContent("【积分订单退积分】 " + modifier + "退积分");
            log.setCreator(modifier);
            log.setCreatorType(ImallOrderLogStatus.CREATORTYPE_SERVICE);
            log.setModifier(modifier);
            /**添加 log 表操作人员类型值 0：客户；1：客服 add by WangHL end**/
            log.setCreatorType(1);
            log.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
            /**添加 log 表操作人员类型值 0：客户；1：客服 add by WangHL start**/
            imallOrderDao.insertImallOrderLog(log);
            return "succ";
        }else{

            return "statusChange";
        }
    }

    @Override
    public List<Map<String,Integer>> collectStatusCount(){
        return imallOrderDao.collectStatusCount();
    }
}
