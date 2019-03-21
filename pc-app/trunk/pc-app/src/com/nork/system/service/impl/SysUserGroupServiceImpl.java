/**    
 * 文件名：SysUserGroupServiceImpl.java    
 *    
 * 版本信息：    
 * 日期：2017-7-6    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.QRCode.MatrixToImageWriter;
import com.nork.design.dao.DesignPlanRenderSceneMapper;
import com.nork.design.model.DesignPlanGroup;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.Group;
import com.nork.design.model.GroupDetail;
import com.nork.design.model.ResRenderPicGroupRef;
import com.nork.design.model.SysBusinessGroup;
import com.nork.design.model.ThumbData;
import com.nork.product.service.BaseCompanyService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.controller.SysUserGroupController;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.dao.SysUserGroupMapper;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUserGroup;
import com.nork.system.model.vo.GroupPicVo;
import com.nork.system.model.vo.ShareDetailVo;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.SysUserGroupService;

/**
 * 
 * 项目名称：timeSpace 
 * 类名称：SysUserGroupServiceImpl 
 * 类描述： 用户自定义分组实现类 
 * 创建人：Timy.Liu
 * 创建时间：2017-7-6 上午10:51:59 
 * 修改人：Timy.Liu 
 * 修改时间：2017-7-6 上午10:51:59 
 * 修改备注：
 * 
 * @version
 * 
 */
@Service
public class SysUserGroupServiceImpl implements SysUserGroupService {
	private static Logger logger = Logger.getLogger(SysUserGroupServiceImpl.class);
	private final String SERVERURL = Utils.getValue("app.server.url","");
	private final String RENDERSERVICEURL = Utils.getValue("app.render.server.url","https://render.sanduspace.com");
	@Autowired
	ResRenderPicService resRenderPicService;
    @Autowired
    private SysUserGroupMapper sysUserGroupMapper;
    @Autowired
    private ResRenderPicMapper resRenderPicMapper;
    @Autowired
    private  DesignPlanRenderSceneMapper designPlanRenderSceneMapper;
    
    @Autowired
    private  BaseCompanyService baseCompanyService;
    /**
     * 
     * 
     * insertSysuserGroup添加用户的自定义分组
     * 
     * @param @param sysUserGroup
     * 
     * @return void 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @Override
    public boolean insertSysuserGroup(SysUserGroup sysUserGroup) {
        if (sysUserGroup == null || StringUtils.isEmpty(sysUserGroup.getName()) || sysUserGroup.getUserId() <= 0)
            return false;

        if (sysUserGroup.getName().trim().length() > 13)
            sysUserGroup.setName(sysUserGroup.getName().substring(0, 13)+"...");

        sysUserGroup.setBid(UUID.randomUUID().toString().replace("-", ""));
        sysUserGroupMapper.insertSysuserGroup(sysUserGroup);
        return true;
    }

    /**
     * 
     * 
     * updateSysuserGroup修改用户的自定义分组
     * 
     * @param @param sysUserGroup
     * 
     * @return void 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @Override
    public boolean updateSysuserGroup(SysUserGroup sysUserGroup) {
       if (sysUserGroup == null || StringUtils.isEmpty(sysUserGroup.getName()) || sysUserGroup.getUserId() <= 0
                || StringUtils.isEmpty(sysUserGroup.getBid())){
            return false;
        }
        if (sysUserGroup.getName().trim().length() > 24){
            sysUserGroup.setName(sysUserGroup.getName().substring(0, 24)+"...");
        }
        sysUserGroupMapper.updateSysuserGroup(sysUserGroup);
        return true;
    }

    /**
     * 
     * 
     * deleteSysuserGroup 删除用户的自定义分组
     * 
     * @param @param sysUserGroup
     * 
     * @return void 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @Override
    public void deleteSysuserGroup(SysUserGroup sysUserGroup) {
        if (sysUserGroup == null || sysUserGroup.getUserId() <= 0 || StringUtils.isEmpty(sysUserGroup.getBid()))
            return;

        sysUserGroupMapper.deleteSysuserGroup(sysUserGroup);
        sysUserGroupMapper.deleteSysuserGroupRef(sysUserGroup);
        
    }

    /**
     * 
     * 
     * listSysuserGroup 根据用户id或者 用户id和name 查询用户自定义分组
     * 
     * @param @param sysUserGroup
     * @param @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @Override
    public ResponseEnvelope listSysuserGroup(SysUserGroup sysUserGroup) {
        if (sysUserGroup == null || sysUserGroup.getUserId() <= 0)
            return null;

        if (StringUtils.isNotEmpty(sysUserGroup.getName()))// 排除“ ”情况
            sysUserGroup.setName(sysUserGroup.getName().trim());
        int count = sysUserGroupMapper.countSysuserGroup(sysUserGroup);
        ResponseEnvelope envelope = new ResponseEnvelope();
        if(count<=0){
            return envelope;
        }
        
        envelope.setTotalCount(count);
        List<ThumbData> list= sysUserGroupMapper.listSysuserGroup(sysUserGroup);
        for (ThumbData thumbData : list) {
        	//List<ThumbData> resList = resRenderPicService.getRenderPicIdsBySceneId(thumbData.getSceneId());
        	ResRenderPicGroupRef resRenderPicGroupFef = new ResRenderPicGroupRef();
    		resRenderPicGroupFef.setGid(thumbData.getBid());
    		List<ThumbData> resList = sysUserGroupMapper.selectRenderList(resRenderPicGroupFef);
        	if(resList == null || resList.size()<=0){
        		thumbData.setPicNum(0);
        	}else{
        		thumbData.setPicNum(resList.size());
        	}
		}
        envelope.setDatalist(list);
        return envelope;
    }

    /**
     * 
     * 
     * getSysuserGroupByBid 根据业务id得到分组实例
     * 
     * @param @param ResponseEnvelope
     * @param @return
     * 
     * @return SysUserGroup 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @Override
    public ResponseEnvelope getSysuserGroupByBid(SysUserGroup sysUserGroup) {

        if (sysUserGroup == null || sysUserGroup.getUserId() <= 0 || StringUtils.isEmpty(sysUserGroup.getBid()))
            return null;

        int count = sysUserGroupMapper.countSysuserGroupByBid(sysUserGroup);
        ResponseEnvelope envelope = new ResponseEnvelope();
        if(count<=0){
            return envelope;
        }
        
        envelope.setTotalCount(count);
        List list= sysUserGroupMapper.getSysuserGroupByBid(sysUserGroup);
        envelope.setDatalist(list);
        return envelope;
    }

    /**
     * 
     * 
     * movein 设计方案移入分组，如果已经存在将直接返回
     * 
     * @param @param sysUserGroup
     * @param @return
     * 
     * @return SysUserGroup 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @SuppressWarnings("rawtypes")
	@Override	 
    public ResponseEnvelope movein(DesignPlanGroup designPlanGroup) {
    	ResponseEnvelope envelope = new ResponseEnvelope();
        if (designPlanGroup == null){
        	envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        if (StringUtils.isEmpty(designPlanGroup.getDesignPlanRenderSceneIds())){
        	envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        if (StringUtils.isEmpty(designPlanGroup.getName())){
        	envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        if (designPlanGroup.getUserId()<=0){
        	envelope.setSuccess(false);
        	envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        // 判断该分组名已重复
        SysBusinessGroup sysBusinessGroupSearch = new SysBusinessGroup();
        sysBusinessGroupSearch.setUserId(designPlanGroup.getUserId());
        sysBusinessGroupSearch.setName(designPlanGroup.getName());
        int count = sysUserGroupMapper.getCount(sysBusinessGroupSearch);
        if(count > 0){
        	envelope.setSuccess(false);
        	envelope.setMessage("该分组名已重复！");
            return envelope;
        }
        
        //校验分享条件是否满足
        List<ThumbData>thumbDataList = new ArrayList<ThumbData>();
        Map<String,String>resMap = new HashMap<String,String>();
        resMap = this.moveinCheck(designPlanGroup.getDesignPlanRenderSceneIds(),thumbDataList);
        if(resMap == null ||resMap.size()<=0){
			envelope.setSuccess(false);
        	envelope.setMessage("操作失败");
            return envelope;
		}else if (!"true".equals(resMap.get("success"))) {
			envelope.setSuccess(false);
        	envelope.setMessage(resMap.get("data"));
            return envelope;
		}
        
        //保存分组基本信息
        SysUserGroup sysUserGroup = new SysUserGroup();
        sysUserGroup.setUserId(designPlanGroup.getUserId());
        sysUserGroup.setName(designPlanGroup.getName());
        sysUserGroup.setDescription(designPlanGroup.getDescription());
        sysUserGroup.setBid(UUID.randomUUID().toString().replace("-", ""));
        Integer groupId = sysUserGroupMapper.insertSysuserGroup(sysUserGroup);
        if(groupId == null || groupId.intValue()<=0){
        	envelope.setSuccess(false);
        	envelope.setMessage(SystemCommonConstant.DATA_EXCEPTION);
            return envelope;
        }
        
        //保存 缩略图和分组的关联关系
        for (ThumbData thumbData : thumbDataList) {
        	DesignPlanGroup addGroup = new DesignPlanGroup();
        	addGroup.setSceneId(thumbData.getSceneId());
        	addGroup.setThumbId(thumbData.getThumbId());
        	addGroup.setGid(sysUserGroup.getBid());
        	addGroup.setBid(UUID.randomUUID().toString().replace("-", ""));
        	ResRenderPic pic= resRenderPicMapper.selectByPrimaryKey(thumbData.getThumbId());
            if(pic != null && pic.getBusinessId()!=null && pic.getBusinessId().intValue()>0){
                SysDictionary dictionary =sysUserGroupMapper.getHouseTypeBydesignPlanId( pic.getBusinessId());
                if(dictionary!=null)
                	addGroup.setSpaceType(dictionary.getName());//设置房屋空间类型
            }
        	sysUserGroupMapper.movein(addGroup);
		}								 
 
//        String qrCodeUrl = SERVERURL + Utils.getValue("app.server.siteName", "").trim()
//      			 + "pages/vr720/vr720Group.htm?code=" + sysUserGroup.getBid();
        //改成使用渲染服务的接口
        String qrCodeUrl = RENDERSERVICEURL + "/pages/vr720/vr720Group.htm?code=" + sysUserGroup.getBid();

        SysBusinessGroup resGroup = new SysBusinessGroup();
        resGroup.setId(sysUserGroup.getId());
        resGroup.setName(designPlanGroup.getName());
        resGroup.setQrCodeUrl(qrCodeUrl);
        envelope.setObj(resGroup);
        envelope.setSuccess(true);
        return envelope;
    }

    
    
    
    /**
	 * 分享校验,改这个接口的同时记得改 同名此class里的另一个接口
	 * @param designPlanRenderSceneIds
	 * @param thumbDataList
	 * @return Map<String,String>
	 */
    public Map<String,String> moveinCheck(String designPlanRenderSceneIds,List<ThumbData>thumbDataList){
    	Map<String,String>resMap = new HashMap<String,String>();
    	if(thumbDataList == null) {
    		resMap.put("success","false");
        	resMap.put("data", "数据错误");
            return resMap;
    	}
    	if(StringUtils.isEmpty(designPlanRenderSceneIds)) {
    		resMap.put("success","false");
        	resMap.put("data", "params error");
            return resMap;
    	}
    	List<Integer>ids = this.stringChangeList(designPlanRenderSceneIds);
        if(ids == null || ids.size()<=0){
        	resMap.put("success","false");
        	resMap.put("data", "params error");
            return resMap;
        }
        for (Integer sceneId : ids) {
        	List<ThumbData> resList = resRenderPicService.getRenderPicIdsBySceneId(sceneId);
        	if(resList == null || resList.size()<=0){
        		resMap.put("success","false");
        		DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneMapper.get(sceneId);
        		if(designPlanRenderScene!=null){
        			resMap.put("data", designPlanRenderScene.getPlanName() + "未找到720全景图，请先渲染");
        		}else{
        			resMap.put("data","未找到720全景图，请先渲染");
        		}
        		return resMap;
        	}
        	for (ThumbData thumbData : resList) {
        		thumbData.setSceneId(sceneId);
			}
        	thumbDataList.addAll(resList);
		}
        if(thumbDataList == null || thumbDataList.size() <=0) {
    		resMap.put("success","false");
        	resMap.put("data", "数据错误");
            return resMap;
    	}
        resMap.put("success","true");
    	return resMap;
    }
    
    
    
    /**
	 * 分享前校验,改这个接口的同时记得改 同名此class里的另一个接口
	 * @param designPlanRenderSceneIds
	 * @return @return Map<String,String>
	 */
    public Map<String, String> moveinCheck(String designPlanRenderSceneIds){
    	Map<String,String>resMap = new HashMap<String,String>();
    	
    	if(StringUtils.isEmpty(designPlanRenderSceneIds)) {
    		resMap.put("success","false");
        	resMap.put("data", "params error");
            return resMap;
    	}
    	List<Integer>ids = this.stringChangeList(designPlanRenderSceneIds);
        if(ids == null || ids.size()<=0){
        	resMap.put("success","false");
        	resMap.put("data", "params error");
            return resMap;
        }
        for (Integer sceneId : ids) {
        	List<ThumbData> resList = resRenderPicService.getRenderPicIdsBySceneId(sceneId);
        	if(resList == null || resList.size()<=0){
        		resMap.put("success","false");
        		DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneMapper.get(sceneId);
        		if(designPlanRenderScene!=null){
        			resMap.put("data", designPlanRenderScene.getPlanName() + "未找到720全景图，请先渲染");
        		}else{
        			resMap.put("data","未找到720全景图，请先渲染");
        		}
        		return resMap;
        	}
		}
        resMap.put("success","true");
    	return resMap;
    }
    
 
    
    
    
    
    
    public List<Integer> stringChangeList(String designPlanRenderSceneIds){
    	List<Integer>ids = new ArrayList<Integer>();
    	if(StringUtils.isEmpty(designPlanRenderSceneIds)){
    		return null;
    	}
    	String [] arr = designPlanRenderSceneIds.split(",");
    	for (String designPlanRenderSceneId : arr) {
    		ids.add(Integer.parseInt(designPlanRenderSceneId));
		}
    	return ids;
    }
    /**
     * 
     * 
     * moveout 设计方案移除分组
     * 
     * @param @param sysUserGroup
     * @param @return
     * 
     * @return SysUserGroup 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @Override
    public boolean moveout(DesignPlanGroup DesignPlanGroup) {
        if (DesignPlanGroup == null)
            return false;

        if (StringUtils.isEmpty(DesignPlanGroup.getBid()))
            return false;

        sysUserGroupMapper.moveout(DesignPlanGroup);
        return true;
    }

    /**
     * 
     * 
     * getDesignPlans 根据分组业务id或者 分组业务id和设计方案id查询
     * 
     * @param @param sysUserGroup
     * @param @return
     * 
     * @return SysUserGroup 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @Override
    public List<DesignPlanGroup> getDesignPlans(DesignPlanGroup DesignPlanGroup) {
        if (DesignPlanGroup == null)
            return null;

        if (StringUtils.isEmpty(DesignPlanGroup.getGid()))
            return null;

        return sysUserGroupMapper.getDesignPlans(DesignPlanGroup);
    }

    /* (non-Javadoc)    
     * @see com.nork.system.service.SysUserGroupService#doShare(java.lang.String)    
     */
    @Override
    public Group doShare(String groupBusinessId) {
        if (StringUtils.isEmpty(groupBusinessId))
            return null;
        List<ResRenderPic> list = sysUserGroupMapper.get720ResRenderPicByGid(groupBusinessId);
        Map roamMap = new HashMap<String, String>();
        List retList = new ArrayList<GroupDetail>();
        String coverPath=null;//分享缩略图片
        for (ResRenderPic pic : list) {
            if (!ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY.equals(pic.getFileKey()))// 不是缩略图，缩略图一定是有pid值的
                continue;

            if (pic.getPid() == null)
                continue;

            if (RenderTypeCode.COMMON_720_LEVEL == pic.getRenderingType()) {
                // 普通渲染，又称单点渲染，此时pid指向的原图id

                if (pic.getId() == null)
                    continue;
                
                if(StringUtils.isEmpty(coverPath))
                    coverPath = pic.getPicPath();
                
                ResRenderPic org_pic = resRenderPicMapper.selectByPrimaryKey(pic.getPid());
                GroupDetail groupDetail = new GroupDetail();
                groupDetail.setGroupName(pic.getPicName());
                groupDetail.setPicId(pic.getPid());
                
                if(org_pic!=null)//原图路径
                    groupDetail.setPicPath(org_pic.getPicPath());
                
                groupDetail.setPlanId(pic.getDesignSceneId());
                groupDetail.setThumbDesc(pic.getRemark());
                groupDetail.setThumbId(pic.getId());
                groupDetail.setThumbPath(pic.getPicPath());
                groupDetail.setType(pic.getRenderingType());
                retList.add(groupDetail);
            } 
        }
        
        /*int trytimes=3;//幂等性，更新失败重试三次
        while(trytimes>0 ) {
            
            int count = sysUserGroupMapper.countListShare(groupBusinessId);
            logger.info(groupBusinessId+"_groupBusinessId_be_shared_"+count);
            SysUserGroup userGroup = new SysUserGroup();
            userGroup.setBid(groupBusinessId);
            userGroup.setShareTimes(count);
            boolean success  = sysUserGroupMapper.addShareTimes(userGroup);//分享次数+1
            if(success)
                break;
            
            --trytimes;
        }
        */
        int count = sysUserGroupMapper.countListShare(groupBusinessId);
        logger.info(groupBusinessId+"_groupBusinessId_be_shared_"+count);
        SysUserGroup userGroup = new SysUserGroup();
        userGroup.setBid(groupBusinessId);
        userGroup.setShareTimes(count);
        boolean success  = sysUserGroupMapper.addShareTimes(userGroup);//分享次数+1
        
        Group group = new Group();
        group.setList(retList);
        group.setThumbPath(coverPath);
        return group;
    }
    /**
     * 
       
     * getSubPic找到pid指向的原图     ，excludId为排除id
       
     * @return 
    
     * @return List    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    private List getSubPic(List<ResRenderPic> list,int pid,int excludId){
        List retList=new ArrayList<ResRenderPic>();
        for(ResRenderPic pic:list){
            
        }
        return null;
    }

    /* (non-Javadoc)    
     * @see com.nork.system.service.SysUserGroupService#list720(int)    
     */
    @Override
    public ResponseEnvelope  list720(ThumbData thumbdata) {

        if (thumbdata == null || thumbdata.getUserId() <= 0)
            return null;

        if (StringUtils.isNotEmpty(thumbdata.getName()))// 排除“ ”情况
            thumbdata.setName(thumbdata.getName().trim());
        int count = sysUserGroupMapper.countList720(thumbdata);

        ResponseEnvelope envelope = new ResponseEnvelope();
        if(count<=0){
            return envelope;
        }
        
        envelope.setTotalCount(count);
        List list= sysUserGroupMapper.list720(thumbdata);
        envelope.setDatalist(list);
        return envelope;
    }

    /* (non-Javadoc)    
     * @see com.nork.system.service.SysUserGroupService#addShareTimes(java.lang.String)    
     */
    @Override
    public String getQrCode(String groupBusynessId,int userId) { 
    	String shareUrl = SERVERURL + Utils.getValue("app.server.siteName", "").trim() 
   			 + "pages/vr720/vr720Group.htm?code=" + groupBusynessId;
		String qrCodeUrl = null;
		try{
			//二维码存放路径
			String resUrl = Utils.getPropertyName("config/res","system.QRCode.pic.upload.path", "/AA/e_userlogs/[yyyy]/[MM]/[dd]/[HH]/system/QRCode/pic/");
			resUrl = Utils.replaceDate(resUrl);
			String qrCodePath = Utils.getValue("app.upload.root", "") +resUrl ;
			//二维码名字
			String rendPicUrlName =""+ System.nanoTime()+userId+".jpg";
			qrCodePath = Utils.dealWithPath(qrCodePath, "linux");
			String companyLogo = baseCompanyService.getCompanyLogoByAuthorizedConfig(userId);
			MatrixToImageWriter.createLogoQRCodeImage(shareUrl, qrCodePath, rendPicUrlName, companyLogo);
			qrCodeUrl = resUrl + rendPicUrlName;
			logger.info("shareUrl_qrCodeUrl_"+qrCodeUrl);
		}catch (Exception e) {
			return qrCodeUrl;
		}
		return qrCodeUrl;
   }

    @Override
    public ResponseEnvelope getShareList(String group_businessId) {
        if(StringUtils.isEmpty(group_businessId)){
            return null;
        }
        ResponseEnvelope envelope = new ResponseEnvelope();
        int count = sysUserGroupMapper.countListShare(group_businessId);
        envelope.setTotalCount(count);
        return envelope;
    }

	@Override
	public int getCount(SysBusinessGroup sysBusinessGroup) {
		return sysUserGroupMapper.getCount(sysBusinessGroup);
	}

	@Override
	public ResRenderPicGroupRef getGroupRefByBid(String bid) {
		return sysUserGroupMapper.getGroupRefByBid(bid);
	}

	@Override
	public SysBusinessGroup getBusinessGroupByBid(String bid) {
		return sysUserGroupMapper.getBusinessGroupByBid(bid);
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

    @Override
    public ResponseEnvelope getGroupDetail(SysUserGroup group_businessId) {
        ResponseEnvelope envelope = new ResponseEnvelope();
        if (group_businessId == null || StringUtils.isEmpty(group_businessId.getBid())){
            return null;
        }
        ShareDetailVo detailVo = sysUserGroupMapper.getGroupDetail(group_businessId);
        List<GroupPicVo> groupPicList = resRenderPicMapper.getGroupPicList(group_businessId.getBid());
        if (detailVo != null){
            detailVo.setPicList(groupPicList);
        }
        envelope.setObj(detailVo);
        return envelope;
    }

    @Override
    public ResponseEnvelope getOnePicDetail(int thumId, Integer userId) {
        ResponseEnvelope envelope = new ResponseEnvelope();
        envelope.setSuccess(false);
        if (thumId < 1){
            return envelope;
        }
        GroupPicVo picDetail = sysUserGroupMapper.getOnePicDetail(thumId);
        if (picDetail != null){
            envelope.setObj(picDetail);
            envelope.setSuccess(true);
        }
        return envelope;
    }


    /**
	 * 已经选择的分组效果图列表
	 * @param bid
	 * @param userId
	 * @return
	 */
	@Override
	public ResponseEnvelope<ThumbData> selectRenderList(String bid, int userId) {
		ResponseEnvelope<ThumbData> envelope = new ResponseEnvelope<ThumbData>();
		List<ThumbData>resList = null;
		if(userId <= 0){
			envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            envelope.setSuccess(false);
            return envelope;
		}
		if(!this.checkParam(bid)){
			envelope.setMessage("params error");
            envelope.setSuccess(false);
            return envelope;
		}
		SysBusinessGroup sysBusinessGroup = this.getBusinessGroupByBid(bid);
		if(sysBusinessGroup == null){
			envelope.setMessage("该分组不存在，或已被删除，请刷新页面");
            envelope.setSuccess(false);
            return envelope;
		}
		ResRenderPicGroupRef resRenderPicGroupFef = new ResRenderPicGroupRef();
		resRenderPicGroupFef.setGid(bid);
		resList = sysUserGroupMapper.selectRenderList(resRenderPicGroupFef);
		/*DesignPlanGroup designPlanGroupSearch = new DesignPlanGroup();
    	designPlanGroupSearch.setGid(sysBusinessGroup.getBid());
    	List<DesignPlanGroup>list = this.getDesignPlans(designPlanGroupSearch);
    	if(list ==null || list.size()<=0){
    		envelope.setSuccess(true);
    		return envelope;
    	}
    	List<Integer>sceneIds = new ArrayList<Integer>();
    	for (DesignPlanGroup designPlanGroup : list) {
    		if(designPlanGroup.getSceneId()!=null){
    			sceneIds.add(designPlanGroup.getSceneId());
    		}
		}
    	if(sceneIds.size()<=0){
    		envelope.setSuccess(true);
    		return envelope;
    	}
    	ThumbData thumbData = new ThumbData();
    	thumbData.setUserId(userId);
    	thumbData.setSceneIds(sceneIds);
    	resList = resRenderPicMapper.getRenderPicByPage(thumbData);*/
    	envelope.setSuccess(true);
    	envelope.setDatalist(resList);
		return envelope;
	}
}
