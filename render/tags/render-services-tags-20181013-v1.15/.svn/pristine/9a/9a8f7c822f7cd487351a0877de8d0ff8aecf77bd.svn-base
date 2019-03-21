package com.nork.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.net.Socket;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.nork.design.model.RenderConfig;

/**
 * Created by Administrator on 2015/11/13.
 */
public class RenderUtil {
    private static Logger logger = Logger.getLogger(RenderUtil.class);
    private static final String requestServer = "192.168.1.224";
    private static final String port = "8000";
    private static final String timeout ="10000";
    //前一个是否已经执行完毕
    public static boolean flag = true;

    /** 渲染压缩文件存储方式 **/
    public static String STORAGE_TYPE_FTP = "ftp";//ftp存储
    public static String STORAGE_TYPE_LOCAL = "local";//本地存储
    public static String STORAGE_TYPE_RUIYUN = "ruiyun";//ruiyun存储



    /**
     * 发送渲染请求
     * @param jsonParam 请求参数
     */
    public static boolean sendRequest(String jsonParam,String renderServer) throws IOException{
        if(StringUtils.isBlank(renderServer) ){
            return false;
        }
        Socket client = null;
        Writer writer = null;
        Integer ports =new Integer(Utils.getPropertyName("render", "app.3dmax.socket.port", port)).intValue();
        try{
            //logger.warn("start renderServer:"+renderServer+":"+ports);
            client = new Socket(renderServer,ports);
            if(client != null){
                logger.warn("socket 连接服务器正常...renderServer="+renderServer+":"+ports);
                client.setSoTimeout(new Integer(Utils.getPropertyName("render", "app.3dmax.socket.timeout", timeout)).intValue());
                writer = new OutputStreamWriter(client.getOutputStream());
                writer.write(jsonParam);
                writer.flush();
                writer.close();
            }
        }catch(IOException io){
            io.printStackTrace();
            logger.info("socket 连接服务器异常...renderServer="+renderServer+":"+ports+";e.message=" + io.getMessage() );
            return false;
        }catch(Exception e){
            e.printStackTrace();
            logger.info("socket 连接服务器异常...renderServer="+renderServer+":"+ports+";e.message=" + e.getMessage() );
            return false;
        }finally{
            try{
                if(writer != null){
                    writer.close();
                }

                if (client != null) {
                    client.close();
                }
            }catch(IOException io){
                logger.error("socket 连接服务器异常...renderServer="+renderServer+":"+ports+";e.message=" + io.getMessage() );
                io.printStackTrace();
                return false;
            }catch(Exception e){
                logger.error("socket 连接服务器异常...renderServer="+renderServer+":"+ports+";e.message=" + e.getMessage() );
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 解压renderRoot目录下的所有zip包
     * @param renderRoot 渲染根目录
     * @param zipPath 解压包所在目录
     */
    public static void unzip(String renderRoot,String zipPath){
        try {
            File file = new File(zipPath);
            File[] files = file.listFiles();
            for( int i=0;i<files.length;i++ ){
                String fileName = files[i].getName();
                if( fileName.endsWith(".rar") || fileName.endsWith(".zip") ){
                    DeCompressUtil.deCompress(files[i].getPath(), renderRoot,"");
                    //删除压缩包
                    files[i].delete();
                }else if( files[i].isDirectory() ){
                    unzip(renderRoot,files[i].getPath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查path目录下是否存在压缩包
     * @param path
     * @return
     */
    public static boolean hasZip(String path){
        boolean flag = false;
        File file = new File(path);
        if( !file.exists() ){
            return false;
        }else{
            File[] files = file.listFiles();
            for( int i=0;i<files.length;i++ ){
                String fileName = files[i].getName();
                if( fileName.endsWith(".rar") || fileName.endsWith(".zip") ){
                    flag = true;
                    break;
                }
            }
            return flag;
        }
    }

    /**
     * 删除文件
     * @param path
     */
    public static boolean deleteFile(String path){
        File file = new File(path);
        if( !file.exists() ){
            return false;
        }
        if( file.isDirectory() ){
            File[] files = file.listFiles();
            //循环删除目录下的子目录
            if( files.length > 0 ){
                for( int i=0;i<files.length;i++ ){
                    File cFile = files[i];
                    deleteFile(cFile.getPath());
                }
                //删除文件夹本身
                file.delete();
            }else{//如果文件夹中没有子目录，则直接删除
                file.delete();
            }
        }else if( file.isFile() ){
            file.delete();
        }
        return true;
    }

    /**
     * 生成配置文件
     */
    public static boolean createDataFile(String fileContent,String renderRoot){
        logger.info("开始生成配置文件createDataFile");
//        String path = Utils.getValue("app.render.root","D:\\MaxRender").trim()+"/3Dmax";
        String path = renderRoot + "";
        logger.info("配置文件path="+path);
        //String tempPath = Utils.getValue("design.designPlan.maxrender.config.upload.path","/data/").trim();
        String tempPath = Utils.getPropertyName("config/res", "design.designPlan.maxrender.config.upload.path", "/data/").trim();
        logger.info("配置文件tempPath="+tempPath);
        String datafile = Utils.getValue("design.designPlan.maxrender.config.name","Data.txt").trim();
        logger.info("配置文件datafile="+datafile);
        String newtempPath = tempPath ;
        if(!"linux".equals(FileUploadUtils.SYSTEM_FORMAT) ){
            newtempPath = tempPath.replace("/","\\");
        }
        logger.info("配置文件newtempPath="+newtempPath);
        String newPath = path + newtempPath + datafile;
        logger.info("开始生成配置文件DataPath="+newPath);
        //////System.out.println("开始生成配置文件DataPath="+newPath);
        File file = new File(newPath);
        FileWriter fw = null;
        try {
            if (!file.getParentFile().exists()){
                file.mkdirs();
            }
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();
            fw = new FileWriter(file);
            fw.write(fileContent);
            fw.close();

        } catch(IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return false;
        }finally{
            try{
                if(fw != null){
                    fw.close();
                }
            }catch(Exception e){
                logger.error(e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        //////System.out.println("Data文件初始格式已生成,path="+newPath+"\n"+"内容如下："+fileContent);
        return true;
    }

    public static boolean replaceFile(String context,RenderConfig renderConfig){
        logger.info("配置文件path="+renderConfig.getRenderRoot());
        String ftpPath = Utils.getPropertyName("config/res", "design.designPlan.maxrender.config.upload.path", "/data/").trim();
        logger.info("配置文件tempPath="+ftpPath);
        String fileName = Utils.getValue("design.designPlan.maxrender.config.name","Data.txt").trim();

//        String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
//        String ftpPath = ((StringUtils.isEmpty(renderConfig.getRenderRoot().trim())||"/".equals(renderConfig.getRenderRoot().trim()))?"":renderConfig.getRenderRoot().trim().replace("\\", "/")) + filePath.substring(0,filePath.lastIndexOf("/"));
        FTPClient ftp = new FTPClient();
        try{
            ftp.connect(renderConfig.getRenderServer());//连接FTP服务器
            ftp.login(renderConfig.getUserName(), renderConfig.getPassword());//登录
            makeFtpDirectory(ftp, ftpPath);
            ftp.changeWorkingDirectory(ftpPath);
            InputStream is = new ByteArrayInputStream(context.getBytes());
            //先删除之前的文件
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.storeFile(new String(fileName.getBytes("utf-8"), "iso-8859-1"), is);
        } catch (IOException e){
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

    public static boolean makeFtpDirectory(FTPClient ftp, String ftpPath){
        boolean flag = false;
        try {
            if( StringUtils.isNotBlank(ftpPath) && ftpPath.lastIndexOf("/") > -1 ) {
                if (!ftp.makeDirectory(ftpPath)) {
                    makeFtpDirectory(ftp, ftpPath.substring(0, ftpPath.lastIndexOf("/")));
                    flag = ftp.makeDirectory(ftpPath);
                }else{
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 生成渲染脚本
     * @param renderConfig
     * @return
     */
    public static JSONObject createScript(RenderConfig renderConfig,String businessCode,String params){
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("success", true);
        String storageType = renderConfig.getStorageType();
//        String uploadDic = Utils.getValue("design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/").trim();
        String uploadDic = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
        // 脚本模版存储目录
        String scriptTemplateDir = Utils.getPropertyName("render", "script.template.path", "/MaxTemplate/");
        // 脚本存储目录
        String scriptDic = Utils.getPropertyName("render", "script.path", "/MaxScript/");
        String renderPath = renderConfig.getRenderRoot();
        String scriptTemplatePath = renderPath + uploadDic + scriptTemplateDir;// 脚本模版存放路径
        String scriptPath = renderPath + uploadDic + scriptDic + businessCode + "/";// 生成脚本存放路径
        try {
            logger.info("storageType："+storageType);
            if ("local".equals(storageType) || "ruiyun".equals(storageType)) {
                File file = new File(scriptTemplatePath);
                String osType = FileUploadUtils.SYSTEM_FORMAT;
                String occu = "/";
                logger.info("SYSTEM_FORMAT："+osType);
                if ("windows".equals(osType)) {
                    occu.replace("/", "\\");
                    renderPath = renderPath.replace("/", "\\");
                    scriptTemplatePath = scriptTemplatePath.replace("/", "\\");
                    scriptPath = scriptPath.replace("/", "\\");
                }
                logger.info("脚本模版存放路径："+scriptTemplatePath);
                logger.info("生成脚本存放路径："+scriptPath);
                if (file.exists() && file.isDirectory()) {
                    /** 生成ImporterFBX **/
                    File importTemplate = new File(scriptTemplatePath + "ImporterFBX.ms");
                    if("ruiyun".equals(storageType)){
                        importTemplate = new File(scriptTemplatePath + "ImporterFBX_ruiyun.ms");
                    }
                    if (!importTemplate.exists()) {
                        jsonObject.remove("success");
                        jsonObject.accumulate("success", false);
                        jsonObject.accumulate("message", "模板文件ImporterFBX.ms没有找到！目录："+scriptTemplatePath + "ImporterFBX.ms");
                        logger.info("模板文件ImporterFBX.ms没有找到！目录："+scriptTemplatePath + "ImporterFBX.ms");
                        deleteFile(scriptPath);// 删除目录
                        return jsonObject;
                    }
                    // 读取模版内容
                    String importTemplateContext = FileUploadUtils.getFileContext(scriptTemplatePath + "ImporterFBX.ms");
                    if("ruiyun".equals(storageType)){
                        importTemplateContext = FileUploadUtils.getFileContext(scriptTemplatePath + "ImporterFBX_ruiyun.ms");
                    }
                    // 生成脚本文件
                    importTemplateContext = importTemplateContext.replaceAll("\\{designPlanCode\\}", businessCode);
                    logger.info("准备生成ImporterFBX.ms文件！目录："+scriptPath + "MaxScene" + occu + "ImporterFBX.ms");
                    if("ruiyun".equals(storageType)){
                        FileUploadUtils.writeTxtFile(scriptPath + "MaxScene" + occu + "ImporterFBX_ruiyun.ms", importTemplateContext);
                    }else{
                        FileUploadUtils.writeTxtFile(scriptPath + "MaxScene" + occu + "ImporterFBX.ms", importTemplateContext);
                    }
                    logger.info("生成成功！");

                    /** 生成Render.bat **/
                    File renderBatTemplate = new File(scriptTemplatePath + "Render.bat");
                    if("ruiyun".equals(storageType)){
                        renderBatTemplate = new File(scriptTemplatePath + "Render_ruiyun.bat");
                    }
                    if (!renderBatTemplate.exists()) {
                        jsonObject.remove("success");
                        jsonObject.accumulate("success", false);
                        jsonObject.accumulate("message", "模板文件Render.bat没有找到！");
                        logger.info("模板文件Render.bat没有找到！目录：" + scriptTemplatePath + "Render.bat");
                        deleteFile(scriptPath);// 删除目录
                        return jsonObject;
                    }
                    // 读取模板内容
                    String renderTemplateContext = FileUploadUtils.getFileContext(scriptTemplatePath + "Render.bat");
                    if("ruiyun".equals(storageType)){
                        renderTemplateContext = FileUploadUtils.getFileContext(scriptTemplatePath + "Render_ruiyun.bat");
                    }
                    renderTemplateContext = renderTemplateContext.replaceAll("\\{designPlanCode\\}", businessCode);
                    logger.warn("准备生成Render.bat文件！目录："+scriptPath + "Render.bat");
                    FileUploadUtils.writeTxtFile(scriptPath + "Render.bat", renderTemplateContext);
                    logger.warn("生成成功！");
                    /** 生成startscene.max **/
                    File sceneTemplate = new File(scriptTemplatePath + "startscene.max");
                    if (!sceneTemplate.exists()) {
                        jsonObject.remove("success");
                        jsonObject.accumulate("success", false);
                        logger.info("模板文件startscene.max没有找到！目录：" + scriptTemplatePath + "startscene.max");
                        jsonObject.accumulate("message", "startscene.max没有找到！");
                        deleteFile(scriptPath);// 删除目录
                        return jsonObject;
                    }
                    logger.info("准备生成生成startscene.max文件！目录："+scriptPath + "startscene.max");
                    FileUploadUtils.fileCopy(scriptTemplatePath + "startscene.max", scriptPath + "startscene.max");
                    logger.info("生成成功！");

                    /** 生成Data.txt **/
                   // String tempPath = Utils.getValue("design.designPlan.maxrender.config.upload.path", "/data/").trim();
                    String tempPath =  Utils.getPropertyName("config/res", "design.designPlan.maxrender.config.upload.path", "/data/").trim();
                    if (StringUtils.isBlank(params)) {
                        jsonObject.remove("success");
                        jsonObject.accumulate("success", false);
                        jsonObject.accumulate("message", "params文本信息为空！");
                        return jsonObject;
                    }
                    logger.warn("准备生成Data.txt文件！目录："+renderPath + tempPath + "Data.txt");
                    FileUploadUtils.writeTxtFile(scriptPath + "Data" + occu + "Data.txt", params);
                    logger.warn("生成成功！");
                }
                //【云渲染】创建任务 ，生成批处理
                logger.info("---------【云渲染】生成批处理文件！");
                if( "ruiyun".equals(storageType) ){
                    //copy renderbus.bat模板到对应的文件下，替换变量保存
                    String renderbusBat = "renderbus.bat";
                    String renderbusTempletPath = scriptTemplatePath + renderbusBat;
                    String rederbusBatPath = scriptPath + renderbusBat;
                    File file2 = new File(renderbusTempletPath);
                    if( file2.exists() ){
                        FileUploadUtils.fileCopy(renderbusTempletPath,rederbusBatPath);
                        File f = new File(rederbusBatPath);
                        if( f.exists() ){
                            String renderContext = FileUploadUtils.getFileContext(rederbusBatPath);
                            // 读取模板内容
                            renderContext = renderContext.replaceAll("\\{designPlanCode\\}", businessCode);
                            // 生成脚本文件
                            FileUploadUtils.writeTxtFile(rederbusBatPath, renderContext);
                            logger.info("生成文件成功:"+rederbusBatPath);
                        }else{
                            logger.info("找不到这个目录文件："+rederbusBatPath+"！");
                        }
                    }else{
                        logger.info("找不到这个目录文件："+renderbusTempletPath+"！");
                    }
                }

            } else if ("ftp".equals(storageType)) {
                /** 生成ImporterFBX **/
                String importerFBXContext = getFileContext(renderConfig,scriptTemplatePath, "ImporterFBX.ms");
                importerFBXContext = importerFBXContext.replaceAll("\\{designPlanCode\\}", businessCode);
                createTxtFile(renderConfig,scriptPath + "MaxScene/","ImporterFBX.ms",importerFBXContext);

                /** 生成Render.bat **/
                String renderBatContext = getFileContext(renderConfig,scriptTemplatePath, "Render.bat");
                renderBatContext = renderBatContext.replaceAll("\\{designPlanCode\\}", businessCode);
                createTxtFile(renderConfig,scriptPath,"Render.bat",renderBatContext);

                /** 生成startscene.max **/
                String startsceneContext = getFileContext(renderConfig,scriptTemplatePath,"startscene.max");
                createTxtFile(renderConfig,scriptPath,"startscene.max",startsceneContext);

                /** 生成Data.txt **/
              //  String tempPath = Utils.getValue("design.designPlan.maxrender.config.upload.path", "/data/").trim();
                String tempPath =  Utils.getPropertyName("config/res", "design.designPlan.maxrender.config.upload.path", "/data/").trim();
                String dataContext = getFileContext(renderConfig,renderPath + tempPath,"Data.txt");
                createTxtFile(renderConfig,scriptPath+"Data","Data.txt",dataContext);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            jsonObject.remove("success");
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message", "生成渲染脚本异常！");
            if ("local".equals(storageType)) {
                deleteFile(scriptPath);
            } else if ("ftp".equals(storageType)) {
                FtpUploadUtils.deleteDir(renderConfig.getRenderRoot(),scriptPath,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
            }
        }
        return jsonObject;
    }

    /**
     * 获取ftp文件内容
     * @param renderConfig
     * @param filePath
     * @param fileName
     * @return
     */
    public static String getFileContext(RenderConfig renderConfig,String filePath,String fileName){
        String context = "";
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(renderConfig.getRenderServer());// 连接FTP服务器
            ftp.login(renderConfig.getUserName(), renderConfig.getPassword());// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.info("ftp连接异常,请检查ftp地址帐号，用户和密码是否正确。。。");
                ftp.disconnect();
                return context;
            }
            ftp.changeWorkingDirectory(filePath);
            logger.info("----------Ftp路径=" + filePath);
            FTPFile[] fs = ftp.listFiles();
            if( fs == null || fs.length == 0 ){
                return context;
            }
            for( FTPFile ff : fs ){
                if( ff != null && ff.getName().equals(fileName) ){
                    /*File tempFile = new File(Constants.UPLOAD_ROOT.replace("\\", "/") + "\\" + fileName);*/
                	File tempFile = new File(Utils.getAbsolutePath(fileName, null));
                    if ( !tempFile.exists() ) {
                        tempFile.getParentFile().mkdirs();
                        tempFile.createNewFile();
                    }
                    logger.info("----------本地路径=" + Utils.getAbsolutePath(fileName, null));
                    /*OutputStream is = new FileOutputStream(Constants.UPLOAD_ROOT.replace("\\", "/") + "/" + fileName);*/
                    OutputStream is = new FileOutputStream(Utils.getAbsolutePath(fileName, null));
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            ftp.logout();
            /*context = FileUploadUtils.getFileContext(Constants.UPLOAD_ROOT + "\\" + fileName);*/
            context = FileUploadUtils.getFileContext(Utils.getAbsolutePath(fileName, null));
            FileUploadUtils.deleteFile("\\" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if( ftp != null ){
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return context;
    }

    /**
     * 创建文件
     * @param renderConfig
     * @return
     */
    public static boolean createTxtFile(RenderConfig renderConfig,String filePath,String fileName,String context){
        boolean flag = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(renderConfig.getRenderServer());// 连接FTP服务器
            ftp.login(renderConfig.getUserName(), renderConfig.getPassword());// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.info("ftp连接异常,请检查ftp地址帐号，用户和密码是否正确。。。");
                ftp.disconnect();
                return flag;
            }
            ftp.changeWorkingDirectory("/");
            makeFtpDirectory(ftp, filePath);
            ftp.changeWorkingDirectory(filePath);
            logger.info("----------Ftp路径=" + filePath);
            InputStream is = new ByteArrayInputStream(context.getBytes());
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.storeFile(new String(fileName.getBytes("utf-8"), "iso-8859-1"), is);
        }catch (Exception e){
            e.printStackTrace();
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
        return flag;
    }


    /**
     * 复制ftp文件
     * @param renderConfig
     * @param resourcePath
     * @param targetPath
     * @param fileName
     * @return
     */
    public boolean copyFile(RenderConfig renderConfig,String resourcePath,String targetPath,String fileName){
        boolean flag = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(renderConfig.getRenderServer());// 连接FTP服务器
            ftp.login(renderConfig.getUserName(), renderConfig.getPassword());// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.info("ftp连接异常,请检查ftp地址帐号，用户和密码是否正确。。。");
                ftp.disconnect();
                return flag;
            }
            ftp.changeWorkingDirectory(resourcePath);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if( ftp != null ){
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 移动整个目录
     * @param sourceDir
     * @param targetDir
     * @return
     */
    public static boolean moveDir(String sourceDir,String targetDir){
        try {
            if (StringUtils.isBlank(sourceDir) || StringUtils.isBlank(targetDir)) {
                return false;
            }
            File sourceFile = new File(sourceDir);
            // 源目录不存在，或者路径不是目录路径
            if (!sourceFile.exists() || !sourceFile.isDirectory()) {
                return false;
            }

            File targetFile = new File(targetDir);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            // 递归拷贝
            copyFolder(sourceFile,targetFile);
            // 删除源目录
            FileUploadUtils.deleteDir(new File(sourceDir));
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 递归拷贝
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyFolder(File src,File dest) throws IOException{
        if( src.isDirectory() ){
            File[] sourceFiles = src.listFiles();
            if( sourceFiles != null ){
                for( File file : sourceFiles ){
                    File destFile = new File(dest,file.getName());
                    copyFolder(file,destFile);
                }
            }
        }else{
            FileUploadUtils.fileCopy(src,dest);
        }
    }

    /**
     * 比较两个时间的时间差
     * @param oldDate 小时间
     * @param newDate 大时间
     * @return
     */
    public static Long timeDiff(Date oldDate,Date newDate){
        if( oldDate == null || newDate == null ){
            return null;
        }
        long oldLong = oldDate.getTime();
        long newLong = newDate.getTime();
        return (newLong-oldLong)/(1000*60);
    }

    /**
     * 创建全景图
     * @param picPath 720度全景图地址
     * @param businessCode 设计方案编码
     */
    public static JSONObject generatePano(String picPath,String businessCode){
        /***********************************************/
        /**                                           **/
        /***********************************************/
        JSONObject jsonObject = new JSONObject();
        if( StringUtils.isBlank(picPath) || StringUtils.isBlank(businessCode) ){
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message","picPath为空或者businessCode为空！");
            return jsonObject;
        }
        File picFile = new File(picPath);
        if( !picFile.exists() ){
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message","没有找到全景图，无法生成！path="+picPath);
            logger.info("没有找到全景图，无法生成！path="+picPath);
            return jsonObject;
        }
        // pano程序目录
        String panoProgramPath = Utils.getPropertyName("render","app.pano.ProgramPath","f:\\");
        // pano程序的config文件存放目录
        String programConfigDir = Utils.getPropertyName("render","app.pano.configPath","templates\\");
        // 模板文件目录
        String panoTemplatePath = Utils.getPropertyName("render","app.pano.panoTemplatePath","F:\\\\TEST\\\\");
        // pano程序结果输出目录
        String panoOutputPath = Utils.getPropertyName("render","app.pano.outPutPath","F:\\\\TEST\\\\wk");

        // 生成脚本
        if( "windows".equals(FileUploadUtils.SYSTEM_FORMAT) ){
            panoProgramPath = panoProgramPath.replace("/", "\\");
            programConfigDir = programConfigDir.replace("/", "\\");
            panoTemplatePath = panoTemplatePath.replace("/", "\\");
//            panoOutputPath = panoOutputPath.replace("/", "\\");
        }
        logger.info(panoTemplatePath+" - "+panoProgramPath+" - " +panoOutputPath+" - "+programConfigDir);

        String newConfigFileName = businessCode+".config";
        File panoTemplateDir = new File(panoTemplatePath);
        if( panoTemplateDir.exists() ){
            /** 生成config文件 **/
            File configFile = new File(panoTemplatePath + "vtour-multires.config");
            if( configFile.exists() ) {
                // 读取模板内容
                String configContext = FileUploadUtils.getFileContext(panoTemplatePath + "vtour-multires.config");
                // 生成配置文件
                configContext = configContext.replaceAll("%INPUTPATH%", panoOutputPath).replaceAll("%BASENAME%", businessCode);
                configContext = configContext.replaceAll("%PLAN_CODE%",businessCode).replaceAll("%HTML_NAME%",businessCode);
                logger.error("准备生成config文件！目录：" + panoProgramPath + programConfigDir + newConfigFileName);
                FileUploadUtils.writeTxtFile(panoProgramPath + programConfigDir + newConfigFileName, configContext);
                logger.info("生成成功！");
            }else{
                jsonObject.remove("success");
                jsonObject.accumulate("success", false);
                jsonObject.accumulate("message", "模板文件vtour-multires.config没有找到！目录：" + panoTemplatePath + "vtour-multires.config");
                logger.error("模板文件vtour-multires.config没有找到！目录：" + panoTemplatePath + "vtour-multires.config");
                return jsonObject;
            }

            logger.info("end read pano shell config");

            /** 生成脚本 **/
            String scriptName = "droplet.bat";
            String newScriptFileName = businessCode+".bat";
            if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) ){
                scriptName = "droplet.sh";
                newScriptFileName = businessCode+".sh";
            }
            if( "windows".equals(FileUploadUtils.SYSTEM_FORMAT) ) {
                File scriptFile = new File(panoTemplatePath + scriptName);
                if (scriptFile.exists()) {
                    // 读取模板内容
                    String scriptContext = FileUploadUtils.getFileContext(panoTemplatePath + scriptName);
                    // 生成脚本文件
                    scriptContext = scriptContext.replaceAll("%CONFIG_PATH%", programConfigDir + newConfigFileName);
                    if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
                        scriptContext = scriptContext.replaceAll("%PIC_PATH%", picPath);
                    }
                    logger.error(  "准备生成droplet文件！目录：" + panoProgramPath + newScriptFileName);
                    FileUploadUtils.writeTxtFile(panoProgramPath + newScriptFileName, scriptContext);
                    logger.error(  "生成成功！");
                } else {
                    jsonObject.remove("success");
                    jsonObject.accumulate("success", false);
                    jsonObject.accumulate("message", "模板文件droplet.bat没有找到！目录：" + panoTemplatePath + scriptName);
                    logger.error(  "模板文件droplet.bat没有找到！目录：" + panoTemplatePath + scriptName);
                    return jsonObject;
                }
            }

            // 执行脚本
            try {
                if ("windows".equals(FileUploadUtils.SYSTEM_FORMAT)) {
                    runPanoScript(panoProgramPath + newScriptFileName, businessCode, picPath);
                    // 删除脚本
                    deletePanoScript(panoProgramPath + programConfigDir + newConfigFileName, panoProgramPath + newScriptFileName);
                } else {
                    runPanoScript(panoProgramPath, businessCode, picPath);
                    // 删除脚本
                    deletePanoScript(panoProgramPath + programConfigDir + newConfigFileName, "");
                }
                jsonObject.accumulate("success",true);
                jsonObject.accumulate("message","===创建浏览器查看全景图成功===！");
            }catch (Exception e){
                logger.error(e.getMessage());
                jsonObject.accumulate("success",false);
                jsonObject.accumulate("message","===创建浏览器查看全景图异常！===");
            }
        }else{
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message","存放脚本模版的目录不存在！");
            logger.error( "存放脚本模版的目录不存在！目录：" + panoTemplatePath);
        }
        return jsonObject;
    }

    /**
     * 执行生成全景图页面的脚本
     * @param batPath
     * @param argStrings
     */
    public static void runPanoScript(String batPath,String businessCode,String... argStrings)throws Exception{
        logger.info("开始执行脚本......");

        if ( StringUtils.isBlank(businessCode) ) {
            logger.error("businessCode is null or empty");
            return ;
        }
        else {}

        // 检查目录是否存在，存在则先删除 added by zhangwenjian
        String checkPath = Utils.getPropertyName("render","app.pano.outPutPath","/sz/sdkj/sandu_resource/vr720/") + businessCode;
        File checkFile = new File(checkPath);
        if( checkFile.exists() ){
            org.apache.commons.io.FileUtils.deleteDirectory(checkFile);
        }
        else {}

        String cmd = "cmd /c start /b " + batPath + " ";
        if (argStrings != null && argStrings.length > 0) {
            for (String string : argStrings) {
                cmd += string + " ";
            }
        }
        try {
            if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) ){
                cmd = batPath + "krpanotools makepano -config="+Utils.getPropertyName("render","app.pano.configPath","templates/")+businessCode+".config "+argStrings[0];
            }
            logger.info("开始执行："+cmd);
            logger.info("runPanoScript start cmd");
            Process ps = Runtime.getRuntime().exec(cmd);
            OutputStream fos=ps.getOutputStream();
            PrintStream print=new PrintStream(fos);
//            Thread.sleep(2000);
            print.print("1\n");
            print.flush();
            print.close();
            InputStream inputStream = ps.getInputStream();
            logger.info("runPanoScript end cmd");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            logger.info("runPanoScript start read steam");
            StringBuffer cmdout = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                cmdout.append(line+"\r\n");
            }
            logger.info(cmdout.toString());
            br.close();
            inputStream.close();
            ps.destroy();
            logger.info("runPanoScript end read steam");
        } catch (Exception ioe) {
//            ioe.printStackTrace();
            logger.error("runPanoScript error, businessCode : " + businessCode +", batPath" + batPath, ioe);
        }
    }

    /**
     * 删除脚本
     * @param configPath
     * @param scriptPath
     */
    public static void deletePanoScript(String configPath,String scriptPath){
        if( StringUtils.isNotBlank(configPath) ){
            logger.info("删除生成的config文件！path="+configPath);
            FileUploadUtils.deleteFile(configPath);
        }
        if( StringUtils.isNotBlank(scriptPath) ){
            logger.info("删除生成的脚本文件！path="+configPath);
            FileUploadUtils.deleteFile(scriptPath);
        }
    }

    public static void main(String[] args) {
//        RenderConfig renderConfig = new RenderConfig();
//        renderConfig.setRenderServer("192.168.1.211");
//        renderConfig.setUserName("zwj");
//        renderConfig.setPassword("123456");
//        renderConfig.setRenderRoot("/");
//        RenderUtil.replaceFile("F:\\resources\\MaxRender\\C07_0022_001_245590_20160605161141382323", renderConfig);
//        RenderUtil.createDataFile("asdasdasdasdasdasdasdasd","f:\\resources");
//        RenderConfig renderConfig = new RenderConfig();
//        renderConfig.setStorageType("local");
//        renderConfig.setRenderRoot("f:\\resources");
//        RenderUtil.createScript(renderConfig, "AAAAAAA");
//        String a = "F:\\chengdu\\111";
//        String b = "F:\\chengdu\\222\\333";
//        RenderUtil.moveDir(a,b);
//        Date a = Utils.parseDate("2016-09-26 17:15:00","yyyy-MM-dd HH:mm:ss");
//        Date b = Utils.parseDate("2016-09-26 17:17:59","yyyy-MM-dd HH:mm:ss");
//        //////System.out.println(RenderUtil.timeDiff(a,b));
//        RenderUtil.generatePano("F:\\quanjing\\wk\\E02_0003.jpg","HEHEHEHEHEH_111");
    }
}
