package com.sandu.pay.user.dao;

import com.sandu.user.model.UserJurisdiction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 用户权限详情表对应的mapper
 *
 * @Author yzw
 * @Date 2018/3/20 11:24
 */
@Repository
public interface UserJurisdictionMapper {


    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insertSelective(UserJurisdiction record);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    UserJurisdiction selectByPrimaryKey(Integer id);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(UserJurisdiction record);

    /**
     * 获取开通移动端信息
     *
     * @param mapl
     * @return
     */
    UserJurisdiction getByUserIdAndPlatformId(Map<String, Object> map);
}
