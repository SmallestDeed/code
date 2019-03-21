package com.nork.design.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import com.nork.design.model.*;
import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.search.DesignPlanSearch;
import com.nork.design.model.unity.ItemExt;
import com.nork.design.model.unity.RoomExt;
import com.nork.design.model.unity.Root;
import com.nork.task.model.SysTask;

/**   
 * @Title: DesignPlanService.java 
 * @Package com.nork.design.service
 * @Description:设计模块-设计方案Service
 * @createAuthor pandajun 
 * @CreateDate 2015-07-03 17:09:51
 * @version V1.0   
 */
public interface DesignPlanService {
	
	/**
	 * 新增数据
	 *
	 * @param designPlan
	 * @return  int 
	 */
	int add(DesignPlan designPlan);

	/**
	 *    更新数据
	 * @param designPlan
	 * @return  int 
	 */
	int update(DesignPlan designPlan);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	ResponseEnvelope delete(int taskBusinessId, DesignPlan designPlan);

	/**
	 *    根据设计方案id，获取designPlan表中的所有数据详情
	 *
	 * @param id
	 * @return  DesignPlan 
	 */
	DesignPlan get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  designPlan
	 * @return   List<DesignPlan>
	 */
	List<DesignPlan> getList(DesignPlan designPlan);

	/**
	 *    获取数据数量
	 *
	 * @param  designPlan
	 * @return   int
	 */
	int getCount(DesignPlanSearch designPlanSearch);
	/**
	 *    获取样板房产品ID
	 *
	 * @param  designPlan
	 * @return   int
	 */
	int getTemplateProductId(Map map);

	/**
	 *    分页获取数据
	 *
	 * @param  designPlan
	 * @return   List<DesignPlan>
	 */
	List<DesignPlan> getPaginatedList(
			DesignPlanSearch designPlantSearch);

	/**
	 * 其他
	 * 
	 */
	List<DesignPlanResult> getDesignList(DesignPlan designPlan);
	/**
	 *    前台设计方案
	 *
	 * @param  designPlan
	 * @return   List<DesignPlan>
	 */
	List<DesignPlan> getPlanList(DesignPlan designPlan);
	
	List<DesignPlan> getDesignsList(DesignPlan designPlan);
	
	List<DesignPlan> getPlanListByAreas(DesignPlan designPlan);
	
	int getPlanCount(DesignPlan designPlan);
	
	List<Map> getUserMaxPlan(Map map);
	
	boolean updateDesignPlan(DesignPlan desPlan, Integer designPlanId, Integer planProductId,
							 Integer productId, String materialPicId, String context, HttpServletRequest request, String splitTexturesChoose) throws InvocationTargetException, IllegalAccessException ;
	
	List<UnityPlanProduct> batchSaveDesignPlan(DesignPlan desPlan, Integer designPlanId, String groupProductJson, Integer groupId,
											   Integer planProductId, String materialPicId, String context, String planGroupId, LoginUser loginUser, Integer groupType, Integer isStandard,
											   String center, String regionMark, Integer styleId, String measureCode) throws InvocationTargetException, IllegalAccessException ;
	
	boolean delPlan(DesignPlan designPlan);
	
	RoomExt getRoomExt(Integer designPlanId);

	ItemExt getItemExt(String posIndexPath, String posName, String itemCode, DesignPlan designPlan, HttpServletRequest request);
	
	Root getRoomByConfig(String jsonConfig);
	
	boolean InitDesignPlanProduct(String jsonConfig);
	
	boolean UpdateDesignPlanProduct(String jsonConfig);

	/**
	 * 填充设计方案配置文件
	 * @param planId
	 * @param context
	 * @return
	 */
	boolean updateDesignPlanConfig(Integer planId, String context, HttpServletRequest request);

	/**
	 * 
	   
	 * updatePlanProductByConfig 方法描述：      
	   
	 * @param context
	 * @param planId
	 * @param isModifySceneTime 只有updatePlanConfig 接口进来可能为true，其余的直接传false。目的是为了让比较效果图方案和原方案是否发生变化
	 * @return
	
	 * @return boolean    返回类型   
	   
	 * @Exception 异常对象    
	   
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */

	boolean updatePlanProductByConfig(String context, Integer planId, boolean isModifySceneTime);
	boolean updatePlanProductByConfig(String context, Integer planId, String contextType);

	Object startRender(Integer planId, String rootDirName, RenderConfig renderConfig, String type);

	
	
	/**
	 * 通过plan_id获得设计方案状态
	 * @param context
	 * @return
	 */
	int getDesignPlanState(Integer designPlanId);

	/**
	 * 清理暂停时间超过配置中允许最大暂停时间的任务
	 */
	void cleanSuspendTask();

	/**
	 * 清理发送渲染指令后，等待时长超出最大等待时间的任务资源文件，并把任务状态置为异常结束
	 */
	void cleanExecutingTask();

	/**
	 * 清理异常状态的任务
	 */
	void cleanExceptionTask();

	/**
	 * 清理系统暂停次数大于等于5的任务
	 */
	void cleanSuspendBySystemTask();

	/**
	 * 修改任务状态并删除资源文件
	 * @param sysTask 任务对象
	 * @param taskState 要设置的状态
	 * @param deleted 是否删除资源文件
	 */
	void updateTaskAndDeleteResources(SysTask sysTask,Integer taskState,Boolean deleted);

	/**
	 * 把任务移动到历史表中
	 */
	void removeTaskToHistory(SysTask sysTask);

	Object renderTask() throws Exception;
	
	JSONObject assembleRenderConfig(SysTask sysTask, RenderConfig renderConfig);
	
	List<DesigPlanWithResult> selectDesignList(DesignPlan designPlan) throws Exception;

	//app修改设计方案查询设计方案所需信息
	DesignPlanModel selectDesignPlanInfo(Integer id);

	/*处理结构返回json格式*/
	UnityPlanProduct getPlanProductStructureJson(UnityPlanProduct unityPlanProduct,DesignPlanProduct planProduct,DesignPlanModel designPlan, LoginUser loginUser);

	/*获取装修导航信息*/
	List<UnityPlanProduct> getDecorationNavigationInfo(List<UnityPlanProduct> unityPlanProductList, List<UnityPlanProduct> newUnityPlanProductList, TreeSet<String> productTypeCodeSet, Map<String, UnityPlanProduct> unityPlanProductMap_p);


	/**
	 * 从样板房中拷贝数据到设计方案中
	 * @param mediaType
	 * @param designPlan
	 * @param designTemplet
	 * @param loginUser
	 * @param request
	 * @return
	 */
	Integer CopyDesignTemplet(String mediaType, DesignPlan designPlan,
							  DesignTemplet designTemplet, LoginUser loginUser,
							  HttpServletRequest request);

	/**
	 * 拷贝一份 样板房的产品到  到设计方案产品表中
	 * @param id
	 * @param designTemplet
	 * @param cacheEnable
	 * @param request
	 * @return
	 */
	Boolean CopyDesignTempletProduct(Integer id,
									 DesignTemplet designTemplet,
									 HttpServletRequest request);

	/**
	 * 从数据库中读取最大值的数据,进入
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	DesignPlan getMaxList(Map map);

	/**
	 * @return 
	 * @param designPlan 
	 * @param params 
	 * 
	* @Description: 保存渲染任务参数（新渲染系统）      
	* @return void    返回类型 
	* @throws
	 */
	String saveRenderParams(String params, DesignPlan designPlan);
	/**
	 * 
	* @Description: 组装渲染需要参数的路径
	* @param planId
	* @param businessCode
	* @return     
	* @return Object    返回类型 
	* @throws
	 */
	List<String> getRenderParamsFilePath(Integer planId, SysTask priorityTask);

	Map<String, String> saveRenderPic(HttpServletRequest request) throws Exception;
	/**
	 * 获取进入设计方案信息
	 * @param designPlanId
	 * @param newFlag
	 * @param houseId
	 * @param livingId
	 * @param residentialUnitsName
	 * @param nO_CACHE 
	 * @param request
	 * @return Object
	 */
	Object getDesignPlanInfo(Integer designPlanId, Integer newFlag, String houseId, String livingId, String residentialUnitsName, Boolean isRelease, LoginUser loginUser, String mediaType, String needOrNoCache);

	/**
	 * 更新进入设计方案缓存数据
	 * @param designPlanId
	 * @param newFlag
	 * @param houseId
	 * @param livingId
	 * @param residentialUnitsName
	 * @param request
	 */
	void updateDesignPlanCache(Integer designPlanId, Integer newFlag, String houseId, String livingId, String residentialUnitsName, HttpServletRequest request);

	JSONObject shelvesValidate(DesignPlan designPlan, LoginUser loginUser);

	/**
	 * 复制设计方案
	 * @param designPlan
	 * @param sysTask
	 * @param request
	 * @return
	 */
	boolean copyPlan_new(DesignPlan designPlan, SysTask sysTask, HttpServletRequest request);

	/**
	 * 
	* @Description: 获取设计方案图片（有渲染效果图有优先取渲染图，无则取俯瞰图）
	* @param desPlan
	* @return     
	* @return DesignPlan    返回类型 
	* @throws
	 */
	DesignPlan getMyDesignPic(DesignPlan desPlan);
	
	DesignPlan getMyDesignDetailPic(DesignPlan designPlan);
	DesignPlanRenderScene getMyDesignSceneDetailPic(DesignPlanRenderScene planRenderScene);
 


	String getU3dModelId(String mediaType, DesignTemplet designTemplet);

	Integer saveRenderPicOf720(DesignPlan designPlan, Map<String, MultipartFile> fileMap, Integer viewPoint, Integer scene, Integer isTurnOn, Integer renderingType, LoginUser loginUser, Integer taskId, Integer panoLevel, String roamJson, Integer sourcePlanId, Integer templateId);

	DesignPlan createDesignPlan(DesignPlan designPlan, DesignPlanRecommended designPlanRecommended,
								DesignTemplet designTemplet, String mediaType, LoginUser loginUser, Integer isDeleted, Integer opType);
	
	List<ProductDTO> getProductDTOList(Integer designPlanId);

	/**
	 * 保存照片级渲染图
	 * @param designPlan
	 * @param fileMap
	 * @param viewPoint
	 * @param scene
	 * @param isTurnOn
	 * @param renderingType
	 * @param loginUser
	 * @return
	 */
	Integer saveRenderPicOfPhoto(DesignPlan designPlan, Map<String, MultipartFile> fileMap, Integer viewPoint,
								 Integer scene, Integer isTurnOn, Integer renderingType, String level, LoginUser loginUser, Integer taskId, Integer sourcePlanId, Integer templateId);
	
	/**
	 * 发送渲染成功/失败消息
	 * @param task
	 * @param state
	 * @param picId
	 * @return
	 * @throws Exception 
	 */
	Integer sendRenderMessage(SysTask task, Integer state, Integer picId) throws Exception;
	
	/**
	 * 
	 * @param loginUser
	 * @param designPlan 查询条件
	 * @param total 记录数
	 * @return List<DesignPlan>
	 */
	List<DesignPlan> getMyDesignPlanList( LoginUser loginUser, DesignPlan designPlan, int total, String mediaType);
	int getTotalFromMyDesignPlan(LoginUser loginUser, DesignPlan designPlan, String mediaType);

	/**
	 * 保存720渲染视频   add by yangzhun
	 * @param designPlan
	 * @param fileMap
	 * @param viewPoint
	 * @param scene
	 * @param isTurnOn
	 * @param renderingType
	 * @param loginUser
	 * @param taskId
	 * @return
	 */
	Integer saveRenderPicOfVideo(DesignPlan designPlan,
								 Map<String, MultipartFile> fileMap, Integer renderingType,
								 LoginUser loginUser, Integer taskId, Integer sourcePlanId, Integer templateId);

	/**
	 * 根据id获取方案场景是否改变字段信息
	 * add by yanghz
	 * @param designPlanId
	 * @return
	 */
    DesignPlan getSceneModifiedById(int designPlanId);

	void delete(Integer id);

	/**
	 * 删除设计方案,并且关联删除其相关数据
	 * @param planId
	 */
	void deleteAllData(long planId);

	/**
	 * describe: 获取创建时间超过10分钟的临时方案
	 * creat_user: yanghz
	 * creat_date: 2017-07-25
	 * creat_time: 下午 05:23
	 **/
	List<DesignPlan> getTempDesignPaln4RenderBakSceneTask();

	/**
	 * describe: 批量删除方案
	 * creat_user: yanghz
	 * creat_date: 2017-07-25
	 * creat_time: 下午 06:19
	 **/
	void batchDelTempDesign(List<Integer> delPlanIdList);
	
	/**
	 * 
	   
	 * existTempDesignPaln(查看用户名下是否有存在该该副本的，为转正的临时设计方案)        
	   
	 * @param designPlanRenderSceneId
	 * @param userId
	 * @return 
	
	 * @return DesignPlan    返回类型   
	   
	 * @Exception 异常对象    
	   
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	DesignPlan getTempDesignPalnId(int designPlanRenderSceneId, int userId);

    /**
     * 根据配置文件新建设计方案,返回设计方案id
     * 
     * @author huangsongbo
     * @param designTempletId
     * @param recommendedPlanId
     * @param configEncrypt
     * @param loginUser 
     * @param mediaType 
     * @param posNameInfoList 
     * @return
     */
	Integer createPlanByConfig(
			Integer designTempletId, Integer recommendedPlanId,
			String configEncrypt, String mediaType,
			LoginUser loginUser, List<PosNameInfo> posNameInfoList,
			Integer opType);
 
	/**
	 * describe: 更新方案配置文件
	 * creat_user: xiaoxc
	 * creat_date: 2017-08-23
	 **/
	ResponseEnvelope<DesignPlan> updatePlanConfig(LoginUser loginUser, Integer planId, Integer opType, String context, String bDirtyConfig, String msgId);

	/**
	 * describe: 方案新增产品
	 * creat_user: xiaoxc
	 * creat_date: 2017-08-23
	 **/
	ResponseEnvelope<DesignPlan> addDesignPlanProduct(Integer designPlanId, String posIndexPath, String posName, Integer productId, String context, Integer bindProductId, LoginUser loginUser, String msgId);

	/**
	 * describe: 方案更新单品
	 * creat_user: xiaoxc
	 * creat_date: 2017-08-23
	 **/
	ResponseEnvelope<DesignPlan> unityUpdate(DesignPlan designPlan, Integer designPlanId, Integer planProductId, Integer productId, String materialPicId, String context, String splitTexturesChoose, HttpServletRequest request, String msgId);

	/**
	 * describe: 方案替换/新增组合
	 * creat_user: xiaoxc
	 * creat_date: 2017-08-23
	 **/
	ResponseEnvelope unityGroupProduct(Integer designPlanId, String groupProductJson, Integer groupId, Integer planProductId,
									   String materialPicId, String context, String planGroupId, LoginUser loginUser, Integer isStandard,
									   String center, String regionMark, Integer styleId, String measureCode, String msgId);

	/**
	 * describe: 获取初始白膜类型
	 * creat_user: xiaoxc
	 * creat_date: 2017-09-05
	 **/
	UnityDesignPlan wrapperData(Integer designPlanId, UnityDesignPlan unityDesignPlan);
	
	
	/**
	 * 将720漫游的位置关系保存到文件中
	 * @param context
	 * @return
	 */
	Integer saveRenderRoamConfig(LoginUser loginUser, String context, Integer renderRoamId);

	/**
	 * 保存设计方案拼花信息
	 * @param msgId
	 * @param planId
	 * @param spellingFlower
	 * @param loginUser
	 * @return
	 */
	Object spellingFlower(SpellingFlowerModel model);

	/**
	 * 方案推荐改造
	 * @param model
	 * @return Map<String,String>
	 */
	Map<String,String> transformAndCopyPlan(TransformAndCopyPlanModel model);


	 /*<!-- COMMON-525 -->*/
	/**
	 * 查询该品牌所经营的大小分类所过滤出的设计方案中产品中不是该品牌的产品编码
	 * @param brandIds 品牌s
	 * @param designPlanId 设计方案id
	 * @return
	 */
	Set<String> getProductCodeLogic(String brandIds, Integer designPlanId);

}
