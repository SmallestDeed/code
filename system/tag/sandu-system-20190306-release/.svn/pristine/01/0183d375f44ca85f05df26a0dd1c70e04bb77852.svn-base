package com.sandu.web.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.sandu.annotation.DuplicateSubmitToken;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.brand.service.BaseBrandService;
import com.sandu.api.category.service.ProCategoryService;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.output.*;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import com.sandu.api.dictionary.output.FranchiserCategoryListVO;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.shop.service.CompanyShopService;
import com.sandu.api.user.service.manage.SysUserManageService;
import com.sandu.commons.util.StringUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sandu.api.company.input.FranchiserAdd;
import com.sandu.api.company.input.FranchiserInfo;
import com.sandu.api.company.input.FranchiserListQuery;
import com.sandu.api.company.input.FranchiserUpdate;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.output.FranchiserCaBrandVO;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.company.service.biz.BaseCompanyBizService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;

/**
 * @Author chenqiang
 * @Description 企业 经销商 控制层
 * @Date 2018/6/1 0001 10:15
 * @Modified By
 */
@Api(tags = "baseFranchiser", description = "企业经销商")
@RestController
@RequestMapping("/v1/base/franchiser")
public class BaseFranchiserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(BaseFranchiserController.class);

    @Autowired
    private BaseCompanyBizService baseCompanyBizService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Resource
    private SysUserManageService sysUserManageService;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private ProCategoryService proCategoryService;

    @Autowired
    private BaseBrandService baseBrandService;

    @Autowired
    private CompanyShopService companyShopService;

    @Autowired
    private DictionaryService dictionaryService;


    @ApiOperation(value = "经销商企业列表", response = FranchiserListVO.class)
    @PostMapping("/list")
    public ResponseEnvelope getFranchiserListDetails(HttpServletRequest request, HttpServletResponse response,
                                                     @Valid @RequestBody FranchiserListQuery franchiserListQuery, BindingResult validResult) {
        try {
            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }
            if (franchiserListQuery.getStart() <= 0)
                return new ResponseEnvelope<>(false, "分页参数有误!");

            //2.获取企业详细数据
            int count = baseCompanyService.getFranchiserListCount(franchiserListQuery);
            List<FranchiserListVO> franchiserListVOList = new ArrayList<>();
            if (count > 0) {
                franchiserListVOList = baseCompanyBizService.getFranchiserListDetails(franchiserListQuery);
            }

            return new ResponseEnvelope<>(true, count, franchiserListVOList);

        } catch (Exception e) {

            logger.error("getFranchiserListDetails 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @DuplicateSubmitToken
    @ApiOperation(value = "新增经销商企业", response = ResponseEnvelope.class)
    @PostMapping
    public ResponseEnvelope addSave(HttpServletRequest request, HttpServletResponse response,
                                    @Valid @RequestBody FranchiserAdd franchiserAdd, BindingResult validResult) {
        try {
            //1.参数校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }

            //2.数据转换
            BaseCompany baseCompany = franchiserAdd.getBaseCompany();

            /** 3.数据校验 */
            //3.1企业名称唯一校验
            int count = baseCompanyService.checkCompanyName(baseCompany.getCompanyName(), null);
            if (count > 0) {
                return new ResponseEnvelope<>(false, "存在相同的企业名称");
            }
            //3.2区域校验
            Map<String, String> map = baseAreaService.checkAreaCode(baseCompany.getProvinceCode(), baseCompany.getCityCode(), baseCompany.getAreaCode(), baseCompany.getStreetCode());
            if (map.get("success").equals("false"))
                return new ResponseEnvelope<>(false, map.get("message"), franchiserAdd.getMsgId());
            //3.3产品分类校验
            //if(StringUtils.isNotEmpty(baseCompany.getProductVisibilityRange())){
            //    boolean bool = proCategoryService.checkCategoryIds(baseCompany.getProductVisibilityRange(),"3");
            //    if(!bool)
            //        return new ResponseEnvelope<>(false, "可见产品范围有误",franchiserAdd.getMsgId());
            //}
            //3.4品牌校验
            if (StringUtils.isNotEmpty(franchiserAdd.getBrandIds())) {
                boolean bool = baseBrandService.checkBrandIds(baseCompany.getBrandId(), baseCompany.getPid());
                if (!bool)
                    return new ResponseEnvelope<>(false, "品牌有误", franchiserAdd.getMsgId());
            }
            //3.4号码格式校验
            if (StringUtils.isNotEmpty(baseCompany.getPhone())) {
                boolean bool = StringUtil.checkPhoneRegex(baseCompany.getPhone(), "1");
                if (!bool)
                    return new ResponseEnvelope<>(false, "号码格式错误");
            }

            //4.获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

            //企业所属行业校验
            if (org.apache.commons.lang3.StringUtils.isNotBlank(franchiserAdd.getCompanyIndustrys())) {
                List<Integer> valueList = StringUtil.getListByString(baseCompany.getCompanyIndustrys());
                List<DictionaryTypeListVO> industrys = dictionaryService.getListByTypeOrValues("industry", valueList);
                if (valueList.size() != industrys.size()) {
                    return new ResponseEnvelope<>(false, "所属行业参数有误");
                }
            }

            //5.新增数据
            int franchiserId = baseCompanyBizService.addFranchiserDetails(baseCompany, loginUser);

            //6.判断返回
            if (franchiserId > 0)
                return new ResponseEnvelope<>(true, franchiserId, franchiserAdd.getMsgId(), "新增经销商企业成功!");
            else
                return new ResponseEnvelope<>(false, franchiserId, franchiserAdd.getMsgId(), "新增经销商企业失败!");

        } catch (Exception e) {

            logger.error("addSave 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!", franchiserAdd.getMsgId());

        }
    }

    @DuplicateSubmitToken
    @ApiOperation(value = "新增经销商企业(u3d专用)", response = ResponseEnvelope.class)
    @PostMapping("/u3d/save")
    public ResponseEnvelope addU3dSave(HttpServletRequest request, HttpServletResponse response,
                                       @Valid @ModelAttribute FranchiserAdd franchiserAdd, BindingResult validResult, String loginName) {
        try {
            //1.参数校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }
            if (StringUtils.isEmpty(loginName))
                return new ResponseEnvelope<>(false, "登录名不能为空!", franchiserAdd.getMsgId());

            //2.数据转换
            BaseCompany baseCompany = franchiserAdd.getBaseCompany();

            /** 3.数据校验 */
            //3.1企业名称唯一校验
            int count = baseCompanyService.checkCompanyName(baseCompany.getCompanyName(), null);
            if (count > 0) {
                return new ResponseEnvelope<>(false, "存在相同的企业名称");
            }
            //3.2区域校验
            Map<String, String> map = baseAreaService.checkAreaCode(baseCompany.getProvinceCode(), baseCompany.getCityCode(), baseCompany.getAreaCode(), baseCompany.getStreetCode());
            if (map.get("success").equals("false"))
                return new ResponseEnvelope<>(false, map.get("message"), franchiserAdd.getMsgId());
            //3.3产品分类校验
//            if(StringUtils.isNotEmpty(baseCompany.getProductVisibilityRange())){
//                boolean bool = proCategoryService.checkCategoryIds(baseCompany.getProductVisibilityRange(),"3");
//                if(!bool)
//                    return new ResponseEnvelope<>(false, "可见产品范围有误",franchiserAdd.getMsgId());
//            }
            //3.4品牌校验
            if (StringUtils.isNotEmpty(franchiserAdd.getBrandIds())) {
                boolean bool = baseBrandService.checkBrandIds(baseCompany.getBrandId(), baseCompany.getPid());
                if (!bool)
                    return new ResponseEnvelope<>(false, "品牌有误", franchiserAdd.getMsgId());
            }
            //4.5号码格式校验
            if (StringUtils.isNotEmpty(baseCompany.getPhone())) {
                boolean bool = StringUtil.checkPhoneRegex(baseCompany.getPhone(), "1");
                if (!bool)
                    return new ResponseEnvelope<>(false, "号码格式错误");
            }

            //4.获取登录用户
            LoginUser loginUser = new LoginUser();
            loginUser.setLoginName(loginName);

            //5.新增数据
            int franchiserId = baseCompanyBizService.addFranchiserDetails(baseCompany, loginUser);

            //6.判断返回
            if (franchiserId > 0)
                return new ResponseEnvelope<>(true, franchiserId, franchiserAdd.getMsgId(), "新增经销商企业成功!");
            else
                return new ResponseEnvelope<>(false, franchiserId, franchiserAdd.getMsgId(), "新增经销商企业失败!");

        } catch (Exception e) {

            logger.error("addSave 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!", franchiserAdd.getMsgId());

        }
    }


    @ApiOperation(value = "编辑经销商企业", response = ResponseEnvelope.class)
    @PutMapping
    public ResponseEnvelope updateSave(HttpServletRequest request, HttpServletResponse response,
                                       @Valid @RequestBody FranchiserUpdate franchiserUpdate, BindingResult validResult) {
        try {
            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }

            //2.数据转换
            BaseCompany baseCompany = franchiserUpdate.getBaseCompany();

            /** 3.数据校验 */
            //3.1企业名称唯一校验
            int count = baseCompanyService.checkCompanyName(baseCompany.getCompanyName(), franchiserUpdate.getId());
            if (count > 0) {
                return new ResponseEnvelope<>(false, "存在相同的企业名称");
            }
            //3.2区域校验
            Map<String, String> map = baseAreaService.checkAreaCode(baseCompany.getProvinceCode(), baseCompany.getCityCode(), baseCompany.getAreaCode(), baseCompany.getStreetCode());
            if (map.get("success").equals("false"))
                return new ResponseEnvelope<>(false, map.get("message"));
            //3.3产品分类校验
            //if(StringUtils.isNotEmpty(baseCompany.getProductVisibilityRange())){
            //    boolean bool = proCategoryService.checkCategoryIds(baseCompany.getProductVisibilityRange(),"3");
            //    if(!bool)
            //        return new ResponseEnvelope<>(false, "可见产品范围有误");
            //}
            //3.4品牌校验
            if (StringUtils.isNotEmpty(baseCompany.getBrandId())) {
                BaseCompany company = baseCompanyService.selectByPrimaryKey(baseCompany.getId());
                boolean bool = baseBrandService.checkBrandIds(baseCompany.getBrandId(), company.getPid());
                if (!bool)
                    return new ResponseEnvelope<>(false, "品牌有误");
            }
            //3.4号码格式校验
            if (StringUtils.isNotEmpty(baseCompany.getPhone())) {
                boolean bool = StringUtil.checkPhoneRegex(baseCompany.getPhone(), "1");
                if (!bool)
                    return new ResponseEnvelope<>(false, "号码格式错误");
            }

            //4.获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

            // add by xiaoxc_2018_06_29 有变更则修改对应经销商店铺分类
            BaseCompany oldCompany = baseCompanyService.getCompanyById(baseCompany.getId());
            String oldCategoryIds = "";
            String newCategoryIds = "";
            if (oldCompany != null && StringUtils.isNotEmpty(oldCompany.getCategoryIds())) {
                oldCategoryIds = oldCompany.getProductVisibilityRange();
            }
            if (StringUtils.isNotEmpty(baseCompany.getProductVisibilityRange())) {
                newCategoryIds = baseCompany.getProductVisibilityRange();
            }
            if (!oldCategoryIds.equals(newCategoryIds)) {
                Boolean falg = companyShopService.disposeShopCategory(baseCompany.getId(), newCategoryIds);
                if (!falg) {
                    return new ResponseEnvelope<BaseCompany>(false, "更新店铺分类异常！");
                }
            }


            //5.修改经销商企业
            baseCompanyService.saveSystemInfo(baseCompany, loginUser);
            int id = baseCompanyService.updateByPrimaryKeySelective(baseCompany);
            List<Long> list = new ArrayList<>();
            list.add(franchiserUpdate.getId());
            Integer result = baseCompanyService.updateCompanyIndustryById(franchiserUpdate.getCompanyIndustrys(), list, loginUser.getLoginName());

            //6.判断返回
            if (id > 0 && result > 0)
                return new ResponseEnvelope<>(true, "修改经销商企业成功!");
            else
                return new ResponseEnvelope<>(false, "修改经销商企业失败!");

        } catch (Exception e) {

            logger.error("updateSave 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @ApiOperation(value = "删除经销商企业", response = ResponseEnvelope.class)
    @DeleteMapping
    public ResponseEnvelope deleteSave(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam("fanchiserId") Integer franchiserId) {
        try {
            //1.数据校验
            if (null == franchiserId)
                return new ResponseEnvelope<>(false, "经销商id不能为空!");

            //2.获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);


            //3.解绑经销商用户
            boolean bool = sysUserManageService.notBindUserToFranchiser(new Long(franchiserId), loginUser.getLoginName());
            if (!bool)
                return new ResponseEnvelope<>(false, "解绑经销商用户失败!");

            //4.删除经销商企业
            int count = baseCompanyService.deleteLogicByPrimaryKey(new Long(franchiserId), loginUser.getLoginName());

            //5.判断返回
            if (count > 0)
                return new ResponseEnvelope<>(true, "删除经销商企业成功!");
            else
                return new ResponseEnvelope<>(false, "删除经销商企业失败!");

        } catch (Exception e) {

            logger.error("deleteSave 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }


    @ApiOperation(value = "经销商企业详情", response = FranchiserDetailsVO.class)
    @GetMapping("/details")
    public ResponseEnvelope getFranchiserInfoDetails(HttpServletRequest request, HttpServletResponse response,
                                                     @Valid FranchiserInfo franchiserInfo, BindingResult validResult) {
        try {
            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }

            //2.获取经销商企业信息
            FranchiserDetailsVO franchiserDetailsVO = baseCompanyBizService.getFranchiserInfoDetails(franchiserInfo);

            return new ResponseEnvelope<>(true, "经销商企业详情获取成功", franchiserDetailsVO);

        } catch (Exception e) {

            logger.error("getFranchiserInfoDetails 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @ApiOperation(value = "获取经销商企业默认品牌与可见产品范围", response = FranchiserCaBrandVO.class)
    @PostMapping("/cabrand")
    public ResponseEnvelope getFranchiserCaBrand(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestParam("companyId") Integer companyId, @RequestParam("msgId") String msgId) {
        try {
            //1.数据校验
            if (null == companyId)
                return new ResponseEnvelope<>(false, "企业id不能为空!");

            //2.获取经销商企业信息
            FranchiserCaBrandVO franchiserCaBrandVO = baseCompanyBizService.getFranchiserCaBrandDetails(companyId);

            return new ResponseEnvelope<>(true, franchiserCaBrandVO, msgId, "获取经销商企业默认品牌与可见产品范围成功");

        } catch (Exception e) {

            logger.error("getFranchiserCaBrand 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }


    /**
     * wangHl
     * 获取经销商企业所属行业
     *
     * @param companyPId 经销商所属厂商Id
     * @param companyId  经销商Id
     * @return
     */
    @ApiOperation(value = "获取经销商企业所属行业", response = FranchiserCategoryListVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyPId", value = "经销商所属厂商Id", paramType = "query", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "companyId", value = "经销商Id", paramType = "query", dataType = "Integer")
    })
    @GetMapping("/franchiser/category")
    public ResponseEnvelope getFranchiserCategory(@RequestParam("companyPId") Integer companyPId, Integer companyId) {
        try {
            //1.数据校验
            if (null == companyPId) {
                return new ResponseEnvelope<>(false, "所属厂商id不能为空!");
            }
            List<FranchiserCategoryListVO> franchiserCategoryList = null;
            if (null != companyId) {
                franchiserCategoryList = baseCompanyBizService.getFranchiserCategoryList(companyPId.longValue(), companyId.longValue());
            } else {
                franchiserCategoryList = baseCompanyBizService.getFranchiserCategoryList(companyPId.longValue(), null);
            }


            return new ResponseEnvelope<>(true, franchiserCategoryList, "ok", "获取经销商企业所属行业成功");
        } catch (Exception e) {

            logger.error("获取经销商企业所属行业 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }


    /**
     * @description：模糊查询经销商所属行业
     * @author : WangHaiLin
     * @date : 2018/8/2 18:00
     * @param: [pId, name]
     * @return: com.sandu.commons.ResponseEnvelope
     */
    @ApiOperation(value = "模糊查询经销商所属行业", response = DictionaryTypeListVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pId", value = "经销商所属厂商Id", paramType = "query", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "name", value = "行业名称", paramType = "query", dataType = "String")
    })
    @GetMapping("/franchiser/category/list")
    public ResponseEnvelope getFranchiserIndustry(@RequestParam(value = "pId") Integer pId, String name) {
        try {
            //校验数据
            if (pId == null) {
                return new ResponseEnvelope<>(false, "参数异常!");
            }

            //获取经销商所属厂商信息
            BaseCompanyDetailsVO company = baseCompanyBizService.getCompanyInfoDetails(pId.longValue());
            if (null == company) {
                logger.error("模糊查询经销商所属行业 获取经销商所属厂商信息获取失败:{}", company);
                return new ResponseEnvelope<>(false, "经销商所属厂商信息获取失败!");
            }
            //经销商所属属行业
            String companyIndustrys = company.getCompanyIndustrys();
            if (null == companyIndustrys) {
                logger.error("模糊查询经销商所属行业 经销商所属厂商所属行业结果:{}", companyIndustrys);
                return new ResponseEnvelope<>(false, "经销商所属厂商没有所属行业");
            }

            //获取经销商可见行业
            List<DictionaryTypeListVO> list = dictionaryService.getFranchiserCategoryList(companyIndustrys, name);
            if (null != list) {
                return new ResponseEnvelope<>(true, "模糊查询经销商所属行业", list);
            }
            return new ResponseEnvelope<>(true, "查询结果为空!");
        } catch (Exception e) {
            logger.error("模糊查询经销商所属行业 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }

    @ApiOperation(value = "新增经销商企业时获取厂商下的行业", response = DictionaryTypeListVO.class)
    @GetMapping(value = "/getFactoryIndustrys")
    public Object getFactoryIndustrys(@RequestParam("companyId") Long companyId) {

        if (companyId == null) {
            return new ResponseEnvelope<>(false, "参数不能为空!");
        }

        BaseCompanyDetailsVO baseCompanyDetailsVO = dictionaryService.getCompanyIndustrys(companyId);

        return new ResponseEnvelope<>(true,null,baseCompanyDetailsVO);
    }
}
