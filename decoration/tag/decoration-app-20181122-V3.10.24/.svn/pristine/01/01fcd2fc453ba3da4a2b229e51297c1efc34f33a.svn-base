/**    
 * 文件名：SysResLevelCfgImpl.java    
 *    
 * 版本信息：    
 * 日期：2017年8月9日    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.pay.model.PayOrder;
import com.nork.pay.service.PayOrderService;
import com.nork.system.model.SysUserLevelConfig;
import com.nork.system.model.SysUserLevelPrice;
import com.nork.system.model.bo.SysUserLevelBo;
import com.nork.system.service.SysUserLevelConfigService;
import com.nork.system.service.SysUserLevelPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.util.Constants;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.system.common.EquipmentConstants;
import com.nork.system.dao.SysResLevelCfgMapper;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.UserLevelCfg;
import com.nork.system.service.SysResLevelCfgService;

/**    
 *     
 * 项目名称：timeSpace    
 * 类名称：SysResLevelCfgImpl    
 * 类描述：    
 * 创建人：Timy.Liu   
 * 创建时间：2017年8月9日 下午3:45:50    
 * 修改人：Timy.Liu    
 * 修改时间：2017年8月9日 下午3:45:50    
 * 修改备注：    
 * @version     
 *     
 */
@Service
public class SysResLevelCfgServiceImpl implements SysResLevelCfgService {
    @Autowired
    private SysResLevelCfgMapper sysResLevelCfgMapper;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private SysUserLevelConfigService sysUserLevelConfigService;
    @Autowired
    private SysUserLevelPriceService sysUserLevelPriceService;

    /* (non-Javadoc)    
     * @see com.nork.system.service.SysResLevelCfg#addUserLevelLimit(com.nork.system.model.UserLevelCfg)    
     */
    @Override
    public boolean addUserLevelLimit(UserLevelCfg cfg) {
        if(cfg==null)
            return false;
        
        if(cfg.getUserId()<=0 || cfg.getUserLevel()<=0)
            return false;
        
        if(cfg.getUserGroupType()<0 || cfg.getVersion()<0)
            return false;
        
        if(StringUtils.isEmpty(cfg.getMobileLimitKey()) || StringUtils.isEmpty(cfg.getPcLimitKey()))
            return false;
        
        sysResLevelCfgMapper.addUserResLevel(cfg);
        sysResLevelCfgMapper.addUserDeviceLimit(cfg);
        return true;
    }


    /* (non-Javadoc)    
     * @see com.nork.system.service.SysResLevelCfgService#getLimitResNumByUserId(int, java.lang.String)    
     */
    @Override
    public String getLimitResNumByUserId(int userId, String resType) {
        Map<String, String> map = new HashMap<>();

        if (userId <= 0 || StringUtils.isEmpty(resType))
            return Constants.RES_NUM_NO_LIMIT;

        if (Utils.enableRedisCache()) {// 缓存启用
            map = JedisUtils.getMap(Constants.USER_LEVEL_LIMIT_CACHE_KEY + userId);
        }

        if (map != null && map.size() > 0)// 命中
        {
            String val = map.get(resType);
            if(StringUtils.isEmpty(val))
                return Constants.RES_NUM_NO_LIMIT;
            
            return val;
        }
        if(null == map)
        	map = new HashMap<>();
        
        UserLevelCfg cfg = new UserLevelCfg();
        cfg.setUserId(userId);
        List<UserLevelCfg> list = sysResLevelCfgMapper.getUserLevelCfg(cfg);// 击穿

        if (list == null || list.size() <= 0)
            return Constants.RES_NUM_NO_LIMIT;

        int size = list.size();
        UserLevelCfg item = null;

        String renVal = null;
        for (int i = 0; i < size; i++) {
            item = list.get(i);
            
            if(null==item || StringUtils.isEmpty(item.getResBigTypeNum()) || StringUtils.isEmpty(item.getResNum()) ){
            	continue;
            }
            
            if(item.getResBigTypeNum().trim().equalsIgnoreCase(resType))//大类，不是小类
                renVal=item.getResNum();
            
            map.put(item.getResBigTypeNum(), item.getResNum());
        }

        if(StringUtils.isEmpty(renVal))
            renVal =  Constants.RES_NUM_NO_LIMIT;
        
        if (!Utils.enableRedisCache())// 缓存未启用
        {
            return renVal;
        }
        
        if (list != null && list.size() > 0) {
            JedisUtils.setMap(Constants.USER_LEVEL_LIMIT_CACHE_KEY + cfg.getUserId(), map, 3600);// 缓存一个小时
        }

        return renVal;
    }
    
    
    
    /**
     * 获取限制设备数量
     * @param userId
     * @param equipmentType
     * @return
     */
    @Override
    public List<SysDictionary> getEquipmentNum(Integer userId,String equipmentType){
    	List<SysDictionary>resList = null;
    	if(userId == null || userId.intValue()<=0){
    		return null;
    	}
    	String valuekey = "";
		if(EquipmentConstants.MOBILE_EQUIPMENT.equals(equipmentType)){
			valuekey = "mobile_limit_"; 
		}else if(EquipmentConstants.PC_EQUIPMENT.equals(equipmentType)){
			valuekey = "pc_limit_"; 
		}else{
			return null;
		}
		resList = sysResLevelCfgMapper.getEquipmentNum(userId,valuekey);
    	return resList;
    }

    @Override
    public void processAfterConsume(String orderNum) {
        //1、查询消费的总金额
        //2、把总金额和sys_user_level_price表的price表进行比较看是否需要升级
        //3、如果不需要升级则不做任何处理，如果需要升级：一个等级一个等级逐级往上比较
        if (StringUtils.isEmpty(orderNum)){
            return;
        }

       PayOrder payOrder = payOrderService.getConsumeSumByOrderNum(orderNum);
        if (payOrder == null){
            return;
        }else {
            Integer totalCocumeFee = payOrder.getTotalFee();//用户消费总金额
            Integer userId = payOrder.getUserId();
            //查询用户当前级别对应的金额
            SysUserLevelConfig levelConfig = new SysUserLevelConfig();
            levelConfig.setBusinessTypeId(Constants.USER_LEVEL_BUSINESS_TYPE_LEVEL_PRICE_ID);
            levelConfig.setUserId(userId);
            SysUserLevelBo userLevelBo = sysUserLevelConfigService.getLevelInfo(levelConfig);//查询用户当前对应的等级
            if (userLevelBo != null){
                int levelPrice = userLevelBo.getPrice();
                if (totalCocumeFee <= levelPrice){//消费总金额未达到等级上限
                    return;
                }else {
                    //根据用户对应的组群类型获取等级列表:根据user_pay_type倒序排序
                    List<SysUserLevelPrice> userLevelPrices = sysUserLevelPriceService.getListByUserId(userId);
                    if(userLevelPrices != null && userLevelPrices.size() > 0){
                        for (SysUserLevelPrice tmp : userLevelPrices){
                            if(totalCocumeFee >= tmp.getPrice()){
                                int id = userLevelBo.getId();
                                int levelId = tmp.getId();
                                sysUserLevelConfigService.updateById(id,levelId);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

}
