package com.sandu.api.company.service;

import com.sandu.user.model.SysUserLevelBo;
import com.sandu.user.model.SysUserLevelConfig;

/**
 * Created by yanghz on 2017-08-15.
 */
public interface SysUserLevelConfigService {
	/**
	 * add by yanghz
	 * addUserLevelLimit 方法描述：获取用户当前等级配置信息
	 *
	 * @return SysUserLevelBo 返回类型
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	SysUserLevelBo getLevelInfo(SysUserLevelConfig levelConfig);

	void updateById(int id, int levelId);

	void initUserLevelByLevelPriceId(long userId, int id);
}
