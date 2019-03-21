/**
 *
 */
package com.sandu.aes.util;

import com.sandu.common.properties.AesProperties;
import com.sandu.common.properties.AppProperties;
import com.sandu.common.util.FileUploadUtils;
import com.sandu.common.util.Utils;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class FileEncrypt {
    private static Logger logger = LogManager.getLogger(FileEncrypt.class);
    private static String osType = FileUploadUtils.SYSTEM_FORMAT;
    // 密匙
    private static String key = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_KEY_FILEKEY, "41e5c74dd46e4ddcb942dc8ce6224a2e").trim();
    // 加密开关
    private static String encryptSwitch = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_SWITCH_FILEKEY, "true").trim();
    // 加密文件后缀配置(确定哪些文件需要加密)
    public final static String encryptFileSuffix = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_FILESUFFIX_FILEKEY, "assetbundle,txt").trim();
    // 加密文件存放地址
    public static String encryptUploadRoot = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_UPLOAD_ROOT_FILEKEY, "/home/nork/resources_src").trim();
    // 文件加密方式
    public static String encryptWay = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_WAY_FILEKEY, "[{\"encryptWay\":\"DES\",\"suffix\":\"txt\"}]").trim();
    // 文件默认上传根目录
    public static String defaultUploadRoot = Utils.getValueByFileKey(AppProperties.APP, AppProperties.UPLOAD_ROOT_FILEKEY, "");
    // 原文件(非加密路径)存放地址
    public static String noEncryptUploadRoot = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_NOENCRYPT_UPLOAD_ROOT_FILEKEY, "/home/nork/resources").trim();

	/*public static String uploadRoot = Utils.getValueByFileKey(AppProperties.APP, AppProperties.UPLOAD_ROOT_FILEKEY, "D:\\app").trim();*/

    /**
     * 默认访问路径(根,域名,url前面部分)
     */
    public static String resourceUrl = Utils.getValueByFileKey(AppProperties.APP, AppProperties.RESOURCES_URL_FILEKEY, "");

    public static void main(String[] args) {
//    	//System.out.println(UUID.randomUUID().toString().replace("-", ""));

        long startTime = System.currentTimeMillis();
        InputStream in = null;
        try {

/*			File file = new File("e:/test/101914_20170614165116846.assetbundle");
			in = new FileInputStream(file);//读取留文件
			addRedundance("e:/test/aes/a_101914_20170614165116846.assetbundle",in);*/

            changRedundance("e:/test/aes/a_101914_20170614165116846.assetbundle", "e:/test/aes/a1_101914_20170614165116846.assetbundle");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //System.out.println(" 耗时  "+(System.currentTimeMillis()-startTime)+" ms");
    }

    /**
     * 更新冗余信息
     *
     * @param srcPath
     * @param destPath
     */
    public static void changRedundance(String srcPath, String destPath) {
        String oldKey = "41e5c74dd46e4ddcb942dc8ce6224a2e";//作废的key
        String newKey = "41e5c74dd46e4ddcb942dc8ce6211111";//打算使用的key
        int index;
        byte[] bytes = new byte[1024];
        byte[] oldKeyBytes = new byte[oldKey.length()];

        File file = new File(srcPath);
        File outFile = new File(destPath);
        if (!file.exists()) {
            //System.out.println(srcPath+" do not exists");
            return;
        }

        if (!outFile.exists())//文件不存在则创建目录
        {
            outFile.getParentFile().mkdirs();
        }

        FileOutputStream oupPut = null;
        InputStream input = null;
        try {
            input = new FileInputStream(file);//读取留文件
            oupPut = new FileOutputStream(outFile);//输出文件

            if ((index = input.read(oldKeyBytes)) != -1) {//读取作废key的长度,写入的是新key的内容
                oupPut.write(newKey.getBytes(), 0, newKey.getBytes().length);
                oupPut.flush();
            }

            while ((index = input.read(bytes)) != -1) {
                oupPut.write(bytes, 0, index);
                oupPut.flush();
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }

                if (oupPut != null) {
                    oupPut.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 配置文件进行加密
     *
     * @param destination 结果输出到目标文件
     * @param file        输入流
     * @throws IOException
     */
    public static void encryptFileOfTxt(String destination, File file) {
        //配置文件加密
        String keyAes = "";
        if (key.length() < 8) {
            keyAes = String.format("%1$0" + (8 - key.length()) + "d", 0);
        } else {
            keyAes = key.substring(0, 8);
        }

        File outFile = new File(destination);
        if (!outFile.exists())//文件不存在则创建目录
        {
            outFile.getParentFile().mkdirs();
        }

        FileOutputStream oupPut = null;
        String value = null;
        //加密
        try {
            value = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String encrypt = AESUtil2.encryptFile(value, keyAes);
        try {
            oupPut = new FileOutputStream(outFile);  //输出文件
            oupPut.write(encrypt.getBytes(), 0, encrypt.getBytes().length);
            oupPut.flush();
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
     * 加密assetbundle后缀类型的文件
     *
     * @param destination 结果输出到目标文件
     * @param input       输入流
     * @throws IOException
     */
    public static void addRedundance(String destination, InputStream input) {
        int index;
        byte[] bytes = new byte[1024];

        File outFile = new File(destination);
        if (!outFile.exists())//文件不存在则创建目录
        {
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
     * 加密文件
     *
     * @param sFile
     * @param relativePath 文件相对路径
     * @author huangsongbo
     */
    public static void encryptFile(File sFile, String relativePath) {
        if (sFile == null) {
            logger.error("------file = null");
            return;
        }
        if (!sFile.exists()) {
            logger.error("------file没有找到(path:" + sFile.getPath() + ")");
            return;
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sFile);
			/*String encryptFilePath = encryptUploadRoot + relativePath;*/
            String encryptFilePath = Utils.getAbsolutePath(relativePath, null);

            // 选择加密方式 ->start
            List<JSONObject> encryptWayConfig = Utils.getJSONObjectByString(encryptWay);
            List<String> DECSuffixList = new ArrayList<String>();
            if (encryptWayConfig != null && encryptWayConfig.size() > 0) {
                // 找出DES加密方法
                for (JSONObject jsonObject : encryptWayConfig) {
                    // app.resources.encrypt.way=[{"encryptWay":"DES","suffix":"txt"}]
                    if (StringUtils.equals(encryptWayEnum.DES.toString(), (String) jsonObject.get("encryptWay"))) {
                        DECSuffixList = Utils.getListFromStr((String) jsonObject.get("suffix"));
                        break;
                    }
                }
            }
            // 选择加密方式 ->end

            // 加密 ->start
            if (DECSuffixList.indexOf(sFile.getPath().substring(sFile.getPath().lastIndexOf(".") + 1)) != -1) {
                encryptFileOfTxt(encryptFilePath, sFile);
            } else {
                addRedundance(encryptFilePath, inputStream);
            }
            // 加密 ->end
        } catch (FileNotFoundException e) {
            logger.error(e.toString());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.toString());
                }
            }
        }
    }

    private enum encryptWayEnum {
        DES
    }

    /**
     * 加密文件,先判断哪些后缀文件是需要加密的，不需要加密则复制就行
     *
     * @param original     原始绝对路径
     * @param relativePath 目标相对路径
     * @param dataStr      时间戳，用于文件夹命名
     * @author yanghuanzhi
     */
    public static void encryptFile(String original, String relativePath, String dataStr) throws IOException {
        original = Utils.dealWithPath(original, osType);
        relativePath = Utils.dealWithPath(relativePath, osType);
        File oldFile = new File(original);
        if (oldFile == null) {
            logger.error("------file = null");
            return;
        }
        if (!oldFile.exists()) {
            logger.error("------file没有找到(path:" + oldFile.getPath() + ")");
            return;
        }
        try {
            InputStream inputStream = new FileInputStream(oldFile);
			/*String encryptFilePath = encryptUploadRoot+ "_" + dataStr + relativePath;*/
            String encryptFilePath = Utils.getAbsolutePath(dataStr + relativePath, null);
            //判断该文件是否需要加密
            boolean isEncrypt = needEncrypt(original);
            if (isEncrypt) {//加密
                String fileSuffix = getFileSuffix(original);
                if (AESFileConstant.SUFFIX_TXT.equals(fileSuffix)) {
                    //配置文件进行加密
                    encryptFileOfTxt(encryptFilePath, oldFile);
                } else {
                    //产品文件加密
                    addRedundance(encryptFilePath, inputStream);
                }
            } else {//只进行复制
                File directoryFile = new File(encryptFilePath);
                if (!directoryFile.exists()) {
                    directoryFile.getParentFile().mkdirs();
                }
                FileUtils.copyFile(oldFile, directoryFile);
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 判断是否要加密文件
     *
     * @param path
     * @return
     * @author huangsongbo
     */
    public static boolean needEncrypt(String path) {
        path = Utils.dealWithPath(path, Utils.getValueByFileKey(AppProperties.APP, AppProperties.SYSTEM_FORMAT_FILEKEY, "linux"));
        // 判断开关
        if (StringUtils.equals("true", encryptSwitch)) {
            // 判断文件后缀
            // 文件suffix
            String suffix = path.substring(path.lastIndexOf(".") + 1, path.length());
            List<String> list = Utils.getListFromStr(encryptFileSuffix);
            if (list.indexOf(suffix) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得文件后缀
     *
     * @param path
     * @return
     * @author yanghz
     */
    public static String getFileSuffix(String path) {
        // 文件suffix
        String suffix = path.substring(path.lastIndexOf(".") + 1, path.length());
        return suffix;
    }

    /**
     * 步骤
     * 1.首先判断是否需要加密
     * 2.加密文件
     *
     * @param sFile
     * @param relativePath
     * @author huangsongbo
     */
    public static void judgeAndEncryptFile(File sFile, String relativePath) {
        relativePath = Utils.dealWithPath(relativePath, Utils.getValueByFileKey(AppProperties.APP, AppProperties.SYSTEM_FORMAT_FILEKEY, "linux"));
        if (needEncrypt(sFile.getPath())) {
            encryptFile(sFile, relativePath);
        }
    }

    /**
     * 删除加密目录/非加密目录对应的该物理文件
     *
     * @param picPath
     */
    public static void deleteAllFile(String picPath) {
        if (StringUtils.isEmpty(picPath)) {
            logger.error("------function:deleteAllFile->StringUtils.isEmpty(picPath) = true");
            return;
        }
        deleteEncryptFile(picPath);
        deleteNoEncryptFile(picPath);
    }

    /**
     * 删除非加密目录对应的该文件
     *
     * @param picPath
     * @author huangsongbo
     */
    private static void deleteNoEncryptFile(String picPath) {
		/*String encryptFilePath = noEncryptUploadRoot + picPath;*/
        String encryptFilePath = Utils.getAbsolutePath(picPath, Utils.getAbsolutePathType.noEncrypt);
        encryptFilePath = Utils.dealWithPath(encryptFilePath, Utils.getValueByFileKey(AppProperties.APP, AppProperties.SYSTEM_FORMAT_FILEKEY, "linux"));
        File file = new File(encryptFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除加密目录对应的该文件
     *
     * @param picPath
     * @author huangsongbo
     */
    private static void deleteEncryptFile(String picPath) {
		/*String encryptFilePath = encryptUploadRoot + picPath;*/
        String encryptFilePath = Utils.getAbsolutePath(picPath, null);
        encryptFilePath = Utils.dealWithPath(encryptFilePath, Utils.getValueByFileKey(AppProperties.APP, AppProperties.SYSTEM_FORMAT_FILEKEY, "linux"));
        File file = new File(encryptFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

}
