package com.nork.decorateOrder.controller;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.decorateOrder.common.PageHelper;
import com.nork.decorateOrder.constant.DecorateOrderConstants;
import com.nork.decorateOrder.dao.DecorateOrderMapper;
import com.nork.decorateOrder.model.DecorateOrder;
import com.nork.decorateOrder.model.input.DecorateCustomerListQuery;
import com.nork.decorateOrder.model.input.DecorateOrderUpdate;
import com.nork.decorateOrder.model.input.RefundApplication;
import com.nork.decorateOrder.model.output.DecorateCustomerDetail;
import com.nork.decorateOrder.model.output.DecorateCustomerVO;
import com.nork.decorateOrder.service.DecorateOrderService;
import com.nork.system.config.SystemConfig;
import com.nork.system.model.SysUserMessage;
import com.nork.system.service.SysUserMessageService;
import com.sandu.common.LoginContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
* @description： 客户订单信息接口
* @author : WangHaiLin
* @date : 2018/10/19 17:56
*
*/
@Controller
@RequestMapping("/{style}/mobile/decorateOrder")
public class DecorateOrderController {

    private static Logger logger = Logger.getLogger(DecorateOrderController.class);

    private final static String LOGPREFIX = "[客户订单模块]:";

    @Autowired
    private DecorateOrderService decorateOrderService;

    @Autowired
    private DecorateOrderMapper decorateOrderMapper;

    @Autowired
    private SysUserMessageService sysUserMessageService;

    /**
     * 客户订单列表查询接口：
     * 订单类型：0-客户店铺预约;1-抢单;2-平台自动派单;3-内部推荐，不传的时候查全部
     * @param query
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope<DecorateCustomerVO> getCustomListInfo( DecorateCustomerListQuery query) {
        //校验参数
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		
        if(loginUser == null || loginUser.getId() == null) {
            logger.error(LOGPREFIX + "(loginUser == null || loginUser.getId() == null) = true");
            return new ResponseEnvelope<>(false, "请重新登录");
        }
        //获取当前登录用户Id
        query.setUserId(loginUser.getId());
        try{
            //查询客户订单数量
            int count = decorateOrderService.getCount(query);

            //分页处理
            PageHelper helper = PageHelper.getPage(count, query.getLimit(), query.getStart());
            query.setStart(helper.getStart());
            query.setLimit(helper.getPageSize());

            List<DecorateCustomerVO> list=null;
            //客户订单数量大于零时进行列表查询
            if (count>0){
                 list= decorateOrderService.getList(query);
            }
            return new ResponseEnvelope<>(true,"none" ,count,list);

        }catch (Exception e){
            logger.error(LOGPREFIX + "(客户订单列表查询--getCustomerList方法异常 ：)"+e);
            return new ResponseEnvelope<>(false,"系统异常");
        }
    }

    /**
     * 通过订单Id查询客户订单详细信息
     * @param orderId 订单Id
     * @return
     */
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    @ResponseBody
    public Object getCustomListInfo(Integer orderId) {
        //校验参数
        if( null==orderId) {
            logger.error(LOGPREFIX + "(orderId == null ) = true");
            return new ResponseEnvelope<DecorateCustomerDetail>(false, "请传递orderId");
        }
        try{
            //查询客户订单详情
            DecorateCustomerDetail customerDetail = decorateOrderService.getCustomerDetail(orderId);
            if (null==customerDetail){
                return new ResponseEnvelope<DecorateCustomerDetail>("查无此信息");
            }
            return new ResponseEnvelope<DecorateCustomerDetail>(true ,customerDetail);

        }catch (Exception e){
            logger.error(LOGPREFIX + "(客户订单详情查--getDetail方法询异常 ：)"+e);
            return new ResponseEnvelope<DecorateCustomerDetail>(false,"系统异常");
        }
    }


    /**
     * 修改订单信息
     * @param update 入参
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope updateInfo(@RequestBody DecorateOrderUpdate update) {
        //校验参数
        if(null==update.getOrderId()) {
            logger.error(LOGPREFIX + "(orderId == null ) = true");
            return new ResponseEnvelope<>(false, "订单Id为空");
        }
        //获取当前登录用户信息40610
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null || loginUser.getId() == null) {
            logger.error(LOGPREFIX + "(loginUser == null || loginUser.getId() == null) = true");
            return new ResponseEnvelope<>(false, "请重新登录");
        }
        //构造修改入参
        DecorateOrder order = this.paramTransform(update, loginUser);
        logger.error(LOGPREFIX + "(修改客户订单状态---入参--)"+order.toString());
        try{
            //修改订单信息
            boolean result = decorateOrderService.updateOrder(order);
            if (result){
                /**
                 * 如果修改成功，并且修改的状态是“已签约”，appoint_user_id 不为空，插入sys_user_message表
                 * **/
                //处理用户消息  开始
                if (null!=update.getOrderStatus()&&update.getOrderStatus().equals(DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_HAVE_HAS_SIGNED)){
                    logger.error(LOGPREFIX + "(修改客户订单状态（已签约）成功---添加消息--start)");
                    DecorateCustomerDetail detail = decorateOrderMapper.getById(update.getOrderId().intValue());
                    logger.error(LOGPREFIX + "(修改客户订单状态（已签约）成功---查询预约用户Id)"+detail.getAppointUserId());
                    //预约用户Id不为空，则向sys_user_message表添加信息
                    if (null!=detail.getAppointUserId()){
                        //构造入参
                        SysUserMessage message = createMessage(detail.getAppointUserId(), loginUser,order.getId());
                        SysUserMessage message2=createMessage2(loginUser,order.getId());
                        logger.error(LOGPREFIX + "(修改客户订单状态（已签约）成功---添加消息--)"+message.toString());
                        int add = sysUserMessageService.add(message);
                        int add2 = sysUserMessageService.add(message2);
                        if (add<0){
                            return new ResponseEnvelope<>(false,"订单状态修改成功，添加客户消息失败");
                        }
                        if (add2<0){
                            return new ResponseEnvelope<>(false,"订单状态修改成功，添加装企消息失败");
                        }
                    }
                    logger.error(LOGPREFIX + "(修改客户订单状态（已签约）成功---添加消息--end)");
                }
               // 处理用户消息结束
                return new ResponseEnvelope<>(true ,"修改成功");
            }
            return new ResponseEnvelope<>(false,"修改失败");
        }catch (Exception e){
            logger.error(LOGPREFIX + "(修改客户订单--updateInfo方法异常 ：)"+e);
            return new ResponseEnvelope<>(false,"系统异常");
        }
    }


    /**
     * 提交退款申请
     * @param refund
     * @return
     */
    @RequestMapping(value = "/refund/application", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope refundApplication(@RequestBody RefundApplication refund) {
        //校验参数--start
        if(null==refund.getOrderId()) {
            logger.error(LOGPREFIX + "(orderId == null ) = true");
            return new ResponseEnvelope<>(false, "订单Id为空");
        }
        if (null==refund.getRefundReason()){
            logger.error(LOGPREFIX + "(refundReason == null ) = true");
            return new ResponseEnvelope<>(false, "申请退款原因为空");
        }
        //校验参数--end
        //获取当前登录用户信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null || loginUser.getId() == null) {
            logger.error(LOGPREFIX + "(loginUser == null || loginUser.getId() == null) = true");
            return new ResponseEnvelope<>(false, "请重新登录");
        }
        //构造退款申请service层入参
        DecorateOrder order = this.getInfoFromRefund(refund, loginUser);
        try{
            //提交退款申请
            boolean result = decorateOrderService.updateOrder(order);
            if (!result){
                return new ResponseEnvelope<>(false,"添加退款申请失败");
            }
            return new ResponseEnvelope<>(true ,"提交退款申请成功");

        }catch (Exception e){
            logger.error(LOGPREFIX + "(退款申请--refundApplication方法异常 ：)"+e);
            return new ResponseEnvelope<>(false,"系统异常");
        }
    }


    /**
     *构造退款申请入参
     *
     * 退款申请实际是修改DecorateOrder表中跟退款相关的数据
     * @param refund
     * @param loginUser
     * @return
     */
    private DecorateOrder getInfoFromRefund(RefundApplication refund, LoginUser loginUser) {
        DecorateOrder order=new DecorateOrder();
        order.setId(refund.getOrderId());
        order.setRefundReason(refund.getRefundReason());
        //退款类型---用户申请
        order.setRefundType(DecorateOrderConstants.REFUND_TYPE_BY_USER);
        //退款状态---待审核
        order.setRefundStatus(DecorateOrderConstants.REFUND_STATUS_WAITING);
        order.setRefundApplyTime(new Date());
        order.setGmtModified(new Date());
        order.setModifier(loginUser.getLoginName());
        return order;
    }


    /**
     * 参数转换，构造修改操作服务层入参
     * @param update 接口入参
     * @param loginUser 当前用户
     * @return
     */
    private DecorateOrder paramTransform(DecorateOrderUpdate update, LoginUser loginUser) {
        DecorateOrder order=new DecorateOrder();
        order.setId(update.getOrderId());
        if (null!=update.getOrderStatus()){
            order.setOrderStatus(update.getOrderStatus());//订单状态
            //如果是将订单状态修改为“已签约”，则加上签约时间
            if(update.getOrderStatus().equals(DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_HAVE_HAS_SIGNED)){
                order.setSignTime(new Date(System.currentTimeMillis()));
                order.setContractStatus(0);//待上传合同
            }
        }
       if (null!=update.getRemark()){
           order.setRemark2(update.getRemark());
       }
        order.setModifier(loginUser.getLoginName()!=null?loginUser.getLoginName():"noName");
        order.setGmtModified(new Date());
        return order;
    }


    /**
     * 构造用户消息实体
     * @param userId
     * @param loginUser
     * @return
     */
    private SysUserMessage createMessage(Long userId,LoginUser loginUser,Long orderId){
        SysUserMessage message=new SysUserMessage();
            message.setUserId(userId.intValue());
            message.setContent("恭喜您签约成功，赶快去评价一下吧。");
            message.setNeedComment(1);//需要评价
            message.setCommentFlag(0);//未评价
            message.setMessageType(2);//系统消息
            message.setStatus(1);//成功
            message.setCreator(loginUser.getLoginName());
            message.setRemark("B端修改订单状态---已签约");
            message.setTitle("签约成功消息");
            message.setTaskId(orderId.intValue());//taskId=orderId
            return message;
    }


    /**
     * 构造装企消息实体
     * @param orderId
     * @param loginUser
     * @return
     */
    private SysUserMessage createMessage2(LoginUser loginUser,Long orderId){
        SysUserMessage message=new SysUserMessage();
        message.setUserId(loginUser.getId());
        message.setContent("恭喜您签约成功，为不影响您后续接单，请在24小时内上传合同。");
        message.setNeedComment(1);//需要评价
        message.setCommentFlag(0);//未评价
        message.setMessageType(2);//系统消息
        message.setStatus(1);//成功
        message.setCreator(loginUser.getLoginName());
        message.setRemark("B端修改订单状态---已签约");
        message.setTitle("签约成功消息");
        message.setTaskId(orderId.intValue());//taskId=orderId
        return message;

    }


}
