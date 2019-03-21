/**    
 * 文件名：SysResLevelCfgMapper.java    
 *    
 * 版本信息：    
 * 日期：2017年8月9日    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.sandu.service.company.dao;

import com.sandu.api.company.model.UserLevelCfg;
import com.sandu.api.dictionary.model.SysDictionary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**    
 *     
 * 项目名称：timeSpace    
 * 类名称：SysResLevelCfgMapper    
 * 类描述：    
 * 创建人：Timy.Liu   
 * 创建时间：2017年8月9日 下午2:43:56    
 * 修改人：Timy.Liu    
 * 修改时间：2017年8月9日 下午2:43:56    
 * 修改备注：    
 * @version     
 *     
 */
@Repository
public interface SysResLevelCfgMapper {
    /**
     * 
       
     * addUserResLevel 方法描述：      用户和产品的数量限制关联
       
     * @param cfg
    
     * @return void    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void addUserResLevel(UserLevelCfg cfg);
    /**
     * 
       
     * addUserDeviceLimit 方法描述：     用户和终端登录数量限制 
       
     * @param cfg
    
     * @return void    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void addUserDeviceLimit(UserLevelCfg cfg);
    /**
     * 
       
     * getUserLevelCfg 方法描述：   得到一个用户与级别相关联的所有限制      
       
     * @param cfg
     * @return
    
     * @return List<UserLevelCfg>    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    
    public List<UserLevelCfg> getUserLevelCfg(UserLevelCfg cfg);
    
    /**
     * 获取限制设备数量
     * @param userId
     * @param valuekey
     * @return
     */
    public List<SysDictionary> getEquipmentNum(@Param("userId") Integer userId, @Param("valuekey") String valuekey);

}
