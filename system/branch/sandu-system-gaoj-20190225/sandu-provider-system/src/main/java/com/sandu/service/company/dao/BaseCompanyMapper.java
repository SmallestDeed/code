package com.sandu.service.company.dao;

import com.sandu.api.company.input.FranchiserListQuery;
import com.sandu.api.company.input.InnerCompanyQuery;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.BaseCompanyMiniProgramConfig;
import com.sandu.api.company.model.bo.InnerCompanyBO;
import com.sandu.api.company.output.BaseCompanyDetailsVO;
import com.sandu.api.company.output.CompanyFranchiserVO;
import com.sandu.api.company.output.FranchiserDetailsVO;
import com.sandu.api.company.output.FranchiserListVO;
import com.sandu.api.user.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Author chenqiang
 * @Description 企业 dao 持久层
 * @Date 2018/5/31 0031 18:18
 * @Modified By
 */
@Repository
public interface BaseCompanyMapper {

    /**
     * 根据主键id 物理删除企业信息
     * @author chenqiang
     * @param id 主键id
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键id 逻辑删除企业信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteLogicByPrimaryKey(@Param("id") Long id, @Param("loginName")String loginName);

    /**
     * 根据企业基础实体类 选择性 新增数据
     * @author chenqiang
     * @param record 企业基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int insertSelective(BaseCompany record);

    /**
     * 根据主键id 查询 企业基础信息
     * @author chenqiang
     * @param id 企业主键id
     * @return 企业基础实体类
     * @date 2018/5/31 0031 18:21
     */
    BaseCompany selectByPrimaryKey(Long id);

    /**
     * 根据主键 选择性 修改数据
     * @author chenqiang
     * @param record 企业基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int updateByPrimaryKeySelective(BaseCompany record);

    /**
     * 根据企业id 查询经销商列表
     * @author chenqiang
     * @param companyId 企业id
     * @param businessType 企业类型
     * @return CompanyFranchiserVO 列表
     * @date 2018/5/31 0031 18:21
     */
    List<CompanyFranchiserVO> selectFranchiserByCompanyId(@Param("companyId")Integer companyId,@Param("businessType")Integer businessType);

    /**
     * 根据企业id 查询企业编辑信息
     * @author chenqiang
     * @param companyId 企业id
     * @return BaseCompanyDetailsVO 对象
     * @date 2018/5/31 0031 18:21
     */
    BaseCompanyDetailsVO selectCompanyInfo(@Param("companyId")Integer companyId);

    /**
     * 根据企业与查询信息 查询经销商列表
     * @author chenqiang
     * @param query FranchiserListQuery 对象
     * @return FranchiserListVO 列表
     * @date 2018/5/31 0031 18:21
     */
    List<FranchiserListVO> selectFranchiserList(FranchiserListQuery query);
    int selectFranchiserListCount(FranchiserListQuery query);

    /**
     * 根据经销商id 查询 经销商企业 编辑信息
     * @author chenqiang
     * @param companyId 企业id
     * @return FranchiserDetailsVO 对象
     * @date 2018/5/31 0031 18:21
     */
    FranchiserDetailsVO selectFranchiserInfo(@Param("companyId")Integer companyId);

    /**
     * 删除企业loggo
     * @author chenqiang
     * @param baseCompany 对象
     * @return 影响行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteCompanyLogo(BaseCompany baseCompany);

    /**
     * 根据企业id 获取经销商企业 列表
     * @author chenqiang
     * @param companyId 企业id
     * @return InnerCompanyInput 列表
     * @date 2018/5/31 0031 18:21
     */
    List<BaseCompany> selectFranchiserListByCompanyId(@Param("companyId") Integer companyId);

    /**
     * 查询当前 条件下 最大的code
     * @author chenqiang
     * @param commanyCodePrefix 前缀
     * @param businessTypeList in 集合
     * @param businessTypeNotList not in 集合
     * @return
     * @date 2018/6/11 0011 17:16
     */
    String selectMaxCompanyCode(@Param("commanyCodePrefix") String commanyCodePrefix,
                             @Param("businessTypeList")List<Integer> businessTypeList,@Param("businessTypeNotList")List<Integer> businessTypeNotList);

    /**
     * 查询当前 条件下 最大的code
     * @author chenqiang
     * @param companyName 企业名称
     * @param companyId 企业id
     * @return 返回数量
     * @date 2018/6/25
     */
    int selectCountByCompanyName(@Param("companyName")String companyName,@Param("companyId") Long companyId);

    /**
     * 修改企业所属行业
     * @param industrys 所属行业
     * @param IdList 企业Id
     * @return 操作影响的行数
     */
    Integer updateCompanyIndustryById(@Param("industrys")String industrys,@Param("IdList")List<Long> IdList,
                                      @Param("loginUserName")String loginUserName);

    List<BaseCompanyMiniProgramConfig> getCompanyMiniProgramConfigs(Long miniProgramCompanyId);

    BaseCompanyMiniProgramConfig getBaseCompanyMiniProConfigByAppid(String appId);

    /**
     * 获取企业下所有的用户
     * @param companyId
     * @return
     */
    List<SysUser> getCompanyUser(Integer companyId);

    /**
     * 获取企业下的企业店铺ID
     * @param companyId
     * @return
     */
    Integer getMainCompanyShopId(Integer companyId);

    List<BaseCompany> selectList(BaseCompany baseCompany);

    List<BaseCompany> selectListByCompanyAPPId(BaseCompany queryBaseCompany);

    String createCompanyCode(@Param("commanyCodePrefix") String commanyCodePrefix,
                             @Param("businessTypeList") List<Integer> businessTypeList,
                             @Param("businessTypeNotList") List<Integer> businessTypeNotList);

    List<InnerCompanyBO> queryInnerCompany(InnerCompanyQuery query);

    List<BaseCompany> queryDistributor(Long id);

    /**
     * 根据类型获取企业信息
     * @param typeIds
     * @return
     */
    List<BaseCompany> queryCompanyByType(@Param("typeIds") List<Integer> typeIds);

    List<BaseCompany> listByIds(@Param("companyIdSet") Set<Integer> companyIdSet);

    int deleteByIds(@Param("companyIds") List<Integer> companyIds, @Param("userName") String userName);

    List<BaseCompany> querySetMealCompany();

    List<Integer> getCompanyByMobile(String telephone);
}