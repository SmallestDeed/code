package com.sandu.service.banner.dao;

import com.sandu.api.banner.model.ActShareImgAdmin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActShareImgAdminMapper {
    int insert(ActShareImgAdmin actShareImgAdmin);

    int update(ActShareImgAdmin actShareImgAdmin);

    List<ActShareImgAdmin> select(@Param("page") Integer page, @Param("limit")Integer limit);

    List<ActShareImgAdmin> getActShareImgByBannerId(Long bannerId);
}
