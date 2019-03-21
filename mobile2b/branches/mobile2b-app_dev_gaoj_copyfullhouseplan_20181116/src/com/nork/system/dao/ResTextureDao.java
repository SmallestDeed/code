/**
 * 
 */
package com.nork.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * res_texture 与 pic 等表的联合查询相关
 * @author louxinhua
 *
 */
@Repository
@Transactional
public interface ResTextureDao {

	
	/**
	 * 获取 ResTexture 相关的 pic_id, pic_path
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getResTexturePicByDefaultID(List<Map<String, Object>> params);
	
	
	
}
