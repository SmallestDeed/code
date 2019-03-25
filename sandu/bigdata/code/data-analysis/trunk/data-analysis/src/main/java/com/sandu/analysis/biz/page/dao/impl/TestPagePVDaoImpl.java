package com.sandu.analysis.biz.page.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.sandu.analysis.biz.page.dao.TestPagePVDao;
import com.sandu.analysis.biz.page.model.PageViewVo;
import com.sandu.analysis.util.JdbcUtils;

public class TestPagePVDaoImpl implements TestPagePVDao{

	public void insert(PageViewVo testPagePV) {
		// TODO Auto-generated method stub
		
	}
	
	public void insertBatch(List<PageViewVo> objList) {		
		try {
			QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "INSERT INTO test_page_pv(name,count) VALUES(?,?)";
			Object params[][] = new Object[objList.size()][];
			for (int i = 0; i < objList.size(); i++) {
				PageViewVo vo = objList.get(i);
				params[i] = new Object[] { vo.getName(), vo.getCount() };
			}
			qr.batch(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	

}
