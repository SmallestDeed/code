package com.sandu.im.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.im.common.bo.ShopBo;
import com.sandu.im.dao.ShopDao;

@Service
public class ShopService {

	@Autowired
	ShopDao shopDao;
	
	 /**
     * 返回一个Map集合,key是shopId,value是shopName
     * @param shopIdList
     * @return
     */
	public Map<Long,String> getShopNameMapping(List<Long> shopIdList){
		if(shopIdList==null || shopIdList.size()==0) {
			return new HashMap<Long,String>();
		}
    	List<ShopBo> list = shopDao.selectList(shopIdList);    	
    	return list.stream().collect(Collectors.toMap(ShopBo::getShopId, ShopBo::getShopName));
    }


	public ShopBo getShopInfoByContactUserSessionId(String contactSessionId) {
		return shopDao.getShopInfoByContactUserSessionId(contactSessionId);
	}
}