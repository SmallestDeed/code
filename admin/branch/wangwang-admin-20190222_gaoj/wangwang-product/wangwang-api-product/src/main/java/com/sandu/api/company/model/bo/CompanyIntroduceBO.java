package com.sandu.api.company.model.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyIntroduceBO implements Serializable
{
    private Integer id;

    private String companyPicPath;

    private String companyIntroduce;

    private Integer picId;

    private String richContext;

    private String introduceTitle;
}
