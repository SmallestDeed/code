package com.sandu.api.house.input;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 户型绘制样板房对象类
 * 
 * @author 何文
 * @version 1.0
 *          <p/>
 *          Company:Sandu Copyright:Copyright(c)2017
 * @date 2018/1/2
 */
@Data
public class DrawDesignTempletNew implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("样板房名称")
	private String templetName;

	@ApiModelProperty("样板房编码")
	private String templetCode;

	@ApiModelProperty("样板房配置文件")
	private List<DrawFileDataNew> templetFile;
}
