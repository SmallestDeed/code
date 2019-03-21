package com.sandu.api.house.service;

import com.sandu.api.house.model.DesignTempletJumpPositionRel;
import com.sandu.api.house.model.DrawBaseHouse;

import java.util.List;

public interface DesignTempletJumpPositionRelService {
    void transformJumpPositionRel(DrawBaseHouse drawHouse);

    int batchInsertSelective(List<DesignTempletJumpPositionRel> records);
}
