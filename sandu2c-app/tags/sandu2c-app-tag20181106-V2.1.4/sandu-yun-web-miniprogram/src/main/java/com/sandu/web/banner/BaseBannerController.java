package com.sandu.web.banner;

import com.google.gson.Gson;
import com.sandu.banner.output.BaseBannerListVO;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.system.service.BaseBannerBizService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 基础-广告控制层
 * @author WangHaiLin
 * @date 2018/5/15  11:43
 */
@Api(tags = "baseBanner",description = "基础-广告")
@Controller
@RequestMapping("/v1/miniprogram/base/banner")
public class BaseBannerController {
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[基础-广告控制层]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseBannerController.class);

    @Autowired
    private BaseBannerBizService baseBannerBizService;
    /**
     * 企业小程序获取Banner列表接口
     * @param positionCode
     * @param companyId
     * @return
     */
    @ApiOperation(value = "企业广告",response = BaseBannerListVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "positionCode",value = "位置编码(system:module:page:positon)",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "companyId",value = "企业Id",paramType = "query",dataType = "Integer",required = true)
    })
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseEnvelope getCompanyBannerImgs(@RequestParam(value = "positionCode") String positionCode,
                                                       @RequestParam(value = "companyId") Integer companyId) {
        //1. 校验参数
        if(null==positionCode||null==companyId){
            logger.debug(CLASS_LOG_PREFIX,"位置编码",gson.toJson(positionCode));
            logger.debug(CLASS_LOG_PREFIX,"企业Id",gson.toJson(companyId));
            return new ResponseEnvelope(false,"参数有误");
        }
        //2. 调用通用查询方法
        List<BaseBannerListVO> bannerListVOS = baseBannerBizService.getBannerImgs(2, positionCode, companyId);
        if(null!=bannerListVOS){
            return new ResponseEnvelope(true,"success",bannerListVOS,bannerListVOS.size());
        }
        return new ResponseEnvelope(true,"无相应结果");
    }
}
