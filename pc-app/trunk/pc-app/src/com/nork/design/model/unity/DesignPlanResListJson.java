package com.nork.design.model.unity;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 8:28 2018/12/4 0004
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import java.io.Serializable;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/12/4 0004PM 8:28
 */
public class DesignPlanResListJson implements Serializable{
    private String designPlanResList;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDesignPlanResList() {
        return designPlanResList;
    }

    public void setDesignPlanResList(String designPlanResList) {
        this.designPlanResList = designPlanResList;
    }
}
