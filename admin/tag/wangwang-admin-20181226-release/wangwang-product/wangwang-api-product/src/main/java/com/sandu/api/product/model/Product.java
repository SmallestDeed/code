package com.sandu.api.product.model;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sun.font.CoreMetrics;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 * @date 2017/12/16
 */
@Data
public class Product implements Serializable {
    //单模型产品
    public static final Integer PRODUCT_TYPE_SINGLE = 1;
    //多模型副产品
    public static final Integer PRODUCT_TYPE_PROTO_NORMAL = 0;
    //多模型主产品
    public static final Integer PRODUCT_TYPE_PROTO_MAIN = 2;

    static public Product copyBean(Product product) {
        Product ret = new Product();
        BeanUtils.copyProperties(product, ret);
        return ret;
    }

    /** */
    private Long id;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品品牌
     */
    private Integer brandId;

    /**
     * 产品风格
     */
    private Integer proStyleId;

    /**
     * 产品规格
     */
    private String productSpec;

    /**
     * 产品颜色
     */
    private Integer colorId;

    /**
     * 产品长度
     */
    private String productLength;

    /**
     * 产品宽度
     */
    private String productWidth;

    /**
     * 产品高度
     */
    private String productHeight;

    /**
     * 销售价格
     */
    private Double salePrice;

    /**
     * 建议销售价格
     */
    private Double advicePrice;

    /**
     * 图片缩略图id
     */
    private Integer picId;

    /**
     * 模型id
     */
    private Integer modelId;

    /**
     * 产品描述
     */
    private String productDesc;

    /**
     * 系统编码
     */
    private String sysCode;

    /**
     * 创建者
     */
    private String creator;

    /** */
    private Date gmtCreate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * PC端产品图片列表
     */
    private String picIds;

    /**
     * 产品材质
     */
    private String materialPicIds;

    /**
     * 房型类别
     */
    private String houseTypeValues;

    /**
     * 产品类型
     */
    private String productTypeValue;

    /**
     * u3d模型
     */
    private String u3dmodelId;

    /** */
    private String mergeProductIds;

    /**
     * 上架操作修改时间
     */
    private Date putawayModified;

    /**
     * 时间备用2
     */
    private Date dateAtt2;

    /**
     * 产品小类
     */
    private Integer productSmallTypeValue;

    /** */
    private Integer putawayState;

    /**
     * 数字备用1
     */
    private Double numAtt3;

    /**
     * 数字备用2
     */
    private Double numAtt4;

    /**
     * 备注
     */
    private String remark;

    /** */
    private Integer ipadU3dmodelId;

    /** */
    private Integer iosU3dmodelId;

    /** */
    private Integer androidU3dmodelId;

    /** */
    private Integer macbookU3dmodelId;

    /** */
    private Integer windowsU3dmodelId;

    /** */
    private Integer webU3dmodelId;

    /** */
    private String decorationId;

    /** */
    private Integer designerId;

    /** */
    private Integer modelingId;

    /** */
    private Integer renderingId;

    /** */
    private String productTypeMark;

    /** */
    private String productSmallTypeMark;

    /** */
    private String originalProductCode;

    /** */
    private Integer spacecomonId;

    /** */
    private String productShortCode;

    /** */
    private String bmIds;

    /** */
    private Integer codeNumber;

    /** */
    private Integer parentId;

    /** */
    private Integer designtempletId;

    /** */
    private Integer materialFileId;

    /** */
    private String material3dPicIds;

    /** */
    private String syncStatus;

    /**
     * 该code使用状态 ， 1:已使用 2未使用
     */
    private Integer codeIsEmploy;

    /**
     * 批次
     */
    private String codeBatch;

    /**
     * 产品型号
     */
    private String productModelNumber;

    /**
     * 背景墙全铺长度
     */
    private String fullPaveLength;

    /**
     * 记录测试操作修改时间
     */
    private Date testModified;

    /**
     * 产品价格单位
     */
    private Integer salePriceValue;

    /**
     * 最小高度
     */
    private String minHeight;

    /**
     * 产品材质描述
     */
    private String productMaterialDesc;

    /**
     * 产品色系编码
     */
    private String colorsLongCode;

    /**
     * 产品配置文件
     */
    private Integer configId;

    /**
     * FBX配置文件
     */
    private Integer fbxFileId;

    /**
     * FBX贴图
     */
    private String fbxTexture;

    /**
     * FBX处理状态 0未处理 1已处理
     */
    private Integer fbxState;

    /**
     * 选中风格信息
     */
    private String productStyleIdInfo;

    /** */
    private Long styleId;

    /**
     * 区域标识
     */
    private String regionMark;

    /**
     * 尺寸代码
     */
    private String measureCode;

    /**
     * 墙体分类
     */
    private String wallType;

    /**
     * 天花布局标识
     */
    private Integer productSmallpoxIdentify;

    /**
     * 地面布局标识
     */
    private Integer productGroundIdentify;

    /**
     * 系列ID
     */
    private Integer seriesId;

    /**
     * 拼花信息文件id
     */
    private Integer spellingFlowerFileId;

    /**
     * 移动端材质替换图片列表
     */
    private String appPicTextureIds;

    /**
     * 布局标识
     */
    private String productSmallpoxIdentifyStr;

    /** */
    private String splitTexturesInfo;
    /**
     * 产品公开状态
     */
    private Integer secrecyFlag;
    /**
     * 产品公司
     */
    private Integer companyId;
    /**
     * 产品来源
     */
    private Integer originId;

    /**
     * spuId
     */
    private Integer goodsSpuId;
    /**
     * 定制产品主id
     */
    private Long productBatchId;
    /**
     * 产品类型
     * 0:多模型副产品
     * 1:单模型产品
     * 2:多模型主产品
     */
    private Integer productBatchType;

    /**
     * 是否单独创建产品（0：是，1：否）
     */
    private Integer isCreatedTexture;

    /**
     * 是否一石多面
     */
    private Integer isComplexParquet;

}
