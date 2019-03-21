package com.sandu.system.model;

import com.sandu.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**   
 * @Title: BasePlatform.java 
 * @Package com.nork.platform.model
 * @Description:基础-平台表
 * @createAuthor pandajun 
 * @CreateDate 2017-12-29 10:16:41
 * @version V1.0   
 */
@Data
public class BasePlatform extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  平台名称  **/
	private String platformName;
	/**  平台类型(前台/后台)  **/
	private String platformType;
	/**  媒介类型  **/
	private String mediaType;
	/**  平台编码  **/
	private String platformCode;
	/**  所属平台分类(2b/2c/sandu)  **/
	private String platformBussinessType;
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
    
    /**获取对象的copy**/
    public BasePlatform copy(){
       BasePlatform obj =  new BasePlatform();
       obj.setPlatformName(this.platformName);
       obj.setPlatformType(this.platformType);
       obj.setMediaType(this.mediaType);
       obj.setPlatformCode(this.platformCode);
       obj.setPlatformBussinessType(this.platformBussinessType);
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setAtt1(this.att1);
       obj.setAtt2(this.att2);
       obj.setNuma1(this.numa1);
       obj.setNuma2(this.numa2);
       obj.setRemark(this.remark);

       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("platformName",this.platformName);
       map.put("platformType",this.platformType);
       map.put("mediaType",this.mediaType);
       map.put("platformCode",this.platformCode);
       map.put("platformBussinessType",this.platformBussinessType);
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("numa1",this.numa1);
       map.put("numa2",this.numa2);
       map.put("remark",this.remark);

       return map;
    }
}
