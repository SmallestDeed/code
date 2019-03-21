package com.nork.system.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.system.model.SysUser;

/**   
 * @Title: SysUserService.java 
 * @Package com.nork.system.service
 * @Description:系统-用户Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 12:30:46
 * @version V1.0   
 */
public interface SysUserService {
	
	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysUser 
	 */
	public SysUser get(Integer id);
	
    /**
	 * 用户登录验证
	 * @param account
	 * @param password
	 * @return
	 */
	public boolean checkWhiteList(String terminalImei);
    
	
	/**
	 * 用token来加密app端需要的key
	 * @param user
	 * @return
	 */
    SysUser EencryptKey(SysUser user);


    /**
     * 根据用户id获取企业名称
     * @author chenqiang
     * @param userId
     * @return 
     * @date 2018/10/8 0008 15:01
     */
    String getCompanyNameByUserId(Integer userId);
}
