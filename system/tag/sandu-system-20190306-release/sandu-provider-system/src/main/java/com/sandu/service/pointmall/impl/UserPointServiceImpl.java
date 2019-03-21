package com.sandu.service.pointmall.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.pointmall.model.UserPointInout;
import com.sandu.api.pointmall.model.vo.PointVo;
import com.sandu.api.pointmall.service.UserPointService;
import com.sandu.service.pointmall.dao.UserPointDao;

@Service("userPointService")
@Transactional
public class UserPointServiceImpl implements UserPointService {
    private Logger logger = LoggerFactory.getLogger(UserPointServiceImpl.class);
    @Autowired
    private UserPointDao userPointDao;

    @Override
    public PointVo getPointVo(int uid) {
        return userPointDao.getPointVo(uid);
    }

    @Override
    public UserPointInout selectLastPoint(int uid) {
        UserPointInout userPointInout = userPointDao.selectLastPoint(uid);
        logger.info("============================:{}",userPointInout);
        return userPointInout;
    }

    @Override
    public int insertSelective(UserPointInout record) {
        return userPointDao.insertSelective(record);
    }
}
