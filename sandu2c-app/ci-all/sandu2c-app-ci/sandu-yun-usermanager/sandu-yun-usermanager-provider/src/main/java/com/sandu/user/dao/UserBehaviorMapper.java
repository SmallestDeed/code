package com.sandu.user.dao;

import com.sandu.user.model.UserBehavior;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBehaviorMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserBehavior record);

    int insertSelective(UserBehavior record);

    UserBehavior selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserBehavior record);

    int updateByPrimaryKey(UserBehavior record);

    List<UserBehavior> selectLikePrimaryKey(@Param("id") String id);
}
