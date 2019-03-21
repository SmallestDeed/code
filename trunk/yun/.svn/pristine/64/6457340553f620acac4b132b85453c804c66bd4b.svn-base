package com.sandu.user.service;

import com.sandu.user.model.*;
import com.sandu.user.model.search.SysUserSearch;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserService.java
 * @Package com.sandu.system.service
 * @Description:系统-用户Service
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 */
public interface SysUserService {
    /**
     * 新增数据
     *
     * @param sysUser
     * @return int
     */
    int add(SysUser sysUser);

    /**
     * 更新数据
     *
     * @param sysUser
     * @return int
     */
    int update(SysUser sysUser);


    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);


    /**
     * 获取数据详情
     *
     * @param id
     * @return SysUser
     */
    SysUser get(Integer id);

    /**
     * 所有数据
     *
     * @param sysUser
     * @return List<SysUser>
     */
    List<SysUser> getList(SysUser sysUser);

    /**
     * 获取数据数量
     *
     * @param sysUserSearch
     * @return int
     */
    int getCount(SysUserSearch sysUserSearch);

    /**
     * 分页获取数据
     *
     * @param sysUsertSearch
     * @return List<SysUser>
     */
    List<SysUser> getPaginatedList(SysUserSearch sysUsertSearch);

    /**
     * 用户登录验证
     *
     * @param account
     * @param password
     * @return
     */
    SysUser checkUserAccount(String account, String password);

    /***
     * 获取系统用户
     * @return
     */
    List<SysUser> getSysList();

    /**
     * 更新用户度币
     *
     * @param user
     * @return
     */
    boolean updateFinance(SysUser user);

    /**
     * 通过角色CODE查询用户
     *
     * @param roleCode
     * @return
     */
    List<SysUser> getUserByRoleCode(String roleCode);

    /**
     * 根据用户昵称查询用户对象
     *
     * @param loginName
     * @return
     */
    List<SysUser> findOneByLoginName(String loginName);

    /**
     * 获取缓存临时用户对象
     *
     * @param userTempKey
     * @return
     */
    UserSO getUserTempObjCache(String userTempKey);

    /**
     * 修改用户信息（是否开通移动端、开通时间、到期时间）的方法
     *
     * @param userId
     * @return
     */
    void updateUserMobileInfo(Integer userId);


    public SysUserLevelBo getLevelInfo(SysUserLevelConfig levelConfig);

    /**
     * add by yanghz
     * addUserLevelLimit 方法描述：根据用户id获取所属组群类型的价格等级列表
     *
     * @param userId
     * @return List<SysUserLevelPrice> 返回类型
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    List<SysUserLevelPrice> getListByUserId(Integer userId);

    void updateById(int id, int levelId);

    SysUser getMiniUser(String openId);
    
    /**
     * 获取经销商用户公司信息列表
     *
     * @param mobile 手机号码
     * @return
     */
    List<CompanyVo> getFranchiserCompanyVoList(String mobile);

    /**
     * 获取厂商用户公司信息列表
     *
     * @param mobile 手机号码
     * @return
     */
    List<CompanyVo> getManufacturerCompanyVoList(String mobile);
}
