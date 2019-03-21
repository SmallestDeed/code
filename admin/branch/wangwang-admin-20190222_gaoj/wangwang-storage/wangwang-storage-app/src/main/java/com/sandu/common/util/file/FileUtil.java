package com.sandu.common.util.file;

import com.sandu.common.exception.StorageException;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * @author bvvy (bvvyeh@gmail.com) 2017/12/16 18:52
 */
public class FileUtil {

    /**
     * 判断是否是图片
     *
     * @param bi bi
     * @return true or false
     */
    public static boolean isImage(BufferedImage bi) {
        return (bi != null && bi.getWidth() > 0 && bi.getHeight() > 0);
    }

    /**
     * 判断是否是图片
     *
     * @param is is
     * @return true or false
     */
    public static boolean isImage(InputStream is) throws IOException {
        return isImage(ImageIO.read(is));
    }

    public static long sizeOf(FileInputStream inputStream) {
        try {
            return inputStream.getChannel().size();
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }

    public static byte[] readFileToByteArray(File file) {

        try {
            return FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            throw new StorageException("文件不存在");
        }
    }

    public static void removeFile(String path) {

        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}
