package com.nork.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;

/**
 * Created by Administrator on 2015/7/6.
 */
public class LogUtil {

    private static ConfigUtil configUtil = ConfigUtil.getInstance("app");
    private static String savePath = configUtil.getValue("app.operationLog.path");
    private static String fileName = configUtil.getValue("app.operationLog.fileName");

    /**
     * 日志存储格式
     */
    private static String logLayout = "{0}#{1}#{2}#{3}#{4}";

    private static final String logTitle = "时间#操作人#模块#功能#链接";

    /**
     * 记录操作日志
     */
    public static void recordLog(HttpServletRequest request){
        String currentPath = request.getServletPath();
        if( !currentPath.endsWith(".htm") ){
            return;
        }

        //得到当前日期
        String currentDate = Utils.formatDate(new Date(),"yyyyMMdd");
        File filePath = new File(savePath+"/"+currentDate+"/");
        try {
            if( !filePath.exists() ){
                filePath.mkdirs();
            }
            File logFile = new File(savePath+"/"+currentDate+"/"+fileName);
            boolean isNewFile = logFile.createNewFile();
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(logFile,true),"GBK");
            //如果为新创建的文件则加上标题
            if( isNewFile ){
                osw.write(logTitle+"\r\n");
            }
            //获取登录人
            LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
            String[] menuParams = currentPath.split("/");
            String model = "";
            String function = "";
            if( menuParams.length == 3 ){
                model = menuParams[1];
                function = menuParams[2];
            }else if( menuParams.length == 5 ){
                model = menuParams[2];
                function = menuParams[3];
            }
            osw.write(MessageFormat.format(logLayout,Utils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"),loginUser.getLoginName(),model,function,currentPath)+"\r\n");
            osw.close();
            //写入日志后判断文件大小
            long fileLength = logFile.length();
            //如果文件大于10M，则另起一个文件
//            if( fileLength / 10485760 >= 10 ){
            if( fileLength >= 1024 ){
                logFile.renameTo(new File(savePath+"/"+currentDate+"/"+logFile.getName()+"_"+Utils.formatDate(new Date(), "yyyyMMddHHmmss")+".log"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        File file = new File("D:\\1.txt");
        file.renameTo(new File("D:\\12.txt"));
    }
}
