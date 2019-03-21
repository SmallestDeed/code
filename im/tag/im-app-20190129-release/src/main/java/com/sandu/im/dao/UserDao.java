package com.sandu.im.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sandu.im.common.bo.UserBo;

@Mapper
public interface UserDao {

	@Select("select uuid sessionId, if(platform_type=3,nick_name,user_name) userName,mobile from sys_user where uuid=#{userSessionId} and is_deleted=0")
	public UserBo selectOne(@Param("userSessionId") String userSessionId);
	
	 
	@Select("<script>"
			+ "select id userId,uuid sessionId,if(platform_type=3,nick_name,user_name) userName,mobile,user_type userType from sys_user where uuid in "
			+ "<foreach item='item' index='index' collection='sessionIdList' open='(' separator=',' close=')'>"
			+ "#{item}"
			+ "</foreach>"
			+ " and is_deleted=0 "
			+ "</script>")
	public List<UserBo> selectList(@Param("sessionIdList") List<String> sessionIdList);

}
