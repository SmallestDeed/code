package com.sandu.home.service.impl;

import com.google.gson.Gson;
import com.sandu.home.dao.BaseHouseMapper;
import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.BaseHouseResult;
import com.sandu.home.model.search.BaseHouseSearch;
import com.sandu.home.service.BaseHouseService;
import com.sandu.home.service.HouseSpaceNewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

}

