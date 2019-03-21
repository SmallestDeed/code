package com.sandu.service.groupproduct.impl;

import static java.util.stream.Collectors.toMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.groupproducct.model.ResFile;
import com.sandu.api.groupproducct.service.ResFileService;
import com.sandu.service.groupproduct.dao.ResFileDao;
/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * wangwang-product
 *
 * @author Sandu
 * @datetime 2018/3/27 16:44
 */
@Service("resFileService")
public class ResFileServiceImpl implements ResFileService {
    @Autowired
    private ResFileDao resFileDao;

    @Override
    public ResFile getFileById(Integer id) {
        return resFileDao.selectByPrimaryKey(id.longValue());
    }

    @Override
    public boolean updateFileById(ResFile file) {
        return resFileDao.updateByPrimaryKey(file) > 0;
    }

    @Override
    public Integer addFile(ResFile resFile) {
        resFileDao.insertSelective(resFile);
        return resFile.getId().intValue();
    }

    @Override
	public Map<Long, String> idAndUrlMap(List<Integer> contractIds) {
		contractIds = contractIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (contractIds == null || contractIds.size() == 0) {
            return Collections.emptyMap();
        }
        List<ResFile> fileLists = resFileDao.listByIds(contractIds);
        return fileLists.stream().collect(toMap(ResFile::getId,ResFile::getFilePath));	
    }
}
