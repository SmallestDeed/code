package com.sandu.api.product.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class ProductQueryPO implements Serializable {
    /**
     * 最高级分类编码
     */
    private String categoryCode;
    /**
     * 分配状态
     */
    private String allotState;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品公开状态：0不公开，1公开
     */
    private Integer secrecy;
    /**
     * 分类下的所有分类ID
     */
    private List<Integer> categoryIds;
    private Integer page;
    private Integer limit;
    /**
     * 排序字段
     */
    private String orderField;
    /**
     * 排序方式
     */
    private String orderMethod;

    /**
     * 查询类型:库查询library/渠道查询channel/线上查询online
     */
    private String queryType;
    /**
     * 线上平台ID,线上用于过滤查询
     */
    private Integer platformId;
    /**
     * 渠道产品上下架状态:1上架/0下架/-1全部
     */
    private Integer putawayState;
    /**
     * 根据产品ID集合查询产品
     */
    private List<Integer> productIds;
    /**
     * 公司id'
     **/
    private Integer companyId;
    /**
     * 品牌ID
     */
    private List<Integer> brandIds;
    /**
     * 渠道的某一平台ID
     */
    private Integer platform2bId;
    /**
     * 线上的某一平台ID
     */
    private Integer platform2cId;
    /**
     * 产品型号
     */
    private String modelNumber;

    /**
     *  产品大/小类编码
     */
    private String productType;

    /**
     * Added by songjianming@sanduspace.com on 2018/12/20
     * 产品库列表页面增加产品大类筛选。为兼容以前productType
     * @link http://jira.3du-inc.net/browse/CMS-634
     */
    private String bigProductType;

    /**
     * 查询偏移量
     */
    private Integer offsetNum;
}
