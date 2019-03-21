package com.sandu.api.v2.house.bo;

import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.house.input.PublicProductInfoDTO;
import com.sandu.api.house.model.DrawDesignTemplet;
import com.sandu.api.house.model.DrawSpaceCommon;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DrawSpaceBO {

    DrawSpaceCommon drawSpaceCommon;
    DrawDesignTemplet drawDesignTemplet;
    List<DrawHouseProductBO> drawHouseProductBOS;

    DrawSpaceCommonDTO drawSpaceCommonDTO;

    /**
     * 空间的区域标识region mark
     */
    List<RegionMark> regionMarks;

    /**
     * 空间的公共部分，窗框/栏杆之类/阳台门
     */
    List<PublicProductInfoDTO> publicProductInfoDTOList;
}
