package com.sandu.system.service.impl;

import com.google.common.base.Joiner;
import com.sandu.system.dao.ResFileMapper;
import com.sandu.system.model.ResFile;
import com.sandu.system.model.search.ResFileSearch;
import com.sandu.system.service.ResFileService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @version V1.0
 * @Title: ResFileServiceImpl.java
 * @Package com.sandu.system.service.impl
 * @Description:系统模块-文件资源库ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-07-02 17:36:00
 */
@Service("resFileService")
public class ResFileServiceImpl implements ResFileService {

    private static Logger logger = LoggerFactory.getLogger(ResFileServiceImpl.class);
    @Autowired
    private ResFileMapper resFileMapper;
    @Value("/data001/resource")
    private String rootPath;

    /**
     * 新增数据
     *
     * @param resFile
     * @return int
     */
    @Override
    public int add(ResFile resFile) {
        resFileMapper.insertSelective(resFile);
        return resFile.getId();
    }

    /**
     * 更新数据
     *
     * @param resFile
     * @return int
     */
    @Override
    public int update(ResFile resFile) {
        return resFileMapper
                .updateByPrimaryKeySelective(resFile);
    }

    /**
     * 更新数据
     *
     * @param resFile
     * @return int
     */
    @Override
    public int update(ResFile resFile, Integer businessId, String fileKey) {
        //获取多个 resPic filekeys ,busniessIds
        String key = resFile.getFileKey();
        Integer bId = resFile.getBusinessId();
        //当前资源 业务Id不为空，且fileKeys为空，就必须给fileKeys赋值
        if (bId != null && bId > 0 && !bId.equals(businessId) && StringUtils.isBlank(resFile.getFileKeys())) {
            resFile.setFileKeys(key);
        }
        String fileKeys = "";
        //如果fileKeys不为空，累加fileKeys
        if (StringUtils.isNotBlank(resFile.getFileKeys())) {
            fileKeys = resFile.getFileKeys() + "," + fileKey;
        } else {
            fileKeys = fileKey + "";
        }
        resFile.setFileKeys(fileKeys);

        String businessIds = "";
        //业务Ids为空或不为空，则赋值或累加
        if (bId != null && bId > 0 && !bId.equals(businessId) && StringUtils.isBlank(resFile.getBusinessIds())) {
            businessIds = resFile.getBusinessId() + "," + businessId;
            resFile.setBusinessId(0);
        } else if (StringUtils.isNotBlank(resFile.getBusinessIds())) {
            businessIds = resFile.getBusinessIds() + "," + businessId;
            resFile.setBusinessId(0);
        } else {
            businessIds = businessId + "";
            resFile.setBusinessId(businessId);
        }
        resFile.setBusinessIds(businessIds);
        return resFileMapper.updateByPrimaryKeySelective(resFile);
    }

  

    /**
     * 获取数据详情
     *
     * @param id
     * @return ResFile
     */
    @Override
    public ResFile get(Integer id) {
        return resFileMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param resFile
     * @return List<ResFile>
     */
    @Override
    public List<ResFile> getList(ResFile resFile) {
        return resFileMapper.selectList(resFile);
    }

    /**
     * 获取数据数量
     *
     * @return int
     */
    @Override
    public int getCount(ResFileSearch resFileSearch) {
        return resFileMapper.selectCount(resFileSearch);
    }


    /**
     * 分页获取数据
     *
     * @return List<ResFile>
     */
    @Override
    public List<ResFile> getPaginatedList(
            ResFileSearch resFileSearch) {
        return resFileMapper.selectPaginatedList(resFileSearch);
    }

	@Override
	public void saveFiles(String planId, List<Map> list, String level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backFillResFile(Integer businessId, Integer resFileId, String fileKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearBackfillResFile(Integer businessId, Integer resFileId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int filePathCount(String filePath) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBusinessId(Integer materialFileId, Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

    @Override
    public String readFile(Integer resId) {
        if(Objects.isNull(resId) || resId == 0){
            return "";
        }
        ResFile byId = resFileMapper.findById(resId.longValue());
        StringBuilder result = new StringBuilder();
        if (Objects.isNull(byId)){
            throw new RuntimeException("获取资源文件失败,id:" + resId);
        }
        try {
            List<String> strings = Files.readAllLines(Paths.get(rootPath, byId.getFilePath()));
            result.append(Joiner.on("").join(strings));
        } catch (IOException e) {
            logger.error("read file error, file path:{}", rootPath + byId.getFilePath());
            e.printStackTrace();
        }
        return result.toString();
    }
}
