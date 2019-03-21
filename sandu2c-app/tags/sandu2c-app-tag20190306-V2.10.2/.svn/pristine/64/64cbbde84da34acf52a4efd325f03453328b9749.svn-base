package com.sandu.web.plan;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 4:43 2018/7/26 0026
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.designplan.model.ResRenderPic;
import com.sandu.designplan.service.DesignPlanRecommendedService;
import com.sandu.designplan.view.SharePlanDTO;
import com.sandu.system.service.ResRenderPicService;
import com.sandu.user.service.SysUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: 推荐方案
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/7/26 0026PM 4:43
 */
@Slf4j
@RestController
@RequestMapping("/v1/union/planRecommended")
public class designPlanRecommendedController {

    private final static String CLASS_LOG_PREFIX = "[推荐方案基础服务]";

    private final static Gson gson = new Gson();



    @Autowired
    private DesignPlanRecommendedService designPlanRecommendedService;

    @Autowired
    private ResRenderPicService resRenderPicService;

    /**
     * @Title: 推荐分享方案列表
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/getshareplanlist", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取推荐分享方案列表", response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curPage", value = "当前页码", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条", required = true, paramType = "query", dataType = "int")
    })
    public ResponseEnvelope getSharePlanList(@ModelAttribute PageModel pageModel,HttpServletRequest request){
        ResponseEnvelope res = new ResponseEnvelope();
        //设置分页参数
        pageModel.setLimit((0 == pageModel.getPageSize()) ? PageModel.DEFAULT_PAGE_PAGESIZE : pageModel.getPageSize());
        pageModel.setStart(pageModel.getStart());
        Integer totalCount = 0 ;
        totalCount = designPlanRecommendedService.getSharePlanCount();
        if(totalCount == 0){
            res = new ResponseEnvelope(false,"SharePlanCount is zero");
            return res;
        }
        log.info(CLASS_LOG_PREFIX+"--推荐分享方案列表总数:"+totalCount);
        List<SharePlanDTO> sharePlanDTOList = designPlanRecommendedService.getSharePlanList(pageModel);
        if( null== sharePlanDTOList || sharePlanDTOList.size() ==0){
            res = new ResponseEnvelope(false,"shareplanlist is null");
            return res;
        }
        //获取一键方案列表的照片级渲染图作为封面
        List <Integer> recommendedIds = new ArrayList<Integer>();
        for (SharePlanDTO result : sharePlanDTOList) {
            recommendedIds.add(result.getPlanRecommendedId());
        }
        List<ResRenderPic> resPicList = resRenderPicService.getResRenderPicListByRecommendedIds(recommendedIds);
        for (SharePlanDTO result : sharePlanDTOList) {
            for (ResRenderPic resPic : resPicList) {
                if (res != null && resPic.getPlanRecommendedId().intValue() == result.getPlanRecommendedId().intValue()) {
                    result.setPlanCoverPath(resPic.getPicPath());
                }
            }
        }
        log.info(CLASS_LOG_PREFIX+"--推荐分享方案列表信息:"+gson.toJson(sharePlanDTOList));
        res = new ResponseEnvelope(true,"success",sharePlanDTOList,totalCount);
        return res;
    }



















}
