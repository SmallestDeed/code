package com.sandu.base.model.query;

import com.sandu.matadata.enums.SortType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 企业查询条件
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "企业查询条件实体类", description = "企业查询条件实体类")
public class BaseCompanyQuery extends BaseQuery<BaseCompanyQuery> {
	private static final long serialVersionUID = 798484153986262764L;
	@ApiModelProperty(value = "企业名称")
	private String companyName;
	@ApiModelProperty(value = "企业服务类型")
	private Integer businessType;
	private String orderBySql;

	public String getOrderBySql() {
		if (getOrderBy() != null && getOrderBy().length() > 0) {
			String orderByKey = getOrderBy();
			// salesVolume 因为没有销量字段,暂时不处理
			// 销量最高：salesVolume
			// 收藏量最高：collectionCount
			// 点赞最多：likeCount
			if (orderByKey == "praiseRate") {
				orderBySql = "a.praise_rate ";
			}
			if (orderBySql != null && orderBySql.length() > 0) {
				if (getSortType()==SortType.Descend.value()) {
					orderBySql += " desc";
				}
			}
		}
		return orderBySql;
	}

	public void setOrderBySql(String orderBySql) {
		this.orderBySql = orderBySql;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
