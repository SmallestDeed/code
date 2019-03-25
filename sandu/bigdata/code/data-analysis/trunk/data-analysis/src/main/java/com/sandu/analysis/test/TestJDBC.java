package com.sandu.analysis.test;

import com.sandu.analysis.biz.factory.DaoFactory;
import com.sandu.analysis.biz.page.dao.PageDao;
import com.sandu.analysis.biz.page.model.Page;

public class TestJDBC {

	public static void main(String[] args) {
		Page page = new Page();
		page.setName("aa");
		PageDao pageDao = DaoFactory.getUserDao();
		pageDao.insert(page);
	}
}
