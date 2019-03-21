package com.sandu.api.category.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class CategoryTreeNode implements Serializable {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 分类编码
     */
    private String code;
    /**
     * 父节点
     */
    private Integer pid;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类等级
     */
    private Integer level;
    /**
     * 子节点
     */
    private List<CategoryTreeNode> children;
    /**
     * 分类下产品类型(1.模型产品/2.贴图产品)
     */
    private Integer categoryType;

    private String parentName;
}
