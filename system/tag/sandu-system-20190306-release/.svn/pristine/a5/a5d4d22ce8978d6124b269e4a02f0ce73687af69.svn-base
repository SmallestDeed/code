package com.sandu.service.company.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.company.model.BaseCompanyMiniProgramTemplateMsg;


@Repository
public interface BaseCompanyMiniProgramTemplateMsgMapper {
	
    /**
     * 通过AppId类型查询模板消息配置
     */
	BaseCompanyMiniProgramTemplateMsg selectByAppidAndType(@Param("appId") String appId,@Param("templateType") Integer templateType);

}
