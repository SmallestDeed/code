package com.nork.cityunion.model.vo;

import java.io.Serializable;

/**
 * 联盟同城门店分组详情
 * @author 陈名
 * 2018/1/18
 */
public class UnionGroupDetailsVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**  组成员名称  **/
    private String name;
    
    /**  电话  **/
    private String phone;
    
    /**  地址  **/
    private String address;
  
    public String getName() {
      return name;
    }
  
    public void setName(String name) {
      this.name = name;
    }
  
    public String getPhone() {
      return phone;
    }
  
    public void setPhone(String phone) {
      this.phone = phone;
    }
  
    public String getAddress() {
      return address;
    }
  
    public void setAddress(String address) {
      this.address = address;
    }
    
  
}
