package com.sandu.search.datasync.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class GroupProductMessageHandler {

    private final static String CLASS_LOG_PREFIX = "Rabbit组合产品消息处理器:";

    //待更新组合产品列表
    private static volatile List<Integer> waitUpdateGroupProductIdList = new ArrayList<>();


}
