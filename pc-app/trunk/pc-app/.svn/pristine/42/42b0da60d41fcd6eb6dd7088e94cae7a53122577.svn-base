package com.nork.product.controller.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
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

/**   
 * @Title: CollectCatalogController.java 
 * @Package com.nork.product.controller
 * @Description:产品管理-收藏目录表Controller
 * @createAuthor pandajun 
 * @CreateDate 2016-07-01 10:46:26
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/web/product/collectCatalog")
public class WebCollectCatalogController {
	private static Logger logger = Logger
			.getLogger(WebCollectCatalogController.class);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/queryCollectCatalogList")
	@ResponseBody
	public Object queryCollectCatalogList(@ModelAttribute("collectCatalogSearch") CollectCatalogSearch collectCatalogSearch,String msgId,HttpServletRequest request, HttpServletResponse response) {

		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return new ResponseEnvelope<BaseBrand>(false, "登录超时，请重新登录!", msgId);
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
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
			return new ResponseEnvelope<BaseBrand>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope(ResultList,msgId) ;
	}

	
	/**
	 * 新建一个默认文件夹
	 * @param user
	 */
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
	@RequestMapping(value = "/addCollectCatalog")
	@ResponseBody
	public Object addCollectCatalog(HttpServletRequest request,String catalogName,String msgId){
		CollectCatalog catalog = new CollectCatalog();
		try{
			if(StringUtils.isBlank(catalogName) ){
				return new ResponseEnvelope<CollectCatalog>(false, "目录名称catalogName参数为空！", msgId);
			}
			if("默认".equals(catalogName)){
				return new ResponseEnvelope<CollectCatalog>(false, "该目录名称无法创建,请重新命名！", msgId);
			}
			LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			if( loginUser == null ){
				return new ResponseEnvelope<BaseBrand>(false, "登录失效！", msgId);
			}
			sysSave(catalog,request);
			catalog.setCatalogName(catalogName);
			catalog.setUserId(loginUser.getId());
			collectCatalogService.add(catalog);
			return  new ResponseEnvelope<CollectCatalog>(catalog,msgId,true);
		}catch( Exception e ){
			e.printStackTrace();
			logger.error(e.getMessage());
			return  new ResponseEnvelope<CollectCatalog>(false, "系统异常！", msgId);
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
	 * @param style
	 * @param userProductCollect
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/collecProduct")
	@ResponseBody
	public Object collecProduct(
			@PathVariable String style,@ModelAttribute("userProductCollect") UserProductCollect userProductCollect
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			userProductCollect = (UserProductCollect)Json_Util.getJsonToBean(jsonStr, UserProductCollect.class);
			if(userProductCollect == null){
				return new ResponseEnvelope<UserProductCollect>(false, "传参异常!","none");
			}
		}
		Integer userId = 0;
		LoginUser loginUser = new LoginUser();
		if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!",userProductCollect.getMsgId());
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			userId = loginUser.getId();
			userProductCollect.setUserId(userId);
		}
		
		String msg = "";
		if( StringUtils.isBlank(userProductCollect.getMsgId()) ){
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
		}
		if( userProductCollect.getUserId() == null || userProductCollect.getUserId() == 0 ){
			msg = "参数userId不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
		}
		if( userProductCollect.getCollectCatalogId() == null || userProductCollect.getCollectCatalogId() == 0){
			msg = "参数collectCatalogId不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
		}
		if( userProductCollect.getProductId() == null ){
			msg = "参数productId不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
		}else if(userProductCollect.getProductId() != null){
			BaseProduct bp = new BaseProduct();
			bp = baseProductService.get(userProductCollect.getProductId());
			if(bp ==null){
				msg = "该产品不存在";
				return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
			}
		}
		userProductCollect.setUserId(userId);
		/**List<UserProductCollect> list = userProductCollectService.getList(userProductCollect);
		if(CustomerListUtils.isNotEmpty(list)){
			msg = "该产品已收藏，请查看收藏夹！";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
		}*/

		logger.info("收藏目录ID："+userProductCollect.getCollectCatalogId());
		CollectCatalog collectCatalog=collectCatalogService.get(userProductCollect.getCollectCatalogId());
		if(collectCatalog==null){
			msg = "该收藏目录不存在";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
		}
		/*检测是否收藏过该产品(不分收藏夹)*/
		Integer collectCatalogId=userProductCollect.getCollectCatalogId();
		userProductCollect.setCollectCatalogId(null);
		List<UserProductCollect> list = userProductCollectService.getList(userProductCollect);
		if( Lists.isNotEmpty(list) ){
			msg = "你已收藏该产品！";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
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
			if("small".equals(style)){
				String message = "ok";
				return new ResponseEnvelope<UserProductCollect>(true,message,userProductCollect.getMsgId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UserProductCollect>(false, "数据异常!",userProductCollect.getMsgId());
		}

		return new ResponseEnvelope<UserProductCollect>(userProductCollect,userProductCollect.getMsgId(),true);
	}
	
	
	
	
	
	/**
	 * 自动存储系统字段
	 */
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
	 * @param msgId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteCollectCatalog")
	@ResponseBody
	public Object deleteCollectCatalog(@ModelAttribute("collectCatalog") CollectCatalog collectCatalog,String msgId,HttpServletRequest request, HttpServletResponse response){
		try{
			
			if(msgId==null||"".equals(msgId)){
				return new ResponseEnvelope<CollectCatalog>(false, "删除失败,缺少参数msgId!",msgId);
			}
			if(collectCatalog==null){
				return new ResponseEnvelope<CollectCatalog>(false, "删除失败,缺少参数id!",msgId);
			}
			if(collectCatalog.getId()==null||collectCatalog.getId()<=0){
				return new ResponseEnvelope<CollectCatalog>(false, "删除失败,缺少参数id!",msgId);
			}
			if(collectCatalog.getUserId()==null||collectCatalog.getUserId()<=0){
				return new ResponseEnvelope<CollectCatalog>(false, "删除失败,缺少参数userId!",msgId);
			}
			
			List<CollectCatalog> list=collectCatalogService.getList(collectCatalog);
			if(list==null||list.size()<=0){
				return new ResponseEnvelope<CollectCatalog>(false, "删除失败,该目录不存在",msgId);
			}
			
			/**CollectCatalog collect_Catalog=collectCatalogService.get(collectCatalog.getId());*/
			if("默认".equals(list.get(0).getCatalogName())){
				return new ResponseEnvelope<CollectCatalog>(false, "默认目录 无法删除",msgId);
			}
			
			collectCatalogService.delete(collectCatalog.getId());
			
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
	
}
