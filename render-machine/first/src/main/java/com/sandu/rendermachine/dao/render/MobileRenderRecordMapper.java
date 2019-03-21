package com.sandu.rendermachine.dao.render;

import com.sandu.rendermachine.model.render.WhiteDeviceList;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface MobileRenderRecordMapper {

	/**
	 * 查询白名单设备号
	 * @return
	 */
	List<WhiteDeviceList> checkWhiteList();

}
