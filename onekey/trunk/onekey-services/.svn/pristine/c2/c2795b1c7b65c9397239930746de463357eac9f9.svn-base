/**    
 * 文件名：SysuserGroupMapper.java    
 *    
 * 版本信息：    
 * 日期：2017-7-6    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.system.dao;

import java.util.List;

import com.nork.system.model.vo.GroupPicVo;
import com.nork.system.model.vo.ShareDetailVo;
import org.springframework.stereotype.Repository;

import com.nork.onekeydesign.model.DesignPlanGroup;
import com.nork.onekeydesign.model.ResRenderPicGroupRef;
import com.nork.onekeydesign.model.SysBusinessGroup;
import com.nork.onekeydesign.model.ThumbData;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUserGroup;

/**
 * 
 * 项目名称：timeSpace 类名称：SysuserGroupMapper 类描述： 创建人：Timy.Liu 创建时间：2017-7-6
 * 上午10:37:08 修改人：Timy.Liu 修改时间：2017-7-6 上午10:37:08 修改备注：
 * 
 * @version
 * 
 */
@Repository
public interface SysUserGroupMapper {

    public int insertSysuserGroup(SysUserGroup sysUserGroup);

    public void updateSysuserGroup(SysUserGroup sysUserGroup);

    public void deleteSysuserGroup(SysUserGroup sysUserGroup);
    public void deleteSysuserGroupRef(SysUserGroup sysUserGroup);
    
    public List<ThumbData> listSysuserGroup(SysUserGroup sysUserGroup);
    public int countSysuserGroup(SysUserGroup sysUserGroup);
    
    public List<ThumbData> getSysuserGroupByBid(SysUserGroup sysUserGroup);
    public int  countSysuserGroupByBid(SysUserGroup sysUserGroup);
    public void movein(DesignPlanGroup DesignPlanGroup);

    public void moveout(DesignPlanGroup DesignPlanGroup);

    public List<DesignPlanGroup> getDesignPlans(DesignPlanGroup DesignPlanGroup);
    /**
     * 
       
     * get720ResRenderPicByGid 得到720的res_render_pic资源   
       
     * @param groupBusinessId
     * @return 
    
     * @return List<ResRenderPic>    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public List<ResRenderPic> get720ResRenderPicByGid(String groupBusinessId);
    /**
     * 
       
     * getHouseTypeBydesignPlanId 根据设计方案id查询得到与之对应的数据字典名称      
       
     * @param designPlanId
     * @return 
    
     * @return SysDictionary    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public SysDictionary getHouseTypeBydesignPlanId(int designPlanId);
    public int  countList720(ThumbData thumbdata);
    /**
     * 
       
     * list720 分页获取个人名下的所有缩列图列表 
       
     * @param DesignPlanGroup
     * @return 
    
     * @return int    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public List<ThumbData>  list720(ThumbData thumbdata);
    
    /**
     * 
       
     * addShareTimes( 发生分享时候的次数统计)        
       
     * @param SysUserGroup 
    
     * @return boolean    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public boolean addShareTimes(SysUserGroup group);

    /**
     * describe: 获取组的分享次数
     * creat_user: yanghz
     * creat_date: 2017-07-31
     * creat_time: 下午 07:13
     **/
    int countListShare(String group_businessId);

    /**
     * 获取总条数
     * @param designPlanGroup
     * @return
     */
	public int getCount(SysBusinessGroup sysBusinessGroup);

	/**
	 * @param designPlanGroupBid
	 * @return
	 */
	public ResRenderPicGroupRef getGroupRefByBid(String bid);

	public SysBusinessGroup getBusinessGroupByBid(String bid);

    public ShareDetailVo getGroupDetail(SysUserGroup group_businessId);

    public GroupPicVo getOnePicDetail(int thumId);
 
	public List<ThumbData> selectRenderList(ResRenderPicGroupRef resRenderPicGroupFef);
	
	public List<SysUserGroup> getGidBySceneId(SysUserGroup sysUserGroup);
	public void deleteBySceneId(SysUserGroup sysUserGroup);
	public int countByGid(SysUserGroup sysUserGroup);
	public void deleteSysuserGroupByBid(SysUserGroup sysUserGroup);

}
