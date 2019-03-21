package com.nork.pano.model.scene;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2017/7/1.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "image")
public class Image implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  **/
    @XmlElement(name = "cube")
    private ImageCube cube;

    /**  **/
    @XmlElement(name = "sphere")
    private ImageSphere sphere;

    public ImageCube getCube() {
        return cube;
    }

    public void setCube(ImageCube cube) {
        this.cube = cube;
    }

    public ImageSphere getSphere() {
        return sphere;
    }

    public void setSphere(ImageSphere sphere) {
        this.sphere = sphere;
    }
}
