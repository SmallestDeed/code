package com.sandu.api.system.service;

import java.util.List;

import com.sandu.api.system.input.SysUserBankcardInfoDTO;
import com.sandu.api.system.model.SysUserBankcardInfo;
import com.sandu.api.system.output.SysUserBankcardInfoListVO;
import com.sandu.api.system.search.SysUserBankcardInfoSearch;
import com.sandu.common.model.output.CommonDTO;

public interface SysUserBankcardInfoService {

	/**
	 * 获取该用户绑定的银行卡信息列表
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	List<SysUserBankcardInfoListVO> getSysUserBankcardInfoListVO(Long userId);

	/**
	 * 
	 * @author huangsongbo
	 * @param sysUserBankcardInfoSearch
	 * @return
	 */
	List<SysUserBankcardInfo> getListBySearch(SysUserBankcardInfoSearch sysUserBankcardInfoSearch);

	/**
	 * 创建银行卡信息
	 * 
	 * @author huangsongbo
	 * @param paramDTO
	 */
	void create(SysUserBankcardInfoDTO paramDTO, String creator);

	/**
	 * 
	 * @author huangsongbo
	 * @param sysUserBankcardInfo
	 * @param creator 是谁创建的
	 * @return
	 */
	int add(SysUserBankcardInfo sysUserBankcardInfo, String creator);

	/**
	 * 删除银行卡信息
	 * 会检测该银行卡数据是不是由该用户创建的, 如果不是, 则不允许删除
	 * 
	 * @author huangsongbo
	 * @param id
	 * @param userId
	 */
	void delete(Long id, Long userId);

	/**
	 * 
	 * @author huangsongbo
	 * @param id
	 * @param userId
	 * @return
	 */
	int deleteByIdAndUserId(Long id, Long userId);

	/**
	 * 
	 * @author huangsongbo
	 * @param userId
	 * @return
	 */
	int getCountByUserId(Long userId);

	/**
	 * 
	 * @author huangsongbo
	 * @param sysUserBankcardInfoSearch
	 * @return
	 */
	int getCountBySearch(SysUserBankcardInfoSearch sysUserBankcardInfoSearch);

	/**
	 * 验证该用户是否还可以创建银行卡
	 * 
	 * @author huangsongbo
	 * @param userId 用户id
	 * @return 是否还可以创建银行卡 + 不能创建银行卡的原因
	 */
	CommonDTO getIsAllowCreateResult(Long userId);

}
