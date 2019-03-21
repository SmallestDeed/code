package com.nork.pano.model.scene.hotspot;

import javax.persistence.Transient;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "hotspot")
public class Hotspot {

    /** 热点名称 **/
    @XmlAttribute(name = "name")
    private String name;
    /** 热点类型 **/
    @XmlAttribute(name = "type")
    private String type = "image";
    /** 热点显示图片 **/
    @XmlAttribute(name = "url")
    private String url;
    /** 载入样式标签的名称 **/
    @XmlAttribute(name = "style")
    private String style;
    /** 热点的宽 **/
    @XmlAttribute(name = "width")
    private String width;
    /** 热点的高 **/
    @XmlAttribute(name = "height")
    private String height;
    /** 是否在跳转下一场景后保持显示 **/
    @XmlAttribute(name = "keep")
    private String keep = "false";
    /** 支持设备类型 **/
    @XmlAttribute(name = "devices")
    private String devices;
    /** 是否可见 **/
    @XmlAttribute(name = "visible")
    private String visible;
    /** 鼠标移到热点上显示小手 **/
    @XmlAttribute(name = "handCursor")
    private String handCursor;

    @XmlAttribute(name = "ath")
    private String ath;

    @XmlAttribute(name = "atv")
    private String atv;
    /** 跳转的场景name **/
    @XmlAttribute(name = "linkedscene")
    private String linkedScene;
    /** 点击事件 **/
    @XmlAttribute(name = "onclick")
    private String onClick;
    /** 加载事件 **/
    @XmlAttribute(name = "onloaded")
    private String onLoaded;
    /** 显示的文字 **/
    @XmlAttribute(name = "text")
    private String text;
    @XmlTransient
    private double distance;

    @XmlElements(value = {@XmlElement(name = "point",type = Point.class)})
    private List<Point> pointList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getKeep() {
        return keep;
    }

    public void setKeep(String keep) {
        this.keep = keep;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getHandCursor() {
        return handCursor;
    }

    public void setHandCursor(String handCursor) {
        this.handCursor = handCursor;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAth() {
        return ath;
    }

    public void setAth(String ath) {
        this.ath = ath;
    }

    public String getAtv() {
        return atv;
    }

    public void setAtv(String atv) {
        this.atv = atv;
    }

    public String getLinkedScene() {
        return linkedScene;
    }

    public void setLinkedScene(String linkedScene) {
        this.linkedScene = linkedScene;
    }

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public String getOnLoaded() {
        return onLoaded;
    }

    public void setOnLoaded(String onLoaded) {
        this.onLoaded = onLoaded;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
