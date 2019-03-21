package com.sandu.rendermachine.model.product;

import com.sandu.rendermachine.model.common.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @version V1.0
 * @Title: ProCategory.java
 * @Package com.nork.product.model
 * @Description:产品模块-产品类目
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:46:36
 */
@Data
public class ProCategory extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 系统编码
     **/
    private String sysCode;
    /**
     * 创建者
     **/
    private String creator;
    /**
     * 创建时间
     **/
    private Date gmtCreate;
    /**
     * 修改人
     **/
    private String modifier;
    /**
     * 修改时间
     **/
    private Date gmtModified;
    /**
     * 是否删除
     **/
    private Integer isDeleted;
    /**
     * 产品分类编码
     **/
    private String code;
    /**
     * 产品分类长编码
     **/
    private String longCode;
    /**
     * 父级
     **/
    private Integer pid;
    /**
     * 名称
     **/
    private String name;
    /**
     * 是否是子节点
     **/
    private Integer isLeaf;
    /**
     * 等级
     **/
    private Integer level;
    /**
     * 排序
     **/
    private Integer ordering;
    /**
     * 字符备用1
     **/
    private String state;
    /**
     * 字符备用2
     **/
    private String att2;
    /**
     * 字符备用3
     **/
    private String att3;
    /**
     * 字符备用4
     **/
    private String att4;
    /**
     * 字符备用5
     **/
    private String att5;
    /**
     * 字符备用6
     **/
    private String att6;
    /**
     * 时间备用1
     **/
    private Date dateAtt1;
    /**
     * 时间备用2
     **/
    private Date dateAtt2;
    /**
     * 整数备用1
     **/
    private Integer numAtt1;
    /**
     * 整数备用2
     **/
    private Integer numAtt2;
    /**
     * 数字备用1
     **/
    private Double numAtt3;
    /**
     * 数字备用2
     **/
    private Double numAtt4;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 子节点集合
     **/
    private List<SearchProCategorySmall> childrenNodes;
    /**
     * 名字首字母
     */
    private String nameFirstLetter;

}
