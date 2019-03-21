package com.sandu.goods.service.impl;

import java.math.BigInteger;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.reflect.TypeToken;
import com.sandu.cache.service.RedisService;
import com.sandu.common.constant.GlobalConstant;
import com.sandu.common.utils.JsonUtils;
import com.sandu.goods.dao.GoodsRecommendDao;
import com.sandu.goods.model.GoodsRecommend;
import com.sandu.goods.model.query.GoodsRecommendQuery;
import com.sandu.goods.model.vo.GoodsRecommendVo;
import com.sandu.goods.service.GoodsRecommendService;
import com.sandu.matadata.CacheKeys;
import com.sandu.matadata.Page;
import com.sandu.sys.model.query.ProductCategoryQuery;
import com.sandu.sys.model.vo.ProductCategoryVo;

@Service("goodsRecommendService")
public class GoodsRecommendServiceImpl implements GoodsRecommendService {
    @Autowired
    private GoodsRecommendDao productRecommendDao;
    @Autowired
    private RedisService redisService;

    private void deleteCache(BigInteger companyId) {
        String key = CacheKeys.getCompanyRecommendProduct(companyId);
        redisService.del(key);
    }

    @Override
    public boolean add(GoodsRecommend recommend) {
        boolean isAdded = false;
        recommend.preInsert();
        int result = productRecommendDao.insert(recommend);
        if (result > 0)
            isAdded = true;
        if (isAdded)
            deleteCache(BigInteger.valueOf(recommend.getCompanyId()));
        return isAdded;
    }

    public boolean batchAdd(List<GoodsRecommend> lstGoods, long companyId) {
        boolean isAdded = false;
        if (lstGoods != null && lstGoods.size() > 0) {
            int result = productRecommendDao.batchInsert(lstGoods);
            if (result > 0)
                isAdded = true;
            if (isAdded)
                deleteCache(BigInteger.valueOf(companyId));
        }
        return isAdded;
    }

    @Override
    public boolean delete(long id, long companyId) {
        boolean isDeleted = false;
        int result = productRecommendDao.delete(id);
        if (result > 0)
            isDeleted = true;
        if (isDeleted)
            deleteCache(BigInteger.valueOf(companyId));
        return isDeleted;
    }

    public boolean batchDelete(GoodsRecommendQuery query) {
        boolean isDeleted = false;
        List<Integer> lstGoodsIds = new ArrayList<Integer>();
        if (StringUtils.isNotEmpty(query.getGoodsIds())) {
            String[] goodsIds = query.getGoodsIds().split(",");
            for (String goodsId : goodsIds) {
                lstGoodsIds.add(Integer.parseInt(goodsId));
            }
            query.setLstGoodsIds(lstGoodsIds);
        }
        int result = productRecommendDao.batchDelete(query);
        if (result > 0)
            isDeleted = true;
        if (isDeleted)
            deleteCache(query.getCompanyId());
        return isDeleted;
    }

    @Override
    public Page<GoodsRecommendVo> getList(GoodsRecommendQuery query) {
        Page<GoodsRecommendVo> page = new Page<GoodsRecommendVo>();
        long total = productRecommendDao.findFontCount(query);
        page.setCount(total);
        List<GoodsRecommendVo> lstVo = productRecommendDao.findFontPageList(query);
        if (lstVo != null && lstVo.size() > 0) {
            page.setList(lstVo);
        }
        return page;
    }

    @Override
    public Map<String,Object> getTopList(GoodsRecommendQuery query) {
        int total = productRecommendDao.countTopList(query);
        if (total > 0) {
            List<GoodsRecommendVo> lstProduct = productRecommendDao.findTopList(query);
            if (lstProduct != null) {
                Map<String,Object> result = new HashMap<>();
                result.put("total",total);
                result.put("list",lstProduct);
                return result;
            }
        }
        return Collections.emptyMap();
    }

}
