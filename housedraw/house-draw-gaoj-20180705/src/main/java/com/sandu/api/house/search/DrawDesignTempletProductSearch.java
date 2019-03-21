package com.sandu.api.house.search;

import java.io.Serializable;

import com.sandu.api.house.model.DrawDesignTempletProduct;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DrawDesignTempletProductSearch extends DrawDesignTempletProduct implements Serializable {

	private static final long serialVersionUID = 633396553357042279L;

	private Long start;

	private Long limit;
}
