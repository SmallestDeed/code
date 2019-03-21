package com.sandu.system.service.impl;


import com.nork.common.model.LoginUser;
import com.sandu.common.file.FileVO;
import com.sandu.common.file.util.file.FilePathUtil;
import com.sandu.common.util.Utils;
import com.sandu.supplydemand.model.SupplyDemandPic;
import com.sandu.system.dao.ResPicMapper;
import com.sandu.system.model.ResPic;
import com.sandu.system.model.search.ResPicSearch;
import com.sandu.system.service.ResPicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.sandu.constant.Punctuation.SLASH;
import static org.apache.commons.io.FilenameUtils.getBaseName;


/**
 * @version V1.0
 * @Title: ResPicServiceImpl.java
 * @Package com.sandu.system.service.impl
 * @Description:系统-图片资源库ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 16:06:59
 */
@Service("resPicService")
public class ResPicServiceImpl implements ResPicService {

    private static Logger logger = LoggerFactory.getLogger(ResPicServiceImpl.class);
    @Autowired
    private ResPicMapper resPicMapper;

    /**
     * 新增数据
     *
     * @param resPic
     * @return int
     */
    @Override
    public int add(ResPic resPic) {
        resPicMapper.insertSelective(resPic);
        return resPic.getId();
    }

    /**
     * 更新数据
     *
     * @param resPic
     * @return int
     */
    @Override
    public int update(ResPic resPic) {
        //获取多个 resPic filekeys ,busniessIds

        return resPicMapper
                .updateByPrimaryKeySelective(resPic);
    }


    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return resPicMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return ResPic
     */
    @Override
    public ResPic get(Integer id) {
        return resPicMapper.selectByPrimaryKey(id);
    }


    /**
     * 所有数据
     *
     * @param resPic
     * @return List<ResPic>
     */
    @Override
    public List<ResPic> getList(ResPic resPic) {
        return resPicMapper.selectList(resPic);
    }

    /**
     * 获取数据数量
     *
     * @return int
     */
    @Override
    public int getCount(ResPicSearch resPicSearch) {
        return resPicMapper.selectCount(resPicSearch);
    }


    /**
     * 分页获取数据
     *
     * @return List<ResPic>
     */
    @Override
    public List<ResPic> getPaginatedList(
            ResPicSearch resPicSearch) {
        return resPicMapper.selectPaginatedList(resPicSearch);
    }

    /**
     * 获取多条数据
     *
     * @return List<ResPic>
     */
    @Override
    public List<ResPic> getResPicByIds(List<Integer> ids)
    {
        if (ids == null || ids.size() == 0)
        {
            return null;
        }
        return resPicMapper.getResPicByIds(ids);
    }

    @Override
    public Long saveUserResPic(FileVO fileVO, String type, String absolutePath, LoginUser loginUser, String storagePath,String module) {
        if("image".equals(type)){
            ResPic resPic = new ResPic();
            resPic.setFileKey(module);
            resPic.setPicSize(fileVO.getSize().intValue());
            resPic.setPicPath(SLASH +FilePathUtil.relativePath(storagePath,absolutePath));
            resPic.setPicWeight(String.valueOf(fileVO.getWidth()));
            resPic.setPicHigh(String.valueOf(fileVO.getHeight()));
            resPic.setGmtCreate(new Date());
            resPic.setPicFormat(FilePathUtil.getExtension(absolutePath));
            resPic.setPicName(getBaseName(absolutePath));
            resPic.setIsDeleted(0);
            resPic.setCreator(loginUser.getName());
            resPicMapper.insertSelective(resPic);
            return  resPic.getId().longValue();
        }
        return 0L;
    }
}
