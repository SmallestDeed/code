package com.sandu.service.storage.impl;

import com.google.common.base.Joiner;
import com.sandu.api.company.common.FileModel;
import com.sandu.api.company.common.FileUtils;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.model.Company;
import com.sandu.api.storage.model.ResFile;
import com.sandu.api.storage.service.ResFileService;
import com.sandu.common.util.file.FilePathUtil;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.commons.util.StringUtils;
import com.sandu.constant.Punctuation;
import com.sandu.service.storage.dao.ResFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.sandu.constant.Punctuation.DOT;
import static com.sandu.util.Commoner.isEmpty;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service("resFileServiceImpl")
public class ResFileServiceImpl implements ResFileService {
    @Value("${upload.base.path}")
    private String rootPath;

    @Autowired
    private ResFileMapper resFileMapper;

    private static final String DESING_DESC_FILE_PATH_PRE = "/design/descFile";
    private static final String DESING_DESC_FILE_PATH_SUFFIX = "html";
    private static final String DESING_DESC_FILE_KEY = "design.descFile";


    @Override
    public int add(ResFile record) {
        Integer result = resFileMapper.insert(record);
        return record.getId();
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
    public Long updateFile(Integer resId, String content) {
        ResFile resFile;
        if (Objects.isNull(resId) || resId == 0) {
            resFile = new ResFile();
            LocalDateTime now = LocalDateTime.now();
            String path = FilePathUtil.formatPath(DESING_DESC_FILE_PATH_PRE, FilePathUtil.datePath(now));
            String fileName = FilePathUtil.filename(now) + DOT + DESING_DESC_FILE_PATH_SUFFIX;
            resFile.setFilePath(Punctuation.SLASH + path + fileName);
            resFile.setFileName("designDesc");//文件名
            resFile.setFileOriginalName(fileName);//原文件名
            resFile.setFileSize("0");
            resFile.setFileSuffix(DESING_DESC_FILE_PATH_SUFFIX);
            resFile.setFileKey(DESING_DESC_FILE_KEY);
            resFile.setFileLevel("0");
            resFile.setFileType("无");
            resFile.setGmtCreate(new Date());
            resFile.setGmtModified(new Date());
            resFile.setCreator("test");
            resFile.setModifier("test");
            resFileMapper.insert(resFile);
        } else {
            resFile = resFileMapper.findById(resId.longValue());
            if (Objects.isNull(resFile)) {
                throw new RuntimeException("获取文件失败");
            }
        }
        StringBuilder sb = new StringBuilder();
        log.info("rootPath:{}",rootPath);
        Path path = Paths.get(sb.append(rootPath).append(resFile.getFilePath()).substring(0, sb.lastIndexOf(Punctuation.SLASH)));
        Path finalPath = Paths.get(sb.toString());
        log.info("finalPath:{}",sb.toString());
        try {
        	log.info("Files.notExists:{}",Files.notExists(path));
            if (Files.notExists(path)) {
                Files.createDirectories(path);
            }
            Files.write(finalPath, content.getBytes(), TRUNCATE_EXISTING, CREATE);
            resFile.setGmtModified(new Date());
            resFile.setFileSize(String.valueOf(Files.size(finalPath)));
            resFileMapper.updateByPrimaryKey(resFile);
        } catch (IOException e) {
            log.error("write file error, file path:{}", path.toString());
            e.printStackTrace();
        }
        return resFile.getId().longValue();
    }

    @Override
    public String readFile(Integer resId) {
        if(Objects.isNull(resId) || resId == 0){
            return "";
        }
        ResFile byId = resFileMapper.findById(resId.longValue());
        StringBuilder result = new StringBuilder();
        if (Objects.isNull(byId))
            throw new RuntimeException("获取资源文件失败,id:" + resId);
        try {
            List<String> strings = Files.readAllLines(Paths.get(rootPath, byId.getFilePath()));
            result.append(Joiner.on("").join(strings));
        } catch (IOException e) {
            log.error("read file error, file path:{}", rootPath + byId.getFilePath());
            e.printStackTrace();
        }
        return result.toString();
    }

	@Override
	public Map<Integer, String> idAndUrlMap(List<Integer> contractIds) {
		contractIds = contractIds.stream().filter(Objects::nonNull).distinct().collect(toList());
        if (isEmpty(contractIds)) {
            return Collections.emptyMap();
        }
        List<ResFile> fileLists = resFileMapper.listByIds(contractIds);
        return fileLists.stream().collect(toMap(ResFile::getId,ResFile::getFilePath));	
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

    @Override
    public boolean updateFileFromSystem(Long resId, String content) {
        boolean falg = false;
        ResFile resFile = this.getById(resId);
        if (resFile != null && StringUtils.isNotEmpty(resFile.getFilePath())) {
            falg = Utils.replaceFile(rootPath+resFile.getFilePath(), content);
        }
        return falg;
    }
}