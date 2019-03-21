package com.sandu.common.constant.attr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class DoorAttr {

    public boolean contains(String smallProType) {
        if (!StringUtils.isNoneBlank(smallProType)) {
            return false;
        }

        DoorAttrType[] attrTypes = DoorAttrType.values();
        for (DoorAttrType attrType : attrTypes) {
            if (attrType.getSmallProType().equals(smallProType)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsProAttrType(Integer proAttrType) {
        if (null == proAttrType) {
            return false;
        }

        DoorAttrValue[] attrValues = DoorAttrValue.values();
        for (DoorAttrValue attrValue : attrValues) {
            if (attrValue.getValue().equals(proAttrType)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsProAttr(String proAttrKey) {
        if (!StringUtils.isNoneBlank(proAttrKey)) {
            return false;
        }

        DoorAttrType[] attrTypes = DoorAttrType.values();
        for (DoorAttrType attrType : attrTypes) {
            if (attrType.getLongCode().equals(proAttrKey)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsProAttrVal(String proAttrKeyVal) {
        if (!StringUtils.isNoneBlank(proAttrKeyVal)) {
            return false;
        }

        DoorAttrType[] attrTypes = DoorAttrType.values();
        DoorAttrValue[] attrValues = DoorAttrValue.values();
        for (DoorAttrType attrType : attrTypes) {
            for (DoorAttrValue attrValue : attrValues) {
                if ((attrType.getLongCode() + attrValue.getType()).equals(proAttrKeyVal)) {
                    return true;
                }
            }
        }

        return false;
    }

    public DoorAttrType getDoorAttrType(String smallProType) {
        if (!StringUtils.isNoneBlank(smallProType)) {
            return null;
        }

        DoorAttrType[] attrTypes = DoorAttrType.values();
        for (DoorAttrType attrType : attrTypes) {
            if (attrType.getSmallProType().equals(smallProType)) {
                return attrType;
            }
        }

        return null;
    }

    public DoorAttrValue getDoorAttrValue(Integer type) {
        if (null == type) {
            return null;
        }

        DoorAttrValue[] attrValues = DoorAttrValue.values();
        for (DoorAttrValue attrValue : attrValues) {
            if (attrValue.getValue().equals(type)) {
                return attrValue;
            }
        }

        return null;
    }

    public String getDoorAttrValueType(String smallProType, Integer type) {
        if (!StringUtils.isNoneBlank(smallProType) || type == null) {
            return null;
        }

        DoorAttrType attrType = getDoorAttrType(smallProType);
        if (attrType != null) {
            DoorAttrValue attrValue = getDoorAttrValue(type);
            if (attrValue != null) {
                return attrType.getLongCode() + attrValue.getType();
            }
        }

        return null;
    }

    @Getter
    @AllArgsConstructor
    public enum DoorAttrType {
        CHUFM("basic_chufm", ".root.hard.chufm.type.", "厨房门"),
        RUHM("basic_ruhm", ".root.hard.ruhm.type.", "入户门"),
        FANGJM("basic_fangjm", ".root.hard.fangjm.type.", "房间门"),
        WEISJM("basic_weisjm", ".root.hard.weisjm.type.", "卫生间门");
        final String smallProType;
        final String longCode;
        final String remark;
    }

    @Getter
    @AllArgsConstructor
    public enum DoorAttrValue {
        ZIMM(1, "type1.", "子母门"),
        DANGKM(2, "type2.", "单开门"),
        SHUANGKM(3, "type3.", "双开门"),
        TUILM(5, "type5.", "推拉门");
        final Integer value;
        final String type;
        final String remark;
    }
}
