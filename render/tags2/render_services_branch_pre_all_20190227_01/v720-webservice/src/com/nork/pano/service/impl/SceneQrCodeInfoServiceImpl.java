package com.nork.pano.service.impl;

import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.ResRenderPicQO;
import com.nork.pano.dao.SceneQrCodeInfoMapper;
import com.nork.pano.model.constant.SceneTypeConstant;
import com.nork.pano.model.dto.QrCodeSceneDto;
import com.nork.pano.model.input.SceneDataSearch;
import com.nork.pano.model.output.DesignPlanStoreReleaseDetailsVo;
import com.nork.pano.model.qrcode.SceneQrCodeInfo;
import com.nork.pano.service.SceneQrCodeInfoService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.ResRenderVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service("sceneQrCodeInfoService")
public class SceneQrCodeInfoServiceImpl implements SceneQrCodeInfoService {

    @Autowired
    private SceneQrCodeInfoMapper sceneQrCodeInfoMapper;
    @Autowired
    private ResRenderPicService resRenderPicService;
    @Autowired
    private ResRenderVideoService resRenderVideoService;
    /**
     *
     * 
     */
    public int deleteByPrimaryKey(Integer id){
        return sceneQrCodeInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     *
     * 
     */
    public int insertSelective(SceneQrCodeInfo record){
        sceneQrCodeInfoMapper.insertSelective(record);
        return record.getId();
    }

    /**
     *
     * 
     */
    public SceneQrCodeInfo selectByPrimaryKey(Integer id){
        return sceneQrCodeInfoMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * 
     */
    public int updateByPrimaryKeySelective(SceneQrCodeInfo record){
        return sceneQrCodeInfoMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据参数获取对象
     * @author chenqiang
     * @param record
     * @return
     * @date 2018/10/9 0009 11:41
     */
    public SceneQrCodeInfo getSceneQrCodeInfo(SceneQrCodeInfo record){
        return sceneQrCodeInfoMapper.selectSceneQrCodeInfo(record);
    }

    @Override
    public List<QrCodeSceneDto> getPlanShareCodeInfoByScenePlanId(Integer planId) {
        List<QrCodeSceneDto> qrCodeSceneDtoList = new ArrayList<>();
        /**
         * 1.找到效果图方案对应所有缩略图
         * 2.分辨渲染类型，拼接对应参数
         * 3.返回
         */
        ResRenderPicQO renderPicQO = new ResRenderPicQO();
        renderPicQO.setDesignSceneId(planId);
        renderPicQO.setFileKeys(Arrays.asList(new String[]{"design.designPlan.render.small.pic","design.designPlan.render.video.cover"}));
        renderPicQO.setOrders("rendering_type asc");
        List<ResRenderPic> picList = resRenderPicService.selectListByFileKeys(renderPicQO);
        if(CustomerListUtils.isEmpty(picList)){
            return null;
        }
        QrCodeSceneDto qrCodeSceneDto = null;
        for (ResRenderPic renderPic : picList){
            if(renderPic == null || renderPic.getId() == null || renderPic.getId() < 1){
                continue;
            }
            Integer renderingType = renderPic.getRenderingType();
            if(renderingType == null){
               continue;
            }
            qrCodeSceneDto = new QrCodeSceneDto();
            qrCodeSceneDto.setRenderingType(renderingType);
            String picPath = renderPic.getPicPath();
            if( RenderTypeCode.COMMON_720_LEVEL == renderingType || RenderTypeCode.HD_720_LEVEL == renderingType ){
                // 普通单图720
                qrCodeSceneDto.setRenderingTypeStr("720效果图");
                //原图id
                qrCodeSceneDto.setRenderId(renderPic.getPid());
                qrCodeSceneDto.setHasChanged(0);
                qrCodeSceneDto.setPlanSourceType(DesignPlanStoreReleaseDetailsVo.PlanSourceType.DESIGNSCENE_PLAN);
                qrCodeSceneDto.setQrCodeType(SceneDataSearch.QRCodeType.QRCODE_TYPE_GENERAL);
                qrCodeSceneDto.setSceneType(SceneTypeConstant.SINGER_SCENE);
            }else if( RenderTypeCode.ROAM_720_LEVEL == renderingType ){
                // 720漫游
                qrCodeSceneDto.setRenderingTypeStr("720多点效果");
                //截图id
                qrCodeSceneDto.setRenderId(renderPic.getSysTaskPicId());
                qrCodeSceneDto.setHasChanged(0);
                qrCodeSceneDto.setPlanSourceType(DesignPlanStoreReleaseDetailsVo.PlanSourceType.DESIGNSCENE_PLAN);
                qrCodeSceneDto.setQrCodeType(SceneDataSearch.QRCodeType.QRCODE_TYPE_GENERAL);
                qrCodeSceneDto.setSceneType(SceneTypeConstant.ROAM_SCENE);
            } else if(RenderTypeCode.COMMON_VIDEO == renderingType || RenderTypeCode.HD_VIDEO == renderingType){
                //普通视频
                ResRenderVideo resRenderVideo = new ResRenderVideo();
                resRenderVideo.setIsDeleted(0);
                resRenderVideo.setSysTaskPicId(renderPic.getId());
                List<ResRenderVideo>videoList = resRenderVideoService.getList(resRenderVideo);
                if(videoList != null && videoList.size() == 1 ){
                    picPath = videoList.get(0).getVideoPath();
                }
                picPath = Utils.getPropertyName("app","app.resources.url","")  + picPath;
                qrCodeSceneDto.setQrCodeContentUrl(picPath);
                qrCodeSceneDto.setRenderingTypeStr("漫游视频");
            } else if(RenderTypeCode.COMMON_PICTURE_LEVEL == renderingType){//照片级
                picPath = Utils.getPropertyName("app","app.resources.url","")  + picPath;
                qrCodeSceneDto.setQrCodeContentUrl(picPath);
                qrCodeSceneDto.setRenderingTypeStr("照片级");
            }else{
                picPath=Utils.getValue("app.resources.url", "")+picPath;
                qrCodeSceneDto.setQrCodeContentUrl(picPath);
                qrCodeSceneDto.setRenderingTypeStr("未知类型");
            }
            qrCodeSceneDtoList.add(qrCodeSceneDto);

        }
        return qrCodeSceneDtoList;
    }
}