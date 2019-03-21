package com.sandu.common.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by kono on 2018/5/30 0030.
 */
public class Utils {


    /**
     * str->List<Integer>
     *
     * @param str
     * @return
     */
    public static List<Integer> getIntegerListFromString(String str) {
        List<Integer> list = new ArrayList<Integer>();
        if (StringUtils.isEmpty(str) || "null".equals(str))
            return list;
        String[] strs = str.split(",");
        for (String idStr : strs) {
            if (StringUtils.isEmpty(idStr)) {
                continue;
            }
            list.add(Integer.parseInt(idStr));
        }
        return list;
    }

    /**
     * 读取文本文件中的文本信息
     * @param filePath
     * @return
     */
    public static String getFileContext(String filePath){
        StringBuffer sb = new StringBuffer();
        try {
            if( !new File(filePath).exists() ){
                return sb.toString();
            }
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
            String str = "";
            while( ( str = br.readLine() ) != null ){
                sb.append(str);
                sb.append("\r\n");
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        String mobile = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        return Pattern.compile(mobile).matcher(str).find();
    }

}
