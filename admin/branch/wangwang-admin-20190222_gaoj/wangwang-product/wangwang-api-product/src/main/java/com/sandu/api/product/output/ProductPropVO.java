package com.sandu.api.product.output;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class ProductPropVO implements Serializable{
    private Integer id;
    private String name;
    private String code;
    private Integer pid;
    private List<ProductPropVO> children;
}
