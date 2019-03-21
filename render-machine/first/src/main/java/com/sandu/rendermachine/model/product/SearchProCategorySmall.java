package com.sandu.rendermachine.model.product;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: ProCategory.java
 * @Package com.nork.product.model.small
 * @createAuthor zhangwj
 * @CreateDate 2015-07-07 11:20
 * @version V1.0
 */
@Data
public class SearchProCategorySmall implements Serializable {

    private Integer aa;

    private String bb;

    private Integer cc;

    private String dd;

    private Integer ee;

    private List<SearchProCategorySmall> ff;
    /*名字首字母*/
	private String gg ;

}
