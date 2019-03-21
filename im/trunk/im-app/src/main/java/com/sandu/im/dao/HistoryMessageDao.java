package com.sandu.im.dao;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sandu.im.model.HistoryMessage;

@Mapper
public interface HistoryMessageDao { 

	@Insert("insert into msg_history_message(id,from_app_id,from_user_session_id, from_user_name, from_user_ip, to_user_session_id, to_user_name, msg_body, send_time,related_obj_type,related_obj_id,is_deleted,msg_type)"
			+ "values(#{id},#{fromAppId},#{fromUserSessionId},#{fromUserName}, #{fromUserIp}, #{toUserSessionId},#{toUserName}, #{msgBody},#{sendTime},#{relatedObjType},#{relatedObjId},0,#{msgType})")
	public void insert(HistoryMessage historyMessage);
	
	@Select("select * from msg_history_message "
			+ "where ((from_user_session_id=#{userSessionId} and to_user_session_id=#{contactSessionId}) "
			+ "or (from_user_session_id=#{contactSessionId} and to_user_session_id=#{userSessionId})) "
			+ "and related_obj_type=#{relatedObjType} and related_obj_id=#{relatedObjId} and is_deleted=0 order by send_time desc")
	public List<HistoryMessage> selectList(@Param("userSessionId")String userSessionId,@Param("contactSessionId")String contactSessionId,
			@Param("relatedObjType")Integer relatedObjType,@Param("relatedObjId")Long relatedObjId);

	@Update("update msg_history_message "
			+ "set is_deleted = 1 "
			+ "where from_user_session_id = #{userSessionId} "
			+ "and to_user_session_id= #{contactSessionId} "
			+ "and related_obj_type=#{relatedObjType} "
			+ "and related_obj_id=#{relatedObjId} "
			+ "and is_deleted=0")
	public void delete(@Param("userSessionId")String userSessionId,@Param("contactSessionId")String contactSessionId
			,@Param("relatedObjType")Integer relatedObjType,@Param("relatedObjId")Long relatedObjId);

}
