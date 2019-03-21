package com.sandu.cart.service.impl;

import com.sandu.cart.MallCartProductRef;
import com.sandu.cart.dao.MallCartProductRefMapper;
import com.sandu.cart.service.MallCartProductRefService;
import com.sandu.goods.model.BaseGoodsSKU;
import com.sandu.goods.service.BaseGoodsSKUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Title: MallCartProductRefServiceImpl.java
 * @Package com.nork.mini.cart.service.impl
 * @Description:小程序-购物车产品关联表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2018-03-28 14:07:30
 */
@Service("mallCartProductRefService")
public class MallCartProductRefServiceImpl implements MallCartProductRefService {

    @Autowired
    private MallCartProductRefMapper mallCartProductRefMapper;
    @Autowired
    private BaseGoodsSKUService baseGoodsSKUService;


    /**
     * 新增数据
     *
     * @param mallCartProductRef
     * @return int
     */
    @Override
    public int add(MallCartProductRef mallCartProductRef) {
        if (null == mallCartProductRef || null == mallCartProductRef.getCartId()) {
            return -1;
        }
        mallCartProductRef.setGmtCreate(new Date());
        mallCartProductRef.setGmtModified(new Date());
        mallCartProductRef.setIsDeleted(0);
        mallCartProductRefMapper.insertSelective(mallCartProductRef);
        return mallCartProductRef.getId();
    }

    /**
     * 更新数据
     *
     * @param mallCartProductRef
     * @return int
     */
    @Override
    public int update(MallCartProductRef mallCartProductRef) {
        return mallCartProductRefMapper.updateByPrimaryKeySelective(mallCartProductRef);
    }

    /**
     * 删除数据
     *
     * @param cartProductIdList
     * @return int
     */
    @Override
    public int delete(List<Integer> cartProductIdList) {
        if (null == cartProductIdList || cartProductIdList.isEmpty()) {
            return -1;
        }
        return mallCartProductRefMapper.deleteByIds(cartProductIdList);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return MallCartProductRef
     */
    @Override
    public MallCartProductRef get(Integer id) {
        return mallCartProductRefMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param mallCartProductRef
     * @return List<MallCartProductRef>
     */
    @Override
    public List<MallCartProductRef> getList(MallCartProductRef mallCartProductRef) {
        List<MallCartProductRef> productList = mallCartProductRefMapper.selectList(mallCartProductRef);
        if (productList != null && productList.size() > 0) {
            List<Integer> productIdList = new ArrayList<>();
            for (MallCartProductRef cartProductRef : productList) {
                productIdList.add(cartProductRef.getProductId());
            }
            List<BaseGoodsSKU> skuList = baseGoodsSKUService.getListByProductIdList(productIdList);
            if (skuList != null && skuList.size() > 0) {
                for (MallCartProductRef cartProductRef : productList) {
                    for (BaseGoodsSKU baseGoodsSKU : skuList) {
                        if (baseGoodsSKU.getProductId().equals(cartProductRef.getProductId())) {
                            if (!cartProductRef.getProductPrice().equals(baseGoodsSKU.getPrice())) {
                                cartProductRef.setProductPrice(baseGoodsSKU.getPrice() == null? 0 : baseGoodsSKU.getPrice().doubleValue());
                            }
                        }
                    }
                }
            }
        }
        return productList;
    }

    @Override
    public List<MallCartProductRef> getMallCartProductRef(MallCartProductRef mallCartProductRef) {
        return mallCartProductRefMapper.selectMallCartProductRefByProductId(mallCartProductRef);
    }

    @Override
    public Integer deleteByProductIds(List<Integer> cartProductIdList, Integer userId) {
        return mallCartProductRefMapper.deleteByProductIds(cartProductIdList, userId);
    }

    /**
     * 其他
     *
     */


}
