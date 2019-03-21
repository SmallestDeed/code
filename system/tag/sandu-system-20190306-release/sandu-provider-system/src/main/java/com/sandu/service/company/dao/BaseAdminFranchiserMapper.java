package com.sandu.service.company.dao;

import com.sandu.api.company.input.BaseFranchiserQuery;
import com.sandu.api.company.model.bo.AdminFranchiserBO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseAdminFranchiserMapper {

    List<AdminFranchiserBO> listFranchiser(BaseFranchiserQuery query);
}
