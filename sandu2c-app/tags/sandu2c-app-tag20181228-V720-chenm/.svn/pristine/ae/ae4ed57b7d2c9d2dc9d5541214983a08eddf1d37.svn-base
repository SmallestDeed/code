package com.sandu.home.model.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.sandu.system.model.RenderTaskState;
import lombok.Data;

@Data
public class BaseHouseGetMatchResultDTO implements Serializable {

	private static final long serialVersionUID = 2386937295460550588L;

	public BaseHouseGetMatchResultDTO() {
		
	}
	
	public BaseHouseGetMatchResultDTO(Integer status) {
		this.status = status;
	}
	
	public BaseHouseGetMatchResultDTO(Integer status, Integer templateId, Integer recommendedId) {
		this.status = status;
		this.templateId = templateId;
		this.recommendedId = recommendedId;
	}
	
	public BaseHouseGetMatchResultDTO(Integer status, HouseGuidePicInfoDTO houseGuidePicInfo) {
		this.status = status;
		this.houseGuidePicInfo = houseGuidePicInfo;
	}
	
	/**
	 * 状态:
	 * 1:无适配样板房(该方案不匹配,请选择其他方案)
	 * 2:适配的样板房都在渲染中(有方案正在渲染中,请稍后)
	 * 3:只存在一个适配且未渲染的样板房(正在装修中,预计需要三分钟)
	 * 4.只存在一个适配且渲染完成的样板房(弹出新增/替换选项)
	 * 5.存在多个适配且非渲染中状态的样板房(弹出户型图,供用户选择使用哪个样板房)
	 * 6.未知情况(按理说程序不允许出现)
	 */
	private Integer status;

	/**
	 * if status = 5 -> 弹出户型图所需要的信息
	 */
	private HouseGuidePicInfoDTO houseGuidePicInfo;
	
	/**
	 * if status = 3 or 4 返回样板房id
	 */
	private Integer templateId;
	
	/**
	 * if status = 3 or 4 返回推荐方案id
	 */
	private Integer recommendedId;

	private List<RenderTaskState> taskStateList;

	private HashMap<Integer,Integer> templateIdMapOfBusinessId;
}
