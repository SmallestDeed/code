package com.sandu.service.customer.dao;


import com.sandu.api.company.model.Company;
import com.sandu.api.customer.input.query.CustomerAlotZoneQuery;
import com.sandu.api.customer.model.CustomerAlotZone;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author sandu-lipeiyuan
 */
@Repository
public interface CustomerAlotZoneMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CustomerAlotZone record);

    CustomerAlotZone selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerAlotZone record);

    List<CustomerAlotZone> selectByOption(CustomerAlotZoneQuery query);

    int countByOption(CustomerAlotZoneQuery query);

    /**
     * 查询已经设置过的分配规则的经销商
     * @param distributor
     * @return
     */
    List<Company> queryDistributorByInfo(Company distributor);

    /**
     * 根据类型查询是否自动生成过分配规则
     * @param type
     * @param channelId
     * @return
     */
    Company getDistributorByType(@Param("type") Integer type,@Param("channelId")Integer channelId);

    /**
     * 根据经销商id查询分配规则表
     * @param companyId
     * @return
     */
    List<CustomerAlotZone> queryDistributorByCompanyId(@Param("companyId") Long companyId);

    /**
     * 根据省市区查询分配规则表满足条件的分配规则id
     * @param province
     * @param city
     * @param area
     * @return
     */
    List<CustomerAlotZone> queryCustomerByCode(@Param("companyId") Integer companyId,@Param("provinceCode") String province, @Param("cityCode")String city,
                                      @Param("areaCode")String area);

    /**
     * 查询所有分配记录表
     * @return
     */
    List<CustomerAlotZone> queryAlotZone();

    /**
     * 取消分配
     * @param companyId
     * @return
     */
    int deleteByCompanyId(@Param("companyId") Integer companyId);

    /**
     * 更新分配记录表分配次数
     * @param alotZoneId
     */
    void updateAllotCountById(@Param("alotZoneId") Integer alotZoneId);

    /**
     * 根据经销商id分组查询分配规则表
     * @return
     */
    List<Integer> queryAlotZoneGourpByDistributorId(CustomerAlotZoneQuery query);

    /**
     * 根据经销商id查询对应的分配信息
     * @param distributorIds
     * @return
     */
    List<CustomerAlotZone> queryAlotZoneByDistributorIds(@Param("ids") List<Integer> distributorIds);

    /**
     * 根据经销商id查询分配规则
     * @param distributorId
     * @return
     */
    List<CustomerAlotZone> queryAlotZoneByDistributorId(@Param("distributorId") Integer distributorId);
}