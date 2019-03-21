package com.nork.pano.service;

import com.nork.pano.model.dto.QrCodeSceneDto;
import com.nork.pano.model.qrcode.SceneQrCodeInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SceneQrCodeInfoService {
    /**
     *
     * 
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * 
     */
    int insertSelective(SceneQrCodeInfo record);

    /**
     *
     * 
     */
    SceneQrCodeInfo selectByPrimaryKey(Integer id);

    /**
     *
     * 
     */
    int updateByPrimaryKeySelective(SceneQrCodeInfo record);


    /**
     * 根据参数获取对象
     * @author chenqiang
     * @param record
     * @return
     * @date 2018/10/9 0009 11:41
     */
    SceneQrCodeInfo getSceneQrCodeInfo(SceneQrCodeInfo record);

    /**
     * 获取一个方案的所有二维码
     * @author: chenm
     * @date: 2019/1/25 11:26
     * @param planId
     * @return: java.util.List<com.nork.pano.model.dto.QrCodeSceneDto>
     */
    List<QrCodeSceneDto>  getPlanShareCodeInfoByScenePlanId(Integer planId);

}