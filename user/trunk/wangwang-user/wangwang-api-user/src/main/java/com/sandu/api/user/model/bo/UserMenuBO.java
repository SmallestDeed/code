package com.sandu.api.user.model.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserMenuBO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     **/
    private Long id;
    /**
     * pid
     **/
    private Long parentid;
    
    private Integer levelId;
    
    private String code;
    /**
     * 名称
     **/
    private String name;
    /**
     * 访问url
     **/
    private String url;
    /**
     * 关键字 (user:add,user:update...)
     **/
    private String keyword;
    /**
     * 类型
     **/
    private String type;

    /**
     * 排序
     */
    private Integer sequence;
    
    
    
    public UserMenuBO(Long id, Long parentid, String name, String url, String keyword, String type,Integer levelId, String code,Integer sequence) {
        super();
        this.id = id;
        this.parentid = parentid;
        this.name = name;
        this.url = url;
        this.keyword = keyword;
        this.type = type;
        this.levelId = levelId;
        this.code = code;
        this.sequence =sequence;
    }


}
