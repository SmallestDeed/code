package com.sandu.analysis.biz.page.offline;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.sandu.analysis.biz.factory.DaoFactory;
import com.sandu.analysis.biz.page.dao.TestPagePVDao;
import com.sandu.analysis.biz.page.model.PageViewVo;


public class PageAnalysis {

	public static void main(String[] args) {
		SparkSession spark = SparkSession
				  .builder()
				  .appName("PageAnalysis")
				  .enableHiveSupport()
				  .getOrCreate();
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		String monthStr = month < 10 ? "0" + month : month + "";
		int day = c.get(Calendar.DAY_OF_MONTH);
		String ym = year + monthStr;
		String d = day < 10 ? "0" + day : day + "";
		System.out.println("当前日期: ym="+ym+",d="+d);
		spark.sql("alter table event_log add if not exists partition(ym='"+ym+"',d='"+d+"') location '/usr/local/applogs/events/"+ym+"/"+d+"'"); 
		Dataset<Row> resultDS = spark.sql("select event_property['curpage'] pageName,count(*) num from event_log where ym='"+ym+"' and d='"+d+"' and event_name='pageview' group by event_property['curpage']");
		
		List<Row> rows = resultDS.collectAsList();
		List<PageViewVo> list = new ArrayList<PageViewVo>();
		for (Row row : rows) {
			PageViewVo obj = new PageViewVo();
			obj.setName(row.getString(0));
			obj.setCount(row.getLong(1));
			list.add(obj);
			System.out.println(row);
		}
		
		TestPagePVDao dao = DaoFactory.getTestPagePVDao();
		dao.insertBatch(list);
		spark.close();
	}
}
