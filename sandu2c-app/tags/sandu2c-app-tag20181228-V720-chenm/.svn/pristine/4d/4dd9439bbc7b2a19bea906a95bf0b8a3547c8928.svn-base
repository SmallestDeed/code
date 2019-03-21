package com.sandu.system.service.impl;

import com.sandu.common.file.FileVO;
import com.sandu.common.file.util.file.FilePathUtil;
import com.sandu.common.util.Utils;
import com.sandu.system.dao.ResHousePicMapper;
import com.sandu.system.model.ResHousePic;
import com.sandu.system.service.ResHousePicService;
import com.sandu.user.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.sandu.constant.Punctuation.SLASH;
import static org.apache.commons.io.FilenameUtils.getBaseName;

/**
 * @version V1.0
 * @Title: ResHousePicServiceImpl.java
 * @Package com.sandu.system.service.impl
 * @Description:系统模块-户型、空间图片资源表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2016-08-13 16:34:09
 */
@Service("resHousePicService")
public class ResHousePicServiceImpl implements ResHousePicService {
    private static Logger logger = LoggerFactory.getLogger(ResPicServiceImpl.class);
    @Autowired
    private ResHousePicMapper resHousePicMapper;
    private static final String FILE_KEY = "home.baseHouse.pic";

    /**
     * 新增数据
     *
     * @param resHousePic
     * @return int
     */
    @Override
    public int add(ResHousePic resHousePic) {
        resHousePicMapper.insertSelective(resHousePic);
        return resHousePic.getId();
    }

    /**
     * 更新数据
     *
     * @param resHousePic
     * @return int
     */
    @Override
    public int update(ResHousePic resHousePic) {
        return resHousePicMapper.updateByPrimaryKeySelective(resHousePic);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return resHousePicMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return ResHousePic
     */
    @Override
    public ResHousePic get(Integer id) {
        return resHousePicMapper.selectByPrimaryKey(id);
    }

    /**
     * 新的上传户型图片保存的方法
     * @param fileVO
     * @param type
     * @param absolutePath
     * @param loginUser
     * @param storagePath
     * @return
     */
    @Override
    public Integer addNew(FileVO fileVO, String type, String absolutePath, LoginUser loginUser, String storagePath) {
        if ("image".equals(type)) {
            ResHousePic resHousePic = new ResHousePic();
            resHousePic.setCreator(loginUser.getName());
            resHousePic.setModifier(loginUser.getName());
            resHousePic.setIsDeleted(0);
            resHousePic.setGmtModified(new Date());
            resHousePic.setGmtCreate(new Date());
            resHousePic.setPicHigh(fileVO.getHeight()+"");
            resHousePic.setPicWeight(fileVO.getWidth()+"");
            resHousePic.setPicName(getBaseName(absolutePath));
            resHousePic.setPicPath(SLASH + FilePathUtil.relativePath(storagePath,absolutePath));
            resHousePic.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
            resHousePic.setPicType(FilePathUtil.getExtension(absolutePath));
            resHousePic.setPicSize(fileVO.getSize().intValue());
            resHousePic.setPicSuffix(FilePathUtil.getExtension(absolutePath));
            resHousePic.setPicFormat(FilePathUtil.getExtension(absolutePath));
            resHousePic.setFileKey(FILE_KEY);
            resHousePicMapper.insertSelective(resHousePic);
            return resHousePic.getId();
        }
        return 0;
    }


}
