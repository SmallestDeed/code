package com.sandu.company.service.impl;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.sysusermessage.model.SysUserMessage;
import com.sandu.company.dao.ProprietorInfoDao;
import com.sandu.company.model.ProprietorInfo;
import com.sandu.company.service.ProprietorInfoService;
import com.sandu.sysusermessage.dao.SysUserMessageMapper;

/**
 * 店铺预约服务 Service实现类
 * @author WangHaiLin
 * @date 2018/9/28  14:01
 */
@Service("proprietorInfoService")
public class ProprietorInfoServiceImpl implements ProprietorInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ProprietorInfoServiceImpl.class.getName());

    @Autowired
    private ProprietorInfoDao proprietorInfoDao;
    
    @Autowired
    private SysUserMessageMapper sysUserMessageMapper;

    @Override
    public int insert(ProprietorInfo services) {
        logger.info("店铺预约服务--service层--入参 ：{}",services);
        int result = proprietorInfoDao.add(services);
        if(result>0){
        	
        	SysUserMessage message = new SysUserMessage();
    		message.setTitle("预约成功");
            message.setContent("预约成功,稍后随选网的客服人员会与您进行进一步沟通,请注意接听来电");
            message.setCreator(services.getAppointUserId()+"");
            message.setGmtCreate(new Date());
            message.setModifier(services.getAppointUserId()+"");
            message.setGmtModified(new Date());
            message.setTaskId(services.getId().intValue());
            message.setIsDeleted(0);
            message.setMessageType(Byte.valueOf("2"));
            message.setUserId(services.getAppointUserId());
            message.setStatus(Byte.valueOf("1"));
            message.setRemark("随选网店铺预约");
            sysUserMessageMapper.insertSelective(message);     
            
            logger.info("店铺预约服务--service层---添加结果 ：{}",result);
            return services.getId().intValue();
        }
        return 0;
    }
}
