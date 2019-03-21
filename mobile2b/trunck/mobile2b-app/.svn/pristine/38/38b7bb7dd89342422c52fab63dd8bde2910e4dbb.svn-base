package com.nork.product.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.common.util.FtpUploadUtils;
import com.nork.design.cache.DesignCacher;
import com.nork.design.cache.DesignPlanCacher;
import com.nork.design.cache.DesignPlanProductCacher;
import com.nork.design.model.*;
import com.nork.design.model.ProductListByTypeInfo.PlanProductInfo;
import com.nork.design.model.search.DesignTempletProductSearch;
import com.nork.design.model.search.DesignTempletSearch;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;

import com.nork.product.cache.GroupProductCacher;
import com.nork.product.model.*;
import com.nork.product.service.*;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.BaseArea;
import com.nork.system.model.ResDesign;
import com.nork.system.service.BaseAreaService;
import com.nork.system.service.ResDesignService;
import com.nork.user.model.UserTypeCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.Constants;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Utils;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.controller.web.WebStructureProductController;
import com.nork.product.dao.StructureProductMapper;
import com.nork.product.model.result.SearchStructureProductDetailResult;
import com.nork.product.model.result.SearchStructureProductResult;
import com.nork.product.model.search.StructureProductDetailsSearch;
import com.nork.product.model.search.StructureProductSearch;
import com.nork.product.model.small.StructureProductSmall;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.ResFileSearch;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**   
 * @Title: StructureProductServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-结构表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-12-02 15:32:05
 * @version V1.0   
 */
@Service("structureProductService")
public class StructureProductServiceImpl implements StructureProductService {
	
	@Autowired
	private BaseAreaService baseAreaService;
	
	@Autowired
	private DesignTempletProductService designTempletProductService;
	@Autowired
	private StructureProductMapper structureProductMapper;
	@Autowired
	private DesignTempletService designTempletService;
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
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private GroupProductService groupProductService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private ResDesignService resDesignService;

	private static Logger logger = Logger.getLogger(WebStructureProductController.class);
	
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

	public SearchStructureProductResult getSearchResult(StructureProduct structureProduct, Integer spaceCommonId, String mediaType, Integer planProductId) {
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
//				String url=Utils.getValue("app.upload.root", "")+resFile.getFilePath();
//				String config=FileUploadUtils.getFileContext(url);
//				searchStructureProductResult.setStructureConfig(config);
				String url = resFile.getFilePath();
				searchStructureProductResult.setFilePath(url);
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
				searchStructureProductDetailResult.setStyleId(structureProductDetail.getStyleId());
				searchStructureProductDetailResult.setMeasureCode(structureProductDetail.getMeasureCode());
				searchStructureProductDetailResult.setRegionMark(structureProductDetail.getRegionMark());
				searchStructureProductDetailResult.setDescribeInfo(structureProductDetail.getDescribeInfo());
				searchStructureProductDetailResult.setTemplateProductId(productId);
				searchStructureProductDetailResult.setBasicModelType(baseProduct.getProductSmallTypeMark().replace("basic_",""));
				/*rulesMap*/
				Map<String,String> map = new HashMap<String,String>();
				map = productAttributeService.getPropertyMap(baseProduct.getId());
				baseProduct.setPropertyMap(map);
				searchStructureProductDetailResult.setStructureProductSign(map.get("structureSign"));
				Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(),
						baseProduct.getProductTypeMark(),baseProduct.getProductSmallTypeMark(),spaceCommonId,null,new DesignRules(),map);
				searchStructureProductDetailResult.setRulesMap(rulesMap);
				searchStructureProductDetailResult.setPropertyMap(map);
				searchStructureProductDetailResult.setBasicPropertyMap(map);
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

	public SearchStructureProductResult getSearchStructureProductResult(StructureProduct structureProduct,String templetStructureCode){
		SearchStructureProductResult searchStructureProductResult=new SearchStructureProductResult
				(structureProduct.getId(), structureProduct.getStructureName(), structureProduct.getStructureCode(),
						"", "", null,"","");
		/*config(从文件中取)*/
		Integer fileId=structureProduct.getConfigFileId();
		if( fileId != null && fileId > 0 ){
			ResFile resFile = resFileService.get(fileId);
			if( resFile != null ){
				/*String url = Utils.getValue("app.upload.root", "")+resFile.getFilePath();*/
				String url = Utils.getAbsolutePath(resFile.getFilePath(), Utils.getAbsolutePathType.encrypt);
				String config=FileUploadUtils.getFileContext(url);
				searchStructureProductResult.setStructureConfig(config);
			}
		}
		searchStructureProductResult.setDelStructureCode(templetStructureCode);
		String regionMark = structureProduct.getPlanStructureRegionMark();
		searchStructureProductResult.setPlanStructureRegionMark(regionMark==null?structureProduct.getRegionMark():regionMark);
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
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(StructureProduct model, LoginUser loginUser) {
		if (model != null) {
			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
	
	
	/**
	 * 定义结构接口(新增结构,用于编辑器调用)
	 * @author huangsongbo
	 * @param structureCode
	 * @param structureName
	 * @param config
	 * @param msgId
	 * @return
	 */
	public Object createStructureProduct(Integer styleId, Integer isCommon, String regionMark, String measureCode,
			String structureCode, String structureName, String templetCode, String groupFlag, String config,
			String msgId, Integer structureSmallType,LoginUser loginUser){
		StructureProduct structureProduct=null;
		//验证参数 
		if(StringUtils.isBlank(structureCode))
			return new ResponseEnvelope<>(false, "参数structureCode不能为空", msgId);
		if(StringUtils.isBlank(templetCode))
			return new ResponseEnvelope<>(false, "参数templetCode不能为空", msgId);
		if(StringUtils.isBlank(config))
			return new ResponseEnvelope<>(false, "参数config不能为空", msgId);
		if(isCommon == null )
			return new ResponseEnvelope<>(false, "参数isCommon不能为空", msgId);
		
		if(Utils.hasEmptyOrSpecialCharacter(structureCode)) {
		  return new ResponseEnvelope<>(false, "请检查结构编码中是否包含空格！", msgId);
		}
		//u3d插件中结构组标记显示为基本结构编码
		if(Utils.hasEmptyOrSpecialCharacter(groupFlag)) {
          return new ResponseEnvelope<>(false, "请检查基本结构编码中是否包含空格！", msgId);
        }

		//去除编码中的空格,换行符之类特殊字符
		structureCode = Utils.replaceSpecialCharacter(structureCode);
		//验证样板房是否存在 
		Integer templetId=0;
		DesignTempletSearch designTempletSearch=new DesignTempletSearch();
		designTempletSearch.setIsDeleted(0);
		designTempletSearch.setStart(0);
		designTempletSearch.setLimit(1);
		designTempletSearch.setDesignCode(templetCode);
		List<DesignTemplet> designTemplets=designTempletService.getPaginatedList(designTempletSearch,loginUser.getId());
		if(designTemplets==null||designTemplets.size()==0)
			return new ResponseEnvelope<>(false, "没有找到指定样板房,code:"+templetCode, msgId);
		DesignTemplet designTemplet=designTemplets.get(0);
		templetId=designTemplet.getId();
		//验证结构组标记是否正确(相同结构组标记,样板房id必须相同) 
		StructureProductSearch structureProductSearch2=new StructureProductSearch();
		structureProductSearch2.setStructureCode(structureCode);
		structureProductSearch2.setStart(0);
		structureProductSearch2.setLimit(1);
		structureProductSearch2.setIsDeleted(0);
		List<StructureProduct> structureProducts2=this.getPaginatedList(structureProductSearch2);
		if(structureProducts2!=null&&structureProducts2.size()>0){
			if(!templetId.equals(structureProducts2.get(0).getTempletId())){
				return new ResponseEnvelope<>(false, "该结构组标记已被其他样板房的结构使用", msgId);
			}
		}
		//验证结构组标记是否正确(相同结构组标记,样板房id必须相同)->end 
		//验证参数->end 
		 
		//保存配置文件(物理文件) 
		//获取文件路径(目录) 
		/*String appUploadRoot=Utils.getValue("app.upload.root", "/home/nork/resources");*/
		/*String configFileUploadPath=Utils.getValue("product.structureProduct.configFile.upload.path", "/product/structureProduct/configFile/");*/
		String configFileUploadPath = Utils.getValueByFileKey(ResProperties.RES, ResProperties.STRUCTUREPRODUCT_CONFIGFILE_FILEKEY, "/AA/c_basedesign/[yyyy]/[MM]/[dd]/[HH]/product/structureProduct/configFile/");
		/*String fileDirStr=appUploadRoot+configFileUploadPath;*/
		String fileDirStr = Utils.getAbsolutePath(configFileUploadPath, Utils.getAbsolutePathType.encrypt);
		File fileDir=new File(fileDirStr);
        if (!fileDir.exists()) {
        	fileDir.mkdirs();
        }
        File txtFile=null;
        PrintStream ps=null;
        try {
			txtFile = File.createTempFile("groupLocation_"+structureCode+"_", ".txt", fileDir);
	        ps = new PrintStream(new FileOutputStream(txtFile));
	        ps.println(config);
	        ps.close();
		} catch (IOException e) {
			logger.error("------生成结构配置文件失败");
			e.printStackTrace();
		}finally{
			if(ps!=null)
				ps.close();
		}
		//保存配置文件(物理文件)->end 
        //保存配置文件信息(resFile表)  
        ResFile resFile= new ResFile();
        String fileName=txtFile.getName();
        String fileCode=System.currentTimeMillis()+"_"+Utils.generateRandomDigitString(6);
        resFile.setFileCode(fileCode);
        resFile.setFileName(fileName.substring(0,fileName .lastIndexOf(".")));
        resFile.setFileOriginalName(fileName.substring(0,fileName .lastIndexOf(".")));
        resFile.setFileType("结构配置文件");
        resFile.setFileLevel("0");
        resFile.setFileSize(txtFile.length()+"");
        resFile.setFilePath(configFileUploadPath+fileName);
        resFile.setFileKey("product.structureProduct.configFile");
        resFile.setFileOrdering(0);
        resFile.setSysCode(fileCode);
        resFile.setIsDeleted(0);
        resFile.setCreator("nologin");
        resFile.setModifier("nologin");
        Date now=new Date();
        resFile.setGmtCreate(now);
        resFile.setGmtModified(now);
        resFile.setFileSuffix(".txt");
        Integer fileId=resFileService.add(resFile);
        //保存配置文件信息(resFile表)->end 
        //StructureProduct structureProduct=null;
        StructureProductSearch structureProductSearch=new StructureProductSearch();
		structureProductSearch.setStructureCode(structureCode);
		structureProductSearch.setStart(0);
		structureProductSearch.setLimit(1);
		//过滤被逻辑删除的结构
        structureProductSearch.setIsDeleted(0);
		List<StructureProduct> structureProducts=this.getPaginatedList(structureProductSearch);
		if(structureProducts!=null&&structureProducts.size()>0){
			structureProduct=structureProducts.get(0);
			if(StringUtils.isNotBlank(structureName))
				structureProduct.setStructureName(structureName);
			// 删除以前的结构明细(更新新的结构明细) ->start
			structureProductDetailsService.deleteByStructureId(structureProduct.getId());
			// 删除以前的结构明细(更新新的结构明细) ->end
			// 删除该样板房产品表中该结构的关系(需要重新绑定) ->start
			this.updateDesignTempletStructureRelationByStructureId(structureProduct.getId());
			// 删除该样板房产品表中该结构的关系(需要重新绑定) ->end
		}else{
			structureProduct=new StructureProduct();
			structureProduct.setStructureCode(structureCode);
			structureProduct.setStatus(0);
			if(StringUtils.isNotBlank(structureName))
				structureProduct.setStructureName(structureName);
			else
				structureProduct.setStructureName(structureCode);
		}
		//通过编码保存结构类型和序号
		this.saveStructureTypeOrNumber(structureCode,structureProduct);
		structureProduct.setStyleId(styleId);
		structureProduct.setRegionMark(regionMark);
		structureProduct.setMeasureCode(measureCode);
		structureProduct.setIsCommon(isCommon);
		structureProduct.setStructureSmallType(structureSmallType);
		structureProduct.setIsDeleted(0);
		sysSave(structureProduct, loginUser);
		//填充其他信息 
		structureProduct.setConfigFileId(fileId);
		if(StringUtils.isNotBlank(groupFlag))
			structureProduct.setGroupFlag(groupFlag);
		if(isCommon != 1){
			structureProduct.setTempletId(templetId);
		}
		if(structureProduct.getId()!=null&&structureProduct.getId()>0)
			this.update(structureProduct);
		else{
			this.add(structureProduct);
		}

		//读取配置文件信息,保存结构明细 
		JSONObject configJson = JSONObject.fromObject(config);
		JSONArray jsonArray = JSONArray.fromObject(configJson.get("ProductStructList"));
		if(jsonArray!=null&&jsonArray.size()>0){
			//把要更新为结构的样板房产品数据查出来->为了判断该list里面是否有已经被定义为结构的产品->如果存在,则不更新样板房产品表
			List<DesignTempletProduct> designTempletProductListFLag = new ArrayList<DesignTempletProduct>();
			//是否更新样板房产品表:true:是;false:不更新
			boolean flag = false;
			//样板房结构需绑定样板房产品关系
			if(isCommon != 1){
				flag = true;
			}

			String dateTime = Utils.getCurrentDateTime(Utils.DATETIMESSS);
			String planGroupId=structureProduct.getId()+"_"+dateTime;
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject=(JSONObject)jsonArray.get(i);
				String productCode=jsonObject.getString("ProductCode");
				if(StringUtils.isBlank(productCode)){
					continue;
				}
				BaseProduct baseProduct=baseProductService.findOneByCode(productCode.trim());
				if(baseProduct==null){
					return new ResponseEnvelope<>(false, "产品未找到:baseProductCode:"+productCode.trim(), msgId);
				}
				//新建结构明细 
				StructureProductDetails structureProductDetails=structureProductDetailsService.createByStructureIdAndProductId(structureProduct.getId(),baseProduct.getId());
				String cameraLook = null;
				String cameraView = null;
				if(jsonObject.containsKey("CameraLook"))
					cameraLook = jsonObject.getString("CameraLook");
				if(jsonObject.containsKey("CameraView"))
					cameraView = jsonObject.getString("CameraView");
//				//System.out.println(cameraLook.length());
				structureProductDetails.setCameraLook((cameraLook==null||"null".equals(cameraLook))?"":cameraLook);
				structureProductDetails.setCameraView((cameraView==null||"null".equals(cameraView))?"":cameraView);
				structureProductDetails.setRegionMark(regionMark);
				structureProductDetails.setStyleId(styleId);
				structureProductDetails.setMeasureCode(measureCode);
				structureProductDetailsService.add(structureProductDetails);
				//新建结构明细->end 
				 
				//该逻辑为根据code找出第一个进行更新 
				if(flag){
					DesignTempletProductSearch designTempletProductSearch=new DesignTempletProductSearch();
					designTempletProductSearch.setStart(0);
					designTempletProductSearch.setLimit(1);
					designTempletProductSearch.setDesignTempletId(templetId);
					designTempletProductSearch.setProductId(baseProduct.getId());
					designTempletProductSearch.setIsDeleted(0);
					List<DesignTempletProduct> designTempletProductList=designTempletProductService.getPaginatedList(designTempletProductSearch);
					if(designTempletProductList!=null&&designTempletProductList.size()>0){
						DesignTempletProduct designTempletProduct=designTempletProductList.get(0);
						//判断为已经被定义了结构或组->break,取消更新样板房产品表
						Integer groupId = designTempletProduct.getProductGroupId();
						if (groupId != null && groupId > 0) {
							flag = false;
						}
						DesignTempletProduct newTempletProduct = new DesignTempletProduct();
						newTempletProduct.setId(designTempletProduct.getId());
						newTempletProduct.setGroupType(1);
						newTempletProduct.setPlanGroupId(planGroupId);
						newTempletProduct.setProductGroupId(structureProduct.getId());
						//designTempletProductService.update(designTempletProduct); 
						designTempletProductListFLag.add(newTempletProduct);
					}
				}
				//该逻辑为根据code找出第一个进行更新->end 
				//更新样板房产品数据->end  
			}
			//更新样板房产品表
			if(flag){
				for(DesignTempletProduct designTempletProduct : designTempletProductListFLag){
					designTempletProductService.update(designTempletProduct);
				}
			}
		}
		//读取配置文件信息,保存结构明细->end
		//回填配置文件的businessid 
		resFile.setBusinessId(structureProduct.getId());
		resFileService.update(resFile);
		/*填充其他信息->end*/
		return new ResponseEnvelope<>(true, "创建/修改成功", msgId);
	}
	/**
	 * 通过编码存入结构类型和序号（方便一键装修结构匹配）
	 * @author xiaoxc
	 * @param structureCode
	 * @param structureProduct
	 * @return StructureProduct
	 */
	public StructureProduct saveStructureTypeOrNumber(String structureCode,StructureProduct structureProduct){
		if( StringUtils.isNotEmpty(structureCode) ){
			String number = structureCode.substring(structureCode.lastIndexOf("_")+1,structureCode.length());
			String strCode = structureCode.substring(0,structureCode.lastIndexOf("_"));
			String type = strCode.substring(strCode.lastIndexOf("_")+1,strCode.length());
			structureProduct.setStructureType(type);
			structureProduct.setStructureNumber(number==null?0:Utils.getIntValue(number));
		}
		return structureProduct;
	}

	public Object searchStructureProduct(Integer planProductId, Integer spaceCommonId,Integer designPlanId,
			String msgId,Integer start,Integer limit,LoginUser loginUser,String mediaType,
			Integer isStandard,Integer styleId,String regionMark,String measureCode) {

//		Integer status = 1;
//		if(UserTypeCode.USER_TYPE_INNER == loginUser.getUserType()){  /*userType == 1 为内部用户，该用户可以看到 测试和上架数据，status = 3  可以看到*/
//			status = 3;
//		}

		/*参数验证*/
		List<SearchStructureProductResult> list=new ArrayList<SearchStructureProductResult>();
		if(planProductId==null || planProductId < 1){
			/*返回空列表*/
			return new ResponseEnvelope<>(new Integer(0), list, msgId);
		}
		if(start==null)
			start=-1;
		if(limit==null)
			limit=-1;
		/*参数验证->end*/
		DesignPlanProduct designPlanProduct=null;
		if(Utils.enableRedisCache())
			designPlanProduct= DesignPlanProductCacher.get(planProductId);
		else
			designPlanProduct = designPlanProductService.get(planProductId);
		if(designPlanProduct==null){
			return new ResponseEnvelope<>(false, "设计方案产品未找到:designPlanProductId:"+planProductId, msgId);
		}
		Integer groupType=designPlanProduct.getGroupType();
		Integer groupId=designPlanProduct.getProductGroupId();
		String structureGroupFlag="";
		Integer templetId=null;
		StructureProductSearch structureSearch = new StructureProductSearch();
		
		StructureProduct structureProduct = null;
		DesignPlan designPlan = null;
		if(Utils.enableRedisCache()) {
			designPlan = DesignPlanCacher.getDesignPlan(designPlanId);
		} else {
			designPlan = designPlanService.get(designPlanId);
		}
		
		// 得到点击的结构的信息/设置布局标识查询条件 ->start
		// 如果是主s地面结构,则带入布局标识查询条件(从样板房表中取地面布局标识)
		if(groupType != null && 1 == groupType.intValue()) {
			structureProduct = this.get(groupId);
			if(structureProduct != null) {
				if(StringUtils.equals("10", designPlanProduct.getRegionMark().trim()) && StringUtils.equals("DJ", structureProduct.getStructureType())) {
					if(designPlan != null) {
						Integer designTempletId = designPlan.getDesignTemplateId();
						if(designTempletId != null) {
							DesignTemplet designTemplet = designTempletService.get(designTempletId);
							if(designTemplet != null) {
								/*String structureGroundIdentify = null;*/
								/*if(designTemplet.getGroundIdentify() != null && designTemplet.getGroundIdentify().intValue() != 0) {*/
								/*if(designTemplet.getGroundIdentify() != null && !StringUtils.equals("0", designTemplet.getGroundIdentify())) {
									structureGroundIdentify = designTemplet.getGroundIdentify();
								}
								structureSearch.setStructureGroundIdentify(structureGroundIdentify);*/
								structureSearch.setIdentifyList(Utils.getIdentifyList(designTemplet.getGroundIdentify()));
							}
						}
					}
				}
			}
		}
		// 得到点击的结构的信息/设置布局标识查询条件 ->end
		
		if (new Integer(1).equals(isStandard)) {
			structureSearch.setIsDeleted(0);
			structureSearch.setIsCommon(1);
//			structureSearch.setStyleId(styleId);
			structureSearch.setMeasureCode(measureCode);
			structureSearch.setStart(start);
			structureSearch.setLimit(limit);

			/*初始化结构是定制结构，搜索通用结构需带上这个结构*/
			if (designPlan != null && designPlan.getDesignTemplateId() != null && designPlan.getDesignTemplateId() > 0) {
				structureSearch.setTempletId(designPlan.getDesignTemplateId());
			}
			structureSearch.setId(groupId);
		}else if(new Integer(0).equals(groupType)){
			/*识别为组合*/
			GroupProduct groupProduct=null;
			if(Utils.enableRedisCache())
				groupProduct= GroupProductCacher.get(groupId);
			else
				groupProduct=groupProductService.get(groupId);
			if(groupProduct==null){
				logger.info("未找到组数据:groupId:"+groupId);
				return new ResponseEnvelope<>(new Integer(0), list, msgId);
			}
			structureGroupFlag=groupProduct.getGroupFlag();
			Integer structureId=groupProduct.getStructureId();
			if(structureId!=null&&structureId>0){
				structureProduct = this.get(structureId);
				if(structureProduct==null){
					logger.info("------结构未找到:id:"+structureId);
				}else{
					templetId=structureProduct.getTempletId();
				}
			}
		}else if(new Integer(1).equals(groupType)){
			/*识别为结构*/
			if(structureProduct==null){
				logger.info("未找到结构数据:structureId:"+groupId);
				return new ResponseEnvelope<>(new Integer(0), list, msgId);
			}
			structureGroupFlag=structureProduct.getGroupFlag();
			templetId=structureProduct.getTempletId();
		}
		if(new Integer(0).equals(isStandard) && StringUtils.isBlank(structureGroupFlag)){
			logger.info("结构组标记为空值,所以返回空集合");
			return new ResponseEnvelope<>(new Integer(0), list, msgId);
		}
		//更具用户类型查找不同状态的产品结构
		List<Integer> statusList = new ArrayList<Integer>();
		if(loginUser.getUserType() == null ){
			logger.error("用户类型  userType 字段为空！");
		}else{
			String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
			if(UserTypeCode.USER_TYPE_INNER.equals(loginUser.getUserType()) && "2".equals(versionType.trim())){//内部用户
				statusList.add(StructureProductStatusCode.HAS_BEEN_PUTAWAY);
				statusList.add(StructureProductStatusCode.TESTING);
				statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
			}else if(UserTypeCode.USER_TYPE_OUTER_B2B == loginUser.getUserType() || UserTypeCode.USER_TYPE_OUTER_B2C == loginUser.getUserType()){//外部用户
				statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
			}else{
//				statusList.add(StructureProductStatusCode.HAS_BEEN_PUTAWAY);
//				statusList.add(StructureProductStatusCode.TESTING);
				statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
			}
		}
		int total = 0;
		if (new Integer(1).equals(isStandard)) {
			structureSearch.setStatusList(statusList);
			total = structureProductMapper.getCommonStructureCount(structureSearch);
		}else{
			total = this.getCountByGroupFlag(structureGroupFlag,statusList,start,limit);
		}
		if (total > 0) {
			List<StructureProduct> structureProducts = null;
			if (new Integer(1).equals(isStandard)) {
				structureProducts = structureProductMapper.getCommonStructureList(structureSearch);
			}else{
				structureProducts = this.findAllByGroupFlag(structureGroupFlag,statusList,templetId,start,limit);
			}
			if (structureProducts != null && structureProducts.size() > 0) {
				for(StructureProduct structureProductItem:structureProducts){
					SearchStructureProductResult searchStructureProductResult = this.getSearchResult(structureProductItem, spaceCommonId,mediaType, planProductId);
					list.add(searchStructureProductResult);
				}
			}
		}

		return new ResponseEnvelope<>(total, list, msgId);
	}

	@Override
	public Object unityStructureProduct(Integer designPlanId,Integer groupId,String planGroupId,
										String groupProductJson,String context,Integer groupType,
										String msgId,LoginUser loginUser,Integer isStandard,
										String center,String regionMark,Integer styleId,String measureCode){

		if (designPlanId == null || StringUtils.isEmpty(context) || StringUtils.isEmpty(groupProductJson)) {
			return new ResponseEnvelope<DesignPlan>(false, "参数存在空值", msgId);
		}
		DesignPlan desPlan=null;
		if(Utils.enableRedisCache()){
			desPlan = DesignCacher.get(designPlanId);
		}else{
			desPlan = designPlanService.get(designPlanId);
		}
		if (desPlan == null) {
			return new ResponseEnvelope<DesignPlan>(false, "方案不存在", msgId);
		}
		// 替换配置内容
		List<UnityPlanProduct> list = null;
		try {
			list = designPlanService.batchSaveDesignPlan(desPlan, designPlanId, groupProductJson, groupId, new Integer(0),
                    "", context, planGroupId, loginUser, groupType,isStandard,center,regionMark,styleId,measureCode);
		} catch (InvocationTargetException e) {
			return new ResponseEnvelope<DesignPlan>(false, "新增数据异常！", msgId);
		} catch (IllegalAccessException e) {
			return new ResponseEnvelope<DesignPlan>(false, "新增数据异常！", msgId);
		}
		if( list == null ){
			return new ResponseEnvelope<DesignPlan>(false, "新增数据异常！", msgId);
		}else{
			list = wrapperData(designPlanId,list);
			// 获取配置路径
			String filePath = "";
			try {
				if (desPlan.getConfigFileId() != null) {
					//ResFile resFile=null;
					ResDesign resDesign = null;
					if(Utils.enableRedisCache()){
						//resFile = ResourceCacher.getFile(desPlan.getConfigFileId());
						resDesign = ResourceCacher.getResDesign(desPlan.getConfigFileId());

					}else{
						//resFile = resFileService.get(desPlan.getConfigFileId());
						resDesign = resDesignService.get(desPlan.getConfigFileId());
					}
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
					/*test*/
					//JSONArray configJson = JSONArray.fromObject(list);
					/*test->end*/
					boolean uploadFtpFlag = false;
					// 上传方式为2或者3表示文件在ftp上
					if (ftpUploadMethod == 2 || ftpUploadMethod == 3) {
						uploadFtpFlag = FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
					} else if (ftpUploadMethod == 1) {
						uploadFtpFlag = Utils.replaceFile(filePath, context);
					}
					Long endTimeUp = System.currentTimeMillis();
					//////System.out.println("times upload:" + (endTimeUp - startTimeUp));
					if (!uploadFtpFlag) {
						return new ResponseEnvelope<DesignPlan>(false, "写入文件失败", msgId);
					}
					if (uploadFtpFlag) {
						boolean flag = designPlanService.updatePlanProductByConfig(context,designPlanId,false);
						if( !flag ){
							return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
						}
					} else {
						return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEnvelope<DesignPlan>(false, "方案数异常", msgId);
			}
		}
		DesignPlan designPlan=null;
		if(Utils.enableRedisCache()){
			designPlan = DesignPlanCacher.getDesignPlan(designPlanId);
		}else{
			designPlan = designPlanService.get(designPlanId);
		}
		if( designPlan != null ){
			designPlan.setGmtModified(new Date());
		}

		return new ResponseEnvelope<>(list, msgId);
	}

	@Override
	public int deleteBatch(List<Integer> list) {
		return 0;
	}

	private List<UnityPlanProduct>  wrapperData(Integer designPlanId, List<UnityPlanProduct> dataList) {
		List<ProductDTO> list = designPlanService.getProductDTOList(designPlanId);
		for(UnityPlanProduct upp : dataList) {
			Integer upp_productId = upp.getProductId();
			for(ProductDTO productDTO : list) {
				Integer productId = productDTO.getProductId();
				if(upp_productId.equals(productId)) {
					String valueKey = productDTO.getValueKey();
					if(StringUtils.isNotBlank(valueKey)) {
						if(valueKey.indexOf("_") != -1) {
							String[] split = valueKey.split("_");
							upp.setBasicModelType(split[1]);
						} else {
							upp.setBasicModelType(valueKey);
						}
					}
				}
			}
		}
		return dataList;
	}

	public List<StructureProduct> getStructuresByDesignPlanId(Integer designPlanId){
		return structureProductMapper.getStructuresByDesignPlanId(designPlanId);
	}

	@Override
	public List<StructureProduct> getStructuresByRecommendedPlanId(Integer recommendedPlanId) {
		return structureProductMapper.getStructuresByRecommendedPlanId(recommendedPlanId);
	}

	@Override
	public Integer easySearch(String measureCode, Integer styleId, Integer structureNumber, List<String> identifyList, String groundIdentify, Integer designTempletId) {
		// 参数验证 ->start
		if(StringUtils.isEmpty(measureCode) && styleId == null && structureNumber == null) {
			return null;
		}
		// 参数验证 ->end
		
		return structureProductMapper.easySearch(measureCode, styleId, structureNumber, identifyList, groundIdentify, designTempletId);
	}

	@Override
	public List<PlanProductInfo> getPlanProductInfoListByStructureId(Integer structureId) {
		// 参数验证 ->start
		if(structureId == null) {
			return null;
		}
		// 参数验证 ->end
		
		return structureProductMapper.getPlanProductInfoListByStructureId(structureId);
	}

	@Override
	public Integer easySearchV2(StructureProductSearch structureProductSearch) {
		// 参数验证 ->start
		if(structureProductSearch == null) {
			return null;
		}
		// 参数验证 ->end
		
		return structureProductMapper.easySearchV2(structureProductSearch);
	}

	@Override
	public List<StructureProduct> getListV2(StructureProductSearch search) {
		return structureProductMapper.getListV2(search);
	}

	@Override
	public int getCountV2(StructureProductSearch search) {
		return structureProductMapper.getCountV2(search);
	}

	
	
	/**
	 * 产品拼花  取结构功能
	 * @param model
	 * @return
	 */
	@Override
	public Object productSpellingFlower(ProductSpellingFlowerModel model) {
		
		StructureProductSearch search = new StructureProductSearch();
		String limit =  model.getLimit();
		String start =  model.getStart();
		String msgId =  model.getMsgId();
		String timeStart =  model.getTimeStart();
		String timeEnd =  model.getTimeEnd();
		String area =  model.getArea();
		String designTempletCode = model.getDesignTempletCode();
		
		//获得所有地面 value
		SysDictionarySearch sysDSearch = new SysDictionarySearch();
		sysDSearch.setIsDeleted(0);
		sysDSearch.setType("structureTypes");
		sysDSearch.setSch_Valuekey_("dmianStructure");
		List<SysDictionary>sysList = sysDictionaryService.getPaginatedList(sysDSearch);
		if(sysList == null || sysList.size() <= 0) {
			return new ResponseEnvelope<>(false, "无地面结构", msgId);
		}
		List <Integer >structureSmallTypeList = new ArrayList<Integer>();
		for (SysDictionary sysDictionary_ : sysList) {
			structureSmallTypeList.add(sysDictionary_.getValue());
		}
		search.setStructureSmallTypeList(structureSmallTypeList);
		search.setDesignTempletCode(designTempletCode);
		
		//城市查询过滤
		if(StringUtils.isNotEmpty(area)) {
			BaseArea baseArea = new BaseArea();
			baseArea.setIsDeleted(0);
			baseArea.setAreaName(area);
			List<BaseArea>areaList = baseAreaService.getList(baseArea);
			if(areaList == null || areaList.size() <= 0) {
				return new ResponseEnvelope<>(false, "该城市不存在", msgId);
			}
			if(StringUtils.isEmpty(areaList.get(0).getAreaCode())) {
				return new ResponseEnvelope<>(false, "该城市数据异常", msgId);
			}
			search.setAreaLongCode("." + areaList.get(0).getAreaCode() + ".");
		}
		
		//分页
		search.setLimit(20);
		search.setStart(0);
		if(StringUtils.isNotEmpty(limit)) {
			search.setLimit(Integer.parseInt(limit));
		}
		if(StringUtils.isNotEmpty(start)) {
			search.setStart(Integer.parseInt(start));
		}
		

		//时间条件查询
 		if(StringUtils.isNotEmpty(timeStart) && StringUtils.isNotEmpty(timeEnd)) {
			try {
				/*SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
				Date dateStart =  sdf.parse(timeStart);
				Date dateEnd =  sdf.parse(timeEnd);
				search.setGmtCreateEnd(dateEnd);
				search.setGmtCreateStart(dateStart);*/
				search.setGmtCreateEndStr(timeEnd);
				search.setGmtCreateStartStr(timeStart);
			}catch (Exception e) {
				return new ResponseEnvelope<>(false, "时间格式错误", msgId);
			}
		}
		search.setIsDeleted(0);
		
		
		int count = 0;
		List<StructureProduct>structureList = null;
		if(StringUtils.isNotEmpty(model.getId())) {
			StructureProduct  structureProduct = this.get(Integer.parseInt(model.getId()));
			structureList = new ArrayList<StructureProduct>();
			structureList.add(structureProduct);
			count = 1;
		}else {
			count = this.getCountV2(search);
			if(count > 0) {
				structureList = this.getListV2(search);
			}
		}

		if(structureList != null && structureList.size() > 0 ) {
			String url = Utils.getPropertyName("app","app.resources.url","");
			for (StructureProduct structureProduct : structureList) {
				//通过结构id 获取结构里的产品 详情	和 windows模型地址	 
				List<ProductSimpleModel>productList = baseProductService.getProductListByStructureId(structureProduct.getId());
				if(productList != null && productList.size() > 0) {
					for (ProductSimpleModel productSimpleModel : productList) {
						if(productSimpleModel == null) {
							continue;
						}
						//获得产品标示
						//if(productSimpleModel.getId().intValue() == 240006) {
						//	System.out.println("==");
						//}
						List<String>productIdentificationlist = baseProductService.getProductIdentification(productSimpleModel.getId());
						//String productIdentification = baseProductService.getProductIdentification(productSimpleModel.getId());
						if(productIdentificationlist!=null && productIdentificationlist.size()>0) {
							productSimpleModel.setProductIdentification(productIdentificationlist.get(0));
						}
						if(StringUtils.isNotEmpty(productSimpleModel.getWindowsU3dModelPath())) {
							productSimpleModel.setWindowsU3dModelPath(url + productSimpleModel.getWindowsU3dModelPath());
						}
					}
					structureProduct.setProductList(productList);
				}

			}
		}
		return new ResponseEnvelope<>(count,structureList, msgId);
	}

	
	
	
	/**
	 * 保存结构拼花文本信息、产品拼花文本信息
	 * @param productSpellingFlower
	 * @param structureSpellingFlower
	 * @param msgId
	 * @return
	 */
	@Override
	public Object saveProductSpellingFlower(String productSpellingFlower, String structureSpellingFlower,
			String structureId,String msgId) {

		if(StringUtils.isEmpty(productSpellingFlower) || StringUtils.isEmpty(structureSpellingFlower) 
				|| StringUtils.isEmpty(structureId) ||StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<>(false, "缺少参数", msgId);
		}
		/*if(productSpellingFlower.indexOf(";") == -1) {
			return new ResponseEnvelope<>(false, "productSpellingFlower 参数异常", msgId);
		}*/

		//保存结构拼花文本信息
		StructureProduct structureProduct = this.get(Integer.parseInt(structureId));
		if(structureProduct == null) {
			return  new ResponseEnvelope<>(false, "id为：" + structureId + "的结构不存在", msgId);
		}
		Map<String,String>resMap = this.saveStructureSpellingFlowerFile(structureProduct, structureSpellingFlower);
		if(resMap == null || resMap.size()<=0){
			return new ResponseEnvelope<DesignPlan>(false,"保存设计方案拼花文件失败",msgId);
		}else if(!"true".equals(resMap.get("success"))){
			return new ResponseEnvelope<DesignPlan>(false,resMap.get("data"),msgId);
		}

		
		
		//保存产品拼花文本信息
		String [] arr =  productSpellingFlower.split(";");
		if(arr == null || arr.length <= 0 || "[]".equals(arr)) {
			return new ResponseEnvelope<>(false, "productSpellingFlower 参数异常", msgId);
		}
		for (String str : arr) {
			String productId = null;  //产品id
			String productSpellingFlowerText = null;//产品拼花文本信息
			
			if(str.indexOf("~") == -1) {
				return new ResponseEnvelope<>(false, "productSpellingFlower 参数异常", msgId);
			}
			productId = str.substring(0, str.indexOf("~"));
			productSpellingFlowerText = str.split("~")[1];
			
			if(!StringUtils.isNumeric(productId)) {
				return new ResponseEnvelope<>(false, "productSpellingFlower 产品id非数字", msgId);
			}
			if(StringUtils.isEmpty(productSpellingFlowerText)) {
				return new ResponseEnvelope<>(false, "productSpellingFlower 参数异常", msgId);
			}
			
			BaseProduct baseProduct = baseProductService.get(Integer.parseInt(productId));
			if(baseProduct == null) {
				return  new ResponseEnvelope<>(false, "id为：" + productId + "的产品不存在", msgId);
			}
			Map<String,String>productMap = this.saveProductSpellingFlowerFile(baseProduct,productSpellingFlowerText);
			if(productMap == null || productMap.size()<=0){
				return new ResponseEnvelope<>(false,"保存设计方案拼花文件失败",msgId);
			}else if(!"true".equals(resMap.get("success"))){
				return new ResponseEnvelope<>(false,productMap.get("data"),msgId);
			}
		}
		return new ResponseEnvelope<>(true,"操作成功",msgId);
	}
 
	
	
	
	
	/**
	 * 保存产品拼花文本信息
	 * @param baseProduct
	 * @param structureSpellingFlower
	 * @return resMap
	 */
	public Map<String,String>  saveProductSpellingFlowerFile(BaseProduct baseProduct,String structureSpellingFlower){
		Map<String,String>resMap = new HashMap<String,String>();
		if(StringUtils.isEmpty(structureSpellingFlower) || baseProduct == null) {
			resMap.put("success", "false");
			return resMap;
		}
		String filePath = null;
		if(baseProduct.getSpellingFlowerFileId()!=null && baseProduct.getSpellingFlowerFileId().intValue() > 0) {
			ResFile resFile = resFileService.get(baseProduct.getSpellingFlowerFileId());
			if(resFile != null) {
				filePath = Utils.getAbsolutePath(resFile.getFilePath(), null);
			}
		}
		String fileKey = "product.baseProduct.spellingFlower.file.upload.path";
		if(StringUtils.isEmpty(filePath)) { //如果拼花路径为空  代表第1次增加拼花信息
			String defaultPath = "/AA/c_basedesign/[YYYY]/[MM]/[dd]/[HH]/product/baseProduct/spellingFlower/file/"; //如果res配置文件中没有该filekey,用的默认地址
			String configKey = Utils.getPropertyName("config/res",fileKey,defaultPath).trim();
			configKey = Utils.replaceDate(configKey);
			String fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6)+".txt";
			filePath = Utils.getAbsolutePath(configKey + fileName, null);
		}
		if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) ){
			filePath = filePath.replace("\\", "/");
		}
		boolean uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath,structureSpellingFlower);
		if(uploadFtpFlag){
			Map <String, String>map = FileUploadUtils.getMap(new File(filePath), fileKey, false);
			boolean flag = resFileService.saveProductSpellingFlowerFile(baseProduct,map);//生成资源数据
			if( !flag ){
				resMap.put("success", "false");
				resMap.put("data", "保存资源数据库失败");
				return resMap;
			}
		}else{
			resMap.put("success", "false");
			resMap.put("data", "保存产品拼花文件失败");
			return resMap;
		}
		resMap.put("success", "true");
		return resMap;
	 
	}
	

	
	
	
	/**
	 * 保存结构拼花文本信息
	 * @param structureProduct
	 * @param structureSpellingFlower
	 * @return resMap
	 */
	public Map<String,String>  saveStructureSpellingFlowerFile(StructureProduct structureProduct,String structureSpellingFlower){
		Map<String,String>resMap = new HashMap<String,String>();
		if(StringUtils.isEmpty(structureSpellingFlower) || structureProduct == null) {
			resMap.put("success", "false");
			return resMap;
		}
		String filePath = null;
		if(structureProduct.getSpellingFlowerFileId()!=null && structureProduct.getSpellingFlowerFileId().intValue() > 0) {
			ResFile resFile = resFileService.get(structureProduct.getSpellingFlowerFileId());
			if(resFile != null) {
				filePath = Utils.getAbsolutePath(resFile.getFilePath(), null);
			}
		}
		String fileKey = "product.structureProduct.spellingFlower.file.upload.path";
		if(StringUtils.isEmpty(filePath)) { //如果拼花路径为空  代表第1次增加拼花信息
			String defaultPath = "/AA/c_basedesign/[YYYY]/[MM]/[dd]/[HH]/product/structureProduct/spellingFlower/file/"; //如果res配置文件中没有该filekey,用的默认地址
			String configKey = Utils.getPropertyName("config/res",fileKey,defaultPath).trim();
			configKey = Utils.replaceDate(configKey);
			String fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6)+".txt";
			filePath = Utils.getAbsolutePath(configKey + fileName, null);
		}
 
		if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) ){
			filePath = filePath.replace("\\", "/");
		}
		boolean uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath,structureSpellingFlower);
		if(uploadFtpFlag){
			Map <String, String>map = FileUploadUtils.getMap(new File(filePath), fileKey, false);
			boolean flag = resFileService.saveStructureSpellingFlowerFile(structureProduct,map);//生成资源数据
			if( !flag ){
				resMap.put("success", "false");
				resMap.put("data", "保存资源数据库失败");
				return resMap;
			}
		}else{
			resMap.put("success", "false");
			resMap.put("data", "保存结构拼花文件失败");
			return resMap;
		}
		resMap.put("success", "true");
		return resMap;
	 
	}

	
	/**
	 * 保存结构拼花文本信息、产品拼花文本信息
	 * @param productSpellingFlower
	 * @param structureSpellingFlower
	 * @param msgId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getStructureSpellingFlowerTxt(String msgId, String structureIds) {
		if(StringUtils.isEmpty(msgId) || StringUtils.isEmpty(structureIds) ){
			return new ResponseEnvelope<>(false, "缺少参数", msgId);
		}	
		String [] arr = structureIds.split(",");
		if(arr == null || arr.length <= 0 || "".equals(arr)) {
			return new ResponseEnvelope<>(false, "structureIds参数结构不对", msgId);
		}
		List<Integer>ids = new ArrayList<Integer>();
		for (String id : arr) {
			ids.add(Integer.parseInt(id));
		}
		//查询所有结构
		List<StructureProduct>StructureProductList = this.getListByids(ids);
		
		List<Integer>resFileIds = new ArrayList<Integer>();
		if(StructureProductList != null && StructureProductList.size() > 0) {
			for (StructureProduct structure: StructureProductList) {
				if(structure.getSpellingFlowerFileId()!=null && structure.getSpellingFlowerFileId().intValue() > 0) {
					resFileIds.add(structure.getSpellingFlowerFileId());
				}
			}
		}
		Map<String,String>resMap = new HashMap<String,String>();
		if(resFileIds != null && resFileIds.size() > 0 ) {
			ResFileSearch resFileSearch = new ResFileSearch();
			resFileSearch.setResIdList(resFileIds);
			resFileSearch.setId(0);
			resFileSearch.setLimit(-1);
			resFileSearch.setStart(-1);
			//查询该结构的所有 拼花文件
			List <ResFile>spellingFlowerList = resFileService.getPaginatedList(resFileSearch);
			if(spellingFlowerList != null && spellingFlowerList.size() > 0) {
				//配对 填装数据
				for (StructureProduct structure: StructureProductList) {
					Integer spellingFlowerFileId = structure.getSpellingFlowerFileId();
					if(spellingFlowerFileId !=null && spellingFlowerFileId.intValue() > 0) {
						for (ResFile resFile : spellingFlowerList) {
							if(spellingFlowerFileId.intValue() == resFile.getId().intValue()) {
								resMap.put(structure.getId() + "", resFile.getFilePath());
							}
						}
					}
				}
			}	
		}
		ResponseEnvelope envelope = new ResponseEnvelope<>();
		envelope.setObj(JSONUtils.toJSONString(resMap));
		envelope.setMsgId(msgId);
		envelope.setSuccess(true);
		return envelope;
	}

	
	/**
	 * 通过ids 查询结构结合
	 * @param ids
	 * @return
	 */
	private List<StructureProduct> getListByids(List<Integer> ids) {
		if(ids == null || ids.size() <= 0 ) {
			return null;
		}
		return structureProductMapper.getListByids(ids);
	}
	
	
}