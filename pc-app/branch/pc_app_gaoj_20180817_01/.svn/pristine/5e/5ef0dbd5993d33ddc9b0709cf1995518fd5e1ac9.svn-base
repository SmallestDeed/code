package com.nork.product.controller.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nork.product.common.PlatformConstants;
import com.nork.product.model.*;
import com.nork.product.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.search.GroupCollectDetailsSearch;
import com.nork.product.model.search.GroupProductCollectSearch;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;

@Controller
@RequestMapping("/{style}/web/product/groupProductCollect")
public class WebGroupProductCollectController {

	@Autowired
	private GroupProductCollectService groupProductCollectService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private GroupCollectDetailsService groupCollectDetailsService;
	@Autowired
	private GroupProductDetailsService groupProductDetailsService;
	@Autowired
	private GroupProductService groupProductService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private ProductPlatformService productPlatformService;
	/**
	 * 编辑组合收藏接口(未完成,备用)
	 * @author huangsongbo
	 * @param groupId 组合id
	 * @param productId 产品id
	 * @param type 0:新增
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/updateDetails")
	@ResponseBody
	public Object updateDetails(
			Integer groupId, Integer productId, Integer type, String msgId,
			HttpServletRequest request) {
		Map<String, Object> map = groupProductCollectService.updateDetailsVerifyParams(groupId, productId, type, msgId);
		boolean success = (boolean) map.get("success");
		if (!success)
			return new ResponseEnvelope<>(false, (String) map.get("msg"), msgId);
		/* 识别组合是否存在 */
		GroupProductCollect groupProductCollect = groupProductCollectService.get(groupId);
		if (groupProductCollect == null || groupProductCollect.getId() == null)
			return new ResponseEnvelope<>(false, "id为" + groupId + "的组合收藏未找到,操作失败", msgId);
		/* 识别组合是否存在->end */
		/*识别该产品是否存在*/
		BaseProduct baseProduct=baseProductService.get(productId);
		if(baseProduct==null||baseProduct.getId()==null)
			return new ResponseEnvelope<>(false, "id为" + baseProduct + "的产品未找到,操作失败", msgId);
		/*识别该产品是否存在->end*/
		if (new Integer(0).equals(type)) {
			/* 组合收藏新增产品 */
			//groupCollectDetailsService.create(groupId,productId);
		}
		return null;
	}

	/**
	 * 组合收藏列表
	 * @param groupProductCollectSearch
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	@ResponseBody
	public Object list(GroupProductCollectSearch groupProductCollectSearch,
			@RequestParam(value = "spaceCommonId",required = false)Integer spaceCommonId,
			HttpServletRequest request){
		/** 获取登录用户 */
		LoginUser loginUser = new LoginUser();
		if( com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!",groupProductCollectSearch.getMsgId());
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			groupProductCollectSearch.setUserType(loginUser.getUserType());
			String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
			groupProductCollectSearch.setVersionType(versionType);
		}
		groupProductCollectSearch.setUserId(loginUser.getId());


		/** 获取该用户所属品牌 */
		String brandIds = null;
		BaseBrand baseBrands = null;
        if(2==loginUser.getUserType()){//企业用户
        	baseBrands = baseBrandService.findBrandIdByUserIdBase(loginUser);
        }else if (3==loginUser.getUserType()){
        	baseBrands = baseBrandService.findBrandIdByUserIdAndUserType(loginUser);
        }
		if(null != baseBrands){
			if(StringUtils.isNotBlank(baseBrands.getBrandStr())){
				brandIds = baseBrands.getBrandStr();
			}
		}


		/** 获取收藏的组合列表 */
		List<GroupProductCollect> list = new ArrayList<GroupProductCollect>();
		int total = groupProductCollectService.getGroupProductCollectCount(groupProductCollectSearch);
		if(total>0){
			int brandState=0;
			list = groupProductCollectService.getGroupProductCollectList(groupProductCollectSearch);
 
			for(GroupProductCollect groupProductCollect : list){
				/*方便前端取组合名*/
				groupProductCollect.setProductName(groupProductCollect.getName());
				
				/*获取组合详情*/
				GroupCollectDetailsSearch groupCollectDetailsSearch = new GroupCollectDetailsSearch();
				groupCollectDetailsSearch.setGroupCollectId(groupProductCollect.getId());
				List<GroupCollectDetails> groupCollectDetails = groupCollectDetailsService.getPaginatedList(groupCollectDetailsSearch);

				if(groupCollectDetails!=null&&groupCollectDetails.size()>0){

					for (GroupCollectDetails groupCollectDetails2 : groupCollectDetails) {
						Integer isMain = groupCollectDetails2.getIsMain();

						if(isMain!=null&&isMain==1){
							Integer productId = groupCollectDetails2.getProductId();
							groupProductCollect.setProductId(productId);
							BaseProduct baseProduct = baseProductService.get(productId);

							/**品牌是否被绑定状态*/
							String productBrandId = baseProduct.getBrandId().toString();
							if( StringUtils.isEmpty(brandIds) ){
								brandState=brandState+1;
							}
							if( (","+brandIds).indexOf(productBrandId)==-1 ){
								brandState=brandState+1;
							}

							Integer brandId=baseProduct.getBrandId();
							if(brandId!=null){
								BaseBrand baseBrand=baseBrandService.get(brandId);
								if(baseBrand!=null&&baseBrand.getId()>0)
									groupProductCollect.setBrandName(baseBrand.getBrandName());
							}
						}

						if(brandState==0){
							if(isMain==null||isMain!=1){
								Integer productId=groupCollectDetails2.getProductId();
								BaseProduct baseProduct=baseProductService.get(productId);
								/**品牌是否被绑定状态*/
								String productBrandId = baseProduct.getBrandId().toString();
								if( StringUtils.isEmpty(brandIds) ){
									brandState=brandState+1;
								}
								if( (","+brandIds).indexOf(productBrandId)==-1 ){
									brandState=brandState+1;
								}
							}
						}
					}
				}

				/** 获取组合平台个性化信息 */
				productPlatformService.getGroupProductCollectInfo(groupProductCollect.getGroupId(), PlatformConstants.PC_2B,groupProductCollect);

				/** 组合位置信息 */
				GroupProduct groupProduct = groupProductService.get(groupProductCollect.getGroupId());
				if(groupProduct!=null&&groupProduct.getId()>0){
					groupProductCollect.setGroupConfig(groupProduct.getLocation());
					if(groupProduct.getLocation() != null) {
						ResFile resFile = null;
						try {
							resFile = resFileService.get(Integer.valueOf(groupProduct.getLocation()));
						}catch (Exception e) {
							e.printStackTrace();
						}
						if(resFile != null) {
							groupProductCollect.setFilePath(resFile.getFilePath());
						}
					}
				}

				/*组装组合里的产品详情*/
				GroupProductDetails groupProductDetailSearch = new GroupProductDetails();
				groupProductDetailSearch.setGroupId(groupProductCollect.getGroupId());
				List<GroupProductDetails> groupProductDetails = groupProductDetailsService.getList(groupProductDetailSearch);

				List<SearchProductGroupDetail> details = null;
				SearchProductGroupDetail groupDetail = null;

				//媒介类型.如果没有值，则表示为web前端（2）
				String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
				if( groupProductDetails != null && groupProductDetails.size() > 0 ){

					details = new ArrayList<>();

					for( GroupProductDetails detailEntity : groupProductDetails ){

						groupDetail = new SearchProductGroupDetail();
						groupDetail.setProductId(detailEntity.getProductId());
						groupDetail.setProductGroupId(groupProductCollect.getGroupId());
						int isMain = detailEntity.getIsMain()==null?0:detailEntity.getIsMain();
						groupDetail.setIsMainProduct(isMain);

						if( detailEntity.getProductId() > 0 ){

							BaseProduct baseProduct = baseProductService.get(detailEntity.getProductId());

							if( baseProduct != null ){
								groupDetail.setProductCode(baseProduct.getProductCode());

								// 组装产品模型地址
								String modelId = baseProductService.getU3dModelId(mediaType, baseProduct);
								if( StringUtils.isNotBlank(modelId) ){
									ResModel resModel = resModelService.get(Integer.valueOf(modelId));
									if( resModel != null ){
									    String root = Tools.getRootPath(resModel.getModelPath(),"");
										File modelFile = new File(root+resModel.getModelPath());
										if( modelFile.exists() ){
											groupDetail.setU3dModelPath(resModel.getModelPath());
										}
									}
								}
	
								// 产品大类信息
								String typeValue = baseProduct.getProductTypeValue();
								if( StringUtils.isNotBlank(typeValue) ){
									SysDictionary dlDic = sysDictionaryService.findOneByTypeAndValue("productType", Integer.valueOf(typeValue));
									groupDetail.setProductTypeValue(dlDic.getValue());
									groupDetail.setProductTypeCode(dlDic.getValuekey());
									groupDetail.setProductTypeName(dlDic.getName());
	
									// 产品小类
									Integer smallType = baseProduct.getProductSmallTypeValue();
									if( smallType != null && smallType.intValue() > 0 ){
										SysDictionary xlDic = sysDictionaryService.findOneByTypeAndValue(dlDic.getValuekey(), smallType);
										groupDetail.setProductSmallTypeValue(xlDic.getValue().toString());
										groupDetail.setProductSmallTypeCode(xlDic.getValuekey());
										groupDetail.setProductSmallTypeName(xlDic.getName());
	
										String rootType = StringUtils.isEmpty(xlDic.getAtt1()) ? "2" : xlDic.getAtt1().trim();
										groupDetail.setRootType(rootType);
									}
								}
	
								// 父类产品分类信息
								if( baseProduct.getParentId() != null && baseProduct.getParentId().intValue() > 0 ){
									BaseProduct parentProduct = baseProductService.get(baseProduct.getParentId());
									if( parentProduct != null ){
										String parentTypeValue = parentProduct.getProductTypeValue();
										if( StringUtils.isNotBlank(parentTypeValue) ){
											SysDictionary dlDic = sysDictionaryService.findOneByTypeAndValue("productType", Integer.valueOf(parentTypeValue));
											groupDetail.setParentTypeValue(dlDic.getValue());
											groupDetail.setParentTypeCode(dlDic.getValuekey());
											groupDetail.setParentTypeName(dlDic.getName());
										}
									}
									groupDetail.setParentProductId(baseProduct.getParentId());
								}
	
								// 长宽高
								groupDetail.setProductWidth(baseProduct.getProductWidth());
								groupDetail.setProductHeight(baseProduct.getProductHeight());
								groupDetail.setProductLength(baseProduct.getProductLength());
	
								// 产品材质图片列表
								String materialIds = baseProduct.getMaterialPicIds();
								if(StringUtils.isNotBlank(materialIds)){
									String[] mIds = materialIds.split(",");
									if( mIds != null ){
										ResPic resPic = new ResPic();
										resPic.setFileKey("product.baseProduct.material");
										resPic.setBusinessId(baseProduct.getId());
										resPic.setOrders(" sequence asc ");
										List<ResPic> materialPics=null;
										if(Utils.enableRedisCache()){
											materialPics =ResourceCacher.getAllPicList(resPic);
										}else{
											materialPics = resPicService.getList(resPic);
										}
										
										if( materialPics != null && materialPics.size() > 0 ){
											String[] materialPaths = new String[materialPics.size()];
											for( int i=0;i<materialPics.size();i++ ){
												resPic = materialPics.get(i);
												if( resPic != null ) {
													materialPaths[i] = resPic.getPicPath();
												}
											}
											groupDetail.setMaterialPicPaths(materialPaths);
										}
									}
								}
	
								 /** 在组合产品查询列表 中 增加产品属性 */
								Map<String,String> map = new HashMap<String,String>();
								map = productAttributeService.getPropertyMap(baseProduct.getId());
								baseProduct.setPropertyMap(map);
								
								// 产品规则
								Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(), baseProduct.getProductTypeMark(),baseProduct.getProductSmallTypeMark(),spaceCommonId,null,new DesignRules(),map);
								groupDetail.setRulesMap(rulesMap);
								
								/*处理拆分材质产品返回默认材质*/
								String splitTexturesInfo = detailEntity.getSplitTexturesChooseInfo();
								Integer isSplit=0;
								List<SplitTextureDTO> splitTextureDTOList = null;
								if ( StringUtils.isNotBlank(splitTexturesInfo)) {
									// 根据组合明细表中的splitTexturesInfo和产品表中的splitTexturesInfo处理多材质信息 ->start
									// 对比组合中多材质默认选中的材质在不在产品表中的多材质信息内
									/*splitTexturesInfo = designPlanProductService.getCorrectSplitTexturesInfo(splitTexturesInfo, groupDetail.getProductId());*/
									splitTexturesInfo = designPlanProductService.matchSplitTexturesInfo(splitTexturesInfo, detailEntity.getProductId(), splitTexturesInfo);
									// 根据组合明细表中的splitTexturesInfo和产品表中的splitTexturesInfo处理多材质信息 ->start
									
									Map<String,Object> splitTextureInfoMap=baseProductService.dealWithSplitTextureInfo(detailEntity.getProductId(), splitTexturesInfo,"choose");
									isSplit=(Integer) splitTextureInfoMap.get("isSplit");
									splitTextureDTOList=(List<SplitTextureDTO>) splitTextureInfoMap.get("splitTexturesChoose");
								}else{
									
								}
								groupDetail.setIsSplit(isSplit);
								groupDetail.setSplitTexturesChoose(splitTextureDTOList);
								groupDetail.setSplitTexturesChooseInfo(splitTexturesInfo);
								/*处理拆分材质产品返回默认材质->end*/
							}
						}
						details.add(groupDetail);
					}
					if(brandState>0){
						groupProductCollect.setSalePrice(-1.0);
					}
					groupProductCollect.setGroupProductDetails(details);
				}
			}
		}
		return new ResponseEnvelope<GroupProductCollect>(total, list, groupProductCollectSearch.getMsgId());
	}
	
	/**
	 * 得到组合收藏详情接口
	 * @author huangsongbo
	 * @param collectId
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/getDetails")
	@ResponseBody
	public Object getDetails(Integer collectId,String msgId){
		GroupProductCollect groupProductCollect=groupProductCollectService.get(collectId);
		List<GroupCollectDetails> list=groupCollectDetailsService.findAllByGroupId(collectId);
		List<BaseProductDTO> baseProductDTOs=new ArrayList<BaseProductDTO>();
		if(list==null||list.size()==0)
			return new ResponseEnvelope<>(groupProductCollect, msgId, true);
		/*得到产品的id+缩略图*/
		for(GroupCollectDetails groupCollectDetails:list){
			String picUrl=baseProductService.getPicUrlFromProductId(groupCollectDetails.getProductId());
			baseProductDTOs.add(new BaseProductDTO(groupCollectDetails.getProductId(), picUrl));
		}
		groupProductCollect.setBaseProducts(baseProductDTOs);
		return new ResponseEnvelope<>(groupProductCollect, msgId, true);
	}
	
	/**
	 * 收藏组合接口
	 * @author huangsongbo
	 * @param groupId
	 * @param msgId
	 * @param type 1:删除该组合的收藏
	 * @return
	 */
	@RequestMapping("/collectGroup")
	@ResponseBody
	public Object collectGroup(Integer groupId,String type,String msgId,HttpServletRequest request){
		if(groupId==null)
			return new ResponseEnvelope<>(false, "参数groupId不能为空", msgId);
		GroupProduct groupProduct=groupProductService.get(groupId);
		if(groupProduct==null||groupProduct.getId()==null)
			return new ResponseEnvelope<>(false, "id为:"+groupId+"的组合未找到,收藏失败", msgId);
		List<GroupProductDetails> list=groupProductDetailsService.findDetailsByGroupId(groupId);
		/*保存组合收藏数据*/
		LoginUser loginUser=com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser==null||loginUser.getId()==null)
			throw new RuntimeException("------收藏组合接口->未找到登录用户信息");
		if(StringUtils.equals("1", type)){
			/*收藏表中删除该组合*/
			int num=groupProductCollectService.deleteByGroupIdAndUserId(groupId,loginUser.getId());
			if(num>0){
				return new ResponseEnvelope<>(true, "删除成功", msgId);
			}else{
				return new ResponseEnvelope<>(true, "该用户未收藏该组合->userid:"+loginUser.getId()+";groupId:"+groupId, msgId);
			}
		}else{
			/*新增组合*/
			int id=groupProductCollectService.saveCollectByGroup(groupProduct,loginUser);
			/*保存组合收藏明细数据*/
			for(GroupProductDetails groupProductDetails:list){
				groupCollectDetailsService.saveByGroupDetails(groupProductDetails,id,loginUser);
			}
			return new ResponseEnvelope<>(true, "收藏成功", msgId);
		}
	}
	
}
