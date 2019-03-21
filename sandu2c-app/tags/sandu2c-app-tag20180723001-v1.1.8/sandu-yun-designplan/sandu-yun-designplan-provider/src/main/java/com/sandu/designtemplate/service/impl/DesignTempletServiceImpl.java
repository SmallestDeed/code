package com.sandu.designtemplate.service.impl;

import com.sandu.design.model.DesignTemplet;
import com.sandu.design.model.search.DesignTempletSearch;
import com.sandu.designtemplate.dao.DesignTempletMapper;
import com.sandu.designtemplate.service.DesignTempletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @Title: DesignTempletServiceImpl.java
 * @Package com.sandu.design.service.impl
 * @Description:设计模块-设计方案样板房表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-07-05 14:47:35
 */
@Service("designTempletService")
public class DesignTempletServiceImpl implements DesignTempletService {

    @Autowired
    private DesignTempletMapper designTempletMapper;

    /**
     * 新增数据
     *
     * @param designTemplet
     * @return int
     */
    @Override
    public int add(DesignTemplet designTemplet) {
        designTempletMapper.insertSelective(designTemplet);
        return designTemplet.getId();
    }

    /**
     * 更新数据
     *
     * @param designTemplet
     * @return int
     */
    @Override
    public int update(DesignTemplet designTemplet) {
        return designTempletMapper.updateByPrimaryKeySelective(designTemplet);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return designTempletMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return DesignTemplet
     */
    @Override
    public DesignTemplet get(Integer id) {
        return designTempletMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DesignTemplet> getPaginatedList(DesignTempletSearch designTempletSearch) {
        List<DesignTemplet> designTemplets = designTempletMapper.selectPaginatedList(designTempletSearch);
        return designTemplets;
    }

    /**
     * 获取数据数量
     *
     * @return int
     */
    @Override
    public int getCount(DesignTempletSearch designTempletSearch) {
        return designTempletMapper.selectCount(designTempletSearch);
    }
}
