package com.sandu.service.audit.impl;

import com.sandu.api.audit.IAuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/4/25
 * @since : sandu_yun_1.0
 */
@Slf4j
@Service("auditService")
public class AuditServiceImpl implements IAuditService {
    @Override
    public void audit(Integer userId, Integer objectId, String objectType, String actType, String msg, boolean successful, List<String> values) {
        log.info("Call audit service to save to DB or message queue.");
    }
}
