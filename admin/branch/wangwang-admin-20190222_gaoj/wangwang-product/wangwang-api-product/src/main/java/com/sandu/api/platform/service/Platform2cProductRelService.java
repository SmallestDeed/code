package com.sandu.api.platform.service;

import java.util.List;
import java.util.Map;

/**
 * @author Sandu
 */
public interface Platform2cProductRelService extends PlatformProductRelService{
    Map<Integer,String> getBePutawayPlatformByProductIds(List<Integer> productIds);
}
