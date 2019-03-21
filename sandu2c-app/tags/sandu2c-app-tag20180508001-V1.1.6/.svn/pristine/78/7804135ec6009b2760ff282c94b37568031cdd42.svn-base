package com.sandu.common.objectconvert.base;

import com.sandu.base.model.BaseArea;
import com.sandu.base.model.vo.BaseAreaVo;
import com.sandu.common.model.PageModel;
import org.codehaus.jackson.map.Serializers;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换省市对象
 *
 * @author chenm
 */
public class BaseAreaObject {

    /**
     * 转换为BaseAreaVo对象
     *
     * @param baseArea
     * @return
     */
    public static BaseAreaVo convertToBaseAreaVo(BaseArea baseArea) {
        BaseAreaVo baseAreaVo = null;

        if (baseArea != null) {
            baseAreaVo = new BaseAreaVo();
            //装入数据
            baseAreaVo.setAreaCode(baseArea.getAreaCode());
            baseAreaVo.setAreaName(baseArea.getAreaName());
            baseAreaVo.setLevelId(baseArea.getLevelId());
            baseAreaVo.setPid(baseArea.getPid());
        }
        return baseAreaVo;
    }

    /**
     * 转换为BaseAreaVo对象
     *
     * @param baseAreas
     * @return
     */
    public static List<BaseAreaVo> convertToBaseAreaVo(List<BaseArea> baseAreas) {
        BaseAreaVo baseAreaVos = null;
        List<BaseAreaVo> baseAreaVoList = null;
        if (null != baseAreas) {
            baseAreaVoList = new ArrayList<>(baseAreas.size());
            for (BaseArea baseArea : baseAreas) {
                baseAreaVos = new BaseAreaVo();
                baseAreaVos.setAreaCode(baseArea.getAreaCode());
                baseAreaVos.setAreaName(baseArea.getAreaName());
                baseAreaVos.setLevelId(baseArea.getLevelId());
                baseAreaVos.setPid(baseArea.getPid());
                baseAreaVoList.add(baseAreaVos);
            }

        }
        return baseAreaVoList;
    }


    /**
     * 转换为BaseArea对象
     *
     * @param baseAreaVo
     * @return
     */
    public static BaseArea parseToBaseArea(BaseAreaVo baseAreaVo, PageModel pageModel) {
        BaseArea baseArea = null;
        if (null != baseAreaVo) {
            baseArea = new BaseArea();
            baseArea.setAreaCode(baseAreaVo.getAreaCode());
            baseArea.setAreaName(baseAreaVo.getAreaName());
            baseArea.setPid(baseAreaVo.getPid());
            baseArea.setLevelId(baseAreaVo.getLevelId());
        }
        //分页
        if (null != pageModel && 0 != pageModel.getPageSize()) {
            baseArea.setCurrentPage(pageModel.getCurPage());
            baseArea.setPageSize(pageModel.getPageSize());
        } else {
            //加载默认分页参数
            baseArea.setPageSize(PageModel.DEFAULT_PAGE_PAGESIZE);
        }
        return baseArea;
    }

    /**
     * 转换为BaseArea对象
     * 无分页
     *
     * @param baseAreaVo
     * @return
     */
    public static BaseArea parseToBaseArea(BaseAreaVo baseAreaVo) {
        BaseArea baseArea = null;
        if (null != baseAreaVo) {
            baseArea = new BaseArea();
            baseArea.setAreaCode(baseAreaVo.getAreaCode());
            baseArea.setAreaName(baseAreaVo.getAreaName());
            baseArea.setPid(baseAreaVo.getPid());
            baseArea.setLevelId(baseAreaVo.getLevelId());
            //去除分页
            baseArea.setPageSize(-1);
            baseArea.setCurrentPage(-1);
        }
        return baseArea;
    }

    public static List<BaseAreaVo> convertToBaseAreaVoList(List<BaseArea> nList) {
        List<BaseAreaVo> baseAreaVoList = new ArrayList<>();
        if(null!=nList&&nList.size()>0){
            for (BaseArea baseArea : nList){
                BaseAreaVo baseAreaVoProvince = new BaseAreaVo();
                baseAreaVoProvince.setId(baseArea.getId());
                baseAreaVoProvince.setAreaCode(baseArea.getAreaCode());
                baseAreaVoProvince.setAreaName(baseArea.getAreaName());
                baseAreaVoProvince.setLevelId(baseArea.getLevelId());
                baseAreaVoProvince.setPid(baseArea.getPid());
                List<BaseAreaVo> baseAreaVoCityList = new ArrayList<>();
                if(null!=baseArea.getLowerArea()&&baseArea.getLowerArea().size()>0){
                    for (BaseArea baseAreaCity:baseArea.getLowerArea()){
                        BaseAreaVo baseAreaVoCity = new BaseAreaVo();
                        baseAreaVoCity.setId(baseAreaCity.getId());
                        baseAreaVoCity.setAreaCode(baseAreaCity.getAreaCode());
                        baseAreaVoCity.setAreaName(baseAreaCity.getAreaName());
                        baseAreaVoCity.setLevelId(baseAreaCity.getLevelId());
                        baseAreaVoCity.setPid(baseAreaCity.getPid());
                        List<BaseAreaVo> baseAreaVoStrictList = new ArrayList<>();
                        if(null!=baseAreaCity.getLowerArea()&&baseAreaCity.getLowerArea().size()>0){
                            for (BaseArea strict:baseAreaCity.getLowerArea()){
                                BaseAreaVo baseAreaVostrict = new BaseAreaVo();
                                baseAreaVostrict.setId(strict.getId());
                                baseAreaVostrict.setAreaCode(strict.getAreaCode());
                                baseAreaVostrict.setAreaName(strict.getAreaName());
                                baseAreaVostrict.setLevelId(strict.getLevelId());
                                baseAreaVostrict.setPid(strict.getPid());
                                baseAreaVoStrictList.add(baseAreaVostrict);
                            }
                        }
                        baseAreaVoCity.setBaseAreaVos(baseAreaVoStrictList);
                        baseAreaVoCityList.add(baseAreaVoCity);
                    }
                }
                baseAreaVoProvince.setBaseAreaVos(baseAreaVoCityList);
                baseAreaVoList.add(baseAreaVoProvince);
            }
        }
        return baseAreaVoList;
    }
}