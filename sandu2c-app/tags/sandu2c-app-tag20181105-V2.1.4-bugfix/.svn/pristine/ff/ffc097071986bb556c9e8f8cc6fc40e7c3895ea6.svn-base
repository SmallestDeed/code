package com.sandu.common.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.net.ftp.FtpClient;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.List;

public class FtpUploadUtils {

    public final static String FTP_ROOT_PATH = Utils.getValue("app.ftp.upload.root", "/").trim();
    public final static String FTP_HOST = Utils.getValue("app.ftp.server.host", "").trim();
    public final static String FTP_USER = Utils.getValue("app.ftp.server.user", "").trim();
    public final static String FTP_PASSWORD = Utils.getValue("app.ftp.server.password", "").trim();
    private static Logger logger = LoggerFactory.getLogger(FtpUploadUtils.class);

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param path     FTP服务器保存目录
     * @param filename 上传到FTP服务器上的文件名
     * @return 成功返回true，否则返回false
     */
    public static boolean ftpUploadFile(String path, MultipartFile file, String filename) {
        boolean success = false;
        FTPClient ftpc = new FTPClient();
        // FileInputStream input = new FileInputStream(new File(sourceFile));
        String ftpPath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? ""
                : FTP_ROOT_PATH.trim().replace("\\", "/")) + path;
        String dbFilePath = path + filename;
        File sFile = new File(dbFilePath);
        try {
            int reply;
            ftpc.connect(FTP_HOST);// 连接FTP服务器
            // ftp.connect(url, port);//连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpc.login(FTP_USER, FTP_PASSWORD);// 登录
            reply = ftpc.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpc.disconnect();
                return success;
            }
            String newpath = ftpPath.substring(0, ftpPath.lastIndexOf("/") - 1);
            String ppath = newpath.substring(0, newpath.lastIndexOf("/"));
            if (!isDirExist(ftpc, ppath)) {
                if (!ftpc.makeDirectory(new String(ppath.getBytes("GBK"), "iso-8859-1"))) {
                    logger.info("创建文件目录【" + ppath + "】 失败！");
                }
            }

            if (!isDirExist(ftpc, ftpPath)) {
                if (!ftpc.makeDirectory(new String(ftpPath.getBytes("GBK"), "iso-8859-1"))) {
                    logger.info("创建文件目录【" + ftpPath + "】 失败！");
                }
            }
            ftpc.changeWorkingDirectory(ftpPath);
            ftpc.setBufferSize(1024); // 设置1M缓冲
            ftpc.setControlEncoding("GBK"); // 设置编码为GBK
            ftpc.setFileType(FTPClient.BINARY_FILE_TYPE); // 文件类型为二进制文件
            // ftpc.storeFile(filename, sFile);
            // 设置上传目录
            // String fileName = new
            // String(file.getOriginalFilename().getBytes("utf-8"),"iso-8859-1");
            // FTPFile[] fs = ftpc.listFiles();
            // if (fs!=null && fs.length>0) {
            // for(int i=0;i<fs.length;i++){
            // if (fs[i].getName().equals(filename)) {
            // ftpc.deleteFile(fs[i].getName());
            // break;
            // }
            // }
            // }
            OutputStream os = ftpc.appendFileStream(filename);
            byte[] bytes = new byte[1024];
            InputStream is = file.getInputStream();
            int c;
            // 暂未考虑中途终止的情况
            while ((c = is.read(bytes)) != -1) {
                os.write(bytes, 0, c);
            }
            os.flush();
            logger.info("++++++:" + sFile.getPath());
            is.close();
            os.close();

            ftpc.logout();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ftpc.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * 判断Ftp目录是否存在,如果不存在则创建目录
     */
    public static boolean isDirExist(FTPClient ftpClient, String dir) {
        boolean success = false;
        try {
            success = ftpClient.changeWorkingDirectory(dir); // 想不到什么好办法来判断目录是否存在，只能用异常了(比较笨).请知道的告诉我一声`
        } catch (IOException e1) {
            // ftpClient.sendServer("MKD " + dir + "/r/n");
        }
        return success;
    }

    public static boolean makeWorkDir(FTPClient ftpc, String ftpPath) {
        boolean flag = true;
        try {
            String newpath = ftpPath.substring(0, ftpPath.lastIndexOf("/") - 1);
            String ppath = newpath.substring(0, newpath.lastIndexOf("/"));
            if (!isDirExist(ftpc, ppath)) {
                if (!ftpc.makeDirectory(new String(ppath.getBytes("GBK"), "iso-8859-1"))) {
                    logger.info("创建文件目录【" + ppath + "】 失败！");
                    return false;
                }
            }

            if (!isDirExist(ftpc, ftpPath)) {
                if (!ftpc.makeDirectory(new String(ftpPath.getBytes("GBK"), "iso-8859-1"))) {
                    logger.info("创建文件目录【" + ftpPath + "】 失败！");
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return flag;
    }

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
            // ftp服务器存放文件目录

            ftpc.connect(FTP_HOST);// 连接FTP服务器
            logger.info("----------连接成功！");
            // ftp.connect(url, port);//连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpc.login(FTP_USER, FTP_PASSWORD);// 登录
            reply = ftpc.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.info("----------登录失败！");
                ftpc.disconnect();
                return success;
            }
            String ftpFilePath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? ""
                    : FTP_ROOT_PATH.trim().replace("\\", "/")) + ftpPath;
            logger.info("----------登录成功！");

            RenderUtil.makeFtpDirectory(ftpc, ftpFilePath);

            ftpc.changeWorkingDirectory(new String(ftpFilePath.getBytes("GBK"), "iso-8859-1"));

            logger.info("----------原路径=" + sourcePath);
            ftpc.setBufferSize(1024); // 设置1M缓冲
            ftpc.setControlEncoding("GBK"); // 设置编码为GBK
            ftpc.setFileType(FTPClient.BINARY_FILE_TYPE); // 文件类型为二进制文件
            FileInputStream in = new FileInputStream(new File(sourcePath));
            // ftpc.storeFile(filename, in);
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
     * Description: 从FTP服务器下载文件
     *
     * @param path      FTP服务器上的相对路径
     * @param resourcesName  要下载的文件名
     * @param localPath 下载后保存到本地的路径
     * @return
     */
    public static boolean downFile(String path, String resourcesName, String localPath) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(FTP_HOST);// 连接FTP服务器
            ftp.login(FTP_USER, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.info("ftp连接异常,请检查ftp地址帐号，用户和密码是否正确。。。");
                ftp.disconnect();
                return success;
            }
            String ftpPath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? ""
                    : FTP_ROOT_PATH.trim().replace("\\", "/")) + path;
            // String ftpPath = FTP_ROOT_PATH.replace("\\", "/")+path;
            /*String bdFilePath = Constants.UPLOAD_ROOT.replace("\\", "/") + localPath;*/
            String bdFilePath = Utils.getAbsolutePath(localPath, null);

            ftp.changeWorkingDirectory(ftpPath);// 转移到FTP服务器目录
            logger.info("----------Ftp路径=" + ftpPath);
            FTPFile[] fs = ftp.listFiles();
            if (fs == null || fs.length == 0) {
                logger.info("ftp路径获取错误！！！ftpPath=" + ftpPath + ";bdFilePath=" + bdFilePath + ";localPath=" + localPath
                        + ";resourcesName=" + resourcesName + ";path=" + path);
                return success;
            }
            for (FTPFile ff : fs) {
                if (ff != null && ff.getName().equals(resourcesName)) {
                    File localFile = new File(bdFilePath);
					/* 如果文件加不存在,创建文件夹 */
                    if (!localFile.exists()) {
                        try {
                            localFile.getParentFile().mkdirs();
                            localFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    logger.info("----------本地路径=" + bdFilePath);
                    OutputStream is = new FileOutputStream(localFile);

                    ftp.retrieveFile(ff.getName(), is);

                    is.close();
                }
            }
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }


    /**
     * Description: 从FTP服务器下载文件
     *
     * @param path      FTP服务器上的相对路径
     * @param resourcesName  要下载的文件名
     * @return
     */
    public static boolean downFile(String path, String resourcesName) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(FTP_HOST);// 连接FTP服务器
            ftp.login(FTP_USER, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.info("ftp连接异常,请检查ftp地址帐号，用户和密码是否正确。。。");
                ftp.disconnect();
                return success;
            }

            String ftpdirpath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? ""
                    : FTP_ROOT_PATH.trim().replace("\\", "/")) + path;
            String ftpPath = ftpdirpath + resourcesName;

            String relativePath = ("".equals(Utils.getValue(FileUploadUtils.SYSTEM_FORMAT, "linux")) ? path : path.replace("/", "\\")) + resourcesName;
            String localPath = Utils.getAbsolutePath(relativePath, null);
			/*String localPath =  Constants.UPLOAD_ROOT +("".equals(Utils.getValue(FileUploadUtils.SYSTEM_FORMAT,"linux")) ?path:path.replace("/", "\\"))+resourcesName;*/
            // String ftpPath = FTP_ROOT_PATH.replace("\\", "/")+path;
            //String bdFilePath = FileUploadUtils.UPLOAD_ROOT.replace("\\", "/") + localPath;

            ftp.changeWorkingDirectory(ftpdirpath);// 转移到FTP服务器目录
            logger.info("----------Ftp目录路径=" + ftpdirpath);
            FTPFile[] fs = ftp.listFiles();
            if (fs == null || fs.length == 0) {
                logger.info("ftp路径获取错误！！！ftpPath=" + ftpPath + ";localPath=" + localPath + ";localPath=" + localPath
                        + ";resourcesName=" + resourcesName + ";path=" + path);
                return success;
            }
            for (FTPFile ff : fs) {
                if (ff != null && ff.getName().equals(resourcesName)) {
                    File localFile = new File(localPath);
					/* 如果文件加不存在,创建文件夹 */
                    if (!localFile.exists()) {
                        try {
                            localFile.getParentFile().mkdirs();
                            localFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    logger.info("----------本地路径=" + localPath);
                    OutputStream is = new FileOutputStream(localFile);

                    ftp.retrieveFile(ff.getName(), is);

                    is.close();
                }
            }
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    /**
     * 替换文件内容
     *
     * @param filePath 文件地址
     * @param context  替换的内容
     * @return
     */
    public static boolean replaceFile(String filePath, String context) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String ftpPath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? ""
                : FTP_ROOT_PATH.trim().replace("\\", "/")) + filePath.substring(0, filePath.lastIndexOf("/"));
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(FTP_HOST);// 连接FTP服务器
            ftp.login(FTP_USER, FTP_PASSWORD);// 登录

            boolean flag = makeWorkDir(ftp, ftpPath);
            if (flag) {
                ftp.changeWorkingDirectory(new String(ftpPath.getBytes("GBK"), "iso-8859-1"));
            } else {
                return false;
            }

            ftp.changeWorkingDirectory(ftpPath);

            InputStream is = new ByteArrayInputStream(context.getBytes());
            // 先删除之前的文件
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.storeFile(new String(fileName.getBytes("utf-8"), "iso-8859-1"), is);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    public static void downloadFileFtp(String filePath, HttpServletResponse response) throws Exception {
        OutputStream outputStream = response.getOutputStream();
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String ftpPath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? "" : FTP_ROOT_PATH.trim().replace("\\", "/")) + filePath.substring(0, filePath.lastIndexOf("/"));
        FtpClient ftpClient = FtpClient.create();
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
        ftpClient.connect(new InetSocketAddress(FTP_HOST, 21));
        ftpClient.login(FTP_USER, FTP_PASSWORD.toCharArray());
        ftpClient.changeDirectory(ftpPath);
        ftpClient.getFile(fileName, outputStream);
        ftpClient.close();
    }


    /**
     * 在ftp上,下载path1路径的文件,并且上传到path2上
     *
     * @param path1 ftp原文件路径.eg:/design/designTemplet/piclist/
     *              253580_20151209150700553.png
     * @param path2 ftp上拷贝到哪个路径下.eg:/design/designTemplet/piclist
     * @return
     */
    public static boolean copyFileFromFtp(String path1, String path2) {
        boolean flag = true;
        String resourcesName = path1.substring(path1.lastIndexOf("/") + 1, path1.length());
        path1 = path1.substring(0, path1.lastIndexOf("/") + 1);
		/* 从ftp下载到本地 */
        // localPath:本地文件路径
        String localPath = Tools.getRootPath(path1, "D:\\app") + path1.replace("/", "\\") + resourcesName;
        if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
            localPath = Tools.getRootPath(path1, "D:\\app") + path1 + resourcesName;
        }
		/*String localPath = Utils.dealWithPath(Utils.getAbsolutePath(path1 + resourcesName, null), null); */

        File file = new File(localPath);
        if (!file.exists()) {
            flag = downFile(path1, resourcesName, localPath);
        }
        if (!flag) {
            logger.info("------从ftp上下载文件失败");
            return false;
        }
		/* 上传文件到指定路径 */
        flag = uploadFile(resourcesName, localPath, path2);
		/* 是否删除本地文件 */
        if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
            FileUploadUtils.deleteFile(localPath);
        }
        return true;
    }


    public static void main(String args[]) {
//		FtpUploadUtils.downFile("/home/baseHouse/pic", "201507301721196005.jpg", "111.jpg");
//		FtpUploadUtils.uploadFile("1.txt","f://1.txt","/exp/demo/pic/");
//		FtpUploadUtils.replaceFile("/exp/demo/pic/1.txt","asdasdasd");

//		String filePath = "D:\\nork\\resources\\design\\designPlan\\C07_0022_001_131503\\usedConfig\\";
        // //////System.out.println(filePath.substring(filePath.lastIndexOf("/")+1));
        // FtpUploadUtils.downFile("/design/designTemplet/u3dmodel/windowsPc/",
        // "545477_20160229103402049.assetbundle", "/config.assetbundle");

        // //////System.out.println(FtpUploadUtils.getFileContext(filePath));
//		FileUploadUtils.deleteDir(new File(filePath).getParentFile());
        // //////System.out.println(FileUploadUtils.downloadFile("/design/designPlan/u3dconfig/640145_20160225185731424.txt","F://"));
        String ftpServer = "192.168.1.211";
        String userName = "zwj";
        String password = "123456";
//		File file = new File("f:\\temp");
//		FtpUploadUtils.uploadDirectory(file,ftpServer,userName,password,false);

//		try {
//			String tempPath = "f:\\aaa";
//			File tempFile = new File(tempPath);
//			if( tempFile.exists() ){
//				tempFile.mkdirs();
//			}
//			DeCompressUtil.deCompress("f:\\temp.rar",tempPath,"");
//			// 上传到ftp目录,并删除临时目录
//			FtpUploadUtils.uploadDirectory(tempFile, ftpServer, userName,password, true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

        FtpUploadUtils.deleteDir("/MaxRender/MaxRender/", "11", ftpServer, userName, password);
    }

    /**
     * 文件批量上传ftp
     *
     * @param files 本地文件的list
     */
    public static void uploadFiles(List<File> files) {
        FTPClient ftpClient = new FTPClient();
        try {
			/* 链接ftp */
            ftpClient.connect(FTP_HOST);
            ftpClient.login(FTP_USER, FTP_PASSWORD);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.info("------连接ftp失败");
                ftpClient.disconnect();
            }
			/* 批量上传 */
            for (File file : files) {
                String filePath = file.getPath().replace("\\", "/");
                String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
				/*String rootPath = Constants.UPLOAD_ROOT;*/
				/*String ftpPath = filePath.replace(rootPath.replace("\\", "/"), "");*/
                String ftpPath = Utils.getRelativeUrlByAbsolutePath(filePath);
                ftpPath = ftpPath.substring(0, ftpPath.lastIndexOf("/") + 1);
                String ftpFilePath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim()))
                        ? "" : FTP_ROOT_PATH.trim().replace("\\", "/")) + ftpPath;

                String newpath = ftpFilePath.substring(0, ftpFilePath.lastIndexOf("/") - 1);
                String ppath = newpath.substring(0, newpath.lastIndexOf("/"));
                if (!isDirExist(ftpClient, ppath)) {
                    if (!ftpClient.makeDirectory(new String(ppath.getBytes("GBK"), "iso-8859-1"))) {
                        logger.info("创建文件目录【" + ppath + "】 失败！");
                    }
                }

                if (!isDirExist(ftpClient, ftpFilePath)) {
                    if (!ftpClient.makeDirectory(new String(ftpFilePath.getBytes("GBK"), "iso-8859-1"))) {
                        logger.info("创建文件目录【" + ftpFilePath + "】 失败！");
                    }
                }
                ftpClient.changeWorkingDirectory(new String(ftpFilePath.getBytes("GBK"), "iso-8859-1"));
                logger.info("----------原路径=" + file.getPath());
                ftpClient.setBufferSize(1024); // 设置1M缓冲
                ftpClient.setControlEncoding("GBK"); // 设置编码为GBK
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); // 文件类型为二进制文件
                FileInputStream in = new FileInputStream(file);
                // ftpc.storeFile(filename, in);
                ftpClient.storeFile(new String(fileName.getBytes("GBK"), "iso-8859-1"), in);
                logger.info("----------Ftp路径=" + ftpFilePath + fileName);
                in.close();
            }
			/* 注销 */
            ftpClient.logout();
        } catch (SocketException e) {
            logger.error("------连接ftp失败");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("------上传文件到ftp失败");
            e.printStackTrace();
        } finally {
            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {

                }
            }
        }
    }

    /**
     * 得到ftp服务器上文件的文本
     *
     * @return
     */
    public static String getFileContext(String filePath) {
        String context = "";
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        boolean flag = FtpUploadUtils.downFile(filePath.substring(0, filePath.lastIndexOf("/")), fileName,
                "\\" + fileName);
        if (flag) {
			/*context = FileUploadUtils.getFileContext(Constants.UPLOAD_ROOT + "\\" + fileName);*/
            context = FileUploadUtils.getFileContext(Utils.getAbsolutePath(fileName, null));
            FileUploadUtils.deleteFile("\\" + fileName);
        }
        return context;
    }

    /**
     * 从本地上传整个文件夹到ftp
     *
     * @param file      文件夹
     * @param ftpServer
     * @param userName
     * @param password
     * @param isDelete  上传完成后是否删除本地文件夹
     */
    public static void uploadDirectory(File file, String ftpServer, String userName, String password, Boolean isDelete) {
        FTPClient ftpc = new FTPClient();
        try {
            int reply;
            //ftp服务器存放文件目录

            ftpc.connect(ftpServer);//连接FTP服务器
            logger.info("----------连接成功！");
//	        ftp.connect(url, port);//连接FTP服务器
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpc.login(userName, password);//登录
            reply = ftpc.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.info("----------登录失败！");
                ftpc.disconnect();
            }
//			String occu = Utils.getValue("design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
            String occu = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
            RenderUtil.makeFtpDirectory(ftpc, occu);
            ftpc.changeWorkingDirectory(occu);
            upload(file, ftpc);

            //删除本地目录
            if (isDelete) {
                FileUploadUtils.deleteDir(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpc != null) {
                try {
                    ftpc.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 向制定ftp上传文件
     *
     * @param file
     * @param ftpc
     */
    public static void upload(File file, FTPClient ftpc) {
        try {
            if (file.isDirectory()) {
                ftpc.makeDirectory(file.getName());
                ftpc.changeWorkingDirectory(file.getName());
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    File cFile = files[i];
                    if (cFile.isDirectory()) {
                        upload(cFile, ftpc);
                        ftpc.changeToParentDirectory();
                    } else {
                        FileInputStream input = new FileInputStream(cFile);
                        ftpc.storeFile(cFile.getName(), input);
                        input.close();
                    }
                }
            } else {
                FileInputStream input = new FileInputStream(file);
                ftpc.storeFile(file.getName(), input);
                input.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除制定ftp文件夹
     */
    public static void deleteDir(String rootPath, String dirName, String ftpServer, String userName, String password) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(ftpServer);
            ftp.login(userName, password);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
            }
            ftp.changeWorkingDirectory(rootPath);
            recursiveDelete(dirName, ftp);
            ftp.changeWorkingDirectory(rootPath);
            ftp.removeDirectory(dirName);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp != null) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除指定ftp文件
     *
     * @param ftp
     */
    public static void recursiveDelete(String pathName, FTPClient ftp) {
        try {
            ftp.changeWorkingDirectory(pathName);
            FTPFile[] ftpFiles = ftp.listFiles();
            if (ftpFiles.length > 0) {
                for (int i = 0; i < ftpFiles.length; i++) {
                    FTPFile cFile = ftpFiles[i];
                    if (cFile.isDirectory()) {
                        recursiveDelete(cFile.getName(), ftp);
                        ftp.changeToParentDirectory();
                        ftp.removeDirectory(cFile.getName());
                    } else {
                        ftp.deleteFile(cFile.getName());
                    }
                }
            } else {
                ftp.changeToParentDirectory();
                ftp.removeDirectory(pathName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
