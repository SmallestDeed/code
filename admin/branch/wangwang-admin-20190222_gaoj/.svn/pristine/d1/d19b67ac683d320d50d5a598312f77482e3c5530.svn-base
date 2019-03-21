package com.sandu.web.activity;

import static com.sandu.constant.ResponseEnum.ERROR;
import static com.sandu.constant.ResponseEnum.NOT_CONTENT;
import static com.sandu.constant.ResponseEnum.SUCCESS;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sandu.api.activity.input.WxSpringActivityAdd;
import com.sandu.api.activity.input.WxSpringActivityQuery;
import com.sandu.api.activity.input.WxSpringExport;
import com.sandu.api.activity.model.WxSpringActivityBO;
import com.sandu.api.activity.output.WxSpringActivityVO;
import com.sandu.api.activity.service.WxSpringActivityService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.util.excel.ExportExcel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@Api(value = "springActivity", tags = "springActivity", description = "springActivity")
@RestController
@RequestMapping(value = "/v1/springActivity")
@Slf4j
public class WxSpringActivityController extends BaseController {

    @Autowired
    private WxSpringActivityService wxSpringActivityService;

    //签到总数据
    private final Integer EXPORT_CHECK_DATA = 1;

    //签到日期分类数量
    private final Integer EXPORT_DATE_DATA = 2;

    //转盘数据
    private final Integer EXPORT_WHEEL_DATA = 3;

    //拼图数据
    private final Integer EXPORT_PICTURE_DATA = 4;

    //任务数据
    private final Integer EXPORT_TASK_DATA = 5;

    @ApiOperation(value = "查询春节活动列表", response = WxSpringActivityVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer")
    })
    @GetMapping(value = "/list")
    public ReturnData queryBasesupplydemandList(@Valid WxSpringActivityQuery query, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        final PageInfo<WxSpringActivityBO> results = wxSpringActivityService.queryAllActivity(query);
        log.debug("Result: {}", results);
        if (results != null && results.getTotal() > 0) {
            final List<WxSpringActivityVO> outputs = Lists.newArrayList();
            results.getList().stream().forEach(result -> {
                WxSpringActivityVO output = new WxSpringActivityVO();
                BeanUtils.copyProperties(result, output);

                outputs.add(output);
            });
            return ReturnData.builder().code(SUCCESS).data(outputs).total(results.getTotal());
        }

        return ReturnData.builder().code(NOT_CONTENT).message("暂无数据");
    }


    @ApiOperation(value = "查询转盘列表", response = WxSpringActivityVO.class)
    @GetMapping(value = "/getWheelList")
    public ReturnData getWheelList() {

        List<WxSpringActivityBO> results = wxSpringActivityService.wheelList();
        log.debug("Result: {}", results);
        if (results != null && results.size() > 0) {
            final List<WxSpringActivityVO> outputs = Lists.newArrayList();
            results.stream().forEach(result -> {
                WxSpringActivityVO output = new WxSpringActivityVO();
                BeanUtils.copyProperties(result, output);

                outputs.add(output);
            });
            return ReturnData.builder().code(SUCCESS).data(outputs);
        }

        return ReturnData.builder().code(NOT_CONTENT).message("暂无数据");
    }

    @ApiOperation(value = "回显转盘信息", response = WxSpringActivityVO.class)
    @GetMapping(value = "/getWheelInfo")
    public ReturnData getWheelInfo(String id) {

        WxSpringActivityBO result = wxSpringActivityService.getWheelInfo(id);
        log.debug("Result: {}", result);
        if (result != null) {
            WxSpringActivityVO output = new WxSpringActivityVO();
            BeanUtils.copyProperties(result, output);
            return ReturnData.builder().code(SUCCESS).data(output);
        }
        return ReturnData.builder().code(NOT_CONTENT).message("没有匹配该活动的转盘(未设置现金抽奖金额)");
    }

    @ApiOperation(value = "新建春节活动", response = ReturnData.class)
    @PostMapping
    public ReturnData createActivity(@Valid @RequestBody WxSpringActivityAdd input, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }

        Integer count = wxSpringActivityService.addSpringActivity(input);
        if (count > 0) {
            return ReturnData.builder().code(SUCCESS).data(count);
        }

        return ReturnData.builder().code(ERROR).message("输入数据有误");
    }

    @GetMapping("/export")
    @ApiOperation(value = "春节活动数据导出", response = ResponseEnvelope.class)
    public void export(WxSpringExport export, HttpServletResponse response) throws Exception {
        //签到总数据
        List<WxSpringActivityBO> datas1 = wxSpringActivityService.queryExport(EXPORT_CHECK_DATA,export.getActivityId());
        //签到日期分类数量
        List<WxSpringActivityBO> datas2 = wxSpringActivityService.queryExport(EXPORT_DATE_DATA,export.getActivityId());
        //转盘数据
        List<WxSpringActivityBO> datas3 = wxSpringActivityService.queryExport(EXPORT_WHEEL_DATA,export.getWheelId());
        //拼图数据
        List<WxSpringActivityBO> datas4 = wxSpringActivityService.queryExport(EXPORT_PICTURE_DATA,export.getActivityId());
        //任务数据
        List<WxSpringActivityBO> datas5 = wxSpringActivityService.queryExport(EXPORT_TASK_DATA,export.getActivityId());
        
        try {
            List<Map<String, Object>> result1 = this.createExcelRecord(datas1,EXPORT_CHECK_DATA);
            List<Map<String, Object>> result2 = this.createExcelRecord(datas2,EXPORT_DATE_DATA);
            List<Map<String, Object>> result3 = this.createExcelRecord(datas3,EXPORT_WHEEL_DATA);
            List<Map<String, Object>> result4 = this.createExcelRecord(datas4,EXPORT_PICTURE_DATA);
            List<Map<String, Object>> result5 = this.createExcelRecord(datas5,EXPORT_TASK_DATA);

            // 导出表格名称
            String filename = "春节活动信息";
            Map<String, String[]> titleMap = new HashMap<>();
            String columns1[] = {"序号","微信昵称", "签到次数", "红包金额"};
            String columns2[] = {"序号","日期", "微信昵称", "签到时间", "红包金额"};
            String columns3[] = {"序号","微信昵称", "抽奖总次数", "已用抽奖次数", "奖品记录"};
            String columns4[] = {"序号","微信昵称", "卡片数量", "任务一状态", "任务二状态", "任务三状态", "邀请好友总数量"};
            String columns5[] = {"序号","任务", "领取人数", "完成人数"};
            titleMap.put("签到总数据", columns1);
            titleMap.put("签到日期分类数量", columns2);
            titleMap.put("转盘数据", columns3);
            titleMap.put("拼图数据", columns4);
            titleMap.put("任务数据", columns5);
            Map<String, String[]> keyMap = new HashMap<>();
            String keys1[] = {"id", "nickName", "signCount", "redPacketAmount"};
            String keys2[] = {"id", "date", "nickName", "signDate", "redPacketAmount"};
            String keys3[] = {"id", "nickName", "rewardCount", "useRewardCount", "rewardRecord"};
            String keys4[] = {"id", "nickName", "cardCount", "taskOne", "taskTwo", "taskThree", "inviteCount"};
            String keys5[] = {"id", "task", "received", "notReceived"};
            keyMap.put("签到总数据", keys1);
            keyMap.put("签到日期分类数量", keys2);
            keyMap.put("转盘数据", keys3);
            keyMap.put("拼图数据", keys4);
            keyMap.put("任务数据", keys5);
            String[] arr = new String[20];
            arr[0] = "签到总数据";
            arr[1] = "签到日期分类数量";
            arr[2] = "转盘数据";
            arr[3] = "拼图数据";
            arr[4] = "任务数据";
            Map<String, List<Map<String, Object>>> listMap = new HashMap<>();
            listMap.put("签到总数据", result1);
            listMap.put("签到日期分类数量", result2);
            listMap.put("转盘数据", result3);
            listMap.put("拼图数据", result4);
            listMap.put("任务数据", result5);
            Workbook wookbook = ExportExcel.createWorkBookThree(listMap, keyMap, titleMap, arr);
            try {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename+".xls", "utf-8"));
                OutputStream outputStream = response.getOutputStream();
                wookbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Map<String, Object>> createExcelRecord(List<WxSpringActivityBO> list, Integer type) {
        List<Map<String, Object>> listmap = new ArrayList<>();
        WxSpringActivityBO project;
        int index = 1;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        switch (type) {
            case 1:
                for (int j = 0; j < list.size(); j++) {
                    project = list.get(j);
                    Map<String, Object> mapValue = new HashMap<>();
                    mapValue.put("id", index++);
                    mapValue.put("nickName", project.getExportData1());
                    mapValue.put("signCount", project.getExportData2());
                    mapValue.put("redPacketAmount", project.getExportData3());
                    listmap.add(mapValue);
                }
                break;
            case 2:
                for (int j = 0; j < list.size(); j++) {
                    project = list.get(j);
                    Map<String, Object> mapValue = new HashMap<>();
                    mapValue.put("id", index++);
                    mapValue.put("date", format2.format(project.getExportData7()));
                    mapValue.put("nickName", project.getExportData1());
                    mapValue.put("signDate", format.format(project.getExportData8()));
                    mapValue.put("redPacketAmount", project.getExportData2());
                    listmap.add(mapValue);
                }
                break;
            case 3:
                for (int j = 0; j < list.size(); j++) {
                    project = list.get(j);
                    Map<String, Object> mapValue = new HashMap<>();
                    mapValue.put("id", index++);
                    mapValue.put("nickName", project.getExportData1());
                    mapValue.put("rewardCount", project.getExportData3());
                    mapValue.put("useRewardCount", project.getExportData4());
                    mapValue.put("rewardRecord", project.getExportData2());
                    listmap.add(mapValue);
                }
                break;
            case 4:
                for (int j = 0; j < list.size(); j++) {
                    project = list.get(j);
                    Map<String, Object> mapValue = new HashMap<>();
                    mapValue.put("id", index++);
                    mapValue.put("nickName", project.getExportData1());
                    mapValue.put("cardCount", project.getExportData2());
                    mapValue.put("inviteCount", project.getExportData3());
                    mapValue.put("taskOne", processTaskStatus(project.getExportData4()));
                    mapValue.put("taskTwo", processTaskStatus(project.getExportData5()));
                    mapValue.put("taskThree", processTaskStatus(project.getExportData6()));
                    listmap.add(mapValue);
                }
                break;
            case 5:

                for (int j = 0; j < list.size(); j++) {
                    project = list.get(j);
                    for(int i = 0 ; i< 3 ; i++){
                        Map<String, Object> mapValue = new HashMap<>();
                        mapValue.put("id", index++);
                        switch (i) {
                            case 0:
                                mapValue.put("task", "绑定手机号");
                                mapValue.put("received", project.getExportData1());
                                mapValue.put("notReceived", project.getExportData2());
                                break;
                            case 1:
                                mapValue.put("task", "装修我家");
                                mapValue.put("received", project.getExportData3());
                                mapValue.put("notReceived", project.getExportData4());
                                break;
                            case 2:
                                mapValue.put("task", "产品替换");
                                mapValue.put("received", project.getExportData5());
                                mapValue.put("notReceived", project.getExportData6());
                                break;
                            default:
                                break;
                        }
                        listmap.add(mapValue);
                    }
                }
                break;
            default:
                listmap = null;
                break;
        }
        return listmap;
    }
    
    private String processTaskStatus(String status) {
    	String taskStatus = "";
    	if(!StringUtils.isEmpty(status)) {
    		switch (status) {
			case "1":
				taskStatus ="已领取";
				break;
			case "2":
				taskStatus ="已完成";
				break;
			default:
				break;
			}
    	}
    	return taskStatus;
    }

}
