package com.sandu.common.uploader;


import com.sandu.api.storage.output.FileVO;
import com.sandu.common.exception.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public abstract class AbstractUploader implements Uploader {


    /**
     * 默认限制大小 1M
     */
    private static final long DEFAULT_LIMIT = 1024 * 1024;
    /**
     * 限制文件大小
     */
    protected long limit = DEFAULT_LIMIT;
    protected MultipartFile file;
    protected String path;
    protected FileVO fileVO;

    public AbstractUploader(MultipartFile file, String path) {
        this.file = file;
        this.path = path;
        this.fileVO = new FileVO();
        this.fileVO.setSize(file.getSize());
        this.checkTypeLimit();
    }

    public AbstractUploader(MultipartFile file, String path, long limit) {
        this(file, path);
        this.limit = limit;
    }

    @Override
    public long getLimit() {
        return limit;
    }

    @Override
    public void setLimit(long limit) {
        this.limit = limit;
    }

    @Override
    public FileVO getFileVO() {
        return fileVO;
    }

    /**
     * 检查文件大小限制
     */
    protected void checkSizeLimit() {
        if (file.getSize() > this.limit) {
            //todo apache fileUtils 的 byteCountToDisplaySize 显示不精确 , 看是否需要重新实现
            throw new StorageException(String.format("请上传文件小于 %s 的文件", FileUtils.byteCountToDisplaySize(this.limit)));
        }
    }

    @Override
    public void upload() {
        checkSizeLimit();
        log.info("上传文件: {},文件写入路径 --{}", this.fileVO, this.path);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(this::writeFile);
    }

    protected void writeFile() {
        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }

    protected abstract void checkTypeLimit();
}
