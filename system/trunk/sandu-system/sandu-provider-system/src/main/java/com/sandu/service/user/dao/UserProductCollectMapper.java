package com.sandu.service.user.dao;

import com.sandu.api.user.model.UserProductCollect;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/7 14:25
 * @since 1.8
 */

@Repository
public interface UserProductCollectMapper {

    List<UserProductCollect> listUserProductCollect(UserProductCollect collect);
}
