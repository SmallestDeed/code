package com.sandu.service.company.impl;

import com.github.pagehelper.PageHelper;
import com.sandu.api.company.input.CompanyIntroduceUpdate;
import com.sandu.api.company.model.Company;
import com.sandu.api.company.model.CompanyMiniProgramConfig;
import com.sandu.api.company.model.RichContext;
import com.sandu.api.company.model.bo.CompanyBO;
import com.sandu.api.company.model.bo.CompanyBrandBo;
import com.sandu.api.company.model.bo.CompanyIntroduceBO;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.service.company.dao.CompanyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.sandu.util.Commoner.isEmpty;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * 企业基础服务
 *
 * @author Sandu
 * @date 2017/12/15
 */
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;
    @Resource
    private ResPicService resPicService;


    @Override
    public int saveCompany(Company company) {
        return companyDao.saveCompany(company);
    }

    @Override
    public int updateCompany(Company company) {
        return companyDao.updateCompany(company);
    }

    @Override
    public int deleteCompanyById(long id) {
        return companyDao.deleteCompanyById(id);
    }

    @Override
    public Company getCompanyById(long id) {
        return companyDao.getCompanyById(id);
    }

    @Override
    public List<Company> findAllCompany(int page, int limit) {
        PageHelper.startPage(page, limit);
        return companyDao.findAllCompany(0);
    }

    @Override
    public List<Company> queryCompanyByCompanyInfo(String companyCode, String companyName, Integer industry,
                                                   String orders, String order, String orderWay,
                                                   int page, int limit) {
        PageHelper.startPage(page, limit);
        return companyDao.queryCompanyByBrandInfo(companyCode, companyName, industry, orders, order, orderWay);
    }


    @Override
    public List<Company> findAllCompanyNameAndId() {
        return companyDao.findAllCompanyNameAndId();
    }

    @Override
    public String queryMaxCompanyCode(String industry) {
        return companyDao.queryMaxCompanyCode(industry);
    }

    @Override
    public List<CompanyBrandBo> getCompanyByBrandIds(List<Integer> brandIds) {
        return companyDao.getCompanyByBrandIds(brandIds);
    }

    @Override
    public CompanyBO getCompanyInfoById(long id) {
        CompanyBO company = companyDao.getCompanyInfoById(id);
        if (company.getCompanyLogo() != null) {
            String path = resPicService.getPathById(company.getCompanyLogo());
            company.setLogoPath(path);
        }
        return company;
    }

    @Override
    public List<Company> findAll(Integer bizType) {
        return companyDao.findAllCompany(bizType);
    }

    @Override
    public List<Integer> listPeerCompanys(Integer companyId) {
        return companyDao.listPeerCompanys(companyId);
    }

    @Override
    public CompanyIntroduceBO getCompanyIntroduce(Integer companyId) {
        return companyDao.getCompanyIntroduce(companyId);
    }

    @Override
    public Integer updateCompanyIntroduceById(CompanyIntroduceUpdate companyIntroduceUpdate) {
        return companyDao.updateCompanyIntroduceById(companyIntroduceUpdate);
    }

    @Override
    public Map<Long, String> idAndNameMap(List<Integer> companyIds) {
        companyIds = companyIds.stream().filter(Objects::nonNull).distinct().collect(toList());
        if (isEmpty(companyIds)) {
            return Collections.emptyMap();
        }
        List<Company> companys = companyDao.listByIds(companyIds);
        return companys.stream().collect(toMap(Company::getId, Company::getCompanyName));
    }

    @Override
    public List<Company> queryDistributor() {
        return companyDao.queryDistributor();
    }

    @Override
    public List<Company> queryDistributorByCompanyId(Integer companyId) {
        return companyDao.queryDistributorByCompanyId(companyId);
    }

    @Override
    public Company getCompanyNameById(Integer companyId) {
        return companyDao.getCompanyNameById(companyId);
    }

    @Override
    public List<Company> queryDealerByPid(Integer pid) {
        return companyDao.queryDealerByPid(pid);
    }

    @Override
    public List<Company> getCompanyByIds(Set<Integer> companyIds) {
        if (companyIds == null || companyIds.isEmpty()) {
            return Collections.emptyList();
        }
        return companyDao.getCompanyByIds(companyIds);
    }

    @Override
    public int saveOrUpdateCompanyRichContext(RichContext context) {
        if(companyDao.getRichContext(context) != null) {
            return companyDao.updateRichContext(context);
        }else{
            return companyDao.saveNewRichContext(context);
        }
    }

    @Override
    public RichContext getRichContext(RichContext context) {
        return companyDao.getRichContext(context);
    }

    @Override
    public CompanyMiniProgramConfig getCompanyMiniConfigByCompanyId(Integer companyId) {
        return companyDao.getCompanyMiniProgramConfigByCompanyId(companyId);
    }
}
