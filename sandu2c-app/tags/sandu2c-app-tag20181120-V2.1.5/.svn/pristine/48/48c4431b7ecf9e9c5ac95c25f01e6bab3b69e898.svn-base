package com.sandu.common.pay;

import com.sandu.common.properties.AppProperties;
import com.sandu.common.util.Utils;

import javax.servlet.http.HttpServletRequest;

public class QrCodeUtil {
    public static String generateQrCode(HttpServletRequest request, String content, String batchNo) {
        String codeUrl = Utils.getPropertyName("config/res", "pay.files.code.update.path", "/AA/f_resource/[yyyy]/[MM]/[dd]/[HH]/pay/files/code/") + "qr-" + batchNo + ".png";
        codeUrl = Utils.replaceDate(codeUrl);
        String filePath = Utils.getAbsolutePath(codeUrl, null);
        ZxingUtils.getQRCodeImge(content, 256, filePath);
        return codeUrl;
    }

    //去掉HttpServletRequest参数
    public static String generateQrCode(String uploadRoot,String content, String batchNo) {
    	//相对路径
    	String codeUrl = Utils.getPropertyName("config/res", "pay.files.code.update.path", "/AA/f_resource/[yyyy]/[MM]/[dd]/[HH]/pay/files/code/") + "qr-" + batchNo + ".png";
        codeUrl = Utils.replaceDate(codeUrl);
        //绝对路径
        String filePath = Utils.dealWithPath(uploadRoot + codeUrl, Utils.getValueByFileKey(AppProperties.APP, AppProperties.SYSTEM_FORMAT_FILEKEY, "linux"));
        ZxingUtils.getQRCodeImge(content, 256, filePath);
        return codeUrl;
    }
    public static String generateQrCode(String content, String batchNo) {
		/*String codeUrl = Utils.getPropertyName("app","pay.files.code.update.path","/e_userlogs/files/code/")+"qr-" + batchNo + ".png";*/
		String codeUrl = Utils.getPropertyName("config/res","pay.files.code.update.path","/AA/f_resource/[yyyy]/[MM]/[dd]/[HH]/pay/files/code/")+"qr-" + batchNo + ".png";
		codeUrl = Utils.replaceDate(codeUrl);
		/*String basePath = Constants.UPLOAD_ROOT;*/
		/*String filePath = new StringBuilder(basePath).append(codeUrl).toString();*/
		String filePath = Utils.getAbsolutePath(codeUrl, null);
		ZxingUtils.getQRCodeImge(content, 256, filePath);
		return codeUrl;
	}
}
