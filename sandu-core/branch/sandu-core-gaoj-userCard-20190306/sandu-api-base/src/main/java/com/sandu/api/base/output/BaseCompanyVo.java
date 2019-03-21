package com.sandu.api.base.output;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: BaseCompanyVo
 * @Auther: gaoj
 * @Date: 2019/3/11 15:26
 * @Description:
 * @Version 1.0
 */
@Data
public class BaseCompanyVo implements Serializable{
    private Integer id;
    private String companyName;
}
