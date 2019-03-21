package com.sandu.search.common.tools;

/**
 * 字符操作类
 *
 * @date 2018/3/15
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
public class StringUtil {

    /**
     * 获取指定字符串出现的次数
     *
     * @param srcText  源字符串
     * @param findText 要查找的字符串
     * @return
     */
    public static int appearNumber(String srcText, String findText) {
        if (null == srcText || "".equals(srcText) || null == findText || "".equals(findText)) {
            return 0;
        }

        //现字符串
        String newSrcText = srcText.replace(findText, "");

        return (srcText.length() - newSrcText.length())/findText.length();
    }

}
