package com.sandu.service.fix.dao;

import com.sandu.api.fix.model.FixCupboardQuery;
import com.sandu.api.house.bo.DrawBaseHouseBO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FixCupboardMapper {
    Integer countFixCupboardHouse(FixCupboardQuery query);

    List<DrawBaseHouseBO> listFixCupboardHouse(FixCupboardQuery query);
}
