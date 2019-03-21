package com.nork.product.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.common.PlatformConstants;
import com.nork.product.dao.ProductGroupDao;
import com.nork.product.model.*;
import com.nork.product.service.*;
import com.nork.productprops.model.ProductPropsSimple;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nork.common.util.Constants;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.service.DesignPlanProductService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.cache.GroupProductDetailsCache;
import com.nork.product.dao.GroupProductMapper;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.model.search.GroupProductSearch;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;
/**   
 * @Title: GroupProductServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-产品组合主表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:52:57
 * @version V1.0   
 */
@Service("groupProductService")
public class GroupProductServiceImpl implements GroupProductService {
    private static Logger logger = Logger.getLogger(GroupProductServiceImpl.class);
    private final static ResourceBundle app = ResourceBundle.getBundle("app");
    @Autowired
    private GroupProductDetailsService groupProductDetailsService;
    @Autowired
    private GroupProductMapper groupProductMapper;
    @Autowired
    private BaseProductService baseProductService; 
    @Autowired
    private ResFileService resFileService;
    @Autowired
    private ResModelService resModelService;
    @Autowired
    private ResTextureService resTextureService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private ProductAttributeService productAttributeService;
    @Autowired
    private DesignRulesService designRulesService;
    @Autowired
    private ProductGroupDao productGroupDao;
    @Autowired
    private GroupProductServiceV2 groupProductServiceV2;
    @Autowired
    private GroupProductService groupProductService;
    @Autowired
    private DesignPlanProductService designPlanProductService;
    @Autowired
    private ProductPlatformService productPlatformService;
    /**
     * 新增数据
     *
     * @param groupProduct
     * @return  int 
     */
    @Override
    public int add(GroupProduct groupProduct) {
        groupProductMapper.insertSelective(groupProduct);
        return groupProduct.getId();
    }
    /**
     * 组合收藏状态
     * @author louxinhua
     *
     */
    private enum GROUP_COLLECTED_STATUS {
        not_collect,  	// 没有收藏
        collected		// 已经收藏
    }

    /**
     *    更新数据
     *
     * @param groupProduct
     * @return  int 
     */
    @Override
    public int update(GroupProduct groupProduct) {
        return groupProductMapper
                .updateByPrimaryKeySelective(groupProduct);
    }
    
    /**
     *    删除数据
     *
     * @param id
     * @return  int 
     */
    @Override
    public int delete(Integer id) {
        return groupProductMapper.deleteByPrimaryKey(id);
    }

    /**
     *    获取数据详情
     *
     * @param id
     * @return  GroupProduct 
     */
    @Override
    public GroupProduct get(Integer id) {
        return groupProductMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     * 
     * @param  groupProduct
     * @return   List<GroupProduct>
     */
    @Override
    public List<GroupProduct> getList(GroupProduct groupProduct) {
        return groupProductMapper.selectList(groupProduct);
    }
    
    /**
     *    获取数据数量
     *
     * @param  groupProduct
     * @return   int
     */
    @Override
    public int getCount(GroupProductSearch groupProductSearch){
        return  groupProductMapper.selectCount(groupProductSearch); 
    }
    

    /**
     *    分页获取数据
     *
     * @param  groupProduct
     * @return   List<GroupProduct>
     */
    @Override
    public List<GroupProduct> getPaginatedList(
            GroupProductSearch groupProductSearch) {
        return groupProductMapper.selectPaginatedList(groupProductSearch);
    }

    /**
     * 其他
     * 
     */

    /**
     * 通过产品查询产品组信息
     * @param groupSearch
     * @return
     */
    @Override
    public int getGroupCountByProduct(GroupProductSearch groupSearch){
        return groupProductMapper.getGroupCountByProduct(groupSearch);
    }

    /**
     * 通过产品查询产品组信息
     * @param productSearch
     * @return
     */
    @Override
    public List<GroupProduct> getGroupListByProduct(GroupProductSearch productSearch){
        return groupProductMapper.getGroupListByProduct(productSearch);
    }
    
    /**
     * 根据品牌查询组合（组合中包括关联产品）
     *
     * @param brandName
     * @return  GroupProduct 
     */
    @Override
    public List<GroupProductDetails> queryBrandGroupList(BaseBrandSearch ResBrand) {
        return groupProductMapper.queryBrandGroupList(ResBrand);
    }

    @Override
    public int queryBrandGroupCount(BaseBrandSearch ResBrand) {
        return groupProductMapper.queryBrandGroupCount(ResBrand);
    }


    /**
     * 得到组合的价格->组合表有价格信息就取价格信息,没有的话就把该组合的产品价格相加
     * @author huangsongbo
     * @param groupId
     * @return
     */
    public Double getSalePrice(Integer groupId) {
        Double salePrice=0.0;
        GroupProduct groupProduct=get(groupId);
        if(groupProduct==null||groupProduct.getId()==null||groupProduct.getId()<=0){
            return 0.0;
        }
        if(groupProduct.getGroupPrice()!=null&&groupProduct.getGroupPrice()>0){
            salePrice=groupProduct.getGroupPrice();
        }else{
            salePrice=groupProductMapper.getPriceFromDetails(groupId);
        }
        return salePrice;
    }

//  @Override
//  public int getGroupCountByStructureId(GroupProductSearch groupSearch) {
//      return groupProductMapper.getGroupCountByStructureId(groupSearch);
//  }
//
//  @Override
//  public List<GroupProduct> getGroupListByStructureId(GroupProductSearch groupSearch) {
//      return groupProductMapper.getGroupListByStructureId(groupSearch);
//  }
    
    
    @Override
    public int getGroupCountByStructureId(Integer structureId) {
        return groupProductMapper.getGroupCountByStructureId(structureId);
    }

    @Override
    public List<GroupProduct> getGroupListByStructureId(Integer structureId) {
        return groupProductMapper.getGroupListByStructureId(structureId);
    }

    @Override
    public GroupProduct getStructureByGroupId(Integer groupId) {
        return groupProductMapper.getStructureByGroupId(groupId);
    }

    @Override
    public List<GroupProductResult> selectCommonList(GroupProduct groupProduct) throws Exception {
        List<GroupProductResult> dataList = new ArrayList<GroupProductResult>();
        dataList = groupProductMapper.selectCommonList(groupProduct);
        return dataList;
    }

    /**
     * 判断该产品是不是否是主产品
     * @param cacheEnable 
     * @param productId 产品id
     * @param cacheEnable 是否开启缓存
     * @return
     */
    public Integer getIsMainProduct(Integer productId,String cacheEnable) {
        Integer isMain = 0;
        GroupProductDetails groupProductDetails = new GroupProductDetails();
        groupProductDetails.setProductId(productId);
        groupProductDetails.setIsMain(1);
        groupProductDetails.setIsDeleted(0);
        groupProductDetails.setStart(0);
        groupProductDetails.setLimit(1);
        List<GroupProductDetails> groupList = new ArrayList<>();
        if(StringUtils.equals("1", cacheEnable)){
            groupList = GroupProductDetailsCache.getList(groupProductDetails);
        }else{
            groupList = groupProductDetailsService.getList(groupProductDetails);
        }
        if(groupList!=null && groupList.size() > 0){
            isMain = 1;
        }
        return isMain;
    }

    /**
     * 验证该产品是否是主产品
     * 0:非主产品
     * 1:是某个组合的主产品
     * @author huangsongbo
     * @param productId 产品id
     */
    public Integer getIsMainProductV2(Integer productId) {
        Integer groupId=groupProductMapper.getIsMainProductV2(productId);
        return (groupId!=null&&groupId>0)?1:0;
    }

    /**
     * 根据结构id和组合状态查找结构组合数量
     * @author huangsongbo
     * @param structureId
     * @return
     */
    public int getGroupCountByStructureIdAndStatus(Integer structureId, Integer usertype,String [] brandIdList,String versionType, Integer groupType) {
        return groupProductMapper.getGroupCountByStructureIdAndStatus(structureId, usertype, brandIdList, versionType, groupType);
    }

    /**
     * 通过id查找组合(关联查找更多信息,比如:品牌名,风格名)
     * @author huangsongbo
     * @param groupId
     * @return
     */
    public GroupProduct getMoreInfoById(Integer groupId) {
        return groupProductMapper.getMoreInfoById(groupId);
    }

    @Override
    public void updateStatus(Integer oldStatus, Integer newStatus) {
        groupProductMapper.updateStatus(oldStatus, newStatus);
    }
    
    /**
     * 单品替换组合方法（适用于天花地板 功能）
     * @param request
     * @param response
     * @return
     */
    @Override
    public SearchProductGroupResult   productSelectGroupReplace(BaseProduct baseProduct, StructureProduct structureProduct,Integer productIndex,Integer spaceCommonId,String mediaType,Integer userType) {
        SearchProductGroupResult groupProduct = null;
        List<SearchProductGroupDetail> details = null;
        SearchProductGroupDetail groupDetail = null;
        GroupProduct entity = null;
        //查询满足条件的组合
        List<GroupProduct> groupList = groupProductMapper.productSelectGroupReplace(baseProduct.getId(),structureProduct.getGroupFlag(),productIndex,userType);
        if(groupList ==null || groupList.size()<=0){
            return null;
        }
        entity = groupList.get(0);
        //为了兼容老数据
        if(entity.getLocation()!=null&&!"".equals(entity.getLocation())){
            ResFile resFile=null;       
            if(StringUtils.isNumeric(entity.getLocation())){//判断里面是不是数字
                resFile =resFileService.get(Integer.parseInt(entity.getLocation()));
                String txt = "";
                if(resFile!=null){
                    /*String url = app.getString("app.upload.root") + resFile.getFilePath();*/
                	String url = Utils.getAbsolutePath(resFile.getFilePath(), Utils.getAbsolutePathType.encrypt);
                    txt = FileUploadUtils.getFileContext(url);
                    entity.setLocation(txt);    
                }
                entity.setLocation(txt);
            }
        }
         
        groupProduct = new SearchProductGroupResult();
        groupProduct.setGroupConfig(entity.getLocation());
        groupProduct.setGroupId(entity.getId());
        groupProduct.setGroupCode(entity.getGroupCode());
        groupProduct.setGroupName(entity.getGroupName());
        
        groupProduct.setStructureCode(entity.getStructureCode());
        groupProduct.setStructureName(entity.getStructureName());
        groupProduct.setProductStructureId(entity.getProductStructureId());
        
        //通过组合id 获取 组合产品,产品基础属性，并且获取相关资源
        List<GroupProductDetails>groupProductDetails = groupProductDetailsService.getDataAndResourcesByGroupId(entity.getId());
        if( groupProductDetails != null && groupProductDetails.size() > 0 ){
            details = new ArrayList<>();
            for (GroupProductDetails detailEntity : groupProductDetails) {
                groupDetail = new SearchProductGroupDetail();
                //特殊情况
                if(detailEntity.getChartletProductModelId()!=null&&detailEntity.getChartletProductModelId().intValue()>0){
                    BaseProduct baseProduct2 = null;
                    baseProduct2 = baseProductService.get(detailEntity.getChartletProductModelId());
                    if(baseProduct2!=null){
                        ResModel resModel= null;
                        resModel=resModelService.get(baseProduct2.getWindowsU3dModelId());
                        if(resModel!=null){
                            groupDetail.setU3dModelPath(resModel.getModelPath());
                            groupDetail.setTextureProductModelId(detailEntity.getChartletProductModelId());  /**贴图产品的模型id*/
                            groupDetail.setModelMinHeight(resModel.getMinHeight());
                        }
                    }
                }else{
                    groupDetail = new SearchProductGroupDetail();
                    groupDetail.setU3dModelPath(detailEntity.getModelPath());
                    groupDetail.setModelMinHeight(detailEntity.getMinHeight());
                }
                 
                groupDetail.setProductIndex(detailEntity.getProductIndex());
                groupDetail.setProductId(detailEntity.getProductId());
                groupDetail.setProductGroupId(groupProduct.getGroupId());
                int isMain = detailEntity.getIsMain()==null?0:detailEntity.getIsMain();
                groupDetail.setIsMainProduct(isMain);
                if( isMain == 1 ) {
                    groupProduct.setProductId(detailEntity.getProductId());
                }
                groupDetail.setProductCode(groupDetail.getProductCode());
                // 组装产品模型地址
                BaseProduct baseProduct_ = baseProductService.get(detailEntity.getProductId());
                if( baseProduct_ != null ){
                    String modelId = baseProductService.getU3dModelId(mediaType, baseProduct_);
                    if( StringUtils.isNotBlank(modelId) ){
                        ResModel resModel = resModelService.get(Integer.valueOf(modelId));
                        if( resModel != null ){
                            /*File modelFile = new File(Utils.getValue("app.upload.root","")+resModel.getModelPath());*/
                        	File modelFile = new File(Utils.getAbsolutePath(resModel.getModelPath(), Utils.getAbsolutePathType.encrypt));
                            if( modelFile.exists() ){
                                groupDetail.setU3dModelPath(resModel.getModelPath());
                            }
                        }
                    }
                    // 产品大类信息
                    String typeValue = baseProduct_.getProductTypeValue();
                    if( StringUtils.isNotBlank(typeValue) ){
                        SysDictionary dlDic = sysDictionaryService.findOneByTypeAndValue("productType", Integer.valueOf(typeValue));
                        groupDetail.setProductTypeValue(dlDic.getValue());
                        groupDetail.setProductTypeCode(dlDic.getValuekey());
                        groupDetail.setProductTypeName(dlDic.getName());

                        // 产品小类
                        Integer smallType = baseProduct_.getProductSmallTypeValue();
                        if( smallType != null && smallType.intValue() > 0 ){
                            SysDictionary xlDic = sysDictionaryService.findOneByTypeAndValue(dlDic.getValuekey(), smallType);
                            groupDetail.setBgWall(Utils.getIsBgWall(StringUtils.trimToEmpty(xlDic.getValuekey())));
                            groupDetail.setProductSmallTypeValue(xlDic.getValue().toString());
                            groupDetail.setProductSmallTypeCode(xlDic.getValuekey());
                            groupDetail.setProductSmallTypeName(xlDic.getName());

                            String rootType = StringUtils.isEmpty(xlDic.getAtt1()) ? "2" : xlDic.getAtt1().trim();
                            groupDetail.setRootType(rootType);
                        }
                    }
                    groupDetail.setParentProductId(baseProduct_.getParentId());
                
                    // 长宽高
                    groupDetail.setProductWidth(baseProduct_.getProductWidth());
                    groupDetail.setProductHeight(baseProduct_.getProductHeight());
                    groupDetail.setProductLength(baseProduct_.getProductLength());

                    // 父类产品分类信息
                    if( baseProduct_.getParentId() != null && baseProduct_.getParentId().intValue() > 0 ){
                        BaseProduct parentProduct = baseProductService.get(baseProduct_.getParentId());
                        if( parentProduct != null ){
                            String parentTypeValue = parentProduct.getProductTypeValue();
                            if( StringUtils.isNotBlank(parentTypeValue) ){
                                SysDictionary dlDic = sysDictionaryService.findOneByTypeAndValue("productType", Integer.valueOf(parentTypeValue));
                                groupDetail.setParentTypeValue(dlDic.getValue());
                                groupDetail.setParentTypeCode(dlDic.getValuekey());
                                groupDetail.setParentTypeName(dlDic.getName());
                            }
                        }
                        groupDetail.setParentProductId(baseProduct_.getParentId());
                    }

                    // 长宽高
                    groupDetail.setProductWidth(baseProduct_.getProductWidth());
                    groupDetail.setProductHeight(baseProduct_.getProductHeight());
                    groupDetail.setProductLength(baseProduct_.getProductLength());

                    /*处理拆分材质产品返回默认材质*/
                    String splitTexturesInfo = baseProduct_.getSplitTexturesInfo();
                    Integer isSplit=0;
                    List<SplitTextureDTO> splitTextureDTOList=null;
                    if(StringUtils.isNotBlank(splitTexturesInfo)){
                        Map<String,Object> map=baseProductService.dealWithSplitTextureInfo(baseProduct_.getId(), splitTexturesInfo,"choose");
                        isSplit=(Integer) map.get("isSplit");
                        splitTextureDTOList=(List<SplitTextureDTO>) map.get("splitTexturesChoose");
                    }else{
                        /*普通产品*/
                        String materialIds = baseProduct_.getMaterialPicIds();
                        Integer materialId=0;
                        if(StringUtils.isNotBlank(materialIds)){
                            List<String> materialIdStrList=Utils.getListFromStr(materialIds);
                            if(materialIdStrList!=null&&materialIdStrList.size()>0){
                                materialId=Integer.valueOf(materialIdStrList.get(0));
                            }
                        }
                        if(materialId!=null&&materialId>0){
                            ResTexture resTexture=resTextureService.get(materialId);
                            if(resTexture!=null){
                                splitTextureDTOList=new ArrayList<SplitTextureDTO>();
                                List<ResTextureDTO> resTextureDTOList=new ArrayList<ResTextureDTO>();
                                SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", null);
                                ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture);
                                groupDetail.setMaterialPicPaths(new String[]{resTextureDTO.getPath()});
                                resTextureDTO.setKey(splitTextureDTO.getKey());
                                resTextureDTO.setProductId(baseProduct.getId());
                                resTextureDTOList.add(resTextureDTO);
                                splitTextureDTO.setList(resTextureDTOList);
                                splitTextureDTOList.add(splitTextureDTO);
                            }
                        }
                    }
                    groupDetail.setIsSplit(isSplit);
                    groupDetail.setSplitTexturesChoose(splitTextureDTOList);
                    /*处理拆分材质产品返回默认材质->end*/
                    
                    //在组合产品查询列表 中 增加产品属性
                    Map<String,String> map = new HashMap<String,String>();
                    map = productAttributeService.getPropertyMap(baseProduct.getId());
                    baseProduct.setPropertyMap(map);
                    
                    // 产品规则
                    Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(), baseProduct.getProductTypeMark(),baseProduct.getProductSmallTypeMark(),spaceCommonId,null,new DesignRules(),map);
                    groupDetail.setRulesMap(rulesMap);
                    groupDetail.setProductStructureId(entity.getProductStructureId());
                    details.add(groupDetail);
                }
                groupProduct.setStructureProductList(details);
            }
        }
        return groupProduct;
    }

	/**
	 * 获取组合类型
	 * 
	 * @author huangsongbo
	 * @param groupTypeMap 类似于缓存的效果
	 * @param productGroupId
	 * @return
	 */
    @Override
	public Integer getGroupType(Map<Integer, Integer> groupTypeMap, Integer productGroupId) {
		
		// *参数验证 ->start
		if(groupTypeMap == null) {
			return null;
		}
		if(productGroupId == null) {
			return null;
		}
		// *参数验证 ->end
		
		if(groupTypeMap.containsKey(productGroupId)) {
			return groupTypeMap.get(productGroupId);
		}else {
			GroupProduct groupProduct = this.get(productGroupId);
			if(groupProduct != null) {
				groupTypeMap.put(productGroupId, groupProduct.getCompositeType());
				return groupProduct.getCompositeType();
			}
		}
		return null;
	}

    /**
     * 方案已使用组合列表
     * @author xiaoxc
     * @param designPlanId
     * @param start
     * @param limit
     * @param loginUser
     * @param spaceCommonId
     * @param groupId
     * @return
     */
    @Override
    public ResponseEnvelope<SearchProductGroupResult> getPlanGroupList(Integer designPlanId, Integer start, Integer limit, LoginUser loginUser, String msgId, Integer spaceCommonId, Integer groupId) {
        List<SearchProductGroupResult> list = new ArrayList<>();
        SearchProductGroupResult groupProduct = null;
        List<ProductGroupBaseInfoModel> groupList = null;
        List<Integer> groupIDSList = new ArrayList<>();
        Integer compositeType = 0;
        if (groupId != null && groupId.intValue() > 0) {
            GroupProduct gp = groupProductService.get(groupId);
            if (gp != null) {
                compositeType = gp.getCompositeType();
            }
        }
        //查询满足条件的组合
        int count = productGroupDao.getPlanGroupCount(designPlanId,compositeType);
        if (count > 0) {
            groupList = productGroupDao.getPlanGroupList(designPlanId,compositeType,start,limit);
            for (ProductGroupBaseInfoModel groupMode : groupList) {
                groupIDSList.add(groupMode.getGroupID());
                groupProduct = new SearchProductGroupResult();
                if (groupMode.getFilePath() != null && !groupMode.getFilePath().trim().isEmpty()) {
                    groupProduct.setFilePath(groupMode.getFilePath());
                }else{
                    groupProduct.setGroupConfig(groupMode.getLocation());
                }
                groupProduct.setGroupId(groupMode.getGroupID());
                groupProduct.setGroupCode(groupMode.getGroupCode());
                groupProduct.setGroupName(groupMode.getGroupName());
                groupProduct.setPicPath(groupMode.getPicPath());

                /** 获取平台个性化信息 */
                productPlatformService.getUseSearchProductGroupResultInfo(groupMode.getGroupID(), PlatformConstants.PC_2B,groupProduct);

                //组合详情info
                groupProduct = getGroupProductDetails(groupProduct, groupMode.getGroupID(), loginUser.getMediaType(), spaceCommonId);
                list.add(groupProduct);
            }

            // 根据 userid 和 productgroupid 获取收藏
            Map<String, Object> params = new HashMap<>();
            params.put("userID", loginUser.getId());
            params.put("groupIDList", groupIDSList);
            List<Map<String, Integer>> collectedGroupList = groupProductServiceV2.getUserCollectedProductGroupByIDS(loginUser.getId(), groupIDSList);

            if ( collectedGroupList != null && collectedGroupList.size() > 0 ) {
                // 说明用户对产品组有收藏，进行遍历分析
                // 首先把collectedGroupList变成一个 map, (实际上 set 也可以)
                Map<Integer, String> tempMap = new HashMap<>();
                for (Map<String, Integer> collectedGroup : collectedGroupList) {
                    tempMap.put( (Integer)collectedGroup.get("groupID") , "");
                }

                // 遍历 resultList ，设置用户是否已经收藏
                for (SearchProductGroupResult groupResult : list) {
                    Integer groupID = groupResult.getGroupId();
                    if ( tempMap.get(groupID) != null ) {
                        // 收藏了
                        groupResult.setCollectState(GROUP_COLLECTED_STATUS.collected.ordinal());
                    }
                    else {
                        // 木有收藏
                        groupResult.setCollectState(GROUP_COLLECTED_STATUS.not_collect.ordinal());
                    }
                }
            }
            else {}
        }

        return new ResponseEnvelope<SearchProductGroupResult>(count, list, msgId);
    }

    /**组合详情info
     * @author xiaoxc
     * @param groupProduct
     * @param groupId
     * @param mediaType
     * @return
     */
    public SearchProductGroupResult getGroupProductDetails(SearchProductGroupResult groupProduct, Integer groupId, String mediaType ,Integer spaceCommonId){
        List<SearchProductGroupDetail> details = null;
        SearchProductGroupDetail groupDetail = null;
        //通过组合id 获取 组合产品,产品基础属性，并且获取相关资源
        List<GroupProductDetails> groupProductDetails = groupProductDetailsService.getDataAndResourcesByGroupId(groupId);
        if( groupProductDetails != null && groupProductDetails.size() > 0 ){
            details = new ArrayList<>();
            for (GroupProductDetails detailEntity : groupProductDetails) {
                groupDetail = new SearchProductGroupDetail();
                //特殊情况
                if(detailEntity.getChartletProductModelId()!=null&&detailEntity.getChartletProductModelId().intValue()>0){
                    BaseProduct baseProduct = baseProductService.get(detailEntity.getChartletProductModelId());
                    if (baseProduct != null) {
                        ResModel resModel = resModelService.get(baseProduct.getWindowsU3dModelId());
                        if (resModel != null) {
                            groupDetail.setU3dModelPath(resModel.getModelPath());
                            groupDetail.setTextureProductModelId(detailEntity.getChartletProductModelId());  /**贴图产品的模型id*/
                            groupDetail.setModelMinHeight(resModel.getMinHeight());
                        }
                    }
                }else{
                    groupDetail = new SearchProductGroupDetail();
                    groupDetail.setU3dModelPath(detailEntity.getModelPath());
                    groupDetail.setModelMinHeight(detailEntity.getMinHeight());
                }
                groupDetail.setProductCode(detailEntity.getProductCode());
                groupDetail.setProductIndex(detailEntity.getProductIndex());
                groupDetail.setProductId(detailEntity.getProductId());
                groupDetail.setProductGroupId(detailEntity.getGroupId());
                groupDetail.setCameraView(detailEntity.getCameraView());
                groupDetail.setCameraLook(detailEntity.getCameraLook());
                int isMain = detailEntity.getIsMain()==null?0:detailEntity.getIsMain();
                groupDetail.setIsMainProduct(isMain);
                // 组装产品模型地址
                BaseProduct baseProduct_ = baseProductService.get(detailEntity.getProductId());
                if( baseProduct_ != null ){
                    String modelId = baseProductService.getU3dModelId(mediaType, baseProduct_);
                    if( StringUtils.isNotBlank(modelId) ){
                        ResModel resModel = resModelService.get(Integer.valueOf(modelId));
                        if( resModel != null ){
                            /*File modelFile = new File(Utils.getValue("app.upload.root","")+resModel.getModelPath());*/
                            File modelFile = new File(Utils.getAbsolutePath(resModel.getModelPath(), Utils.getAbsolutePathType.encrypt));
                            if( modelFile.exists() ){
                                groupDetail.setU3dModelPath(resModel.getModelPath());
                            }
                        }
                    }
                    // 产品大类信息
                    String typeValue = baseProduct_.getProductTypeValue();
                    if( StringUtils.isNotBlank(typeValue) ){
                        SysDictionary dlDic = sysDictionaryService.findOneByTypeAndValue("productType", Integer.valueOf(typeValue));
                        groupDetail.setProductTypeValue(dlDic.getValue());
                        groupDetail.setProductTypeCode(dlDic.getValuekey());
                        groupDetail.setProductTypeName(dlDic.getName());

                        // 产品小类
                        Integer smallType = baseProduct_.getProductSmallTypeValue();
                        if( smallType != null && smallType.intValue() > 0 ){
                            SysDictionary xlDic = sysDictionaryService.findOneByTypeAndValue(dlDic.getValuekey(), smallType);
                            groupDetail.setBgWall(Utils.getIsBgWall(StringUtils.trimToEmpty(xlDic.getValuekey())));
                            groupDetail.setProductSmallTypeValue(xlDic.getValue().toString());
                            groupDetail.setProductSmallTypeCode(xlDic.getValuekey());
                            groupDetail.setProductSmallTypeName(xlDic.getName());

                            String rootType = StringUtils.isEmpty(xlDic.getAtt1()) ? "2" : xlDic.getAtt1().trim();
                            groupDetail.setRootType(rootType);
                        }
                    }
                    groupDetail.setParentProductId(baseProduct_.getParentId());

                    // 长宽高
                    groupDetail.setProductWidth(baseProduct_.getProductWidth());
                    groupDetail.setProductHeight(baseProduct_.getProductHeight());
                    groupDetail.setProductLength(baseProduct_.getProductLength());

                    // 父类产品分类信息
                    if( baseProduct_.getParentId() != null && baseProduct_.getParentId().intValue() > 0 ){
                        BaseProduct parentProduct = baseProductService.get(baseProduct_.getParentId());
                        if( parentProduct != null ){
                            String parentTypeValue = parentProduct.getProductTypeValue();
                            if( StringUtils.isNotBlank(parentTypeValue) ){
                                SysDictionary dlDic = sysDictionaryService.findOneByTypeAndValue("productType", Integer.valueOf(parentTypeValue));
                                groupDetail.setParentTypeValue(dlDic.getValue());
                                groupDetail.setParentTypeCode(dlDic.getValuekey());
                                groupDetail.setParentTypeName(dlDic.getName());
                            }
                        }
                        groupDetail.setParentProductId(baseProduct_.getParentId());
                    }

                    // 长宽高
                    groupDetail.setProductWidth(baseProduct_.getProductWidth());
                    groupDetail.setProductHeight(baseProduct_.getProductHeight());
                    groupDetail.setProductLength(baseProduct_.getProductLength());

                    /*处理拆分材质产品返回默认材质*/
                    String splitTexturesInfo = baseProduct_.getSplitTexturesInfo();
                    Integer isSplit=0;
                    List<SplitTextureDTO> splitTextureDTOList=null;
                    if(StringUtils.isNotBlank(splitTexturesInfo)){
						// 对比组合中多材质默认选中的材质在不在产品表中的多材质信息内
						if(StringUtils.isNotEmpty(detailEntity.getSplitTexturesChooseInfo())) {
							splitTexturesInfo = designPlanProductService.matchSplitTexturesInfo(splitTexturesInfo, detailEntity.getProductId(), detailEntity.getSplitTexturesChooseInfo());
						}
                        Map<String,Object> map=baseProductService.dealWithSplitTextureInfo(baseProduct_.getId(), splitTexturesInfo,"choose");
                        isSplit=(Integer) map.get("isSplit");
                        splitTextureDTOList=(List<SplitTextureDTO>) map.get("splitTexturesChoose");
                    }else{
                        /*普通产品*/
                        String materialIds = baseProduct_.getMaterialPicIds();
                        Integer materialId=0;
                        if(StringUtils.isNotBlank(materialIds)){
                            List<String> materialIdStrList=Utils.getListFromStr(materialIds);
                            if(materialIdStrList!=null&&materialIdStrList.size()>0){
                                materialId=Integer.valueOf(materialIdStrList.get(0));
                            }
                        }
                        if(materialId!=null&&materialId>0){
                            ResTexture resTexture=resTextureService.get(materialId);
                            if(resTexture!=null){
                                splitTextureDTOList=new ArrayList<SplitTextureDTO>();
                                List<ResTextureDTO> resTextureDTOList=new ArrayList<ResTextureDTO>();
                                SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", null);
                                ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture);
                                groupDetail.setMaterialPicPaths(new String[]{resTextureDTO.getPath()});
                                resTextureDTO.setKey(splitTextureDTO.getKey());
                                resTextureDTO.setProductId(groupDetail.getProductId());
                                resTextureDTOList.add(resTextureDTO);
                                splitTextureDTO.setList(resTextureDTOList);
                                splitTextureDTOList.add(splitTextureDTO);
                            }
                        }
                    }
                    groupDetail.setIsSplit(isSplit);
                    groupDetail.setSplitTexturesChoose(splitTextureDTOList);
                    groupDetail.setSplitTexturesChooseInfo(splitTexturesInfo);
                    /*处理拆分材质产品返回默认材质->end*/

                    //在组合产品查询列表 中 增加产品属性
                    Map<String,String> map = new HashMap<String,String>();
                    map = productAttributeService.getPropertyMap(groupDetail.getProductId());
                    groupDetail.setPropertyMap(map);
//                  // 产品规则
                    Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct_.getId().toString(), baseProduct_.getProductTypeMark(),baseProduct_.getProductSmallTypeMark(),spaceCommonId,null,new DesignRules(),map);
                    groupDetail.setRulesMap(rulesMap);
                    details.add(groupDetail);
                }
                groupProduct.setGroupProductDetails(details);
            }
        }
        return groupProduct;
    }

	@Override
	public GroupProduct getGroupProductByCache(Map<Integer, GroupProduct> groupInfoMap, Integer groupId) {
		// 参数验证/处理 ->start
		if(groupId == null) {
			logger.error("GroupProductServiceImpl.getGroupProductByCache ->groupId = null");
		}
		if(groupInfoMap == null) {
			groupInfoMap = new HashMap<Integer, GroupProduct>();
		}
		// 参数验证/处理 ->end
		
		GroupProduct groupProduct = null;
		
		if(groupInfoMap.containsKey(groupId)) {
			groupProduct = groupInfoMap.get(groupId);
		}else {
			groupProduct = groupProductService.get(groupId);
			if(groupProduct != null) {
				// 查询主产品关联的过滤属性信息
				/*groupProduct.setProductFilterPropList(this.getProductFilterPropList(groupId));*/
				groupInfoMap.put(groupId, groupProduct);
			}
		}
		return groupProduct;
	}

	/**
	 * 获取组合主产品的过滤属性信息
	 * 
	 * @author huangsongbo
	 * @param groupId
	 * @return
	 */
	private List<ProductPropsSimple> getProductFilterPropList(Integer groupId) {
		// 参数验证/处理 ->start
		if(groupId == null) {
			return null;
		}
		// 参数验证/处理 ->end
		
		// 取得主产品id
		Integer mainProductId = this.getMainProductIdByGroupId(groupId);
		
		/*List<ProductPropsSimple> productFilterPropList = test(mainProductId);*/
		List<ProductPropsSimple> productFilterPropList = baseProductService.getProductFilterPropList(mainProductId);
		
		return productFilterPropList;
	}

	private Integer getMainProductIdByGroupId(Integer groupId) {
		// 参数验证/处理 ->start
		if(groupId == null) {
			return null;
		}
		// 参数验证/处理 ->end
		return groupProductMapper.getMainProductIdByGroupId(groupId);
	}

    /**
     * 建材家居组合列表
     * @param ResBrand
     * @return
     */
    @Override
    public List<GroupProductDetails> getBuildingHomeGroupList(BaseBrandSearch ResBrand) {
        return groupProductMapper.findBuildingHomeGroupList(ResBrand);
    }

    /**
     * 建材家居组合数量
     * @param ResBrand
     * @return
     */
    @Override
    public int getBuildingHomeGroupCount(BaseBrandSearch ResBrand) {
        return groupProductMapper.findBuildingHomeGroupCount(ResBrand);
    }

    /**
     * 查询组合配置文件地址
     * @param groupId
     * @return
     */
    @Override
    public String getGroupConfigByGroupId(Integer groupId) {
        return groupProductMapper.getGroupConfigByGroupId(groupId);
    }

    @Override
    public int getCountByGroupIdList(List<Integer> groupIdList) {
        return groupProductMapper.getCountByGroupIdList(groupIdList);
    }

    @Override
    public String createNewGroupProductCode() {
      String code = null;
      StringBuffer codeBuffer =new StringBuffer();
      //获取最大组合Id
      Integer maxId = groupProductMapper.getMaxGroupProductId() + 1;
      int idLength = maxId.toString().length();
      for (int i = 0; i < 10 - idLength; i++) {
        codeBuffer.append("0");
      }
      code = Constants.GROUP_PRODUCT_CODE_PREFIX + codeBuffer.toString() + maxId.toString();
      return code;
    }

}
