package com.sandu.common.util;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 11:42 2018/7/7 0007
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */


/**
 * 表情处理工具
 * @ClassName: FilterEmojiUtil
 * </p>
 * @author chenxingfei
 * @date 2017年4月7日 上午9:59:49
 *
 */
public class FilterEmojiUtil {
    /**
     * emoji表情替换
     *
     * @param source  原字符串
     * @param slipStr emoji表情替换成的字符串
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source, String slipStr) {
        if (StringUtils.isNotBlank(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        } else {
            return source;
        }
    }

    /**
     * 判断是否包含emoji表情
     *
     * @param source         原字符串
     * @param
     * @return 过滤后的字符串
     */
    public static boolean checkEmoji(String source) {
        if (StringUtils.isNotBlank(source)) {
            return source.matches("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]");
        } else {
            return false;
        }
    }

}