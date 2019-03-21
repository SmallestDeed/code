package com.sandu.api.storage.service;

import com.sandu.api.storage.model.ResPic;

import java.util.List;
import java.util.Map;

/**
 * create by bvvy
 */
public interface ResPicService {

    /**
     * 添加新图片信息
     *
     * @param resPic input
     * @return id
     */
    Long addResPic(ResPic resPic);

    /**
     * 生成缩略图
     *
     * @param originImage 原图对象
     * @param module      模块名
     * @param scene       缩略图应用场景： ipad,web
     * @return long  缩略图ID
     */
    long generateThumbnailImage(ResPic originImage, String module, String scene);

    /**
     * 更新主图的smallInfo字段
     *
     * @param masterImageId 原图ID
     * @param webImageId    web缩略图ID
     * @param ipadImageId   ipad缩略图ID
     * @return int 更新记录数
     */
    int updateSmallPicInfoField(long masterImageId, long webImageId, long ipadImageId,
                                long iosImageId, long androidImageId, long normalId);

    /**
     * 获取图片信息
     *
     * @param resPicId id
     * @return respic
     */
    ResPic getResPicDetail(Long resPicId);

    List<ResPic> getResPicDetailByIds(List<Long> resPicId);

    /**
     * 保存图片信息
     *
     * @param resPic 输入
     * @return id
     */
    int saveResPic(ResPic resPic);

    /**
     * 列出图片信息
     *
     * @param resPic query
     * @return id
     */
    List<ResPic> listResPic(ResPic resPic);

    /**
     * 删除图片信息
     *
     * @param resPicId id
     * @return id
     */
    int deleteResPic(Long resPicId);


    String getPathById(Integer id);

    Map<Integer, String> idAndPathMap(List<Integer> picIds);

    /**
     * 根据图片路径更新图片路径
     *
     * @param resPic
     * @param sourceThumbPic
     */
    void updatePicByPicPath(ResPic resPic, String sourceThumbPic);
}
