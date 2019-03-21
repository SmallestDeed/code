package com.sandu.web.user.controller;

import com.nork.common.model.LoginUser;
import com.sandu.base.service.BaseAreaService;
import com.sandu.common.LoginContext;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.LoginUserCommonConstant;
import com.sandu.common.util.collections.Lists;
import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.vo.UserHouseAccountsVo;
import com.sandu.home.service.HouseSpaceNewService;
import com.sandu.system.model.ResHousePic;
import com.sandu.system.service.ResHousePicService;
import com.sandu.user.model.UserSO;
import com.sandu.user.service.UserFinanceService;
import com.sandu.user.service.UserSessionService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @version V1.0
 * @Description:系统-用户资源模块
 * @createAuthor weisheng
 * @CreateDate 2017-11-25 12:30:46
 */
@RestController
@RequestMapping("/v1/miniprogram/userresources")
public class UserResourcesConroller {
	
	@Autowired
	private UserSessionService userSessionService;
	@Autowired
	private UserFinanceService userFinanceService;
	
	
	@Autowired
	private ResHousePicService resHousePicService;
	
	@Autowired
	private HouseSpaceNewService houseSpaceNewService;
	@Autowired
	private BaseAreaService baseAreaService;
    /**
     * 
     *
     * 查询用户使用的户型
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/finduseraccounts")
    @ResponseBody
    public ResponseEnvelope findUserAccounts(@ModelAttribute PageModel pageModel,Integer isSort,HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		if(loginUser==null){
			return new ResponseEnvelope(false, "请先登录");
		}
		//查询用户的总共户型套数
		Integer userAlreadyBoughtHouseCount = userFinanceService.queryUserAlreadyBoughtHouseCount(loginUser.getId());
		//查询用户已使用户型套数
		Integer userUsedHouseCount = userFinanceService.queryUserUsedHouseCount(loginUser.getId());
		//查询用户可使用户型套数
		Integer userValidHouseCount = userAlreadyBoughtHouseCount-userUsedHouseCount;
		BaseHouse queryBaseHouse = new BaseHouse();
		queryBaseHouse.setUserId(loginUser.getId());
        if (null != pageModel && 0 != pageModel.getPageSize()) {
        	queryBaseHouse.setStart(pageModel.getStart());
        	queryBaseHouse.setLimit(pageModel.getPageSize());
        } else {
        	queryBaseHouse.setLimit(PageModel.DEFAULT_PAGE_PAGESIZE);
        }
        if(null!=isSort) {
        	queryBaseHouse.setIsSort(isSort);
        }
		//查询用户已使用户型的详细信息
		List<BaseHouse> houselist = userFinanceService.queryUserUsedHouseDetailList(queryBaseHouse);
		if(houselist!=null) {
			for (BaseHouse baseHouse : houselist) {
				 // 取户型的缩略图和大图
//	            if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() != 0) {
//	                ResHousePic resHousePic = resHousePicService.get(baseHouse.getPicRes1Id());
//	                if (null != resHousePic) {
//	                    baseHouse.setThumbnailPath(resHousePic.getThumbnailPath());
//	                    baseHouse.setLargeThumbnailPath(resHousePic.getLargeThumbnailPath());
//	                }
//	            }

				// 取户型的缩略图和大图,优先户型绘制的图
				if (baseHouse.getSnapPicId() != null && baseHouse.getSnapPicId() > 0) {
					// 截图图片处理 v3.9.4(draw-v1.0.2)
					ResHousePic resHousePic = resHousePicService.get(baseHouse.getSnapPicId());
					baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
					baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
				} else if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() > 0) {
					ResHousePic resHousePic = resHousePicService.get(baseHouse.getPicRes1Id());
					baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getThumbnailPath() : "");
					baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getLargeThumbnailPath() : "");
				} else {
					baseHouse.setThumbnailPath("/default/huxing_bg_pic_no.png");
					baseHouse.setLargeThumbnailPath("/default/huxing_bg_pic_no.png");
				}

	            String longCodeName ="";
	            //查询小区的地址
	            if(StringUtils.isNotBlank(baseHouse.getAreaLongCode())) {
	            	String longCode = baseHouse.getAreaLongCode();
	            	String str = longCode.substring(1, longCode.length()-1);
	            	String[] split = str.split("\\.");
	                List<String> list = new ArrayList<String>();    	            
	            	for (String code : split) {
	            	 if(!list.contains(code)) {
	            		 list.add(code);
	            		 String codeName = baseAreaService.getCodeName(code);
	            		 longCodeName += codeName;
	            	 }
					}	            	
	            }
	            baseHouse.setHouseAddress(longCodeName);
	            
	            
	            
	            	            
	            // 取户型的空间定位类型
	            List<String> spaceTypeList = houseSpaceNewService.getSpaceTypeListByHouseId(baseHouse.getId());
	            if (Lists.isEmpty(spaceTypeList)) {
	                continue;
	            }
	            Map<String, Integer> elementsCount = new HashMap<String, Integer>();
	            for (String s : spaceTypeList) {
	                Integer i = elementsCount.get(s);
	                if (i == null) {
	                    elementsCount.put(s, 1);
	                } else {
	                    elementsCount.put(s, i + 1);
	                }
	            }
	            baseHouse.setHouseTypeStr(((elementsCount.containsKey("3") ? elementsCount.get("3") : 0) + "厅"
	                    + (elementsCount.containsKey("4") ? elementsCount.get("4") : 0)) + "室"
	                    + (elementsCount.containsKey("6") ? elementsCount.get("6") : 0) + "卫"
	                    + (elementsCount.containsKey("5") ? elementsCount.get("5") : 0) + "厨"
	                    + (elementsCount.containsKey("7") ? elementsCount.get("7") : 0) + "书"
	                    + (elementsCount.containsKey("8") ? elementsCount.get("8") : 0) + "衣"
	                    + (elementsCount.containsKey("9") ? elementsCount.get("9") : 0) + "其他");

	        }
		}
		
		UserHouseAccountsVo userHouseAccountsVo = new UserHouseAccountsVo();
		userHouseAccountsVo.setUserAlreadyBoughtHouseCount(userAlreadyBoughtHouseCount);
		userHouseAccountsVo.setUserUsedHouseCount(userUsedHouseCount);
		userHouseAccountsVo.setUserValidHouseCount(userValidHouseCount);
		userHouseAccountsVo.setHouselist(houselist);
		
    	return new ResponseEnvelope(true, "success", userHouseAccountsVo,null==userUsedHouseCount?0:userUsedHouseCount);
    }
}
