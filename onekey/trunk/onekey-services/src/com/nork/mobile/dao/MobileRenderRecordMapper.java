package com.nork.mobile.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.WhiteDeviceList;
import com.nork.mobile.model.ProductGroupReplaceTaskDetail;
import com.nork.mobile.model.ProductReplaceTaskDetail;
import com.nork.mobile.model.search.MobileRenderRecord;

@Repository
@Transactional
public interface MobileRenderRecordMapper {

	/**
	 * 添加渲染记录
	 * @param record
	 * @return
	 */
	int insertSelective(MobileRenderRecord record);
	
	/**
	 * 根据条件查询移动端所有的渲染记录
	 * @param record
	 * @return
	 */
	List<MobileRenderRecord> selectList(MobileRenderRecord record);
	/**
	 * 插入
	 * @param productReplaceTaskDetail
	 * @return
	 */
	int insertReplace(ProductReplaceTaskDetail productReplaceTaskDetail);
	/**
	 * taskId查询
	 * @param productReplaceTaskDetail
	 * @return
	 */
	List<ProductReplaceTaskDetail> selectListByTaskId(@Param("taskId") Integer taskId);
	/**
	 * 查询所有替换产品
	 * @param productReplaceTaskDetail
	 * @return
	 */
	List<ProductReplaceTaskDetail> selectReplaceProductList(ProductReplaceTaskDetail productReplaceTaskDetail);
	/**
	 * 根据taskId查询替换产品
	 * @param taskId
	 * @return
	 */
	ProductReplaceTaskDetail selectByTaskId(@Param("taskId") Integer taskId);
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	int batchInsertDataList (@Param("list")List<ProductReplaceTaskDetail> list);
	/**
	 * 查询白名单设备号
	 * @param whiteDeviceList
	 * @return
	 */
	List<WhiteDeviceList> checkWhiteList(WhiteDeviceList whiteDeviceList);
	/**
	 * 根据任务ID删除替换产品
	 * @param taskId
	 * @return
	 */
	public int deleteReplaceProductById(@Param("taskId") Integer taskId); 
	
	/**
	 * 根据taskId查询待删除的产品
	 * @param taskId
	 * @return
	 */
	List<ProductReplaceTaskDetail>  selectDelListByTaskId(@Param("taskId") Integer taskId);
	
	/**
	 * 批量插入组合替换产品
	 * @param list
	 * @return
	 */
	int batchInsertGroupList (@Param("list")List<ProductGroupReplaceTaskDetail>list);
	
	/**
	 * taskId查询组合替换
	 * @param ProductGroupReplaceTaskDetail
	 * @return
	 */
	List<ProductGroupReplaceTaskDetail> selectGroupListByTaskId(@Param("taskId") Integer taskId);
	/**
	 * 根据任务ID删除组合替换产品
	 * @param taskId
	 * @return
	 */
	public int deleteReplaceGroupProductByTaskId(@Param("taskId") Integer taskId);
	
	/**
	 * 批量插入材质替换产品
	 * @param list
	 * @return
	 */
	int batchInsertTextureList (@Param("list")List<ProductReplaceTaskDetail>list);
	
	public int  insertGroupProductReplace(ProductGroupReplaceTaskDetail productGroupReplaceTaskDetail); 
	
	/**
	 * taskId查询材质替换
	 * @param ProductGroupReplaceTaskDetail
	 * @return
	 */
	List<ProductReplaceTaskDetail> selectTextureListByTaskId(@Param("taskId") Integer taskId);
	/**
	 * 根据任务ID删除材质替换产品
	 * @param taskId
	 * @return
	 */
	public int deleteReplaceTextureProductByTaskId(@Param("taskId") Integer taskId);
}
