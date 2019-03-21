package com.nork.product.model;

/**
 * 所有的com.nork.product.model下entity的字段的常量
 * @author huangsongbo
 *
 */
public class ProductConstant {

	/**
	 * MatchGroupProductResult类的isReName字段常量:已经匹配过
	 */
	public static final Integer MATCHGROUPPRODUCTRESULT_ISRENAME_ISMATCHED = 1;
	
	/**
	 * MatchGroupProductResult类的isReName字段常量:第一次匹配(未匹配过)
	 */
	public static final Integer MATCHGROUPPRODUCTRESULT_ISRENAME_NOMATCH = 0;

	/**
	 * 产品已删除消息提示
	 */
	public static final String PRODUCT_DELETED_MESSAGE_HINT = "该产品已删除，无法查看详情！";

	/**
	 * 产品未上架消息提示
	 */
	public static final String PRODUCT_NOT_SHELVES_MESSAGE_HINT = "该产品已下架，无法查看详情！";

	/**
	 * 不是拼花产品
	 */
	public static final Integer ISSPELLINGFLOWERPRODUCT_FALSE = 0;

	/**
	 * 是拼花产品
	 */
	public static final Integer ISSPELLINGFLOWERPRODUCT_TRUE = 1;
}
