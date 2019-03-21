package com.sandu.web.goods.controller;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.designplan.vo.RecommendedPlanProductRelatedVo;
import com.sandu.goods.input.GoodsListQuery;
import com.sandu.goods.input.GoodsSkuQuery;
import com.sandu.goods.model.PO.GoodsListPO;
import com.sandu.goods.output.*;
import com.sandu.goods.service.BaseGoodsSKUService;
import com.sandu.goods.service.BaseGoodsSPUService;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.model.UserProductCollect;
import com.sandu.product.service.BaseCompanyService;
import com.sandu.product.service.UserProductCollectService;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;
import com.sandu.web.designplan.controller.DesignPlanRecommendedController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Api(tags = "BaseGoodsController", description = "商品")
@Log4j2
@RestController
@RequestMapping("/v1/miniprogram/goods/basegoods")
public class BaseGoodsController
{
    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private BaseGoodsSPUService baseGoodsSPUService;
    @Autowired
    private BaseGoodsSKUService baseGoodsSKUService;
    @Autowired
    private DesignPlanRecommendedController designPlanRecommendedController;
    @Autowired
    private UserProductCollectService userProductCollectService;
    @Autowired
    private SysUserService sysUserService;
    @Value("${sanducloudhome.company.code}")
    private String companyCode;


    /**
     * @Author: zhangchengda
     * @Description: 获取商品列表
     * @Date: 18:08 2018/5/30
     */
    @ApiOperation(value = "获取商品列表", response = ResponseEnvelope.class)
    @GetMapping("/list")
    public ResponseEnvelope getGoodsList(GoodsListQuery goodsListQuery, HttpServletRequest request)
    {
        GoodsListPO goodsListPO = new GoodsListPO();
        // 查询是否是访问公司
        BaseCompany baseCompany;
        switch(request.getHeader("Platform-Code"))
        {
            case "brand2c":
            case "miniProgram":
            default:
                baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Referer"));
                if (baseCompany == null)
                    baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
                break;
            case "mobile2b":
                LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
                if (loginUser == null) return new ResponseEnvelope(false,"用户未登陆!!!");
                SysUser user = sysUserService.get(loginUser.getId());
                baseCompany = baseCompanyService.get(user.getBusinessAdministrationId());
                break;
        }
        if (baseCompany == null)
            return new ResponseEnvelope(false, "未获取到公司");
        goodsListPO.setCompanyId(companyCode.equals(baseCompany.getCompanyCode())?null:baseCompany.getId());

        if(goodsListQuery.getCategoryCode() != null)
        {
            if (goodsListQuery.getCategoryCode().contains(","))
            {
                goodsListPO.setCodeList(Arrays.asList(goodsListQuery.getCategoryCode().split(",")));
                goodsListPO.setCodeListSize(goodsListPO.getCodeList().size());
            }else
            {
                goodsListPO.setGoodsType("%" + goodsListQuery.getCategoryCode().trim() + "%");
            }
        }

        if(goodsListQuery.getCurPage() != null && goodsListQuery.getPageSize() != null)
        {
            goodsListPO.setStart((goodsListQuery.getCurPage()-1)*goodsListQuery.getPageSize());
            goodsListPO.setLimit(goodsListQuery.getPageSize());
        }

        Integer total = baseGoodsSPUService.getGoodsListCount(goodsListPO);
        if (total > 0)
        {
            List<GoodsVO> goodsVOList = baseGoodsSPUService.getGoodsList(goodsListPO);
            if (goodsVOList == null || goodsVOList.size() == 0)
            {
                return new ResponseEnvelope(true, "查询产品出错");
            }
            return new ResponseEnvelope(true, "success", goodsVOList, total);
        }
        return new ResponseEnvelope(true, "该分类下没有产品");
    }

    /**
     * @Author: zhangchengda
     * @Description: 商品详情
     * @Date: 18:10 2018/5/30
     */
    @ApiOperation(value = "获取商品详情", response = ResponseEnvelope.class)
    @GetMapping("/detail")
    public ResponseEnvelope getGoodsDetail(Integer id)
    {
        GoodsDetailVO goodsDetailVO = baseGoodsSPUService.getGoodsDetail(id);
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        if (loginUser == null)
        {
            goodsDetailVO.setIsFavorite(0);
        }else
        {
            UserProductCollect collect = new UserProductCollect();
            collect.setUserId(loginUser.getId());
            collect.setSpuId(id);
            List<UserProductCollect> userProductCollect = userProductCollectService.getList(collect);

            if (userProductCollect == null || userProductCollect.size() == 0)
            {
                goodsDetailVO.setIsFavorite(0);
            } else
            {
                goodsDetailVO.setIsFavorite(1);
            }
        }
        return new ResponseEnvelope(true, "success", goodsDetailVO);
    }

    /**
     * @Author: zhangchengda
     * @Description: 商品案例
     * @Date: 10:02 2018/5/31
     */
    @ApiOperation(value = "商品案例", response = ResponseEnvelope.class)
    @GetMapping("/design")
    public ResponseEnvelope getGoodsDesign(@RequestParam("spuId") Integer spuId, HttpServletRequest request)
    {
        List<Integer> skuIds = baseGoodsSKUService.getProductIdsBySpuId(spuId);
        List<RecommendedPlanProductRelatedVo> recommendedPlanProductRelatedVoList = new ArrayList<>();
        for (Integer skuId : skuIds)
        {
            recommendedPlanProductRelatedVoList.addAll((List<RecommendedPlanProductRelatedVo>)designPlanRecommendedController.recommendedPlanOfProductRelated(skuId,request).getObj());
        }

        return new ResponseEnvelope(true, "success", recommendedPlanProductRelatedVoList);
    }

    /**
     * @Author: zhangchengda
     * @Description: 商品具有的规格属性
     * @Date: 10:28 2018/5/31
     */
    @ApiOperation(value = "获取商品具有的规格属性", response = ResponseEnvelope.class)
    @GetMapping("/attr")
    public ResponseEnvelope getGoodsAttribute(Integer id)
    {
        GoodsSkuVO goodsSkuVO = baseGoodsSPUService.getSpuAttrList(id);
        String selected = "";
        for (SpuAttributeVO spuAttributeVO : goodsSkuVO.getAttr())
        {
            for (SpuAttrValueVO spuAttrValueVO : spuAttributeVO.getAttrValue())
            {
                if (spuAttrValueVO.getIsSelected() == 1)
                {
                    selected += "".equals(selected)?spuAttrValueVO.getAttrValueName():","+spuAttrValueVO.getAttrValueName();
                }
            }
        }
        goodsSkuVO.setAttribute(selected);
        return new ResponseEnvelope(true, "success", goodsSkuVO);
    }

    /**
     * @Author: zhangchengda
     * @Description: 根据选择的规格获取sku信息
     * @Date: 10:29 2018/5/31
     */
    @ApiOperation(value = "根据选择的规格获取sku信息", response = ResponseEnvelope.class)
    @GetMapping("/sku")
    public ResponseEnvelope getSkuInfoByAttribute(GoodsSkuQuery goodsSkuQuery)
    {
        GoodsSkuVO goodsSkuVO = baseGoodsSKUService.getSkuInfoByAttrs(goodsSkuQuery);
        return new ResponseEnvelope(true, "success", goodsSkuVO);
    }

    /**
     * @Author: zhangchengda
     * @Description: 小程序首页企业信息
     * @Date: 13:48 2018/7/2
     */
    @ApiOperation(value = "", response = ResponseEnvelope.class)
    @GetMapping("/company")
    public ResponseEnvelope getCompanyIntroduce(Integer companyId)
    {
        CompanyIntroduceVO introduce = baseCompanyService.getIntroduce(companyId);
        return new ResponseEnvelope(true,"success",introduce);
    }
}
