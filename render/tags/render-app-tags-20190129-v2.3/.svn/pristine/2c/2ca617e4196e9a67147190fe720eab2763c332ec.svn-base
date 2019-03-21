package com.nork.system.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.design.model.DesignRenderRoam;
import com.nork.design.model.ResRenderPicQO;
import com.nork.pano.model.scene.PanoramaVo;
import com.nork.render.model.HeadMessage;
import com.nork.render.model.ResRenderData;
import com.nork.system.model.ResRenderPic;

/**   
 * @Title: ResRenderPicService.java 
 * @Package com.nork.system.service
 * @Description:渲染图片资源库-渲染图片资源库Service
 * @createAuthor pandajun 
 * @CreateDate 2017-03-22 14:39:08
 * @version V1.0   
 */
public interface ResRenderPicService {
	

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResRenderPic 
	 */
	public ResRenderPic get(Integer id);
	
	
	/**
     * 多点二维码图片http://192.168.1.232:6080/app/pages/vr720/vr720Roam.htm?code=70ae74da695c40389a083f13cb8b1839
       
     * get720RomanCode.得到720多点分享的code    
       
     * @param ResRenderPic。传入 截图id 和创建人create_user_id
     * @return 
    
     * @return DesignRenderRoam    返回类型    取uuid
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
	public DesignRenderRoam get720RomanCode(ResRenderPic resRenderPic);
	
	/**
     * 单点二维码图片。http://192.168.2.13:8080/app/pages/vr720/vr720Single.htm?code=20170712200703077_646765
       
     * get720SingleCode 得到720单点分享需要的code        
       
     * @param resRenderPic 传入 原图id和create_user_id
     * @return 
    
     * @return ResRenderPic    返回类型    取 sysCode
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public ResRenderPic get720SingleCode(ResRenderPic resRenderPic);
    
    /**
     * ...
     * update by huangsongbo 2018.8.13
     * add param: dateDue 截至日期
     * 暂定的需求是:超过截至日期的二维码,跳转到其他页面
     * 
     * @param resPic
     * @param dateDue
     * @return
     */
    String getQRpicPath(ResRenderPic resPic, Date dateDue);
    
    /**
	 *查询所有的缩略图和封面图 
	 */
	public List<ResRenderPic> selectListByFileKeys(ResRenderPicQO resRenderPicQO);
	
	/**
	 * 所有数据
	 * 
	 * @param  resRenderPic
	 * @return   List<ResRenderPic>
	 */
	public List<ResRenderPic> getList(ResRenderPic resRenderPic);

	 /**
     * 根据 联盟方案素材发布ID 查找该方案内的720单点渲染图列表
     * @param releaseId
     * @return
     */
    public List<ResRenderPic> getListByUnionDesignPlanStoreReleaseId(Integer releaseId);

	enum renderResEnum {
		designPlanRenderScene, designPlanRecommended
	}

	Map<String,List<ResRenderData>> getAllResRenderPic(Integer designPlanRecommendedId, renderResEnum type);

	/**
	 * 获取720场景展示相关信息
	 * @return
	 */
	HeadMessage getSceneHeadMessage(PanoramaVo panoramaVo, HeadMessage headMessage, Integer userId,String platformType);
}
