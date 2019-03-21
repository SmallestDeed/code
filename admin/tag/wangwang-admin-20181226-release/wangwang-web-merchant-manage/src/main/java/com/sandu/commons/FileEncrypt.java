/**
 *
 */
package com.sandu.commons;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class FileEncrypt {

    // 文件加密方式
    public static String encryptWay = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_WAY_FILEKEY, "[{\"encryptWay\":\"DES\",\"suffix\":\"txt\"}]").trim();
    private static Logger logger = LoggerFactory.getLogger(FileEncrypt.class);
    // 密匙
    private static String key = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_KEY_FILEKEY, "41e5c74dd46e4ddcb942dc8ce6224a2e").trim();

    /**
     * 加密assetbundle后缀类型的文件
     *
     * @param destination 结果输出到目标文件
     * @param input       输入流
     * @throws IOException
     */
    public static void addRedundance(String destination, InputStream input) {
        // 选择加密方式 ->start
        List<Map> encryptWayConfig = Utils.getJSONObjectByString(encryptWay);
        List<String> DECSuffixList = new ArrayList<String>();
        if (encryptWayConfig != null && encryptWayConfig.size() > 0) {
            // 找出DES加密方法
            for (Map jsonObject : encryptWayConfig) {
                // app.resources.encrypt.way=[{"encryptWay":"DES","suffix":"txt"}]
                if (StringUtils.equals(encryptWayEnum.DES.toString(), (String) jsonObject.get("encryptWay"))) {
                    DECSuffixList = Utils.getListFromStr((String) jsonObject.get("suffix"));
                    break;
                }
            }
        }
        // 选择加密方式 ->end

        int index;
        byte[] bytes = new byte[1024];

        File outFile = new File(destination);
        if (!outFile.exists()) {//文件不存在则创建目录
            outFile.getParentFile().mkdirs();
        }

        FileOutputStream oupPut = null;
        try {
            oupPut = new FileOutputStream(outFile);  //输出文件
            oupPut.write(key.getBytes(), 0, key.getBytes().length);
            oupPut.flush();
            while ((index = input.read(bytes)) != -1) {  //每次读取1024byte，直到返回-1表示结束
                oupPut.write(bytes, 0, index);
                oupPut.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != oupPut) {
                    oupPut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * str(逗号隔开格式)转化为list
     *
     * @param str
     * @return
     * @author huangsongbo
     */

    public static List<String> getListFromStr(String str) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isBlank(str)) {
            return list;
        }
        String[] strs = str.split(",");
        list = Arrays.asList(strs);
        return list;
    }

    private enum encryptWayEnum {
        DES
    }
}
