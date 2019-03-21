package com.nork.pano.model.scene;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2017/7/1.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cube")
public class ImageCube implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlAttribute(name = "url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
