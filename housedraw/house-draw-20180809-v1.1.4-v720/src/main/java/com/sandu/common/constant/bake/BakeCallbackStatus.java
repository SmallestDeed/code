package com.sandu.common.constant.bake;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BakeCallbackStatus {
    FAILURE(1, "烘焙失败"),
    SUCCEED(2, "烘焙成功");

    Integer status;
    String message;
}
