package com.sandu.api.company.common;

import com.sandu.commons.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/6/2 0002 14:58
 * @Modified By
 */
public class StringUtil {

    /**
     * 切割逗号,连接字符串，返回Integer List
     * @author chenqiang
     * @param str
     * @return List<Integer>
     * @date 2018/6/2 0002 15:06
     */
    public static List<Integer> getListByString(String str){
        List<Integer> idList = null;
        if(StringUtils.isNotEmpty(str)){
            idList = new ArrayList<>();
            String[] strList = str.split(",");
            for (String s : strList) {
                try {
                    if(StringUtils.isNotEmpty(s))
                        idList.add(Integer.parseInt(s));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return idList;
    }

    /**
    * 切割逗号,连接字符串，返回String List
    * @author : WangHaiLin
    * @date : 2018/7/24 17:52
    * @param str 带有逗号的字符串
    * @return java.util.List<java.lang.String>
    *
    */
    public static List<String> getStringListByString(String str){
        List<String> idList = null;
        if(StringUtils.isNotEmpty(str)){
            idList = new ArrayList<>();
            String[] strList = str.split(",");
            for (String s : strList) {
                try {
                    if(StringUtils.isNotEmpty(s))
                        idList.add(s);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return idList;
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

    /**
     * 字符串 去除 空格
     * @author chenqiang
     * @param str 字符串
     * @return 字符串
     */
    public static String getStringTrim(String str){
        String resultStr = "";
        if(StringUtils.isNotEmpty(str)){
            resultStr = str.trim();
        }
        return resultStr;
    }

    /**
     * 号码格式 格式校验
     * @author chenqiang
     * @param phone 字符串
     * @param type 1:可以座机、2：只能手机
     * @return 是否成功：true
     */
    public static boolean checkPhoneRegex(String phone,String type){
        boolean bool = true;
        String regexPhone = "";
        if("1".equals(type)){
            regexPhone = "^\\b800-[0-9]{6,7}\\b|\\b400-[0-9]{6,7}\\b|\\b((0[0-9]{2,3}-)([1-9][0-9]{6,7}))\\b|\\b(((13[0-9])|(14[5|7])|(15([0-9]))|(17[0-3,5-8])|(18[0-9])|166|198|199)\\d{8}\\b)$";
        }else{
            regexPhone = "^((13[0-9])|(14[5|7])|(15([0-9]))|(17[0-3,5-8])|(18[0-9])|166|198|199)\\d{8}$";
        }

        if(StringUtils.isNotEmpty(phone)){
            // 编译正则表达式
            Pattern pattern = Pattern.compile(regexPhone);
            // 忽略大小写的写法
            // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(phone);
            // 字符串是否与正则表达式相匹配
            bool = matcher.matches();
        }
        return bool;
    }
}
