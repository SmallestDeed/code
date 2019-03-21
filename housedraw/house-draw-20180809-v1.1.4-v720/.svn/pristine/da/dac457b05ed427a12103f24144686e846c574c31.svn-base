package com.sandu.service.templet.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.house.model.DesignTemplet;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0 
 * Company:Sandu Copyright:Copyright(c)2017
 * @date 2018/1/2
 */
@Repository
public interface DesignTempletMapper {
	/**
	 * 保存样板房
	 * 
	 * @param designTemplet
	 *            样板房对象
	 * @return
	 */
	Long saveDesignTemplet(DesignTemplet designTemplet);

	/**
	 * 维护样板房配置文件至样板房信息
	 * 
	 * @param fileId
	 *            配置文件id
	 * @param templetId
	 *            样板房id
	 */
	void updateDesignTemplet(@Param("fileId") Long fileId, @Param("templetId") Long templetId, @Param("type") String fileType);

	DesignTemplet getDesignTempletByDesignCode(String designCode);
	
	int updateByPrimaryKeySelective(DesignTemplet record);
}
