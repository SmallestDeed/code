package com.sandu.pano.model.scene;

import com.sandu.pano.model.scene.hotspot.Hotspot;

import javax.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Administrator on 2017/7/1.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "scene")
public class Scene implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 设计方案ID
     **/
    @XmlAttribute(name = "planId")
    private Integer planId;
    /**  **/
    @XmlAttribute(name = "name")
    private String name;
    /**  **/
    @XmlAttribute(name = "title")
    private String title;
    /**  **/
    @XmlAttribute(name = "onstart")
    private String onStart;
    /**  **/
    @XmlAttribute(name = "thumburl")
    private String thumbUrl;
    /**  **/
    @XmlAttribute(name = "lat")
    private String lat;
    /**  **/
    @XmlAttribute(name = "lng")
    private String lng;
    /**  **/
    @XmlAttribute(name = "heading")
    private String heading;
    /**  **/
    @XmlElement(name = "view")
    private View view;
    /**  **/
    @XmlElement(name = "preview")
    private Preview preview;
    /**  **/
    @XmlElement(name = "image")
    private Image image;
    /**  **/
    @XmlElements(value = {@XmlElement(name = "hotspot", type = Hotspot.class)})
    private List<Hotspot> hotspotList;
    /**
     * 窗口占比
     **/
    @XmlTransient
    private String windowsPercent;

    /**
     * 副本id
     */
    private Integer designPlanRenderSceneId;

    /**
     * 推荐方案id
     */
    private Integer designPlanRecommendedId;

    public Integer getDesignPlanRenderSceneId() {
        return designPlanRenderSceneId;
    }

    public void setDesignPlanRenderSceneId(Integer designPlanRenderSceneId) {
        this.designPlanRenderSceneId = designPlanRenderSceneId;
    }

    public Integer getDesignPlanRecommendedId() {
        return designPlanRecommendedId;
    }

    public void setDesignPlanRecommendedId(Integer designPlanRecommendedId) {
        this.designPlanRecommendedId = designPlanRecommendedId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOnStart() {
        return onStart;
    }

    public void setOnStart(String onStart) {
        this.onStart = onStart;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Hotspot> getHotspotList() {
        return hotspotList;
    }

    public void setHotspotList(List<Hotspot> hotspotList) {
        this.hotspotList = hotspotList;
    }

    public String getWindowsPercent() {
        return windowsPercent;
    }

    public void setWindowsPercent(String windowsPercent) {
        this.windowsPercent = windowsPercent;
    }

	@Override
	public String toString() {
		return "Scene [planId=" + planId + ", name=" + name + ", title=" + title + ", onStart=" + onStart
				+ ", thumbUrl=" + thumbUrl + ", lat=" + lat + ", lng=" + lng + ", heading=" + heading + ", view=" + view
				+ ", preview=" + preview + ", image=" + image + ", hotspotList=" + hotspotList + ", windowsPercent="
				+ windowsPercent + ", designPlanRenderSceneId=" + designPlanRenderSceneId + ", designPlanRecommendedId="
				+ designPlanRecommendedId + "]";
	}
    
}
