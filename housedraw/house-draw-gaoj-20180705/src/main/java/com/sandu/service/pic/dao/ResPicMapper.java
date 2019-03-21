package com.sandu.service.pic.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.res.model.ResPic;

/**
 * Description:
 * 户型图片数据库操作类
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/28
 */

@Repository
public interface ResPicMapper {
	
	ResPic getResPic(Long id);

	List<ResPic> listResPicById(@Param("args")Set<Long> args);
	
	Integer insertSelective(ResPic resPic);
}
