package com.nork.task.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 2:14 2018/6/8 0008
 * @Modified By:
 */
public class FileUtil {


    /**
     * 下载远程文件并保存到本地
     *
     * @param remoteFilePath 远程文件路径
     * @param localFilePath
     *            本地文件路径
     */
    public static void downloadFile(String remoteFilePath, String localFilePath) {
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        if (!f.getParent().isEmpty()) {
            try {
                f.getParentFile().mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection) urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.flush();
                httpUrl.disconnect();
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
