package com.sandu.common.util;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:38 2018/6/25 0025
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.user.model.BankWord;
import org.springframework.util.ClassUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author weisheng
 * @Title: 敏感词校验
 * @Package
 * @Description:
 * @date 2018/6/25 0025AM 10:38
 */

public class BankWordUtil {

    public static Set<String> bankWordMap;

    //读取文件
    public static void readTxtByLine(String path) {
        Set<String> keyWordSet = new HashSet<String>();
        String temp = null;
        BufferedReader reader = null;
        try {
            //file = new ClassPathResource(path).getFile();
            InputStream in = ClassUtils.class.getClassLoader().getResourceAsStream(path);
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            while ((temp = reader.readLine()) != null) {
                keyWordSet.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        bankWordMap = keyWordSet;
    }


    public static void addBadWordToHashMap(Set<String> words) {

    }
}