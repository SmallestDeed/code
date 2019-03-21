package com.sandu.web.banner;

import com.google.gson.Gson;
import com.sandu.banner.output.BaseBannerListVO;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.platform.BasePlatform;
import com.sandu.system.service.BaseBannerBizService;
import com.sandu.system.service.BasePlatformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 基础-广告控制层
 * @author WangHaiLin
 * @date 2018/5/15  11:49
 */
@Api(tags = "baseBanner",description = "基础-广告")
@Controller
@RequestMapping("/v1/union/base/banner")
public class BaseBannerController {
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[基础-广告控制层]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseBannerController.class);
    @Autowired
    private BasePlatformService basePlatformService;
    @Autowired
    private BaseBannerBizService baseBannerBizService;
    /**
     * 同城联盟获取Banner列表接口
     * @param positionCode
     * @param request
     * @return
     */
    @ApiOperation(value = "同城联盟广告",response = BaseBannerListVO.class)
    @ApiImplicitParam(name = "positionCode",value = "位置编码(system:module:page:positon)",paramType = "query",dataType = "String",required = true)
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
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
                List<BaseBannerListVO> bannerListVOS = baseBannerBizService.getBannerImgs(1, positionCode, basePlatform.getId());
                if (bannerListVOS!=null&&bannerListVOS.size()>0){
                    return new ResponseEnvelope(true,"success",bannerListVOS,bannerListVOS.size());
                }
                return new ResponseEnvelope(true,"无相应信息",null,0);
            }
            return new ResponseEnvelope(false,"平台不存在");
        }
        return new ResponseEnvelope(false,"从头部获取平台编码失败");
    }
}
