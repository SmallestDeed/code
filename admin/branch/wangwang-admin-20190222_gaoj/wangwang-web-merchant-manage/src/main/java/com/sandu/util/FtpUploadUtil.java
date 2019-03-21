package com.sandu.util;

import com.sandu.commons.Utils;
import com.sandu.commons.perprety.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public class FtpUploadUtil {

    private final static Logger logger = LoggerFactory.getLogger(FtpUploadUtil.class);

    public final static String FTP_ROOT_PATH =Utils.getValueByFileKey(AppProperties.APP, AppProperties.FTP_UPLOAD_ROOT_FILEKEY, "/").trim();
    public final static String FTP_HOST = Utils.getValueByFileKey(AppProperties.APP, AppProperties.FTP_SERVER_HOST_FILEKEY, "").trim();
    public final static String FTP_USER = Utils.getValueByFileKey(AppProperties.APP, AppProperties.FTP_SERVER_USER_FILEKEY, "").trim();
    public final static String FTP_PASSWORD = Utils.getValueByFileKey(AppProperties.APP, AppProperties.FTP_SERVER_PASSWORD_FILEKEY, "").trim();

    /**
     * 向FTP服务器上传文件
     *
     * @param filename   文件名称
     * @param sourcePath 文件路径
     * @param ftpPath    ftp存放路径
     * @return
     */
    public static boolean uploadFile(String filename, String sourcePath, String ftpPath) {
        boolean success = false;
        FTPClient ftpc = new FTPClient();
        try {
            int reply;

            // 连接FTP服务器ftp.connect(url, port);
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpc.connect(FTP_HOST);
            logger.info("----------连接成功！");
            // 登录
            ftpc.login(FTP_USER, FTP_PASSWORD);
            reply = ftpc.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.info("----------登录失败！");
                ftpc.disconnect();
                return success;
            }
            String ftpFilePath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? ""
                    : FTP_ROOT_PATH.trim().replace("\\", "/")) + ftpPath;
            logger.info("----------登录成功！");
            //创建目录  ftp服务器存放文件目录
            makeFtpDirectory(ftpc, ftpFilePath);

            ftpc.changeWorkingDirectory(new String(ftpFilePath.getBytes("GBK"), "iso-8859-1"));
            logger.info("----------原路径=" + sourcePath);

            // 设置1M缓冲
            ftpc.setBufferSize(1024);
            // 设置编码为GBK
            ftpc.setControlEncoding("GBK");
            // 文件类型为二进制文件
            ftpc.setFileType(FTPClient.BINARY_FILE_TYPE);
            FileInputStream in = new FileInputStream(new File(sourcePath));
            ftpc.storeFile(new String(filename.getBytes("GBK"), "iso-8859-1"), in);
            logger.info("----------Ftp路径=" + ftpFilePath + filename);
            in.close();
            ftpc.logout();
            success = true;
        } catch (Exception e) {
            logger.error("----------上传异常！" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (ftpc != null && ftpc.isConnected()) {
                try {
                    ftpc.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;

    }

    /**
     * 创建文件目录
     * @param ftp
     * @param ftpPath
     * @return
     */
    private static boolean makeFtpDirectory(FTPClient ftp, String ftpPath) {
        boolean flag = false;
        try {
            if (StringUtils.isNotBlank(ftpPath) && ftpPath.lastIndexOf("/") > -1) {
                if (!ftp.makeDirectory(ftpPath)) {
                    makeFtpDirectory(ftp, ftpPath.substring(0, ftpPath.lastIndexOf("/")));
                    flag = ftp.makeDirectory(ftpPath);
                } else {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
