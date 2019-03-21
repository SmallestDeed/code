package com.nork.design.service.impl;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.ClientEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.cache.CommonCacher;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.design.common.RecommendedDecorateState;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanOperationLog;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.ProductDTO;
import com.nork.design.model.UnityDesignPlan;
import com.nork.design.model.UnityPlanProduct;
import com.nork.design.service.DesignPlanOperationLogService;
import com.nork.design.service.DesignPlanRecommendedService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.design.service.PlanSaveService;
import com.nork.home.service.BaseHouseService;
import com.nork.product.service.BaseProductService;
import com.nork.system.model.ResModel;



@Service("PlanSaveService")
@Transactional
@ClientEndpoint
public class PlanSaveServiceImpl implements PlanSaveService{
    @Autowired
    private DesignPlanOperationLogService designPlanOperationLogService;
    @Autowired
    private DesignPlanService designPlanService;
    @Resource
    private DesignPlanRecommendedService designPlanRecommendedService;
    @Resource
    private BaseHouseService baseHouseService;
    @Autowired
    private DesignTempletService designTempletService;
    @Autowired
    private BaseProductService baseProductService;
    private static final Logger logger = LoggerFactory.getLogger(PlanSaveServiceImpl.class);
    private final static ResourceBundle app = ResourceBundle.getBundle("app");
    
    /**
     * 通过设计方案id查询U3D设计方案对象(设计方案-获取设计方案进入模型)
     * 
     * */
    @Override
    public Object getDesignPlan(
    		String houseId, 
    		String livingId, 
    		Integer designPlanId,
            String residentialUnitsName, 
            Integer newFlag, 
            String msgId,LoginUser loginUser,
            Boolean isRelease,
            String mediaType,
            Long startTime,
            String modelType) {
        DesignPlanOperationLog designPlanOperationLog = new DesignPlanOperationLog();
        designPlanOperationLog.setUserId(loginUser.getId());
        designPlanOperationLog.setDesignPlanId(designPlanId);
        designPlanOperationLog.setStatus(0);
        designPlanOperationLog.setBusinessKey(SystemCommonConstant.MODIFIED_DESIGN_PLAN);
        designPlanOperationLog.setGmtCreate(new Date());
        designPlanOperationLog.setIsDeleted(0);
        designPlanOperationLog.setCreator(loginUser.getLoginName());
        designPlanOperationLog.setSysCode(Utils
                                                .getCurrentDateTime(Utils.DATETIMESSS)
                                                + "_"
                                                + Utils.generateRandomDigitString(6));
        designPlanOperationLog.setModifier(loginUser.getLoginName());
        designPlanOperationLog.setGmtModified(new Date());
        designPlanOperationLogService.insertSelective(designPlanOperationLog);
        //添加数据结束<<end
        
        Map<Object,Object> paramsMap = new HashMap<>(1);
        paramsMap.put("designPlanId", designPlanId);
        DesignPlan designPlan = designPlanService.get(designPlanId);    
        //测试发布中 、发布中 、待审核的方案不能删除、修改,点击删除提示用户"请先取消发布!" 
        if(isRelease!=null && !isRelease){  //不是 复制或者 拷贝 的 都需要走里面的方法
                if(designPlan!=null && designPlan.getIsRelease()!=null ){
                        boolean flag = designPlanRecommendedService.isDesignPlanCheck(loginUser,null);
                        if(flag){
                                if(designPlan.getIsRelease().intValue() == RecommendedDecorateState.IS_RELEASEING){
                                        return new ResponseEnvelope<>(false,"该方案正在发布!  ","none");
                                }
                        }else{
                                if(designPlan.getIsRelease().intValue() == RecommendedDecorateState.IS_RELEASEING 
                                                || designPlan.getIsRelease().intValue() == RecommendedDecorateState.IS_TEST_RELEASE
                                                        ||designPlan.getIsRelease().intValue() == RecommendedDecorateState.WAITING_CHECK_RELEASE){
                                        return new ResponseEnvelope<>(false,"请取消发布后编辑!  ","none");
                                }
                        }       
                }
        }

        //设计方案信息
        UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
        ResponseEnvelope responseEnvelopeInfo = (ResponseEnvelope)designPlanService.getDesignPlanInfo(designPlanId,newFlag,houseId,livingId,residentialUnitsName,isRelease,loginUser,mediaType, null,modelType);
        if (responseEnvelopeInfo.isSuccess()) {
                unityDesignPlan = (UnityDesignPlan)responseEnvelopeInfo.getObj();
                unityDesignPlan.setEffectsConfig(designPlan.getEffectsConfig());
        } else{
                logger.error(responseEnvelopeInfo.getMessage());
                return responseEnvelopeInfo;
        }       
        unityDesignPlan = wrapperData(designPlanId, unityDesignPlan);
        //logger.error("进入设计方案designPlanId="+designPlanId+"消耗时间:" + (System.currentTimeMillis() - startTime));
        ResponseEnvelope responseEnvelope = new  ResponseEnvelope<UnityDesignPlan>(unityDesignPlan, msgId, true);
        
        if(Utils.enableRedisCache()){
                CommonCacher.addAll(ModuleType.DesignPlan, "getDesignPlanWeb", paramsMap, responseEnvelope);
        }
        return responseEnvelope;
    }
    
    
    private UnityDesignPlan wrapperData(Integer designPlanId, UnityDesignPlan unityDesignPlan) {

        List<UnityPlanProduct> dataList = unityDesignPlan.getDatalist();
        List<ProductDTO> list = designPlanService.getProductDTOList(designPlanId);
        for(UnityPlanProduct upp : dataList) {
                Integer upp_productId = upp.getProductId();
                for(ProductDTO productDTO : list) {
                        Integer productId = productDTO.getProductId();
                        if(null != upp_productId && StringUtils.isNotBlank(upp.getPosIndexPath())){
                            if(upp_productId.equals(productId) && upp.getPosIndexPath().equals(productDTO.getPosIndexPath())) {
                                String valueKey = productDTO.getValueKey();
                                if(StringUtils.isNotBlank(valueKey)) {
                                    if(valueKey.indexOf("_") != -1) {
                                        String[] split = valueKey.split("_");
                                        upp.setBasicModelType(split[1]);
                                    } else {
                                        upp.setBasicModelType(valueKey);
                                    }
                                }
                            }
                        }
                }
        }
        
        return unityDesignPlan;
}
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean isNewPlan(LoginUser userLogin, Map map) {
        boolean isNewPlan = false;
        if (map == null) {
                map = new HashMap();
        }
        Integer templetId = (Integer) map.get("templetId");
        Map paraMap = new HashMap();
        paraMap.put("designId", templetId);
        int times = Integer.parseInt(app.getString("design.designPlan.interval.times").trim());
        paraMap.put("times", times);
        paraMap.put("userId", userLogin.getId());
        List list = designPlanService.getUserMaxPlan(paraMap);
        if (list != null && list.size() > 0) {
                map.put("list", list);
                isNewPlan = false;
        } else {
                map = new HashMap();
                isNewPlan = true;
        }
        return isNewPlan;
    }
}
