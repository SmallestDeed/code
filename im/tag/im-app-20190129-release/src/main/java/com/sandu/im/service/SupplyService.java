package com.sandu.im.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.im.common.bo.SupplyBo;
import com.sandu.im.dao.SupplyDao;

@Service
public class SupplyService {

	@Autowired
	SupplyDao supplyDao;
	
	
	 /**
     * 返回一个Map集合,key是supplyId,value是supplyName
     * @param supplyIdList
     * @return
     */
	public Map<Long,String> getSupplyTitleMapping(List<Long> supplyIdList){
		if(supplyIdList==null || supplyIdList.size()==0) {
			return new HashMap<Long,String>();
		}
    	List<SupplyBo> list = supplyDao.selectList(supplyIdList);    	
    	return list.stream().collect(Collectors.toMap(SupplyBo::getSupplyId, SupplyBo::getTitle));
    }
	
	

	
}