package com.sandu.pay.order.dao;

import com.sandu.pay.order.PayModelVo;
import com.sandu.pay.order.model.PayModelGroupRef;
import com.sandu.pay.order.vo.PackageInfoVo;
import com.sandu.pay.order.vo.PayModelInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author yzw
 * @Date 2018/1/16 15:57
 */
public interface PayModelGroupRefMapper {

    /**
     * 删除
     *
     * @param id 付款方式业务关联表id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加
     *
     * @param record
     * @return
     */
    int insertSelective(PayModelGroupRef record);

    /**
     * 查询
     *
     * @param id
     * @return 付款方式业务关联表id
     */
    PayModelGroupRef selectByPrimaryKey(Integer id);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PayModelGroupRef record);

    /**
     * 获取用户未过期的付款方式业务关联表记录
     *
     * @param map
     * @return
     */
    List<PayModelGroupRef> getPayModelGroupRefList(Map<String, Object> map);

    /**
     * 获取付款方式业务关联表数据
     *
     * @param map
     * @return
     */
    public PayModelGroupRef getPayModelGroupRef(Map<String, Object> map);

    /**
     * 获取用户的套餐列表
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> getUserRefInfoList(Map<String, Object> map);

    /**
     * 获取用户可免费渲染的关联信息
     *
     * @param map
     * @return
     */
    List<PayModelVo> getPayModelVoRenderList(Map<String, Object> map);

    /**
     * 获取用户可免费购买户型的关联信息
     *
     * @param map
     * @return
     */
    List<PayModelVo> getPayModelVoHouseList(Map<String, Object> map);

    /**
     * 获取公司id
     *
     * @param companyDomainName 公司品牌网站域名
     * @return
     */
    Integer getCompanyNameByDoMianName(@Param("companyDomainName") String companyDomainName);


    /**
     * 普通用户或者经销商用户渲染的时候获取的产品名称（假如用户具备包年、包月、赠送，则按照时间最早的来取）
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商 0否 1是
     * @return
     */
    public String getRenderPackageName(@Param("businessId") Integer businessId, @Param("isFranchiser") Integer isFranchiser);

    /**
     * 获取付款方式关联表id（假如用户具备包年、包月、赠送，则按照时间最迟的来取）
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商 0否 1是
     * @return
     */
    public Integer getPayModelGroupRefId(@Param("businessId") Integer businessId, @Param("isFranchiser") Integer isFranchiser);

    /**
     * pc端获取用户的套餐信息
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商 0否 1是
     * @return
     */
    public PackageInfoVo getPackageInfoVoPc(@Param("businessId") Integer businessId, @Param("isFranchiser") Integer isFranchiser);

    /**
     * 获取用户的渲染套餐（包含过期）
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商子主账号 0否 1是
     * @return
     */
    public List<PayModelInfoVo> getPayModelVoList(@Param("businessId") Integer businessId, @Param("isFranchiser") Integer isFranchiser);

    /**
     * 获取用户的套餐信息（未过期的套餐）
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商子主账号 0否 1是
     * @return
     */
    public List<PackageInfoVo> getPackageInfoVoList(@Param("businessId") Integer businessId, @Param("isFranchiser") Integer isFranchiser);

    List<Map<String,Object>> getUserRefInfoList2C(@Param("businessId") Integer userId,@Param("bizType") String renderType2c);

    List<PayModelVo> getPayModelVoRenderList2CBybusinessId(Integer userId);

    PayModelGroupRef getPayModelVoListByBusinessId(Integer businessId);

    PayModelGroupRef selectByBusinessId(Integer userId);

    PayModelGroupRef selectIsExistCompanyPackages(@Param("businessId")Integer id, @Param("payModelConfigId")Integer payModelConfigId);

    PayModelGroupRef selectByBusinessIdAndPayModelConfigId(@Param("businessId")Long businessId, @Param("payModelConfigId")Integer payModelConfigId);

    PayModelGroupRef getByBusinessId(int businessId);
}
