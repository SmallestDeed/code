package com.sandu.util;

import com.sandu.api.queue.SyncMessage;
import com.sandu.api.queue.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RabbitUtils {

    @Autowired
    private QueueService queueService;

    public void sycMessageDoSend(List<Integer> ids, Integer messageAction, String messageId, int module, String platformType) {
        List<Map> content = ids.stream().map(item -> {
            HashMap<String, Integer> tmp = new HashMap<>(1);
            tmp.put("id", item);
            return tmp;
        }).collect(Collectors.toList());
        SyncMessage message = new SyncMessage();
        message.setAction(messageAction);
        message.setMessageId(messageId);
        message.setModule(module);
        message.setPlatformType(platformType);
        message.setObject(content);
        queueService.send(message);
    }
}
