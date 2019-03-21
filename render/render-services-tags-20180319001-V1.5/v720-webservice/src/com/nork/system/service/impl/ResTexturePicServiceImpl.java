/**
 * 
 */
package com.nork.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.product.model.SplitTextureDTO;
import com.nork.system.dao.ResTextureDao;
import com.nork.system.service.ResTexturePicService;

/**
 * 
 * 
 * 
 * @author louxinhua
 *
 */
@Service("resTexturePicService")
public class ResTexturePicServiceImpl implements ResTexturePicService {

	
	@Autowired
	private ResTextureDao resTextureDao;
	
	@Override
	public Map<Integer, SplitTextureDTO.ResTextureDTO> getResTexturePicByDefaultID(List<Map<String, Object>> params) {
		
		if ( params == null || params.isEmpty() ) {
			return null;
		}
		else {}
		
		List<Map<String, Object>> list = resTextureDao.getResTexturePicByDefaultID(params);
		
		if ( list != null && !list.isEmpty() ) {
			Map<Integer, SplitTextureDTO.ResTextureDTO> resultMap = new HashMap<>();
			
			/*
			 123 as productID, 
	rt.id as resTextureID, rt.texture_attr_value as textureAttrValue, rt.file_height as fileHeight, rt.file_width as fileWidth, rt.lay_modes as layModes,
	rt.pic_id as picID, rp.pic_path as picPath 
			 */
			
			for (Map<String, Object> map : list) {
				
				Integer productID = (Integer)map.get("productID");
				Integer resTextureID = (Integer)map.get("resTextureID");
				Integer textureAttrValue = (Integer)map.get("textureAttrValue");
				Integer fileHeight = (Integer)map.get("fileHeight");
				Integer fileWidth = (Integer)map.get("fileWidth");
				String layModes = (String)map.get("layModes");
				String picPath = (String)map.get("picPath");
				
				SplitTextureDTO.ResTextureDTO resTextureDTO = new SplitTextureDTO().new ResTextureDTO(resTextureID, 
					 StringUtils.trimToEmpty(picPath), textureAttrValue, fileHeight, fileWidth, layModes,"","","");
				
				resultMap.put(productID, resTextureDTO);
			}
			
			return resultMap;
		}
		else {}
		
		
		
		return null;
	}

	
	
}
