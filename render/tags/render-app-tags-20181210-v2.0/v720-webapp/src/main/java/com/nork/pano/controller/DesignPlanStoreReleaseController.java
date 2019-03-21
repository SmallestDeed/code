package com.nork.pano.controller;

import com.nork.cityunion.model.input.UnionGroupSearch;
import com.nork.cityunion.model.vo.UnionGroupVo;
import com.nork.cityunion.model.vo.UnionSpecialOfferVo;
import com.nork.cityunion.service.UnionGroupService;
import com.nork.cityunion.service.UnionSpecialOfferService;
import com.nork.common.constant.HeaderConstants;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.JsonUtil;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.pano.model.input.SceneDataSearch;
import com.nork.pano.model.constant.SceneTypeConstant;
import com.nork.pano.model.input.SceneDataSearch;
import com.nork.pano.model.output.DesignPlanStoreReleaseVo;
import com.nork.pano.model.output.RoamSceneDataVo;
import com.nork.pano.model.output.UnionStoreSingleDataVo;
import com.nork.pano.model.qrcode.SceneQrCodeInfo;
import com.nork.pano.service.DesignPlanStoreReleaseService;
import com.nork.pano.service.SceneQrCodeInfoService;
import com.sandu.common.LoginContext;
import org.apache.poi.ss.formula.functions.T;
import com.nork.pano.service.PanoramaService;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 720分享制作
 */
@Controller
@RequestMapping("/v1/panorama")
public class DesignPlanStoreReleaseController {

    private static Logger logger = LoggerFactory.getLogger(DesignPlanStoreReleaseController.class);
    //随选网appid
    private static final String WX_SMALLl_APPID = Utils.getPropertyName("config/share","wx.small.share.appid","wx42e6b214e6cdaed3");
    //三度总店appid
    private static final String WX_SMALL_SANDU_APPID = Utils.getPropertyName("config/share","wx.small.sandu.appid","wx0d37f598e1028825");

    @Autowired
    private DesignPlanStoreReleaseService designPlanStoreReleaseService;
    @Autowired
    private PanoramaService panoramaService;
    @Autowired
    private SceneQrCodeInfoService sceneQrCodeInfoService;
    @Autowired
    private UnionGroupService unionGroupService;
    @Autowired
    private UnionSpecialOfferService unionSpecialOfferService;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    public Object get(@ModelAttribute SceneDataSearch search,HttpServletRequest request){
        if(search ==null){
            return new ResponseEnvelope<>(false, "参数为空！");
        }

        System.out.println("get请求---------参数：" + search.toString());
        ResponseEnvelope<T>  res = new ResponseEnvelope<T>();
        RoamSceneDataVo roamSceneDataVo = null;
        DesignPlanStoreReleaseVo designPlanStoreReleaseVo = null;
        UnionStoreSingleDataVo storeSingleDataVo = null;
        //用户....
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser != null && loginUser.getId() != null){
            search.setUserId(loginUser.getId());
        }
        //平台
        String platformType = request.getHeader(HeaderConstants.PLATFORM_CODE);
        //域名
        String domainName = Utils.getDomainNameByHost(request);
        if(Utils.isEmpty(platformType) || Utils.isEmpty(domainName) || search.getSceneType() != null){
            search.setPlatformType(SceneDataSearch.PlatformType.PC2B);
        }else if(Objects.equals("miniProgram",platformType)){
            if(!Utils.isEmpty(domainName) && (Objects.equals(WX_SMALLl_APPID,domainName) || Objects.equals(WX_SMALL_SANDU_APPID,domainName))){
                //兼容之前的错误逻辑,当appid 为wx42e6b214e6cdaed3或wx0d37f598e1028825 都认为来源是随选网
                //随选网
                search.setPlatformType(SceneDataSearch.PlatformType.SELECTDECORATION);
            }else{
                //企业小程序
                search.setPlatformType(SceneDataSearch.PlatformType.COMPANY_MINIPROGRAM);
            }
        }else if(Objects.equals("mobile2b",platformType)){
            search.setPlatformType(SceneDataSearch.PlatformType.COMPANY_MOBILE2B);
        }else{
            search.setPlatformType(SceneDataSearch.PlatformType.OTHERS);
        }

        //自定义标题文案
        String desc = null;
        try {
            if(search.getSceneType() == null){
                //其他地方共用此接口，没有传sceneType则默认为是720制作分享方案，以适配原逻辑
                search.setSceneType(SceneTypeConstant.STORE_RELEASE_SCENE);
            }else{
                /**
                 * 校验描述文案是否有修改(hasChanged 为 1)
                 * 如果有修改则接收到的renderId为SceneQrCodeInfo 主键,将其转为需要的业务id，
                 * 并得到描述文案
                 */
                if(search.getHasChanged() != null && 1 == search.getHasChanged()){
                    SceneQrCodeInfo codeInfo = sceneQrCodeInfoService.selectByPrimaryKey(search.getRenderId());
                    if(codeInfo != null && !Utils.isEmpty(codeInfo.getRenderId())){
                        search.setRenderId(Integer.parseInt(codeInfo.getRenderId()));
                        desc = codeInfo.getCopywritingInformation();
                    }else{
                        logger.error("参数有误，找不到二维码相关记录信息,参数:{}",search.toString());
                    }
                }
            }

            //根据场景类型查找不同场景数据
            switch (search.getSceneType()){
                case SceneTypeConstant.SINGER_SCENE:
                    roamSceneDataVo = panoramaService.vr720SingleSenceData(search);
                    res.setObj(roamSceneDataVo);
                    break;
                case SceneTypeConstant.ROAM_SCENE:
                    roamSceneDataVo = panoramaService.vr720RoamSenceData(search);
                    res.setObj(roamSceneDataVo);
                    break;
                case SceneTypeConstant.GROUP_SCENE:
                    roamSceneDataVo = panoramaService.vr720GroupSenceData(search);
                    res.setObj(roamSceneDataVo);
                    break;
                case SceneTypeConstant.UNION_STORE_SINGLE_SCENE:
                    storeSingleDataVo = panoramaService.vr720UnionStoreSingleData(search);
                    res.setObj(storeSingleDataVo);
                    break;
                case SceneTypeConstant.STORE_RELEASE_SCENE:
                    designPlanStoreReleaseVo = designPlanStoreReleaseService.getPanorama(search,loginUser);
                    System.out.println("designPlanStoreReleaseVo：" + JsonUtil.toJson(designPlanStoreReleaseVo));
                    // 更新浏览量
                    designPlanStoreReleaseService.updatePv(designPlanStoreReleaseVo.getId());
                    System.out.println("designPlanStoreReleaseService.updatePv uuid：" + designPlanStoreReleaseVo.getId());
                    res.setObj(designPlanStoreReleaseVo);
                    break;
                default:
                    logger.error("get方法参数sceneType为{},是无效的类型" , search.getSceneType());
                    res.setSuccess(false);
                    res.setMessage("无效的场景类型！");
                    break;
            }
            //填充自定义分享文案
            if(roamSceneDataVo != null && !Utils.isEmpty(desc)){
                roamSceneDataVo.setDesc(desc);
            }
            if(storeSingleDataVo != null && !Utils.isEmpty(desc)){
                storeSingleDataVo.setDesc(desc);
            }
            if(designPlanStoreReleaseVo != null && !Utils.isEmpty(desc)){
                designPlanStoreReleaseVo.setDesc(desc);
            }

        } catch (Exception e) {
            logger.error("get请求出现异常!",e);
            res.setSuccess(false);
            res.setMessage("出现异常!");
        }

        return res;
    }

    @RequestMapping(value = "/getUnionGroupVoList")
    @ResponseBody
    public Object getUnionGroupDetailsList(@ModelAttribute UnionGroupSearch unionGroupSearch) {
        if(unionGroupSearch == null || unionGroupSearch.getSceneType() == null){
            return new ResponseEnvelope<UnionGroupVo>(false, "参数为空！");
        }
        UnionGroupVo unionGroupVo = null;
        switch (unionGroupSearch.getSceneType()){
            case SceneTypeConstant.UNION_STORE_SINGLE_SCENE:
                unionGroupVo = unionGroupService.getUnionGroupByUnionStoreRelease(unionGroupSearch.getId());
                break;
            case SceneTypeConstant.STORE_RELEASE_SCENE:
                unionGroupVo = unionGroupService.getUnionGroupByStoreRelease(unionGroupSearch.getId());
                break;
            default:
                logger.error("getUnionGroupDetailsList 方法参数sceneType为{},是无效的类型" , unionGroupSearch.getSceneType());
                return new ResponseEnvelope<UnionGroupVo>(false,"sceneType无效");
        }
            return new ResponseEnvelope<UnionGroupVo>(true,unionGroupVo);
    }

    @RequestMapping(value = "/getUnionSpecialOfferVo")
    @ResponseBody
    public Object getUnionSpecialOfferVo(@RequestParam(value = "id")Integer id) {
        if (id == null || id <= 0) {
            return new ResponseEnvelope<UnionSpecialOfferVo>(false, "参数为空！");
        }
        UnionSpecialOfferVo specialOfferVo = unionSpecialOfferService.getUnionSpecialOfferVoById(id);
        return new ResponseEnvelope<UnionSpecialOfferVo>(true,specialOfferVo);
    }

}
