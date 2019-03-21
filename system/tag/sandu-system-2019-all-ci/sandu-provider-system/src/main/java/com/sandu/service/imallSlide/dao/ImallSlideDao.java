package com.sandu.service.imallSlide.dao;

import com.sandu.api.imallSlide.model.ImallSlide;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author djc
 * @datetime 2018/6/23 18:41
 */
@Repository
public interface ImallSlideDao {

    /**
     * 插入
     *
     * @param model
     * @return
     */
    int insert(ImallSlide model);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteImallSlide(@Param("id") int id);

    /**
     * 查询
     * @param type
     * @return
     */
    List<ImallSlide> getList(@Param("type") Integer type);


    ImallSlide getByFileName(@Param("fileName") String fileName);
}
