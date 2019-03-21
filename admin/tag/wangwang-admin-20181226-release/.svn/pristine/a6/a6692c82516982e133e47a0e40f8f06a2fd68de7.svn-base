package com.sandu.service.solution.dao;

import com.sandu.api.solution.model.ResRenderPic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源图片mapper
 *
 * @author bvvy
 */
@Repository
public interface ResRenderPicMapper {
    /**
     * delete
     *
     * @param id id
     * @return 1, 0
     */
    int deleteByPrimaryKey(Long id);


    /**
     * insert
     *
     * @param record record
     * @return 1 0
     */
    int insertSelective(ResRenderPic record);

    /**
     * get one
     *
     * @param id id
     * @return one
     */
    ResRenderPic selectByPrimaryKey(Long id);

    /**
     * update
     *
     * @param record record
     * @return 1 , 0
     */
    int updateByPrimaryKeySelective(ResRenderPic record);

    /**
     * delete logic
     *
     * @param id id
     * @return 1, 0
     */
    int deleteLogicByPrimaryKey(@Param("id") Long id);

    /**
     * list
     *
     * @param resRenderPic query
     * @return list
     */
    List<ResRenderPic> selectListSelective(ResRenderPic resRenderPic);

    /**
     * 通过id集获取效果图
     *
     * @param picIds ids
     * @return list
     */
    List<ResRenderPic> listByIds(@Param("picIds") List<Integer> picIds);

    /**
     * 获取方案效果图
     *
     * @param designPlanId 方案
     * @return list
     */
    List<ResRenderPic> listByPlan(@Param("planId") String designPlanId);

    /**
     * 批量添加
     * @param renderPics render pic
     */
    void batchAddResRenderPic(@Param("renderPics") List<ResRenderPic> renderPics,@Param("planId") Long planId);
    
	List<ResRenderPic> selectResRenderPicByParam(@Param("planRecommendedId")String plandId,@Param("renderingType")String renderingType,@Param("fileKey")String fileKey,@Param("picType")String picType);

    List<ResRenderPic> listPicForFixPath(@Param("limit") int limit);
}