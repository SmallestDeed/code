package com.sandu.common.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class ThumbnailConvert {
    private String CMYK_COMMAND = "mogrify -colorspace RGB -quality 80 file1";// 转换cmyk格式

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
            System.err.println("IOException " + e.getMessage());
            return false;
        }
        return true;
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        //boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
           /* if (hasAlpha) {
	         transparency = Transparency.BITMASK;
	         }*/

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
	        /*if (hasAlpha) {
	         type = BufferedImage.TYPE_INT_ARGB;
	         }*/
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }

}
