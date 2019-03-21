package com.sandu.service.pointmall.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.pointmall.model.ImallGiftInout;
import com.sandu.api.pointmall.model.query.GiftQuery;
import com.sandu.api.pointmall.model.vo.GiftSingleVo;
import com.sandu.api.pointmall.model.vo.GiftVo;
import com.sandu.api.pointmall.service.GiftService;
import com.sandu.service.pointmall.dao.GiftDao;
import com.sandu.service.pointmall.dao.GiftInoutDao;

@Service("giftService2")
@Transactional
public class GiftServiceImpl implements GiftService {
    @Autowired
    private GiftDao giftDao;

    @Autowired
    private GiftInoutDao giftInoutDao;

    @Override
    public List<GiftVo> getGiftList() {

        GiftQuery giftEntity = new GiftQuery();

       return  giftDao.findFrontList(giftEntity);


    }

    @Override
    public GiftVo getGiftById(int giftId) {
        return (GiftVo) giftDao.get(giftId);

    }

    @Override
    public GiftSingleVo getGiftAndImgsById(int giftId) {
        return giftDao.getGiftAndImgsById(giftId);
    }

    @Override
    public int updateInventory(int inventory, int id) {
        return giftDao.updateInventory(inventory,id);
    }

    @Override
    public int insertGiftInoutDaoSelective(ImallGiftInout record) {
        return giftInoutDao.insertGiftInoutDaoSelective(record);
    }
}
