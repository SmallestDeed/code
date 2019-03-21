package com.sandu.commons.constant;

import com.sandu.commons.Kav;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author chenqiang
 * @Description 企业类型与用户类型 对应 常量类（内部用户）
 * @Date 2018/6/23 0023 20:07
 * @Modified By
 */
public enum  BusinessTypeToUserTypeConstant implements Serializable{
    BUSINESSTYPE_COMPANY("1","2"),
    BUSINESSTYPE_FRANCHISER("2","3"),
    BUSINESSTYPE_STORE("3","15"),
    BUSINESSTYPE_DESIGN("4","5"),
    BUSINESSTYPE_DECORATION("5","5,13"),
    BUSINESSTYPE_DESIGNER("6","5"),
    BUSINESSTYPE_FOREMAN("7","5,13"),
    BUSINESSTYPE_FRANCHISER_D("9","14");

    private String BusinessType;
    private String userType;

    private BusinessTypeToUserTypeConstant(String key, String value) {
        this.BusinessType = key;
        this.userType = value;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    /**
     * 根据key获取value
     *
     * @param key : 键值key
     * @return String
     */
    public static String getValueByKey(String key) {
        BusinessTypeToUserTypeConstant[] enums = BusinessTypeToUserTypeConstant.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getBusinessType().equals(key)) {
                return enums[i].getUserType();
            }
        }
        return "";
    }

    /**
     * 转换为'KAV'列表
     *
     * @return List<Kav>
     */
    public static List<Kav> toKavs() {
        List<Kav> l_kavs = new ArrayList<Kav>();
        BusinessTypeToUserTypeConstant[] enums = BusinessTypeToUserTypeConstant.values();
        for (int i = 0; i < enums.length; i++) {
            Kav kav = new Kav();
            kav.setKey(enums[i].getBusinessType().toString());
            kav.setValue(enums[i].getUserType()+"");
            l_kavs.add(kav);
        }
        return l_kavs;
    }

    /**
     * 转换为MAP集合
     *
     * @returnMap<String, String>
     */
    public static Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        BusinessTypeToUserTypeConstant[] enums = BusinessTypeToUserTypeConstant.values();
        for (int i = 0; i < enums.length; i++) {
            map.put(enums[i].getBusinessType().toString(), enums[i].getUserType().toString());
        }
        return map;
    }

    //    1	    内部用户
    //    2	    厂商
    //    3	    经销商
    //    4	    设计公司
    //    5	    设计师
    //    6     装修公司
    //    7	    学校（培训机构）
    //    8	    普通用户
    //    9	    游客
    //    11	中介
    //    12	业主
    //    13	工长
    //    14	门店
    //    /**
    //     *厂商
    //     */
    //    public static int BUSINESS_TYPE_COMPANY = 1;
    //    /**
    //     *经销商
    //     */
    //    public static int BUSINESS_TYPE_FRANCHISER = 2;
    //    /**
    //     *门店
    //     */
    //    public static int BUSINESS_TYPE_STORE = 3;
    //    /**
    //     *设计公司
    //     */
    //    public static int BUSINESS_TYPE_DESIGN = 4;
    //    /**
    //     *装修公司
    //     */
    //    public static int BUSINESS_TYPE_DECORATION = 5;
    //
    //    /** 虚拟企业 设计师 */
    //    public static int BUSINESS_TYPE_DESIGNPENSON = 6;
    //    /** 虚拟企业  工长（施工单位） */
    //    public static int BUSINESS_TYPE_BUILD = 7;
}


