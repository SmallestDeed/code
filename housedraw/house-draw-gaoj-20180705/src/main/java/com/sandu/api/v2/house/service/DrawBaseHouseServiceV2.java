package com.sandu.api.v2.house.service;

import com.sandu.api.fix.model.FixCupboardSubmitBO;
import com.sandu.api.fix.service.DrawSpaceSubmitFilter;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.v2.house.bo.DrawSpaceBO;

import java.util.List;

public interface DrawBaseHouseServiceV2 {
    void submit(DrawBaseHouseSubmitDTO dtoNew, UserLoginBO loginBO);

    List<DrawSpaceBO> submit(UserLoginBO loginBO,
                             FixCupboardSubmitBO fixCupboard,
                             DrawSpaceSubmitFilter filter);
}
