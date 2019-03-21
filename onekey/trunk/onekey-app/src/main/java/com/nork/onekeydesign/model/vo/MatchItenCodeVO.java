package com.nork.onekeydesign.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kono on 2017/12/26 0026.
 */
public class MatchItenCodeVO implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer designPlanId;

    private List<PosNameInfoVO> posNameInfoVOList;

    public Integer getDesignPlanId() {
        return designPlanId;
    }

    public void setDesignPlanId(Integer designPlanId) {
        this.designPlanId = designPlanId;
    }

    public List<PosNameInfoVO> getPosNameInfoVOList() {
        return posNameInfoVOList;
    }

    public void setPosNameInfoVOList(List<PosNameInfoVO> posNameInfoVOList) {
        this.posNameInfoVOList = posNameInfoVOList;
    }
}
