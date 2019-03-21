package com.sandu.api.company.service.biz;

import com.sandu.api.brand.output.FranchiserBrandVO;
import com.sandu.api.company.input.BaseCompanyUpdate;
import com.sandu.api.company.input.FranchiserAdd;
import com.sandu.api.company.input.FranchiserInfo;
import com.sandu.api.company.input.FranchiserListQuery;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.output.*;
import com.sandu.api.dictionary.output.FranchiserCategoryListVO;
import org.springframework.stereotype.Component;
import com.sandu.commons.LoginUser;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @Author chenqiang
 * @Description 企业 逻辑 业务层
 * @Date 2018/6/1 0001 10:15
 * @Modified By
 */
@Component
public interface BaseCompanyBizService {

    /**
     * 根据企业id 查询经销商列表
     * @author chenqiang
     * @param companyId 企业id
     * @return CompanyFranchiserVO 列表
     * @date 2018/5/31 0031 18:21
     */
    List<CompanyFranchiserVO> getFranchiserListByCompany(Long companyId);

    /**
     * 根据企业id 查询经销商列表
     * @param companyId
     * @return
     */
    List<CompanyFranchiserVO> getFranchiserList(Long companyId);
    
    /**
     * 根据企业id 查询企业编辑详细信息
     * @author chenqiang
     * @param companyId 企业id
     * @return BaseCompanyDetailsVO 对象
     * @date 2018/5/31 0031 18:21
     */
    BaseCompanyDetailsVO getCompanyInfoDetails(Long companyId);


    /**
     * 根据企业与查询信息 查询经销商列表
     * @author chenqiang
     * @param query FranchiserListQuery 对象
     * @return FranchiserListVO 列表
     * @date 2018/5/31 0031 18:21
     */
    List<FranchiserListVO> getFranchiserListDetails(FranchiserListQuery query);


    /**
     * 根据企业id 查询经销商企业 编辑详细信息
     * @author chenqiang
     * @param franchiserInfo 入参
     * @return FranchiserDetailsVO 对象
     * @date 2018/5/31 0031 18:21
     */
    FranchiserDetailsVO getFranchiserInfoDetails(FranchiserInfo franchiserInfo);

    /**
     * 删除企业 logo 图片
     * @author chenqiang
     * @param picIds 入参
     * @param loginUser 登录用户
     * @return 影响行数
     * @date 2018/5/31 0031 18:21
     */
    Integer deleteCompanyLogo(String picIds, LoginUser loginUser,Integer companyId);

    /**
     * 修改厂商
     * @author chenqiang
     * @param baseCompany 入参
     * @param loginUser 登录用户
     * @param logoBool 是否删除logo:true:删除
     * @return 是否成功修改
     * @date 2018/5/31 0031 18:21
     */
    boolean updateComPanyDetails(BaseCompany baseCompany,LoginUser loginUser,boolean logoBool);


    /**
     * 修改非厂商
     * @author chenqiang
     * @param baseCompany 入参
     * @param loginUser 登录用户
     * @param logoBool 是否删除logo:true:删除
     * @return 是否成功修改
     * @date 2018/5/31 0031 18:21
     */
    boolean updateComPanyNonDetails(BaseCompany baseCompany,LoginUser loginUser,boolean logoBool);

    /**
     * 新增经销商 详情
     * @author chenqiang
     * @param baseCompany 对象
     * @param loginUser 登录用户
     * @return id
     * @date 2018/5/31 0031 18:21
     */
    int addFranchiserDetails(BaseCompany baseCompany,LoginUser loginUser);


    /**
     * 经销商默认可见产品范围与品牌
     * @author chenqiang
     * @param companyId 入参
     * @return FranchiserCaBrandVO 对象
     * @date 2018/5/31 0031 18:21
     */
    FranchiserCaBrandVO getFranchiserCaBrandDetails(Integer companyId);


    /**
     * wangHl
     * 获取经销商所属行业
     * @param companyPid 经销商所属厂商Id
     * @param companyId 经销商Id
     * @return List 所属行业集合
     */
    List<FranchiserCategoryListVO> getFranchiserCategoryList(Long companyPid,Long companyId);



}
