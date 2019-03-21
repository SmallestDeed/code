package com.nork.system.service;

import java.util.List;

import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.search.ResTextureSearch;

/**   
 * @Title: ResTextureService.java 
 * @Package com.nork.system.service
 * @Description:系统模块-材质库Service
 * @createAuthor pandajun 
 * @CreateDate 2016-06-30 14:10:42
 * @version V1.0   
 */
public interface ResTextureService {
	/**
	 * 新增数据
	 *
	 * @param resTexture
	 * @return  int 
	 */
	public int add(ResTexture resTexture);

	/**
	 *    更新数据
	 *
	 * @param resTexture
	 * @return  int 
	 */
	public int update(ResTexture resTexture);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResTexture 
	 */
	public ResTexture get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  resTexture
	 * @return   List<ResTexture>
	 */
	public List<ResTexture> getList(ResTexture resTexture);

	/**
	 *    获取数据数量
	 *
	 * @param  resTexture
	 * @return   int
	 */
	public int getCount(ResTextureSearch resTextureSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  resTexture
	 * @return   List<ResTexture>
	 */
	public List<ResTexture> getPaginatedList(
				ResTextureSearch resTexturetSearch);

	/**
	 * 根据图片信息,补充材质数据信息
	 * @author huangsongbo
	 * @param resTexture 
	 * @param resPic
	 */
	public void saveParamsByResPic(ResTexture resTexture, ResPic resPic);

	/**
	 * ResTexture转化为ResTextureDTO
	 * @author huangsongbo
	 * @param resTexture
	 * @return
	 */
	public ResTextureDTO fromResTexture(ResTexture resTexture);
	
	/**
	 * 根据文件信息,补充材质数据信息
	 * @param resTexture
	 * @param resFile
	 */
	public void saveParamsByResFile(ResTexture resTexture, ResFile resFile);

	/**
	 * 通过id 集合 批量获取数据
	 * @param textureIdStrList
	 * @return
	 */
	public List<ResTexture> getBatchGet(ResTexture resTexture_);


}
