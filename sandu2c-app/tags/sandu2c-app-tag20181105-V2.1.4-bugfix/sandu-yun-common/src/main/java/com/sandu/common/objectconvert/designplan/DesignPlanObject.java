package com.sandu.common.objectconvert.designplan;

import com.sandu.common.model.PageModel;
import com.sandu.designplan.model.DesignPlan;
import com.sandu.designplan.vo.DesignPlanVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengxuangang
 * @desc 设计方案对象转换
 * @date 20170925
 */
public class DesignPlanObject {

    //转换为DesignPlan对象
    public static DesignPlan parseToDesignPlan(DesignPlanVo designPlanVo, PageModel pageModel) {

        DesignPlan designPlan = null;
        if (null != designPlanVo) {
            //初始化对象
            designPlan = new DesignPlan();
            //装入数据
            designPlan.setId(designPlanVo.getId());
            designPlan.setDesignId(designPlanVo.getDesignPlanId());
            designPlan.setPlanCode(designPlanVo.getDesignPlanCode());
            designPlan.setPlanName(designPlanVo.getDesignPlanName());
            designPlan.setDesignStyleId(designPlanVo.getDesignPlanStyleId());
            designPlan.setPicId(designPlanVo.getDesignPlanPicId());
            designPlan.setPicPath(designPlanVo.getDesignPlanPicPath());
            designPlan.setPlanDesc(designPlanVo.getDesignPlanDesc());
            designPlan.setSpaceAreas(designPlanVo.getDesignPlanAreas());
            designPlan.setSpaceCommonId(designPlanVo.getDesignPlanSpaceType());
            designPlan.setSpaceFunctionId(designPlanVo.getSpaceFunctionId());
        }

        if (null != pageModel && 0 != pageModel.getPageSize()) {
            designPlan.setCurrentPage(pageModel.getCurPage());
            designPlan.setPageSize(pageModel.getPageSize());
        } else {
            //加载默认分页参数
            designPlan.setPageSize(PageModel.DEFAULT_PAGE_PAGESIZE);
        }

        return designPlan;
    }

    //转换为DesignPlanVo对象
    public static List<DesignPlanVo> convertToDesignPlanVo(List<DesignPlan> designPlanList) {

        //初始化对象
        DesignPlanVo designPlanVo = null;
        List<DesignPlanVo> designPlanVoList = null;

        if (null != designPlanList) {
            designPlanVoList = new ArrayList<>(designPlanList.size());
            for (DesignPlan designPlan : designPlanList) {

                //初始化对象
                designPlanVo = new DesignPlanVo();
                //装入数据
                designPlanVo.setId(designPlan.getId());
                designPlanVo.setDesignPlanId(designPlan.getDesignPlanId());
                designPlanVo.setDesignPlanCode(designPlan.getPlanCode());
                designPlanVo.setDesignPlanName(designPlan.getPlanName());
                designPlanVo.setDesignPlanStyleId(designPlan.getDesignStyleId());
                designPlanVo.setDesignPlanPicId(designPlan.getPicId());
                designPlanVo.setDesignPlanPicPath(designPlan.getPicPath());
                designPlanVo.setDesignPlanDesc(designPlan.getPlanDesc());
                designPlanVo.setDesignPlanAreas(designPlan.getSpaceAreas());
                designPlanVo.setDesignPlanSpaceType(designPlan.getSpaceCommonId());
                designPlanVo.setSpaceFunctionId(designPlan.getSpaceFunctionId());


                designPlanVoList.add(designPlanVo);
            }
        }

        return designPlanVoList;
    }

    //转换为DesignPlanVo对象
    public static DesignPlanVo convertToDesignPlanVo(DesignPlan designPlan) {

        //初始化对象
        DesignPlanVo designPlanVo = null;

        if (null != designPlan) {
            //初始化对象
            designPlanVo = new DesignPlanVo();
            //装入数据
            designPlanVo.setId(designPlan.getId());
            designPlanVo.setDesignPlanId(designPlan.getDesignPlanId());
            designPlanVo.setDesignPlanCode(designPlan.getPlanCode());
            designPlanVo.setDesignPlanName(designPlan.getPlanName());
            designPlanVo.setDesignPlanStyleId(designPlan.getDesignStyleId());
            designPlanVo.setDesignPlanPicId(designPlan.getPicId());
            designPlanVo.setDesignPlanPicPath(designPlan.getPicPath());
            designPlanVo.setDesignPlanDesc(designPlan.getPlanDesc());
            designPlanVo.setDesignPlanAreas(designPlan.getSpaceAreas());
            designPlanVo.setDesignPlanSpaceType(designPlan.getSpaceCommonId());
            designPlanVo.setSpaceFunctionId(designPlan.getSpaceFunctionId());
        }

        return designPlanVo;
    }
}
