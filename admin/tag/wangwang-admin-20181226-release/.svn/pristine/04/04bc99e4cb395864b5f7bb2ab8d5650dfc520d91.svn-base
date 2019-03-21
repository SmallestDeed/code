package com.sandu.api.customer.service;


import com.github.pagehelper.PageInfo;
import com.sandu.api.company.model.Company;
import com.sandu.api.customer.input.CustomerAlotZoneAdd;
import com.sandu.api.customer.input.query.CustomerAlotZoneQuery;
import com.sandu.api.customer.model.CustomerAlotZone;
import com.sandu.api.customer.output.CustomerAlotZoneVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author sandu-lipeiyuan
 */
@Component
public interface CustomerAlotZoneService {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CustomerAlotZone record);

    CustomerAlotZone selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerAlotZone record);

    /**
     * 查询厂商下面所有的经销商分配信息
     * @param query
     * @return
     */
    List<CustomerAlotZone> selectByOption(CustomerAlotZoneQuery query);

    int countByOption(CustomerAlotZoneQuery query);

    /**
     * 自动为有地址的经销商生成分配规则
     */
    void autoGeneraRule();

    /**
     * 自动按区域分配客户
     */
    void allotAreaRule();

    /**
     * 将用户转为小程序客户
     */
    void allotCustomer();
    /**
     * 手动新增分配规则
     * @param add
     * @return
     */
    int addGeneraRule(CustomerAlotZoneAdd add);

    /**
     * 根据经销商查询分配规则
     * @param companyId
     * @return
     */
    List<CustomerAlotZone> queryAlotZoneByCompany(Long companyId);

    /**
     * 取消分配
     * @param companyId
     * @return
     */
    int deleteByCompanyId(Integer companyId);

    /**
     * 查询厂商下面所有的经销商分配信息
     * @param query
     * @return
     */
    List<CustomerAlotZoneVO> queryAlotZoneList(CustomerAlotZoneQuery query);

    /**
     * 查询厂商下面所有的经销商
     * @param companyId
     * @return
     */
    List<Company> queryCompanyList(Integer companyId);

    /**
     * 获得地区编码，名称map集合
     * @return
     */
    Map<String,String> queryAreaMap();

    List<CustomerAlotZone> queryAlotZoneByDistributorId(Integer distributorId);

}