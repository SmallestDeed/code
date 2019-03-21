package com.sandu.common.file.uploader.concrete;

import com.sandu.common.file.uploader.AbstractUploader;
import org.springframework.web.multipart.MultipartFile;

/**
 * creator by bvvy
 */
public class ModelUploader extends AbstractUploader {


    public ModelUploader(MultipartFile file, String path) {
        super(file, path);
    }

    @Override
    protected void checkTypeLimit() {
    }

}

