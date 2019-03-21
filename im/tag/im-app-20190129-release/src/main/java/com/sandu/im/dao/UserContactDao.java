package com.sandu.im.dao;

import java.util.List;

import com.sandu.im.common.bo.UserBo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sandu.im.common.bo.UserContactBo;
import com.sandu.im.model.UserContact;

@Mapper
public interface UserContactDao {

	@Select("select * from msg_user_contact "
			+ "where user_session_id=#{userSessionId} "
			+ "and contact_session_id=#{contactSessionId} "
			+ "and related_obj_type=#{relatedObjType} "
			+ "and related_obj_id=#{relatedObjId} "
			+ "and is_deleted=0")
	public UserContact selectUserContact(@Param("userSessionId")String userSessionId,@Param("contactSessionId")String contactSessionId,
			@Param("relatedObjType")Integer relatedObjType,@Param("relatedObjId")Long relatedObjId);

	@Insert("insert into msg_user_contact(id, user_session_id, contact_session_id, created_date, last_updated_date, unread_msg,related_obj_type,related_obj_id,related_obj_owner_session_id,is_deleted)"
			+ "values(#{id}, #{userSessionId}, #{contactSessionId}, #{createdDate}, #{lastUpdatedDate}, #{unreadMsg},#{relatedObjType},#{relatedObjId},#{relatedObjOwnerSessionId},0)")
	public void insert(UserContact userContact);

	@Update("update msg_user_contact "
			+ "set unread_msg = 0,"
			+ "last_updated_date=now() "
			+ "where user_session_id = #{userSessionId} "
			+ "and contact_session_id= #{contactSessionId} "
			+ "and related_obj_type=#{relatedObjType} "
			+ "and related_obj_id=#{relatedObjId} "
			+ "and is_deleted=0")
	public void updateToResetUnreadMsg(@Param("userSessionId")String userSessionId,@Param("contactSessionId")String contactSessionId
			,@Param("relatedObjType")Integer relatedObjType,@Param("relatedObjId")Long relatedObjId);

	
	@Update("update msg_user_contact "
			+ "set unread_msg = unread_msg+1,"
			+ "last_updated_date=now() "
			+ "where user_session_id = #{contactSessionId} "
			+ "and contact_session_id= #{userSessionId} "
			+ "and related_obj_type=#{relatedObjType} "
			+ "and related_obj_id=#{relatedObjId} "
			+ "and is_deleted=0")
	public void updateToAddUnreadMsg(@Param("userSessionId")String userSessionId,@Param("contactSessionId")String contactSessionId,
			@Param("relatedObjType")Integer relatedObjType,@Param("relatedObjId")Long relatedObjId);

	
	@Select("select related_obj_owner_session_id relatedObjOwnerSessionId,related_obj_type relatedObjType,related_obj_id relatedObjId,contact_session_id contactSessionId,unread_msg unreadMsg "
			+ "from msg_user_contact "
			+ "where user_session_id=#{userSessionId} "
			+ "and is_deleted=0 "
			+ "order by unread_msg desc,last_updated_date desc ")
	public List<UserContactBo> selectUserContactList(String userSessionId);

	@Update("update msg_user_contact "
			+ "set is_deleted = 1,"
			+ "last_updated_date=now() "
			+ "where user_session_id = #{userSessionId} "
			+ "and contact_session_id= #{contactSessionId} "
			+ "and related_obj_type=#{relatedObjType} "
			+ "and related_obj_id=#{relatedObjId} "
			+ "and is_deleted=0")
	public void deleteContact(@Param("userSessionId")String userSessionId,@Param("contactSessionId")String contactSessionId
			,@Param("relatedObjType")Integer relatedObjType,@Param("relatedObjId")Long relatedObjId);

	@Select("<script>" +
			"SELECT count(*) FROM sys_user u,company_shop c " +
			"where u.uuid in" +
			"(SELECT to_user_session_id FROM msg_history_message where from_user_session_id = #{userSessionId}) " +
			" AND u.is_deleted = 0 and u.user_type = #{userType} AND u.id = c.user_id"+
			" AND FIND_IN_SET(3,c.release_platform_values)"+
			"<if test='userName != null'>AND u.user_name = #{userName}</if>"+
			"<if test='shopName != null'>AND c.shop_name = #{shopName}</if>"+
			"</script>"
	)
	int countChatRecordByTypeAndUserSessionId(@Param("userSessionId") String userSessionId, @Param("userType")Integer userType,@Param("userName") String userName, @Param("shopName")String shopName);

	@Select("<script>" +
			"SELECT u.id as userId,u.user_name,u.uuid,u.mobile,u.user_type FROM sys_user u,company_shop c " +
			"where u.uuid in" +
			"(SELECT to_user_session_id FROM msg_history_message where from_user_session_id = #{userSessionId} order by send_time desc)" +
			" AND u.is_deleted = 0 and u.user_type = #{userType} AND u.id = c.user_id"+
			" AND FIND_IN_SET(3,c.release_platform_values)"+
			"<if test='userName!=null'> AND u.user_name = #{userName}</if>"+
			"<if test='shopName!=null'> AND c.shop_name = #{shopName}</if>"+
			" limit #{page},5"+
			"</script>")
	List<UserBo> getChatRecordByTypeAndUserSessionId(@Param("userSessionId") String userSessionId, @Param("userType")Integer userType,@Param("userName") String userName, @Param("shopName")String shopName,@Param("page") Integer page, Integer limit);

	@Select(
			"<script>" +
					"SELECT u.id as userId,u.user_name,u.uuid as sessionId,u.mobile,u.user_type FROM sys_user u,company_shop c " +
					"where u.id in" +
					"<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>" +
							"#{item}"+
					"</foreach>"+
					" AND u.is_deleted = 0 and u.id = c.user_id"+
					" AND FIND_IN_SET(3,c.release_platform_values)"+
					"<if test='userName!=null'> AND u.user_name = #{userName} </if>"+
					"<if test='shopName!=null'> AND c.shop_name = #{shopName} </if>"+
					"limit #{page},3"+
			"</script>"
	)
	List<UserBo> getHotDesignerUserInfo(@Param("ids") List<Integer> hotDesignrIds, @Param("userName") String userName, @Param("shopName") String shopName,@Param("page")Integer page);

	@Select("<script>" +
			"SELECT count(*) FROM sys_user u,company_shop c " +
			" where" +
			" u.is_deleted = 0 and u.user_type = #{userType} AND u.id = c.user_id"+
			" AND FIND_IN_SET(3,c.release_platform_values)"+
			"<if test='userName!=null'> AND u.user_name = #{userName}</if>"+
			"<if test='shopName!=null'> AND c.shop_name = #{shopName}</if>"+
			"</script>")
	int countSanduShopDesigners(@Param("userName") String userName, @Param("shopName")String shopName,@Param("userType")Integer userType);

	@Select("<script>" +
			"SELECT u.id as userId,u.user_name,u.uuid as sessionId,u.mobile,u.user_type FROM sys_user u,company_shop c " +
			" where" +
			" u.is_deleted = 0 and u.user_type = #{userType} AND u.id = c.user_id"+
			" AND FIND_IN_SET(3,c.release_platform_values)"+
			"<if test='userName!=null'> AND u.user_name = #{userName}</if>"+
			"<if test='shopName!=null'> AND c.shop_name = #{shopName}</if>"+
			" order by convert(u.user_name using gbk) ASC"+
			" limit #{page},#{size} "+
			"</script>")
	List<UserBo> getSanduShopDesigners(@Param("userName") String userName, @Param("shopName")String shopName,@Param("userType")Integer userType, @Param("page")int sanduDesigners, @Param("size")int size);
}
