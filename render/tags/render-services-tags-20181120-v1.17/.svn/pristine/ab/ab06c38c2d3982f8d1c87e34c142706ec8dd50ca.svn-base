package com.nork.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nork.sync.model.ResEntity;
import com.nork.system.model.ResModel;
import com.nork.system.model.search.ResModelSearch;

/**   
 * @Title: ResModelService.java 
 * @Package com.nork.system.service
 * @Description:系统-模型资源库Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 16:05:22
 * @version V1.0   
 */
public interface ResModelService {
	/**
	 * 新增数据
	 *
	 * @param resModel
	 * @return  int 
	 */
	public int add(ResModel resModel);

	/**
	 *    更新数据
	 *
	 * @param resModel
	 * @return  int 
	 */
	public int update(ResModel resModel);
	/**
	 *    更新数据
	 *
	 * @param resModel
	 * @return  int 
	 */
	public int update(ResModel resModel,Integer businessId,String modelKey);

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
	 * @return  ResModel 
	 */
	public ResModel get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  resModel
	 * @return   List<ResModel>
	 */
	public List<ResModel> getList(ResModel resModel);

	/**
	 *    获取数据数量
	 *
	 * @param  resModel
	 * @return   int
	 */
	public int getCount(ResModelSearch resModelSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  resModel
	 * @return   List<ResModel>
	 */
	public List<ResModel> getPaginatedList(
				ResModelSearch resModeltSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 验证媒介类型
	 * @param mediaType
	 * @param fileId
	 * @return
	 */
	public boolean checkMediaType(String mediaType,Integer fileId);

	/**
	 * 回填模型资源的businessId
	 * @param businessId 业务表ID
	 * @param resModelId 模型资源表ID
	 */
	public void backFillResModel(Integer businessId,Integer resModelId,String modelKey);
	
	/**
	 * 修改之前需先删除当前模型资源信息
	 * @param businessId 业务表ID
	 * @param resModelId 模型资源表ID
	 */
	public void clearBackfillResModel(Integer businessId,Integer resModelId);
	
	/**
	 * 直接删除模型调用此方法
	 * @param businessId 业务表ID
	 * @param resModelId 模型资源表ID
	 */
	public void clearBackfillResModel(Integer businessId,String fileKey,Integer resModelId);
	
	/**
	 * 同类型添加模型数据(更新code和businessId)
	 * @param objectId (产品)id
	 * @param objectCode (产品)code
	 * @return 新生成的model的id
	 */
	public Integer sameTypeAdd(Integer modelId,Integer objectId, String objectCode,HttpServletRequest request);
	
	/**
	 * 更新model数据的code
	 * @param id modelId
	 * @param code 变更后的code
	 */
	public void updateCode(Integer id,String code);
	
	/**
	 *  查询该数据是否有共享一个模型 
	 * @param modelPath
	 * @return
	 */
	public int modelPathCount(String modelPath);

	/**
	 * 同类型添加模型数据(更新code和businessId)
	 * @param objectId (产品)id
	 * @param objectCode (产品)code
	 * @return 新生成的model的id
	 */
	public Integer sameTypeAdd(Integer modelId,Integer objectId, String objectCode,HttpServletRequest request,String fileKey);

	public boolean updatePath(Integer modelId, String designCode);
	
	/**
	 * 属性新增复制模型数据
	 * @param modelId 待复制的model的id
	 * @return 新生成的model的id(生成失败返回0)
	 */
	public Integer copyModel(Integer modelId,HttpServletRequest request);
	
	ResEntity selectResEntity(Integer id);
	
	/**
	 * 保存ResEntity对象
	 * @param resEntity
	 * @return
	 */
	public int insertEntity(ResEntity resEntity);

	/**
	 * 通过产品id得到该pc模型的code
	 * @author huangsongbo
	 * @param productId
	 * @return
	 */
	public String getModelCodeByProductId(Integer productId);
	
	public boolean decompressModel(Integer modelId, String productCode);
	
}
