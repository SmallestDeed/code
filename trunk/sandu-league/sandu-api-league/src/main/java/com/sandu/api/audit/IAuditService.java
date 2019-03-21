package com.sandu.api.audit;

import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/4/25
 * @since : sandu_yun_1.0
 */
public interface IAuditService {
    void audit(Integer userId, Integer objectId, String objectType, String actType, String msg
            , boolean successful, List<String> values);
}
