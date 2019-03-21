package com.nork.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.nork.base.definedvalue.ConstValue;
import com.nork.common.constant.RoleConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import com.nork.system.model.SysRole;
import com.nork.system.model.SysUserLevelPrice;
import com.nork.system.model.SysUserRole;
import com.nork.system.service.SysRoleService;
import com.nork.system.service.SysUserLevelConfigService;
import com.nork.system.service.SysUserLevelPriceService;
import com.nork.system.service.SysUserRoleService;
import com.nork.user.model.bo.UserRegisterInfoBo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.user.dao.SysUserRegisterInfoMapper;
import com.nork.user.model.SysUserRegisterInfo;
import com.nork.user.model.search.SysUserRegisterInfoSearch;
import com.nork.user.service.SysUserRegisterInfoService;

/**   
 * @Title: SysUserRegisterInfoServiceImpl.java 
 * @Package com.nork.user.service.impl
 * @Description:用户注册信息-用户注册信息ServiceImpl
 * @createAuthor yanghuanzhi
 * @CreateDate 2017-08-07 16:53:22
 * @version V1.0   
 */
@Service("sysUserRegisterInfoService")
public class SysUserRegisterInfoServiceImpl implements SysUserRegisterInfoService {
	Logger logger = Logger.getLogger(SysUserRegisterInfoServiceImpl.class);
	private SysUserRegisterInfoMapper sysUserRegisterInfoMapper;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	SysUserLevelConfigService sysUserLevelConfigService;
	@Autowired
	SysUserLevelPriceService sysUserLevelPriceService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	public void setSysUserRegisterInfoMapper(
			SysUserRegisterInfoMapper sysUserRegisterInfoMapper) {
		this.sysUserRegisterInfoMapper = sysUserRegisterInfoMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysUserRegisterInfo
	 * @return  int
	 */
	@Override
	public int add(SysUserRegisterInfo sysUserRegisterInfo) {
		sysUserRegisterInfoMapper.insertSelective(sysUserRegisterInfo);
		return sysUserRegisterInfo.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysUserRegisterInfo
	 * @return  int 
	 */
	@Override
	public int update(SysUserRegisterInfo sysUserRegisterInfo) {
		return sysUserRegisterInfoMapper
				.updateByPrimaryKeySelective(sysUserRegisterInfo);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysUserRegisterInfoMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysUserRegisterInfo 
	 */
	@Override
	public SysUserRegisterInfo get(Integer id) {
		return sysUserRegisterInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysUserRegisterInfo
	 * @return   List<SysUserRegisterInfo>
	 */
	@Override
	public List<SysUserRegisterInfo> getList(SysUserRegisterInfo sysUserRegisterInfo) {
	    return sysUserRegisterInfoMapper.selectList(sysUserRegisterInfo);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysUserRegisterInfo
	 * @return   int
	 */
	@Override
	public int getCount(SysUserRegisterInfoSearch sysUserRegisterInfoSearch){
		return  sysUserRegisterInfoMapper.selectCount(sysUserRegisterInfoSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysUserRegisterInfo
	 * @return   List<SysUserRegisterInfo>
	 */
	@Override
	public List<SysUserRegisterInfo> getPaginatedList(
			SysUserRegisterInfoSearch sysUserRegisterInfoSearch) {
		return sysUserRegisterInfoMapper.selectPaginatedList(sysUserRegisterInfoSearch);
	}

    @Override
    public int addRegisterInfo(UserRegisterInfoBo registerInfoBo) {
		if(registerInfoBo == null || registerInfoBo.getUserId() < 1){
			return 0;
		}
		long userId = registerInfoBo.getUserId();
		int userType = registerInfoBo.getUserType();

		SysUserRegisterInfo registerInfo = new SysUserRegisterInfo();
		registerInfo.setName(registerInfoBo.getName());
		registerInfo.setUserId(userId);
		registerInfo.setUserType(userType);
		registerInfo.setBid(UUID.randomUUID().toString().replace("-", ""));
		registerInfo.setCreator("nologin");
		registerInfo.setModifier("nologin");
		registerInfo.setGmtModified(new Date());
		registerInfo.setGmtCreate(new Date());
		registerInfo.setIsDeleted(ConstValue.DEFAULT_IS_DELETED.is_not_deleted.ordinal());
		registerInfo.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
		int i = sysUserRegisterInfoMapper.insertSelective(registerInfo);

		//给新注册的用户添加个默认角色
		if (i > 0){
			int roleId = sysRoleService.getRoleByCode(RoleConstant.ROLE_U3D_DEFAULT);
			if (roleId < 1){
				logger.error("role is null:role_code="+RoleConstant.ROLE_U3D_DEFAULT);
				return 0;
			}
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId((int)userId);
			userRole.setRoleId(roleId);
			userRole.setGmtCreate(new Date());
			userRole.setGmtModified(new Date());
			userRole.setCreator("nologin");
			userRole.setModifier("nologin");
			userRole.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
			userRole.setIsDeleted(ConstValue.DEFAULT_IS_DELETED.is_not_deleted.ordinal());
			sysUserRoleService.add(userRole);
		}
		//初始化用户等级
		SysUserLevelPrice levelPrice = sysUserLevelPriceService.getIdByUserType(userType);
		if (levelPrice !=null && levelPrice.getId() > 0){
			sysUserLevelConfigService.initUserLevelByLevelPriceId(userId,levelPrice.getId());
		}
		return i;
    }

    /**
	 * 其他
	 * 
	 */


}
