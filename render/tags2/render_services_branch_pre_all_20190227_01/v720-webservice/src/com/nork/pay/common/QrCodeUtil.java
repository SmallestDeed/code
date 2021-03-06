package com.nork.pay.common;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.nork.common.util.Constants;
import com.nork.common.util.QRCode.MatrixToImageWriter;
import com.nork.common.util.Utils;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.util.Random;

public class QrCodeUtil {


	private static final String CHARSET = "utf-8";
	private static final String FORMAT_NAME = "JPG";
	// 二维码尺寸
	private static final int QRCODE_SIZE = 256;
	// LOGO宽度
	private static final int WIDTH = 60;
	// LOGO高度
	private static final int HEIGHT = 60;

	public static String generateQrCode(HttpServletRequest request, String content, String batchNo) {
		String codeUrl = Utils.getPropertyName("config/res","pay.files.code.update.path","/AA/f_resource/[yyyy]/[MM]/[dd]/[HH]/pay/files/code/")+"qr-" + batchNo + ".png";
		codeUrl = Utils.replaceDate(codeUrl);
		// String  path=request.getServletContext().getRealPath("/files/code")+batchNo+".png";
//		String basePath = request.getServletContext().getRealPath("/");
		/*String basePath = Constants.UPLOAD_ROOT;*/
		/*String filePath = new StringBuilder(basePath).append(codeUrl).toString();*/
		String filePath = Utils.getAbsolutePath(codeUrl, null);
		ZxingUtils.getQRCodeImge(content, 256, filePath);
		return codeUrl;
	}
	//去掉HttpServletRequest参数
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

	/**
	 * 生成二维码带logo
	 * @param content
	 * @param batchNo
	 * @param companyLogo
	 * @return
	 */
	public static String generateQrCode(String content, String batchNo, String companyLogo) {
		String codeUrl = Utils.getPropertyName("config/res","pay.files.code.update.path","/AA/f_resource/[yyyy]/[MM]/[dd]/[HH]/pay/files/code/")+"qr-" + batchNo + ".png";
		codeUrl = Utils.replaceDate(codeUrl);
		String filePath = Utils.getAbsolutePath(codeUrl, null);
		try {
			QrCodeUtil.encode(content,companyLogo,filePath,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codeUrl;
	}

	private static BufferedImage createImage(String content, String imgPath,
											 boolean needCompress) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
						: 0xFFFFFFFF);
			}
		}
		if (imgPath == null || "".equals(imgPath)) {
			return image;
		}
		// 插入图片
		QrCodeUtil.insertImage(image, imgPath, needCompress);
		return image;
	}

	/**
	 * 插入LOGO
	 *
	 * @param source
	 *            二维码图片
	 * @param imgPath
	 *            LOGO图片地址
	 * @param needCompress
	 *            是否压缩
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, String imgPath,
									boolean needCompress) throws Exception {
		File file = new File(imgPath);
		if (!file.exists()) {
			System.err.println(""+imgPath+"   该文件不存在！");
			return;
		}
		Image src = ImageIO.read(new File(imgPath));
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > WIDTH) {
				width = WIDTH;
			}
			if (height > HEIGHT) {
				height = HEIGHT;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (QRCODE_SIZE - width) / 2;
		int y = (QRCODE_SIZE - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 *
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param destPath
	 *            存放目录
	 * @param needCompress
	 *            是否压缩LOGO
	 * @throws Exception
	 */
	public static void encode(String content, String imgPath, String destPath,
							  boolean needCompress) throws Exception {
		BufferedImage image = QrCodeUtil.createImage(content, imgPath,
				needCompress);
		mkdirs(destPath);
		String file = new Random().nextInt(99999999)+".jpg";
		ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));
	}

	/**
	 * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
	 */
	public static void mkdirs(String destPath) {
		File file =new File(destPath);
		//当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
	}
}
