package com.sandu.util.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.base.Strings;
import com.sandu.annotation.ExportRow;
import com.sandu.constant.Punctuation;

public class ExcelUtils<B> {
    private Map<String, XSSFPictureData> cell2Pic;
    private List<Field> fieldList;
    private List<Field> cellSortList;
    private Class m;
    final private static Long SINGLE_PIC_BYTE_SIZE = 2 * 1024 * 1024L;
//    final private static Long SINGLE_PIC_BYTE_SIZE = 200 * 1024L;

    public ExcelUtils(Class<B> m) {
        this.m = m;
        Field[] fields = m.getDeclaredFields();
        this.fieldList = Arrays.asList(fields);
        cellSortList = new ArrayList<>(fieldList.size());
    }


    public List<B> readExcelToModel(InputStream is, Integer sheetIndex, String imgBaseDir, String realPath) {
        XSSFSheet sheet = this.initSheet(is, sheetIndex);
        this.mapAllPictures(sheet);
        boolean b = this.validExcel(sheet);
        List<B> result = null;
        if (b) {
            result = this.initModels(sheet, imgBaseDir, realPath);
        }
        return result;
    }

    private XSSFSheet initSheet(InputStream is, Integer sheetIndex) {
        XSSFWorkbook wb;
        try {
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取文件失败...");
        }
        return wb.getSheetAt(sheetIndex);
    }

    private List<B> initModels(XSSFSheet sheet, String imgBaseDir, String realPath) {
        List<B> result = new LinkedList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (isEnd(row)) break;
            Object o;
            try {
                o = m.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("");
            }
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                XSSFCell cell = row.getCell(j);
                Field field = cellSortList.get(j);
                field.setAccessible(true);
                //设置字段数据
                if (field.getAnnotation(Row.class).picFlag()) {
                    //save pic
                    XSSFPictureData xssfPictureData = cell2Pic.get(i + "_" + j);
                    if (xssfPictureData == null) {
                        continue;
                    }
                    String filePath = this.savePic(imgBaseDir, realPath, xssfPictureData, i + "_" + j);
                    this.setParamToBean(field, o, filePath);
                    continue;
                }
                if (field.getType().equals(String.class)) {
                    this.setParamToBean(field, o, cell.toString().replace(".0", ""));
                }
                if (field.getType().equals(Double.class)) {
                    this.setParamToBean(field, o, cell.getNumericCellValue());
                }
                if (field.getType().equals(Integer.class)) {
                    this.setParamToBean(field, o, Double.valueOf(cell.getNumericCellValue()).intValue());
                }
                if (field.getType().equals(Date.class)) {
                    this.setParamToBean(field, o, cell.getDateCellValue());
                }
            }
            result.add((B) o);
        }
        return result;
    }

    private void setParamToBean(Field field, Object o, Object filePath) {
        try {
            field.set(o, filePath);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String savePic(@NotNull String baseDir, @NotNull String formatDir, @NotNull XSSFPictureData pic, String fileMark) {
        LocalDateTime now = LocalDateTime.now();
        String pathFormat = now.format(DateTimeFormatter.ofPattern("/yyyy/MM/dd/HH"));
        String dirPath = Paths.get(baseDir, formatDir, pathFormat).toString().replace("\\\\", "/");
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        byte[] data = pic.getData();

        String subType = pic.getPackagePart().getContentTypeDetails().getSubType();
        //拼接文件路径
        String fileName = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS")) + fileMark + Punctuation.DOT + subType;
        String filePath = dirPath + Punctuation.SLASH + fileName;
        //写入文件
        try {
            Files.write(Paths.get(filePath), data, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        String result = Paths.get(formatDir, pathFormat, fileName).toString();
        return result.replaceAll("\\\\", "/").replace("//", "/");
    }

    private boolean validExcel(@NotNull XSSFSheet sheet) {
        if (sheet.getPhysicalNumberOfRows() == 0) {
            throw new RuntimeException("请检查excel文件列名是否正确");
        }
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (i != 0 && isEnd(row)) break;
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                if (!this.checkCell(row.getCell(j), j)) {
                    System.out.println("index is :" + i + "_" + j);
                    System.out.println("check field : " + cellSortList.get(j).getAnnotation(Row.class).value() + ",value is :" + row.getCell(j).toString());

                }
            }
        }
        return true;

    }

    private boolean isEnd(XSSFRow row) {
        if (row == null || row.getCell(0) == null) {
            return true;
        }
        return Strings.isNullOrEmpty(row.getCell(0).toString());
    }

    private boolean checkCell(XSSFCell cell, Integer index) {
        //验证数据
        int rowIndex = cell.getRowIndex();
        if (rowIndex == 0) {
            Field first = fieldList.stream()
                    .filter(f -> f.getAnnotation(Row.class).value().trim().equalsIgnoreCase(cell.toString().trim()))
                    .findFirst().orElseThrow(() -> new RuntimeException("请检查excel文件列名是否正确"));
            cellSortList.add(index, first);
            return true;
        }
        if (cellSortList.size() != fieldList.size()) {
            throw new RuntimeException("请检查excel文件列名是否正确");
        }
        Row annotation = cellSortList.get(index).getAnnotation(Row.class);
        Field field = cellSortList.get(index);
        if (annotation.value().trim().startsWith("*")) {
            //校验图片字段是否 存在图片
            if (annotation.picFlag() && cell2Pic.get(rowIndex + "_" + index) == null) {
                throw new RuntimeException(String.format("请检查第%s行,%s 列图片", rowIndex, field.getAnnotation(Row.class).value()));
            }
            if (annotation.picFlag() && cell2Pic.get(rowIndex + "_" + index) != null) {
                int length = cell2Pic.get(rowIndex + "_" + index).getData().length;
                if (length > SINGLE_PIC_BYTE_SIZE) {
                    throw new RuntimeException(String.format("请检查第%s行,%s 列图片大小是否超过2M", rowIndex, field.getAnnotation(Row.class).value()));
                }
            }
            if (!annotation.picFlag() && StringUtils.isBlank(cell.toString())) {
                throw new RuntimeException(String.format("请检查第%s行,%s 列数据为必填选项", rowIndex, field.getAnnotation(Row.class).value()));
            }
            //校验内容长度
            if (annotation.maxLength() > 0 && annotation.maxLength() < cell.toString().length()) {
                throw new RuntimeException(String.format("请检查第%s行,%s 列数据长度限制为%s",
                        rowIndex,
                        field.getAnnotation(Row.class).value(),
                        annotation.maxLength()));
            }
            if (StringUtils.isNoneBlank(cell.toString())) {
                if (field.getType().equals(Integer.class) || field.getType().equals(Long.class) || field.getType().equals(Double.class)) {
                    try {
                        Double.parseDouble(cell.toString());
                    } catch (Exception e) {
                        throw new RuntimeException(String.format("请检查第%s行,%s 列数据为数字类型", rowIndex, field.getAnnotation(Row.class).value()));
                    }
                }
            }
        }
        return true;
    }

    private void mapAllPictures(XSSFSheet sheet) {
        this.cell2Pic = new HashMap<>();
        if (sheet.getDrawingPatriarch() == null) {
            return;
        }
        List<XSSFShape> shapes = sheet.getDrawingPatriarch().getShapes();
        for (XSSFShape shape : shapes) {
            if (shape instanceof XSSFPicture) {
                XSSFPicture picture = (XSSFPicture) shape;
                XSSFClientAnchor cAnchor = picture.getClientAnchor();
                XSSFPictureData pdata = picture.getPictureData();
                String key = cAnchor.getRow1() + "_" + cAnchor.getCol1(); // 行号-列号
                this.cell2Pic.put(key, pdata);
            }
        }
    }

    
    /**
     * datas为导出的数据
     * fileName为excel文件名称
     * sheetName为sheet名称
     * 实体类注解通用导出方法
     * @param datas
     * @param fileName
     * @param sheetName
     */
    public static <T> void exportExcel(List<T> datas, String fileName, String sheetName, HttpServletResponse response, boolean displayAll) throws IOException, IllegalAccessException {
        // String fileExcelName = new SimpleDateFormat("yyyyMMddhh").format(new Date()).toString() + fileName;
    	Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        org.apache.poi.ss.usermodel.Row row = sheet.createRow(0);//创建第一行
        CellStyle cellStyle = workbook.createCellStyle();
        Font dataFont = workbook.createFont();
		dataFont.setFontName("simsun");
		// dataFont.setFontHeightInPoints((short) 14);
		dataFont.setColor(IndexedColors.BLACK.index);
		cellStyle.setFont(dataFont);
        Boolean isHasTitle = false;
        for (int i = 0; i < datas.size(); i++) {
            org.apache.poi.ss.usermodel.Row rowBatch = sheet.createRow(i + 1);
            Object o = datas.get(i);
            if (!isHasTitle) {
                for (Field field : o.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(ExportRow.class)) {
                        //获取字段注解
                    	ExportRow columnConfig = field.getAnnotation(ExportRow.class);
                        if (displayAll) {
                            //判断是否已经获取过该code的字典数据 避免重复获取
                            Cell cell = row.createCell(Integer.valueOf(columnConfig.orderBy().toString()));
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(columnConfig.value().toString());
                        }
                        if (!displayAll && columnConfig.display()) {
                        	Cell cell = row.createCell(Integer.valueOf(columnConfig.orderBy().toString()) - 1);
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(columnConfig.value().toString());
                        }
                    }
                }
                isHasTitle = true;
            }
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ExportRow.class)) {
                    //获取字段注解
                	ExportRow columnConfig = field.getAnnotation(ExportRow.class);
                    if (displayAll) {
                    	Cell cell = rowBatch.createCell(Integer.valueOf(columnConfig.orderBy().toString()));
                        cell.setCellStyle(cellStyle);
                        //判断是否已经获取过该code的字典数据 避免重复获取
                        cell.setCellValue(field.get(o) == null ? "" : field.get(o).toString());
                    }
                    if (!displayAll && columnConfig.display()) {
                    	Cell cell = rowBatch.createCell(Integer.valueOf(columnConfig.orderBy().toString()) - 1);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(field.get(o) == null ? "" : field.get(o).toString());
                    }
                }
            }
        }
        response.setContentType("application/vnd.ms-excel");
	    response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName+".xls", "utf-8"));
	    OutputStream outputStream = response.getOutputStream();
	    FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\excle_test\\测试.xls"));
	    workbook.write(outputStream);
	    workbook.write(fileOutputStream);
	    outputStream.flush();
	    outputStream.close();
    }

    public static String getSystemFileCharset(){
        Properties pro = System.getProperties();
        return pro.getProperty("file.encoding");
     }
    

    public static void main(String[] args) throws IOException, InvalidFormatException {
//        String path = "D:\\temp\\upload\\2018\\09\\18\\16\\201809181600611_4.png";
//
//        String s1 = path.replaceAll("\\\\", "/");
//        System.out.println(path);
//        System.out.println(s1);

        List<String> s = new LinkedList<>();
        ExcelUtils<TextureImportBean> utils = new ExcelUtils(TextureImportBean.class);
        final String filePath = "C:\\Users\\Sandu\\Desktop\\test.xlsx";
        String substring = filePath.substring(filePath.lastIndexOf(Punctuation.DOT) + 1);
        try {
            utils.readExcelToModel(new FileInputStream(filePath), 0, "D:/temp/upload/src", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
