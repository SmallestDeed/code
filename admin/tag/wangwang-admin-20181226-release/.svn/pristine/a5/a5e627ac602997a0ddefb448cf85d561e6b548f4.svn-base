package com.sandu.commons;

import com.sandu.api.company.common.FileModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/6/4 0004 14:41
 * @Modified By
 */
@Slf4j
public class FileUtils {

    public final static String  SERVER_FILE_PATH = "serverFilePath";
    public final static String  DB_FILE_PATH = "dbFilePath";

    /**
 	 * 将某个文件或图片等转换为文件资源对象，自动读取资源大小等信息
 	 * sFile 上传文件
 	 * uploadPath 文件上传路径
 	 * isExistTimestamp 文件名称是否存在时间戳
 	 *
 	 */
    public static Map<String, String> getMap(File sFile, String uploadPath, boolean isExistTimestamp){
        Map <String, String>map = new HashMap<String, String>();

        if(sFile.isDirectory() || !sFile.exists() || StringUtils.isEmpty(uploadPath)){
            return map;
        }

        String fname = sFile.getName();
        long fileSize = sFile.length();
        String suffix = fname.substring(fname.lastIndexOf("."));

        String filename = fname;
        if(isExistTimestamp){
            if(fname.contains("_"))
                filename = fname.substring(0, fname.lastIndexOf("_"));
        }else{
            filename = fname.substring(0, fname.lastIndexOf("."));
        }


        if(!StringUtils.isEmpty(uploadPath)){
            map.put(FileModel.FILE_KEY,uploadPath.replace(".upload.path", ""));
        }
        if (!StringUtils.isEmpty(filename)) {
            map.put(FileModel.FILE_NAME,filename);
        }

        String fileorgname = fname.substring(0,fname.lastIndexOf("."));
        map.put(FileModel.FILE_ORIGINAL_NAME,fileorgname);// 物理文件名称
        map.put(FileModel.FILE_SUFFIX,suffix);// 文件后缀
        map.put(FileModel.FILE_SIZE,new Long(fileSize).toString());// 文件大小

        if (".png".equals(suffix.toLowerCase())
                || ".jpg".equals(suffix.toLowerCase())
                || ".jpeg".equals(suffix.toLowerCase())
                || ".gif".equals(suffix.toLowerCase())) {
            BufferedImage bufferedImage = null;
            try{
                bufferedImage = ImageIO.read(sFile);
            }catch(Exception e){
                e.printStackTrace();
            }
            int width = bufferedImage==null?-1:bufferedImage.getWidth(); // 图片宽度
            int height =  bufferedImage==null?-1:bufferedImage.getHeight();// 图片高度
            String format = suffix.replace(".", "");
            map.put(FileModel.PIC_WEIGHT,new Integer(width).toString());
            map.put(FileModel.PIC_HEIGHT,new Integer(height).toString());
            map.put(FileModel.FORMAT,format);
        }

        map.put(SERVER_FILE_PATH, sFile.getAbsolutePath());
        String dbFilePath =  com.sandu.commons.Utils.getRelativeUrlByAbsolutePath(sFile.getAbsolutePath());
        if(StringUtils.isEmpty(dbFilePath))
            dbFilePath = com.sandu.commons.Utils.dealWithPath(sFile.getAbsolutePath(), "linux");

        map.put(DB_FILE_PATH, dbFilePath);
        map.put(FileModel.FILE_PATH, dbFilePath);

        return map;
    }

    public static boolean writeTxtFile(String filePath, String context) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        } else {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException var8) {
                    var8.printStackTrace();
                }
            } else {
                file.delete();
            }

            OutputStreamWriter ow = null;

            try {
                ow = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                ow.write(context);
                ow.close();
                return true;
            } catch (Exception var7) {
                try {
                    if (ow != null) {
                        ow.close();
                    }

                    return false;
                } catch (IOException var6) {
                    var6.printStackTrace();
                    return false;
                }
            }
        }
    }

    public static String getFileContext(String filePath) {
        StringBuffer sb = new StringBuffer();

        try {
            if (!(new File(filePath)).exists()) {
                return sb.toString();
            }

            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String str = "";

            while((str = br.readLine()) != null) {
                sb.append(str);
                sb.append("\r\n");
            }

            br.close();
            fr.close();
        } catch (FileNotFoundException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * 删除加密目录/非加密目录对应的该物理文件
     * @param picPath
     */
    public static void deleteAllFile(String picPath) {
        deleteEncryptFile(picPath);
        deleteNoEncryptFile(picPath);
    }

    /**
     * 删除非加密目录对应的该文件
     * @author huangsongbo
     * @param picPath
     */
    private static void deleteNoEncryptFile(String picPath) {
        String noEncryptFilePath = picPath.replace("\\", "/");
        File file = new File(noEncryptFilePath);
        if(file.exists()){
            transferDeletedFile(picPath,noEncryptFilePath);//将删除的物理文件 转移到 指定目录，30天后自动清理
        }
    }

    /**
     * 删除加密目录对应的该文件
     * @author huangsongbo
     * @param picPath
     */
    private static void deleteEncryptFile(String picPath) {
        String encryptFilePath = picPath.replace("\\", "/");
        File file = new File(encryptFilePath);
        if(file.exists()){
            transferDeletedFile(picPath,encryptFilePath);//将删除的物理文件 转移到 指定目录，30天后自动清理
        }
    }


    /**
     * 将删除的物理文件 转移到 指定目录，30天后自动清理
     * @param relativePath    相对路径
     * @param absolutePath	  绝对路径
     */
    public static Map<String,String> transferDeletedFile(String relativePath,String absolutePath ){
        Map<String,String>resMap = new HashMap<String,String>();
        if(StringUtils.isEmpty(relativePath) || StringUtils.isEmpty(absolutePath)){
            log.error(" transferDeletedFile method : lack  parame ");
            resMap.put("success", "false");
            resMap.put("data", "缺少参数");
            return resMap;
        }
        if(relativePath.indexOf("\\")>-1){
            relativePath = relativePath.replace("\\", "/");
        }
        if(absolutePath.indexOf("\\")>-1){
            absolutePath = absolutePath.replace("\\", "/");
        }
        if(absolutePath.indexOf(relativePath)<=-1){
            log.error(" transferDeletedFile method : relative path and absolute path   Don't match ");
            resMap.put("success", "false");
            resMap.put("data", "绝对路径 与 相对路径 不匹配");
            return resMap;
        }

        File file = new File(absolutePath);//判断物理文件是否存在
        if(!file.exists()){
            log.info(" transferDeletedFile method : not found filePath " + absolutePath);
            resMap.put("success", "false");
            resMap.put("data", "物理文件不存在" + absolutePath);
            return resMap;
        }

        String deletedFilePath =  relativePath;     /** relativePath与absolutePath路径一模一样 相当于没有删除 */
        boolean flag = shearFile(absolutePath,deletedFilePath);//开始剪切文件
        if(flag){
            resMap.put("success", "true");
        }else{
            resMap.put("success", "false");
            resMap.put("data", "文件剪切至 删除目录 失败");
        }
        return resMap;
    }


    /**
     * 剪切文件
     * @param currentFilePath  当前文件路径
     * @param deletedFilePath  将删除的文件移至新的路径
     */
    public static boolean shearFile(String currentFilePath,String deletedFilePath) {
        boolean flag = false;

        File currentFile = new File(currentFilePath);
        File deletedFile = new File(deletedFilePath);

        String directory = deletedFilePath.replace(deletedFile.getName(), "");
        File deletedFileDirectory = new File(directory);
        if(!deletedFileDirectory.exists()){
            deletedFileDirectory.mkdirs();
        }

        InputStream in = null;
        OutputStream out = null;
        try{
            in = new FileInputStream(currentFile);
            out = new FileOutputStream(deletedFile);
            byte[] bytes = new byte[1024];
            int len = -1;
            while((len=in.read(bytes))!=-1){
                out.write(bytes, 0, len);
            }
            flag = true;
        }catch(FileNotFoundException e) {
            log.error(" shearFile method :" + e);
        }catch (IOException e) {
            log.error(" shearFile method :" + e);
        }finally{
            try{
                if(in != null)  in.close();
                if(out != null)  out.close();
                if(flag){
                    currentFile.delete();
                }
                return flag;
            }catch(Exception e) {
                log.error(" shearFile method :" + e);
            }
        }
        return  flag;
    }

}
