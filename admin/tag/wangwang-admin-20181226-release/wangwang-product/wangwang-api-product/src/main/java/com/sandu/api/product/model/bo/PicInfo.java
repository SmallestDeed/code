package com.sandu.api.product.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * @author Sandu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PicInfo implements Serializable{
    private int id;
    private String path;
}
