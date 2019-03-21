package com.sandu.common.uploader;

import com.sandu.api.storage.output.FileVO;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * 这设计还是不对...
 * @author bvvy (bvvyeh@gmail.com) 2017/12/16 18:52
 */
public interface Uploader {

    /**
     * 上传
     */
    void upload();

    /**
     * 限制
     *
     * @return return
     */
    long getLimit();

    /**
     * 设置大小限制
     */
    void setLimit(long size);

    /**
     * 文件信息
     *
     * @return fileInfo
     */

    FileVO getFileVO();
}
