package com.sandu.resource.service.impl;

import com.sandu.resource.dao.ResFileDao;
import com.sandu.resource.model.ResFile;
import com.sandu.resource.service.ResFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文件资源service实现
 * @author WangHaiLin
 * @date 2018/8/15  13:50
 */

@Service("resFileService")
public class ResFileServiceImpl implements ResFileService {
    private static final Logger logger = LoggerFactory.getLogger(ResFileServiceImpl.class.getName());

    @Autowired
    private ResFileDao resFileDao;

    /**
     *
     * @param fileId 文件Id
     * @return
     */
    @Override
    public ResFile getById(Long fileId) {
        return resFileDao.getById(fileId);
    }
}
