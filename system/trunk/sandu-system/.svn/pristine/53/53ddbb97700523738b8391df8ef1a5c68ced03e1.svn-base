package com.sandu.web.act;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.sandu.api.act.model.WxActBargain;
import com.sandu.api.act.model.WxActBargainRegistration;
import com.sandu.api.act.service.WxActBargainRegistrationService;
import com.sandu.api.act.service.WxActBargainService;
import com.sandu.api.company.model.BaseCompanyMiniProgramTemplateMsg;
import com.sandu.api.user.service.SysUserService;
import com.sandu.web.async.AsyncCallTemplateMsgService;


/**
 * 砍价活动定时任务
 * @author WangHaiLin
 * @date 2018/11/22  9:52
 *  0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
	0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
	0 0 12 ? * WED 表示每个星期三中午12点
	"0 0 12 * * ?" 每天中午12点触发
	"0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
	"0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
	"0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
	"0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
	"0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
	"0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发
	"0 15 10 15 * ?" 每月15日上午10:15触发
	"0 15 10 L * ?" 每月最后一日的上午10:15触发
	"0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
	"0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
	"0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
	"0 15 10 ? * *" 每天上午10:15触发
	"0 15 10 * * ?" 每天上午10:15触发
	"0 15 10 * * ? *" 每天上午10:15触发
	"0 15 10 * * ? 2005" 2005年的每天上午10:15触发
 */
@Configuration
@EnableScheduling
public class ActTimer {

    private Logger logger = LoggerFactory.getLogger(ActTimer.class);

    @Autowired
    private WxActBargainService wxActBargainService;
    
    @Autowired
    private WxActBargainRegistrationService wxActBargainRegistrationService;
    
    @Autowired
    private AsyncCallTemplateMsgService asyncCallService;
    
    @Resource
    private SysUserService userService;
    
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private static String actProgressNotifyText="您喜欢的%1$s即将过期，只差%2$s元即可%3$s元拿";

    /**
     * 处理逻辑：原本是查出有效活动，在代码中扣除库存和记录虚拟扣除数量，由于并发问题可能会导致库存出现异常
     *          现修改逻辑，开启定时任务时，直接在update的sql中处理这个逻辑，Mybatis的锁，会解决并发问题。
     */
    //@Scheduled(cron = "0 0/2 * * * ?")//每两分钟执行一次
    @Scheduled(cron = "0 0 * * * ?")//每小时执行一次
    public void reduceStock() {
        logger.info("虚拟扣库存：定时任务启动----Start");
        try{
            wxActBargainService.modifyVitualCount();
        }catch (Exception e){
            logger.error("定时任务，虚拟扣除库存系统异常",e);
        }
        logger.info("虚拟扣库存：定时任务结束----end");
    }
    

    /**
     * 活动结束前第2上午10点、最后1天上午10点，系统给没有砍价成功的用户发送“砍价进度通知”
     */
    @Scheduled(cron = "0 0 10 * * ?")//每天上午10点执行一次
    public void sendActBargainProgressTemplateMsg() {
    	Long beginTime = System.currentTimeMillis();
    	logger.info("砍价进度通知：定时任务启动----Start");
        try{
        	List<WxActBargain> actList = wxActBargainService.getWillExpireList();
			if (actList != null && actList.size() > 0) {
				for (WxActBargain actEntity : actList) {
					List<WxActBargainRegistration> regList = wxActBargainRegistrationService.getBargainRegistrationsByActId(actEntity.getId());
					if(regList!=null && regList.size()>0) {
						for(WxActBargainRegistration regEntity:regList) {
							//未完成的任务进行通知
							if(regEntity!=null && regEntity.getCompleteStatus()==WxActBargainRegistration.COMPLETE_STATUS_UNFINISH) {
								//构建模板消息数据
								Map templateDate = this.buildActBarginRegProgressTempalteData
										(regEntity.getProductName(), 
										 String.format(actProgressNotifyText, 
												 regEntity.getProductName(),
												 regEntity.getProductRemainPrice().doubleValue()== regEntity.getProductRemainPrice().intValue()?
														 String.valueOf(regEntity.getProductRemainPrice().intValue()):
															 regEntity.getProductRemainPrice().doubleValue(),
												 regEntity.getProductMinPrice().doubleValue()==regEntity.getProductMinPrice().intValue()?
														 String.valueOf(regEntity.getProductMinPrice().intValue()):
															 regEntity.getProductMinPrice().doubleValue()),
										 dateFormat.format(actEntity.getEndTime()));
								logger.info("开始发送模板消息:活动id:{},活动名称:{},openId:{}",actEntity.getId(),actEntity.getActName(),regEntity.getOpenId());
								//发送模板消息
//								asyncCallService.sendTemplateMsg(userService.getMiniUser(regEntity.getOpenId()),BaseCompanyMiniProgramTemplateMsg.TEMPLATE_TYPE_ACT_BARGAIN_REG_PROGRESS,templateDate,actEntity.getId(),regEntity.getId());
							}
						}
					}
				}
        	}
        }catch (Exception e){
            logger.error("砍价进度通知定时任务异常",e);
        }finally {
        	logger.info("砍价进度通知：定时任务结束----end:"+(System.currentTimeMillis()-beginTime)+"ms");
        }
        
    }
    
    private Map buildActBarginRegProgressTempalteData(String productName,String cutProgress,String endTime) {
		Map<String, Map> keywordsMap = new LinkedHashMap<String, Map>();
		Map<String, String> keywordTempMap = null;
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", productName);
		keywordsMap.put("keyword1", keywordTempMap);
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", cutProgress);
		keywordsMap.put("keyword2", keywordTempMap);
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", endTime);
		keywordsMap.put("keyword3", keywordTempMap);
		
		return keywordsMap;
	} 
    
    public static void main(String[] args) {
    	Double d = new Double(9.0);
    	System.out.println(d.doubleValue()== d.intValue());
    	System.out.println( d.intValue());
    	
    	//bug
    	System.out.println((d.doubleValue()==d.intValue())?d.intValue():d.doubleValue());
    	System.out.println((d.doubleValue()==d.intValue())?String.valueOf(d.intValue()):d.doubleValue());

    	 
	}
       
}
