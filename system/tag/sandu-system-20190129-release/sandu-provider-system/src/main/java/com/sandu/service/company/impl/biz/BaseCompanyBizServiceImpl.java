package com.sandu.service.company.impl.biz;

import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.brand.input.CompanyBrandQuery;
import com.sandu.api.brand.output.CompanyBrandVO;
import com.sandu.api.brand.service.BaseBrandService;
import com.sandu.api.category.service.ProCategoryService;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.input.FranchiserInfo;
import com.sandu.api.company.input.FranchiserListQuery;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.CompanyCategoryRel;
import com.sandu.api.company.output.*;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.company.service.CompanyCategoryRelService;
import com.sandu.api.company.service.biz.BaseCompanyBizService;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import com.sandu.api.dictionary.output.FranchiserCategoryListVO;
import com.sandu.api.pic.model.ResPic;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.api.shop.service.CompanyShopService;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.user.service.SysUserService;
import com.sandu.api.user.service.manage.SysUserManageService;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.commons.constant.BusinessTypeConstant;
import com.sandu.commons.util.StringUtils;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author chenqiang
 * @Description 企业 逻辑 业务层
 * @Date 2018/6/1 0001 10:15
 * @Modified By
 */
@Slf4j
@Service("baseCompanyBizService")
public class BaseCompanyBizServiceImpl implements BaseCompanyBizService {

    private Logger logger = LoggerFactory.getLogger(BaseCompanyBizServiceImpl.class);

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Resource
    private SysUserManageService sysUserManageService;

    @Autowired
    private ProCategoryService proCategoryService;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private ResPicService resPicService;

    @Autowired
    private BaseBrandService baseBrandService;

    @Autowired
    private CompanyCategoryRelService companyCategoryRelService;

    @Autowired
    private CompanyShopService companyShopService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据企业id 查询经销商列表
     *
     * @param companyId 企业id
     * @return CompanyFranchiserVO 列表
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public List<CompanyFranchiserVO> getFranchiserListByCompany(Long companyId) {

        //1.获取企业经销商列表
        List<CompanyFranchiserVO> franchiserList = baseCompanyService.getFranchiserListByCompany(companyId.intValue(), BusinessTypeConstant.BUSINESSTYPE_FRANCHISER);

        return franchiserList;
    }

    /**
     * 根据企业id 查询经销商列表
     */
    @Override
    public List<CompanyFranchiserVO> getFranchiserList(Long companyId) {
        //1.判断
        if (null == companyId)
            return null;

        //2.获取企业经销商列表
        List<CompanyFranchiserVO> franchiserList = baseCompanyService.getFranchiserListByCompany(companyId.intValue(), BusinessTypeConstant.BUSINESSTYPE_FRANCHISER);

        return franchiserList;
    }

    /**
     * 根据企业id 查询企业编辑详细信息
     *
     * @param companyId 企业id
     * @return BaseCompanyDetailsVO 对象
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public BaseCompanyDetailsVO getCompanyInfoDetails(Long companyId) {

        //1.获取当前企业基础info信息
        BaseCompanyDetailsVO baseCompanyInfo = baseCompanyService.getCompanyInfo(companyId.intValue());


        //2.获取当前企业可见产品范围名称
        String productVisibilityRange = baseCompanyInfo.getProductVisibilityRange();
        List<Integer> rangeList = StringUtil.getListByString(productVisibilityRange);
        if (null != rangeList) {
            String categoryNames = proCategoryService.getCategoryNames(rangeList);
            baseCompanyInfo.setProductVisibilityRangeName(categoryNames);
        }

        //3.获取当前企业 行政区域 名称
        List<String> codeList = new ArrayList<>();
        codeList.add(baseCompanyInfo.getProvinceCode());
        codeList.add(baseCompanyInfo.getCityCode());
        codeList.add(baseCompanyInfo.getAreaCode());
        codeList.add(baseCompanyInfo.getStreetCode());
        String areaNames = baseAreaService.getAreaNames(codeList);
        if (StringUtils.isNotEmpty(areaNames)) {
            int i = 0;
            for (String s : areaNames.split(",")) {
                switch (i) {
                    case 0:
                        baseCompanyInfo.setProvinceCodeName(s);
                        break;
                    case 1:
                        baseCompanyInfo.setCityCodeName(s);
                        break;
                    case 2:
                        baseCompanyInfo.setAreaCodeName(s);
                        break;
                    case 3:
                        baseCompanyInfo.setStreetCodeName(s);
                        break;
                }
                i++;
            }
        }

        //4.获取企业logo图片路径
        if (null != baseCompanyInfo.getCompanyLogo()) {
            ResPic resPic = resPicService.selectByPrimaryKey(new Long(baseCompanyInfo.getCompanyLogo()));
            baseCompanyInfo.setCompanyLogoPath(null != resPic ? resPic.getPicPath() : "");
        }

        //5.获取企业品牌
        String brandNames = baseBrandService.getBrandnamesByCompanyId(baseCompanyInfo.getId());
        baseCompanyInfo.setBrandName(brandNames);

        /**获取企业所属行业名称 add by wanghl**/
        //6.获取企业所属行业名称

        String industryNames = this.getIndustryNames(baseCompanyInfo.getCompanyIndustrys());
        if (null != industryNames)
            baseCompanyInfo.setCompanyIndustryNames(industryNames);
        /**add by wanghl end**/
        return baseCompanyInfo;
    }


    /**
     * 根据企业与查询信息 查询经销商列表
     *
     * @param query FranchiserListQuery 对象
     * @return FranchiserListVO 列表
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    public List<FranchiserListVO> getFranchiserListDetails(FranchiserListQuery query) {

        //1.参数处理
        if (StringUtils.isEmpty(query.getOrders()))
            query.setOrders("gmt_create DESC");
        query.setStart((query.getStart() - 1) * query.getLimit());

        //2.获取基础数据
        List<FranchiserListVO> franchiserListVOList = baseCompanyService.getFranchiserList(query);

        //3.数据处理
        for (FranchiserListVO franchiserListVO : franchiserListVOList) {

            //获取当前经销商企业 行政区域 名称
            List<String> codeList = new ArrayList<>();
            codeList.add(franchiserListVO.getProvinceCode());
            codeList.add(franchiserListVO.getCityCode());
            codeList.add(franchiserListVO.getAreaCode());
            codeList.add(franchiserListVO.getStreetCode());
            String areaNames = baseAreaService.getAreaNames(codeList);
            franchiserListVO.setAreaNames(StringUtils.isNotEmpty(areaNames) ? areaNames.replace(",", "") : "");

            //获取当前经销商企业 行政区域 名称
            String brandsIds = franchiserListVO.getBrandIds();
            List<Integer> idList = StringUtil.getListByString(brandsIds);
            if (null != idList)
                franchiserListVO.setBrandName(baseBrandService.getBrandNamesByIds(idList));
        }

        return franchiserListVOList;
    }


    /**
     * 根据企业id 查询经销商企业 编辑详细信息
     *
     * @param franchiserInfo 入参
     * @return FranchiserDetailsVO 对象
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    public FranchiserDetailsVO getFranchiserInfoDetails(FranchiserInfo franchiserInfo) {

        //1.获取经销商企业基本信息
        FranchiserDetailsVO franchiserDetailsVO = baseCompanyService.getFranchiserInfo(franchiserInfo.getId());

        //2.获取当前企业可见产品范围名称
        String productVisibilityRange = franchiserDetailsVO.getProductVisibilityRange();
        List<Integer> rangeList = StringUtil.getListByString(productVisibilityRange);
        if (null != rangeList) {
            franchiserDetailsVO.setProductVisibilityRangeNameList(proCategoryService.getListByIdList(rangeList));
            //二级id
            List<Integer> idListTwo = proCategoryService.getPCategoryListByIdList(rangeList);
            if (null != idListTwo)
                franchiserDetailsVO.setProductVisibilityRangeNameListP(proCategoryService.getListByIdList(idListTwo));
        }

        //3.获取当前企业 行政区域 名称
        List<String> codeList = new ArrayList<>();
        codeList.add(franchiserDetailsVO.getProvinceCode());
        codeList.add(franchiserDetailsVO.getCityCode());
        codeList.add(franchiserDetailsVO.getAreaCode());
        codeList.add(franchiserDetailsVO.getStreetCode());
        String areaNames = baseAreaService.getAreaNames(codeList);
        if (StringUtils.isNotEmpty(areaNames)) {
            int i = 0;
            for (String s : areaNames.split(",")) {
                switch (i) {
                    case 0:
                        franchiserDetailsVO.setProvinceCodeName(s);
                        break;
                    case 1:
                        franchiserDetailsVO.setCityCodeName(s);
                        break;
                    case 2:
                        franchiserDetailsVO.setAreaCodeName(s);
                        break;
                    case 3:
                        franchiserDetailsVO.setStreetCodeName(s);
                        break;
                }
                i++;
            }
        }

        //4.获取经销商品牌
        List<Integer> idList = StringUtil.getListByString(franchiserDetailsVO.getBrandIds());
        if (null != idList)
            franchiserDetailsVO.setBrandList(baseBrandService.getBrandList(idList));

        /**获取企业所属行业 add by wanghl**/
        //5.获取经销商企业所属行业名称
        String industryNames = this.getIndustryNames(franchiserDetailsVO.getCompanyIndustrys());
        if (null != industryNames)
            franchiserDetailsVO.setCompanyIndustryNames(industryNames);
        /**add by wanghl end**/

        return franchiserDetailsVO;
    }

    /**
     * 获取企业所属行业名(行业名以逗号隔开)
     *
     * @param industryValues 所属行业在数据字典中的values
     * @return java.lang.String 行业名
     * @author : WangHaiLin
     * @date 2018/7/24 16:50
     */
    private String getIndustryNames(String industryValues) {
        List<Integer> integerList = StringUtil.getListByString(industryValues);
        StringBuilder sb = new StringBuilder();
        if (null != integerList) {
            List<DictionaryTypeListVO> industrys = dictionaryService.getListByTypeOrValues("industry", integerList);
            if (null != industrys) {
                for (DictionaryTypeListVO dictionary : industrys) {
                    sb.append(dictionary.getName());
                    sb.append(",");
                }
            }
            String names = sb.toString();
            String substring = names.substring(0, names.length() - 1);
            return substring;
        }
        return null;
    }


    /**
     * 根据企业id 新增 企业loggo 图片
     *
     * @param map       入参
     * @param loginUser 登录用户
     * @return resPic 主键id
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    public Integer uploadPicSaveInfo(Map<String, String> map, LoginUser loginUser, Integer companyId) {

        //1.新增resPic图片信息表
        Integer id = resPicService.saveUploadImgPic(map, companyId, loginUser, "企业loggo图片");

        if (id <= 0)
            return 0;

        //2.修改企业表
        BaseCompany baseCompany = new BaseCompany();
        baseCompany.setId(new Long(companyId));
        baseCompany.setCompanyLogo(id);

        baseCompanyService.updateByPrimaryKeySelective(baseCompany);

        return id;
    }

    /**
     * 删除企业 logo 图片
     *
     * @param picIds    入参
     * @param loginUser 登录用户
     * @return 影响行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    public Integer deleteCompanyLogo(String picIds, LoginUser loginUser, Integer companyId) {
        int count = 0;

        //1.删除图片
        boolean bool = resPicService.deletePic(picIds, loginUser);

        if (bool) {

            //2.修改企业id
            BaseCompany baseCompany = new BaseCompany();
            baseCompany.setId(new Long(companyId));
            baseCompanyService.saveSystemInfo(baseCompany, loginUser);
            count = baseCompanyService.deleteCompanyLogo(baseCompany);

        }

        return count;
    }

    /**
     * 修改厂商
     *
     * @param baseCompany 入参
     * @param loginUser   登录用户
     * @param logoBool    是否删除logo:true:删除
     * @return 是否成功修改
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    public boolean updateComPanyDetails(BaseCompany baseCompany, LoginUser loginUser, boolean logoBool) {
        boolean bool = true;

        try {

            //1.获取之前的企业信息
            BaseCompany bc = baseCompanyService.selectByPrimaryKey(baseCompany.getId());
            if (bc != null) {
                if (StringUtils.isEmpty(bc.getProductVisibilityRange())) {
                    bc.setProductVisibilityRange("");
                }

                /** 处理CompanyCategoryRel表数据 */
                //1.判断企业是否做出了修改 有（修改企业经销商可见产品范围）
                if (!bc.getProductVisibilityRange().equals(baseCompany.getProductVisibilityRange())) {
                    //2.物理删除企业产品分类表数据
                    companyCategoryRelService.delCompanyCategoryRelByCompanyId(baseCompany.getId().intValue());

                    //3.获取全部 产品分类id 集合 并新增CompanyCategoryRel数据
                    if (StringUtils.isNotEmpty(baseCompany.getProductVisibilityRange())) {
                        String[] categoryIds = baseCompany.getProductVisibilityRange().split(",");
                        List<Integer> idListRoom = new ArrayList<>();
                        for (String categoryId : categoryIds) {
                            if (StringUtils.isNotEmpty(categoryId))
                                idListRoom.add(Integer.parseInt(categoryId));
                        }
                        List<Integer> idListTwo = proCategoryService.getPCategoryListByIdList(idListRoom);
                        List<Integer> idListOne = proCategoryService.getPCategoryListByIdList(idListTwo);
                        idListRoom.addAll(idListTwo);
                        idListRoom.addAll(idListOne);

                        //2.1批量新增企业产品分类表数据
                        List<CompanyCategoryRel> companyCategoryRelList = new ArrayList<>();
                        for (Integer categoryId : idListRoom) {
                            CompanyCategoryRel companyCategoryRel = new CompanyCategoryRel();
                            companyCategoryRel.setCategoryId(categoryId);
                            companyCategoryRel.setCompanyId(baseCompany.getId().intValue());
                            companyCategoryRel.setGmtCreate(new Date());
                            companyCategoryRel.setCreator(loginUser.getLoginName());
                            companyCategoryRel.setIsDeleted(0);
                            companyCategoryRel.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                            companyCategoryRel.setGmtModified(new Date());
                            companyCategoryRel.setModifier(loginUser.getLoginName());
                            companyCategoryRelList.add(companyCategoryRel);
                        }
                        if (companyCategoryRelList != null && companyCategoryRelList.size() > 0)
                            companyCategoryRelService.addListCompanyCategoryRel(companyCategoryRelList);
                    }
                }

                /**处理经销商所属行业 add by wanghl**/
                logger.info("处理经销商所属行业start");
                if (bc.getBusinessType() == 1) {
                    //修改前厂商企业所属行业不为空(若修改前为空，则说明修改前厂商所属经销商所属行业也为空，不需要做过多处理)
                    if (StringUtils.isNotBlank(bc.getCompanyIndustrys())) {
                        //修改前后，厂商所属行业有变化
                        if (!bc.getCompanyIndustrys().equals(baseCompany.getCompanyIndustrys())) {
                            logger.info("修改前后厂商所属行业有变化");
                            logger.info("修改前厂商所属行业:{}", bc.getCompanyIndustrys());
                            logger.info("将要修改的厂商所属行业:{}", baseCompany.getCompanyIndustrys());
                            //获取企业所有经销商
                            List<BaseCompany> baseCompanyList = baseCompanyService.getFranchiserListByCompanyId(baseCompany.getId().intValue());
                            if (null != baseCompanyList) {
                                List<Long> franchiserIds = new ArrayList<>();//经销商Id集合
                                String newFranchiserIndustrys = null;//经销商新所属行业
                                for (BaseCompany franchiserCompany : baseCompanyList) {
                                    //判断厂商将要修改的值不为空
                                    if (StringUtils.isNotBlank(baseCompany.getCompanyIndustrys())) {
                                        //判断 经销商原来是否存有所属行业(若是存有，则需要移除厂商所属行业变化而减少的所属行业值)
                                        if (StringUtils.isNotBlank(franchiserCompany.getCompanyIndustrys())) {
                                            StringBuilder sbIndustrys = new StringBuilder();

                                            String oldIndustrys = bc.getCompanyIndustrys();//厂商原来所属行业值
                                            String newIndustrys = baseCompany.getCompanyIndustrys();//厂商将要修改的所属行业值
                                            String franchiserIndustrys = franchiserCompany.getCompanyIndustrys();//经销商原有所属行业
                                            List<Integer> oldList = StringUtil.getListByString(oldIndustrys);//厂商原来所属行业value集合
                                            List<Integer> newList = StringUtil.getListByString(newIndustrys);//厂商将要修改的所属行业value集合
                                            List<Integer> franchiserList = StringUtil.getListByString(franchiserIndustrys);//经销商原有所属行业value集合
                                            //厂商原来所属行业中有，但是将改变的值中没有，并且厂商所属经销商的所属行业中也存在的值，将被从经销商所属行业中移除
                                            logger.info("经销商原所属行业：{}", franchiserList);
                                            if (null != oldList) {
                                                //循环厂商原所属行业value值
                                                for (Integer value : oldList) {
                                                    //存在于厂商旧范围中，而不存在于厂商新范围里的值
                                                    if (!newList.contains(value)) {
                                                        if (null != franchiserList && franchiserList.contains(value)) {//如果存在于经销商的所属行业中，则移除
                                                            franchiserList.remove(value);
                                                            logger.info("从经销商移除旧值成功,旧值：{}", value);
                                                        }
                                                    }
                                                }
                                            }
                                            if (null != franchiserList && franchiserList.size() > 0) {
                                                for (Integer franchiserValue : franchiserList) {//将经销商剩余行业重新拼接成字符串
                                                    sbIndustrys.append(franchiserValue);
                                                    sbIndustrys.append(",");
                                                }
                                                String Industrys = sbIndustrys.toString();
                                                newFranchiserIndustrys = Industrys.substring(0, Industrys.length() - 1);//取出最后一个逗号
                                            }
//                                            franchiserIds.add(franchiserCompany.getId());
                                        }
                                    } else {//厂商的所属行业将被改成空；则厂商所属的经销商所属行业将都被置空
                                        newFranchiserIndustrys = null;
//                                        franchiserIds.add(franchiserCompany.getId());
                                    }
                                    franchiserIds.add(franchiserCompany.getId());
                                }
                                newFranchiserIndustrys = baseCompany.getCompanyIndustrys();
                                if (franchiserIds != null && !franchiserIds.isEmpty()) {
                                    Integer integer = baseCompanyService.updateCompanyIndustryById(newFranchiserIndustrys, franchiserIds, loginUser.getLoginName());
                                    logger.info("经销商所属行业修改结果：{}", integer);
                                }
                            }
                        }
                    }
                }
                logger.info("处理经销商所属行业end");

                /** 处理企业经销商数据 */
                //1.判断企业是否做出了修改 有（修改企业经销商可见产品范围）
                if (!bc.getProductVisibilityRange().equals(baseCompany.getProductVisibilityRange())) {

                    //2.处理企业可见产品范围
                    StringBuffer sbBaseCompany = null;
                    if (StringUtils.isNotEmpty(baseCompany.getProductVisibilityRange()))
                        sbBaseCompany = new StringBuffer(baseCompany.getProductVisibilityRange()).append(",");

                    //3.有变动，修改所属经销商：获取当前企业所属经销商列表
                    List<BaseCompany> baseCompanyList = baseCompanyService.getFranchiserListByCompanyId(baseCompany.getId().intValue());
                    for (BaseCompany baseCompanyF : baseCompanyList) {

                        StringBuffer cateStr = new StringBuffer();

                        //4.判断 经销商与企业 是否存在可见产品范围
                        if (StringUtils.isNotEmpty(baseCompanyF.getProductVisibilityRange())
                                && StringUtils.isNotEmpty(baseCompany.getProductVisibilityRange())) {

                            List<String> categoryIdListF = StringUtils.isNotEmpty(baseCompanyF.getProductVisibilityRange()) ? Arrays.asList(baseCompanyF.getProductVisibilityRange().split(",")) : new ArrayList<String>();

                            if (null != categoryIdListF && categoryIdListF.size() > 0) {
                                for (String s : categoryIdListF) {

                                    //5.判断企业可见产品范围是否包含改经销商可见产品范围
                                    if (StringUtils.isNotEmpty(s)) {
                                        if (sbBaseCompany.toString().contains(s + ",")) {
                                            //6.包含：保留
                                            cateStr.append(s + ",");
                                        }
                                    }

                                }

                            }

                        }

//                        baseCompanyF.setProductVisibilityRange(StringUtils.isNotEmpty(cateStr.toString()) ? cateStr.toString().substring(0, cateStr.toString().length() - 1) : "");
                        baseCompanyF.setProductVisibilityRange(baseCompany.getProductVisibilityRange());
                        baseCompanyF.setCategoryIds(baseCompanyF.getProductVisibilityRange());
                        baseCompanyService.updateByPrimaryKeySelective(baseCompanyF);

                        // add by xiaoxc_2018_06_29 有变更则修改对应经销商店铺分类
                        Boolean falg = companyShopService.disposeShopCategory(baseCompanyF.getId(), baseCompanyF.getProductVisibilityRange());
                        if (!falg) {
                            return false;
                        }
                    }
                    // add by xiaoxc_2018_06_29 有变更则修改对应厂商店铺分类
                    Boolean falg = companyShopService.disposeShopCategory(baseCompany.getId(), baseCompany.getProductVisibilityRange());
                    if (!falg) {
                        return false;
                    }
                }
            }


            /** 修改厂商数据 */
            baseCompanyService.saveSystemInfo(baseCompany, loginUser);
            baseCompanyService.updateByPrimaryKeySelective(baseCompany);
            //修改厂商所属行业
            if (null != baseCompany.getCompanyIndustrys()) {
                List<Long> list = new ArrayList<>();
                list.add(baseCompany.getId());
                baseCompanyService.updateCompanyIndustryById(baseCompany.getCompanyIndustrys(), list, loginUser.getLoginName());

            }

            /** 修改厂商logo */
            if (logoBool) {
                boolean boolPic = true;
                BaseCompany logCompany = baseCompanyService.selectByPrimaryKey(baseCompany.getId());
                if (null != logCompany.getCompanyLogo())
                    boolPic = resPicService.deletePic(logCompany.getCompanyLogo().toString(), loginUser);

                if (boolPic) {
                    log.info("updateComPanyDetails 方法 修改厂商 boolPic=" + boolPic);
                    BaseCompany baseCompanyLogo = new BaseCompany();
                    baseCompanyLogo.setId(baseCompany.getId());
                    baseCompanyService.saveSystemInfo(baseCompany, loginUser);
                    int count = baseCompanyService.deleteCompanyLogo(baseCompany);
                    if (count <= 0)
                        boolPic = false;
                    log.info("updateComPanyDetails 方法 修改厂商 updatecount=" + count);
                }

                if (!boolPic) {
                    log.error("updateComPanyDetails 方法 修改厂商logo 失败");
                    return false;
                }
            } else {
                //回填businessId
                ResPic resPic = new ResPic();
                resPic.setId(new Long(baseCompany.getCompanyLogo()));
                resPic.setBusinessId(baseCompany.getId().intValue());
                resPicService.updateByPrimaryKeySelective(resPic);
            }


        } catch (Exception e) {
            bool = false;
            log.error("updateComPanyDetails 方法 修改厂商 出错", e);
        }

        return bool;
    }

    /**
     * 修改厂商
     *
     * @param baseCompany 入参
     * @param loginUser   登录用户
     * @param logoBool    是否删除logo:true:删除
     * @return 是否成功修改
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    public boolean updateComPanyNonDetails(BaseCompany baseCompany, LoginUser loginUser, boolean logoBool) {
        boolean bool = true;

        try {

            /** 修改厂商数据 */
            baseCompanyService.saveSystemInfo(baseCompany, loginUser);
            baseCompanyService.updateByPrimaryKeySelective(baseCompany);


            /** 修改厂商logo */
            if (logoBool) {
                boolean boolPic = true;
                BaseCompany logCompany = baseCompanyService.selectByPrimaryKey(baseCompany.getId());
                if (null != logCompany.getCompanyLogo())
                    boolPic = resPicService.deletePic(logCompany.getCompanyLogo().toString(), loginUser);

                if (boolPic) {
                    log.info("updateComPanyDetails 方法 修改非厂商 boolPic=" + boolPic);
                    BaseCompany baseCompanyLogo = new BaseCompany();
                    baseCompanyLogo.setId(baseCompany.getId());
                    baseCompanyService.saveSystemInfo(baseCompany, loginUser);
                    int count = baseCompanyService.deleteCompanyLogo(baseCompany);
                    if (count <= 0)
                        boolPic = false;
                    log.info("updateComPanyDetails 方法 修改非厂商 updatecount=" + count);
                }

                if (!boolPic) {
                    log.error("updateComPanyDetails 方法 修改非厂商logo 失败");
                    return false;
                }
            } else {
                //回填businessId
                ResPic resPic = new ResPic();
                resPic.setId(new Long(baseCompany.getCompanyLogo()));
                resPic.setBusinessId(baseCompany.getId().intValue());
                resPicService.updateByPrimaryKeySelective(resPic);
            }


        } catch (Exception e) {
            bool = false;
            log.error("updateComPanyDetails 方法 修改非厂商 出错", e);
        }

        return bool;
    }

    /**
     * 新增经销商 详情
     *
     * @param baseCompany 对象
     * @param loginUser   登录用户
     * @return 是否成功新增
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    public int addFranchiserDetails(BaseCompany baseCompany, LoginUser loginUser) {

        //1.获取最大code
        String franchiserCode = "";
        try {

            franchiserCode = createCompanyCode(baseCompany.getPid(), "2");

        } catch (Exception e) {
            log.error("创建经销商code出错", e);
            return 0;
        }

        //2.填充数据
        baseCompany.setCompanyCode(franchiserCode);
        baseCompany.setBusinessType(BusinessTypeConstant.BUSINESSTYPE_FRANCHISER);
        baseCompanyService.saveSystemInfo(baseCompany, loginUser);

        //4.新增经销商企业
        int id = baseCompanyService.insertSelective(baseCompany);

        if (id > 0) {
            //检验用户是否存在虚拟企业
            sysUserService.checkUserIsExistVirtualCompany(loginUser.getId());
        }

        //5.返回
        return id;

    }


    /**
     * 创建企业/经销商code
     *
     * @param companyId 厂商/企业id
     * @param type      1：企业、2：经销商
     * @return
     * @author chenqiang
     * @date 2018/6/11 0011 17:34
     */
    private String createCompanyCode(Integer companyId, String type) throws Exception {

        List<Integer> businessTypeList = null;
        List<Integer> businessTypeNotList = null;
        StringBuffer commanyCodePrefix = new StringBuffer();//前缀
        StringBuffer commanyCodeSuffix = new StringBuffer();//后缀
        int count = 0;

        /**
         * 生成企业code
         * 生成规则：C   +   7位数序列
         * 生成经销商code
         * 生成规则：厂商code + F + 4位序列
         */

        //1.公司类型参数
        if ("2".equals(type)) {
            businessTypeList = new ArrayList<>();
            businessTypeList.add(BusinessTypeConstant.BUSINESSTYPE_FRANCHISER);
            BaseCompany baseCompany = baseCompanyService.selectByPrimaryKey(new Long(companyId));
            commanyCodePrefix.append(baseCompany.getCompanyCode());
            commanyCodePrefix.append("F");
            count = 4;
        } else {
            businessTypeNotList = new ArrayList<Integer>();
            businessTypeNotList.add(BusinessTypeConstant.BUSINESSTYPE_FRANCHISER);
            commanyCodePrefix.append("C");
            count = 7;
        }

        //获取当前最大序列
        String companyCodeMax = "";
        companyCodeMax = baseCompanyService.getMaxCompanyCode(commanyCodePrefix.toString() + "%", businessTypeList, businessTypeNotList);  /**获取当前前缀的最大序列号**/


        //序列
        Integer commanyCode = 0;
        //获取最大序列
        if (StringUtils.isNotEmpty(companyCodeMax))
            commanyCode = Integer.parseInt(companyCodeMax.substring(commanyCodePrefix.length(), companyCodeMax.length()));
        //增加1
        commanyCode++;
        //赋值后缀
        commanyCodeSuffix.append(commanyCode);
        //循环补齐
        int k = commanyCodeSuffix.length();
        for (int i = 0; i < count - k; i++) {
            commanyCodeSuffix.insert(0, "0");
        }


        return commanyCodePrefix + "" + commanyCodeSuffix;
    }


    /**
     * 经销商默认可见产品范围与品牌与所属行业
     *
     * @param companyId 入参
     * @return 是否成功新增
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    public FranchiserCaBrandVO getFranchiserCaBrandDetails(Integer companyId) {
        FranchiserCaBrandVO franchiserCaBrandVO = new FranchiserCaBrandVO();

        //1.获取企业信息
        BaseCompany baseCompany = baseCompanyService.selectByPrimaryKey(new Long(companyId));

        //2.获取当前企业可见产品范围名称
        String productVisibilityRange = baseCompany.getProductVisibilityRange();
        franchiserCaBrandVO.setProductVisibilityRange(productVisibilityRange);
        List<Integer> rangeList = StringUtil.getListByString(productVisibilityRange);
        if (null != rangeList) {
            franchiserCaBrandVO.setProductVisibilityRangeNameList(proCategoryService.getListByIdList(rangeList));
            //二级id
            List<Integer> idListTwo = proCategoryService.getPCategoryListByIdList(rangeList);
            if (null != idListTwo)
                franchiserCaBrandVO.setProductVisibilityRangeNameListP(proCategoryService.getListByIdList(idListTwo));
        }


        //3.获取经销商品牌集合
        CompanyBrandQuery query = new CompanyBrandQuery();
        query.setCompanyId(companyId);
        List<CompanyBrandVO> brandVOList = baseBrandService.getBrandByCompanyId(query);
        franchiserCaBrandVO.setBrandList(brandVOList);

        //4.获取经销商品牌ids
        StringBuffer brandIds = new StringBuffer();
        for (CompanyBrandVO companyBrandVO : brandVOList) {
            brandIds.append(companyBrandVO.getBrandId() + ",");
        }
        if (brandIds.length() > 0)
            franchiserCaBrandVO.setBrandIds(brandIds.substring(0, brandIds.length() - 1));

        return franchiserCaBrandVO;
    }


    /**
     * wangHl
     * 获取经销商所属行业
     *
     * @param companyPid 经销商所属厂商Id
     * @param companyId  经销商Id
     * @return List 所属行业集合
     */
    @Override
    public List<FranchiserCategoryListVO> getFranchiserCategoryList(Long companyPid, Long companyId) {
        if (null != companyId && null != companyId) {//编辑
            BaseCompany Pcompany = baseCompanyService.selectByPrimaryKey(companyPid);
            logger.info("编辑时：获取企业所属厂商结果：{}", Pcompany);
            if (null != Pcompany && null != Pcompany.getCompanyIndustrys()) {
                String StringIndustry = Pcompany.getCompanyIndustrys();
                List<Integer> industryValues = StringUtil.getListByString(StringIndustry);//厂商所属行业value集合
                List<DictionaryTypeListVO> pIndustrys = dictionaryService.getListByTypeOrValues("industry", industryValues);
                logger.info("编辑时：厂商所属行业查询结果：{}", pIndustrys);
                //如果厂商的行业可见范围为空时,经销商的所属行业为空
                if(null == pIndustrys || pIndustrys.isEmpty()){
                    return Collections.emptyList();
                }

                List<DictionaryTypeListVO> fIndustrys = new ArrayList<>();
                List<Integer> franchiserValues = null;
                BaseCompany baseCompany = baseCompanyService.selectByPrimaryKey(companyId);
                if (null != baseCompany && null != baseCompany.getCompanyIndustrys()) {
                    franchiserValues = StringUtil.getListByString(baseCompany.getCompanyIndustrys());
                    fIndustrys = dictionaryService.getListByTypeOrValues("industry", franchiserValues);
                    logger.info("编辑时：经销商原所属行业查询结果：{}", fIndustrys);
                }

                //检验厂商跟经销商所属行业是否有交集
                List<FranchiserCategoryListVO> list = new ArrayList<>();
                for (DictionaryTypeListVO d:pIndustrys){
                    FranchiserCategoryListVO f = new FranchiserCategoryListVO();
                    f.setName(d.getName());
                    f.setAtt2(d.getAtt2());
                    f.setValue(d.getValue());
                    if(fIndustrys.contains(f.getValue())){
                        f.setIsSelected(1);
                    }else{
                        f.setIsSelected(0);
                    }
                    list.add(f);
                }
                return list;
                /*List<FranchiserCategoryListVO> list = new ArrayList<>();
                 if (null != pIndustrys && null != fIndustrys) {
                    for (DictionaryTypeListVO PDictionary : pIndustrys) {
                        FranchiserCategoryListVO franchiserCategory = new FranchiserCategoryListVO();
                        franchiserCategory.setName(PDictionary.getName());
                        franchiserCategory.setAtt2(PDictionary.getAtt2());
                        franchiserCategory.setValue(PDictionary.getValue());
                        for (DictionaryTypeListVO FDictionary : fIndustrys) {
                            if (PDictionary.getValue() == FDictionary.getValue()) {
                                franchiserCategory.setIsSelected(1);
                                break;
                            }
                        }
                        if (null == franchiserCategory.getIsSelected()) {
                            franchiserCategory.setIsSelected(0);
                        }
                        list.add(franchiserCategory);
                    }

                } else if (null != pIndustrys && null == fIndustrys) {
                    for (DictionaryTypeListVO dictionary : pIndustrys) {
                        FranchiserCategoryListVO franchiserCategory = new FranchiserCategoryListVO();
                        franchiserCategory.setName(dictionary.getName());
                        franchiserCategory.setAtt2(dictionary.getAtt2());
                        franchiserCategory.setValue(dictionary.getValue());
                        franchiserCategory.setIsSelected(0);
                        list.add(franchiserCategory);
                    }
                }*/
                //return list;
            }
            return null;
        } else if (null != companyPid && null == companyId) {//新增
            BaseCompany Pcompany = baseCompanyService.selectByPrimaryKey(companyPid);
            logger.info("新增时时：获取企业所属厂商结果：{}", Pcompany);
            if (null != Pcompany && null != Pcompany.getCompanyIndustrys()) {
                String StringIndustry = Pcompany.getCompanyIndustrys();
                if (org.apache.commons.lang3.StringUtils.isEmpty(StringIndustry)){
                    return Collections.emptyList();
                }
                List<Integer> industryValues = StringUtil.getListByString(StringIndustry);//厂商所属行业value集合
                List<DictionaryTypeListVO> pIndustrys = dictionaryService.getListByTypeOrValues("industry", industryValues);
                logger.info("编辑时：厂商所属行业查询结果：{}", pIndustrys);
                List<FranchiserCategoryListVO> list = new ArrayList<>();
                for (DictionaryTypeListVO dictionary : pIndustrys) {
                    FranchiserCategoryListVO franchiserCategory = new FranchiserCategoryListVO();
                    franchiserCategory.setName(dictionary.getName());
                    franchiserCategory.setAtt2(dictionary.getAtt2());
                    franchiserCategory.setValue(dictionary.getValue());
                    franchiserCategory.setIsSelected(1);
                    list.add(franchiserCategory);
                }
                return list;
            }
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }
}
