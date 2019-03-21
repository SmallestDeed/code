package com.nork.sync.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.base.model.ExportConst;
import com.nork.common.model.LoginUser;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.model.search.DesignTempletProductSearch;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.BaseHouseService;
import com.nork.home.service.DesignRecommendationService;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProductPutawayState;
import com.nork.product.model.ProductRecommendation;
import com.nork.product.model.search.ProductRecommendationSearch;
import com.nork.product.service.BaseBrandService;
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
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysUserService;
import com.nork.system.websocket.obj.CommonServer;

/**   
 * @Title: BaseAreaServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:
 * 收集客户端的最新数据，为发送服务端做准备
 * 数据收集：
 * 文件收集：
 * 收集后主要为了传输到服务端
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 15:31:09
 * @version V1.0   
 */
@Service("clientDataService")
@Transactional
public class ClientDataServiceImpl implements ClientDataService{
	private static Logger logger = Logger.getLogger(ClientDataService.class);
	public final static String SYSTEM_FORMAT = Utils.getValue("app.system.format", "linux").trim();

	public final static Boolean saveFiles = true;//是否推送文件
	public final static Boolean saveRecommendations = true;//是否推送样板房推荐产品

	@Autowired
	private ResFileService resFileService;
	@Autowired
	private ResPicService  resPicService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private SysUserService  sysUserService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private BaseHouseService baseHouseService;
	@Autowired
	private DesignRecommendationService designRecommendationService;
	@Autowired
	private SpaceCommonService spaceCommonService;
	@Autowired
	public ProductRecommendationService productRecommendationService;
	@Autowired
	public DesignTempletProductService templetProductService;

	@Override
	public Map<String,Object> spaceCommonCollect(Integer spaceCommonId,LoginUser loginUser) {
		//是否给客户端推送同步信息
		Session webSocketSession = null;
		if(CommonServer.clientMap.containsKey(loginUser.getId())){
			webSocketSession = CommonServer.clientMap.get(loginUser.getId());
		}
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("success",true);
		Map<String, String> map = new HashMap<String, String>();
		//空间(户型图picId，3d模型modelId，3d俯视图view3dPic，
		//(modelU3dId,windowsPcU3dModelId,macBookPcU3dModelId,ipadU3dModelId,iosU3dModelId,androidU3dModelId)
		SyncSpaceCommon syncSpaceCommon = new SyncSpaceCommon();
		SpaceCommon  sc = spaceCommonService.get(spaceCommonId);
		spaceCommonService.sysSave(sc,loginUser);
		syncSpaceCommon.setSpaceCommon(sc);
		if(sc == null){
			logger.info("空间数据不存在！spaceCommonId = " + spaceCommonId);
			resultMap.put("success",false);
			resultMap.put("message","空间数据不存在!");
			return resultMap;
		}
		CommonServer.sendMessage(webSocketSession, "【"+sc.getSpaceCode()+"】空间的基础信息组装成功.\r\n");
		if( saveFiles ){
			//户型图
			if(sc.getPicId() != null && sc.getPicId().intValue() >0){
				ResEntity picEntity = resPicService.selectResEntity(sc.getPicId());
				if( picEntity != null && StringUtils.isNotBlank(picEntity.getResPath()) ){
					picEntity.setFile(constructDataHandler(picEntity.getResPath()));
					sysSaveResEntity(picEntity, loginUser);
					syncSpaceCommon.setPicEntity(picEntity);
					CommonServer.sendMessage(webSocketSession, "【" + sc.getSpaceCode() + "】空间的户型图组装成功.\r\n");

					//户型图(缩略图)
					if( StringUtils.isNotBlank(picEntity.getSmallPicInfo()) ){
						map = getSmallPicId(picEntity.getSmallPicInfo());
						if( StringUtils.isNotBlank(map.get("ipad")) ){
							ResEntity iPadSmallPicEntity = resPicService.selectResEntity(Integer.valueOf(map.get("ipad")));
							if( iPadSmallPicEntity != null && StringUtils.isNotBlank(iPadSmallPicEntity.getResPath()) ){
								iPadSmallPicEntity.setFile(constructDataHandler(iPadSmallPicEntity.getResPath()));
								sysSaveResEntity(iPadSmallPicEntity, loginUser);
								syncSpaceCommon.setiPadSmallPicEntity(iPadSmallPicEntity);
								CommonServer.sendMessage(webSocketSession, "【" + sc.getSpaceCode() + "】空间的iPad版户型图组装成功(缩略图).\r\n");
							} else{
								logger.info("导出数据：" + ExportConst.SpaceCommon_HouseSmall_Pic + " is null;path=" + iPadSmallPicEntity == null ? null : iPadSmallPicEntity.getResPath());
								resultMap.put("success",false);
								resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]iPad版3d俯视图的缩略图!");
								return resultMap;
							}
						}else{
							logger.info("导出数据：" + ExportConst.SpaceCommon_HouseSmall_Pic + " is null");
							resultMap.put("success",false);
							resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]iPad版3d俯视图的缩略图!");
							return resultMap;
						}
						if( StringUtils.isNotBlank(map.get("web")) ){
							ResEntity webSmallPicEntity = resPicService.selectResEntity(Integer.valueOf(map.get("web")));
							if( webSmallPicEntity != null && StringUtils.isNotBlank(webSmallPicEntity.getResPath()) ){
								webSmallPicEntity.setFile(constructDataHandler(webSmallPicEntity.getResPath()));
								sysSaveResEntity(webSmallPicEntity, loginUser);
								syncSpaceCommon.setWebSmallPicEntity(webSmallPicEntity);
								CommonServer.sendMessage(webSocketSession, "【" + sc.getSpaceCode() + "】空间的web版户型图组装成功(缩略图).\r\n");
							} else{
								logger.info("导出数据："+ExportConst.SpaceCommon_HouseSmall_Pic + " is null;path="+webSmallPicEntity==null?null:webSmallPicEntity.getResPath());
								resultMap.put("success",false);
								resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]web版3d俯视图的缩略图!");
								return resultMap;
							}
						}else{
							logger.info("导出数据："+ExportConst.SpaceCommon_HouseSmall_Pic + " is null");
							resultMap.put("success",false);
							resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的iPad版3d俯视图的缩略图!");
							return resultMap;
						}
					}else{
						resultMap.put("success",false);
						resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的3d俯视图的缩略图!");
						return resultMap;
					}
				} else{
					logger.info("导出数据："+ExportConst.SpaceCommon_House_Pic + " is null;path="+picEntity==null?null:picEntity.getResPath());
					resultMap.put("success",false);
					resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的3d俯视图!");
					return resultMap;
				}
			}else{
				logger.info("导出数据："+ExportConst.SpaceCommon_House_Pic + " is null");
				resultMap.put("success",false);
				resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的3d俯视图!");
				return resultMap;
			}

			//3d俯视图
			Integer view3dPic = StringUtils.isEmpty(sc.getView3dPic())?new Integer(-1):new Integer(sc.getView3dPic());
			if(view3dPic != null && view3dPic >0){
				ResEntity view3dPicEntity = resPicService.selectResEntity(view3dPic);
				if( view3dPicEntity != null && StringUtils.isNotBlank(view3dPicEntity.getResPath()) ){
					view3dPicEntity.setFile(constructDataHandler(view3dPicEntity.getResPath()));
					sysSaveResEntity(view3dPicEntity, loginUser);
					syncSpaceCommon.setView3dPicEntity(view3dPicEntity);
					CommonServer.sendMessage(webSocketSession, "【" + sc.getSpaceCode() + "】空间的3D俯视图组装成功.\r\n");

					//3d俯视图(缩略图)
					if( StringUtils.isNotBlank(view3dPicEntity.getSmallPicInfo()) ) {
						map = getSmallPicId(view3dPicEntity.getSmallPicInfo());
						if ( StringUtils.isNotBlank(map.get("ipad")) ) {
							ResEntity iPadSmallView3dPicEntity = resPicService.selectResEntity(Integer.valueOf(map.get("ipad")));
							if (iPadSmallView3dPicEntity != null && StringUtils.isNotBlank(iPadSmallView3dPicEntity.getResPath())) {
								iPadSmallView3dPicEntity.setFile(constructDataHandler(iPadSmallView3dPicEntity.getResPath()));
								sysSaveResEntity(iPadSmallView3dPicEntity, loginUser);
								syncSpaceCommon.setiPadSmallView3dPicEntity(iPadSmallView3dPicEntity);
								CommonServer.sendMessage(webSocketSession, "【" + sc.getSpaceCode() + "】空间的iPad版3D俯视图组装成功(缩略图).\r\n");
							} else {
								logger.info("导出数据：" + ExportConst.SpaceCommon_3dViewSmall_Pic + " is null;path=" + (iPadSmallView3dPicEntity == null ? null : iPadSmallView3dPicEntity.getResPath()));
								resultMap.put("success",false);
								resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的iPad版3d俯视图的缩略图!");
								return resultMap;
							}
						} else {
							logger.info("导出数据：" + ExportConst.SpaceCommon_3dViewSmall_Pic + " is null");
							resultMap.put("success",false);
							resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的iPad版3d俯视图的缩略图!");
							return resultMap;
						}
						if ( StringUtils.isNotBlank(map.get("web")) ) {
							ResEntity webSmallView3dPicEntity = resPicService.selectResEntity(Integer.valueOf(map.get("web")));
							if (webSmallView3dPicEntity != null && StringUtils.isNotBlank(webSmallView3dPicEntity.getResPath())) {
								webSmallView3dPicEntity.setFile(constructDataHandler(webSmallView3dPicEntity.getResPath()));
								sysSaveResEntity(webSmallView3dPicEntity, loginUser);
								syncSpaceCommon.setWebSmallView3dPicEntity(webSmallView3dPicEntity);
								CommonServer.sendMessage(webSocketSession, "【" + sc.getSpaceCode() + "】空间的web版3D俯视图组装成功(缩略图).\r\n");
							} else {
								logger.info("导出数据：" + ExportConst.SpaceCommon_3dViewSmall_Pic + " is null;path=" + (webSmallView3dPicEntity == null ? null : webSmallView3dPicEntity.getResPath()));
								resultMap.put("success",false);
								resultMap.put("message", "没有找到空间["+sc.getSpaceCode()+"]的web版3d俯视图的缩略图!");
								return resultMap;
							}
						} else {
							logger.info("导出数据：" + ExportConst.SpaceCommon_3dViewSmall_Pic + " is null");
							resultMap.put("success",false);
							resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的web版3d俯视图的缩略图!");
							return resultMap;
						}
					}else{
						resultMap.put("success",false);
						resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的3d俯视图缩略图!");
						return resultMap;
					}
				} else{
					logger.info("导出数据："+ExportConst.SpaceCommon_3dView_Pic + " is null;path="+view3dPicEntity==null?null:view3dPicEntity.getResPath());
					resultMap.put("success",false);
					resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的3d俯视图!");
					return resultMap;
				}
			}else{
				logger.info("导出数据："+ExportConst.SpaceCommon_3dView_Pic + " is null");
				resultMap.put("success",false);
				resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的3d俯视图!");
				return resultMap;
			}

			//平面图
			Integer viewPlanPic = StringUtils.isEmpty(sc.getViewPlanIds())?new Integer(-1):new Integer(sc.getViewPlanIds());
			if(viewPlanPic != null && viewPlanPic >0){
				ResEntity viewPlanPicEntity = resPicService.selectResEntity(viewPlanPic);
				if( viewPlanPicEntity != null && StringUtils.isNotBlank(viewPlanPicEntity.getResPath()) ){
					viewPlanPicEntity.setFile(constructDataHandler(viewPlanPicEntity.getResPath()));
					sysSaveResEntity(viewPlanPicEntity, loginUser);
					syncSpaceCommon.setViewPlanPicEntity(viewPlanPicEntity);
					CommonServer.sendMessage(webSocketSession, "【" + sc.getSpaceCode() + "】空间的平面图组装成功.\r\n");

					//平面图(缩略图)
					if( StringUtils.isNotBlank(viewPlanPicEntity.getSmallPicInfo()) ) {
						map = getSmallPicId(viewPlanPicEntity.getSmallPicInfo());
						if ( StringUtils.isNotBlank(map.get("ipad")) ) {
							ResEntity iPadSmallViewPlanPicEntity = resPicService.selectResEntity(Integer.valueOf(map.get("ipad")));
							if (iPadSmallViewPlanPicEntity != null && StringUtils.isNotBlank(iPadSmallViewPlanPicEntity.getResPath())) {
								iPadSmallViewPlanPicEntity.setFile(constructDataHandler(iPadSmallViewPlanPicEntity.getResPath()));
								sysSaveResEntity(iPadSmallViewPlanPicEntity, loginUser);
								syncSpaceCommon.setiPadSmallViewPlanPicEntity(iPadSmallViewPlanPicEntity);
								CommonServer.sendMessage(webSocketSession, "【" + sc.getSpaceCode() + "】空间的iPad版平面图组装成功(缩略图).\r\n");
							} else {
								logger.info("导出数据：" + ExportConst.SpaceCommon_viewPlanSmall_Pic + " is null;path=" + (iPadSmallViewPlanPicEntity == null ? null : iPadSmallViewPlanPicEntity.getResPath()));
								resultMap.put("success",false);
								resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的iPad版平面图的缩略图!");
								return resultMap;
							}
						} else {
							logger.info("导出数据：" + ExportConst.SpaceCommon_viewPlanSmall_Pic + " is null");
							resultMap.put("success",false);
							resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的iPad版平面图的缩略图!");
							return resultMap;
						}
						if ( StringUtils.isNotBlank(map.get("web")) ) {
							ResEntity webSmallViewPlanPicEntity = resPicService.selectResEntity(Integer.valueOf(map.get("web")));
							if (webSmallViewPlanPicEntity != null && StringUtils.isNotBlank(webSmallViewPlanPicEntity.getResPath())) {
								webSmallViewPlanPicEntity.setFile(constructDataHandler(webSmallViewPlanPicEntity.getResPath()));
								sysSaveResEntity(webSmallViewPlanPicEntity, loginUser);
								syncSpaceCommon.setWebSmallViewPlanPicEntity(webSmallViewPlanPicEntity);
								CommonServer.sendMessage(webSocketSession, "【" + sc.getSpaceCode() + "】空间的web版平面图组装成功(缩略图).\r\n");
							} else {
								logger.info("导出数据：" + ExportConst.SpaceCommon_viewPlanSmall_Pic + " is null;path=" + (webSmallViewPlanPicEntity == null ? null : webSmallViewPlanPicEntity.getResPath()));
								resultMap.put("success",false);
								resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的web版平面图的缩略图!");
								return resultMap;
							}
						} else {
							logger.info("导出数据：" + ExportConst.SpaceCommon_viewPlanSmall_Pic + " is null");
							resultMap.put("success",false);
							resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的web版平面图的缩略图!");
							return resultMap;
						}
					}else{
						resultMap.put("success",false);
						resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的平面图的缩略图!");
						return resultMap;
					}
				} else{
					logger.info("导出数据："+ExportConst.SpaceCommon_viewPlan_Pic + " is null;path="+(viewPlanPicEntity==null?null:viewPlanPicEntity.getResPath()));
					resultMap.put("success",false);
					resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的平面图!");
					return resultMap;
				}
			}else{
				logger.info("导出数据："+ExportConst.SpaceCommon_viewPlan_Pic + " is null");
				resultMap.put("success",false);
				resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的平面图!");
				return resultMap;
			}

			//空间CAD图
			if(sc.getCadPicId() != null && sc.getCadPicId() >0){
				ResEntity cadPicEntity = resPicService.selectResEntity(sc.getCadPicId());
				if( cadPicEntity != null && StringUtils.isNotBlank(cadPicEntity.getResPath()) ){
					cadPicEntity.setFile(constructDataHandler(cadPicEntity.getResPath()));
					sysSaveResEntity(cadPicEntity, loginUser);
					syncSpaceCommon.setCadPicEntity(cadPicEntity);
					CommonServer.sendMessage(webSocketSession, "【" + sc.getSpaceCode() + "】空间的CAD图组装成功(缩略图).\r\n");
				} else{
					logger.info("导出数据："+ExportConst.SpaceCommon_cadPic_Pic + " is null;path="+(cadPicEntity==null?null : cadPicEntity.getResPath()));
					resultMap.put("success",false);
					resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的cad图片!");
					return resultMap;
				}
			}else{
				logger.info("导出数据："+ExportConst.SpaceCommon_cadPic_Pic + " is null");
				resultMap.put("success",false);
				resultMap.put("message","没有找到空间["+sc.getSpaceCode()+"]的cad图片!");
				return resultMap;
			}
		}
		resultMap.put("obj",syncSpaceCommon);
		return resultMap;
	}

	@Override
	public  Map<String,Object> designTempletCollect(Integer designTempletId,LoginUser loginUser) {
		//是否给客户端推送同步信息
		Session webSocketSession = null;
		if(CommonServer.clientMap.containsKey(loginUser.getId())){
			webSocketSession = CommonServer.clientMap.get(loginUser.getId());
		}
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("success",true);
		//样板房(空间布局图picId,配置文件configFileId，渲染图renderPicIds,效果图列表effectPic，3d效果图moduleId,白天eveningFileId,黄昏dawnFileId,晚上nightFileId)
		//(u3dModelPath,iosU3dModelId,androidU3dModelId,macBookU3dModelId,windowsU3dModelId,ipadU3dModelId)
		Map<String, String> map = new HashMap<String, String>();
		SyncDesignTemplet syncDesignTemplet = new SyncDesignTemplet();
		DesignTemplet dt = designTempletService.get(designTempletId);
		designTempletService.sysSave(dt,loginUser);
		syncDesignTemplet.setDesignTemplet(dt);
		if( dt == null){
			logger.info("导出数据："+"designTemplet is null ! designTempletId=" + designTempletId+";dt="+dt);
			resultMap.put("success",false);
			resultMap.put("message","样板房不存在!");
			return resultMap;
		}
		//给客户端发送同步日志
		CommonServer.sendMessage(webSocketSession, "【"+dt.getDesignCode()+"】样板房基础数据组装成功.\r\n");
		if( saveFiles ) {
			//空间图
			if (dt.getPicId() != null && dt.getPicId().intValue() > 0) {
				ResEntity picEntity = resPicService.selectResEntity(dt.getPicId());
				if (picEntity != null && StringUtils.isNotBlank(picEntity.getResPath())) {
					picEntity.setFile(constructDataHandler(picEntity.getResPath()));
					sysSaveResEntity(picEntity, loginUser);
					syncDesignTemplet.setPicEntity(picEntity);

					//空间缩略图
					if (StringUtils.isNotBlank(picEntity.getSmallPicInfo())) {
						map = getSmallPicId(picEntity.getSmallPicInfo());
						if (StringUtils.isNotBlank(map.get("ipad"))) {
							ResEntity iPadSmallPicEntity = resPicService.selectResEntity(Integer.valueOf(map.get("ipad")));
							if (iPadSmallPicEntity != null && StringUtils.isNotBlank(iPadSmallPicEntity.getResPath())) {
								iPadSmallPicEntity.setFile(constructDataHandler(iPadSmallPicEntity.getResPath()));
								sysSaveResEntity(iPadSmallPicEntity, loginUser);
								syncDesignTemplet.setiPadSmallPicEntity(iPadSmallPicEntity);
							} else {
								logger.info("导出数据：" + ExportConst.DesignTemplet_spaceLaypSmall_pic + " is null;path=" + (iPadSmallPicEntity == null ? null : iPadSmallPicEntity.getResPath()));
								resultMap.put("success",false);
								resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]iPad版的空间缩略图!");
								return resultMap;
							}
						} else {
							logger.info("导出数据：" + ExportConst.DesignTemplet_spaceLaypSmall_pic + " is null;dt.getSmallPicId()" + map.get("ipad"));
							resultMap.put("success",false);
							resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的iPad版空间缩略图!");
							return resultMap;
						}
						if (StringUtils.isNotBlank(map.get("web"))) {
							ResEntity webSmallPicEntity = resPicService.selectResEntity(Integer.valueOf(map.get("web")));
							if (webSmallPicEntity != null && StringUtils.isNotBlank(webSmallPicEntity.getResPath())) {
								webSmallPicEntity.setFile(constructDataHandler(webSmallPicEntity.getResPath()));
								sysSaveResEntity(webSmallPicEntity, loginUser);
								syncDesignTemplet.setWebSmallPicEntity(webSmallPicEntity);
							} else {
								logger.info("导出数据：" + ExportConst.DesignTemplet_spaceLaypSmall_pic + " is null;path=" + (webSmallPicEntity == null ? null : webSmallPicEntity.getResPath()));
								resultMap.put("success",false);
								resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的web版空间缩略图!");
								return resultMap;
							}
						} else {
							logger.info("导出数据：" + ExportConst.DesignTemplet_spaceLaypSmall_pic + " is null;dt.getSmallPicId()" + map.get("ipad"));
							resultMap.put("success",false);
							resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的web版空间缩略图!");
							return resultMap;
						}
						CommonServer.sendMessage(webSocketSession, "【"+dt.getDesignCode()+"】样板房的空间布局图缩略图组装成功.\r\n");
					} else {
						resultMap.put("success",false);
						resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的空间缩略图!");
						return resultMap;
					}
				} else {
					logger.info("导出数据：" + ExportConst.DesignTemplet_spaceLay_pic + " is null;dt.getPicId()" + dt.getPicId());
					resultMap.put("success",false);
					resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的空间图!");
					return resultMap;
				}
				CommonServer.sendMessage(webSocketSession, "【"+dt.getDesignCode()+"】样板房的空间布局图组装成功.\r\n");
			} else {
				logger.info("导出数据：" + ExportConst.DesignTemplet_spaceLay_pic + " is null;dt.getPicId()" + dt.getPicId());
				resultMap.put("success",false);
				resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的空间图!");
				return resultMap;
			}
			//配置文件
			if (dt.getConfigFileId() != null && dt.getConfigFileId().intValue() > 0) {
				ResEntity configFileEntity = resFileService.selectResEntity(dt.getConfigFileId());
				if (configFileEntity != null && StringUtils.isNotBlank(configFileEntity.getResPath())) {
					configFileEntity.setFile(constructDataHandler(configFileEntity.getResPath()));
					sysSaveResEntity(configFileEntity, loginUser);
					syncDesignTemplet.setConfigFileEntity(configFileEntity);
				} else {
					logger.info("导出数据：" + ExportConst.DesignTemplet_confing_file + " is null;path=" + (configFileEntity == null ? null : configFileEntity.getResPath()));
					resultMap.put("success",false);
					resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的配置文件!");
					return resultMap;
				}
				CommonServer.sendMessage(webSocketSession, "【"+dt.getDesignCode()+"】样板房的配置文件组装成功.\r\n");
			} else {
				logger.info("导出数据：" + ExportConst.DesignTemplet_confing_file + " is null;dt.getPicId()" + dt.getPicId());
				resultMap.put("success",false);
				resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的配置文件!");
				return resultMap;
			}

			//效果图列表
			if (StringUtils.isNotBlank(dt.getEffectPic())) {
				String[] strs = dt.getEffectPic().split(",");
				List<ResEntity> effectPicEntities = new ArrayList<>();
				ResEntity effectPicEntity = null;
				int i = 0;
				for (String str : strs) {
					if (StringUtils.isNotBlank(str) && new Integer(str) > 0) {
						effectPicEntity = resPicService.selectResEntity(Integer.valueOf(str));
						if (effectPicEntity != null && StringUtils.isNotBlank(effectPicEntity.getResPath())) {
							effectPicEntity.setFile(constructDataHandler(effectPicEntity.getResPath()));
							sysSaveResEntity(effectPicEntity, loginUser);
							effectPicEntities.add(effectPicEntity);

							if (StringUtils.isNotBlank(effectPicEntity.getSmallPicInfo())) {
								map = getSmallPicId(effectPicEntity.getSmallPicInfo());
								//效果图缩略图列表
								if (StringUtils.isNotBlank(map.get("ipad"))) {
									ResEntity iPadSmallEffectPicEntity = resPicService.selectResEntity(Integer.valueOf(map.get("ipad")));
									if (iPadSmallEffectPicEntity != null && StringUtils.isNotBlank(iPadSmallEffectPicEntity.getResPath())) {
										iPadSmallEffectPicEntity.setFile(constructDataHandler(iPadSmallEffectPicEntity.getResPath()));
										sysSaveResEntity(iPadSmallEffectPicEntity, loginUser);
										syncDesignTemplet.setiPadSmallEffectPicEntity(iPadSmallEffectPicEntity);
										CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的3D效果图iPad版缩略图组装成功.\r\n");
									} else {
										logger.info("导出数据：" + ExportConst.DesignTemplet_EffectPicSmall_pic + " is null;path=" + (iPadSmallEffectPicEntity == null ? null : iPadSmallEffectPicEntity.getResPath()));
										resultMap.put("success",false);
										resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的效果图的iPad版缩略图!");
										return resultMap;
									}
								} else {
									logger.info("导出数据：" + ExportConst.DesignTemplet_EffectPicSmall_pic + " is null; id = " + map.get("ipad"));
									resultMap.put("success",false);
									resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的效果图的iPad版缩略图!");
									return resultMap;
								}
								if (StringUtils.isNotBlank(map.get("web"))) {
									ResEntity webSmallEffectPicEntity = resPicService.selectResEntity(Integer.valueOf(map.get("web")));
									if (webSmallEffectPicEntity != null && StringUtils.isNotBlank(webSmallEffectPicEntity.getResPath())) {
										webSmallEffectPicEntity.setFile(constructDataHandler(webSmallEffectPicEntity.getResPath()));
										sysSaveResEntity(webSmallEffectPicEntity, loginUser);
										syncDesignTemplet.setiPadSmallEffectPicEntity(webSmallEffectPicEntity);
										CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的3D效果图web版缩略图组装成功.\r\n");
									} else {
										logger.info("导出数据：" + ExportConst.DesignTemplet_EffectPicSmall_pic + " is null;path=" + (webSmallEffectPicEntity == null ? null : webSmallEffectPicEntity.getResPath()));
										resultMap.put("success",false);
										resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的效果图的web版缩略图!");
										return resultMap;
									}
								} else {
									logger.info("导出数据：" + ExportConst.DesignTemplet_EffectPicSmall_pic + " is null; id = " + map.get("ipad"));
									resultMap.put("success",false);
									resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的效果图的web版缩略图!");
									return resultMap;
								}
							} else {
								resultMap.put("success",false);
								resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的效果图的缩略图!");
								return resultMap;
							}
						} else {
							logger.info("导出数据：" + ExportConst.DesignTemplet_EffectPics_pic + " is null;path=" + (effectPicEntity == null ? null : effectPicEntity.getResPath()));
							resultMap.put("success",false);
							resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的效果图!");
							return resultMap;
						}
					} else {
						logger.info("导出数据：" + ExportConst.DesignTemplet_EffectPics_pic + " is null;str=" + str + ";i=" + i);
						resultMap.put("success",false);
						resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的效果图!");
						return resultMap;
					}
				}
				syncDesignTemplet.setEffectPicEntities(effectPicEntities);
				CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的3D效果图组装成功.\r\n");
			} else {
				logger.info("导出数据：" + ExportConst.DesignTemplet_EffectPics_pic + " is null;dt.getEffectPic()=" + dt.getEffectPic());
				resultMap.put("success",false);
				resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的效果图!");
				return resultMap;
			}

			//平面效果图列表
			if (StringUtils.isNotBlank(dt.getEffectPlanIds())) {
				String[] strs = dt.getEffectPlanIds().split(",");
				List<ResEntity> effectPlanPicEntities = new ArrayList<>();
				ResEntity effectPlanPicEntity = null;
				int i = 0;
				for (String str : strs) {
					if (StringUtils.isNotBlank(str) && new Integer(str) > 0) {
						effectPlanPicEntity = resPicService.selectResEntity(Integer.valueOf(str));
						if (effectPlanPicEntity != null && StringUtils.isNotBlank(effectPlanPicEntity.getResPath())) {
							effectPlanPicEntity.setFile(constructDataHandler(effectPlanPicEntity.getResPath()));
							sysSaveResEntity(effectPlanPicEntity, loginUser);
							effectPlanPicEntities.add(effectPlanPicEntity);

							//平面效果图缩略图列表
							if (StringUtils.isNotBlank(effectPlanPicEntity.getSmallPicInfo())) {
								map = getSmallPicId(effectPlanPicEntity.getSmallPicInfo());
								if (StringUtils.isNotBlank(map.get("ipad"))) {
									ResEntity iPadSmallEffectPlanPicEntity = resPicService.selectResEntity(Integer.valueOf(map.get("ipad")));
									if (iPadSmallEffectPlanPicEntity != null && StringUtils.isNotBlank(iPadSmallEffectPlanPicEntity.getResPath())) {
										iPadSmallEffectPlanPicEntity.setFile(constructDataHandler(iPadSmallEffectPlanPicEntity.getResPath()));
										sysSaveResEntity(iPadSmallEffectPlanPicEntity, loginUser);
										syncDesignTemplet.setiPadSmallEffectPlanPicEntity(iPadSmallEffectPlanPicEntity);
										CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的平面效果图的iPad版缩略图组装成功.\r\n");
									} else {
										logger.info("导出数据：" + ExportConst.DesignTemplet_effectPlanSmall_pic + " is null;path=" + (iPadSmallEffectPlanPicEntity == null ? null : iPadSmallEffectPlanPicEntity.getResPath()));
										resultMap.put("success",false);
										resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的iPad版平面效果图的缩略图!");
										return resultMap;
									}
								} else {
									logger.info("导出数据：" + ExportConst.DesignTemplet_effectPlanSmall_pic + " is null;dt.getEffectPlanSmallId() = " + dt.getEffectPlanSmallId());
									resultMap.put("success",false);
									resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的iPad版平面效果图的缩略图!");
									return resultMap;
								}

								if (StringUtils.isNotBlank(map.get("web"))) {
									ResEntity webSmallEffectPlanPicEntity = resPicService.selectResEntity(Integer.valueOf(map.get("web")));
									if (webSmallEffectPlanPicEntity != null && StringUtils.isNotBlank(webSmallEffectPlanPicEntity.getResPath())) {
										webSmallEffectPlanPicEntity.setFile(constructDataHandler(webSmallEffectPlanPicEntity.getResPath()));
										sysSaveResEntity(webSmallEffectPlanPicEntity, loginUser);
										syncDesignTemplet.setWebSmallEffectPlanPicEntity(webSmallEffectPlanPicEntity);
										CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的平面效果图的web版缩略图组装成功.\r\n");
									} else {
										logger.info("导出数据：" + ExportConst.DesignTemplet_effectPlanSmall_pic + " is null;path=" + (webSmallEffectPlanPicEntity == null ? null : webSmallEffectPlanPicEntity.getResPath()));
										resultMap.put("success",false);
										resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的web版平面效果图的缩略图!");
										return resultMap;
									}
								} else {
									logger.info("导出数据：" + ExportConst.DesignTemplet_effectPlanSmall_pic + " is null;dt.getEffectPlanSmallId() = " + dt.getEffectPlanSmallId());
									resultMap.put("success",false);
									resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的web版平面效果图的缩略图!");
									return resultMap;
								}
							} else {
								resultMap.put("success",false);
								resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的平面效果图的缩略图!");
								return resultMap;
							}
						} else {
							logger.info("导出数据：" + ExportConst.DesignTemplet_effectPlanPics_pic + " is null;path=" + (effectPlanPicEntity == null ? null : effectPlanPicEntity.getResPath()));
							resultMap.put("success",false);
							resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的平面效果图!");
							return resultMap;
						}
					} else {
						logger.info("导出数据：" + ExportConst.DesignTemplet_effectPlanPics_pic + " is null;str=" + str + ";i=" + i);
						resultMap.put("success",false);
						resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的平面效果图!");
						return resultMap;
					}
				}
				syncDesignTemplet.setEffectPlanPicEntities(effectPlanPicEntities);
				CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的平面效果图组装成功.\r\n");
			} else {
				logger.info("导出数据：" + ExportConst.DesignTemplet_effectPlanPics_pic + " is null;dt.getEffectPlanPic()=" + dt.getEffectPlanIds());
				resultMap.put("success",false);
				resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的平面效果图!");
				return resultMap;
			}

			//3d模型
			Integer _3dmax = dt.getModel3dId();
			if (_3dmax != null && _3dmax > 0) {
				ResEntity _3dMaxEntity = resModelService.selectResEntity(_3dmax);
				if (_3dMaxEntity != null && StringUtils.isNotBlank(_3dMaxEntity.getResPath())) {
					_3dMaxEntity.setFile(constructDataHandler(_3dMaxEntity.getResPath()));
					sysSaveResEntity(_3dMaxEntity, loginUser);
					syncDesignTemplet.set_3dMaxModel(_3dMaxEntity);
					CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的3Dmax模型(灯光文件)组装成功.\r\n");
				} else {
					logger.info("导出数据：" + ExportConst.DesignTemplet_3dmax_model + " is null;path=" + (_3dMaxEntity == null ? null : _3dMaxEntity.getResPath()));
					resultMap.put("success",false);
					resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的3dmax模型!");
					return resultMap;
				}
			} else {
				logger.info("导出数据：" + ExportConst.DesignTemplet_3dmax_model + " is null;dt.getModel3dId() = " + _3dmax);
				resultMap.put("success",false);
				resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的3dmax模型!");
				return resultMap;
			}
			//web
			Integer web = dt.getWebModelU3dId();
			if (web != null && web > 0) {
				ResEntity webModelU3dEntity = resModelService.selectResEntity(web);
				if (webModelU3dEntity != null && StringUtils.isNotBlank(webModelU3dEntity.getResPath())) {
					webModelU3dEntity.setFile(constructDataHandler(webModelU3dEntity.getResPath()));
					sysSaveResEntity(webModelU3dEntity, loginUser);
					syncDesignTemplet.setWebModelU3dEntity(webModelU3dEntity);
					CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的web版U3D模型组装成功.\r\n");
				} else {
					logger.info("导出数据：" + ExportConst.DesignTemplet_web_model + " is null;path=" + (webModelU3dEntity == null ? null : webModelU3dEntity.getResPath()));
					resultMap.put("success",false);
					resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的web前端u3d模型!");
					return resultMap;
				}
			} else {
				logger.info("导出数据：" + ExportConst.DesignTemplet_web_model + " is null;dt.getWebModelU3dId() = " + dt.getWebModelU3dId());
				resultMap.put("success",false);
				resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的web前端u3d模型!");
				return resultMap;
			}

			//ipad
			Integer ipad = dt.getIpadModelU3dId();
			if (ipad != null && ipad > 0) {
				ResEntity iPadModelU3dEntity = resModelService.selectResEntity(ipad);
				if (iPadModelU3dEntity != null && StringUtils.isNotBlank(iPadModelU3dEntity.getResPath())) {
					iPadModelU3dEntity.setFile(constructDataHandler(iPadModelU3dEntity.getResPath()));
					sysSaveResEntity(iPadModelU3dEntity, loginUser);
					syncDesignTemplet.setiPadModelU3dEntity(iPadModelU3dEntity);
					CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的iPad版U3D模型组装成功.\r\n");
				} else {
					logger.info("导出数据：" + ExportConst.DesignTemplet_pad_model + " is null;path=" + (iPadModelU3dEntity == null ? null : iPadModelU3dEntity.getResPath()));
					resultMap.put("success",false);
					resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的iPad端u3d模型!");
					return resultMap;
				}
			} else {
				logger.info("导出数据：" + ExportConst.DesignTemplet_pad_model + " is null;dt.getIpadModelU3dId() = " + dt.getIpadModelU3dId());
				resultMap.put("success",false);
				resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的iPad端u3d模型!");
				return resultMap;
			}
			//pc
			Integer window = dt.getPcModelU3dId();
			if (window != null && window > 0) {
				ResEntity windowsModelU3dEntity = resModelService.selectResEntity(window);
				if (windowsModelU3dEntity != null && StringUtils.isNotBlank(windowsModelU3dEntity.getResPath())) {
					windowsModelU3dEntity.setFile(constructDataHandler(windowsModelU3dEntity.getResPath()));
					sysSaveResEntity(windowsModelU3dEntity, loginUser);
					syncDesignTemplet.setWindowsModelU3dEntity(windowsModelU3dEntity);
					CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的pc版U3D模型组装成功.\r\n");
				} else {
					logger.info("导出数据：" + ExportConst.DesignTemplet_win_model + " is null;path=" + (windowsModelU3dEntity == null ? null : windowsModelU3dEntity.getResPath()));
					resultMap.put("success",false);
					resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的pc端u3d模型!");
					return resultMap;
				}
			} else {
				logger.info("导出数据：" + ExportConst.DesignTemplet_win_model + " is null;dt.getPcModelU3dId()" + dt.getPcModelU3dId());
				resultMap.put("success",false);
				resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的pc端u3d模型!");
				return resultMap;
			}
			//mac
			Integer mac = dt.getMacBookpcModelU3dId();
			if (mac != null && mac > 0) {
				ResEntity macBookModelU3dEntity = resModelService.selectResEntity(mac);
				if (macBookModelU3dEntity != null && StringUtils.isNotBlank(macBookModelU3dEntity.getResPath())) {
					macBookModelU3dEntity.setFile(constructDataHandler(macBookModelU3dEntity.getResPath()));
					sysSaveResEntity(macBookModelU3dEntity, loginUser);
					syncDesignTemplet.setMacBookModelU3dEntity(macBookModelU3dEntity);
					CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的macBook版U3D模型组装成功.\r\n");
				} else {
					logger.info("导出数据：" + ExportConst.DesignTemplet_mac_model + " is null;path=" + (macBookModelU3dEntity == null ? null : macBookModelU3dEntity.getResPath()));
					resultMap.put("success",false);
					resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的macBook端u3d模型!");
					return resultMap;
				}
			} else {
				logger.info("导出数据：" + ExportConst.DesignTemplet_mac_model + " is null;dt.getMacBookpcModelU3dId()" + dt.getMacBookpcModelU3dId());
				resultMap.put("success",false);
				resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的macBook端u3d模型!");
				return resultMap;
			}
			//android
			Integer android = dt.getAndroidModelU3dId();
			if (android != null && android > 0) {
				ResEntity androidModelU3dEntity = resModelService.selectResEntity(android);
				if (androidModelU3dEntity != null && StringUtils.isNotBlank(androidModelU3dEntity.getResPath())) {
					androidModelU3dEntity.setFile(constructDataHandler(androidModelU3dEntity.getResPath()));
					sysSaveResEntity(androidModelU3dEntity, loginUser);
					syncDesignTemplet.setAndroidModelU3dEntity(androidModelU3dEntity);
					CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的Android版U3D模型组装成功.\r\n");
				} else {
					logger.info("导出数据：" + ExportConst.DesignTemplet_mac_model + " is null;path=" + (androidModelU3dEntity == null ? null : androidModelU3dEntity.getResPath()));
					resultMap.put("success",false);
					resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的android端u3d模型!");
					return resultMap;
				}
			} else {
				logger.info("导出数据：" + ExportConst.DesignTemplet_android_model + " is null;dt.getWebModelU3dId()" + dt.getWebModelU3dId());
				resultMap.put("success",false);
				resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的android端u3d模型!");
				return resultMap;
			}
			//ios
			Integer ios = dt.getIosModelU3dId();
			if (ios != null && ios > 0) {
				ResEntity iosModelU3dEntity = resModelService.selectResEntity(ios);
				if (iosModelU3dEntity != null && StringUtils.isNotBlank(iosModelU3dEntity.getResPath())) {
					iosModelU3dEntity.setFile(constructDataHandler(iosModelU3dEntity.getResPath()));
					sysSaveResEntity(iosModelU3dEntity, loginUser);
					syncDesignTemplet.setIosModelU3dEntity(iosModelU3dEntity);
					CommonServer.sendMessage(webSocketSession, "【" + dt.getDesignCode() + "】样板房的Ios版U3D模型组装成功.\r\n");
				} else {
					logger.info("导出数据：" + ExportConst.DesignTemplet_ios_model + " is null;path=" + (iosModelU3dEntity == null ? null : iosModelU3dEntity.getResPath()));
					resultMap.put("success",false);
					resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的ios端u3d模型!");
					return resultMap;
				}
			} else {
				logger.info("导出数据：" + ExportConst.DesignTemplet_ios_model + " is null;dt.getIosModelU3dId()" + dt.getIosModelU3dId());
				resultMap.put("success",false);
				resultMap.put("message","没有找到样板房["+dt.getDesignCode()+"]的ios端u3d模型!");
				return resultMap;
			}
		}
		resultMap.put("obj",syncDesignTemplet);
		return resultMap;
	}

	@Override
	public BaseBrand baseBrandCollect(Integer baseBrandId) {
		Map<String, Object> map = new HashMap<String, Object>();
		BaseBrand bb = baseBrandService.get(baseBrandId);
		String bl  = bb.getBrandLogo();
		 if(StringUtils.isNotBlank(bl)){
		    	//map.put(ExportConst.BaseBrand_Logo,bl);
			 bb.setBrandLogoPath(bl);
		    }else{
		    	logger.info("导出数据："+ExportConst.BaseBrand_Logo + " is null;path="+bl);
		    	return null;
		    }
		return bb;
	}



	@Override
	public Map<String,Object> baseProductCollect(Integer baseProductId,LoginUser loginUser) {
		//是否给客户端推送同步信息
		Session webSocketSession = null;
		if(CommonServer.clientMap.containsKey(loginUser.getId())){
			webSocketSession = CommonServer.clientMap.get(loginUser.getId());
		}
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("success",true);
		//产品库(缩略图picId,图片列表smallPicIds,材质materialPicIds,3D模型modelId)
		//(u3dModelPath,iosU3dModelId,androidU3dModelId,macBookU3dModelId,windowsU3dModelId,ipadU3dModelId)
		Map<String, Object> map = new HashMap<String, Object>();
		SyncBaseProduct syncBaseProduct = new SyncBaseProduct();
		ProductEntity bp = baseProductService.selectProductEntity(baseProductId);
		if(bp == null){
			logger.info("产品不存在：baseProductId=" + baseProductId);
			resultMap.put("success",false);
			resultMap.put("message","["+baseProductId+"]产品不存在!");
			return resultMap;
		}
		boolean isBaimo = bp.getProductCode().startsWith("baimo");
		/*if( !isBaimo && bp.getPutawayState() != 1 ){
			logger.info("该产品还未上架！productId = " + baseProductId);
			resultMap.put("success",false);
			resultMap.put("message","产品["+bp.getProductCode()+"]还未上架!");
			return resultMap;
		}*/
		
		if( !isBaimo && bp.getPutawayState() != BaseProductPutawayState.IS_RELEASE.intValue() ){
			logger.info("该产品还未发布！productId = " + baseProductId);
			resultMap.put("success",false);
			resultMap.put("message","产品["+bp.getProductCode()+"]还未发布!");
			return resultMap;
		}
		
		baseProductService.sysSave(bp,loginUser);
		syncBaseProduct.setBaseProduct(bp);
		CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品的基础信息组装成功.\r\n");
		if( saveFiles ) {
			if (bp != null) {
				// 缩略图
				if (bp.getPicId() != null && bp.getPicId() > 0) {
					ResEntity picEntity = resPicService.selectResEntity(bp.getPicId());
					if (picEntity != null && StringUtils.isNotBlank(picEntity.getResPath())) {
						picEntity.setFile(constructDataHandler(picEntity.getResPath()));
						sysSaveResEntity(picEntity, loginUser);
						syncBaseProduct.setPicEntity(picEntity);
						CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品的缩略图组装成功.\r\n");
					} else {
						logger.info("导出数据：" + ExportConst.BaseProduct_PicId_Pic + " is null;path=" + (picEntity == null ? null : picEntity.getResPath()));
						resultMap.put("success",false);
						resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的缩略图!");
						return resultMap;
					}
				} else {
					logger.info("导出数据：" + ExportConst.BaseProduct_PicId_Pic + " is null;bp.getPicId()" + bp.getPicId());
					resultMap.put("success",false);
					resultMap.put("message","没有找到["+bp.getProductCode()+"]产品的缩略图!");
					return resultMap;
				}

				// 白模产品没有上传图片列表
				if( !isBaimo ) {
					if (StringUtils.isNotBlank(bp.getPicIds())) {
						String[] strs = bp.getPicIds().split(",");
						List<ResEntity> smallPicEntities = new ArrayList<>();
						ResEntity smallPicEntity = null;
						int i = 0;
						for (String str : strs) {
							if (StringUtils.isNotBlank(str) && new Integer(str) > 0) {
								smallPicEntity = resPicService.selectResEntity(Integer.valueOf(str));
								if (smallPicEntity != null && StringUtils.isNotBlank(smallPicEntity.getResPath())) {
									smallPicEntity.setFile(constructDataHandler(smallPicEntity.getResPath()));
									sysSaveResEntity(smallPicEntity, loginUser);
									smallPicEntities.add(smallPicEntity);
								} else {
									logger.info("导出数据：" + ExportConst.BaseProduct_SmallPicIds_pic + " is null;path=" + (smallPicEntity == null ? null : smallPicEntity.getResPath()));
									resultMap.put("success", false);
									resultMap.put("message", "没有找到该产品的产品图片列表!");
									return resultMap;
								}
							} else {
								logger.info("导出数据：" + ExportConst.BaseProduct_SmallPicIds_pic + " is null;str=" + str + ";i=" + i);
								resultMap.put("success", false);
								resultMap.put("message", "没有找到产品[" + bp.getProductCode() + "]的产品图片列表!");
								return resultMap;
							}
						}
						syncBaseProduct.setSmallPicEntities(smallPicEntities);
						CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品的图片列表组装成功.\r\n");
					} else {
						logger.info("导出数据：" + ExportConst.BaseProduct_SmallPicIds_pic + " is null;bp.getSmallPicIds()=" + bp.getPicIds() + ";");
						resultMap.put("success", false);
						resultMap.put("message", "没有找到产品[" + bp.getProductCode() + "]的产品图片列表!");
						return resultMap;
					}

					//材质图片列表。白模产品没有上传材质
					if (StringUtils.isNotBlank(bp.getMaterialPicIds())) {
						String[] strs = bp.getMaterialPicIds().split(",");
						List<ResEntity> materialPicEntities = new ArrayList<>();
						ResEntity materialPicEntity = null;
						int i = 0;
						for (String str : strs) {
							if (StringUtils.isNotBlank(str) && new Integer(str) > 0) {
								materialPicEntity = resPicService.selectResEntity(Integer.valueOf(str));
								if (materialPicEntity != null && StringUtils.isNotBlank(materialPicEntity.getResPath())) {
									materialPicEntity.setFile(constructDataHandler(materialPicEntity.getResPath()));
									sysSaveResEntity(materialPicEntity, loginUser);
									materialPicEntities.add(materialPicEntity);
								} else {
									logger.info("导出数据：" + ExportConst.BaseProduct_MaterialPicIds_pic + " is null;path=" + (materialPicEntity == null ? null : materialPicEntity.getResPath()));
									resultMap.put("success", false);
									resultMap.put("message", "没有找到产品[" + bp.getProductCode() + "]的u3d材质图片列表!");
									return resultMap;
								}
							} else {
								logger.info("导出数据：" + ExportConst.BaseProduct_MaterialPicIds_pic + " is null;str=" + str + ";i=" + i);
								resultMap.put("success", false);
								resultMap.put("message", "没有找到产品[" + bp.getProductCode() + "]的u3d材质图片列表!");
								return resultMap;
							}
						}
						syncBaseProduct.setMaterialPicEntities(materialPicEntities);
						CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品材质组装成功.\r\n");
					} else {
						logger.info("导出数据：" + ExportConst.BaseProduct_MaterialPicIds_pic + " is null;bp.getMaterialPicIds()=" + bp.getMaterialPicIds() + ";");
						resultMap.put("success", false);
						resultMap.put("message", "没有找到产品[" + bp.getProductCode() + "]的u3d材质图片列表!");
						return resultMap;
					}
				}

				//3dmaxModel
				if (bp.getModelId() != null && bp.getModelId() > 0) {
					ResEntity modelEntity = resModelService.selectResEntity(bp.getModelId());
					if (modelEntity != null && StringUtils.isNotBlank(modelEntity.getResPath())) {
						modelEntity.setFile(constructDataHandler(modelEntity.getResPath()));
						sysSaveResEntity(modelEntity, loginUser);
						syncBaseProduct.setModelEntity(modelEntity);
						CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品的3Dmax模型组装成功.\r\n");
					} else {
						logger.info("导出数据：" + ExportConst.BaseProduct_3dmaxModelId_model + " is null;path=" + (modelEntity == null ? null : modelEntity.getResPath()));
						resultMap.put("success",false);
						resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的3dmax模型!");
						return resultMap;
					}
				} else {
					logger.info("导出数据：" + ExportConst.BaseProduct_3dmaxModelId_model + " is null;bp.getModelId()" + bp.getModelId());
					resultMap.put("success",false);
					resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的3dmax模型!");
					return resultMap;
				}
				//3d材质。白模产品没有上传3dmax材质
				if( !isBaimo ) {
					if (StringUtils.isNotBlank(bp.getMaterial3dPicIds())) {
						String[] strs = bp.getMaterial3dPicIds().split(",");
						List<ResEntity> material3dPicEntities = new ArrayList<>();
						ResEntity material3dPicEntity = null;
						int i = 0;
						for (String str : strs) {
							if (StringUtils.isNotBlank(str) && new Integer(str) > 0) {
								material3dPicEntity = resPicService.selectResEntity(Integer.valueOf(str));
								if (material3dPicEntity != null && StringUtils.isNotBlank(material3dPicEntity.getResPath())) {
									material3dPicEntity.setFile(constructDataHandler(material3dPicEntity.getResPath()));
									sysSaveResEntity(material3dPicEntity, loginUser);
									material3dPicEntities.add(material3dPicEntity);
								} else {
									logger.info("导出数据：" + ExportConst.BaseProduct_3dmaxMaterial_model + " is null;path=" + (material3dPicEntity == null ? null : material3dPicEntity.getResPath()));
									resultMap.put("success", false);
									resultMap.put("message", "没有找到产品[" + bp.getProductCode() + "]的3dmax材质图片列表!");
									return resultMap;
								}
							} else {
								logger.info("导出数据：" + ExportConst.BaseProduct_3dmaxMaterial_model + " is null;str=" + str + ";i=" + i);
								resultMap.put("success", false);
								resultMap.put("message", "没有找到产品[" + bp.getProductCode() + "]的3dmax材质图片列表!");
								return resultMap;
							}
						}
						syncBaseProduct.setMaterial3dPicEntities(material3dPicEntities);
						CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品的3Dmax材质组装成功.\r\n");
					} else {
						logger.info("导出数据：" + ExportConst.BaseProduct_3dmaxMaterial_model + " is null;bp.getMaterial3dPicIds()=" + bp.getMaterial3dPicIds() + ";");
						resultMap.put("success", false);
						resultMap.put("message", "没有找到产品[" + bp.getProductCode() + "]的3dmax材质图片列表!");
						return resultMap;
					}
					//u3d材质球。白模产品没有上传u3d材质球
					if (bp.getMaterialFileId() != null && bp.getMaterialFileId() > 0) {
						ResEntity materialFileEntity = resFileService.selectResEntity(bp.getMaterialFileId());
						if (materialFileEntity != null && StringUtils.isNotBlank(materialFileEntity.getResPath())) {
							materialFileEntity.setFile(constructDataHandler(materialFileEntity.getResPath()));
							sysSaveResEntity(materialFileEntity, loginUser);
							syncBaseProduct.setMaterialFileEntity(materialFileEntity);
							CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品的U3D材质球组装成功.\r\n");
						} else {
							logger.info("导出数据：" + ExportConst.BaseProduct_u3dMaterialFile_file + " is null;path=" + (materialFileEntity == null ? null : materialFileEntity.getResPath()));
							resultMap.put("success", false);
							resultMap.put("message", "没有找到产品[" + bp.getProductCode() + "]的u3d材质球!");
							return resultMap;
						}
					} else {
						logger.info("导出数据：" + ExportConst.BaseProduct_u3dMaterialFile_file + " is null;bp.getMaterialFileId()=" + bp.getMaterialFileId());
						resultMap.put("success", false);
						resultMap.put("message", "没有找到产品[" + bp.getProductCode() + "]的u3d材质球!");
						return resultMap;
					}
				}
				//web
				if (StringUtils.isNotEmpty(bp.getU3dModelId()) && new Integer(bp.getU3dModelId()) > 0) {
					ResEntity u3dModelEntity = resModelService.selectResEntity(Integer.valueOf(bp.getU3dModelId()));
					if (u3dModelEntity != null && StringUtils.isNotBlank(u3dModelEntity.getResPath())) {
						u3dModelEntity.setFile(constructDataHandler(u3dModelEntity.getResPath()));
						sysSaveResEntity(u3dModelEntity, loginUser);
						syncBaseProduct.setWebU3dModelEntity(u3dModelEntity);
						CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品的web版U3D模型组装成功.\r\n");
					} else {
						logger.info("导出数据：" + ExportConst.BaseProduct_web_model + " is null;path=" + (u3dModelEntity == null ? null : u3dModelEntity.getResPath()));
						resultMap.put("success",false);
						resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的web前端的u3d模型!");
						return resultMap;
					}
				} else {
					logger.info("导出数据：" + ExportConst.BaseProduct_web_model + " is null;bp.getU3dModelId()=" + bp.getU3dModelId());
					resultMap.put("success",false);
					resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的web前端的u3d模型!");
					return resultMap;
				}
				//ios
				if (bp.getIosU3dModelId() != null && bp.getIosU3dModelId() > 0) {
					ResEntity iosU3dModelEntity = resModelService.selectResEntity(bp.getIosU3dModelId());
					if (iosU3dModelEntity != null && StringUtils.isNotBlank(iosU3dModelEntity.getResPath())) {
						iosU3dModelEntity.setFile(constructDataHandler(iosU3dModelEntity.getResPath()));
						sysSaveResEntity(iosU3dModelEntity, loginUser);
						syncBaseProduct.setIosU3dModelEntity(iosU3dModelEntity);
						CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品的Ios版U3D模型组装成功.\r\n");
					} else {
						logger.info("导出数据：" + ExportConst.BaseProduct_ios_model + " is null;path=" + (iosU3dModelEntity == null ? null : iosU3dModelEntity.getResPath()));
						resultMap.put("success",false);
						resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的ios的u3d模型!");
						return resultMap;
					}
				} else {
					logger.info("导出数据：" + ExportConst.BaseProduct_ios_model + " is null;bp.getIosU3dModelId()=" + bp.getIosU3dModelId());
					resultMap.put("success",false);
					resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的ios的u3d模型!");
					return resultMap;
				}
				//android
				if (bp.getAndroidU3dModelId() != null && bp.getAndroidU3dModelId() > 0) {
					ResEntity androidU3dModelEntity = resModelService.selectResEntity(bp.getAndroidU3dModelId());
					if (androidU3dModelEntity != null && StringUtils.isNotBlank(androidU3dModelEntity.getResPath())) {
						androidU3dModelEntity.setFile(constructDataHandler(androidU3dModelEntity.getResPath()));
						sysSaveResEntity(androidU3dModelEntity, loginUser);
						syncBaseProduct.setAndroidU3dModelEntity(androidU3dModelEntity);
						CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品的Android版U3D模型组装成功.\r\n");
					} else {
						logger.info("导出数据：" + ExportConst.BaseProduct_android_model + " is null;path=" + (androidU3dModelEntity == null ? null : androidU3dModelEntity.getResPath()));
						resultMap.put("success",false);
						resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的android的u3d模型!");
						return resultMap;
					}
				} else {
					logger.info("导出数据：" + ExportConst.BaseProduct_android_model + " is null;bp.getAndroidU3dModelId()=" + bp.getAndroidU3dModelId());
					resultMap.put("success",false);
					resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的android的u3d模型!");
					return resultMap;
				}
				//mac
				if (bp.getMacBookU3dModelId() != null && bp.getMacBookU3dModelId() > 0) {
					ResEntity macBookU3dModelEntity = resModelService.selectResEntity(bp.getMacBookU3dModelId());
					if (macBookU3dModelEntity != null && StringUtils.isNotBlank(macBookU3dModelEntity.getResPath())) {
						macBookU3dModelEntity.setFile(constructDataHandler(macBookU3dModelEntity.getResPath()));
						sysSaveResEntity(macBookU3dModelEntity, loginUser);
						syncBaseProduct.setMacBookU3dModelEntity(macBookU3dModelEntity);
						CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品的macBook版U3D模型组装成功.\r\n");
					} else {
						logger.info("导出数据：" + ExportConst.BaseProduct_mac_model + " is null;path=" + (macBookU3dModelEntity == null ? null : macBookU3dModelEntity.getResPath()));
						resultMap.put("success",false);
						resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的macBook的u3d模型!");
						return resultMap;
					}
				} else {
					logger.info("导出数据：" + ExportConst.BaseProduct_mac_model + " is null;bp.getMacBookU3dModelId()=" + bp.getMacBookU3dModelId());
					resultMap.put("success",false);
					resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的macBook的u3d模型!");
					return resultMap;
				}
				//win
				if (bp.getWindowsU3dModelId() != null && bp.getWindowsU3dModelId() > 0) {
					ResEntity windowsU3dModelEntity = resModelService.selectResEntity(bp.getWindowsU3dModelId());
					if (windowsU3dModelEntity != null && StringUtils.isNotBlank(windowsU3dModelEntity.getResPath())) {
						windowsU3dModelEntity.setFile(constructDataHandler(windowsU3dModelEntity.getResPath()));
						sysSaveResEntity(windowsU3dModelEntity, loginUser);
						syncBaseProduct.setWindowsU3dModelEntity(windowsU3dModelEntity);
						CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品的windows版U3D模型组装成功.\r\n");
					} else {
						logger.info("导出数据：" + ExportConst.BaseProduct_win_model + " is null;path=" + (windowsU3dModelEntity == null ? null : windowsU3dModelEntity.getResPath()));
						resultMap.put("success",false);
						resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的pc的u3d模型!");
						return resultMap;
					}
				} else {
					logger.info("导出数据：" + ExportConst.BaseProduct_win_model + " is null;bp.getWindowsU3dModelId()=" + bp.getWindowsU3dModelId());
					resultMap.put("success",false);
					resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的pc的u3d模型!");
					return resultMap;
				}
				if (bp.getIpadU3dModelId() != null && bp.getIpadU3dModelId() > 0) {
					ResEntity iPadU3dModelEntity = resModelService.selectResEntity(bp.getIpadU3dModelId());
					if (iPadU3dModelEntity != null && StringUtils.isNotBlank(iPadU3dModelEntity.getResPath())) {
						iPadU3dModelEntity.setFile(constructDataHandler(iPadU3dModelEntity.getResPath()));
						sysSaveResEntity(iPadU3dModelEntity, loginUser);
						syncBaseProduct.setiPadU3dModelEntity(iPadU3dModelEntity);
						CommonServer.sendMessage(webSocketSession, "【" + bp.getProductCode() + "】产品的iPad版U3D模型组装成功.\r\n");
					} else {
						logger.info("导出数据：" + ExportConst.BaseProduct_pad_model + " is null;path=" + (iPadU3dModelEntity == null ? null : iPadU3dModelEntity.getResPath()));
						resultMap.put("success",false);
						resultMap.put("message","没有找到产品["+bp.getProductCode()+"]的iPad的u3d模型!");
						return resultMap;
					}
				} else {
					logger.info("导出数据：" + ExportConst.BaseProduct_pad_model + " is null;bp.getIpadU3dModelId()=" + bp.getIpadU3dModelId());
					resultMap.put("success",false);
					resultMap.put("message","没有找到产品["+bp.getProductCode() + "]的iPad的u3d模型!");
					return resultMap;
				}
			}
		}
		resultMap.put("obj",syncBaseProduct);
		return resultMap;
	}

	/**
	 * 通过文件路径转换成ResEntity
	 * @param filePath
	 * @return
	 */
	public static DataHandler constructDataHandler(String filePath) {
		// construct FileEntity
		/*File file = new File(Tools.getRootPath(filePath, "D:\\app") + "/" + filePath);*/
		File file = new File(Utils.getAbsolutePath(filePath, null));
		DataSource source = new FileDataSource(file);
		DataHandler handler = new DataHandler(source);
		return handler;
	}

	public String getResourcePath(Object list, String resId,String type){
		if("file".equals(type)){
			if(list !=null){
				for(ResFile res:(List<ResFile>)list){
					if(new Integer(resId).intValue() ==res.getId().intValue()){
						return res.getFilePath();
					}
				}
			}else{
				ResFile resFile = resFileService.get(new Integer(resId));
				if(resFile!= null && StringUtils.isNotBlank(resFile.getFilePath())){
					return resFile.getFilePath();
				}
			}
		}
		if("pic".equals(type)){
			if(list ==null){
				ResPic resPic = resPicService.get(new Integer(resId));
				if(resPic!= null && StringUtils.isNotBlank(resPic.getPicPath())){
					return resPic.getPicPath();
				}
			}else{
				for(ResPic res:(List<ResPic>)list){
					////////System.out.println(resId + "==" + res.getId());
					if(new Integer(resId).intValue() == res.getId().intValue()){
						return res.getPicPath();
					}
				}
			}
		}
		if("model".equals(type)){
			if(list ==null){
				ResModel resModel = resModelService.get(new Integer(resId));
				if(resModel!= null && StringUtils.isNotBlank(resModel.getModelPath())){
					return resModel.getModelPath();
				}
			}else{
				for(ResModel res:(List<ResModel>)list){
					if(new Integer(resId).intValue() ==res.getId().intValue()){
						return res.getModelPath();
					}
				}
			}
		}
		return "";
	}

	/**
	 * 组装样板房和关联数据
	 * @param designTempletId
	 * @return
	 */
	@Override
	public Map<String,Object> assembleDesignTemplet(Integer designTempletId,LoginUser loginUser){
		//是否给客户端推送同步信息
		Session webSocketSession = null;
		if(CommonServer.clientMap.containsKey(loginUser.getId())){
			webSocketSession = CommonServer.clientMap.get(loginUser.getId());
		}
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		logger.info("开始组装样板房和关联数据。。。。designTempletId = " + designTempletId);
		// 组装样板房
		Map<String,Object> syncDesignTempletMap = designTempletCollect(designTempletId,loginUser);
		SyncDesignTemplet syncDesignTemplet = null;
		if( (Boolean)syncDesignTempletMap.get("success") ){
			syncDesignTemplet = (SyncDesignTemplet)syncDesignTempletMap.get("obj");
		}else{
			resultMap.put("success",false);
			resultMap.put("message",syncDesignTempletMap.get("message"));
			return resultMap;
		}

		// 组装样板房所属空间
		SyncSpaceCommon syncSpaceCommon = null;
		CommonServer.sendMessage(webSocketSession, "开始组装样板房所属空间\r\n");
		Map<String,Object> spaceMap = assembleSpaceCommon(syncDesignTemplet.getDesignTemplet().getSpaceCommonId(),loginUser);
		if( (Boolean)spaceMap.get("success") ){
			syncSpaceCommon = (SyncSpaceCommon) spaceMap.get("obj");
		}else{
			resultMap.put("success", false);
			resultMap.put("message", spaceMap.get("message"));
			return resultMap;
		}

		// 检查样板房的产品是否存在
		Map<String,Object> designTempletProductMap = assembleDesignTempletProduct(designTempletId,loginUser);
		List syncDesignTempletProducts = new ArrayList();
		if( (Boolean)designTempletProductMap.get("success") ){
			if( designTempletProductMap.get("dataList") != null ){
				syncDesignTempletProducts = (List) designTempletProductMap.get("dataList");
			}
		}else{
			resultMap.put("success", false);
			resultMap.put("message", designTempletProductMap.get("message"));
			return resultMap;
		}
		// 检查推荐产品
		Map<String,Object> productRecommendationMap = assembleProductRecommendation(designTempletId,loginUser);
		List syncProductRecommendations = new ArrayList();
		if( (Boolean)productRecommendationMap.get("success") ){
			if( productRecommendationMap.get("dataList") != null ){
				syncProductRecommendations = (List) productRecommendationMap.get("dataList");
			}
		}else{
			resultMap.put("success", false);
			resultMap.put("message", productRecommendationMap.get("message"));
			return resultMap;
		}

		/** 最终组装 */
		// 样板房所属空间信息
		syncDesignTemplet.setSyncSpaceCommon(syncSpaceCommon);
		// 样板房产品信息
		syncDesignTemplet.setSyncDesignTempletProducts(syncDesignTempletProducts);
		// 样板房推荐产品信息
		syncDesignTemplet.setSyncProductRecommendations(syncProductRecommendations);
		resultMap.put("obj",syncDesignTemplet);
		return resultMap;
	}

	/**
	 * 组装空间信息
	 * @param spaceCommonId
	 * @return
	 */
	@Override
	public Map<String,Object> assembleSpaceCommon(Integer spaceCommonId,LoginUser loginUser){
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("success",true);
		logger.info("开始组装空间数据。。。。spaceCommonId = " + spaceCommonId);
		if( spaceCommonId == null ){
			logger.info("spaceCommonId不能为空！");
			resultMap.put("success",false);
			resultMap.put("message","spaceCommonId不能为空！");
			return resultMap;
		}
		// 组装空间
		SyncSpaceCommon syncSpaceCommon = null;
		Map<String,Object> syncSpaceCommonMap = spaceCommonCollect(spaceCommonId, loginUser);
		if( (Boolean)syncSpaceCommonMap.get("success") ){
			syncSpaceCommon = (SyncSpaceCommon) syncSpaceCommonMap.get("obj");
		}else{
			logger.info(syncSpaceCommonMap.get("message"));
			resultMap.put("success",false);
			resultMap.put("message",syncSpaceCommonMap.get("message"));
			return resultMap;
		}

//		jsonObject.accumulate("obj",syncSpaceCommon);
		resultMap.put("obj",syncSpaceCommon);
		return resultMap;
	}

	/**
	 * 组装产品信息
	 * @param productId
	 * @return
	 */
	@Override
	public Map<String,Object> assembleProduct(Integer productId,LoginUser loginUser){
		logger.info("开始组装产品数据。。。。productId = " + productId);
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		if( productId == null ){
			logger.info("productId不能为空！");
			resultMap.put("success",false);
			resultMap.put("message","productId不能为空！");
			return resultMap;
		}
		SyncBaseProduct syncBaseProduct = null;
		Map<String,Object> syncBaseProductMap = baseProductCollect(productId, loginUser);
		if( (Boolean)syncBaseProductMap.get("success") ){
			syncBaseProduct = (SyncBaseProduct) syncBaseProductMap.get("obj");
		}else{
			logger.info(syncBaseProductMap.get("message")+"productId = " + productId);
			resultMap.put("success",false);
			resultMap.put("message",syncBaseProductMap.get("message"));
			return resultMap;
		}

		resultMap.put("obj",syncBaseProduct);
		return resultMap;
	}

	/**
	 * 组装样板房中产品
	 * @param designTempletId
	 * @return
	 */
	@Override
	public Map<String,Object> assembleDesignTempletProduct(Integer designTempletId,LoginUser loginUser){
		//是否给客户端推送同步信息
		Session webSocketSession = null;
		if(CommonServer.clientMap.containsKey(loginUser.getId())){
			webSocketSession = CommonServer.clientMap.get(loginUser.getId());
		}
		CommonServer.sendMessage(webSocketSession, "开始组装样板房中的产品\r\n");
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		if( designTempletId == null ){
			logger.info("designTempletId不能为空！designTempletId = " + designTempletId);
			resultMap.put("success",false);
			resultMap.put("message","designTempletId不能为空！");
			return resultMap;
		}
		// 获取样板房与产品的关联信息
		DesignTempletProductSearch designTempletProductSearch = new DesignTempletProductSearch();
		designTempletProductSearch.setDesignTempletId(designTempletId);
		designTempletProductSearch.setIsDeleted(0);
		List<DesignTempletProduct> designTempletProducts = templetProductService.getList(designTempletProductSearch);
		logger.info("样板房关联产品数量：" + designTempletProducts.size());
		if( designTempletProducts != null && designTempletProducts.size() > 0 ){
			CommonServer.sendMessage(webSocketSession, "关联产品数量【"+designTempletProducts.size()+"】\r\n");
			/** 最终组装 */
			SyncDesignTempletProduct syncDesignTempletProduct = null;
			List<SyncDesignTempletProduct> syncDesignTempletProducts = new ArrayList<>();
			Map<String,Object> productMap = null;
			int index = 1;
			for( DesignTempletProduct designTempletProduct : designTempletProducts ){
				CommonServer.sendMessage(webSocketSession, "开始组装第【"+index+"】个产品：");
				logger.info("组装样板房关联产品。productId="+designTempletProduct.getProductId());
				syncDesignTempletProduct = new SyncDesignTempletProduct();
				// 关联数据
				templetProductService.sysSave(designTempletProduct,loginUser);
				syncDesignTempletProduct.setDesignTempletProduct(designTempletProduct);
				// 产品信息
				productMap = assembleProduct(designTempletProduct.getProductId(),loginUser);
				if( (Boolean)productMap.get("success") ){
					syncDesignTempletProduct.setSyncBaseProduct((SyncBaseProduct) productMap.get("obj"));
					syncDesignTempletProducts.add(syncDesignTempletProduct);
				}else{
					resultMap.put("success",false);
					resultMap.put("message",productMap.get("message"));
					return resultMap;
				}
				index++;
			}
			resultMap.put("dataList", syncDesignTempletProducts);
		}else{
			logger.info("该样板房内没有产品！designTempletId = " + designTempletId);
			resultMap.put("success", false);
			resultMap.put("message", "该样板房内没有产品！");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * 组装样板房产品推荐
	 * @param designTempletId
	 * @return
	 */
	@Override
	public Map<String,Object> assembleProductRecommendation(Integer designTempletId,LoginUser loginUser){
		//是否给客户端推送同步信息
		Session webSocketSession = null;
		if(CommonServer.clientMap.containsKey(loginUser.getId())){
			webSocketSession = CommonServer.clientMap.get(loginUser.getId());
		}
		CommonServer.sendMessage(webSocketSession, "开始组装样板房产品推荐信息\r\n");
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		if( saveRecommendations ) {
			if (designTempletId == null) {
				logger.info("designTempletId不能为空！designTempletId = " + designTempletId);
				resultMap.put("success",false);
				resultMap.put("message","designTempletId不能为空！");
				return resultMap;
			}
			// 获取样板房产品推荐信息
			ProductRecommendationSearch search = new ProductRecommendationSearch();
			search.setDesignTempletId(designTempletId);
			search.setIsDeleted(0);
			List<ProductRecommendation> productRecommendations = productRecommendationService.getList(search);
			if (productRecommendations != null && productRecommendations.size() > 0) {
				CommonServer.sendMessage(webSocketSession, "共有【"+productRecommendations.size()+"】条推荐信息\r\n");
				List<SyncProductRecommendation> syncProductRecommendations = new ArrayList<>();
				SyncProductRecommendation syncProductRecommendation = null;
				Map<String,Object> productMap = new HashMap<>();
				int index = 0;
				for (ProductRecommendation productRecommendation : productRecommendations) {
					CommonServer.sendMessage(webSocketSession, "开始组装第【"+index+"】条推荐信息：");
					productRecommendationService.sysSave(productRecommendation,loginUser);
					syncProductRecommendation = new SyncProductRecommendation();
					syncProductRecommendation.setProductRecommendation(productRecommendation);
					// 获取推荐的产品信息
					productMap = assembleProduct(productRecommendation.getProductId(),loginUser);
					if ((Boolean)productMap.get("success")) {
						syncProductRecommendation.setSyncBaseProduct((SyncBaseProduct) productMap.get("obj"));
						syncProductRecommendations.add(syncProductRecommendation);
					} else {
						resultMap.put("success",false);
						resultMap.put("message",productMap.get("message"));
						return resultMap;
					}
					index++;
				}
				resultMap.put("dataList",syncProductRecommendations);
			} else {
				logger.info("该样板房没有推荐产品！designTempletId = " + designTempletId);
				resultMap.put("success",false);
				resultMap.put("message","该样板房["+designTempletId+"]没有推荐产品！");
				return resultMap;
			}
		}
		return resultMap;
	}

	public Map<String, String> getSmallPicId(String fileDesc){
		Map<String, String> map = new HashMap<String, String>();
		String[] strs = fileDesc.split(";");
		for (String str : strs) {
			if (str.split(":").length == 2) {
				map.put(str.split(":")[0].trim(), str.split(":")[1].trim());
			}
		}
		return map;
	}

	public ResEntity sysSaveResEntity(ResEntity resEntity,LoginUser loginUser){
		resEntity.setCreator(loginUser.getLoginName());
		resEntity.setModifier(loginUser.getLoginName());
		resEntity.setGmtCreate(new Date());
		resEntity.setGmtModified(new Date());
		return resEntity;
	}

	public static void main(String[] args) {
//		JSONObject jsonObject = new JSONObject();
//		List<SyncDesignTempletProduct> syncDesignTempletProducts = new ArrayList<>();
//		SyncDesignTempletProduct syncDesignTempletProduct = new SyncDesignTempletProduct();
//		DesignTempletProduct designTempletProduct1 = new DesignTempletProduct();
//		designTempletProduct1.setDesignTempletId(123);
//		designTempletProduct1.setProductId(222);
//		syncDesignTempletProduct.setDesignTempletProduct(designTempletProduct1);
//		SyncBaseProduct syncBaseProduct = new SyncBaseProduct();
//		ProductEntity baseProduct = new ProductEntity();
//		baseProduct.setProductName("aaaaaa");
//		syncBaseProduct.setBaseProduct(baseProduct);
//		syncDesignTempletProduct.setSyncBaseProduct(syncBaseProduct);
//		syncDesignTempletProducts.add(syncDesignTempletProduct);
//		jsonObject.accumulate("dataList", syncDesignTempletProducts);
//
//		List<SyncDesignTempletProduct> dataList = (List<SyncDesignTempletProduct>)JSONArray.toCollection((JSONArray)jsonObject.get("dataList"),SyncDesignTempletProduct.class);
//		List < SyncDesignTempletProduct > a = (List<SyncDesignTempletProduct>) jsonObject.get("dataList");
//		//////System.out.println(a.get(0).getSyncBaseProduct().getBaseProduct().getProductName());

//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-svr-mybatis.xml");
//		ClientDataService client = (ClientDataService) applicationContext.getBean("clientDataService");
////		SyncSpaceCommon syncSpaceCommon = client.spaceCommonCollect(2245);
//		SyncDesignTemplet syncDesignTemplet = client.designTempletCollect(1092);
//		JsonConfig config = new JsonConfig();
//		config.setJsonPropertyFilter(new PropertyFilter() {
//			@Override
//			public boolean apply(Object arg0, String arg1, Object arg2) {
//				if (arg1.equals("spaceCommon")) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//		});
//
//		JSONObject jsonObject = new JSONObject();
//		syncSpaceCommon.setView3dPicEntity(null);
//		syncSpaceCommon.setPicEntity(null);
//		syncSpaceCommon.setCadPicEntity(null);
//		syncSpaceCommon.setSmallPicEntity(null);
//		syncSpaceCommon.setSmallView3dPicEntity(null);
//		syncSpaceCommon.setSmallViewPlanPicEntity(null);
//		syncSpaceCommon.setViewPlanPicEntity(null);
//		jsonObject.accumulate("obj",JSONObject.fromObject(syncDesignTemplet,config));

//		DesignTempletService service = (DesignTempletService) applicationContext.getBean("designTempletService");
//		ClientDataService client = (ClientDataService) applicationContext.getBean("clientDataService");
//		SyncDesignTemplet designTemplet = new SyncDesignTemplet();
//		SyncSpaceCommon syncSpaceCommon = client.spaceCommonCollect(2245);
//		syncSpaceCommon.setView3dPicEntity(null);
//		syncSpaceCommon.setPicEntity(null);
//		syncSpaceCommon.setCadPicEntity(null);
//		syncSpaceCommon.setSmallPicEntity(null);
//		syncSpaceCommon.setSmallView3dPicEntity(null);
//		syncSpaceCommon.setSmallViewPlanPicEntity(null);
//		syncSpaceCommon.setViewPlanPicEntity(null);
//		DesignTemplet dt = new DesignTemplet();
//		dt.setDesignCode("123123123");
//		dt.setDesignName("aaaaaaa");
//		DesignTemplet dt = service.get(1092);
//		designTemplet.setDesignTemplet(dt);
		ResEntity resEntity = new ResEntity();
		resEntity.setFile(constructDataHandler("D:\\nork\\resources\\home\\spaceCommon\\view3dPic\\682523_20160520174841672.jpg"));
		resEntity.setResFileName("682523_20160520174841672");
//		syncSpaceCommon.setViewPlanPicEntity(resEntity);
//		JSONObject jsonObject = JSONObject.fromObject(resEntity);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.accumulate("obj", syncSpaceCommon);
//		//////System.out.println(((SyncSpaceCommon) JSONObject.toBean((JSONObject) jsonObject.get("obj"), SyncSpaceCommon.class)).getSpaceCommon().getSpaceCode());

	}
}
