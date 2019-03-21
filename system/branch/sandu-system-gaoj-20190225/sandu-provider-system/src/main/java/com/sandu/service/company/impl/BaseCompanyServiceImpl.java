package com.sandu.service.company.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.company.input.FranchiserListQuery;
import com.sandu.api.company.input.InnerCompanyQuery;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.BaseCompanyMiniProgramConfig;
import com.sandu.api.company.model.bo.InnerCompanyBO;
import com.sandu.api.company.output.BaseCompanyDetailsVO;
import com.sandu.api.company.output.CompanyFranchiserVO;
import com.sandu.api.company.output.FranchiserDetailsVO;
import com.sandu.api.company.output.FranchiserListVO;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.user.model.SysUser;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.commons.constant.BusinessTypeConstant;
import com.sandu.service.company.dao.BaseCompanyMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author chenqiang
 * @Description 企业 业务层
 * @Date 2018/6/1 0001 10:15
 * @Modified By
 */

@Slf4j
@Service("baseCompanyService")
public class BaseCompanyServiceImpl implements BaseCompanyService {

    @Autowired
    private BaseCompanyMapper baseCompanyMapper;


    @Override
    public BaseCompany getCompanyById(long id) {
        return baseCompanyMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键id 物理删除企业信息
     *
     * @param id 主键id
     * @return 受影响的行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        return baseCompanyMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据主键id 逻辑删除企业信息
     *
     * @param id        主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public int deleteLogicByPrimaryKey(Long id, String loginName) {
        return baseCompanyMapper.deleteLogicByPrimaryKey(id, loginName);
    }

    /**
     * 根据企业基础实体类 选择性 新增数据
     *
     * @param record 企业基础实体类 对象
     * @return 受影响的行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public int insertSelective(BaseCompany record) {
        int count = baseCompanyMapper.insertSelective(record);
        if (count > 0) {

            return record.getId().intValue();
        } else {
            return 0;
        }
    }

    /**
     * 根据主键id 查询 企业基础信息
     *
     * @param id 企业主键id
     * @return 企业基础实体类
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public BaseCompany selectByPrimaryKey(Long id) {
        return baseCompanyMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键 选择性 修改数据
     *
     * @param record 企业基础实体类 对象
     * @return 受影响的行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public int updateByPrimaryKeySelective(BaseCompany record) {
        return baseCompanyMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据企业id 查询经销商列表
     *
     * @param companyId    企业id
     * @param businessType 企业类型
     * @return CompanyFranchiserVO 列表
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public List<CompanyFranchiserVO> getFranchiserListByCompany(Integer companyId, Integer businessType) {
        List<CompanyFranchiserVO> franchiserVOList = baseCompanyMapper.selectFranchiserByCompanyId(companyId, BusinessTypeConstant.BUSINESSTYPE_FRANCHISER);

        return franchiserVOList != null ? franchiserVOList : new ArrayList<>();
    }


    /**
     * 根据企业id 查询企业编辑信息
     *
     * @param companyId 企业id
     * @return BaseCompanyDetailsVO 对象
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public BaseCompanyDetailsVO getCompanyInfo(Integer companyId) {
        return baseCompanyMapper.selectCompanyInfo(companyId);
    }

    /**
     * 根据企业与查询信息 查询经销商列表
     *
     * @param query FranchiserListQuery 对象
     * @return FranchiserListVO 列表
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public List<FranchiserListVO> getFranchiserList(FranchiserListQuery query) {
        List<FranchiserListVO> franchiserListVOList = baseCompanyMapper.selectFranchiserList(query);

        return franchiserListVOList != null ? franchiserListVOList : new ArrayList<>();
    }

    @Override
    public int getFranchiserListCount(FranchiserListQuery query) {
        return baseCompanyMapper.selectFranchiserListCount(query);
    }


    /**
     * 根据经销商id 查询 经销商企业 编辑信息
     *
     * @param companyId 企业id
     * @return FranchiserDetailsVO 对象
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public FranchiserDetailsVO getFranchiserInfo(Integer companyId) {
        return baseCompanyMapper.selectFranchiserInfo(companyId);
    }

    /**
     * 删除企业loggo
     *
     * @param baseCompany 对象
     * @return 影响行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public int deleteCompanyLogo(BaseCompany baseCompany) {
        return baseCompanyMapper.deleteCompanyLogo(baseCompany);
    }

    /**
     * 自动存储系统字段
     *
     * @param baseCompany 企业
     * @param loginUser   当前登录用户
     */
    @Override
    public void saveSystemInfo(BaseCompany baseCompany, LoginUser loginUser) {
        if (baseCompany != null) {
            //新增
            if (baseCompany.getId() == null) {
                baseCompany.setGmtCreate(new Date());
                baseCompany.setCreator(loginUser.getLoginName());
                baseCompany.setIsDeleted(0);
                if (baseCompany.getSysCode() == null || "".equals(baseCompany.getSysCode())) {
                    baseCompany.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }
            //修改
            baseCompany.setGmtModified(new Date());
            baseCompany.setModifier(loginUser.getLoginName());
        }
    }

    /**
     * 根据企业id 获取经销商企业 列表
     *
     * @param companyId 企业id
     * @return InnerCompanyInput 列表
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public List<BaseCompany> getFranchiserListByCompanyId(Integer companyId) {
        return baseCompanyMapper.selectFranchiserListByCompanyId(companyId);
    }

    /**
     * 查询当前 条件下 最大的code
     *
     * @param commanyCodePrefix   前缀
     * @param businessTypeList    in 集合
     * @param businessTypeNotList not in 集合
     * @return 返回 最大 code
     * @author chenqiang
     * @date 2018/6/11 0011 17:16
     */
    @Override
    public String getMaxCompanyCode(String commanyCodePrefix, List<Integer> businessTypeList, List<Integer> businessTypeNotList) {
        return baseCompanyMapper.selectMaxCompanyCode(commanyCodePrefix, businessTypeList, businessTypeNotList);
    }

    /**
     * 查询当前 条件下 最大的code
     *
     * @param companyName 企业名称
     * @param companyId   企业id
     * @return 返回数量
     * @author chenqiang
     * @date 2018/6/25
     */
    @Override
    public int checkCompanyName(String companyName, Long companyId) {
        return baseCompanyMapper.selectCountByCompanyName(companyName, companyId);
    }


    /**
     * 修改企业所属行业
     *
     * @param industrys 所属行业
     * @param IdList    企业Id
     * @return 操作影响的行数
     */
    @Override
    public Integer updateCompanyIndustryById(String industrys, List<Long> IdList, String loginUserName) {
        return baseCompanyMapper.updateCompanyIndustryById(industrys, IdList, loginUserName);
    }
    @Override
    public List<BaseCompanyMiniProgramConfig> getCompanyMiniProgramConfigs(Long miniProgramCompanyId) {
        return baseCompanyMapper.getCompanyMiniProgramConfigs(miniProgramCompanyId);
    }

    @Override
    public BaseCompanyMiniProgramConfig getBaseCompanyMiniProConfigByAppid(String appId) {
        return baseCompanyMapper.getBaseCompanyMiniProConfigByAppid(appId);
    }
    /**
     * 获取企业下所有的用户
     * @param companyId
     * @return
     */
    @Override
    public List<SysUser> getCompanyUser(Integer companyId) {
        return baseCompanyMapper.getCompanyUser(companyId);
    }

    @Override
    public Integer getMainCompanyShopId(Integer companyId) {
        return baseCompanyMapper.getMainCompanyShopId(companyId);
    }

    @Override
    public BaseCompany getCompanyByDomainUrl(String domainUrl) {
        BaseCompany company = null;
        if (StringUtils.isNotBlank(domainUrl)) {
            if (domainUrl.contains("servicewechat")) {
                //Mini program
                String appId = getCompanyIdFromMiniProgramUrl(domainUrl);
                company = getCompanyByConpanyAppId(appId);
                company.setAppId(appId);
            } else {
                String domainName = domainUrl.substring(domainUrl.indexOf("//") + 2, domainUrl.indexOf("."));
                company = getCompanyByDomainName(domainName);
            }

        }
        return company;
    }

    @Override
    public List<BaseCompany> getList(BaseCompany baseCompany) {
        return baseCompanyMapper.selectList(baseCompany);
    }

    @Override
    public String createCompanyCode(String commanyCodePrefix, List<Integer> businessTypeList, List<Integer> businessTypeNotList) {

        return baseCompanyMapper.createCompanyCode(commanyCodePrefix, businessTypeList, businessTypeNotList);

    }

    @Override
    public PageInfo<InnerCompanyBO> queryInnerCompany(InnerCompanyQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<InnerCompanyBO> innerCompanyBOS = baseCompanyMapper.queryInnerCompany(query);
        return new PageInfo<>(innerCompanyBOS);

    }

    @Override
    public List<BaseCompany> queryDistributor(Long id) {
        return baseCompanyMapper.queryDistributor(id);
    }

    @Override
    public List<BaseCompany> listByIds(Set<Integer> companyIdSet) {
        return baseCompanyMapper.listByIds(companyIdSet);
    }

    @Override
    public Integer deleteByIds(List<Integer> companyIds, String userName) {
        return baseCompanyMapper.deleteByIds(companyIds, userName);
    }


    private String getCompanyIdFromMiniProgramUrl(String referer) {
        if (null != referer && !"".equals(referer) && (
                referer.startsWith("http://servicewechat.com/")
                        || referer.startsWith("https://servicewechat.com/")
        )) {
            //去掉前缀
            referer = referer.substring(referer.indexOf("//servicewechat.com/") + 20, referer.length());
            //去掉后缀
            referer = referer.substring(0, referer.indexOf("/"));
        }

        return referer;
    }

    private BaseCompany getCompanyByConpanyAppId(String conpanyAppId) {
        BaseCompany queryBaseCompany = new BaseCompany();
        queryBaseCompany.setAppId(conpanyAppId);
        queryBaseCompany.setIsDeleted(0);
        List<BaseCompany> baseCompanyList = baseCompanyMapper.selectListByCompanyAPPId(queryBaseCompany);
        BaseCompany baseCompany = null;
        if (baseCompanyList != null && baseCompanyList.size() > 0) {
            baseCompany = baseCompanyList.get(0);
        }
        return baseCompany;
    }

    private BaseCompany getCompanyByDomainName(String companyDomainName) {
        BaseCompany queryBaseCompany = new BaseCompany();
        queryBaseCompany.setCompanyDomainName(companyDomainName);
        queryBaseCompany.setIsDeleted(0);
        List<BaseCompany> baseCompanyList = this.getList(queryBaseCompany);
        BaseCompany baseCompany = null;
        if (baseCompanyList != null && baseCompanyList.size() > 0) {
            baseCompany = baseCompanyList.get(0);
        }
        return baseCompany;
    }

    @Override
    public List<BaseCompany> queryCompanyByType(List<Integer> typeIds) {
        return baseCompanyMapper.queryCompanyByType(typeIds);
    }

    @Override
    public List<BaseCompany> querySetMealCompany() {
        return baseCompanyMapper.querySetMealCompany();
    }

    @Override
    public List<Integer> getCompanyByMobile(String telephone) {
        return baseCompanyMapper.getCompanyByMobile(telephone);
    }
}
