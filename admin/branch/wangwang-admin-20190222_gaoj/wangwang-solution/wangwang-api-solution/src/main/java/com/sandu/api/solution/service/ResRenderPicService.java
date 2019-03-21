package com.sandu.api.solution.service;

import com.sandu.api.solution.model.ResRenderPic;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ResRenderPicService class
 *
 * @author bvvy
 * @date 2018/04/02
 */
public interface ResRenderPicService {


    /**
     * 根据id集获取图片id地址map
     *
     * @param ids ids
     * @return Map(id, 地址)
     */
    Map<Integer, String> idAndPathMap(List<Integer> ids);

    /**
     * 根据id 获取图片地址
     *
     * @param id id
     * @return path
     */
    String getPathById(Integer id);

    /**
     * 批量添加
     * @param renderPics RenderPic
     */
    void batchAddResRenderPic(List<ResRenderPic> renderPics,Long planId);

    /**
     * 增加渲染图片资源库
     * @param resRenderPic
     */
	int addResRenderPic(ResRenderPic resRenderPic);
	
	/**
	 * 更新数据
	 * @param resRenderPic
	 * @return  int 
	 */
	public int updateResRenderPic(ResRenderPic resRenderPic);

	/**
	 * 获取符合条件的数据
	 * @param reqMap
	 * @return
	 */
	public List<ResRenderPic> selectResRenderPicByParam(String plandId,String renderingType,String fileKey,String picType);
}
