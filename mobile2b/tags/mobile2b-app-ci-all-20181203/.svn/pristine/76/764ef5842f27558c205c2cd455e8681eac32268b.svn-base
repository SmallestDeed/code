package com.nork.demo.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;

import com.nork.common.excel.ExcelView;
import com.nork.common.util.Utils;
import com.nork.demo.model.Exp;

public class ExpReportView  extends ExcelView<Exp> {
	public static final String QUERYNAME = "演示demo通用例子报表";
	 
	public static final int IDCELLNUM = 0;
	public static final int CREATORCELLNUM = 1;
	public static final int GMTCREATECELLNUM = 2;
	public static final int STRATTCELLNUM = 3;
	public static final int INTATTCELLNUM = 4;
	public static final int DOUBLUEATTCELLNUM = 5;
	public static final int DATEATTCELLNUM = 6;

	
     public ExpReportView() {
        super();
        this.name = QUERYNAME;
     }
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    	 super.buildExcelDocument(model, workbook, request, response);
    }

    
    @Override
    public void createDataRow(HSSFWorkbook wb, HSSFSheet sheet, Exp exp, int i) {
		// 填写每一行的内容
		HSSFRow sheetRow = sheet.createRow(i);

		sheetRow.createCell(IDCELLNUM).setCellValue(exp.getId());

		if (exp.getCreator() != null) {
			sheetRow.createCell(CREATORCELLNUM).setCellValue(exp.getCreator());
		}

		if (exp.getGmtCreate() != null) {
			sheetRow.createCell(GMTCREATECELLNUM).setCellValue(Utils.parseDate(exp.getGmtCreate(),Utils.DATE));
		}
		if (exp.getStrAtt() != null) {
			sheetRow.createCell(STRATTCELLNUM).setCellValue(exp.getStrAtt());
		}
		if (exp.getIntAtt() != null) {
			sheetRow.createCell(INTATTCELLNUM).setCellValue(exp.getIntAtt().intValue());
		}
		if (exp.getDoubleAtt() != null) {
			sheetRow.createCell(DOUBLUEATTCELLNUM).setCellValue(exp.getDoubleAtt().floatValue());
		}
		if (exp.getDateAtt() != null) {
			sheetRow.createCell(DATEATTCELLNUM).setCellValue(exp.getDateAtt());
		}
  }


    @Override
    public void createTitleRow(HSSFSheet sheet) {
    	this.getCell(sheet, 0, IDCELLNUM).setCellValue("主键ID");
        this.getCell(sheet, 0, CREATORCELLNUM).setCellValue("创建人");
        this.getCell(sheet, 0, GMTCREATECELLNUM).setCellValue("创建时间");
        this.getCell(sheet, 0, STRATTCELLNUM).setCellValue("字符串");
        this.getCell(sheet, 0, INTATTCELLNUM).setCellValue("整数");
        this.getCell(sheet, 0, DOUBLUEATTCELLNUM).setCellValue("浮点数");
        this.getCell(sheet, 0, DATEATTCELLNUM).setCellValue("时间");
    }
    
    @Override
    public void createColumnStyle(HSSFWorkbook wb, HSSFSheet sheet) {
        CreationHelper createHelper = wb.getCreationHelper();
        CellStyle dateCellStyle = wb.createCellStyle();   //日期的显示格式
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
        CellStyle numCellStyle = wb.createCellStyle();
        numCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00")); //数据显示格式
        CellStyle intCellStyle = wb.createCellStyle();
        intCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0")); //整形显示格式
        CellStyle textCellStyle = wb.createCellStyle();
        textCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("@")); //文本类型显示格式

        /*
        textCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        textCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        textCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        textCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        textCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
         
       //另外一种文本的样式,暂时不使用
        CellStyle newtextCellStyle = wb.createCellStyle();
        newtextCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("@")); //文本类型显示格式
        HSSFFont font = wb.createFont();    
        font.setColor(HSSFColor.BLUE.index);
        font.setUnderline(HSSFFont.U_SINGLE);  
        newtextCellStyle.setFont(font);
        newtextCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        newtextCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        newtextCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        newtextCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        
        newtextCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        
        //为excel表格中的每一列设置显示格式
        sheet.setColumnWidth(IDCELLNUM, 3766);
        sheet.setColumnWidth(CREATORCELLNUM, 3766);
        sheet.setColumnWidth(GMTCREATECELLNUM, 3766);
        sheet.setColumnWidth(STRATTCELLNUM, 3766);
        sheet.setColumnWidth(INTATTCELLNUM, 3766);
        sheet.setColumnWidth(DOUBLUEATTCELLNUM, 3766);
        sheet.setColumnWidth(DATEATTCELLNUM, 3766);*/



      //为excel表格中的每一列设置显示格式
        this.getCell(sheet, 0, IDCELLNUM).setCellValue("主键ID");
        this.getCell(sheet, 0, CREATORCELLNUM).setCellValue("创建人");
        this.getCell(sheet, 0, GMTCREATECELLNUM).setCellValue("创建时间");
        this.getCell(sheet, 0, STRATTCELLNUM).setCellValue("字符串");
        this.getCell(sheet, 0, INTATTCELLNUM).setCellValue("整数");
        this.getCell(sheet, 0, DOUBLUEATTCELLNUM).setCellValue("浮点数");
        this.getCell(sheet, 0, DATEATTCELLNUM).setCellValue("时间");

        this.getCell(sheet, 0, IDCELLNUM).setCellStyle(textCellStyle);
        this.getCell(sheet, 0, CREATORCELLNUM).setCellStyle(textCellStyle);
        this.getCell(sheet, 0, GMTCREATECELLNUM).setCellStyle(dateCellStyle);
        this.getCell(sheet, 0, STRATTCELLNUM).setCellStyle(textCellStyle);
        this.getCell(sheet, 0, INTATTCELLNUM).setCellStyle(intCellStyle);
        this.getCell(sheet, 0, DOUBLUEATTCELLNUM).setCellStyle(numCellStyle);
        this.getCell(sheet, 0, DATEATTCELLNUM).setCellStyle(dateCellStyle);
        
        textCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);//水平居中
        textCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        dateCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        dateCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        intCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        intCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        numCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        numCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        sheet.setDefaultColumnStyle(IDCELLNUM, textCellStyle); 
        sheet.setDefaultColumnStyle(CREATORCELLNUM, textCellStyle); 
        sheet.setDefaultColumnStyle(GMTCREATECELLNUM, dateCellStyle); 
        sheet.setDefaultColumnStyle(STRATTCELLNUM, textCellStyle);
        sheet.setDefaultColumnStyle(INTATTCELLNUM, intCellStyle);
        sheet.setDefaultColumnStyle(DOUBLUEATTCELLNUM, numCellStyle);
        sheet.setDefaultColumnStyle(DATEATTCELLNUM, dateCellStyle);
        
        // 设置标题的宽度（创建时间、时间）
		sheet.setColumnWidth(ExpReportView.GMTCREATECELLNUM, 3000);// 设置列的宽度为
		sheet.setColumnWidth(ExpReportView.DATEATTCELLNUM, 3000);

    }

}
