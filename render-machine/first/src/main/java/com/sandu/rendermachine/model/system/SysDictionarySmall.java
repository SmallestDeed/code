package com.sandu.rendermachine.model.system;

import lombok.Data;

import java.io.Serializable;


/**   
 * @Title: SysDictionary.java 
 * @Package com.nork.system.model.small
 * @Description:系统管理-数据字典
 * @createAuthor pandajun 
 * @CreateDate 2015-05-26 11:45:04
 * @version V1.0   
 */
@Data
public class SysDictionarySmall implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
	/**  类型  **/
	private String type;
	/**  唯一标示  **/
	private String valuekey;
	/**  值  **/
	private Integer value;
	/**  名称  **/
	private String name;
	/**  排序  **/
	private Integer ordering;
}
