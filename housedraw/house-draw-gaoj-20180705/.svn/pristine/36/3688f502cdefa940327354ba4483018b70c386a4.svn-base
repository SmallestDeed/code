package com.sandu.web.fix.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.fix.model.*;
import com.sandu.api.fix.service.FixCupboardService;
import com.sandu.api.house.bo.DrawBakeTaskBO;
import com.sandu.api.house.bo.DrawBaseProductBO;
import com.sandu.api.house.input.PublicProductInfoDTO;
import com.sandu.api.house.model.DrawBakeTaskDetail;
import com.sandu.api.house.model.DrawDesignTemplet;
import com.sandu.api.house.model.DrawResFile;
import com.sandu.api.house.output.DrawBakeTaskDetailVO;
import com.sandu.api.house.output.DrawBakeTaskVO;
import com.sandu.api.house.output.DrawProductDataVO;
import com.sandu.api.house.service.DrawDesignTempletService;
import com.sandu.api.house.service.DrawResFileService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.house.DrawBaseProductConstant;
import com.sandu.exception.AppException;
import com.sandu.exception.BizException;
import com.sandu.exception.BusinessException;
import com.sandu.exception.DrawBusinessException;
import com.sandu.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/v1/fix/cupboard")
public class FixCupboardController extends BaseController {

    @Value("${resource.domain}")
    private String resourceDomain;

    @Autowired
    DrawResFileService drawResFileService;

    @Autowired
    FixCupboardService fixCupboardService;

    @Autowired
    DrawDesignTempletService drawDesignTempletService;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/list")
    public ReturnData listFixCupboardHouse(HttpServletRequest request, FixCupboardQuery query) {
        ReturnData response = setMsgId(request);
        // pageSize
//        query.setPageSize(super.getLimit(query.getPageSize()));
//        query.setPageNum(super.getPage(query.getPageNum(), query.getPageSize()));

        Map<String, Object> map = fixCupboardService.listFixCupboardHouse(query);
        if (map != null && map.get("list") != null) {
            return response.list((List<FixCupboardListBO>) map.get("list"));
        }

        return response;
    }

    @PostMapping("/save")
    public ReturnData save(HttpServletRequest request, String data) {
        ReturnData response = setMsgId(request);

        if (StringUtils.isEmpty(data)) {
            return response.status(false).code(ResponseEnum.PARAM_ERROR);
        }

        FixCupboardSaveBO fixCupboard;
        try {
            fixCupboard = objectMapper.readValue(data, FixCupboardSaveBO.class);
        } catch (IOException e) {
            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
        }

        if (fixCupboard == null) {
            return response.status(false).code(ResponseEnum.PARAM_ERROR);
        }

        // 保存户型
        Map<String, Object> saveMap = fixCupboardService.save(fixCupboard);

        return response.data(saveMap);
    }

    @PostMapping("/submit")
    public ReturnData submit(HttpServletRequest request, @RequestParam("jsonStr") String data) {
        ReturnData response = setMsgId(request);
        if (StringUtils.isEmpty(data)) {
            return response.status(false).code(ResponseEnum.PARAM_ERROR);
        }

        FixCupboardSubmitBO fixCupboard;
        try {
            fixCupboard = objectMapper.readValue(data, FixCupboardSubmitBO.class);
        } catch (IOException e) {
            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
        }

        if (fixCupboard == null) {
            return response.status(false).code(ResponseEnum.PARAM_ERROR);
        }

        // submit
        fixCupboardService.submit(fixCupboard);

        return response;
    }

    @PostMapping("/getTask")
    public ReturnData getTask(HttpServletRequest request, String queueName) {
        ReturnData response = setMsgId(request);

        Long taskId = fixCupboardService.getTaskId(queueName);
        if (taskId != null) {
            DrawBakeTaskVO taskVO = new DrawBakeTaskVO();
            taskVO.setTaskId(taskId);
            return response.data(taskVO);
        }

        return response.message("暂时没有烘焙任务");
    }

    @PostMapping("/getSubTask")
    public ReturnData getSubTask(HttpServletRequest request, Long taskId, String queueName) {
        ReturnData response = setMsgId(request);
        if (taskId == null) {
            return response.status(false).code(ResponseEnum.PARAM_ERROR);
        }

        response.setResourceUrl(resourceDomain);

        // get sub task
        List<DrawBakeTaskBO> subTask = fixCupboardService.getSubTask(queueName, taskId);

        return response.data(getDrawBakeTaskVO(subTask));
    }

    private DrawBakeTaskVO getDrawBakeTaskVO(List<DrawBakeTaskBO> list) throws BusinessException {
        // 返回页面数据对象
        DrawBakeTaskVO taskVO = new DrawBakeTaskVO();

        // json
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, PublicProductInfoDTO.class);

        list.parallelStream().forEach(task -> {
            // 任务数据
            taskVO.setTaskId(task.getId());
            taskVO.setHouseId(task.getHouseId());

            // 任务详情数据
            List<DrawBakeTaskDetail> taskDetails = task.getDrawBakeTaskDetail();

            if (taskDetails != null && !taskDetails.isEmpty()) {
                DrawBakeTaskDetail detail = taskDetails.get(0);
                DrawBakeTaskDetailVO detailVO = new DrawBakeTaskDetailVO();
                detailVO.setTaskDetailId(detail.getId());
                // 空间
                detailVO.setSpaceId(detail.getSpaceId());
                detailVO.setSpaceCode(detail.getSpaceCode());
                detailVO.setSpaceFileId(Long.parseLong(detail.getSpaceFileIds()));
                detailVO.setSpaceFilePath(detail.getSpaceFilePath());

                // 产品view对象
                List<DrawProductDataVO> drawProductDataVOS = getDrawProductDataVOS(detail);

                // public产品对象(栏杆/窗户等) ->start
                List<DrawDesignTemplet> drawDesignTempletList = drawDesignTempletService.findAllBySpaceCommonId(detail.getSpaceId());
                if (drawDesignTempletList == null || drawDesignTempletList.size() == 0) {
                    log.error("draw_样板房信息没有找到,drawSpaceCommonId = {}", detail.getSpaceId());
                } else {
                    DrawDesignTemplet drawDesignTemplet = drawDesignTempletList.get(0);
                    if (StringUtils.isNotEmpty(drawDesignTemplet.getPublicProductInfo())) {
                        List<PublicProductInfoDTO> publicProductInfoDTOList;
                        try {
                            publicProductInfoDTOList = objectMapper.readValue(drawDesignTemplet.getPublicProductInfo(), javaType);
                        } catch (Exception e) {
                            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
                        }

                        if (publicProductInfoDTOList != null && publicProductInfoDTOList.size() > 0) {
                            for (PublicProductInfoDTO publicProductInfoDTO : publicProductInfoDTOList) {
                                DrawResFile drawResFile = drawResFileService.get(publicProductInfoDTO.getFileId());
                                if (drawResFile == null) {
                                    log.error("draw_res_file数据没有找到,id = {}", publicProductInfoDTO.getFileId());
                                    continue;
                                }
                                DrawProductDataVO drawProductDataVO = new DrawProductDataVO();
                                drawProductDataVO.setFileId(publicProductInfoDTO.getFileId());
                                drawProductDataVO.setFileName(drawResFile.getFileName());
                                drawProductDataVO.setFilePath(drawResFile.getFilePath());
                                drawProductDataVO.setUniqueId(publicProductInfoDTO.getUniqueId());
                                drawProductDataVO.setModelType(DrawBaseProductConstant.MODEL_TYPE_PUBLIC);
                                drawProductDataVOS.add(drawProductDataVO);
                            }
                        }
                    }
                }
                // public产品对象(栏杆/窗户等) ->end

                detailVO.setProductData(drawProductDataVOS);

                taskVO.setTaskDetail(detailVO);
            }
        });

        return taskVO;
    }

    private List<DrawProductDataVO> getDrawProductDataVOS(DrawBakeTaskDetail detail) {
        String data = detail.getProductData();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, DrawBaseProductBO.class);
        List<DrawBaseProductBO> drawBaseProductBOS;

        try {
            drawBaseProductBOS = objectMapper.readValue(data, javaType);
        } catch (IOException e) {
            throw new BizException(e);
        }

        List<DrawProductDataVO> drawProductDataVOS = new ArrayList<>();
        if (drawBaseProductBOS != null && !drawBaseProductBOS.isEmpty()) {
            for (DrawBaseProductBO drawBaseProductBO : drawBaseProductBOS) {
                DrawProductDataVO drawProductDataVO = new DrawProductDataVO();
                drawProductDataVO.setProductId(drawBaseProductBO.getId());
                drawProductDataVO.setProductCode(drawBaseProductBO.getProductCode());
                drawProductDataVO.setFileId(drawBaseProductBO.getFileId());
                drawProductDataVO.setFileName(drawBaseProductBO.getFileName());
                drawProductDataVO.setFilePath(drawBaseProductBO.getFilePath());
                drawProductDataVO.setUniqueId(drawBaseProductBO.getUniqueId());
                drawProductDataVO.setModelType(DrawBaseProductConstant.MODEL_TYPE_HARD);
                drawProductDataVOS.add(drawProductDataVO);
            }
        }

        return drawProductDataVOS;
    }

    @PostMapping("/callback")
    public ReturnData callback(HttpServletRequest request, @RequestParam("jsonStr") String data) {
        ReturnData response = setMsgId(request);

        if (StringUtils.isEmpty(data)) {
            return response.status(false).code(ResponseEnum.PARAM_ERROR);
        }

        FixCupboardCallbackBO fixCupboard = null;
        try {
            fixCupboard = objectMapper.readValue(data, FixCupboardCallbackBO.class);
            if (fixCupboard == null) {
                throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR);
            }

            // handler bake task
            String message = fixCupboardService.callback(fixCupboard);
            return response.message(Objects.toString(message, "未知的错误!"));
        } catch (Exception e) {
            log.error("处理烘焙任务异常, data => {}", data, e);
            // handler error task
            return handlerCallbackError(response, fixCupboard, data, e);
        }
    }

    /**
     * 处理烘焙失败的任务
     *
     * @param response
     * @param fixCupboard
     * @param data
     * @param e
     * @return
     */
    private ReturnData handlerCallbackError(ReturnData response, FixCupboardCallbackBO fixCupboard, String data, Exception e) {
        try {
            if (fixCupboard == null) {
                fixCupboard = objectMapper.readValue(data, FixCupboardCallbackBO.class);
            }

            fixCupboard.setMessage(ExceptionUtils.getStackTrace(e));

            // handler callback error
            fixCupboardService.handlerCallbackError(fixCupboard, data);
        } catch (Exception ex) {
            log.error("ERROR--ERROR--回滚烘焙任务异常--ERROR--ERROR", ex);
        }

        // handler app exception
        if (e instanceof AppException) {
            AppException ex = (AppException) e;
            return response.status(false).code(ex.getResponseEnum());
        }

        // handler exception
        return response.status(false).message(Objects.toString(e.getMessage(), "未知的错误!"));
    }
}
