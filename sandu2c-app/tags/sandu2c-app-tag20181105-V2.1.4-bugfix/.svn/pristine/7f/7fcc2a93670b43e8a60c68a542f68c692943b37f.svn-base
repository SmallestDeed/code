package com.sandu.base.service.impl;


import com.google.gson.Gson;
import com.sandu.base.dao.BaseLivingMapper;
import com.sandu.base.model.BaseArea;
import com.sandu.base.model.BaseLiving;
import com.sandu.base.model.search.BaseLivingSearch;
import com.sandu.base.service.BaseAreaService;
import com.sandu.base.service.BaseLivingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @version V1.0
 * @Title: BaseLivingServiceImpl.java
 * @Package com.sandu.system.service.impl
 * @Description:系统-小区ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 14:41:11
 */
@Service("baseLivingService")
public class BaseLivingServiceImpl implements BaseLivingService {
    private final static Gson GSON = new Gson();
    private final static String CLASS_LOG_PREFIX = "[小区搜索服务实现类]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseLivingServiceImpl.class);
    @Autowired
    private BaseLivingMapper baseLivingMapper;
    @Autowired
    private BaseAreaService baseAreaService;

    /**
     * 新增数据
     *
     * @param baseLiving
     * @return int
     */
    @Override
    public int add(BaseLiving baseLiving) {
        baseLivingMapper.insertSelective(baseLiving);
        return baseLiving.getId();
    }

    /**
     * 更新数据
     *
     * @param baseLiving
     * @return int
     */
    @Override
    public int update(BaseLiving baseLiving) {
        return baseLivingMapper
                .updateByPrimaryKeySelective(baseLiving);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return baseLivingMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseLiving
     */
    @Override
    public BaseLiving get(Integer id) {
        return baseLivingMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param baseLiving
     * @return List<BaseLiving>
     */
    @Override
    public List<BaseLiving> getList(BaseLiving baseLiving) {
        return baseLivingMapper.selectList(baseLiving);
    }

    /**
     * 获取数据数量
     *
     * @return int
     */
    @Override
    public int getCount(BaseLivingSearch baseLivingSearch) {
        return baseLivingMapper.selectCount(baseLivingSearch);
    }


    /**
     * 分页获取数据
     *
     * @return List<BaseLiving>
     */
    @Override
    public List<BaseLiving> getPaginatedList(
            BaseLivingSearch baseLivingSearch) {
        return baseLivingMapper.selectPaginatedList(baseLivingSearch);
    }

    /**
     * 根据省市，小区名查询小区
     */
    @Override
    public List<BaseLiving> getLivingByName(BaseLivingSearch baseLivingSearch) {
        List<BaseLiving> list = new ArrayList<BaseLiving>();
        baseLivingSearch.setOrder("id");
        baseLivingSearch.setOrderNum("desc");
        list = this.getPaginatedList(baseLivingSearch);
        for (BaseLiving living : list) {
            living.setPageSize(baseLivingSearch.getPageSize());
            living.setCurrentPage(baseLivingSearch.getCurrentPage());
            living.setAreaStr("无区域");
            String areaId = living.getAreaId();
            if (StringUtils.isNotBlank(areaId)) {
                BaseArea baseArea = new BaseArea();
                baseArea.setAreaCode(String.valueOf(areaId));
                logger.info(CLASS_LOG_PREFIX + "根据小区ID查询小区数据：{}", null == baseArea ? null : baseArea.toString());
                List<BaseArea> arealist = baseAreaService.getList(baseArea);
                logger.info(CLASS_LOG_PREFIX + "根据小区ID查询小区数据完成:{}", GSON.toJson(arealist));
                //区域名称
                String areaName = "";
                if (arealist != null && arealist.size() > 0) {
                    BaseArea area = arealist.get(0);
                    areaName = area.getAreaName();
                    String areaStr = "";
                    areaStr = areaName;
                    living.setAreaStr(areaStr);
                }
            }
        }
        return list;
    }


}
