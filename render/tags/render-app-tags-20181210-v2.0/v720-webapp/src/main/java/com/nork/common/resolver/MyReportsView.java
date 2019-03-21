//package com.nork.common.resolver;
//
//import java.util.Map;
//import java.util.Properties;
//
//import javax.servlet.http.HttpServletResponse;
//
//
//
//import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
//
//import net.sf.jasperreports.engine.JasperPrint;
//
//
//public class MyReportsView extends JasperReportsMultiFormatView {
//	public static final String ATTACHEMT_FILE_NAME_KEY = "attachmentFileName"; // 格式不为html时的下载文件名
//	public static final String JASPER_URL = "url";
//	public static final String JASPER_DATA_SOURCE = "jrMainDataSource";
//	
//	
//	@Override
//	protected void renderReport(JasperPrint populatedReport,
//			Map<String, Object> model, HttpServletResponse response)
//			throws Exception {
//		
//		Object format = model.get(DEFAULT_FORMAT_KEY);
//		if (format == null) {
//			throw new IllegalArgumentException(
//					"model中未找到指定的输出格式(format:html、pdf、xls、csv)");
//		}
//		String fileName ="";
//		if (!format.equals("html")) {
//			Object attachmentFileName = model.get(ATTACHEMT_FILE_NAME_KEY);
//			if (attachmentFileName == null) {
//				throw new IllegalArgumentException(
//						"model中未指定输出文件名(attachmentFileName)");
//			}
//			Properties contentDispositionMappings = getContentDispositionMappings();
//			contentDispositionMappings
//					.put(format.toString(), "attachment; filename="
//							+ attachmentFileName + "." + format);
//			fileName = attachmentFileName.toString();
//		}
//		
//		//重写？？是否会显示参数录入框todo
//		/*try {
//			populatedReport = JasperFillManager.fillReport(
//					fileName, model, new JREmptyDataSource());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}*/
//		
//		super.renderReport(populatedReport, model, response);
//	}
//}
