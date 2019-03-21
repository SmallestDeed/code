package com.nork.sync.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.jws.WebService;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.util.Tools;
import com.nork.onekeydesign.model.DesignTemplet;
import com.nork.onekeydesign.model.DesignTempletProduct;
import com.nork.onekeydesign.service.DesignTempletProductService;
import com.nork.onekeydesign.service.DesignTempletService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.model.ProductRecommendation;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.ProductRecommendationService;
import com.nork.sync.model.ProductEntity;
import com.nork.sync.model.ResEntity;
import com.nork.sync.model.SyncBaseProduct;
import com.nork.sync.model.SyncDesignTemplet;
import com.nork.sync.model.SyncDesignTempletProduct;
import com.nork.sync.model.SyncProductRecommendation;
import com.nork.sync.model.SyncSpaceCommon;
import com.nork.sync.service.ClientDataService;
import com.nork.sync.service.ServerDataService;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;

/**   
 * @Title: BaseAreaServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-行政区域ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 15:31:09
 * @version V1.0   
 */
@Transactional
@Service("serverDataService")
@WebService(endpointInterface = "com.nork.sync.service.ServerDataService",serviceName = "ServerDataService")
public class ServerDataServiceImpl implements ServerDataService{

	private final static ResourceBundle app = ResourceBundle.getBundle("app");

	@Autowired
	public ClientDataService clientDataService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private SpaceCommonService spaceCommonService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
	@Autowired
	private ProductRecommendationService productRecommendationService;

	public SpaceCommonService getSpaceCommonService() {
		return spaceCommonService;
	}

	public void setSpaceCommonService(SpaceCommonService spaceCommonService) {
		this.spaceCommonService = spaceCommonService;
	}

	/**
	 * 保存样板房
	 * @param syncDesignTemplet
	 * @return
	 */
	@Override
	public String insertDesignTemplet(SyncDesignTemplet syncDesignTemplet){
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("success",true);
		try {
			/** 保存样板房所属空间信息 */
			// 获取控件信息
			SyncSpaceCommon syncSpaceCommon = syncDesignTemplet.getSyncSpaceCommon();
			SpaceCommon spaceCommon = syncSpaceCommon.getSpaceCommon();
			// 删除同编码空间数据
			spaceCommonService.deleteByCode(spaceCommon.getSpaceCode());
			//保存空间基础信息
			int newSpaceCommonId = spaceCommonService.add(spaceCommon);

			/** 保存样板房资源文件 */
			//空间cad图
			ResEntity cadPicEntity = syncSpaceCommon.getCadPicEntity();
			Integer newCadPicId = 0;
			if (cadPicEntity != null) {
				cadPicEntity.setBusinessId(newSpaceCommonId);// 业务ID
				newCadPicId = resPicService.insertEntity(cadPicEntity);// 保存数据
				saveFile(cadPicEntity, cadPicEntity.getResPath());// 保存文件
			}

			//空间户型图ipad缩略图文件
			ResEntity iPadSmallPicEntity = syncSpaceCommon.getiPadSmallPicEntity();
			Integer newIPadSmallPicId = 0;
			if (iPadSmallPicEntity != null) {
				iPadSmallPicEntity.setBusinessId(newSpaceCommonId);// 业务ID
				newIPadSmallPicId = resPicService.insertEntity(iPadSmallPicEntity);// 保存数据
				saveFile(iPadSmallPicEntity, iPadSmallPicEntity.getResPath());// 保存文件
			}
			//空间户型图web缩略图文件
			ResEntity webSmallPicEntity = syncSpaceCommon.getWebSmallPicEntity();
			Integer newWebSmallPicId = 0;
			if (webSmallPicEntity != null) {
				webSmallPicEntity.setBusinessId(newSpaceCommonId);// 业务ID
				newWebSmallPicId = resPicService.insertEntity(webSmallPicEntity);// 保存数据
				saveFile(webSmallPicEntity, webSmallPicEntity.getResPath());// 保存文件
			}
			//空间户型图
			ResEntity picEntity = syncSpaceCommon.getPicEntity();
			Integer newPicId = 0;
			if (picEntity != null) {
				picEntity.setBusinessId(newSpaceCommonId);// 业务ID
				picEntity.setSmallPicInfo("web:" + newWebSmallPicId + ";ipad:" + newIPadSmallPicId);// 缩略图信息
				newPicId = resPicService.insertEntity(picEntity);// 保存数据
				saveFile(picEntity, picEntity.getResPath());// 保存文件
			}

			//空间平面图ipad缩略图文件
			ResEntity iPadSmallViewPlanPicEntity = syncSpaceCommon.getiPadSmallViewPlanPicEntity();
			Integer newIPadSmallViewPlanPicId = 0;
			if (iPadSmallViewPlanPicEntity != null) {
				iPadSmallViewPlanPicEntity.setBusinessId(newSpaceCommonId);// 业务ID
				newIPadSmallViewPlanPicId = resPicService.insertEntity(iPadSmallViewPlanPicEntity);// 保存数据
				saveFile(iPadSmallViewPlanPicEntity, iPadSmallViewPlanPicEntity.getResPath());// 保存文件
			}
			//空间平面图web缩略图文件
			ResEntity webSmallViewPlanPicEntity = syncSpaceCommon.getWebSmallViewPlanPicEntity();
			Integer newWebSmallViewPlanPicId = 0;
			if (webSmallViewPlanPicEntity != null) {
				webSmallViewPlanPicEntity.setBusinessId(newSpaceCommonId);// 业务ID
				newWebSmallViewPlanPicId = resPicService.insertEntity(webSmallViewPlanPicEntity);// 保存数据
				saveFile(webSmallViewPlanPicEntity, webSmallViewPlanPicEntity.getResPath());// 保存文件
			}
			//空间平面图文件
			ResEntity viewPlanPicEntity = syncSpaceCommon.getViewPlanPicEntity();
			Integer newViewPlanPicId = 0;
			if (viewPlanPicEntity != null) {
				viewPlanPicEntity.setBusinessId(newSpaceCommonId);// 业务ID
				viewPlanPicEntity.setSmallPicInfo("web:" + newWebSmallViewPlanPicId + ";ipad:" + newIPadSmallViewPlanPicId);// 缩略图信息
				newViewPlanPicId = resPicService.insertEntity(viewPlanPicEntity);// 保存数据
				saveFile(viewPlanPicEntity, viewPlanPicEntity.getResPath());// 保存文件
			}

			//空间3d俯视ipad缩略图文件
			ResEntity iPadSmallView3dPicEntity = syncSpaceCommon.getiPadSmallView3dPicEntity();
			Integer newIPadSmallView3dPicId = 0;
			if (iPadSmallView3dPicEntity != null) {
				iPadSmallView3dPicEntity.setBusinessId(newSpaceCommonId);// 业务ID
				newIPadSmallView3dPicId = resPicService.insertEntity(iPadSmallView3dPicEntity);// 保存数据
				saveFile(iPadSmallView3dPicEntity, iPadSmallView3dPicEntity.getResPath());// 保存文件
			}
			//空间3d俯视web缩略图文件
			ResEntity webSmallView3dPicEntity = syncSpaceCommon.getWebSmallView3dPicEntity();
			Integer newWebSmallView3dPicId = 0;
			if (webSmallView3dPicEntity != null) {
				webSmallView3dPicEntity.setBusinessId(newSpaceCommonId);// 业务ID
				newWebSmallView3dPicId = resPicService.insertEntity(webSmallView3dPicEntity);// 保存数据
				saveFile(webSmallView3dPicEntity, webSmallView3dPicEntity.getResPath());// 保存文件
			}
			//空间3d俯视图文件
			ResEntity view3dPicEntity = syncSpaceCommon.getView3dPicEntity();
			Integer newView3dPicId = 0;
			if (view3dPicEntity != null) {
				view3dPicEntity.setSmallPicInfo("web:" + newWebSmallView3dPicId + ";ipad:" + newIPadSmallView3dPicId);// 缩略图信息
				view3dPicEntity.setBusinessId(newSpaceCommonId);// 业务ID
				newView3dPicId = resPicService.insertEntity(view3dPicEntity);// 保存数据
				saveFile(view3dPicEntity, view3dPicEntity.getResPath());// 保存文件
			}

			// 回填空间资源文件ID
			spaceCommon.setCadPicId(newCadPicId);
			spaceCommon.setPicId(newPicId);
			spaceCommon.setViewPlanIds(newViewPlanPicId.toString());
			spaceCommon.setView3dPic(newView3dPicId.toString());
			spaceCommonService.update(spaceCommon);

			/** 保存样板房信息 */
			// 获取样板房参数
			DesignTemplet designTemplet = syncDesignTemplet.getDesignTemplet();

			/** 删除该样板房所有关联数据 */
			// 删除该样板房产品推荐信息
			productRecommendationService.deleteByDesignCode(designTemplet.getDesignCode());
			// 删除样板房关联产品信息
			designTempletProductService.deleteByDesignTempletCode(designTemplet.getDesignCode());
			// 删除同编码样板房数据
			designTempletService.deleteByCode(designTemplet.getDesignCode());

			// 保存样板房
			designTemplet.setSpaceCommonId(newSpaceCommonId);
			int newDesignTemplateId = designTempletService.add(designTemplet);

			/** 保存样板房资源文件 */
			// 3d模型文件
			ResEntity _3dMaxModel = syncDesignTemplet.get_3dMaxModel();
			Integer new3dMaxModelId = 0;
			if (_3dMaxModel != null) {
				_3dMaxModel.setBusinessId(newDesignTemplateId);// 业务ID
				new3dMaxModelId = resModelService.insertEntity(_3dMaxModel);// 保存数据
				saveFile(_3dMaxModel, _3dMaxModel.getResPath());// 保存文件
			}
			// u3d模型文件 android
			ResEntity androidModelU3dEntity = syncDesignTemplet.getAndroidModelU3dEntity();
			Integer newAndroidModelU3dId = 0;
			if (androidModelU3dEntity != null) {
				androidModelU3dEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newAndroidModelU3dId = resModelService.insertEntity(androidModelU3dEntity);// 保存数据
				saveFile(androidModelU3dEntity, androidModelU3dEntity.getResPath());// 保存文件
			}
			// u3d模型文件 ios
			ResEntity iosModelU3dEntity = syncDesignTemplet.getIosModelU3dEntity();
			Integer newIosModelU3dId = 0;
			if (iosModelU3dEntity != null) {
				iosModelU3dEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newIosModelU3dId = resModelService.insertEntity(iosModelU3dEntity);// 保存数据
				saveFile(iosModelU3dEntity, iosModelU3dEntity.getResPath());// 保存文件
			}
			// u3d模型文件 iPad
			ResEntity iPadModelU3dEntity = syncDesignTemplet.getiPadModelU3dEntity();
			Integer newIPadModelU3dId = 0;
			if (iPadModelU3dEntity != null) {
				iPadModelU3dEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newIPadModelU3dId = resModelService.insertEntity(iPadModelU3dEntity);// 保存数据
				saveFile(iPadModelU3dEntity, iPadModelU3dEntity.getResPath());// 保存文件
			}
			// u3d模型文件 windows
			ResEntity windowsModelU3dEntity = syncDesignTemplet.getWindowsModelU3dEntity();
			Integer newWindowsModelU3dId = 0;
			if (windowsModelU3dEntity != null) {
				windowsModelU3dEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newWindowsModelU3dId = resModelService.insertEntity(windowsModelU3dEntity);// 保存数据
				saveFile(windowsModelU3dEntity, windowsModelU3dEntity.getResPath());// 保存文件
			}
			// u3d模型文件 macBook
			ResEntity macBookModelU3dEntity = syncDesignTemplet.getMacBookModelU3dEntity();
			Integer newMacBookModelU3dId = 0;
			if (macBookModelU3dEntity != null) {
				macBookModelU3dEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newMacBookModelU3dId = resModelService.insertEntity(macBookModelU3dEntity);// 保存数据
				saveFile(macBookModelU3dEntity, macBookModelU3dEntity.getResPath());// 保存文件
			}
			// u3d模型文件 web
			ResEntity webModelU3dEntity = syncDesignTemplet.getWebModelU3dEntity();
			Integer newWebModelU3dId = 0;
			if (webModelU3dEntity != null) {
				webModelU3dEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newWebModelU3dId = resModelService.insertEntity(webModelU3dEntity);// 保存数据
				saveFile(webModelU3dEntity, webModelU3dEntity.getResPath());// 保存文件
			}
			// 配置文件
			ResEntity configFileEntity = syncDesignTemplet.getConfigFileEntity();
			Integer newConfigFileId = 0;
			if (configFileEntity != null) {
				configFileEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newConfigFileId = resFileService.insertEntity(configFileEntity);// 保存数据
				saveFile(configFileEntity, configFileEntity.getResPath());// 保存文件
			}

			// 空间图缩略图 ipad
			ResEntity designTemplateIPadSmallPicEntity = syncDesignTemplet.getiPadSmallPicEntity();
			Integer newDesignTemplateIPadSmallPicId = 0;
			if (designTemplateIPadSmallPicEntity != null) {
				designTemplateIPadSmallPicEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newDesignTemplateIPadSmallPicId = resPicService.insertEntity(designTemplateIPadSmallPicEntity);// 保存数据
				saveFile(designTemplateIPadSmallPicEntity, designTemplateIPadSmallPicEntity.getResPath());// 保存文件
			}
			// 空间图缩略图 web
			ResEntity designTemplateWebSmallPicEntity = syncDesignTemplet.getWebSmallPicEntity();
			Integer newDesignTemplateWebSmallPicId = 0;
			if (designTemplateWebSmallPicEntity != null) {
				designTemplateWebSmallPicEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newDesignTemplateWebSmallPicId = resPicService.insertEntity(designTemplateWebSmallPicEntity);// 保存数据
				saveFile(designTemplateWebSmallPicEntity, designTemplateWebSmallPicEntity.getResPath());// 保存文件
			}
			// 空间图
			ResEntity designTemplatePicEntity = syncDesignTemplet.getPicEntity();
			Integer newDesignTemplatePicId = 0;
			if (designTemplatePicEntity != null) {
				designTemplatePicEntity.setBusinessId(newDesignTemplateId);// 业务ID
				designTemplatePicEntity.setSmallPicInfo("web:" + newDesignTemplateWebSmallPicId + ";ipad:" + newDesignTemplateIPadSmallPicId);// 缩略图信息
				newDesignTemplatePicId = resPicService.insertEntity(designTemplatePicEntity);// 保存数据
				saveFile(designTemplatePicEntity, designTemplatePicEntity.getResPath());// 保存文件
			}

			// 效果图缩略图 ipad
			ResEntity iPadSmallEffectPicEntity = syncDesignTemplet.getiPadSmallEffectPicEntity();
			Integer newIPadSmallEffectPicId = 0;
			if (iPadSmallEffectPicEntity != null) {
				iPadSmallEffectPicEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newIPadSmallEffectPicId = resPicService.insertEntity(iPadSmallEffectPicEntity);// 保存数据
				saveFile(iPadSmallEffectPicEntity, iPadSmallEffectPicEntity.getResPath());// 保存文件
			}
			// 效果图缩略图 web
			ResEntity webSmallEffectPicEntity = syncDesignTemplet.getWebSmallEffectPicEntity();
			Integer newWebSmallEffectPicId = 0;
			if (webSmallEffectPicEntity != null) {
				webSmallEffectPicEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newWebSmallEffectPicId = resPicService.insertEntity(webSmallEffectPicEntity);// 保存数据
				saveFile(webSmallEffectPicEntity, webSmallEffectPicEntity.getResPath());// 保存文件
			}
			// 效果图
			List<ResEntity> effectPicEntities = syncDesignTemplet.getEffectPicEntities();
			String newEffectPicIdStr = "";
			ResEntity effectPicEntity = null;
			if( effectPicEntities != null && effectPicEntities.size() > 0 ) {
				for (int i = 0; i < effectPicEntities.size(); i++) {
					effectPicEntity = effectPicEntities.get(i);
					effectPicEntity.setBusinessId(newDesignTemplateId);// 业务ID
					if (StringUtils.isNotBlank(effectPicEntity.getSmallPicInfo())) {
						effectPicEntity.setSmallPicInfo("web:" + newWebSmallEffectPicId + ";ipad:" + newIPadSmallEffectPicId);// 缩略图信息
					}
					Integer newEffectPicId = resPicService.insertEntity(effectPicEntity);// 保存数据
					saveFile(effectPicEntity, effectPicEntity.getResPath());// 保存文件
					newEffectPicIdStr += newEffectPicId;
					if (i < effectPicEntities.size() - 1) {
						newEffectPicIdStr += ",";
					}
				}
			}

			//平面图缩略图 ipad
			ResEntity iPadSmallEffectPlanEntity = syncDesignTemplet.getiPadSmallEffectPlanPicEntity();
			Integer newIPadSmallEffectPlanPicId = 0;
			if (iPadSmallEffectPlanEntity != null) {
				iPadSmallEffectPlanEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newIPadSmallEffectPlanPicId = resPicService.insertEntity(iPadSmallEffectPlanEntity);// 保存数据
				saveFile(iPadSmallEffectPlanEntity, iPadSmallEffectPlanEntity.getResPath());// 保存文件
			}
			//平面图缩略图 ipad
			ResEntity webSmallEffectPlanEntity = syncDesignTemplet.getWebSmallEffectPlanPicEntity();
			Integer newWebSmallEffectPlanPicId = 0;
			if (webSmallEffectPlanEntity != null) {
				webSmallEffectPlanEntity.setBusinessId(newDesignTemplateId);// 业务ID
				newWebSmallEffectPlanPicId = resPicService.insertEntity(webSmallEffectPlanEntity);// 保存数据
				saveFile(webSmallEffectPlanEntity, webSmallEffectPlanEntity.getResPath());// 保存文件
			}
			//平面图缩略图
			List<ResEntity> effectPlanPicEntities = syncDesignTemplet.getEffectPlanPicEntities();
			String newEffectPlanPicIdStr = "";
			ResEntity effectPlanPicEntity = null;
			if( effectPlanPicEntities != null && effectPlanPicEntities.size() > 0 ) {
				for (int i = 0; i < effectPlanPicEntities.size(); i++) {
					effectPlanPicEntity = effectPlanPicEntities.get(i);
					effectPlanPicEntity.setBusinessId(newDesignTemplateId);// 业务ID
					if (StringUtils.isNotBlank(effectPlanPicEntity.getSmallPicInfo())) {
						effectPlanPicEntity.setSmallPicInfo("web:" + newWebSmallEffectPlanPicId + ";ipad:" + newIPadSmallEffectPlanPicId);// 缩略图信息
					}
					Integer newEffectPlanPicId = resPicService.insertEntity(effectPlanPicEntity);// 保存数据
					saveFile(effectPlanPicEntity, effectPlanPicEntity.getResPath());// 保存文件
					newEffectPlanPicIdStr += newEffectPlanPicId;
					if (i < effectPlanPicEntities.size() - 1) {
						newEffectPlanPicIdStr += ",";
					}
				}
			}

			// 回填样板房资源文件ID
			designTemplet.setPicId(newDesignTemplatePicId);
			designTemplet.setEffectPic(newEffectPicIdStr);
			designTemplet.setEffectPlanIds(newEffectPlanPicIdStr);
			designTemplet.setModel3dId(new3dMaxModelId);
			designTemplet.setConfigFileId(newConfigFileId);
			designTemplet.setWebModelU3dId(newWebModelU3dId);
			designTemplet.setIpadModelU3dId(newIPadModelU3dId);
			designTemplet.setIosModelU3dId(newIosModelU3dId);
			designTemplet.setAndroidModelU3dId(newAndroidModelU3dId);
			designTemplet.setPcModelU3dId(newWindowsModelU3dId);
			designTemplet.setMacBookpcModelU3dId(newMacBookModelU3dId);
			designTempletService.update(designTemplet);

			//样板房关联产品信息
			Map<Integer, Integer> productContrastMap = new HashMap<>();
			Map<Integer, Integer> designTemplateProductContrastMap = new HashMap<>();
			List<SyncDesignTempletProduct> syncDesignTempletProducts = syncDesignTemplet.getSyncDesignTempletProducts();
			DesignTempletProduct designTempletProduct = null;
			SyncBaseProduct syncBaseProduct = null;
			Integer oldProductId = 0;
			Integer oldDesignTemplateProductId = 0;
			for (SyncDesignTempletProduct syncDesignTempletProduct : syncDesignTempletProducts) {
				/** 保存产品信息 */
				syncBaseProduct = syncDesignTempletProduct.getSyncBaseProduct();
				oldProductId = syncBaseProduct.getBaseProduct().getId();
				// 已添加过该产品则不新增
				if ( !productContrastMap.containsKey(oldProductId) ) {
					// 添加产品
					Integer newProductId = saveProduct(syncBaseProduct);
					productContrastMap.put(oldProductId,newProductId);
				}
				/** 保存样板房产品关联信息 */
				designTempletProduct = syncDesignTempletProduct.getDesignTempletProduct();
				oldDesignTemplateProductId = designTempletProduct.getId();
				designTempletProduct.setProductId(productContrastMap.get(oldProductId));
				designTempletProduct.setDesignTempletId(newDesignTemplateId);
				int newDesignTemplateProductId = designTempletProductService.add(designTempletProduct);
				designTemplateProductContrastMap.put(oldDesignTemplateProductId, newDesignTemplateProductId);
			}
			//样板房产品推荐信息
			List<SyncProductRecommendation> syncProductRecommendations = syncDesignTemplet.getSyncProductRecommendations();
			ProductRecommendation productRecommendation = null;
			if( syncProductRecommendations != null && syncProductRecommendations.size() > 0 ) {
				SyncBaseProduct recommendationSyncProduct = null;
				ProductEntity recommendationProductEntity = null;
				Integer oldProductId_recommendation = 0;
				for (SyncProductRecommendation syncProductRecommendation : syncProductRecommendations) {
					recommendationSyncProduct = syncProductRecommendation.getSyncBaseProduct();
					recommendationProductEntity = recommendationSyncProduct.getBaseProduct();
					oldProductId_recommendation = recommendationProductEntity.getId();
					// 如果该产品已经在此次同步中新增了，则不重复新增
					if( !productContrastMap.containsKey(oldProductId_recommendation) ){
						int newProductId = saveProduct(recommendationSyncProduct);
						productContrastMap.put(oldProductId_recommendation,newProductId);
					}
					productRecommendation = syncProductRecommendation.getProductRecommendation();
					productRecommendation.setProductId(productContrastMap.get(productRecommendation.getProductId()));
					productRecommendation.setDesignTempletId(newDesignTemplateId);
					productRecommendation.setTempletProductId(designTemplateProductContrastMap.get(productRecommendation.getTempletProductId()));
					// 保存产品推荐信息
					productRecommendationService.add(productRecommendation);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		jsonObject.accumulate("message","同步成功！");
		//////System.out.println(jsonObject.toString());
		return jsonObject.toString();
	}

	/**
	 * 保存空间
	 * @param syncSpaceCommon
	 * @return
	 */
	@Override
	public Object insertSpaceCommon(SyncSpaceCommon syncSpaceCommon){
		//////System.out.println("syncSpaceCommon = " + syncSpaceCommon);
		return null;
	}

	/**
	 * 保存产品
	 * @param syncBaseProduct
	 * @return
	 */
	@Override
	public Object insertProduct(SyncBaseProduct syncBaseProduct){
		//////System.out.println("syncBaseProduct = " + syncBaseProduct);
		return null;
	}

	/**
	 * 生成文件
	 * @param fileEntity
	 * @param newPath
	 * @return
	 */
	public Boolean saveFile(ResEntity fileEntity,String newPath){
		DataHandler handler = fileEntity.getFile();
		InputStream is = null;
		OutputStream os = null;
		try {
			File file = new File(Tools.getRootPath(newPath, "" )+ newPath);
			if( !file.getParentFile().exists() ){
				file.getParentFile().mkdirs();
			}
			is = handler.getInputStream();
			os = new FileOutputStream(file);
			int n = 0;
			while ((n = is.read()) != -1) {
				os.write(n);
			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public int saveProduct(SyncBaseProduct syncBaseProduct){
		ProductEntity productEntity = syncBaseProduct.getBaseProduct();
		// 删除编码相同的产品
		baseProductService.deleteByCode(productEntity.getProductCode());
		// 保存产品基础信息
		int newProductId = baseProductService.insertEntity(productEntity);

		// 是否为白模产品
		boolean isBaimo = productEntity.getProductCode().startsWith("baimo");

		/** 保存产品资源文件 */
		// 3d模型文件
		ResEntity modelEntity_product = syncBaseProduct.getModelEntity();
		Integer new3dMaxModelId_product = 0;
		if (modelEntity_product != null) {
			modelEntity_product.setBusinessId(newProductId);// 业务ID
			new3dMaxModelId_product = resModelService.insertEntity(modelEntity_product);// 保存数据
			saveFile(modelEntity_product, modelEntity_product.getResPath());// 保存文件
		}
		// u3d模型文件 android
		ResEntity androidModelU3dEntity_product = syncBaseProduct.getAndroidU3dModelEntity();
		Integer newAndroidModelU3dId_product = 0;
		if (androidModelU3dEntity_product != null) {
			androidModelU3dEntity_product.setBusinessId(newProductId);// 业务ID
			newAndroidModelU3dId_product = resModelService.insertEntity(androidModelU3dEntity_product);// 保存数据
			saveFile(androidModelU3dEntity_product, androidModelU3dEntity_product.getResPath());// 保存文件
		}
		// u3d模型文件 ios
		ResEntity iosModelU3dEntity_product = syncBaseProduct.getIosU3dModelEntity();
		Integer newIosModelU3dId_product = 0;
		if (iosModelU3dEntity_product != null) {
			iosModelU3dEntity_product.setBusinessId(newProductId);// 业务ID
			newIosModelU3dId_product = resModelService.insertEntity(iosModelU3dEntity_product);// 保存数据
			saveFile(iosModelU3dEntity_product, iosModelU3dEntity_product.getResPath());// 保存文件
		}
		// u3d模型文件 iPad
		ResEntity iPadModelU3dEntity_product = syncBaseProduct.getiPadU3dModelEntity();
		Integer newIPadModelU3dId_product = 0;
		if (iPadModelU3dEntity_product != null) {
			iPadModelU3dEntity_product.setBusinessId(newProductId);// 业务ID
			newIPadModelU3dId_product = resModelService.insertEntity(iPadModelU3dEntity_product);// 保存数据
			saveFile(iPadModelU3dEntity_product, iPadModelU3dEntity_product.getResPath());// 保存文件
		}
		// u3d模型文件 windows
		ResEntity windowsModelU3dEntity_product = syncBaseProduct.getWindowsU3dModelEntity();
		Integer newWindowsModelU3dId_product = 0;
		if (windowsModelU3dEntity_product != null) {
			windowsModelU3dEntity_product.setBusinessId(newProductId);// 业务ID
			newWindowsModelU3dId_product = resModelService.insertEntity(windowsModelU3dEntity_product);// 保存数据
			saveFile(windowsModelU3dEntity_product, windowsModelU3dEntity_product.getResPath());// 保存文件
		}
		// u3d模型文件 macBook
		ResEntity macBookModelU3dEntity_product = syncBaseProduct.getMacBookU3dModelEntity();
		Integer newMacBookModelU3dId_product = 0;
		if (macBookModelU3dEntity_product != null) {
			macBookModelU3dEntity_product.setBusinessId(newProductId);// 业务ID
			newMacBookModelU3dId_product = resModelService.insertEntity(macBookModelU3dEntity_product);// 保存数据
			saveFile(macBookModelU3dEntity_product, macBookModelU3dEntity_product.getResPath());// 保存文件
		}
		// u3d模型文件 web
		ResEntity webModelU3dEntity_product = syncBaseProduct.getWebU3dModelEntity();
		Integer newWebModelU3dId_product = 0;
		if (webModelU3dEntity_product != null) {
			webModelU3dEntity_product.setBusinessId(newProductId);// 业务ID
			newWebModelU3dId_product = resModelService.insertEntity(webModelU3dEntity_product);// 保存数据
			saveFile(webModelU3dEntity_product, webModelU3dEntity_product.getResPath());// 保存文件
		}

		// 缩略图
		ResEntity smallPicEntity_product = syncBaseProduct.getPicEntity();
		Integer newSmallPic_product = 0;
		if (smallPicEntity_product != null) {
			smallPicEntity_product.setBusinessId(newProductId);// 业务ID
			newSmallPic_product = resPicService.insertEntity(smallPicEntity_product);// 保存数据
			saveFile(smallPicEntity_product, smallPicEntity_product.getResPath());// 保存文件
		}
		// 产品图片列表
		List<ResEntity> picEntities_product = syncBaseProduct.getSmallPicEntities();
		String picIdStr = "";
		if (picEntities_product != null && picEntities_product.size() > 0 && !isBaimo) {
			ResEntity picEntity_product = null;
			for (int i = 0; i < picEntities_product.size(); i++) {
				picEntity_product = picEntities_product.get(i);
				picEntity_product.setBusinessId(newProductId);// 业务ID
				Integer newPic_product = resPicService.insertEntity(picEntity_product);// 保存数据
				saveFile(picEntity_product, picEntity_product.getResPath());// 保存文件
				picIdStr += newPic_product;
				if (i < picEntities_product.size() - 1) {
					picIdStr += ",";
				}
			}
		}
		// u3d材质列表
		List<ResEntity> material3dPicEntities = syncBaseProduct.getMaterial3dPicEntities();
		String material3dPicIdsStr = "";
		if (material3dPicEntities != null && material3dPicEntities.size() > 0 && !isBaimo) {
			ResEntity material3dPicEntity = null;
			for (int i = 0; i < material3dPicEntities.size(); i++) {
				material3dPicEntity = material3dPicEntities.get(i);
				material3dPicEntity.setBusinessId(newProductId);// 业务ID
				Integer newMaterial3dPicId = resPicService.insertEntity(material3dPicEntity);// 保存数据
				saveFile(material3dPicEntity, material3dPicEntity.getResPath());// 保存文件
				material3dPicIdsStr += newMaterial3dPicId;
				if (i < material3dPicEntities.size() - 1) {
					material3dPicIdsStr += ",";
				}
			}
		}
		// 材质球
		ResEntity materialFileEntity = syncBaseProduct.getMaterialFileEntity();
		Integer newMaterialFileId = 0;
		if (materialFileEntity != null && !isBaimo) {
			materialFileEntity.setBusinessId(newProductId);// 业务ID
			newMaterialFileId = resFileService.insertEntity(materialFileEntity);// 保存数据
			saveFile(materialFileEntity, materialFileEntity.getResPath());// 保存文件
		}
		// 3dmax材质列表
		List<ResEntity> materialPicEntities = syncBaseProduct.getMaterialPicEntities();
		String materialPicIdsStr = "";
		if (materialPicEntities != null && materialPicEntities.size() > 0 && !isBaimo) {
			ResEntity materialPicEntity = null;
			for (int i = 0; i < materialPicEntities.size(); i++) {
				materialPicEntity = materialPicEntities.get(i);
				materialPicEntity.setBusinessId(newProductId);// 业务ID
				Integer newMaterialPicId = resPicService.insertEntity(materialPicEntity);// 保存数据
				saveFile(materialPicEntity, materialPicEntity.getResPath());// 保存文件
				materialPicIdsStr += newMaterialPicId;
				if (i < materialPicEntities.size() - 1) {
					materialPicIdsStr += ",";
				}
			}
		}

		// 回填产品资源文件ID
		productEntity.setPicId(newSmallPic_product);
		productEntity.setPicIds(picIdStr);
		productEntity.setMaterialFileId(newMaterialFileId);
		productEntity.setMaterial3dPicIds(material3dPicIdsStr);
		productEntity.setMaterialPicIds(materialPicIdsStr);
		productEntity.setU3dModelId(newWebModelU3dId_product.toString());
		productEntity.setWindowsU3dModelId(newWindowsModelU3dId_product);
		productEntity.setMacBookU3dModelId(newMacBookModelU3dId_product);
		productEntity.setIosU3dModelId(newIosModelU3dId_product);
		productEntity.setAndroidU3dModelId(newAndroidModelU3dId_product);
		productEntity.setIpadU3dModelId(newIPadModelU3dId_product);
		baseProductService.updateEntity(productEntity);

		return newProductId;
	}
}
