/**    
 * 文件名：SysResLevelCfg.java    
 *    
 * 版本信息：    
 * 日期：2017年8月9日    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.system.service;

import java.util.List;
import java.util.Map;

import com.nork.system.model.SysDictionary;
import com.nork.system.model.UserLevelCfg;

/**
 * 
 * 项目名称：timeSpace 类名称：SysResLevelCfg 类描述： 创建人：Timy.Liu 创建时间：2017年8月9日 下午3:45:21
 * 修改人：Timy.Liu 修改时间：2017年8月9日 下午3:45:21 修改备注：
 * 
 * @version
 * 
 */
public interface SysResLevelCfgService {
    /**
     * 
     * 
     * addUserLevelLimit 方法描述： 添加用户级别相关限制
     * 
     * @param cfg
     * @return
     * 
     * @return boolean 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public boolean addUserLevelLimit(UserLevelCfg cfg);

    /**
     * 
       
     * getLimitResNumByUserId 方法描述：  根据用户id得到指定的可访问的资源数量    
       
     * @param userId  用户id
     * @param resType 资源内部编号
    
     * @return
     * @return String    返回类型 
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public String getLimitResNumByUserId(int userId,String resType);
    
    
    /**
     * 获取限制设备数量
     * @param userId
     * @param equipmentType
     * @return
     */
    public List<SysDictionary> getEquipmentNum(Integer userId,String equipmentType);

    /**
     *add by yanghz

     * processAfterConsume 方法描述：  根据消费金额判断是否需要对用户进行自动升级

     * @param orderNum  订单号

     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void processAfterConsume(String orderNum);
}
