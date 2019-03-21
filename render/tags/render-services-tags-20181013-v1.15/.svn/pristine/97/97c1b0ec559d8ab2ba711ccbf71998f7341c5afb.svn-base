package com.nork.demo.service;

import java.util.List;

import com.nork.demo.model.Exp;
import com.nork.demo.model.search.ExpSearch;

/**   
 * @Title: ExpService.java 
 * @Package com.nork.demo.service
 * @Description:演示模块-参考例子Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-17 20:11:49
 * @version V1.0   
 */
public interface ExpService {
	/**
	 * 新增数据
	 *
	 * @param exp
	 * @return  int 
	 */
	public int add(Exp exp);

	/**
	 *    更新数据
	 *
	 * @param exp
	 * @return  int 
	 */
	public int update(Exp exp);

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
	 * @return  Exp 
	 */
	public Exp get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  exp
	 * @return   List<Exp>
	 */
	public List<Exp> getList(Exp exp);

	/**
	 *    获取数据数量
	 *
	 * @param  exp
	 * @return   int
	 */
	public int getCount(ExpSearch expSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  exp
	 * @return   List<Exp>
	 */
	public List<Exp> getPaginatedList(
				ExpSearch exptSearch);

	/**
	 * 其他
	 * 
	 */

}
