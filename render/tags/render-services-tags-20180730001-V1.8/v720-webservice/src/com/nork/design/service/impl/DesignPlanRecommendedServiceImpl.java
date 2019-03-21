package com.nork.design.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nork.design.dao.DesignPlanRecommendedMapperV2;
import com.nork.design.model.DesignPlanRecommended;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import com.nork.design.common.RecommendedDecorateState;
import com.nork.design.dao.DesignPlanBrandMapper;
import com.nork.design.dao.DesignPlanCheckMapper;
import com.nork.design.dao.DesignPlanMapper;
import com.nork.design.dao.DesignPlanProductMapper;
import com.nork.design.dao.DesignTempletMapper;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanBrand;
import com.nork.design.model.DesignPlanCheck;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletPutawayState;
import com.nork.design.service.DesignPlanRecommendedService;
import com.nork.home.dao.SpaceCommonMapper;
import com.nork.home.model.SpaceCommon;
import com.nork.home.model.SpaceCommonStatus;
import com.nork.product.dao.BaseBrandMapper;
import com.nork.product.dao.BaseProductMapper;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.BaseProductPutawayState;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.dao.SysRoleMapper;
import com.nork.system.dao.SysUserMapper;
import com.nork.system.dao.SysUserRoleMapper;
import com.nork.system.model.SysRole;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUserRole;
import com.nork.system.model.search.ResRenderPicSearch;
import com.nork.system.model.search.SysUserRoleSearch;

@Service("designPlanRecommendedService")
@Transactional
public class DesignPlanRecommendedServiceImpl implements DesignPlanRecommendedService{

	Logger logger = Logger.getLogger(DesignPlanRecommendedServiceImpl.class);

	BaseProductMapper baseProductMapper;
	
	DesignPlanCheckMapper designPlanCheckMapper;
	
	DesignPlanBrandMapper designPlanBrandMapper;
	
	DesignPlanMapper designPlanMapper;
	
	SysUserMapper sysUserMapper;
	
	SysUserRoleMapper sysUserRoleMapper;
 
	SysRoleMapper sysRoleMapper;
	
	BaseBrandMapper baseBrandMapper;
	
	ResRenderPicMapper resRenderPicMapper;
	
	DesignTempletMapper designTempletMapper;
	
	SpaceCommonMapper spaceCommonMapper;
	
	DesignPlanProductMapper designPlanProductMapper;

	@Autowired
    private DesignPlanRecommendedMapperV2 designPlanRecommendedMapperV2;
	
	@Autowired
	public void setBaseProductMapper(BaseProductMapper baseProductMapper) {
		this.baseProductMapper = baseProductMapper;
	}
	@Autowired
	public void setDesignPlanProductMapper(DesignPlanProductMapper designPlanProductMapper) {
		this.designPlanProductMapper = designPlanProductMapper;
	}
	@Autowired
	public void setSpaceCommonMapper(SpaceCommonMapper spaceCommonMapper) {
		this.spaceCommonMapper = spaceCommonMapper;
	}
	@Autowired
	public void setDesignTempletMapper(DesignTempletMapper designTempletMapper) {
		this.designTempletMapper = designTempletMapper;
	}
	@Autowired
	public void setResRenderPicMapper(ResRenderPicMapper resRenderPicMapper) {
		this.resRenderPicMapper = resRenderPicMapper;
	}
	@Autowired
	public void setDesignPlanMapper(DesignPlanMapper designPlanMapper) {
		this.designPlanMapper = designPlanMapper;
	}
	@Autowired
	public void setBaseBrandMapper(BaseBrandMapper baseBrandMapper) {
		this.baseBrandMapper = baseBrandMapper;
	}

	@Autowired
	public void setDesignPlanCheckMapper(DesignPlanCheckMapper designPlanCheckMapper) {
		this.designPlanCheckMapper = designPlanCheckMapper;
	}
	
	@Autowired
	public void setDesignPlanBrandMapper(DesignPlanBrandMapper designPlanBrandMapper) {
		this.designPlanBrandMapper = designPlanBrandMapper;
	}

	@Autowired
	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	@Autowired
	public void setSysUserRoleMapper(SysUserRoleMapper sysUserRoleMapper) {
		this.sysUserRoleMapper = sysUserRoleMapper;
	}
	
	@Autowired
	public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
	}
	
	
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanBrand model,HttpServletRequest request){
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
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanCheck model,HttpServletRequest request){
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
	 * 判断是否拥有设计方案拷贝 和 发布的权限
	 * @param loginUser
	 * @return
	 */
	public boolean isPermissions(LoginUser loginUser){
		boolean flag = false;
		List<SysRole>sysRoleList = null;
		SysRole sysRole = null;
		try{
			SysRole newsSysRole= new SysRole();
			newsSysRole.setIsDeleted(0);
			newsSysRole.setCode("023");
			sysRoleList = sysRoleMapper.selectList(newsSysRole);
			if(sysRoleList!=null&&sysRoleList.size()==1){
				sysRole = sysRoleList.get(0);
				SysUser sysUser = sysUserMapper.selectByPrimaryKey(loginUser.getId());
				if(sysUser!=null){
					List<SysUserRole>sysUserRoleList = null;
					SysUserRole sysUserRole = new SysUserRole();
					sysUserRole.setUserId(sysUser.getId());
					sysUserRole.setRoleId(sysRole.getId());
					sysUserRole.setIsDeleted(0);
					sysUserRoleList = sysUserRoleMapper.selectList(sysUserRole);
					if(sysUserRoleList!=null&&sysUserRoleList.size()>0){
						flag = true;
					}
				}	
			}
		}catch (Exception e) {
			return flag;
		}
		return flag;
	}
	
	/**
	 * 判断是否有审核权限
	 * @param loginUser
	 * @return
	 */
	public boolean isDesignPlanCheck(LoginUser loginUser,Integer userId){
		boolean permissions = false;
		if(loginUser == null && userId == null){
			return permissions;
		}
		 
		Integer roleId = null;
		List<SysRole>roleList = null;
		try{
			SysRole sysRole = new SysRole();
			sysRole.setIsDeleted(0);
			sysRole.setCode("0232");
			roleList = sysRoleMapper.selectList(sysRole);
			if(roleList!=null && roleList.size()>= 1 ){
				roleId = roleList.get(0).getId();
			}else{
				return permissions;
			}
			if(roleId !=null && roleId.intValue()>0){
				SysUserRoleSearch sysUserRoleSearch = new SysUserRoleSearch();
				if(loginUser != null){
					sysUserRoleSearch.setUserId(loginUser.getId());
				}else{
					sysUserRoleSearch.setUserId(userId);
				}
				sysUserRoleSearch.setRoleId(roleId);
				int count = sysUserRoleMapper.selectCount(sysUserRoleSearch);
				if(count>0){
					permissions = true;
				}
			}
		}catch (Exception e) {
			logger.error("isDesignPlanCheck  methods the error  :" + e);
			return permissions;
		}
		return permissions;
	}
	
	/**
	 * 发布成功，进入审核 逻辑
	 * @param designPlan
	 * @param brandIds
	 * @return
	 */
	@Override
	public boolean planIsRelease(DesignPlan designPlan,Integer isRelease, List<String> brandIds,List<String>  checkIdList,HttpServletRequest request ) {
		boolean flag = false;
		if(designPlan==null){
			return flag;
		}
		try{
			int i = 0;
			int j = 0 ;
			// 设计师点发布  需要 选择审核人员，  测试发布则不需要
			if(RecommendedDecorateState.IS_RELEASEING == isRelease.intValue()){  
				if(checkIdList!=null){
					//通过设计方案id  删除 审核人员
					designPlanCheckMapper.deletedBydesignPlanId(designPlan.getId());
					for (String userId : checkIdList) {
						DesignPlanCheck designPlanCheck = new DesignPlanCheck();
						designPlanCheck.setIsDeleted(0);
						designPlanCheck.setPlanId(designPlan.getId());
						designPlanCheck.setUserId(Integer.parseInt(userId));
						sysSave(designPlanCheck,request);
						designPlanCheckMapper.insertSelective(designPlanCheck);
					}
				}
			}
			if(brandIds!=null){
				i = brandIds.size();
				//这里可以改成批量新增
				for (String brandId : brandIds) {
					DesignPlanBrand designPlanBrand = new DesignPlanBrand();
					designPlanBrand.setIsDeleted(0);
					designPlanBrand.setPlanId(designPlan.getId());
					designPlanBrand.setBrandId(Integer.parseInt(brandId));
					sysSave(designPlanBrand,request);
					designPlanBrandMapper.insertSelective(designPlanBrand);
					j  = j  + 1;
				}
			}
			if(j == i){
				DesignPlan newDesignPlan = new DesignPlan();
				newDesignPlan.setIsRecommended(designPlan.getIsRecommended());
				newDesignPlan.setIsDefaultDecorate(designPlan.getIsDefaultDecorate());
				newDesignPlan.setPlanNumber(designPlan.getPlanNumber());
				newDesignPlan.setDesignRecommendedStyleId(designPlan.getDesignRecommendedStyleId());
				newDesignPlan.setId(designPlan.getId());
				newDesignPlan.setGmtModified(new Date());
				newDesignPlan.setReleaseTime(new Date());  //每次发布 更新发布时间，方便排序
				if(RecommendedDecorateState.IS_RELEASEING == isRelease.intValue() ){
					newDesignPlan.setIsRelease(RecommendedDecorateState.WAITING_CHECK_RELEASE);  //提交发布成功后进入审核
				}
				if(RecommendedDecorateState.IS_TEST_RELEASE == isRelease.intValue() ){
					newDesignPlan.setIsRelease(RecommendedDecorateState.IS_TEST_RELEASE);  //测试发布  无需审核
				}
				designPlanMapper.updateByPrimaryKeySelective(newDesignPlan);
				flag = true;
			} 
		}catch (Exception e) {
			logger.error("planIsRelease  methods the error  :" + e);
			return flag;
		}
		return flag;
	}
	
	
	
	/**
	 * 取消发布逻辑
	 * @param designPlan
	 * @return
	 */
	@Override
	public boolean planNoRelease(DesignPlan designPlan) {
		boolean flag = false;
		if(designPlan==null){
			return flag;
		}
		try{
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setIsRelease(RecommendedDecorateState.NO_RELEASE);
			newDesignPlan.setId(designPlan.getId());
			designPlanMapper.updateByPrimaryKeySelective(newDesignPlan);
			flag = true;
		}catch (Exception e) {
			logger.error("planNoRelease  methods the error  :" + e);
			return flag;
		}
		return flag;
	}

	
	/**
	 * 方案发布校验方法
	 * 发布需要校验
	 * 			1.品牌不能重复添加 
	 * 			2.是否有封面，
	 * 			3.是否有720渲染，
	 * 			4.是否有照片级渲染
	 * 			5.样板房  空间 是否为发布中
	 * 			6.验证设计方案产品是否发布
	 * @param designPlan
	 * @return
	 */
	public Map<String,String> planIsReleaseCheck(DesignPlan designPlan,List<String>brandIdList){
		Map<String,String>resMap = new HashMap<String,String>();
		if(designPlan==null){
			resMap.put("success", "false");
			resMap.put("data", "方案不能为空");
			return resMap;
		}
		/*if(brandIdList==null||brandIdList.size()<=0){
			resMap.put("success", "false");
			resMap.put("data", "brandIds不能为空");
			return resMap;
		}*/
		
		try{
			/*1.品牌不能重复添加*/
			if(brandIdList!=null&&brandIdList.size()>0){
				List<DesignPlanBrand>list= new ArrayList<DesignPlanBrand>();
				DesignPlanBrand designPlanBrand = new DesignPlanBrand();
				designPlanBrand.setIsDeleted(0);
				designPlanBrand.setPlanId(designPlan.getId());
				list = designPlanBrandMapper.selectList(designPlanBrand);
				if(list!=null&&list.size()>0){
					for (DesignPlanBrand planBrand : list) {
						String brandId = planBrand.getBrandId()+"";
						if(brandId!=null&&!"".equals(brandId)){
							for (String newBrandId : brandIdList) {
								if(brandId.equals(newBrandId)){
									if("-1".equals(brandId)){
										resMap.put("success", "false");
										resMap.put("data", "所有品牌:选项重复！");
										return resMap;
									}
									BaseBrand baseBrand  = baseBrandMapper.selectByPrimaryKey(planBrand.getBrandId());
									if(baseBrand!=null){
										resMap.put("success", "false");
										resMap.put("data", baseBrand.getBrandName() + ":重复！");
										return resMap;
									}else{
										resMap.put("success", "false");
										resMap.put("data", "品牌重复！");
										return resMap;
									}
								}
							}
						}
					}
				}
			}
			/*2.是否有封面*/
			Integer coverPicId =  designPlan.getCoverPicId();
			if(coverPicId==null||coverPicId.intValue()<=0){
				resMap.put("success", "false");
				resMap.put("data", "请上传封面图片");
				return resMap;
			}
			/*3.是否有720渲染，
			 *4.是否有照片级渲染*/
			Integer i = 0;  //照片级普通 
			Integer j = 0;  //照片级高清
			Integer l = 0;  //照片级超高清
			ResRenderPicSearch resRenderPicSearch = new ResRenderPicSearch();
			resRenderPicSearch.setIsDeleted(0);
			resRenderPicSearch.setBusinessId(designPlan.getId());
			resRenderPicSearch.setRenderingType(RenderTypeCode.COMMON_PICTURE_LEVEL);
			i = resRenderPicMapper.selectCount(resRenderPicSearch);
			resRenderPicSearch.setRenderingType(RenderTypeCode.HD_PICTURE_LEVEL);
			j = resRenderPicMapper.selectCount(resRenderPicSearch);
			resRenderPicSearch.setRenderingType(RenderTypeCode.ULTRA_HD_PICTURE_LEVEL);
			l = resRenderPicMapper.selectCount(resRenderPicSearch);
			//三种图片一种都没有 不允许发布
			if((i==null||i.intValue()<=0)&&(j==null||j.intValue()<=0)&&(l==null||l.intValue()<=0)){
				resMap.put("success", "false");
				resMap.put("data", "该方案没有照片级渲染图！");
				return resMap;
			}
			
			
			Integer x = 0;  //720度普通
			Integer y = 0;  //720度高清
			ResRenderPicSearch resRenderPicSearch2 = new ResRenderPicSearch();
			resRenderPicSearch2.setIsDeleted(0);
			resRenderPicSearch2.setBusinessId(designPlan.getId());
			resRenderPicSearch2.setRenderingType(RenderTypeCode.COMMON_720_LEVEL);
			x = resRenderPicMapper.selectCount(resRenderPicSearch2);
			resRenderPicSearch2.setRenderingType(RenderTypeCode.HD_720_LEVEL);
			y = resRenderPicMapper.selectCount(resRenderPicSearch2);
			//三种图片一种都没有 不允许发布
			if((x==null||x.intValue()<=0)&&(y==null||y.intValue()<=0)){
				resMap.put("success", "false");
				resMap.put("data", "该方案没有720渲染图!");
				return resMap;
			}
			
			
			//5.样板房  空间 是否为发布中，否则不让发布
			if(designPlan.getDesignTemplateId()!=null && designPlan.getDesignTemplateId().intValue()>0){
				DesignTemplet designTemplet = designTempletMapper.selectByPrimaryKey(designPlan.getDesignTemplateId());
				if(designTemplet == null || designTemplet.getPutawayState() != DesignTempletPutawayState.IS_RELEASE.intValue()){
					resMap.put("success", "false");
					resMap.put("data", "该方案推荐 的 样板房 未发布!");
					return resMap;
				}
				if(designTemplet!=null){
					SpaceCommon spaceCommon = spaceCommonMapper.selectByPrimaryKey(designTemplet.getSpaceCommonId());
					if(spaceCommon == null || spaceCommon.getStatus().intValue() != SpaceCommonStatus.IS_RELEASE.intValue() ){
						resMap.put("success", "false");
						resMap.put("data", "该方案推荐 的空间 未发布！");
						return resMap;
					}
				}
			}
			/*6.验证设计方案产品是否发布*/
			DesignPlanProduct designPlanProductSearch = new DesignPlanProduct();
			designPlanProductSearch.setPlanId(designPlan.getId());
			designPlanProductSearch.setIsDeleted(0);
			List<DesignPlanProduct> designPlanProducts = designPlanProductMapper.selectList(designPlanProductSearch);
			if( designPlanProducts != null && designPlanProducts.size() > 0 ){
				for( DesignPlanProduct designPlanProduct : designPlanProducts ){
					Integer productId = designPlanProduct.getProductId();
//					if(productId.intValue() == 157405){
//						//System.out.println("");
//					}
					if( productId != null ){
						BaseProduct baseProduct = baseProductMapper.selectByPrimaryKey(productId);
						if( baseProduct != null ){
							if(baseProduct.getPutawayState().intValue() != BaseProductPutawayState.IS_RELEASE.intValue()&&
									baseProduct.getPutawayState().intValue() != BaseProductPutawayState.IS_UP.intValue()&&
											baseProduct.getPutawayState().intValue() != BaseProductPutawayState.IS_TEST.intValue()
									){
								resMap.put("success", "false");
								resMap.put("data", "产品 ["+baseProduct.getProductCode()+"] 未发布,请替换或删除该产品,再进行发布!");
								return resMap;
							}
						}
					}
				}
			}
			resMap.put("success", "true");
		}catch (Exception e) {
			logger.error("planIsReleaseCheck  methods the error  :" + e);
			resMap.put("success", "false");
			resMap.put("data", "数据错误");
			return resMap;
		}
		return resMap;
	}
	
	
	/**
	 * 测试发布  只需要判断产品 是否为未上架
	 * @param designPlan
	 * @return
	 */
	public Map<String,String> planIsTestCheck(DesignPlan designPlan,List<String>brandIdList){
		Map<String,String>resMap = new HashMap<String,String>();
		if(designPlan==null){
			resMap.put("success", "false");
			resMap.put("data", "方案不能为空");
			return resMap;
		}
		/*if(brandIdList==null||brandIdList.size()<=0){
			resMap.put("success", "false");
			resMap.put("data", "brandIds不能为空");
			return resMap;
		}*/
		
		try{
			/*1.品牌不能重复添加*/
			if(brandIdList!=null&&brandIdList.size()>0){
				List<DesignPlanBrand>list= new ArrayList<DesignPlanBrand>();
				DesignPlanBrand designPlanBrand = new DesignPlanBrand();
				designPlanBrand.setIsDeleted(0);
				designPlanBrand.setPlanId(designPlan.getId());
				list = designPlanBrandMapper.selectList(designPlanBrand);
				if(list!=null&&list.size()>0){
					for (DesignPlanBrand planBrand : list) {
						String brandId = planBrand.getBrandId()+"";
						if(brandId!=null&&!"".equals(brandId)){
							for (String newBrandId : brandIdList) {
								if(brandId.equals(newBrandId)){
									if("-1".equals(brandId)){
										resMap.put("success", "false");
										resMap.put("data", "所有品牌:选项重复！");
										return resMap;
									}
									BaseBrand baseBrand  = baseBrandMapper.selectByPrimaryKey(planBrand.getBrandId());
									if(baseBrand!=null){
										resMap.put("success", "false");
										resMap.put("data", baseBrand.getBrandName() + ":重复！");
										return resMap;
									}else{
										resMap.put("success", "false");
										resMap.put("data", "品牌重复！");
										return resMap;
									}
								}
							}
						}
					}
				}
			}
			/*2.是否有封面*/
			Integer coverPicId =  designPlan.getCoverPicId();
			if(coverPicId==null||coverPicId.intValue()<=0){
				resMap.put("success", "false");
				resMap.put("data", "请上传封面图片");
				return resMap;
			}
			/*3.是否有720渲染，
			 *4.是否有照片级渲染*/
			Integer i = 0;  //照片级普通 
			Integer j = 0;  //照片级高清
			Integer l = 0;  //照片级超高清
			ResRenderPicSearch resRenderPicSearch = new ResRenderPicSearch();
			resRenderPicSearch.setIsDeleted(0);
			resRenderPicSearch.setBusinessId(designPlan.getId());
			resRenderPicSearch.setRenderingType(RenderTypeCode.COMMON_PICTURE_LEVEL);
			i = resRenderPicMapper.selectCount(resRenderPicSearch);
			resRenderPicSearch.setRenderingType(RenderTypeCode.HD_PICTURE_LEVEL);
			j = resRenderPicMapper.selectCount(resRenderPicSearch);
			resRenderPicSearch.setRenderingType(RenderTypeCode.ULTRA_HD_PICTURE_LEVEL);
			l = resRenderPicMapper.selectCount(resRenderPicSearch);
			//三种图片一种都没有 不允许发布
			if((i==null||i.intValue()<=0)&&(j==null||j.intValue()<=0)&&(l==null||l.intValue()<=0)){
				resMap.put("success", "false");
				resMap.put("data", "该方案没有照片级渲染图！");
				return resMap;
			}
			
			
			Integer x = 0;  //720度普通
			Integer y = 0;  //720度高清
			ResRenderPicSearch resRenderPicSearch2 = new ResRenderPicSearch();
			resRenderPicSearch2.setIsDeleted(0);
			resRenderPicSearch2.setBusinessId(designPlan.getId());
			resRenderPicSearch2.setRenderingType(RenderTypeCode.COMMON_720_LEVEL);
			x = resRenderPicMapper.selectCount(resRenderPicSearch2);
			resRenderPicSearch2.setRenderingType(RenderTypeCode.HD_720_LEVEL);
			y = resRenderPicMapper.selectCount(resRenderPicSearch2);
			//三种图片一种都没有 不允许发布
			if((x==null||x.intValue()<=0)&&(y==null||y.intValue()<=0)){
				resMap.put("success", "false");
				resMap.put("data", "该方案没有720渲染图!");
				return resMap;
			}
			
			
			//5.样板房  空间 是否为发布中，否则不让发布
			if(designPlan.getDesignTemplateId()!=null && designPlan.getDesignTemplateId().intValue()>0){
				DesignTemplet designTemplet = designTempletMapper.selectByPrimaryKey(designPlan.getDesignTemplateId());
				if(designTemplet == null 
						|| designTemplet.getPutawayState() == DesignTempletPutawayState.NO_UP.intValue()
							|| designTemplet.getPutawayState() == DesignTempletPutawayState.IS_DOWN.intValue()){
					resMap.put("success", "false");
					resMap.put("data", "该方案推荐 的 样板房 未上架!");
					return resMap;
				}
				if(designTemplet!=null){
					SpaceCommon spaceCommon = spaceCommonMapper.selectByPrimaryKey(designTemplet.getSpaceCommonId());
					if(spaceCommon == null 
							|| spaceCommon.getStatus().intValue() == SpaceCommonStatus.NO_UP.intValue() 
								|| spaceCommon.getStatus().intValue() == SpaceCommonStatus.IS_DOWN.intValue()
									|| spaceCommon.getStatus().intValue() == SpaceCommonStatus.IS_DISABLE.intValue()){
						resMap.put("success", "false");
						resMap.put("data", "该方案推荐 的空间 未上架！");
						return resMap;
					}
				}
			}
			/*6.验证设计方案产品是否发布*/
			DesignPlanProduct designPlanProductSearch = new DesignPlanProduct();
			designPlanProductSearch.setPlanId(designPlan.getId());
			designPlanProductSearch.setIsDeleted(0);
			List<DesignPlanProduct> designPlanProducts = designPlanProductMapper.selectList(designPlanProductSearch);
			if( designPlanProducts != null && designPlanProducts.size() > 0 ){
				for( DesignPlanProduct designPlanProduct : designPlanProducts ){
					Integer productId = designPlanProduct.getProductId();
					if( productId != null ){
						BaseProduct baseProduct = baseProductMapper.selectByPrimaryKey(productId);
						if( baseProduct != null ){
							if(baseProduct.getPutawayState().intValue() == BaseProductPutawayState.NO_UP.intValue()
									|| baseProduct.getPutawayState().intValue() == BaseProductPutawayState.IS_DOWN.intValue() 
									){
								resMap.put("success", "false");
								resMap.put("data", "产品 ["+baseProduct.getProductCode()+"] 未上架,请替换或删除该产品,再进行发布!");
								return resMap;
							}
						}
					}
				}
			}
			resMap.put("success", "true");
		}catch (Exception e) {
			logger.error("planIsReleaseCheck  methods the error  :" + e);
			resMap.put("success", "false");
			resMap.put("data", "数据错误");
			return resMap;
		}
		return resMap;
	}

    @Override
    public DesignPlanRecommended get(Integer planRecommendedId) {
        return designPlanRecommendedMapperV2.selectByPrimaryKey(planRecommendedId);
    }
}
