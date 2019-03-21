package com.sandu.common.uploader.factory;

import com.sandu.common.exception.StorageException;
import com.sandu.common.uploader.Uploader;
import com.sandu.common.uploader.concrete.CommonFileUploader;
import com.sandu.common.uploader.concrete.ImgUploader;
import com.sandu.common.uploader.concrete.ModelUploader;
import org.springframework.web.multipart.MultipartFile;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * @author bvvy (bvvyeh@gmail.com) 2017/12/16 18:52
 */
public class UploaderFactory {

    public static Uploader createUploader(String type, MultipartFile file, String path) {

        switch (type) {
            case "image":
                return new ImgUploader(file, path);
            case "file":
                return new CommonFileUploader(file, path);
            case "model":
                return new ModelUploader(file, path);
            default:
                throw new StorageException(String.format("不存在的类型 %s", type));
        }
    }
}
