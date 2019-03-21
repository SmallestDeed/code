package com.nork.demo.controller;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

import com.nork.common.util.Utils;
import com.nork.demo.model.Exp;

@SuppressWarnings("deprecation")
public class ExpReportMoreView extends ExpReportView {
	public static final String QUERYNAME = "演示demo合并例子报表";

	public ExpReportMoreView() {
		super();
		this.name = QUERYNAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 从model中得到数据
		List<Exp> datalist = (List<Exp>) model.get(DATANAME);
		List<String> mergelist = (List<String>) model.get(MERGELIST);

		HSSFSheet sheet = workbook.createSheet(name);
		sheet.setDefaultColumnWidth((int) 24);
		this.createColumnStyle(workbook, sheet);

		if (mergelist != null && mergelist.size() > 0) {
			for (int i = 0; i < mergelist.size(); i++) {
				String merges = (String) mergelist.get(i);
				String[] merge = merges.split(",");
				if (merge.length == 4) {
					sheet.addMergedRegion(new CellRangeAddress(Integer
							.parseInt(merge[0])+1, Integer.parseInt(merge[1])+1,
							Integer.parseInt(merge[2]), Integer
									.parseInt(merge[3])));
				}
			}
		}

		HSSFCellStyle style = workbook.createCellStyle();
		// 设置对齐方式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		// 设置字体
		HSSFFont font = workbook.createFont();// 创建字体对象
		font.setFontHeightInPoints((short) 15);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体
		font.setFontName("黑体");// 设置字体为黑体
		style.setFont(font);// 将字体加入到样式中

		// 创建标题行
		this.createTitleRow(sheet);

		for (int i = 0; i < datalist.size(); i++) {
			createDataRow(workbook, sheet, datalist.get(i), i + 1);
		}

		// 设置标题的宽度（创建时间、时间）
		sheet.setColumnWidth(ExpReportView.GMTCREATECELLNUM, 3000);// 设置列的宽度为
		sheet.setColumnWidth(ExpReportView.DATEATTCELLNUM, 3000);

		String filename = null;// 设置下载时客户端Excel的名称
		filename = Utils.encodeFilename(name, request);// 处理中文文件名
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ filename+".xls");

		OutputStream ouputStream = response.getOutputStream();

		workbook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

}
