package com.sandu.web.user.controller;

import com.sandu.api.account.model.PayAccount;
import com.sandu.api.account.model.PayOrder;
import com.sandu.api.account.service.PayAccountService;
import com.sandu.api.account.service.PayOrderService;
import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.base.service.BasePlatformService;
import com.sandu.api.user.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.ResponseEnvelope;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@RestController
@RequestMapping(value = "/v1/dubi")
public class UserDubiController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserDubiController.class);

    @Autowired
    private PayAccountService payAccountService;

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private BasePlatformService basePlatformService;

    @ApiOperation(value = "用户度币信息")
    @GetMapping(value = "/getUserDubiInfo")
    public Object getUserDubiInfo(HttpServletRequest request) {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {
            PayAccount payAccount = payAccountService.getPayAccountByUserId(loginUser.getId());
            if (Objects.nonNull(payAccount)){
                payAccount.setBalanceAmount(payAccount.getBalanceAmount() / 10);
            }
            return new ResponseEnvelope<>(true, payAccount == null ? new PayAccount() : payAccount);
        } catch (Exception e) {
            LOGGER.error("系统错误", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
    }

    @ApiOperation(value = "用户版权费用消费列表")
    @GetMapping(value = "/getUserDubiExpensesRecordList")
    public Object getUserDubiExpensesRecordList(Long platformId, String ExpenseType, Integer start, Integer limit) {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        if (Objects.isNull(start) && Objects.isNull(limit)) {
            start = 0;
            limit = 10;
        } else {
            start = (start - 1) * 10;
        }

        try {
            //统计记录数
            int count = payOrderService.countExpensesRecordList(loginUser.getId(), ExpenseType, platformId);

            List<PayOrder> payOrders = new ArrayList<>();

            if (count > 0) {
                payOrders = payOrderService.selectExpensesRecordList(loginUser.getId(), ExpenseType, platformId, start, limit);
                //设置平台名称
                this.setPlatformName(payOrders);
                //充值度币时,totalFee中存的是分,取出来时 /10
                this.handlerShowTotalFee(payOrders,10);
            }
            return new ResponseEnvelope<>(true, count, payOrders);
        } catch (Exception e) {
            LOGGER.error("系统错误", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
    }

    private void setPlatformName(List<PayOrder> payOrders) {
        //获取度币消费平台
        List<BasePlatform> lists = basePlatformService.queryList();
        for (PayOrder payOrder : payOrders){
            for (BasePlatform plat : lists){
                if (Objects.equals(payOrder.getPlatformId(),plat.getId().intValue())){
                    payOrder.setPlatformName(plat.getPlatformName());
                    break;
                }
            }
        }

    }


    @ApiOperation(value = "获取用户充值记录")
    @GetMapping(value = "/getUserRechargeRecord")
    public Object getUserRechargeRecord(Integer start, Integer limit) {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        if (Objects.isNull(start) && Objects.isNull(limit)) {
            start = 0;
            limit = 10;
        } else {
            start = (start - 1) * 10;
        }

        try {
            int count = payOrderService.countUserRechargeRecord(loginUser.getId());

            List<PayOrder> payOrders = new ArrayList<>();

            if (count > 0) {
                payOrders = payOrderService.getUserRechargeRecords(loginUser.getId(), start, limit);
                //设置平台名称
                this.setPlatformName(payOrders);
                //充值度币时,totalFee中存的是分,取出来时 /100
                this.handlerShowTotalFee(payOrders, 100);
            }
            return new ResponseEnvelope<>(true, count, payOrders);
        } catch (Exception e) {
            LOGGER.error("系统错误", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
    }

    private void handlerShowTotalFee(List<PayOrder> payOrders, int multiple) {
        payOrders.stream().forEach(it->{
            if(Objects.nonNull(it.getTotalFee())){
                it.setTotalFee(it.getTotalFee()/ multiple);
            }
        });
    }

    @GetMapping("/getPlatforms")
    public Object getBasePlatfroms() {

        List<BasePlatform> basePlatforms;
        try {
            basePlatforms = basePlatformService.queryList();
        } catch (Exception e) {
            LOGGER.error("系统错误", e);
            return new ResponseEnvelope<>(true, "系统错误");
        }
        return new ResponseEnvelope<>(true, basePlatforms);
    }

}
