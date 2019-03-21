package com.sandu.test.draw.v2.service;

import com.sandu.api.v2.house.bo.DrawHouseSubmitBO;
import com.sandu.api.v2.house.bo.DrawSpaceBO;

import java.util.List;

public interface DrawHouseSubmitService {
    void beforeSaveHandler(List<DrawSpaceBO> drawSpaceBOS);

    <T> T handlerHouseSubmit(DrawHouseSubmitBO houseSubmitBO);

    Integer batchSaveByHouseSubmit(List<DrawSpaceBO> drawSpaceBOS);
}