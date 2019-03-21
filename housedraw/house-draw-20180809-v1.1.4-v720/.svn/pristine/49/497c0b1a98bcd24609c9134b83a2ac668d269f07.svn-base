package com.sandu.web.bake.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.sandu.api.house.service.DrawBakeTaskService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.ResponseEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/bake/task")
public class DrawBakeTaskController extends BaseController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DrawBakeTaskService drawBakeTaskService;

    @PostMapping("/reset/{taskId}")
    public ReturnData resetTask(@PathVariable Integer taskId) {
        if (taskId == null) {
            return ReturnData.builder().status(false).code(ResponseEnum.PARAM_ERROR);
        }

        drawBakeTaskService.batchResetTask(Lists.newArrayList(taskId));
        return ReturnData.builder().code(ResponseEnum.SUCCESS).status(true);
    }

    @PostMapping("/batch/reset")
    public ReturnData batchResetTask(String data) {
        if (StringUtils.isEmpty(data)) {
            return ReturnData.builder().status(false).code(ResponseEnum.PARAM_ERROR);
        }

        List<String> strings = Lists.newArrayList(data.split(","));
        List<Integer> taskIds = strings.stream().filter(StringUtils::isNoneBlank).distinct().map(Integer::parseInt).collect(Collectors.toList());

        drawBakeTaskService.batchResetTask(taskIds);

        return ReturnData.builder().code(ResponseEnum.SUCCESS).status(true);
    }
}
