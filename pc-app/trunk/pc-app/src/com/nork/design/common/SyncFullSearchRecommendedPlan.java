package com.nork.design.common;

import com.nork.common.util.MessageUtil;
import com.nork.common.util.Utils;
import com.sandu.search.exception.ElasticSearchException;
import com.sandu.search.service.design.RecommendedPlanService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 同步发布方案到搜索服务
 *
 * @auth xiaoxc
 * @data 2018/8/22
 */
@Controller
public class SyncFullSearchRecommendedPlan {

    private static Logger logger = LoggerFactory.getLogger(SyncFullSearchRecommendedPlan.class);
    private final static String CLASS_LOG_PREFIX = "[方案同步]推荐方案信息同步ES库:";

    /**
     * 同步发布方案到ES库 ---> Http服务
     * @param planIds 多个方案Id
     * @return
     */
    public static MessageUtil syncRecommendedPlanByPlanId(String planIds) {
        if (StringUtils.isEmpty(planIds)) {
            return new MessageUtil(false, "planId is empty!");
        }
        String uri = Utils.getValue("fullsearch.sync.recommendationplan.url","").trim();
//            uri = "http://localhost:9999/fullsearch-app/sync/recommendedPlan/syncRecommendedPlanById";
        logger.error(CLASS_LOG_PREFIX + "url={}, planIds={}", uri, planIds);
        try {
            HttpClient httpclient = new DefaultHttpClient();
            if (!StringUtils.isEmpty(uri)) {
                HttpPost httppost = new HttpPost(uri);
                httppost.addHeader("Content-Type", "application/json");
                //传参
                JSONObject obj = new JSONObject();
                obj.put("recommendedPlanIds", planIds);
                obj.put("businessType", "开始更新pc2b发布方案数据！");

                logger.info(CLASS_LOG_PREFIX + "请求参数：" + obj.toString());
                httppost.setEntity(new StringEntity(obj.toString(),"UTF-8"));

                HttpResponse response = httpclient.execute(httppost);

                // 检验状态码，如果成功接收数据
                int code = response.getStatusLine().getStatusCode();
                if (code == 200) {
                    String message = EntityUtils.toString(response.getEntity());
                    return new MessageUtil(true, message);
                }
            }
        } catch (IOException e){
            logger.error(CLASS_LOG_PREFIX + "同步发布方案异常 IOException:{}" , e);
            return new MessageUtil(true, e.getMessage());
        }catch (Exception e) {
            logger.error(CLASS_LOG_PREFIX + "同步发布方案异常 Exception:{}" , e);
            return new MessageUtil(true, e.getMessage());
        }
        return new MessageUtil(false, "请求失败 url=" + uri);
    }

    /**
     * 同步发布方案到ES库 ---> RPC服务
     * @param planIds    多个方案Id
     * @param actionType 操作类型
     * @data 2018-11-08
     * @return
     */
    /*public static MessageUtil syncRecommendedPlanByPlanIds(String planIds, int actionType, RecommendedPlanService recommendedPlanService) {
        logger.error(CLASS_LOG_PREFIX + "planIds:{},actionType:{}", planIds, actionType);
        if (StringUtils.isEmpty(planIds)) {
            return new MessageUtil(false, "planId is empty!");
        }
        try {
            if (recommendedPlanService != null) {
                recommendedPlanService.syncRecommendedPlan(planIds, actionType);
            } else {
                logger.error(CLASS_LOG_PREFIX + "RPC服务recommendedPlanService为空！");
            }
        } catch (ElasticSearchException e) {
            logger.error(CLASS_LOG_PREFIX + "调用RPC服务异常！e：{}", e);
            return new MessageUtil(false, "调用RPC服务异常！e：" + e);
        }
        return new MessageUtil(true);
    }*/

}
