/**    
 * 文件名：SysuserGroupServcie.java    
 *    
 * 版本信息：    
 * 日期：2017-7-6    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.system.service;

import java.util.List;
import java.util.Map;

import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlanGroup;
import com.nork.design.model.Group;
import com.nork.design.model.ResRenderPicGroupRef;
import com.nork.design.model.SysBusinessGroup;
import com.nork.design.model.ThumbData;
import com.nork.system.model.SysUserGroup;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 项目名称：timeSpace 
 * 类名称：SysUserGroupService 
 * 类描述： 创建人：Timy.Liu 
 * 创建时间：2017-7-6
 * 上午10:47:13 
 * 修改人：Timy.Liu 
 * 修改时间：2017-7-6 上午10:47:13 
 * 修改备注：
 * 
 * @version
 * 
 */
public interface SysUserGroupService {

    public boolean insertSysuserGroup(SysUserGroup sysUserGroup);

    public boolean updateSysuserGroup(SysUserGroup sysUserGroup);

    public void deleteSysuserGroup(SysUserGroup sysUserGroup);

    public ResponseEnvelope listSysuserGroup(SysUserGroup sysUserGroup);

    public ResponseEnvelope getSysuserGroupByBid(SysUserGroup sysUserGroup);

    public ResponseEnvelope movein(DesignPlanGroup DesignPlanGroup);

    public boolean moveout(DesignPlanGroup DesignPlanGroup);

    public List<DesignPlanGroup> getDesignPlans(DesignPlanGroup DesignPlanGroup);
    /**
     * 
       
     * doShare 分享组里面的设计方案渲染图  
       
     * @param groupBusinessId
     * @return 
    
     * @return List    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public Group doShare(String groupBusinessId);
   /**
    * 
      
    * list720 查询个人名下没在指定组类的720单点多点缩略图片     
      
    * @param thumbdata
    * @return 
   
    * @return List<ThumbData>    返回类型   
      
    * @Exception 异常对象    
      
    * @since  CodingExample　Ver(编码范例查看) 1.1
    */
   public ResponseEnvelope  list720(ThumbData thumbdata);
   /**
    * 
      
    * getQrCode( 发生分享时候的次数统计)        
      
    * @param groupBusynessId 
   
    * @return void    返回类型   
      
    * @Exception 异常对象    
      
    * @since  CodingExample　Ver(编码范例查看) 1.1
    */
   public String getQrCode(String groupBusynessId,int userId);

    /**
     * describe: 获取分享统计列表
     * creat_user: yanghz
     * creat_date: 2017-07-31
     * creat_time: 下午 06:18
     **/
    ResponseEnvelope getShareList(String group_businessId);

	public int getCount(SysBusinessGroup sysBusinessGroup);

 

	public ResRenderPicGroupRef getGroupRefByBid(String bid);

	 
	/**
	 * 通过big 查询 数据
	 * @param bid
	 * @return
	 */
	public SysBusinessGroup getBusinessGroupByBid(String parseInt);
 
	/**
	 * 已经选择的分组效果图列表
	 * @param bid
	 * @param userId
	 * @return
	 */
	public ResponseEnvelope<ThumbData> selectRenderList(String bid, int userId);
 

    /**
     * describe: 查看分享(组详情)
     * creat_user: yanghz
     * creat_date: 2017-08-02
     * creat_time: 下午 02:50
     *
     * @param group_businessId*/
    ResponseEnvelope getGroupDetail(SysUserGroup group_businessId);

    ResponseEnvelope getOnePicDetail(int thumId, Integer userId);
    /**
	 * 分享前校验
	 * @param designPlanRenderSceneIds
	 * @return
	 */
	public Map<String, String> moveinCheck(String designPlanRenderSceneIds);

    /**
     * 根据主键查询方案组合分享相关信息
     * @param id
     * @return
     */
    public SysUserGroup getPlanGroupByPrimaryKey(Integer id);

    /**
     *

     * addShareTimes( 发生分享时候更新次数统计)

     * @param group

     * @return boolean    返回类型

     * @Exception 异常对象

     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public boolean addShareTimes(SysUserGroup group);
}
