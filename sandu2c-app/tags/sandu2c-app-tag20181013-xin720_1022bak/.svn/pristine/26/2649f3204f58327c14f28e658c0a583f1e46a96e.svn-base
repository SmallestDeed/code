package com.sandu.home.service;
import com.sandu.common.exception.BizException;
import com.sandu.design.model.DesignTemplet;
import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.BaseHouseResult;
import com.sandu.home.model.dto.BaseHouseGetMatchResultDTO;
import com.sandu.home.model.dto.HouseGuidePicInfoDTO;
import com.sandu.home.model.search.BaseHouseSearch;

import java.util.List;
import java.util.Map;


/**
 * @version V1.0
 * @Title: BaseHouseService.java
 * @Package com.sandu.business.service
 * @Description:业务-户型Service
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 11:53:51
 */
public interface BaseHouseService {
    /**
     * 新增数据
     *
     * @param baseHouse
     * @return int
     */
    int add(BaseHouse baseHouse);

    /**
     * 更新数据
     *
     * @param baseHouse
     * @return int
     */
    int update(BaseHouse baseHouse);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseHouse
     */
    BaseHouse get(Integer id);

    /**
     * 所有数据
     *
     * @param baseHouse
     * @return List<BaseHouse>
     */
    List<BaseHouse> getList(BaseHouse baseHouse);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(BaseHouseSearch baseHouseSearch);


    /**
     * 分页获取数据
     *
     * @return List<BaseHouse>
     */
    List<BaseHouse> getPaginatedList(
            BaseHouseSearch baseHousetSearch);

    /**
     * getPaginatedList改造,得到更多的信息
     *
     * @param baseHouseSearch
     * @return
     */
    List<BaseHouse> getPaginatedListMoreInfo(BaseHouseSearch baseHouseSearch);

    /**
     * 查找小区内的户型信息
     *
     * @param baseHouseSearch
     * @return
     */
    List<BaseHouse> getBaseHousesByLivingId(BaseHouseSearch baseHouseSearch);

    /**
     * 小区搜索
     *
     * @param baseHouse
     * @return List<BaseHouse>
     */
    List<BaseHouseResult> getHouseList(
            BaseHouseResult baseHouseResult);


    /**
     * 获取拥有空间的户型
     *
     * @param baseHouseSearch
     * @return
     */
    int getHaveSpaceCount(BaseHouseSearch baseHouseSearch);

    /**
     * 获取拥有空间的户型
     *
     * @param baseHouseSearch
     * @return
     */
    List<BaseHouse> getHaveSpaceList(BaseHouseSearch baseHouseSearch);

    int getHouseCount(BaseHouseResult baseHouseResultSearch);

    /**
     * 根据渲染方案副本主键集合查询所关联的房型信息
     *
     * @param sceneIds 方案副本ID集合
     * @return
     */
    List<BaseHouse> listBaseHouseByDesignPLanReanderSceneId(List<String> sceneIds);

    /**
     * 获取户型2d导航图及其关联的信息(坐标信息,坐标对应跳转的样板房信息)
     * 需求:可以点击2d导航图上的坐标(选择样板房),搜索对应的推荐方案
     *
     * @param houseId         户型id
     * @param fullHousePlanId 全屋方案id 用来查询当前渲染状态
     * @param renderStatusMap 同fullHousePlanId也是用提供渲染状态信息,只不过提供了已经查好的渲染状态map,此参数优先级更大,
     * @param designTempletMap key = 样板房id, value = 样板房信息, add by huangsongbo 2018.10.12 为了填充样板房的其他信息(该样板房匹配到了哪个推荐方案)
     * @return
     * @throws BizException
     * @author huangsongbo
     */
	HouseGuidePicInfoDTO getHouseGuidePicInfoDTO(
			Integer houseId, 
			Integer fullHousePlanId, 
			Map<Integer, Integer> renderStatusMap,
			Map<Integer, DesignTemplet> designTempletMap
			) throws BizException;

	/**
     * 获取可装修方案结果:
     * 选择推荐方案,再选择户型(houseId)或者我的方案(recommendedPlanId)
     * 
     * a.户型中只有一个适用的样板房
     *     |
     *     |->检测该样板房对应方案状态
     *             |
     *             |->未渲染/渲染失败: 直接装进该样板房
     *             |
     *             |->渲染中: 提示样板房对应的方案正在渲染中,不允许装修
     *             |
     *             |->渲染完成: 提示要更新还是新增
     * 
     * b.户型中有n(n>=2)个适用样板房
     *     |
     *     |->检测样板房状态为渲染中的个数n1
     *             |
     *             |->n1 = n - 1(只有一个可装入样板房):
     *             |        |
     *             |        |->检测剩余的样板房的状态
     *             |                |
     *             |                |->未渲染/渲染失败: 直接装进该样板房
     *             |                |
     *             |                |->渲染完成: 提示要更新还是新增
     *             |
     *             |->n1 = n(没有可装入样板房):提示样板房对应的方案正在渲染中,不允许装修
     *             |
     *             |->n1 < n - 1(有多个可装入样板房(数量>2)):弹出户型图供其选择
     *             
     * c.没有适用样板房:提示不匹配
     * 
     * @author huangsongbo
     * @param houseId
     * @param recommendedPlanId
     * @param fullPlanId
     * @return
	 * @throws BizException 
     */
	BaseHouseGetMatchResultDTO getMatchResult(Integer houseId, Integer recommendedPlanId, Integer fullPlanId) throws BizException;


    HouseGuidePicInfoDTO getAlreadyHouseGuidePicInfoDTO(Integer houseId, Integer fullHousePlanId, List <Integer> templateIds) throws BizException;
}
