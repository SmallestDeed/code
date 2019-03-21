package com.nork.system.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.reflect.TypeToken;
import com.nork.common.exception.BizException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.pano.PanoramaUtil;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.JsonUtil;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.dao.DesignPlanMapper;
import com.nork.design.dao.DesignRenderRoamMapper;
import com.nork.design.model.*;
import com.nork.design.service.DesignPlanRecommendedService;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.home.service.SpaceCommonService;
import com.nork.pano.model.dto.SceneControlInfoDto;
import com.nork.pano.model.output.DesignPlanStoreReleaseDetailsVo;
import com.nork.pano.model.output.PanoramaDataVo;
import com.nork.pano.model.roam.Coordinate;
import com.nork.pano.model.roam.Roam;
import com.nork.pano.model.scene.PanoramaVo;
import com.nork.pano.model.scene.SingleSceneVo;
import com.nork.pano.service.PanoramaService;
import com.nork.pano.service.impl.PanoramaServiceImpl;
import com.nork.product.model.BaseCompany;
import com.nork.product.model.CompanyShop;
import com.nork.product.service.BaseCompanyService;
import com.nork.product.service.CompanyShopService;
import com.nork.render.model.HeadMessage;
import com.nork.render.model.RenderTypeCode;
import com.nork.render.model.ResRenderData;
import com.nork.system.model.*;
import com.nork.system.model.small.ResScreenPicData;
import com.nork.system.service.*;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.Constants;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.home.dao.SpaceCommonMapper;
import com.nork.home.model.SpaceCommon;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.dao.SysDictionaryMapper;
import com.nork.system.model.search.ResRenderPicSearch;

/**   
 * @Title: ResRenderPicServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:渲染图片资源库-渲染图片资源库ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-03-22 14:39:08
 * @version V1.0   
 */
@SuppressWarnings("all")
@Service("resRenderPicService")
public class ResRenderPicServiceImpl implements ResRenderPicService {
    private static Logger logger = Logger.getLogger(ResRenderPicServiceImpl.class);
    private static final String rederResPath = Utils.getPropertyName("app","app.resources.url","https://show.sanduspace.com");
	private static final Integer PLAN_MODULE_SOURCE = 1;//来自推荐方案模块的方案
	private static final Integer PLAN_SHOP_SOURCE = 2;//来自店铺方案列表的方案
	private static final Integer PLAN_USER_SOURCE = 3;//用户自身的方案（效果图方案）
	private ResRenderPicMapper resRenderPicMapper;
	@Autowired
	private DesignPlanMapper designPlanMapper;
	@Autowired
	private SpaceCommonService spaceCommonService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	 
	@Autowired
	public void setResRenderPicMapper(
			ResRenderPicMapper resRenderPicMapper) {
		this.resRenderPicMapper = resRenderPicMapper;
	}
	@Autowired
	private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	
	@Autowired
	private ResRenderVideoService resRenderVideoService;
	@Autowired
	private DesignRenderRoamMapper designRenderRoamMapper;
	@Autowired
	private ResDesignService resDesignService;
	@Autowired
	private DesignPlanRecommendedService designPlanRecommendedService;
	@Autowired
	private DesignPlanRenderSceneService designPlanRenderSceneService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private BaseCompanyService baseCompanyService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private CompanyShopService companyShopService;
	@Autowired
	private ResRenderPicService resRenderPicService;
	@Autowired
	private PanoramaService panoramaService;

	/**
	 * 新增数据
	 *
	 * @param resRenderPic
	 * @return  int 
	 */
	@Override
	public int add(ResRenderPic resRenderPic) {
	    if(null == resRenderPic || null == resRenderPic.getBusinessId() || resRenderPic.getBusinessId().intValue()<=0)
	        return 0;
	    DesignPlan   designPlan=designPlanMapper.selectByPrimaryKey(resRenderPic.getBusinessId());
	    if(designPlan == null){
	        logger.info("do not find object for id="+resRenderPic.getBusinessId());
	        return 0;
	    }
	    
	    resRenderPic.setDesignSceneId(designPlan.getDesignSceneId());
	    resRenderPic.setDesignPlanName(designPlan.getPlanName());
	    SpaceCommon spaceCommon = spaceCommonService.get(designPlan.getSpaceCommonId());
	    logger.info("spaceCommon="+(spaceCommon==null));
	    
	    if(spaceCommon!=null){
	        resRenderPic.setArea(spaceCommon.getSpaceAreas());//空间面积大小
	        SysDictionary query = new SysDictionary();
	        query.setType(Constants.SYS_DICTIONARY_TYPE_HOUSETYPE);
	        query.setValue(spaceCommon.getSpaceFunctionId());
	        SysDictionary sysDictionary = sysDictionaryService.getNameByType(query);
	        logger.info("sysDictionary="+(sysDictionary!=null ));
            if(sysDictionary!=null )
                resRenderPic.setSpaceType(sysDictionary.getName());//设置房屋空间类型
	    }
	    logger.info(designPlan.getDesignSceneId()+"_getSpaceType="+(resRenderPic.getSpaceType()));
	    resRenderPic.setCreateUserId(designPlan.getUserId());
		resRenderPicMapper.insertSelective(resRenderPic);
		logger.info(designPlan.getDesignSceneId()+"_resRenderPic.getId()="+resRenderPic.getId());
		return resRenderPic.getId();
	}

	
	/**
	 * 新增数据
	 *
	 * @param resRenderPic
	 * @return  int 
	 */
	@Override
	public int addNew(ResRenderPic resRenderPic) {
	    if(null == resRenderPic || null == resRenderPic.getPlanRecommendedId() || resRenderPic.getPlanRecommendedId().intValue()<=0)
	        return 0;
	    DesignPlanRecommended   designPlanRecommended = designPlanRecommendedServiceV2.get(resRenderPic.getPlanRecommendedId());
	    if(designPlanRecommended == null){
	        logger.info("do not find object for id="+resRenderPic.getPlanRecommendedId());
	        return 0;
	    }
//	    DesignPlan designPlan = designPlanMapper.selectByPrimaryKey(resRenderPic.getBusinessId());
//	    if(designPlan!= null){
//	        resRenderPic.setDesignSceneId(designPlan.getDesignSceneId());
//        }
	    resRenderPic.setDesignPlanName(designPlanRecommended.getPlanName());
	    SpaceCommon spaceCommon = spaceCommonService.get(designPlanRecommended.getSpaceCommonId());
	    logger.info("spaceCommon="+(spaceCommon==null));
	    
	    if(spaceCommon!=null){
	        resRenderPic.setArea(spaceCommon.getSpaceAreas());//空间面积大小
	        SysDictionary query = new SysDictionary();
	        query.setType(Constants.SYS_DICTIONARY_TYPE_HOUSETYPE);
	        query.setValue(spaceCommon.getSpaceFunctionId());
	        SysDictionary sysDictionary = sysDictionaryService.getNameByType(query);
	        logger.info("sysDictionary="+(sysDictionary!=null ));
            if(sysDictionary!=null )
                resRenderPic.setSpaceType(sysDictionary.getName());//设置房屋空间类型
	    }
	    logger.info("getSpaceType="+(resRenderPic.getSpaceType()));
	    resRenderPic.setCreateUserId(designPlanRecommended.getUserId());
		resRenderPicMapper.insertSelective(resRenderPic);
		logger.info("resRenderPic.getId()="+resRenderPic.getId());
		return resRenderPic.getId();
	}
	/**
	 *    更新数据
	 *
	 * @param resRenderPic
	 * @return  int 
	 */
	@Override
	public int update(ResRenderPic resRenderPic) {
		return resRenderPicMapper
				.updateByPrimaryKeySelective(resRenderPic);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return resRenderPicMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResRenderPic 
	 */
	@Override
	public ResRenderPic get(Integer id) {
		return resRenderPicMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  resRenderPic
	 * @return   List<ResRenderPic>
	 */
	@Override
	public List<ResRenderPic> getList(ResRenderPic resRenderPic) {
	    return resRenderPicMapper.selectList(resRenderPic);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  resRenderPic
	 * @return   int
	 */
	@Override
	public int getCount(ResRenderPicSearch resRenderPicSearch){
		return  resRenderPicMapper.selectCount(resRenderPicSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  resRenderPic
	 * @return   List<ResRenderPic>
	 */
	@Override
	public List<ResRenderPic> getPaginatedList(
			ResRenderPicSearch resRenderPicSearch) {
		return resRenderPicMapper.selectPaginatedList(resRenderPicSearch);
	}

	

	/**
	 * 其他
	 * 
	 */
	
     /**
      * 根据推荐方案Id和渲染类型获得最新渲染原图
      */
	@Override
	public ResRenderPic getResRenderPicByPlanRecommended(Integer planRecommendedId,Integer renderingType){
	if(planRecommendedId!=null){
		List<ResRenderPic> renderPics=null;
		renderPics=resRenderPicMapper.getResRenderPicByPlanRecommended(planRecommendedId,renderingType);
		if(renderPics!=null&&renderPics.size()>0){
			ResRenderPic renderPic=renderPics.get(0);
			return renderPic;
		}else{
			return new ResRenderPic();
		}
	}
		return new ResRenderPic();
	}

	/**
	 * 为了渲染列表查询所有的缩略图和封面图
	 */
	@Override
	public List<ResRenderPic> selectListByFileKeys(ResRenderPicQO resRenderPicQO) {
		return resRenderPicMapper.selectListByFileKeys(resRenderPicQO);
	}

    @Override
    public int getLatestSmallPic(ResRenderPic renderPic) {
//		if (renderPic == null || StringUtils.isEmpty(renderPic.getFileKey()) || renderPic.getBusinessId() == null  || renderPic.getBusinessId() == null){
//			return 0;
//		}
		if (renderPic == null || renderPic.getFileKeyList() == null  || renderPic.getFileKeyList().size() <= 0 || renderPic.getBusinessId() == null  || renderPic.getBusinessId() == null){
			return 0;
		}
		return resRenderPicMapper.getLatestSmallPicByBusi(renderPic);
    }

	@Override
	public List<ResRenderPic> getRenderResourceListByopInfo(Integer sourcePlanId, Integer templateId,
			Integer renderType) {
		return resRenderPicMapper.getRenderResourceListByopInfo(sourcePlanId, templateId, renderType);
	}

	/**
	 * 通过pid获取缩略图
	 * @param pid
	 * @return
	 */
	@Override
	public ResRenderPic selectOneByPid(Integer pid){
		// TODO Auto-generated method stub
		return resRenderPicMapper.selectOneByPid(pid);
	}


	@Override
	public void deletePicBySceneId(Integer sceneId) {
		if(sceneId == null || sceneId < 1){
			logger.error("------function:deletePicBySceneId(Integer sceneId)->sceneId == null || sceneId < 1");
			return;
		}
		resRenderPicMapper.deletePicBySceneId(sceneId);
	}

    @Override
    public void updateOrigPicBySmallPicId(ResRenderPic render) {
	resRenderPicMapper.updateOrigPicBySmallPicId(render);
    }

	@Override
	public int getTaskIdBySmallId(int smallId) {
		return resRenderPicMapper.getTaskIdBySmallId(smallId);
	}

	@Override
	public void updateSceneIdByTaskId(int sceneId, int taskId) {
		resRenderPicMapper.updateSceneIdByTaskId(sceneId, taskId);
	}


	@Override
	public String getQRCodeInfo(ResRenderPic resPic, Date dateDue) {
		String urlSuffix = "";
		
		// 参数验证/处理 ->start
		if(dateDue != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			urlSuffix = "dateDueStr=" + simpleDateFormat.format(dateDue);
		}
		// 参数验证/处理 ->end
		
		String picPath="";
		String code = "";
		if(resPic.getRenderingType() != null){
			if( RenderTypeCode.COMMON_720_LEVEL == resPic.getRenderingType() || RenderTypeCode.HD_720_LEVEL == resPic.getRenderingType() ){// 普通单图720
				ResRenderPic singleCode = resRenderPicMapper.get720SingleCode(resPic);//得到720单点分享需要的code
				if(singleCode != null && StringUtils.isNotEmpty(singleCode.getSysCode())){
					code = singleCode.getSysCode();
				}else{
					logger.error("ResRenderPic is null ,res_render_pic_id="+resPic.getId());
					return null;
				}
				picPath = "pages/vr720/vr720Single.htm?code="+code;
			}else if( RenderTypeCode.ROAM_720_LEVEL == resPic.getRenderingType() ){// 720漫游
				// 获取720漫游组信息，传过来的pid问截图ID。渲染图列表接口返回了缩略图列表和对应的截图ID
				DesignRenderRoam romanCode = resRenderPicMapper.get720RomanCode(resPic);
				if(romanCode != null && StringUtils.isNotEmpty(romanCode.getUuid())){
					code = romanCode.getUuid();
					picPath = "pages/vr720/vr720Roam.htm?code="+code;
				}else{
					logger.error("DesignRenderRoam is null ,res_render_pic_id="+resPic.getId());
					return null;
				}
			} else if(RenderTypeCode.COMMON_VIDEO == resPic.getRenderingType() || RenderTypeCode.HD_VIDEO == resPic.getRenderingType()){//普通视频
				ResRenderVideo resRenderVideo = new ResRenderVideo();
				resRenderVideo.setIsDeleted(0);
				resRenderVideo.setSysTaskPicId(resPic.getId());
				List<ResRenderVideo>videoList = resRenderVideoService.getList(resRenderVideo);
				if(videoList == null || videoList.size()<=0 ){
					return null;
				}else if(videoList != null && videoList.size() == 1 ){
					picPath = videoList.get(0).getVideoPath();
				}else{
					return null;
				}
			}else{
				picPath=resPic.getPicPath();
			}
		}else{
			/*取普通渲染图路径*/
			picPath=resPic.getPicPath();
		}
		if(StringUtils.isNotEmpty(picPath) && StringUtils.isNotBlank(urlSuffix)) {
			if(picPath.indexOf("?") != -1) {
				picPath = picPath + "&" + urlSuffix;
			}else {
				picPath = picPath + "?" + urlSuffix;
			}
		}
		return picPath;
	}

	@Override
	public String getQRpicPath(ResRenderPic resPic, Date dateDue){
		// 参数验证 ->start

		// 参数验证 ->end

		int renderingType = resPic.getRenderingType().intValue();
		String picPath = getQRCodeInfo(resPic, dateDue);
		if(resPic.getRenderingType() != null){
			if( RenderTypeCode.COMMON_720_LEVEL == renderingType || RenderTypeCode.HD_720_LEVEL == renderingType ){// 普通单图720
				picPath = Utils.getPropertyName("app","app.render.server.url","")
						+ Utils.getPropertyName("app","app.render.server.siteName","") + picPath;
//				picPath = Utils.getPropertyName("app","app.resources.url","")  + picPath;
			}else if( RenderTypeCode.ROAM_720_LEVEL == renderingType ){// 720漫游
					picPath = Utils.getPropertyName("app","app.render.server.url","")
							+ Utils.getPropertyName("app","app.render.server.siteName","") + picPath;
//					picPath = Utils.getPropertyName("app","app.resources.url","")  + picPath;
			} else if(RenderTypeCode.COMMON_VIDEO == renderingType){//普通视频
				picPath = Utils.getPropertyName("app","app.resources.url","")  + picPath;
			}else if(RenderTypeCode.HD_VIDEO == renderingType){//高清视频
				picPath = Utils.getPropertyName("app","app.resources.url","")  + picPath;
			}else{
				picPath=Utils.getValue("app.resources.url", "")+picPath;
			}
		}else{
			/*取普通渲染图路径*/
			picPath=Utils.getValue("app.resources.url", "")+picPath;
		}
		return picPath;
	}

	/**
	 * 通过推荐id 渲染图
	 * @param planRecommendedId
	 */
	@Override
	public void deletedByPlanRecommendedId(int planRecommendedId) {
		resRenderPicMapper.deletedByPlanRecommendedId(planRecommendedId);
	}
	
	@Override
    public List<ThumbData> getRenderPicIdsBySceneId(long sceneId) {
		if(sceneId < 1){
			logger.error("params error, 'sceneId' <1");
			return null;
		}

		List<ThumbData> screenIds = resRenderPicMapper.getRenderPicIdsBySceneId(sceneId);
        return screenIds;
    }


	@Override
	public int countRoamByFileKey(ResRenderPicSearch resRenderPicSearch) {
		return resRenderPicMapper.countRoamByFileKey(resRenderPicSearch);
	}


	@Override
	public int insertSelective(ResRenderPic resRenderPic) {
		resRenderPicMapper.insertSelective(resRenderPic);
		logger.error("resRenderPic.getId()="+resRenderPic.getId());
		return resRenderPic.getId();
	}
	/**
	 * 从自动渲染里面取720全景效果图
	 * @param resRenderPic
	 * @return
	 */
	@Override
	public List<ResRenderPic> selectListOfMobile(ResRenderPic resRenderPic){
		return resRenderPicMapper.selectListOfMobile(resRenderPic);
	}

	/**
	 * 从自动渲染里面通过pid取缩略图
	 * @param pid
	 * @return
	 */
	@Override
	public ResRenderPic selectOneByPidOfMobile(Integer pid){
		return resRenderPicMapper.selectOneByPidOfMobile(pid);
	}


	@Override
	public DesignRenderRoam get720RomanCode(ResRenderPic resRenderPic) {
		// TODO Auto-generated method stub
		return resRenderPicMapper.get720RomanCode(resRenderPic);
	}


	@Override
	public ResRenderPic get720SingleCode(ResRenderPic resRenderPic) {
		// TODO Auto-generated method stub
		return resRenderPicMapper.get720SingleCode(resRenderPic);//得到720单点分享需要的code
	}

	/**
	 * 根据 联盟方案素材发布ID 查找该方案内的720单点渲染图列表
	 */
	@Override
	public List<ResRenderPic> getListByUnionDesignPlanStoreReleaseId(Integer releaseId) {
	  if(releaseId == null ) {
	      return null;
	  }
	  return resRenderPicMapper.getListByUnionDesignPlanStoreReleaseId(releaseId);
	}

	/**
	 * 根据图片类型和方案副本获取图片
	 * @param picType
	 * @param taskId
	 * @return
	 */
	@Override
	public ResRenderPic getBytaskIdAndPicType(String picType, Integer taskId) {
		if (picType == null || taskId == null) {
			return null;
		}
		return resRenderPicMapper.getBytaskIdAndPicType(picType,taskId);
	}

	@Override
	public Map<String, List<ResRenderData>> getAllResRenderPic(Integer id, renderResEnum type) {
		Map<String, List<ResRenderData>> resRenderDataMap = new HashMap<>();
		//获取照片级资源
		//List<ResRenderData> photoRes = this.getPhotoRes(id, type);
		//resRenderDataMap.put("photo", photoRes);
		//获取视频资源
		//List<ResRenderData> videoRes = this.getVideoRes(id, type);
		//resRenderDataMap.put("video", videoRes);
		//获取单点720资源
		List<ResRenderData> single720Res = this.getSingle720Res(id, type);
		resRenderDataMap.put("single720", single720Res);
		//获取多点720资源
		List<ResRenderData> roam720Res = this.getRoam720Res(id, type);
		resRenderDataMap.put("roam720", roam720Res);

		return resRenderDataMap;
	}

	//获取照片级资源
	@SuppressWarnings("all")
	private List<ResRenderData> getPhotoRes(Integer id, renderResEnum type) {
		ResRenderPic resRenderPic = new ResRenderPic();
		List<String> fileKeys = new ArrayList<>();
		List<ResRenderData> resRenderDataList = new ArrayList<>();
		if (type == renderResEnum.designPlanRecommended) {
			resRenderPic.setPlanRecommendedId(id);
			fileKeys.add("design.designPlanRecommended.render.small.pic");
		} else if (type == renderResEnum.designPlanRenderScene) {
			resRenderPic.setDesignSceneId(id);
			fileKeys.add("design.designPlan.render.small.pic");
		} else {
			return null;
		}
		resRenderPic.setIsDeleted(0);
		resRenderPic.setOrder("gmt_create desc");
		resRenderPic.setRenderingType(1);

		resRenderPic.setFileKeyList(fileKeys);
		List<ResRenderPic> picList = this.getList(resRenderPic);
		if (picList != null && picList.size() > 0) {
			for (ResRenderPic res : picList) {
				ResRenderPic renderPic = this.get(res.getPid());
				ResRenderData resRenderData = new ResRenderData();
				resRenderData.setRenderingType(res.getRenderingType());
				resRenderData.setPicPath(this.getRenderPicInfo(renderPic));
				resRenderDataList.add(resRenderData);
			}
		}
		return resRenderDataList;


	}

	//获取视频资源
	private List<ResRenderData> getVideoRes(Integer id, renderResEnum type) {
		ResRenderPic resRenderPic = new ResRenderPic();
		List<String> fileKeys = new ArrayList<>();
		List<ResRenderData> resRenderDataList = new ArrayList<>();
		if (type == renderResEnum.designPlanRecommended) {
			resRenderPic.setPlanRecommendedId(id);
			fileKeys.add("design.designPlanRecommended.render.video.cover");
			fileKeys.add("design.designPlanRecommended.render.pic");
		} else if (type == renderResEnum.designPlanRenderScene) {
			resRenderPic.setDesignSceneId(id);
			fileKeys.add("design.designPlan.render.video.cover");
		} else {
			return null;
		}
		resRenderPic.setIsDeleted(0);
		resRenderPic.setOrder("gmt_create desc");
		resRenderPic.setRenderingType(6);

		resRenderPic.setFileKeyList(fileKeys);
		List<ResRenderPic> picList = this.getList(resRenderPic);
		if (picList != null && picList.size() > 0) {
			for (ResRenderPic res : picList) {
				ResRenderPic renderPic = this.get(res.getId());
				ResRenderData resRenderData = new ResRenderData();
				resRenderData.setRenderingType(renderPic.getRenderingType());
				resRenderData.setPicPath(this.getRenderPicInfo(renderPic));
				resRenderDataList.add(resRenderData);
			}
		}
		return resRenderDataList;


	}


	//获取单点720资源
	@SuppressWarnings("all")
	private List<ResRenderData> getSingle720Res(Integer id, renderResEnum type) {
		ResRenderPic resRenderPic = new ResRenderPic();
		List<String> fileKeys = new ArrayList<>();
		List<ResRenderData> resRenderDataList = new ArrayList<>();
		if (type == renderResEnum.designPlanRecommended) {
			resRenderPic.setPlanRecommendedId(id);
			fileKeys.add("design.designPlanRecommended.render.small.pic");
		} else if (type == renderResEnum.designPlanRenderScene) {
			resRenderPic.setDesignSceneId(id);
			fileKeys.add("design.designPlan.render.small.pic");
		} else {
			return null;
		}

		resRenderPic.setIsDeleted(0);
		resRenderPic.setOrder("gmt_create desc");
		resRenderPic.setRenderingType(4);
		resRenderPic.setFileKeyList(fileKeys);
		List<ResRenderPic> picList = this.getList(resRenderPic);
		if (picList != null && picList.size() > 0) {
			for (ResRenderPic res : picList) {
				ResRenderPic renderPic = this.get(res.getPid());
				ResRenderData resRenderData = new ResRenderData();
				resRenderData.setRenderingType(res.getRenderingType());
				resRenderData.setPicPath(this.getRenderPicInfo(renderPic));

				SingleSceneVo singleSceneVo = new SingleSceneVo();
				//获取单点720缩略图和原图
				singleSceneVo.setThumbnailPicPath(res.getPicPath());
				singleSceneVo.setPicPath(renderPic.getPicPath());
				Integer isShear  = PanoramaServiceImpl.getIsShearByFilePath(renderPic.getPicPath());
				singleSceneVo.setIsShear(isShear);

				// 获取页面需要显示的业务信息（用户昵称、企业LOGO、企业名称
				PanoramaVo panoramaVo = new PanoramaVo();
				PanoramaVo newPanoramaVo = getUserInfoDetail(id, panoramaVo, type);
				singleSceneVo.setPanoramaVo(newPanoramaVo);

				resRenderData.setSingleSceneVo(singleSceneVo);
				resRenderDataList.add(resRenderData);
			}
		}
		return resRenderDataList;


	}


	//获取多点720资源
	@SuppressWarnings("all")
	private List<ResRenderData> getRoam720Res(Integer id, renderResEnum type) {
		ResRenderPic resRenderPic = new ResRenderPic();
		List<String> fileKeys = new ArrayList<>();
		List<ResRenderData> resRenderDataList = new ArrayList<>();
	/*	DesignPlanRecommended planRecommended = null;
		DesignPlanRenderScene planRenderScene = null;
		SpaceCommon spaceCommon = null;
		Integer groupPrimaryId = null;*/
		if (type == renderResEnum.designPlanRecommended) {
			resRenderPic.setPlanRecommendedId(id);
			fileKeys.add("design.designPlanRecommended.render.small.pic");
		/*	planRecommended = designPlanRecommendedService.get(id);
			groupPrimaryId = planRecommended.getGroupPrimaryId();
			spaceCommon = spaceCommonService.get(planRecommended.getSpaceCommonId());*/
		} else if (type == renderResEnum.designPlanRenderScene) {
			resRenderPic.setDesignSceneId(id);
			fileKeys.add("design.designPlan.render.small.pic");
			/*planRenderScene = designPlanRenderSceneService.get(id);
			groupPrimaryId = planRenderScene.getGroupPrimaryId();
			spaceCommon = spaceCommonService.get(planRenderScene.getSpaceCommonId());*/
		} else {
			return null;
		}

		resRenderPic.setIsDeleted(0);
		resRenderPic.setOrder("gmt_create desc");
		resRenderPic.setRenderingType(8);

		resRenderPic.setFileKeyList(fileKeys);
		List<ResRenderPic> picList = this.getList(resRenderPic);
		if (picList != null && picList.size() > 0) {
			for (ResRenderPic res : picList) {
				ResRenderPic renderPic = null;
				if (type == renderResEnum.designPlanRecommended) {
					renderPic = this.get(res.getPid());
				} else if (type == renderResEnum.designPlanRenderScene) {
					renderPic = this.get(res.getSysTaskPicId());
				} else {
					return null;
				}


				ResRenderData resRenderData = new ResRenderData();
				resRenderData.setRenderingType(res.getRenderingType());
				resRenderData.setPicPath(this.getRenderPicInfo(renderPic));
				SingleSceneVo singleSceneVo = new SingleSceneVo();
				//获取多点720的渲染截图
				singleSceneVo.setPicPath(renderPic.getPicPath());
				singleSceneVo.setThumbnailPicPath(res.getPicPath());

				//获取多点720的热点位置
				List<Roam> roams = getRoams(resRenderData);
				if (null != roams && 0 <roams.size()) {
					singleSceneVo.setRoamList(roams);
				} else {
					//推荐方案720多点容错处理xiaoxc_20180815
					//场景：多点没有配置文件无法打开720多点图片，容错显示单个图片
					if (type == renderResEnum.designPlanRecommended) {
						String picPath = resRenderPicMapper.getRenderRoamPicByRecommendedPlanId(id);
						if (!StringUtils.isEmpty(picPath)) {
							singleSceneVo.setPicPath(picPath);
							Integer isShear  = PanoramaServiceImpl.getIsShearByFilePath(renderPic.getPicPath());
							singleSceneVo.setIsShear(isShear);
						}
					}
				}

				// 获取页面需要显示的业务信息（用户昵称、企业LOGO、企业名称
				PanoramaVo panoramaVo = new PanoramaVo();
				PanoramaVo newPanoramaVo = getUserInfoDetail(id, panoramaVo, type);
				singleSceneVo.setPanoramaVo(newPanoramaVo);


				resRenderData.setSingleSceneVo(singleSceneVo);

			/*	if (type == renderResEnum.designPlanRecommended) {
					resRenderData.setPlanRecommendedId(id);
				}else{

				}
				resRenderData.setGroupPrimaryId(groupPrimaryId);
				resRenderData.setSpaceFunctionId(spaceCommon.getSpaceFunctionId());*/
				resRenderDataList.add(resRenderData);
			}
		}

		return resRenderDataList;
	}

	private String getRenderPicInfo(ResRenderPic resPic) {
		String picPath;
		if (resPic.getRenderingType() != null) {
			if (RenderTypeCode.COMMON_720_LEVEL == resPic.getRenderingType() || RenderTypeCode.HD_720_LEVEL == resPic.getRenderingType()) {// 普通单图720
				ResRenderPic singleCode = resRenderPicMapper.get720SingleCode(resPic);//得到720单点分享需要的code
				if (singleCode != null && org.apache.commons.lang3.StringUtils.isNotEmpty(singleCode.getSysCode())) {
					picPath = singleCode.getSysCode();
				} else {
					return null;
				}

			} else if (RenderTypeCode.ROAM_720_LEVEL == resPic.getRenderingType()) {// 720漫游
				// 获取720漫游组信息，传过来的pid问截图ID。渲染图列表接口返回了缩略图列表和对应的截图ID
				DesignRenderRoam romanCode = resRenderPicMapper.get720RomanCode(resPic);
				if (romanCode != null && org.apache.commons.lang3.StringUtils.isNotEmpty(romanCode.getUuid())) {
					picPath = romanCode.getUuid();
				} else {
					return null;
				}
			} else if (RenderTypeCode.COMMON_VIDEO == resPic.getRenderingType() || RenderTypeCode.HD_VIDEO == resPic.getRenderingType()) {//普通视频
				ResRenderVideo resRenderVideo = new ResRenderVideo();
				resRenderVideo.setIsDeleted(0);
				resRenderVideo.setSysTaskPicId(resPic.getId());
				List<ResRenderVideo> videoList = resRenderVideoService.getList(resRenderVideo);
				if (videoList == null || videoList.size() <= 0) {
					return null;
				} else if (videoList != null && videoList.size() == 1) {
					picPath = videoList.get(0).getVideoPath();
				} else {
					return null;
				}
			} else {
				picPath = resPic.getPicPath();
			}
		} else {
            /*取普通渲染图路径*/
			picPath = resPic.getPicPath();
		}
		return picPath;
	}

	private List<Roam> getRoams(ResRenderData resRenderData) {
		List<Roam> roamList = null;
		DesignRenderRoam designRenderRoam = designRenderRoamMapper.selectByUUID(resRenderData.getPicPath());
		if (designRenderRoam != null) {
			// 获取多720图片之间的关系信息
			String roamConfigContext = "";
			Integer roamConfigId = designRenderRoam.getRoamConfig();
			if (roamConfigId != null && roamConfigId.intValue() > 0) {
				ResDesign roamConfig = resDesignService.get(roamConfigId);
				if (roamConfig != null) {

					String filePath = rederResPath + roamConfig.getFilePath();
					roamConfigContext = FileUploadUtils.getJsonByInternet(filePath);
				}
			}
			logger.error("roamConfigContext = " + roamConfigContext);
			if (StringUtils.isNotBlank(roamConfigContext)) {
				JSONArray jsonArray = JSONArray.fromObject(roamConfigContext);
				roamList = JSONArray.toList(jsonArray, Roam.class);
//				roamList = JsonUtil.fromJson(roamConfigContext, new TypeToken<Roam>() {}.getType());
			}
		}
		List<Roam> roams = new ArrayList<>();
		if (roamList != null && roamList.size() > 0) {

			for (Roam roam : roamList) {
				Integer resId = roam.getFieldName();
				ResRenderPic r = this.get(resId);
				List<Coordinate> coordinateList = new ArrayList<>();
				roam.setFieldNamePath(r.getPicPath());
				Integer isShear  = PanoramaServiceImpl.getIsShearByFilePath(r.getPicPath());
				roam.setIsShear(isShear);
				for (Roam roam1 : roamList) {
					Integer resId1 = roam1.getFieldName();
					ResRenderPic r1 = this.get(resId1);

					Coordinate coordinate = new Coordinate();
					if (roam1.getFieldName() == roam.getFieldName()) {
						continue;
					}
					coordinate.setAth(PanoramaUtil.getAth(roam, roam1));
					coordinate.setAtv(PanoramaUtil.getAtv(roam, roam1));
					coordinate.setTargetFieldName(roam1.getFieldName());
					coordinate.setTargetFieldNamePath(r1.getPicPath());

					coordinateList.add(coordinate);
				}
				roam.setCoordinateList(coordinateList);
				roams.add(roam);
			}
		}
		return roams;
	}


	private PanoramaVo getUserInfoDetail(Integer id, PanoramaVo panoramaVo, renderResEnum type) {
		if (type == renderResEnum.designPlanRecommended) {
			DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(id);
			panoramaVo = getPanoramaVoByRecommendedId(panoramaVo, designPlanRecommended);
		} else if (type == renderResEnum.designPlanRenderScene) {
			DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(id);
			panoramaVo = getPanoramaVoBySceneId(panoramaVo, designPlanRenderScene);
		} else {
			return null;
		}

		return panoramaVo;
	}

	@SuppressWarnings("all")
	private PanoramaVo getPanoramaVoByRecommendedId(PanoramaVo panoramaVo, DesignPlanRecommended designPlanRecommended) {
		panoramaVo.setUserId(designPlanRecommended.getUserId());
		Integer userId = designPlanRecommended.getUserId();
		SysUser sysUser = sysUserService.get(userId);
		if (sysUser != null) {
			panoramaVo.setUserId(sysUser.getId());
			panoramaVo.setUserType(sysUser.getUserType());
			panoramaVo.setUserName(sysUser.getUserName());
			//如果是经销商账号 就取公司的Pid
			if (sysUser.getUserType().intValue() == 3) {
				// 获取用户公司
				Integer companyId = sysUser.getBusinessAdministrationId();
				BaseCompany company = baseCompanyService.get(companyId);
				if (company.getPid().intValue() > 0) {
					BaseCompany baseCompany = baseCompanyService.get(company.getPid());
					if (baseCompany != null) {
						panoramaVo.setCompanyName(baseCompany.getCompanyName());

						// 获取用户公司LOGO
						if (baseCompany.getCompanyLogo() != null) {
							ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
							if (resPic != null) {
								panoramaVo.setCompanyLogoPath(resPic.getPicPath());
							}
						}
					}
				}

			} else {
				// 获取用户公司
				Integer companyId = sysUser.getBusinessAdministrationId();
				BaseCompany baseCompany = baseCompanyService.get(companyId);
				if (baseCompany != null) {
					panoramaVo.setCompanyName(baseCompany.getCompanyName());
					// 获取用户公司LOGO
					if (baseCompany.getCompanyLogo() != null) {
						ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
						if (resPic != null) {
							panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
						}
					}
				}
			}
		}
		// 获取房型类型
		if (designPlanRecommended.getSpaceCommonId() != null) {
			SpaceCommon spaceCommon = spaceCommonService.get(designPlanRecommended.getSpaceCommonId());
			if (spaceCommon != null && spaceCommon.getSpaceFunctionId() != null) {
				SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType", spaceCommon.getSpaceFunctionId());
				panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
			}
		}
		return panoramaVo;
	}


	/**
	 * 根据场景ID获取全景vo信息
	 * @param panoramaVo
	 * @param designPlanRenderScene
	 * @return
	 */
	@SuppressWarnings("all")
	private PanoramaVo getPanoramaVoBySceneId(PanoramaVo panoramaVo, DesignPlanRenderScene designPlanRenderScene) {
		panoramaVo.setUserId(designPlanRenderScene.getUserId());
		Integer userId = designPlanRenderScene.getUserId();
		SysUser sysUser = sysUserService.get(userId);
		if (sysUser != null) {
			panoramaVo.setUserId(sysUser.getId());
			panoramaVo.setUserType(sysUser.getUserType());
			panoramaVo.setUserName(StringUtils.isNotBlank(sysUser.getUserName()) ? sysUser.getUserName() : sysUser.getNickName());
			//如果是经销商账号 就取公司的Pid
			if (sysUser.getUserType().intValue() == 3) {
				// 获取用户公司
				Integer companyId = sysUser.getBusinessAdministrationId();
				BaseCompany company = baseCompanyService.get(companyId);
				if (company.getPid().intValue() > 0) {
					BaseCompany baseCompany = baseCompanyService.get(company.getPid());
					if (baseCompany != null) {
						panoramaVo.setCompanyName(baseCompany.getCompanyName());
						// 获取用户公司LOGO
						if (baseCompany.getCompanyLogo() != null) {
							ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
							if (resPic != null) {
								panoramaVo.setCompanyLogoPath(resPic.getPicPath());
							}
						}
					}
				}

			} else {
				// 获取用户公司
				Integer companyId = sysUser.getBusinessAdministrationId();
				BaseCompany baseCompany = baseCompanyService.get(companyId);
				if (baseCompany != null) {
					panoramaVo.setCompanyName(baseCompany.getCompanyName());
					// 获取用户公司LOGO
					if (baseCompany.getCompanyLogo() != null) {
						ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
						if (resPic != null) {
							panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
						}
					}
				}
			}
		}
		// 获取房型类型
		if (designPlanRenderScene.getSpaceCommonId() != null) {
			SpaceCommon spaceCommon = spaceCommonService.get(designPlanRenderScene.getSpaceCommonId());
			if (spaceCommon != null && spaceCommon.getSpaceFunctionId() != null) {
				SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType", spaceCommon.getSpaceFunctionId());
				panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
			}
		}
		return panoramaVo;
	}

	@Override
	public HeadMessage getSceneHeadMessage(PanoramaVo panoramaVo, HeadMessage headMessage, Integer userId,String platformType) throws BizException {
		if(headMessage == null){
			headMessage = new HeadMessage();
		}
		Integer designPlanRecommendedId = panoramaVo.getDesignPlanRecommendedId();
		Integer designPlanRenderSceneId = panoramaVo.getDesignPlanRenderSceneId();
		Integer spaceCommonId = null;
		Integer coverPicId = null;
		Integer logoPicId = null;
		SceneControlInfoDto infoDto = null;
		DesignPlanRecommended designPlanRecommended = null;
		String planName = null;
		try {
			if (designPlanRecommendedId != null && designPlanRecommendedId.intValue() > 0) {
                designPlanRecommended = designPlanRecommendedServiceV2.get(designPlanRecommendedId);
                if (designPlanRecommended == null) {

                }
				planName = designPlanRecommended.getPlanName();
                // 获取方案来源，处理头像相关信息
                Integer planSource = panoramaVo.getPlanSource();
                if (planSource != null && planSource != 0) {
                    if (Objects.equals(PLAN_MODULE_SOURCE,planSource)) {
                        /**
                         * 显示企业第一个创建的发布到随选网的店铺信息
                         */
                        if(designPlanRecommended.getCompanyId() != null){
                            CompanyShop companyShop = null;
                            companyShop = companyShopService.getCompanyShopByCompanyId(designPlanRecommended.getCompanyId());
                            if(companyShop != null){
                                headMessage.setShopId(companyShop.getId());
                                headMessage.setHeadName(companyShop.getShopName());
                                headMessage.setHeadPhoneNumber(companyShop.getContactPhone());
                                logoPicId = companyShop.getLogoPicId();
                            }else{
                                //显示店铺信息
                                BaseCompany company = baseCompanyService.get(designPlanRecommended.getCompanyId());
                                if(company != null){
                                    headMessage.setHeadName(company.getCompanyName());
                                    headMessage.setHeadPhoneNumber(company.getCompanyPhone());
                                    logoPicId = company.getCompanyLogo();
                                }
                            }
                        }

                    } else if (Objects.equals(PLAN_SHOP_SOURCE,planSource)) {
						// 如果是店铺的推荐方案，则显示对应店铺的头像信息，并需要点击跳转对应店铺
						Integer shopId = panoramaVo.getShopId();
						if (shopId != null || shopId != 0) {
							CompanyShop shop = baseCompanyService.getHeadMessageOfShop(shopId);
							if (shop != null) {
								headMessage.setShopId(shop.getId());
								headMessage.setHeadName(shop.getShopName());
								headMessage.setHeadPhoneNumber(shop.getContactPhone());
								headMessage.setHeadPath(shop.getShopLogoPath());
							}
						}
					}else{
						SysUser sysUser = sysUserService.get(designPlanRecommended.getUserId());
						if(sysUser != null){
							headMessage.setHeadName(sysUser.getNickName());
							headMessage.setHeadPhoneNumber(sysUser.getMobile());
							headMessage.setHeadPath(sysUser.getHeadPic());
						}
					}
                    //增加参数，用于分享场景装进我家
                    headMessage.setGroupPrimaryId(designPlanRecommended.getGroupPrimaryId());
                    headMessage.setPlanRecommendedId(designPlanRecommendedId);
                    spaceCommonId = designPlanRecommended.getSpaceCommonId();
                    if(Objects.equals(Constants.RECOMMENDED_TYPE_ONE_KEY_PUB, designPlanRecommended.getRecommendedType()) && Objects.equals(1, designPlanRecommended.getChargeType())){
                        headMessage.setPlanPrice(designPlanRecommended.getPlanPrice());
                    }
                }
                infoDto = panoramaService.getSceneControlInfo(platformType,null, PanoramaDataVo.PlanSourceType.RECOMMENDED_PLAN
                        ,designPlanRecommendedId,userId,null);
				//封面图
				if(designPlanRecommended.getCoverPicId() != null && designPlanRecommended.getCoverPicId() > 0){
					coverPicId = designPlanRecommended.getCoverPicId();
				}else{
					//去查找一张图做为缩略图
					coverPicId = resRenderPicService.getSmallRenderPicByPlan(designPlanRecommendedId, DesignPlanStoreReleaseDetailsVo.PlanSourceType.RECOMMENDED_PLAN);
				}
            } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
                DesignPlanRenderScene planRenderScene = designPlanRenderSceneService.get(designPlanRenderSceneId);
				planName = planRenderScene.getPlanName();
                spaceCommonId = planRenderScene != null ? planRenderScene.getSpaceCommonId() : null;
                infoDto = panoramaService.getSceneControlInfo(platformType,null, PanoramaDataVo.PlanSourceType.DESIGNSCENE_PLAN
                        ,designPlanRenderSceneId,userId,null);
                //封面图
				if(planRenderScene.getCoverPicId() != null && planRenderScene.getCoverPicId() > 0){
					coverPicId = planRenderScene.getCoverPicId();
				}else{
					//查找一张渲染图做为缩略图
					coverPicId = resRenderPicService.getSmallRenderPicByPlan(designPlanRenderSceneId, DesignPlanStoreReleaseDetailsVo.PlanSourceType.DESIGNSCENE_PLAN);
				}
				SysUser sysUser = sysUserService.get(planRenderScene.getUserId());
				if(sysUser != null){
					headMessage.setHeadName(sysUser.getNickName());
					headMessage.setHeadPhoneNumber(sysUser.getMobile());
					if(sysUser.getPicId() != null && sysUser.getPicId() > 0){
						ResPic logoPic = resPicService.get(sysUser.getPicId());
						headMessage.setHeadPath(logoPic != null ? logoPic.getPicPath() : null);
					}
				}
            } else {
            }
		} catch (BizException e) {
			e.printStackTrace();
			throw new BizException(e.getMessage());
		}

		//logo
		if(logoPicId != null && logoPicId > 0){
			ResPic logoPic = resPicService.get(logoPicId);
			headMessage.setHeadPath(logoPic != null ? logoPic.getPicPath():null);
		}
		//空间信息
		if(spaceCommonId != null && spaceCommonId > 0){
			Integer functionId = spaceCommonService.getSpaceFunctionIdBySpaceCommonId(spaceCommonId);
			headMessage.setSpaceFunctionId(functionId);
			//空间类型
			SysDictionary houseTypeDictionary = sysDictionaryService.findOneByTypeAndValue("housetype",functionId);
			headMessage.setHouseTypeName(houseTypeDictionary != null ? houseTypeDictionary.getName() : null);
		}
		//封面图
		if(coverPicId != null){
			ResRenderPic coverPic = resRenderPicService.get(coverPicId);
			headMessage.setCoverPath(coverPic != null ? coverPic.getPicPath() : null);
		}
		//付费方案售价
		if(designPlanRecommendedId != null && designPlanRecommendedId > 0 && Objects.equals(1, designPlanRecommended.getChargeType())){
			headMessage.setPlanPrice(designPlanRecommended.getPlanPrice());
		}
		//控制场景功能按钮
		if(infoDto != null){
			headMessage.setViewControlType(infoDto.getViewControlType());
			headMessage.setCopyRightPermission(infoDto.getCopyRightPermission());
			headMessage.setHavePurchased(infoDto.getHavePurchased());
		}
		//方案名称
		headMessage.setPlanName(planName);
		return headMessage;
	}


	@Override
	public Integer getSmallRenderPicByPlan(Integer planId, Integer planSourceType) {
		if(planSourceType == null || planId == null){
			return null;
		}
		ResRenderPicQO renderPicQO = new ResRenderPicQO();
		switch (planSourceType){
			case DesignPlanStoreReleaseDetailsVo.PlanSourceType.RECOMMENDED_PLAN:
				renderPicQO.setPlanRecommendedId(planId);
				renderPicQO.setFileKeys(Arrays.asList(new String[]{"design.designPlanRecommended.render.small.pic","design.designPlanRecommended.render.video.cover"}));
				break;
			case DesignPlanStoreReleaseDetailsVo.PlanSourceType.DESIGNSCENE_PLAN:
				renderPicQO.setDesignSceneId(planId);
				renderPicQO.setFileKeys(Arrays.asList(new String[]{"design.designPlan.render.small.pic","design.designPlan.render.video.cover"}));
				break;
			case DesignPlanStoreReleaseDetailsVo.PlanSourceType.DESIGN_PLAN:
				renderPicQO.setBusinessId(planId);
				renderPicQO.setFileKeys(Arrays.asList(new String[]{"design.designPlan.render.small.pic","design.designPlan.render.video.cover"}));
				break;
		}
		renderPicQO.setOrders("rendering_type desc");
		List<ResRenderPic> picList = this.selectListByFileKeys(renderPicQO);
		if(CustomerListUtils.isNotEmpty(picList)){
			return picList.get(0).getId();
		}
		return null;
	}
}
