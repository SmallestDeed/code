package com.sandu.cart.dao;

import java.util.List;

import com.sandu.cart.MallBaseCart;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Title: MallBaseCartMapper.java
 * @Package com.nork.mini.cart.dao
 * @Description:小程序-购物车Mapper
 * @createAuthor pandajun
 * @CreateDate 2018-03-28 10:57:15
 * @version V1.0
 */
@Repository
@Transactional
public interface MallBaseCartMapper {
    int insertSelective(MallBaseCart record);

    int updateByPrimaryKeySelective(MallBaseCart record);

    int deleteByPrimaryKey(Integer id);

    MallBaseCart selectByPrimaryKey(Integer id);

    List<MallBaseCart> selectList(MallBaseCart mallBaseCart);

    MallBaseCart getByUserId(Integer userId);
    /**
     * 其他
     *
     */
}
