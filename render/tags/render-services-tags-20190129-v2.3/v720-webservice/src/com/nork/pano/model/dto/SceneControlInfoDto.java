package com.nork.pano.model.dto;

/**
 * Created by chenm on 2018/12/15.
 */
public class SceneControlInfoDto {

    /** 控制场景中功能按钮显示的值 1,2,3,4,5**/
    private Integer viewControlType;
    /** 用户是否需要购买方案版权:0.不需要;1.需要 **/
    private Integer copyRightPermission = 0;
    /** 判断是否已购买方案.0.未购买 1.已购买 **/
    private Integer havePurchased;

    public Integer getViewControlType() {
        return viewControlType;
    }

    public void setViewControlType(Integer viewControlType) {
        this.viewControlType = viewControlType;
    }

    public Integer getCopyRightPermission() {
        return copyRightPermission;
    }

    public void setCopyRightPermission(Integer copyRightPermission) {
        this.copyRightPermission = copyRightPermission;
    }

    public Integer getHavePurchased() {
        return havePurchased;
    }

    public void setHavePurchased(Integer havePurchased) {
        this.havePurchased = havePurchased;
    }
}
