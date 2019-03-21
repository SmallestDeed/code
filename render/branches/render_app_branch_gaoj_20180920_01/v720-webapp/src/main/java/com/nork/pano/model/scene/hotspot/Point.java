package com.nork.pano.model.scene.hotspot;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2017/7/4.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "point")
public class Point implements Serializable {
	private static final long serialVersionUID = 1L;

    @XmlAttribute(name = "ath")
    private String ath;
    @XmlAttribute(name = "atv")
    private String atv;

}
