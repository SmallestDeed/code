package com.nork.design.service.impl;

import java.util.*;

import javax.websocket.ClientEndpoint;

import com.nork.design.model.*;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.aes.util.FileEncrypt;
import com.nork.base.model.BasePlatform;
import com.nork.base.service.BasePlatformService;
import com.nork.common.constant.RoleConstant;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.StringUtils;
import com.nork.common.util.collections.Lists;
import com.nork.design.dao.DesignPlanRenderSceneMapper;
import com.nork.design.service.DesignPlanProductRenderSceneService;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.design.service.DesignTempletService;
import com.nork.product.common.PlatformConstants;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseBrand;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.dao.SysUserGroupMapper;
import com.nork.system.model.ResDesignRenderScene;
import com.nork.system.model.ResHousePic;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysUserGroup;
import com.nork.system.model.search.ResRenderPicSearch;
import com.nork.system.service.ResDesignRenderSceneService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;



/**
 * @Title: DesignPlanServiceImpl.java 
 * @Package com.nork.design.service.impl
 * @Description:设计模块-设计方案ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-07-03 17:09:51
 * @version V1.0   
 */
@Service("designPlanRenderSceneService")
@ClientEndpoint
public class DesignPlanRenderSceneServiceImpl implements DesignPlanRenderSceneService {

	Logger logger = LoggerFactory.getLogger(DesignPlanRenderSceneServiceImpl.class);
	
	@Autowired
	private DesignPlanRenderSceneMapper designPlanRenderSceneMapper;
	
	@Autowired
	private SysUserGroupMapper sysUserGroupMapper;
	
	@Autowired
	private ResModelService resModelService;
	
	@Autowired
	private ResDesignRenderSceneService resDesignRenderSceneService;
	
	@Autowired
	private DesignPlanProductRenderSceneService designPlanProductRenderSceneService;
	
	@Autowired
	private ResRenderPicService resRenderPicService;
	
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	
	@Autowired
	private ResPicService resPicService;
	
	@Autowired
	private BaseBrandService baseBrandService;
	
	@Autowired
	private DesignTempletService designTempletService;
	
	@Autowired
	private BasePlatformService basePlatformService;
	
	@Override
	public int add(DesignPlanRenderScene designPlanEctype) {
	    if(designPlanEctype.getPlatformId() == null) {
	        //添加平台标识
	        BasePlatform basePlatform = basePlatformService.findOneByPlatformCode(PlatformConstants.PC_2B);//平台信息
	        if(basePlatform == null) {
	          logger.error("缺失PC端平台信息！"); 
	        }else {
	          designPlanEctype.setPlatformId(basePlatform.getId());
	        }
	    }
		designPlanRenderSceneMapper.insert(designPlanEctype);
		return designPlanEctype.getId();
	}

	@Override
	public void update(DesignPlanRenderScene designPlanEctype) {
		if(designPlanEctype.getId() == null){
			return;
		}
		designPlanRenderSceneMapper.update(designPlanEctype);
	}

	@Override
	public void delete(long id) {
		if(id < 1){
			return;
		}
		designPlanRenderSceneMapper.delete(id);
		
		SysUserGroup sysUserGroup = new SysUserGroup(); 
		sysUserGroup.setSceneId((int)id);
		List<SysUserGroup> list= sysUserGroupMapper.getGidBySceneId(sysUserGroup);//查询该场景存在于分享的组bid
		sysUserGroupMapper.deleteBySceneId(sysUserGroup);//删除分组分享里面的东西
		
		for(SysUserGroup group: list ) {
		    if(StringUtils.isEmpty(group.getBid()))
		        continue;
		    int count = sysUserGroupMapper.countByGid(group);//看看关联组里面有没有东西，没东西的把组也删除
		    if(count>0)
		        continue;
		    sysUserGroupMapper.deleteSysuserGroupByBid(group);
		}
	}

	@Override
	public DesignPlanResRenderScene getDesignPlanResRenderSceneById(Integer designPlanRenderSceneId) {

		DesignPlanResRenderScene designPlanResRenderScene = null;
    	
    	// 获取设计方案信息 ->start
    	DesignPlanRenderScene designPlanRenderScene = this.get(designPlanRenderSceneId);
    	if(designPlanRenderScene == null){
    		logger.error("------designPlanRenderScene is null, designPlanRenderSceneId:" + designPlanRenderSceneId);
    		return null;
    	}
    	// 获取设计方案信息 ->end
    	
    	// 获取模型文件 ->start
    	ResModel resModel = null;
    	if(designPlanRenderScene.getModelId() != null){
    		resModel = resModelService.get(designPlanRenderScene.getModelId());
    	}
    	
    	if(resModel == null){
    		logger.error("------resModel is null, resModelId:" + designPlanRenderScene.getModelId());
    		return null;
    	}
    	// 获取模型文件 ->end
    	
    	// 获取配置文件 ->start
    	ResDesignRenderScene resDesignRenderScene = null;
    	if(designPlanRenderScene.getConfigFileId() != null){
    		resDesignRenderScene = resDesignRenderSceneService.get(designPlanRenderScene.getConfigFileId());
    	}
    	
    	if(resDesignRenderScene == null){
    		logger.error("------resDesign is null, resDesignId:" + designPlanRenderScene.getConfigFileId());
    		return null;
    	}
    	// 获取配置文件 ->end
    	
    	// 获取设计方案产品列表 ->start
    	List<DesignPlanProductRenderScene> designPlanProductRenderSceneList = designPlanProductRenderSceneService.getListByPlanId(designPlanRenderScene.getId());
    	if(designPlanProductRenderSceneList == null || designPlanProductRenderSceneList.size() == 0){
    		logger.error("------designPlanProductRenderSceneList is null or size = 0, designPlanProductRenderId:" + designPlanRenderScene.getId());
    		return null;
    	}
    	// 获取设计方案产品列表 ->end
    	
    	designPlanResRenderScene = new DesignPlanResRenderScene(designPlanRenderScene, resModel, resDesignRenderScene, designPlanProductRenderSceneList);
    	
        return designPlanResRenderScene;
    }

	@Override
	public DesignPlanRenderScene get(Integer id) {
		return designPlanRenderSceneMapper.get(id);
	}
	
	@Override
	public DesignPlanRenderScene getLatelyPlanScenByPlanId(int designPlanId) {
		return  designPlanRenderSceneMapper.getLatelyPlanScenByPlanId(designPlanId);
	}

	@Override
	public Integer getIdByThumbId(long thumbId) {
		if(thumbId < 1){
			logger.error("------function:getRenderBakScene->thumbId < 1;(thumbId = " + thumbId + ")");
			return null;
		}
		
		ResRenderPic resRenderPic = resRenderPicService.get((int)thumbId);
		
		if(resRenderPic == null){
			logger.error("------function:getRenderBakScene->resRenderPic = null;(resRenderPicId = " + thumbId + ")");
			return null;
		}
		
		Long designPlanRenderSceneId = Long.valueOf(resRenderPic.getDesignSceneId());
		
		if(designPlanRenderSceneId == null){
			logger.error("------function:getRenderBakScene->resRenderPic.getDesignSceneId() = null;(resRenderPicId = " + thumbId + ")");
			return null;
		}
		
		return Integer.valueOf(designPlanRenderSceneId + "");
	}

	@Override
	public DesignPlanRenderScene getDesignPlanSceneByThumbId(long thumbId) {
		ResRenderPic resRenderPic = resRenderPicService.get((int)thumbId);
		Integer designPlanRenderSceneId = Integer.valueOf(resRenderPic.getDesignSceneId());
		DesignPlanRenderScene designPlanRenderScene=this.get(designPlanRenderSceneId);
		return designPlanRenderScene;
	}

	@Override
	public void deleteAllDataById(Integer id) {
		
		DesignPlanRenderScene designPlanRenderScene = this.get(id);
		
		if(designPlanRenderScene == null){
			logger.error("------function:deleteAllDataById->designPlanRenderScene = null(id:" + id + ")");
			return;
		}
		
		// 删除配置文件数据及配置文件物理文件 ->start
		Integer resDesignRenderSceneId = designPlanRenderScene.getConfigFileId();
		
		ResDesignRenderScene resDesignRenderScene = null;
		if(resDesignRenderSceneId != null){
			resDesignRenderScene = resDesignRenderSceneService.get(resDesignRenderSceneId);
			if(resDesignRenderScene != null){
				// 删除物理文件
				FileEncrypt.deleteAllFile(resDesignRenderScene.getFilePath());
				// 删除数据
				resDesignRenderSceneService.delete(resDesignRenderSceneId);
			}
		}
		// 删除配置文件数据及配置文件物理文件 ->end
		
		// 删除设计方案产品列表副本 ->start
		// 删除设计方案产品列表副本 ->end
		designPlanProductRenderSceneService.deleteByPlanId(id);
		
		// 删除设计方案副本 ->start
		this.delete(id);
		// 删除设计方案副本 ->end
		
	}

    @Override
    public int getDesignPlanSceneState(Integer designPlanId) {
		return designPlanRenderSceneMapper.getDesignPlanSceneState(designPlanId);
    }

    /**
	 * 通过厂商看到 经销商下 所有用到 该厂商品牌的设计方案
	 * @param msgId
	 * @param brandId
	 * @param planName
	 * @param loginUser
	 * @return
	 */
	@Override
	public ResponseEnvelope<DesignPlanRenderScene> vendorPlanList(String msgId, String brandId, String planName,
			LoginUser loginUser,String limit,String start) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isEmpty(msgId)){
			return new ResponseEnvelope<DesignPlanRenderScene>(false," parame not found!",msgId);
		}
        if (loginUser==null) {
            return new ResponseEnvelope<DesignPlanRenderScene>(false,SystemCommonConstant.PLEASE_LOGIN,msgId);
        }
		//获取 该用户为厂商的所有 授权码数据
        List<Integer>brandIds = new ArrayList<Integer>();
		
       /* List<AuthorizedConfig> vendorList = authorizedConfigService.vendorAuthorizedConfigList(loginUser.getId());
		if(vendorList == null ||vendorList.size()<=0 ){
			return new ResponseEnvelope<DesignPlanRenderScene>(false,"不是厂商，无权限",msgId);
		}
		for (AuthorizedConfig authorizedConfig : vendorList) {
			if(StringUtils.isNotEmpty(authorizedConfig.getBrandIds())){
				brandIds.add(Integer.parseInt(authorizedConfig.getBrandIds()));
			}
		}*/
        
        String beandIdStr = null;
        BaseBrand baseBrand = null;
        if(2==loginUser.getUserType()){//企业用户
        	baseBrand = baseBrandService.selectUserIdByBrandIds(loginUser);
        }
		if(null != baseBrand){
			if(StringUtils.isNotBlank(baseBrand.getBrandStr())){
				beandIdStr = baseBrand.getBrandStr();
				String[] strs = beandIdStr.split(",");
				for(int i=0;i<strs.length;i++){
					brandIds.add(Integer.parseInt(strs[i]));
				}
			}
		}
		
		
		if(brandIds.size()<=0 || beandIdStr == null){
			return new ResponseEnvelope<DesignPlanRenderScene>(false,"没有权限!",msgId);
		}
		//查询该厂商下的经销商 （只要经销商 和 厂商是同品牌，那么全是该厂商的经销商）
		List<Integer>userIds = new ArrayList<Integer>();//厂商下的所有用户id
		
		//查询该公司下所有的经销商下面的用户信息
		List<LoginUser> authorizedList = baseBrandService.selectBrandByUserId(loginUser.getId());
		
		//查询该公司下面所有的经销商信息
		List<BaseBrand> dealerInfo = baseBrandService.selectCompanyUserIdByDealerId(loginUser.getId());
		
		for (LoginUser loginUsers : authorizedList) {
			if(loginUsers.getId()!=null || loginUsers.getId().intValue()>0){
				userIds.add(loginUsers.getId());
			}
		}
		
		//List<AuthorizedConfig> authorizedList = authorizedConfigService.getDealersAuthorizedConfigByBrandId(brandIds);
		if(authorizedList == null ||authorizedList.size()<=0 ){
			return new ResponseEnvelope<DesignPlanRenderScene>(false,"暂无经销商！",msgId);
		}
		if(userIds.size()<=0){
			return new ResponseEnvelope<DesignPlanRenderScene>(false,"数据错误",msgId);
		}
		
		
		 List<BaseBrand> companyByUserId=null;
		 int count = 0;
		 List<DesignPlanRenderScene>list1 =  new  ArrayList<DesignPlanRenderScene>();
		 List<DesignPlanRenderScene>list =  new  ArrayList<DesignPlanRenderScene>();
		 
		 map.put("userId", loginUser.getId());//厂商id
		 if(null != dealerInfo){
				if(dealerInfo.size()>0){
					//循环所有的经销商
					for(BaseBrand info : dealerInfo){
						map.put("dealerId", info.getId());//经销商id
						
						//1.获取每个经销商下的用户信息
						companyByUserId = baseBrandService.selectCompanyByUserId(map);
						
						List<Integer>dealerBrandIds = new ArrayList<Integer>();//经销商的品牌id
						List<Integer>dealerUserIds = new ArrayList<Integer>();//经销商下面的用户id
						 
						
						for(BaseBrand brandList:companyByUserId){
							dealerUserIds.add(brandList.getUserId());//经销商下的用户id
						}
						String beandIdStr1 = null;
						
						for(BaseBrand brandList:companyByUserId){
							beandIdStr1 = brandList.getBrandStr();//经销商所属的品牌Ids
							break;
						}
						
						//2.获取经销商所属的品牌
						if(null != beandIdStr1){
							String[] strs = beandIdStr1.split(",");
							for(int i=0;i<strs.length;i++){
								dealerBrandIds.add(Integer.parseInt(strs[i]));
							}
						}
						
							//如果通过品牌查询，先判断这个品牌 是否被该厂商拥有
							DesignPlanRenderScene designPlanRenderScene  = new DesignPlanRenderScene();
							designPlanRenderScene.setBrandIds(dealerBrandIds);
							designPlanRenderScene.setUserIds(dealerUserIds);
							designPlanRenderScene.setPlanName(planName);
							designPlanRenderScene.setLimit(-1);
					    	designPlanRenderScene.setStart(-1);
							
							if(StringUtils.isNotEmpty(brandId)){
								int i = Integer.parseInt(brandId);
								if(dealerBrandIds.contains(i)){
									List<Integer>brandIdsSearch = new ArrayList<Integer>();
									brandIdsSearch.add(i);
									dealerBrandIds = brandIdsSearch;
									designPlanRenderScene.setBrandIds(dealerBrandIds);
								}else{
									//return new ResponseEnvelope<DesignPlanRenderScene>(0,list, msgId);
									continue;
								}
							}
							//通过产品品牌  和 授权码用户id 查询 设计方案
							count = this.getVendorCount(designPlanRenderScene);
							//count+=count;
							if(count>0){
								list1 =  this.getVendorList(designPlanRenderScene);
								for (DesignPlanRenderScene designPlanRenderScene_ : list1) {
									ResRenderPic render = new ResRenderPic();
									render.setDesignSceneId(designPlanRenderScene_.getId());
									render.setIsDeleted(0);
									render.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY.replace(".upload.path", ""));
									List<ResRenderPic>renderList = resRenderPicService.getList(render);
									if(renderList!=null && renderList.size()>0){
										designPlanRenderScene_.setPicPath(renderList.get(0).getPicPath());
									}
								}
							} 
							list.addAll(list1);
					}
				}
			}
	        // 去重
	        /*list2.stream().forEach(
	                p -> {
	                    if (!list.contains(p)) {
	                    	list.add(p);
	                    }
	                }
	        );*/
		 if(null != list){
			 for(int i=0;i<list.size()-1;i++){
				 for(int j=0;j<list.size()-1-i;j++){
					 if(list.get(i).getId().equals(list.get(j+1).getId())){
						 list.remove(i);
						// count--;
					 }
				 }
			 }
			 count = list.size();
		 }
		return new ResponseEnvelope<DesignPlanRenderScene>(count,list, msgId);
	}
	
 
	/**解析固定格式字符串*/
	private Map<String,String> readFileDesc(String fileDesc){
		Map<String, String> map = new HashMap<String, String>();
		if( StringUtils.isNotBlank(fileDesc) ){
			String[] strs = fileDesc.split(";");
			for (String str : strs) {
				if (str.split(":").length == 2) {
					map.put(str.split(":")[0].trim(), str.split(":")[1].trim());
				}
			}
		}
		return map;
	}
	
	/**
     * 通过产品品牌  和 授权码用户id 查询 设计方案
     * @param brandIds
     * @param userIds
     * @return
     */
	public int getVendorCount(DesignPlanRenderScene designPlanRenderScene) {
		return designPlanRenderSceneMapper.getVendorCount(designPlanRenderScene);
	}
	
    /**
     * 通过产品品牌  和 授权码用户id 查询 设计方案
     * @param brandIds
     * @param userIds
     * @return
     */
	public List<DesignPlanRenderScene> getVendorList(DesignPlanRenderScene designPlanRenderScene) {
		return designPlanRenderSceneMapper.getVendorList(designPlanRenderScene);
	}
	
	
	/**
	 * 得到该厂商的所有品牌
	 * @param msgId
	 * @param loginUser
	 * @return
	 */
	@Override
	public ResponseEnvelope<BaseBrand> manufacturerBrandList(String msgId, LoginUser loginUser) {
		Map<String,Object>map = new HashMap<String,Object>();
		if(StringUtils.isEmpty(msgId)){
			return new ResponseEnvelope<BaseBrand>(false," parame not found!",msgId);
		}
        if (loginUser.getId() < 0) {
            return new ResponseEnvelope<BaseBrand>(false,SystemCommonConstant.PLEASE_LOGIN,msgId);
        }
        List<BaseBrand>list = new ArrayList<BaseBrand>();
		//获取 该用户为厂商的所有 授权码数据
        List<Integer>brandIds = new ArrayList<Integer>();
		/*List<AuthorizedConfig> vendorList = authorizedConfigService.vendorAuthorizedConfigList(loginUser.getId());
		if(vendorList == null ||vendorList.size()<=0 ){
			return new ResponseEnvelope<BaseBrand>(0,list, msgId);
		}
		for (AuthorizedConfig authorizedConfig : vendorList) {
			if(StringUtils.isNotEmpty(authorizedConfig.getBrandIds())){
				brandIds.add(Integer.parseInt(authorizedConfig.getBrandIds()));
			}
		}*/
        BaseBrand baseBrand = null;
        if(2==loginUser.getUserType()){//企业用户
        	baseBrand = baseBrandService.findBrandIdByUserIdBase(loginUser);
        }else if (3==loginUser.getUserType()){
        	baseBrand = baseBrandService.findBrandIdByUserIdAndUserType(loginUser);
        }
		if(null != baseBrand){
			if(StringUtils.isNotBlank(baseBrand.getBrandStr())){
				String beandIds = baseBrand.getBrandStr();
				String[] strs = beandIds.split(",");
				//StringBuffer sb = new StringBuffer();
				for(int i=0;i<strs.length;i++){
					//sb.append(strs[i]);//append String并不拥有该方法，所以借助StringBuffer
					//String sb1 = sb.toString();
					brandIds.add(Integer.parseInt(strs[i]));
				}
			}
		}
		if(brandIds.size()<=0){
			return new ResponseEnvelope<BaseBrand>(false,"没有权限!",msgId);
		}
		list = baseBrandService.getListByIds(brandIds);
		return new ResponseEnvelope<BaseBrand>(list.size(),list, msgId);
	}

	@Override
	public String getSubmitButtonStatus(Integer dprCommonId, Integer dprCapacityId, Set<String> roleSet) {
		// 参数验证 ->start
		if(Lists.isEmpty(roleSet)) {
			return null;
		}
		// 参数验证 ->end
		
		StringBuffer returnStr = new StringBuffer();
		if(dprCommonId == null) {
			if(roleSet.contains(RoleConstant.GENERAL_SCHEME)) {
				returnStr.append("1");
			}
		}
		if(dprCapacityId == null) {
			if(roleSet.contains(RoleConstant.INTELLIGENT_SCHEME)) {
				if(returnStr.length() > 0) {
					returnStr.append(",");
				}
				returnStr.append("2");
			}
		}
		
		if(returnStr.length() == 0) {
			return null;
		}else {
			return returnStr.toString();
		}
	}

	/**
	 * 根据效果图方案id 查询存在几个功能空间
	 * @author chenqiang
	 * @param idList id集合
	 * @return
	 * @date 2018/8/13 0013 16:09
	 */
	public List<String> selectSpaceListByIdList(List<Integer> idList){
		return designPlanRenderSceneMapper.selectSpaceListByIdList(idList);
	}

	/**
	 * 效果图方案打组
	 * @author chenqiang
	 * @param groupList 选中组合列表
	 * @return
	 * @date 2018/8/13 0013 15:18
	 */
	public boolean addGroupList(List<DesignRenderGroup> groupList,LoginUser loginUser){
		boolean bool = true;

		//添加组合
		try {
			DesignPlanRenderScene designPlanRenderScene = null;
			for (DesignRenderGroup designRenderGroup : groupList) {
				designPlanRenderScene = new DesignPlanRenderScene();
				designPlanRenderScene.setId(designRenderGroup.getId());
				designPlanRenderScene.setGroupPrimaryId(Integer.parseInt(designRenderGroup.getGroupPrimaryId()));
				designPlanRenderScene.setApplySpaceAreas(designRenderGroup.getApplySpaceAreas());
				designPlanRenderScene.setModifier(loginUser.getLoginName());
				designPlanRenderScene.setGmtModified(new Date());
				designPlanRenderSceneMapper.update(designPlanRenderScene);
			}
		}catch (Exception e){
			bool = false;
			logger.error("效果图方案打组出错",e);
		}

		return bool;
	}

	/**
	 * 效果图方案解组
	 * @author chenqiang
	 * @param designRenderId 方案id
	 * @return
	 * @date 2018/8/13 0013 16:27
	 */
	@Override
	public int delGroupList(Integer designRenderId,LoginUser loginUser){
		return designPlanRenderSceneMapper.delGroupList(designRenderId,loginUser.getLoginName());
	}

	/**
	 *
	 * @author chenqiang
	 * @param groupId 组合id
	 * @param isDeleted 是否删除
	 * @return
	 * @date 2018/8/13 0013 18:32
	 */
	@Override
	public List<DesignPlanRenderScene> getGroupList(Integer groupId,Integer isDeleted){
		if (null == groupId || groupId <= 0) {
			return  null;
		}
		return designPlanRenderSceneMapper.getGroupList(groupId,isDeleted);
	}
}
