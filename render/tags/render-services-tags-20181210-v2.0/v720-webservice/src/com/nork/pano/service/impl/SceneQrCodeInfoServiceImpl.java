package com.nork.pano.service.impl;

import com.nork.pano.dao.SceneQrCodeInfoMapper;
import com.nork.pano.model.qrcode.SceneQrCodeInfo;
import com.nork.pano.service.SceneQrCodeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sceneQrCodeInfoService")
public class SceneQrCodeInfoServiceImpl implements SceneQrCodeInfoService {

    @Autowired
    private SceneQrCodeInfoMapper sceneQrCodeInfoMapper;

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
}