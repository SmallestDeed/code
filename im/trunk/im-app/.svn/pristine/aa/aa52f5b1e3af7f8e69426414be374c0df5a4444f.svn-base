package com.sandu.im.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sandu.im.common.bo.SupplyBo;

@Mapper
public interface SupplyDao {

	 
	@Select("<script>"
			+ "select id supplyId,title from base_supply_demand where id in "
			+ "<foreach item='item' index='index' collection='supplyIdList' open='(' separator=',' close=')'>"
			+ "#{item}"
			+ "</foreach>"
			+ " and is_deleted=0 "
			+ "</script>")
	public List<SupplyBo> selectList(@Param("supplyIdList") List<Long> supplyIdList);

}
