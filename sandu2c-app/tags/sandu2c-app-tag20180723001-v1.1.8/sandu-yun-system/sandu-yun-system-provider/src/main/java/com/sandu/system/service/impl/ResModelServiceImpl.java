package com.sandu.system.service.impl;

import com.sandu.system.dao.ResModelMapper;
import com.sandu.system.model.ResModel;
import com.sandu.system.model.search.ResModelSearch;
import com.sandu.system.service.ResModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @Title: ResModelServiceImpl.java
 * @Package com.sandu.system.service.impl
 * @Description:系统-模型资源库ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 16:05:22
 */
@Service("resModelService")
public class ResModelServiceImpl implements ResModelService {

    private static Logger logger = LoggerFactory.getLogger(ResModelServiceImpl.class);

    @Autowired
    private ResModelMapper resModelMapper;

    /**
     * 新增数据
     *
     * @param resModel
     * @return int
     */
    @Override
    public int add(ResModel resModel) {
        resModelMapper.insertSelective(resModel);
        return resModel.getId();
    }

    /**
     * 更新数据
     *
     * @param resModel
     * @return int
     */
    @Override
    public int update(ResModel resModel) {
        return resModelMapper
                .updateByPrimaryKeySelective(resModel);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return resModelMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return ResModel
     */
    @Override
    public ResModel get(Integer id) {
        return resModelMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param resModel
     * @return List<ResModel>
     */
    @Override
    public List<ResModel> getList(ResModel resModel) {
        return resModelMapper.selectList(resModel);
    }

    /**
     * 获取数据数量
     *
     * @return int
     */
    @Override
    public int getCount(ResModelSearch resModelSearch) {
        return resModelMapper.selectCount(resModelSearch);
    }


    /**
     * 分页获取数据
     *
     * @return List<ResModel>
     */
    @Override
    public List<ResModel> getPaginatedList(
            ResModelSearch resModelSearch) {
        return resModelMapper.selectPaginatedList(resModelSearch);
    }
}
