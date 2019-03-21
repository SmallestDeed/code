package com.nork.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nork.sandu.model.dto.TDesignSketch;
import com.nork.sync.model.ResEntity;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.search.ResPicSearch;

/**   
 * @Title: ResPicService.java 
 * @Package com.nork.system.service
 * @Description:系统-图片资源库Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 16:06:59
 * @version V1.0   
 */
public interface ResPicService {
	/**
	 * 新增数据
	 *
	 * @param resPic
	 * @return  int 
	 */
	public int add(ResPic resPic);

	/**
	 *    更新数据
	 *
	 * @param resPic
	 * @return  int 
	 */
	public int update(ResPic resPic);
	

	/**
	 *    更新数据
	 *
	 * @param resPic
	 * @return  int 
	 */
	public int update(ResPic resPic,Integer businessId,String picKey,String sysCode);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResPic 
	 */
	public ResPic get(Integer id);
	
    public TDesignSketch findById(Integer id);
    
    public int findCount(ResPicSearch resPicSearch);
    
    public List<TDesignSketch> findList(ResPicSearch resPicSearch);

	/**
	 * 所有数据
	 * 
	 * @param  resPic
	 * @return   List<ResPic>
	 */
	public List<ResPic> getList(ResPic resPic);

	/**
	 *    获取数据数量
	 *
	 * @param  resPic
	 * @return   int
	 */
	public int getCount(ResPicSearch resPicSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  resPic
	 * @return   List<ResPic>
	 */
	public List<ResPic> getPaginatedList(
				ResPicSearch resPictSearch);

	/**
	 * 其他
	 * 
	 */


	/**
	 * 将图片信息记录到数据库中
	 */
	public Integer saveFiles(String planId,List<Map> list,String level,String renderingType);
	
	
	
	/**
	 * 将渲染图片信息记录到数据库中
	 */
	//public Integer saveResDesign(String planId,List<Map> list,String level,String renderingType);
	
	
	/**
	 * 回填图片资源
	 * @param businessId 业务表ID
	 * @param rePicId 图片资源表ID
	 */
	public void backFillResPic(Integer businessId,Integer rePicId,String picKey,String sysCode);
	/**
	 * 删除当前资源信息
	 * @param businessId 业务Id
	 * @param picId  图片资源Id 
	 */
	public void clearBackfillResPic(Integer businessId,Integer picId);
	
	/**生成缩略图*/
	public Map<String,ResPic> createThumbnail(ResPic resPic,HttpServletRequest request) throws IOException;
	
	public void updateSmallPicBusinessId(ResPic resPic);
	
	/**查询该数据是否有共享一个文件  **/
	public int picPathCount(String picPath);

	/**
	 * 更新图片code
	 * @param picId 图片id
	 * @param code 变更后的code
	 */
	public void updateCode(Integer picId, String code);

	/**
	 * 更新图片的path(应对于例如样板房附件路径中间加code的这种情况)
	 * @param picId 需更新的图片id
	 * @return 已修改->true;保持原样或者修改失败->false
	 */
	public boolean updatePath(Integer picId,String code);
	
	/**
	 * 属性新增复制图片数据
	 * @param pidId 待复制的pidId的id
	 * @return 新生成的pid的id(生成失败返回0)
	 */
	public Integer copyPic(Integer pidId, HttpServletRequest request);
	
	/**
	 * 获取文件数据为ResEntity对象
	 * @param id
	 * @return
	 */
	ResEntity selectResEntity(Integer id);
	
	/**
	 * 保存ResEntity对象
	 * @param resEntity
	 * @return
	 */
	public int insertEntity(ResEntity resEntity);
	
	 int selectCountGuide(ResPicSearch resPicSearch);
	    
		List<ResPic> selectPaginatedListGuide(
				ResPicSearch resPicSearch);

		/**
		 * 保存照片级渲染图片
		 * add by yanghz
		 * @param planId
		 * @param list
		 * @param level
		 * @param renderingType
		 */
	public Integer savePlanRenderPicOfPhot(Integer planId, List<Map> list, String level, Integer renderingType,Integer sourcePlanId, Integer templateId);
	
	/**
	 * 一次性取出要查询的所以Id条件
	 * add by jiangwei
	 * @param list
	 */
	public List<ResPic> getBatchGet(List<Integer> list);
	/**
	 * 批量查询
	 * @param resPic
	 * @return
	 */
	public List<ResPic> getPicList(List<String> list);
}
