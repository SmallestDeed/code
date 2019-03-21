package com.sandu.base.dao;

import com.sandu.base.model.BaseCompany;
import com.sandu.base.model.query.BaseCompanyQuery;
import com.sandu.base.model.vo.BaseCompanyVo;
import com.sandu.common.persistence.CrudDao;

public interface BaseCompanyDao extends
CrudDao<BaseCompany,BaseCompanyQuery,BaseCompanyVo>{
	BaseCompanyVo getdetail(long id);
}
