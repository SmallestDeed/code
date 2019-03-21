package com.sandu.service.system.impl;

import com.sandu.api.system.model.ResVersion;
import com.sandu.api.system.service.ResVersionService;
import com.sandu.service.system.dao.ResVersionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("resVersionService")
public class ResVersionServiceImpl implements ResVersionService {

    @Autowired
    private ResVersionDao resVersionDao;

    @Override
    public ResVersion get(Integer patchFileId) {
        return resVersionDao.selectByPrimaryKey(patchFileId);
    }

    @Override
    public List<ResVersion> getList(ResVersion exeFile_search) {
        return resVersionDao.selectList(exeFile_search);
    }
}
