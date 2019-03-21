package com.nork.mobile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sandu.common.LoginContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.cache.utils.JedisUserUtils;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.design.model.DesignPlanRecommendFavoriteRef;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.model.Favorite;
import com.nork.design.model.FavoriteRecommendedModel;
import com.nork.design.service.DesignPlanRecommendFavoriteService;
import com.nork.mobile.model.search.DesignPlanRecommendedModel;
import com.nork.product.dao.CompanyFranchiserGroupMapper;
import com.nork.product.model.CompanyFranchiserGroup;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;

/**
 * 移动端关于收藏功能的controller
 * @author yangzhun
 */
@Controller
@RequestMapping("/{style}/mobile/designPlanCollect")
public class MobileDesignPlanCollectController {

    private static Logger logger = Logger.getLogger(MobileDesignPlanCollectController.class);

	@Autowired
    private DesignPlanRecommendFavoriteService designPlanRecommendFavoriteService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private CompanyFranchiserGroupMapper companyFranchiserGroupMapper;

	 /**
     *getFavoritesList 方法描述：列表展示用户个人创建的收藏夹
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/getFavoritesList")
    public ResponseEnvelope getFavoritesList(@RequestBody FavoriteRecommendedModel model, HttpServletRequest request) {
        ResponseEnvelope envelope = new ResponseEnvelope();
        String msgId = model.getMsgId();
        //获取登录用户信息

//		HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("AuthorizationData");
//		String appKey = map.get("appKey");
//		String cacheKey = "user_H5Token:" + appKey;
//		LoginUser loginUser = (LoginUser) JedisUtils2.getObject(cacheKey);
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }

        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        int userId = loginUser.getId();

        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        List listSearch = designPlanRecommendFavoriteService.listFavorites(favorite);
        if(listSearch == null || listSearch.size()<=0){
        	Favorite favoriteAdd = new Favorite();
        	favoriteAdd.setName("默认");
        	favoriteAdd.setUserId(userId);
            designPlanRecommendFavoriteService.addFavorite(favoriteAdd);
        }
        List<Favorite> list = designPlanRecommendFavoriteService.listFavorites(favorite);
        if(list == null || list.size()<=0){
        	 envelope.setDatalist(new ArrayList<>());
        	 envelope.setTotalCount(0);
        	 return envelope;
        }else{
        	Favorite defaultFavorite = null;
        	 
        	for (int i = 0; i < list.size(); i++) {
				if("默认".equals(list.get(i).getName().trim())){
					defaultFavorite = list.get(i);
					list.remove(i);
				}
			}
        	if(defaultFavorite!=null){
        		list.add(0, defaultFavorite);
        	}
        	envelope.setDatalist(list);
        	envelope.setTotalCount(list.size());
        	return envelope;
        }
    }
   
	
	/**
	 * add 将一个推荐方案  加入  收藏夹
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/addDesingPlanCollect")
	@ResponseBody
	public ResponseEnvelope addDesingPlanCollect(@RequestBody DesignPlanRecommendedModel model, HttpServletRequest request) {
        ResponseEnvelope envelope = new ResponseEnvelope();
        String msgId = model.getMsgId();
        Integer recommendId = model.getPlanRecommendedId();// 推荐方案的id
        String favoriate_businessId = model.getFid();// 收藏夹的业务id
        //获取登录用户

//        @SuppressWarnings("unchecked")
//		HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("AuthorizationData");
//		String appKey = map.get("appKey");
//		String cacheKey = "user_H5Token:" + appKey;
//		LoginUser loginUser = (LoginUser) JedisUtils2.getObject(cacheKey);
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId) || StringUtils.isEmpty(favoriate_businessId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        
        DesignPlanRecommendFavoriteRef favoriteRef = new DesignPlanRecommendFavoriteRef();
        favoriteRef.setFid(favoriate_businessId);
        favoriteRef.setRecommendId(recommendId.intValue());
        favoriteRef.setStatus(1);

        int count = designPlanRecommendFavoriteService.existInFavorite(favoriteRef);
        if (count > 0) { 
        	 envelope.setSuccess(false);
             envelope.setMessage("请勿重复收藏");
             return envelope;
        }
        boolean res = designPlanRecommendFavoriteService.moveInFavorite(favoriteRef);
        envelope.setSuccess(res);
//        if (res) {
//        	designPlanRecommendFavoriteService.get
//        	envelope.setMessage("");
//        }else {
//        	envelope.setMessage("params error");
//        }
        return envelope;
    }
	
	/**
     * del 把设计方案移除个人收藏夹    
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.POST,value = "/delDesingPlanCollect")
    @ResponseBody
    public ResponseEnvelope del(@RequestBody DesignPlanRecommendedModel model, HttpServletRequest request){
        ResponseEnvelope envelope = new ResponseEnvelope();
        String msgId = model.getMsgId();
        String recommendRef_busyneesId = model.getFid();// 推荐方案的业务id
        //获取登录用户

//        @SuppressWarnings("unchecked")
//		HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("AuthorizationData");
//		String appKey = map.get("appKey");
//		String cacheKey = "user_H5Token:" + appKey;
//		LoginUser loginUser = (LoginUser) JedisUtils2.getObject(cacheKey);
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId) || StringUtils.isEmpty(recommendRef_busyneesId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }

        DesignPlanRecommendFavoriteRef favoriteRef = new DesignPlanRecommendFavoriteRef();
        favoriteRef.setBid(recommendRef_busyneesId);
        boolean res = designPlanRecommendFavoriteService.moveOutFavorite(favoriteRef);
        envelope.setSuccess(res);
        if (!res)
            envelope.setMessage("params error");

        return envelope;
    }
	
	
	/**
	 * get获取个人收藏夹量的设计方案id 同时支持针对 收藏夹里面显示的名称搜索 或者 通过搜索夹的业务id获取指定设计方案计划
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getCollectList")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommendedResult> favoritePlanRecommendedList(
			@RequestBody FavoriteRecommendedModel model, HttpServletRequest request){
    	String msgId = model.getMsgId();
    	//主账号ID
    	Integer franchiserId = null;
    	//经销商账号类型
    	Integer franchiserAccountType  = null;
		//获取登录用户信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		boolean valid = this.checkParam(msgId);
		if(!valid) {
			return new ResponseEnvelope<>(false,"参数不完整",msgId);
		}
		if(loginUser == null || loginUser.getId() <= 0 ){
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"请重新登录！",msgId);
		}
		Integer userId = loginUser.getId();
		SysUser sysUser = sysUserService.get(userId);
		Integer userType = sysUser.getUserType();
		model.setUserId(userId);
		model.setUserType(userType);
		Integer franchiseGroupId = sysUser.getFranchiserGroupId();
		logger.error("favoritePlanRecommendedList    userId= "+userId+"     franchiseGroupId=" + franchiseGroupId);
		//1为经销商 子账号 ,0为其他账号
		Integer type = 0;
		if (userType.intValue() == 3  && franchiseGroupId.intValue() > 0) {
        	CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupMapper.selectByPrimaryKey(franchiseGroupId);
        	franchiserId = companyFranchiserGroup.getFranchiserId();
        	franchiserAccountType = sysUser.getFranchiserAccountType();
        	if (userType.intValue() == 3 && franchiserAccountType.intValue() == 2) {
        		type = 1;
        	}
		}
		model.setFranchiserId(franchiserId);
		return designPlanRecommendFavoriteService.favoritePlanRecommendedList(model,type);
    }
	
	/**
	 * 参数完整性判断
	 * @param args
	 * @return
	 */
	private boolean checkParam(String... args) {
		boolean result = true;
		for(String arg :args) {
			if(StringUtils.isEmpty(arg)) {
				result = false;
				return result;
			}
		}
		return result;
	}

    /**
     *getHasCollected 判断方案是否被收藏
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/getDesignPlanHasBeCollected")
    public ResponseEnvelope getHasCollected(@RequestBody DesignPlanRecommendFavoriteRef ref) {
        String bid = designPlanRecommendFavoriteService.getHasCollected(ref);
        boolean success = true;
        Object obj = bid;
        if(bid == null){
            success = false;
            obj = "-1";
        }
        return new ResponseEnvelope(success, obj);
    }
}
