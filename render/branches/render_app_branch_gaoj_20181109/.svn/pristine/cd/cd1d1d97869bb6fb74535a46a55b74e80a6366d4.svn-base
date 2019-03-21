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
    /**  **/
    @XmlAttribute(name = "vlookatmin")
    private String vlookatmin;
    /**  **/
    @XmlAttribute(name = "vlookatmax")
    private String vlookatmax;
    

    /**
     * 默认配置
     * @return
     */
    public static View getDefaultView(){
        View view = new View();
        view.setHlookat("0");
        view.setVlookat("0");
        view.setFovType("MFOV");
        view.setFov("90");
        view.setMaxPixelZoom("0");
        view.setFovMin("70");
        view.setFovMax("100");
        view.setVlookatmin("-90");
        view.setVlookatmax("70");
        view.setLimitView("range");
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

	public String getVlookatmin() {
		return vlookatmin;
	}

	public void setVlookatmin(String vlookatmin) {
		this.vlookatmin = vlookatmin;
	}

	public String getVlookatmax() {
		return vlookatmax;
	}

	public void setVlookatmax(String vlookatmax) {
		this.vlookatmax = vlookatmax;
	}
    
}
