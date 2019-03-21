package com.sandu.api.servicepurchase.serivce.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.servicepurchase.input.query.ServiceQuery;
import com.sandu.api.servicepurchase.model.ServicesPurchaseRecord;
import com.sandu.api.servicepurchase.model.bo.*;
import com.sandu.api.servicepurchase.model.vo.OfficalServicesListVO;
import com.sandu.api.servicepurchase.output.ServicesBaseInfoVO;
import com.sandu.api.user.model.SysUser;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public interface ServicesPurchaseBizService {
    /**
     * 根据服务用户可用类型获取服务
     *
     * @param companyId     企业ID
     * @param userTypeValue 用户类型value
     * @return
     */
    PageInfo<ServicesListBO> getServicesByScope(ServiceQuery query);

    /**
     * 获取服务权限信息
     *
     * @param id 服务ID
     * @return
     */
    List<ServicesFuncBO> getServiceFuncsByServiceId(Integer id);

    /**
     * 获取企业已经购服务
     *
     * @param companyId
     */
    PageInfo<ServicesPurchaseListBO> getBePurchasedServices(ServiceQuery query);

    /**
     * 企业购买记录
     *
     * @param companyId
     * @return
     */
    PageInfo<ServicesPurchaseLogListBO> getPurchasedServicesLogs(ServiceQuery query);

    ServicesBaseInfoVO findServicesPriceById(Long servicesId);

    /**
     * 根据套餐ID和时长选择计算总价
     *
     * @param servicesId
     * @param priceId
     * @param purchaseAmount
     * @return
     */
    BigDecimal getServicesPayAmount(Long servicesId, Long priceId, Long purchaseAmount);

    /**
     * 购买请求入口
     *
     * @param add
     * @return
     */
    Map<String, Object> saveBuy(ServicesPurchaseRecord add);

    /**
     * 查看用户角色是否已过期
     *
     * @param account
     * @param roleIds
     * @return
     */
    ServicesRoleInfoBO getValidRoles(String account, Long userId, Set<Long>roleIds);

    /**
     * 获取官网套餐信息
     *
     * @param channelId
     * @return
     */
    OfficalServicesListVO getOfficalServicesInfo(Integer channelId);

    /**
     * 检查手机号是否参与套餐
     *
     * @param telephone
     * @return
     */
    int checkTelephoneExists(String telephone);

    /**
     * 生成套餐订单
     *
     * @param serviceRecordInitBO
     * @return
     */
    ServicesPurchaseRecord initServiceOrder(ServiceRecordInitBO serviceRecordInitBO);

    /**
     * 更新订单状态以及账户使用期
     *
     * @param orderNum     套餐订单号
     * @param recordStatus 套餐支付状态
     * @return
     */
    boolean syncServiceAccount(String orderNum, String recordStatus);

    Map<String, Object> getFreeRenderDurationByUserid(Long userId);

    /**
     * 更新订单的支付状态
     *
     * @param payTradeNo
     * @param recordStatus
     */
    boolean updateRedisStatus(String payTradeNo, String recordStatus);

    /**
     * 获取订单的支付状态
     *
     * @param payTradeNo
     * @return
     */
    String getTradeStatus(String payTradeNo);

    Object mobile2bRegisterUsePackage(SysUser sysUser);
}
