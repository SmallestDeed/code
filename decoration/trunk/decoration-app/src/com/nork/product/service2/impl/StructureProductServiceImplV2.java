package com.nork.product.service2.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.util.Constants;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.ServiceHint;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignCacher;
import com.nork.design.cache.DesignPlanCacher;
import com.nork.design.cache.DesignPlanProductCacher;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.model.UnityPlanProduct;
import com.nork.design.model.search.DesignTempletProductSearch;
import com.nork.design.model.search.DesignTempletSearch;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.GroupProductCacher;
import com.nork.product.controller2.web.StructureProductParameter;
import com.nork.product.dao.StructureProductMapper;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.StructureProduct;
import com.nork.product.model.StructureProductDetails;
import com.nork.product.model.StructureProductStatusCode;
import com.nork.product.model.result.SearchStructureProductDetailResult;
import com.nork.product.model.result.SearchStructureProductResult;
import com.nork.product.model.search.StructureProductSearch;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service.StructureProductDetailsService;
import com.nork.product.service2.StructureProductServiceV2;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.user.model.UserTypeCode;

/**   
 * @Title: StructureProductServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-结构表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-12-02 15:32:05
 * @version V1.0   
 */
@Service("structureProductService2")
public class StructureProductServiceImplV2 implements StructureProductServiceV2 {
	private static Logger logger = Logger.getLogger(StructureProductServiceImplV2.class);
	@Autowired
	private StructureProductMapper structureProductMapper;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private StructureProductDetailsService structureProductDetailsService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
	@Autowired
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private GroupProductService groupProductService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private ResDesignService resDesignService;
	
	/**
	 * 新增数据
	 *
	 * @param structureProduct
	 * @return  int 
	 */
	@Override
	public int add(StructureProduct structureProduct) {
		structureProductMapper.insertSelective(structureProduct);
		return structureProduct.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param structureProduct
	 * @return  int 
	 */
	@Override
	public int update(StructureProduct structureProduct) {
		return structureProductMapper
				.updateByPrimaryKeySelective(structureProduct);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return structureProductMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  StructureProduct 
	 */
	@Override
	public StructureProduct get(Integer id) {
		return structureProductMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  structureProduct
	 * @return   List<StructureProduct>
	 */
	@Override
	public List<StructureProduct> getList(StructureProduct structureProduct) {
	    return structureProductMapper.selectList(structureProduct);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @return   int
	 */
	@Override
	public int getCount(StructureProductSearch structureProductSearch){
		return  structureProductMapper.selectCount(structureProductSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @return   List<StructureProduct>
	 */
	@Override
	public List<StructureProduct> getPaginatedList(
			StructureProductSearch structureProductSearch) {
		return structureProductMapper.selectPaginatedList(structureProductSearch);
	}

	/**
	 * 通过结构组标记查找结构
	 * @author huangsongbo
	 * @return
	 */
	public List<StructureProduct> findAllByGroupFlag(String groupFlag,List<Integer> statusList, Integer templetId, Integer start, Integer limit) {
		StructureProductSearch structureProductSearch=new StructureProductSearch();
		structureProductSearch.setStart(start);
		structureProductSearch.setLimit(limit);
		structureProductSearch.setGroupFlag(groupFlag);
		structureProductSearch.setStatusList(statusList);
		if(templetId!=null&&templetId>0){
			structureProductSearch.setTempletId(templetId);
		}
		return this.getPaginatedList(structureProductSearch);
	}

	public SearchStructureProductResult getSearchResult(StructureProduct structureProduct, Integer spaceCommonId, String mediaType, Integer planProductId, HttpServletRequest request) {
		SearchStructureProductResult searchStructureProductResult=new SearchStructureProductResult
				(structureProduct.getId(), structureProduct.getStructureName(), structureProduct.getStructureCode(),
						"", "", null,"","");
		/*封面图片路径*/
		Integer picId=structureProduct.getPicId();
		if(picId!=null&&picId>0){
			ResPic resPic=resPicService.get(picId);
			if(resPic!=null){
				searchStructureProductResult.setPicPath(resPic.getPicPath());
			}
			Integer smallPicId = Utils.getSmallPicId(resPic,"ipad");
			if( smallPicId != null && smallPicId.intValue() > 0 ){
				ResPic resSmallPic = resPicService.get(smallPicId);
				searchStructureProductResult.setSmallPicPath(resSmallPic==null?"":resSmallPic.getPicPath());
			}
		}
		Integer descPicPath = structureProduct.getDescPicId();
		if(descPicPath!=null&&descPicPath>0) {
			ResPic resPic = resPicService.get(descPicPath);
			if (resPic != null) {
				searchStructureProductResult.setDescPicPath(resPic.getPicPath());
			}
		}
		searchStructureProductResult.setStructureDes(structureProduct.getRemark());
		/*封面图片路径->end*/
		/*config(从文件中取)*/
		Integer fileId=structureProduct.getConfigFileId();
		if(fileId!=null&&fileId>0){
			ResFile resFile=resFileService.get(fileId);
			if(resFile!=null){
				String url=Tools.getRootPath(resFile.getFilePath(),"")+resFile.getFilePath();
				String config=FileUploadUtils.getFileContext(url);
				searchStructureProductResult.setStructureConfig(config);
			}
		}
		/*config(从文件中取)->end*/
		/*明细list*/
		/*查询明细*/
		List<SearchStructureProductDetailResult> searchStructureProductDetailResults=new ArrayList<SearchStructureProductDetailResult>();
		List<StructureProductDetails> structureProductDetailList=structureProductDetailsService.findAllByStructureId(structureProduct.getId());
		if(structureProductDetailList!=null&&structureProductDetailList.size()>0){
			for(StructureProductDetails structureProductDetail:structureProductDetailList){
				Integer productId=structureProductDetail.getProductId();
				BaseProduct baseProduct=null;
				if(productId!=null&&productId>0){
					if(Utils.enableRedisCache())
						baseProduct=BaseProductCacher.get(productId);
					else
						baseProduct=baseProductService.get(productId);
				}
				if(baseProduct==null)
					continue;
				String productTypeValue = baseProduct.getProductTypeValue();
				if(StringUtils.isNotBlank(productTypeValue)){
					SysDictionary productTypeSysDic = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(productTypeValue));
					baseProduct.setProductTypeMark(productTypeSysDic.getValuekey());
					Integer productSmallTypeValue = baseProduct.getProductSmallTypeValue();
					if (productTypeSysDic.getValue() != null && productSmallTypeValue != null) {
						SysDictionary productSmallTypeSysDic = sysDictionaryService.getSysDictionaryByValue(productTypeSysDic.getValuekey(), productSmallTypeValue);
						baseProduct.setProductSmallTypeMark(productSmallTypeSysDic.getValuekey());
					}
				}
				SearchStructureProductDetailResult searchStructureProductDetailResult=baseProductService.getStructureDetailsSearch(baseProduct, mediaType);
				searchStructureProductDetailResult.setCameraLook(structureProductDetail.getCameraLook());
				searchStructureProductDetailResult.setCameraView(structureProductDetail.getCameraView());
				searchStructureProductDetailResult.setProductStructureId(structureProduct.getId());
				searchStructureProductDetailResult.setTemplateProductId(productId);
				/*rulesMap*/
				Map<String,String> map = new HashMap<String,String>();
				map = productAttributeService.getPropertyMap(baseProduct.getId());
				baseProduct.setPropertyMap(map);
				searchStructureProductDetailResult.setStructureProductSign(map.get("structureSign"));
				Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(),
						baseProduct.getProductTypeMark(),baseProduct.getProductSmallTypeMark(),spaceCommonId,null,new DesignRules(),map);
				searchStructureProductDetailResult.setRulesMap(rulesMap);
				searchStructureProductDetailResult.setPropertyMap(map);
				/*rulesMap->end*/
				searchStructureProductDetailResults.add(searchStructureProductDetailResult);
			}
		}
		searchStructureProductResult.setStructureProductList(searchStructureProductDetailResults);
		/*明细list->end*/
		return searchStructureProductResult;
	}

	@Override
	public int getCountByGroupFlag(String groupFlag, List<Integer> statusList,Integer start, Integer limit) {
		StructureProductSearch structureProductSearch=new StructureProductSearch();
		structureProductSearch.setStart(start);
		structureProductSearch.setLimit(limit);
		structureProductSearch.setStatusList(statusList);
		structureProductSearch.setGroupFlag(groupFlag);
		return this.getCount(structureProductSearch);
	}

	/**
	 * 应用到这个结构的样板房产品数据回复成单品状态(非结构)
	 * @author huangsongbo
	 * @param structureId
	 */
	public void updateDesignTempletStructureRelationByStructureId(Integer structureId) {
		structureProductMapper.updateDesignTempletStructureRelationByStructureId(structureId);
	}



	@Override
	public List<StructureProduct> getStructureObject(StructureProductSearch structureProductSearch){
		return structureProductMapper.getStructureObject(structureProductSearch);
	}

	public SearchStructureProductResult getSearchStructureProductResult(StructureProduct structureProduct){
		SearchStructureProductResult searchStructureProductResult=new SearchStructureProductResult
				(structureProduct.getId(), structureProduct.getStructureName(), structureProduct.getStructureCode(),
						"", "", null,"","");
		/*config(从文件中取)*/
		Integer fileId=structureProduct.getConfigFileId();
		if( fileId != null && fileId > 0 ){
			ResFile resFile = resFileService.get(fileId);
			if( resFile != null ){
				String url = Tools.getRootPath(resFile.getFilePath(), "")+resFile.getFilePath();
				String config=FileUploadUtils.getFileContext(url);
				searchStructureProductResult.setStructureConfig(config);
			}
		}
		/*查询明细*/
		List<SearchStructureProductDetailResult> searchStructureProductDetailResults=new ArrayList<SearchStructureProductDetailResult>();
		List<StructureProductDetails> structureProductDetailList = structureProductDetailsService.findAllByStructureId(structureProduct.getId());
		if( structureProductDetailList!=null && structureProductDetailList.size()>0 ){
			for (StructureProductDetails structureProductDetail:structureProductDetailList) {
				Integer productId=structureProductDetail.getProductId();
				BaseProduct baseProduct=null;
				if(productId != null && productId > 0 ){
					if(Utils.enableRedisCache())
						baseProduct=BaseProductCacher.get(productId);
					else
						baseProduct=baseProductService.get(productId);
				}
				if(baseProduct==null) {
					continue;
				}
				SearchStructureProductDetailResult searchStructureProductDetailResult = new SearchStructureProductDetailResult();
				searchStructureProductDetailResult.setCameraLook(structureProductDetail.getCameraLook());
				searchStructureProductDetailResult.setCameraView(structureProductDetail.getCameraView());
				searchStructureProductDetailResult.setProductStructureId(structureProduct.getId());
				searchStructureProductDetailResult.setProductId(baseProduct.getId());
				searchStructureProductDetailResult.setProductCode(baseProduct.getProductCode());
				Map<String,String> map = new HashMap<String,String>();
				map = productAttributeService.getPropertyMap(baseProduct.getId());
				searchStructureProductDetailResult.setStructureProductSign(map.get("structureSign"));
				searchStructureProductDetailResults.add(searchStructureProductDetailResult);
			}
		}
		searchStructureProductResult.setStructureProductList(searchStructureProductDetailResults);
		return searchStructureProductResult;
	}

	@Override
	public void updateStatus(Integer oldStatus, Integer newStatus) {
		structureProductMapper.updateStatus(oldStatus, newStatus);
	}

	@Override
	public int deleteBatch(List<Integer> list) {
		return structureProductMapper.deleteBatch(list);
	}

	
	/**
	 * 通过编码存入结构类型和序号（方便一键装修结构匹配）
	 * 
	 * @author xiaoxc
	 * @param structureCode
	 * @param structureProduct
	 * @return StructureProduct
	 */
	public StructureProduct saveStructureTypeOrNumber(String structureCode, StructureProduct structureProduct) {
		if (StringUtils.isNotEmpty(structureCode)) {
			String number = structureCode.substring(structureCode.lastIndexOf("_") + 1, structureCode.length());
			String strCode = structureCode.substring(0, structureCode.lastIndexOf("_"));
			String type = strCode.substring(strCode.lastIndexOf("_") + 1, strCode.length());
			structureProduct.setStructureType(type);
			structureProduct.setStructureNumber(number == null ? 0 : Utils.getIntValue(number));
		}
		return structureProduct;
	}
	
	@Override
	public ServiceHint createStructureProduct(StructureProductParameter sParameter,int userId) {
		ServiceHint serviceHint = new ServiceHint();	//要返回的错误信息对象
		Integer templetId = 0;
		DesignTempletSearch designTempletSearch = new DesignTempletSearch();
		designTempletSearch.setIsDeleted(0);
		designTempletSearch.setStart(0);
		designTempletSearch.setLimit(1);
		designTempletSearch.setDesignCode(sParameter.getTempletCode());
		List<DesignTemplet> designTemplets = designTempletService.getPaginatedList(designTempletSearch,userId);

		if (designTemplets == null || designTemplets.size() == 0){
			serviceHint.setTrueOrFalse(false);
			serviceHint.setMessage("没有找到指定样板房,code:"+sParameter.getTempletCode());
			serviceHint.setMsgId(sParameter.getMsgId());
			return serviceHint;
		}
		DesignTemplet designTemplet = designTemplets.get(0);
		templetId = designTemplet.getId();
		/* 验证结构组标记是否正确(相同结构组标记,样板房id必须相同) */
		StructureProductSearch structureProductSearch2 = new StructureProductSearch();
		structureProductSearch2.setStructureCode(sParameter.getStructureCode());
		structureProductSearch2.setStart(0);
		structureProductSearch2.setLimit(1);
		List<StructureProduct> structureProducts2 = getPaginatedList(structureProductSearch2);
		if (structureProducts2 != null && structureProducts2.size() > 0) {
			if (!templetId.equals(structureProducts2.get(0).getTempletId())) {
				serviceHint.setTrueOrFalse(false);
				serviceHint.setMessage("该结构组标记已被其他样板房的结构使用");
				serviceHint.setMsgId(sParameter.getMsgId());
				return serviceHint;
			}
		}
		/* 获取文件路径(目录) */
		String configFileUploadPath = Utils.getValue("product.structureProduct.configFile.upload.path", "/product/structureProduct/configFile/");
		String appUploadRoot =Tools.getRootPath(configFileUploadPath, "/home/nork/resources");
		String fileDirStr = appUploadRoot + configFileUploadPath;
		File fileDir = new File(fileDirStr);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		File txtFile = null;
		PrintStream ps = null;
		try {
			txtFile = File.createTempFile("groupLocation_" + sParameter.getStructureCode() + "_", ".txt", fileDir);
			ps = new PrintStream(new FileOutputStream(txtFile));
			ps.println(sParameter.getConfig());
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				ps.close();
		}
		/* 保存配置文件(物理文件)->end */
		/* 保存配置文件信息(resFile表) */
		ResFile resFile = new ResFile();
		String fileName = txtFile.getName();
		String fileCode = System.currentTimeMillis() + "_" + Utils.generateRandomDigitString(6);
		resFile.setFileCode(fileCode);
		resFile.setFileName(fileName.substring(0, fileName.lastIndexOf(".")));
		resFile.setFileOriginalName(fileName.substring(0, fileName.lastIndexOf(".")));
		resFile.setFileType("结构配置文件");
		resFile.setFileLevel("0");
		resFile.setFileSize(txtFile.length() + "");
		resFile.setFilePath(configFileUploadPath + fileName);
		resFile.setFileKey("product.structureProduct.configFile");
		resFile.setFileOrdering(0);
		resFile.setSysCode(fileCode);
		resFile.setIsDeleted(0);
		resFile.setCreator("nologin");
		resFile.setModifier("nologin");
		Date now = new Date();
		resFile.setGmtCreate(now);
		resFile.setGmtModified(now);
		resFile.setFileSuffix(".txt");
		Integer fileId = resFileService.add(resFile);
		/* 保存配置文件信息(resFile表)->end */
		StructureProduct structureProduct = null;
		StructureProductSearch structureProductSearch = new StructureProductSearch();
		structureProductSearch.setStructureCode(sParameter.getStructureCode());
		structureProductSearch.setStart(0);
		structureProductSearch.setLimit(1);
		List<StructureProduct> structureProducts = getPaginatedList(structureProductSearch);
		if (structureProducts != null && structureProducts.size() > 0) {
			structureProduct = structureProducts.get(0);
			if (StringUtils.isNotBlank(sParameter.getStructureCode()))
				structureProduct.setStructureName(sParameter.getStructureCode());
			// 删除以前的结构明细(更新新的结构明细) ->start
			structureProductDetailsService.deleteByStructureId(structureProduct.getId());
			// 删除以前的结构明细(更新新的结构明细) ->end
			// 删除该样板房产品表中该结构的关系(需要重新绑定) ->start
			updateDesignTempletStructureRelationByStructureId(structureProduct.getId());
			// 删除该样板房产品表中该结构的关系(需要重新绑定) ->end
		} else {
			structureProduct = new StructureProduct();
			structureProduct.setStructureCode(sParameter.getStructureCode());
			structureProduct.setStatus(0);
			if (StringUtils.isNotBlank(sParameter.getStructureName()))
				structureProduct.setStructureName(sParameter.getStructureName());
			else
				structureProduct.setStructureName(sParameter.getStructureCode());
		}
		// 通过编码保存结构类型和序号
		this.saveStructureTypeOrNumber(sParameter.getStructureCode(), structureProduct);
		/* 填充其他信息 */
		structureProduct.setConfigFileId(fileId);
		if (StringUtils.isNotBlank(sParameter.getGroupFlag()))
			structureProduct.setGroupFlag(sParameter.getGroupFlag());
		structureProduct.setTempletId(templetId);
		if (structureProduct.getId() != null && structureProduct.getId() > 0)
			update(structureProduct);
		else {
			add(structureProduct);
		}

		/* 读取配置文件信息,保存结构明细 */
		JSONObject configJson = JSONObject.fromObject(sParameter.getConfig());
		JSONArray jsonArray = JSONArray.fromObject(configJson.get("ProductStructList"));
		if (jsonArray != null && jsonArray.size() > 0) {
			// 把要更新为结构的样板房产品数据查出来->为了判断该list里面是否有已经被定义为结构的产品->如果存在,则不更新样板房产品表
			List<DesignTempletProduct> designTempletProductListFLag = new ArrayList<DesignTempletProduct>();
			// 是否更新样板房产品表:true:是;false:不更新
			boolean flag = true;
			String dateTime = Utils.getCurrentDateTime(Utils.DATETIMESSS);
			String planGroupId = structureProduct.getId() + "_" + dateTime;
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				String productCode = jsonObject.getString("ProductCode");
				if (StringUtils.isBlank(productCode)) {
					continue;
				}
				BaseProduct baseProduct = baseProductService.findOneByCode(productCode.trim());
				if (baseProduct == null) {
					serviceHint.setTrueOrFalse(false);
					serviceHint.setMessage("产品未找到:baseProductCode:"+ productCode.trim());
					serviceHint.setMsgId(sParameter.getMsgId());
					return serviceHint;
				}
				/* 新建结构明细 */
				StructureProductDetails structureProductDetails = structureProductDetailsService
						.createByStructureIdAndProductId(structureProduct.getId(), baseProduct.getId());
				String cameraLook = null;
				String cameraView = null;
				if (jsonObject.containsKey("CameraLook"))
					cameraLook = jsonObject.getString("CameraLook");
				if (jsonObject.containsKey("CameraView"))
					cameraView = jsonObject.getString("CameraView");
				structureProductDetails.setCameraLook((cameraLook == null || "null".equals(cameraLook)) ? "" : cameraLook);
				structureProductDetails.setCameraView((cameraView == null || "null".equals(cameraView)) ? "" : cameraView);
				structureProductDetailsService.add(structureProductDetails);
				/* 该逻辑为根据code找出第一个进行更新 */
				if (flag) {
					DesignTempletProductSearch designTempletProductSearch = new DesignTempletProductSearch();
					designTempletProductSearch.setStart(0);
					designTempletProductSearch.setLimit(1);
					designTempletProductSearch.setDesignTempletId(templetId);
					designTempletProductSearch.setProductId(baseProduct.getId());
					designTempletProductSearch.setIsDeleted(0);
					List<DesignTempletProduct> designTempletProductList = designTempletProductService.getPaginatedList(designTempletProductSearch);
					if (designTempletProductList != null && designTempletProductList.size() > 0) {
						DesignTempletProduct designTempletProduct = designTempletProductList.get(0);
						// 判断为已经被定义了结构或组->break,取消更新样板房产品表
						Integer groupId = designTempletProduct.getProductGroupId();
						if (groupId != null && groupId > 0) {
							flag = false;
						}
						DesignTempletProduct newTempletProduct = new DesignTempletProduct();
						newTempletProduct.setId(designTempletProduct.getId());
						newTempletProduct.setGroupType(1);
						newTempletProduct.setPlanGroupId(planGroupId);
						newTempletProduct.setProductGroupId(structureProduct.getId());
						designTempletProductListFLag.add(designTempletProduct);
					}
				}
				/* 该逻辑为根据code找出第一个进行更新->end */
				/* 更新样板房产品数据->end */
			}
			// 更新样板房产品表
			if (flag) {
				for (DesignTempletProduct designTempletProduct : designTempletProductListFLag) {
					designTempletProductService.update(designTempletProduct);
				}
			}
		}
		/* 读取配置文件信息,保存结构明细->end */
		/* 回填配置文件的businessid */
		resFile.setBusinessId(structureProduct.getId());
		resFileService.update(resFile);
		/* 填充其他信息->end */
		return serviceHint;
	}

	/**
	 * 查询结构接口
	 */
	public StructureProductParameter searchStructureProduct(StructureProductParameter sParameter) {
		ServiceHint serviceHint = new ServiceHint();		//提示要返回的信息
		if (sParameter.getStart() == null)
			sParameter.setStart(-1);
		if (sParameter.getLimit() == null)
			sParameter.setLimit(-1);
		/* 参数验证->end */
		DesignPlanProduct designPlanProduct = null;
		if (Utils.enableRedisCache())
			designPlanProduct = DesignPlanProductCacher.get(sParameter.getPlanProductId());
		else
			designPlanProduct = designPlanProductService.get(sParameter.getPlanProductId());
		if (designPlanProduct == null) {
			serviceHint.setTrueOrFalse(false);
			serviceHint.setMessage("设计方案产品未找到:designPlanProductId:"+sParameter.getPlanProductId());
			serviceHint.setMsgId(sParameter.getMsgId());
			return sParameter;
		}
		Integer groupType = designPlanProduct.getGroupType();
		Integer groupId = designPlanProduct.getProductGroupId();
		String structureGroupFlag = "";
		Integer templetId = null;
		if ("0".equals(groupType)) {
			/* 识别为组合 */
			GroupProduct groupProduct = null;
			if (Utils.enableRedisCache())
				groupProduct = GroupProductCacher.get(groupId);
			else
				groupProduct = groupProductService.get(groupId);
			if (groupProduct == null) {
				logger.info("未找到组数据:groupId:" + groupId);
				serviceHint.setInts(0);
				serviceHint.setList(sParameter.getList());;
				serviceHint.setMsgId(sParameter.getMsgId());
				return sParameter;
			}
			structureGroupFlag = groupProduct.getGroupFlag();
			Integer structureId = groupProduct.getStructureId();
			if (structureId != null && structureId > 0) {
				StructureProduct structureProduct = get(structureId);
				if (structureProduct == null) {
					logger.info("------结构未找到:id:" + structureId);
				} else {
					templetId = structureProduct.getTempletId();
				}
			}
		} else if ("1".equals(groupType)) {
			/* 识别为结构 */
			StructureProduct structureProduct = get(groupId);
			if (structureProduct == null) {
				logger.info("未找到结构数据:structureId:" + groupId);
				serviceHint.setInts(0);
				serviceHint.setList(sParameter.getList());;
				serviceHint.setMsgId(sParameter.getMsgId());
				return sParameter;
			}
			structureGroupFlag = structureProduct.getGroupFlag();
			templetId = structureProduct.getTempletId();
		}
		if (StringUtils.isBlank(structureGroupFlag)) {
			logger.info("结构组标记为空值,所以返回空集合");
			serviceHint.setInts(0);
			serviceHint.setList(sParameter.getList());;
			serviceHint.setMsgId(sParameter.getMsgId());
			return sParameter;
		}
		// 更具用户类型查找不同状态的产品结构
		List<Integer> statusList = new ArrayList<Integer>();
		if (sParameter.getLoginUser().getUserType() == null) {
			logger.error("用户类型  userType 字段为空！");
		} else {
			String versionType = Utils.getPropertyName("app", "sys.version.type", "1")
					.trim();/* 1为外网 2 为内网 默认为外网 */
			if (UserTypeCode.USER_TYPE_INNER == sParameter.getLoginUser().getUserType() && "2".equals(versionType)) {// 内部用户
				statusList.add(StructureProductStatusCode.HAS_BEEN_PUTAWAY);
				statusList.add(StructureProductStatusCode.TESTING);
				statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
			} else if (UserTypeCode.USER_TYPE_OUTER_B2B == sParameter.getLoginUser().getUserType()
					|| UserTypeCode.USER_TYPE_OUTER_B2C == sParameter.getLoginUser().getUserType()) {// 外部用户
				statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
			} else {
				statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
			}
		}
		int total = getCountByGroupFlag(structureGroupFlag, statusList, sParameter.getStart(), sParameter.getLimit());
		sParameter.setTotal(total);
		if (total > 0) {
			List<StructureProduct> structureProducts = findAllByGroupFlag(structureGroupFlag,statusList, templetId, sParameter.getStart(), sParameter.getLimit());
			if (structureProducts != null && structureProducts.size() > 0) {
				for (StructureProduct structureProduct : structureProducts) {
					String mediaType = SystemCommonUtil.getMediaType(sParameter.getRequest());
					SearchStructureProductResult searchStructureProductResult = getSearchResult(structureProduct,
							sParameter.getSpaceCommonId(), mediaType, sParameter.getPlanProductId(), sParameter.getRequest());
					sParameter.getList().add(searchStructureProductResult);
				}
			}
		}
		
		return sParameter;
	}


	/**
	 * 替换结构接口
	 * @throws IllegalAccessException 
	 * @throws InvocationTargetException 
	 */
	public StructureProductParameter unityStructureProduct(StructureProductParameter sParameter)  {
		DesignPlan desPlan = null;
		desPlan = Utils.enableRedisCache() ? DesignCacher.get(sParameter.getDesignPlanId()) : 
			designPlanService.get(sParameter.getDesignPlanId());
		if (desPlan == null) {
			sParameter.getServiceHint().setTrueOrFalse(false);
			sParameter.getServiceHint().setMessage("方案不存在");
			sParameter.getServiceHint().setMsgId(sParameter.getMsgId());
			return sParameter;
		}
		try{
		// 替换配置内容
		List<UnityPlanProduct> list = designPlanService.batchSaveDesignPlan(desPlan, sParameter.getDesignPlanId(), sParameter.getGroupProductJson(),
				sParameter.getGroupId(), 0, "", sParameter.getContext(), sParameter.getPlanGroupId(), null,sParameter.getGroupType(),null,null,null,null,null);
		sParameter.setUlist(list);
		if (list == null) {
			sParameter.getServiceHint().setTrueOrFalse(false);
			sParameter.getServiceHint().setMessage("新增数据异常");
			sParameter.getServiceHint().setMsgId(sParameter.getMsgId());
			return sParameter;
		} else {
		// 获取配置路径
		String filePath = "";
		if (desPlan.getConfigFileId() != null) {
			ResDesign resDesign = null;
			resDesign = Utils.enableRedisCache() ? 
					ResourceCacher.getResDesign(desPlan.getConfigFileId()) : resDesignService.get(desPlan.getConfigFileId());
			/*filePath = Constants.UPLOAD_ROOT + resDesign.getFilePath().replace("/", "\\");
			if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
				filePath = Constants.UPLOAD_ROOT + resDesign.getFilePath();
			}*/
			filePath = Utils.dealWithPath(Utils.getAbsolutePath(resDesign.getFilePath(), null), null);
			/**
			 * TODO
			 * 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值，
			 * 则上传到web服务器。
			 **/
			Long startTimeUp = System.currentTimeMillis();
			Integer ftpUploadMethod = Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD, 1);
			boolean uploadFtpFlag = false;
			// 上传方式为2或者3表示文件在ftp上
			if (ftpUploadMethod == 2 || ftpUploadMethod == 3) {
				uploadFtpFlag = FtpUploadUtils.replaceFile(resDesign.getFilePath(), sParameter.getContext());
			} else if (ftpUploadMethod == 1) {
				uploadFtpFlag = Utils.replaceFile(filePath, sParameter.getContext());
			}
			Long endTimeUp = System.currentTimeMillis();
			if (!uploadFtpFlag) {
				sParameter.getServiceHint().setTrueOrFalse(false);
				sParameter.getServiceHint().setMessage("写入文件失败");
				sParameter.getServiceHint().setMsgId(sParameter.getMsgId());
				return sParameter;
				}
			if (uploadFtpFlag) {
				boolean flag = designPlanService.updatePlanProductByConfig(sParameter.getContext(), sParameter.getDesignPlanId(),false);
				if (!flag) {
					sParameter.getServiceHint().setTrueOrFalse(false);
					sParameter.getServiceHint().setMessage("配置文件更新异常");
					sParameter.getServiceHint().setMsgId(sParameter.getMsgId());
					return sParameter;
				}
			} else {
				sParameter.getServiceHint().setTrueOrFalse(false);
				sParameter.getServiceHint().setMessage("配置文件更新异常");
				sParameter.getServiceHint().setMsgId(sParameter.getMsgId());
				return sParameter;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			sParameter.getServiceHint().setTrueOrFalse(false);
			sParameter.getServiceHint().setMessage("方案数异常");
			sParameter.getServiceHint().setMsgId(sParameter.getMsgId());
			return sParameter;
		}
		DesignPlan designPlan = null;
		if (Utils.enableRedisCache()) {
			designPlan = DesignPlanCacher.getDesignPlan(sParameter.getDesignPlanId());
		} else {
			designPlan = designPlanService.get(sParameter.getDesignPlanId());
		}
		if (designPlan != null) {
			designPlan.setGmtModified(new Date());
		}
		// 异步更新进入设计方案缓存
		/* 设计方案产品列表不走缓存。2017-07-31
		designPlanService.updateDesignPlanCache(sParameter.getDesignPlanId(), sParameter.getNewFlag(),
				sParameter.getHouseId(), sParameter.getLivingId(), sParameter.getResidentialUnitsName(), sParameter.getRequest());*/
		return sParameter;
	}
	
	/**
	 * 一键替换结构匹配(多个结构)
	 */
	public StructureProductParameter matchingStructureData(StructureProductParameter sParameter){
		DesignTemplet designTemplet = null;
		designTemplet = Utils.enableRedisCache() ? 
				DesignCacher.getTemplet(sParameter.getDesignTempletId()) : designTempletService.get(sParameter.getDesignTempletId());
		if (designTemplet == null) {
			sParameter.getServiceHint().setTrueOrFalse(false);
			sParameter.getServiceHint().setMessage("样板房不存在");
			sParameter.getServiceHint().setMsgId(sParameter.getMsgId());
			return sParameter;
		}
		
		String codesArry[] = sParameter.getStructureCodes().split(",");
		for (String structureCode : codesArry) {
			StructureProductSearch structureProductSearch = new StructureProductSearch();
			StructureProduct structureProduct = null;
			// 通过结构编码找到结构对象
			structureProductSearch.setStructureCode(structureCode);
			structureProductSearch.setIsDeleted(0);
			structureProductSearch.setStart(0);
			structureProductSearch.setLimit(1);
			List<StructureProduct> structureList = getPaginatedList(structureProductSearch);
	
			if (Lists.isNotEmpty(structureList)) {
				structureProduct = structureList.get(0);
			} else {
				sParameter.getServiceHint().setTrueOrFalse(false);
				sParameter.getServiceHint().setMessage("模板结构不存在！structureCode=" + structureCode);
				sParameter.getServiceHint().setMsgId(sParameter.getMsgId());
				return sParameter;
			}
			// 搜索匹配的结构及结构明细
			StructureProductSearch templetStructureSearch = new StructureProductSearch();
			templetStructureSearch.setStructureType(structureProduct.getStructureType());// 结构类型
			templetStructureSearch.setTempletId(sParameter.getDesignTempletId());
			templetStructureSearch.setStructureNumber(structureProduct.getStructureNumber());
			templetStructureSearch.setStyleId(structureProduct.getStyleId());// 结构款式
			List<Integer> statusList = new ArrayList<>();
			if (sParameter.getLoginUser().getUserType().intValue() == UserTypeCode.USER_TYPE_INNER.intValue()) {
				statusList.add(StructureProductStatusCode.HAS_BEEN_PUTAWAY);
				statusList.add(StructureProductStatusCode.TESTING);
				statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
			} else {
				statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
			}
			templetStructureSearch.setStatusList(statusList);
			templetStructureSearch.setIsDeleted(0);
			templetStructureSearch.setStart(0);
			templetStructureSearch.setLimit(1);
			List<StructureProduct> templetStructureList = getStructureObject(templetStructureSearch);
			StructureProduct structureProduct1 = null;
			if (Lists.isNotEmpty(templetStructureList)) {
				structureProduct1 = templetStructureList.get(0);
			} else {
				sParameter.getServiceHint().setTrueOrFalse(false);
				sParameter.getServiceHint().setMessage("找不到匹配" + sParameter.getStructureCode() + "的结构！designTempletId=" + sParameter.getDesignTempletId());
				sParameter.getServiceHint().setMsgId(sParameter.getMsgId());
				return sParameter;
			}
			// 结构列表json
			SearchStructureProductResult searchStructureProductResult = getSearchStructureProductResult(structureProduct1);
			sParameter.getList().add(searchStructureProductResult);
		}
			int total = Utils.getListTotal(sParameter.getList());
			sParameter.setTotal(total);
		return sParameter;
	}
}
