package com.nork.product.dao2;

import java.io.Serializable;
import java.util.List;
/***
 * @Title: BaseMapper.java 
 * @Package com.nork.product.dao
 * @Description:通用的Mapper
 * @author yangzhun
 * @CreateDate 2017-06-07 20:03:47
 * @param <T>
 */
public interface BaseMapper<T extends Serializable> {
	/**
	 * 新增数据
	 *
	 * @param T
	 * @return  int 
	 */
	public int insertSelective(T t);

	/**
	 *    更新数据
	 *
	 * @param T
	 * @return  int 
	 */
	public int updateByPrimaryKeySelective(T t);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int deleteByPrimaryKey(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  T 
	 */
	public T selectByPrimaryKey(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  T
	 * @return   List
	 */
	public List selectList(T t);

	/**
	 *    获取数据数量
	 *
	 * @param  T
	 * @return   int
	 */
	public int selectCount(T t);

	/**
	 *    分页获取数据
	 *
	 * @param  T
	 * @return   List
	 */
	public List selectPaginatedList(T t);
}
