package com.sandu.api.customer.service;


import com.github.pagehelper.PageInfo;
import com.sandu.api.customer.input.query.CustomerBaseInfoQuery;
import com.sandu.api.customer.model.CustomerBaseInfo;
import com.sandu.api.customer.output.CustomerVO;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author sandu-lipeiyuan
 */
@Component
public interface CustomerBaseInfoService {

    int deleteByPrimaryKey(Long id);

    int insertSelective(CustomerBaseInfo record);

    CustomerBaseInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerBaseInfo record);

    List<CustomerBaseInfo> selectByOption(CustomerBaseInfoQuery query);

    int countByOption(CustomerBaseInfoQuery query);

    PageInfo<CustomerBaseInfo> pageSelectByOption(CustomerBaseInfoQuery query, Integer pageNo, Integer pageSize);

    int updateByUserId(CustomerBaseInfo customerBaseInfo);

    /**
     * 查询未被分配过的客户
     * @return
     */
    List<CustomerBaseInfo> queryNotAllotCus();

    /**
     * 查询需要进行导出的数据
     * @param customerBaseInfoQuery
     * @return
     */
	List<CustomerVO> queryExportData(CustomerBaseInfoQuery customerBaseInfoQuery);
}