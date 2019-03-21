package com.sandu.api.system.service;

import java.util.List;

import com.sandu.api.system.input.AuthorizedConfigSearch;
import com.sandu.api.system.model.AuthorizedConfig;

public interface AuthorizedConfigService {
    /**
     * 获取数据数量
     *
     * @param authorizedConfigSearch
     * @return int
     */
    public int getCount(AuthorizedConfigSearch authorizedConfigSearch);

    
    public AuthorizedConfig queryAuthorizedConfigByUserId(Long userId);
    
    /**
	 * 所有数据
	 * 
	 * @param  authorizedConfig
	 * @return   List<AuthorizedConfig>
	 */
	public List<AuthorizedConfig> getList(AuthorizedConfig authorizedConfig);
}
