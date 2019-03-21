package com.sandu.resource.service;

import java.util.List;

import com.sandu.resource.model.vo.ResPicVo;

/***
 * 图片资源服务接口
 * @author Administrator
 *
 */
public interface ResPicService {
   
   /***
    * 获取企业的推荐方案(最多取4条记录)
    * @param companyId
    * @return
    */
   List<ResPicVo> getCompanyRecommendPlan(long companyId);
   
   /***
    * 查找门店的形象图片(做多取4条记录)
    * @param shopId
    * @return
    */
   List<ResPicVo> getStoreFigure(long shopId);

   /**
    * 通过Ids获取资源地址
    * @param ids
    * @return
    */
   List<String> getPicPathByIds(String ids);


   /**
    * add by WangHaiLin
    * 通过id获取图片信息
    * @param id 图片Id
    * @return ResPicVo
    */
   ResPicVo getPicById(Long id);
}
