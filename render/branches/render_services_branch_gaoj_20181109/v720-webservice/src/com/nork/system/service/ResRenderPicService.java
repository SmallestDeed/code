package com.nork.system.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.design.model.DesignRenderRoam;
import com.nork.design.model.ResRenderPicQO;
import com.nork.design.model.ThumbData;
import com.nork.render.model.ResRenderData;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.search.ResRenderPicSearch;
import com.nork.system.model.small.ResScreenPicData;

/**   
 * @Title: ResRenderPicService.java 
 * @Package com.nork.system.service
 * @Description:渲染图片资源库-渲染图片资源库Service
 * @createAuthor pandajun 
 * @CreateDate 2017-03-22 14:39:08
 * @version V1.0   
 */
public interface ResRenderPicService {
	public int countRoamByFileKey(ResRenderPicSearch resRenderPicSearch);
	/**
	 * 新增数据
	 *
	 * @param resRenderPic
	 * @return  int 
	 */
	public int add(ResRenderPic resRenderPic);
	
	
	/**
	 * 新增数据
	 *
	 * @param resRenderPic
	 * @return  int 
	 */
	public int addNew(ResRenderPic resRenderPic);

	/**
	 *    更新数据
	 *
	 * @param resRenderPic
	 * @return  int 
	 */
	public int update(ResRenderPic resRenderPic);

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
	 * @return  ResRenderPic 
	 */
	public ResRenderPic get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  resRenderPic
	 * @return   List<ResRenderPic>
	 */
	public List<ResRenderPic> getList(ResRenderPic resRenderPic);

	/**
	 *    获取数据数量
	 *
	 * @param  resRenderPic
	 * @return   int
	 */
	public int getCount(ResRenderPicSearch resRenderPicSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  resRenderPic
	 * @return   List<ResRenderPic>
	 */
	public List<ResRenderPic> getPaginatedList(
				ResRenderPicSearch resRenderPictSearch);

	/**
	 * 其他
	 * 
	 */
	
	/**
     * 根据推荐方案Id和渲染类型获得最新渲染原图
     */
	public ResRenderPic getResRenderPicByPlanRecommended(Integer planRecommendedId,Integer renderingType);
	
	
	/**
	 *查询所有的缩略图和封面图 
	 */
	public List<ResRenderPic> selectListByFileKeys(ResRenderPicQO resRenderPicQO);

	/**
	 *
	 * getLatestSmallPic通过设计方案id获得最新的一条渲染缩略图id

	 * @param renderPic
	 * @return int    返回类型
	 * @Exception 异常对象
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
    public int getLatestSmallPic(ResRenderPic renderPic);

	
	public List<ResRenderPic> getRenderResourceListByopInfo(Integer sourcePlanId, Integer templateId, Integer renderType);

	/**
	 * 通过pid获取缩略图
	 * @param pid
	 * @return
	 */
	ResRenderPic selectOneByPid(Integer pid);

	/**
	 * 删除副本关联的渲染图
	 * @param sceneId 设计方案副本id
	 */
	public void deletePicBySceneId(Integer sceneId);

	/**
	 * describe: 更新原图所有原图根据缩略图id
	 * creat_user: yanghz
	 * creat_date: 2017-07-22
	 * creat_time: 上午 01:25
	 *
	 * @param resId
	 * @param resId*/
	void updateOrigPicBySmallPicId(ResRenderPic render);

	/**
	 * describe: 根据所缩略图id获取截图id
	 * creat_user: yanghz
	 * creat_date: 2017-07-22
	 * creat_time: 上午 01:55
	 **/
	public int getTaskIdBySmallId(int smallId);

	/**
	 * describe: 根据截图id更新场景id
	 * creat_user: yanghz
	 * creat_date: 2017-07-22
	 * creat_time: 上午 01:57
	 **/
	public void updateSceneIdByTaskId(int sceneId, int taskId);

	/**
	 * describe: 获取图片浏览地址信息
	 * 
	 * update by huangsongbo 2018.8.13
	 * add param: dateDue 截至日期
	 * 暂定的需求是:超过截至日期的二维码,跳转到其他页面
	 * 
	 * creat_user: yanghz
	 * creat_date: 2017-07-27
	 * creat_time: 下午 02:00
	 **/
	String getQRCodeInfo(ResRenderPic resPic, Date dateDue);

	/**
	* ...
	* update by huangsongbo 2018.8.13
	* add param: dateDue 截至日期
	* 暂定的需求是:超过截至日期的二维码,跳转到其他页面
	* 
	* @param resPic
	* @param dateDue
	* @return
	*/
	String getQRpicPath(ResRenderPic resPic, Date dateDue);

	/**
	 * 通过推荐id 渲染图
	 * @param planRecommendedId
	 */
	public void deletedByPlanRecommendedId(int planRecommendedId);
	
	/**
	 * describe: 根据场景副本id查询该方案下所有缩略图
	 * creat_user: yanghz
	 * creat_date: 2017-08-01
	 * creat_time: 上午 11:33
	 * @param sceneId 场景id
	 **/
	public List<ThumbData> getRenderPicIdsBySceneId(long sceneId);
	
	
	public int insertSelective(ResRenderPic resRenderPic);
	
	 /**
     * 从自动渲染里面取720全景效果图
     * @param resRenderPic
     * @return
     */
    List<ResRenderPic> selectListOfMobile(ResRenderPic resRenderPic);
    public ResRenderPic selectOneByPidOfMobile(Integer pid);
    
    /**
     * 多点二维码图片http://192.168.1.232:6080/app/pages/vr720/vr720Roam.htm?code=70ae74da695c40389a083f13cb8b1839
       
     * get720RomanCode.得到720多点分享的code    
       
     * @param ResRenderPic。传入 截图id 和创建人create_user_id
     * @return 
    
     * @return DesignRenderRoam    返回类型    取uuid
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
	public DesignRenderRoam get720RomanCode(ResRenderPic resRenderPic);
	
	/**
     * 单点二维码图片。http://192.168.2.13:8080/app/pages/vr720/vr720Single.htm?code=20170712200703077_646765
       
     * get720SingleCode 得到720单点分享需要的code        
       
     * @param resRenderPic 传入 原图id和create_user_id
     * @return 
    
     * @return ResRenderPic    返回类型    取 sysCode
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public ResRenderPic get720SingleCode(ResRenderPic resRenderPic);
    
    /**
     * 根据 联盟方案素材发布ID 查找该方案内的720单点渲染图列表
     * @param releaseId
     * @return
     */
    public List<ResRenderPic> getListByUnionDesignPlanStoreReleaseId(Integer releaseId);

	/**
	 * 根据图片类型和方案副本获取图片
	 * @param picType
	 * @param taskId
	 * @return
	 */
	ResRenderPic getBytaskIdAndPicType(String picType,Integer taskId);

	enum renderResEnum {
		designPlanRenderScene, designPlanRecommended
	}

	Map<String,List<ResRenderData>> getAllResRenderPic(Integer designPlanRecommendedId, renderResEnum type);

}
