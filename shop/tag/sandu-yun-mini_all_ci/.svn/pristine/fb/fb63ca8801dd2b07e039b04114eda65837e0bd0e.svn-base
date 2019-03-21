package com.sandu.company.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sandu.common.persistence.CrudDao;
import com.sandu.company.model.CompanyDesigner;
import com.sandu.company.model.query.CompanyDesignerQuery;
import com.sandu.company.model.vo.CompanyDesignerVo;

@Repository
public interface CompanyDesignerDao extends 
CrudDao<CompanyDesigner,CompanyDesignerQuery,CompanyDesignerVo>{
	List<CompanyDesignerVo> getTopDesigner(CompanyDesignerQuery input);

	int getDesignerCount(CompanyDesignerQuery query);
}
