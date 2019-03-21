/**    
 * 文件名：BaseAreaQueryBean.java    
 *    
 * 版本信息：    
 * 日期：2017年8月23日    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.sandu.system.model.search;

import java.io.Serializable;

import com.sandu.system.model.BaseArea;
import org.apache.commons.lang3.StringUtils;


public class BaseAreaQueryBean implements Serializable {

    
    private static final long serialVersionUID = 9172963891098707840L;

    private String proviceCode;

    private String cityCode;

    private String areaCode;

    private String longCode;

    public BaseAreaQueryBean(String longCode){
        String[] codeArr = longCode.split("\\.");//按'.'分隔的时候要转义
        if(codeArr.length >= 1){
            this.proviceCode = codeArr[1];
        }
        if(codeArr.length >= 2){
            this.cityCode = codeArr[2];
        }
        if(codeArr.length >= 3){
            this.areaCode = codeArr[3];
        }
    }
  /*  public BaseAreaQueryBean(String areaCode) {

        this.areaCode = areaCode;
    }*/

    public String getProviceCode() {
      /*  if (StringUtils.isNotEmpty(this.areaCode) && this.areaCode.length() == 6) {
            proviceCode = this.areaCode.substring(0, 2) + "0000";
        }*/
        return proviceCode;
    }

    public void setProviceCode(String proviceCode) {
        this.proviceCode = proviceCode;
    }

    public String getCityCode() {
       /* if (StringUtils.isNotEmpty(this.areaCode) && this.areaCode.length() == 6) {
            cityCode = this.areaCode.substring(0, 4) + "00";
        }*/
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getLongCode() {
        return longCode;
    }

    public void setLongCode(String longCode) {
        this.longCode = longCode;
    }
}
