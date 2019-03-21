/**    
 * 文件名：DesignPalnRender.java    
 *    
 * 版本信息：    
 * 日期：2017-7-12    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.design.service;

import java.util.List;

import com.nork.design.model.DesignPlanRes;

/**
 * 
 * 项目名称：timeSpace 
 * 类名称：DesignPalnRender 
 * 类描述： 主要封装设计方案发生渲染时的场景，和从效果图进入场景的抽象方法
 * 创建人：Timy.Liu 
 * 创建时间：2017-7-12 下午5:54:41 
 * 修改人：Timy.Liu 
 * 修改时间：2017-7-12 下午5:54:41
 * 修改备注：
 * 
 * @version
 * 
 */
public abstract class DesignPalnRender {
    /**
     * 
     * 
     * getDesignPlanRes根据设计方案id获取设计方案关联的所有资源（除渲染资源）
     * 
     * @param designPlanId
     * @return
     * 
     * @return DesignPlanRes 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public abstract DesignPlanRes getDesignPlanRes(int designPlanId);

    /**
     * 
     * 
     * saveAsRenderBakScene设计方案所有资源另存为副本（除渲染资源）
     * 
     * @param designPlanRes
     * @return
     * 
     * @return int 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public abstract long saveAsRenderBakScene(DesignPlanRes designPlanRes);

    /**
     * 
     * 
     * getRenderBakScene 根据设计方案副本id，获取副本所有资源（除渲染资源）
     * 
     * @param bakSceneId
     * @return
     * 
     * @return DesignPlanRes 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public abstract DesignPlanRes getRenderBakScene(int bakSceneId);

    /**
     * 
     * 
     * saveAsTempDesignPaln4RenderBakScene 副本所有资源（除渲染资源）另存为临时设计方案（用户不可见）
     * 
     * @param designPlanRes
     * @return
     * 
     * @return long 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public abstract long saveAsTempDesignPaln4RenderBakScene(DesignPlanRes designPlanRes);

    /**
     * 
     * 
     * deleteTempDesignPaln4RenderBakScene删除为副本创建的临时设计方案（可能需要定时任务来销毁冗余数据）
     * 
     * @param tempDesignPalnId
     * 
     * @return void 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public abstract void deleteTempDesignPaln4RenderBakScene(long tempDesignPalnId);

    /**
     * 
     * 
     * changeTempDesignPalnVisible为副本创建的临时设计方案设置为可见
     * 
     * @param tempDesignPalnId
     * 
     * @return void 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public abstract void changeTempDesignPalnVisible(long tempDesignPalnId);

    /**
     * 
     * 
     * associatedRenderResWithBakScene渲染图和副本建立关联关系
     * 
     * @param resId
     * @param bakSceneId
     * 
     * @return void 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public abstract void associatedRenderResWithBakScene(long resId, long bakSceneId);

    /**
     * 
     * 
     * checkModify4DesignPlan设计方案是否有更改判断
     * 
     * @param designPlanId
     * @return
     * 
     * @return boolean 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public abstract boolean checkModify4DesignPlan(long designPlanId);

    /**
     * 
     * 
     * checkModify4BakScene为副本创建的临时设计方案是否有更改判断
     * 
     * @param bakSceneId
     * @return
     * 
     * @return boolean 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public abstract boolean checkModify4BakScene(long bakSceneId);

    /**
     * 
     * 
     * checkExistBakScene4DesignPlan查看一个设计方案是否存在渲染的场景
     * 
     * @param designPlanId
     * @return
     * 
     * @return boolean 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public abstract boolean checkExistBakScene4DesignPlan(long designPlanId);

    /**
     * 
     * 
     * getRenderResPage效果图分页获取
     * 
     * @param page
     * @param pageSize
     * @return
     * 
     * @return List 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public abstract List getRenderResPage(int page, int pageSize);

}
