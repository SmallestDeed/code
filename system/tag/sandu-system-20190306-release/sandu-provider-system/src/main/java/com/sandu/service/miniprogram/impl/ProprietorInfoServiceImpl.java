package com.sandu.service.miniprogram.impl;

import com.sandu.api.miniprogram.input.ProprietorInfoAdd;
import com.sandu.api.miniprogram.input.ProprietorInfoQuery;
import com.sandu.api.miniprogram.input.UnionPlatformUpdate;
import com.sandu.api.miniprogram.model.ProprietorInfo;
import com.sandu.api.miniprogram.model.UnionPlatform;
import com.sandu.api.miniprogram.output.ProprietorInfoVO;
import com.sandu.api.miniprogram.service.ProprietorInfoService;
import com.sandu.api.sysusermessage.model.SysUserMessage;
import com.sandu.service.miniprogram.dao.ProprietorInfoMapper;
import com.sandu.service.miniprogram.dao.UnionPlatformMapper;
import com.sandu.service.sysusermessage.dao.SysUserMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Yuxc
 * @Date: Created in 18:21 2018/9/4
 */

@Service("proprietorInfoService")
@Slf4j
public class ProprietorInfoServiceImpl implements ProprietorInfoService {

    @Autowired
    private ProprietorInfoMapper proprietorInfoMapper;

    @Autowired
    private UnionPlatformMapper unionPlatformMapper;

    @Autowired
    private SysUserMessageMapper sysUserMessageMapper;

    @Override
    public int addProprietor(ProprietorInfoAdd add) {
        //数据转换
        ProprietorInfo proprietor = dataConversion(add);
        int id = proprietorInfoMapper.insertSelective(proprietor);
        log.debug("Proprietor: {}", proprietor);
        if (add.getAppointUserId() != null) {
            SysUserMessage message = new SysUserMessage();
            message.setTitle("预约成功!");
            message.setContent("免费装修报价提交成功,稍后随选网的客服人员会与您进行进一步沟通,请注意接听来电");
            message.setCreator(add.getAppointUserId() + "");
            message.setGmtCreate(new Date());
            message.setModifier(add.getAppointUserId() + "");
            message.setGmtModified(new Date());
            message.setTaskId(id);
            message.setIsDeleted(0);
            message.setMessageType(Byte.valueOf("2"));
            message.setUserId(add.getAppointUserId());
            message.setStatus(Byte.valueOf("1"));
            message.setRemark("随选网免费预约");
            sysUserMessageMapper.insertSelective(message);
        }
        return id;
    }

    /**
     * 数据转换
     *
     * @param obj
     * @return
     */
    private ProprietorInfo dataConversion(Object obj) {
        ProprietorInfo result = new ProprietorInfo();
        BeanUtils.copyProperties(obj, result);
        return result;
    }

    @Override
    public ProprietorInfo queryProprietorByPhone(String mobile, Integer businessType, Integer type) {
        return proprietorInfoMapper.selectByPhone(mobile, businessType, type);
    }

    @Override
    public List<ProprietorInfo> queryProprietor() {
        return proprietorInfoMapper.selectByInit();
    }

    @Override
    public int updatePlatform(UnionPlatformUpdate update) {
        //校验是否存在该平台
        UnionPlatform platform = unionPlatformMapper.selectByName(update.getPlatName());
        if (null != platform) {
            //修改点击数量
            return unionPlatformMapper.updateByName(update.getPlatName());
        }
        platform = new UnionPlatform();
        platform.setPlatName(update.getPlatName());
        return unionPlatformMapper.insertSelective(platform);
    }

    @Override
    public List<ProprietorInfoVO> getProprietorInfoList(ProprietorInfoQuery query) {
        return proprietorInfoMapper.getProprietorInfoList(query);
    }

    @Override
    public Integer getProprietorInfoListCount(ProprietorInfoQuery query) {
        return proprietorInfoMapper.getProprietorInfoListCount(query);
    }

    @Override
    public Integer updateProprietorInfoProcess(List<Integer> idList) {
        return proprietorInfoMapper.updateProprietorInfoProcess(idList);
    }
}
