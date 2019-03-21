package com.sandu.api.platform.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**   
 * @author Sandu
 * @Title: PlatformProductRel.java
 * @Package com.nork.product.model
 * @Description:产品-类型平台-产品关联表
 * @createAuthor pandajun 
 * @CreateDate 2017-12-28 20:58:57
 * @version V1.0   
 */
@Data
public class PlatformProductRel implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  平台ID  **/
	private Integer platformId;
	/**  产品ID  **/
	private Integer productId;
	/**  产品上下架状态
	 * 0为下架,1为上架.
	 */
	private Integer putawayState;
	/**  此产品是否已经在此平台发布(状态描述)
	 *	0为未发布,1为发布.
	 *  **/
	private Integer allotState;
	/**  发布人ID  **/
	private Integer userId;
	/**  产品在此平台售价  **/
	private Double salePrice;
	/**  产品在此平台描述  **/
	private String description;
	/**  系统编码  **/
	private String sysCode;
	/**  创建者  **/
	private String creator;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;
	/**  是否删除  **/
	private Integer isDeleted;
	/**  字符备用1  **/
	private String att1;
	/**  字符备用2  **/

	private String att2;
	/**  整数备用1  **/
	private Integer numa1;
	/**  整数备用2  **/
	private Integer numa2;
	/**  备注  **/
	private String remark;
	/**  建议售价*/
	private Double advicePrice;
	/**  产品风格*/
	private String styleId;
	/**  产品缩略图*/
	private Integer picId;
	/**  产品所有图片*/
	private String picIds;
}
