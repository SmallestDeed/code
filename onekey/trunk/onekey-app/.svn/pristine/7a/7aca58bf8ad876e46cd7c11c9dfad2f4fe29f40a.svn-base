package com.nork.onekeydesign.model;

import java.io.Serializable;
import java.util.List;

import com.nork.onekeydesign.model.vo.DimMatchInfoVO;

public class BedroomProductMatchDTO implements Serializable{

	private static final long serialVersionUID = -8823912576164994964L;

	/**
	 * 单品匹配信息
	 */
	private List<ProductMatchInfoDTO> productMatchInfo;
	
	/**
	 * 结构匹配信息
	 */
	private List<StructureMatchInfoDTO> structureMatchInfo;
	
	/**
	 * 组合匹配信息
	 */
	private List<GroupMatchInfoDTO> groupMatchInfo;
	
	/**
	 * 地面匹配数据
	 * 2018.3.15通用版本地面结构相关逻辑修改(地面结构修改为一块整铺的地面+拼花的数据)
	 */
	private List<DimMatchInfoVO> dimMatchInfo;
	
	/**
	 * 样板房配置文件地址
	 */
	private String designTempletConfigUrl;
	
	/**
	 * 推荐方案配置文件地址
	 */
	private String recommendedPlanConfigUrl;

	/**
	 * 1:old数据;2:来源于户型绘制
	 */
	private Integer dataType;
	
	/**
	 * 备用推荐方案id
	 * 默认值为0(应前端需要)
	 * add by huangsongbo 2018.7.17
	 */
	private Integer standbyRecommendedId = 0;
	
	public Integer getStandbyRecommendedId() {
		return standbyRecommendedId;
	}

	public void setStandbyRecommendedId(Integer standbyRecommendedId) {
		this.standbyRecommendedId = standbyRecommendedId;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public List<DimMatchInfoVO> getDimMatchInfo() {
		return dimMatchInfo;
	}

	public void setDimMatchInfo(List<DimMatchInfoVO> dimMatchInfo) {
		this.dimMatchInfo = dimMatchInfo;
	}

	public String getDesignTempletConfigUrl() {
		return designTempletConfigUrl;
	}

	public void setDesignTempletConfigUrl(String designTempletConfigUrl) {
		this.designTempletConfigUrl = designTempletConfigUrl;
	}

	public String getRecommendedPlanConfigUrl() {
		return recommendedPlanConfigUrl;
	}

	public void setRecommendedPlanConfigUrl(String recommendedPlanConfigUrl) {
		this.recommendedPlanConfigUrl = recommendedPlanConfigUrl;
	}

	public List<ProductMatchInfoDTO> getProductMatchInfo() {
		return productMatchInfo;
	}

	public void setProductMatchInfo(List<ProductMatchInfoDTO> productMatchInfo) {
		this.productMatchInfo = productMatchInfo;
	}

	public List<StructureMatchInfoDTO> getStructureMatchInfo() {
		return structureMatchInfo;
	}

	public void setStructureMatchInfo(List<StructureMatchInfoDTO> structureMatchInfo) {
		this.structureMatchInfo = structureMatchInfo;
	}

	public List<GroupMatchInfoDTO> getGroupMatchInfo() {
		return groupMatchInfo;
	}

	public void setGroupMatchInfo(List<GroupMatchInfoDTO> groupMatchInfo) {
		this.groupMatchInfo = groupMatchInfo;
	}

	public class ProductMatchInfoDTO{
		
		/**
		 * 要删除的白膜(被替换的白膜)的posName
		 */
		private String delPosName;
		
		/**
		 * 替换后的产品code(如果为空,则以为对应白膜要删除)
		 */
		private String productCode;

		/**
		 * 推荐方案匹配到单品的posName
		 */
		private String recommendedPosName;
		
		/**
		 * 匹配日志
		 */
		private String matchInfo;
		
		/**
		 * 对应的设计方案产品表的id
		 */
		private Integer designPlanProductId;
		
		private String bigTypeValuekey;
		
		private String smallTypeValuekey;
		
		/**
		 * 是否是背景墙
		 * 0.普通墙面
		 * 1.背景墙
		 * 2.门框
		 */
		private Integer wallType;
		
		/**
		 * 匹配到的推荐方案中的产品的posName
		 */
		private String matchedPosName;
		
		public String getMatchedPosName() {
			return matchedPosName;
		}

		public void setMatchedPosName(String matchedPosName) {
			this.matchedPosName = matchedPosName;
		}

		public Integer getWallType() {
			return wallType;
		}

		public void setWallType(Integer wallType) {
			this.wallType = wallType;
		}

		public String getBigTypeValuekey() {
			return bigTypeValuekey;
		}

		public void setBigTypeValuekey(String bigTypeValuekey) {
			this.bigTypeValuekey = bigTypeValuekey;
		}

		public String getSmallTypeValuekey() {
			return smallTypeValuekey;
		}

		public void setSmallTypeValuekey(String smallTypeValuekey) {
			this.smallTypeValuekey = smallTypeValuekey;
		}

		public Integer getDesignPlanProductId() {
			return designPlanProductId;
		}

		public void setDesignPlanProductId(Integer designPlanProductId) {
			this.designPlanProductId = designPlanProductId;
		}

		public String getMatchInfo() {
			return matchInfo;
		}

		public void setMatchInfo(String matchInfo) {
			this.matchInfo = matchInfo;
		}

		public String getRecommendedPosName() {
			return recommendedPosName;
		}

		public void setRecommendedPosName(String recommendedPosName) {
			this.recommendedPosName = recommendedPosName;
		}

		public String getDelPosName() {
			return delPosName;
		}

		public void setDelPosName(String delPosName) {
			this.delPosName = delPosName;
		}

		public String getProductCode() {
			return productCode;
		}

		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}
		
	}
	
	public static class WallTypeConstant{
		
		public static final Integer BEIJING = 1;
		
		public static final Integer NORMAL = 0;
				
		public static final Integer MENGKUANG = 2;
		
	}
	
	public class StructureMatchInfoDTO{
		
		/**
		 * 待删除的结构白膜posName
		 */
		private List<String> delPosNameList;
		
		/**
		 * 推荐方案中,结构产品posName+productCode信息
		 */
		private List<DetailInfo> structureDetailInfoList;
		
		/**
		 * 要替换进来的结构配置文件
		 */
		private String structureConfig;

		/**
		 * 样板房中白膜结构中心点(待删除结构的中心点)
		 */
		private String delStructureCenter;
		
		/**
		 * 新的planStructureId
		 */
		/*private String newPlanStructureId;*/
		
		/**
		 * 匹配信息
		 */
		private String matchInfo;
		
		/**
		 * 样板房中结构的区域标识
		 */
		private String  delStructureRegionMark;
		
		/**
		 * 匹配上的推荐方案中的结构的id
		 */
		private Integer productStructureId;
		
		/**
		 * 匹配上的推荐方案中的结构的区域标识
		 */
		private String planStructureRegionMark;
		
		public Integer getProductStructureId() {
			return productStructureId;
		}

		public void setProductStructureId(Integer productStructureId) {
			this.productStructureId = productStructureId;
		}

		public String getPlanStructureRegionMark() {
			return planStructureRegionMark;
		}

		public void setPlanStructureRegionMark(String planStructureRegionMark) {
			this.planStructureRegionMark = planStructureRegionMark;
		}

		public String getDelStructureRegionMark() {
			return delStructureRegionMark;
		}

		public void setDelStructureRegionMark(String delStructureRegionMark) {
			this.delStructureRegionMark = delStructureRegionMark;
		}

		public String getMatchInfo() {
			return matchInfo;
		}

		public void setMatchInfo(String matchInfo) {
			this.matchInfo = matchInfo;
		}

		/*public String getNewPlanStructureId() {
			return newPlanStructureId;
		}

		public void setNewPlanStructureId(String newPlanStructureId) {
			this.newPlanStructureId = newPlanStructureId;
		}*/

		public String getDelStructureCenter() {
			return delStructureCenter;
		}

		public void setDelStructureCenter(String delStructureCenter) {
			this.delStructureCenter = delStructureCenter;
		}

		public List<DetailInfo> getStructureDetailInfoList() {
			return structureDetailInfoList;
		}

		public void setStructureDetailInfoList(List<DetailInfo> structureDetailInfoList) {
			this.structureDetailInfoList = structureDetailInfoList;
		}

		public List<String> getDelPosNameList() {
			return delPosNameList;
		}

		public void setDelPosNameList(List<String> delPosNameList) {
			this.delPosNameList = delPosNameList;
		}

		public String getStructureConfig() {
			return structureConfig;
		}

		public void setStructureConfig(String structureConfig) {
			this.structureConfig = structureConfig;
		}
		
	}
	
	public class DetailInfo{
		/**
		 * 推荐方案中结构/组合产品的posName
		 */
		private String recommendedPlanProductPosName;
		
		/**
		 * 推荐方案中结构/组合产品的code
		 */
		private String recommendedPlanProductCode;

		private Integer designPlanProductId;
		
		private String bigTypeValuekey;
		
		private String smallTypeValuekey;
		
		/**
		 * 是否是主产品
		 * 0:非
		 * 1:是主产品
		 */
		private Integer isMainProduct;
		
		/**
		 * 组合中产品的唯一标识
		 */
		private String groupProductUniqueId;
		
		/**
		 * 初始化白膜code
		 */
		private String basicModelCode;
		
		/**
		 * 匹配到的推荐方案中的产品的posName
		 */
		private String matchedPosName;
		
		public String getMatchedPosName() {
			return matchedPosName;
		}

		public void setMatchedPosName(String matchedPosName) {
			this.matchedPosName = matchedPosName;
		}
		
		public String getBasicModelCode() {
			return basicModelCode;
		}

		public void setBasicModelCode(String basicModelCode) {
			this.basicModelCode = basicModelCode;
		}

		public String getGroupProductUniqueId() {
			return groupProductUniqueId;
		}

		public void setGroupProductUniqueId(String groupProductUniqueId) {
			this.groupProductUniqueId = groupProductUniqueId;
		}

		public Integer getIsMainProduct() {
			return isMainProduct;
		}

		public void setIsMainProduct(Integer isMainProduct) {
			this.isMainProduct = isMainProduct;
		}

		public String getBigTypeValuekey() {
			return bigTypeValuekey;
		}

		public void setBigTypeValuekey(String bigTypeValuekey) {
			this.bigTypeValuekey = bigTypeValuekey;
		}

		public String getSmallTypeValuekey() {
			return smallTypeValuekey;
		}

		public void setSmallTypeValuekey(String smallTypeValuekey) {
			this.smallTypeValuekey = smallTypeValuekey;
		}

		public Integer getDesignPlanProductId() {
			return designPlanProductId;
		}

		public void setDesignPlanProductId(Integer designPlanProductId) {
			this.designPlanProductId = designPlanProductId;
		}

		public String getRecommendedPlanProductPosName() {
			return recommendedPlanProductPosName;
		}

		public void setRecommendedPlanProductPosName(String recommendedPlanProductPosName) {
			this.recommendedPlanProductPosName = recommendedPlanProductPosName;
		}

		public String getRecommendedPlanProductCode() {
			return recommendedPlanProductCode;
		}

		public void setRecommendedPlanProductCode(String recommendedPlanProductCode) {
			this.recommendedPlanProductCode = recommendedPlanProductCode;
		}
		
	}
	
	public class GroupMatchInfoDTO{
		/**
		 * 待删除的组合白膜posName
		 */
		private List<String> delPosNameList;
		
		/**
		 * 推荐方案中,组合产品posName+productCode信息
		 */
		private List<DetailInfo> groupDetailInfoList;
		
		/**
		 * 要替换进来的组合配置文件
		 */
		private String groupConfig;

		/**
		 * 新的planStructureId
		 */
		private String newPlanGroupId;
		
		/**
		 * 匹配信息
		 */
		private String matchInfo;
		
		/**
		 * 主产品posName(样板房) 
		 */
		private String mainProductPosName;
		
		public String getMainProductPosName() {
			return mainProductPosName;
		}

		public void setMainProductPosName(String mainProductPosName) {
			this.mainProductPosName = mainProductPosName;
		}

		public String getNewPlanGroupId() {
			return newPlanGroupId;
		}

		public void setNewPlanGroupId(String newPlanGroupId) {
			this.newPlanGroupId = newPlanGroupId;
		}

		public String getMatchInfo() {
			return matchInfo;
		}

		public void setMatchInfo(String matchInfo) {
			this.matchInfo = matchInfo;
		}

		public List<DetailInfo> getGroupDetailInfoList() {
			return groupDetailInfoList;
		}

		public void setGroupDetailInfoList(List<DetailInfo> groupDetailInfoList) {
			this.groupDetailInfoList = groupDetailInfoList;
		}

		public List<String> getDelPosNameList() {
			return delPosNameList;
		}

		public void setDelPosNameList(List<String> delPosNameList) {
			this.delPosNameList = delPosNameList;
		}

		public String getGroupConfig() {
			return groupConfig;
		}

		public void setGroupConfig(String groupConfig) {
			this.groupConfig = groupConfig;
		}
		
	}
	
}
