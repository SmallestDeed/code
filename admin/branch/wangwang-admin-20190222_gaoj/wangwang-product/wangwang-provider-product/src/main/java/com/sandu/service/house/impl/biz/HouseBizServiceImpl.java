package com.sandu.service.house.impl.biz;

import com.sandu.api.house.input.HouseAdd;
import com.sandu.api.house.input.HouseUpdate;
import com.sandu.api.house.model.House;
import com.sandu.api.house.service.HouseService;
import com.sandu.api.house.service.biz.HouseBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * house
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Jul-31 14:14
 */
@Slf4j
@Service("houseBizService")
public class HouseBizServiceImpl implements HouseBizService {

    @Autowired
    private HouseService houseService;

    @Override
    public int create(HouseAdd input) {
        House.HouseBuilder builder = House.builder();

        House house = builder.build();
        BeanUtils.copyProperties(input, house);

        return houseService.insert(house);
    }

    @Override
    public int update(HouseUpdate input) {
        House.HouseBuilder builder = House.builder();
        House house = builder.build();

        BeanUtils.copyProperties(input, house);
        //转换原字段ID
        house.setId(input.getHouseId());
        return houseService.update(house);
    }



    @Override
    public House getById(int houseId) {
        return houseService.getById(houseId);
    }


}
