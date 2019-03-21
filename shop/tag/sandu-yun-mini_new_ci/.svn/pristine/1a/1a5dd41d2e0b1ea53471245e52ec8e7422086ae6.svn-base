package com.sandu.resource.dao;

import java.util.HashMap;
import java.util.List;

import com.sandu.company.util.MappingHelper;
import com.sandu.resource.model.query.ResPicQuery;
import com.sandu.resource.model.vo.ResPicVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

public interface ResPicDao {
   /***
    * 查找企业的推荐方案(最多取4条记录)
    * @param query
    * @return
    */
   List<ResPicVo> findCompanyPlan(ResPicQuery query);
   
   /***
    * 查找门店的形象图片(做多取4条记录)
    * @param query
    * @return
    */
   List<ResPicVo> findStoreFigure(ResPicQuery query);

   /***
    * 通过Ids获取资源地址
    * @param idList
    * @return
    */
   List<String> findPicPathByIds(@Param("idList") List<Integer> idList);

   @MapKey("id")
   List<MappingHelper<Long, String>> findPicPathMapByIds(@Param("idList") List<Integer> idList);



   /**
    * add by WangHaiLin
    *通过Id获取图片资源信息
    * @param id 图片id
    * @return ResPicVo
    */
   ResPicVo findPicById(@Param("id") Long id);
}
