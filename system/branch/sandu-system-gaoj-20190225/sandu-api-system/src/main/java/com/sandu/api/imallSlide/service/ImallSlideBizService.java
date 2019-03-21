package com.sandu.api.imallSlide.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.imallSlide.model.ImallSlide;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/28 17:40
 */
public interface ImallSlideBizService {
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
    int delete(int id);

    /**
     * 查询
     * @param type
     * @return
     */
    List<ImallSlide> getList(int type);
}
