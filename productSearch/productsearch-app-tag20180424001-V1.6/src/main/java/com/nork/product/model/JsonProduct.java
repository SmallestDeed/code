package com.nork.product.model;

import net.sf.json.JSONArray;

/**
 * Created by Administrator on 2015/11/4.
 */
public class JsonProduct {

    /** 根节点 **/
    private String rootName;
    /** 默认挂载的产品编码 **/
    private String itemCode;
    /** 当前挂节点相对于根节点的路径 **/
    private String posIndexPath;
    /** 当前节点的挂节点名称 **/
    private String posName;
    /** 预留，更换次数 **/
    private Integer changeTimeCount;
    /** 观察相机的路径，相对于根节点room **/
    private String cameraIndexPath;
    /** 挂节点位置信息 **/
    private LinkPosition linkPosition;
    /** 挂节点缩放信息 **/
    private LinkScale linkScale;
    /** 挂节点旋转信息 **/
    private LinkQua linkQua;
    /** 绑定点信息 **/
    private JSONArray bindPointDataEx;
    /** 绑定点字符串 **/
    private String bindParentObjStr;
    /** 结构编码 **/
    private String structCode;
    /** 结构产品编码 **/
    private String structureProductCode;
    //初始化白膜产品编码
    private String initProductCode;
    //初始化白膜产品挂节点
    private String initPosName;
    /*方案组合标识*/
    private String planGroupId;

    public String getPlanGroupId() {
        return planGroupId;
    }

    public void setPlanGroupId(String planGroupId) {
        this.planGroupId = planGroupId;
    }

    public String getStructureProductCode() {
        return structureProductCode;
    }

    public void setStructureProductCode(String structureProductCode) {
        this.structureProductCode = structureProductCode;
    }

    public String getInitProductCode() {
        return initProductCode;
    }

    public void setInitProductCode(String initProductCode) {
        this.initProductCode = initProductCode;
    }

    public String getInitPosName() {
        return initPosName;
    }

    public void setInitPosName(String initPosName) {
        this.initPosName = initPosName;
    }

    public String getStructCode() {
        return structCode;
    }

    public void setStructCode(String structCode) {
        this.structCode = structCode;
    }

    public JSONArray getBindPointDataEx() {
        return bindPointDataEx;
    }

    public void setBindPointDataEx(JSONArray bindPointDataEx) {
        this.bindPointDataEx = bindPointDataEx;
    }

    public String getBindParentObjStr() {
        return bindParentObjStr;
    }

    public void setBindParentObjStr(String bindParentObjStr) {
        this.bindParentObjStr = bindParentObjStr;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getPosIndexPath() {
        return posIndexPath;
    }

    public void setPosIndexPath(String posIndexPath) {
        this.posIndexPath = posIndexPath;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public Integer getChangeTimeCount() {
        return changeTimeCount;
    }

    public void setChangeTimeCount(Integer changeTimeCount) {
        this.changeTimeCount = changeTimeCount;
    }

    public String getCameraIndexPath() {
        return cameraIndexPath;
    }

    public void setCameraIndexPath(String cameraIndexPath) {
        this.cameraIndexPath = cameraIndexPath;
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
}
