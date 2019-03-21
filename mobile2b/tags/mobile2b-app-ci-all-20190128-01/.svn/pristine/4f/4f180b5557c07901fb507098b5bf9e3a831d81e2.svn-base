package com.nork.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.nork.system.model.ResHousePic;
import com.nork.system.model.SysUser;
import com.nork.system.model.search.ResHousePicSearch;

/**   
 * @Title: ResHousePicService.java 
 * @Package com.nork.system.service
 * @Description:系统模块-户型、空间图片资源表Service
 * @createAuthor pandajun 
 * @CreateDate 2016-08-13 16:34:09
 * @version V1.0   
 */
public interface ResHousePicService {
	/**
	 * 新增数据
	 *
	 * @param resHousePic
	 * @return  int 
	 */
	public int add(ResHousePic resHousePic);

	/**
	 *    更新数据
	 *
	 * @param resHousePic
	 * @return  int 
	 */
	public int update(ResHousePic resHousePic);

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
	 * @return  ResHousePic 
	 */
	public ResHousePic get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  resHousePic
	 * @return   List<ResHousePic>
	 */
	public List<ResHousePic> getList(ResHousePic resHousePic);

	/**
	 *    获取数据数量
	 *
	 * @param  resHousePic
	 * @return   int
	 */
	public int getCount(ResHousePicSearch resHousePicSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  resHousePic
	 * @return   List<ResHousePic>
	 */
	public List<ResHousePic> getPaginatedList(
				ResHousePicSearch resHousePictSearch);

	
	public void updateSmallPicBusinessId(ResHousePic resHousePic);

	
	
	public Map<String, ResHousePic> createThumbnail(ResHousePic resHousePic,
			HttpServletRequest request) throws IOException;

	public void backFillResPic(Integer spaceId, Integer picId, String picKey,
			String spaceCode);

	public Integer saveMultipartFile(MultipartFile file, String fileKey,SysUser sysUser);
	
}
