package com.nork.design.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import com.nork.design.model.*;
import com.nork.product.model.BaseProductStyle;
import com.nork.system.model.SysDictionary;
import net.sf.json.JSONObject;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.controller.web.IntelligenceDecorationController.PosNameInfo;
import com.nork.design.exception.DesignPlanException;
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
	public int add(DesignPlan designPlan);

	/**
	 *    更新数据
	 * @param designPlan
	 * @return  int
	 */
	public int update(DesignPlan designPlan);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int
	 */
	public ResponseEnvelope delete(int taskBusinessId,DesignPlan designPlan);

	/**
	 *    根据设计方案id，获取designPlan表中的所有数据详情
	 *
	 * @param id
	 * @return  DesignPlan
	 */
	public DesignPlan get(Integer id);

	/**
	 * 所有数据
	 *
	 * @param  designPlan
	 * @return   List<DesignPlan>
	 */
	public List<DesignPlan> getList(DesignPlan designPlan);

	/**
	 *    获取数据数量
	 *
	 * @param  designPlan
	 * @return   int
	 */
	public int getCount(DesignPlanSearch designPlanSearch);
	/**
	 *    获取样板房产品ID
	 *
	 * @param  designPlan
	 * @return   int
	 */
	public int getTemplateProductId(Map map);

	/**
	 *    分页获取数据
	 *
	 * @param  designPlan
	 * @return   List<DesignPlan>
	 */
	public List<DesignPlan> getPaginatedList(
				DesignPlanSearch designPlantSearch);

	/**
	 * 其他
	 *
	 */
	public List<DesignPlanResult> getDesignList(DesignPlan designPlan);
	/**
	 *    前台设计方案
	 *
	 * @param  designPlan
	 * @return   List<DesignPlan>
	 */
	public List<DesignPlan> getPlanList(DesignPlan designPlan);

	public List<DesignPlan> getDesignsList(DesignPlan designPlan);

	public List<DesignPlan> getPlanListByAreas(DesignPlan designPlan);

	public int getPlanCount(DesignPlan designPlan);

	public List<Map> getUserMaxPlan(Map map);

	public boolean updateDesignPlan(DesignPlan desPlan,Integer designPlanId,String planProductIds,
			String materialPicId, LoginUser loginUser, String splitTexturesChoose) throws InvocationTargetException, IllegalAccessException ;

	List<UnityPlanProduct> batchSaveDesignPlan(DesignPlan desPlan,Integer designPlanId, String groupProductJson,Integer groupId,
			Integer planProductId, String materialPicId,String context,String planGroupId,LoginUser loginUser, Integer groupType,Integer isStandard,
			String center,String regionMark,Integer styleId,String measureCode) throws InvocationTargetException, IllegalAccessException ;

	public boolean delPlan(DesignPlan designPlan);

	//public void getPlanDesignPicJob() throws Exception;
	public void getPlanDesignPicJobByTask() throws Exception;
	public RoomExt getRoomExt(Integer designPlanId);

	public ItemExt getItemExt(String posIndexPath,String posName,String itemCode,DesignPlan designPlan,HttpServletRequest request);

	public Root getRoomByConfig(String jsonConfig);

	public boolean InitDesignPlanProduct(String jsonConfig);

	public boolean UpdateDesignPlanProduct(String jsonConfig);

	/**
	 * 填充设计方案配置文件
	 * @param planId
	 * @param context
	 * @return
	 */
	public boolean updateDesignPlanConfig(Integer planId,String context,HttpServletRequest request);

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

	public boolean updatePlanProductByConfig(String context,Integer planId,boolean isModifySceneTime);
	public boolean updatePlanProductByConfig(String context,Integer planId, String contextType);

	public Object startRender(Integer planId,String rootDirName,RenderConfig renderConfig,String type);



	/**
	 * 通过plan_id获得设计方案状态
	 * @param context
	 * @return
	 */
	public int getDesignPlanState(Integer designPlanId);

	/**
	 * 清理暂停时间超过配置中允许最大暂停时间的任务
	 */
	public void cleanSuspendTask();

	/**
	 * 清理发送渲染指令后，等待时长超出最大等待时间的任务资源文件，并把任务状态置为异常结束
	 */
	public void cleanExecutingTask();

	/**
	 * 清理异常状态的任务
	 */
	public void cleanExceptionTask();

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

	public Object renderTask() throws Exception;

	public JSONObject assembleRenderConfig(SysTask sysTask,RenderConfig renderConfig);

	List<DesigPlanWithResult> selectDesignList(DesignPlan designPlan) throws Exception;

	//app修改设计方案查询设计方案所需信息
	DesignPlanModel selectDesignPlanInfo(Integer id);

	/*处理结构返回json格式*/
	UnityPlanProduct getPlanProductStructureJson(UnityPlanProduct unityPlanProduct,DesignPlanProduct planProduct,DesignPlanModel designPlan, LoginUser loginUser);

	/*获取装修导航信息*/
	List<UnityPlanProduct> getDecorationNavigationInfo(List<UnityPlanProduct> unityPlanProductList, List<UnityPlanProduct> newUnityPlanProductList, TreeSet<String> productTypeCodeSet, Map<String, UnityPlanProduct> unityPlanProductMap_p);

	public Integer planCopyFileFromResDesignScene(String resId,String fileKey,String defaultPath, Integer bussniess,String code,LoginUser loginUser);
	/**
	 * 从样板房中拷贝数据到设计方案中
	 * @param mediaType
	 * @param designPlan
	 * @param designTemplet
	 * @param loginUser
	 * @param request
	 * @return
	 */
	public Integer CopyDesignTemplet(String mediaType, DesignPlan designPlan,
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
	public Boolean CopyDesignTempletProduct(Integer id,
			DesignTemplet designTemplet,
			HttpServletRequest request);

	/**
	 * 从数据库中读取最大值的数据,进入
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public DesignPlan getMaxList(Map map);

	/**
	 * @return
	 * @param designPlan
	 * @param params
	 *
	* @Description: 保存渲染任务参数（新渲染系统）
	* @return void    返回类型
	* @throws
	 */
	public String saveRenderParams(String params, DesignPlan designPlan);
	/**
	 *
	* @Description: 组装渲染需要参数的路径
	* @param planId
	* @param businessCode
	* @return
	* @return Object    返回类型
	* @throws
	 */
	public List<String> getRenderParamsFilePath(Integer planId, SysTask priorityTask);

	public Map<String, String> saveRenderPic(HttpServletRequest request) throws Exception;
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
	public Object getDesignPlanInfo(Integer designPlanId,Integer newFlag,String houseId,String livingId,String residentialUnitsName, Boolean isRelease, LoginUser loginUser, String mediaType, String needOrNoCache);

	/**
	 * 更新进入设计方案缓存数据
	 * @param designPlanId
	 * @param newFlag
	 * @param houseId
	 * @param livingId
	 * @param residentialUnitsName
	 * @param request
	 */
	public void updateDesignPlanCache(Integer designPlanId,Integer newFlag,String houseId,String livingId,String residentialUnitsName,HttpServletRequest request);


	/**
	 * 获取推荐方案资源信息
	 * @author chenqiang
	 * @param designPlanRecommendeIds
	 * @return
	 * @date 2018/10/31 0031 16:42
	 */
	Object getDesignRecommendedInfo(String designPlanRecommendeIds,LoginUser loginUser,String msgId);

	public JSONObject shelvesValidate(DesignPlan designPlan,LoginUser loginUser);

	/**
	 * 复制设计方案
	 * @param designPlan
	 * @param sysTask
	 * @param request
	 * @return
	 */
	public boolean copyPlan_new(DesignPlan designPlan, SysTask sysTask, HttpServletRequest request);

	/**
	 *
	* @Description: 获取设计方案图片（有渲染效果图有优先取渲染图，无则取俯瞰图）
	* @param desPlan
	* @return
	* @return DesignPlan    返回类型
	* @throws
	 */
	public DesignPlan getMyDesignPic(DesignPlan desPlan);

	public DesignPlan getMyDesignDetailPic(DesignPlan designPlan);
	public DesignPlanRenderScene getMyDesignSceneDetailPic(DesignPlanRenderScene planRenderScene);

	/**
	 * 根据草图方案 获取方案渲染信息
	 * @author chenqiang
	 * @param designPlan 草图方案对象
	 * @return
	 * @date 2018/8/13 0013 10:50
	 */
	DesignToRenderPicInfo getDesignToRenderPicInfo(DesignPlan designPlan);

	/**
	 * 根据空间id获取风格集合
	 * @author chenqiang
	 * @param spaceCommonId 空间id
	 * @return
	 * @date 2018/8/13 0013 10:49
	 */
	List<BaseProductStyle> getBaseProductStyleList(Integer spaceCommonId);


	public String getU3dModelId(String mediaType,DesignTemplet designTemplet);

	public Integer saveRenderPicOf720(DesignPlan designPlan, Map<String, MultipartFile> fileMap, Integer viewPoint, Integer scene, Integer isTurnOn, Integer renderingType,LoginUser loginUser,Integer taskId, Integer panoLevel,String roamJson,Integer sourcePlanId, Integer templateId, String cameraLocation, Integer needShear);

	public DesignPlan createDesignPlan(DesignPlan designPlan,DesignPlanRecommended designPlanRecommended,
			DesignTemplet designTemplet,String mediaType,LoginUser loginUser, Integer isDeleted, Integer opType);

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
	public Integer saveRenderPicOfPhoto(DesignPlan designPlan, Map<String, MultipartFile> fileMap, Integer viewPoint,
			Integer scene, Integer isTurnOn, Integer renderingType,String level, LoginUser loginUser,Integer taskId,Integer sourcePlanId, Integer templateId);

	/**
	 * 发送渲染成功/失败消息
	 * @param task
	 * @param state
	 * @param picId
	 * @return
	 * @throws Exception
	 */
	public Integer sendRenderMessage(SysTask task,Integer state,Integer picId) throws Exception;

	/**
	 *
	 * @param loginUser
	 * @param designPlan 查询条件
	 * @param total 记录数
	 * @return List<DesignPlan>
	 */
	List<DesignPlan> getMyDesignPlanList( LoginUser loginUser, DesignPlan designPlan, int total);
	int getTotalFromMyDesignPlan(LoginUser loginUser, DesignPlan designPlan);

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
	public Integer saveRenderPicOfVideo(DesignPlan designPlan,
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
	public void deleteAllData(long planId);

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
    public DesignPlan getTempDesignPalnId(int designPlanRenderSceneId,int userId);

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
	public Integer createPlanByConfig(
			Integer designTempletId, Integer recommendedPlanId,
			String configEncrypt, String mediaType,
			LoginUser loginUser, List<PosNameInfo> posNameInfoList,
			Integer opType);

	/**
	 * describe: 更新方案配置文件
	 * creat_user: xiaoxc
	 * creat_date: 2017-08-23
	 **/
	public ResponseEnvelope<DesignPlan> updatePlanConfig(LoginUser loginUser,Integer planId,Integer opType,String context,String bDirtyConfig,String msgId);

	/**
	 * describe: 方案新增产品
	 * creat_user: xiaoxc
	 * creat_date: 2017-08-23
	 **/
	public ResponseEnvelope<DesignPlan> addDesignPlanProduct(Integer designPlanId,String posIndexPath,String posName,Integer productId,String context,Integer bindProductId,LoginUser loginUser,String msgId);

	/**
	 * describe: 方案更新单品
	 * creat_user: xiaoxc
	 * creat_date: 2017-08-23
	 **/
	public ResponseEnvelope<DesignPlan> unityUpdate(DesignPlan designPlan,Integer designPlanId,String planProductIds,String materialPicId, String splitTexturesChoose, LoginUser loginUser,String msgId);

	/**
	 * describe: 方案替换/新增组合
	 * creat_user: xiaoxc
	 * creat_date: 2017-08-23
	 **/
	public ResponseEnvelope unityGroupProduct(Integer designPlanId,String groupProductJson,Integer groupId,Integer planProductId,
											  String materialPicId,String context,String planGroupId,LoginUser loginUser,  Integer isStandard,
											  String center,String regionMark,Integer styleId,String measureCode,String msgId);

	/**
	 * describe: 获取初始白膜类型
	 * creat_user: xiaoxc
	 * creat_date: 2017-09-05
	 **/
	public UnityDesignPlan wrapperData(Integer designPlanId, UnityDesignPlan unityDesignPlan);


	/**
	 * 将720漫游的位置关系保存到文件中
	 * @param context
	 * @return
	 */
	public Integer saveRenderRoamConfig(LoginUser loginUser, String context, Integer renderRoamId);

	/**
	 * 保存设计方案拼花信息
	 * @param msgId
	 * @param planId
	 * @param spellingFlower
	 * @param loginUser
	 * @return
	 */
	public Object spellingFlower(SpellingFlowerModel model);

	/**
	 * 方案推荐改造
	 * @param model
	 * @return Map<String,String>
	 */
	public Map<String,String> transformAndCopyPlan(TransformAndCopyPlanModel model);


	 /*<!-- COMMON-525 -->*/
	/**
	 * 查询该品牌所经营的大小分类所过滤出的设计方案中产品中不是该品牌的产品编码
	 * @param brandIds 品牌s
	 * @param designPlanId 设计方案id
	 * @return
	 */
	Set<String> getProductCodeLogic(String brandIds, Integer designPlanId);

	/**
	 * 更新设计方案配置文件
	 * 
	 * @author huangsongbo
	 * @param context
	 * @param designPlan
	 */
	void updateContext(String context, DesignPlan designPlan);

	/**
	 * 根据json信息保存新增的产品(批量),并保存配置文件
	 * 
	 * @author huangsongbo
	 * @param newProductsJson
	 * @param designPlan
	 * @param loginUser
	 * @param context
	 * @return
	 * @throws DesignPlanException 
	 */
	public List<AddDesignPlanProductsResultVO> addDesignPlanProducts(
			String newProductsJson, 
			String context,
			DesignPlan designPlan,
			LoginUser loginUser
			) throws DesignPlanException;

	/**
	 * 根据json信息保存新增的产品(批量)
	 * 
	 * @author huangsongbo
	 * @param newProductsJson
	 * @param designPlan
	 * @param loginUser
	 * @return
	 * @throws DesignPlanException
	 */
	List<AddDesignPlanProductsResultVO> saveProductByJson(
			String newProductsJson, 
			DesignPlan designPlan,
			LoginUser loginUser
			) throws DesignPlanException;

	/**
	 * 更新水刀配置文件
	 * 
	 * @author huangsongbo
	 * @param designPlanId
	 * @param json
	 * @return
	 */
	public boolean updateWaterjetInfo(Integer designPlanId, String json);


	/**
	 * 根据方案产品Id获取设计方案产品信息
	 * @param planProductId
	 * @param type
	 */
	DesignPlanProduct getDesignProduct(Integer planProductId, String type);

	/**
	 * 根据方案Id获取设计方案信息
	 * @param designPlanId
	 * @param type
	 */
	DesignPlan getDesignPlan(Integer designPlanId, String type);
}
