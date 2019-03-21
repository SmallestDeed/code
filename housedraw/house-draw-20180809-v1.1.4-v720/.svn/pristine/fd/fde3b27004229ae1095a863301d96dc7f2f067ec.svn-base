package com.sandu.common.mysql;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface MysqlExecutor {

    @Getter
    @AllArgsConstructor
    enum BATCH {
        UPDATE(10, "批量更新数量"),
        DELETE(100, "批量删除数量"),
        INSERT(100, "批量插入数量");

        int count;
        String remark;
    }

    @Getter
    @AllArgsConstructor
    enum QUERY {
        DEFAULTS_PAGE_INDEX(0),
        DEFAULTS_PAGE_SIZE(20);

        int value;
    }
}
