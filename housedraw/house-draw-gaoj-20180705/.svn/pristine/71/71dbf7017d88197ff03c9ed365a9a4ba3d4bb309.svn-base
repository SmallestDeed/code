package com.sandu.api.v2.house.bo;

import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.house.model.DrawDesignTemplet;
import com.sandu.api.house.model.DrawSpaceCommon;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Setter
@Getter
public class DrawHouseSubmitBO {

    UserLoginBO loginBO;
    DrawBaseHouse drawHouse;
    DrawSpaceBO drawSpaceBO;

    // group index存放
    Map<String, String> idxMap = new HashMap<>();

    public DrawSpaceCommonDTO getDrawSpaceCommonDTO() {
        return drawSpaceBO != null ? drawSpaceBO.getDrawSpaceCommonDTO() : null;
    }

    public DrawSpaceCommon getDrawSpaceCommon() {
        return drawSpaceBO != null ? drawSpaceBO.getDrawSpaceCommon() : null;
    }

    public DrawDesignTemplet getDrawDesignTemplet() {
        return drawSpaceBO != null ? drawSpaceBO.getDrawDesignTemplet() : null;
    }
}