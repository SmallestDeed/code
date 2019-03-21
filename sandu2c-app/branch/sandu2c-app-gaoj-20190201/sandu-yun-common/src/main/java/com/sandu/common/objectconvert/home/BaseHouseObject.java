package com.sandu.common.objectconvert.home;

import com.sandu.common.model.PageModel;
import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.search.BaseHouseSearch;
import com.sandu.home.model.vo.BaseHouseVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换户型对象
 *
 * @author chenm
 */
public class BaseHouseObject {

    /**
     * 转换为BaseHouseVo对象
     *
     * @param baseHouse
     * @return
     */
    public static BaseHouseVo convertToBaseLivingVo(BaseHouse baseHouse) {
        BaseHouseVo baseHouseVo = null;

        if (baseHouse != null) {
            baseHouseVo = new BaseHouseVo();
            //装入数据
            baseHouseVo.setId(baseHouse.getId());
            baseHouseVo.setLivingId(baseHouse.getLivingId());
            baseHouseVo.setLivingName(baseHouse.getLivingName());
            baseHouseVo.setHouseName(baseHouse.getHouseName());
            baseHouseVo.setHouseTypeStr(baseHouse.getHouseTypeStr());
            baseHouseVo.setThumbnailPath(baseHouse.getThumbnailPath());
        }
        return baseHouseVo;
    }

    /**
     * 转换为BaseLivingVo对象
     *
     * @param baseHouses
     * @return
     */
    public static List<BaseHouseVo> convertTobaseLivingVo(List<BaseHouse> baseHouses) {
        BaseHouseVo baseHouseVo = null;
        List<BaseHouseVo> baseHouseList = null;
        if (null != baseHouses) {
            baseHouseList = new ArrayList<BaseHouseVo>(baseHouses.size());
            for (BaseHouse baseHouse : baseHouses) {
                baseHouseVo = new BaseHouseVo();
                baseHouseVo.setId(baseHouse.getId());
                baseHouseVo.setLivingId(baseHouse.getLivingId());
                baseHouseVo.setLivingName(baseHouse.getLivingName());
                baseHouseVo.setHouseName(baseHouse.getHouseCommonCode());
                baseHouseVo.setHouseTypeStr(baseHouse.getHouseTypeStr());
                baseHouseVo.setThumbnailPath(baseHouse.getThumbnailPath());
                baseHouseVo.setLargeThumbnailPath(baseHouse.getLargeThumbnailPath());
                baseHouseVo.setTotalArea(baseHouse.getTotalArea());
                baseHouseVo.setFavoriteNum(baseHouse.getFavoriteNum());
                baseHouseVo.setLikeNum(baseHouse.getLikeNum());
                baseHouseVo.setIsFavorite(baseHouse.getIsFavorite());
                baseHouseVo.setIsLike(baseHouse.getIsLike());
                baseHouseList.add(baseHouseVo);
            }

        }
        return baseHouseList;
    }

    /**
     * 转换为BaseHouse对象
     *
     * @param baseHouseVo
     * @return
     */
    public static BaseHouse parseToBaseLiving(BaseHouseVo baseHouseVo, PageModel pageModel) {
        BaseHouse baseHouse = null;
        if (null != baseHouseVo) {
            baseHouse = new BaseHouse();
            baseHouse.setId(baseHouseVo.getId());
            baseHouse.setLivingId(baseHouseVo.getLivingId());
            baseHouse.setLivingName(baseHouseVo.getLivingName());
            baseHouse.setHouseName(baseHouseVo.getHouseName());
            baseHouse.setHouseTypeStr(baseHouseVo.getHouseTypeStr());
            baseHouse.setThumbnailPath(baseHouseVo.getThumbnailPath());
            baseHouse.setTotalArea(baseHouseVo.getTotalArea());
        }
        if (null != pageModel && 0 != pageModel.getPageSize()) {
            baseHouse.setCurrentPage(pageModel.getCurPage());
            baseHouse.setPageSize(pageModel.getPageSize());
        } else {
            //加载默认分页参数
            baseHouse.setPageSize(PageModel.DEFAULT_PAGE_PAGESIZE);
        }
        return baseHouse;
    }

    /**
     * 转换为 BaseHouseSearch 对象
     *
     * @param baseHouseVo
     * @return
     */
    public static BaseHouseSearch parseToBaseLivingSearch(BaseHouseVo baseHouseVo, PageModel pageModel) {
        BaseHouseSearch baseHouseSearch = null;
        if (null != baseHouseVo) {
            baseHouseSearch = new BaseHouseSearch();
            baseHouseSearch.setId(baseHouseVo.getId());
            baseHouseSearch.setLivingId(baseHouseVo.getLivingId());
            baseHouseSearch.setLivingName(baseHouseVo.getLivingName());
            baseHouseSearch.setHouseName(baseHouseVo.getHouseName());
            baseHouseSearch.setHouseTypeStr(baseHouseVo.getHouseTypeStr());
            baseHouseSearch.setThumbnailPath(baseHouseVo.getThumbnailPath());
            baseHouseSearch.setTotalArea(baseHouseVo.getTotalArea());

        }
        if (null != pageModel && 0 != pageModel.getPageSize()) {
            baseHouseSearch.setCurrentPage(pageModel.getStart());
            baseHouseSearch.setPageSize(pageModel.getPageSize());
        } else {
            //加载默认分页参数
            baseHouseSearch.setPageSize(PageModel.DEFAULT_PAGE_PAGESIZE);
        }
        return baseHouseSearch;
    }

}
