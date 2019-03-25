package com.sandu.analysis.biz.page.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sandu.analysis.biz.page.dao.PageDao;
import com.sandu.analysis.biz.page.model.Page;
import com.sandu.analysis.util.JdbcUtils;

public class PageDaoImpl implements PageDao{

	public void insert(Page page) {
		try {
			QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "insert into page(name,url) values(?,?)";
			// 将三个?占位符的实际参数写在数组中
			Object[] params = {page.getName(),page.getUrl()};
			// 调用QueryRunner类的方法update执行SQL语句
			int row = qr.update(sql, params);
			System.out.println(row);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public Page selectById(Long id) {
		try {
			QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from page where id=?";
			Object params[] = { 2 };
			Page page = qr.query(sql, new BeanHandler<Page>(Page.class), params);
			return page;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void selectList() {
		try {
			QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from page";
			List<Page> list = qr.query(sql, new BeanListHandler<Page>(Page.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertBatch() {
		try {
			QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "insert into page(name,url) values(?,?)";
			Object params[][] = new Object[10][];
			for (int i = 0; i < 10; i++) {
				params[i] = new Object[] { "123" + i, "" + i };
			}
			qr.batch(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	 
	public void updateById() {
		try {
			QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "update page set name=? where id=?";
			Object params[] = { "ddd", 2 };
			qr.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	
}
