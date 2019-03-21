package com.nork.onekeydesign.model.vo;

import java.io.Serializable;
import java.util.List;

import com.nork.product.model.SplitTextureDTO;

/**
 * 
 * @author huangsongbo
 *
 */
public class StructureDetailInfoVO implements Serializable{

	private static final long serialVersionUID = -965391373722380798L;

	/**
	 * 结构中某块地面的structureSign(属性标识)
	 */
	private String structureSign;
	
	/**
	 * 结构中某块地面材质相关信息
	 */
	private List<SplitTextureDTO> splitTexturesChooseInfo;

	/**
	 * 产品id
	 */
	private Integer productId;
	
	/**
	 * 产品编码
	 */
	private String productCode;
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getStructureSign() {
		return structureSign;
	}

	public void setStructureSign(String structureSign) {
		this.structureSign = structureSign;
	}

	public List<SplitTextureDTO> getSplitTexturesChooseInfo() {
		return splitTexturesChooseInfo;
	}

	public void setSplitTexturesChooseInfo(List<SplitTextureDTO> splitTexturesChooseInfo) {
		this.splitTexturesChooseInfo = splitTexturesChooseInfo;
	}
	
}
