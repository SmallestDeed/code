package com.sandu.service.storage.dao;


import com.sandu.api.storage.model.ResPic;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ResPicMapper {

    /**
     * 通过主键删除
     *
     * @param id id
     * @return 是否成功 1  0
     */
    @Transactional
    int deleteByPrimaryKey(Long id);

    /**
     * 选择插入
     *
     * @param record record
     * @return 是否成功 1  0
     */
    @Transactional
    int insertSelective(ResPic record);

    /**
     * 通过主键获取记录
     *
     * @param id id
     * @return 记录
     */
    ResPic selectByPrimaryKey(Long id);

    /**
     * 选择更新
     *
     * @param record record
     * @return 是否成功 1  0
     */
    @Transactional
    int updateByPrimaryKeySelective(ResPic record);

    /**
     * 分页查询
     *
     * @return 记录
     */
    List<ResPic> selectResPic();

    List<ResPic> listByIds(@Param("picIds") List<Integer> picIds);

    ResPic getByPicPath(String sourceThumbPic);
}