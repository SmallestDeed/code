package com.sandu.api.company.input;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CompanyIntroduceUpdate implements Serializable
{
    @NotNull(message = "企业ID不能为空!")
    private Integer companyId;

    @NotNull(message = "图片ID不能为空!")
    @Min(value = 0, message = "保存图片失败!")
    private Integer companyPicId;

    @NotNull(message = "介绍内容不能为空!")
    @Size(max = 2000, message = "字数不能超过2000字!")
    private String companyIntroduce;

    @NotNull(message = "用户ID不能为空!")
    private Integer userId;


    private String introduceTitle;

    private String richContext;

}
