package com.nork.design.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.cache.DesignTempletCacher;
import com.nork.design.dao.DesignTempletMapper;
import com.nork.design.model.DesignSpaceResult;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletPutawayState;
import com.nork.design.model.DesignTempletResult;
import com.nork.design.model.search.DesignTempletSearch;
import com.nork.design.service.DesignTempletService;
import com.nork.home.cache.SpaceCommonCacher;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysResLevelCfgService;

import net.sf.json.JSONObject;

/**
 * @Title: DesignTempletServiceImpl.java
 * @Package com.nork.design.service.impl
 * @Description:设计模块-设计方案样板房表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-07-05 14:47:35
 * @version V1.0
 */
@Service("designTempletService")
@Transactional
public class DesignTempletServiceImpl implements DesignTempletService {

	private static Logger logger = Logger
			.getLogger(DesignTempletServiceImpl.class);
	private DesignTempletMapper designTempletMapper;
	@Resource
	private ResPicService resPicService;
	@Resource
	private ResModelService resModelService;
	@Resource
	private ResFileService resFileService;
	@Resource
	private SpaceCommonService spaceCommonService;
	@Autowired
	private SysResLevelCfgService sysResLevelCfgService;
	@Autowired
	public void setDesignTempletMapper(DesignTempletMapper designTempletMapper) {
		this.designTempletMapper = designTempletMapper;
	}

	/**
	 * 新增数据
	 * 
	 * @param designTemplet
	 * @return int
	 */
	@Override
	public int add(DesignTemplet designTemplet) {
		designTempletMapper.insertSelective(designTemplet);
		return designTemplet.getId();
	}

	/**
	 * 更新数据
	 * 
	 * @param designTemplet
	 * @return int
	 */
	@Override
	public int update(DesignTemplet designTemplet) {
		return designTempletMapper.updateByPrimaryKeySelective(designTemplet);
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return designTempletMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 * 
	 * @param id
	 * @return DesignTemplet
	 */
	@Override
	public DesignTemplet get(Integer id) {
		return designTempletMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param designTemplet
	 * @return List<DesignTemplet>
	 */
	@Override
	public List<DesignTemplet> getList(DesignTemplet designTemplet) {
		return designTempletMapper.selectList(designTemplet);
	}

	/**
	 * 获取数据数量
	 * 
	 * @param designTemplet
	 * @return int
	 */
	@Override
	public int getCount(DesignTempletSearch designTempletSearch,int userId) {
	    
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId, Constants.RES_BIG_TYPE_NUM_TEMPLATE);//19代表样板房
        int valNum = Integer.valueOf(val);
        
        designTempletSearch.setLevelLimitCount(valNum);
		return designTempletMapper.selectCount(designTempletSearch);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param designTemplet
	 * @return List<DesignTemplet>
	 */
	@Override
	public List<DesignTemplet> getPaginatedList(
			DesignTempletSearch designTempletSearch,int userId) {
	    
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId, Constants.RES_BIG_TYPE_NUM_TEMPLATE);//19在数据字典中代表样板房
        int valNum = Integer.valueOf(val);
        designTempletSearch.setLevelLimitCount(valNum);
	    
		List<DesignTemplet> designTemplets = designTempletMapper
				.selectPaginatedList(designTempletSearch);

		/* 过滤逻辑->空间,样板房两个层级,下级没数据,不显示上级 */
		// for(int i=0;i>designTemplets.size();i++){
		// boolean flag=false;
		// List<Integer> spaceIds=new ArrayList<Integer>();
		// spaceIds.add(designTemplets.get(i).getSpaceCommonId());
		// List<Integer> templetIds=getTempletIdsBySpaceIds(spaceIds);
		// if(templetIds==null||templetIds.size()==0)
		// flag=true;
		// if(flag==true){
		// designTemplets.remove(i);
		// i--;
		// }
		// }
		/* 过滤逻辑->空间,样板房两个层级,下级没数据,不显示上级->end */
		return designTemplets;
	}

	public List<DesignTemplet> getListBySpaceIdsAndArea(List<String> spaceIds) {
		return designTempletMapper.getListBySpaceIdsAndArea(spaceIds);
	}

	/**
	 * 获取空间（样板房）数据
	 * 
	 * @param designTemplet
	 * @return int
	 */
	@Override
	public List<DesignSpaceResult> getSpaceList(DesignTemplet designTemplet) {
		return designTempletMapper.spaceList(designTemplet);
	}

	@Override
	public DesignSpaceResult getSpaceRender(Integer templetId) {
		return designTempletMapper.spaceRender(templetId);
	}

	@Override
	public DesignTempletResult getDesignList(Integer designId) {
		return designTempletMapper.designList(designId);
	}

	/**
	 * 样板房新增、修改需回填的信息表
	 * 
	 * @param designTemplet
	 */
	public void backfill(DesignTemplet designTemplet) {
		Integer designId = designTemplet.getId();
		// 回填空间布局图
		if (designTemplet.getPicId() != null && designTemplet.getPicId() > 0) {
			String picKey = "design.designTemplet.pic";
			resPicService.backFillResPic(designId, designTemplet.getPicId(),
					picKey, designTemplet.getDesignCode());
		}
		// 回填样板房cad图
		if (designTemplet.getCadPicId() != null
				&& designTemplet.getCadPicId() > 0) {
			String picKey = "design.designTemplet.pic";
			resPicService.backFillResPic(designId, designTemplet.getCadPicId(),
					picKey, designTemplet.getDesignCode());
		}
		// 效果图
		String effectPicIdStr = designTemplet.getEffectPic();
		if (StringUtils.isNotBlank(effectPicIdStr)) {
			String[] effectPicIds = effectPicIdStr.split(",");
			for (int i = 0; i < effectPicIds.length; i++) {
				if (StringUtils.isNotBlank(effectPicIds[i])) {
					String picKey = "design.designTemplet.piclist";
					resPicService.backFillResPic(designId,
							Utils.getIntValue(effectPicIds[i]), picKey,
							designTemplet.getDesignCode());
				}
			}
		}
		// 回填模型资源
		if (designTemplet.getModel3dId() != null
				&& designTemplet.getModel3dId() > 0) {
			String modelKey = "design.designTemplet.3dmodel";
			resModelService.backFillResModel(designId,
					designTemplet.getModel3dId(), modelKey);
		}
		if (designTemplet.getIpadModelU3dId() != null
				&& designTemplet.getIpadModelU3dId() > 0) {
			String modelKey = "design.designTemplet.u3dmodel.ipad";
			resModelService.backFillResModel(designId,
					designTemplet.getIpadModelU3dId(), modelKey);
		}
		if (designTemplet.getIosModelU3dId() != null
				&& designTemplet.getIosModelU3dId() > 0) {
			String modelKey = "design.designTemplet.u3dmodel.ios";
			resModelService.backFillResModel(designId,
					designTemplet.getIosModelU3dId(), modelKey);
		}
		if (designTemplet.getAndroidModelU3dId() != null
				&& designTemplet.getAndroidModelU3dId() > 0) {
			String modelKey = "design.designTemplet.u3dmodel.android";
			resModelService.backFillResModel(designId,
					designTemplet.getAndroidModelU3dId(), modelKey);
		}
		if (designTemplet.getMacBookpcModelU3dId() != null
				&& designTemplet.getMacBookpcModelU3dId() > 0) {
			String modelKey = "design.designTemplet.u3dmodel.macBook";
			resModelService.backFillResModel(designId,
					designTemplet.getMacBookpcModelU3dId(), modelKey);
		}
		if (designTemplet.getWebModelU3dId() != null
				&& designTemplet.getWebModelU3dId() > 0) {
			String modelKey = "design.designTemplet.u3dmodel.windows";
			resModelService.backFillResModel(designId,
					designTemplet.getWebModelU3dId(), modelKey);
		}
		if (designTemplet.getModelId() != null
				&& designTemplet.getModelId() > 0) {
			String modelKey = "design.designTemplet.model";
			resModelService.backFillResModel(designId,
					designTemplet.getModelId(), modelKey);
		}
		// 回填配置文件
		if (designTemplet.getConfigFileId() != null
				&& designTemplet.getConfigFileId() > 0) {
			String fileKey = "design.designTemplet.configFile";
			resFileService.backFillResFile(designId,
					designTemplet.getConfigFileId(), fileKey);
		}
		// 效果平面图
		String effectPlanIds = designTemplet.getEffectPlanIds();
		if (StringUtils.isNotBlank(effectPlanIds)) {
			String[] effectPlanIdList = effectPlanIds.split(",");
			for (int i = 0; i < effectPlanIdList.length; i++) {
				if (StringUtils.isNotBlank(effectPlanIdList[i])) {
					String picKey = "design.designTemplet.effectPlan";
					resPicService.backFillResPic(designId,
							Utils.getIntValue(effectPlanIdList[i]), picKey,
							designTemplet.getDesignCode());
				}
			}
		}
	}

	/**
	 * 样板房修改先清除资源回填信息
	 * 
	 * @param designTemplet
	 */
	public void clearBackfill(DesignTemplet designTemplet) {

		Integer designId = designTemplet.getId();
		// 空间布局图
		if (designTemplet.getPicId() != null && designTemplet.getPicId() > 0) {
			resPicService.clearBackfillResPic(designId,
					designTemplet.getPicId());
		}
		// 效果图
		String effectPicIdStr = designTemplet.getEffectPic();
		if (StringUtils.isNotBlank(effectPicIdStr)) {
			String[] effectPicIds = effectPicIdStr.split(",");
			for (int i = 0; i < effectPicIds.length; i++) {
				if (StringUtils.isNotBlank(effectPicIds[i])) {
					resPicService.clearBackfillResPic(designId,
							Utils.getIntValue(effectPicIds[i]));
				}
			}
		}
		// 模型资源
		if (designTemplet.getModel3dId() != null
				&& designTemplet.getModel3dId() > 0) {
			resModelService.clearBackfillResModel(designId,
					designTemplet.getModel3dId());
		}
		if (designTemplet.getIpadModelU3dId() != null
				&& designTemplet.getIpadModelU3dId() > 0) {
			resModelService.clearBackfillResModel(designId,
					designTemplet.getIpadModelU3dId());
		}
		if (designTemplet.getIosModelU3dId() != null
				&& designTemplet.getIosModelU3dId() > 0) {
			resModelService.clearBackfillResModel(designId,
					designTemplet.getIosModelU3dId());
		}
		if (designTemplet.getAndroidModelU3dId() != null
				&& designTemplet.getAndroidModelU3dId() > 0) {
			resModelService.clearBackfillResModel(designId,
					designTemplet.getAndroidModelU3dId());
		}
		if (designTemplet.getMacBookpcModelU3dId() != null
				&& designTemplet.getMacBookpcModelU3dId() > 0) {
			resModelService.clearBackfillResModel(designId,
					designTemplet.getMacBookpcModelU3dId());
		}
		if (designTemplet.getWebModelU3dId() != null
				&& designTemplet.getWebModelU3dId() > 0) {
			resModelService.clearBackfillResModel(designId,
					designTemplet.getWebModelU3dId());
		}
		if (designTemplet.getModelId() != null
				&& designTemplet.getModelId() > 0) {
			resModelService.clearBackfillResModel(designId,
					designTemplet.getModelId());
		}
		// 配置文件
		if (designTemplet.getConfigFileId() != null
				&& designTemplet.getConfigFileId() > 0) {
			resFileService.clearBackfillResFile(designId,
					designTemplet.getConfigFileId());
		}
	}

	@Override
	public List<String> findAllName() {
		return designTempletMapper.findAllName();
	}

	@Override
	public int findAllCode(DesignTempletSearch designTempletSearch) {
		return designTempletMapper.findAllCode(designTempletSearch);
	}

	@Override
	public List<DesignTemplet> getVacantRoomList(
			DesignTempletSearch designTempletSearch) {
		return designTempletMapper.selectVacantRoomList(designTempletSearch);
	}

	/**
	 * 检查样板房资源文件是否齐全
	 * 
	 * @param designTempletId
	 * @return
	 */
	public JSONObject checkResourceFile(Integer designTempletId) {
		JSONObject jsonObject = new JSONObject();
		if (designTempletId == null) {
			jsonObject.accumulate("success", false);
			jsonObject.accumulate("message", "designTempletId不能为空!");
			return jsonObject;
		}
		DesignTemplet designTemplet = designTempletMapper
				.selectByPrimaryKey(designTempletId);
		if (designTemplet == null) {
			jsonObject.accumulate("success", false);
			jsonObject.accumulate("message", "样板房不存在!designTempletId = "
					+ designTempletId);
			return jsonObject;
		}
		// 空间布局图
		Integer picId = designTemplet.getPicId();
		if (picId == null) {
			jsonObject.accumulate("success", false);
			jsonObject.accumulate("message",
					"【" + designTemplet.getDesignCode() + "】该样板房没有上传空间布局图!");
			return jsonObject;
		}
		ResPic pic = resPicService.get(picId);
		if (pic == null) {
			jsonObject.accumulate("success", false);
			jsonObject.accumulate("message",
					"【" + designTemplet.getDesignCode()
							+ "】该样板房的空间布局图在图片资源中没有找到!resPicId = " + picId);
			return jsonObject;
		}
		/*File picFile = new File(Tools.getRootPath(pic.getPicPath(),
				"/home/nork/resources_178") + "/" + pic.getPicPath());*/
		File picFile = new File(Utils.getAbsolutePath(pic.getPicPath(), null));
				
		if (!picFile.exists()) {
			jsonObject.accumulate("success", false);
			jsonObject.accumulate("message",
					"【" + designTemplet.getDesignCode()
							+ "】该样板房的空间布局图的物理文件没有找到!resPicId = " + picId);
			return jsonObject;
		}
		// 空间布局图缩略图
		String smallPicInfo = pic.getSmallPicInfo();
		if (StringUtils.isBlank(smallPicInfo)) {
			jsonObject.accumulate("success", false);
			jsonObject.accumulate("message",
					"【" + designTemplet.getDesignCode() + "】该样板房没有生成空间布局图缩略图!");
			return jsonObject;
		}
		// String[] smallPicArr = smallPicInfo.split(";");
		// for( String smallPic : smallPicArr ){
		// if( StringUtils.isNotBlank(smallPic) ){
		// String[] picArr = smallPic.split(":");
		//
		// }
		// }
		// 3D效果图列表
		String effectPicStr = designTemplet.getEffectPic();
		if (StringUtils.isNotBlank(effectPicStr)) {
			jsonObject.accumulate("success", false);
			jsonObject.accumulate("message",
					"【" + designTemplet.getDesignCode() + "】该样板房没有上传3D效果图!");
			return jsonObject;
		}
		String[] effectPicArr = effectPicStr.split(",");
		for (String effectPicIdStr : effectPicArr) {
			if (StringUtils.isNotBlank(effectPicIdStr)) {
				Integer effectPicId = 0;
				try {
					effectPicId = Integer.valueOf(effectPicIdStr);
				} catch (NumberFormatException e) {
					jsonObject.accumulate("success", false);
					jsonObject.accumulate("message",
							"【" + designTemplet.getDesignCode()
									+ "】该样板房没有上传3D效果图!");
					return jsonObject;
				}
				ResPic effectPic = resPicService.get(effectPicId);
				if (effectPic == null) {
					jsonObject.accumulate("success", false);
					jsonObject.accumulate("message",
							"【" + designTemplet.getDesignCode()
									+ "】该样板房的3D效果图在图片资源中没有找到!resPicId = "
									+ effectPicId);
					return jsonObject;
				}
				/*File effectPicFile = new File(Tools.getRootPath(
						effectPic.getPicPath(), "/home/nork/resources_178")
						+ "/" + effectPic.getPicPath());*/
				File effectPicFile = new File(Utils.getAbsolutePath(effectPic.getPicPath(), null));
				if (!effectPicFile.exists()) {
					jsonObject.accumulate("success", false);
					jsonObject.accumulate("message",
							"【" + designTemplet.getDesignCode()
									+ "】该样板房的3D效果图的物理文件没有找到!resPicId = "
									+ effectPicId);
					return jsonObject;
				}
				// 3D效果图缩略图
				String smallEffectPicInfo = effectPic.getSmallPicInfo();
				if (StringUtils.isBlank(smallEffectPicInfo)) {
					jsonObject.accumulate("success", false);
					jsonObject.accumulate("message",
							"【" + designTemplet.getDesignCode()
									+ "】该样板房没有生成3D效果缩略图!");
					return jsonObject;
				}
			}
		}

		return jsonObject;
	}

	/**
	 * 通过编码删除
	 * 
	 * @param designCode
	 * @return
	 */
	@Override
	public int deleteByCode(String designCode) {
		// 删除资源文件

		// 删除数据
		return designTempletMapper.deleteByCode(designCode);
	}

	@Override
	public DesignTemplet sysSave(DesignTemplet designTemplet,
			LoginUser loginUser) {
		designTemplet.setCreator(loginUser.getLoginName());
		designTemplet.setModifier(loginUser.getLoginName());
		designTemplet.setGmtCreate(new Date());
		designTemplet.setGmtModified(new Date());
		return designTemplet;
	}

	/**
	 * 根据产品id查找推荐该产品的已上架样板房的code
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public List<String> getCodeByProductIdFromProductRecommendation(Integer id) {
		return designTempletMapper
				.getCodeByProductIdFromProductRecommendation(id);
	}

	/**
	 * 根据空间id查找关联的已上架的样板房
	 * 
	 * @author huangsongbo
	 * @param spaceCommonId
	 * @return
	 */
	public List<String> getTempletCodeBySpaceId(Integer spaceCommonId) {
		return designTempletMapper.getTempletCodeBySpaceId(spaceCommonId);
	}

	/**
	 * 根据空间ids得到关联样板房ids
	 * 
	 * @author huangsongbo
	 * @param spaceIds
	 * @return
	 */
	public List<Integer> getTempletIdsBySpaceIds(List<Integer> spaceIds,
			List<Integer> putawayStates) {
		return designTempletMapper.getTempletIdsBySpaceIds(spaceIds,
				putawayStates);
	}

	/**
	 * 根据产品id查找关联的所有样板房
	 * 
	 * @author
	 * @param productId
	 * @return
	 */
	public List<DesignTemplet> getDesignTempletByProduct(Map map) {
		return designTempletMapper.getDesignTempletByProduct(map);
	}

	/**
	 * 根据产品id查找关联的所有样板房 总条数
	 * 
	 * @author
	 * @param productId
	 * @return
	 */
	@Override
	public int getDesignTempletByProductCount(Map map) {
		return designTempletMapper.getDesignTempletByProductCount(map);
	}

	@Override
	public DesignTemplet getV2(Integer templateId) {
	    
        /*DesignTemplet designTemplet = null;
        if(Utils.enableRedisCache()){
            designTemplet = DesignCacher.getTemplet(templateId);
        }
        if(null!=designTemplet && designTemplet.getId()!=null && designTemplet.getId().intValue()>0)
            return designTemplet;
        */
		return designTempletMapper.selectByPrimaryKeyV2(templateId);
	}

	@Override
	public List<Integer> findIdListByStatus(int status) {
		return designTempletMapper.findIdListByStatus(status);
	}

	/**
	 * 批量修改样板房状态
	 * 
	 * @author huangsongbo
	 * @param oldStatus
	 *            旧的状态
	 * @param newStatus
	 *            新的状态
	 */
	public void updateStatus(int oldStatus, int newStatus) {
		designTempletMapper.updateStatus(oldStatus, newStatus);
	}

	/**
	 * 通过编码获取样板房对象
	 * 
	 * @author xiaoxc
	 * @param templetCode
	 *            样板房编码 return DesignTemplet
	 */
	public DesignTemplet getDesignTempletByCode(String templetCode) {

		DesignTempletSearch designTempletSearch = new DesignTempletSearch();
		designTempletSearch.setDesignCode(templetCode);
		designTempletSearch.setIsDeleted(0);
		designTempletSearch.setStart(0);
		designTempletSearch.setLimit(1);
		designTempletSearch.setLevelLimitCount(-1);
		List<DesignTemplet> designTemplet = designTempletMapper
				.selectPaginatedList(designTempletSearch);
		if (designTemplet != null && designTemplet.size() > 0) {
			return designTemplet.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 样板房查询接口。根据空间查询出样板房列表
	 * 
	 * @param spaceCommonIdText
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	@Override
	public Object newSpaceDesign(String spaceCommonIdText, String msgId,
			String limit, String start, LoginUser loginUser) {
		
		int total = 0;
		List<DesignTemplet> list = new ArrayList<DesignTemplet>();
		DesignTempletSearch designTempletSearch = new DesignTempletSearch();
		try {
			// 上架条件
			/* designTempletSearch.setPutawayState(1); */
			// 发布条件
			designTempletSearch
					.setPutawayState(DesignTempletPutawayState.IS_RELEASE
							.intValue());

			designTempletSearch.setSpaceCommonId(Integer
					.parseInt(spaceCommonIdText));
			if (StringUtils.isNotBlank(start)) {
				designTempletSearch.setStart(Integer.parseInt(start));
			}
			if (StringUtils.isNotBlank(limit)) {
				designTempletSearch.setLimit(Integer.parseInt(limit));
			}
			/* 根据登录用户类型(内部用户,普通用户)决定查询条件putawayState */
			Integer userType = null;
//			if (loginUser==null) {
//				logger.info("------查询样板房接口->无法识别登录用户");
//			} else {
				String versionType = Utils.getPropertyName("app",
						"sys.version.type", "1").trim();/* 1为外网 2 为内网 默认为外网 */
				userType = loginUser.getUserType();
				if (userType != null && userType.intValue() == 1
						&& "2".equals(versionType)) {
					designTempletSearch.setPutawayState(null);
					Integer[] integers = new Integer[] { 1, 2, 3 };//1上架 2测试3为增加 的发布
					designTempletSearch.setPutawayStates(Arrays
							.asList(integers));
				}
//			}
			/* 根据登录用户类型(内部用户,普通用户)决定查询条件putawayState->end */

			if (Utils.enableRedisCache()) {
				if (userType != null && userType.intValue() == 1) {
					total = DesignTempletCacher.getTotalWithParameter2(
							designTempletSearch, userType,loginUser.getId());
				} else {
					total = DesignTempletCacher
							.getTotalWithParameter(designTempletSearch,loginUser.getId());
				}
			} else {
				total = this.getCount(designTempletSearch,loginUser.getId());
			}

			if (total > 0) {
				if (Utils.enableRedisCache()) {
					if (userType != null && userType.intValue() == 1) {
						list = DesignTempletCacher.getPageWithParameter2(
								designTempletSearch, userType,loginUser.getId());
					} else {
						list = DesignTempletCacher
								.getPageWithParameter(designTempletSearch,loginUser.getId());
					}
				} else {
					list = this.getPaginatedList(designTempletSearch,loginUser.getId());
				}

			}

			/* 关联查询spaceName和spaceAreas */
			if (CustomerListUtils.isNotEmpty(list)) {
				for (DesignTemplet templet : list) {
					/* 空间布局缩略图 */
					ResPic resPic2 = null;
					if (templet.getPicId() != null) {
						if (Utils.enableRedisCache()) {
							resPic2 = ResourceCacher.getPic(templet.getPicId());
						} else {
							resPic2 = resPicService.get(templet.getPicId());
						}
					}
					if (resPic2 != null) {
						templet.setPicPath(resPic2.getPicPath());
						ResPic smallResPic = resPicService.get(Utils
								.getSmallPicId(resPic2, "ipad"));
						// templet.setSmallpicPath(resPic2 == null ? "" :
						// Utils.getSmallPath(resPic2.getPicPath(), mediaType));
						templet.setSmallpicPath(smallResPic == null ? ""
								: (Utils.isBlank(smallResPic.getPicPath()) ? ""
										: smallResPic.getPicPath()));
					}
					// 效果图列表
					String effectPicId = templet.getEffectPic();
					if (!StringUtils.isEmpty(effectPicId)) {
						List<String> effectlist = new ArrayList();
						if (effectPicId.contains(",")) {
							String[] strs = effectPicId.split(",");
							for (String s : strs) {
								if (!StringUtils.isEmpty(s)) {
									ResPic resPic = null;
									if (Utils.enableRedisCache()) {
										resPic = ResourceCacher.getPic(Integer
												.parseInt(s));
									} else {
										resPic = resPicService.get(Integer
												.parseInt(s));
									}

									if (resPic != null
											&& StringUtils.isNotBlank(resPic
													.getPicPath())) {
										effectlist.add(resPic.getPicPath());
									}
								}
							}
							templet.setEffectPicListPath(effectlist
									.toArray(new String[effectlist.size()]));
							ResPic resPic = null;
							if (Utils.enableRedisCache()) {
								resPic = ResourceCacher.getPic(Integer
										.parseInt(strs[0]));
							} else {
								resPic = resPicService.get(Integer
										.parseInt(strs[0]));
							}
							ResPic smallResPic = resPicService.get(Utils
									.getSmallPicId(resPic, "ipad"));
							// templet.setEffectSmallPicPath(resPic == null ? ""
							// : Utils.getSmallPath(resPic.getPicPath(),
							// mediaType));
							templet.setEffectSmallPicPath(smallResPic == null ? ""
									: (Utils.isBlank(smallResPic.getPicPath()) ? ""
											: smallResPic.getPicPath()));

							// 客户端取值错误，临时修改为对应的值todo
							templet.setPicPath(templet.getEffectPicListPath()[0]);
							templet.setSmallpicPath(templet
									.getEffectSmallPicPath());
						} else {
							ResPic resPic = null;
							if (Utils.enableRedisCache()) {
								resPic = ResourceCacher.getPic(Integer
										.parseInt(effectPicId));
							} else {
								resPic = resPicService.get(Integer
										.parseInt(effectPicId));
							}

							String[] s = new String[1];
							s[0] = resPic == null ? "" : (Utils.isEmpty(resPic
									.getPicPath()) ? "" : resPic.getPicPath());
							templet.setEffectPicListPath(s);
							ResPic smallResPic = resPicService.get(Utils
									.getSmallPicId(resPic, "ipad"));
							// templet.setEffectSmallPicPath(resPic == null ? ""
							// : Utils.getSmallPath(resPic.getPicPath(),
							// mediaType));
							templet.setEffectSmallPicPath(smallResPic == null ? ""
									: (Utils.isBlank(smallResPic.getPicPath()) ? ""
											: smallResPic.getPicPath()));

							// 客户端取值错误，临时修改为对应的值todo
							templet.setPicPath(templet.getEffectPicListPath()[0]);
							templet.setSmallpicPath(templet
									.getEffectSmallPicPath());
						}
					}
					/* 关联查询平面效果图url及对应缩略图url */
					String effectPlanIds = templet.getEffectPlanIds();
					if (StringUtils.isNotBlank(effectPlanIds)) {
						Integer effectPlanId = 0;
						if (effectPlanIds.split(",").length > 1) {
							effectPlanId = Integer.valueOf(effectPlanIds
									.split(",")[0]);
						} else {
							effectPlanId = Integer.valueOf(effectPlanIds);
						}
						ResPic resPic = resPicService.get(effectPlanId);
						if (resPic != null
								&& StringUtils.isNotBlank(resPic.getPicPath())) {
							templet.setEffectPlanUrl(resPic.getPicPath());
							ResPic smallResPic = resPicService.get(Utils
									.getSmallPicId(resPic, "ipad"));
							// templet.setEffectPlanSmallUrl(Utils.getSmallPath(resPic.getPicPath(),mediaType));
							templet.setEffectPlanSmallUrl(smallResPic == null ? ""
									: (Utils.isBlank(smallResPic.getPicPath()) ? ""
											: smallResPic.getPicPath()));
						}
					}

					Integer spaceCommonId = templet.getSpaceCommonId();
					SpaceCommon spacecommon = null;
					if (Utils.enableRedisCache()) {
						spacecommon = SpaceCommonCacher.get(spaceCommonId);
					} else {
						spacecommon = spaceCommonService.get(spaceCommonId);
					}

					templet.setSpaceCommonName(spacecommon == null ? ""
							: spacecommon.getSpaceName());
					templet.setSpaceAreas(spacecommon == null ? ""
							: spacecommon.getSpaceAreas());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignTemplet>(false, "数据异常", msgId);
		}
		return new ResponseEnvelope<DesignTemplet>(total, list, msgId);
	}
	
	@Override
	public DesignTemplet selectByTemplateCode(String designCode) {
		return designTempletMapper.selectByTemplateCode(designCode);
	}

}
