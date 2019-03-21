package com.sandu.interaction.service;

import com.sandu.interaction.model.Like;
import com.sandu.interaction.model.query.LikeQuery;

/***
 * 点赞服务接口
 * @author Administrator
 *
 */
public interface LikeService {
   boolean add(Like like);
   boolean cancel(Like like);
   /***
    * 根据点赞类别和点赞对象ID获取点赞总数
    * @param query
    * @return
    */
   int getCount(int likeType,long objId);
   
   /***
    * 根据点赞类别、用户ID和点赞对象ID获取点赞数
    * @param likeType
    * @param userId
    * @param objId
    * @return
    */
   int getCount(int likeType,long userId,long objId);
}
