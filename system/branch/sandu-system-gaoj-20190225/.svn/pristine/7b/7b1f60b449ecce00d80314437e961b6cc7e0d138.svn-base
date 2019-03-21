package com.sandu.service.file.impl;

import com.sandu.api.company.common.FileModel;
import com.sandu.api.company.common.FileUtils;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.file.model.ResFile;
import com.sandu.api.file.service.ResFileService;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.commons.util.StringUtils;
import com.sandu.service.file.dao.ResFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * @Desc 资源文件 基础 业务层
 * @Auth xiaoxc
 * @Date 2018-06-20
 * @Modified By
 */
@Slf4j
@Service("resFileService")
public class ResFileServiceImpl implements ResFileService {

    @Autowired
    private ResFileMapper resFileMapper;
    @Value("${upload.base.path}")
    private String rootPath;

    @Override
    public int add(ResFile record) {
        int result = resFileMapper.insertSelective(record);
        if (result >= 0) {
            return record.getId();
        }
        return 0;
    }

    @Override
    public int delete(Long id) {
        return resFileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(ResFile record) {
        return resFileMapper.updateByPrimaryKey(record);
    }

    @Override
    public ResFile getById(Long id) {
        return resFileMapper.findById(id);
    }

    @Override
    public Integer saveFile(String uploadPath, String content, LoginUser loginUser, Integer businessId, String fileKey, String fileType) {
        if (StringUtils.isEmpty(uploadPath) || StringUtils.isEmpty(content)) {
            return null;
        }
        //补全路径
        String filePath = StringUtil.replaceDate(rootPath + uploadPath, null);
        StringBuilder builder = new StringBuilder();
        builder.append(filePath);
        builder.append("/");
        builder.append(Utils.generateRandomDigitString(6));
        builder.append("_");
        builder.append(System.currentTimeMillis());
        builder.append("_1");
        builder.append(".txt");
        filePath = builder.toString();
        boolean falg = Utils.writeTxtFile(filePath, content);
        Integer resId = 0;
        if (falg) {
            //获取上传文件信息
            File file = new File(filePath);
            Map<String, String> map = FileUtils.getMap(file, fileKey,true);
            resId = this.saveResFileDate(map, businessId, loginUser, fileType);
            if (resId == null || resId == 0) {
                return null;
            }
        } else {
            return null;
        }
        return resId;
    }

    /**
     * 保存配置文件数据
     * @param map
     * @param businessId
     * @param loginUser
     * @param mes
     * @return
     */
    private Integer saveResFileDate(Map map, Integer businessId, LoginUser loginUser,String mes) {
        Integer id = null;
        if (map != null && map.size() > 0) {
            ResFile resFile = new ResFile();
            //设置数据
            String dbFilePath = map.get(FileUtils.DB_FILE_PATH).toString();
            String fileName = map.get(FileModel.FILE_NAME).toString();
            resFile.setFileName(fileName);//文件名
            resFile.setFileOriginalName(fileName);//原文件名
            resFile.setFileSize(map.get(FileModel.FILE_SIZE).toString());
            resFile.setFileSuffix(map.get(FileModel.FILE_SUFFIX).toString());
            resFile.setBusinessId(businessId);//该资源所关联的表id
            resFile.setFilePath(dbFilePath);
            resFile.setFileKey(map.get(FileModel.FILE_KEY).toString());
            resFile.setFileLevel("0");
            resFile.setFileType(mes);
            this.saveSystemInfo(resFile, loginUser);
            //保存
            id = this.add(resFile);
        }
        return id;
    }

    @Override
    public boolean updateFile(Long resId, String content) {
        boolean falg = false;
        ResFile resFile = this.getById(resId);
        if (resFile != null && StringUtils.isNotEmpty(resFile.getFilePath())) {
            falg = Utils.replaceFile(rootPath+resFile.getFilePath(), content);
        }
        return falg;
    }

    /**
     * 自动存储系统字段
     * @param file 文件
     * @param loginUser 当前登录用户
     */
    @SuppressWarnings("all")
    public void saveSystemInfo(ResFile file, LoginUser loginUser) {
        if(file != null){
            //新增
            if(file.getId() == null){
                file.setGmtCreate(new Date());
                file.setCreator(loginUser.getLoginName()==null?"nologin":loginUser.getLoginName());
                file.setIsDeleted(0);
                if(file.getSysCode()==null || "".equals(file.getSysCode())){
                    file.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                    file.setFileCode(file.getSysCode());
                }
            }
            //修改
            file.setGmtModified(new Date());
            file.setModifier(loginUser.getLoginName()==null?"nologin":loginUser.getLoginName());
        }
    }
}
