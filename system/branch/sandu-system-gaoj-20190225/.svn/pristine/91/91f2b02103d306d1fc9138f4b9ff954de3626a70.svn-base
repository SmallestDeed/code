package com.sandu.service.pointmall.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.pointmall.model.UserPointInout;
import com.sandu.api.pointmall.model.query.GiftQuery;
import com.sandu.api.pointmall.model.vo.GiftVo;
import com.sandu.api.pointmall.model.vo.PointVo;

@Repository
public interface UserPointDao extends CrudDao<UserPointDao, GiftQuery, GiftVo> {

    PointVo getPointVo(@Param("uid") int uid);

    UserPointInout selectLastPoint(@Param("uid") int uid);

    int insertSelective(UserPointInout record);
}
