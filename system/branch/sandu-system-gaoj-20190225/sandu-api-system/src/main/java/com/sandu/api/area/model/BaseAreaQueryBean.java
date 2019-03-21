/**    
 * 文件名：BaseAreaQueryBean.java    
 *    
 * 版本信息：    
 * 日期：2017年8月23日    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.sandu.api.area.model;


import com.sandu.commons.util.StringUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 项目名称：mgt 类名称：BaseAreaQueryBean 类描述： 创建人：Timy.Liu 创建时间：2017年8月23日 下午3:45:25
 * 修改人：Timy.Liu 修改时间：2017年8月23日 下午3:45:25 修改备注：
 * 
 * @version
 * 
 */
@Data
public class BaseAreaQueryBean implements Serializable {

    /**    
     * serialVersionUID:TODO 方法描述：   
     *    
     * @since Ver 1.1    
     */    
    
    private static final long serialVersionUID = 9172963891098707840L;

    /**
     * serialVersionUID:TODO 方法描述：
     * 
     * @since Ver 1.1
     */

    private String proviceCode;

    private String cityCode;

    private String areaCode;

    public BaseAreaQueryBean(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getProviceCode() {
        if (StringUtils.isNotEmpty(this.areaCode) && this.areaCode.length() == 6) {
            proviceCode = this.areaCode.substring(0, 2) + "0000";
        }
        return proviceCode;
    }

    public void setProviceCode(String proviceCode) {
        this.proviceCode = proviceCode;
    }

    public String getCityCode() {
        if (StringUtils.isNotEmpty(this.areaCode) && this.areaCode.length() == 6) {
            cityCode = this.areaCode.substring(0, 4) + "00";
        }
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
}
