package com.sandu.common.objectconvert.base;

import com.sandu.base.model.BaseLiving;
import com.sandu.base.model.search.BaseLivingSearch;
import com.sandu.base.model.vo.BaseLivingVo;
import com.sandu.common.model.PageModel;
import com.sandu.common.tool.EscapeUnescape;
import com.sandu.home.model.BaseHouseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换小区对象
 *
 * @author chenm
 */
public class BaseLivingObject {

    /**
     * 转换为BaseLivingVo对象
     *
     * @return
     */
    public static BaseLivingVo convertToBaseLivingVo(BaseLiving baseLiving) {
        BaseLivingVo baseLivingVo = null;

        if (baseLiving != null) {
            baseLivingVo = new BaseLivingVo();
            //装入数据
            baseLivingVo.setId(baseLiving.getId());
            baseLivingVo.setLivingCode(baseLiving.getLivingCode());
            baseLivingVo.setLivingName(baseLiving.getLivingName());
            baseLivingVo.setAreaStr(baseLiving.getAreaId());
        }
        return baseLivingVo;
    }

    /**
     * 转换为BaseLivingVo对象
     *
     * @return
     */
    public static List<BaseLivingVo> convertTobaseLivingVo(List<BaseHouseResult> BaseLivings) {
        BaseLivingVo baseLivingVo = null;
        List<BaseLivingVo> baseLivingList = null;
        if (null != BaseLivings) {
            baseLivingList = new ArrayList<BaseLivingVo>(BaseLivings.size());
            for (BaseHouseResult baseHouseResult : BaseLivings) {
                baseLivingVo = new BaseLivingVo();
                baseLivingVo.setId(baseHouseResult.getLivingId());
                baseLivingVo.setLivingName(baseHouseResult.getLivingName());
                baseLivingVo.setAreaStr(baseHouseResult.getDistrictName());
                baseLivingVo.setHouseCount(baseHouseResult.getHouseCount());;
                baseLivingList.add(baseLivingVo);
            }

        }
        return baseLivingList;
    }

    public static List<BaseLivingVo> convertTobaseLivingVo2(List<BaseLiving> BaseLivings) {
        BaseLivingVo baseLivingVo = null;
        List<BaseLivingVo> baseLivingList = null;
        if (null != BaseLivings) {
            baseLivingList = new ArrayList<BaseLivingVo>(BaseLivings.size());
            for (BaseLiving baseLiving : BaseLivings) {
                baseLivingVo = new BaseLivingVo();
                baseLivingVo.setId(baseLiving.getId());
                baseLivingVo.setLivingCode(baseLiving.getLivingCode());
                baseLivingVo.setLivingName(baseLiving.getLivingName());
                baseLivingVo.setAreaStr(baseLiving.getAreaStr());

                baseLivingList.add(baseLivingVo);
            }

        }
        return baseLivingList;
    }

    /**
     * 转换为BaseLiving对象
     *
     * @return
     */
    public static BaseLiving parseToBaseLiving(BaseLivingVo baseLivingVo) {
        BaseLiving baseLiving = null;
        if (null != baseLivingVo) {
            baseLiving = new BaseLiving();
            baseLiving.setId(baseLivingVo.getId());
            baseLiving.setLivingCode(baseLivingVo.getLivingCode());
            baseLiving.setLivingName(baseLivingVo.getLivingName());
            baseLiving.setAreaStr(baseLivingVo.getAreaStr());
        }
        return baseLiving;
    }

    /**
     * 转换为 BaseLivingSearch 对象
     *
     * @param baseLivingVo
     * @return
     */
    public static BaseLivingSearch parseToBaseLivingSearch(BaseLivingVo baseLivingVo, PageModel pageModel) {
        BaseLivingSearch baseLivingSearch = null;
        if (null != baseLivingVo) {
            baseLivingSearch = new BaseLivingSearch();
            baseLivingSearch.setLivingName(baseLivingVo.getLivingName());
            baseLivingSearch.setSch_AreaLongCode_(baseLivingVo.getSch_AreaLongCode_());
            baseLivingSearch.setSch_LivingName_(EscapeUnescape.unescape(baseLivingVo.getSch_LivingName_()));
        }
        if (null != pageModel && 0 != pageModel.getPageSize()) {
            baseLivingSearch.setCurrentPage(pageModel.getCurPage());
            baseLivingSearch.setPageSize(pageModel.getPageSize());
        } else {
            //加载默认分页参数
            baseLivingSearch.setPageSize(PageModel.DEFAULT_PAGE_PAGESIZE);
        }
        return baseLivingSearch;
    }


}
