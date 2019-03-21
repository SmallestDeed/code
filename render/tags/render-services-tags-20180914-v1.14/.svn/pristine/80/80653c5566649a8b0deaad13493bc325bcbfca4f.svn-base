package com.nork.common.excel;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.nork.common.util.Utils;

public abstract class ExcelView<T> extends AbstractExcelView {
	
	    public static final String DATANAME  = "datalist";
	    public static final String MERGELIST = "mergelist";
	    
	    protected String name; //导出的excel文件名

	    public ExcelView() {
	        super();
	    }
	    
	    public ExcelView(String filename) {
	        super();
	        this.name = filename;
	    }


	    @SuppressWarnings("unchecked")
		@Override
	    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
	        //从model中得到数据
	        List<T> datalist = (List<T>) model.get(DATANAME);
	        HSSFSheet sheet = workbook.createSheet(name);
	        sheet.setDefaultColumnWidth((int) 12);
	        this.createColumnStyle(workbook, sheet);

	        //创建标题行
	        this.createTitleRow(sheet);

	        for (int i = 0; i < datalist.size(); i++) {
	             this.createDataRow(workbook, sheet, datalist.get(i), i + 1);
	        }

	        String filename = null;//设置下载时客户端Excel的名称
	        filename = Utils.encodeFilename(name, request);//处理中文文件名
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-disposition", "attachment;filename=" + filename+".xls");
	        
	        OutputStream ouputStream = response.getOutputStream();
	        workbook.write(ouputStream);
	        ouputStream.flush();
	        ouputStream.close();
	    }

	    public abstract void createColumnStyle(HSSFWorkbook wb, HSSFSheet sheet);

	    public abstract void createTitleRow(HSSFSheet sheet);
	    
	    public abstract void createDataRow(HSSFWorkbook wb, HSSFSheet sheet, T model, int i);

}
