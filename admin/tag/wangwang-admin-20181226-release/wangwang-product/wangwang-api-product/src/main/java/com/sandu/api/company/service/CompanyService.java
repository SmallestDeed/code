package com.sandu.api.company.service;

import com.sandu.api.company.input.CompanyIntroduceUpdate;
import com.sandu.api.company.model.Company;
import com.sandu.api.company.model.CompanyMiniProgramConfig;
import com.sandu.api.company.model.RichContext;
import com.sandu.api.company.model.bo.CompanyBO;
import com.sandu.api.company.model.bo.CompanyBrandBo;
import com.sandu.api.company.model.bo.CompanyIntroduceBO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Sandu
 * @date 2017/12/14
 */
public interface CompanyService {
    /**
     * 新增数据
     *
     * @param company
     * @return int
     */
    int saveCompany(Company company);

    /**
     * 更新数据
     *
     * @param company
     * @return int
     */
    int updateCompany(Company company);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int deleteCompanyById(long id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return Company
     */
    Company getCompanyById(long id);

    /**
     * 获取数据(无筛选)
     *
     * @param page
     * @param limit
     * @return
     */
    List<Company> findAllCompany(int page, int limit);

    /**
     * 过滤查询
     *
     * @param companyCode
     * @param companyName
     * @param industry
     * @param order
     * @param orderWay
     * @param page
     * @param limit
     * @return
     */
    List<Company> queryCompanyByCompanyInfo(String companyCode, String companyName, Integer industry,
                                            String orders, String order, String orderWay,
                                            int page, int limit);


    /**
     * 查询所有的名称和ID(创建品牌时,需要选择选择企业)
     *
     * @return
     */
    List<Company> findAllCompanyNameAndId();

    /**
     * 查询某一行业最大的企业code(用于生成新code)
     *
     * @param industry
     * @return
     */
    String queryMaxCompanyCode(String industry);

    List<CompanyBrandBo> getCompanyByBrandIds(List<Integer> brandIds);

    CompanyBO getCompanyInfoById(long id);

    List<Company> findAll(Integer bizType);

    List<Integer> listPeerCompanys(Integer companyId);

    /**
     * 获取品牌介绍信息
     *
     * @param companyId
     * @return
     */
    CompanyIntroduceBO getCompanyIntroduce(Integer companyId);

    /**
     * 更新品牌介绍信息
     *
     * @param companyIntroduceUpdate
     * @return
     */
    Integer updateCompanyIntroduceById(CompanyIntroduceUpdate companyIntroduceUpdate);

    /**
     * 根据公司id匹配公司名称
     *
     * @param companyIds
     * @return
     */
    Map<Long, String> idAndNameMap(List<Integer> companyIds);

    /**
     * 查询有完整地址的经销商
     *
     * @return
     */
    List<Company> queryDistributor();

    /**
     * 根据厂商查询下面的经销商
     *
     * @param companyId
     * @return
     */
    List<Company> queryDistributorByCompanyId(Integer companyId);

    /**
     * 根据id查询公司名称
     *
     * @param companyId
     * @return
     */
    Company getCompanyNameById(Integer companyId);

    /**
     * 查询厂商下面的经销商
     *
     * @param pid 厂商id
     * @return
     */
    List<Company> queryDealerByPid(Integer pid);

    List<Company> getCompanyByIds(Set<Integer> companyIds);

    int saveOrUpdateCompanyRichContext(RichContext context);

    RichContext getRichContext(RichContext context);

    CompanyMiniProgramConfig getCompanyMiniConfigByCompanyId(Integer companyId);

}
