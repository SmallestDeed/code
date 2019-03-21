package com.sandu.interaction.service;

import com.sandu.interaction.model.Collection;
import com.sandu.interaction.model.query.CollectionQuery;
import com.sandu.interaction.model.vo.CollectionVo;
import com.sandu.matadata.Page;

/***
 * 收藏服务接口
 * @author Administrator
 *
 */
public interface CollectionService {
	/***
	 * 添加收藏
	 * @param collection
	 * @return
	 */
   boolean add(Collection collection);
   /**
    * 删除收藏
    * @param collection
    * @return
    */
   boolean delete(Collection collection);
   /***
    * 分页查询收藏信息
    * @param query
    * @return
    */
   Page<CollectionVo> getList(CollectionQuery query);
   
   /***
    * 根据收藏类别和收藏对象ID获取被收藏对象的总收藏数
    * @param objId
    * @param collectionType
    * @return
    */
   int getCount(int collectionType,long objId);
   
   /***
    * 根据收藏类别、用户ID和收藏对象ID获取被收藏对象的总收藏数
    * @param type
    * @param userId
    * @param objId
    * @return
    */
   int getCount(int type, long userId,long objId);
}
