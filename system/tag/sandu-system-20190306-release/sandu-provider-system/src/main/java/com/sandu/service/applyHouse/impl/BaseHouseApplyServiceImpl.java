package com.sandu.service.applyHouse.impl;

import com.sandu.api.applyHouse.model.BaseHouseApply;
import com.sandu.api.applyHouse.service.BaseHouseApplyService;
import com.sandu.service.applyHouse.dao.BaseHouseApplyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("baseHouseApplyService")
public class BaseHouseApplyServiceImpl implements BaseHouseApplyService {

    @Autowired
    private BaseHouseApplyDao baseHouseApplyDao;

    @Override
    public BaseHouseApply selectByPrimaryKey(Integer applyHouseId) {
        return baseHouseApplyDao.selectByPrimaryKey(applyHouseId);
    }

    @Override
    public List<BaseHouseApply> selectApplyHouseOverSixHour() {
        return baseHouseApplyDao.selectApplyHouseOverSixHour();
    }

    @Override
    public boolean updateEnteringAdviceMsgFlag(Integer id) {
        return baseHouseApplyDao.updateEnteringAdviceMsgFlag(id) > 0;
    }

    @Override
    public void batchUpdateEnteringAdviceMsgFlag(List<Integer> houseIds) {
        baseHouseApplyDao.batchUpdateEnteringAdviceMsgFlag(houseIds);
    }
}
