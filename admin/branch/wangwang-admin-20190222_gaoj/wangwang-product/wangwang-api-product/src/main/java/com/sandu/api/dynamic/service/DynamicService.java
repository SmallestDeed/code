package com.sandu.api.dynamic.service;

import com.sandu.api.dynamic.input.DynamicQuery;
import com.sandu.api.dynamic.model.Dynamic;

import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 14:47 2018/10/24
 */
public interface DynamicService {
    List<Dynamic> queryAll(DynamicQuery query);
}
