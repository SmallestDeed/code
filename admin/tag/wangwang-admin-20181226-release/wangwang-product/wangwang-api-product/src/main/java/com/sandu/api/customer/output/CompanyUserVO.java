package com.sandu.api.customer.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyUserVO implements Serializable {
    private static final long serialVersionUID = -5552097720828017920L;
    @JsonInclude()
    private Integer id;
    @JsonInclude()
    private String name;
}
