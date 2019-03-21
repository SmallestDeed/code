package com.sandu.user.dao;


import com.sandu.user.model.UserGuideStepMini;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserGuideStepMiniMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserGuideStepMini record);

    int insertSelective(UserGuideStepMini record);

    UserGuideStepMini selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserGuideStepMini record);

    int updateByPrimaryKey(UserGuideStepMini record);

    UserGuideStepMini selectByUserId(@Param("userId") int userId);
}