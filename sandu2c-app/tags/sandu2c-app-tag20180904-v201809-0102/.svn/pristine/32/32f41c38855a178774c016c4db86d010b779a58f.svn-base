package com.sandu.system.service.impl;

import com.sandu.common.util.Utils;
import com.sandu.product.model.SplitTextureDTO;
import com.sandu.system.dao.ResTextureMapper;
import com.sandu.system.model.ResFile;
import com.sandu.system.model.ResPic;
import com.sandu.system.model.ResTexture;
import com.sandu.system.service.ResFileService;
import com.sandu.system.service.ResPicService;
import com.sandu.system.service.ResTextureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @Title: ResTextureServiceImpl.java
 * @Package com.sandu.system.service.impl
 * @Description:系统模块-材质库ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2016-06-30 14:10:42
 */
@Service("resTextureService")
public class ResTextureServiceImpl implements ResTextureService {

    @Autowired
    private ResTextureMapper resTextureMapper;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private ResFileService resFileService;

    /**
     * 新增数据
     *
     * @param resTexture
     * @return int
     */
    @Override
    public int add(ResTexture resTexture) {
        resTextureMapper.insertSelective(resTexture);
        return resTexture.getId();
    }

    /**
     * 更新数据
     *
     * @param resTexture
     * @return int
     */
    @Override
    public int update(ResTexture resTexture) {
        return resTextureMapper
                .updateByPrimaryKeySelective(resTexture);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return resTextureMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return ResTexture
     */
    @Override
    public ResTexture get(Integer id) {
        return resTextureMapper.selectByPrimaryKey(id);
    }

    @Override
    public SplitTextureDTO.ResTextureDTO fromResTexture(ResTexture resTexture) {//TODO:增加了材质球文件路径
        Integer picId = resTexture.getPicId();
        Integer normalPicId = resTexture.getNormalPicId();
        String path = "";
        String normalPicPath = "";

        Integer textureBallFileId = resTexture.getTextureBallFileId();
        String materialPath = "";
        if (textureBallFileId != null && textureBallFileId.intValue() > 0) {
            ResFile resFile = resFileService.get(textureBallFileId);
            if (resFile != null) {
                materialPath = resFile.getFilePath();
            }
        }

        if (picId != null && picId.intValue() > 0) {
            ResPic resPic = resPicService.get(picId);
            if (resPic != null) {
                path = resPic.getPicPath();
            }
        }
        if (normalPicId != null && normalPicId.intValue() > 0) {
            ResPic resPic = resPicService.get(normalPicId);
            if (resPic != null) {
                normalPicPath = Utils.getValue("app.resources.url", "") + resPic.getPicPath();
                normalPicPath = Utils.dealWithPath(normalPicPath, "linux");
            }
        }
        return new SplitTextureDTO().new ResTextureDTO(resTexture.getId(), path, resTexture.getTextureAttrValue(), resTexture.getFileHeight(),
                resTexture.getFileWidth(), resTexture.getLaymodes(), materialPath, resTexture.getNormalParam(), normalPicPath);
    }
    
    
    
	/**
	 * 通过id 集合 批量获取数据
	 * @param resTexture
	 * @return
	 */
	@Override
	public List<ResTexture> getBatchGet(ResTexture resTexture) {
		return resTextureMapper.getBatchGet(resTexture);
	}
}
