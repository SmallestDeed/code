package com.sandu.service.house.impl;

import com.sandu.api.house.model.House;
import com.sandu.api.house.service.HouseService;
import com.sandu.service.house.dao.HouseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * house
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Jul-31 14:14
 */
@Slf4j
@Service("houseService")
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Override
    public int insert(House house) {
        int result = houseMapper.insert(house);
        if (result > 0) {
            return house.getId();
        }
        return 0;
    }

    @Override
    public int update(House house) {
        return houseMapper.updateByPrimaryKey(house);
    }

    @Override
    public int delete(Set<Integer> houseIds) {
        return houseMapper.deleteByPrimaryKey(houseIds);
    }

    @Override
    public House getById(int houseId) {
        return houseMapper.selectByPrimaryKey(houseId);
    }

    @Override
    public List<House> getByUserId(int UserId) {
        return houseMapper.getByUserId(UserId);
    }


}
