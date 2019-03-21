package com.sandu.service.collect.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 4:30 2018/10/23 0023
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.api.collect.input.RecordMachineInfoOperationAdd;
import com.sandu.api.collect.model.RecordMachineInfoOperation;
import com.sandu.api.collect.service.RecordMachineInfoOperationService;
import com.sandu.common.util.Utils;
import com.sandu.queue.service.QueueService;
import com.sandu.service.collect.dao.RecordMachineInfoOperationDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/10/23 0023PM 4:30
 */
@Service("recordMachineInfoOperationService")
@Slf4j
public class RecordMachineInfoOperationServiceImpl implements RecordMachineInfoOperationService {


    @Autowired
    private RecordMachineInfoOperationDao recordMachineInfoOperationDao;

    @Autowired
    private QueueService queueService;

    @Override
    public int saveActiveRecordMachineInfoOperation(RecordMachineInfoOperationAdd recordMachineInfoOperationAdd) {
        //数据转换
        RecordMachineInfoOperation record = this.buildRecordMachineInfoOperation(recordMachineInfoOperationAdd);
        log.debug("Active Record: {}", record);
        int result = recordMachineInfoOperationDao.insertRecordMachineInfoOperation(record);
        if(result > 0){
            //发送队列通知
            queueService.sendDeviceInfo(record);
            return record.getId().intValue();
        }

        return 0;
    }


    @Override
    public int saveResgiterRecordMachineInfoOperation(RecordMachineInfoOperationAdd recordMachineInfoOperationAdd) {
        //数据转换
        RecordMachineInfoOperation record = this.buildRecordMachineInfoOperation(recordMachineInfoOperationAdd);
        log.debug("Register record: {}", record);
        int result = recordMachineInfoOperationDao.insertRecordMachineInfoOperation(record);
        if(result > 0){
            //发送队列通知
            queueService.sendDeviceInfo(record);
            return record.getId().intValue();
        }

        return 0;
    }

    @Override
    public int saveLoginRecordMachineInfoOperation(RecordMachineInfoOperationAdd recordMachineInfoOperationAdd) {
        //数据转换
        RecordMachineInfoOperation record = this.buildRecordMachineInfoOperation(recordMachineInfoOperationAdd);
        log.debug("Login record: {}", record);
        int result = recordMachineInfoOperationDao.insertRecordMachineInfoOperation(record);
        if(result > 0){
            //发送队列通知
            queueService.sendDeviceInfo(record);
            return record.getId().intValue();
        }

        return 0;
    }

    private RecordMachineInfoOperation buildRecordMachineInfoOperation(RecordMachineInfoOperationAdd recordMachineInfoOperationAdd) {
        RecordMachineInfoOperation recordMachineInfoOperation = new RecordMachineInfoOperation();
        Date date = new Date();
        recordMachineInfoOperation.setUserAgent(recordMachineInfoOperationAdd.getUserAgent());
        recordMachineInfoOperation.setAppName(recordMachineInfoOperationAdd.getAppName());
        recordMachineInfoOperation.setAppVersion(recordMachineInfoOperationAdd.getAppVersion());
        recordMachineInfoOperation.setAppIp(recordMachineInfoOperationAdd.getAppIp());
        recordMachineInfoOperation.setOs(recordMachineInfoOperationAdd.getOs());
        recordMachineInfoOperation.setOsVersion(recordMachineInfoOperationAdd.getOsVersion());
        recordMachineInfoOperation.setPhoneBrand(recordMachineInfoOperationAdd.getPhoneBrand());
        recordMachineInfoOperation.setPhoneModel(recordMachineInfoOperationAdd.getPhoneModel());
        recordMachineInfoOperation.setMac(recordMachineInfoOperationAdd.getMac());
        recordMachineInfoOperation.setIdfa(recordMachineInfoOperationAdd.getIdfa());
        recordMachineInfoOperation.setAndroidId(recordMachineInfoOperationAdd.getAndroidId());
        recordMachineInfoOperation.setImei(recordMachineInfoOperationAdd.getImei());
        recordMachineInfoOperation.setUuid(recordMachineInfoOperationAdd.getUuid());
        recordMachineInfoOperation.setOpenId(recordMachineInfoOperationAdd.getOpenId());
        recordMachineInfoOperation.setUdid(recordMachineInfoOperationAdd.getUdid());
        recordMachineInfoOperation.setScreenWidth(recordMachineInfoOperationAdd.getScreenWidth());
        recordMachineInfoOperation.setScreenHeight(recordMachineInfoOperationAdd.getScreenHeight());
        recordMachineInfoOperation.setRecordType(recordMachineInfoOperationAdd.getRecordType());
        recordMachineInfoOperation.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
        recordMachineInfoOperation.setCreator("system");
        recordMachineInfoOperation.setGmtCreate(date);
        recordMachineInfoOperation.setModifier("system");
        recordMachineInfoOperation.setGmtModified(date);
        recordMachineInfoOperation.setIsDeleted(0);
        return recordMachineInfoOperation;
    }

}
