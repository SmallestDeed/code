package com.sandu.common.uploader.concrete;

import com.sandu.common.exception.StorageException;
import com.sandu.common.uploader.AbstractUploader;
import com.sandu.common.util.file.FileUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * @author bvvy (bvvyeh@gmail.com) 2017/12/16 18:52
 */
public class ImgUploader extends AbstractUploader {

    private BufferedImage bi;


    public ImgUploader(MultipartFile file, String path) {
        super(file, path);
    }

    @Override
    protected void writeFile() {
        try {
            ImageIO.write(bi, FilenameUtils.getExtension(path), new File(path));
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }


    @Override
    protected void checkTypeLimit() {
        try {
            BufferedImage bi = ImageIO.read(file.getInputStream());
            this.bi = bi;
            if (!FileUtil.isImage(bi)) {
                throw new StorageException("请上传图片文件");
            }
            fileVO.setHeight(bi.getHeight());
            fileVO.setWidth(bi.getWidth());
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }
}
