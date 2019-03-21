package com.sandu.system.service;

import com.sandu.system.model.ResRenderPic;

public interface ResRenderPicService {

    /**
     * 新增数据
     *
     * @param resRenderPic
     * @return  int
     */
    int add(ResRenderPic resRenderPic);

    /**
     *    更新数据
     *
     * @param resRenderPic
     * @return  int
     */
    int update(ResRenderPic resRenderPic);

    /**
     *    删除数据
     *
     * @param id
     * @return  int
     */
    int delete(Integer id);

    /**
     *    获取数据详情
     *
     * @param id
     * @return  ResRenderPic
     */
    ResRenderPic get(Integer id);

}
