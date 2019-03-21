package com.nork.design.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanModel;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRes;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.model.ProductCostDetail;
import com.nork.design.model.ProductDTO;
import com.nork.design.model.ProductsCost;
import com.nork.design.model.ProductsCostType;
import com.nork.design.model.UnityDesignPlan;
import com.nork.design.model.UnityPlanProduct;
import com.nork.design.model.UsedProducts;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;

import net.sf.json.JSONObject;
/**
 * 一键装修流程优化分表
 * @author Xiaozunp
 *
 */
public interface OptimizePlanService {
	int countOnekeyPlan(DesignPlan designPlan);
	
	public DesignPlan createDesignPlan(DesignPlan designPlan,DesignPlanRecommended designPlanRecommended,DesignTemplet designTemplet,
			   String mediaType,LoginUser loginUser);
	
	public boolean saveResDesign(DesignPlan designPlan, Map map);
	
	public JSONObject analysisJson(Integer designTempletId,Integer planId, DesignPlan designPlan, String context, LoginUser loginUser) throws Exception ;
	
	
	public DesignPlan getDesignPlanByRecomendPlanIdAndTPId(Integer recommendedPlanId, Integer templateId);
	
	public ResDesign get(Integer id) ;
	
	public int add(DesignPlan designPlan) ;
	
	
	public int add(ResRenderVideo resRenderVideo);
	
	public int update(ResRenderVideo resRenderVideo);
	
	public int add(ResDesign resDesign);
	/**
	 *    根据设计方案id，获取designPlan表中的所有数据详情
	 *
	 * @param id
	 * @return  DesignPlan 
	 */
	public DesignPlan getPlan(Integer id);
	
	/**
	 *    更新数据
	 * @param designPlan
	 * @return  int 
	 */
	public int update(DesignPlan designPlan);
	
	public ResponseEnvelope<UnityDesignPlan> getUnityDesignPlan(Integer recommendedPlanId ,Integer designTempletId, Integer isParent, Integer isAllReplace,String msgId, LoginUser loginUser,String mediaType,Integer opType) throws IllegalAccessException, InvocationTargetException;
	
	public void add(List<DesignPlanProduct> designPlanProduct);
	
	/**
	 * 所有数据
	 * 
	 * @param  designPlan
	 * @return   List<DesignPlan>
	 */
	public List<DesignPlanProduct> getList(DesignPlanProduct designPlan);
	public void batchAdd(List<DesignPlanProduct> planProductList) ;
	/**
	 * 获取进入设计方案信息
	 * @param designPlanId
	 * @param newFlag
	 * @param houseId
	 * @param livingId
	 * @param residentialUnitsName
	 * @param request
	 * @return Object
	 */
	public Object getDesignPlanInfo(Integer designPlanId,Integer newFlag,String houseId,String livingId,String residentialUnitsName, Boolean isRelease, LoginUser loginUser, String mediaType);
	public UnityPlanProduct getPlanProductStructureJson(UnityPlanProduct unityPlanProduct,DesignPlanProduct planProduct,DesignPlanModel designPlan, LoginUser loginUser) ;
		
	public List<UnityPlanProduct> getDecorationNavigationInfo(List<UnityPlanProduct> unityPlanProductList,List<UnityPlanProduct> newUnityPlanProductList,TreeSet<String> productTypeCodeSet,Map<String, UnityPlanProduct> unityPlanProductMap_p) ;
	
	public DesignPlanModel selectDesignPlanInfo(Integer id);
	/**
	 * 通过配置内容修改设计方案产品
	 * @param context
	 * @return
	 */
	public boolean updatePlanProductByConfig(String context,Integer planId);
	
	public boolean updatePlanProductByConfig(String context,Integer planId, String contextType);
	
	public DesignPlanProduct getPlanProduct(Integer id);
	/**
	 *    更新数据
	 *
	 * @param designPlanProduct
	 * @return  int 
	 */
	public int updatePlanProduct(DesignPlanProduct designPlanProduct);
	
	public int updateRes(ResDesign resDesign);
	
	public int addPlanProduct(DesignPlanProduct designPlanProduct);
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

	public boolean updateDesignPlan(DesignPlan desPlan,Integer designPlanId,Integer planProductId,
			Integer productId , String materialPicId,String context,HttpServletRequest request, String splitTexturesChoose) throws InvocationTargetException, IllegalAccessException;
	/**
	 * 根据设计方案id查找设计方案产品列表
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public List<DesignPlanProduct> getListByPlanId(Integer planId);
	
	public DesignPlanRes getDesignPlanRes(long designPlanId);
	
	public long saveAsRenderOnekey(DesignPlanRes designPlanRes);
	
	void delete(Integer id);
	/**
	 *    更新数据
	 *
	 * @param resRenderPic
	 * @return  int 
	 */
	public int update(ResRenderPic resRenderPic);
	 /**
     * 
     * isInvisible4Render判断一个设计方式，是否是渲染场景对应的临时方案，且不可见      
     * @author huangsongbo
     * @param designPlanId
     * @return 
    
     * @return boolean    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public boolean isInvisible4Render(long designPlanId);
    /**
     * changeTempDesignPalnVisible为副本创建的临时设计方案设置为可见
     * @author huangsongbo
     * @param tempDesignPalnId 设计方案id
     * 
     * @return void 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public  void changeTempDesignPalnVisible(long planId);
	// -------------------------------- 分割线 -------------------------------------
	public ResponseEnvelope<UnityDesignPlan> findOnekeyAutoRenderPlanInfo(Integer designTempletId,DesignPlanRecommended designPlanRecommended,String context,String msgId, LoginUser loginUser, String mediaType);
	
	int costTypeListCount(DesignPlanProduct designPlanProduct);
	/**
	 * 结算汇总清单
	 * @param designPlanProduct
	 * @return
	 */
	List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct);
	/**
	 * 结算汇总清单
	 * @return
	 */
	List<ProductsCost> costList(ProductsCostType productsCostType);

	/**
	 * 结算清单明细
	 * @param cost
	 * @return
	 */
	List<ProductCostDetail> costDetail(ProductsCost cost);
	
	
	/**
	 * 获取设计方案费用列表
	 * @param loginUser
	 * @param designPlanProduct
	 */
	List<ProductsCostType> costListPlan(LoginUser loginUser, DesignPlanProduct designPlanProduct);
	public List<ProductsCostType> getProductsCost(Integer planId, Integer userId, Integer userType);
	int countOnekeyDesignPlan(ResRenderPic resRenderPic);
	/**
	 * 移动端获取渲染后的720原图sysCode
	 * @param planRecommendedId 推荐方案ID
	 * @param sourceTemplateId 样板房ID
	 * @return
	 */
	public Map<String,String> get720SysCode(Integer planRecommendedId , Integer sourceTemplateId, Integer renderingType,Integer taskId);
	/**
	 * 
	 * @param designPlanId 设计方案ID
	 * @return
	 */
	public DesignPlanRes getAutoRenderDesignPlanRes(Integer designPlanId);
	/**
	 * 设计方案资源存副本
	 * @param designPlanRes
	 * @return
	 */
	public Integer saveAsRenderBakScene(DesignPlanRes designPlanRes,LoginUser loginUser) ;

	
	public List<ProductDTO> getProductDTOList(Integer designPlanId);
	public UnityDesignPlan wrapperData(Integer designPlanId, UnityDesignPlan unityDesignPlan);
}
