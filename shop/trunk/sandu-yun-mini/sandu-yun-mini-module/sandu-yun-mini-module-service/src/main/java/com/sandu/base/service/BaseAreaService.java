package com.sandu.base.service;

import java.util.List;
import com.sandu.base.model.vo.BaseAreaVo;

/***
 * 行政区划服务接口
 * @author Administrator
 *
 */
public interface BaseAreaService {
	/***
	 * 获取所有城市列表
	 * @return
	 */
    List<BaseAreaVo> getCityList();
    
    /***
     * 获取开通选装服务的城市列表
     * @return
     */
    List<BaseAreaVo> getOpenServiceCityList();
}
