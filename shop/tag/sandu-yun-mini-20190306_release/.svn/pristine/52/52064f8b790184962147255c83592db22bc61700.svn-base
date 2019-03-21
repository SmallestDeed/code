package com.sandu.search.service;

import java.util.List;

import com.sandu.search.model.SearchUser;
import com.sandu.search.model.vo.SearchHotVo;
import com.sandu.search.model.vo.SearchUserVo;

/***
 * 搜索服务接口
 * @author Administrator
 *
 */
public interface SearchService {
   /***
    * 删除用户搜索的历史数据
    * @param search
    * @return
    */
   boolean deleteSearchUser(SearchUser search);
   /***
    * 获取热门搜索列表
    * @return
    */
   List<SearchHotVo> getHotList();
   
   /***
    * 根据用户ID获取前几条历史搜索数据
    * @param userId
    * @return
    */
   List<SearchUserVo> getHistoryList(long userId);
   
   /****
    * 保存用户搜索的历史数据
    * @param search
    * @return
    */
   boolean saveSearchUser(SearchUser search);
}
