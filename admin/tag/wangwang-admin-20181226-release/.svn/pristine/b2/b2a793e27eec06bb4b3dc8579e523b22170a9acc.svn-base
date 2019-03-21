package com.sandu.api.basewaterjet.input;

import com.sandu.common.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * base_waterjet
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-09 10:03
 */
@Data
public class BasewaterjetQuery extends BaseQuery implements Serializable {

    @ApiModelProperty(value = "水刀编码")
    private String templateCode;
    @ApiModelProperty(value = "水刀名称")
    private String templateName;
    @ApiModelProperty(value = "水刀品牌")
    private Integer brandId;
    @ApiModelProperty(value = "水刀状态")
    private Integer templateStatus;
    @ApiModelProperty(value = "水刀形状")
    private Integer templateShapeValue;
    @ApiModelProperty(value = "水刀品牌id集合")
    private List<Integer> brandIdList;
}
