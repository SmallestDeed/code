package com.nork.common.util.QRCode;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.net.util.Base64;
import sun.misc.BASE64Encoder;

public final class MatrixToImageWriter {

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private static final int QRCODE_SIZE = 400;
	private static final int LOGO_WIDTH = 60;
	private static final int LOGO_HEIGHT = 60;

	private MatrixToImageWriter() {
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	public static void writeToFileWidthLogo(BitMatrix matrix, String format, File file, String logoPath) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		try {
			addLogo(image,logoPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			String content = "http://www.baidu.com";
//			String path = "E:/11";
//			File dir=new File(path);
//			if(!dir.exists())
//				dir.mkdirs();

//			@SuppressWarnings("rawtypes")
//			Map hints = new HashMap();
//			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400);
		/*	File file1 = new File(path, "二维码.jpg");
			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);*/
			BufferedImage image = toBufferedImage(bitMatrix);
			/*File file1 = new File(path, "二维码.jpg");
			ImageIO.write(image, "jpg", file1);*/
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(image,"png",outputStream);
			byte[] bytes = outputStream.toByteArray();
			System.out.println(new String(bytes));
			String imgBase64Str = new String(Base64.encodeBase64(bytes));
		/*	BASE64Encoder encoder = new BASE64Encoder();
			String imgBase64Str = encoder.encodeBuffer(bytes).trim();*/
			System.out.println(imgBase64Str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成二维码图片
	 * @param content
	 * @param path
	 * @param picName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static void createQRCodeImage(String content, String path, String picName){
		try {
			File dir=new File(path);
			if(!dir.exists())
				dir.mkdirs();
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			@SuppressWarnings("rawtypes")
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
			File file1 = new File(path, picName);
			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成二维码图片(带logo)
	 * @param content
	 * @param path
	 * @param picName
	 * @param logoPath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static void createLogoQRCodeImage(String content, String path, String picName, String logoPath){
		try {
			File dir=new File(path);
			if(!dir.exists())
				dir.mkdirs();
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			@SuppressWarnings("rawtypes")
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
			File file1 = new File(path, picName);
			MatrixToImageWriter.writeToFileWidthLogo(bitMatrix, "jpg", file1, logoPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片加logo
	 * @param source
	 * @param logoPath
	 */
	public static void addLogo(BufferedImage source, String logoPath )throws Exception{
		File file = new File(logoPath);
		if( !file.exists() || file.isDirectory() ){
			return;
		}
		Image src = ImageIO.read(file);
		Image image = src.getScaledInstance(LOGO_WIDTH, LOGO_HEIGHT,
				Image.SCALE_SMOOTH);
		BufferedImage tag = new BufferedImage(LOGO_WIDTH, LOGO_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		src = image;

		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (QRCODE_SIZE - LOGO_WIDTH) / 2;
		int y = (QRCODE_SIZE - LOGO_HEIGHT) / 2;
		graph.drawImage(src, x, y, LOGO_WIDTH, LOGO_HEIGHT, null);
		Shape shape = new RoundRectangle2D.Float(x, y, LOGO_WIDTH, LOGO_WIDTH, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	public static String getQRCodeBase64(String content,Integer width,Integer height) throws Exception {
		String imgBase64Str = null;
		if(width == null){
			width = 400;
		}
		if(height == null){
			height = 400;
		}
		/*content=new String(content.getBytes("UTF-8"),"ISO-8859-1");*/
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		BufferedImage image = toBufferedImage(bitMatrix);
		ImageIO.write(image,"png",outputStream);
		byte[] bytes = outputStream.toByteArray();
		imgBase64Str = new String(Base64.encodeBase64(bytes));
		/*BASE64Encoder encoder = new BASE64Encoder();
		imgBase64Str = encoder.encodeBuffer(bytes).trim();*/
		return imgBase64Str;
	}
}
