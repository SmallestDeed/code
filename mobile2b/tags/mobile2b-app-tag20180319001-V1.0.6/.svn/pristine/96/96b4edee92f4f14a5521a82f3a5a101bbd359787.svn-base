package com.nork.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.demo.dao.ExpMapper;
import com.nork.demo.model.Exp;
import com.nork.demo.model.search.ExpSearch;
import com.nork.demo.service.ExpService;

/**   
 * @Title: ExpServiceImpl.java 
 * @Package com.nork.demo.service.impl
 * @Description:演示模块-参考例子ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-17 20:11:49
 * @version V1.0   
 */
@Service("expService")
@Transactional
public class ExpServiceImpl implements ExpService {

	private ExpMapper expMapper;

	@Autowired
	public void setExpMapper(
			ExpMapper expMapper) {
		this.expMapper = expMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param exp
	 * @return  int 
	 */
	@Override
	public int add(Exp exp) {
		expMapper.insertSelective(exp);
		return exp.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param exp
	 * @return  int 
	 */
	@Override
	public int update(Exp exp) {
		return expMapper
				.updateByPrimaryKeySelective(exp);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return expMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  Exp 
	 */
	@Override
	public Exp get(Integer id) {
		return expMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  exp
	 * @return   List<Exp>
	 */
	@Override
	public List<Exp> getList(Exp exp) {
	    return expMapper.selectList(exp);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  exp
	 * @return   int
	 */
	@Override
	public int getCount(ExpSearch expSearch){
		return  expMapper.selectCount(expSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  exp
	 * @return   List<Exp>
	 */
	@Override
	public List<Exp> getPaginatedList(
			ExpSearch expSearch) {
		return expMapper.selectPaginatedList(expSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
