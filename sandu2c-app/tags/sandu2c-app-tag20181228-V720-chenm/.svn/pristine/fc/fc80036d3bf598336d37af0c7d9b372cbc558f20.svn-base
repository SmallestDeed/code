package com.sandu.design.model.unity;

import net.sf.json.JSONArray;

import java.io.Serializable;

public class RoomConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 根节点
     **/
    private String rootName;
    /**
     * 当前挂节点相对于根节点的路径
     **/
    private String posIndexPath;
    /**
     * 默认挂载的产品编码
     **/
    private String itemCode;
    /**
     * 产品扩展信息
     **/
    private ItemExt itemExt;
    /**
     * 当前节点的挂节点名称
     **/
    private String posName;
    /**
     * 预留，更换次数
     **/
    private int changeTimeCount;
    /**
     * 观察相机的路径，相对于根节点room
     **/
    private String cameraIndexPath;
    /**
     * 记录远景的时候，在哪几个眼睛视角下需要隐藏或者显示半模
     **/
    private String[] lookPoint;
    /**
     * 挂节点位置信息
     **/
    private LinkPosition linkPosition;
    /**
     * 挂节点缩放信息
     **/
    private LinkScale linkScale;
    /**
     * 挂节点旋转信息
     **/
    private LinkQua linkQua;
    /** 绑定点信息 **/
//	private List<BindPointDataEx> bindPointDataEx;
    /**
     * 绑定点信息
     **/
    private JSONArray bindPointDataEx;
    /**
     * 绑定点字符串
     **/
    private String bindParentObjStr;
    /**
     * 设计方案产品ID
     **/
    private Integer planProductId;

    public JSONArray getBindPointDataEx() {
        return bindPointDataEx;
    }

    public void setBindPointDataEx(JSONArray bindPointDataEx) {
        this.bindPointDataEx = bindPointDataEx;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getPosIndexPath() {
        return posIndexPath;
    }

    public void setPosIndexPath(String posIndexPath) {
        this.posIndexPath = posIndexPath;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public ItemExt getItemExt() {
        return itemExt;
    }

    public void setItemExt(ItemExt itemExt) {
        this.itemExt = itemExt;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public int getChangeTimeCount() {
        return changeTimeCount;
    }

    public void setChangeTimeCount(int changeTimeCount) {
        this.changeTimeCount = changeTimeCount;
    }

    public String getCameraIndexPath() {
        return cameraIndexPath;
    }

    public void setCameraIndexPath(String cameraIndexPath) {
        this.cameraIndexPath = cameraIndexPath;
    }

    public String[] getLookPoint() {
        return lookPoint;
    }

    public void setLookPoint(String[] lookPoint) {
        this.lookPoint = lookPoint;
    }

    public LinkPosition getLinkPosition() {
        return linkPosition;
    }

    public void setLinkPosition(LinkPosition linkPosition) {
        this.linkPosition = linkPosition;
    }

    public LinkScale getLinkScale() {
        return linkScale;
    }

    public void setLinkScale(LinkScale linkScale) {
        this.linkScale = linkScale;
    }

    public LinkQua getLinkQua() {
        return linkQua;
    }

    public void setLinkQua(LinkQua linkQua) {
        this.linkQua = linkQua;
    }

    public String getBindParentObjStr() {
        return bindParentObjStr;
    }

    public void setBindParentObjStr(String bindParentObjStr) {
        this.bindParentObjStr = bindParentObjStr;
    }

    public Integer getPlanProductId() {
        return planProductId;
    }

    public void setPlanProductId(Integer planProductId) {
        this.planProductId = planProductId;
    }
}
