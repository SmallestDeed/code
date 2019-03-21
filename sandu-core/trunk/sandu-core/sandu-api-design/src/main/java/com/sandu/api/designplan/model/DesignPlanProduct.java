package com.sandu.api.designplan.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: DesignPlanProduct.java
 * @Package com.nork.design.model
 * @Description:设计方案-设计方案产品库
 * @createAuthor pandajun
 * @CreateDate 2015-06-26 11:26:11
 */
@Data
public class DesignPlanProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 设计方案产品ID
     **/
    private Integer designPlanProductId;
    /**
     * 系统编码
     **/
    private String sysCode;
    /**
     * 创建者
     **/
    private String creator;
    /**
     * 创建时间
     **/
    private Date gmtCreate;
    /**
     * 修改人
     **/
    private String modifier;
    /**
     * 修改时间
     **/
    private Date gmtModified;
    /**
     * 是否删除
     **/
    private Integer isDeleted;
    /**
     * 方案id
     **/
    private Integer planId;
    /**
     * 设计方案id
     **/
    private Integer designPlanId;
    /**
     * 产品挂节点路径
     **/
    private String posIndexPath;
    /**
     * 产品id
     **/
    private Integer productId;
    /**
     * 初始产品ID
     **/
    private Integer initProductId;
    /**
     * 产品编码
     **/
    private String productCode;
    /**
     * 产品名称
     **/
    private String productName;
    /**
     * 产品缩略图
     **/
    private String productSmallPicPath;
    /**
     * 产品缩略图
     **/
    private String productU3dModelPath;
    /**
     * 产品编码
     **/
    private Integer productType;
    /**
     * 相机位置文件id
     **/
    private Integer locationFileId;
    /**
     * 产品顺序
     **/
    private String productSequence;
    /**
     * 字符备用1
     **/
    private String materialPicId;
    /**
     * 作为组合中产品的唯一标识使用(区分组合中两个一模一样的产品,使用与一件装修功能)
     */
    private String att2;
    /**
     * 字符备用3
     **/
    private String att3;
    /**
     * 字符备用4
     **/
    private String att4;
    /**
     * 字符备用5
     **/
    private String att5;
    /**
     * 字符备用6
     **/
    private String att6;
    /**
     * 时间备用1
     **/
    private Date dateAtt1;
    /**
     * 时间备用2
     **/
    private Date dateAtt2;
    /**
     * 样板房列表主键1
     **/
    private Integer planProductId;
    /**
     * 样板房列表产品id
     **/
    private Integer templatePlanProductId;
    /**
     * 显示状态
     **/
    private Integer displayStatus;
    /**
     * 数字备用1
     **/
    private Double numAtt3;
    /**
     * 数字备用2
     **/
    private Double numAtt4;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 是否隐藏
     **/
    private Integer isHide;
    /**
     * 产品类型名称
     **/
    private String productTypeName;
    /**
     * 用户ID
     **/
    private Integer userId;
    /**
     * 品牌名称
     **/
    private String brandName;
    /**
     * 产品价格
     **/
    private Double salePrice;
    //记录物体是否被移动过
    private Integer isDirty;

    private String productTypeCode;
    private String productSmallTypeCode;
    private String productSmallTypeName;
    private String materialConfigPath;
    private Integer productSmallTypeValue;
    private Integer parentProductId;
    //材质图片s
    private String[] materialPicPaths;
    /**
     * 规则json
     **/
    private Map<String, String> rulesMap;
    //组合产品ＩＤ
    private Integer productGroupId;
    //是否是主产品（１，是；0，否）
    private Integer isMainProduct;

    private String rootType;
    private String productLength;
    private String productWidth;
    private String productHeight;
    /**
     * u3d模型
     **/
    private String u3dModelPath;
    /*组合id+时间戳(区分同groupId组)*/
    private String planGroupId;
    /*有模型的产品id*///平吊天花换模型在换贴图用到，换贴图用当前产品模型
    private Integer modelProductId;
    /**
     * 挂节点名称
     **/
    private String posName;
    /**
     * 绑定父产品ID
     **/
    private String bindParentProductId;
    /*设计方案产品材质信息*/
    private String splitTexturesChooseInfo;
    /*判断是否是可拆分材质产品:0:普通产品;1:可拆分材质产品(橱柜)*/
    private Integer isSplit = new Integer(0);
    private String status;//如果绑定三度或者是内部则产品清单不做过滤

    private List<String> att2List;


    /*是组合还是结构,0:组合,1:结构*/
    private Integer groupType;

    /**
     * 产品属性
     **/
    private Map<String, String> propertyMap;


    private String isInternalUser;

    //产品同小分类序号
    private Integer sameProductTypeIndex;
    //0、实体墙  1、背景墙 2、门框
    private Integer bgWall;

    private String brandsStr;

    private List<Integer> resIdList;

    /*产品的状态*/
    private Integer productPutawayState;

    /*产品的状态集合*/
    private List<Integer> productPutawayStateList;

    /**
     * 是否做了材质替换(0:否;1:是)
     */
    private Integer isReplaceTexture;

    /**
     * 设计方案对应样板房产品表的posName
     */
    private String oldPosName;


    /*标准(1标准 0 非标准)*/
    private Integer isStandard;
    /*中心点*/
    private String center;
    /*区域标识*/
    private String regionMark;
    /*款式id*/
    private Integer styleId;
    /*尺寸代码*/
    private String measureCode;
    /*描述(区域、尺寸代码)*/
    private String describeInfo;
    //序号
    private Integer productIndex;

    /*是否为结构中的主产品标识（1是，0否）*/
    private Integer isMainStructureProduct;
    /*主产品单品是否可以作为组合方式替换（1是，0否）*/
    private Integer isGroupReplaceWay;

    List<Integer> designPlanProductIds = new ArrayList<Integer>();

    /*墙体类型*/
    private String wallOrientation;
    /*墙体方位*/
    private String wallType;

    /**
     * 组合中产品的唯一标识
     */
    private String groupProductUniqueId;


    //用户产品配置三种情况（1品牌，2品牌大类，3品牌大类小类）
    private String brands;
    private String bigType;
    private String smallType;


}
