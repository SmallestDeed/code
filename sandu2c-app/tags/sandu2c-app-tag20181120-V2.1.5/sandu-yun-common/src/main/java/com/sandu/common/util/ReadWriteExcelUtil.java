package com.sandu.common.util;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;

public class ReadWriteExcelUtil {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*String fileName = "d:" + File.separator + "Book1.xls";
		ReadWriteExcelUtil.readExcel(fileName);
		String a = "LXD-20010101-001";
		String[] c = a.split("-");
		Integer b = new Integer(c[2]) + 1;
		//////System.out.println(b);
		
		String test ="3123123";
		String value = StringUtils.leftPad(b.toString(), 3, "0");
		//////System.out.println(value);*/
		//String ff = ReadWriteExcelUtil.readExcel(fileName);
		////////System.out.println(ff);
//		List<NewContractItems> list = ReadWriteExcelUtil.readExcelTo(fileName);
//		for(NewContractItems n : list){
//			//////System.out.println("清单项名称"+n.getItemName());
			////////System.out.println("单位"+n.getUnitName());
			////////System.out.println("合同数量"+n.getConNumber());
//		}
		//String fileName1 = "d:" + File.separator + "abc.xls";
		//ReadWriteExcelUtil.writeExcel(fileName1);
		
		String str = " abc ";
		//////System.out.println("str:" + str.trim()+"length:" + str.trim().length());
	}
	
	/**
	 * 從excel文件中讀取所有的內容
	 * 
	 * @param fileName excel文件
	 * @return excel文件的內容
	 */
	public static String readExcel(String fileName) {
		StringBuffer sb = new StringBuffer();
		Workbook wb = null;
		try {
			// 构造Workbook（工作薄）对象
			wb = Workbook.getWorkbook(new File(fileName));
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (wb == null) {
            return null;
        }
		try {
			checkFile(wb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//////System.out.println("异常："+e);
		}
		// 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
		Sheet[] sheet = wb.getSheets();

		if (sheet != null && sheet.length > 0) {
			// 对每个工作表进行循环
			for (int i = 0; i < sheet.length; i++) {
				// 得到当前工作表的行数
				int rowNum = sheet[i].getRows();
				for (int j = 0; j < rowNum; j++) {
					// 得到当前行的所有单元格
					Cell[] cells = sheet[i].getRow(j);
					if (cells != null && cells.length > 0) {
						// 对每个单元格进行循环
						for (int k = 0; k < cells.length; k++) {
							// 读取当前单元格的值
							String cellValue = cells[k].getContents();
							sb.append(cellValue + "\t");
						}
					}
					sb.append("\r\n");
				}
				sb.append("\r\n");
			}
		}
		// 最后关闭资源，释放内存
		wb.close();
		return sb.toString();
	}
	
//	public static List<NewContractItems> readExcelTo(InputStream is) throws Exception {
//		//StringBuffer sb = new StringBuffer();
//		List<NewContractItems> list = new ArrayList<NewContractItems>();
//		Workbook wb = null;
//		try {
//			// 构造Workbook（工作薄）对象
//			wb = Workbook.getWorkbook(is);
//		} catch (BiffException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//			checkFile(wb);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw e;
//		}
//		if (wb == null){
//			return null;
//		}
//		// 获得了Workbook对象之后，就可以通到Sheet（工作表）对象了
//		Sheet[] sheet = wb.getSheets();
//
//		if (sheet != null && sheet.length > 0) {
//			// 对每个工作表进行循环
//				// 得到当前工作表的行数
//				int rowNum = sheet[0].getRows();
//				//第一行为标题 不记录
//				for (int j = 2; j < rowNum; j++) {
//					// 得到当前行的所有单元格
//					Cell[] cells = sheet[0].getRow(j);
//					if (cells != null && cells.length > 0) {
//						NewContractItems newContractItems = new NewContractItems();
//						// 对每个单元格进行循环 
//						for (int k = 0; k < cells.length; k++) {
//							// 读取当前单元格的值
//							String cellValue = cells[k].getContents();
//							
//							if(cellValue == null || "".equals(cellValue.trim())){
//								continue;
//							}
//							newContractItems = setNewContractItems(k,cellValue,newContractItems);
//						}
//						if(checkModel(newContractItems)){
//							list.add(newContractItems);
//						}else if(allBlank(newContractItems)){
//							throw new Exception();
//						}
//					}
//					//sb.append("\r\n");
//				}
//				//sb.append("\r\n");
//		}
//		// 最后关闭资源，释放内存
//		wb.close();
//		return list;
//	}
	
	/**
	 * 验证 NewContractItems 对象是否真诚
	 * @param newContractItems
	 * @return 
	 */
//	public static boolean checkModel(NewContractItems newContractItems){
//		boolean flag = false;
//		if(newContractItems.getItemCode() != null && !"".equals(newContractItems.getItemCode()) && newContractItems.getItemName() != null && !"".equals(newContractItems.getItemName()))
//		{
//			flag = true;
//		}
//		int a = 0, b = 0, c = 0, d = 0;
//		if(newContractItems.getUnitName() != null && !"".equals(newContractItems.getUnitName()) ){
//			a = 1;
//		}
//		if(newContractItems.getConNumber() != null){
//			b = 1;
//		}
//		if(newContractItems.getConPrice() != null){
//			c = 1;
//		}
//		if(newContractItems.getTotalPrice() != null){
//			d = 1;
//		}
//		if(a == b && b== c && c == d && flag){
//			flag = true;
//		}else {
//			flag = false;
//		}
//		return flag;
//	}
	
//	public static boolean allBlank(NewContractItems newContractItems){
//		boolean flag = true;
//		int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0;
//		if(newContractItems.getItemCode() != null && !"".equals(newContractItems.getItemCode())){
//			e = 1;
//		}
//		if(newContractItems.getItemName() != null && !"".equals(newContractItems.getItemName()))
//		{
//			f = 1;
//		}
//		if(newContractItems.getUnitName() != null && !"".equals(newContractItems.getUnitName())){
//			a = 1;
//		}
//		if(newContractItems.getConNumber() != null){
//			b = 1;
//		}
//		if(newContractItems.getConPrice() != null){
//			c = 1;
//		}
//		if(newContractItems.getTotalPrice() != null){
//			d = 1;
//		}
//		if(a == b && b == c && c == d && d == e && e == f && f == 0){
//			flag = false;
//		}
//		return flag;
//	}
	/**
	 * @param cellvalue
	 * @return 转换成对象
	 */
//	public static NewContractItems setNewContractItems(int k,String cellvalue,NewContractItems newContractItems) throws Exception{
//		String c = cellvalue.trim();
//		switch(k){
//			//清单项编号
//			case 0 : newContractItems.setItemCode(c); break;
//			//清单项名称
//			case 1 : newContractItems.setItemName(c); break;
//			//单位
//			case 2 : newContractItems.setUnitName(c); break;
//			//合同数量
//			case 3 : newContractItems.setConNumber(new BigDecimal(c)); break;
//			//合同单价
//			case 4 : newContractItems.setConPrice(new BigDecimal(c)); break;
//			//合同金额
//			case 5 : newContractItems.setTotalPrice(new BigDecimal(c)); break;
//		}
//		return newContractItems;
//	}
	
	public static boolean checkTemplate(String cellvalue){
		boolean flag = false;
		if("编号".equals(cellvalue)){
			flag = true;
		}
		return flag;
	}
	/**
	 * 把內容寫入excel文件中
	 * 
	 * @param fileName
	 *            要寫入的文件的名稱
	 */
	public static void writeExcel(String fileName) {
		WritableWorkbook wwb = null;
		try {
			// 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
			wwb = Workbook.createWorkbook(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (wwb != null) {
			// 创建一个可写入的工作表
			// Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
			WritableSheet ws = wwb.createSheet("sheet1", 0);

			// 下面开始添加单元格
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 5; j++) {
					// 这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
					Label labelC = new Label(j, i, "这是第" + (i + 1) + "行，第"
							+ (j + 1) + "列");
					try {
						// 将生成的单元格添加到工作表中
						ws.addCell(labelC);
					} catch (RowsExceededException e) {
						e.printStackTrace();
					} catch (WriteException e) {
						e.printStackTrace();
					}

				}
			}

			try {
				// 从内存中写入文件中
				wwb.write();
				// 关闭资源，释放内存
				wwb.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param wb
	 * @return 是否为模板文件
	 * @throws Exception 
	 */
	public static boolean checkFile(Workbook wb) throws Exception{
		boolean flag = false;
		//checker标识在sheet2上
		int sheet = 1;
		//checker标识在row368行上
		int row = 367;
		//checker标识在L列
		int cell = 11;
		//checker标识为数字 748264
		int checknum = 748264;
		
		String checker = null;
		try {
			Cell[] cells = wb.getSheet(sheet).getRow(row);
			checker = cells[cell].getContents();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		if(checknum == new Integer(checker)){
			flag = true;
		}
		return flag;
	}
}
