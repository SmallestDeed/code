package com.nork.common.async;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.nork.common.cache.CommonCacher;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.Utils;
import com.nork.design.model.UnityDesignPlan;
import com.nork.design.service.DesignPlanService;

/**
 * Created by Administrator on 2017/2/20 0020.
 */
public class UpdateDesignPlanCacheTask implements Callable<Result> {

    private final String SERVERURL = Utils.getValue("app.render.server.url", "http://localhost:18080/onlineDecorate");
    private final String RESOURCESURL = Utils.getValue("app.resources.url",
            "http://localhost:18080/onlineDecorate/upload");
    public final static String SYSTEM_FORMAT = Utils.getValue("app.system.format", "linux").trim();

    private static Logger logger = Logger.getLogger(ProductSaveTask.class);
    private DesignPlanService designPlanService=SpringContextHolder.getBean(DesignPlanService.class);

    private UpdateDesignPlanCacheParameter parameter;

    public UpdateDesignPlanCacheTask(UpdateDesignPlanCacheParameter parameter){
        this.parameter=parameter;
    }

    @Override
    public Result call() throws Exception {
        Integer designPlanId = parameter.getDesignPlanId();
        String houseId = parameter.getHouseId();
        String livingId = parameter.getLivingId();
        String residentialUnitsName = parameter.getResidentialUnitsName();
        Integer newFlag = parameter.getNewFlag();
        HttpServletRequest request = parameter.getRequest();
        LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		String mediaType = loginUser.getMediaType(); //TODO :CheckthisLogic
       
		Map<Object,Object>	paramsMap = new HashMap<>();
        paramsMap.put("designPlanId", designPlanId);
        if(Utils.enableRedisCache()){
        	CommonCacher.removeAll(ModuleType.DesignPlan, "getDesignPlanWeb",paramsMap);
        }
        UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
        ResponseEnvelope responseEnvelopeInfo = (ResponseEnvelope)designPlanService.getDesignPlanInfo(designPlanId,newFlag,houseId,livingId,residentialUnitsName,false,loginUser,mediaType, null);
        if( responseEnvelopeInfo.isSuccess() ){
            unityDesignPlan = (UnityDesignPlan)responseEnvelopeInfo.getObj();
        }else{
            return null ;
        }

        ResponseEnvelope responseEnvelope= new  ResponseEnvelope<UnityDesignPlan>(unityDesignPlan, "74", true);
        if(Utils.enableRedisCache()){
        	CommonCacher.addAll(ModuleType.DesignPlan, "getDesignPlanWeb", paramsMap, responseEnvelope);
        }
        return null;
    }
}
