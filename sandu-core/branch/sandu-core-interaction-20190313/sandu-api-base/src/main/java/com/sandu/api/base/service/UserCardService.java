package com.sandu.api.base.service;


import com.sandu.api.base.common.LoginUser;
import com.sandu.api.base.common.exception.BizException;
import com.sandu.api.base.model.UserCard;
import com.sandu.api.base.output.UserCardVo;

public interface UserCardService {

    /**
     * 根据用户id获取名片
     *
     * @param userId
     * @return
     * @throws BizException
     */
    UserCardVo getUserCardByUserId(Integer userId) throws BizException;

    /**
     * 功能描述
     *
     * @param userCard
     * @return boolean
     * @throws BizException
     * @author gaoj
     * @date 2019/1/7
     */
    boolean insertOrUpdate(UserCard userCard) throws BizException;

    /**
     * 权限：（谁能看到这个图标） 账号层级（3个条件必须同时满足才可以看到入口）
     * 1、有账号（包含正式和试用）——能登录的都有账号。
     * 2、角色：厂商内部用户+厂商经销商+独立经销商。
     *          B端所有角色都可以 update by gaoj 20190218
     * 3、该厂商或独立经销商已在三度开通企业小程序/门店小程序。
     *
     * @param userId
     * @return boolean
     * @throws BizException
     * @author gaoj
     * @date 2019/1/7
     */
    boolean checkUserHaveUserCard(Integer userId) throws BizException;

    /**
     * 获取微信小程序二维码
     *
     * @param companyId
     * @param loginUser
     * @return
     * @throws BizException
     */
    String getWXACode(Integer companyId, LoginUser loginUser, Integer userCardId) throws BizException;

    UserCard getById(Integer userCardId) throws BizException;
}
