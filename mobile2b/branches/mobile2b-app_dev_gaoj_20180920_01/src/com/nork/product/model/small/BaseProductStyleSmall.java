package com.nork.product.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: BaseProductStyle.java 
 * @Package com.nork.product.model.small
 * @Description:产品管理-产品风格
 * @createAuthor pandajun 
 * @CreateDate 2017-04-17 11:42:49
 * @version V1.0   
 */
public class BaseProductStyleSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  父类id  **/
	private Integer pid;
	/**  名称  **/
	private String name;
	/**  唯一标识编码  **/
	private String code;
	/**  方便于搜索的字段  **/
	private String longCode;
	/**  比如_两条现代简约的数据_value值定为一样  **/
	private Integer value;
	/**  是否是目录/最下级节点_0:是最底层节点_1:是目录  **/
	private Integer isLeaf;
	/**  等级目录  **/
	private Integer level;
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

	public  Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid){
		this.pid = pid;
	}
	public  String getName() {
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public  String getCode() {
		return code;
	}
	public void setCode(String code){
		this.code = code;
	}
	public  String getLongCode() {
		return longCode;
	}
	public void setLongCode(String longCode){
		this.longCode = longCode;
	}
	public  Integer getValue() {
		return value;
	}
	public void setValue(Integer value){
		this.value = value;
	}
	public  Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf){
		this.isLeaf = isLeaf;
	}
	public  Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level){
		this.level = level;
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
	public  String getAtt1() {
		return att1;
	}
	public void setAtt1(String att1){
		this.att1 = att1;
	}
	public  String getAtt2() {
		return att2;
	}
	public void setAtt2(String att2){
		this.att2 = att2;
	}
	public  Integer getNuma1() {
		return numa1;
	}
	public void setNuma1(Integer numa1){
		this.numa1 = numa1;
	}
	public  Integer getNuma2() {
		return numa2;
	}
	public void setNuma2(Integer numa2){
		this.numa2 = numa2;
	}
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}


}
