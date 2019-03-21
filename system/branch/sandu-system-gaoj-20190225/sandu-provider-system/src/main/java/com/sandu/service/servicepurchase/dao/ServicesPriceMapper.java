package com.sandu.service.servicepurchase.dao;

import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.servicepurchase.input.query.ServicesBaseInfoQuery;
import com.sandu.api.servicepurchase.input.query.ServicesPriceQuery;
import com.sandu.api.servicepurchase.model.ServicesPrice;
import com.sandu.api.servicepurchase.output.ServicesPriceVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServicesPrice record);

    int insertSelective(ServicesPrice record);

    ServicesPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServicesPrice record);

    int updateByPrimaryKey(ServicesPrice record);

    List<ServicesPriceVO> selectByServicesId(Long servicesId);

    List<ServicesPrice> selectServicesPriceList(Long servicesId);

    List<ServicesPrice> queryByOption(ServicesPriceQuery servicesPriceQuery);

    /**
     * 获取公司套餐价格详情
     *
     * @param id
     * @param cid
     * @param duration
     * @param priceUnit
     * @return
     */
    ServicesPrice getPriceInfo(@Param("servicesId") Integer id, @Param("companyId") String cid, @Param("duration") Integer duration, @Param("priceUnit") Integer priceUnit);

    /**
     * 查询套餐
     *
     * @param servicesId
     * @return
     */
    List<ServicesPrice> queryServicesPriceBySid(@Param("servicesId") Integer servicesId);

    /**
     * 删除套餐
     *
     * @param servicesId
     * @return
     */
    int deleteServicesPriceBySid(@Param("servicesId") Integer servicesId);

    /**
     * 删除套餐servicesId
     * @param servicesId
     * @return
     */
    int deleteServicesPrice(@Param("servicesId") Integer servicesId);

    /**
     * 根据套餐id获取套餐企业信息
     * @param query
     * @return
     */
    Integer getCount(ServicesBaseInfoQuery query);

    /**
     * 查询公司对应的套餐价格
     * @param query
     * @return
     */
    List<ServicesPrice> queryPriceForCompany(ServicesBaseInfoQuery query);

    List<ServicesPrice> queryPriceForCompanyByPage(@Param("servicesId") Integer servicesId, @Param("companyIds") List<Integer> companyIds);

    /**
     * 查询基本公司
     * @param companyId
     * @return
     */
    BaseCompany getCompanyList(@Param("companyId") Integer companyId);

    /**
     * 删除企业套餐价格
     * @param companyId
     * @param servicesId
     * @return
     */
    int deleteCompanyPrice(@Param("companyId") Integer companyId, @Param("servicesId") Integer servicesId);

    /**
     * 根据公司id和套餐id查询 套餐价格信息
     * @param companyId
     * @param servicesId
     * @return
     */
    List<ServicesPrice> getPriceByCompanyId(@Param("companyId") Integer companyId, @Param("servicesId") Integer servicesId);
}