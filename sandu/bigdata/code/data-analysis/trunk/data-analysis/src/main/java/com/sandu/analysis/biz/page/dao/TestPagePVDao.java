package com.sandu.analysis.biz.page.dao;

import java.util.List;

import com.sandu.analysis.biz.page.model.PageViewVo;

public interface TestPagePVDao {

	void insert(PageViewVo testPagePV);
	
	void insertBatch(List<PageViewVo> objList);
}
