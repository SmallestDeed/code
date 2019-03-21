package com.nork.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.nork.base.model.BasePlatform;
import com.nork.base.service.BasePlatformService;
import com.nork.common.exception.BizException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.pano.util.ShearJob;
import com.nork.common.util.JsonObjectUtil;
import com.nork.common.util.Utils;
import com.nork.design.dao.DesignPlanMapper;
import com.nork.design.model.*;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.po.ResRenderPicPo;
import com.nork.system.model.query.ResRenderPicQuery;
import com.nork.system.model.vo.RenderPicOpenCVResult;
import com.nork.threadpool.ThreadPool;
import com.nork.threadpool.ThreadPoolManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nork.common.util.Constants;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.home.dao.SpaceCommonMapper;
import com.nork.home.model.SpaceCommon;
import com.nork.product.common.PlatformConstants;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.dao.SysDictionaryMapper;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.ResRenderPicSearch;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.ResRenderVideoService;

/**   
 * @Title: ResRenderPicServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:渲染图片资源库-渲染图片资源库ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-03-22 14:39:08
 * @version V1.0   
 */
@Service("resRenderPicService")
@EnableScheduling
public class ResRenderPicServiceImpl implements ResRenderPicService {
//    private static Logger logger = Logger.getLogger(ResRenderPicServiceImpl.class);

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ResRenderPicServiceImpl.class);

    private static final String CLASS_LOG_PREFIX = "方案渲染图片服务:";

	private ResRenderPicMapper resRenderPicMapper;
	@Autowired
	private DesignPlanMapper designPlanMapper;
	@Autowired
	private SpaceCommonMapper spaceCommonMapper;
	@Autowired
	private SysDictionaryMapper sysDictionaryMapper;
	@Autowired
	private ThreadPoolManager threadPoolManager;
	 
	@Autowired
	public void setResRenderPicMapper(
			ResRenderPicMapper resRenderPicMapper) {
		this.resRenderPicMapper = resRenderPicMapper;
	}
	@Autowired
	DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	
	@Autowired
	ResRenderVideoService resRenderVideoService;
	
	@Autowired
	private BasePlatformService basePlatformService;
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
	    SpaceCommon spaceCommon = spaceCommonMapper.selectByPrimaryKey(designPlan.getSpaceCommonId());
	    logger.info("spaceCommon="+(spaceCommon==null));
	    
	    if(spaceCommon!=null){
	        resRenderPic.setArea(spaceCommon.getSpaceAreas());//空间面积大小
	        SysDictionary query = new SysDictionary();
	        query.setType(Constants.SYS_DICTIONARY_TYPE_HOUSETYPE);
	        query.setValue(spaceCommon.getSpaceFunctionId());
	        SysDictionary sysDictionary = sysDictionaryMapper.getNameByType(query);
	        logger.info("sysDictionary="+(sysDictionary!=null ));
            if(sysDictionary!=null )
                resRenderPic.setSpaceType(sysDictionary.getName());//设置房屋空间类型
	    }
	    logger.info(designPlan.getDesignSceneId()+"_getSpaceType="+(resRenderPic.getSpaceType()));
	    resRenderPic.setCreateUserId(designPlan.getUserId());
	    //添加平台标识
	    if(resRenderPic.getPlatformId() == null) {
  	      BasePlatform basePlatform = basePlatformService.findOneByPlatformCode(PlatformConstants.PC_2B);//平台信息
          if(basePlatform == null) {
            logger.error("平台信息错误"); 
          }
          resRenderPic.setPlatformId(basePlatform.getId());
	    }
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
	    SpaceCommon spaceCommon = spaceCommonMapper.selectByPrimaryKey(designPlanRecommended.getSpaceCommonId());
	    logger.info("spaceCommon="+(spaceCommon==null));
	    
	    if(spaceCommon!=null){
	        resRenderPic.setArea(spaceCommon.getSpaceAreas());//空间面积大小
	        SysDictionary query = new SysDictionary();
	        query.setType(Constants.SYS_DICTIONARY_TYPE_HOUSETYPE);
	        query.setValue(spaceCommon.getSpaceFunctionId());
	        SysDictionary sysDictionary = sysDictionaryMapper.getNameByType(query);
	        logger.info("sysDictionary="+(sysDictionary!=null ));
            if(sysDictionary!=null )
                resRenderPic.setSpaceType(sysDictionary.getName());//设置房屋空间类型
	    }
	    logger.info("getSpaceType="+(resRenderPic.getSpaceType()));
	    resRenderPic.setCreateUserId(designPlanRecommended.getUserId());
	    //添加平台标识
	    if(resRenderPic.getPlatformId() == null) {
          BasePlatform basePlatform = basePlatformService.findOneByPlatformCode(PlatformConstants.PC_2B);//平台信息
          if(basePlatform == null) {
            logger.error("缺失PC端平台信息！"); 
          }else {
            resRenderPic.setPlatformId(basePlatform.getId());
          }
	    }
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
		return null;
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
	public String getQRCodeInfo(ResRenderPic resPic, LoginUser loginUser) {
//		resPic.setCreateUserId(loginUser.getId());

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
				String romanCode = resRenderPicMapper.get720RomanUuid(resPic.getId());
				if(StringUtils.isNotEmpty(romanCode)){
					picPath = "pages/vr720/vr720Roam.htm?code="+romanCode;
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
		return picPath;
	}

	@Override
	public String getQRpicPath(ResRenderPic resPic, LoginUser loginUser){
		int renderingType = resPic.getRenderingType().intValue();
		String picPath = getQRCodeInfo(resPic, loginUser);
		if(resPic.getRenderingType() != null){
			if( RenderTypeCode.COMMON_720_LEVEL == renderingType || RenderTypeCode.HD_720_LEVEL == renderingType ){// 普通单图720
				picPath = Utils.getPropertyName("app","app.server.url","")
						+ Utils.getPropertyName("app","app.server.siteName","") + picPath;
//				picPath = Utils.getPropertyName("app","app.resources.url","")  + picPath;
			}else if( RenderTypeCode.ROAM_720_LEVEL == renderingType ){// 720漫游
					picPath = Utils.getPropertyName("app","app.server.url","")
							+ Utils.getPropertyName("app","app.server.siteName","") + picPath;
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
  	    //添加平台标识
	    if(resRenderPic.getPlatformId() == null) {
          BasePlatform basePlatform = basePlatformService.findOneByPlatformCode(PlatformConstants.PC_2B);//平台信息
          if(basePlatform == null) {
            logger.error("缺失PC端平台信息！"); 
          }else {
            resRenderPic.setPlatformId(basePlatform.getId());
          }
  	    }
		resRenderPicMapper.insertSelective(resRenderPic);
		logger.error("resRenderPic.getId()="+resRenderPic.getId());
		return resRenderPic.getId();
	}

	@Override
	public List<ResRenderPicPo> findHistoryData(ResRenderPicQuery query) throws BizException {
		if (null == query) {
			return null;
		}
		//初始化长度
		if (query.getLimit() == 0) {
			query.setLimit(query.QUERY_RESRENDERPIC_LIMIT);
		}
		//查询方案渲染图片信息
		logger.info(CLASS_LOG_PREFIX + "正在查询方案渲染图片信息query:" + JsonObjectUtil.bean2Json(query));
		List<ResRenderPicPo> resRenderPicPoList;
		try {
			resRenderPicPoList = resRenderPicMapper.findHistoryData(query);
		} catch (Exception e) {
			logger.error(CLASS_LOG_PREFIX + "获取查询方案渲染图片信息失败,Exception:" + e + ",query:" + JsonObjectUtil.bean2Json(query));
			throw new BizException(ResRenderPicPo.EXCEPTION_CODE, CLASS_LOG_PREFIX + "获取查询方案渲染图片信息失败,Exception:" + e + ",query:" + JsonObjectUtil.bean2Json(query));
		}
		logger.info(CLASS_LOG_PREFIX + "查询方案渲染图片信息完成,List<ResRenderPicPo>长度:" + resRenderPicPoList.size());

		return resRenderPicPoList;
	}


	@Override
	public ResponseEnvelope repairRecommendedPlanV720HistoryData(String planIds) {

		//实例化查询条件对象
		ResRenderPicQuery query = new ResRenderPicQuery();

		//推荐方案ids
		if (!StringUtils.isEmpty(planIds)) {
			query.setRecommendedIdList(Arrays.asList(planIds.split(",")));
		}

		//渲染类型
		List<Integer> renderingTypeList = new ArrayList<>(2);
		renderingTypeList.add(RenderTypeCode.COMMON_720_LEVEL);
		renderingTypeList.add(RenderTypeCode.ROAM_720_LEVEL);
		query.setRenderingTypeList(renderingTypeList);

		//渲染图片类型
		List<String> picTypeList = new ArrayList<>(2);
		picTypeList.add(RenderTypeCode.RENDER_PIC_TIME_SCREENSHOT);
		picTypeList.add(RenderTypeCode.RENDER_PIC_TIME_THUMBNAIL);
		query.setPicTypeList(picTypeList);

		//响应结果对象
		RenderPicOpenCVResult result = new RenderPicOpenCVResult();
		//数据查询初始位
		int start = 0;
		//每次数据量
		int limit = ResRenderPicQuery.QUERY_RESRENDERPIC_LIMIT;
		//是否继续处理
		boolean isContinueHandler = true;

		logger.error("开始切割720图片。。。");
		while (isContinueHandler) {
			//数据查询初始位
			query.setStart(start);

			List<ResRenderPicPo> resRenderPicPoList;
			try {
				resRenderPicPoList = this.findHistoryData(query);
			} catch (BizException be) {
				logger.error(be.getMsg());
				return new ResponseEnvelope(false, be.getMessage());
			}
			if (resRenderPicPoList == null && 0 == resRenderPicPoList.size()) {
				return new ResponseEnvelope(true, "数据为空");
			}
			//递增start下标
			start = start +limit;
			//总条数
			result.setTotal(result.getTotal() + resRenderPicPoList.size());

			//数据不足指定数据量表示已查询出最后一条数据,终止循环
			if (resRenderPicPoList.size() < ResRenderPicQuery.QUERY_RESRENDERPIC_LIMIT) {
				isContinueHandler = false;
			}

			for (int i = 0; i < resRenderPicPoList.size(); i++) {
				ResRenderPicPo resPo = resRenderPicPoList.get(i);
				//需切割的目标图片文件
				String picPath = resPo.getPicPath();
				if (!org.apache.commons.lang3.StringUtils.isEmpty(picPath)) {
					//追加根目录
					String dbPicPath = Utils.getAbsolutePath(picPath, null);
					File picfile = new File(dbPicPath);
					//不存在跳过
					if (!picfile.exists()) {
						logger.error("全景图物理文件不存在！resId=" + resPo.getResRenderPicId() + ", picPath=" + dbPicPath);
						result.setFailedCount(result.getFailedCount() + 1);
						continue;
					}
					//是文件夹跳过
					if (picfile.exists() && picfile.isDirectory()) {
						result.setProcessedCount(result.getProcessedCount() + 1);
						continue;
					} else {
						//相对路径 已切割图片地址制作新的文件夹地址
						String renderPicDir = picPath.substring(0, picPath.lastIndexOf("."));
						//绝对路径 追加根目录
						String dbRenderPicDir = Utils.getAbsolutePath(renderPicDir+ "/", null);
						resPo.setPicPath(picPath.substring(0, picPath.lastIndexOf(".")));
						if (!org.apache.commons.lang3.StringUtils.isEmpty(dbRenderPicDir)) {
							File newFile = new File(dbRenderPicDir);
							// 不存在则创建文件夹
							if (!newFile.exists()) {
								newFile.mkdir();
							}
							try {
								logger.info("全景图切割dbPicPath = " + dbPicPath + ";dbRenderPicDir = " + dbRenderPicDir);
								ShearJob shearJob = new ShearJob(resRenderPicMapper, resPo, dbPicPath, dbRenderPicDir);
								ThreadPool threadPool = threadPoolManager.getThreadPool();
								threadPool.submit(shearJob);
							} catch (Exception e) {
								logger.error("全景图切割失败!resId=" + resPo.getResRenderPicId() + ", Exception:" + e);
								result.setFailedCount(result.getFailedCount() + 1);
								continue;
							}
						}

						logger.info("全景图新地址：" + dbRenderPicDir + picfile.getName());
					}
				} else {
					logger.error("渲染图地址为空！resId=" + resPo.getResRenderPicId());
					result.setFailedCount(result.getFailedCount() + 1);
					continue;
				}
			}
		}

		return new ResponseEnvelope(true, result);
	}

//	@Scheduled(fixedDelay = 1000*6)
	public void test(){
		System.out.println("=====================================================");
		System.out.println("=====================================================");
		System.out.println("=====================================================");
		System.out.println("=====================================================");
		System.out.println("=======================只会执行一次====================");
		System.out.println("=====================================================");
		System.out.println("=====================================================");
		System.out.println("=====================================================");
		System.out.println("=====================================================");
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

//	@Scheduled(fixedDelay = 1000*6)
	public void repair(){
		logger.error("=================开始批量切割老数据=================");
		int pageSize = 500;
		ResRenderPicQuery query = new ResRenderPicQuery();
		query.setLimit(pageSize);
		Integer totalCount = resRenderPicMapper.findHistoryCount(query);
		logger.error("需要切割的老数据总数：{}", totalCount);
		if( totalCount == null || totalCount.intValue() == 0 ){
			return;
		}
		int pageCount = totalCount / pageSize;
		if( totalCount%pageSize > 0 ){
			pageCount += 1;
		}
		logger.error("总页数：{}  每页处理数量：{}", pageCount, pageSize);

		// 分页查询
		for( int currentPage=0;currentPage<pageCount;currentPage++ ){
			/*if( currentPage > 0 ){
				try {
					logger.error("执行完第{}页，休息一下", currentPage-1);
					TimeUnit.SECONDS.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}*/
			logger.error("开始处理第{}页数据", (currentPage + 1));
			query.setStart(currentPage*pageSize);
			// 最后一页没有500数据，只查剩下的
			if( currentPage == pageCount - 1 ){
				query.setLimit(totalCount%pageSize);
			}
			List<ResRenderPicPo> list = resRenderPicMapper.findHistoryData(query);
			if( list == null || list.size() == 0 ){
				continue;
			}
			for(int i=0,listSize=list.size();i<listSize;i++){
				ResRenderPicPo resRenderPicPo = list.get(i);
				if( resRenderPicPo == null ){
					continue;
				}
				// 源文件路径
				String picPath = resRenderPicPo.getPicPath();
				String sourceFilePath = Utils.getAbsolutePath(picPath, null);
				File sourceFile = new File(sourceFilePath);
				// 源文件不存在或者已经是目录则跳出当前循环
				if( !sourceFile.exists() || sourceFile.isDirectory() ){
					logger.error("资源{}目录不存在，或者目录为文件夹", resRenderPicPo.getResRenderPicId());
					continue;
				}
				// 切割后文件夹路径
				resRenderPicPo.setPicPath(picPath.substring(0, picPath.lastIndexOf(".")));
				String targetDirPath = Utils.getAbsolutePath(picPath.substring(0, picPath.lastIndexOf(".")) + "/", null);
				File targetFile = new File(targetDirPath);
				if( !targetFile.exists() ){
					targetFile.mkdirs();
				}
				resRenderPicPo.setIndex("["+(currentPage + 1) + "-" + (i+1)+"]");
				ShearJob shearJob = new ShearJob(resRenderPicMapper, resRenderPicPo, sourceFilePath, targetDirPath);
				ThreadPool threadPool = threadPoolManager.getThreadPool();
				threadPool.submit(shearJob);
			}
			logger.error("=========================第{}页执行完了", currentPage + 1);
		}
	}

    /*public void test2(){
        ThreadPoolManager threadPoolManager = new ThreadPoolManager();
        ThreadPoolImpl threadPool = new ThreadPoolImpl();
        threadPool.init();
        String[] paths = new String[]{
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\1.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\2.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\3.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\4.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\5.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\6.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\7.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\8.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\9.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\10.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\11.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\12.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\13.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\14.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\15.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\16.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\17.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\18.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\19.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\20.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\21.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\22.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\23.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\24.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\25.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\26.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\27.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\28.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\29.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\30.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\31.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\32.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\33.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\34.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\35.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\36.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\37.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\38.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\39.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\40.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\41.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\42.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\43.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\44.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\45.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\46.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\47.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\48.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\49.jpg",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\50.jpg",
        };
        String outputPath = "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\";
        String[] outputPaths = new String[]{
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\a\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\b\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\c\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\d\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\e\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\f\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\g\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\h\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\i\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\j\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\k\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\l\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\m\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\n\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\o\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\p\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\q\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\r\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\s\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\t\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\u\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\v\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\w\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\x\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\y\\",
                "C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\test\\z\\",
        };
        System.out.println("开始切割时间：" + Utils.getCurrentDateTime());

        for( int j=0;j<outputPaths.length;j++ ){
            for( int i=0;i<paths.length;i++ ){
                ShearJob2 job = new ShearJob2((j+1)+"-"+(i+1), paths[i], outputPaths[j] + (i+1) + "\\");
                Future<Boolean> future = threadPool.submit(job);
            }
        }
    }*/
}
