/**
 * 
 */
package com.nork.system.service;

import java.util.List;
import java.util.Map;

import com.nork.product.model.SplitTextureDTO;

/**
 * restexture 与 respic 的服务
 * @author louxinhua
 *
 */
public interface ResTexturePicService {

	/**
	 * 获取 ResTexture 相关的 pic_id, pic_path
	 * @param params
	 * @return
	 */
	public Map<Integer, SplitTextureDTO.ResTextureDTO> getResTexturePicByDefaultID(List<Map<String, Object>> params);
	
}
