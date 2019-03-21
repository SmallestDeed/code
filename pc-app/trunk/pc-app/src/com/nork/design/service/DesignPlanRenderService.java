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

import com.nork.common.model.LoginUser;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.DesignPlanRes;
import com.nork.design.model.DesignPlanResRenderScene;
import com.nork.design.model.RenderPicVO;

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
public interface DesignPlanRenderService {
    /**
     * 修改副本信息的名称
     * <p>
     * updataBakSceneName(这里用一句话描述这个方法的作用)
     *
     * @param designPlanRenderScene
     * @return void    返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public void updataBakSceneName(DesignPlanRenderScene designPlanRenderScene);

    /**
     * allownFreeRender 判断一个用户，是否能够免费渲染（允许渲染的次数可配置）
     *
     * @param
     * @param designPlanId
     * @param userId
     * @return boolean    返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public boolean allownFreeRender(Integer designPlanId, Integer userId);

    /**
     * getDesignPlanRes根据设计方案id获取设计方案关联的所有资源（除渲染资源）
     *
     * @param designPlanId
     * @return DesignPlanRes 返回类型
     * @author huangsongbo
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public DesignPlanRes getDesignPlanRes(long designPlanId);

    /**
     * saveAsRenderBakScene
     * 设计方案所有资源另存为副本(除渲染资源)
     *
     * @param designPlanRes
     * @return int 返回类型
     * @author huangsongbo
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public long saveAsRenderBakScene(DesignPlanRes designPlanRes);

    /**
     * getRenderBakScene 根据设计渲染时的缩略图id，获取副本所有资源（除渲染资源）
     *
     * @param thumbId
     * @return DesignPlanRes 返回类型
     * @author huangsongbo
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public DesignPlanRes getRenderBakScene(long thumbId);

    /**
     * getLatestRenderBakScene得到设计方案最近渲染保存的副本id
     *
     * @param designId
     * @return long    返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public long getLatestRenderBakScene(long designId);

    /**
     * 根据设计方案id得到最新的缩略图id（可能有并发问题）
     * <p>
     * getLatestThumbId(这里用一句话描述这个方法的作用)
     *
     * @param designId
     * @return long    返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public long getLatestThumbId(long designId);

    /**
     * saveAsTempDesignPaln4RenderBakScene 副本所有资源（除渲染资源）另存为临时设计方案（用户不可见），进入的是用户自己的
     *
     * @param designPlanRes
     * @param userId        用户id
     * @return long 返回类型
     * @author huangsongbo
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public long saveAsTempDesignPaln4RenderBakScene(DesignPlanRes designPlanRes, long userId);

    /**
     * deleteTempDesignPaln4RenderBakScene删除指定用户名下的，为副本创建的临时设计方案（可能需要定时任务来销毁冗余数据）
     *
     * @param TempDesignPalnId 缩略图id
     * @param userId           用户id
     * @return void 返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public void deleteTempDesignPaln4RenderBakScene(long TempDesignPalnId, long userId);

    /**
     * changeTempDesignPalnVisible为副本创建的临时设计方案设置为可见
     *
     * @param tempDesignPalnId 设计方案id
     * @return void 返回类型
     * @author huangsongbo
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public void changeTempDesignPalnVisible(long planId);

    /**
     * associatedRenderResWithBakScene渲染图和副本建立关联关系
     *
     * @param resId      缩略图id
     * @param bakSceneId
     * @param loginName
     * @return void 返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public void associatedRenderResWithBakScene(long resId, long bakSceneId, String loginName);

    /**
     * checkModify4DesignPlan对设计方案和最近的创建的副本做比较，是否有更改判断
     *
     * @param designPlanId
     * @return boolean 返回类型  返回 true:有修改，返回false：没有修改
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public boolean checkModify4DesignPlan(long designPlanId);

    /**
     * checkModify4BakScene对副本创建的临时设计方案和该副本比较，是否有改变
     *
     * @param planId@return
     * @return boolean 返回类型 返回 true:有修改，返回false：没有修改
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public boolean checkModify4BakScene(long designPlanId, long designScenePlanId);

    /**
     * checkExistBakScene4DesignPlan查看一个设计方案是否存在渲染的场景
     *
     * @param designPlanId
     * @return boolean 返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public boolean checkExistBakScene4DesignPlan(long designPlanId);

    /**
     * getRenderResPage效果图分页获取
     *
     * @param page
     * @param pageSize
     * @return List 返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public List getRenderResPage(int page, int pageSize);

    /**
     * getTempDesignPalnId4BakScene 根据渲染时保存的场景id，复制一个临时的设计方案副本到designplan表，并返回id
     * 需要调用saveAsTempDesignPaln4RenderBakScene接口
     *
     * @param bakSceneId
     * @return void    返回类型
     * @author huangsongbo
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public long getTempDesignPalnId4BakScene(long bakSceneId);

    /**
     * processAfterRender 处理渲染后建立副本等一系列任务
     *
     * @param renderPic
     * @return void    返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public void processAfterRender(RenderPicVO renderPic);

    /**
     * 一定会复制一个副本，返回一个副本id的方法
     *
     * @param renderPic
     * @return
     */
    public long processAfterRender2(RenderPicVO renderPic);

    /**
     * isInvisible4Render判断一个设计方式，是否是渲染场景对应的临时方案，且不可见
     *
     * @param designPlanId
     * @return boolean    返回类型
     * @author huangsongbo
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public boolean isInvisible4Render(long designPlanId);

    /**
     * verifyDesignPlanRes 验证DesignPlanRes的有效性
     *
     * @param thumbPicId
     * @param designPlanRes
     * @return boolean    返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public boolean verifyDesignPlanRes(long thumbPicId, DesignPlanRes designPlanRes);

    /**
     * 副本资源(DesignPlanResRenderScene)转化为设计方案资源(DesignPlanRes)
     *
     * @param designPlanResRenderScene
     * @return
     * @author huangsongbo
     */
    public DesignPlanRes getDesignPlanResByDesignPlanResRenderScene(DesignPlanResRenderScene designPlanResRenderScene);

    /**
     * 保存设计方案相关的所有信息(DesignPlanRes)
     *
     * @param designPlanRes
     * @return
     */
    public long saveAsRender(DesignPlanRes designPlanRes);

    /**
     * existTempDesignPaln( 判断该用户名下是否存在指定的副本的临时方案，如果存在，直接返回临时方案id)
     *
     * @param thumbId
     * @param userId
     * @return long    返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public long existTempDesignPaln(long thumbId, int userId);


    /**
     * 草图方案输出效果图接口
     *
     * @param designPlanId   草图方案id
     * @param designPlanName 效果图方案名称
     * @param planDesc       效果图方案描述
     * @param styleId        效果图方案风格
     * @param renderType     效果图方案类型
     * @param renderPics     选择的渲染信息（id,id.....）
     * @param loginUser      登录用户
     * @return
     * @author chenqiang
     * @date 2018/8/13 0013 10:36
     */
    int createDesignPlanRenderScene(String designPlanId, String designPlanName, String planDesc, String styleId, String renderType, String renderPics, LoginUser loginUser);

    List<Integer> autocreateDesignPlanRenderScene(List<DesignPlanRes> designPlanResList, LoginUser loginUser);



}
