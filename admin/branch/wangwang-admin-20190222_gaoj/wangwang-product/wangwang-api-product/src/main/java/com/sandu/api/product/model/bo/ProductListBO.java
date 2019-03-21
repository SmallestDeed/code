package com.sandu.api.product.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class ProductListBO implements Serializable {
    private Integer id;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品缩略图ID
     */
    private Integer picId;
    /**
     * 产品缩略图路径
     */
    private String picPath;
    /**
     * 产品品牌名称
     */
    private String brandName;
    /**
     * 产品品牌主键
     */
    private Integer brandId;
    /**
     * 产品分类ID(最高级)
     */
    private Integer categoryId;
    /**
     * 产品分类信息(用于列表/详情页展示)
     */
    private String categoryNames;
    /**
     * 产品模型id
     */
    private Integer modelId;
    /**
     * 产品模型编码
     */
    private String modelCode;
    /**
     * 产品贴图编码
     */
    private String textureId;
    /**
     * 产品渠道分配状态
     */
    private Integer status2b;
    /**
     * 产品渠道分配时间
     */
    private Date gmtCreate2b;
    /**
     * 产品线上分配状态
     */
    private Integer status2c;
    /**
     * 产品线上分配时间
     */
    private Date gmtCreate2c;
    /**
     * 产品创建时间
     */
    private Date gmtCreate;
    /**
     * 产品公开状态：0不公开，1公开
     */
    private Integer secrecy;
    /**
     * 渠道产品上架状态
     */
    private Integer putAwayStatus2b;
    
    /**
     * 渠道产品上架状态
     */
    private Integer putAwayStatus2c;
    
    /**
     * 线上产品以上架平台名称
     */
    private List<String> putAwayPlatformNames;

    /**
     * 线上产品以上架平台名称
     */
    private List<Integer> putAwayPlatformIds;
    /**
     * 产品型号
     */
    private String modelNumber;
    /**
     * 产品所属公司
     **/
    private String companyName;

    /**
     * 此产品分配的所有平台ID     id,id,id字符串
     */
    private String platformIds;
    
    /**
     * 此产品分配的所有平台ID     id,id,id字符串
     */
    private String platform2cIds;
    
    /**
     * 此产品在各平台的上架状态  1,0,1字符串
     */
    private String putStatus;
    /**
     * 渠道默认缩略图
     */
    private Integer pic2bId;
    /**
     * 线上默认缩略图
     */
    private Integer pic2cId;
    /**
     * 产品小类
     */
    private String smallType;

    private String Type;
    /**
     * 产品类型(定制/软装)
     */
    private Integer productBatchType;

    private Integer isCreatedTexture;
}
