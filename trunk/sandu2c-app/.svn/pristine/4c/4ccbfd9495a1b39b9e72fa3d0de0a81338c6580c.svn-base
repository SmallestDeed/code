package com.sandu.system.service;

import com.sandu.product.model.SplitTextureDTO.ResTextureDTO;
import com.sandu.system.model.ResTexture;

import java.util.List;

/**
 * @version V1.0
 * @Title: ResTextureService.java
 * @Package com.sandu.system.service
 * @Description:系统模块-材质库Service
 * @createAuthor pandajun
 * @CreateDate 2016-06-30 14:10:42
 */
public interface ResTextureService {
    /**
     * 新增数据
     *
     * @param resTexture
     * @return int
     */
    int add(ResTexture resTexture);

    /**
     * 更新数据
     *
     * @param resTexture
     * @return int
     */
    int update(ResTexture resTexture);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return ResTexture
     */
    ResTexture get(Integer id);


    /**
     * ResTexture转化为ResTextureDTO
     *
     * @param resTexture
     * @return
     * @author huangsongbo
     */
    ResTextureDTO fromResTexture(ResTexture resTexture);
    
    
	/**
	 * 通过id 集合 批量获取数据
	 * @param textureIdStrList
	 * @return
	 */
    List<ResTexture> getBatchGet(ResTexture resTexture_);
}
