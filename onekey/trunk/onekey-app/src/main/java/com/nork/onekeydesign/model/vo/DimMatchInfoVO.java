package com.nork.onekeydesign.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author huangsongbo
 *
 */
public class DimMatchInfoVO implements Serializable{

	private static final long serialVersionUID = -4630599299423506724L;

	/**
	 * 样板房该地面的区域标识
	 */
	private String templetRegionMark;
	
	/**
	 * 推荐方案中匹配到的地面结构
	 */
	private String recommendedRegionMark;
	
	/**
	 * 匹配到的推荐方案中的结构编码
	 */
	private String recommendedStructureCode;
	
	/**
	 * 还要加一个顶点数据
	 */
	private String vertexDataFilePath;
	
	/**
	 * 匹配到的结构详情
	 */
	private List<StructureDetailInfoVO> structureDetailInfoList;

	public String getVertexDataFilePath() {
		return vertexDataFilePath;
	}

	public void setVertexDataFilePath(String vertexDataFilePath) {
		this.vertexDataFilePath = vertexDataFilePath;
	}

	public String getTempletRegionMark() {
		return templetRegionMark;
	}

	public void setTempletRegionMark(String templetRegionMark) {
		this.templetRegionMark = templetRegionMark;
	}

	public String getRecommendedRegionMark() {
		return recommendedRegionMark;
	}

	public void setRecommendedRegionMark(String recommendedRegionMark) {
		this.recommendedRegionMark = recommendedRegionMark;
	}

	public String getRecommendedStructureCode() {
		return recommendedStructureCode;
	}

	public void setRecommendedStructureCode(String recommendedStructureCode) {
		this.recommendedStructureCode = recommendedStructureCode;
	}

	public List<StructureDetailInfoVO> getStructureDetailInfoList() {
		return structureDetailInfoList;
	}

	public void setStructureDetailInfoList(List<StructureDetailInfoVO> structureDetailInfoList) {
		this.structureDetailInfoList = structureDetailInfoList;
	}
	
}
