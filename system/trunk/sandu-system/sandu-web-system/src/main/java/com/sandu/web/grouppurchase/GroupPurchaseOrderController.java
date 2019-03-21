package com.sandu.web.grouppurchase;

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.api.grouppurchase.input.GroupPurchaseOrderNew;
import com.sandu.api.grouppurchase.output.GroupPurchaseOrderVO;
import com.sandu.api.grouppurchase.service.biz.GroupPurchaseOrderBizService;
import com.sandu.common.LoginContext;
import com.sandu.commons.ResponseEnvelope;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/11 10:18
 * @since 1.8
 */

@Slf4j
@RestController
@RequestMapping("/v1/group/purchase/order")
public class GroupPurchaseOrderController {

    @Resource
    GroupPurchaseOrderBizService groupPurchaseOrderBizService;

    @PostMapping("/create")
    public ResponseEnvelope<GroupPurchaseOrderVO> createOrder(GroupPurchaseOrderNew orderNew, HttpServletRequest request) {
        if (orderNew == null) {
            log.error("参数异常：orderNew不能为空");
            return new ResponseEnvelope<>(false, "参数异常：orderNew不能为空");
        }

        if (StringUtils.isBlank(orderNew.getConsignee()) || StringUtils.isBlank(orderNew.getMobile())
                || StringUtils.isBlank(orderNew.getProvince()) || StringUtils.isBlank(orderNew.getDistrict())
                || StringUtils.isBlank(orderNew.getCity()) || StringUtils.isBlank(orderNew.getAddress())) {
            log.error("参数异常：参数缺失，orderNew => {}", orderNew);
            return new ResponseEnvelope<>(false, "参数异常：参数缺失");
        }

        // 用户领取的优惠价
        if (StringUtils.isNotEmpty(orderNew.getCouponUserNo())) {
            orderNew.setReceiveNo(Long.parseLong(orderNew.getCouponUserNo()));
        }

        if (orderNew.getOrderProductList() == null || orderNew.getOrderProductList().isEmpty()) {
            log.error("参数异常：orderProductList不能为空");
            return new ResponseEnvelope<>(false, "请选择需要购买的商品");
        }

        // 获取用户信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope<>(false, "请登录!");
        }
        orderNew.setUserId(loginUser.getId());

        // 获取当前的企业ID
        String domainUrl;
        try {
            domainUrl = request.getHeader("Referer");
            if (domainUrl == null) {
                domainUrl = request.getHeader("Custom-Referer");
            }
        } catch (Exception e) {
            domainUrl = request.getHeader("Custom-Referer");
        }
        orderNew.setDomainUrl(domainUrl);

        return groupPurchaseOrderBizService.createOrder(orderNew);
    }
}
