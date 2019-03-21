package com.sandu.rendermachine.model.product;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**   
 * @Title: ProCategorySearch.java 
 * @Package com.nork.product.model
 * @Description:产品模块-产品类目查询对象
 * @createAuthor pandajun 
 * @CreateDate 2015-06-17 14:46:36
 * @version V1.0   
 */
@Data
public class ProCategorySearch extends  ProCategory implements Serializable{
private static final long serialVersionUID = 1L;
	/**  系统编码-模糊查询  **/
	private String sch_SysCode_;
	/**  系统编码-左模糊查询  **/
	private String sch_SysCode;
	/**  系统编码-右模糊查询  **/
	private String schSysCode_;
	/**  系统编码-区间查询-开始字符串  **/
	private String sysCodeStart;
	/**  系统编码-区间查询-结束字符串  **/
	private String sysCodeEnd;
	/**  创建者-模糊查询  **/
	private String sch_Creator_;
	/**  创建者-左模糊查询  **/
	private String sch_Creator;
	/**  创建者-右模糊查询  **/
	private String schCreator_;
	/**  创建者-区间查询-开始字符串  **/
	private String creatorStart;
	/**  创建者-区间查询-结束字符串  **/
	private String creatorEnd;
	/**  创建时间-区间查询-开始时间  **/
	private Date gmtCreateStart;
	/**  创建时间-区间查询-结束时间  **/
	private Date gmtCreateEnd;
	/**  修改人-模糊查询  **/
	private String sch_Modifier_;
	/**  修改人-左模糊查询  **/
	private String sch_Modifier;
	/**  修改人-右模糊查询  **/
	private String schModifier_;
	/**  修改人-区间查询-开始字符串  **/
	private String modifierStart;
	/**  修改人-区间查询-结束字符串  **/
	private String modifierEnd;
	/**  修改时间-区间查询-开始时间  **/
	private Date gmtModifiedStart;
	/**  修改时间-区间查询-结束时间  **/
	private Date gmtModifiedEnd;
	/**  产品分类编码-模糊查询  **/
	private String sch_Code_;
	/**  产品分类编码-左模糊查询  **/
	private String sch_Code;
	/**  产品分类编码-右模糊查询  **/
	private String schCode_;
	/**  产品分类编码-区间查询-开始字符串  **/
	private String codeStart;
	/**  产品分类编码-区间查询-结束字符串  **/
	private String codeEnd;
	/**  产品分类长编码-模糊查询  **/
	private String sch_LongCode_;
	/**  产品分类长编码-左模糊查询  **/
	private String sch_LongCode;
	/**  产品分类长编码-右模糊查询  **/
	private String schLongCode_;
	/**  产品分类长编码-区间查询-开始字符串  **/
	private String longCodeStart;
	/**  产品分类长编码-区间查询-结束字符串  **/
	private String longCodeEnd;
	/**  名称-模糊查询  **/
	private String sch_Name_;
	/**  名称-左模糊查询  **/
	private String sch_Name;
	/**  名称-右模糊查询  **/
	private String schName_;
	/**  名称-区间查询-开始字符串  **/
	private String nameStart;
	/**  名称-区间查询-结束字符串  **/
	private String nameEnd;
	/**  字符备用1-模糊查询  **/
	private String sch_Att1_;
	/**  字符备用1-左模糊查询  **/
	private String sch_Att1;
	/**  字符备用1-右模糊查询  **/
	private String schAtt1_;
	/**  字符备用1-区间查询-开始字符串  **/
	private String att1Start;
	/**  字符备用1-区间查询-结束字符串  **/
	private String att1End;
	/**  字符备用2-模糊查询  **/
	private String sch_Att2_;
	/**  字符备用2-左模糊查询  **/
	private String sch_Att2;
	/**  字符备用2-右模糊查询  **/
	private String schAtt2_;
	/**  字符备用2-区间查询-开始字符串  **/
	private String att2Start;
	/**  字符备用2-区间查询-结束字符串  **/
	private String att2End;
	/**  字符备用3-模糊查询  **/
	private String sch_Att3_;
	/**  字符备用3-左模糊查询  **/
	private String sch_Att3;
	/**  字符备用3-右模糊查询  **/
	private String schAtt3_;
	/**  字符备用3-区间查询-开始字符串  **/
	private String att3Start;
	/**  字符备用3-区间查询-结束字符串  **/
	private String att3End;
	/**  字符备用4-模糊查询  **/
	private String sch_Att4_;
	/**  字符备用4-左模糊查询  **/
	private String sch_Att4;
	/**  字符备用4-右模糊查询  **/
	private String schAtt4_;
	/**  字符备用4-区间查询-开始字符串  **/
	private String att4Start;
	/**  字符备用4-区间查询-结束字符串  **/
	private String att4End;
	/**  字符备用5-模糊查询  **/
	private String sch_Att5_;
	/**  字符备用5-左模糊查询  **/
	private String sch_Att5;
	/**  字符备用5-右模糊查询  **/
	private String schAtt5_;
	/**  字符备用5-区间查询-开始字符串  **/
	private String att5Start;
	/**  字符备用5-区间查询-结束字符串  **/
	private String att5End;
	/**  字符备用6-模糊查询  **/
	private String sch_Att6_;
	/**  字符备用6-左模糊查询  **/
	private String sch_Att6;
	/**  字符备用6-右模糊查询  **/
	private String schAtt6_;
	/**  字符备用6-区间查询-开始字符串  **/
	private String att6Start;
	/**  字符备用6-区间查询-结束字符串  **/
	private String att6End;
	/**  时间备用1-区间查询-开始时间  **/
	private Date dateAtt1Start;
	/**  时间备用1-区间查询-结束时间  **/
	private Date dateAtt1End;
	/**  时间备用2-区间查询-开始时间  **/
	private Date dateAtt2Start;
	/**  时间备用2-区间查询-结束时间  **/
	private Date dateAtt2End;
	/**  备注-模糊查询  **/
	private String sch_Remark_;
	/**  备注-左模糊查询  **/
	private String sch_Remark;
	/**  备注-右模糊查询  **/
	private String schRemark_;
	/**  备注-区间查询-开始字符串  **/
	private String remarkStart;
	/**  备注-区间查询-结束字符串  **/
	private String remarkEnd;

}
