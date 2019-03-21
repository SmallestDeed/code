package com.sandu.web.designplan.controller;

import com.google.gson.Gson;
import com.sandu.cache.service.RedisService;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.designplan.model.DesignPlanProductVO;
import com.sandu.fullhouse.service.FullHouseDesignPlanService;
import com.sandu.product.model.BaseProduct;
import com.sandu.product.service.BaseProductStyleService;
import com.sandu.system.model.BaseHousePicFullHousePlanRel;
import com.sandu.system.model.RenderTaskState;
import com.sandu.system.model.RenderTaskStateSearch;
import com.sandu.system.service.BaseHousePicFullHousePlanRelService;
import com.sandu.system.service.ResPicService;
import com.sandu.system.service.SysDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/miniprogram/fullHousePlan")
public class FullHouseController {

    //Json转换类
    private final static Gson gson = new Gson();
    private final static String DESIGN_PLAN_CONDITION_METADATA="DESIGN_PLAN_CONDITION_METADATA";
    private final static String CLASS_LOG_PREFIX = "[设计方案服务]:";
    private final static Logger logger = LoggerFactory.getLogger(FullHouseController.class);
    @Resource
    private ResPicService resPicService;
    @Resource
    private BaseProductStyleService baseProductStyleService;
    @Resource
    private SysDictionaryService sysDictionaryService;
    @Resource
    private RedisService redisService;
    @Resource
    private FullHouseDesignPlanService fullHouseDesignPlanService;
    @Autowired
    private BaseHousePicFullHousePlanRelService baseHousePicFullHousePlanRelService;


    /**
     * 方案富文本详情获取
     */
    @RequestMapping("/config")
    public ResponseEnvelope configDesignPlan(@RequestParam Integer id) {
        String content = fullHouseDesignPlanService.showDesignPlanConfig(id);
        return new ResponseEnvelope(true, "", content);
    }

    /**
     * 全屋方案单空间方案产品集合获取
     */
    @RequestMapping("/singlePlan/combo")
    public ResponseEnvelope getDesignPlanProduct(@RequestParam Integer planId, @RequestParam Integer productBatchType,
                                                 @RequestParam(required = false) String spaceName,@RequestParam(required = false) Integer pageNo) {
        List<DesignPlanProductVO> results = fullHouseDesignPlanService.listDesignPlanProducts(planId,productBatchType,spaceName,pageNo);
        if (results != null && results.size() > 0) {
            return new ResponseEnvelope(true, "", results);
        } else {
            return new ResponseEnvelope(false, "没有数据了", null);
        }
    }

    /**
     * 查询全屋方案的任务状态
     * @param fullPlanHouseId
     * @param templateId
     * @return
     */
    @RequestMapping("/getSpaceRenderState")
    public ResponseEnvelope getSpaceRenderState(@RequestParam Integer fullPlanHouseId,
                                                @RequestParam Integer templateId){
        List<Integer> templateIds = new ArrayList<>();
        templateIds.add(templateId);
        RenderTaskStateSearch search = new RenderTaskStateSearch();
        search.setNewFullPlanHouseId(fullPlanHouseId);
        search.setTemplateIds(templateIds);
        // 先到任务状态表里查询任务状态记录
        List<RenderTaskState> stateList = baseHousePicFullHousePlanRelService.getRenderTaskStateList(search);
        if (stateList == null || stateList.size() <= 0) {
            // 查不到任务状态表的话就去任务表里查
            List<RenderTaskState> taskList = baseHousePicFullHousePlanRelService.getRenderTaskListByFullHouseIdAndTemplateIds(search);
            if (taskList == null || taskList.size() <= 0){
                // 任务表里也查不到，有可能是因为任务刚好被取了，再去任务状态表查一次
                List<RenderTaskState> newStateList = baseHousePicFullHousePlanRelService.getRenderTaskStateList(search);
                if (newStateList == null || newStateList.size() <= 0){
                    BaseHousePicFullHousePlanRel fullHousePlanRel = baseHousePicFullHousePlanRelService.getBaseHousePicFullHousePlanRelByTemplateId(search);
                    if (null == fullHousePlanRel) {
                        // 查不到则该空间未渲染
                        return new ResponseEnvelope(true, "该空间未渲染", 3);
                    } else {
                        return new ResponseEnvelope(true,1);
                    }
                }else if (newStateList.size() == 1){
                    // 查到了则返回任务状态
                    return new ResponseEnvelope(true, stateList.get(0).getState());
                }else if (newStateList.size() > 1){
                    return new ResponseEnvelope(false, "查询出的任务状态结果大于1条");
                }
            }else if (taskList.size() == 1){
                // 任务表里查到了数据，返回值2为当前空间正在渲染中
                return new ResponseEnvelope(true, 2);
            }else {
                return new ResponseEnvelope(false, "查询出的任务结果大于1条");
            }
        }else if (stateList.size() == 1){
            // 任务状态表查到了则返回任务状态
            return new ResponseEnvelope(true, stateList.get(0).getState());
        }else if (stateList.size() > 1){
            return new ResponseEnvelope(false, "查询出的任务状态结果大于1条");
        }
        return null;
    }
}
