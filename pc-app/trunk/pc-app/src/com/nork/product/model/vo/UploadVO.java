package com.nork.product.model.vo;

import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/11/12 0012 15:47
 * @Modified By
 */
public class UploadVO implements Serializable {
    private static final long serialVersionUID = -4793489812964621870L;

    private String id;

    private String name;

    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
