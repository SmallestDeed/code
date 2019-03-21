package com.nork.product.model.small;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: ProCategory.java
 * @Package com.nork.product.model.small
 * @createAuthor zhangwj
 * @CreateDate 2015-07-07 11:20
 * @version V1.0
 */
public class SearchProCategorySmall implements Serializable {

    private static final long serialVersionUID = 1L;

//    /**
//     * 分类ID
//     */
//    private Integer cid;
//    /**
//     * 分类code
//     */
//    private String categoryCode;
//    /**
//     * 父级ID
//     */
//    private Integer pid;
//    /**
//     * 分类名称
//     */
//    private String name;
//    /**
//     * 排序
//     */
//    private Integer ordering;
//    /**
//     * 子级分类集合
//     */
//    private List<SearchProCategorySmall> childrenNodes = new ArrayList<SearchProCategorySmall>();
//
//    public Integer getCid() {
//        return cid;
//    }
//
//    public void setCid(Integer cid) {
//        this.cid = cid;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getPid() {
//        return pid;
//    }
//
//    public void setPid(Integer pid) {
//        this.pid = pid;
//    }
//
//    public List<SearchProCategorySmall> getChildrenNodes() {
//        return childrenNodes;
//    }
//
//    public void setChildrenNodes(List<SearchProCategorySmall> childrenNodes) {
//        this.childrenNodes = childrenNodes;
//    }
//
//    public Integer getOrdering() {
//        return ordering;
//    }
//
//    public void setOrdering(Integer ordering) {
//        this.ordering = ordering;
//    }
//
//    public String getCategoryCode() {
//        return categoryCode;
//    }
//
//    public void setCategoryCode(String categoryCode) {
//        this.categoryCode = categoryCode;
//    }
    private Integer aa;

    private String bb;

    private Integer cc;

    private String dd;

    private Integer ee;

    private List<SearchProCategorySmall> ff;

    
    /*名字首字母*/
	private String gg ;
    
	//图片id
	private Integer picIds;
	//图片路径
	private String picPath;
	
	public Integer getPicIds() {
		return picIds;
	}

	public void setPicIds(Integer picIds) {
		this.picIds = picIds;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getGg() {
		return gg;
	}

	public void setGg(String gg) {
		this.gg = gg;
	}

	public Integer getAa() {
        return aa;
    }

    public void setAa(Integer aa) {
        this.aa = aa;
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb;
    }

    public Integer getCc() {
        return cc;
    }

    public void setCc(Integer cc) {
        this.cc = cc;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public Integer getEe() {
        return ee;
    }

    public void setEe(Integer ee) {
        this.ee = ee;
    }

    public List<SearchProCategorySmall> getFf() {
        return ff;
    }

    public void setFf(List<SearchProCategorySmall> ff) {
        this.ff = ff;
    }
}
