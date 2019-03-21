package com.sandu.api.company.service.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.company.input.InnerCompanyInput;
import com.sandu.api.company.input.InnerCompanyQuery;
import com.sandu.api.company.model.bo.InnerCompanyBO;
import com.sandu.api.company.output.InnerCompanyDetails;

import java.util.List;

/**
 * @author Sandu
 * @ClassName InnerCompanyBizService
 * @date 2018/11/6
 */
public interface InnerCompanyBizService {

	/**
	 * 新增企业
	 *
	 * @param companyInput
	 * @return
	 */
	Integer addCompany(InnerCompanyInput companyInput);


	/**
	 * 根据企业ID删除企业
	 *
	 * @param companyIds ids
	 * @param userId     操作人ID
	 * @return
	 */
	Integer deleteByIds(List<Integer> companyIds, Integer userId);

	/**
	 * 更新企业信息
	 *
	 * @param companyInput
	 * @return
	 */
	Integer updateCompany(InnerCompanyInput companyInput);


	/**
	 * 查询企业列表
	 *
	 * @return
	 * @param query
	 */
	PageInfo<InnerCompanyBO> listCompany(InnerCompanyQuery query);


	/**
	 * 获取企业详情
	 *
	 * @param companyId
	 * @return
	 */
	InnerCompanyDetails details(Long companyId);

	int checkCompanyName(String name);
}
