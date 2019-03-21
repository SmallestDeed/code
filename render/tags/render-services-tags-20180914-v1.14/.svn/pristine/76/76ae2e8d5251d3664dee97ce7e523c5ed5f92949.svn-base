package com.nork.customerservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.customerservice.dao.SysFaqMapper;
import com.nork.customerservice.model.SysFaq;
import com.nork.customerservice.model.search.SysFaqSearch;
import com.nork.customerservice.service.SysFaqService;

/**   
 * @Title: SysFaqServiceImpl.java 
 * @Package com.nork.customerservice.service.impl
 * @Description:客服中心-常见问题ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-04-27 14:35:55
 * @version V1.0   
 */
@Service("sysFaqService")
public class SysFaqServiceImpl implements SysFaqService {

	private SysFaqMapper sysFaqMapper;

	@Autowired
	public void setSysFaqMapper(
			SysFaqMapper sysFaqMapper) {
		this.sysFaqMapper = sysFaqMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysFaq
	 * @return  int 
	 */
	@Override
	public int add(SysFaq sysFaq) {
		sysFaqMapper.insertSelective(sysFaq);
		return sysFaq.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysFaq
	 * @return  int 
	 */
	@Override
	public int update(SysFaq sysFaq) {
		return sysFaqMapper
				.updateByPrimaryKeySelective(sysFaq);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysFaqMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysFaq 
	 */
	@Override
	public SysFaq get(Integer id) {
		return sysFaqMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysFaq
	 * @return   List<SysFaq>
	 */
	@Override
	public List<SysFaq> getList(SysFaq sysFaq) {
	    return sysFaqMapper.selectList(sysFaq);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysFaq
	 * @return   int
	 */
	@Override
	public int getCount(SysFaqSearch sysFaqSearch){
		return  sysFaqMapper.selectCount(sysFaqSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysFaq
	 * @return   List<SysFaq>
	 */
	@Override
	public List<SysFaq> getPaginatedList(
			SysFaqSearch sysFaqSearch) {
		return sysFaqMapper.selectPaginatedList(sysFaqSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
