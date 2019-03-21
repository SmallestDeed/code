package com.sandu.system.dao;

import com.sandu.design.model.DesignRenderRoam;
import com.sandu.design.model.ResRenderPicQO;
import com.sandu.design.model.ThumbData;
import com.sandu.designplan.model.ResRenderPic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: ResRenderPicMapper.java
 * @Package com.sandu.system.dao
 * @Description:渲染图片资源库-渲染图片资源库Mapper
 * @createAuthor pandajun
 * @CreateDate 2017-03-22 14:39:08
 */
@Repository
public interface ResRenderPicMapper {

    List<ResRenderPic> getResRenderPicByPlanRecommended(@Param("planRecommendedId") Integer planRecommendedId, @Param("renderingType") Integer renderingType);

    List<ResRenderPic> selectList(ResRenderPic resRenderPic);

    ResRenderPic selectByPrimaryKey(Integer id);
    
    ResRenderPic get720SingleCode(ResRenderPic resRenderPic);
    
    DesignRenderRoam get720RomanCode(ResRenderPic resRenderPic);

    /**
     * 通过pid获取缩略图
     * @param pid
     * @return
     */
    ResRenderPic selectOneByPid(Integer pid);

    /**
     * 从自动渲染里面取720全景效果图
     * @param resRenderPic
     * @return
     */
    List<ResRenderPic> selectListOfMobile(ResRenderPic resRenderPic);

    /**
     * 从自动渲染里面通过pid取缩略图
     * @param pid
     * @return
     */
    ResRenderPic selectOneByPidOfMobile(Integer pid);

    /**
     * 查询渲染任务完成后的数据--为提高性能，减少字段
     * @param resRenderPic  渲染对象
     * @return
     */
    List<ResRenderPic> queryPicListByResRenderPic(ResRenderPic resRenderPic);

    /**
     * countRenderPicByPage分页查询出自己名下的缩略图 (计数)
     * @param thumbData
     * @return int    返回类型
     * @Exception 异常对象
     * @since  CodingExample　Ver(编码范例查看) 1.1
     * todo
     */
    int countRenderPicByPage(ThumbData thumbData);

    /**
     * 分页查询出自己名下的缩略图
     * getrenderPicByPage(这里用一句话描述这个方法的作用)
     * @param thumbData
     * @return List<ThumbData>    返回类型
     * @Exception 异常对象
     * @since  CodingExample　Ver(编码范例查看) 1.1
     * todo
     */
    List<ThumbData> getRenderPicByPage(ThumbData thumbData);

	List<ResRenderPic> selectListByFileKeys(ResRenderPicQO resRenderPicQO);

    List<ResRenderPic> selectRenderPicList(Integer planRecommendedId);

    List<ResRenderPic> selectRenderPicListByRecommendIds(@Param("planRecommendedIds")List<Integer> planRecommendedIds);

    List<ResRenderPic> listByIds(@Param("picIds") List<Integer> picIds);
}
