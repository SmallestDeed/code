package com.sandu.api.base.service;

import com.sandu.api.base.model.BasePlatform;
import org.springframework.stereotype.Component;

/**
 * Created by kono on 2018/6/1 0001.
 */
@Component
public interface BasePlatformService {

    BasePlatform getBasePlatform(String platformCode);
}
