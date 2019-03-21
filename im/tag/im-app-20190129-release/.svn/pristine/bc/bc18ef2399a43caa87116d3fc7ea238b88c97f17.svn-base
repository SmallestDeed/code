package com.sandu.im.service;

import com.sandu.im.dao.BaseHouseDao;
import com.sandu.im.model.BaseHouse;
import com.sandu.im.model.ResHousePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BaseHouseService {

    @Autowired
    private BaseHouseDao baseHouseDao;

    public List<BaseHouse> queryBaseHouseInfo(Set<Integer> houseIds) {
        return baseHouseDao.selectBaseHouseInfo(houseIds);
    }

    public ResHousePic getHousePicPath(Integer picId) {
        return baseHouseDao.getHousePicPath(picId);
    }

    public String getLivingName(String code) {
        return baseHouseDao.getLivingName(code);
    }

    public List<String> getSpaceTypeListByHouseId(Integer id) {
        return baseHouseDao.getSpaceTypeListByHouseId(id);
    }
}
