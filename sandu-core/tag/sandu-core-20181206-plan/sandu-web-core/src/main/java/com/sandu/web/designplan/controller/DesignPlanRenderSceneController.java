package com.sandu.web.designplan.controller;

import com.google.gson.Gson;
import com.sandu.api.base.common.BaseController;
import com.sandu.api.base.common.LoginUser;
import com.sandu.api.base.common.ResponseEnvelope;
import com.sandu.api.base.common.exception.BizException;
import com.sandu.api.designplan.input.DesignPlanInput;
import com.sandu.api.designplan.input.FullHousePlanInput;
import com.sandu.api.designplan.input.PlanInput;
import com.sandu.api.designplan.input.PlanRenderSceneInput;
import com.sandu.api.designplan.output.DesignPlanDecorationVo;
import com.sandu.api.designplan.output.DesignPlanRenderSceneVo;
import com.sandu.api.designplan.output.SingleDesignPlanVo;
import com.sandu.api.designplan.output.SinglePlanRenderSceneVo;
import com.sandu.api.designplan.service.DesignPlanRenderSceneService;
import com.sandu.api.fullhouse.input.FullHouseDesignPlanAdd;
import com.sandu.common.LoginContext;
import lombok.extern.slf4j.Slf4j;
;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xiaoxc on 2018/5/31 0031.
 */
@Slf4j
@RestController
@RequestMapping("/v1/core/designplanrenderscene")
public class DesignPlanRenderSceneController extends BaseController {


    private final static String CLASS_LOG_PREFIX = "[效果图方案服务]:";
    private final static Gson gson = new Gson();

    @Autowired
    private DesignPlanRenderSceneService designPlanRenderSceneService;

    /**
     * 通过全屋户型ID获取所有空间的效果图方案
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/designplanrenderscenelist",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope designPlanRenderSceneList( DesignPlanInput designPlanInput) {

        ResponseEnvelope res = new ResponseEnvelope();

        //参数校验
        Integer planId = designPlanInput.getPlanId();
        Integer planType = designPlanInput.getPlanType();
        if(null == planId || 0 == planId || null == planType || planType ==0){
               return new ResponseEnvelope(false,"缺少参数");

        }
        log.info(CLASS_LOG_PREFIX+"方案ID:"+planId+"---------"+"方案类型"+planType);

        //获取效果图方案列表
        DesignPlanRenderSceneVo designPlanRenderSceneVo;
        try {
            if(planType == 4){
                designPlanRenderSceneVo = designPlanRenderSceneService.getDesignPlanRenderSceneList(planId,designPlanInput.getDesignTemplateId());
            }else  if (planType == 3){
                designPlanRenderSceneVo = designPlanRenderSceneService.getDesignPlanRenderSceneList(planId);
            }else {
                designPlanRenderSceneVo = null;
            }

            if(null == designPlanRenderSceneVo ){
                log.error(CLASS_LOG_PREFIX+"获取效果图方案列表失败,"+"参数:"+planId+"-----"+planType);
                return new ResponseEnvelope(false,"获取效果图方案列表失败");
            }
        }catch (BizException e){
            log.error(CLASS_LOG_PREFIX+"获取效果图方案列表数据异常:"+e);
            return new ResponseEnvelope(false,CLASS_LOG_PREFIX+"获取效果图方案列表数据异常");
        }
        res.setSuccess(true);
        res.setMsgId(designPlanInput.getMsgId());
        res.setObj(designPlanRenderSceneVo);
        return res;
    }

    /**
     * 获取所有已装修和未装修的效果图方案
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/designplandecorationlist",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope designPlanDecorationList(@RequestParam(value = "planType",required = false)Integer planType,//3:单空间我的设计方案,4:全屋我的设计方案
                                                     @RequestParam(value = "demandId",required = false)Integer demandId,
                                                     @RequestParam(value = "planId",required = false) Integer planId) {
        //登录用户校验
      LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false,CLASS_LOG_PREFIX + "请登录!");
        }
        log.info(CLASS_LOG_PREFIX+"登录用户:"+gson.toJson(loginUser));

        List<DesignPlanDecorationVo> designPlanDecorationVoList = new ArrayList<>();

        //获取未装修效果图方案列表
        DesignPlanRenderSceneVo unDecorationPlan;
        try {
            if( planType!=null && planType == 4 && planId !=null && planId>0 ){
                unDecorationPlan = designPlanRenderSceneService.getDesignPlanRenderSceneList(planId, 0);
            }else if(  planType!=null && planType == 3 && planId !=null && planId>0 ){
                unDecorationPlan = designPlanRenderSceneService.getDesignPlanRenderSceneList(planId);
            }else {
                unDecorationPlan = null;
            }

            if(null == unDecorationPlan ){
                log.warn(CLASS_LOG_PREFIX+"获取未装修效果图方案列表为空,"+"参数:"+demandId+"-------"+loginUser.getId());

            }
        }catch (BizException e){
            log.error(CLASS_LOG_PREFIX+"获取未装修效果图方案列表数据异常:"+e);
            return new ResponseEnvelope(false,CLASS_LOG_PREFIX+"获取未装修效果图方案列表数据异常");
        }

        //获取已装修的草图方案列表
        List<SingleDesignPlanVo> decorationPlanList;
        try {
            decorationPlanList = designPlanRenderSceneService.getDesignPlanList(demandId,loginUser.getId());
            if(null == decorationPlanList || decorationPlanList.size() == 0){
                log.warn(CLASS_LOG_PREFIX+"获取已装修效果图方案列表为空,"+"参数:"+demandId+"-------"+loginUser.getId());
            }
        }catch (BizException e){
            log.error(CLASS_LOG_PREFIX+"获取已装修效果图方案列表数据异常:"+e);
            return new ResponseEnvelope(false,CLASS_LOG_PREFIX+"获取已装修效果图方案列表数据异常");
        }


        //组装返回参数
        if( null!=unDecorationPlan){
            List<SinglePlanRenderSceneVo> singlePlanRenderSceneList =  unDecorationPlan.getSinglePlanRenderSceneList();
            if(null != singlePlanRenderSceneList && singlePlanRenderSceneList.size() > 0){
                for(SinglePlanRenderSceneVo singlePlanRenderSceneVo : singlePlanRenderSceneList){
                    DesignPlanDecorationVo designPlanDecorationVo = new DesignPlanDecorationVo();
                    designPlanDecorationVo.setUnDecorationPlanVo(singlePlanRenderSceneVo);
                    designPlanDecorationVo.setSpaceName(singlePlanRenderSceneVo.getSpaceName());
                    designPlanDecorationVo.setSpaceType(singlePlanRenderSceneVo.getSpaceType());
                    if(null != decorationPlanList && decorationPlanList.size() > 0  ){
                        List<SingleDesignPlanVo> singleDesignPlanVoList = new ArrayList<>();
                        for(SingleDesignPlanVo singleDesignPlanVo : decorationPlanList){
                            if(null != singleDesignPlanVo.getSpaceFunctionId() && 0< singleDesignPlanVo.getSpaceFunctionId()
                                    && singleDesignPlanVo.getSpaceFunctionId().intValue()==singlePlanRenderSceneVo.getSpaceType().intValue()
                                    &&singleDesignPlanVo.getDesignTemplateId().intValue()==singlePlanRenderSceneVo.getDesignTemplateId().intValue()
                                    ){
                                singleDesignPlanVoList.add(singleDesignPlanVo);
                            }
                        }
                        designPlanDecorationVo.setDecorationPlanVoList(singleDesignPlanVoList);
                    }
                    designPlanDecorationVoList.add(designPlanDecorationVo);
                }
            }
        }else if(unDecorationPlan==null && decorationPlanList!=null && decorationPlanList.size() > 0){
            Map<Integer,List<SingleDesignPlanVo>> integerlistMap = new HashMap<>();
            for (SingleDesignPlanVo singleDesignPlanVo : decorationPlanList){
                if(integerlistMap.containsKey(singleDesignPlanVo.getSpaceFunctionId())){
                    List<SingleDesignPlanVo> singleDesignPlanVoList = integerlistMap.get(singleDesignPlanVo.getSpaceFunctionId());
                    singleDesignPlanVoList.add(singleDesignPlanVo);
                    integerlistMap.put(singleDesignPlanVo.getSpaceFunctionId(),singleDesignPlanVoList);
                }else {
                    List<SingleDesignPlanVo> singleDesignPlanVoList = new ArrayList<>();
                    singleDesignPlanVoList.add(singleDesignPlanVo);
                    integerlistMap.put(singleDesignPlanVo.getSpaceFunctionId(),singleDesignPlanVoList);
                }
            }
            for(Map.Entry<Integer,List<SingleDesignPlanVo>> entry : integerlistMap.entrySet()){
                DesignPlanDecorationVo designPlanDecorationVo = new DesignPlanDecorationVo();
                designPlanDecorationVo.setSpaceName(entry.getValue().get(0).getSpaceName());
                designPlanDecorationVo.setSpaceType(entry.getKey());
                designPlanDecorationVo.setDecorationPlanVoList(entry.getValue());
                designPlanDecorationVoList.add(designPlanDecorationVo);
            }


        }



        return new ResponseEnvelope(true,designPlanDecorationVoList);
    }









    /**
     * 通过添加的方案组装成新的全屋方案
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/buildfullhouseplan",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope buildFullHousePlan(@RequestBody FullHousePlanInput fullHousePlanInput, HttpServletRequest request) {
        //登录用户校验
       LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false,CLASS_LOG_PREFIX + "请登录!");
        }
        log.info(CLASS_LOG_PREFIX+"登录用户:"+gson.toJson(loginUser));




        //参数校验
        List<PlanInput> planInputList = fullHousePlanInput.getPlanInputList();
        List<PlanRenderSceneInput> planRenderSceneInputList = fullHousePlanInput.getPlanRenderSceneInputList();
        if ( (planRenderSceneInputList ==null || planRenderSceneInputList.size() ==0) && (planInputList==null || planInputList.size() == 0 ) ){
            return new ResponseEnvelope(false,CLASS_LOG_PREFIX+"方案缺失参数");
        }
        Integer planType = fullHousePlanInput.getPlanType();
        if(planType == null || planType == 0){
            return new ResponseEnvelope(false,CLASS_LOG_PREFIX+"方案类型缺失");
        }

        //全屋户型方案比传户型ID
       if(fullHousePlanInput.getPlanType().intValue() == 4&& (fullHousePlanInput.getHouseId()==null || fullHousePlanInput.getHouseId()==0)){
            return new ResponseEnvelope(false,CLASS_LOG_PREFIX+"全屋户型方案比传户型ID");
        }


        String authorization = request.getHeader("Authorization");

        String planName = fullHousePlanInput.getPlanName();
        Integer planId = fullHousePlanInput.getPlanId();
        Integer houseId = fullHousePlanInput.getHouseId();
        if(StringUtils.isBlank(planName)){
            return new ResponseEnvelope(false,CLASS_LOG_PREFIX+"planName缺失");
        }

        //输出效果图方案
        log.info(CLASS_LOG_PREFIX+"开始输出效果图方案");
        SinglePlanRenderSceneVo singlePlanRenderSceneVo;
        try {
            if(planType==4){
                singlePlanRenderSceneVo =  designPlanRenderSceneService.creatNewFullHousePlan(planRenderSceneInputList,planInputList,loginUser,planName,planId,houseId,authorization);
            }else if(planType==3){
                singlePlanRenderSceneVo =  designPlanRenderSceneService.creatNewDesignPlan(planRenderSceneInputList,planInputList,loginUser,authorization);
            }else {
                singlePlanRenderSceneVo=null;
            }
            if(null ==singlePlanRenderSceneVo){
                return new ResponseEnvelope(false,CLASS_LOG_PREFIX+"组装全屋方案失败");
            }
        }catch (BizException e){
            log.error(CLASS_LOG_PREFIX+"输出效果图方案数据异常"+e);
            return new ResponseEnvelope(false,CLASS_LOG_PREFIX+"输出效果图方案数据异常");
        }
        log.info(CLASS_LOG_PREFIX+"开始输出效果图方案完成");

        return new ResponseEnvelope(true,singlePlanRenderSceneVo);
    }


























}
