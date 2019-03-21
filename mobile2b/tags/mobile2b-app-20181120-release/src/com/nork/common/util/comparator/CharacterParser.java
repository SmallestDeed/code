package com.nork.common.util.comparator;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 字母转换
 * 
 */
public class CharacterParser
{
	/**
	 * 获取字符串全屏
	 * @param src	字符串（汉字 or 其他字符串）
	 * @return	汉字全拼  or  原字符
	 */
	public String getQuanPin(String src) { 
        char[] t1 = null; 
        t1 = src.toCharArray();  
        String[] t2 = new String[t1.length]; 
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat(); 
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length; 
        try {  
            for (int i =0; i < t0; i++) {  
                // 判断能否为汉字字符  
                // //////System.out.println(t1[i]);
               if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) { 
                   t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中 
                    t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后 
               } else { 
                   // 如果不是汉字字符，间接取出字符并连接到字符串t4后 
                    t4 += Character.toString(t1[i]);  
                }  
            }  
       } catch (BadHanyuPinyinOutputFormatCombination e) { 
           e.printStackTrace();  
        }  
       return t4;  
    }

	/**
	 * 获取字符串汉字拼音的首字母，如果不是汉字，则返回“#”
	 * @param src	要转换的字符串
	 * @return	大写字母或者“#”
	 */
	public String getFristLetter(String src) { 
		String result = "#";
		String str = Character.toString(src.toCharArray()[0]);
		char c = str.toCharArray()[0];
		
		HanyuPinyinOutputFormat hpof = new HanyuPinyinOutputFormat(); 
		hpof.setCaseType(HanyuPinyinCaseType.UPPERCASE);  
		hpof.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
		hpof.setVCharType(HanyuPinyinVCharType.WITH_V);  
		if(str.matches("[a-zA-Z]")){
			
			result = str.toUpperCase();
		}else
		if(str.matches("[\\u4E00-\\u9FA5]+")){
			try
			{
				result = PinyinHelper.toHanyuPinyinStringArray(c, hpof)[0];
			}
			catch (BadHanyuPinyinOutputFormatCombination e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

}
