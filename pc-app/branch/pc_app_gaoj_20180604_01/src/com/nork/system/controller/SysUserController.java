package com.nork.system.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.CollationKey;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.pay.model.PayAccount;
import com.nork.pay.service.PayAccountService;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginMenu;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.ProCategory;
import com.nork.product.model.search.ProCategorySearch;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.ProCategoryService;
import com.nork.system.model.BaseArea;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysFunc;
import com.nork.system.model.SysGroup;
import com.nork.system.model.SysRole;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUserFans;
import com.nork.system.model.SysUserRole;
import com.nork.system.model.UserAccess;
import com.nork.system.model.search.BaseAreaSearch;
import com.nork.system.model.search.DesignerWorksSearch;
import com.nork.system.model.search.SysRoleSearch;
import com.nork.system.model.search.SysUserFansSearch;
import com.nork.system.model.search.SysUserSearch;
import com.nork.system.model.search.UserAccessSearch;
import com.nork.system.model.small.SysUserSmall;
import com.nork.system.service.BaseAreaService;
import com.nork.system.service.DesignerWorksService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysFuncService;
import com.nork.system.service.SysGroupService;
import com.nork.system.service.SysRoleService;
import com.nork.system.service.SysUserFansService;
import com.nork.system.service.SysUserRoleService;
import com.nork.system.service.SysUserService;
import com.nork.system.service.UserAccessService;

/**
 * @Title: SysUserController.java
 * @Package com.nork.system.controller
 * @Description:系统-用户Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/system/sysUser")
public class SysUserController {
	private static Logger logger = Logger
			.getLogger(SysUserController.class);
	private final JsonDataServiceImpl<SysUser> JsonUtil = new JsonDataServiceImpl<SysUser>();
	private final String STYLE="jsp";
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/system/sysUser";
	private final String BASEMAIN = "/"+ STYLE + "/system/base/sysUser";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/system/sysUser";

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysFuncService sysFuncService;

	@Autowired
	private SysDictionaryService sysDictionaryService;

	@Autowired
	private SysGroupService sysGroupService;

	@Autowired
	private BaseAreaService baseAreaService;

    @Autowired
    private SysUserFansService sysUserFansService;
    @Autowired
	private ResPicService resPicService;
    @Autowired
	private DesignerWorksService designerWorksService;
	@Autowired
	private UserAccessService userAccessService;
	@Autowired
	private BaseBrandService baseBrandService;
	
   // private final String PIC_UPLOAD_PATH = "system.sysUser.pic.upload.path";
    
    @Autowired
    private ProCategoryService categoryService;

    @Autowired
	private PayAccountService payAccountService;

	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Object login(SysUser sysUser ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUser = (SysUser)JsonUtil.getJsonToBean(jsonStr, SysUser.class);
			if(sysUser==null){
			   return new ResponseEnvelope<SysUser>(false, "未传递正确的参数!","login");
			}
		}

		SysUser user = sysUserService.checkUserAccount(sysUser.getAccount(), sysUser.getPassword());
		
		if(user!=null){
			 String userJsonBean = JsonUtil.getBeanToJsonData(user);
			 SysUserSmall sysUserSmall= new JsonDataServiceImpl<SysUserSmall>().getJsonToBean(userJsonBean, SysUserSmall.class);
			 return new ResponseEnvelope<SysUserSmall>(sysUserSmall,sysUser.getMsgId(),true);
		}else{
			 return new ResponseEnvelope<SysUser>(false, "用户名或密码错误",sysUser.getMsgId());

		}

	}

	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/jsplogin")
	public Object jsplogin(SysUser sysUser ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUser = (SysUser)JsonUtil.getJsonToBean(jsonStr, SysUser.class);
			if(sysUser==null){
			   return new ResponseEnvelope<SysUser>(false, "未传递正确的参数!","login");
			}
		}
		String account = sysUser.getAccount();
		String password = sysUser.getPassword();
		password=sysUserService.getMd5Password(password);
		if(StringUtils.isEmpty(account)||StringUtils.isEmpty(password)){
			request.setAttribute("loginerror", "用户名或密码不能为空，请输入！");
			//return Utils.getPageUrl(request,  "/jsp/login");
			return "redirect:/pages/jsp/login.jsp";
		}
		sysUserService=SpringContextHolder.getBean(SysUserService.class);
		SysUser user = sysUserService.checkUserAccount(account, password);
		if(user!=null){
			LoginUser loginUser = user.toLoginUser();
			ResPic resPic = null;
			if( user.getPicId() != null && user.getPicId() != 0 ){
				resPic = resPicService.get(Integer.valueOf(user.getPicId()));
			}
			String picPath = "";
			if( resPic != null ){
				picPath = Utils.getValue("app.resources.url","http://localhost:89")+resPic.getPicPath();
			}else{
				if(user!= null && (user.getSex() ==null || user.getSex() == 1)){//男
					picPath = request.getContextPath() + "/pages/online/images/user/manIcon.jpg";
				}else if( user.getSex() == 2 ){//女
					picPath = request.getContextPath() + "/pages/online/images/user/womenIcon.jpg";
				}
			}
			loginUser.setPicPath(picPath);
			loginUser.setBrandIds(user.getBrandIds());
//			request.getSession().setAttribute("loginUser",loginUser);

			List<SysDictionary> sysDictionaryList = sysDictionaryService.getList(new SysDictionary());
//			request.getSession().setAttribute("sysDictionaryList", sysDictionaryList);

			//生成session-key
			user.setToken(Utils.generateRandomDigitString(32));
			sysUserService.update(user);

			//获取该用户的菜单权限
			List<LoginMenu> datalist= new ArrayList<LoginMenu>();
			List<SysFunc> funcList = sysFuncService.getUserMenus(loginUser.getId());
			//转成loginMenu
			if(funcList != null){
				for(SysFunc func:funcList){
					datalist.add(func.toLoginMenu());
				}
			}
			//datalist = new LoginMenu().getMenulist();
			List<LoginMenu> menu01list = new ArrayList<LoginMenu>();
			for(LoginMenu m:datalist ){
				if(m.getParentId()==1 && m.getLevelId()==1){
					menu01list.add(m);
				}
			}
//			request.getSession().setAttribute("menu01List", menu01list);
 

		/*	ProCategory category = categoryService.get(1);
            List<SearchProCategorySmall> categoryList = recursionCategory(category);
            category.setChildrenNodes(categoryList);
            SearchProCategorySmall categorySmall = new SearchProCategorySmall();
            categorySmall.setCid(category.getId());
            categorySmall.setCategoryCode(category.getCode());
            categorySmall.setPid(category.getPid());
            categorySmall.setName(category.getName());
            categorySmall.setChildrenNodes(category.getChildrenNodes());
            JSONObject jsonObject = JSONObject.fromObject(categorySmall);
            request.getSession().setAttribute("CATEGORY", categorySmall);
            try {
            	request.getSession().setAttribute("CATEGORY_JSON_DATA",URLEncoder.encode(jsonObject.toString(),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
			//return Utils.getPageUrl(request,  "/jsp/index");
			 ProCategory category = null; //categoryService.get(1);
	            ProCategory pc = new ProCategory();
	            pc.setOrder(" ordering");
	            List<ProCategory> categoryAllList = categoryService.getList(pc);
	            if(categoryAllList!= null && categoryAllList.size()>=0){
	            	for(ProCategory ppc :categoryAllList){
	            		if(ppc.getId().intValue() == 1){
	            			category = ppc;
	            			break;
	            		}
	            	}
	            }
	            List<SearchProCategorySmall> categoryList = recursionCategory2(category,categoryAllList);
	            category.setChildrenNodes(categoryList);
	            SearchProCategorySmall categorySmall = new SearchProCategorySmall();
	            categorySmall.setAa(category.getId());
	            categorySmall.setBb(category.getCode());
	            categorySmall.setCc(category.getPid());
	            categorySmall.setDd(category.getName());
	            categorySmall.setFf(category.getChildrenNodes());
	            JSONObject jsonObject = JSONObject.fromObject(categorySmall);
	            request.getSession().setAttribute("CATEGORY", categorySmall);
	            try {
	            	 request.getSession().setAttribute("CATEGORY_JSON_DATA",URLEncoder.encode(jsonObject.toString(),"UTF-8"));
	            } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	            }
			return "redirect:/pages/jsp/index.jsp";

		}else{
			request.setAttribute("loginerror", "用户名或密码不正确，请重新输入！");
			//return  Utils.getPageUrl(request,  "/jsp/login");
			return "redirect:/pages/jsp/login.jsp";
		}

	}

	 /**
     * 递归查询分类
     * @param category
     * @return
     */
    public List<SearchProCategorySmall> recursionCategory2(ProCategory category,List<ProCategory> categoryAllList){
        List<SearchProCategorySmall> childrenNodes = category.getChildrenNodes();
        ProCategorySearch search = new ProCategorySearch();
        search.setPid(category.getId());
        search.setLongCode(category.getLongCode());
        List<ProCategory> list = new ArrayList<ProCategory>();
        if(categoryAllList!= null && categoryAllList.size()>0){
        	for(ProCategory pc:categoryAllList){
        		if(pc.getPid().intValue()==search.getPid().intValue() && pc.getLongCode().indexOf(category.getLongCode()) != -1){
        			list.add(pc);
        		}
        	}
        }else{
	        list = categoryService.getList(search);
	    }
        if( list != null && list.size() > 0 ){
            if( childrenNodes == null ){
                childrenNodes = new ArrayList<SearchProCategorySmall>();
            }
        }
        SearchProCategorySmall newCategory = null;
        for( ProCategory childrenNode : list ){
            newCategory = new SearchProCategorySmall();
            newCategory.setAa(childrenNode.getId());
            newCategory.setCc(childrenNode.getPid());
            newCategory.setDd(childrenNode.getName());
            newCategory.setBb(childrenNode.getCode());
            newCategory.setFf(recursionCategory2(childrenNode,categoryAllList));
            childrenNodes.add(newCategory);
        }
        category.setChildrenNodes(childrenNodes);
        
        return childrenNodes;
    }
    
	  /**
     * 递归查询分类
     * @param category
     * @return
     */
    public List<SearchProCategorySmall> recursionCategory(ProCategory category){
        List<SearchProCategorySmall> childrenNodes = category.getChildrenNodes();
        ProCategorySearch search = new ProCategorySearch();
        search.setPid(category.getId());
        search.setLongCode(category.getLongCode());
        List<ProCategory> list = categoryService.getList(search);
        if( list != null && list.size() > 0 ){
            if( childrenNodes == null ){
                childrenNodes = new ArrayList<SearchProCategorySmall>();
            }
            SearchProCategorySmall newCategory = null;
            for( ProCategory childrenNode : list ){
                newCategory = new SearchProCategorySmall();
                newCategory.setAa(childrenNode.getId());
                newCategory.setCc(childrenNode.getPid());
                newCategory.setDd(childrenNode.getName());
                newCategory.setBb(childrenNode.getCode());
                newCategory.setFf(recursionCategory(childrenNode));
                childrenNodes.add(newCategory);
            }
            category.setChildrenNodes(childrenNodes);
        }
        return childrenNodes;
    }
    
	/**
	 * 用户退出
	 */
	@RequestMapping(value = "/loginout")
	public Object loginout(SysUser sysUser ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 清除session
	    request.getSession().removeAttribute("loginUser");
	    request.getSession().removeAttribute("sysDictionaryList");
	    request.getSession().removeAttribute("sysUserList");
	    request.getSession().removeAttribute("CATEGORY");
        return  "redirect:" + Utils.getPageUrl(request,  "/pages/jsp/login.jsp");
	}


	/**
	 * 用户退出
	 */
	@RequestMapping(value = "/clear")
	@ResponseBody
	public void clear(SysUser sysUser ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 清除session
	    request.getSession().removeAttribute("loginUser");
	    request.getSession().removeAttribute("sysDictionaryList");
	    request.getSession().removeAttribute("sysUserList");
	}

	/**
	 * 用户注册
	 */
	@RequestMapping(value = "/register")
	@ResponseBody
	public Object register(@ModelAttribute("sysUser") SysUser sysUser,HttpServletRequest request, HttpServletResponse response) throws Exception{

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   sysUser = (SysUser)JsonUtil.getJsonToBean(jsonStr, SysUser.class);
		   if(sysUser == null){
			  return new ResponseEnvelope<SysUser>(false, "传参异常!","none");
		   }
		}

		try {
			sysSave(sysUser,request);
			if (sysUser.getId() == null) {
				int id = sysUserService.add(sysUser,request);
				logger.info("add:id=" + id);
				sysUser.setId(id);

			} else {
				int id = sysUserService.update(sysUser);
				logger.info("update:id=" + id);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!",sysUser.getMsgId());
		}
		return new ResponseEnvelope<SysUser>(true, "注册成功!",sysUser.getMsgId());
	}

	/**
	 * 验证昵称是否存在
	 * @param sysUser
	 * @param type
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkNickName")
	public void checkNickName(@ModelAttribute("sysUser") SysUser sysUser,String type,HttpServletRequest request, HttpServletResponse response) throws IOException {

		String flag = "false";

		 if("edit".equals(type)){
			 SysUser user =  sysUserService.get(sysUser.getId());
			 if(user!= null && sysUser.getNickName()!=null&&sysUser.getNickName().equals(user.getNickName())){
				 flag = "true";
				 response.getWriter().write(flag);
				 return;
			 }
		 }
		 List<SysUser> list = sysUserService.getList(sysUser);
			if(CustomerListUtils.isNotEmpty(list)){
				flag = "false";
			}else{
				flag = "true";
		}

		response.getWriter().print(flag);
	}


	/**
	 * 验证手机是否存在
	 * @param sysUser
	 * @param type
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkMobile")
	public void checkMobile(@ModelAttribute("sysUser") SysUser sysUser,String type,HttpServletRequest request, HttpServletResponse response) throws IOException {

		String flag = "true";
		 
		 if("edit".equals(type)){
			 SysUser user =  sysUserService.get(sysUser.getId());
			 if(user!= null && sysUser.getMobile()!=null&&sysUser.getMobile().equals(user.getMobile())){
				 flag = "true";
				 response.getWriter().write(flag);
				 return;
			 }
		 }

		List<SysUser> list = sysUserService.getList(sysUser);
		if(CustomerListUtils.isNotEmpty(list)){
			flag = "false";
		}else{
			flag = "true";
		}

		response.getWriter().print(flag);
	}

	/**
	 * 验证旧手机是否存在
	 * @param sysUser
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/oldMobile")
	public void oldMobile(@ModelAttribute("sysUser") SysUser sysUser,HttpServletRequest request, HttpServletResponse response) throws IOException {

		 String flag = "false";
		 try{
			 SysUser user =  sysUserService.get(sysUser.getId());
			 if(user!= null && user.getMobile()!=null&&sysUser.getOldMobile().equals(user.getMobile())){
				 flag = "true";
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 response.getWriter().write(flag);
		 return ; 
	}

	/**
	 * 验证邮箱是否存在
	 * @param sysUser
	 * @param type
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkEmail")
	public void checkEmail(@ModelAttribute("sysUser") SysUser sysUser,String type,HttpServletRequest request, HttpServletResponse response) throws IOException {

		String flag = "false";

		 if("edit".equals(type)){
			 SysUser user =  sysUserService.get(sysUser.getId());
			 if(user!= null && sysUser.getEmail()!=null&&sysUser.getEmail().equals(user.getEmail())){
				 flag = "true";
				 response.getWriter().write(flag);
				 return;
			 }
		 }
		 List<SysUser> list = sysUserService.getList(sysUser);
		 if(CustomerListUtils.isNotEmpty(list)){
			flag = "false";
		 }else{
			flag = "true";
	    }

		response.getWriter().print(flag);
	}

	/**
	 * 新用户判断
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/sysUserCheck")
	@ResponseBody
	public Object sysUserCheck(
			String account, String password,HttpServletRequest request) throws Exception {
		ResponseEnvelope res=null;
		if (!Utils.isEmpty(account) && !Utils.isEmpty(password)) {
			password=sysUserService.getMd5Password(password);
			SysUserSearch search = new SysUserSearch();
			search.setIsDeleted(0);
			search.setNickName(account);
			int count = sysUserService.getCount(search);

			if (count == 0) {
				return new ResponseEnvelope<SysUserSearch>(false, "该用户不存在!");
				/**res=new ResponseEnvelope<SysUserSearch>(false, "该用户不存在!");
				return res*/
			}
			SysUser sysUser = new SysUser();
			sysUser.setPassword(password);
			sysUser.setNickName(account);
			sysUser.setIsDeleted(0);
			List<SysUser> list = sysUserService.getList(sysUser);
			if (list == null || list.size() == 0 || list.size() > 1) {
				return new ResponseEnvelope<SysUser>(false, "该用户密码不正确!");
				/**res=new ResponseEnvelope<SysUser>(false, "该用户密码不正确!");
				return res;*/
			}
			SysUser user = (SysUser) list.get(0);
			/**if (user.getStatus().intValue() != 1) {
				return new ResponseEnvelope<SysUser>(false, "该用户名已禁用!");
			}*/
			return new ResponseEnvelope<SysUser>(true);
		}

		return new ResponseEnvelope<SysUser>(false, "登录用户或密码为空!"); 
		/**res=new ResponseEnvelope<SysUser>(false, "登录用户或密码为空!");
		return ZipUtils.compressionZip(res);*/
	}


		/**
		 * 用户列表
		 * @param sysUserSearch
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value = "/jsplist")
	    public Object jsplist(@ModelAttribute("sysUserSearch") SysUserSearch sysUserSearch,String userIdsInfo,HttpServletRequest request, HttpServletResponse response) {

			List<SysUser> list = new ArrayList<SysUser> ();
			int total = 0;
			try {
				total = sysUserService.getCount(sysUserSearch);
				if (total > 0) {
					list = sysUserService.getPaginatedList(sysUserSearch);
					if(CustomerListUtils.isNotEmpty(list)){

						for(int i=0;i<list.size();i++){
							SysUser user = list.get(i);
							Integer userType = user.getUserType();

							SysDictionary sysDictionary = new SysDictionary();
							sysDictionary.setType("userType");
							sysDictionary.setValue(userType);
							List<SysDictionary> dictionaryList  = sysDictionaryService.getList(sysDictionary);
							sysDictionary = dictionaryList.get(0);

							user.setUserTypeName(sysDictionary.getName());
							
							BaseAreaSearch baseAreaSearch = new BaseAreaSearch();
							if(user.getAreaId() != null){
								
								baseAreaSearch.setAreaCode(user.getAreaId().toString());
								List<BaseArea> baseArealist= baseAreaService.getPaginatedList(baseAreaSearch);
								
								user.setAreaName(baseArealist.get(0).getAreaName()==""?"":baseArealist.get(0).getAreaName());
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			ResponseEnvelope<SysUser> res = new ResponseEnvelope<SysUser>(total, list);
			request.setAttribute("userIds", userIdsInfo);
			request.setAttribute("list", list);
			request.setAttribute("res", res);
			request.setAttribute("search", sysUserSearch);
			String pageType = request.getParameter("pageType");
			String url = JSPMAIN + "/sysUser_list";
			if("userProductConfig".equals(pageType)){
				request.setAttribute("pageType", pageType);
				url = "/jsp/product/userProductConfig/user_list";
			}

			return  Utils.getPageUrl(request, url);
		}


	   /**
		 * 角色列表
		 */
		@RequestMapping(value = "/role/list")
	    public Object rolelist(Integer userId,@ModelAttribute("sysRoleSearch") SysRoleSearch sysRoleSearch,HttpServletRequest request, HttpServletResponse response) {


			List<SysRole> rolelist = new ArrayList<SysRole> ();
			int total = 0;
			try {
				total = sysRoleService.getCount(sysRoleSearch);

				if (total > 0) {
					rolelist = sysRoleService.getPaginatedList(sysRoleSearch);

					if(CustomerListUtils.isNotEmpty(rolelist)){

						for(int i=0;i<rolelist.size();i++){

							SysRole role = rolelist.get(i);

							SysUserRole sysUserRole = new SysUserRole();
							sysUserRole.setRoleId(role.getId());
							sysUserRole.setUserId(userId);

							List<SysUserRole> userRoleList = sysUserRoleService.getList(sysUserRole);
							if(CustomerListUtils.isNotEmpty(userRoleList)){

								role.setIsChecked(1);
							}else{
								role.setIsChecked(0);
							}
				       }

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			ResponseEnvelope<SysRole> res = new ResponseEnvelope<SysRole>(total, rolelist);
			request.setAttribute("rolelist", rolelist);
			request.setAttribute("res", res);
			request.setAttribute("search", sysRoleSearch);

			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(userId);
			List<SysUserRole> userRolelist = sysUserRoleService.getList(sysUserRole);
			if(CustomerListUtils.isNotEmpty(userRolelist)){
				for(int i=0;i<userRolelist.size();i++){
					SysUserRole userRole = userRolelist.get(i);
					if(userRole != null){
						SysRole sysRole = sysRoleService.get(userRole.getRoleId());
						if(sysRole != null){
							userRole.setRoleName(sysRole.getName());
						}else{
							logger.info("sysRole is null");
						}
					}else{
						logger.info("userRole is null");
					}
		       }
			}
			request.setAttribute("userId", userId);
			request.setAttribute("userRolelist", userRolelist);

			return  Utils.getPageUrl(request, JSPMAIN + "/sysUser_role");
	}



	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 *    用户 基础主页面
	 *
	 * @param
	 * @return   String
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}

	/**
	 *    用户 主页面
	 *
	 * @param
	 * @return   String
	 */
	@RequestMapping(value = "/main")
	public String main() {
		return MAIN;
	}

    /**
	 *    访问主页面
	 *
	 * @param
	 * @return   String
	 */
	@RequestMapping(value = "/jspmain")
	public String jspmain(HttpServletRequest request) {
		request.setAttribute("autoFlag", true);
		return JSPMAIN+"/list";
	}

	/**
	 * 保存 用户
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("sysUser") SysUser sysUser
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
		/*密码加密*/
		if(StringUtils.isNotBlank(sysUser.getPassword())){
			sysUser.setPassword(sysUserService.getMd5Password(sysUser.getPassword()));
		}else{
			sysUser.setPassword(null);
		}
		int res_id = -1; 
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   sysUser = (SysUser)JsonUtil.getJsonToBean(jsonStr, SysUser.class);
		   if(sysUser == null){
			  return new ResponseEnvelope<SysUser>(false, "传参异常!","none");
		   }
		}
		
		try {
			sysUser.setMediaType(1);
			String areaLongCode ="";
			if(StringUtils.isNotEmpty(sysUser.getProvinceCode()) && StringUtils.isEmpty(sysUser.getCityCode())){
				areaLongCode = "."+sysUser.getProvinceCode()+".";
			}else if(StringUtils.isNotEmpty(sysUser.getProvinceCode())  && StringUtils.isNotEmpty(sysUser.getCityCode()) && sysUser.getAreaId() == null){
				areaLongCode = "."+sysUser.getProvinceCode()+"."+sysUser.getCityCode()+".";
			}else if(StringUtils.isNotEmpty(sysUser.getProvinceCode())  && StringUtils.isNotEmpty(sysUser.getCityCode()) && sysUser.getAreaId()!=null){
				areaLongCode = "."+sysUser.getProvinceCode()+"."+sysUser.getCityCode()+"."+sysUser.getAreaId()+".";
			}
			
			sysUser.setAreaLongCode(areaLongCode);
			if(sysUser.getGroupId()==null){
			    sysUser.setGroupId(0);
			}
		    sysUser.setLevel(1);
		    sysSave(sysUser,request);
			if (sysUser.getId() == null) {
				//sysUser.setPicId(res_id);
				int id = sysUserService.add(sysUser,request);
				logger.info("add:id=" + id);
				sysUser.setId(id);
			} else {
				if(res_id != -1){
					sysUser.setPicId(res_id);
				}
				int id = sysUserService.update(sysUser);
				logger.info("update:id=" + id);
			}

			if("small".equals(style)){
				 String sysUserJson = JsonUtil.getBeanToJsonData(sysUser);
				 SysUserSmall sysUserSmall= new JsonDataServiceImpl<SysUserSmall>().getJsonToBean(sysUserJson, SysUserSmall.class);

				 return new ResponseEnvelope<SysUserSmall>(sysUserSmall,sysUser.getMsgId(),true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!",sysUser.getMsgId());
		}
		return new ResponseEnvelope<SysUser>(sysUser,sysUser.getMsgId(),true);
	}
	
	/**
	 * 设计师认证--接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/viewCertification")
	@ResponseBody
	public Object viewCertification(@PathVariable String style, String msgId,@ModelAttribute("sysUser") SysUser sysUser,HttpServletRequest request){
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUser = (SysUser)JsonUtil.getJsonToBean(jsonStr, SysUser.class);
			if(sysUser == null){
				return new ResponseEnvelope<SysUser>(false, "传参异常!","none");
			}
		}
		
		String msg = "";
		if( StringUtils.isBlank(msgId) ){
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<SysUser>(false,msg,msgId);
		}
		
		LoginUser loginUser = new LoginUser();
		//SysUser sysUser = new SysUser();
		if( com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
			return  "redirect:" + Utils.getPageUrl(request,  "/pages/online/user/login.jsp");
		}
		
		loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if("anonymous".equals(loginUser.getId())){
			return  "redirect:" + Utils.getPageUrl(request,  "/pages/online/user/login.jsp");
		}else{
			sysUser = sysUserService.get(loginUser.getId());
		}
	
		if(sysUser.getPicId()!=null){
			ResPic resPic = resPicService.get(Integer.valueOf(sysUser.getPicId()));
			sysUser.setPicPath(resPic==null?"":resPic.getPicPath());
			//request.setAttribute("picPath", resPic==null?"":resPic.getPicPath());
		}
		return new ResponseEnvelope<SysUser>(sysUser,msgId,true);
	}
	
	
	/**
	 *申请设计师认证---接口
	 */
	@RequestMapping(value = "/designerAuthenticationIF")
	@ResponseBody
	public Object designerAuthenticationIF(
			@PathVariable String style,@ModelAttribute("sysUser") SysUser sysUser,String msgId
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int res_id = -1; 
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUser = (SysUser)JsonUtil.getJsonToBean(jsonStr, SysUser.class);
			if(sysUser == null){
				return new ResponseEnvelope<SysUser>(false, "传参异常!","none");
			}
		}
		//获取登录用户
		LoginUser loginUser = new LoginUser();
		if( com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
			return new ResponseEnvelope<SysUser>(false, "登录超时，请重新登录!",sysUser.getMsgId());
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			sysUser.setId(loginUser.getId());
		}
		
		String msg = "";
		if( StringUtils.isBlank(msgId) ){
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<SysUser>(false,msg,msgId);
		}
		try {
			String areaLongCode = "."+sysUser.getProvinceCode()+"."+sysUser.getAreaId()+".";
			sysUser.setAreaLongCode(areaLongCode);
		    sysUser.setGroupId(0);
		    //sysUser.setLevel(1);
			sysSave(sysUser,request);
			if (sysUser.getId() == null) {
				int id = sysUserService.add(sysUser,request);
				logger.info("add:id=" + id);
				sysUser.setId(id);
			} else {
				if(res_id != -1){
					sysUser.setPicId(res_id);
				}
				int id = sysUserService.update(sysUser);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				String sysUserJson = JsonUtil.getBeanToJsonData(sysUser);
				SysUserSmall sysUserSmall= new JsonDataServiceImpl<SysUserSmall>().getJsonToBean(sysUserJson, SysUserSmall.class);
				
				return new ResponseEnvelope<SysUserSmall>(sysUserSmall,msgId,true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!",msgId);
		}
		sysUser = sysUserService.get(sysUser.getId());
		if(sysUser.getPicId()!=null){
			ResPic resPic = resPicService.get(Integer.valueOf(sysUser.getPicId()));
			//request.setAttribute("picPath", resPic==null?"":resPic.getPicPath());
			sysUser.setPicPath(resPic.getPicPath());
		}
		
		return new ResponseEnvelope<SysUser>(sysUser,msgId,true);
	}

	/**
	 *获取 用户详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("sysUser") SysUser sysUser
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUser = (SysUser)JsonUtil.getJsonToBean(jsonStr, SysUser.class);
			if(sysUser == null){
			   return new ResponseEnvelope<SysUser>(false,"none", "传参异常!");
			}
			id =  sysUser.getId();
			msgId = sysUser.getMsgId() ;
		}else{
		    id =  sysUser.getId();
		    msgId = sysUser.getMsgId() ;
		}
		
		LoginUser loginUser = new LoginUser();
		 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
			loginUser.setLoginName("nologin");
		 }else{
		    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		 }

		if(id == null ){
		   return new ResponseEnvelope<SysUser>(false, "参数缺少id!",msgId);
		}
		
		//记录访问
		UserAccess userAccess = new UserAccess();
		//访问id
		userAccess.setAccessId(loginUser.getId());
		//被访问id
		userAccess.setBeAccessedId(sysUser.getId());
		if(loginUser.getId() != sysUser.getId()){
			sysSave(userAccess,request);
			if(userAccess != null && userAccess.getAccessId() != null && userAccess.getBeAccessedId() != null){
				userAccessService.add(userAccess);
			}
		}
		
		try {
			sysUser = sysUserService.get(id);
			if( sysUser == null ){
				return new ResponseEnvelope<SysUser>(false, "没有找到用户!",msgId);
			}
            //当前登录人是否已经关注过该用户
//            LoginUser loginUser = new LoginUser();
//            if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
//                loginUser.setId(-1);
//                loginUser.setLoginName("nologin");
//            } else {
//                loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
//            }
            SysUserFansSearch sysUserFansSearch = new SysUserFansSearch();
            sysUserFansSearch.setUserId(id);
            sysUserFansSearch.setFansUserId(loginUser.getId());
            Integer count = sysUserFansService.getCount(sysUserFansSearch);
            if( count > 0 ){
                sysUser.setAttentionState(1);
            }else{
                sysUser.setAttentionState(0);
            }
			//查询所在地
			BaseArea baseArea = new BaseArea();
			baseArea.setAreaCode(sysUser.getAreaId()+"");
 			List<BaseArea> list = baseAreaService.getList(baseArea);
			String areaName = "";
            String pid = "";
			if( list != null && list.size() > 0 ){
				baseArea = list.get(0);
				areaName = baseArea.getAreaName();
                pid = baseArea.getPid();
				baseArea = new BaseArea();
				baseArea.setAreaCode(pid);
				list = baseAreaService.getList(baseArea);
				if( list != null && list.size() > 0 ){
					baseArea = list.get(0);
					areaName = baseArea.getAreaName() + areaName;
				}
				sysUser.setAreaName(areaName);
			}

			ResPic resPic = resPicService.get(sysUser.getPicId());
			sysUser.setPicPath(resPic==null?"":resPic.getPicPath());
			sysUser.setFansCount(fansCount(sysUser.getId()));
			sysUser.setAttentionCount(attentionCount(sysUser.getId()));
			sysUser.setWorksCount(worksCount(sysUser.getId()));
			sysUser.setAccessCount(accessCount(sysUser.getId()));
			String speciality  = sysUser.getSpecialityValue();
			StringBuffer specialityName = new StringBuffer();
			if(StringUtils.isNotBlank(speciality)){
				if(speciality.contains(",")){
					String str[] = speciality.split(",");
					for(String value:str){
						SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("designstyles",Integer.parseInt(value));
						specialityName.append(dictionary==null?"":dictionary.getName()+",");
					}
					String name = specialityName.toString();
					sysUser.setSpecialityName(name.substring(0,name.length()-1));
				}else{
					SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("designstyles",Integer.parseInt(speciality));
					specialityName.append(dictionary==null?"":dictionary.getName());
					sysUser.setSpecialityName(specialityName.toString());
				}
			}

			//岗位
			SysDictionary sysDic = sysDictionaryService.getSysDictionaryByValue("userType", StringUtils.isBlank(sysUser.getJob()) ? -1 : Integer.valueOf(sysUser.getJob()));
			if(sysDic!=null){
				sysUser.setJobName(sysDic.getName());
			}
			if("small".equals(style) && sysUser != null ){
				 String sysUserJson = JsonUtil.getBeanToJsonData(sysUser);
				 SysUserSmall sysUserSmall= new JsonDataServiceImpl<SysUserSmall>().getJsonToBean(sysUserJson, SysUserSmall.class);

				 return new ResponseEnvelope<SysUserSmall>(sysUserSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<SysUser>(sysUser,msgId,true);
	}

   /**
	 * 删除用户,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("sysUser") SysUser sysUser
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUser = (SysUser)JsonUtil.getJsonToBean(jsonStr, SysUser.class);
			if(sysUser == null){
			   return new ResponseEnvelope<SysUser>(false, "传参异常!","none");
			}
			ids =  sysUser.getIds();
			msgId = sysUser.getMsgId() ;
		}else{
			ids =  sysUser.getIds();
			msgId = sysUser.getMsgId() ;
		}

		if (ids == null) {
		    return new ResponseEnvelope<SysUser>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = sysUserService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = sysUserService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}

			if( i == 0){
				return new ResponseEnvelope<SysUser>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<SysUser>(true,msgId,true);
	}

	/**
	 * 用户列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("sysUserSearch") SysUserSearch sysUserSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
		if( sysUserSearch.getLimit() == null ) {
			sysUserSearch.setLimit(new Integer(20));
		}

 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserSearch = (SysUserSearch)JsonUtil.getJsonToBean(jsonStr, SysUserSearch.class);
			if(sysUserSearch == null){
			   return new ResponseEnvelope<SysUser>(false, "传参异常!","none");
			}
		}

		List<SysUser> list = new ArrayList<SysUser> ();
		int total = 0;
		try {
			total = sysUserService.getCount(sysUserSearch);
            logger.info("total:" + total);

			if (total > 0) {
				list = sysUserService.getPaginatedList(
						sysUserSearch);
			}

			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysUserJsonList = JsonUtil.getListToJsonData(list);
				 List<SysUserSmall> smallList= new JsonDataServiceImpl<SysUserSmall>().getJsonToBeanList(sysUserJsonList, SysUserSmall.class);
				 return new ResponseEnvelope<SysUserSmall>(total,smallList,sysUserSearch.getMsgId());
			 }

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!",sysUserSearch.getMsgId());
		}
		return new ResponseEnvelope<SysUser>(total, list,sysUserSearch.getMsgId());
	}


   /**
	 * 用户全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("sysUserSearch") SysUserSearch sysUserSearch,
	            @RequestParam(value = "msgId",required = false)String msgId,
	            HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserSearch = (SysUserSearch)JsonUtil.getJsonToBean(jsonStr, SysUserSearch.class);
			if(sysUserSearch == null){
			   return new ResponseEnvelope<SysUser>(false, "传参异常!","none");
			}
		}
		
		String msg = ""; 
		if( StringUtils.isEmpty(msgId) ){
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<SysUser>(false,msg,msgId);
		}
    	List<SysUser> list = new ArrayList<SysUser> ();
		int total = 0;
		try {
			sysUserSearch.setUserType(2);
			total = sysUserService.getCount(sysUserSearch);
            logger.info("total:" + total);

			if (total > 0) {
				list = sysUserService.getPaginatedList(sysUserSearch);
			}

			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysUserJsonList = JsonUtil.getListToJsonData(list);
				 List<SysUserSmall> smallList= new JsonDataServiceImpl<SysUserSmall>().getJsonToBeanList(sysUserJsonList, SysUserSmall.class);
				 return new ResponseEnvelope<SysUserSmall>(total,smallList,sysUserSearch.getMsgId());
			 }

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!",sysUserSearch.getMsgId());
		}
		return new ResponseEnvelope<SysUser>(total, list,sysUserSearch.getMsgId());
	}

    /**
	 *获取 用户详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		SysUser sysUser = null;
		try {
			sysUser = sysUserService.get(id);
		    String areaLongCode = sysUser.getAreaLongCode();
		    if(areaLongCode != null && !"".equals(areaLongCode)){
		    	String [] str = areaLongCode.split("\\.");
		    	if(str != null && str.length > 0 && str.length <=2) {
		    		request.setAttribute("proId", str[1]);
		    	}else if(str != null && str.length > 2 && str.length <=3){
		    		request.setAttribute("proId", str[1]);
		    		request.setAttribute("cityId", str[2]);
		    	}else if(str != null && str.length > 3 && str.length <=4){
		    		request.setAttribute("proId", str[1]);
		    		request.setAttribute("cityId", str[2]);
		    		request.setAttribute("areaId", str[3]);
		    	}
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!");
		}
		ResponseEnvelope<SysUser> res = new ResponseEnvelope<SysUser>(sysUser);
		request.setAttribute("res", res);
		String url ="";
		String type = (String)request.getParameter("pageType");
		/*绑定brandNames*/
		String brandNames="";
		if(StringUtils.isNotBlank(sysUser.getBrandIds())){
			if(sysUser.getBrandIds().indexOf(",")!=-1){
				String[] strs=sysUser.getBrandIds().split(",");
				for(String str:strs){
					if(StringUtils.isNotBlank(str)){
						BaseBrand baseBrand=baseBrandService.get(Integer.parseInt(str));
						if(baseBrand!=null){
							brandNames+=baseBrand.getBrandName()+",";
						}
					}
				}
			}else{
				String str=sysUser.getBrandIds();
				if(StringUtils.isNotBlank(str)){
					BaseBrand baseBrand=baseBrandService.get(Integer.parseInt(str));
					if(baseBrand!=null){
						brandNames+=baseBrand.getBrandName()+",";
					}
				}
			}
		}
		/*绑定sysUserTypeName*/
		String sysUserTypeName="";
		if(sysUser.getUserType()!=null){
			SysDictionary sysDictionary=sysDictionaryService.getSysDictionaryByValue("userType", sysUser.getUserType());
			sysUserTypeName=sysDictionary.getName();
		}
		request.setAttribute("sysUserTypeName", sysUserTypeName);
		if(StringUtils.isNotBlank(brandNames)){
			brandNames=brandNames.substring(0, brandNames.length()-1);
		}
		request.setAttribute("brandNames", brandNames);
		/*绑定groupName*/
		String groupName="";
		if(sysUser.getGroupId()!=null&&sysUser.getGroupId()>0){
			SysGroup sysGroup=sysGroupService.get(sysUser.getGroupId());
			groupName=sysGroup.getOrgName();
		}
		request.setAttribute("groupName", groupName);
		if("edit".equals(type)){
			SysGroup sysGroup= sysGroupService.get(sysUser.getGroupId());
			request.setAttribute("groupName", sysGroup==null?"":sysGroup.getOrgName());
			url = JSPMAIN + "/sysUser_edit";
		}else{
			url = JSPMAIN + "/sysUser_view";
		}
		return  Utils.getPageUrl(request, url);
	}

	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editPwd")
	public void editPwd(HttpServletRequest request,HttpServletResponse response){
		String message = "0";
		try {
			String oldPwd = request.getParameter("oldPwd");
			String newPwd = request.getParameter("newPwd");
			String newPwd2 = request.getParameter("newPwd2");
			//得到登录用户
			LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			SysUser sysUser = sysUserService.get(loginUser.getId());
			if( sysUser.getPassword().equals(oldPwd) ){
				if( oldPwd.equals(newPwd) ){
					message = "新密码不能与老密码相同";
				}else if( newPwd.equals(newPwd2) ){
					sysUser.setPassword(newPwd);
					sysUserService.update(sysUser);
				}
			}else{
				message = "密码输入不正确";
			}
			response.getWriter().print(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(SysUser model,HttpServletRequest request){
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
	 * 查询设计师列表
	 * @param sysUserSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getDesignersList")
	@ResponseBody
	public Object getDesignersList(@ModelAttribute(value = "sysUserSearch")SysUserSearch sysUserSearch,String msgId,HttpServletRequest request, HttpServletResponse response) {
		List<SysUser> list  = new ArrayList<SysUser>();
		int total = 0 ;
		try {
			sysUserSearch.setUserType(2);
			total = sysUserService.getCount(sysUserSearch);
			logger.info("total=" +total);
			if(total > 0){
				list = sysUserService.getPaginatedList(sysUserSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!");
		}

		return new ResponseEnvelope<SysUser>(total,list,msgId);
	}

	/**
	 * 审核用户
	 * @param auditUsers
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/auditUser")
	@ResponseBody
	public Object auditUser(String userIds,HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		LoginUser loginUser = new LoginUser();
		 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
			loginUser.setLoginName("nologin");
		 }else{
		    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		 }
		 try {
			if (userIds != null ) {
			   if(userIds.contains(",")){
					String[] strId = userIds.split(",");
					for (String id : strId) {
						SysUser user = sysUserService.get(Utils.getIntValue(id));
						user.setIdcardVerifyState(1);
						user.setVerifyUser(loginUser.getLoginName());
						user.setVerifyDate(new Date());
						sysUserService.update(user);
					}
			   }else{
				   SysUser user = sysUserService.get(Utils.getIntValue(userIds));
					user.setIdcardVerifyState(1);
					user.setVerifyUser(loginUser.getLoginName());
					user.setVerifyDate(new Date());
					sysUserService.update(user);
			   }
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.accumulate("success",true);
			jsonObject.accumulate("message","审核失败！");
			return jsonObject;
		}

		 jsonObject.accumulate("success",true);
		 jsonObject.accumulate("message","审核成功！");
		return jsonObject;
	}

    @RequestMapping(value = "/checkOldMobile")
	public Object checkOldMobile(Integer id,String oldMobile,HttpServletRequest request,HttpServletResponse response){
		String flag = "false";
		try{
            SysUser user =  sysUserService.get(id);
            if(user!= null && StringUtils.isNotBlank(oldMobile) && oldMobile.equals(user.getMobile())){
                flag = "true";
                response.getWriter().write(flag);
            }

			response.getWriter().print(flag);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
    



/**
 * 用户中心--热门设计师列表
 * @param sysUserSearch
 * @param request
 * @param response
 * @return
 */
@RequestMapping(value = "/topDesignerList")
@ResponseBody
public Object topDesignerList(@PathVariable String style,
		@ModelAttribute("sysUserSearch") SysUserSearch sysUserSearch,HttpServletRequest request, HttpServletResponse response) {
	String jsonStr = Utils.getJsonStr(request);
	if (jsonStr != null && jsonStr.trim().length() > 0) {
		sysUserSearch = (SysUserSearch) JsonUtil.getJsonToBean(jsonStr, SysUserSearch.class);
		if (sysUserSearch.getId() == null) {
			return new ResponseEnvelope<SysUserSearch>(false, "传参异常!", "none");
		}
	} 
	String msg = "";
	if( StringUtils.isBlank(sysUserSearch.getMsgId()) ){
		msg = "参数msgId不能为空";
		return new ResponseEnvelope<SysUserSearch>(false,msg,sysUserSearch.getMsgId());
	} 
	
	List<SysUser> list  = new ArrayList<SysUser>();
	int total = 0 ;
	//省市查询条件 给areaLongCode赋值
	StringBuffer areaCode = new StringBuffer();
	String provinceCode = sysUserSearch.getProvinceCode();
	String cityCode = sysUserSearch.getCityCode();
	if(StringUtils.isNotBlank(provinceCode)){
		areaCode.append("."+provinceCode);
	}
	if(StringUtils.isNotBlank(cityCode)){
		areaCode.append("."+cityCode);
	}
	if(StringUtils.isNotBlank(areaCode.toString())){
		areaCode.append(".");
	}
	sysUserSearch.setAreaLongCode(areaCode.toString());
	try {
		sysUserSearch.setUserType(2);
		total = sysUserService.getCount(sysUserSearch); 
		logger.info("total=" +total);
		if(total > 0){
			list = sysUserService.getPaginatedList(sysUserSearch);
			for(SysUser user : list){
				user.setFansCount(fansCount(user.getId()));
				user.setAttentionCount(attentionCount(user.getId()));
				user.setWorksCount(worksCount(user.getId()));
				user.setAccessCount(accessCount(user.getId()));
				//获取设计师头像路径
				if(user.getPicId()!=null && user.getPicId()>0){
					ResPic resPic = resPicService.get(user.getPicId());
					user.setPicPath(resPic==null?"":resPic.getPicPath());
				}
				//获取设计师 地址
				String areaLongCode = user.getAreaLongCode();
				StringBuffer areaName = new StringBuffer();
				if(StringUtils.isNotBlank(areaLongCode)){
					if(areaLongCode.contains(".")){
						String area[] = areaLongCode.split("\\.");
						for(String code : area){
							BaseArea baseArea = new BaseArea();
							baseArea.setAreaCode(code);
							List<BaseArea> areaList = baseAreaService.getList(baseArea);
							if(CustomerListUtils.isNotEmpty(areaList)){
								areaName.append(areaList.get(0).getAreaName());
							}
						}
					}
				}else{
					areaName.append("未知");
				}
				user.setAreaName(areaName.toString());
			}
		}
	}  catch (Exception e) {
		e.printStackTrace();
		return new ResponseEnvelope<SysUser>(false, "数据异常!",sysUserSearch.getMsgId());
	}
	return new ResponseEnvelope<SysUser>(total, list,sysUserSearch.getMsgId());
  }


//获取粉丝数量
	public int fansCount(Integer userId){
		SysUserFans userFans = new SysUserFans();
		userFans.setUserId(userId);
		userFans.setIsDeleted(0);
		int fansCount = sysUserFansService.getFansCount(userFans);
		return fansCount ;
	}
	//获取关注数量
	public int attentionCount(Integer userId){
		SysUserFans userFans = new SysUserFans();
		userFans.setFansUserId(userId);
		userFans.setIsDeleted(0);
		int attentionCount = sysUserFansService.getFansCount(userFans);
		return attentionCount;
	}
	//获取作品数量
	public int worksCount(Integer userId){
		 DesignerWorksSearch designerWorksSearch = new DesignerWorksSearch();
		 designerWorksSearch.setUserId(userId);
		 int worksCount = designerWorksService.getCount(designerWorksSearch);
		 return worksCount;
	}
	//获取访问数量
	public int accessCount(Integer userId){
		UserAccessSearch userAccessSearch = new UserAccessSearch();
		userAccessSearch.setBeAccessedId(userId);
		return userAccessService.getCount(userAccessSearch);
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResPic model,HttpServletRequest request){
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
	
	@RequestMapping(value = "/brands")
	public Object brands(
			@ModelAttribute("sysUser") SysUser sysUser,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("brandIds", sysUser.getBrandIds());
		return Utils.getPageUrl(request, JSPMAIN + "/sysUser_brands");
	}
	
	/**
	 * 后台下拉显示用户
	 */
	@RequestMapping(value = "/getUserList")
	@ResponseBody
	public Object getUserList(
	            @PathVariable String style,@ModelAttribute("sysUser") SysUser sysUser
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
    	List<SysUser> list = new ArrayList<SysUser> ();
		try {
			 list = sysUserService.getList(sysUser);
			 /*排序(按首字母拼音)*/
			final Collator collator = Collator.getInstance(java.util.Locale.CHINA);
			Collections.sort(list, new Comparator<SysUser>() {
				@Override
				public int compare(SysUser o1, SysUser o2) {
					CollationKey key1=collator.getCollationKey(o1.getUserName());
					CollationKey key2=collator.getCollationKey(o2.getUserName());
					return key1.compareTo(key2);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!",sysUser.getMsgId());
		}
		return new ResponseEnvelope<SysUser>(list.size(), list,sysUser.getMsgId());
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UserAccess model,HttpServletRequest request){
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
	
	/**得到修改gmt_create不自动更新时间的sql*/
	@RequestMapping("/getSql")
	@ResponseBody
	public String getSql(){
		String sql="";
		List<String> tableNames=sysUserService.getTableNames();
		if(tableNames!=null&&tableNames.size()>0){
			for(String tableName:tableNames){
				sql+="alter table "+tableName+" modify gmt_create TIMESTAMP not null default CURRENT_TIMESTAMP;\n";
			}
		}
		////////System.out.println(sql);
		return sql;
	}
	
	/**
	 * 用户产品配置选择用户文本框显示用户名称
	 * @param ids
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getUserName")
	@ResponseBody
	public String getUserName(
			String ids,HttpServletRequest request, HttpServletResponse response){
		
		String userName="";
		if(StringUtils.isNotBlank(ids)){
			StringBuffer sb = new StringBuffer();
			String array[] = ids.split(",");
			for(String userId : array){
				SysUser user = sysUserService.get(Utils.getIntValue(userId));
				sb.append(user.getUserName()).append(",");
			}
			if(StringUtils.isNotBlank(sb.toString())){
				userName = sb.toString().substring(0, sb.toString().length()-1);
			}
		}
		return userName;
	}
	
}