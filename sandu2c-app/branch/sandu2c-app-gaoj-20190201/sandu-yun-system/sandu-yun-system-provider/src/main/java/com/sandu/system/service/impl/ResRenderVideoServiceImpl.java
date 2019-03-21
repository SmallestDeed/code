package com.sandu.system.service.impl;

import com.sandu.system.dao.ResRenderVideoMapper;
import com.sandu.system.model.ResRenderVideo;
import com.sandu.system.service.ResRenderVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @Title: ResRenderVideoServiceImpl.java
 * @Package com.sandu.system.service.impl
 * @Description:渲染视频资源库-渲染视频资源表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2017-07-07 19:03:29
 */
@Service("resRenderVideoService")
public class ResRenderVideoServiceImpl implements ResRenderVideoService {

    @Autowired
    private ResRenderVideoMapper resRenderVideoMapper;

    /**
     * 新增数据
     *
     * @param resRenderVideo
     * @return int
     */
    @Override
    public int add(ResRenderVideo resRenderVideo) {
        resRenderVideoMapper.insertSelective(resRenderVideo);
        return resRenderVideo.getId();
    }

    /**
     * 更新数据
     *
     * @param resRenderVideo
     * @return int
     */
    @Override
    public int update(ResRenderVideo resRenderVideo) {
        return resRenderVideoMapper
                .updateByPrimaryKeySelective(resRenderVideo);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return resRenderVideoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return ResRenderVideo
     */
    @Override
    public ResRenderVideo get(Integer id) {
        return resRenderVideoMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param resRenderVideo
     * @return List<ResRenderVideo>
     */
    @Override
    public List<ResRenderVideo> getList(ResRenderVideo resRenderVideo) {
        return resRenderVideoMapper.selectList(resRenderVideo);
    }


    /**
     * 根据封面图id（渲染任务id）查到视频资源
     */
    @Override
    public ResRenderVideo selectBySysTaskPicId(Integer sysTaskPicId) {
        return resRenderVideoMapper.selectBySysTaskPicId(sysTaskPicId);
    }
}
