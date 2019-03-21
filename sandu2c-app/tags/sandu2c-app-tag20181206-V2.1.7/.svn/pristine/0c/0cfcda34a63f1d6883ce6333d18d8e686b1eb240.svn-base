package com.sandu.web.product.controller;

import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.base.BaseBrandObject;
import com.sandu.product.model.BaseBrand;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.service.BaseBrandService;
import com.sandu.product.service.BaseCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/13
 */

@RestController
@RequestMapping("/v1/tocmobile/brand")
public class BaseBrandController {

    public static Logger log = LoggerFactory.getLogger(BaseBrandController.class);
    private final static String CLASS_LOG_PREFIX = "[基本品牌服务]";
    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private BaseBrandService baseBrandService;

    /**
     * @param request
     * @param brandId
     * @return校验当前产品是否属于本公司
     */
    @RequestMapping(value = "/validateCompany")
    @ResponseBody
    public ResponseEnvelope validateCompany(HttpServletRequest request, Integer brandId) {
        log.debug("Process in BaseBrandController.validateBrand method parameter barandId:{}", brandId);

        // 查询是否是访问公司
        BaseCompany baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Referer"));
        if (baseCompany == null) {
            baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
        }
        if (null == baseCompany) {
            return new ResponseEnvelope(false, "没有获取到厂商信息");
        }

        return baseCompanyService.validateCompany(baseCompany.getId(), brandId);

    }

    /**
     * @param request
     * @return 获取当前登录公司的品牌信息
     */
    @RequestMapping(value = "/getAllBrandFromCompany", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getAllBrandFromCompany(HttpServletRequest request) {
        // 查询是否是访问公司
        BaseCompany baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Referer"));
        if (baseCompany == null) {
            baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
        }
        List<BaseBrand> brandList = null;
        if (null != baseCompany) {
            brandList = baseBrandService.getAllBrandByCompanyId(baseCompany.getId());
            if (null == brandList || brandList.size() <= 0) {
                return new ResponseEnvelope(false, "该厂商下没有关联任何品牌");
            }
        }
        return new ResponseEnvelope(true, "success", BaseBrandObject.parseToBrandVoList(brandList));
    }
}
