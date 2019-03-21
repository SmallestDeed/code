package com.sandu.web.mainpage.controller;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.PlatformConstant;
import com.sandu.goods.model.PO.GoodsListPO;
import com.sandu.goods.output.CompanyIntroduceVO;
import com.sandu.goods.output.GoodsDetailVO;
import com.sandu.goods.service.BaseGoodsSPUService;
import com.sandu.platform.BasePlatform;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.service.BaseCompanyService;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.sandu.goods.constant.PlatformConstant.BRAND2C_PLATFORM_CODE;
import static com.sandu.goods.constant.PlatformConstant.MINIPROGRAM_PLATFORM_CODE;
import static com.sandu.goods.constant.PlatformConstant.MOBILE2B_PLATFORM_CODE;

@Api(tags = "MainPageController", description = "小程序首页")
@Log4j2
@RestController
@RequestMapping("/v1/miniprogram/mainpage")
public class MainPageController {

    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private BaseGoodsSPUService baseGoodsSPUService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * @Author: zhangchengda
     * @Description: 小程序首页企业信息
     * @Date: 13:48 2018/7/2
     */
    @ApiOperation(value = "品牌介绍", response = ResponseEnvelope.class)
    @GetMapping("/company")
    public ResponseEnvelope getCompanyIntroduce(Integer companyId) {
        log.info("getCompanyIntroduce(Integer companyId);companyId={}", companyId);
        CompanyIntroduceVO introduce = baseCompanyService.getIntroduce(companyId);
        log.info("getCompanyIntroduce(Integer companyId);result={}", introduce);
        return new ResponseEnvelope(true, "success", introduce);
    }

    /**
     * @Author: zhangchengda
     * @Description: 小程序首页特卖商品
     * @Date: 17:52 2018/7/23
     */
    @ApiOperation(value = "特卖商品", response = ResponseEnvelope.class)
    @GetMapping("/specialOffer")
    public ResponseEnvelope getSpecialOfferGoods(HttpServletRequest request) {
        GoodsListPO goodsListPO = new GoodsListPO();
        try {
            goodsListPO = getCompanyAndBrand(goodsListPO, request);
        } catch (Exception e) {
            return new ResponseEnvelope(false, e.getMessage());
        }
        log.info("查询小程序首页特卖商品：goodsListPO = ", goodsListPO.toString());
        List<GoodsDetailVO> goods = baseGoodsSPUService.getSpecialOfferGoods(goodsListPO);
        return new ResponseEnvelope(true, "success", goods);
    }

    /**
     * @Author: zhangchengda
     * @Description: 小程序首页预售新品
     * @Date: 17:52 2018/7/23
     */
    @ApiOperation(value = "预售新品", response = ResponseEnvelope.class)
    @GetMapping("/presellGoods")
    public ResponseEnvelope getPresellGoods(HttpServletRequest request) {
        GoodsListPO goodsListPO = new GoodsListPO();
        try {
            goodsListPO = getCompanyAndBrand(goodsListPO, request);
        } catch (Exception e) {
            return new ResponseEnvelope(false, e.getMessage());
        }
        log.info("查询小程序首页预售新品：goodsListPO = ", goodsListPO.toString());
        List<GoodsDetailVO> goods = baseGoodsSPUService.getPresellGoods(goodsListPO);
        return new ResponseEnvelope(true, "success", goods);
    }

    private GoodsListPO getCompanyAndBrand(GoodsListPO goodsListPO, HttpServletRequest request) {
        // 查询是否是访问公司
        log.info("访问平台:{}", request.getHeader("Platform-Code"));
        BaseCompany baseCompany;
        String platformCode = request.getHeader("Platform-Code");
        if (platformCode == null || "".equals(platformCode)) {
            throw new RuntimeException("未获取到平台编码");
        }
        switch (platformCode) {
            case BRAND2C_PLATFORM_CODE:
            case MINIPROGRAM_PLATFORM_CODE:
            default:
                baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Referer"));
                if (baseCompany == null)
                    baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
                break;
            case MOBILE2B_PLATFORM_CODE:
                LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
                if (loginUser == null) {
                    throw new RuntimeException("用户未登陆!!!");
                }
                SysUser user = sysUserService.get(loginUser.getId());
                baseCompany = baseCompanyService.get(user.getBusinessAdministrationId());
                break;
        }
        if (baseCompany == null) {
            throw new RuntimeException("未获取到公司");
        }
        // test ->start
        /*baseCompany.setBusinessType(4);*/
        // test ->end
        log.info("访问公司,baseCompany:{}", baseCompany.toString());

        goodsListPO.setCompanyId(baseCompany.getId());
        // 增加品牌过滤
        List<Integer> enableBrandIdList = null;
        if (PlatformConstant.PLATFORM_CODE_MINI_PROGRAM.equalsIgnoreCase(platformCode)) {
            enableBrandIdList = baseCompanyService.getEnableBrandIdsByAppId(baseCompany.getAppId());
        } else {
            enableBrandIdList = baseCompanyService.getBrandIdListByCompanyId(baseCompany.getId(), null,
                    platformCode);
        }
        if (enableBrandIdList != null && enableBrandIdList.size() > 0) {
            goodsListPO.setBrandIdList(enableBrandIdList);
        } else {
            throw new RuntimeException("没有找到品牌");
        }
        return goodsListPO;
    }
}
