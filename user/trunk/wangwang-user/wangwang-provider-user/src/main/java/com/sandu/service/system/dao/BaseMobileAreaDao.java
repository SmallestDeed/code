package com.sandu.service.system.dao;

import com.sandu.api.system.model.BaseMobileArea;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseMobileAreaDao {
    BaseMobileArea queryBaseMobileAreaByMobilePrefix(String mobilePrefix);
}
