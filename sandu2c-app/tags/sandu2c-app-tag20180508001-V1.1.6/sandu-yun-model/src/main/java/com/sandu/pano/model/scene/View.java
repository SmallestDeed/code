package com.sandu.pano.model.scene;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2017/7/1.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "view")
public class View implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
    @XmlAttribute(name = "hlookat")
    private String hlookat;
    /**  **/
    @XmlAttribute(name = "vlookat")
    private String vlookat;
    /**  **/
    @XmlAttribute(name = "fovtype")
    private String fovType;
    /**  **/
    @XmlAttribute(name = "fov")
    private String fov;
    /**  **/
    @XmlAttribute(name = "maxpixelzoom")
    private String maxPixelZoom;
    /**  **/
    @XmlAttribute(name = "fovmin")
    private String fovMin;
    /**  **/
    @XmlAttribute(name = "fovmax")
    private String fovMax;
    /**  **/
    @XmlAttribute(name = "limitview")
    private String limitView;

    /**
     * 默认配置
     *
     * @return
     */
    public static View getDefaultView() {
        View view = new View();
        view.setHlookat("0");
        view.setVlookat("0");
        view.setFovType("MFOV");
        view.setFov("120");
        view.setFovMin("90");
        view.setFovMax("130");
        view.setLimitView("auto");
        return view;
    }

    public String getHlookat() {
        return hlookat;
    }

    public void setHlookat(String hlookat) {
        this.hlookat = hlookat;
    }

    public String getVlookat() {
        return vlookat;
    }

    public void setVlookat(String vlookat) {
        this.vlookat = vlookat;
    }

    public String getFovType() {
        return fovType;
    }

    public void setFovType(String fovType) {
        this.fovType = fovType;
    }

    public String getFov() {
        return fov;
    }

    public void setFov(String fov) {
        this.fov = fov;
    }

    public String getMaxPixelZoom() {
        return maxPixelZoom;
    }

    public void setMaxPixelZoom(String maxPixelZoom) {
        this.maxPixelZoom = maxPixelZoom;
    }

    public String getFovMin() {
        return fovMin;
    }

    public void setFovMin(String fovMin) {
        this.fovMin = fovMin;
    }

    public String getFovMax() {
        return fovMax;
    }

    public void setFovMax(String fovMax) {
        this.fovMax = fovMax;
    }

    public String getLimitView() {
        return limitView;
    }

    public void setLimitView(String limitView) {
        this.limitView = limitView;
    }
}
