package com.sandu.system.service;

import java.util.List;
import java.util.Map;

import com.sandu.system.model.ResFile;
import com.sandu.system.model.search.ResFileSearch;

/**   
 * @Title: ResFileService.java 
 * @Package com.nork.system.service
 * @Description:系统模块-文件资源库Service
 * @createAuthor pandajun 
 * @CreateDate 2015-07-02 17:36:00
 * @version V1.0   
 */
public interface ResFileService {
	/**
	 * 新增数据
	 *
	 * @param resFile
	 * @return  int 
	 */
	public int add(ResFile resFile);

	/**
	 *    更新数据
	 *
	 * @param resFile
	 * @return  int 
	 */
	public int update(ResFile resFile);
	/**
	 *    更新数据
	 *
	 * @param resFile
	 * @return  int 
	 */
	public int update(ResFile resFile,Integer businessId,String fileKey);

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
	 * @return  ResFile 
	 */
	public ResFile get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  resFile
	 * @return   List<ResFile>
	 */
	public List<ResFile> getList(ResFile resFile);

	/**
	 *    获取数据数量
	 *
	 * @param  resFile
	 * @return   int
	 */
	public int getCount(ResFileSearch resFileSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  resFile
	 * @return   List<ResFile>
	 */
	public List<ResFile> getPaginatedList(
				ResFileSearch resFiletSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 将图片信息记录到数据库中
	 */
	public void saveFiles(String planId,List<Map> list,String level);
	
	/**
	 * 回填文件资源的businessId
	 * @param businessId 业务表ID
	 * @param resModelId 模型资源表ID
	 */
	public void backFillResFile(Integer businessId,Integer resFileId,String fileKey);
	
	/**
	 * 删除当前文件资源信息
	 * @param businessId 业务表ID
	 * @param resModelId 模型资源表ID
	 */
	public void clearBackfillResFile(Integer businessId,Integer resFileId);
	
	/**
	 * 查询该数据是否有共享一个文件
	 * @param filePath
	 * @return
	 */
	public int filePathCount(String filePath);

	/**
	 * 回填businessId
	 * @param materialFileId 文件id
	 * @param id businessId设置为id值
	 */
	public void setBusinessId(Integer materialFileId, Integer id);


	/**
	 * 查询富文本
	 * @param descFileId
	 * @return
	 */
    String readFile(Integer descFileId);
}
