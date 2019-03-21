package com.sandu.product.model.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * BaseProductPO
 * 
 * @author huangsongbo
 * @date 2018.5.11
 */
@Data
public class BaseProductPO implements Serializable{

	private static final long serialVersionUID = -5210439207968871116L;

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
	 * 品牌id
	 */
	private Integer brandId;
	
	/**
	 * 产品风格id?
	 * 备注:旧字段,现在没用到??
	 */
	private Integer proStyleId;
	
	/**
	 * 产品规格
	 */
	private String productSpec;
	
	/**
	 * 颜色id
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
	private Double salePriceAdvice;
	
	/**
	 * 图片缩略图id
	 */
	private Integer picId;
	
	/**
	 * 3D模型id
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
	
	/**
	 * 创建时间
	 */
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
	 * 产品小图片
	 */
	private String picIds;
	
	/**
	 * 产品材质
	 */
	private String materialPicIds;
	
	/**
	 * 产品房间归属
	 */
	private String houseTypeValues;
	
	/**
	 * 产品类型
	 */
	private String productTypeValue;
	
	/**
	 * u3d模型
	 */
	private String u3dModelId;
	
	/**
	 * 同类型合并（合并产品ID）
	 */
	private String mergeProductIds;
	
	/**
	 * 时间备用1
	 */
	private Date putawayModified;
	
	/**
	 * 时间备用2
	 */
	private Date dateAtt2;
	
	/**
	 * 产品类型（小类）
	 */
	private Integer productSmallTypeValue;
	
	/**
	 * 上架状态
	 */
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
	
	/**
	 * ipad使用u3d模型
	 */
	private Integer ipadU3dModelId;
	
	/**
	 * ios使用u3d模型
	 */
	private Integer iosU3dModelId;
	
	/**
	 * android使用u3d模型
	 */
	private Integer androidU3dModelId;
	
	/**
	 * macBook使用u3d模型
	 */
	private Integer macBookU3dModelId;
	
	/**
	 * windows使用u3d模型
	 */
	private Integer windowsU3dModelId;
	
	/**
	 * web使用u3d模型
	 */
	private Integer webU3dmodelId;
	
	/**
	 * 饰品id
	 */
	private String decorationId;
	
	/**
	 * 设计师id
	 */
	private Integer designerId;
	
	/**
	 * 建模师id
	 */
	private Integer modelingId;
	
	/**
	 * 渲染师id
	 */
	private Integer renderingId;
	
	/**
	 * 产品小分类标示
	 */
	private String productSmallTypeMark;
	
	/**
	 * 产品大分类标示
	 */
	private String productTypeMark;
	
	/**
	 * 原始产品编码
	 */
	private String originalProductCode;
	
	/**
	 * 空间id
	 */
	private Integer spaceComonId;
	
	/**
	 * 产品短编码
	 */
	private String productShortCode;
	
	/**
	 * 硬装产品新增存的白模id
	 */
	private String bmIds;
	
	/**
	 * 编码序号
	 */
	private Integer codeNumber;
	
	/**
	 * 同类型产品(标识同一类)
	 */
	private Integer parentId;
	
	/**
	 * 样板房ID
	 */
	private Integer designTempletId;
	
	/**
	 * 材质球配置文件id(关联res_file)
	 */
	private Integer materialFileId;
	
	/**
	 * 3dMax材质图片
	 */
	private String material3dPicIds;
	
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
	private String  productModelNumber;
	
	/**
	 * 全铺长度
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
	 * 产品最小高度（天花用到）
	 */
	private String minHeight;
	
	/**
	 * 拆分材质信息
	 */
	private String splitTexturesInfo;
	
	/**
	 * 产品材质描述
	 */
	private String productMaterialDesc;
		
	/**
	 * 产品色系
	 */
	private String colorsLongCode;
	
	/**
	 * 配置文件ID
	 */
	private Integer configId;
	
	/**
	 * fbx文件ID
	 */
	private Integer fbxFileId;
	
	/**
	 * fbx贴图多个
	 */
	private String fbxTexture;
	
	/**
	 * fbx状态
	 */
	private Integer fbxState;
	
	/**
	 * 产品风格
	 */
	private String productStyleIdInfo;
	
	/**
	 * 款式ID
	 */
	private Integer styleId;
	
	/**
	 * 区域款式
	 * 尺寸代码
	 * 是否是标准 1 是， 0 否
	 */
	private String regionMark;
	
	/**
	 * 尺寸编码
	 */
	private String measureCode;
	
	/**
	 * 墙体分类
	 */
	private String wallType;
	
	/**
	 * 天花布局标识
	 */
	private String productSmallpoxIdentify;
	
	/**
	 * 地面布局标识
	 */
	private Integer productGroundIdentify;
	
	/**
	 * 系列ID
	 */
	private Integer seriesId;
	
	/**
	 * 前段传来的 拼花 json 传
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
	
	/**
	 * 建议售价价格
	 */
	private Double advicePrice;
	
	/**
	 * 是否保密：0不保密，1保密
	 */
	private Integer secrecyFlag;
	
	/**
	 * 天花截面数据
	 */
	private String ceilingCrossSectionData;
	
	/**
	 * 是否为标准，null或1：否；2是
	 */
	private Integer isStandard;
	
	/**
	 * 射灯距离天花顶的高度
	 */
	private String distanceCeilingTopHeight;
	
	/**
	 * 软装白膜产品是否拉伸过;
	 * 1:是; 
	 * null或2:否;
	 */
	private Integer isStretched;
	
	/**
	 * 公司id
	 */
	private Integer companyId;
	
	/**
	 * 来源产品Id
	 */
	private Integer originId;
	
}
