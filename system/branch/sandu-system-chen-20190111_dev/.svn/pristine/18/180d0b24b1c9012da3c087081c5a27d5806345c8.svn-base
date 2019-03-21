package com.sandu.web.category;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.sandu.api.category.model.output.CategoryAndIndustryVO;
import com.sandu.api.category.output.CategoryListVO;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.dictionary.service.DictionaryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sandu.api.category.input.ProCategoryListQuery;
import com.sandu.api.category.output.ProCategoryListVO;
import com.sandu.api.category.service.ProCategoryService;
import com.sandu.api.category.service.biz.ProCategoryBizService;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.commons.util.StringUtils;
import com.sandu.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author chenqiang
 * @Description 产品分类 控制层
 * @Date 2018/6/1 0001 10:12
 * @Modified By
 */
@Api(tags = "proCategory", description = "产品分类")
@RestController
@RequestMapping("/v1/pro/category")
public class ProCategoryController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(ProCategoryController.class);

    @Autowired
    private ProCategoryService proCategoryService;

    @Autowired
    private ProCategoryBizService proCategoryBizService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private BaseCompanyService companyService;


    @ApiOperation(value = "产品分类列表查询", response = ProCategoryListVO.class)
    @GetMapping("/list")
    public ResponseEnvelope getCategoryListByPid(@Valid ProCategoryListQuery query, BindingResult validResult) {
        try {

            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }

            //2.获取list数据
            List<ProCategoryListVO> categoryList = proCategoryService.getCategoryListByPidLevel(query);

            return new ResponseEnvelope<>(true, categoryList.size() , categoryList);

        } catch (Exception e) {

            logger.error("getCategoryListByPid 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @ApiOperation(value = "企业产品分类列表All", response = ProCategoryListVO.class)
    @GetMapping("/company/listAll")
    public ResponseEnvelope getCompanyListAll(@RequestParam(value = "productVisibilityRange",required = false) String productVisibilityRange) {
        try {

            //1.数据校验
            if(StringUtils.isEmpty(productVisibilityRange))
                productVisibilityRange = "";

            //2.获取list数据
            List<ProCategoryListVO> categoryList = proCategoryBizService.getCompanyCategoryList(productVisibilityRange);

            return new ResponseEnvelope<>(true, categoryList.size() , categoryList);

        } catch (Exception e) {

            logger.error("getCompanyListAll 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @ApiOperation(value = "经销商产品分类列表All", response = ProCategoryListVO.class)
    @GetMapping("/franchiser/listAll")
    public ResponseEnvelope geFranchiserListAll(@RequestParam(value = "companyId",required = false) Integer companyId,@RequestParam(value = "productVisibilityRange",required = false) String productVisibilityRange) {
        try {

            //1.数据校验
            if(null == companyId)
                return new ResponseEnvelope<>(false, "企业不能为空!");
            if(StringUtils.isEmpty(productVisibilityRange)) {
                productVisibilityRange = "";
            }


            //2.获取list数据
            List<ProCategoryListVO> categoryList = proCategoryBizService.getFranchiserCategoryList(companyId,productVisibilityRange);

            return new ResponseEnvelope<>(true, categoryList.size() , categoryList);

        } catch (Exception e) {

            logger.error("geFranchiserListAll 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }


    /**
     * wangHl
     * 处理企业所属行业变化而导致企业可见产品范围变化
     * @param newIndustryValues 新所属行业value集合
     * @param oldIndustryValues 原所属行业value集合
     * @param oldCategoryIds 原可见产品Id集合
     * @return
     */
    @ApiOperation(value = "处理企业所属行业变化而导致企业可见产品范围变化", response = CategoryAndIndustryVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newIndustryValues",value = "新所属行业value集合",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "oldIndustryValues",value = "原所属行业value集合",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "oldCategoryIds",value = "原可见产品Id集合",paramType = "query",dataType = "String")
    })
    @GetMapping("/industry/list")
    public ResponseEnvelope getCategoryByCompanyIndustry(String newIndustryValues, String oldIndustryValues, String oldCategoryIds){
        logger.info("处理企业所属行业变化而导致企业可见产品范围变化接口");
        try {
            logger.info("参数 新所属行业value:{}",newIndustryValues);
            logger.info("参数 原所属行业value:{}",oldIndustryValues);
            logger.info("参数 原可见产品Id:{}",oldCategoryIds);
            //处理参数
            List<Integer> oldCategoryList=null;
            if (org.apache.commons.lang3.StringUtils.isEmpty(oldCategoryIds)){
                oldCategoryList=new ArrayList<>();
            }else{
                oldCategoryList=StringUtil.getListByString(oldCategoryIds);
            }
            List<Integer> oldIndustryList=null;
            if (org.apache.commons.lang3.StringUtils.isEmpty(oldIndustryValues)){
                oldIndustryList=new ArrayList<>();
            }else{
                oldIndustryList=StringUtil.getListByString(oldIndustryValues);
            }
            List<Integer> newIndustryList=null;
            if (org.apache.commons.lang3.StringUtils.isEmpty(newIndustryValues)){
                newIndustryList=new ArrayList<>();
            }else{
                newIndustryList=StringUtil.getListByString(newIndustryValues);
            }
            logger.info("参数 新所属行业value集合:{}",newIndustryList);
            logger.info("参数 原所属行业value集合:{}",oldIndustryList);
            logger.info("参数 原可见产品Id集合:{}",oldCategoryList);

            CategoryAndIndustryVO CategoryAndIndustryVO = proCategoryBizService.getCategoryByCompanyIndustry(newIndustryList, oldIndustryList, oldCategoryList);
            return new ResponseEnvelope<>(true,CategoryAndIndustryVO);

        }catch (Exception e){
            logger.error("参数 新所属行业value:{}",newIndustryValues);
            logger.error("参数 原所属行业value:{}",oldIndustryValues);
            logger.error("参数 原可见产品Id:{}",oldCategoryIds);
            logger.error("处理企业新的可见产品范围方法 系统异常:", e);
            return new ResponseEnvelope<>(false,"处理企业新的可见产品范围方法 系统异常", e);
        }

    }


    /**
     * wangHl   沒使用
     * 通过Pid查询可见产品范围集合
     * @param pId 可见商品父级Id
     * @return
     */
    @ApiOperation(value = "通过Pid查询可见产品范围集合", response = CategoryListVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pId",value = "父级Id",paramType = "query",dataType = "Long",required = true)
    })
    @GetMapping("/industry/list/bypid")
    public ResponseEnvelope getCategoryByCompanyIndustry(@RequestParam(value = "pId")Long pId) {
        try {
            //校验数据
            if (pId==null){
                return new ResponseEnvelope<>(false, "参数异常!");
            }
            //获取可见产品范围集合
            List<CategoryListVO> list = proCategoryService.getCategoryByPId(pId);
            if (null!=list){
                return new ResponseEnvelope<>(true,"通过Pid查询可见产品范围集合成功",list);
            }
            return new ResponseEnvelope<>(true, "查询结果为空!");
        }catch (Exception e){
            logger.error("通过Pid查询可见产品范围集合 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }


}
