package com.nork.onekeydesign.model.unity;

import java.io.Serializable;

public class RoomExt  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer spaceCommonId;
	
	private Integer designTempletId;
	
	private Integer designPlanId;
	
	private Integer designSourceType;
	
	private Integer designSourceId;
	
	private String  designMode;

	private Integer mediaType;

	public Integer getSpaceCommonId() {
		return spaceCommonId;
	}

	public void setSpaceCommonId(Integer spaceCommonId) {
		this.spaceCommonId = spaceCommonId;
	}

	public Integer getDesignTempletId() {
		return designTempletId;
	}

	public void setDesignTempletId(Integer designTempletId) {
		this.designTempletId = designTempletId;
	}

	public Integer getDesignPlanId() {
		return designPlanId;
	}

	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
	}

	public Integer getDesignSourceType() {
		return designSourceType;
	}

	public void setDesignSourceType(Integer designSourceType) {
		this.designSourceType = designSourceType;
	}

	public Integer getDesignSourceId() {
		return designSourceId;
	}

	public void setDesignSourceId(Integer designSourceId) {
		this.designSourceId = designSourceId;
	}

	public String getDesignMode() {
		return designMode;
	}

	public void setDesignMode(String designMode) {
		this.designMode = designMode;
	}

	public Integer getMediaType() {
		return mediaType;
	}

	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}

}
