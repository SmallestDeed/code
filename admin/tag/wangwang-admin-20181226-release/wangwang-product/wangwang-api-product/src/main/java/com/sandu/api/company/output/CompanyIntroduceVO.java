package com.sandu.api.company.output;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyIntroduceVO implements Serializable
{
    private Integer companyId;

    private String picPath;

    private String introduce;

    private Integer picId;

    private String richContext;

    private String introduceTitle;
}
