package com.sandu.web.area;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.sandu.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sandu.api.area.input.BaseAreaListQuery;
import com.sandu.api.area.output.BaseAreaListVO;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author chenqiang
 * @Description 系统区域 控制层
 * @Date 2018/6/1 0001 10:17
 * @Modified By
 */
@Api(tags = "baseArea", description = "基础系统区域")
@RestController
@RequestMapping("/v1/base/area")
public class BaseAreaController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(BaseAreaController.class);

    @Autowired
    private BaseAreaService baseAreaService;

    @ApiOperation(value = "行政区域列表查询", response = BaseAreaListVO.class)
    @PostMapping("/list")
    public ResponseEnvelope getAreaListByCode(HttpServletRequest request, HttpServletResponse response,
                                              @Valid @RequestBody BaseAreaListQuery query, BindingResult validResult) {
        try {

            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }

            //2.获取list数据
            List<BaseAreaListVO> areaList = baseAreaService.getAreaListByCode(query);

            return new ResponseEnvelope<>(true, areaList.size() , areaList,query.getMsgId());

        } catch (Exception e) {

            logger.error("getAreaListByCode 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!",query.getMsgId());

        }
    }

    @ApiOperation(value = "行政区域列表查询(U3D专用)", response = BaseAreaListVO.class)
    @PostMapping("/u3d/list")
    public ResponseEnvelope getAreaListByCode(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam("areaCode")String areaCode,@RequestParam("msgId")String msgId) {
        try {
            //1.数据校验
            if(StringUtils.isEmpty(areaCode))
                return new ResponseEnvelope<>(false, "区域code不能为空!",msgId);
            if(StringUtils.isEmpty(msgId))
                return new ResponseEnvelope<>(false, "msgId不能为空!",msgId);

            //2.组装参数
            BaseAreaListQuery query = new BaseAreaListQuery();
            query.setAreaCode(areaCode);
            query.setMsgId(msgId);

            //3.获取list数据
            List<BaseAreaListVO> areaList = baseAreaService.getAreaListByCode(query);

            return new ResponseEnvelope<>(true, areaList.size() , areaList,query.getMsgId());

        } catch (Exception e) {

            logger.error("getAreaListByCode 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!",msgId);

        }
    }

}
