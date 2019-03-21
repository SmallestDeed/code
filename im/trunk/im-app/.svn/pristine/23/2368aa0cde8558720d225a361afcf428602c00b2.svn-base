package com.sandu.im.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sandu.im.common.bo.ShopBo;

@Mapper
public interface ShopDao {

	 
	@Select("<script>"
			+ "select id shopId,shop_name shopName from company_shop where id in "
			+ "<foreach item='item' index='index' collection='shopIdList' open='(' separator=',' close=')'>"
			+ "#{item}"
			+ "</foreach>"
			+ " and is_deleted=0 "
			+ "</script>")
	public List<ShopBo> selectList(@Param("shopIdList") List<Long> shopIdList);

	@Select("select shop.id as shopId,shop.shop_name from company_shop shop," +
			"sys_user u where shop.user_id = u.id " +
			"and u.uuid = #{contactSessionId} and " +
			"shop.is_deleted = 0 and u.is_deleted = 0")
	ShopBo getShopInfoByContactUserSessionId(@Param("contactSessionId")String contactSessionId);
}
