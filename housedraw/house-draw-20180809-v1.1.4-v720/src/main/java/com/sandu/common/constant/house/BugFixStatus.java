package com.sandu.common.constant.house;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BugFixStatus {
    APPLY_FIX(0, "待修复"),
    FIX_ERROR(1, "修复异常"),
    FIX_SUCCEED(2, "修复成功"),
    FIX_PROCESS(3, "修复中"),
    FIX_BAKE_SUCCEED(4, "烘焙成功");

    Integer status;
    String message;
}
