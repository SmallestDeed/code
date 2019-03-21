package com.nork.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.user.model.SysUserRegisterInfo;
import com.nork.user.model.search.SysUserRegisterInfoSearch;

/**   
 * @Title: SysUserRegisterInfoMapper.java 
 * @Package com.nork.user.dao
 * @Description:用户注册信息-用户注册信息Mapper
 * @createAuthor yanghaunzhi
 * @CreateDate 2017-08-07 16:53:22
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysUserRegisterInfoMapper {
    int insertSelective(SysUserRegisterInfo record);

    int updateByPrimaryKeySelective(SysUserRegisterInfo record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysUserRegisterInfo selectByPrimaryKey(Integer id);
    
    int selectCount(SysUserRegisterInfoSearch sysUserRegisterInfoSearch);
    
	List<SysUserRegisterInfo> selectPaginatedList(
            SysUserRegisterInfoSearch sysUserRegisterInfoSearch);
			
    List<SysUserRegisterInfo> selectList(SysUserRegisterInfo sysUserRegisterInfo);
    
	/**
	 * 其他
	 * 
	 */
}
