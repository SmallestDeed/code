package com.sandu.resource.service;

import com.sandu.resource.model.ResFile;
import org.springframework.stereotype.Component;

/**
 * 文件资源库
 * @author WangHaiLin
 * @date 2018/8/15  13:41
 */

@Component
public interface ResFileService {
    /**
     * 通过Id查询资源文件
     * @param fileId 文件Id
     * @return ResFile
     */
    ResFile getById(Long fileId);
}
