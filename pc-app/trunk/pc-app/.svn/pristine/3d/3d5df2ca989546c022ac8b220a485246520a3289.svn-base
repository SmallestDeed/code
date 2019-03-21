/**    
 * 文件名：SysUserGroupController.java    
 *    
 * 版本信息：    
 * 日期：2017-7-6    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlanGroup;
import com.nork.design.model.ResRenderPicGroupRef;
import com.nork.design.model.SysBusinessGroup;
import com.nork.design.model.ThumbData;
import com.nork.system.model.SysUserGroup;
import com.nork.system.model.UserLevelCfg;
import com.nork.system.service.SysResLevelCfgService;
import com.nork.system.service.SysUserGroupService;

/**
 * 
 * 项目名称：timeSpace 类名称：SysUserGroupController 类描述： 用户自定义分组 创建人：Timy.Liu
 * 创建时间：2017-7-6 上午11:56:29 修改人：Timy.Liu 修改时间：2017-7-6 上午11:56:29 修改备注：
 * 
 * @version
 * 
 */
@Controller
@RequestMapping(value = "/{style}/group")
public class SysUserGroupController {
    private static Logger logger = Logger.getLogger(SysUserGroupController.class);
    private final String SERVERURL = Utils.getValue("app.server.url","");
    private final String RENDERSERVICEURL = Utils.getValue("app.render.server.url","http://render.sanduspace.com");
    @Autowired
    private SysUserGroupService sysUserGroupService;
    @Autowired
    private  SysResLevelCfgService sysResLevelCfgService;
    /**
     * 
     * 
     * listGroup 列表显示用户对应的自定义分组
     * 
     * @param @param request
     * @param @param response
     * @param @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象 http://localhost:8080/timeSpace/group/list.htm?bid=
     *            c205312eb64e4ac0af33cdb1de6e812a&uid=123
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping(value = "/listGroup")
    @ResponseBody
    public ResponseEnvelope listGroup(HttpServletRequest request, HttpServletResponse response) {
        ResponseEnvelope envelope = new ResponseEnvelope();

        String sartIndex = request.getParameter("st");// 分页起始页，第一页传0
        String pageSize = request.getParameter("pz");// 每页显示多少条，最大不能超过20页

        String name = request.getParameter("nm");//搜索条件(组名/描述)
        String msgId = request.getParameter("msgId");
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            envelope.setMsgId(msgId);
            return envelope;
        }

        int start = 0;
        int size = 20;
        if (StringUtils.isNotEmpty(pageSize) && StringUtils.isNumeric(pageSize)) {
            size = Integer.valueOf(pageSize) > 20 ? 20 : Integer.valueOf(pageSize);// 最多二十条
        }
        if (size == 0)
            size = 20;

        if (StringUtils.isNotEmpty(sartIndex) && StringUtils.isNumeric(sartIndex)) {
            start = Integer.valueOf(sartIndex);
        }

        int userId = loginUser.getId();
        SysUserGroup sysUserGroup = new SysUserGroup();
        sysUserGroup.setUserId(userId);
        sysUserGroup.setName(name);
        sysUserGroup.setStart(start);
        sysUserGroup.setPageSize(size);

        envelope =  sysUserGroupService.listSysuserGroup(sysUserGroup);
        envelope.setMsgId(msgId);
        
        return envelope;
    }

    /**
     * 
     * 
     * addGroup新增用户自定义分组
     * 
     * @param @param request
     * @param @param response
     * @param @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象
     *            http://localhost:8080/timeSpace/group/add.htm?nm=test&uid=123
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public ResponseEnvelope addGroup(HttpServletRequest request, HttpServletResponse response) {

        ResponseEnvelope envelope = new ResponseEnvelope();

        String name = request.getParameter("nm");// 分组名称 长度校验/每个人创建的分组应该有限制
        String msgId = request.getParameter("msgId");
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }

        int userId = loginUser.getId();

        if (StringUtils.isEmpty(name)) {
            envelope.setMessage("params error");
            envelope.setSuccess(false);
            return envelope;
        }
        if(name.trim().length() > 24){
        	envelope.setMessage("分组名超出24个字节");
            envelope.setSuccess(false);
            return envelope;
        }
        SysUserGroup sysUserGroup = new SysUserGroup();
        sysUserGroup.setName(name);
        sysUserGroup.setUserId(userId);

        boolean result = sysUserGroupService.insertSysuserGroup(sysUserGroup);
        envelope.setSuccess(result);
        return envelope;
    }

    /**
     * 
     * 
     * updateGroup修改用户自定义分分组
     * http://localhost:8080/timeSpace/group/update.htm?bid
     * =c205312eb64e4ac0af33cdb1de6e812a&uid=123&nm=test
     * 
     * @param @param request
     * @param @param response
     * @param @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseEnvelope updateGroup(HttpServletRequest request, HttpServletResponse response) {

        ResponseEnvelope envelope = new ResponseEnvelope();

        String name = request.getParameter("nm"); 
        String bid = request.getParameter("bid");
        String description = request.getParameter("dp");
        String msgId = request.getParameter("msgId");
        envelope.setMsgId(msgId);
         
        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        if(StringUtils.isEmpty(name)){
        	envelope.setSuccess(false);
            envelope.setMessage("分组名不能为空");
            return envelope;
        }
        if(StringUtils.isEmpty(name.trim())){
        	envelope.setSuccess(false);
            envelope.setMessage("分组名不能为空");
            return envelope;
        }
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        int userId = loginUser.getId();

        if (StringUtils.isEmpty(bid)) {
            envelope.setMessage("params error");
            envelope.setSuccess(false);
            return envelope;
        }
 
        if(name.trim().length() > 24){
        	envelope.setMessage("分组名超出24个字节");
            envelope.setSuccess(false);
            return envelope;
        }
        //
        SysBusinessGroup sysBusinessGroup = sysUserGroupService.getBusinessGroupByBid(bid);
        if(sysBusinessGroup == null){
        	envelope.setMessage("该分组被删除，请刷新页面");
            envelope.setSuccess(false);
            return envelope;
        }
        if(sysBusinessGroup.getUserId()!=loginUser.getId().intValue()){
        	envelope.setMessage("无法操作他人分组");
            envelope.setSuccess(false);
            return envelope;
        }
        SysUserGroup sysUserGroup = new SysUserGroup();
        // 判断组名是否存在
        SysBusinessGroup sysBusinessGroupSearch = new SysBusinessGroup();
        sysBusinessGroupSearch.setUserId(userId);
        sysBusinessGroupSearch.setName(name);
        int count = sysUserGroupService.getCount(sysBusinessGroupSearch);
        if(count > 1){
        	envelope.setMessage("操作失败");
            envelope.setSuccess(false);
            return envelope;
        }
        if(count == 1){
        	if(!bid.equals(sysBusinessGroup.getBid())){
        		envelope.setMessage("操作失败");
                envelope.setSuccess(false);
                return envelope;
        	}
        	sysUserGroup.setName(name);
        }
        if(count < 1){
        	sysUserGroup.setName(name);
        }
        if(StringUtils.isNotEmpty(description)){
        	sysUserGroup.setDescription(description);
        }
        sysUserGroup.setUserId(userId);
        sysUserGroup.setBid(bid);
        boolean result = sysUserGroupService.updateSysuserGroup(sysUserGroup);
        envelope.setSuccess(result);
        envelope.setObj(sysUserGroup);
        return envelope;
    }

    /**
     * 
     * 
     * delGroup删除用户自定义的分组
     * 
     * @param @param request
     * @param @param response
     * @param @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象 http://localhost:8080/timeSpace/group/del.htm?bid=1232&msgId=234
     *            c205312eb64e4ac0af33cdb1de6e812a&uid=123
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping(value = "/del")
    @ResponseBody
    public ResponseEnvelope delGroup(HttpServletRequest request, HttpServletResponse response) {

        ResponseEnvelope envelope = new ResponseEnvelope();

        String bid = request.getParameter("bid");
        String msgId = request.getParameter("msgId");
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        int userId =loginUser.getId();

        if (StringUtils.isEmpty(bid)) {
            envelope.setMessage("params error");
            envelope.setSuccess(false);
            return envelope;
        }

        SysUserGroup sysUserGroup = new SysUserGroup();
        sysUserGroup.setUserId(userId);
        sysUserGroup.setBid(bid);

        sysUserGroupService.deleteSysuserGroup(sysUserGroup);
        envelope.setSuccess(true);
        return envelope;
    }

    /**
     * 根据业务id查询对应的分组图集（全景图和多点全景图）
     * 
     * getGroup(这里用一句话描述这个方法的作用)
     * 
     * @param request
     * @param response
     * @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象 http://localhost:8080/timeSpace/group/get.htm?bid=
     *            c205312eb64e4ac0af33cdb1de6e812a&uid=123
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping(value = "/get")
    @ResponseBody
    public ResponseEnvelope getGroup(HttpServletRequest request, HttpServletResponse response) {

        ResponseEnvelope envelope = new ResponseEnvelope();
        String sartIndex = request.getParameter("st");// 分页起始页，第一页传0
        String pageSize = request.getParameter("pz");// 每页显示多少条，最大不能超过20页

        String bid = request.getParameter("bid");
        String msgId = request.getParameter("msgId");
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        int userId = loginUser.getId();

        if (StringUtils.isEmpty(bid)) {
            envelope.setMessage("params error");
            envelope.setSuccess(false);
            return envelope;
        }

        SysUserGroup sysUserGroup = new SysUserGroup();
        sysUserGroup.setUserId(userId);
        sysUserGroup.setBid(bid);
        sysUserGroup.setStart(Integer.parseInt(sartIndex));
        sysUserGroup.setPageSize(Integer.parseInt(pageSize));

        envelope = sysUserGroupService.getSysuserGroupByBid(sysUserGroup);
        envelope.setMsgId(msgId);
        return envelope;
    }

    /**
     * 
     * 
     * movein设计方案移入到分组
     * 
     * @param request
     * @param response
     * @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象
     *            http://localhost:8080/timeSpace/group/movein.htm?did=85911
     *            &gid=c8d8bf13a84b4950b5b809afdcc9460b&tt=720
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/movein")
    @ResponseBody
    public ResponseEnvelope movein(HttpServletRequest request, HttpServletResponse response) {

        ResponseEnvelope envelope = new ResponseEnvelope();
        String designPlanRenderSceneIds = request.getParameter("dprsIds");// 副本id
        String name = request.getParameter("nm");	// 标题
        String description = request.getParameter("desc");	// 描述
        String msgId = request.getParameter("msgId");
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }

        int userId = loginUser.getId();
        if (StringUtils.isEmpty(designPlanRenderSceneIds)) {
            envelope.setMessage("请勾选方案！");
            envelope.setSuccess(false);
            return envelope;
        }

        if (StringUtils.isEmpty(name)) {
            envelope.setMessage("params error");
            envelope.setSuccess(false);
            return envelope;
        }

        if(name.trim().length() > 24){
        	envelope.setMessage("分组名超出24个字节");
            envelope.setSuccess(false);
            return envelope;
        }
        
        DesignPlanGroup DesignPlanGroup = new DesignPlanGroup();
        DesignPlanGroup.setUserId(userId);
        DesignPlanGroup.setName(name);
        DesignPlanGroup.setDescription(description);
        DesignPlanGroup.setDesignPlanRenderSceneIds(designPlanRenderSceneIds);
        envelope =  sysUserGroupService.movein(DesignPlanGroup);
        envelope.setMsgId(msgId);
        return envelope;
    }

    
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/moveinCheck")
    @ResponseBody
    public ResponseEnvelope moveinCheck (HttpServletRequest request, HttpServletResponse response) {
    	String designPlanRenderSceneIds = request.getParameter("dprsIds");// 副本id
    	String msgId = request.getParameter("msgId");
    	if (StringUtils.isEmpty(designPlanRenderSceneIds)) {
    		return new ResponseEnvelope(false,"请勾选方案！",msgId);
        }
    	Map<String,String>resMap = new HashMap<String,String>();
    	resMap = sysUserGroupService.moveinCheck(designPlanRenderSceneIds);
    	if(resMap == null ||resMap.size()<=0){
        	return new ResponseEnvelope(false,"操作失败",msgId);
		}else if (!"true".equals(resMap.get("success"))) {
			return new ResponseEnvelope(false,resMap.get("data"),msgId);
		}
    	return new ResponseEnvelope(true,"ok",msgId);
    }

    
    /**
     * 
     * 
     * moveout 设计方案移出到分组
     * 
     * @param request
     * @param response
     * @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象 http://localhost:8080/timeSpace/group/moveout.htm?bid=9
     *            a8e2bf08065429c90980c62183798f2
     *            &gid=7e9cc4f1a5054e40bb7cd01d9dd6097e
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping(value = "/moveout")
    @ResponseBody
    public ResponseEnvelope moveout(HttpServletRequest request, HttpServletResponse response) {

        ResponseEnvelope envelope = new ResponseEnvelope();
        String designPlanGroupBid = request.getParameter("bid");// 设计方案计划业务id
        String msgId = request.getParameter("msgId");
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }

        if (StringUtils.isEmpty(designPlanGroupBid)) {
            envelope.setMessage("params error");
            envelope.setSuccess(false);
            return envelope;
        }

        DesignPlanGroup DesignPlanGroup = new DesignPlanGroup();
        DesignPlanGroup.setBid(designPlanGroupBid);

        ResRenderPicGroupRef resRenderPicGroupRef = sysUserGroupService.getGroupRefByBid(designPlanGroupBid);
        if(resRenderPicGroupRef.getGid()!=null){
        	SysBusinessGroup sysBusinessGroup = sysUserGroupService.getBusinessGroupByBid(resRenderPicGroupRef.getGid());
            if(sysBusinessGroup == null){
            	envelope.setMessage("该分组被删除，请刷新页面");
                envelope.setSuccess(false);
                return envelope;
            }
            if(sysBusinessGroup.getUserId()!=loginUser.getId().intValue()){
            	envelope.setMessage("无法操作他人分组");
                envelope.setSuccess(false);
                return envelope;
            }
        }else{
        	envelope.setMessage("数据错误");
            envelope.setSuccess(false);
            return envelope;
        }
        if(resRenderPicGroupRef!=null && resRenderPicGroupRef.getGid()!=null){
        	DesignPlanGroup designPlanGroupSearch = new DesignPlanGroup();
        	designPlanGroupSearch.setGid(resRenderPicGroupRef.getGid());
        	List<DesignPlanGroup>list = sysUserGroupService.getDesignPlans(designPlanGroupSearch);
        	if(list!=null && list.size()<=1){
        		envelope.setMessage("最后一条记录 无法删除！");
                envelope.setSuccess(false);
                return envelope;
        	}
        }
        boolean result = sysUserGroupService.moveout(DesignPlanGroup);
        envelope.setSuccess(result);

        return envelope;
    }

    /**
     * 
       
     * addShareTimes组发生分享时候的计数
       
     * @param request
     * @param response
     * @return 
    
     * @return ResponseEnvelope    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping(value = "/addShare")
    @ResponseBody
    public ResponseEnvelope addShareTimes(HttpServletRequest request, HttpServletResponse response) {

        ResponseEnvelope envelope = new ResponseEnvelope();

        String group_businessId = request.getParameter("gid");// 分组业务id
        String msgId = request.getParameter("msgId");
        logger.info(group_businessId + "_be_shared");
        if (StringUtils.isEmpty(msgId)) {
        	 envelope.setMessage("params error");
             envelope.setSuccess(false);
             return envelope;
        }
        if (StringUtils.isEmpty(group_businessId)) {
            envelope.setMessage("params error");
            envelope.setSuccess(false);
            return envelope;
        }
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        /*String qrCodeUrl = sysUserGroupService.getQrCode(group_businessId,loginUser.getId());*/// 最近三天做缓存，三天后自动清空
       /* String qrCodeUrl = SERVERURL + Utils.getValue("app.server.siteName", "").trim() //直接返回访问路径，不在返回2维码
      			 + "pages/vr720/vr720Group.htm?code=" + group_businessId;*/
        String qrCodeUrl = RENDERSERVICEURL + "/pages/vr720/vr720Group.htm?code=" + group_businessId;
        // 加分享日志统计，关联表中的对应统计字段加一
        envelope.setSuccess(true);
        envelope.setObj(qrCodeUrl);
        envelope.setMsgId(msgId);
        return envelope;
    }
    
    /**
     * 列表分页显示自己名下所有的,不在指定分组的全景图
     * 
     * list720  选择多点渲染图到分组
     * 
     * @param request
     * @param response
     * @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping(value = "/list720")
    @ResponseBody
    public ResponseEnvelope list720(HttpServletRequest request, HttpServletResponse response) {

        ResponseEnvelope envelope = new ResponseEnvelope();
        String sartIndex = request.getParameter("st");// 分页起始页，第一页传0
        String pageSize = request.getParameter("pz");// 每页显示多少条，最大不能超过20页
        String name = request.getParameter("nm");// 查询名称
        String gid = request.getParameter("gid");// 分组业务id
        String msgId = request.getParameter("msgId");
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        int userId = loginUser.getId();
        int start = 0;
        int size = 20;
        if (StringUtils.isNotEmpty(pageSize) && StringUtils.isNumeric(pageSize)) {
            size = Integer.valueOf(pageSize) > 20 ? 20 : Integer.valueOf(pageSize);// 最多二十条
        }
        if (size == 0)
            size = 20;

        if (StringUtils.isNotEmpty(sartIndex) && StringUtils.isNumeric(sartIndex)) {
            start = Integer.valueOf(sartIndex);
        }

        int group_bid=0;
        if(StringUtils.isNotEmpty(gid) && StringUtils.isNumeric(gid))
            group_bid = Integer.valueOf(gid);
        
        ThumbData query = new ThumbData();
        query.setUserId(userId);
        query.setName(name);
        query.setStart(start);
        query.setPageSize(size);
        query.setThumbId(group_bid);
        envelope= sysUserGroupService.list720(query);
        // 加分享日志统计
        envelope.setMsgId(msgId);

        return envelope;
    }

    /**
     *

     * addShareTimes获取组的分享次数

     * @param request
     * @param response
     * @return

     * @return ResponseEnvelope    返回类型

     * @Exception 异常对象

     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping(value = "/getShare")
    @ResponseBody
    public ResponseEnvelope getShareTimes(HttpServletRequest request, HttpServletResponse response) {

        ResponseEnvelope envelope = new ResponseEnvelope();

        String group_businessId = request.getParameter("gid");// 分组业务id
        String msgId = request.getParameter("msgId");

        logger.info(group_businessId + "_be_shared");

        if (StringUtils.isEmpty(group_businessId) || StringUtils.isEmpty(msgId)) {
            envelope.setMessage("params error");
            envelope.setSuccess(false);
            return envelope;
        }

        envelope = sysUserGroupService.getShareList(group_businessId);
        envelope.setSuccess(true);
        envelope.setMsgId(msgId);

        return envelope;
    }
    /**
     *

     * getGroupDetail获取分享列表中组的详情（点击组的查看按钮）

     * @param request
     * @param response
     * @return

     * @return ResponseEnvelope    返回类型

     * @Exception 异常对象

     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping( value = "/getGroupDetail")
    @ResponseBody
    public ResponseEnvelope getGroupDetail(HttpServletRequest request, HttpServletResponse response) {

        ResponseEnvelope envelope = new ResponseEnvelope();

        String group_businessId = request.getParameter("gid");// 分组业务id
        String msgId = request.getParameter("msgId");

        logger.info(group_businessId + "_be_shared");
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        if (StringUtils.isEmpty(group_businessId) || StringUtils.isEmpty(msgId)) {
            envelope.setMessage("params error");
            envelope.setSuccess(false);
            return envelope;
        }

        SysUserGroup sysUserGroup = new SysUserGroup();
        sysUserGroup.setUserId(loginUser.getId());
        sysUserGroup.setBid(group_businessId);
        envelope = sysUserGroupService.getGroupDetail(sysUserGroup);
        envelope.setMsgId(msgId);

        return envelope;
    }


 /**
  * 已经选择的分组效果图列表
  * @param request
  * @param response
  * @return
  */
    @RequestMapping(value = "/selectRenderList")
    @ResponseBody
    public ResponseEnvelope<ThumbData> selectRenderList(HttpServletRequest request, HttpServletResponse response) {
    	ResponseEnvelope<ThumbData> envelope = new ResponseEnvelope<ThumbData>();
    	String bid = request.getParameter("bid");
        String msgId = request.getParameter("msgId");
        envelope.setMsgId(msgId);
        if(!this.checkParam(bid,msgId)){
        	envelope.setMessage("params error");
            envelope.setSuccess(false);
            return envelope;
        }

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser.getId()<0) {
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        int userId =loginUser.getId();
        envelope = sysUserGroupService.selectRenderList(bid,userId);
        envelope.setMsgId(msgId);
    	return envelope;
    }


    /**
	 * 参数完整性判断
	 * @param args
	 * @return
	 */
	private boolean checkParam(String... args) {
		boolean result = true;
		for(String arg :args) {
			if(StringUtils.isEmpty(arg)) {
				result = false;
				return result;
			}
		}
		return result;
	}

    /**
     *

     * getOnePicDetail获取单张图片的详情

     * @param request
     * @param response
     * @return

     * @return ResponseEnvelope    返回类型

     * @Exception 异常对象

     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping( value = "/getOnePicDetail")
    @ResponseBody
    public ResponseEnvelope getOnePicDetail(HttpServletRequest request, HttpServletResponse response) {

        ResponseEnvelope envelope = new ResponseEnvelope();

        String thumId = request.getParameter("thumId");// 缩略图id
        String msgId = request.getParameter("msgId");

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        if (StringUtils.isEmpty(thumId) || StringUtils.isEmpty(msgId)) {
            envelope.setMessage("params error");
            envelope.setSuccess(false);
            return envelope;
        }
        Integer userId = loginUser.getId();
        envelope = sysUserGroupService.getOnePicDetail(Integer.parseInt(thumId),userId);
        envelope.setMsgId(msgId);

        return envelope;
    }
    /**
     * 
       
     * deleteUserLevelResCacher 方法描述：     由于用户级别和资源限制数量相关，更改数据库后，缓存需要手动更新
       
     * @return
    
     * @return ResponseEnvelope    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping( value = "/delUserLevelCacher")
    @ResponseBody
    public ResponseEnvelope deleteUserLevelResCacher() {
        JedisUtils.removeWithKeyPrefix(Constants.USER_LEVEL_LIMIT_CACHE_KEY);
        ResponseEnvelope envelope = new ResponseEnvelope();
        return envelope;
    }
}
