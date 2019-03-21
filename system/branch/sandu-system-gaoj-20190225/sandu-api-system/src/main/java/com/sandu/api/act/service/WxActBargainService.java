package com.sandu.api.act.service;

import java.util.List;
import java.util.Date;

import com.sandu.api.act.model.WxActBargain;
import org.springframework.stereotype.Component;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * bargain
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:24
 */
@Component
public interface WxActBargainService {

    /**
     * 插入
     *
     * @param wxactbargain
     * @return
     */
    void createWxActBargain(WxActBargain wxActBargain);

    /**
     * 更新
     *
     * @param wxactbargain
     * @return
     */
    int modifyWxActBargain(WxActBargain wxactbargain);

    /**
     * 删除
     *
     * @param actIds
     * @return
     */
    int removeWxActBargain(String actId);

    /**
     * 通过ID获取详情
     *
     * @param actId
     * @return
     */
     WxActBargain getWxActBargain(String actId);
     
     /**
      * 通过ID和appid获取详情
      *
      * @param actId
      * @return
      */
      WxActBargain getWxActBargain(String actId,String appId);
     
     /**
      * 获取活动状态
      * @param wxActBargain
      * @return
      */
     Integer getWxActBargainStatus(WxActBargain wxActBargain);
     
     /**
      *  获取活动状态
      * @param actId
      * @return
      */
     Integer getWxActBargainStatus(String actId,String appId);

     /**
      * 扣减库存
      * @param actId
      * @return
      */
	 boolean reduceProductInventory(String actId);

	 /**
	  * 增加参与人数
	  * @param actId
	  */
	 void increaseParticipants(String actId);

    List<WxActBargain> selectByAppids(List<String> appids, Integer page, Integer limit);

    /**
     * 废弃不用了
     * 查询所有有效砍价活动
     * @return
     */
    List<WxActBargain> getAllEffectiveBargain(Date currentTime);
    
    
    List<WxActBargain> getWillExpireList();

    /**
    * 定时任务，扣除库存
    * @author : WangHaiLin
    * @date  2018/11/23 18:13
    * @return void  无返回
    *
    */
    void modifyVitualCount();


    int countByAppids(List<String> appids);

    WxActBargain selectByActName(String actName);

    WxActBargain getWxActBargainByActName(String actName);
}
