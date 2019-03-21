package com.sandu.api.v2.house.service;

import com.sandu.api.house.model.DrawBakeTaskDetail;
import com.sandu.api.house.model.DrawBaseHouse;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/16
 */

public interface CleanDrawHouseHandler {
    Integer cleanOldDrawHouse(DrawBaseHouse drawHouse, DrawBakeTaskDetail subTask);
}
