package com.nork.home.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.home.model.BaseHouse;
import com.nork.home.model.DrawBaseHouse;
import com.nork.home.model.search.DrawBaseHouseQuery;

/**
 * 
 * @author songjianming@sanduspace.cn
 * 2018 2018年1月27日
 *
 */
@Repository
@Transactional
public interface DrawBaseHouseMapper {
    List<DrawBaseHouse> listDrawHouse(DrawBaseHouseQuery query);
    Long countDrawHouse(DrawBaseHouseQuery query);
    
    Integer deleteDrawHouse(BaseHouse house);
    
    int getDealSpaceByHouseId(Long houseId);
}
