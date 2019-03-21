package com.sandu.search.dao;

import java.util.List;

import com.sandu.search.model.SearchHot;
import com.sandu.search.model.SearchUser;
import com.sandu.search.model.query.SearchHotQuery;
import com.sandu.search.model.query.SearchUserQuery;
import com.sandu.search.model.vo.SearchHotVo;
import com.sandu.search.model.vo.SearchUserVo;

public interface SearchDao {
	 List<SearchHotVo> getHotList();
	 List<SearchUserVo> getHistoryList(SearchUserQuery query);
	 int saveSearchUser(SearchUser search);
	 int deleteSearchUser(long id);
	 int deleteSearchUserWithUserId(long userId);
	 SearchHotVo getSearchKey(SearchHotQuery query);
	 SearchUserVo getSearchHistoryKey(SearchUserQuery query);
	 int updateSearchCount(SearchHot search);
	 int updateHistorySearchCount(SearchUser search);
	 int saveSearchHot(SearchUser search);
}
