package com.sandu.service.customer.dao;


import com.sandu.api.customer.input.query.CustomerBaseInfoQuery;
import com.sandu.api.customer.model.CustomerBaseInfo;
import com.sandu.api.customer.output.CustomerVO;

import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author sandu-lipeiyuan
 */
@Repository
public interface CustomerBaseInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CustomerBaseInfo record);

    CustomerBaseInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerBaseInfo record);

    List<CustomerBaseInfo> selectByOption(CustomerBaseInfoQuery query);

    Integer countByOption(CustomerBaseInfoQuery query);

    int updateByUserId(CustomerBaseInfo record);

    List<CustomerBaseInfo> queryByOption(CustomerBaseInfoQuery query);

    /**
     * 查询未被分配过的客户
     * @return
     */
    List<CustomerBaseInfo> queryNotAllotCus();

    void allotCustomer();

    /**
     * 获取需要导出的数据
     * @param customerBaseInfoQuery
     * @return
     */
	List<CustomerVO> queryExportData(CustomerBaseInfoQuery customerBaseInfoQuery);
}