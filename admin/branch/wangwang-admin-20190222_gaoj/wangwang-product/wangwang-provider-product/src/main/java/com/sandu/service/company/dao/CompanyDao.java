package com.sandu.service.company.dao;

import com.sandu.api.company.input.CompanyIntroduceUpdate;
import com.sandu.api.company.model.Company;
import com.sandu.api.company.model.CompanyMiniProgramConfig;
import com.sandu.api.company.model.RichContext;
import com.sandu.api.company.model.bo.CompanyBO;
import com.sandu.api.company.model.bo.CompanyBrandBo;
import com.sandu.api.company.model.bo.CompanyIntroduceBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Sandu
 * @date 2017/12/14
 */
@Repository
public interface CompanyDao {
    /**
     * 添加企业
     *
     * @param company
     * @return
     */
    int saveCompany(Company company);

    /**
     * 更新企业
     *
     * @param company
     * @return
     */
    int updateCompany(Company company);

    /**
     * 删除企业
     *
     * @param id
     * @return
     */
    int deleteCompanyById(@Param("id") long id);

    /**
     * 根据ID查询企业
     *
     * @param id
     * @return
     */
    Company getCompanyById(@Param("id") long id);

    /**
     * 无条件加载企业数据
     *
     * @return
     */
    List<Company> findAllCompany(@Param("bizType") Integer bizType);

    /**
     * 过滤查询分页数据
     *
     * @param companyCode
     * @param companyName
     * @param industry
     * @param order
     * @param orderWay
     * @return
     */
    List<Company> queryCompanyByBrandInfo(@Param("companyCode") String companyCode,
                                          @Param("companyName") String companyName,
                                          @Param("industry") Integer industry,
                                          @Param("orders") String orders,
                                          @Param("order") String order,
                                          @Param("orderWay") String orderWay
    );

    /**
     * 获取所有数据的name 和ID
     *
     * @return
     */
    List<Company> findAllCompanyNameAndId();

    String queryMaxCompanyCode(@Param("industry") String industry);

    List<CompanyBrandBo> getCompanyByBrandIds(List<Integer> brandIds);

    CompanyBO getCompanyInfoById(long id);

    @Select("SELECT DISTINCT bc.id FROM base_company bc" +
            " WHERE bc.is_deleted = 0 AND bc.id IN" +
            "                            (SELECT DISTINCT ccr.company_id" +
            "                             FROM company_category_rel ccr" +
            "                             WHERE ccr.is_deleted = 0 and ccr.category_id IN (" +
            "                               SELECT DISTINCT ccr.category_id" +
            "                               FROM company_category_rel ccr  LEFT JOIN pro_category pc on ccr.category_id = pc.id" +
            "                               WHERE ccr.is_deleted=0 and ccr.company_id = #{companyId} and pc.level = 3 and pc.is_deleted = 0))")
    List<Integer> listPeerCompanys(@Param("companyId") Integer companyId);

    CompanyIntroduceBO getCompanyIntroduce(Integer companyId);

    Integer updateCompanyIntroduceById(CompanyIntroduceUpdate companyIntroduceUpdate);

    List<Company> listByIds(@Param("companyIds") List<Integer> companyIds);

    List<Company> queryDistributor();

    List<Company> queryDistributorByCompanyId(@Param("companyId") Integer companyId);

    Company getCompanyNameById(@Param("companyId") Integer companyId);

    List<Company> queryDealerByPid(Integer pid);

    List<Company> getCompanyByIds(@Param("companyIds") Set<Integer> companyIds);

    int saveNewRichContext(RichContext context);

    int updateRichContext(RichContext context);

    RichContext getRichContext(RichContext search);

    CompanyMiniProgramConfig getCompanyMiniProgramConfigByCompanyId(Integer companyId);
}
