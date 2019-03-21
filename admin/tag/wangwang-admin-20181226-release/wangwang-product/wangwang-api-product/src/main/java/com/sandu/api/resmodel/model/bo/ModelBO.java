package com.sandu.api.resmodel.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ModelBO implements Serializable {
    private Long id;

    /**
     * 模型编码
     */
    private String modelCode;

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 模型文件名称
     */
    private String modelFileName;

    /**
     * 模型文件类型
     */
    private String modelType;

    /**
     * 模型大小
     */
    private String modelSize;

    /**
     * 模型后缀
     */
    private String modelSuffix;

    /**
     * 模型级别
     */
    private String modelLevel;

    /**
     * 模型路径
     */
    private String modelPath;

    /**
     * 模型描述
     */
    private String modelDesc;

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
     * 模型排序
     */
    private Integer modelOrdering;

    /**
     * 字符备用1
     */
    private String fileKey;

    /** */
    private String fileKeys;

    /** */
    private String businessIds;

    /**
     * 字符备用4
     */
    private String att4;

    /**
     * 字符备用5
     */
    private String att5;

    /**
     * 字符备用6
     */
    private String att6;

    /**
     * 时间备用1
     */
    private Date dateAtt1;

    /**
     * 时间备用2
     */
    private Date dateAtt2;

    /**
     * 整数备用1
     */
    private Integer businessId;

    /**
     * 整数备用2
     */
    private Integer numAtt2;

    /**
     * 数字备用1
     */
    private BigDecimal numAtt3;

    /**
     * 数字备用2
     */
    private BigDecimal numAtt4;

    /**
     * 备注
     */
    private String remark;

    /**
     * 长
     */
    private Integer length;

    /**
     * 高
     */
    private Integer height;

    /**
     * 宽
     */
    private Integer width;

    /**
     * 最小高度
     */
    private Integer minHeight;

    /** */
    private Integer isModelShare;

    /**
     * 是否解压
     */
    private Integer isDecompress;

    /**
     * 缩略图的文件路径
     */
    private String thumbPicPath;
    /**
     * 分类ids
     */
    private String categoryIds;
    /**
     * 分类names
     */
    private String categoryNames;
    /**
     * 转换状态 ING 转换中 SUCCESS 成功 FAIL 失败
     */
    private String transStatus;

    /**
     * 模型型号
     */
    private String modelModelNum;

    /**
     * 公司id
     */
    private Integer companyId;

    /**
     * 3du文件路径
     */
    private String file3duPath;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 源模型id
     */
    private Long originId;

    /**
     * 模型大类
     */
    private String typeMark;
    /**
     * 模型小类
     */
    private String smallTypeMark;

    private List<String> typeNames;
    /**
     * 模型区域信息
     */
    private List<ModelAreaBO> modelTextureInfo;

    @ApiModelProperty(value = "模型材质数量")
    private Integer textureCount;

    @ApiModelProperty(value = "主模型标志 true:为主模型,flse:副模型 ")
    private Boolean mainProductFlag;

}
