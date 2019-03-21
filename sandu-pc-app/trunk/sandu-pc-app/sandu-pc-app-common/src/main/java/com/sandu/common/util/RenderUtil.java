package com.sandu.common.util;

import java.io.BufferedReader;
import java.io.File;
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
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2015/11/13.
 */
public class RenderUtil {
    private static Logger logger = Logger.getLogger(RenderUtil.class);
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
        //String tempPath = Utils.getValue("onekeydesign.designPlan.maxrender.config.upload.path","/data/").trim();
        String tempPath = Utils.getPropertyName("config/res", "design.designPlan.maxrender.config.upload.path", "/data/").trim();
        logger.info("配置文件tempPath="+tempPath);
        String datafile = Utils.getValue("onekeydesign.designPlan.maxrender.config.name","Data.txt").trim();
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
