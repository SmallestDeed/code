package com.sandu.util;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbnailConvert {

	private static final Logger LOG = LoggerFactory.getLogger(ThumbnailConvert.class);

	// 转换cmyk格式
	private String CMYK_COMMAND = "mogrify -colorspace RGB -quality 80 file1";

	public void setCMYK_COMMAND(String file1) {
		exeCommand(CMYK_COMMAND.replace("file1", file1));
	}

	public boolean exeCommand(String cmd) {
		InputStreamReader ir = null;
		LineNumberReader input = null;
		try {
			// linux下java执行指令：Runtime.getRuntime().exec (String str);
			Process process = Runtime.getRuntime().exec(cmd);
			ir = new InputStreamReader(process.getInputStream());
			input = new LineNumberReader(ir);
			while ((input.readLine()) != null) {
			}
			ir.close();
			input.close();
		} catch (java.io.IOException e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		BufferedImage bimage = null;
		image = new ImageIcon(image).getImage();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			int transparency = Transparency.OPAQUE;
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			int type = BufferedImage.TYPE_INT_RGB;
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		Graphics g = bimage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}
}
