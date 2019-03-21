package com.sandu.api.user.model.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserMenuTreeBO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     **/
    private Long id;
   
    
    private String code;
    
    public UserMenuTreeBO(Long id, String code, String name, String url) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.url = url;
	}

	/**
     * 名称
     **/
    private String name;
    /**
     * 访问url
     **/
    private String url;
    
    private List<UserMenuTreeBO> subMenuList = new ArrayList<UserMenuTreeBO>();


}
