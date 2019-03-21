package com.nork.product.controller.web;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.param.ParamCommonVerification;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.ProductCategoryRelCacher;
import com.nork.product.cache.UserProductCollectCacher;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CollectCatalog;
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.result.SearchCollectCatalogResult;
import com.nork.product.model.search.CollectCatalogSearch;
import com.nork.product.model.search.UserProductCollectSearch;
import com.nork.product.model.small.UserProductCollectSmall;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.CollectCatalogService;
import com.nork.product.service.UserProductCollectService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**   
 * @Title: CollectCatalogController.java 
 * @Package com.nork.product.controller
 * @Description:产品管理-收藏目录表Controller
 * @createAuthor pandajun 
 * @CreateDate 2016-07-01 10:46:26
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/vue/product/collectCatalog")
public class VueCollectCatalogController {
	private static Logger logger = Logger
			.getLogger(VueCollectCatalogController.class);
	private final JsonDataServiceImpl<CollectCatalog> JsonUtil = new JsonDataServiceImpl<CollectCatalog>();
	private final JsonDataServiceImpl<UserProductCollect> Json_Util = new JsonDataServiceImpl<UserProductCollect>();
	@Autowired
	private CollectCatalogService collectCatalogService;
	@Autowired
	private UserProductCollectService userProductCollectService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private SysUserService sysUserService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 * 收藏目录表列表 接口
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/queryCollectCatalogList")
	@ResponseBody
	public Object queryCollectCatalogList(@RequestBody CollectCatalogSearch collectCatalogSearch, HttpServletRequest request) {

		String msgId = "";
		if (collectCatalogSearch != null) {
			msgId = collectCatalogSearch.getMsgId();
		}
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);;
		if (loginUser == null) {
			return new ResponseEnvelope<BaseBrand>(false, "登录超时，请重新登录!", msgId);
		} else {
			collectCatalogSearch.setUserId(loginUser.getId());
		}

		List<SearchCollectCatalogResult> ResultList = new ArrayList<SearchCollectCatalogResult> ();
		List<CollectCatalog> list = new ArrayList<CollectCatalog> ();
		int count = 0;
		try {
			count = collectCatalogService.getCount(collectCatalogSearch);
            logger.info("total:" + count);
            
            if(count<=0){
            	SysUser user=sysUserService.get(loginUser.getId());
            	newCollectCatalogController(user);
    			count = collectCatalogService.getCount(collectCatalogSearch);
            }
            if(count>0){
            	collectCatalogSearch.setOrder("gmt_create");
    			collectCatalogSearch.setOrderNum("desc");
    			list = collectCatalogService.getPaginatedList(collectCatalogSearch);	
            }
            if(list!=null&&list.size()>0){
				for (CollectCatalog collectCatalog : list) {
					SearchCollectCatalogResult searchCollectCatalogResult = new SearchCollectCatalogResult();
					searchCollectCatalogResult.setId(collectCatalog.getId());	
					searchCollectCatalogResult.setCatalogName(collectCatalog.getCatalogName());
					searchCollectCatalogResult.setSysCode(collectCatalog.getSysCode());
					searchCollectCatalogResult.setIsLocked(collectCatalog.getIsLocked());
					ResultList.add(searchCollectCatalogResult);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope(ResultList,msgId) ;
	}

	
	/**
	 * 新建一个默认文件夹
	 * @param user
	 */
	@SuppressWarnings("all")
	public void newCollectCatalogController(SysUser user){
		CollectCatalog catalog = new CollectCatalog();
		catalog.setCatalogName("默认");
		catalog.setIsLocked(1);
		catalog.setUserId(user.getId());
		catalog.setIsDeleted(0);
		catalog.setSysCode(System.currentTimeMillis()+"_"+randomNumber());
		catalog.setModifier(user.getMobile());
		catalog.setCreator(user.getNickName());
		collectCatalogService.add(catalog);
	}
	
    public int  randomNumber(){
        Set<Integer> m = new HashSet<Integer>();
        /**for(int i=0;i<100;i++){*/
            int a;
            do{
             a = (int)(Math.random()*1000000);
            }while(m.contains(a));
            m.add(a);
 
       /** }*/
		return a;
     }
    
    
	/**
	 * 添加收藏目录
	 * @param request
	 * @param catalogName
	 * @return
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/addCollectCatalog")
	@ResponseBody
	public Object addCollectCatalog(@RequestBody CollectCatalog collectCatalog, HttpServletRequest request){

		if (collectCatalog == null) {
			return new ResponseEnvelope<CollectCatalog>(false, "param is empty！", "");
		}
		String catalogName = collectCatalog.getCatalogName();
		String msgId = collectCatalog.getMsgId();
		CollectCatalog catalog = new CollectCatalog();

		if(StringUtils.isBlank(catalogName) ){
			return new ResponseEnvelope<CollectCatalog>(false, "目录名称catalogName参数为空！", msgId);
		}
		if("默认".equals(catalogName)){
			return new ResponseEnvelope<CollectCatalog>(false, "该目录名称无法创建,请重新命名！", msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if( loginUser == null ){
			return new ResponseEnvelope<BaseBrand>(false, "登录失效！", msgId);
		}
		sysSave(catalog,request);
		catalog.setCatalogName(catalogName);
		catalog.setUserId(loginUser.getId());
		int result = collectCatalogService.add(catalog);
		if (result > 0) {
			return  new ResponseEnvelope<CollectCatalog>(catalog,msgId,true);
		} else {
			return  new ResponseEnvelope<CollectCatalog>(false,"保存失败", msgId);
		}
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(CollectCatalog model,HttpServletRequest request){
		if(model != null){
			LoginUser loginUser = new LoginUser();
			if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
				loginUser.setLoginName("nologin");
			}else{
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if(model.getId() == null){
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if(model.getSysCode()==null || "".equals(model.getSysCode())){
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
	
	

	/**
	 * 保存我的产品到 收藏目录
	 * @param userProductCollect
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/collecProduct")
	@ResponseBody
	public Object collecProduct(@RequestBody UserProductCollect userProductCollect
			,HttpServletRequest request) throws Exception{

		if (userProductCollect == null) {
			return new ResponseEnvelope<UserProductCollect>(false, "param is emtyp!","");
		}
		String msgId = userProductCollect.getMsgId();
		//获取登录用户信息
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser == null ){
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!",userProductCollect.getMsgId());
		}else{
			userProductCollect.setUserId(loginUser.getId());
		}
		Integer productId = userProductCollect.getProductId();
		Integer collectCatalogId = userProductCollect.getCollectCatalogId();
		//参数验证
		boolean valid = ParamCommonVerification.checkTheParamIsValid(msgId, productId, collectCatalogId);
		if (!valid) {
			return new ResponseEnvelope<UserProductCollectSmall>(false,"param error!", msgId);
		}
		//获取产品信息
		BaseProduct bp = baseProductService.get(productId);
		if (bp == null) {
			logger.error("产品不存在 productId = " + productId);
			return new ResponseEnvelope<UserProductCollectSmall>(false,"该产品不存在", msgId);
		}
		/**List<UserProductCollect> list = userProductCollectService.getList(userProductCollect);
		if(CustomerListUtils.isNotEmpty(list)){
			msg = "该产品已收藏，请查看收藏夹！";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
		}*/

		CollectCatalog collectCatalog = collectCatalogService.get(collectCatalogId);
		if (collectCatalog == null) {
			return new ResponseEnvelope<UserProductCollectSmall>(false, "该收藏目录不存在", msgId);
		}
		/*检测是否收藏过该产品(不分收藏夹)*/
		userProductCollect.setCollectCatalogId(null);
		List<UserProductCollect> list = userProductCollectService.getList(userProductCollect);
		if( Lists.isNotEmpty(list) ){
			return new ResponseEnvelope<UserProductCollectSmall>(false,"你已收藏该产品！", msgId);
		}
		userProductCollect.setCollectCatalogId(collectCatalogId);
		/*检测是否收藏过该产品(不分收藏夹)->end*/
		try {
			sysSave(userProductCollect,request);
			if (userProductCollect.getId() == null) {
				int id = userProductCollectService.add(userProductCollect);
				logger.info("add:id=" + id);
				userProductCollect.setId(id);
				BaseProductCacher.remove(id);
			} else {
				int id = userProductCollectService.update(userProductCollect);
				logger.info("update:id=" + id);
				BaseProductCacher.remove(id);
			}
			UserProductCollectCacher.remove(userProductCollect.getId());
			ProductCategoryRelCacher.remove(1);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UserProductCollect>(false, "数据异常!", msgId);
		}

		return new ResponseEnvelope<>(true, "收藏成功", msgId);
	}
	
	
	
	
	
	/**
	 * 自动存储系统字段
	 */
	@SuppressWarnings("all")
	private void sysSave(UserProductCollect model,HttpServletRequest request){
		if(model != null){
				 LoginUser loginUser = new LoginUser();
				 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				 }
				 
				if(model.getId() == null){
					model.setGmtCreate(new Date());
					model.setCreator(loginUser.getLoginName());
					model.setIsDeleted(0);
				    if(model.getSysCode()==null || "".equals(model.getSysCode())){
					   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				   }
				}
				
				model.setGmtModified(new Date());
				model.setModifier(loginUser.getLoginName());
		}
	}
	
	/**
	 * 删除 收藏目录(转移 产品至 默认目录 方法)
	 * @param collectCatalog
	 * @param request
	 * @return
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/deleteCollectCatalog")
	@ResponseBody
	public Object deleteCollectCatalog(@RequestBody CollectCatalog collectCatalog, HttpServletRequest request){

		if (collectCatalog == null) {
			return new ResponseEnvelope<CollectCatalog>(false, "param is empty!","");
		}
		String msgId = collectCatalog.getMsgId();
		Integer id = collectCatalog.getId();
		if (id==null || id <= 0) {
			return new ResponseEnvelope<CollectCatalog>(false, "删除失败,缺少参数id!", msgId);
		}
		//获取登录用户信息
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser == null) {
			return new ResponseEnvelope<CollectCatalog>(false, "请登录系统!",msgId);
		} else {
			collectCatalog.setUserId(loginUser.getId());
		}

		try{
			List<CollectCatalog> list=collectCatalogService.getList(collectCatalog);
			if (list == null || list.size() <= 0) {
				return new ResponseEnvelope<CollectCatalog>(false, "删除失败,该目录不存在",msgId);
			}
			/**CollectCatalog collect_Catalog=collectCatalogService.get(collectCatalog.getId());*/
			if("默认".equals(list.get(0).getCatalogName())){
				return new ResponseEnvelope<CollectCatalog>(false, "默认目录 无法删除",msgId);
			}
			collectCatalogService.delete(id);
			
			/*******查询该用户的  默认收藏夹  Id******/
			CollectCatalogSearch collectCatalogSearch= new CollectCatalogSearch();
			collectCatalogSearch.setUserId(collectCatalog.getUserId());
			collectCatalogSearch.setCatalogName("默认");
			List<CollectCatalog> CollectCatalogList=collectCatalogService.getPaginatedList(collectCatalogSearch);
			int defaultId=CollectCatalogList.get(0).getId();   
			
			/*******通过用户id 和 收藏夹Id，将将收藏夹ID  改为 默认Id************/
			UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
			userProductCollectSearch.setUserId(collectCatalog.getUserId());
			userProductCollectSearch.setCollectCatalogId(collectCatalog.getId());
			userProductCollectSearch.setDefaultId(defaultId);
			userProductCollectService.transferCollection(userProductCollectSearch);   /**转移方法**/
			
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEnvelope<CollectCatalog>(false, "删除失败:"+e,msgId);
		}
		
		return new ResponseEnvelope<CollectCatalog>(true,msgId,true);
	}

	/**
	 * 产品收藏删除-----接口
	 * @param userProductCollect
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delUserProduct")
	@ResponseBody
	public Object delUserProduct(@RequestBody UserProductCollect userProductCollect, HttpServletRequest request) {

		if (userProductCollect == null) {
			return new ResponseEnvelope<UserProductCollect>(false, "param is empty!", "");
		}
		String msgId = userProductCollect.getMsgId();
		Integer productId = userProductCollect.getProductId();
		//参数验证空
		boolean valid = ParamCommonVerification.checkTheParamIsValid(msgId, productId);
		if (!valid) {
			return new ResponseEnvelope<UserProductCollect>(false, "param is error!", "");
		}
		//获取登录用户信息
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser == null) {
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!", msgId);
		} else {
			userProductCollect.setUserId(loginUser.getId());
		}
		//查询是否存在
		List<UserProductCollect> list = userProductCollectService.getList(userProductCollect);
		if (list !=null && list.size() > 0) {
			int flag = userProductCollectService.delete(list.get(0).getId());
			if (flag == 1) {
				UserProductCollectCacher.remove(list.get(0).getId());
				ProductCategoryRelCacher.remove(1);
				return new ResponseEnvelope<UserProductCollect>(true, msgId,true);
			} else {
				return new ResponseEnvelope<UserProductCollect>(false, "数据删除失败!", msgId);
			}
		} else {
			return new ResponseEnvelope<UserProductCollect>(false, "该记录不存在或已被删除!", msgId);
		}
	}
	
}
