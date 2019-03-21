package com.sandu.web.servicepurchase;


import com.sandu.web.servicepurchase.ExcelMark;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelExportUtils {
    static public void createTitle(HSSFWorkbook workbook, HSSFSheet sheet, Class clazz) {
        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(2, 12 * 256);
        sheet.setColumnWidth(3, 17 * 256);

        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        for (Field field : clazz.getDeclaredFields()) {
            HSSFCell cell;
            ExcelMark mark = field.getAnnotation(ExcelMark.class);
            cell = row.createCell(mark.cellNum());
            cell.setCellValue(mark.cellName());
            cell.setCellStyle(style);
        }
    }

    static public void  setData(Workbook workbook, HSSFSheet sheet, List data, OutputStream out) throws Exception {
        if (data.isEmpty()) {
            return;
        }
        //设置日期格式
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        Class<?> clazz = data.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Object datum : data) {
            HSSFRow row = sheet.createRow(rowNum);
            for (Field field : fields) {
                field.setAccessible(true);
                ExcelMark annotation = field.getAnnotation(ExcelMark.class);
                try {
                    if (String.class.equals(field.getType())) {
                        row.createCell(annotation.cellNum()).setCellValue(String.valueOf(field.get(datum)));
                    } else if (Date.class.equals(field.getType())) {
                        HSSFCell cell = row.createCell(annotation.cellNum());
                        cell.setCellValue((Date) (field.get(datum)));
                        cell.setCellStyle(style);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            rowNum++;
        }
        //拼装blobName
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateTime = dateFormat.format(new Date());
        workbook.write(out);
    }

}
