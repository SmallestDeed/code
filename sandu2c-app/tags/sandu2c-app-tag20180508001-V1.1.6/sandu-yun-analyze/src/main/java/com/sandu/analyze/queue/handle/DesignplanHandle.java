package com.sandu.analyze.queue.handle;

import com.sandu.amqp.entity.queue.DesignPlanMessage;
import com.sandu.analyze.common.constant.elasticsearch.IndexConstant;
import com.sandu.analyze.common.constant.elasticsearch.TypeConstant;
import com.sandu.analyze.common.tool.EntityCopyUtils;
import com.sandu.analyze.common.tool.JsonUtil;
import com.sandu.analyze.entity.elasticseatch.dto.IndexRequestDTO;
import com.sandu.analyze.entity.elasticseatch.index.DesignPlanMappingData;
import com.sandu.analyze.exception.ElasticSearchException;
import com.sandu.analyze.exception.MessageHandleException;
import com.sandu.analyze.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * 设计方案消息处理
 *
 * @date 2018/5/3
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
@Slf4j
@Component
public class DesignplanHandle {

    private static final String CLASS_PREFIX_LOG = "[设计方案消息处理]:";

    @Autowired
    ElasticSearchService elasticSearchService;

    /**
     * 处理消息
     *
     * @param designPlanMessage 设计方案对象
     */
    public void handle(DesignPlanMessage designPlanMessage) throws MessageHandleException {


        if (null != designPlanMessage) {
            /*************** 验证数据 **************/
            int userId = designPlanMessage.getUserId();
            int platformType = designPlanMessage.getPlatformType();
            int actionType = designPlanMessage.getActionType();
            int designPlanId = designPlanMessage.getDesignPlanId();
            if (0 > userId || 0 > platformType || 0 > actionType ||  0 > designPlanId) {
                log.info(CLASS_PREFIX_LOG + "设计方案数据不完整,DesignPlanMessage:{}.", designPlanMessage);
                throw new MessageHandleException(CLASS_PREFIX_LOG + "设计方案数据不完整,DesignPlanMessage:" + designPlanMessage + ".");
            }

            /*************** 处理数据 **************/
            //复制对象
            DesignPlanMappingData designPlanMappingData = new DesignPlanMappingData();
            EntityCopyUtils.copyData(designPlanMessage, designPlanMappingData);
            //增加操作时间
            designPlanMappingData.setActionDate(new Date());

            /*************** 索引数据 **************/

            boolean indexFlag = false;
            try {
                indexFlag = elasticSearchService.index(new IndexRequestDTO(
                        IndexConstant.INDEX_DESIGNPLAN_RECORD,
                        TypeConstant.DESIGNPLAN,
                        UUID.randomUUID().toString(),
                        JsonUtil.toJson(designPlanMappingData)));
            } catch (ElasticSearchException e) {
                log.error(CLASS_PREFIX_LOG + "索引数据异常,ElasticSearchException:{},DesignPlanMappingData:{}.", e, designPlanMappingData);
                throw new MessageHandleException(CLASS_PREFIX_LOG + "索引数据异常,DesignPlanMappingData:" + designPlanMappingData, e);
            }

            log.info(CLASS_PREFIX_LOG + "索引数据完成，indexFlag:{}.", indexFlag);

        }
        log.info(CLASS_PREFIX_LOG + "处理设计方案消息完成:DesignPlan:{}.", designPlanMessage);
    }

    /*public static Date getDay(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        date = calendar.getTime();
        return date;
    }*/
}
