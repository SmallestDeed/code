package com.sandu.cart.dao;

import com.sandu.cart.MallCartProductRef;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V1.0
 * @Title: MallCartProductRefMapper.java
 * @Package com.nork.mini.cart.dao
 * @Description:小程序-购物车产品关联表Mapper
 * @createAuthor pandajun
 * @CreateDate 2018-03-28 14:07:30
 */
@Repository
@Transactional
public interface MallCartProductRefMapper {
    int insertSelective(MallCartProductRef record);

    int updateByPrimaryKeySelective(MallCartProductRef record);

    int deleteByIds(List<Integer> cartProductIdList);

    MallCartProductRef selectByPrimaryKey(Integer id);

    List<MallCartProductRef> selectList(MallCartProductRef mallCartProductRef);

    List<MallCartProductRef> selectMallCartProductRefByProductId(MallCartProductRef mallCartProductRef);

    /**
     * 其他
     *
     */
}
