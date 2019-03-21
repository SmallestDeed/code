package com.nork.pano.controller;

import com.nork.cityunion.model.input.UnionGroupSearch;
import com.nork.cityunion.model.vo.UnionGroupVo;
import com.nork.cityunion.model.vo.UnionSpecialOfferVo;
import com.nork.cityunion.service.UnionGroupService;
import com.nork.cityunion.service.UnionSpecialOfferService;
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

/**
 * 720分享制作
 */
@Controller
@RequestMapping("/v1/panorama")
public class DesignPlanStoreReleaseController {

    private static Logger logger = LoggerFactory.getLogger(DesignPlanStoreReleaseController.class);

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
    public Object get(@ModelAttribute SceneDataSearch search){
        if(search ==null){
            return new ResponseEnvelope<>(false, "参数为空！");
        }

        System.out.println("get请求---------参数：" + search.toString());
        ResponseEnvelope<T>  res = new ResponseEnvelope<T>();
        RoamSceneDataVo roamSceneDataVo = new RoamSceneDataVo();
        //自定义标题文案
        String desc = null;
        try {
            if(search.getSceneType() == null){
                //其他地方共用此接口，没有传sceneType则默认为是720制作分享方案，以适配原逻辑
                search.setSceneType(SceneTypeConstant.STORE_RELEASE_SCENE);
            }else{
                /**
                 * 校验描述文案是否有修改
                 * 如果有修改则renderId为SceneQrCodeInfo 主键,将其转为需要的业务id，
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
                    if(!Utils.isEmpty(desc)){
                        roamSceneDataVo.setDesc(desc);
                    }
                    res.setObj(roamSceneDataVo);
                    break;
                case SceneTypeConstant.ROAM_SCENE:
                    roamSceneDataVo = panoramaService.vr720RoamSenceData(search);
                    if(!Utils.isEmpty(desc)){
                        roamSceneDataVo.setDesc(desc);
                    }
                    res.setObj(roamSceneDataVo);
                    break;
                case SceneTypeConstant.GROUP_SCENE:
                    roamSceneDataVo = panoramaService.vr720GroupSenceData(search);
                    if(!Utils.isEmpty(desc)){
                        roamSceneDataVo.setDesc(desc);
                    }
                    res.setObj(roamSceneDataVo);
                    break;
                case SceneTypeConstant.UNION_STORE_SINGLE_SCENE:
                    UnionStoreSingleDataVo storeSingleDataVo = panoramaService.vr720UnionStoreSingleData(search);
                    if(!Utils.isEmpty(desc)){
                        storeSingleDataVo.setDesc(desc);
                    }
                    res.setObj(storeSingleDataVo);
                    break;
                case SceneTypeConstant.STORE_RELEASE_SCENE:
                    DesignPlanStoreReleaseVo designPlanStoreReleaseVo = null;
                    designPlanStoreReleaseVo = designPlanStoreReleaseService.getPanorama(search);
                    if(!Utils.isEmpty(desc)){
                        designPlanStoreReleaseVo.setDesc(desc);
                    }
                    System.out.println("designPlanStoreReleaseVo：" + JsonUtil.toJson(designPlanStoreReleaseVo));
                    // 更新浏览量
                    designPlanStoreReleaseService.updatePv(search.getRenderId());
                    System.out.println("designPlanStoreReleaseService.updatePv uuid：" + search.getRenderId());
                    res.setObj(designPlanStoreReleaseVo);
                    break;
                default:
                    logger.error("get方法参数sceneType为{},是无效的类型" , search.getSceneType());
                    res.setSuccess(false);
                    res.setMessage("无效的场景类型！");
                    break;
            }
        } catch (Exception e) {
            logger.error("get请求出现异常!",e);
            System.out.println(e);
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
