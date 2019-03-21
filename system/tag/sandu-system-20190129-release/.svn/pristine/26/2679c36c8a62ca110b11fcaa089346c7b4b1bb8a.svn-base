package com.sandu.service.pointmall.dao;

import org.springframework.stereotype.Repository;

import com.sandu.api.pointmall.model.Users;
import com.sandu.api.pointmall.model.query.GiftQuery;
import com.sandu.api.pointmall.model.vo.GiftVo;

@Repository
public interface UsersDao extends CrudDao<UsersDao, GiftQuery, GiftVo> {

    Users selectByPrimaryKey(Long uid);
}
