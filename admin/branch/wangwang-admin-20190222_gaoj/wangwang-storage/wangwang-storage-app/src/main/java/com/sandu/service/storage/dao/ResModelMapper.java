package com.sandu.service.storage.dao;

import com.sandu.api.storage.model.ResModel;

import java.util.List;

public interface ResModelMapper {

    /**
     * 通过主键删除
     *
     * @param id id
     * @return 是否成功 1  0
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 选择插入
     *
     * @param record record
     * @return 是否成功 1  0
     */
    int insertSelective(ResModel record);

    /**
     * 通过主键获取记录
     *
     * @param id id
     * @return 记录
     */
    ResModel selectByPrimaryKey(Long id);

    /**
     * 选择更新
     *
     * @param record record
     * @return 是否成功 1  0
     */
    int updateByPrimaryKeySelective(ResModel record);

    /**
     * 分页查询
     *
     * @return 记录
     */
    List<ResModel> selectResModel();

    ResModel getFirstNoneTransModel();
}