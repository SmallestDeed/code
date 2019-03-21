package com.sandu.node.dao;

import com.sandu.node.model.UserNodeInfoRel;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserNodeInfoRelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserNodeInfoRel record);

    int insertSelective(UserNodeInfoRel record);

    UserNodeInfoRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserNodeInfoRel record);

    int updateByPrimaryKey(UserNodeInfoRel record);

    List<UserNodeInfoRel> getUserNodeInfoRel(@Param("start") Integer start,
                                             @Param("size") Integer size);

    Integer updateUserNodeInfoRelByUnique(UserNodeInfoRel userNodeInfoRel);

    List<Map<String, Object>> getUserContent(@Param("start") Integer start,
                                      @Param("size") Integer size,
                                      @Param("nodeType") Integer nodeType,
                                      @Param("detailType") Integer detailType);

    List<UserNodeInfoRel> getUserNodeInfoRelSelective(UserNodeInfoRel userNodeInfoRel);
}