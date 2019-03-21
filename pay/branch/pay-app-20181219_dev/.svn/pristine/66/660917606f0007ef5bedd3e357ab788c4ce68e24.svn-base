package com.sandu.user.service;

import com.sandu.user.model.UserJurisdiction;

/**
 * 用户权限详情表对应的service
 *
 * @Author yzw
 * @Date 2018/3/20 11:23
 */
public interface UserJurisdictionService {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    UserJurisdiction add(UserJurisdiction record);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean delete(Integer id);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    UserJurisdiction update(UserJurisdiction record);

    /**
     * 获取
     *
     * @param id
     * @return
     */
    UserJurisdiction get(Integer id);

    /**
     * 用户开通移动端（2b）
     *
     * @param userId     用户id
     * @param platformId 平台id
     */
    public void addMobileLogin(Integer userId, Integer platformId);

    /**
     * 获取开通移动端信息
     *
     * @param userId     用户id
     * @param platformId 平台id
     * @return
     */
    UserJurisdiction getByUserIdAndPlatformId(Integer userId, Integer platformId);

    /**
     * 获取开通移动端信息
     *
     * @param userId     用户id
     * @param platformId 用户id
     * @return 如果用户为开通或者已开通并且已过期，则返回null
     */
    UserJurisdiction getMobile2bUserJurisdiction(Integer userId, Integer platformId);
}
