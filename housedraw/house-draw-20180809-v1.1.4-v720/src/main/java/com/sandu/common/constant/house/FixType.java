package com.sandu.common.constant.house;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FixType {

    DEFAULTS(0, "非修复数据（默认）"),
    FIX_CUPBOARD(1, "修复橱柜");

    Integer status;
    String message;
}
