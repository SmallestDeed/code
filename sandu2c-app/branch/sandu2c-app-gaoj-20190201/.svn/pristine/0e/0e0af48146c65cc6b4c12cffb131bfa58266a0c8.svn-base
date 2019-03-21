package com.sandu.cart.service.impl;

import java.util.Date;
import java.util.List;

import com.sandu.cart.MallBaseCart;
import com.sandu.cart.dao.MallBaseCartMapper;
import com.sandu.cart.service.MallBaseCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Title: MallBaseCartServiceImpl.java
 * @Package com.nork.mini.cart.service.impl
 * @Description:小程序-购物车ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2018-03-28 10:57:15
 * @version V1.0
 */
@Service("mallBaseCartService")
public class MallBaseCartServiceImpl implements MallBaseCartService {

	@Autowired
	private MallBaseCartMapper mallBaseCartMapper;

	/**
	 * 新增数据
	 *
	 * @param mallBaseCart
	 * @return  int
	 */
	@Override
	public int add(MallBaseCart mallBaseCart) {
		if (null == mallBaseCart || null == mallBaseCart.getUserId()) {
			return -1;
		}
		mallBaseCart.setGmtCreate(new Date());
		mallBaseCart.setGmtModified(new Date());
		mallBaseCart.setIsDeleted(0);
		mallBaseCartMapper.insertSelective(mallBaseCart);
		return mallBaseCart.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param mallBaseCart
	 * @return  int
	 */
	@Override
	public int update(MallBaseCart mallBaseCart) {
		return mallBaseCartMapper
				.updateByPrimaryKeySelective(mallBaseCart);
	}

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int
	 */
	@Override
	public int delete(Integer id) {
		return mallBaseCartMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  MallBaseCart
	 */
	@Override
	public MallBaseCart get(Integer id) {
		return mallBaseCartMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 *
	 * @param  mallBaseCart
	 * @return   List<MallBaseCart>
	 */
	@Override
	public List<MallBaseCart> getList(MallBaseCart mallBaseCart) {
		return mallBaseCartMapper.selectList(mallBaseCart);
	}

	@Override
	public MallBaseCart getByUserId(Integer userId) {
		if (null == userId) {
			return null;
		}
		return mallBaseCartMapper.getByUserId(userId);
	}

	/**
	 * 其他
	 *
	 */


}
