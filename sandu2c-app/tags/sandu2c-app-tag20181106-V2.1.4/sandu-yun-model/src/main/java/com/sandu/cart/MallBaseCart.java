package com.sandu.cart;

import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


/**
 * @Title: MallBaseCart.java
 * @Package com.nork.mini.cart.model
 * @Description:小程序-购物车
 * @createAuthor pandajun
 * @CreateDate 2018-03-28 10:57:15
 * @version V1.0
 */
public class MallBaseCart  extends Mapper implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**  用户id  **/
	private Integer userId;
	/**  购物车总价  **/
	private Double cartAmount;
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
	/**  备注  **/
	private String remark;
	/**  购物车产品集合  **/
	private List<MallCartProductRef> cartProductList;

	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public Double getCartAmount() {
		return cartAmount;
	}
	public void setCartAmount(Double cartAmount) {
		this.cartAmount = cartAmount;
	}
	public  String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode){
		this.sysCode = sysCode;
	}
	public  String getCreator() {
		return creator;
	}
	public void setCreator(String creator){
		this.creator = creator;
	}
	public  Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}
	public  String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
	public  Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}
	public  Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public List<MallCartProductRef> getCartProductList() {
		return cartProductList;
	}
	public void setCartProductList(List<MallCartProductRef> cartProductList) {
		this.cartProductList = cartProductList;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		MallBaseCart other = (MallBaseCart) that;
		return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
				&& (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
				&& (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
				&& (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
				&& (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
				&& (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
				&& (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
				&& (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
				&& (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
				;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
		result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
		result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
		result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
		result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
		result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
		result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
		result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
		;
		return result;
	}

	/**获取对象的copy**/
	public MallBaseCart copy(){
		MallBaseCart obj =  new MallBaseCart();
		obj.setUserId(this.userId);
		obj.setSysCode(this.sysCode);
		obj.setCreator(this.creator);
		obj.setGmtCreate(this.gmtCreate);
		obj.setModifier(this.modifier);
		obj.setGmtModified(this.gmtModified);
		obj.setIsDeleted(this.isDeleted);
		obj.setRemark(this.remark);

		return obj;
	}

	/**获取对象的map**/
	public Map toMap(){
		Map map =  new HashMap();
		map.put("userId",this.userId);
		map.put("sysCode",this.sysCode);
		map.put("creator",this.creator);
		map.put("gmtCreate",this.gmtCreate);
		map.put("modifier",this.modifier);
		map.put("gmtModified",this.gmtModified);
		map.put("isDeleted",this.isDeleted);
		map.put("remark",this.remark);

		return map;
	}
}
