package com.sandu.web.banner;

import com.google.gson.Gson;
import com.sandu.api.banner.output.BaseBannerWebListVO;
import com.sandu.api.banner.service.biz.BaseBannerBizService;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.platform.model.BasePlatform;
import com.sandu.api.platform.service.BasePlatformService;
import com.sandu.commons.ResponseEnvelope;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础-广告 前端 controller层
 * @author WangHaiLin
 * @date 2018/5/25  16:43
 */
@Api(tags = "BannerAdWeb",description ="广告前端接口")
@RestController
@RequestMapping("v1/base/banner/web")
public class BaseBannerAdWebController {
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[基础-广告控制层]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseBannerAdWebController.class);

    @Autowired
    private BaseBannerBizService baseBannerBizService;

    @Autowired
    private BasePlatformService basePlatformService;

    @Autowired
    private BaseCompanyService baseCompanyService;


    /**
     * 企业小程序获取Banner列表接口
     * @param positionCode
     * @param companyId
     * @return
     */
    @ApiOperation(value = "企业广告",response = BaseBannerWebListVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "positionCode",value = "位置编码(system:module:page:positon)",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "companyId",value = "企业Id",paramType = "query",dataType = "Integer",required = true)
    })
    @GetMapping("/miniprogram/list")
    public ResponseEnvelope getCompanyBannerImgs(@RequestParam(value = "positionCode") String positionCode, @RequestParam(value = "companyId") Integer companyId) {
        try{
            //1. 校验参数
            if(null==positionCode||null==companyId){
                logger.debug(CLASS_LOG_PREFIX,"位置编码",gson.toJson(positionCode));
                logger.debug(CLASS_LOG_PREFIX,"企业Id",gson.toJson(companyId));
                return new ResponseEnvelope<>(false, "参数有误!");
            }
            //2. 调用通用查询方法
            List<BaseBannerWebListVO> bannerListVOS = baseBannerBizService.getBannerImgs(2, positionCode, companyId);
            if(null!=bannerListVOS){
                return new ResponseEnvelope<>(true, bannerListVOS.size(),bannerListVOS);
            }
            return new ResponseEnvelope<>(true,0,null);
        }catch (Exception ex){
            logger.error("系统异常:",ex);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }


    /**
     * 同城联盟获取Banner列表接口
     * @param positionCode
     * @param request
     * @return
     */
    @ApiOperation(value = "同城联盟广告",response = BaseBannerWebListVO.class)
    @ApiImplicitParam(name = "positionCode",value = "位置编码(system:module:page:positon)",paramType = "query",dataType = "String",required = true)
    @ResponseBody
    @RequestMapping(value = "/union/list",method = RequestMethod.GET)
    public ResponseEnvelope getUnionBannerImgs(@RequestParam(value = "positionCode") String positionCode,
                                               HttpServletRequest request) {
        //1. 校验参数
        if (null==positionCode||"".equals(positionCode)){
            logger.debug(CLASS_LOG_PREFIX,"位置编码",positionCode);
            return new ResponseEnvelope(false,"positionCode参数有误");
        }
        //2. 从头部获取平台编码
        String platformCode = request.getHeader("Platform-Code");
        //3. 根据平台编码查询平台id
        if(null!=platformCode){
            BasePlatform basePlatform = basePlatformService.getByPlatformCode(platformCode);
            logger.debug(CLASS_LOG_PREFIX,"同城联盟平台信息",gson.toJson(basePlatform));
            if(null!=basePlatform){
                //4.调用通用方法
                List<BaseBannerWebListVO> bannerListVOS = baseBannerBizService.getBannerImgs(1, positionCode, basePlatform.getId());
                if (bannerListVOS!=null&&bannerListVOS.size()>0){
                    return new ResponseEnvelope(true,bannerListVOS.size(),bannerListVOS);
                }
                return new ResponseEnvelope(true,0,null);
            }
            return new ResponseEnvelope(false,"平台不存在");
        }
        return new ResponseEnvelope(false,"从头部获取平台编码失败");
    }


    /**
     * 选装网获取Banner列表接口
     * @param positionCode
     * @param basePlatformId
     * @return
     */
    @ApiOperation(value = "选装网广告",response = BaseBannerWebListVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "positionCode",value = "位置编码(system:module:page:positon)",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "basePlatformId",value = "平台Id",paramType = "query",dataType = "Integer",required = true)
    })
    @GetMapping(value = "/xzminiprogram/list")
    public ResponseEnvelope getXZBannerImgs(@RequestParam(value = "positionCode") String positionCode,
                                            @RequestParam(value = "basePlatformId") Integer basePlatformId ) {
        if (null==positionCode||"".equals(positionCode)){
            logger.debug(CLASS_LOG_PREFIX,"位置编码",gson.toJson(positionCode));
            return new ResponseEnvelope(false,"positionCode参数有误");
        }
        if (null==basePlatformId){
            return new ResponseEnvelope(false,"basePlatformId参数有误");
        }
        BasePlatform basePlatform = basePlatformService.get(basePlatformId);
        logger.debug(CLASS_LOG_PREFIX,"选装网平台信息",gson.toJson(basePlatform));
        if(null!=basePlatform){
            List<BaseBannerWebListVO> bannerListVOS = baseBannerBizService.getBannerImgs(1, positionCode, basePlatform.getId());
            if(bannerListVOS!=null&&bannerListVOS.size()>0){
                return new ResponseEnvelope(true,bannerListVOS.size(),bannerListVOS);
            }
            return new ResponseEnvelope(true,"无此对应信息",null);
        }
        return new ResponseEnvelope(false,"平台不存在");
    }

    /**
     * 装企小程序获取Banner列表接口
     * @param positionCode
     * @return
     */
    @ApiOperation(value = "企业店铺广告",response = BaseBannerWebListVO.class)
    @GetMapping("/shop/list")
    public ResponseEnvelope getShopBannerImgs(@RequestParam(value = "positionCode") String positionCode, HttpServletRequest request) {
        try{
            //1. 校验参数
            if(null==positionCode){
                logger.debug(CLASS_LOG_PREFIX,"位置编码",gson.toJson(positionCode));
                return new ResponseEnvelope<>(false, "参数有误!");
            }
            BaseCompany baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Referer"));
            if (null == baseCompany) {
                baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
                if (null == baseCompany) {
                    return new ResponseEnvelope(false, "未获取到公司信息");
                }
            }
            Integer companyId = baseCompany.getId().intValue();
            Integer shopId = baseCompanyService.getMainCompanyShopId(companyId);
            if (shopId == null) {
                return new ResponseEnvelope(false, "未获取到企业店铺信息");
            }
            //2. 调用通用查询方法
            List<BaseBannerWebListVO> bannerListVOS = baseBannerBizService.getBannerImgs(3, positionCode, shopId);
            if(null!=bannerListVOS){
                return new ResponseEnvelope<>(true, bannerListVOS.size(),bannerListVOS);
            }
            return new ResponseEnvelope<>(true,0,null);
        }catch (Exception ex){
            logger.error("系统异常:",ex);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }

    @ApiOperation(value = "获取店铺ID和企业ID",response = ResponseEnvelope.class)
    @GetMapping("/getId")
    public ResponseEnvelope getShopId(HttpServletRequest request){
        BaseCompany baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Referer"));
        if (null == baseCompany) {
            baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
            if (null == baseCompany) {
                return new ResponseEnvelope(false, "未获取到公司信息");
            }
        }
        Integer companyId = baseCompany.getId().intValue();
        Integer shopId = baseCompanyService.getMainCompanyShopId(companyId);
        Map<String, Integer> map = new HashMap<>();
        map.put("shopId", shopId);
        map.put("companyId", companyId);
        return new ResponseEnvelope(true, map);
    }
}
