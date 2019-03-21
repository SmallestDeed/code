package com.sandu.web.brand.controller;

import com.github.pagehelper.PageInfo;
import com.sandu.api.brand.input.BrandQueryVO;
import com.sandu.api.brand.input.BrandSubmit;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.model.bo.BrandQuery;
import com.sandu.api.brand.output.BrandVO;
import com.sandu.api.brand.service.BrandService;
import com.sandu.api.company.model.bo.CompanyBO;
import com.sandu.api.company.model.bo.CompanyBrandBo;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.product.model.Product;
import com.sandu.api.product.model.bo.ProductListBO;
import com.sandu.api.product.model.po.ProductQueryPO;
import com.sandu.api.product.service.ProductService;
import com.sandu.common.ReturnData;
import com.sandu.util.BrandUtil;
import com.sandu.util.CopyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sandu.constant.ResponseEnum.*;

/**
 * @author sandu-lipeiyuan
 */
@Api(description = "品牌", tags = "brand")
@RestController
@RequestMapping("/v1/brand")
public class BrandController {

    public static final String DATETIMESSS = "yyyyMMddHHmmssSSS";
    //操作类型标识
    private static final Integer INSERT_TYPE = 1;
    private static final Integer UPDATE_TYPE = 2;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ProductService productService;

    @ApiOperation(value = "获取品牌下拉框", response = BrandVO.class)
    @GetMapping("/list")
    public ReturnData getAllBrand() {
        ReturnData data = ReturnData.builder();
        List<Brand> brands = brandService.findAllBrandNameAndId();
        List<BrandVO> rets = new ArrayList<>();
        for (Brand brand : brands) {
            BrandVO tmp = new BrandVO();
            tmp.setId(brand.getId().intValue());
            tmp.setName(brand.getBrandName());
            rets.add(tmp);
        }
        if (rets.isEmpty()) {
            return data.success(false).code(NOT_CONTENT).message("为查询到相关数据...").data(new ArrayList<>());
        }
        return data.success(true).code(SUCCESS).data(rets);
    }


    @ApiOperation(value = "获取企业的品牌", response = BrandVO.class)
    @GetMapping("/withCompany")
    public ReturnData getBrandsByCompany(Integer companyId) {
        List<Brand> brands = brandService.getBrandByCompanyId(companyId);
        List<BrandVO> rets = new ArrayList<>();
        brands.forEach(item -> {
            BrandVO tmp = new BrandVO();
            tmp.setId(item.getId().intValue());
            tmp.setName(item.getBrandName());
            rets.add(tmp);
        });
        if (rets.isEmpty()) {
            return ReturnData.builder().success(false).code(NOT_CONTENT).message("为查询到相关数据...").data(new ArrayList<>());
        }
        return ReturnData.builder().success(true).code(SUCCESS).data(rets);
    }


    /**
     * 品牌列表
     *
     * @param brandQueryVO
     * @return
     */
    @ApiOperation(value = "分页获取品牌列表", response = BrandVO.class)
    @GetMapping("/getPageList")
    public ReturnData getPageBrandList(BrandQueryVO brandQueryVO) {

        //对象复制
        BrandQuery brandQuery = new BrandQuery();
        brandQuery = (BrandQuery) CopyUtil.copyProperties(brandQuery, brandQueryVO);

        PageInfo<Brand> brandPageInfo = brandService.pageQueryBrand(brandQuery, brandQueryVO.getPageNo(), brandQueryVO.getPageSize());
        List<Brand> brandList = brandPageInfo.getList();
        if (CollectionUtils.isEmpty(brandList)) {
            return ReturnData.builder().success(true).code(NOT_CONTENT).data(new BrandVO()).message("未查询到相关数据...");
        }

        Map<Integer, Integer> companyMap = getCompanyMap(brandList);

        BrandVO brandVO = new BrandVO();
        for (Brand brand : brandList) {
            brandVO = (BrandVO) CopyUtil.copyProperties(brandVO, brand);
            brandVO.setCompanyName(companyMap.get(brand.getCompanyId()));
        }

        return ReturnData.builder().success(true).code(SUCCESS).data(brandList).total(brandPageInfo.getTotal());

    }

    /**
     * 根据品牌id获取公司名称
     *
     * @param brandList
     * @return
     */
    private Map<Integer, Integer> getCompanyMap(List<Brand> brandList) {
        List<Integer> brandIds = new ArrayList<>();
        for (Brand brand : brandList) {
            brandIds.add(brand.getId().intValue());
        }
        List<CompanyBrandBo> companyBrandBoList = companyService.getCompanyByBrandIds(brandIds);
        return companyBrandBoList.stream().collect(Collectors.toMap(CompanyBrandBo::getCompanyId, CompanyBrandBo::getBrandId));
    }

    @ApiOperation(value = "获取品牌详情/编辑详情", response = BrandVO.class)
    @GetMapping("/getDetailById/{id}")
    public ReturnData update(@PathVariable Integer id) {
        //响应参数
        BrandVO brandVO = new BrandVO();

        //请求参数
        BrandQuery brandQuery = new BrandQuery();
        brandQuery.setId(Long.valueOf(id));

        List<Brand> brandList = brandService.queryBrand(brandQuery);
        if (CollectionUtils.isEmpty(brandList)) {
            return ReturnData.builder().success(true).code(NOT_CONTENT).data(brandVO).message("未查询到相关数据...");
        }

        Map<Integer, Integer> companyMap = getCompanyMap(brandList);

        //组装数据
        Brand brand = brandList.get(0);
        brandVO = (BrandVO) CopyUtil.copyProperties(brandVO, brand);
        brandVO.setCompanyName(companyMap.get(brand.getId()));

        return ReturnData.builder().success(true).code(SUCCESS).data(brandVO);
    }


    /**
     * 品牌列表
     *
     * @param brandQueryVO
     * @return
     */
    @ApiOperation(value = "不分页根据条件获取品牌列表", response = BrandVO.class)
    @GetMapping("/getList")
    public ReturnData getBrandList(BrandQueryVO brandQueryVO) {

        BrandQuery brandQuery = new BrandQuery();

        //对象复制
        brandQuery = (BrandQuery) CopyUtil.copyProperties(brandQuery, brandQueryVO);

        List<Brand> brandList = brandService.queryBrand(brandQuery);
        if (CollectionUtils.isEmpty(brandList)) {
            return ReturnData.builder().success(true).code(NOT_CONTENT).data(new BrandVO()).message("未查询到相关数据...");
        }

        Map<Integer, Integer> companyMap = getCompanyMap(brandList);

        BrandVO brandVO = new BrandVO();
        for (Brand brand : brandList) {
            brandVO = (BrandVO) CopyUtil.copyProperties(brandVO, brand);
            brandVO.setCompanyName(companyMap.get(brand.getCompanyId()));
        }

        return ReturnData.builder().success(true).code(SUCCESS).data(brandList);

    }

    /**
     * 编辑品牌
     *
     * @param brandSubmit
     * @return
     */
    @ApiOperation(value = "编辑品牌")
    @PutMapping("/updateBrand")
    public ReturnData updateBrand(@RequestBody BrandSubmit brandSubmit) {

        if (brandSubmit.getId() == null || !(brandSubmit.getId() > 0)) {
            return ReturnData.builder().success(false).code(PARAM_ERROR).message("请求参数错误");
        }

        if ((checkBrandName(brandSubmit.getBrandName(), brandSubmit.getCompanyId(), UPDATE_TYPE))) {
            return ReturnData.builder().success(false).message("品牌名称已存在");
        }

        Brand brand = new Brand();

        //对象复制
        brand = (Brand) CopyUtil.copyProperties(brand, brandSubmit);

        int updateBrand = brandService.updateBrand(brand);

        if (updateBrand < 0) {
            return ReturnData.builder().success(false).code(ERROR);
        } else {
            return ReturnData.builder().success(true).code(SUCCESS);
        }

    }


    /**
     * 新增品牌
     *
     * @param brandSubmit
     * @return
     */
    @ApiOperation(value = "新增品牌")
    @PostMapping("/insertBrand")
    public ReturnData insertBrand(@RequestBody BrandSubmit brandSubmit) {

        if (checkBrandCode(brandSubmit.getBrandCode())) {
            return ReturnData.builder().success(false).message("品牌编码已存在");
        }

        if (null == brandSubmit.getBrandStyleId()) {
            return ReturnData.builder().success(false).message("未选择风格");
        }

        if ((checkBrandName(brandSubmit.getBrandName(), brandSubmit.getCompanyId(), INSERT_TYPE))) {
            return ReturnData.builder().success(false).message("品牌名称已存在");
        }

        String sysCode = BrandUtil.getgetCurrentDateTime(LocalDateTime.now(), DATETIMESSS) + "_" + BrandUtil.generateRandomDigitString(6);
        Brand brand = new Brand();
        brand.setSysCode(sysCode);
        //对象复制
        brand = (Brand) CopyUtil.copyProperties(brand, brandSubmit);

        int updateBrand = brandService.saveBrand(brand);

        if (updateBrand < 0) {
            return ReturnData.builder().success(false).code(ERROR);
        } else {
            return ReturnData.builder().success(true).code(SUCCESS);
        }

    }


    /**
     * 校验品牌名称是否重复
     *
     * @param brandName
     * @param CompanyId
     * @return
     */
    private boolean checkBrandName(String brandName, Integer CompanyId, Integer type) {

        BrandQuery brandQuery = new BrandQuery();
        brandQuery.setBrandName(brandName);
        brandQuery.setCompanyId(CompanyId);
        List<Brand> brandList = brandService.queryBrand(brandQuery);
        if (CollectionUtils.isEmpty(brandList)) {
            return false;
        }
        //如果是更新则要去掉自己
        if (type == 2 && brandList.size() <= 1) {
            return false;
        }
        return true;
    }

    /**
     * 单个删除品牌
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "单个删除品牌")
    @DeleteMapping("/delBrandById/{id}")
    public ReturnData delBrandById(@PathVariable Integer id) {

        if (checkIsAllowToDeleteBrand(id) == false) {
            return ReturnData.builder().success(false).code(NOT_CONTENT).message("该品牌下有关联产品，若要删除品牌，请先删除对应产品!");
        }

        int deleteBrandById = brandService.deleteBrandById(id.longValue());

        if (deleteBrandById < 0) {
            return ReturnData.builder().success(false).code(ERROR);
        } else {
            return ReturnData.builder().success(true).code(SUCCESS);
        }

    }

    /**
     * 批量删除品牌
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除品牌")
    @DeleteMapping("/delBrandByIds")
    public ReturnData delBrandByIds(@RequestBody List<Integer> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            return ReturnData.builder().success(false).code(PARAM_ERROR).message("id为空");
        }

        Set<Integer> brandIds = checkIsAllowToDeleteBrand(ids);
        if (brandIds != null) {
            return ReturnData.builder().success(false).code(NOT_CONTENT).message("该品牌下有关联产品，若要删除品牌，请先删除对应产品! id:" + ids.toString());
        }
        int deleteBrandById = 0;
        for (Integer id : ids) {
            deleteBrandById = brandService.deleteBrandById(id.longValue());
        }

        if (deleteBrandById != brandIds.size()) {
            return ReturnData.builder().success(false).code(ERROR);
        } else {
            return ReturnData.builder().success(true).code(SUCCESS);
        }

    }

    /**
     * 生成品牌code
     * 生成规则：行业valueKey   + 企业  + 3位 序列  例：（ABC00001001）
     */
    @ApiModelProperty(value = "生成品牌编码")
    @GetMapping(value = "/createBrandCode")
    public ReturnData createBrandCode(Integer companyId) {
        try {
            if (companyId == null) {
                return ReturnData.builder().success(false).code(PARAM_ERROR);
            }

            CompanyBO company = companyService.getCompanyInfoById(Long.valueOf(companyId));
            String companyCode = company.getCompanyCode();

            Brand brand = new Brand();
            brand.setCompanyId(companyId);
            String brandMaxCode = brandService.createBrandCode(brand);
            String newBrandCode = getNewBrandCode(companyCode, brandMaxCode);
            return ReturnData.builder().success(false).code(SUCCESS).data(newBrandCode);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.builder().success(false).code(ERROR);
        }
    }

    /**
     * 生成新品牌编码
     *
     * @param companyCode
     * @param baseBrandXulie
     * @return
     */
    private String getNewBrandCode(String companyCode, String baseBrandXulie) {
        String newBrandCode;
        //没有则 默认为初始序列号
        String str = "001";
        int i = 0;
        if (StringUtils.isNotBlank(baseBrandXulie)) {
            i = Integer.parseInt(baseBrandXulie);
        }

        if (i > 0) {
            int s = i;
            s = ++s;
            s = s == 1000 ? 1 : s;
            //计算 转型
            String reslut = s > 10 ? (s > 100 ? s + "" : "0" + s) : "00" + s;
            newBrandCode = companyCode + reslut;
        } else {
            newBrandCode = companyCode + str;
        }
        return newBrandCode;
    }

    /**
     * 检验品牌编码是否存在
     */
    private Boolean checkBrandCode(String brandCode) {
        Brand brand = brandService.getBrandByBrandCode(brandCode);
        if (brand != null) {
            return true;
        }
        return false;
    }

    /**
     * 检查品牌是否可以删除
     *
     * @param brandId
     * @return
     * @author huangsongbo
     */
    private boolean checkIsAllowToDeleteBrand(Integer brandId) {
        List<Product> baseProductList = productService.getProductByBrandId(brandId, 1, 1);
        if (CollectionUtils.isEmpty(baseProductList)) {
            return true;
        }
        return false;
    }

    /**
     * 批量检查品牌是否可以删除ids
     *
     * @param brandIdList
     * @return
     * @author huangsongbo
     */
    private Set<Integer> checkIsAllowToDeleteBrand(List<Integer> brandIdList) {

        ProductQueryPO productQueryPO = new ProductQueryPO();
        productQueryPO.setBrandIds(brandIdList);
        PageInfo<ProductListBO> baseProductList = productService.queryProducts(productQueryPO);
        List<ProductListBO> productListBOList = baseProductList.getList();
        if (CollectionUtils.isEmpty(baseProductList.getList())) {
            return null;
        }
        Set<Integer> brandIds = null;
        for (ProductListBO productListBO : productListBOList) {
            brandIds.add(productListBO.getBrandId());
        }
        return brandIds;
    }

}