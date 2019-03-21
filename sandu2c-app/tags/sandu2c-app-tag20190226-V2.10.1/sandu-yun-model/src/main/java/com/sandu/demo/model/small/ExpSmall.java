package com.sandu.demo.model.small;

import java.io.Serializable;
import java.util.Date;

/**
 * @version V1.0
 * @Title: Exp.java
 * @Package com.sandu.demo.model.small
 * @Description:演示模块-参考例子
 * @createAuthor pandajun
 * @CreateDate 2015-05-17 20:11:49
 */
public class ExpSmall implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * 字符串属性
     **/
    private String strAtt;
    /**
     * 整数属性
     **/
    private Integer intAtt;
    /**
     * 数字属性
     **/
    private Double doubleAtt;
    /**
     * 时间属性
     **/
    private Date dateAtt;
    /**
     * 大文本属性
     **/
    private String htmlAtt;


    public String getStrAtt() {
        return strAtt;
    }

    public void setStrAtt(String strAtt) {
        this.strAtt = strAtt;
    }

    public Integer getIntAtt() {
        return intAtt;
    }

    public void setIntAtt(Integer intAtt) {
        this.intAtt = intAtt;
    }

    public Double getDoubleAtt() {
        return doubleAtt;
    }

    public void setDoubleAtt(Double doubleAtt) {
        this.doubleAtt = doubleAtt;
    }

    public Date getDateAtt() {
        return dateAtt;
    }

    public void setDateAtt(Date dateAtt) {
        this.dateAtt = dateAtt;
    }

    public String getHtmlAtt() {
        return htmlAtt;
    }

    public void setHtmlAtt(String htmlAtt) {
        this.htmlAtt = htmlAtt;
    }

}
