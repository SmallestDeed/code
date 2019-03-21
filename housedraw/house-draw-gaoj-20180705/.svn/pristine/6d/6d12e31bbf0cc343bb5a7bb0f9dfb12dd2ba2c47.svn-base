package com.sandu.common.constant;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 * <p>
 * 2018年3月19日
 */

public enum RegionMarkConstant {
    // 地面
    MAIN_FLOOR(10, "basic_dimz", "原主地面"),
    PORCH_FLOOR(20, "basic_dimxg", "原玄关地面"),
    AISLE_FLOOR(30, "basic_dimgd", "原过道地面"),

    // 天花
    MAIN_PLATFOND(10, "basic_tianh", "原主天花"),
    PORCH_PLATFOND(20, "basic_xgtianh", "原玄关天花"),
    AISLE_PLATFOND(30, "basic_gdtianh", "原过道天花");

    private int index;
    private String valuekey;
    private String remark;

    public int getIndex() {
        return index;
    }

    public String getValuekey() {
        return valuekey;
    }

    public String getRemark() {
        return remark;
    }

    RegionMarkConstant(int index, String valuekey, String remark) {
        this.index = index;
        this.valuekey = valuekey;
        this.remark = remark;
    }

    public static boolean contains(String valuekey) {
        RegionMarkConstant[] values = values();
        for (RegionMarkConstant mark : values) {
            if (mark.getValuekey().equals(valuekey)) {
                return true;
            }
        }
        return false;
    }
}
