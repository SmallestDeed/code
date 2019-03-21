/**    
 * 文件名：Group.java    
 *    
 * 版本信息：    
 * 日期：2017年8月4日    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.design.model;

import java.io.Serializable;
import java.util.List;

/**    
 *     
 * 项目名称：timeSpace    
 * 类名称：Group    
 * 类描述：    
 * 创建人：Timy.Liu   
 * 创建时间：2017年8月4日 下午2:06:09    
 * 修改人：Timy.Liu    
 * 修改时间：2017年8月4日 下午2:06:09    
 * 修改备注：    
 * @version     
 *     
 */
public class Group implements Serializable {

    /**    
     * serialVersionUID:TODO 方法描述：   
     *    
     * @since Ver 1.1    
     */    
    
    private static final long serialVersionUID = 3748322485628181726L;
    /**
     * 720全景list
     */
    List<GroupDetail> list;
    /**
     * 分享封面图 
     */
    private String thumbPath;
    public List<GroupDetail> getList() {
        return list;
    }
    public void setList(List<GroupDetail> list) {
        this.list = list;
    }
    public String getThumbPath() {
        return thumbPath;
    }
    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }
    

}
