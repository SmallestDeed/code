package com.sandu.home.service.impl;

import com.google.gson.Gson;
import com.sandu.common.exception.BizException;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.designplan.DesignPlanRecommend;
import com.sandu.design.model.DesignTemplet;
import com.sandu.designplan.model.DesignPlanRecommended;
import com.sandu.designplan.service.DesignPlanRecommendedService;
import com.sandu.designtemplate.service.DesignTempletService;
import com.sandu.fullhouse.model.FullHouseDesignPlan;
import com.sandu.fullhouse.service.FullHouseDesignPlanService;
import com.sandu.home.dao.BaseHouseMapper;
import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.BaseHouseResult;
import com.sandu.home.model.dto.BaseHouseGetMatchResultDTO;
import com.sandu.home.model.dto.GuideInfoDTO;
import com.sandu.home.model.dto.HouseGuidePicInfoDTO;
import com.sandu.home.model.search.BaseHouseSearch;
import com.sandu.home.service.BaseHouseService;
import com.sandu.home.service.HouseSpaceNewService;
import com.sandu.system.model.*;
import com.sandu.system.model.constants.BaseHousePicFullHousePlanRelConstant;
import com.sandu.system.service.BaseHouseGuidePicInfoService;
import com.sandu.system.service.BaseHousePicFullHousePlanRelService;
import com.sandu.system.service.ResHousePicService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @version V1.0
 * @Title: BaseHouseServiceImpl.java
 * @Package com.sandu.business.service.impl
 * @Description:业务-户型ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 11:53:51
 */
@Service("baseHouseService")
public class BaseHouseServiceImpl implements BaseHouseService {
    private final static String CLASS_LOG_PREFIX = "[户型搜索服务]:";
    private static Gson gson = new Gson();
    private static Logger logger = LoggerFactory.getLogger(BaseHouseServiceImpl.class);

    @Autowired
    private BaseHouseMapper baseHouseMapper;
    
    @Autowired
    private HouseSpaceNewService houseSpaceNewService;
    
    @Autowired
    private ResHousePicService resHousePicService;
    
    @Autowired
    private BaseHouseGuidePicInfoService baseHouseGuidePicInfoService;
    
    @Autowired
    private FullHouseDesignPlanService fullHouseDesignPlanService;
    
    @Autowired
    private DesignTempletService designTempletService;

    @Autowired
    private DesignPlanRecommendedService designPlanRecommendedService;
    
    @Autowired
    private BaseHousePicFullHousePlanRelService baseHousePicFullHousePlanRelService;


    
    /**
     * 新增数据
     *
     * @param baseHouse
     * @return int
     */
    @Override
    public int add(BaseHouse baseHouse) {
        baseHouseMapper.insertSelective(baseHouse);
        return baseHouse.getId();
    }

    /**
     * 更新数据
     *
     * @param baseHouse
     * @return int
     */
    @Override
    public int update(BaseHouse baseHouse) {
        return baseHouseMapper
                .updateByPrimaryKeySelective(baseHouse);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return baseHouseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseHouse
     */
    @Override
    public BaseHouse get(Integer id) {
        return baseHouseMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param baseHouse
     * @return List<BaseHouse>
     */
    @Override
    public List<BaseHouse> getList(BaseHouse baseHouse) {
        return baseHouseMapper.selectList(baseHouse);
    }

    /**
     * 获取数据数量
     *
     * @return int
     */
    @Override
    public int getCount(BaseHouseSearch baseHouseSearch) {
        return baseHouseMapper.selectCount(baseHouseSearch);
    }

    /**
     * 分页获取数据
     *
     * @return List<BaseHouse>
     */
    @Override
    public List<BaseHouse> getPaginatedList(
            BaseHouseSearch baseHouseSearch) {
        List<BaseHouse> baseHouses = baseHouseMapper.selectPaginatedList(baseHouseSearch);
        return baseHouses;
    }

    @Override
    public List<BaseHouse> getPaginatedListMoreInfo(BaseHouseSearch baseHouseSearch) {
        return baseHouseMapper.getPaginatedListMoreInfo(baseHouseSearch);
    }

    @Override
    public List<BaseHouse> getBaseHousesByLivingId(BaseHouseSearch baseHouseSearch) {
        List<BaseHouse> houses = this.getPaginatedListMoreInfo(baseHouseSearch);
        //处理房型
        for (BaseHouse baseHouse : houses) {
            baseHouse.setPageSize(baseHouseSearch.getPageSize());
            baseHouse.setCurrentPage(baseHouseSearch.getCurrentPage());
            List<String> spaceTypeList = houseSpaceNewService.getSpaceTypeListByHouseId(baseHouse.getId());
            logger.info(CLASS_LOG_PREFIX + "通过户型ID获取该户型下所有的空间类型：{}", gson.toJson(spaceTypeList));
            if (null == spaceTypeList || spaceTypeList.size() <= 0) {
                continue;
            }

            Map<String, Integer> elementsCount = new HashMap<String, Integer>();
            for (String s : spaceTypeList) {
                Integer i = elementsCount.get(s);
                if (i == null) {
                    elementsCount.put(s, 1);
                } else {
                    elementsCount.put(s, i + 1);
                }
            }
            //处理房型，待产品确认
/*            baseHouse.setHouseTypeStr(((elementsCount.containsKey("1") ? elementsCount.get("1") : 0) + (elementsCount.containsKey("7") ? elementsCount.get("7") : 0)
                    + (elementsCount.containsKey("8") ? elementsCount.get("8") : 0)) + "室"
                    + (elementsCount.containsKey("2") ? elementsCount.get("2") : 0) + "厅"
                    + (elementsCount.containsKey("3") ? elementsCount.get("3") : 0) + "卫" + (elementsCount.containsKey("4") ? elementsCount.get("4") : 0) + "厨"
                    + (elementsCount.containsKey("9") ? elementsCount.get("9") : 0) + "其他");*/

            baseHouse.setHouseTypeStr(((elementsCount.containsKey("3") ? elementsCount.get("3") : 0) + "厅"
                    + (elementsCount.containsKey("4") ? elementsCount.get("4") : 0)) + "室"
                    + (elementsCount.containsKey("5") ? elementsCount.get("5") : 0) + "厨"
                    + (elementsCount.containsKey("6") ? elementsCount.get("6") : 0) + "卫"
                    + (elementsCount.containsKey("7") ? elementsCount.get("7") : 0) + "书"
                    + (elementsCount.containsKey("8") ? elementsCount.get("8") : 0) + "衣"
                    + (elementsCount.containsKey("9") ? elementsCount.get("9") : 0) + "其他");

        }
        return houses;
    }

    @Override
    public List<BaseHouseResult> getHouseList(
            BaseHouseResult baseHouseResultSearch) {
        List<BaseHouseResult> baseHouseResults = null;
        baseHouseResults = baseHouseMapper.houseSearchList(baseHouseResultSearch);
        return baseHouseResults;
    }


    /**
     * 获取拥有空间的户型
     *
     * @param baseHouseSearch
     * @return
     */
    @Override
    public int getHaveSpaceCount(BaseHouseSearch baseHouseSearch) {
        return baseHouseMapper.getHaveSpaceCount(baseHouseSearch);
    }

    /**
     * 获取拥有空间的户型
     *
     * @param baseHouseSearch
     * @return
     */
    @Override
    public List<BaseHouse> getHaveSpaceList(BaseHouseSearch baseHouseSearch) {
        return baseHouseMapper.getHaveSpaceList(baseHouseSearch);
    }

    @Override
    public int getHouseCount(BaseHouseResult baseHouseResult) {
        return baseHouseMapper.getHouseCount(baseHouseResult);
    }

    @Override
    public List<BaseHouse> listBaseHouseByDesignPLanReanderSceneId(List<String> sceneIds) {
        return baseHouseMapper.listBaseHouseByDesignPLanReanderSceneId(sceneIds);
    }

	@Override
	public HouseGuidePicInfoDTO getHouseGuidePicInfoDTO(
			Integer houseId, 
			Integer fullHousePlanId, 
			Map<Integer, Integer> renderStatusMap,
			Map<Integer, DesignTemplet> designTempletMap
			) throws BizException {
		String exceptionLogPrefix = "获取户型2d导航图出错; ";
		
		// 参数验证 ->start
		if(houseId == null) {
			throw new BizException(exceptionLogPrefix + "params error : houseId = null");
		}
		BaseHouse baseHouse = this.get(houseId);
		if(baseHouse == null) {
			throw new BizException(exceptionLogPrefix + "户型数据没有找到; houseId = " + houseId);
		}
		// 参数验证 ->end
		
		HouseGuidePicInfoDTO houseGuidePicInfoDTO = new HouseGuidePicInfoDTO();
		
		// get图片信息 ->start
		Integer picId = baseHouse.getSnapPicId();
		if(picId == null) {
			throw new BizException(exceptionLogPrefix + "户型2d导航图没有找到; picId = null; houseId = " + houseId);
		}
		ResHousePic resHousePic = resHousePicService.get(picId);
		if(resHousePic == null) {
			throw new BizException(exceptionLogPrefix + "户型2d导航图没有找到; picId = " + picId);
		}
		// get图片信息 ->end
		
		houseGuidePicInfoDTO.setPicPath(resHousePic.getPicPath());
		
		// get坐标和烘焙状态信息 ->start
		List<GuideInfoDTO> guideInfoDTOList = baseHouseGuidePicInfoService.getGuideInfoDTOListByPicId(picId, fullHousePlanId, renderStatusMap, designTempletMap);
		// get坐标和烘焙状态信息 ->end

        if(fullHousePlanId !=null && fullHousePlanId.intValue() !=0) {
            List<Integer> templateIds = new ArrayList <>();
            for(GuideInfoDTO guideInfoDTO : guideInfoDTOList) {
                templateIds.add(guideInfoDTO.getDesignTempletId());
            }
            RenderTaskStateSearch search = new RenderTaskStateSearch();
//            search.setFullPlanHouseId(fullHousePlanId);
            search.setTemplateIds(templateIds);
            search.setNewFullPlanHouseId(fullHousePlanId);
            List<RenderTaskState> taskStates = baseHousePicFullHousePlanRelService.getRenderTaskStateList(search);
            List<RenderTaskState> taskList = baseHousePicFullHousePlanRelService.getRenderTaskListByFullHouseIdAndTemplateIds(search);
            for(RenderTaskState state : taskList) {
                state.setState(2);
            }
            taskStates.addAll(taskList);
            houseGuidePicInfoDTO.setTaskStates(taskStates);
            // 先到任务状态表里查询任务状态记录
        }
		houseGuidePicInfoDTO.setGuideInfoList(guideInfoDTOList);
				
		return houseGuidePicInfoDTO;
	}

	@Override
	public BaseHouseGetMatchResultDTO getMatchResult(Integer houseId, Integer recommendedPlanId, Integer fullPlanId) throws BizException {
		// ------参数验证 ->start
		if(recommendedPlanId == null) {
			throw new BizException("参数 recommendedPlanId 不能为空");
		}
		if(houseId == null && fullPlanId == null) {
			throw new BizException("参数 houseId 和 fullPlanId 不能同时为空");
		}
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(recommendedPlanId);
		if(designPlanRecommended == null) {
			throw new BizException("推荐方案没有找到; recommendedId = " + recommendedPlanId);
		}
		// ------参数验证 ->end
		
		Integer houseIdInUse = null;
		if(houseId != null) {
			houseIdInUse = houseId;
		}else {
			houseIdInUse = fullHouseDesignPlanService.getHouseIdByFullPlanId(fullPlanId);
		}
		
		if(houseIdInUse == null) {
			throw new BizException("没有找到对应户型");
		}
		
		List<DesignTemplet> designTempletList = designTempletService.getListByHouseId(houseIdInUse);
		
		// ------遍历该户型下所有样板房(designTempletList), 检测与推荐方案适配的样板房 ->start
		if(designTempletList == null || designTempletList.size() == 0) {
			throw new BizException("检测到该户型下没有样板房");
		}
		// 注释by huangsongbo 2018.10.12 由于没考虑到精选方案的情况,故注释
		/*designTempletList = designTempletList.stream().filter(item -> designTempletService.getIsMatch(item, designPlanRecommended)).collect(Collectors.toList());*/
		
		// if 该方案是精选方案,then designPlanRecommendedList = 该精选方案包含的所有子方案的list
		// if 该方案非精选方案,then designPlanRecommendedList = 只包含该方案的list
		List<DesignPlanRecommended> designPlanRecommendedList = designPlanRecommendedService.getGroupDesignPlanRecommendedList(designPlanRecommended);
		for (int index = 0; index < designTempletList.size(); index ++) {
			Integer matchRecommendedId = designTempletService.getMatchRecommendedId(designTempletList.get(index), designPlanRecommendedList);
			if(matchRecommendedId != null && matchRecommendedId > 0) {
				// 该样板房有匹配上推荐方案
				designTempletList.get(index).setMatchRecommendedId(matchRecommendedId);
			}else {
				// 没有匹配的推荐方案,移除该样板房
				designTempletList.remove(index);
				index --;
			}
		}
		// ------遍历该户型下所有样板房(designTempletList), 检测与推荐方案适配的样板房 ->end
		
		if(designTempletList == null || designTempletList.size() == 0) {
			// status = 1: 无适配样板房(该方案不匹配,请选择其他方案)
			return new BaseHouseGetMatchResultDTO(1);
		}
		
		// ------if(从我的方案装修) -> 获取样板房装修状态 ->start
		// key = 样板房id, value = 渲染状态
		Map<Integer, Integer> baseHousePicFullHousePlanRelMap = baseHousePicFullHousePlanRelService.getRenderStatusMap(fullPlanId);
		// ------if(从我的方案装修) -> 获取样板房装修状态 ->end
		
		// ------统计渲染中样板房数量+未渲染/渲染完成样板房数量 ->start
		// 未渲染/渲染失败样板房idList
//		List<Integer> enableIdList = new ArrayList<Integer>();
		// 渲染中样板房idList
		List<Integer> disableIdList = new ArrayList<Integer>();
		// 渲染成功样板房idList
//		List<Integer> successIdList = new ArrayList<Integer>();


        List<Integer> needToConfirmDecorationIdList = new ArrayList<Integer>();//Need to confirm to decoration which the space,Because this plan adaptation multiple space.
        List<Integer> alreadyDecorationIdList = new ArrayList<Integer>();//Already decoration the id list.
        // key = 样板房id, value = 样板房信息
        Map<Integer, DesignTemplet> designTempletMap = new HashMap<Integer, DesignTemplet>();

		// key = 样板房id, value = 渲染状态, 区别于baseHousePicFullHousePlanRelMap,renderStatusMap的key,是designTempletList所有的样板房id,
		// 而baseHousePicFullHousePlanRelMap只是从渲染状态表查出的数据,designTempletList中的样板房id可能在baseHousePicFullHousePlanRelMap中不存在,此情况视为未渲染
		Map<Integer, Integer> renderStatusMap = new HashMap<Integer, Integer>();
        for(DesignTemplet item : designTempletList) {
        	designTempletMap.put(item.getId(), item);
            if(baseHousePicFullHousePlanRelMap.containsKey(item.getId())) {
                Integer status = baseHousePicFullHousePlanRelMap.get(item.getId());
                renderStatusMap.put(item.getId(), status);
                if(status == null) {
                    needToConfirmDecorationIdList.add(item.getId());
                    continue;
                }
                if(BaseHousePicFullHousePlanRelConstant.STATE_FAILED.intValue() == status.intValue()) {
                    needToConfirmDecorationIdList.add(item.getId());
                }else if(BaseHousePicFullHousePlanRelConstant.STATE_IN_PROGRESS.intValue() == status.intValue()) {
                    disableIdList.add(item.getId());
                }else if(BaseHousePicFullHousePlanRelConstant.STATE_SUCCESS.intValue() == status.intValue()) {
                    alreadyDecorationIdList.add(item.getId());
                }else {
                    needToConfirmDecorationIdList.add(item.getId());
                }
            }else {
                renderStatusMap.put(item.getId(), BaseHousePicFullHousePlanRelConstant.STATE_NOT_BEGIN);
                needToConfirmDecorationIdList.add(item.getId());
            }
        }
        // ------根据统计数量,返回对应消息 ->start
        if(disableIdList.size() == designTempletList.size()) {
            // 所有可用样板房,都在渲染中
            return new BaseHouseGetMatchResultDTO(2);
        }
        List <RenderTaskState> taskStates = new ArrayList <>();
        if(fullPlanId != null && fullPlanId.intValue() != 0 && alreadyDecorationIdList.size() >0) {
            RenderTaskStateSearch search = new RenderTaskStateSearch();
            search.setNewFullPlanHouseId(fullPlanId);
            search.setTemplateIds(alreadyDecorationIdList);
            taskStates = baseHousePicFullHousePlanRelService.getRenderTaskStateList(search);
        }


        BaseHouseGetMatchResultDTO baseHouseGetMatchResultDTO = null;
        //Priority resolve the don't decoration space.
        int countOfNoDecoration =needToConfirmDecorationIdList.size();
        if(countOfNoDecoration >0 ) {
            if(countOfNoDecoration == 1) { //1 :1
                /*return new BaseHouseGetMatchResultDTO(3, needToConfirmDecorationIdList.get(0), recommendedPlanId);*/
                baseHouseGetMatchResultDTO = new BaseHouseGetMatchResultDTO(3, needToConfirmDecorationIdList.get(0), designTempletMap.get(needToConfirmDecorationIdList.get(0)).getMatchRecommendedId());
                baseHouseGetMatchResultDTO.setTaskStateList(taskStates);
                return baseHouseGetMatchResultDTO;
            }else {
                //1:N
                HouseGuidePicInfoDTO houseGuidePicInfoDTO = this.getHouseGuidePicInfoDTO(houseIdInUse, null, renderStatusMap, designTempletMap);
                baseHouseGetMatchResultDTO = new BaseHouseGetMatchResultDTO(5, houseGuidePicInfoDTO);
                baseHouseGetMatchResultDTO.setTaskStateList(taskStates);
                return baseHouseGetMatchResultDTO;
            }
        }
        int countOfAlreadyDecoration =alreadyDecorationIdList.size();
        if(countOfAlreadyDecoration > 0) {
            if(countOfAlreadyDecoration == 1) {
                //1:1
                /*BaseHouseGetMatchResultDTO  baseHouseGetMatchResultDTO = new BaseHouseGetMatchResultDTO(4, alreadyDecorationIdList.get(0), recommendedPlanId);*/
            	baseHouseGetMatchResultDTO = new BaseHouseGetMatchResultDTO(4, alreadyDecorationIdList.get(0), designTempletMap.get(alreadyDecorationIdList.get(0)).getMatchRecommendedId());
                baseHouseGetMatchResultDTO.setTaskStateList(taskStates);
                return baseHouseGetMatchResultDTO;
            }else {
                //1:N
//                HouseGuidePicInfoDTO houseGuidePicInfoDTO = this.getHouseGuidePicInfoDTO(houseIdInUse, null, renderStatusMap);
                HouseGuidePicInfoDTO houseGuidePicInfoDTO  = this.getAlreadyHouseGuidePicInfoDTO(houseId, fullPlanId,alreadyDecorationIdList);
                baseHouseGetMatchResultDTO = new BaseHouseGetMatchResultDTO(5, houseGuidePicInfoDTO);
                baseHouseGetMatchResultDTO.setTaskStateList(taskStates);
                return baseHouseGetMatchResultDTO;
            }
        }
		// ------已经判定为未渲染+已渲染数量>2,需要返回户型图及对应坐标信息及对应渲染状态信息供用户选择样板房 ->start
		HouseGuidePicInfoDTO houseGuidePicInfoDTO = this.getHouseGuidePicInfoDTO(houseIdInUse, null, renderStatusMap, designTempletMap);
        baseHouseGetMatchResultDTO =new BaseHouseGetMatchResultDTO(5, houseGuidePicInfoDTO);
        baseHouseGetMatchResultDTO.setTaskStateList(taskStates);
        return baseHouseGetMatchResultDTO;
		// ------已经判定为未渲染+已渲染数量>2,需要返回户型图及对应坐标信息及对应渲染状态信息供用户选择样板房 ->end
	}

    @Override
    public HouseGuidePicInfoDTO getAlreadyHouseGuidePicInfoDTO(Integer houseId, Integer fullHousePlanId, List<Integer> templateIds) throws BizException {
        String exceptionLogPrefix = "获取户型2d导航图出错; ";

        // 参数验证 ->start
        if(houseId == null) {
            throw new BizException(exceptionLogPrefix + "params error : houseId = null");
        }
        BaseHouse baseHouse = this.get(houseId);
        if(baseHouse == null) {
            throw new BizException(exceptionLogPrefix + "户型数据没有找到; houseId = " + houseId);
        }
        // 参数验证 ->end

        HouseGuidePicInfoDTO houseGuidePicInfoDTO = new HouseGuidePicInfoDTO();

        // get图片信息 ->start
        Integer picId = baseHouse.getSnapPicId();
        if(picId == null) {
            throw new BizException(exceptionLogPrefix + "户型2d导航图没有找到; picId = null; houseId = " + houseId);
        }
        ResHousePic resHousePic = resHousePicService.get(picId);
        if(resHousePic == null) {
            throw new BizException(exceptionLogPrefix + "户型2d导航图没有找到; picId = " + picId);
        }
        // get图片信息 ->end

        houseGuidePicInfoDTO.setPicPath(resHousePic.getPicPath());

        // get坐标和烘焙状态信息 ->start
        List<GuideInfoDTO> guideInfoDTOList = baseHouseGuidePicInfoService.getGuideInfoDTOListByTemplatedIds(picId, templateIds);
        // get坐标和烘焙状态信息 ->end
//        BaseHousePicFullHousePlanRelSearch search = new BaseHousePicFullHousePlanRelSearch();
//        search.setFullHousePlanId(fullHousePlanId);
//        search.setTemplateIds(templateIds);
//        List<BaseHousePicFullHousePlanRel>  baseHousePicFullHousePlanRelList = baseHousePicFullHousePlanRelService.getBaseHousePicFullHousePlanRelList(search);
//        List<GuideInfoDTO> guideInfoDTOList = new ArrayList <>();
//        for(BaseHousePicFullHousePlanRel rel : baseHousePicFullHousePlanRelList) {
//            GuideInfoDTO guideInfoDTO = new GuideInfoDTO();
//            guideInfoDTO.setDesignTempletId(Integer.valueOf(rel.getDesignTempletId().toString()));
//            guideInfoDTO.setXCoordinate(rel.getXCoordinate());
//            guideInfoDTO.setYCoordinate(rel.getYCoordinate());
//            guideInfoDTO.setRenderStatus(BaseHousePicFullHousePlanRelConstant.STATE_SUCCESS);
//            guideInfoDTO.setSpaceType(Integer.valueOf(rel.getSpaceType()));
////            guideInfoDTO.setRenderStatus(Integer.valueOf(rel.getState().toString()));
//            guideInfoDTO.setSpaceType(Integer.valueOf(rel.getSpaceType()));
//            guideInfoDTO.setSpaceArea(rel.getSpaceArea());
//            guideInfoDTOList.add(guideInfoDTO);
//        }
        houseGuidePicInfoDTO.setGuideInfoList(guideInfoDTOList);
        return houseGuidePicInfoDTO;
    }

}

