package com.nork.common.util;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/*import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

*/

public class ImageUtils {
	private static Logger logger = Logger.getLogger(ImageUtils.class);
	public static int MID =0;//left
	public static int LEFT_UP =1;//left
	public static int RIGHT_UP =2;//right
	public static int LEFT_DOWN =3;//right
	public static int RIGHT_DOWN =4;//right
	public static int RIGHT_UP2 =5;
	public static int RIGHT_DOWN2 =6;
	/** 
     * 图片添加水印 
     * @param srcImgPath 需要添加水印的图片的路径 
     * @param outImgPath 添加水印后图片输出路径 
     * @param markContentColor 水印文字的颜色 
     * @param waterMarkContent 水印的文字 
     */  
    public void mark(String srcImgPath, String outImgPath, Color markContentColor, String waterMarkContent) {  
        try {  
            // 读取原图片信息  
            File srcImgFile = new File(srcImgPath);  
            Image srcImg = ImageIO.read(srcImgFile);  
            int srcImgWidth = srcImg.getWidth(null);  
            int srcImgHeight = srcImg.getHeight(null);  
            // 加水印  
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);  
            Graphics2D g = bufImg.createGraphics();  
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);  
            //Font font = new Font("Courier New", Font.PLAIN, 12);  
            Font font = new Font("宋体", Font.PLAIN, 50);    
            g.setColor(markContentColor); //根据图片的背景设置水印颜色  
              
            g.setFont(font);  
            int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 3;  
            int y = srcImgHeight - 3;  
            //int x = (srcImgWidth - getWatermarkLength(watermarkStr, g)) / 2;  
            //int y = srcImgHeight / 2;  
            g.drawString(waterMarkContent, x, y);  
            g.dispose();  
            // 输出图片  
            FileOutputStream outImgStream = new FileOutputStream(outImgPath);  
            ImageIO.write(bufImg, "jpg", outImgStream);  
            outImgStream.flush();  
            outImgStream.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 获取水印文字总长度 
     * @param waterMarkContent 水印的文字 
     * @param g 
     * @return 水印文字总长度 
     */  
    public int getWatermarkLength(String waterMarkContent, Graphics2D g) {  
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());  
    }  

    /** 
     * 获取水印文字高度
     * @param waterMarkContent 水印的文字 
     * @param g 
     * @return 水印文字总长度 
     */  
    public int getWatermarkHeight(String waterMarkContent, Graphics2D g) {  
        return g.getFontMetrics(g.getFont()).getHeight();  
    }  
   
    /**
	 * 打印文字水印图片
	 * 
	 * @param pressText
	 *            --文字
	 * @param targetImg
	 *            -- 目标图片
	 * @param fontName
	 *            -- 字体名
	 * @param fontStyle
	 *            -- 字体样式
	 * @param color
	 *            -- 字体颜色
	 * @param fontSize
	 *            -- 字体大小
	 * @param totalX
	 *            --图片总x像素
	 * @param totalY
	 *            --图片总y像素
	 */

	public static void pressTextByType(String pressText, String targetImg,
			String fontName, int fontStyle, int color, int fontSize, int type) {
		try {
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			logger.info("wideth="+wideth+",height="+height);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			//Graphics g = image.createGraphics();
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);

			//g.setColor(Color.black);
			 Font font = new Font("微软雅黑",Font.BOLD, 22);
			 if(!StringUtils.isEmpty(fontName)){
				 new Font(fontName, fontStyle, fontSize);
			 }
			 g.setFont(font);
			 g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.2f));
			int wideth_biao = g.getFontMetrics(g.getFont()).charsWidth(pressText.toCharArray(), 0, pressText.length());
			int height_biao = g.getFontMetrics(g.getFont()).getHeight();
			
			FontRenderContext frc = g.getFontRenderContext();
		    GlyphVector v = font.createGlyphVector(frc, pressText);
		    Shape shape = v.getOutline();
			if(ImageUtils.LEFT_UP==type){
				//g.drawString(pressText, 25,height_biao*2-10);
				g.translate(25,height_biao*2-10);
			}else if(ImageUtils.RIGHT_UP==type){
				//g.drawString(pressText, wideth-wideth_biao-25,height_biao*2-10);
				g.translate(wideth-wideth_biao-25,height_biao*2-10);
			}else if(ImageUtils.LEFT_DOWN==type){
				//g.drawString(pressText, 25,height-height_biao);
//				FontRenderContext frc = g.getFontRenderContext();
//			    GlyphVector v = font.createGlyphVector(frc, "2016-03-22");
//			    Shape shape = v.getOutline();
			    g.translate(25,height-height_biao);
//			    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//			    g.setColor(Color.black);
//			    g.fill(shape);
//			    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.2f));
//			    g.setColor(Color.WHITE);
//			    g.setStroke(new BasicStroke(1));
//			    g.draw(shape);
			}else if(ImageUtils.RIGHT_DOWN==type){
				//g.drawString(pressText, wideth-wideth_biao-25,height-height_biao);
				g.translate(wideth-wideth_biao-25,height-height_biao);
			}else{
				 //g.drawString(pressText, (wideth - wideth_biao) / 2,(height - height_biao) / 2);
				g.translate((wideth - wideth_biao) / 2,(height - height_biao) / 2);
			}
		    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g.setColor(Color.black);
		    g.fill(shape);
		    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.2f));
		    g.setColor(Color.WHITE);
		    g.setStroke(new BasicStroke(1));
		    g.draw(shape);
			g.dispose();
			/*FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();*/
			 String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
			 ImageIO.write(image, /*"GIF"*/ formatName /* format desired */ , new File(targetImg) /* target */ );
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	/**
	 * 把图片印刷到图片上
	 * 
	 * @param pressImg
	 *            -- 水印文件
	 * @param targetImg
	 *            -- 目标文件
	 * @param type
	 *            -- 生成的位置类型
	 */
	public final static void pressImageByType(String pressImg, String targetImg,int type) {
		try {
			// 目标文件
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			logger.info("src:wideth="+wideth+",height="+height);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);

			// 水印文件
			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			logger.info("ico:wideth="+wideth_biao+",height="+height_biao);
			if(ImageUtils.LEFT_UP==type){
				g.drawImage(src_biao, wideth_biao * 2,
						height_biao * 2, wideth_biao, height_biao, null);
			}else if(ImageUtils.RIGHT_UP==type){
				g.drawImage(src_biao, wideth - wideth_biao * 2,
						height_biao * 2, wideth_biao, height_biao, null);
			}else if(ImageUtils.LEFT_DOWN==type){
				g.drawImage(src_biao, wideth_biao * 2,
						height - height_biao * 2, wideth_biao, height_biao, null);
			}else if(ImageUtils.RIGHT_DOWN==type){
				g.drawImage(src_biao, wideth - wideth_biao * 2,
						height - height_biao * 2, wideth_biao, height_biao, null);
			}else{
				 g.drawImage(src_biao, (wideth - wideth_biao) / 2,
						 (height - height_biao) / 2, wideth_biao, height_biao, null);
			}
		
			
	
			// 水印文件结束
			g.dispose();
		/*	FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();*/
			 String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
			 ImageIO.write(image, /*"GIF"*/ formatName /* format desired */ , new File(targetImg) /* target */ );
			

		 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * 把图片印刷到图片上
	 * 
	 * @param pressImg
	 *            -- 水印文件
	 * @param targetImg
	 *            -- 目标文件
	 * @param x
	 *            --x坐标
	 * @param y
	 *            --y坐标
	 */
	public final static void pressImage(String pressImg, String targetImg,
			int x, int y) {
		try {
			// 目标文件
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			logger.info("src:wideth="+wideth+",height="+height);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);

			// 水印文件
			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			logger.info("ico:wideth="+wideth_biao+",height="+height_biao);
			/*g.drawImage(src_biao, (wideth - wideth_biao) / 2,
					(height - height_biao) / 2, wideth_biao, height_biao, null);*/
			g.drawImage(src_biao, x,y, wideth_biao, height_biao, null);
		/*	g.drawString(pressText, wideth - fontSize - x, height - fontSize
					/ 2 - y);*/
			// 水印文件结束
			g.dispose();
			/*FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();*/
			 String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
			 ImageIO.write(image, /*"GIF"*/ formatName /* format desired */ , new File(targetImg) /* target */ );
			    /*FileOutputStream outImgStream = new FileOutputStream(targetImg);  
	            ImageIO.write(image, formatName, outImgStream);  
	            outImgStream.flush();  
	            outImgStream.close();*/
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	/**
	 * 打印文字水印图片
	 * 
	 * @param pressText
	 *            --文字
	 * @param targetImg
	 *            -- 目标图片
	 * @param fontName
	 *            -- 字体名
	 * @param fontStyle
	 *            -- 字体样式
	 * @param color
	 *            -- 字体颜色
	 * @param fontSize
	 *            -- 字体大小
	 * @param x
	 *            -- 偏移量
	 * @param y
	 */

	public static void pressText(String pressText, String targetImg,
			String fontName, int fontStyle, int color, int fontSize, int x,
			int y) {
		try {
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			logger.info("wideth="+wideth+",height="+height);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);

			g.setColor(Color.RED);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			
			/*g.drawString(pressText, wideth - fontSize - x, height - fontSize
					/ 2 - y);*/
			g.drawString(pressText, x, y);
			g.dispose();
			/*FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();*/
			 String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
			 ImageIO.write(image, /*"GIF"*/ formatName /* format desired */ , new File(targetImg) /* target */ );
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	/*
     * 生成文字
     */
	public static void pressText(String pressText, String targetImg, int type) {
		pressTextByType(pressText,targetImg,null,-1,-1,-1,type);
	}
	/*
     * 生成图片
     */
	public final static void pressImage(String pressImg, String targetImg,int type) {
		pressImageByType(pressImg, targetImg, type);
	}
	
	/**
	 * 添加水印(方案一)
	 * @param serverFilePath 图片路径
	 * @param str 水印图右上角的字符串
	 * @param isTurnOn 开关灯
	 * @throws IOException 
	 */
	/*public static void watermarking(String serverFilePath,String str,Integer isTurnOn) throws IOException{
		logger.warn("--------生成水印图.path:"+serverFilePath);
		File file=new File(serverFilePath);
		if(!file.exists())
			throw new RuntimeException("未找到待添加水印的图片.path:"+serverFilePath);
		Image src = ImageIO.read(file);
		int wideth = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawImage(src, 0, 0, wideth, height, null);
		右下角时间
		image = watermarkingText(Utils.getCurrentDateTime(Utils.DATE_TIME2), image, null, Font.BOLD, RIGHT_DOWN,0.3f);
		右上角文字
		image = watermarkingText(str, image, null, Font.BOLD, RIGHT_UP2,0.3f);
		中间logo
		水印图片存放文件夹
		String urlStr=Constants.UPLOAD_ROOT.trim()+"/system/logo_watermark/";
		系统处理
		String SYSTEM_FORMAT = Utils.getValue("app.system.format", "linux").trim();
		if("windows".equals(SYSTEM_FORMAT)){
			urlStr = urlStr.replace("/", "\\");
		}else{
			
		}
		watermarkingImage(urlStr+"sanduspace_logo.png", image, MID, 3.0);
		开关灯图片
		if(isTurnOn!=null){
			if(new Integer(1).equals(isTurnOn)){
				watermarkingImage(urlStr+"lightOn.png", image, RIGHT_UP2, 30.0);
			}else{
				watermarkingImage(urlStr+"lightOff.png", image, RIGHT_UP2, 30.0);
			}
		}
		生成图片
		String formatName = serverFilePath.substring(serverFilePath.lastIndexOf(".") + 1);
		ImageIO.write(image, formatName, new File(serverFilePath));
		logger.warn("--------生成水印图结束.path:"+serverFilePath);
	}*/
	
	/**
	 * 添加文字水印
	 * @param text
	 * @param image
	 * @param fontName
	 * @param fontStyle
	 * @param color
	 * @param fontSize
	 * @param type
	 * @return
	 */
	private static BufferedImage watermarkingText(String text, BufferedImage image,
		String fontName, int fontStyle, int type, float alpha) {
		int width = image.getWidth();
		int height = image.getHeight();
		int fontSize=(width/100)+1;
		Graphics2D g = image.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		Font font = new Font(fontName, fontStyle, fontSize);
		g.setFont(font);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
		int width_biao = g.getFontMetrics(g.getFont()).charsWidth(text.toCharArray(), 0, text.length());
		int height_biao = g.getFontMetrics(g.getFont()).getHeight();
		FontRenderContext frc = g.getFontRenderContext();
	    GlyphVector v = font.createGlyphVector(frc, text);
	    Shape shape = v.getOutline();
		if(ImageUtils.LEFT_UP==type){
			g.translate(25,height_biao*2-10);
		}else if(ImageUtils.RIGHT_UP==type){
			g.translate(width-width_biao-25,height_biao*2-10);
		}else if(ImageUtils.LEFT_DOWN==type){
		    g.translate(25,height-height_biao);
		}else if(ImageUtils.RIGHT_DOWN==type){
			g.translate(width-(width_biao*1.25),height-(height_biao*0.7));
		}else if(ImageUtils.RIGHT_UP2==type){
			g.translate(width-width_biao-(width*0.01),height_biao*2);
		}else{
			g.translate((width - width_biao) / 2,(height - height_biao) / 2);
		}
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setColor(Color.WHITE);
	    g.fill(shape);
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
	    g.setColor(Color.GRAY);
	    g.setStroke(new BasicStroke((width/2000)+1));
	    g.draw(shape);
		g.dispose();
		return image;
	}
	
	/**
	 * 添加水印图
	 * @param pressImg 水印图地址
	 * @param image BufferedImage流
	 * @param type 位置
	 * @param rate 比率
	 * @return
	 * @throws IOException
	 */
	public final static BufferedImage watermarkingImage(String pressImg, BufferedImage image, int type, double rate) throws IOException {
		// 目标文件
		int width = image.getWidth();
		int height = image.getHeight();
		Graphics g = image.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		// 水印文件
		File _filebiao = new File(pressImg);
		if(!_filebiao.exists()){
			logger.info("------水印图片不存在.path:"+pressImg);
			return image;
		}
		Image src_biao = ImageIO.read(_filebiao);
		int width_biao = src_biao.getWidth(null);
		rate=(width/rate)/width_biao;
		width_biao=(int)(width_biao*rate);
		int height_biao = src_biao.getHeight(null);
		height_biao=(int)(height_biao*rate);
		if (ImageUtils.LEFT_UP == type) {
			//g.drawImage(src_biao, width_biao * 2, height_biao * 2, width_biao, height_biao, null);
			g.drawImage(src_biao, (int)(width_biao * 0.5), height_biao, width_biao, height_biao, null);
		} else if (ImageUtils.RIGHT_UP == type) {
			g.drawImage(src_biao, width - (int)(width_biao * 1.2), (int)(height_biao * 0.2), width_biao, height_biao, null);
		} else if (ImageUtils.LEFT_DOWN == type) {
			g.drawImage(src_biao, (int)(width_biao*0.5), height - (int)(height_biao*1.5), width_biao, height_biao, null);
		} else if (ImageUtils.RIGHT_DOWN == type) {
			g.drawImage(src_biao, width - width_biao * 2, height - height_biao * 2, width_biao, height_biao, null);
		} else if (ImageUtils.RIGHT_DOWN2 == type) {
			g.drawImage(src_biao, (int)(width - width_biao * 1.1), (int)(height - height_biao * 1.4), width_biao, height_biao, null);
		} else if (ImageUtils.RIGHT_UP2== type) {
			g.drawImage(src_biao, (int)(width - width_biao-(width*0.01)), (int)(height_biao * 1.5), width_biao, height_biao, null);
		} else {
			g.drawImage(src_biao, (width - width_biao) / 2, (height - height_biao) / 2, width_biao, height_biao,
					null);
		}
		g.dispose();
		return image;
	}
	
	/**
	 * 生成水印图方案2
	 * @param serverFilePath 图片路径
	 * @param scene 1:白天;2:黑夜;3:黄昏
	 * @param type 1:高清;2:快速
	 * @param isTurnOn 0:关灯;1:开灯
	 * @throws IOException
	 */
	public static void watermarking2(String serverFilePath,Integer scene,Integer type,Integer isTurnOn) throws IOException{

		/** 经 需求 去除水印 */
//		logger.warn("--------生成水印图.path:"+serverFilePath);
//		File file=new File(serverFilePath);
//		if(!file.exists())
//			throw new RuntimeException("未找到待添加水印的图片.path:"+serverFilePath);
//		BufferedImage image = ImageIO.read(file);
//		/*右下角时间*/
//		image = watermarkingText(Utils.getCurrentDateTime(Utils.DATE_TIME2), image, null, Font.BOLD, RIGHT_DOWN,0.3f);
//		/*水印图片存放文件夹*/
//		String str=ImageUtils.class.getResource("/").toString();
//		str=URLDecoder.decode(str, "utf-8");
//		String urlStr = str.substring(0, str.indexOf("WEB-INF/")) + "pages/app/image/logo_watermark/";
//		urlStr = urlStr.replace("file:/", "");
//		logger.warn("------系统识别为:"+Utils.getValue("app.system.format", "linux").trim());
//		if(StringUtils.equals("linux", Utils.getValue("app.system.format", "linux").trim())){
//			urlStr="/"+urlStr;
//		}
//		logger.warn("------水印图logo目录:"+urlStr);
//		/*系统处理*/
//		String SYSTEM_FORMAT = Utils.getValue("app.system.format", "linux").trim();
//		if("windows".equals(SYSTEM_FORMAT)){
//			urlStr = urlStr.replace("/", "\\");
//		}
//		/*中间logo*/
//		//watermarkingImage(urlStr+"sanduspace_logo.png", image, MID, 3.0);
//		watermarkingImage(urlStr+"sanduspace_logo3.png", image, RIGHT_DOWN2, 5.5);
//		/*中间logo->end*/
//		/*高清快速图片->end*/
//		/*生成图片*/
//		String formatName = serverFilePath.substring(serverFilePath.lastIndexOf(".") + 1);
//		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
//		if (iter.hasNext()) {
//		    ImageWriter writer = iter.next();
//		    ImageWriteParam param = writer.getDefaultWriteParam();
//		    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//		    param.setCompressionQuality(1f);
//		    FileImageOutputStream out = new FileImageOutputStream(new File(
//		    		serverFilePath));
//		    writer.setOutput(out);
//		    writer.write(null, new IIOImage(image, null, null), param);
//		    out.close();
//		    writer.dispose();
//		}
//		logger.warn("--------生成水印图结束.path:"+serverFilePath);
	}

	/**
	 * 生成水印图（原图）
	 * @param serverFilePath
	 * @param scene
	 * @param type
	 * @param isTurnOn
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static boolean watermarkingNew(String serverFilePath,Boolean isSmall,String fileKey) throws IOException{
		if(StringUtils.isBlank(fileKey)){
			return false;
		}
		if(isSmall){
			return true;

		}
		logger.warn("--------生成水印图.path:"+serverFilePath);
			/*判断图片是否存在*/
		Boolean original=false;
		String path=null;
		File file=new File(serverFilePath);
		BufferedImage image=null;
		if(file.exists()){
			try{
				image = ImageIO.read(file);
			}catch(Exception e){
				return false;
			}
		}else{
			throw new RuntimeException("图片不存在"+file);
		}
			/*保存原图*/
		original = saveOriginalpic(serverFilePath);
		if(!original){
			//logger.error(serverFilePath+"：该水印图，原图生成失败");
		}

			/*水印图片存放文件夹*/
		String str=ImageUtils.class.getResource("/").toString();
		str=URLDecoder.decode(str, "utf-8");
		String urlStr = str.substring(0, str.indexOf("WEB-INF/")) + "images";
		urlStr = urlStr.replace("file:/", "");
		logger.warn("------系统识别为:"+Utils.getAppValue("app.system.format", "linux").trim());
		if(StringUtils.equals("linux", Utils.getAppValue("app.system.format", "linux").trim())){
			urlStr="/"+urlStr;
		}
		logger.warn("------水印图logo目录:"+urlStr);
			/*系统处理*/
		String SYSTEM_FORMAT = Utils.getAppValue("app.system.format", "linux").trim();
		if("windows".equals(SYSTEM_FORMAT)){
			urlStr = urlStr.replace("/", "\\");
		}
			/*根据图片尺寸类型，获取相应的水印图*/
		path = imgProportion(isSmall,fileKey,image);
		if(StringUtils.isBlank(path)){
			//logger.error(serverFilePath+"：没有找到相符的水印图");
			return false;
		}
		watermarkingImage(urlStr+path, image, MID, 1.0);
		String formatName = serverFilePath.substring(serverFilePath.lastIndexOf(".") + 1);
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
		if (iter.hasNext()){
			ImageWriter writer = iter.next();
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(1f);
			FileImageOutputStream out = new FileImageOutputStream(new File(
					serverFilePath));
			writer.setOutput(out);
			writer.write(null, new IIOImage(image, null, null), param);
			out.close();
			writer.dispose();
		}

		logger.warn("--------生成水印图结束.path:"+serverFilePath);
		return true;
	}

	/**
	 * 根据图片尺寸类型，获取相应的水印图
	 * @param serverFilePath
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static Boolean saveOriginalpic(String serverFilePath) throws IOException{

		String relativePath = Utils.getRelativeUrlByAbsolutePath(serverFilePath);

		/*String url=Utils.getValueByFileKey(AppProperties.APP, AppProperties.UPLOAD_ROOT_FILEKEY, "/home/nork/resources");*/
		Boolean state = false;
		/*url= url.replace("\\", "/")+"/";
		serverFilePath = serverFilePath.replace("\\", "/");
		String originalFilePath=serverFilePath.replace(url, url+"src/");*/
		String originalFilePath = Utils.getAbsolutePath(relativePath, Utils.getAbsolutePathType.noEncrypt);
		File f = new File(originalFilePath);/*创建文件夹*/
		String fn = f.getName();
		String folder = originalFilePath.replace(fn, "");
		File x = new File(folder);
		if(!x.exists()){
			x.mkdirs();
		}
		FileInputStream fis=null;
		FileOutputStream fos=null;
		try {
			fis=new FileInputStream(serverFilePath);
			fos=new FileOutputStream(originalFilePath);
			byte buf[]=new byte[1024];
			int n=0;
			while ((n=fis.read(buf))!=-1){
				fos.write(buf);
			}
		}catch (Exception e) {
			logger.warn("--------在生成水印图时，复制原图失败:"+e);
		}finally{
			fis.close();
			fos.close();
		}
		File o = new File(originalFilePath);
		if(o.exists()){
			state=true;
		}
		return state;
	}
	/**
	 * 通过图片尺寸类型
	 * @param isSmall
	 * @param picType
	 * @param image
	 * @return
	 */
	public static String imgProportion(Boolean isSmall ,String picType,BufferedImage image){

		int oldWidth=image.getWidth();
		int oldHeight=image.getHeight();
		boolean flag = false;
		String imgName="";
		String fileUrl = "";
		if("design.designTemplet.pic".equals(picType)){
			if(isSmall){
				imgName="576X432.png";
				flag = true;
			}else{
				imgName="1200X900.png";
				flag = true;
			}
		}else if("design.designTemplet.piclist".equals(picType)){
			if(isSmall){
				imgName="576X432.png";
				flag = true;
			}else{
				imgName="1200X900.png";
				flag = true;
			}
		}else if("design.designTemplet.effectPlan".equals(picType)){
			if(isSmall){
				imgName="576X432.png";
				flag = true;
			}else{
				imgName="1200X900.png";
				flag = true;
			}
		}else if("product.baseProduct.piclist".equals(picType)){
			if(isSmall){
				imgName="256X256.png";
				flag = true;
			}else{
				if(oldWidth<700&&oldHeight<700){
					imgName="512X512.png";
					flag = true;
				}else{
					imgName="1024X1024.png";
					flag = true;
				}
			}
		}else if("home.spaceCommon.pic".equals(picType)){
			if(isSmall){
				imgName="576X432.png";
				flag = true;
			}else{
				imgName="1200X900.png";
				flag = true;
			}
		}else if("home.spaceCommon.view3dPic".equals(picType)){
			if(isSmall){
				imgName="576X432.png";
				flag = true;
			}else{
				imgName="1200X900.png";
				flag = true;
			}
		}else if("home.spaceCommon.viewPlan".equals(picType)){
			if(isSmall){
				imgName="576X432.png";
				flag = true;
			}else{
				imgName="1200X900.png";
				flag = true;
			}
		}else if("home.baseHouse.pic".equals(picType)){
			if(isSmall){
				imgName="576X432.png";
				flag = true;
			}else{
				imgName="1200X900.png";
				flag = true;
			}
		}else if("product.groupProduct.pic".equals(picType)){
			if(isSmall){

			}else{

			}
		}else if("product.baseBrand.logo".equals(picType)){
			if(isSmall){

			}else{

			}
		}else if("product.baseProduct.pic.ipad".equals(picType)){
			isSmall=true;
			imgName="256X256.png";
		}else if("baseHouse".equals(picType)){
			isSmall = true;
			imgName = "280X210.png";
			flag = true;
		}else{
			if(isSmall){

			}else{

			}
		}

		if(!flag){
			return "";
		}

		if(isSmall){
			fileUrl="/watermarkingSmall/";
		}else{

			fileUrl="/watermarking/";
		}

		return fileUrl+imgName;
	}


	public static void main(String[] args) throws IOException  {
		/*//////System.out.println(System.getProperty("user.dir"));
		//////System.out.println(ImageUtils.class.getResource(""));*/
		watermarking2("C:\\Users\\Administrator\\Desktop\\test1\\SP(O@4NCJ44~{C(N0{8RA~X.png", 3, 1, 1);
		//watermarking2("C:\\Users\\Administrator\\Desktop\\水印测试\\1kk.JPG", 3, 1, 1);
		//watermarking2("C:\\Users\\Administrator\\Desktop\\水印测试\\16.jpg", 3, 1, 1);
//		watermarking("C:\\Users\\Administrator\\Desktop\\水印测试\\21d1d128599121.55c8cf9909450.jpg","白天高清", 1);
		//ImageUtils.pressImage("D:\\nork\\logo.png", "D:\\nork\\src.png", 54, 300);
		//ImageUtils.pressText("高清", "D:\\nork\\src.png", "宋体", Font.PLAIN, Font.BOLD, 30, 1889-54, 41+28);
		//ImageUtils.pressText(Utils.getCurrentDateTime(Utils.DATE_TIME), "D:\\nork\\src.png", "宋体", Font.PLAIN, Font.BOLD, 30, 1866-287, 876+28);
	//	ImageUtils.pressImage("C:\\Users\\Administrator\\Desktop\\水印测试\\水印图.png", "C:\\Users\\Administrator\\Desktop\\水印测试\\l02xd_485216_20160301181414220102.jpg",ImageUtils.MID);
		//ImageUtils.pressText(Utils.getCurrentDateTime(Utils.DATE_TIME), "C:\\Users\\Administrator\\Desktop\\水印测试\\效果图.jpg", ImageUtils.LEFT_UP);
//		ImageUtils.pressText(Utils.getCurrentDateTime(Utils.DATE_TIME), "C:\\Users\\Administrator\\Desktop\\水印测试\\442576_20160309181604654.jpg", ImageUtils.RIGHT_UP);
//		ImageUtils.pressText(Utils.getCurrentDateTime(Utils.DATE_TIME), "C:\\Users\\Administrator\\Desktop\\水印测试\\442576_20160309181604654.jpg", ImageUtils.LEFT_DOWN);
//		ImageUtils.pressText(Utils.getCurrentDateTime(Utils.DATE_TIME), "C:\\Users\\Administrator\\Desktop\\水印测试\\442576_20160309181604654.jpg", ImageUtils.RIGHT_DOWN);
//		ImageUtils.pressText(Utils.getCurrentDateTime(Utils.DATE_TIME), "C:\\Users\\Administrator\\Desktop\\水印测试\\442576_20160309181604654.jpg", ImageUtils.MID);
		//ImageUtils.pressImage("D:\\nork\\logo.png", "D:\\nork\\src.png",ImageUtils.MID);
	/*	ImageUtils.pressImage("D:\\nork\\logo.png", "D:\\nork\\src.png", ImageUtils.LEFT_UP);
		ImageUtils.pressImage("D:\\nork\\logo.png", "D:\\nork\\src.png", ImageUtils.RIGHT_UP);
		ImageUtils.pressImage("D:\\nork\\logo.png", "D:\\nork\\src.png", ImageUtils.LEFT_DOWN);
		ImageUtils.pressImage("D:\\nork\\logo.png", "D:\\nork\\src.png", ImageUtils.RIGHT_DOWN);
		ImageUtils.pressImage("D:\\nork\\logo.png", "D:\\nork\\src.png", ImageUtils.MID);*/
		//Utils.copyDirectoryToDirectory(new File("C:\\Users\\Administrator\\Desktop\\test1"), new File("C:\\Users\\Administrator\\Desktop\\test2"));
		//////System.out.println("success");
	}

}
