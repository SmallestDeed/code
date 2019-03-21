package com.sandu.web.imall;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    /**
     * null或空
     * @param val
     * @return
     */
    public static boolean IsNullOrEmpty(String val){
        if (val==null || val==""){
            return true;
        }else{
            return false;
        }
    }

    public static boolean IsNullOrEmpty(Object val){
        if (val==null || val==""){
            return true;
        }else{
            return false;
        }
    }

    public static int isNullOrEmptyInt(Object obj,int defValue){
        if(null != obj && !"".equals(obj)){
            return Integer.parseInt(obj.toString());
        }else{
            return defValue;
        }
    }
    
    /**
     * 路径 补全 方法
     * @author chenqiang
     * @param filePath
     * @param date
     * @return
     */
    public static String replaceDate(String filePath, Date date) {
        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String dateInfo = simpleDateFormat.format(date);
        String[] dateInfoArrays = dateInfo.split("_");
        if (filePath.indexOf("[yyyy]") != -1) {
            filePath = filePath.replace("[yyyy]", dateInfoArrays[0]);
        }

        if (filePath.indexOf("[MM]") != -1) {
            filePath = filePath.replace("[MM]", dateInfoArrays[1]);
        }

        if (filePath.indexOf("[dd]") != -1) {
            filePath = filePath.replace("[dd]", dateInfoArrays[2]);
        }

        if (filePath.indexOf("[HH]") != -1) {
            filePath = filePath.replace("[HH]", dateInfoArrays[3]);
        }

        if (filePath.indexOf("[mm]") != -1) {
            filePath = filePath.replace("[mm]", dateInfoArrays[4]);
        }

        if (filePath.indexOf("[ss]") != -1) {
            filePath = filePath.replace("[ss]", dateInfoArrays[5]);
        }

        return filePath;
    }


}
