package com.sandu.service.pointmall.dao;

import java.util.List;

/***
 * DAO基本方法接口
 * @author Administrator
 *
 * @param <T>
 */
public interface CrudDao<T,Q,V> extends BaseDao {

	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(long id);
	
	/**
	 * 获取单个数据
	 * @param entity
	 * @return
	 */
	public T get(T entity);
	
	/**
	 * 分页查询数据列表(供后台调用)
	 * @param queryEntity
	 * @return T
	 */
	public List<T> findList(Q queryEntity);
	
	/***
	 * 分页查询数据列表(供前端调用)
	 * @param queryEntity
	 * @return
	 */
	public List<V> findFrontList(Q queryEntity);
	
	/**
	 * 查询所有数据列表(供后台调用)
	 * @param queryEntity
	 * @return
	 */
	public List<T> findAllList(Q queryEntity);
	
	/**
	 * 查询所有数据列表(供前端调用)
	 * @param queryEntity
	 * @return
	 */
	public List<V> findFontAllList(Q queryEntity);
	
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public int insert(T entity);
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public int update(T entity);
	
	/**
	 * 删除数据（一般为逻辑删除，更新is_delete字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(long id);
	
	/**
	 * 删除数据（一般为逻辑删除，更新is_delete字段为1）
	 * @param entity
	 * @return
	 */
	public int delete(T entity);
	
}
