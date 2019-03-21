package com.nork.product.dao2;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.GroupProductCollect;
import com.nork.product.model.search.GroupProductCollectSearch;

/**   
 * @Title: GroupProductCollectMapper.java 
 * @Package com.nork.product.dao2
 * @Description:产品模块-组合收藏表Mapper
 * @createAuthor yangzhun 
 * @CreateDate 2017-6-19 16:54:08
 */
public interface GroupProductCollectMapper extends BaseMapper<GroupProductCollect>  {
    
	/**
	 * 其他
	 * 
	 */
    
    int groupProductCollectCount(GroupProductCollectSearch groupProductCollectSearch);
    
    List<GroupProductCollect> groupProductCollectList(
			GroupProductCollectSearch groupProductCollectSearch);
}
