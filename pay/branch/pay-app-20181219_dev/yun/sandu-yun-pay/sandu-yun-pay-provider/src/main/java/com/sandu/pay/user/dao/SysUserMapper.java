package com.sandu.pay.user.dao;

import com.sandu.user.model.SysUser;
import com.sandu.user.model.*;
import com.sandu.user.model.search.SysUserSearch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserMapper.java
 * @Package com.sandu.system.dao
 * @Description:系统-用户Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 */
@Repository
public interface SysUserMapper {
    int insertSelective(SysUser record);

    int updateByPrimaryKeySelective(SysUser record);

    int deleteByPrimaryKey(Integer id);

    SysUser selectByPrimaryKey(Integer id);

    int selectCount(SysUserSearch sysUserSearch);

    List<SysUser> selectPaginatedList(SysUserSearch sysUserSearch);

    List<SysUser> selectList(SysUser sysUser);

    /***
     * 获取系统用户
     * @return
     */
	List<SysUser> getSysList();

    int updateFinanceById(SysUser sysUser);

    List<SysUser> getUserByRoleCode(String roleCode);

    List<SysUser> findOneByLoginName(String loginName);

    /**
     *
     * 修改用户信息（是否开通移动端、开通时间、到期时间）的方法
     *
     * @param sysUser
     */
    void updateUserMobileInfo(SysUser sysUser);
    List<SysUserLevelBo> getLevelInfo(SysUserLevelConfig levelConfig);
    List<SysUserLevelPrice> getListByUserId(@Param("userId") Integer userId);
    void updateById(@Param("id") int id, @Param("levelId") int levelId);
    
    /**
     * 查询用户列表
     * @param user
     * @return
     */
	List<SysUser> selectUserList(SysUser user);
    /**
     * 获取经销商用户公司信息列表
     *
     * @param mobile 手机号码
     * @return
     */
    List<CompanyVo> getFranchiserCompanyVoList(@Param("mobile") String mobile);

    /**
     * 获取厂商用户公司信息列表
     *
     * @param mobile 手机号码
     * @return
     */
    List<CompanyVo> getManufacturerCompanyVoList(@Param("mobile") String mobile);
}
