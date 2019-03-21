package com.sandu.service.base.dao;

import com.sandu.api.base.model.GroupProductImageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/11/22
 * @since : sandu_yun_1.0
 */
@Repository
public interface CommonMapper {
    List<GroupProductImageInfo> getAllGroupProductImageInfoList();

    int batchUpdateResSmallPicPath(List<GroupProductImageInfo> list);


    int updateResSmallPicPath(@Param("imageInfo")GroupProductImageInfo imageInfo);
}
