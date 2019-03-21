package com.sandu.api.company.service;

import com.sandu.api.company.input.BaseFranchiserNew;
import com.sandu.api.company.input.BaseFranchiserQuery;
import com.sandu.api.company.input.BaseFranchiserUpdate;
import com.sandu.api.company.model.bo.ItemVO;
import com.sandu.api.company.output.AdminFranchiserDetailsVO;
import com.sandu.api.company.output.AdminFranchiserVO;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;

public interface BaseAdminFranchiserService {

    ResponseEnvelope<AdminFranchiserDetailsVO> getFranchiser(Long companyId);

    ResponseEnvelope<AdminFranchiserVO> listFranchiser(BaseFranchiserQuery query);

    ResponseEnvelope addFranchiser(BaseFranchiserNew franchiserNew, LoginUser loginUser);

    ResponseEnvelope updateFranchiser(BaseFranchiserUpdate update, LoginUser loginUser);

    ResponseEnvelope deleteFranchiser(Long companyId, LoginUser loginUser);

    ResponseEnvelope<ItemVO> listIndustry(Long pid);
}
