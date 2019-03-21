package com.sandu.service.gift.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.gift.model.*;
import com.sandu.api.gift.service.GiftService;
import com.sandu.service.gift.dao.GiftDao;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/28 18:41
 */
@Service("giftService")
@Slf4j
public class GiftServiceImpl implements GiftService {

    private Logger logger = LoggerFactory.getLogger(GiftServiceImpl.class);

    @Autowired
    private GiftDao giftDao;

    @Transactional
    @Override
    public int insert(Gift gift) {
        int result = giftDao.insert(gift);
        //记录
        GiftInout inout=new GiftInout();
        inout.setGiftId(gift.getId());
        inout.setOperType(GiftInoutStatus.OPER_GIFT);
        inout.setPrice(gift.getPrice());
        inout.setPoint(gift.getPoint());
        inout.setCount(gift.getInventory());
        inout.setCreator(gift.getCreator());
        inout.setRemark("【新增礼品】增加库存"+gift.getInventory());
        giftDao.addImallGiftInout(inout);
        if (result > 0) {
            return gift.getId();
        }
        return 0;
    }

    @Override
    public int update(Gift gift) {
        //Gift model=giftDao.getById(gift.getId());
        //记录
        GiftInout inout=new GiftInout();
        inout.setGiftId(gift.getId());
        inout.setOperType(GiftInoutStatus.OPER_GIFT);
        inout.setPrice(gift.getPrice());
        inout.setPoint(gift.getPoint());
        inout.setCount(gift.getInventory());
        //inout.setInventory(model.getInventory()+gift.getInventory());//获取
        inout.setInventory(gift.getInventory());
        inout.setCreator(gift.getModifier());
        inout.setRemark("【编辑礼品】编辑后库存"+gift.getInventory());
        giftDao.addImallGiftInout(inout);
        return giftDao.update(gift);
    }

    @Override
    public int updatePutaway(Set<Integer> ids,int putaway,String modifier) {
        return giftDao.updatePutaway(ids,putaway,modifier);
    }

    @Override
    public int delete(Set<Integer> ids,String modifier) {
        return giftDao.delete(ids,modifier);
    }

    @Override
    public Gift getById(int id) {
        return giftDao.getById(id);
    }

    @Override
    public PageInfo<Gift> findAll(GiftPO query) {
        PageHelper.startPage(query.getPage(), query.getLimit(),query.getOrderBy());
        List<Gift> results = giftDao.findAll(query);
        return new PageInfo<Gift>(results);
    }

    @Override
    public int insertGiftImage(GiftImage model){
        int result = giftDao.insertGiftImage(model);
        if(result>0) {
            logger.info("insertGiftImage------image---id"+model.getId());
            return model.getId();
        }
        return 0;
    }

    @Override
    public List<GiftImage> getGiftImages(int id){
        return giftDao.getGiftImages(id);
    }

    @Override
    public int deleteGiftImageById(int id,String modifier){
        return giftDao.deleteGiftImageById(id,modifier);
    }

    @Override
    public int updateCover(int id,int giftId,String modifier){
        int result = giftDao.updateCoverDefault(giftId, modifier);//礼品图片重置封面
        logger.info("updateCoverDefault结果 result:{}",result);
        return giftDao.updateCover(id,modifier);
    }

    @Override
    public int updateSmall(int id,int giftId,String modifier){
        int result = giftDao.updateSmallDefault(giftId, modifier);
        logger.info("updateSmallDefault结果 result:{}",result);
        return giftDao.updateSmall(id,modifier);
    }


    @Override
    public String getGiftByCode(String code) {
        return giftDao.getGiftByCode(code);
    }


}
