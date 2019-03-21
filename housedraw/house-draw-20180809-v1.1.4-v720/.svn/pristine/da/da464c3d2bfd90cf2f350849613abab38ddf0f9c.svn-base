package com.sandu.web.res.task.controller;

import com.sandu.api.task.input.CleanResTaskConfigUpdate;
import com.sandu.api.task.service.CleanDrawResFileTaskService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/30
 */

@RestController
@RequestMapping("/v1/clean/res/task")
public class CleanResourceTaskController extends BaseController {

    @Autowired
    CleanDrawResFileTaskService cleanDrawResFileTaskService;

    @PostMapping("/update/conf")
    public ReturnData updateLimit(String msgId, CleanResTaskConfigUpdate updateConf) {
        ReturnData response = setMsgId(msgId);
        if (updateConf == null) {
            throw new BizException("参数 updateConf不能为空");
        }

        cleanDrawResFileTaskService.updateCleanResTaskConfig(updateConf);
        return response.status(true).code(ResponseEnum.SUCCESS);
    }
}
