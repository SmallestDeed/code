package com.sandu.service.pointmall.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.pointmall.model.vo.GiftImageVo;
import com.sandu.api.pointmall.service.GiftImageService;
import com.sandu.service.pointmall.dao.GiftImageDao;

@Service("giftImageService")
@Transactional
public class GiftImageServiceImpl implements GiftImageService {
    @Autowired
    private GiftImageDao giftImageDao;


    @Override
    public List<String> getGiftImageList(int giftId) {
        return giftImageDao.getGiftImageList(giftId);
    }

    @Override
    public GiftImageVo getGiftImageById(int id, int giftId, int cover) {
        return giftImageDao.getGiftImageById(id,giftId,cover);
    }
}
