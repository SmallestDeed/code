package com.sandu.api.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.*;
import java.util.*;


public class Utils {
    public static final String DATE = "yyyy-MM-dd";
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME2 = "yyyy/MM/dd HH:mm:ss";
    public static final String DATETIME = "yyyyMMddHHmmss";
    public static final String DATETIMESSS = "yyyyMMddHHmmssSSS";
    public static final String DATE2 = "yyyyMMdd";
    public static final int OVERTIMESETUP = 2 * 60 * 60;

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /***
     * 获得几年后的时间
     * @param date
     * @param day
     * @return
     */
    public static Date getYearBefore(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) + day);
        return now.getTime();

    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public static Date getTimeBefore(Date d, int times) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - times);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getTimeAfter(Date d, int times) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + times);
        return now.getTime();
    }

    public static String getDateStr(Date date, String dateFormatStr) {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        String str = "";
        try {
            str = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 中文转码为utf-8码
     */
    public static String getUtf8Str(String s) {
        String ret = null;
        try {
            ret = URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return ret;
    }

    /**
     * utf-8码转为中文
     */
    public static String getStrUtf8(String s) {
        String ret = null;
        try {
            ret = URLDecoder.decode(s, "utf-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return ret;
    }

    public static boolean isChineseStr(String pValue) {
        for (int i = 0; i < pValue.length(); i++) {
            if ((int) pValue.charAt(i) > 256) {
                return true;
            }
        }
        return false;
    }

    public static String getnotNullStringdis(Object s) {
        return s == null ? "" : s.toString();
    }

    public static Integer getnotNullInt(Object s) {
        try {
            return s == null ? new Integer(0) : new Integer(s.toString());
        } catch (Exception ex) {
            return new Integer(0);
        }

    }

    public static String getnoNullInt(Object s) {
        try {
            if (s == null) {
                return "";
            } else if (new Integer(s.toString()).intValue() == 0) {
                return "";
            } else {
                return s.toString();
            }
        } catch (Exception ex) {
            return "";
        }

    }

    public static int getIntValue(String v) {
        return getIntValue(v, -1);
    }

    /***** 将给出的字符串v转换成整形值返回，如果例外则返回预给值def ************/
    public static int getIntValue(String v, int def) {
        try {
            return Integer.parseInt(v);
        } catch (Exception ex) {
            return def;
        }
    }

    /**
     * 将给出的字符串为空时范围默认值。
     *
     * @param String 要校验的字符串。
     * @return 如果是空，将返回def，否则返回str。
     */
    public static String isStrValue(String str, String def) {
        if ((str == null) || (str.length() <= 0)) {
            return def;
        }
        return str;
    }

    public static Timestamp getTimestamp(String str) {
        Timestamp ret = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = dateFormat.parse(str);
            long datelong = date.getTime();
            ret = new Timestamp(datelong);

        } catch (Exception e) {
        }
        return ret;
    }

    public static Object getValue(Object obj, String type) {
        if (obj == null || type == null) {
            return null;
        }

        if ("String".equals(type)) {
            return obj.toString();
        }
        if ("Integer".equals(type)) {
            return new Integer(obj.toString());
        }

        return null;
    }

    /**
     * 检验一个String是否为空。
     *
     * @param String 要校验的字符串。
     * @return 如果是空，将返回true，否则返回false。
     */
    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() <= 0);

    }

    /**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * <p>
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str 要检查的字符串
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Format Date into which format you define
     *
     * @param date   (java.util.Date)
     * @param format (String)
     * @return String example formatDate(date, "yyyy-MM-dd HH:mm:ss")
     */
    public static String formatDate(Date date, String newFormat) {
        if ((date == null) || (newFormat == null)) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(newFormat);

        return formatter.format(date);
    }

    public static String getCurrentDateTime(String _dtFormat) {
        String currentdatetime = "";
        try {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat dtFormat = new SimpleDateFormat(_dtFormat);
            currentdatetime = dtFormat.format(date);
        } catch (Exception e) {
            //////System.out.println("时间格式不正确");
            e.printStackTrace();
        }
        return currentdatetime;
    }

    public static String getCurrentDateTime() {
        return getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentDateTimeSSS() {
        return getCurrentDateTime("yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static String getCurrentDate() {
        return getCurrentDateTime("yyyy-MM-dd");
    }

    public static String getYestodayDateTime(String _dtFormat) {
        String currentdatetime = "";
        try {
            Date today = new Date();
            Date date = new Date(today.getTime() - 24 * 3600 * 1000);
            SimpleDateFormat dtFormat = new SimpleDateFormat(_dtFormat);
            currentdatetime = dtFormat.format(date);
        } catch (Exception e) {
            //////System.out.println("时间格式不正确");
            e.printStackTrace();
        }
        return currentdatetime;
    }

    public static String getYestodayDate() {
        return getYestodayDateTime("yyyy-MM-dd");
    }

    public static String getTomorrow() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + 24 * 60 * 60 * 1000);
        return format.format(tomorrow);
    }

    /**
     * @param strDate   时间型的字符串
     * @param _dtFormat 形如"yyyy-MM-dd HH:mm:ss"的字符串 把 strDate 时间字符串 转换为 _dtFormat 格式
     * @return
     */
    public static String getCurrentDateTime(String strDate, String _dtFormat) {
        String strDateTime;
        Date tDate = null;
        if (null == strDate) {
            return getCurrentDateTime();
        }
        SimpleDateFormat smpDateFormat = new SimpleDateFormat(_dtFormat);
        ParsePosition pos = new ParsePosition(0);
        tDate = smpDateFormat.parse(strDate, pos); // 标准格式的date类型时间
        strDateTime = smpDateFormat.format(tDate); // 标准格式的String 类型时间
        return strDateTime;
    }

    /**
     * 产生指定长度的无规律数字字符串
     *
     * @param aLength 生成的随机数的长度
     * @return 生成的随机字符串 throws 卡号生成异常
     */
    public static String generateRandomDigitString(int aLength) {
        SecureRandom tRandom = new SecureRandom();
        long tLong;
        String aString = "";

        tRandom.nextLong();
        tLong = Math.abs(tRandom.nextLong());
        aString = (String.valueOf(tLong)).trim();
        while (aString.length() < aLength) {
            tLong = Math.abs(tRandom.nextLong());
            aString += (String.valueOf(tLong)).trim();
        }
        aString = aString.substring(0, aLength);

        return aString;
    }

    /**
     * 返回去除前后空格的字符串。
     *
     * @param 字符串
     * @return 处理后的字符串
     */
    public static String trim(String str) {
        if (str == null) {
            return null;
        }

        return str.trim();
    }

    /**
     * 检验一个String符合一定长度，是否为空。
     *
     * @param String  要校验的字符串。
     * @param int     最大字符串长度
     * @param boolean 是否允许为空
     * @return 如果是合法, 返回true，否则返回false。
     */
    public static boolean isValidString(String str, int maxLen, boolean noAllowEmpty) {
        if (isExceed(str, maxLen)) {
            return false;
        }

        return !noAllowEmpty || !isEmpty(str);
    }

    /**
     * 检验一个String字符是否太长。
     *
     * @param String 要校验的字符串。
     * @param int    最大字符串长度
     * @return 如果是太长，将返回true，否则返回false。
     */
    public static boolean isExceed(String str, int maxLen) {
        int len = 0;
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                len++;
                if ((int) str.charAt(i) > 256) {
                    len++;
                }
            }
        }
        return (str != null) && (len > maxLen);

    }

    public static Date parseDate(String date, String newFormat) {
        if ((date == null) || (newFormat == null)) {
            return null;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(newFormat);
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatDate(String date, String newFormat) {
        if ((date == null) || (newFormat == null)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(newFormat);
        return formatter.format(date);
    }

    public static Date parseDate(Date date, String newFormat) {
        return Utils.parseDate(Utils.formatDate(date, newFormat), newFormat);
    }

    /**
     * 格式化金额比例
     */
    public static String formatRefundRate(String str) {
        if (str == null || "".equals(str.trim()) || "0".equals(str.trim())) {
            return "0%";
        } else if (!str.contains(".")) {
            return str;
        } else {

            DecimalFormat df = new DecimalFormat("#0.00");

            return df.format(Double.valueOf(str.trim()) * 100) + "%";
        }
    }

    /*
     * 将#str1#str2#str3#组装成列表
     */
    public static List<String> getList(String strs) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(strs)) {
            return list;
        }

        String[] str = strs.split("#");
        for (int i = 0; i < list.size(); i++) {
            list.add(str[i]);
        }
        return list;
    }

    public static String getString(List<Integer> lst) {
        StringBuffer sb = new StringBuffer();
        for (Integer t : lst) {
            if (t != null) {
                sb.append(String.valueOf(t) + "_");
            }
        }
        return sb.toString();
    }

    /*
     * 判断str2是否在#str1#str2#str3#中存在
     */
    public static int check(String str, String strs) {
        if (StringUtils.isEmpty(strs)) {
            return -1;
        }

        if (strs.indexOf(str) == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public static String getFileName(String path, int type) {
        if (path == null || path.trim().length() == 0) {
            return "";
        }
        int point = path.lastIndexOf(".");
        int plo = path.lastIndexOf("/");
        if (point == -1 || plo == -1) {
            return "";
        }
        // 全名
        if (type == 0) {
            return path.substring(plo + 1, path.length());
        }
        // 文件名称
        if (type == 1) {
            return path.substring(plo + 1, point - 1);
        }
        // 后缀
        if (type == 2) {
            return path.substring(point + 1, path.length());
        }

        return "";
    }


    public static String getValue(String key, String defalut) {
        String value = "";

        try {
            ResourceBundle app = ResourceBundle.getBundle("app");
            value = app.getString(key);
        } catch (Exception e) {
            // e.printStackTrace();
            value = defalut;
        }
        return value;
    }

    /**
     * @param key
     * @param defalut
     * @param type
     * @author xiaozunping
     */
    public static String getValue(String key, String defalut, String type) {
        String value = "";
        if (type == null) {
            value = defalut;
        }
        try {
            ResourceBundle serverurl = ResourceBundle.getBundle("serverurl");
            value = serverurl.getString(key);
        } catch (Exception e) {
            // e.printStackTrace();
            value = defalut;
        }
        return value;
    }


    public static String getDirectionKey(String code, int type) {
        String key = "";
        if (code == null || code.length() == 0) {
            return "";
        }

        if ("-21".equals(code)) {
            return type == 1 ? "leftEntrance" : "leftin";
        }

        if ("-22".equals(code)) {
            return type == 1 ? "leftExit" : "leftout";
        }

        if ("-11".equals(code)) {
            return type == 1 ? "rightEntrance" : "rightin";
        }

        if ("-12".equals(code)) {
            return type == 1 ? "rightExit" : "rightout";
        }

        if ("2".equals(code)) {
            return type == 1 ? "left" : "left";
        }

        if ("1".equals(code)) {
            return type == 1 ? "right" : "right";
        }

        if ("1_".equals(code)) {
            return type == 1 ? "rightOutside" : "rightfoot";
        }

        if ("2_".equals(code)) {
            return type == 1 ? "leftOutside" : "lefttop";
        }
        if ("0".equals(code)) {
            return type == 1 ? "centerMid" : "mid";
        }
        if ("01".equals(code)) {
            return type == 1 ? "centerLeft" : "midin";
        }
        if ("02".equals(code)) {
            return type == 1 ? "centerRight" : "midout";
        }

        return key;
    }


    public static String getFirstUpperStr(String key) {
        String newKey = "";
        if (key != null && !"".equals(key)) {
            newKey = String.valueOf(Character.toUpperCase(key.charAt(0))) + key.substring(1, key.length());
        }

        return newKey;
    }

    public static Map getJavaType(String javaclass) {
        Map map = new HashMap();
        try {
            Class<?> obj = Class.forName(javaclass);

            Field[] f = obj.getDeclaredFields();
            for (Field field : f) {
                field.setAccessible(true);
                map.put(field.getName(), field.getType().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }


    public static String getMdeiaType(String mediaType) {
        if (StringUtils.isEmpty(mediaType)) {
            return "";
        }
        String type = "web";
        if ("3".equals(mediaType)) {// windows
            type = "web";
        } else if ("4".equals(mediaType)) {// mac
            type = "web";
        } else if ("5".equals(mediaType)) {// ios
            type = "ipad";
        } else if ("6".equals(mediaType)) {// andriod
            type = "ipad";
        } else if ("7".equals(mediaType)) {// ipad
            type = "ipad";
        } else {// web
            type = "web";
        }
        return type;
    }

    // 更新文件内容
    public static boolean replaceFile(String path, String context) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        FileWriter fw = null;
        try {
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            fw = new FileWriter(f);
            fw.write(context);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return true;
    }

    public static List<String> getContextList(String path) {
        return getContextList(path, "GBK");
    }

    public static List<String> getContextList(String path, String charsetName) {
        List<String> lines = new ArrayList<String>();
        BufferedReader br = null;
        try {
            /*
             * br = new BufferedReader(new InputStreamReader(new
			 * FileInputStream( path), "UTF-8"));
			 */
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), charsetName));
            String line = null;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return lines;
    }

    /**
     * 读取文件内容
     *
     * @param filePathAndName String 如 c:\\1.txt 绝对路径
     * @return boolean
     */
    public static String readFile(String filePathAndName) {
        String fileContent = "";
        try {
            File f = new File(filePathAndName);
            if (f.isFile() && f.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(f), "GBK");
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent += line;
                }
                read.close();
            }
        } catch (Exception e) {
            //////System.out.println("读取文件内容操作出错");
            e.printStackTrace();
        }
        return fileContent;
    }

    /*
     * 获取 格式如: 大类:;(回车符) 小类:;(回车符) 的List格式
     */
    public static List<String> getEnterList(String enterStr) {
        List<String> list = new ArrayList<String>();
        if (!StringUtils.isEmpty(enterStr)) {
            String[] lines = enterStr.split("\n");
            String data = "";
            for (String line : lines) {
                if (line.contains(";")) {
                    list.add(line.replace(";", ""));
                } else {
                    data = data + line;
                }
                if (data.contains(";")) {
                    list.add(data.replace(";", ""));
                    data = "";
                }
            }
        }
        return list;
    }

    /*
     * 获取 格式如: 大类:;(回车符) 小类:;(回车符) 的Map格式
     */
    public static Map<String, String> getEnterMap(String enterStr) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        List<String> list = Utils.getEnterList(enterStr);
        if (list != null && list.size() > 0) {
            for (String str : list) {
                String[] d = str.split(":");
                map.put(d[0], d[1]);
            }
        }
        return map;
    }

    public static Map<String, String> getPathMap(String type, String path) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        /// 硬装/家具/单人沙发/品牌/20150623-1435_20150705173155475.jpg;
        /// 省份/城市/地区/年份/平方/小区(楼盘名称)/户型图
        if ("house".equals(type) && !StringUtils.isEmpty(path)) {
            String[] ph = path.split("/");
            map.put("省份", ph[1]);// 省份province
            map.put("城市", ph[2]);// 城市city
            map.put("地区", ph[3]);// 地区area
            map.put("年份", ph[4]);// 年份year
            // map.put("平方", ph[5]);
            map.put("小区", ph[6]);// 小区living
        }
        if ("product".equals(type) && !StringUtils.isEmpty(path)) {
            String[] ph = path.split("/");
            /* map.put("硬装", ph[1]); */
            map.put("大类", ph[2]);// 大类class
            map.put("小类", ph[3]);// 小类subClass
            map.put("品牌", ph[4]);// 品牌brand
        }

        return map;
    }

    public static List<Map<String, String>> getAreaList(String line) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (!StringUtils.isEmpty(line) && line.contains(",")) {
            String[] ones = line.split(",");
            for (String str : ones) {
                if (str.contains("X")) {
                    String[] twos = str.split("X");
                    String length = twos[0];
                    String wigth = twos[1];
                    Map<String, String> map = new LinkedHashMap<String, String>();
                    map.put("length", length);
                    map.put("wigth", wigth);
                    list.add(map);
                }

            }
        }

        return list;
    }


    /**
     * 字符串替换，从头到尾查询一次，替换后的字符串不检查
     *
     * @param str    源字符串
     * @param oldStr 目标字符串
     * @param newStr 替换字符串
     * @return 替换后的字符串
     */
    static public String replaceAll(String str, String oldStr, String newStr) {
        int i = str.indexOf(oldStr);
        int n = 0;
        while (i != -1) {
            str = str.substring(0, i) + newStr + str.substring(i + oldStr.length());
            i = str.indexOf(oldStr, i + newStr.length());
            n++;
        }
        return str;
    }

    /**
     * 字符串替换，左边第一个。
     *
     * @param str    源字符串
     * @param oldStr 目标字符串
     * @param newStr 替换字符串
     * @return 替换后的字符串
     */
    static public String replaceFirst(String str, String oldStr, String newStr) {
        int i = str.indexOf(oldStr);
        if (i == -1) {
            return str;
        }
        str = str.substring(0, i) + newStr + str.substring(i + oldStr.length());
        return str;
    }

    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    private static boolean isNotCommon(char c) {

        return c == '#'
                || c == '='
                || c == '&'
                || c == ','
                || c == '%'
                || c == '‘'
                || c == '’'
                || c == '~'
                || c == '-';
    }

    // 完整的判断中文汉字和符号
    public static boolean isContainChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotCommon(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isNotCommon(c)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 分页2
     *
     * @param list  list
     * @param start start
     * @param limit limit
     * @return
     * @author huangsongbo
     */
    public static List<?> paging2(List<?> list, int start, int limit) {
        if (list == null) {
            return null;
        }
        if (start >= list.size()) {
            return null;
        }
        int end = start + limit;
        if (start + limit > list.size()) {
            end = list.size();
        }
        return list.subList(start, end);
    }

    /**
     * str(逗号隔开格式)转化为list
     *
     * @param ids
     * @return
     * @author huangsongbo
     */
    public static List<String> getListFromStr(String str) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isBlank(str)) {
            return list;
        }
        if (str.startsWith(",")) {
            str = str.substring(1, str.length());
        }
        if (str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }
        String[] strs = str.split(",");
        list = Arrays.asList(strs);
        return list;
    }

    public static String getPropertyName(String proName, String key, String def) {
        String value = "";

        try {
            ResourceBundle app = ResourceBundle.getBundle(proName);
            value = app.getString(key);
            if (StringUtils.isBlank(value)) {
                value = def;
            }
        } catch (Exception e) {
            // e.printStackTrace();
            value = def;
        }
        return value;
    }

    public static String getTimeStr() {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(now);
    }

    //白模分类转非白模分类
    public static String getTypeValueKey(String valueKey) {

        if ("basic_beij".equals(valueKey.trim())) {
            valueKey = "beijing";
        }
        if (StringUtils.isNotBlank(valueKey) && valueKey.indexOf("_") != -1) {
            valueKey = valueKey.substring(valueKey.indexOf("_") + 1);
        }
        return valueKey;
    }

    //判断是否是背景墙
    public static Integer getIsBgWall(String valueKey) {
        String bgWall = ",chuangk,basic_chuangk,basic_mengk,mengk,beijing,basic_beij,basic_xingx,basic_chuangt,basic_cant,basic_shaf,basic_dians,xingx,chuangt,cant,shaf,dians,";
        String productSmallTypes = Utils.getValue("filter.productLH.productSmallType", "");
        if (StringUtils.isNotBlank(valueKey)) {
            valueKey = "," + valueKey + ",";
            if (bgWall.indexOf(valueKey) != -1) {
                if (productSmallTypes.indexOf(valueKey) != -1) {
                    return 2;
                }
                return 1;
            }
        }
        return 0;
    }

    //匹配产品配置类型
    public static boolean isMateProductType(String valueKey, String appKey) {
        if (StringUtils.isNotBlank(appKey) && StringUtils.isNotBlank(valueKey)) {
            valueKey = "," + valueKey + ",";
            if (appKey.indexOf(valueKey) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前行数
     *
     * @return
     */
    public static String getLineNumber() {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        return "Line:" + ste.getLineNumber() + ";";
    }

    /**
     * 取得对应图片的缩略图的图片id(针对于空间,户型等图片存在res_house_pic的表中的图片)
     *
     * @param smallPicInfo
     * @return
     * @author huangsongbo
     */
    public static Integer getSmallPicId(String smallPicInfo, String type) {
        if (StringUtils.isBlank(smallPicInfo)) {
            return null;
        }
        Map<String, String> msgMap = getMapFromStr(smallPicInfo);
        String picIdStr = msgMap.get(type);
        if (StringUtils.isBlank(picIdStr)) {
            return null;
        }
        return Integer.valueOf(picIdStr);
    }


    /**
     * 特殊格式字符串处理成map:格式:web:38;ipad:37;
     *
     * @param smallPicInfo
     * @return
     * @author huangsongbo
     */
    public static Map<String, String> getMapFromStr(String fileDesc) {
        Map<String, String> map = new HashMap<String, String>();
        String[] strs = fileDesc.split(";");
        for (String str : strs) {
            if (str.split(":").length == 2) {
                map.put(str.split(":")[0].trim(), str.split(":")[1].trim());
            }
        }
        return map;
    }


    private static void mapPutCustom(Map<String, Set<String>> map, String key, String value) {
        Set<String> set = null;
        if (map.containsKey(key)) {
			/*添加*/
            set = map.get(key);
            if (!set.contains(value)) {
                set.add(value);
            }
        } else {
			/*新建*/
            set = new HashSet<String>();
            set.add(value);
            map.put(key, set);
        }
    }


    /**
     * List<String>->List<Integer>
     *
     * @param listFromStr
     * @return
     * @author huangsongbo
     */
    public static List<Integer> getIntegerListFromStringList(List<String> listFromStr) {
        List<Integer> returnList = new ArrayList<Integer>();
        for (String str : listFromStr) {
            returnList.add(Integer.valueOf(str));
        }
        return returnList;
    }

    /**
     * str->List<Integer>
     *
     * @param productAttributeIds
     * @return
     */
    public static List<Integer> getIntegerListFromStringList(String str) {
        List<Integer> list = new ArrayList<Integer>();
        if (StringUtils.isBlank(str)) {
            return list;
        }
        String[] strs = str.split(",");
        for (String idStr : strs) {
            list.add(Integer.parseInt(idStr));
        }
        return list;
    }


    public static String getTimeExpire(int i) {
        Date now = new Date();
        long nowTime = now.getTime();
        nowTime += i * 60 * 1000;
        Date date = new Date(nowTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

    /**
     * 拆分字符串变成List
     *
     * @return
     * @author huangsongbo
     */
    public static List<String> getListFromStr(String str, String splitStr) {
        List<String> strList = new ArrayList<String>();
        if (StringUtils.isBlank(str)) {
            return strList;
        }
        if (str.startsWith(splitStr)) {
            str = str.substring(1, str.length());
        }
        if (str.endsWith(splitStr)) {
            str = str.substring(0, str.length() - 1);
        }
        splitStr = "\\" + splitStr;
        String[] strs = str.split(splitStr);
        strList = Arrays.asList(strs);
        return strList;
    }

    /**
     * 获取list大小
     *
     * @param list
     * @return
     * @author xiaoxc
     */
    public static int getListTotal(List list) {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    /**
     * 获取i分钟之后的Date
     *
     * @param i
     * @return
     */
    public static Date getLateTime(int i) {
        Date now = new Date();
        long nowTime = now.getTime();
        nowTime += i * 60 * 1000;
        return new Date(nowTime);
    }

    /**
     * List<String>类型转List<Integer>类型
     */
    public static List<Integer> StringToIntegerLst(List<String> inList) {
        List<Integer> iList = new ArrayList<Integer>(inList.size());
        try {
            for (int i = 0, j = inList.size(); i < j; i++) {
                iList.add(Integer.parseInt(inList.get(i)));
            }
        } catch (Exception e) {
        }
        return iList;
    }

    /**
     * 获取本机IP
     *
     * @throws UnknownHostException
     */
    public static String getLocalIP() {
        String localip = "";
        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();
            localip = ia.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return localip;
    }

    /**
     * 拼凑路径
     */
    public static String getPath(String key, String defalut) {
        String value = "";
        try {
            ResourceBundle app = ResourceBundle.getBundle("app");
            value = app.getString(key);
        } catch (Exception e) {
            value = defalut;
        }
        return value;
    }

    public static String getPath2(String key, String defalut) {
        String value = "";
        try {
            ResourceBundle res = ResourceBundle.getBundle("config/res");
            value = res.getString(key);
        } catch (Exception e) {
            value = defalut;
        }
        return value;
    }

    /**
     * 获取昨天的日期
     */
    public static String dayDate(Integer days) {
        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        calendar2.add(Calendar.DATE, days);
        return sdf2.format(calendar2.getTime());
    }

    /**
     * 获取以昨天年月日命名的txt文件
     */
    public static List<String> walk2(Path dir, Integer days) {
        List<String> list = new ArrayList<String>();
        try (
                DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")) {        //只是要txt文件,就读取
            for (Path entry : stream) {
                String convert = entry.toString();
                String last = convert.substring(convert.lastIndexOf("_") + 1);
                String lastBack = last.substring(0, 8);
                if (Integer.parseInt(lastBack) <= Integer.parseInt(Utils.dayDate(days))) {//按照指定日期筛选
                    list.add(entry.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}