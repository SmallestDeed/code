package com.sandu.web.brand;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.brand.input.CompanyBrandQuery;
import com.sandu.api.brand.output.CompanyBrandVO;
import com.sandu.api.brand.service.BaseBrandService;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author chenqiang
 * @Description 品牌 控制层
 * @Date 2018/6/1 0001 10:08
 * @Modified By
 */
@Api(tags = "baseBrand", description = "基础品牌")
@RestController
@RequestMapping("/v1/base/brand")
public class BaseBrandController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(BaseBrandController.class);

    @Autowired
    private BaseBrandService baseBrandService;

    @ApiOperation(value = "企业所属品牌列表查询", response = CompanyBrandVO.class)
    @GetMapping("/company/list")
    public ResponseEnvelope getBrandByCompanyId(@Valid CompanyBrandQuery query, BindingResult validResult) {
        try {

            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }

            //2.获取list数据
            List<CompanyBrandVO> brandList = baseBrandService.getBrandByCompanyId(query);

            return new ResponseEnvelope<>(true, brandList.size() , brandList);

        } catch (Exception e) {

            logger.error("getBrandByCompanyId 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }
}
