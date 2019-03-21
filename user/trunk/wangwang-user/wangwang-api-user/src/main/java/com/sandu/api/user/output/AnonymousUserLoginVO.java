package com.sandu.api.user.output;

import java.io.Serializable;

import com.sandu.api.user.model.bo.UserMenuTreeBO;

import lombok.Data;
@Data
public class AnonymousUserLoginVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private UserMenuTreeBO menuTree;
}
