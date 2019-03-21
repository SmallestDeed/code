package com.sandu.service.company.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.brand.output.FranchiserBrandVO;
import com.sandu.api.brand.service.BaseBrandService;
import com.sandu.api.category.output.CategoryListVO;
import com.sandu.api.category.service.ProCategoryService;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.input.BaseFranchiserNew;
import com.sandu.api.company.input.BaseFranchiserQuery;
import com.sandu.api.company.input.BaseFranchiserUpdate;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.bo.AdminFranchiserBO;
import com.sandu.api.company.model.bo.ItemVO;
import com.sandu.api.company.output.AdminFranchiserDetailsVO;
import com.sandu.api.company.output.AdminFranchiserVO;
import com.sandu.api.company.service.BaseAdminFranchiserService;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.company.service.biz.BaseCompanyBizService;
import com.sandu.api.dictionary.model.SysDictionary;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.shop.model.CompanyShop;
import com.sandu.api.shop.service.CompanyShopArticleService;
import com.sandu.api.shop.service.CompanyShopDesignPlanService;
import com.sandu.api.shop.service.CompanyShopService;
import com.sandu.api.shop.service.ProjectCaseService;
import com.sandu.api.user.service.SysUserService;
import com.sandu.api.user.service.manage.SysUserManageService;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.commons.Utils;
import com.sandu.commons.constant.BusinessTypeConstant;
import com.sandu.service.brand.dao.BaseBrandMapper;
import com.sandu.service.company.dao.BaseAdminFranchiserMapper;
import com.sandu.service.dictionary.dao.SysDictionaryMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("baseAdminFranchiserService")
public class BaseAdminFranchiserServiceImpl implements BaseAdminFranchiserService {

    @Autowired
    BaseAdminFranchiserMapper baseAdminFranchiserMapper;

    @Autowired
    BaseBrandMapper baseBrandMapper;

    @Autowired
    SysDictionaryMapper sysDictionaryMapper;

    @Autowired
    BaseCompanyService baseCompanyService;

    @Autowired
    BaseAreaService baseAreaService;

    @Autowired
    BaseBrandService baseBrandService;

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    BaseCompanyBizService baseCompanyBizService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    CompanyShopService companyShopService;

    @Autowired
    ProCategoryService proCategoryService;

    @Autowired
    SysUserManageService sysUserManageService;

    @Autowired
    ProjectCaseService projectCaseService;

    @Autowired
    CompanyShopArticleService companyShopArticleService;

    @Autowired
    CompanyShopDesignPlanService companyShopDesignPlanService;

    /**
     * 获取经销商详情
     *
     * @param companyId
     * @return
     */
    @Override
    public ResponseEnvelope<AdminFranchiserDetailsVO> getFranchiser(Long companyId) {
        BaseCompany baseCompany = baseCompanyService.selectByPrimaryKey(companyId);
        if (baseCompany == null || baseCompany.getIsDeleted() == 1) {
            log.error("查询的经销商不存在或被删除, companyId => {}", companyId);
            return new ResponseEnvelope<>(false, "查询的经销商不存在或被删除");
        }

        AdminFranchiserDetailsVO detailsVO = new AdminFranchiserDetailsVO();
        try {
            BeanUtils.copyProperties(detailsVO, baseCompany);
        } catch (Exception e) {
            log.error("获取经销商：BeanUtils.copyProperties 异常", e);
            return new ResponseEnvelope<>(false, "系统错误，请稍后再试！");
        }

        // 品牌
        if (!StringUtils.isEmpty(baseCompany.getBrandId())) {
            AdminFranchiserBO brandBO = new AdminFranchiserBO();
            brandBO.setBrandIds(baseCompany.getBrandId());
            Map<Integer, String> mapCompanyBrand = this.mapCompanyBrand(Lists.newArrayList(brandBO));
            String brandNames = this.getCompanyIndustries(mapCompanyBrand, baseCompany.getBrandId());
            detailsVO.setBrandNames(brandNames);
            detailsVO.setBrandIds(Splitter.on(",").trimResults().omitEmptyStrings().splitToList(baseCompany.getBrandId())
                    .stream().map(Integer::valueOf).collect(Collectors.toList()));
        }

        // 所属行业
        if (!StringUtils.isEmpty(baseCompany.getCompanyIndustrys())) {
            Map<Integer, String> mapCompanyIndustries = this.mapCompanyIndustries();
            detailsVO.setIndustryNames(this.getCompanyIndustries(mapCompanyIndustries, baseCompany.getCompanyIndustrys()));
            detailsVO.setIndustryIds(Splitter.on(",").trimResults().omitEmptyStrings().splitToList(baseCompany.getCompanyIndustrys())
                    .stream().map(Integer::valueOf).collect(Collectors.toList()));
        }

        // 可见范围
        String productVisibilityRange = baseCompany.getProductVisibilityRange();
        if (!StringUtils.isEmpty(productVisibilityRange)) {
            List<Integer> listRange = StringUtil.getListByString(productVisibilityRange);
            if (null != listRange) {
                List<String> listCategory = proCategoryService.getListByIdList(listRange).
                        stream().map(CategoryListVO::getCategoryName).collect(Collectors.toList());
                detailsVO.setProductVisibilityRangeIds(listRange);
                detailsVO.setProductVisibilityRangeNames(Joiner.on(",").skipNulls().join(listCategory));
            }
        }

        // 省市区
        String areaNames = baseAreaService.getAreaNames(Lists.newArrayList(baseCompany.getProvinceCode(), baseCompany.getCityCode(),
                baseCompany.getAreaCode(), baseCompany.getStreetCode()));
        detailsVO.setCompanyArea(StringUtils.isEmpty(areaNames) ? "" : areaNames.replace(",", "-"));

        return new ResponseEnvelope<>(true, detailsVO);
    }

    /**
     * 经销商列表
     *
     * @param query
     * @return
     */
    @Override
    public ResponseEnvelope<AdminFranchiserVO> listFranchiser(BaseFranchiserQuery query) {
        if (!StringUtils.isEmpty(query.getContactTelephone())) {
            List<Integer> companyIds = baseCompanyService.getCompanyByMobile(query.getContactTelephone());
            query.setCompanyIds((companyIds != null && !companyIds.isEmpty())
                    ? companyIds.stream().distinct().collect(Collectors.toList())
                    : null);
        }

        PageHelper.startPage(query.getPage(), query.getLimit());
        List<AdminFranchiserBO> listFranchiser = baseAdminFranchiserMapper.listFranchiser(query);
        if (listFranchiser == null || listFranchiser.isEmpty()) {
            log.info("经销商列表：通过 query => {} 未检索到数据", query);
            return new ResponseEnvelope<>(true, "暂无数据");
        }

        // 所属品牌
        PageInfo<AdminFranchiserBO> pageInfo = new PageInfo<>(listFranchiser);
        Map<Integer, String> mapCompanyBrand = this.mapCompanyBrand(listFranchiser);

        // 所属行业
        Map<Integer, String> mapCompanyIndustries = this.mapCompanyIndustries();

        List<AdminFranchiserVO> listVO = new ArrayList<>();
        for (AdminFranchiserBO itemBO : listFranchiser) {
            AdminFranchiserVO itemVO = new AdminFranchiserVO();
            try {
                BeanUtils.copyProperties(itemVO, itemBO);
            } catch (Exception e) {
                log.error("经销商列表：BeanUtils.copyProperties 异常", e);
                return new ResponseEnvelope<>(false, "系统错误，请稍后再试！");
            }

            // 所属品牌
            itemVO.setBrands(this.getBrands(mapCompanyBrand, itemBO.getBrandIds()));
            // 所属行业
            itemVO.setCompanyIndustries(this.getCompanyIndustries(mapCompanyIndustries, itemBO.getCompanyIndustryIds()));

            listVO.add(itemVO);
        }

        return new ResponseEnvelope<>(true, pageInfo.getTotal(), listVO);
    }

    /**
     * @param list
     * @return
     */
    private Map<Integer, String> mapCompanyBrand(List<AdminFranchiserBO> list) {
        List<List<Integer>> collect = list.stream().filter(item -> !StringUtils.isEmpty(item.getBrandIds()))
                .map(item -> Lists.newArrayList(Splitter.on(",").omitEmptyStrings().trimResults()
                        .splitToList(item.getBrandIds()).stream().map(Integer::valueOf)
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());

        List<Integer> brandIds = new ArrayList<>();
        collect.forEach(brandIds::addAll);

        if (brandIds.isEmpty()) {
            log.warn("经销商列表：经销商的所属品牌为空， list => {}", list);
            return new HashMap<>(0);
        }

        List<FranchiserBrandVO> listBrandVO = baseBrandMapper.selectBrandList(brandIds);
        return listBrandVO.stream().collect(Collectors.toMap(FranchiserBrandVO::getBrandId, FranchiserBrandVO::getBrandName, (p, n) -> n));
    }

    /**
     * @param map
     * @param brandIds
     * @return
     */
    private String getBrands(Map<Integer, String> map, String brandIds) {
        if (StringUtils.isEmpty(brandIds)) {
            return null;
        }

        List<String> brands = new ArrayList<>();
        Splitter.on(",").trimResults().omitEmptyStrings().splitToList(brandIds)
                .stream().filter(Objects::nonNull).forEach(item -> brands.add(map.get(Integer.valueOf(item))));

        return Joiner.on(",").join(brands);
    }

    /**
     * @return
     */
    private Map<Integer, String> mapCompanyIndustries() {
        List<SysDictionary> listIndustry = sysDictionaryMapper.listCompanyIndustry("industry");
        return listIndustry.stream().collect(Collectors.toMap(SysDictionary::getValue, SysDictionary::getName, (p, n) -> n));
    }

    /**
     * @param map
     * @param companyIndustryIds
     * @return
     */
    private String getCompanyIndustries(Map<Integer, String> map, String companyIndustryIds) {
        if (StringUtils.isEmpty(companyIndustryIds)) {
            return null;
        }

        List<String> list = new ArrayList<>();
        Splitter.on(",").trimResults().omitEmptyStrings().splitToList(companyIndustryIds)
                .stream().filter(Objects::nonNull).forEach(item -> list.add(map.get(Integer.valueOf(item))));

        return Joiner.on(",").join(list);
    }

    /**
     * 添加经销商
     *
     * @param franchiserNew
     * @param loginUser
     * @return
     */
    @Override
    @Transactional
    public ResponseEnvelope addFranchiser(BaseFranchiserNew franchiserNew, LoginUser loginUser) {
        // 验证
        ResponseEnvelope<Object> errorResp = this.validFranchiser(null, franchiserNew.getCompanyName(),
                franchiserNew.getProvinceCode(), franchiserNew.getCityCode(), franchiserNew.getAreaCode(), franchiserNew.getStreetCode(),
                franchiserNew.getBrandIds(), franchiserNew.getPid(),
                franchiserNew.getPhone(), franchiserNew.getCompanyIndustrys());
        if (errorResp != null) {
            log.error("添加经销商：未通过验证，errorResp => {}", errorResp);
            return errorResp;
        }

        BaseCompany baseCompany = new BaseCompany();
        try {
            BeanUtils.copyProperties(baseCompany, franchiserNew);
            // 生成编码
            String companyCode = baseCompanyBizService.createCompanyCode(franchiserNew.getPid(), "2");
            baseCompany.setCompanyCode(companyCode);
        } catch (Exception e) {
            log.error("添加经销商：BeanUtils.copyProperties 异常 || 生成companyCode异常", e);
            return new ResponseEnvelope(false, "系统错误，请稍后再试！");
        }

        baseCompany.setBusinessType(BusinessTypeConstant.BUSINESSTYPE_FRANCHISER);
        baseCompany.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        // 品牌
        baseCompany.setBrandId(franchiserNew.getBrandIds());
        // 分类
        baseCompany.setCategoryIds(franchiserNew.getProductVisibilityRange());
        // long_area_code
        List<String> longAreaCode = Lists.newArrayList(franchiserNew.getProvinceCode(), franchiserNew.getCityCode(),
                franchiserNew.getAreaCode(), franchiserNew.getStreetCode()).stream().filter(item -> !StringUtils.isEmpty(item)).collect(Collectors.toList());
        baseCompany.setLongAreaCode(Joiner.on(",").skipNulls().join(longAreaCode));

        // basic fields
        baseCompany.setIsDeleted(0);
        baseCompany.setGmtCreate(new Date());
        baseCompany.setGmtModified(new Date());
        baseCompany.setCreator(loginUser.getLoginName());
        baseCompany.setModifier(loginUser.getLoginName());

        int count = baseCompanyService.insertSelective(baseCompany);
        if (count > 0) {
            // 检验用户是否存在虚拟企业
            sysUserService.checkUserIsExistVirtualCompany(loginUser.getId());
        }

        return new ResponseEnvelope(true, "添加成功");
    }

    /**
     * 验证添加和更新经销商的Model数据
     *
     * @param companyName
     * @param provinceCode
     * @param cityCode
     * @param areaCode
     * @param streetCode
     * @param brandIds
     * @param pid
     * @param phone
     * @param companyIndustries
     * @param <T>
     * @return
     */
    private <T> ResponseEnvelope<T> validFranchiser(Long companyId, String companyName,
                                                    String provinceCode, String cityCode, String areaCode, String streetCode,
                                                    String brandIds, Integer pid,
                                                    String phone, String companyIndustries) {
        // 企业名称唯一校验
        int count = baseCompanyService.checkCompanyName(companyName, companyId);
        if (count > 0) {
            log.error("存在相同的企业名称，companyName => {}", companyName);
            return new ResponseEnvelope<>(false, "存在相同的企业名称");
        }

        // 区域校验
        Map<String, String> map = baseAreaService.checkAreaCode(provinceCode, cityCode, areaCode, streetCode);
        if (map.get("success").equals("false")) {
            log.error("选择的区域不存在，provinceCode => {}, cityCode => {}, areaCode => {}, streetCode => {}",
                    provinceCode, cityCode, areaCode, streetCode);
            return new ResponseEnvelope<>(false, "选择的区域不存在，请重新选择！");
        }

        // 品牌校验
        if (StringUtils.isNotEmpty(brandIds)) {
            boolean bool = baseBrandService.checkBrandIds(brandIds, pid);
            if (!bool) {
                log.error("选择的品牌不存在，brandIds => {}", brandIds);
                return new ResponseEnvelope<>(false, "选择的品牌不存在，请重新选择！");
            }
        }

        // 号码格式校验
        if (StringUtils.isNotEmpty(phone)) {
            boolean bool = StringUtil.checkPhoneRegex(phone, "1");
            if (!bool) {
                log.error("输入的手机号码格式不能正确，phone => {}", phone);
                return new ResponseEnvelope<>(false, "输入的手机号码格式不能正确");
            }
        }

        // 企业所属行业校验
        if (StringUtils.isNotBlank(companyIndustries)) {
            List<Integer> listIndustry = StringUtil.getListByString(companyIndustries);
            List<DictionaryTypeListVO> industries = dictionaryService.getListByTypeOrValues("industry", listIndustry);
            if (listIndustry.size() != industries.size()) {
                log.error("选择的行业不能存在，请重新选择！，companyIndustries => {}", companyIndustries);
                return new ResponseEnvelope<>(false, "选择的行业不能存在，请重新选择！");
            }
        }

        return null;
    }

    /**
     * 更新经销商
     *
     * @param update
     * @param loginUser
     * @return
     */
    @Override
    @Transactional
    public ResponseEnvelope updateFranchiser(BaseFranchiserUpdate update, LoginUser loginUser) {
        BaseCompany oldCompany = baseCompanyService.selectByPrimaryKey(update.getId());
        if (oldCompany == null || oldCompany.getIsDeleted() == 1) {
            log.error("更新经销商：经销商不存在或被删除, update => {}", update);
            return new ResponseEnvelope(false, update.getCompanyName() + "经销商不存在或被删除");
        }

        // 验证数据
        ResponseEnvelope<Object> errorResp = this.validFranchiser(update.getId(), update.getCompanyName(),
                update.getProvinceCode(), update.getCityCode(), update.getAreaCode(), update.getStreetCode(),
                update.getBrandIds(), update.getPid(),
                update.getPhone(), update.getCompanyIndustrys());
        if (errorResp != null) {
            log.error("更新经销商：未通过验证，errorResp => {}", errorResp);
            return errorResp;
        }

        BaseCompany baseCompany = new BaseCompany();
        baseCompany.setId(oldCompany.getId());
        try {
            BeanUtils.copyProperties(baseCompany, update);
        } catch (Exception e) {
            log.error("更新经销商：BeanUtils.copyProperties 异常", e);
            return new ResponseEnvelope();
        }

        // 可见分类处理, 同步店铺的分类
        String oldCategoryIds = StringUtils.isNotEmpty(oldCompany.getCategoryIds()) ? oldCompany.getProductVisibilityRange() : "";
        String newCategoryIds = StringUtils.isNotEmpty(baseCompany.getProductVisibilityRange()) ? baseCompany.getProductVisibilityRange() : "";
        if (!oldCategoryIds.equals(newCategoryIds)) {
            Boolean flag = companyShopService.disposeShopCategory(baseCompany.getId(), newCategoryIds);
            if (!flag) {
                log.error("更新经销商：更新店铺分类异常, update => {}", update);
                return new ResponseEnvelope<BaseCompany>(false, "更新店铺分类异常！");
            }
        }

        baseCompany.setGmtModified(new Date());
        baseCompany.setModifier(loginUser.getLoginName());
        // 所属行业
        baseCompany.setCompanyIndustrys(update.getCompanyIndustrys());
        // 品牌
        baseCompany.setBrandId(update.getBrandIds());
        // 分类
        baseCompany.setCategoryIds(update.getProductVisibilityRange());

        // long_area_code
        List<String> listAreaCode = Lists.newArrayList(update.getProvinceCode(), update.getCityCode(), update.getAreaCode(), update.getStreetCode());
        listAreaCode.removeIf(StringUtils::isEmpty);
        baseCompany.setLongAreaCode(Joiner.on(".").join(listAreaCode));

        baseCompanyService.updateByPrimaryKeySelective(baseCompany);

        return new ResponseEnvelope<BaseCompany>(true, "编辑成功！");
    }

    /**
     * 删除经销商
     *
     * @param companyId
     * @return
     */
    @Override
    @Transactional
    public ResponseEnvelope deleteFranchiser(Long companyId, LoginUser loginUser) {
        BaseCompany baseCompany = baseCompanyService.selectByPrimaryKey(companyId);
        if (baseCompany == null) {
            log.error("删除经销商：删除的经销商不存在， companyId = > {}", companyId);
            return new ResponseEnvelope(false, "删除的经销商不存在！");
        }

        // 删除店铺、博文、工程案例、方案
        List<CompanyShop> listCompanyShop = companyShopService.listShopByCompanyId(companyId);
        if (listCompanyShop != null && !listCompanyShop.isEmpty()) {
            List<Integer> listShopId = listCompanyShop.stream().map(CompanyShop::getId).collect(Collectors.toList());
            // 工程案例
            projectCaseService.deleteCaseByShopId(listShopId, loginUser.getLoginName());
            // 博文
            companyShopArticleService.deleteArticleByShopId(listShopId, loginUser.getLoginName());
            // 方案
            companyShopDesignPlanService.deleteDesignPlanByShopId(listShopId);
            // 删除店铺
            companyShopService.deleteShopById(listShopId, loginUser.getLoginName());

            log.info("删除经销商：删除 companyId => {} 的店铺 listShopId => {} 的博文、工程案例、方案、店铺", companyId, listShopId);
        }

        // 解绑经销商用户
        boolean untyingFlag = sysUserManageService.notBindUserToFranchiser(companyId, loginUser.getLoginName());
        if (!untyingFlag) {
            log.info("删除经销商：解绑经销商用户失败， companyId => {}", companyId);
            return new ResponseEnvelope<>(false, "解绑经销商用户失败!");
        }

        // 删除经销商企业
        baseCompanyService.deleteLogicByPrimaryKey(companyId, loginUser.getLoginName());

        return new ResponseEnvelope<BaseCompany>(true, "删除成功！");
    }

    /**
     * 厂商的行业列表
     *
     * @param pid
     * @return
     */
    @Override
    public ResponseEnvelope<ItemVO> listIndustry(Long pid) {
        BaseCompany baseCompany = baseCompanyService.selectByPrimaryKey(pid);
        if (baseCompany == null || StringUtils.isEmpty(baseCompany.getCompanyIndustrys())) {
            log.error("行业列表：厂商 pid => {} 没有所属行业信息", pid);
            return new ResponseEnvelope<>(false, "厂商没有所属行业信息！");
        }

        List<Integer> list = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(baseCompany.getCompanyIndustrys())
                .stream().map(Integer::valueOf).collect(Collectors.toList());
        if (list.isEmpty()) {
            log.error("行业列表：厂商 pid => {} 没有所属行业信息", pid);
            return new ResponseEnvelope<>(false, "厂商没有所属行业信息！");
        }

        List<DictionaryTypeListVO> industries = dictionaryService.getListByTypeOrValues("industry", list);
        Map<Integer, DictionaryTypeListVO> listVOMap = industries.stream().collect(Collectors.toMap(DictionaryTypeListVO::getValue, item -> item, (p, n) -> n));

        List<ItemVO> listVO = new ArrayList<>();
        list.forEach(item -> listVO.add(ItemVO.builder().id(listVOMap.get(item).getValue().longValue())
                .name(listVOMap.get(item).getName()).build()));

        return new ResponseEnvelope<>(true, listVO);
    }
}
