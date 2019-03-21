package com.sandu.pay.user.service.impl;

import com.google.gson.Gson;
import com.sandu.user.model.SysUser;
import com.sandu.cache.RedisKeyConstans;
import com.sandu.cache.service.PayRedisLoginService;
import com.sandu.common.util.Validator;
import com.sandu.common.util.collections.CustomerListUtils;
import com.sandu.common.util.collections.Lists;
import com.sandu.pay.user.dao.SysUserMapper;
import com.sandu.user.model.*;
import com.sandu.user.model.search.SysUserSearch;
import com.sandu.user.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserServiceImpl.java
 * @Package com.sandu.system.service.impl
 * @Description:系统-用户ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    //Json转换类
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[系统用户操作]:";
    private final static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private PayRedisLoginService payRedisLoginService;

    
    /**
     * 新增数据
     *
     * @param sysUser
     * @return int
     */
    @Override
    public int add(SysUser sysUser) {
        sysUserMapper.insertSelective(sysUser);
        return sysUser.getId();
    }

    /**
     * 更新数据
     *
     * @param sysUser
     * @return int
     */
    @Override
    public int update(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return sysUserMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return SysUser
     */
    @Override
    public SysUser get(Integer id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param sysUser
     * @return List<SysUser>
     */
    @Override
    public List<SysUser> getList(SysUser sysUser) {

        return sysUserMapper.selectList(sysUser);
    }

    /**
     * 获取数据数量
     *
     * @param sysUserSearch
     * @return int
     */
    @Override

    public int getCount(SysUserSearch sysUserSearch) {
        return sysUserMapper.selectCount(sysUserSearch);
    }

    /**
     * 分页获取数据
     *
     * @param sysUserSearch
     * @return List<SysUser>
     */
    @Override

    public List<SysUser> getPaginatedList(SysUserSearch sysUserSearch) {
        return sysUserMapper.selectPaginatedList(sysUserSearch);
    }

    /**
     * 用户登录验证
     */

    @Override
    public SysUser checkUserAccount(String account, String password) {
        //空参判断
        if (org.springframework.util.StringUtils.isEmpty(account) || org.springframework.util.StringUtils.isEmpty(password)) {
            return null;
        }

        SysUser user = new SysUser();
        user.setIsDeleted(0);
        user.setPassword(password);

        List<SysUser> list;
        //手机登录验证
        if (Validator.MOBILE.validate(account)) {
            user.setMobile(account);
            logger.info(CLASS_LOG_PREFIX + "用户登录验证->常事手机登录");
            list = sysUserMapper.selectList(user);
        } else if (Validator.EAMIL.validate(account)) {
            //邮箱登录验证
            user.setEmail(account);
            list = sysUserMapper.selectList(user);
        } else {
            //昵称登录验证
            user.setNickName(account);
            list = sysUserMapper.selectList(user);
            //用户名登录的支持
            if (CustomerListUtils.isEmpty(list)) {
                user.setUserName(account);
                list = sysUserMapper.selectList(user);
            }
        }

        if (CustomerListUtils.isNotEmpty(list)) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public List<SysUser> getSysList() {
        return sysUserMapper.getSysList();
    }

    @Override
    public boolean updateFinance(SysUser user) {
        int result = sysUserMapper.updateFinanceById(user);
        return result > 0;
    }

    /**
     * 通过角色CODE查询用户
     *
     * @param roleCode
     * @return
     */
    @Override
    public List<SysUser> getUserByRoleCode(String roleCode) {
        if (StringUtils.isNotBlank(roleCode)) {
            //todo  sql未实现
            return sysUserMapper.getUserByRoleCode(roleCode);
        }
        return null;
    }

    @Override
    public List<SysUser> findOneByLoginName(String loginName) {
        //todo sql未实现
        return sysUserMapper.findOneByLoginName(loginName);
    }
    
 
    /**
	 * 获取缓存临时用户对象
	 * @param userTempKey
	 * @return
	 */
	@Override
	public UserSO getUserTempObjCache(String userTempKey){

		logger.info(CLASS_LOG_PREFIX + "userTempKey = "+userTempKey);
		if (StringUtils.isEmpty(userTempKey) && "null".equals(userTempKey)) {
			return null;
		}
		UserSO userSO = null ;
		String userTempObj = payRedisLoginService.getMap(RedisKeyConstans.USER_TEMP_SESSION_MAP,userTempKey);
		logger.info(CLASS_LOG_PREFIX + "userTempObj = " + gson.toJson(userTempObj));
		//获取之后立刻删除该缓存
//		redisService.delMap(RedisKeyConstans.USER_TEMP_SESSION_MAP, userTempKey);
		if (StringUtils.isNotEmpty(userTempObj)) {
			//转换用户对象
			UserTempSessionBo userTempSessionBo = gson.fromJson(userTempObj, UserTempSessionBo.class);
			if (userTempSessionBo.getValidTime()-System.currentTimeMillis() < 0) {
				logger.info(CLASS_LOG_PREFIX + "获取用户会话信息超时！");
				userSO = new UserSO();
				userSO.setUserId(-1);
				return userSO;
			}
			userSO = userTempSessionBo.getUserSO();
			logger.info(CLASS_LOG_PREFIX + "获取临时用户对象UserSO = " + userSO.toString());
		}

		return userSO;
	}

    /**
     * 修改用户信息（是否开通移动端、开通时间、到期时间）的方法
     */
    @Override
    public void updateUserMobileInfo(Integer userId) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        Date date = new Date();// 取当前时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.YEAR, 1);// 把日期往后增加一年.整数往后推,负数往前移动
        Date outOfDate = calendar.getTime(); // 这个时间就是日期往后推一年的结果
        if (sysUser != null) {
            if (sysUser.getExistsMobile() == null || sysUser.getExistsMobile().intValue() == 0) {
                sysUser.setExistsMobile(new Integer(1));
                sysUser.setMobileOpenedDate(date);
                sysUser.setMobileClosedDate(outOfDate);
            } else {
                sysUser.setMobileClosedDate(outOfDate);
            }
            sysUserMapper.updateUserMobileInfo(sysUser);
        }
    }
    
    
    
    @Override
    public SysUserLevelBo getLevelInfo(SysUserLevelConfig levelConfig) {
        if (levelConfig == null || levelConfig.getUserId() < 0 ){
            return null;
        }
        /*return sysUserLevelConfigMapper.getLevelInfo(levelConfig);*/
        List<SysUserLevelBo> sysUserLevelBoList = sysUserMapper.getLevelInfo(levelConfig);
        if(Lists.isEmpty(sysUserLevelBoList)) {
        	return null;
        }else {
        	return sysUserLevelBoList.get(0);
        }
    }
    
    @Override
    public List<SysUserLevelPrice> getListByUserId(Integer userId) {
        if (userId == null || userId < 1){
            return null;
        }
        return sysUserMapper.getListByUserId(userId);
    }
    
   @Override
    public void updateById(int id, int levelId) {
        if (id < 1){
            return;
        }
        sysUserMapper.updateById(id,levelId);
    }
   
   @Override
	public SysUser getMiniUser(String openId) {
		// TODO Auto-generated method stub
		SysUser user = new SysUser();
		user.setOpenId(openId);
		user.setIsDeleted(0);
		List<SysUser> userList = sysUserMapper.selectUserList(user);
		if(userList!=null && userList.size()>0) {
			return userList.get(0);
		}
		return null;
	}

    /**
     * 获取经销商用户公司信息列表
     *
     * @param mobile 手机号码
     * @return
     */
    @Override
    public List<CompanyVo> getFranchiserCompanyVoList(String mobile) {
        return this.sysUserMapper.getFranchiserCompanyVoList(mobile);
    }

    /**
     * 获取厂商用户公司信息列表
     *
     * @param mobile 手机号码
     * @return
     */
    @Override
    public List<CompanyVo> getManufacturerCompanyVoList(String mobile) {
        return this.sysUserMapper.getManufacturerCompanyVoList(mobile);
    }

}
