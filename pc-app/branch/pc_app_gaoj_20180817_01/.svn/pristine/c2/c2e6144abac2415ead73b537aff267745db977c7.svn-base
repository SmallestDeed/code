package com.nork.design.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nork.design.dao.DesignPlanProductDao;
import com.nork.design.model.*;
import com.nork.product.model.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.async.ProductSaveParameter;
import com.nork.common.cache.CommonCacher;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.FileModel;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.AesProperties;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.AESUtil2;
import com.nork.common.util.ClassReflectionUtils;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.IgnoreJsonPropertyFilter;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignPlanProductCacher;
import com.nork.design.cache.UsedProductsCacher;
import com.nork.design.controller.web.IntelligenceDecorationController.PosNameInfo;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.dao.DesignPlanProductMapper;
import com.nork.design.dao.DesignTempletMapper;
import com.nork.design.dao.OptimizePlanMapper;
import com.nork.design.model.ProductListByTypeInfo.PlanProductInfo;
import com.nork.design.model.search.DesignPlanProductSearch;
import com.nork.design.model.unity.BindPointDataEx;
import com.nork.design.service.DesignPlanProductRenderSceneService;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanRecommendedProductServiceV2;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.OptimizePlanService;
import com.nork.design.service.UsedProductsService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.dao.AuthorizedConfigMapper;
import com.nork.product.dao.BaseBrandMapper;
import com.nork.product.dao.BaseProductMapper;
import com.nork.product.model.result.DesignProductResult;
import com.nork.product.model.search.StructureProductSearch;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.product.service.ProductUsageCountService;
import com.nork.product.service.StructureProductService;
import com.nork.resource.model.ResUsedProducts;
import com.nork.resource.service.ResUsedProductsService;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

/**   
 * @Title: DesignPlanProductServiceImpl.java 
 * @Package com.nork.design.service.impl
 * @Description:设计方案-设计方案产品库ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-06-26 11:26:11
 * @version V1.0   
 */
@Service("designPlanProductService")
@Transactional
public class DesignPlanProductServiceImpl implements DesignPlanProductService {

	Logger logger = Logger.getLogger(DesignPlanProductServiceImpl.class);
	private static String PASSWORD_CRYPT_KEY= Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_KEY_FILEKEY, "41e5c74dd46e4ddcb942dc8ce6224a2e").trim();

	private DesignPlanProductMapper designPlanProductMapper;

	@Autowired
	public void setDesignPlanProductMapper(
			DesignPlanProductMapper designPlanProductMapper) {
		this.designPlanProductMapper = designPlanProductMapper;
	}
	
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private UsedProductsService usedProductsService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private ResUsedProductsService resUsedProductsService;
	@Autowired
	private AuthorizedConfigMapper authorizedConfigMapper;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
	@Autowired
	private StructureProductService structureProductService;
	@Autowired
	private ProductUsageCountService productUsageCountService;
	@Autowired
	private BaseProductMapper baseProductMapper;
	@Autowired
	private DesignPlanRecommendedProductServiceV2 designPlanRecommendedProductServiceV2;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private DesignTempletMapper designTempletMapper;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private DesignPlanProductRenderSceneService designPlanProductRenderSceneService;
	@Autowired
	private OptimizePlanMapper optimizePlanMapper;
	@Autowired
	private OptimizePlanService optimizePlanService;
	@Autowired
	private DesignPlanProductDao designPlanProductDao;
	@Autowired
    private BaseBrandService baseBrandService;

	@Autowired
	private DesignPlanProductService designPlanProductService;
	
	/**
	 * 新增数据
	 *
	 * @param designPlanProduct
	 * @return  int 
	 */
	@Override
	public int add(DesignPlanProduct designPlanProduct) {
		/*删除 进入该样板房的缓存*/
		Map<Object,Object>	paramsMap=new HashMap<>();
		paramsMap.put("designPlanId", designPlanProduct.getDesignPlanId());
		if(Utils.enableRedisCache()){
			CommonCacher.removeAll(ModuleType.DesignPlan, "getDesignPlanWeb", paramsMap);		
		}
		designPlanProductMapper.insertSelective(designPlanProduct);
		return designPlanProduct.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param designPlanProduct
	 * @return  int 
	 */
	@Override
	public int update(DesignPlanProduct designPlanProduct) {
		/*删除 进入该样板房的缓存*/
		Map<Object,Object>	paramsMap=new HashMap<>();
		paramsMap.put("designPlanId", designPlanProduct.getDesignPlanId());
		if(Utils.enableRedisCache()){
			CommonCacher.removeAll(ModuleType.DesignPlan, "getDesignPlanWeb", paramsMap);		
		}
		return designPlanProductMapper
				.updateByPrimaryKeySelective(designPlanProduct);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return designPlanProductMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignPlanProduct 
	 */
	@Override
	public DesignPlanProduct get(Integer id) {
		return designPlanProductMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  designPlanProduct
	 * @return   List<DesignPlanProduct>
	 */
	@Override
	public List<DesignPlanProduct> getList(DesignPlanProduct designPlanProduct) {
	    return designPlanProductMapper.selectList(designPlanProduct);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @return   int
	 */
	@Override
	public int getCount(DesignPlanProductSearch designPlanProductSearch){
		return  designPlanProductMapper.selectCount(designPlanProductSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @return   List<DesignPlanProduct>
	 */
	@Override
	public List<DesignPlanProduct> getPaginatedList(
			DesignPlanProductSearch designPlanProductSearch) {
		return designPlanProductMapper.selectPaginatedList(designPlanProductSearch);
	}

	/**
	 * 其他
	 * 
	 */
	@Override
	public List<UnityPlanProduct> unityProductList(Integer templetId) {
		return designPlanProductMapper.unityProuctList(templetId);
	}

	@Override
	public List<DesignPlanProductResult> planProductList(
			DesignPlanProduct designPlanProduct) {
		return designPlanProductMapper.planProductList(designPlanProduct);
	}

	@Override
	public int planProductCount(DesignPlanProduct designPlanProduct) {
		return designPlanProductMapper.planProductCount(designPlanProduct);
	}

	@Override
	public int costListCount(DesignPlanProduct designPlanProduct){
		return designPlanProductMapper.costListCount(designPlanProduct);
	}

	/**
	 * 结算汇总清单
	 * @return
	 */
	@Override
	public List<ProductsCost> costList(ProductsCostType productsCostType){
		return designPlanProductMapper.costList(productsCostType);
	}
	@Override
	public List<ProductsCost> costListShare(ProductsCostType productsCostType){
		return designPlanProductMapper.costListShare(productsCostType);
	}
	/**
	 * 结算清单明细
	 * @param cost
	 * @return
	 */
	@Override
	public List<ProductCostDetail> costDetail(ProductsCost cost){
		return designPlanProductMapper.costDetail(cost);
	}
	@Override
	public List<ProductCostDetail> costDetailShare(ProductsCost cost){
		return designPlanProductMapper.costDetailShare(cost);
	}
	
	@Override
	public int costTypeListCount(DesignPlanProduct designPlanProduct){
		return designPlanProductMapper.costTypeListCount(designPlanProduct);
	}
	@Override
	public int costTypeListCountShare(DesignPlanProduct designPlanProduct){
		return designPlanProductMapper.costTypeListCountShare(designPlanProduct);
	}

	@Override
	public int costTypeListCount(DesignPlanProduct designPlanProduct, costListEnum type) {
		
		// 参数验证/处理 ->start
		if(designPlanProduct == null) {
			return 0;
		}
		if(type == null) {
			type = costListEnum.designPlan;
		}
		// 参数验证/处理 ->end
		
		if(type == costListEnum.designPlan) {
			return designPlanProductMapper.costTypeListCount(designPlanProduct);
		}else if(type == costListEnum.designPlanRenderScene) {
			return designPlanProductRenderSceneService.costTypeListCount(designPlanProduct);
		}else if(type == costListEnum.designPlanRecommended) {
			return designPlanRecommendedProductServiceV2.costTypeListCount(designPlanProduct);
		}else if(type == costListEnum.oneKeyDesignPlan){
			return optimizePlanMapper.costTypeListCount(designPlanProduct);
		}else {
			
		}
		
		return 0;
	}
	
	private List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct, costListEnum type) {
		// 参数验证/处理 ->start
		if(designPlanProduct == null) {
			return null;
		}
		if(type == null) {
			type = costListEnum.designPlan;
		}
		// 参数验证/处理 ->end
		
		if(type == costListEnum.designPlan) {
			return designPlanProductMapper.costTypeList(designPlanProduct);
		}else if(type == costListEnum.designPlanRenderScene) {
			return designPlanProductRenderSceneService.costTypeList(designPlanProduct);
		}else if(type == costListEnum.designPlanRecommended) {
			return designPlanRecommendedProductServiceV2.costTypeList(designPlanProduct);
		}else if(type == costListEnum.oneKeyDesignPlan){
			return optimizePlanMapper.costTypeList(designPlanProduct);
		}else {
			
		}
		
		return null;
	}
	
	/**
	 * 结算类型汇总清单
	 * @param designPlanProduct
	 * @return
	 */
	@Override
	public List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct){
		return designPlanProductMapper.costTypeList(designPlanProduct);
	}
	/**
	 * 结算类型汇总清单
	 * @param designPlanProduct
	 * @return
	 */
	@Override
	public List<ProductsCostType> costTypeListShare(DesignPlanProduct designPlanProduct){
		return designPlanProductMapper.costTypeListShare(designPlanProduct);
	}

	
	/**
	 * 
	 * 通过产品code去找同类型的产品挂接点，设计方案id和挂接点更新产品
	 * 如果存在，则更新
	 * 如果不存在，则插入
	 * **/
	@Override
	public int updateDesignPlanProduct(Integer designPlanId,String posIndexPath,Integer productId,String posName,String context,Integer bindProductId, LoginUser loginUser) {
		Integer designPlanProductId = -1;
		if(designPlanId==null || StringUtils.isEmpty(posIndexPath)||productId==null||StringUtils.isBlank(posName)){
			return -1;
		}
		//获取设计方案
		DesignPlan dpp = designPlanService.get(designPlanId);
		if(dpp==null){
		   return -1;
		}
		//获取产品
		BaseProduct baseProduct =baseProductService.get(productId);
		if(baseProduct==null){
			return -1;
		}
		//更新设计方案产品
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setIsDeleted(0);
		designPlanProduct.setPlanId(designPlanId);
		designPlanProduct.setPosIndexPath(posIndexPath);
		designPlanProduct.setPosName(posName);
		List<DesignPlanProduct> list = designPlanProductMapper.selectList(designPlanProduct);
		if(list!= null && list.size()>0){
			if(list.size()>1){
			   return -1;
			}
			DesignPlanProduct record = list.get(0);
			if(record==null){
			   return -1;
			}
			designPlanProduct = new DesignPlanProduct();
			designPlanProduct.setId(record.getId());
			designPlanProduct.setProductId(productId);
			designPlanProduct.setIsHide(0);
			designPlanProduct.setIsDeleted(0);
			designPlanProduct.setSplitTexturesChooseInfo(baseProduct.getSplitTexturesInfo());
			sysSave(designPlanProduct, loginUser);
			designPlanProductMapper.updateByPrimaryKeySelective(designPlanProduct);	
			designPlanProductId = designPlanProduct.getId();
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(dpp.getId());
			newDesignPlan.setGmtModified(new Date());
			designPlanService.update(newDesignPlan);
		}else{
			DesignPlanProduct record = new DesignPlanProduct();
			record.setSplitTexturesChooseInfo(baseProduct.getSplitTexturesInfo());
			record.setIsDeleted(0);
			record.setPlanId(dpp.getId());
			record.setPosIndexPath(posIndexPath);
			logger.info("==========设计方案新增产品：productId = " + productId + "; posIndexPath = " + posIndexPath + "==========");
			record.setPosName(posName);
			record.setProductId(productId);
			record.setPlanProductId(0);
			record.setIsHide(0);
			record.setDisplayStatus(0);
			record.setInitProductId(productId);
			if( bindProductId != null && bindProductId > 0 ){
				record.setInitProductId(bindProductId);
			}
			sysSave(record, loginUser);
			designPlanProductMapper.insertSelective(record);
			designPlanProductId = record.getId();
			
			DesignPlanProductCacher.remove(designPlanProductId); 
			
			if(baseProduct.getProductCode() != null && !baseProduct.getProductCode().startsWith("baimo_")){
				UsedProducts usedProducts = new UsedProducts();
				usedProducts.setCreator(dpp.getCreator());
				usedProducts.setGmtCreate(new Date());
				usedProducts.setGmtModified(new Date());
				usedProducts.setModifier(dpp.getCreator());
				usedProducts.setIsDeleted(0);
				usedProducts.setUserId(dpp.getUserId());
				usedProducts.setDesignId(designPlanId);
				usedProducts.setProductId(productId);
				if( baseProduct != null && StringUtils.isNotEmpty(baseProduct.getMaterialPicIds()) ){
					String arr[] = baseProduct.getMaterialPicIds().split(",");
					if(arr.length>0){
						usedProducts.setNuma1(Utils.getIntValue(arr[0]));
					}
				}
				//usedProducts.setAtt1(context);
				sysSave(usedProducts, loginUser);
				int id = usedProductsService.add(usedProducts);
				UsedProductsCacher.remove(id);
				
				//配置内容存储到文件中
				/**TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值，则上传到web服务器。**/
				Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
				boolean uploadFtpFlag = false;
				//已使用配置内容存储路径
//				String usedConfigPath = Utils.getValue("design.designPlan.usedConfig.upload.path","/design/designPlan/[code]/usedConfig/");
//				usedConfigPath = usedConfigPath.replace("[code]",dpp.getPlanCode());
				
				/*String usedConfigPath = Utils.getValue("design.designPlan.usedConfig.upload.path","/AA/e_userlogs/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/usedConfig/");*/
				String usedConfigPath = Utils.getValueByFileKey(ResProperties.RES, ResProperties.DESIGNPLAN_USEDCONFIG_FILEKEY, "/AA/e_userlogs/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/usedConfig/");
				usedConfigPath = Utils.replaceDate(usedConfigPath);
				
				//文件名称
				String fileName = id+".txt";
				//先把文件保存到本地
				usedConfigPath = Utils.replaceDate(usedConfigPath);
				/*String filePath = Constants.UPLOAD_ROOT + usedConfigPath + fileName;*/
				String filePath = Utils.getAbsolutePath(usedConfigPath + fileName, Utils.getAbsolutePathType.encrypt);
				uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath,context);
				//获取文件大小
				Map map = FileUploadUtils.getMap(new File(filePath), "design.designPlan.usedConfig.upload.path", false);
				String fileSize =null;
				if(map!=null&&map.size()>0){
					 fileSize = map.get(FileModel.FILE_SIZE).toString();
				}
				if( uploadFtpFlag ) {
					//上传方式为2或者3表示文件在ftp上
					if (ftpUploadMethod == 2) {
						uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, usedConfigPath);
					} else if (ftpUploadMethod == 3) {
						uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, usedConfigPath);
						//删除本地文件
						/*FileUploadUtils.deleteDir(new File(Constants.UPLOAD_ROOT + usedConfigPath).getParentFile());*/
						FileUploadUtils.deleteDir(new File(Utils.getAbsolutePath(usedConfigPath, Utils.getAbsolutePathType.encrypt)).getParentFile());
					}
				}
				/*已使用产品资源记录    start*/
				ResUsedProducts resUsedProducts = new ResUsedProducts();
				resUsedProducts.setFileCode(record.getSysCode());
				resUsedProducts.setFileName(fileName.substring(0, fileName.lastIndexOf(".")));
				resUsedProducts.setFileOriginalName(fileName);
				//resUsedProducts.setFileType(fileType);
				resUsedProducts.setFileSize(fileSize);
				resUsedProducts.setFileSuffix(fileName.substring(fileName.lastIndexOf(".")));
				//resUsedProducts.setFileLevel();
				resUsedProducts.setFilePath(usedConfigPath + fileName);
				//resUsedProducts.setFileDesc(fileDesc);
				//resUsedProducts.setFileOrdering(fileOrdering);
				resUsedProducts.setFileKey("design.designPlan.usedConfig");
				resUsedProducts.setBusinessId(id);//已使用产品id
				resUsedProducts.setGmtModified(new Date());
				//resUsedProducts.setRemark(remark);
				sysSave(resUsedProducts,loginUser);
				int fileId = resUsedProductsService.add(resUsedProducts);
				//回填fileId
				UsedProducts products = usedProductsService.get(id);
				if(products != null){
					UsedProducts newProducts = new UsedProducts();
					newProducts.setId(products.getId());
					newProducts.setResUsedId(fileId);
					usedProductsService.update(newProducts);
				}
			}
			/*已使用产品资源记录    end*/
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(dpp.getId());
			newDesignPlan.setGmtModified(new Date());
			designPlanService.update(newDesignPlan);
			
		}
		return designPlanProductId;
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResUsedProducts model, LoginUser loginUser){
		if(model != null){
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
	private void sysSave(DesignPlanProduct model,LoginUser loginUser){
		if(model != null){
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
    private void sysSave(UsedProducts model, LoginUser loginUser) {
        if (model != null) {
            if (model.getId() == null) {
                model.setGmtCreate(new Date());
                model.setCreator(loginUser.getLoginName());
                model.setIsDeleted(0);
                if (model.getSysCode() == null || "".equals(model.getSysCode())) {
                    model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }

            model.setGmtModified(new Date());
            model.setModifier(loginUser.getLoginName());
        }
    }

	/**
	 * 解除设计方案产品中的组关系
	 * @author huangsongbo
	 * @param designPlanId
	 * @param planGroupId
	 * @return
	 */
	public int relieveGroupByPlanIdAndplanGroupId(Integer designPlanId, String planGroupId) {
		/*删除 进入该样板房的缓存*/
		Map<Object,Object>	paramsMap=new HashMap<>();
		paramsMap.put("designPlanId", designPlanId);
		if(Utils.enableRedisCache()){
			CommonCacher.removeAll(ModuleType.DesignPlan, "getDesignPlanWeb", paramsMap);		
		}
		
		return designPlanProductMapper.relieveGroupByPlanIdAndplanGroupId(designPlanId, planGroupId);
	}

	/**
	 * 批量新增设计方案产品
	 * @param planProductList
	 */
	@Override
	public void batchAdd(List<DesignPlanProduct> planProductList) {
		if( planProductList != null && planProductList.size() > 0 ){
			/*删除 进入该样板房的缓存*/
			Map<Object, Object> paramsMap = new HashMap<>();
			paramsMap.put("designPlanId", planProductList.get(0).getDesignPlanId());
			if (Utils.enableRedisCache()) {
				CommonCacher.removeAll(ModuleType.DesignPlan, "getDesignPlanWeb", paramsMap);
			}
			designPlanProductMapper.batchAdd(planProductList);
		}
	}

	@Override
	public List<DesignPlanProductResult> planProductListV2(
			DesignPlanProduct designPlanProduct) {
		return designPlanProductMapper.planProductListV2(designPlanProduct);
	}

	@Override
	public DesignPlanProduct findIdByInitProductIdAndPlanId(Integer initProductId, Integer planId) {
		return designPlanProductMapper.findIdByInitProductIdAndPlanId(initProductId, planId);
	}

	public enum costListEnum{
		designPlan, designPlanRenderScene, designPlanRecommended,oneKeyDesignPlan
	}
	
	/**
	 * 获取设计方案费用列表
	 * @param loginUser
	 * @param designPlanProduct
	 */
	@Override
	public List<ProductsCostType> costList(LoginUser loginUser, DesignPlanProduct designPlanProduct, costListEnum type){
		if(type == null) {
			type = costListEnum.designPlan;
		}
		/*查询用户关联的序列号list*/
		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
		authorizedConfig.setUserId(loginUser.getId());
		authorizedConfig.setState(new Integer(1));
		//authorizedConfig.setTerminalImei(terminalImei);
		List<AuthorizedConfig> list = authorizedConfigMapper.selectList(authorizedConfig);
		List <String>slist=new ArrayList<String>();
                /*遍历序列号集合获取品牌id*/
                for (AuthorizedConfig auth : list) {
                    String brandIds =auth.getBrandIds();
                    slist.add(brandIds);
                }
                
                //String brandIds = authorizedConfig.getBrandIds();
                //Integer brandId=Integer.valueOf(sb.toString()).intValue();
                BaseBrand baseBrand=new BaseBrand();
                boolean flag = false;
                for (String brandid : slist) {
                    Integer brandId=Integer.valueOf(brandid).intValue();
                    baseBrand = baseBrandService.get(brandId);
                    if(Utils.isSanduUser(baseBrand.getBrandReferred())){
                        flag = true;
                        break;
                    }
                }
		/*查询用户关联的序列号list->end*/
		/*add品牌,大类,小类,产品查询条件*/
		List<BaseProduct> baseProductList=new ArrayList<BaseProduct>();
		/*if( 3 == loginUser.getUserType() ){*/
		// update by huangsongbo 2017.12.8
		if(Utils.isExternalUser(loginUser.getUserType())) {
		    //用户序列号如果绑定三度则产品清单不做过滤
		    if(!flag){
			if(list!=null&&list.size()>0){
				for(AuthorizedConfig authorizedConfigItem:list){
					/*查询条件注入到BaseProduct类中*/
					BaseProduct baseProduct=baseProductService.getBaseProductFromAuthorizedConfig(authorizedConfigItem);
					baseProductList.add(baseProduct);
				}
				/*设置查询条件(序列号)*/
				designPlanProduct.setBaseProduct(baseProductList);
			}
		    }
		}
		/*add品牌,大类,小类,产品查询条件->end*/
		int total = this.costTypeListCount(designPlanProduct, type);
		List<ProductsCostType> costTypeList = new ArrayList<ProductsCostType>();
		List<ProductsCost> costList = new ArrayList<ProductsCost>();
		if( total > 0 ){
			SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("productUnitPrice", "price_yuan");
			/*查询软硬装下面包含的产品小类等信息(带入序列号查询条件)*/
			costTypeList = this.costTypeList(designPlanProduct, type);
			for(ProductsCostType costType : costTypeList){
				costType.setPlanId(designPlanProduct.getPlanId());
				costType.setUserId(loginUser.getId());
				//costType.setAuthorizedConfigList(list);
				costType.setBaseProduct(baseProductList);
				/*得到结算汇总清单
				 * 设置查询条件(序列号)*/
				costList = this.costList(costType, type);
				List<ProductCostDetail> costDetails = null;
				int productCount = 0 ;
				for (ProductsCost cost : costList) {
					productCount += cost.getProductCount();
					/** 得到结算详情清单 */
					cost.setPlanId(designPlanProduct.getPlanId());
					cost.setUserId(loginUser.getId());
					cost.setAuthorizedConfigList(list);
					cost.setBaseProduct(baseProductList);
					if( dictionary != null ){
						cost.setSalePriceValueName(dictionary.getName());
					}
					if("1".equals(cost.getCostTypeValue())||"2".equals(cost.getCostTypeValue())||"3".equals(cost.getCostTypeValue())){
						BigDecimal salePrice = cost.getTotalPrice();
						BigDecimal totalPrice  = costType.getTotalPrice();
						BigDecimal new_totalPrice  = totalPrice.subtract(salePrice);
						costType.setTotalPrice(new_totalPrice);
						cost.setTotalPrice(null);
					}
					costDetails = this.costDetail(cost, type);
					if(costDetails!=null&&costDetails.size()>0){
						for (ProductCostDetail productCostDetail : costDetails) {
							//添加一个字段，前端用来判断背景墙是否可以删除，add by yangz
							if ("qiangm".equals(productCostDetail.getCategoryCode())) {//墙面
								productCostDetail.setBgWall(Utils.getIsBgWall(productCostDetail.getValuekey()));
							} else {
								productCostDetail.setBgWall(0);
							}
							//   end
							BaseProduct baseProduct = baseProductService.get(productCostDetail.getProductId());
							//讲 地面  墙面 天花的 价格 从总价中  去除
							if("1".equals(baseProduct.getProductTypeValue())||"2".equals(baseProduct.getProductTypeValue())||"3".equals(baseProduct.getProductTypeValue())){
								if(baseProduct!=null){
									productCostDetail.setTotalPrice(null);
									productCostDetail.setUnitPrice(null);
								}

							}
						}
					}
					cost.setDetailList(costDetails);
				}
				costType.setDetailList(costList);
				costType.setProductCount(productCount);
				if( dictionary != null ){
					costType.setSalePriceValueName(dictionary.getName());
				}
				costType.setSalePriceValueName(dictionary==null?"":dictionary.getName());
			}
		}
		return costTypeList;
	}

	private List<ProductsCost> costList(ProductsCostType productsCostType, costListEnum type) {
		// 参数验证/参数处理 ->start
		if(productsCostType == null) {
			return null;
		}
		if(type == null) {
			type = costListEnum.designPlan;
		}
		// 参数验证/参数处理 ->end
		
		if(type == costListEnum.designPlan) {
			return designPlanProductMapper.costList(productsCostType);
		}else if(type == costListEnum.designPlanRenderScene) {
			return designPlanProductRenderSceneService.costList(productsCostType);
		}else if(type == costListEnum.designPlanRecommended) {
			return designPlanRecommendedProductServiceV2.costList(productsCostType);
		}else if(type == costListEnum.oneKeyDesignPlan){
			return optimizePlanMapper.costList(productsCostType);
		}else {
			
		}
		
		return null;
	}

	private List<ProductCostDetail> costDetail(ProductsCost cost, costListEnum type) {
		// 参数验证/参数处理 ->start
		if(cost == null) {
			return null;
		}
		if(type == null) {
			type = costListEnum.designPlan;
		}
		// 参数验证/参数处理 ->end
		
		if(type == costListEnum.designPlan) {
			return designPlanProductMapper.costDetail(cost);
		}else if(type == costListEnum.designPlanRenderScene) {
			return designPlanProductRenderSceneService.costDetail(cost);
		}else if(type == costListEnum.designPlanRecommended) {
			return designPlanRecommendedProductServiceV2.costDetail(cost);
		}else if(type == costListEnum.oneKeyDesignPlan){
			return optimizePlanMapper.costDetail(cost);
		}else {
			
		}
		
		return null;
	}

	/**
	 *  通过解析配置文件中的内容，解析json，并自动生成产品列表
	 * @param designPlan
	 * @param context
	 * @return
	 * @throws Exception
	 */
		JSONObject resultJSON = new JSONObject();
		public JSONObject analysisJson(Integer designTempletId ,Integer recommendedPlanId, DesignPlan designPlan, String context, LoginUser loginUser) throws Exception {
		resultJSON.accumulate("success",false);
		StringBuffer  sb = new StringBuffer("");
		String newDate = String.valueOf(System.currentTimeMillis());
		// 存结构名称->initProductCode
		Map<String, String> map = new HashMap<String, String>();
		//配置文件解密
		try {
			if(PASSWORD_CRYPT_KEY.length() < 8){
				PASSWORD_CRYPT_KEY = String.format("%1$0"+(8-PASSWORD_CRYPT_KEY.length())+"d",0);
			}else{
				PASSWORD_CRYPT_KEY = PASSWORD_CRYPT_KEY.substring(0,8);
			}
			context = AESUtil2.decrypt(context,PASSWORD_CRYPT_KEY);
		} catch (Exception e1) {
			logger.error("文件解密异常:designTempletId="+designTempletId+ e1.getMessage());
			resultJSON.remove("success");
			resultJSON.accumulate("success", false);
			resultJSON.accumulate("message","文件解密异常");
			return resultJSON;
		}
		try {
			//读取配置文件中的内容
//			String fileContext = FileUploadUtils.getFileContext(filePath);
			//将配置文件中的内容转换成json格式
			JSONObject jsonObject = JSONObject.fromObject(context);
			JSONArray jsonArray = (JSONArray) jsonObject.get("RoomConfig");
			JsonConfig jsonConfig = getJsonConfig("JsonProduct");
			List<JsonProduct> productCodes = (List<JsonProduct>) JSONArray.toCollection(jsonArray, jsonConfig);

			//白膜的产品列表
			List<DesignProductResult> templetProductList =  baseProductMapper.getTempletProductList(null,designTempletId);
			if (Lists.isEmpty(templetProductList)) {
				resultJSON.remove("success");
				resultJSON.accumulate("success", false);
				resultJSON.accumulate("message","找不到样板房产品列表");
				return resultJSON;
			}
			//设计方案产品列表
			/*List<DesignProductResult> planProductList = baseProductMapper.getPlanProductList(planId);*/
			List<DesignProductResult> planProductList = designPlanRecommendedProductServiceV2.getPlanProductList(recommendedPlanId);
			if (Lists.isEmpty(planProductList)) {
				resultJSON.remove("success");
				resultJSON.accumulate("success", false);
				resultJSON.accumulate("message","找不到模板方案产品列表");
				return resultJSON;
			}

			//组装需要保存的数据
			List<DesignPlanProduct> designProductList = new ArrayList<>();
			for (JsonProduct jsonProduct : productCodes) {
				DesignPlanProduct designProduct = new DesignPlanProduct();
				BaseProduct baseProduct = null ;
				BaseProduct structureBmProduct = null;
				DesignProductResult initProduct = null;
				DesignProductResult planProduct = null;
				DesignProductResult planProduct1 = null;
				DesignProductResult planProduct2 = null;
				DesignProductResult planProduct3 = null;
				DesignProductResult wallTypePlan = null;
				DesignProductResult wallOrientationPlan = null;
				//获取产品对象
				if( StringUtils.isNotEmpty(jsonProduct.getItemCode()) ){
					baseProduct = baseProductService.findOneByCode(jsonProduct.getItemCode());
				}else{
					continue;
				}
				//获取初始化产品对象
				if (StringUtils.isNotEmpty(jsonProduct.getInitProductCode())) {
					for (DesignProductResult templetProductResult : templetProductList) {
						if (jsonProduct.getInitProductCode().equals(templetProductResult.getProductCode())
								&& jsonProduct.getInitPosName().equals(templetProductResult.getPosName())) {
							initProduct = templetProductResult;
							break;
						}
					}
				}else{

					//组合没有存InitProductCode
					for (DesignProductResult designProductResult : planProductList) {
						if (jsonProduct.getItemCode().equals(designProductResult.getProductCode())
								&& jsonProduct.getPlanGroupId().equals(designProductResult.getPlanGroupId())) {
							initProduct = designProductResult;
							break;
						}
					}

					// 说明plan_group_id是重新生成的,(适用于多对一/多对少),就只对比plan_group_id "_"前面的字符(group_id)
					if(initProduct == null){
						for (DesignProductResult designProductResult : planProductList) {
							if (jsonProduct.getItemCode().equals(designProductResult.getProductCode())
									&& jsonProduct.getPlanGroupId().substring(0, jsonProduct.getPlanGroupId().indexOf("_")).
									equals(designProductResult.getPlanGroupId().substring(0, designProductResult.getPlanGroupId().indexOf("_")))) {
								initProduct = designProductResult;
								break;
							}
						}
					}

				}
				//结构才有这个属性值，结构白膜产品编码
				if (StringUtils.isNotEmpty(jsonProduct.getStructureProductCode())) {
					structureBmProduct = baseProductService.findOneByCode(jsonProduct.getStructureProductCode());
				}
				if (baseProduct != null && baseProduct.getId() > 0) {
					sysSave(designProduct, loginUser);
					designProduct.setProductId(baseProduct.getId());
					designProduct.setPlanId(designPlan.getId());
					// 通过挂节点来生成排序
					String productSequence = com.nork.common.util.StringUtils.replaceString(jsonProduct.getPosIndexPath(), "/");
					designProduct.setProductSequence(productSequence);
					designProduct.setPosIndexPath(jsonProduct.getPosIndexPath());
					designProduct.setPosName(jsonProduct.getPosName());
					if( baseProduct.getProductCode().startsWith("baimo_") && initProduct !=null ){
						designProduct.setIsMainProduct(initProduct.getIsMainProduct()==null?0:initProduct.getIsMainProduct());
						designProduct.setPlanGroupId(initProduct.getPlanGroupId());
						designProduct.setProductGroupId(initProduct.getProductGroupId()==null?0:initProduct.getProductGroupId());
						designProduct.setGroupType(initProduct.getGroupType()==null?0:initProduct.getGroupType());
						//3.0版本新增的字段信息
						setPlanProductInfo(designProduct,initProduct);

					}else{
						//副墙匹配多维材质信息用到
						String orientation = "";
						String viceOrientation = "";//副方位
						if (initProduct != null) {
							String wallOrientae = initProduct.getWallOrientation();
							if (StringUtils.isNotEmpty(wallOrientae) && !"0".equals(wallOrientae)) {
								orientation = wallOrientae.substring(0,1);
								if(wallOrientae.length() == 3){
									viceOrientation = initProduct.getWallOrientation().substring(1,3);
								}
							}
						}

						//获取方案产品属性或样板房白膜产品属性、（同产品同白膜分类）
						for (DesignProductResult designProductResult : planProductList) {
							if (initProduct != null && initProduct.getBigTypeValue().equals(designProductResult.getBigTypeValue())) {
								if (baseProduct.getId().equals(designProductResult.getProductId())) {
									//组合产品
									if (StringUtils.isEmpty(jsonProduct.getInitProductCode())
											&& jsonProduct.getPlanGroupId().equals(designProductResult.getPlanGroupId())) {
										planProduct1 = designProductResult;
										break;
									}else {
										//同分类同产品
										planProduct = designProductResult;
										break;
									}
								} else {
									//同分类不同产品
									if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(designProductResult.getBigTypeKey())) {
										if (initProduct.getProductGroupId() != null && initProduct.getProductGroupId() > 0) {
											int tmp = 0;
											//判断样板房结构背景墙分类和方位是否在方案中存在。
											if (StringUtils.isNotEmpty(designProductResult.getWallType()) && StringUtils.isNotEmpty(designProductResult.getWallOrientation())) {
												for (DesignProductResult desPro : planProductList) {
													if (StringUtils.isNotEmpty(initProduct.getWallType())
															&& initProduct.getWallType().equals(desPro.getWallType())) {
														tmp++;
														break;
													}
													if (StringUtils.isNotEmpty(initProduct.getWallOrientation())
															&& initProduct.getWallOrientation().equals(desPro.getWallOrientation())) {
														tmp++;
														break;
													}
												}
												//不存在进行处理一层
												if (tmp == 0) {
													//由于副墙墙体分类和主墙墙体分类不一致，导致副墙匹配的背景墙和主墙不一致。程序纠正副墙墙体分类
													if (StringUtils.isNotEmpty(viceOrientation) && !viceOrientation.equals("00")) {
														for (DesignProductResult dpr : templetProductList) {
															if ((orientation + "00").equals(dpr.getWallOrientation())) {
																initProduct.setWallType(dpr.getWallType());
																break;
															} else {
																continue;
															}
														}
													}
												}
											}
											if (StringUtils.isNotEmpty(initProduct.getWallType())
													&& initProduct.getWallType().equals(designProductResult.getWallType())) {
												//有结构ID则在按序号匹配
												if (initProduct.getProductIndex().equals(designProductResult.getProductIndex())) {
													planProduct2 = designProductResult;
													break;
												} else {
													wallTypePlan = designProductResult;
												}
											} else {
												if (StringUtils.isNotEmpty(initProduct.getWallOrientation())
														&& initProduct.getWallOrientation().equals(designProductResult.getWallOrientation())) {
													wallOrientationPlan = designProductResult;
												}
											}
										} else {
											//由于副墙墙体分类和主墙墙体分类不一致，导致副墙匹配的背景墙和主墙不一致。程序纠正副墙墙体分类
											if (StringUtils.isNotEmpty(viceOrientation) && !viceOrientation.equals("00")) {
												for (DesignProductResult dpr : templetProductList) {
													if ((orientation+"00").equals(dpr.getWallOrientation())) {
														initProduct.setWallType(dpr.getWallType());
														break;
													}else {
														continue;
													}
												}
											}

											//不是结构组则按墙体方位和墙体类型匹配
											if (StringUtils.isNotEmpty(initProduct.getWallType())
													&& StringUtils.isNotEmpty(initProduct.getWallOrientation())
													&& initProduct.getWallType().equals(designProductResult.getWallType())
													&& initProduct.getWallOrientation().equals(designProductResult.getWallOrientation())) {
												planProduct2 = designProductResult;
												break;
											}else if (StringUtils.isNotEmpty(initProduct.getWallType())
													&& StringUtils.isNotEmpty(initProduct.getWallOrientation())
													&& initProduct.getWallType().equals(designProductResult.getWallType())
													&& StringUtils.isNotEmpty(orientation)
													&& ("."+designProductResult.getWallOrientation()).indexOf("."+orientation) != -1) {
												planProduct2 = designProductResult;
												break;
											}  else {
												if (StringUtils.isNotEmpty(initProduct.getWallType())
														&& initProduct.getWallType().equals(designProductResult.getWallType())) {
													wallTypePlan = designProductResult;
												}
												if (StringUtils.isNotEmpty(initProduct.getWallOrientation())
														&& initProduct.getWallOrientation().equals(designProductResult.getWallOrientation())) {
													wallOrientationPlan = designProductResult;
												}else if (StringUtils.isNotEmpty(orientation) && ("."+designProductResult.getWallOrientation()).indexOf("."+orientation) != -1) {
													wallOrientationPlan = designProductResult;
												}
											}
										}
									} else if ("dzho".equals(designProductResult.getSmallTypeKey()) || "basic_dzho".equals(designProductResult.getSmallTypeKey())) {
										if (StringUtils.isNotEmpty(initProduct.getWallType())
												&& initProduct.getWallType().equals(designProductResult.getWallType())) {
											planProduct2 = designProductResult;
											break;
										}else{
											wallTypePlan = designProductResult;
										}
									}else if ("meng".equals(designProductResult.getBigTypeKey())) {
										if (designProductResult.getBmSmallTypeValue() == initProduct.getSmallTypeValue()) {
											planProduct2 = designProductResult;
											break;
										}
									}else {
										planProduct3 = designProductResult;
										continue;
									}
								}
							}
						}
						if (planProduct2 == null && wallTypePlan != null) {
							planProduct2 = wallTypePlan;
						}
						if (planProduct2 == null && wallOrientationPlan != null) {
							planProduct2 = wallOrientationPlan;
						}
						if (planProduct2 == null && planProduct3 != null) {
							planProduct2 = planProduct3;
						}
						if (StringUtils.isNotEmpty(jsonProduct.getStructCode())) {
							//结构处理
							StructureProductSearch structureProductSearch = new StructureProductSearch();
							structureProductSearch.setStructureCode(jsonProduct.getStructCode());
							structureProductSearch.setIsDeleted(0);
							structureProductSearch.setStart(0);
							structureProductSearch.setLimit(1);
							List<StructureProduct> list = structureProductService.getPaginatedList(structureProductSearch);
							if( list != null && list.size() > 0 ){
								StructureProduct structureProduct = list.get(0);

								if(map.containsKey(jsonProduct.getInitProductCode())) {
									// 应对特殊情况:有两个一模一样的结构,plan_group_id的时间搓要更新
									newDate = map.get(jsonProduct.getInitProductCode());
								}else {
									newDate = String.valueOf(System.currentTimeMillis() + Utils.generateRandomDigitString(6));
								}

								designProduct.setPlanGroupId(structureProduct.getId()+"_"+newDate);
								designProduct.setProductGroupId(structureProduct.getId());
								designProduct.setGroupType(1);
								setPlanProductInfo(designProduct,initProduct);

								// 特殊处理
								map.put(jsonProduct.getInitProductCode(), newDate);

							}
						}else if (planProduct != null) {
							setPlanProductInfo(designProduct,planProduct,initProduct,baseProduct);
						}else if (planProduct1 != null) {
							setPlanProductInfo(designProduct,planProduct1,initProduct,baseProduct);
						} else if (planProduct2 != null) {
							setPlanProductInfo(designProduct,planProduct2,initProduct,baseProduct);
						} else{
							designProduct.setSplitTexturesChooseInfo(baseProduct.getSplitTexturesInfo());
						}
					}
				}
				//更新绑定点信息（如背景墙绑定墙，需把背景墙信息更新到墙的BindParentProductId）
				//如果该产品有绑定点，则去找样板房白膜绑定点关系（配置文件记录的是产品）
				JSONArray bindPointDataEx = jsonProduct.getBindPointDataEx();
				JsonConfig jc = getJsonConfig("BindPointDataEx");
				List<BindPointDataEx> bindPointDataExList = (List<BindPointDataEx>) JSONArray.toCollection(bindPointDataEx,jc);
				if (Lists.isNotEmpty(bindPointDataExList)) {
					// test ->start
					/*if(initProduct == null){
						//System.out.println();
					}*/
					// test ->end
					designProduct.setBindParentProductId(initProduct.getBindParentProductId());
				}
				if (structureBmProduct != null) {
					designProduct.setInitProductId(structureBmProduct.getId());
				}else if (initProduct != null) {
					designProduct.setInitProductId(initProduct.getProductId());
				}else{
					designProduct.setInitProductId(baseProduct.getId());
				}
				designProductList.add(designProduct);
			}
			String sb_  = sb.toString();
			if(sb_!=null&&!"".equals(sb_)){
				resultJSON.remove("success");
				resultJSON.accumulate("success", false);
				resultJSON.accumulate("message",sb_);
				return resultJSON;
			}
			//排序
			ComparatorT comparatorT = new ComparatorT();
			Collections.sort(designProductList, comparatorT);
			logger.error("开始存方案产品和已使用产品");
			//保存到样板间产品表
			for (DesignPlanProduct configData : designProductList) {
				designPlanProductMapper.insertSelective(configData);
				//存储产品到已使用列表
				if (StringUtils.isNotEmpty(configData.getProductCode()) && !configData.getProductCode().startsWith("baimo_")) {
					UsedProducts usedProducts = new UsedProducts();
					usedProducts.setCreator(designPlan.getCreator());
					usedProducts.setUserId(designPlan.getUserId());
					usedProducts.setDesignId(designPlan.getId());
					usedProducts.setProductId(configData.getProductId());
					usedProducts.setGroupId(configData.getProductGroupId());
					usedProducts.setRemark("一键生成");
					sysSave(usedProducts, loginUser);
					usedProductsService.add(usedProducts);
				}
			}
			resultJSON.remove("success");
			resultJSON.accumulate("success", true);
		} catch (Exception e) {
			/*e.printStackTrace();*/
			logger.error(e.getMessage(), e);
			logger.error("异常");
			resultJSON.remove("success");
			resultJSON.accumulate("success", false);
			resultJSON.accumulate("message","更新设计方案产品列表数据异常");
			return resultJSON;
		}
		return resultJSON;
	}

	public static JsonConfig getJsonConfig(String type){
		JsonConfig jsonConfig = new JsonConfig();
		if(type.equals("JsonProduct")){
			jsonConfig.setRootClass(JsonProduct.class);
		}else{
			jsonConfig.setRootClass(BindPointDataEx.class);
		}
		//处理json中key的首字母大写情况
		jsonConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
			@Override
			public String transformToJavaIdentifier(String s) {
				char[] chars = s.toCharArray();
				chars[0] = Character.toLowerCase(chars[0]);
				return new String(chars);
			}
		});
		//过滤在json中Entity没有的属性
		jsonConfig.setJavaPropertyFilter(new IgnoreJsonPropertyFilter());
		return jsonConfig;
	}

	// 根据产品顺序排序（升序）
	public class ComparatorT implements Comparator {
		public int compare(Object obj1, Object obj2) {
			DesignPlanProduct unity1 = (DesignPlanProduct) obj1;
			DesignPlanProduct unity2 = (DesignPlanProduct) obj2;
			int flag = (unity1.getProductSequence() == null ? new Integer(0) : new Integer(unity1.getProductSequence()))
					.compareTo(unity2.getProductSequence() == null ? new Integer(0)
							: new Integer(unity2.getProductSequence()));
			if (flag == 0) {
				return (unity1.getProductSequence() == null ? new Integer(0) : new Integer(unity1.getProductSequence()))
						.compareTo(unity2.getProductSequence() == null ? new Integer(0)
								: new Integer(unity2.getProductSequence()));
			} else {
				return flag;
			}
		}
	}

	private void setPlanProductInfo(DesignPlanProduct designProduct, DesignProductResult initProduct){
		designProduct.setMeasureCode(initProduct.getMeasureCode());
		designProduct.setRegionMark(initProduct.getRegionMark());
		designProduct.setCenter(initProduct.getCenter());
		designProduct.setStyleId(initProduct.getStyleId());
		designProduct.setIsStandard(initProduct.getIsStandard());
		designProduct.setProductIndex(initProduct.getProductIndex());
		designProduct.setWallType(initProduct.getWallType());
		designProduct.setWallOrientation(initProduct.getWallOrientation());
		designProduct.setIsGroupReplaceWay(initProduct.getIsGroupReplaceWay());
		designProduct.setIsMainStructureProduct(initProduct.getIsMainStructureProduct());
	}

	private void setPlanProductInfo(DesignPlanProduct designProduct, DesignProductResult planProduct, DesignProductResult initProduct,BaseProduct baseProduct){
		designProduct.setSplitTexturesChooseInfo(this.getSplitTexturesInfo(baseProduct,planProduct));
		//结构组合用的样板房结构数据
		if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(initProduct.getBigTypeKey())  && initProduct.getProductGroupId() != null
				&& initProduct.getProductGroupId() > 0) {
				designProduct.setIsMainProduct(initProduct.getIsMainProduct());
				designProduct.setPlanGroupId(initProduct.getPlanGroupId());
				designProduct.setProductGroupId(initProduct.getProductGroupId());
				designProduct.setGroupType(initProduct.getGroupType());
		}else{
			designProduct.setIsMainProduct(initProduct.getIsMainProduct());
			designProduct.setPlanGroupId(initProduct.getPlanGroupId());
			designProduct.setProductGroupId(initProduct.getProductGroupId());
			designProduct.setGroupType(initProduct.getGroupType());
		}
		setPlanProductInfo(designProduct,initProduct);
	}

	/**
	 * 创建设计方案产品列表
	 * @param designProductList
	 * @return
	* */
	public boolean saveByConfigFileData(DesignPlan designPlan,List<DesignPlanProduct> designProductList){
		try {
			// 配置文件产品列表
			for (DesignPlanProduct configData : designProductList) {
				String productCode = configData.getProductCode();
				if (StringUtils.isNotBlank(productCode)) {
					designPlanProductMapper.insertSelective(configData);
				}
			}
			// 识别默认结构(暂时定为序号为001)->start
//			autoUpdateStructureRelation(designTempletId);
			// 识别默认结构(暂时定为序号为001)->e
		}catch (Exception e){
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	public List<DesignPlanProductResult> getByPlanIdGroupMainProduct(DesignPlanProduct designPlanProduct){
		return designPlanProductMapper.byPlanIdGroupMainProduct(designPlanProduct);
	}

	@Override
	public ResponseEnvelope updateStructureBjWallProducts(String productJson,String planProductIds,String msgId,LoginUser loginUser){
		try {
			String[] array = planProductIds.split(",");
			for (String id : array) {
				DesignPlanProduct planProduct = this.get(Utils.getIntValue(id));
				if( planProduct == null ){
					return new ResponseEnvelope(false, "找不到方案产品ID："+id, msgId);
				}
				//将字符串转换成jsonObject对象 //需新增的产品信息
				JSONArray groupProductArray = JSONArray.fromObject(productJson);
				if( groupProductArray != null && groupProductArray.size() > 0 ) {
					for (int i = 0; i < groupProductArray.size(); i++) {
						JSONObject jsonObj = (JSONObject) groupProductArray.get(i);
						Integer productIndex = (Integer) jsonObj.get("productIndex");
						Integer productId = (Integer) jsonObj.get("productId");
						if (planProduct.getProductIndex().equals(productIndex)) {
							DesignPlanProduct newPlanProduct = new DesignPlanProduct();
							newPlanProduct.setId(planProduct.getId());
							BaseProduct baseProduct = baseProductService.get(productId);
							//更新多材质信息
							if( baseProduct != null && StringUtils.isNotBlank(baseProduct.getSplitTexturesInfo()) ){
								newPlanProduct.setSplitTexturesChooseInfo(baseProduct.getSplitTexturesInfo());
							}else{
								newPlanProduct.setSplitTexturesChooseInfo("");
							}
							newPlanProduct.setProductId(productId);
							newPlanProduct.setIsHide(0);
							this.update(newPlanProduct);
							if(Utils.enableRedisCache()){
								DesignPlanProductCacher.remove(Utils.getIntValue(id));
							}
							UsedProducts usedProducts = new UsedProducts();
							usedProducts.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS)+"_"+Utils.generateRandomDigitString(6));
							usedProducts.setCreator(loginUser.getLoginName());
							usedProducts.setGmtCreate(new Date());
							usedProducts.setGmtModified(new Date());
							usedProducts.setModifier(loginUser.getName());
							usedProducts.setIsDeleted(0);
							usedProducts.setUserId(loginUser.getId());
							usedProducts.setDesignId(planProduct.getPlanId());
							usedProducts.setProductId(productId);
							usedProducts.setPlanProductId(planProduct.getProductId());
							usedProducts.setPosIndexPath(planProduct.getPosIndexPath());
							int id_ = usedProductsService.add(usedProducts);
							if(Utils.enableRedisCache()){
								UsedProductsCacher.remove(id_);
							}
							/*更新产品使用次数表*/
							productUsageCountService.update(loginUser.getId(),productId,array.length);
						}else{
							continue;
						}
					}
				}else{
					return new ResponseEnvelope<>(false, "无背景墙结构组产品数据更新!", msgId);
				}
			}
		} catch (Exception e) {
			return new ResponseEnvelope<>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<>(true, "更新数据成功！", msgId);
	}
	
	/**
	 * 通过设计方案id 获取设计方案产品列表详情
	 * @param planId
	 * @return
	 */
	@Override
	public List<DesignPlanProduct> getBaseProductListByPlanId(Integer planId) {
		return designPlanProductMapper.getBaseProductListByPlanId(planId);
	}

    @Override
    public void batchDelTempDesignProduct(List<Integer> delProductList) {
		if(delProductList != null && delProductList.size() > 0){
			designPlanProductMapper.batchDelTempDesignProduct(delProductList);
		}
    }

    @Override
	public List<DesignPlanProduct> getListByPlanId(Integer planId) {
		/*DesignPlanProductSearch designPlanProductSearch = new DesignPlanProductSearch();
		designPlanProductSearch.setStart(-1);
		designPlanProductSearch.setLimit(-1);
		designPlanProductSearch.setIsDeleted(0);
		designPlanProductSearch.setPlanId(planId);
		return this.getPaginatedList(designPlanProductSearch);*/
		return designPlanProductMapper.getListByPlanIdAndIsDeleted(planId, null);
	}

	@Override
	public void saveDesignPlanProductList(List<DesignPlanProduct> designPlanProductList, long planId) {
		for(DesignPlanProduct designPlanProduct : designPlanProductList){
			designPlanProduct.setPlanId((int)planId);
		}
		this.save(designPlanProductList);
	}

	@Override
	public void save(List<DesignPlanProduct> designPlanProductList) {
		designPlanProductMapper.insertList(designPlanProductList);
	}

	@Override
	public List<DesignPlanProduct> getDesignPlanProductListByDesignPlanProductRenderSceneList(
			List<DesignPlanProductRenderScene> designPlanProductRenderSceneList) {
		List<DesignPlanProduct> designPlanProductList = new ArrayList<DesignPlanProduct>();
		if(designPlanProductRenderSceneList == null || designPlanProductRenderSceneList.size() == 0){
			return designPlanProductList;
		}
		
		for(DesignPlanProductRenderScene designPlanProductRenderScene : designPlanProductRenderSceneList){
			DesignPlanProduct designPlanProduct = new DesignPlanProduct();
			try {
				ClassReflectionUtils.reflectionAttr(designPlanProductRenderScene, designPlanProduct);
				designPlanProductList.add(designPlanProduct);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				return new ArrayList<DesignPlanProduct>();
			}
		}
		
		return designPlanProductList;
	}

	@Override
	public List<DesignPlanProduct> getGroupProductList(String planGroupId, Integer planId) {
		
		// 参数验证 ->start
		if(StringUtils.isEmpty(planGroupId)){
			logger.error("------function:getGroupProductList(String planGroupId, Integer planId)->StringUtils.isEmpty(planGroupId) = true");
			return null;
		}
		if(planId == null){
			logger.error("------function:getGroupProductList(String planGroupId, Integer planId)->planId = null");
			return null;
		}
		// 参数验证 ->end
		
		DesignPlanProductSearch designPlanProductSearch = new DesignPlanProductSearch();
		designPlanProductSearch.setStart(-1);
		designPlanProductSearch.setLimit(-1);
		designPlanProductSearch.setPlanId(planId);
		designPlanProductSearch.setPlanGroupId(planGroupId);
		return this.getPaginatedList(designPlanProductSearch);
	}

	@Override
	public boolean updateByConfig(String configEncrypt, List<PosNameInfo> posNameInfoList, Integer opType) {
		
		// *参数验证/参数处理 ->start
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// *参数验证/参数处理 ->end
		
		// *处理posNameInfoList ->start
		Map<String, Integer> posNameInfoMap = new HashMap<String, Integer>();
		for(PosNameInfo posNameInfo : posNameInfoList) {
			posNameInfoMap.put(posNameInfo.getPosName(), posNameInfo.getDeignPlanProductId());
		}
		// *处理posNameInfoList ->end
		
		// *configEncrypt解密 ->start
		if(PASSWORD_CRYPT_KEY.length() < 8){
			PASSWORD_CRYPT_KEY = String.format("%1$0"+(8-PASSWORD_CRYPT_KEY.length())+"d",0);
		}else{
			PASSWORD_CRYPT_KEY = PASSWORD_CRYPT_KEY.substring(0,8);
		}
		
		String config = null;
		try {
			config = AESUtil2.decrypt(configEncrypt, PASSWORD_CRYPT_KEY);
		} catch (Exception e) {
			logger.error(e);
			logger.error("function:DesignPlanProductServiceImpl.createByConfig(....)->\n解密配置文件失败");
			return false;
		}
		// *configEncrypt解密 ->end
		
		// *解析json,更新DesignPlanProduct ->start
		try {
			JSONObject jsonObject = JSONObject.fromObject(config);
			JSONArray jsonArray = (JSONArray) jsonObject.get("RoomConfig");
			for (int index = 0; index < jsonArray.size(); index++) {
				JSONObject productJson = jsonArray.getJSONObject(index);
				// *找到对应DesignPlanProduct ->start
				String posName = productJson.getString("PosName");
				if(StringUtils.isEmpty(posName)) {
					logger.error("function:DesignPlanProductServiceImpl.updateByConfig(....)->\n某个posName没有从配置文件中取到");
					return false;
				}
				Integer designPlanProductId = posNameInfoMap.get(posName);
				if(designPlanProductId == null) {
					logger.error("function:DesignPlanProductServiceImpl.updateByConfig(....)->\n在posNameInfoList没有找到posName为:" + posName + "的设计方案产品");
					return false;
				}
				/*DesignPlanProduct designPlanProduct = this.get(designPlanProductId);
				if(designPlanProduct == null) {
					logger.error("function:DesignPlanProductServiceImpl.updateByConfig(....)->\nDesignPlanProduct not found:designPlanProductId:" + designPlanProductId + "");
					return false;
				}*/
				// *找到对应DesignPlanProduct ->end
				
				DesignPlanProduct designPlanProduct = new DesignPlanProduct();
				designPlanProduct.setId(designPlanProductId);
				this.updateDesignPlanProductByJson(designPlanProduct, productJson);
				if(DesignPlanConstants.AUTO_RENDER != opType) {
					this.update(designPlanProduct);
				}else {
					// 自动渲染
					optimizePlanService.updatePlanProduct(designPlanProduct);
				}
			}
		}catch (Exception e) {
			logger.error("------解析配置文件失败");
			e.printStackTrace();
			return false;
		}
		// *解析json,更新DesignPlanProduct ->start
		
		return true;
	}

	/**
	 * 根据json信息生成DesignPlanProduct
	 * 
	 * @author huangsongbo
	 * @param designPlanProduct
	 * @param productJson
	 * @return
	 */
	private DesignPlanProduct updateDesignPlanProductByJson(DesignPlanProduct designPlanProduct, JSONObject productJson) {
		
		// *参数验证 ->start
		if(productJson == null) {
			logger.error("function:DesignPlanProductServiceImpl.getDesignPlanProductByJson(....)->\n参数productJson = null");
			return null;
		}
		if(designPlanProduct == null) {
			logger.error("function:DesignPlanProductServiceImpl.getDesignPlanProductByJson(....)->\n参数designPlanProduct = null");
			return null;
		}
		// *参数验证 ->end
		
		// *补充信息 ->start
		designPlanProduct.setPosIndexPath(productJson.getString("PosIndexPath"));
		designPlanProduct.setProductSequence(com.nork.common.util.StringUtils.replaceString(designPlanProduct.getPosIndexPath(), "/"));
		designPlanProduct.setPosName(productJson.getString("PosName"));
		// *补充信息 ->end
		
		return designPlanProduct;
	}

	@Override
	public Integer createByPlanProductInfo(PlanProductInfo planProductInfo, Integer planId, String username, Integer opType) {
		
		// *参数验证/参数处理 ->start
		if(planProductInfo == null) {
			return null;
		}
		if(planId == null || planId < 0) {
			logger.error("function:DesignPlanProductServiceImpl.createByPlanProductInfo(....)->\n参数planId:(planId == null || planId < 0) = true;planId:" + planId);
			return null;
		}
		if(StringUtils.isEmpty(username)) {
			logger.error("function:DesignPlanProductServiceImpl.createByPlanProductInfo(....)->\n参数username不能为空:(StringUtils.isEmpty(username)) = true;username:" + username);
			return null;
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// *参数验证/参数处理 ->start
		
		// *设置designPlanProduct属性 ->start
		DesignPlanProduct  designPlanProduct = new DesignPlanProduct();
		LoginUser loginUser = new LoginUser();
		loginUser.setLoginName(username);
		this.sysSave(designPlanProduct, loginUser);
		designPlanProduct.setPlanId(planId);
		designPlanProduct.setProductId(planProductInfo.getProductId());
		designPlanProduct.setPlanProductId(planProductInfo.getPlanProductId());
		designPlanProduct.setIsHide(0);
		designPlanProduct.setInitProductId(planProductInfo.getInitProductId());
		designPlanProduct.setIsDirty(0);
		designPlanProduct.setProductGroupId(planProductInfo.getGroupOrStructureId());
		designPlanProduct.setIsMainProduct(planProductInfo.getIsMainProduct());
		designPlanProduct.setModelProductId(0);
		designPlanProduct.setBindParentProductId(planProductInfo.getBindParentProductId());
		designPlanProduct.setSplitTexturesChooseInfo(planProductInfo.getSplitTexturesChooseInfo());
		designPlanProduct.setSameProductTypeIndex(0);
		designPlanProduct.setIsStandard(planProductInfo.getIsStandard());
		designPlanProduct.setCenter(planProductInfo.getCenter());
		designPlanProduct.setRegionMark(planProductInfo.getRegionMark());
		designPlanProduct.setMeasureCode(planProductInfo.getMeasureCode());
		designPlanProduct.setWallOrientation(planProductInfo.getWallOrientation());
		designPlanProduct.setWallType(planProductInfo.getWallType());
		designPlanProduct.setStyleId(planProductInfo.getStyleId());
		designPlanProduct.setProductIndex(planProductInfo.getProductIndex());
		designPlanProduct.setIsMainStructureProduct(planProductInfo.getIsMainStructureProduct());
		designPlanProduct.setIsGroupReplaceWay(planProductInfo.getIsGroupReplaceWay());
		designPlanProduct.setPlanGroupId(planProductInfo.getPlanGroupId());
		designPlanProduct.setGroupType(planProductInfo.getGroupType());
		designPlanProduct.setIsGroupReplaceWay(planProductInfo.getIsGroupReplaceWay());
		designPlanProduct.setProductIndex(planProductInfo.getProductIndex());
		// *设置designPlanProduct属性 ->end
		
		if(DesignPlanConstants.AUTO_RENDER != opType) {
			this.add(designPlanProduct);
		}else {
			// 自动渲染,则把设计方案产品数据存在自动渲染产品表里
			optimizePlanService.addPlanProduct(designPlanProduct);
		}
		
		return designPlanProduct.getId();
	}
	
	/**
	 * 还原已删除的门框、背景墙、窗帘
	 */
	@SuppressWarnings({ "null", "unchecked", "rawtypes" })
	@Override
	public Object reductionProduct(String msgId,Integer designPlanId,String mediaType,LoginUser loginUser) {
		/*String cacheEnable=Utils.getValue("redisCacheEnable", "0");*/
		String msg = "";
		DesignPlanModel designPlan = designPlanService.selectDesignPlanInfo(designPlanId);
		Integer spaceId = designPlan.getSpaceCommonId();
		SysDictionary sysDictionary = new SysDictionary();
		//获取该设计方案中已删除的产品 
		
		// update by huangsongbo 2018.6.12 加入oldPosName ->start
		/*List<DesignPlanProduct> list = designPlanProductMapper.getListByPlanIdAndIsDeleted(designPlanId, 1);*/
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setIsDeleted(1);
		designPlanProduct.setPlanId(designPlanId);
		List<DesignPlanProduct> list = designPlanProductService.getListMoreInfo(designPlanProduct);
		// update by huangsongbo 2018.6.12 加入oldPosName ->end
		
		List<Integer> ids = new ArrayList<>();
		if(Lists.isNotEmpty(list)) {
			for(DesignPlanProduct planProduct : list) {
				ids.add(planProduct.getId());
			}
			if(Lists.isNotEmpty(ids)) {
				designPlanProductMapper.updateIsDeleted(ids,0);
			}
		}
		List<UnityPlanProduct> reList = new ArrayList<>(); 
		if(Lists.isNotEmpty(list)) {
			for(DesignPlanProduct planProduct : list) {
//				List<BaseProduct> baseProduct = baseProductMapper.getByDesignPlanId(designPlanId);
				BaseProduct baseProduct = baseProductService.get(planProduct.getInitProductId());
				
				if(baseProduct != null) {
					//通过大小类获取小类信息
					Map<String, Object> map2 = new HashMap<>();
					map2.put("smallTypeValue", baseProduct.getProductSmallTypeValue());
					map2.put("typeValue", baseProduct.getProductTypeValue());
					sysDictionary = sysDictionaryService.selectSmallTypeObj(map2);
					//判断该产品是否是属于可还原的
					String productType = Utils.getValue("app.reducible.product.valueKey", "");
					if(productType.indexOf(sysDictionary.getValuekey()) != -1) {

						UnityPlanProduct unityPlanProduct = new UnityPlanProduct();
						unityPlanProduct.setIsDirty(planProduct.getIsDirty());
						unityPlanProduct.setPlanProductId(planProduct.getId());
						unityPlanProduct.setProductSequence(planProduct.getProductSequence());
						unityPlanProduct.setMaterialPicPaths(new String[]{});
						unityPlanProduct.setDecorationModelPath(new String[]{});
						unityPlanProduct.setPosIndexPath(planProduct.getPosIndexPath());
						unityPlanProduct.setPosName(planProduct.getPosName());
						unityPlanProduct.setBindProductId(planProduct.getBindParentProductId());
						
						/*天花、地面、地面结构拆分*/
						unityPlanProduct.setIsStandard(planProduct.getIsStandard());
						unityPlanProduct.setCenter(planProduct.getCenter());
						unityPlanProduct.setRegionMark(planProduct.getRegionMark());
						unityPlanProduct.setStyleId(planProduct.getStyleId());
						unityPlanProduct.setMeasureCode(planProduct.getMeasureCode());
						unityPlanProduct.setDescribeInfo(planProduct.getDescribeInfo());
						unityPlanProduct.setProductIndex(planProduct.getProductIndex());
						
						unityPlanProduct.setIsGroupReplaceWay(planProduct.getIsGroupReplaceWay());
						unityPlanProduct.setIsMainStructureProduct(planProduct.getIsMainStructureProduct());

						unityPlanProduct.setOldPosName(planProduct.getOldPosName());
						
						/*处理结构返回格式*/
						unityPlanProduct = designPlanService.getPlanProductStructureJson(unityPlanProduct, planProduct, designPlan, loginUser);
						// 产品的基本信息
//						BaseProduct baseProduct = null;
						if (planProduct.getProductId() != null && planProduct.getProductId() > 0) {
							if (Utils.enableRedisCache()) {
								baseProduct = BaseProductCacher.get(planProduct.getInitProductId());
							} else {
								baseProduct = baseProductService.get(planProduct.getInitProductId());//产品公用基本信息   '产品品牌',  '产品风格', '产品规格',  '产品颜色',  '产品长度', '产品宽度', '产品高度', '销售价格',等等
							}
						}
						if (null == baseProduct) {
							msg = "planProduct.getProductId():" + planProduct.getProductId();
							return new ResponseEnvelope<UnityDesignPlan>(false, msg);
						}

						String productTypeValue = baseProduct.getProductTypeValue();
						Integer productSmallTypeValue = baseProduct.getProductSmallTypeValue();
//						SysDictionary sysDictionary = new SysDictionary();
						if (baseProduct != null && productSmallTypeValue != null
								&& StringUtils.isNotBlank(productTypeValue)) {
							//通过大小类获取小类信息
							Map<String, Object> map = new HashMap<>();
							map.put("smallTypeValue", productSmallTypeValue);
							map.put("typeValue", productTypeValue);
							sysDictionary = sysDictionaryService.selectSmallTypeObj(map);

							if (sysDictionary != null) {
								unityPlanProduct.setMoveWay(sysDictionary.getAtt5());
							} else {
								logger.debug("sDictionary is null, sd.getValuekey()=" + sysDictionary.getValuekey() + ";baseProduct.getProductSmallTypeValue()=" + baseProduct.getProductSmallTypeValue() + ";productid=" + baseProduct.getProductId() + ";baseProduct.getProductTypeValue()=" + baseProduct.getProductTypeValue());
							}
							if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(sysDictionary.getType())) {//墙面
								unityPlanProduct.setBgWall(Utils.getIsBgWall(sysDictionary == null ? "" : sysDictionary.getValuekey()));
							} else {
								unityPlanProduct.setBgWall(0);
							}
							if (baseProduct.getBmIds() != null) {
								unityPlanProduct.setIsCustomized(1);
							}
						}
						if (null != baseProduct) {
							unityPlanProduct.setProductId(baseProduct.getId());
							unityPlanProduct.setProductCode(baseProduct.getProductCode());
							unityPlanProduct.setParentProductId(baseProduct.getParentId());
							unityPlanProduct.setProductLength(baseProduct.getProductLength());
							unityPlanProduct.setProductWidth(baseProduct.getProductWidth());
							unityPlanProduct.setProductHeight(baseProduct.getProductHeight());
							unityPlanProduct.setMinHeight(baseProduct.getMinHeight());
							//如果该墙面有绑定关系，则取绑定产品白模长宽高
							String bindProductid = planProduct.getBindParentProductId();
							if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(sysDictionary.getType()) && StringUtils.isNotBlank(bindProductid)) {
								String array[] = bindProductid.split(",");
								BaseProduct baiMoProduct = null;
								StringBuffer fullPaveLength = new StringBuffer();

								for (String bindId : array) {
									if (Utils.enableRedisCache()) {
										baiMoProduct = BaseProductCacher.get(Utils.getIntValue(bindId));
									} else {
										baiMoProduct = baseProductService.get(Utils.getIntValue(bindId));
									}
									if (baiMoProduct != null && baiMoProduct.getFullPaveLength() != null) {
										fullPaveLength.append(baiMoProduct.getFullPaveLength() + ",");
									}
								}
								if (fullPaveLength.toString().length() > 0) {
									String fullPave = fullPaveLength.toString();
									unityPlanProduct.setFullPaveLength(fullPave != null ? fullPave.substring(0, fullPave.length() - 1) : fullPave);
								}
							}
							//如果是背景墙、窗帘、淋浴屏则取白模产品的长宽高
							Integer baiMoId = planProduct.getInitProductId();
							Map<String,String > stretchZoomMap = baseProductService.getStretchZoomLength(sysDictionary == null ? "" : sysDictionary.getValuekey());
							if (stretchZoomMap != null && stretchZoomMap.size() > 0 && baiMoId != null && baiMoId.intValue() > 0) {
								BaseProduct baiMoProduct = null;
								if (Utils.enableRedisCache()) {
									baiMoProduct = BaseProductCacher.get(baiMoId);
								} else {
									baiMoProduct = baseProductService.get(baiMoId);
								}
								if (baiMoProduct != null) {
									if (StringUtils.isNotEmpty(baiMoProduct.getProductLength())) {
										unityPlanProduct.setInitModelLength(Integer.parseInt(baiMoProduct.getProductLength()));
									}
									if (StringUtils.isNotEmpty(baiMoProduct.getProductWidth())) {
										unityPlanProduct.setInitModelWidth(Integer.parseInt(baiMoProduct.getProductWidth()));
									}
									if (StringUtils.isNotEmpty(baiMoProduct.getProductHeight())) {
										unityPlanProduct.setInitModelHeight(Integer.parseInt(baiMoProduct.getProductHeight()));
									}
									if (baiMoProduct.getFullPaveLength() != null) {
										unityPlanProduct.setFullPaveLength(baiMoProduct.getFullPaveLength());
									}
								}
							}
						}
						//系列标志
						unityPlanProduct.setSeriesSign(baseProductService.getSeriesSign(sysDictionary==null?null:sysDictionary.getValuekey()));
						String u3dModelId = baseProductService.getU3dModelId(mediaType == null ? "2" : mediaType.toString(), baseProduct);
						ResModel resModel = resModelService.get(StringUtils.isEmpty(u3dModelId) ? 0 : new Integer(u3dModelId));
						if (resModel != null) {
							unityPlanProduct.setProductModelPath(resModel.getModelPath());
							unityPlanProduct.setModelLength(resModel.getLength());
							unityPlanProduct.setModelWidth(resModel.getWidth());
							unityPlanProduct.setModelHeight(resModel.getHeight());
							unityPlanProduct.setModelMinHeight(resModel.getMinHeight());
						} else {
							//unityPlanProduct.setProductModelPath("");
							/*应对只有材质的硬装产品无模型的情况*/
							boolean isHard = false;
							if (baseProduct != null) {
								isHard = baseProductService.isHard(baseProduct);
							}
							if (isHard) {
								BaseProduct baimoProduct = null;
								Integer currentProductId = null;
								//换贴图应找当前产品模型
								if (planProduct.getModelProductId() != null && planProduct.getModelProductId() != 0) {
									currentProductId = planProduct.getModelProductId();
									unityPlanProduct.setModelProductId(planProduct.getModelProductId());
								} else {
									currentProductId = planProduct.getInitProductId();
								}

//								BaseProduct baseProduct_ = new BaseProduct();
//								baseProduct_.setId(currentProductId);
//								baseProduct_.setMediaType(mediaType);
								if (Utils.enableRedisCache()) {
									baimoProduct = BaseProductCacher.get(currentProductId);
//									baimoProduct = BaseProductCacher.getDataAndModel(baseProduct_);
								} else {
		 							baimoProduct = baseProductService.get(currentProductId);
//									baimoProduct = baseProductService.getDataAndModel(baseProduct_);
								}
								/*获取不同媒介u3d模型*/
								String modelId = baseProductService.getU3dModelId(mediaType,baimoProduct);
								if( StringUtils.isNotBlank(modelId) ){
									ResModel resModel1 = null ;
									if(Utils.enableRedisCache()){
										resModel1 = ResourceCacher.getModel(Integer.valueOf(modelId));
									}else{
										resModel1 = resModelService.get(Integer.valueOf(modelId));
									}
									if( resModel1 != null ){
										unityPlanProduct.setProductModelPath(resModel1.getModelPath());
									}
								}
							}
						}

						if (baseProduct != null && StringUtils.isNotBlank(baseProduct.getMaterialPicIds())) {
							String materialIds = baseProduct.getMaterialPicIds();
							List<String> idsInfo = Utils.getListFromStr(materialIds);
							List<String> materialPicList = new ArrayList<String>();
							ResTexture resTextureTemp = null;
							for (String idStr : idsInfo) {
								ResTexture resTexture = resTextureService.get(Integer.valueOf(idStr));//材质库
								if (resTexture == null)
									continue;
								if (resTextureTemp == null) {
									resTextureTemp = resTexture;
									unityPlanProduct.setTextureAttrValue(resTextureTemp.getTextureAttrValue());
									unityPlanProduct.setLaymodes(resTextureTemp.getLaymodes());
								}
								if (resTexture != null && resTexture.getId() != null) {
									materialPicList.add(resTexture.getFilePath());
								}
							}
							unityPlanProduct.setMaterialPicPaths((String[]) materialPicList.toArray(new String[materialPicList.size()]));
						}

						/*---------------------xiaoxc  end-----*/
						/* 产品子集数量*/
						unityPlanProduct.setLeafNum(0);
						/* 标示产品在界面中的展示类型*/
						unityPlanProduct.setIsLeaf(new Integer(1));
						/* 产品是否隐藏*/
						unityPlanProduct.setIsHide(planProduct.getIsHide());

						String splitTexturesInfo = baseProduct.getSplitTexturesInfo();
						if (StringUtils.isNotBlank(splitTexturesInfo)) {
							if (StringUtils.isNotBlank(planProduct.getSplitTexturesChooseInfo())) {
								splitTexturesInfo = planProduct.getSplitTexturesChooseInfo();
							}
							Map<String, Object> map = baseProductService.dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo, "choose");
							unityPlanProduct.setIsSplit((Integer) map.get("isSplit"));
							unityPlanProduct.setSplitTexturesChoose((List<SplitTextureDTO>) map.get("splitTexturesChoose"));
						} else {
							List<SplitTextureDTO> splitTextureDTOList = new ArrayList<SplitTextureDTO>();
							String materialIds = baseProduct.getMaterialPicIds();
							Integer materialId = 0;
							if (StringUtils.isNotBlank(materialIds)) {
								List<String> materialIdStrList = Utils.getListFromStr(materialIds);
								if (materialIdStrList != null && materialIdStrList.size() > 0) {
									materialId = Integer.valueOf(materialIdStrList.get(0));
								}
							}
							if (materialId != null && materialId > 0) {
								ResTexture resTexture = resTextureService.get(materialId);
								if (resTexture != null) {
									splitTextureDTOList = new ArrayList<SplitTextureDTO>();
									List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<SplitTextureDTO.ResTextureDTO>();
									SplitTextureDTO splitTextureDTO = new SplitTextureDTO("1", "", null);
									SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureService.fromResTexture(resTexture);
									resTextureDTO.setKey(splitTextureDTO.getKey());
									resTextureDTO.setProductId(baseProduct.getId());
									resTextureDTOList.add(resTextureDTO);
									splitTextureDTO.setList(resTextureDTOList);
									splitTextureDTOList.add(splitTextureDTO);
									unityPlanProduct.setSplitTexturesChoose(splitTextureDTOList);
								}
							}
						}

						/*处理拆分材质产品的默认材质信息->end*/
						UnityPlanProduct unityPlanProduct_p = unityPlanProduct.copy();
						// 产品类别信息
						if (!StringUtils.isEmpty(productTypeValue)) {
							SysDictionary sd = sysDictionaryService.getSysDictionary("productType", new Integer(productTypeValue));
							if (sd != null) {
								/* 为保证父节点与子节点的productTypeCode相同，指定如下规则：
								子节点时，parentTypeCode和smallTyeCode，productTypeCode三者都存在值，smallTyeCode为本身的节点的信息值parentTypeCode与productTypeCode相等
								父节点时，parentTypeCode存在值(暂时不取)，productTypeCode为节点本身信息值，因为子节点太多，故子节点smallTyeCode为空值*/
								unityPlanProduct.setProductTypeValue(sd.getValue());
								unityPlanProduct.setProductTypeCode(sd.getValuekey());
								unityPlanProduct.setProductTypeName(sd.getName());

								unityPlanProduct_p.setProductTypeValue(sd.getValue());
								unityPlanProduct_p.setProductTypeCode(sd.getValuekey());
								unityPlanProduct_p.setProductTypeName(sd.getName());
								/* 获取子节点的父节点信息*/
								unityPlanProduct.setParentTypeCode(sd.getValuekey());
								unityPlanProduct.setParentTypeName(sd.getName());
								unityPlanProduct.setParentTypeValue(sd.getValue());

								unityPlanProduct_p.setParentTypeValue(-1);
								unityPlanProduct_p.setParentTypeCode("");
								unityPlanProduct_p.setParentTypeName("");

								/* 获取子节点的节点信息*/
								if (productSmallTypeValue != null && new Integer(productSmallTypeValue).intValue() > 0) {
									if (sysDictionary != null) {
										unityPlanProduct.setSmallTypeValue(sysDictionary.getValue());
										unityPlanProduct.setSmallTypeCode(sysDictionary.getValuekey());
										unityPlanProduct.setSmallTypeName(sysDictionary.getName());
										/* 是否是白模*/
										Integer isBm = 0;
										if ("baimo".equals(sysDictionary.getAtt3())) {
											isBm = 1;
											String bjType = Utils.getValue("app.smallProductType.beiJingWall", "");
											if( bjType.indexOf(sysDictionary.getValuekey()) != -1 ){
												unityPlanProduct.setIsHide(1);
											}
										}
										unityPlanProduct.setIsBaimo(isBm);
										unityPlanProduct_p.setIsBaimo(isBm);
										/* 软装硬装以下规则处理，同时按最小基本的数据定义-按1硬装2软装,默认软装*/
										String rootType = StringUtils.isEmpty(sysDictionary.getAtt1()) ? "2" : sysDictionary.getAtt1().trim();
										unityPlanProduct.setRootType(rootType);
										unityPlanProduct_p.setRootType("");
										unityPlanProduct_p.setIsBaimo(isBm);
										unityPlanProduct_p.setSmallTypeValue(-1);
										unityPlanProduct_p.setSmallTypeCode("");
										unityPlanProduct_p.setSmallTypeName("");
									}
								}
							}
						}

						Map<String, String> map = new HashMap<String, String>();
						/*将材质的长宽也给  塞到这个list 中取 */
						String aterialPicIds = baseProduct.getMaterialPicIds();/**材质id**/
						if (aterialPicIds != null && !"".equals(aterialPicIds)) {
							if (StringUtils.isNumeric(aterialPicIds)) {
								ResTexture resTexture = resTextureService.get(Integer.parseInt(aterialPicIds));
								if (resTexture != null) {
									unityPlanProduct.setTextureWidth(resTexture.getFileWidth() + "");
									unityPlanProduct.setTextureHeight(resTexture.getFileHeight() + "");
								}
							}
						}
						/*在组合产品查询列表 中 增加产品属性*/
						map = productAttributeService.getPropertyMap(baseProduct.getId());//产品属性
						unityPlanProduct.setPropertyMap(map);

						// 关联白模产品的属性
						Map<String,String> basicPropertyMap = new HashMap<>();
						basicPropertyMap = productAttributeService.getPropertyMap(planProduct.getInitProductId());
						unityPlanProduct.setBasicPropertyMap(basicPropertyMap);

						/* 样板房产品ID*/
						unityPlanProduct.setTemplateProductId(
								planProduct.getInitProductId() == null ? "" : planProduct.getInitProductId().toString());

						/* 组装产品的规则*/
						String productTypeCode = unityPlanProduct.getProductTypeCode();/* 产品大类*/
						String productSmallTypeCode = unityPlanProduct.getSmallTypeCode();/* 产品小类*/
						String productId = null;// 产品ID
						if (unityPlanProduct.getProductId() != null) {
							productId = unityPlanProduct.getProductId().toString();/*产品ID*/
						}

						/*获取规则*/
						Map<String, String> rulesMap = new HashMap<>();
						Map<Object, Object> rulesParamsMap = new HashMap<>();
						rulesParamsMap.put("rulesProductId", productId);
						ResponseEnvelope rulesResponseMapResult = null;
						if(Utils.enableRedisCache()){
							rulesResponseMapResult = CommonCacher.getAll(ModuleType.DesignPlan, "getRulesSecondaryList", rulesParamsMap);
						}
						
						if (rulesResponseMapResult != null) {
							rulesMap = (Map<String, String>) rulesResponseMapResult.getObj();
						} else {
							rulesMap = designRulesService.getRulesSecondaryList(productId, productTypeCode,
									productSmallTypeCode, spaceId, designPlan.getDesignTemplateId(), new DesignRules(), map);

						}
						unityPlanProduct.setRulesMap(rulesMap);
						logger.debug("产品Id=" + productId + "spaceId=" + spaceId + ":" + rulesMap);
						reList.add(unityPlanProduct);
					
						//将产品的初始白模产品id 赋值给  产品id
//						designPlanProduct.setProductId(designPlanProduct.getInitProductId());
//						reList.add(baseProduct);
						continue;
					}
				}
			}
		}
		if(Lists.isNotEmpty(ids)) {
			designPlanProductMapper.updateIsDeleted(ids,1);
		}
		ResponseEnvelope<UnityPlanProduct> envelope = new ResponseEnvelope<>();
		//根据设计方案id查询该方案中可还原的白模产品
//		List<BaseProduct> baseProductList = baseProductMapper.getByDesignPlanId(designPlanId);
		envelope.setDatalist(reList);
		//查询改样板房的配置文件保存路径
		DesignPlan designPlan2 = designPlanService.get(designPlanId);
		DesignTemplet designTemplet = designTempletMapper.selectByPrimaryKey(designPlan2.getDesignTemplateId());
		if(designTemplet != null) {
			ResFile resFile = resFileService.get(designTemplet.getConfigFileId());
			envelope.setObj(resFile.getFilePath());
		}

		envelope.setMsgId(msgId);
		return envelope;
	}

	@Override
	public ResponseEnvelope<DesignPlanProduct> delProductAndSynConfigFile(ProductSaveParameter productParam,LoginUser loginUser) {
		DesignPlanProduct designPlanProduct = productParam.getDesignPlanProduct();
		String ids = designPlanProduct.getIds();
		Integer isDelete = designPlanProduct.getIsDeleted();
		Integer planId = designPlanProduct.getPlanId();
		Integer reUploadConfig = productParam.getReUploadConfig();
		String msgId = designPlanProduct.getMsgId();
		String context = productParam.getContext();
		if (StringUtils.isNotBlank(ids) && isDelete != null && planId != null) {
			List<Integer> ints = new ArrayList<>();
			if (StringUtils.isNotBlank(ids)) {
				if (ids.indexOf(",") != -1) {
					String[] strs = ids.split(",");
					for (String str : strs) {
						if (StringUtils.isNotBlank(str))
							ints.add(Integer.parseInt(str));
					}
				} else {
					if (StringUtils.isNotBlank(ids))
						ints.add(Integer.parseInt(ids));
				}
			}
			/* 循环修改isDelete */
			if (isDelete != null) {
				for (Integer id : ints) {
					DesignPlanProduct plan = get(id);
					if (plan != null) {
						/* 删除的产品为主产品时->解组 */
						if (new Integer(1).equals(plan.getIsMainProduct())
								&& StringUtils.isNotBlank(plan.getPlanGroupId())
								&& !StringUtils.equals("0", plan.getPlanGroupId())) {
							/* 是主产品->解组 */
							relieveGroupByPlanIdAndplanGroupId(plan.getPlanId(), plan.getPlanGroupId());
							logger.warn("删除主产品操作导致解组:时间:" + Utils.getTimeStr() + ";设计方案id:" + plan.getDesignPlanId()
									+ ";planProductId:" + plan.getId());
						}
						/* 删除的产品为主产品时->解组->end */
						DesignPlanProduct newPlanProduct = new DesignPlanProduct();
						newPlanProduct.setId(plan.getId());
						newPlanProduct.setIsDeleted(designPlanProduct.getIsDeleted());
						newPlanProduct.setGmtModified(new Date());
						update(newPlanProduct);

					}
				}
			}
			/** 更新设计方案最后修改时间 **/
			DesignPlan designPlan = designPlanService.get(planId);
			if (designPlan != null) {
				DesignPlan newDesignPlan = new DesignPlan();
				newDesignPlan.setId(designPlan.getId());
				newDesignPlan.setIsChange(1);
				newDesignPlan.setGmtModified(new Date());
				designPlanService.update(newDesignPlan);
			}
		}
		if (isDelete == null) {
			sysSave(designPlanProduct, loginUser);
			if (designPlanProduct.getId() == null) {
				int id = add(designPlanProduct);
				designPlanProduct.setId(id);
			} else {
			   update(designPlanProduct);
			}
		}
			
		if (reUploadConfig != null && reUploadConfig.intValue() == 1) {
			designPlanService.updatePlanConfig(loginUser, planId, DesignPlanConstants.USER_RENDER, context, "true",msgId);
		}
		if (Utils.enableRedisCache()) {
			DesignPlanProductCacher.remove(1);
			UsedProductsCacher.remove(1);
		}
		return new ResponseEnvelope<DesignPlanProduct>(designPlanProduct, designPlanProduct.getMsgId(), true);
	}

    /* (non-Javadoc)    
     * @see com.nork.design.service.DesignPlanProductService#getDesignPlanProductList(com.nork.design.model.DesignPlanProduct)    
     */
    @Override
    public List<DesignPlanProductResult> getDesignPlanProductList(DesignPlanProduct designPlanProduct) {
        return  designPlanProductMapper.getDesignPlanProductList(designPlanProduct);
    }

    //对比产品多维和方案产品多维材质
    public String getSplitTexturesInfo(BaseProduct baseProduct ,DesignProductResult designProductResult){
    	String productTexturesInfo = baseProduct.getSplitTexturesInfo();
    	String designTexturesInfo = designProductResult.getSplitTexturesChooseInfo();
    	if (StringUtils.isEmpty(productTexturesInfo)) {
			return productTexturesInfo;
		}
		if (StringUtils.isEmpty(designTexturesInfo)) {
			return productTexturesInfo;
		}
		if (productTexturesInfo.equals(designTexturesInfo)) {
    		return designTexturesInfo;
		}
		//解析json [{'name':'wu_chuangt_0003_01','key':'1','textureIds':'21987,21986,21985,21983,21982','defaultId':'21982'}]
		JSONArray productJsonArray = JSONArray.fromObject(productTexturesInfo);
		List<SplitTextureInfoDTO> productSplitTextureList = JSONArray.toList(productJsonArray, SplitTextureInfoDTO.class);
		JSONArray designProductJsonArray = JSONArray.fromObject(designTexturesInfo);
		List<SplitTextureInfoDTO> designProductSplitTextureList = JSONArray.toList(designProductJsonArray, SplitTextureInfoDTO.class);

		// 首先保证两个产品的拆分数相同
		if(productSplitTextureList != null && designProductSplitTextureList != null && productSplitTextureList.size() == designProductSplitTextureList.size()) {
			for (SplitTextureInfoDTO productTexture : productSplitTextureList) {
				for (SplitTextureInfoDTO designgProductTexture : designProductSplitTextureList) {
					//如果多维材质名称相同直接返回方案多维材质
					if (productTexture.getName().equals(designgProductTexture.getName())) {
						return designTexturesInfo;
					}
					//如果key相同，则判断方案产品材质否存在产品材质ID里，有直接返回方案多维材质
					if (productTexture.getKey().equals(designgProductTexture.getKey())) {
						if(productTexture.getTextureIds().indexOf(designgProductTexture.getDefaultId()) != 0){
							return designTexturesInfo;
						}
					}
				}
			}
		}
		return productTexturesInfo;
    }

	@Override
	public String matchSplitTexturesInfo(String splitTexturesChooseInfo, Integer productId,
			String splitTexturesInfoRecommended) {
		// 现在有情况,就是设计方案产品表中的splitTexture字段,和base_product表中的splitTexture字段不匹配,所以不能直接用样板房产品表中的splitTexture来匹配材质,(出现原因可能是产品中途被改过多材质)
		// 参数验证/参数处理 ->start
		if(StringUtils.isEmpty(splitTexturesChooseInfo)) {
			return null;
		}
		if(productId == null) {
			return splitTexturesChooseInfo;
		}
		if(StringUtils.isEmpty(splitTexturesInfoRecommended)) {
			return splitTexturesChooseInfo;
		}
		// 参数验证/参数处理 ->end
		
		List<SplitTextureInfoDTO> splitTextureListSelected = Utils.getSplitTextureInfoDTOList(splitTexturesChooseInfo);
		if(Lists.isEmpty(splitTextureListSelected)) {
			return null;
		}
		BaseProduct baseProduct = baseProductService.get(productId);
		if(baseProduct == null) {
			return splitTexturesChooseInfo;
		}
		if(StringUtils.isEmpty(baseProduct.getSplitTexturesInfo())) {
			return splitTexturesChooseInfo;
		}
		/*List<SplitTextureInfoDTO> splitTextureListBaseProduct = Utils.getSplitTextureInfoDTOList(baseProduct.getSplitTexturesInfo());
		if(Lists.isEmpty(splitTextureListBaseProduct)) {
			return splitTexturesChooseInfo;
		}*/
			
		/*if(splitTextureListSelected.size() == splitTextureListBaseProduct.size()) {*/
		List<SplitTextureInfoDTO> splitTextureListRecommended = Utils.getSplitTextureInfoDTOList(splitTexturesInfoRecommended);
		if(Lists.isEmpty(splitTextureListRecommended)) {
			return splitTexturesChooseInfo;
		}
		for (int index = 0; index < splitTextureListSelected.size(); index++) {
			SplitTextureInfoDTO splitTextureInfoDTO = splitTextureListSelected.get(index);
			if(splitTextureListRecommended.size() >= (index + 1)) {
				Integer restextureId = splitTextureListRecommended.get(index).getDefaultId();
				List<Integer> restextureIdList = Utils.getIntegerListFromStringList(splitTextureInfoDTO.getTextureIds());
				if(restextureIdList.indexOf(restextureId) != -1) {
					splitTextureInfoDTO.setDefaultId(restextureId);
				}
			}
		}
		/*}*/
		logger.debug(JSONArray.fromObject(splitTextureListSelected).toString());
		return JSONArray.fromObject(splitTextureListSelected).toString();
	}

	/**
	 * 根据系列方案产品ID查询方案产品列表
	 * @param planProductIdList (系列方案产品ID)
	 * @author xiaoxc
	 * @return List<DesignProductResult>
	 */
	@Override
	public List<DesignProductResult> getPlanSeriesProductByPlanProuctIds(List<String> planProductIdList){
		return designPlanProductDao.findPlanSeriesProductByPlanProuctIds(planProductIdList);
	}

	/**
	 * 更新方案多个产品
	 * @param planProductJson [{方案产品ID ：产品ID}]
	 * @param planId 方案ID
	 * @param needDelPlanProductIds 需删除的方案产品IDs
	 * @param loginUser
	 * @return
	 */
	@Override
	public boolean updatePlanProducts(String planProductJson, Integer planId, String needDelPlanProductIds, LoginUser loginUser){

		Gson gson2 = new Gson();
 		List<PlanProductJsonModel> modelList = gson2.fromJson(planProductJson, new TypeToken<List<PlanProductJsonModel>>() {}.getType());
		if (null == modelList || modelList.size() == 0) {
			return false;
		}
 		for (PlanProductJsonModel planProductJsonModel : modelList) {
			Integer planProductId = planProductJsonModel.getPlanProductId();
			Integer productId = planProductJsonModel.getProductId();
			DesignPlanProduct newPlanProduct = new DesignPlanProduct();
			newPlanProduct.setId(planProductId);
			BaseProduct baseProduct = baseProductService.get(productId);
			//更新多材质信息
			if (baseProduct != null && StringUtils.isNotBlank(baseProduct.getSplitTexturesInfo())) {
				newPlanProduct.setSplitTexturesChooseInfo(baseProduct.getSplitTexturesInfo());
			} else {
				newPlanProduct.setSplitTexturesChooseInfo("");
			}
			newPlanProduct.setProductId(productId);
			newPlanProduct.setIsHide(0);
			this.update(newPlanProduct);
			if (Utils.enableRedisCache()) {
				DesignPlanProductCacher.remove(planId);
			}
			//添加已使用记录
			UsedProducts usedProducts = new UsedProducts();
			usedProducts.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
			usedProducts.setCreator(loginUser.getLoginName());
			usedProducts.setGmtCreate(new Date());
			usedProducts.setGmtModified(new Date());
			usedProducts.setModifier(loginUser.getName());
			usedProducts.setIsDeleted(0);
			usedProducts.setUserId(loginUser.getId());
			usedProducts.setDesignId(planId);
			usedProducts.setProductId(productId);
			usedProducts.setPlanProductId(planProductId);
			int id_ = usedProductsService.add(usedProducts);
			if (Utils.enableRedisCache()) {
				UsedProductsCacher.remove(id_);
			}
			/*更新产品使用次数表*/
			productUsageCountService.update(loginUser.getId(), productId, 1);
		}

		//TODO 把需要删除的方案产品逻辑删除
		if (StringUtils.isNotEmpty(needDelPlanProductIds)) {
			List<Integer> ids = Utils.getListFromInt(needDelPlanProductIds);
			designPlanProductMapper.updateIsDeleted(ids,1);
		}

		return true;
	}

	@Override
	public List<String> scopeProductByuserId(Integer userId) {
		// TODO Auto-generated method stub
		return designPlanProductMapper.scopeProductByuserId(userId);
	}

	@Override
	public List<DesignPlanProduct> getListMoreInfo(DesignPlanProduct designPlanProduct) {
		// 参数验证 ->start
		if(designPlanProduct == null) {
			return null;
		}
		// 参数验证 ->end
		return designPlanProductMapper.getListMoreInfo(designPlanProduct);
	}

}
