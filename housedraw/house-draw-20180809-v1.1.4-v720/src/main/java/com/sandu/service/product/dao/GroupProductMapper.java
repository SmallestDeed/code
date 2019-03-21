package com.sandu.service.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.product.bo.BaseProductBO;
import com.sandu.api.product.bo.GroupProductBO;
import com.sandu.api.house.input.GroupProductQuery;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/29
 */

@Repository
public interface GroupProductMapper {

	Long countGroupProduct(GroupProductQuery query);

	List<GroupProductBO> listGroupProduct(GroupProductQuery query);
	
	List<BaseProductBO> listBaseProduct(@Param("list")List<GroupProductBO> list);
}
