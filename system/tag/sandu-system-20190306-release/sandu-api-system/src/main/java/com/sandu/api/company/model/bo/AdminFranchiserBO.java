package com.sandu.api.company.model.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class AdminFranchiserBO implements Serializable {
    Long companyId;
    Long pid;
    String companyCode;
    String companyName;
    String brandIds;
    String contactTelephone;
    String companyIndustryIds;
}
