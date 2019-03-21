package com.sandu.api.v2.house.bo;

import com.sandu.common.constant.RegionMarkConstant;

public class RegionMark {

    private int index;
    private RegionMarkConstant region;

    public RegionMark(RegionMarkConstant region) {
        this.region = region;
        this.index = region.getIndex();
    }

    public int getRegionMark(String valueKey) {
        if (this.region.getValuekey().equals(valueKey)) {
            return index++;
        }
        return -1;
    }
}