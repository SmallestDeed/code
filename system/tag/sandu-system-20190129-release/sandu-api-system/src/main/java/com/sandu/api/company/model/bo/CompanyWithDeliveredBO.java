package com.sandu.api.company.model.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by bvvy
 * @date 2018/4/13
 */
@Data
public class CompanyWithDeliveredBO implements Serializable {
    private Integer companyId;
    private String companyName;
    private Integer delivered;
}
