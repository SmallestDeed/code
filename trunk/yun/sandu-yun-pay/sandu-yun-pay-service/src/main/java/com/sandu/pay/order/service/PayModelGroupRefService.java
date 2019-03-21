package com.sandu.pay.order.service;

import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.order.PayModelVo;
import com.sandu.pay.order.model.PayModelGroupRef;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.pay.order.vo.PackageInfoVo;
import com.sandu.pay.order.vo.PayModelInfoVo;
import com.sandu.user.model.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @Author yzw
 * @Date 2018/1/16 15:57
 */
public interface PayModelGroupRefService {

    /**
     * 添加
     *
     * @param record
     * @return
     */
    PayModelGroupRef add(PayModelGroupRef record);

    /**
     * 删除
     *
     * @param id 付款方式业务关联表id
     * @return
     */
    boolean delete(Integer id);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    PayModelGroupRef update(PayModelGroupRef record);

    /**
     * 查询
     *
     * @param id
     * @return 付款方式业务关联表id
     */
    PayModelGroupRef get(Integer id);

    /**
     * 判断是否具备渲染权限接口
     *
     * @param platformId 平台id
     * @param userId     用户id
     * @return
     */
    public ResultMessage checkRenderGroopRef(Integer platformId, Integer userId);

    /**
     * 用户绑定序列号赠送
     *
     * @param basePlatform
     * @param sysUser
     * @return
     */
    public ResultMessage addGiveGroopRef(BasePlatform basePlatform, SysUser sysUser);

    /**
     * 获取付款方式业务关联表数据
     *
     * @param businessId       业务id
     * @param payModelConfigId 付款方式业务关联表id
     * @return
     */
    public PayModelGroupRef getPayModelGroupRef(Integer businessId, Integer payModelConfigId);

    /**
     * 用户付款后插入记录到付款方式业务关联表
     *
     * @param orderNo 订单号
     */
    public void addPayModelGroupRef(String orderNo);

    /**
     * 获取渲染时候的提示信息
     *
     * @param payModelGroupRefId 付款方式业务关联表id
     * @return
     */
    public Map<String, Object> getMessage(Integer payModelGroupRefId);

    /**
     * 获取用户的套餐列表
     *
     * @param businessId 业务id
     * @param bizType    业务类型
     * @param rangeType  范围类型
     * @return
     */
    public List<Map<String, Object>> getUserRefInfoList(Integer businessId, String bizType, Integer rangeType);

    /**
     * 判断经销商用户是否具备包年包月的权限
     *
     * @param platformId        平台id
     * @param franchiserGroupId 经销商账号组id
     * @param userId            用户id
     * @return
     */
    public ResultMessage checkRenderShareGroopRef(Integer platformId, Integer franchiserGroupId, Integer userId);

     /**
     * 获取用户可免费渲染的关联信息
     *
     * @param sysUser
     * @param basePlatform      平台信息
     * @param companyDomainName 公司品牌网站域名
     * @return
     */
    List<PayModelVo> getPayModelVoRenderList(SysUser sysUser, BasePlatform basePlatform, String companyDomainName);

    /**
     * 获取用户可免费购买户型的关联信息
     *
     * @param sysUser
     * @param platformId        平台id
     * @param companyDomainName 公司品牌网站域名
     * @return
     */
    List<PayModelVo> getPayModelVoHouseList(SysUser sysUser, Integer platformId, String companyDomainName);

    /**
     * 普通用户或者经销商用户渲染的时候获取的产品名称（假如用户具备包年、包月、赠送，则按照时间最早的来取）
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商子主账号 0否 1是
     * @return
     */
    public String getRenderPackageName(Integer businessId, Integer isFranchiser);

    /**
     * 获取付款方式关联表id（假如用户具备包年、包月、赠送，则按照时间最迟的来取）
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商子主账号 0否 1是
     * @return
     */
    public Integer getPayModelGroupRefId(Integer businessId, Integer isFranchiser);

    /**
     * pc端获取用户的套餐信息
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商子主账号 0否 1是
     * @return
     */
    public PackageInfoVo getPackageInfoVoPc(Integer businessId, Integer isFranchiser);

    /**
     * 获取用户的渲染套餐（包含过期）
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商子主账号 0否 1是
     * @return
     */
    public List<PayModelInfoVo> getPayModelVoList(Integer businessId, Integer isFranchiser);

    /**
     * 获取用户的套餐信息（未过期的套餐）
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商子主账号 0否 1是
     * @return
     */
    public List<PackageInfoVo> getPackageInfoVoList(Integer businessId, Integer isFranchiser);

    List<Map<String,Object>> getUserRefInfoList2C(Integer userId, String renderType2c);

    void addPayModelGroupRef2C(PayOrder orderNo);

    List<PayModelVo> getPayModelVoRenderList2C(Long userId);

    boolean checkUserPackages(Integer userId);

    /**
     * B端登录调用校验企业是否具有赠送包年套餐
     * @param companyId
     * @return
     */
    PayModelGroupRef checkCompanyGivePackage(Long companyId);

    /**
     * 插入B端企业赠送包年套餐
     * @param userId
     * @param p
     */
    void add2bCompanyGivePackage(Long userId,PayModelGroupRef p);

    PayModelGroupRef checkCompanyBuyRender(Long userId);

    int add2bUserPackageGiveRender(Long userId,Integer month);

    boolean updatePackageGiveRender(Long userId,Integer month);
}
