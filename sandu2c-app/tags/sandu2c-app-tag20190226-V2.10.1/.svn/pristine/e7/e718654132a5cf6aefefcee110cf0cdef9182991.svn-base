package com.sandu.common.util;

import com.sandu.aes.util.FileEncrypt;
import com.sandu.common.properties.AppProperties;

import java.util.Map;

/**
 * 资源分布式存储工具类
 *
 * @author huangsongbo
 */
public class ResDistributeUtils {

    /**
     * 分布式未加密文件上传路径配置
     * app.upload.root.distribute
     */
    public static final String noEncryptDistribute = Utils.getValueByFileKey(AppProperties.APP, AppProperties.APP_RESOURCES_UPLOAD_ROOT_DISTRIBUTE_FILEKEY, "");

    /**
     * 分布式加密文件上传路径配置
     * app.resources.encrypt.upload.root.distribute
     */
    public static final String encryptDistribute = Utils.getValueByFileKey(AppProperties.APP, AppProperties.APP_RESOURCES_ENCRYPT_UPLOAD_ROOT_DISTRIBUTE_FILEKEY, "");

    /**
     * 域名配置
     * app.resources.url.distribute
     */
    public static final String urlDistribute = Utils.getValueByFileKey(AppProperties.APP, AppProperties.APP_RESOURCES_URL_DISTRIBUTE_FILEKEY, "");

    public static final Map<String, String> noEncryptDistributeMap = getNoEncryptDistributeMap();

    public static final Map<String, String> encryptDistributeMap = getEncryptDistributeMap();

    public static final Map<String, String> urlDistributeMap = getUrlDistributeMap();

    private static Map<String, String> getNoEncryptDistributeMap() {
        String json = "";
        if (StringUtils.isEmpty(noEncryptDistribute)) {
            json = "{\"cfg\":[{\"modelName\":\"d_userdesign,e_userlogs,b_base,c_basedesign,a_common,f_resource,g_other\",\"uploadRoot\":\"" + Utils.dealWithPath(FileEncrypt.noEncryptUploadRoot, null) + "\"}]}";
        } else {
            json = noEncryptDistribute;
        }
        return Utils.getMapFromListJsonStr(json, "cfg", "modelName", "uploadRoot");
    }

    private static Map<String, String> getUrlDistributeMap() {
        String json = "";
        if (StringUtils.isEmpty(urlDistribute)) {
            json = "{\"cfg\":[{\"modelName\":\"d_userdesign,e_userlogs,b_base,c_basedesign,a_common,f_resource,g_other\",\"domain\":\"" + Utils.getValueByFileKey(AppProperties.APP, AppProperties.RESOURCES_URL_FILEKEY, "") + "\"}]}";
        } else {
            json = urlDistribute;
        }
        return Utils.getMapFromListJsonStr(json, "cfg", "modelName", "domain");
    }

    private static Map<String, String> getEncryptDistributeMap() {
        String json = "";
        if (StringUtils.isEmpty(encryptDistribute)) {
            json = "{\"cfg\":[{\"modelName\":\"d_userdesign,e_userlogs,b_base,c_basedesign,a_common,f_resource,g_other\",\"uploadRoot\":\"" + Utils.dealWithPath(FileEncrypt.encryptUploadRoot, null) + "\"}]}";
        } else {
            json = encryptDistribute;
        }
        return Utils.getMapFromListJsonStr(json, "cfg", "modelName", "uploadRoot");
    }

}
