package com.nork.product.dao2;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.GroupCollectDetails;
/**   
 * @Title: GroupCollectDetailsMapper2.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-组合收藏明细表Mapper
 * @createAuthor yangzhun
 * @CreateDate 2017-6-15 16:51:04
 */
public interface GroupCollectDetailsMapper extends BaseMapper<GroupCollectDetails> {

	/**
	 * 其他
	 */
	
	List<GroupCollectDetails> findAllByGroupId(Integer id);
	
	void deleteByGroupCollectId(Integer groupCollectId);
}
