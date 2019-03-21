package com.sandu.api.shop.input;

import com.sandu.api.shop.model.po.CasePO;
import com.sandu.api.shop.model.po.ShopPO;
import com.sandu.commons.Mapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询工程案例列表条件
 *
 * @auth xiaoxc
 * @data 2018-06-20
 */
@Data
public class ProjectCaseQuery extends Mapper implements Serializable {

    @ApiModelProperty(value="标题")
    private String caseTitle;

    @ApiModelProperty(value="店铺Id")
    private Integer shopId;

    @ApiModelProperty(value = "店铺类型：1.企业店铺，2.个人店铺")
    private Integer shopType;

    /**
     * 转换PO对象
     * @param query
     * @return
     */
    public CasePO getCasePOFromCaseQuery(ProjectCaseQuery query) {
        CasePO casePO = new CasePO();
        if (query != null) {
            casePO.setCaseTitle(query.getCaseTitle());
            casePO.setShopId(query.getShopId());
            casePO.setStart(query.getStart());
            casePO.setLimit(query.getLimit());
        }
        return casePO;
    }
}
