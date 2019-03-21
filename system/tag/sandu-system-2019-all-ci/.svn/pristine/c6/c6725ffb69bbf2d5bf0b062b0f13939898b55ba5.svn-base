package com.sandu.web.collect;


import com.sandu.api.collect.contants.RecordMachineInfoOperationContant;
import com.sandu.api.collect.input.RecordMachineInfoOperationAdd;
import com.sandu.api.collect.service.RecordMachineInfoOperationService;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.gson.GsonUtil;
import com.sandu.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 * 记录设备信息操作controller
 *
 * @author weis
 * @datetime 2018/10/23 15:35
 */
@RestController
@RequestMapping(value = "/v1/collect")
public class MachineInfoOperationController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(MachineInfoOperationController.class);

    private final RecordMachineInfoOperationService recordMachineInfoOperationService;

    @Autowired
    public MachineInfoOperationController(RecordMachineInfoOperationService recordMachineInfoOperationService) {
        this.recordMachineInfoOperationService = recordMachineInfoOperationService;
    }

    @PostMapping(value = "/save")
    public ResponseEnvelope saveMachineInfoOperationRecord(@Valid @RequestBody RecordMachineInfoOperationAdd recordMachineInfoOperationAdd, BindingResult validResult) {
        logger.debug("Record: {}", recordMachineInfoOperationAdd);
        //校验请求参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }

        //判断业务类型为1是保存激活设备信息
        int recordMachineInfoOperationId = 0;
        if (recordMachineInfoOperationAdd.getRecordType().intValue() == RecordMachineInfoOperationContant.MACHINE_ACTIVE) {
            try {
                recordMachineInfoOperationId = recordMachineInfoOperationService.saveActiveRecordMachineInfoOperation(recordMachineInfoOperationAdd);
                if (!(recordMachineInfoOperationId > 0)) {
                    logger.error("保存激活设备信息数据失败:" + GsonUtil.toJson(recordMachineInfoOperationAdd));
                    return new ResponseEnvelope(false, "保存激活设备信息数据失败");
                }
            } catch (Exception e) {
                logger.error("保存激活设备信息数据异常:", e);
                return new ResponseEnvelope<>(false, "保存激活设备信息数据异常!");
            }
            //2是保存注册设备信息
        } else if (recordMachineInfoOperationAdd.getRecordType().intValue() == RecordMachineInfoOperationContant.MACHINE_REGISTER) {
            if (null == recordMachineInfoOperationAdd.getUserId() || 0 == recordMachineInfoOperationAdd.getUserId()) {
                return new ResponseEnvelope<>(false, "保存注册设备信息缺失userId!");
            }
            try {
                recordMachineInfoOperationId = recordMachineInfoOperationService.saveResgiterRecordMachineInfoOperation(recordMachineInfoOperationAdd);
                if (!(recordMachineInfoOperationId > 0)) {
                    logger.error("保存注册设备信息数据失败:" + GsonUtil.toJson(recordMachineInfoOperationAdd));
                    return new ResponseEnvelope(false, "保存注册设备信息数据失败");
                }
            } catch (Exception e) {
                logger.error("保存注册设备信息数据异常:", e);
                return new ResponseEnvelope(false, "保存注册设备信息数据异常!");
            }
        }else if(recordMachineInfoOperationAdd.getRecordType().intValue() == RecordMachineInfoOperationContant.MACHINE_LOGIN){
            recordMachineInfoOperationId = recordMachineInfoOperationService.saveLoginRecordMachineInfoOperation(recordMachineInfoOperationAdd);
        }

        logger.info("保存注册设备信息数据成功:" + recordMachineInfoOperationId);
        //保存成功后像rabbitmq中写入一条消息
        try {
            List<Integer> ids = new ArrayList<>();
            ids.add(recordMachineInfoOperationId);
            logger.info("发送消息成功:" + GsonUtil.toJson(ids));
            return new ResponseEnvelope(true, "保存成功", recordMachineInfoOperationId);
        } catch (Exception e) {
            logger.error("发送消息失败:" + e);
        } finally {
            return new ResponseEnvelope(true, "保存成功", recordMachineInfoOperationId);
        }
    }

}
