package com.nork.job;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Created by yanghz on 2017-07-27.
 */
public class DelQRPicScheul implements Runnable {
    private static Logger logger = Logger.getLogger(DelQRPicScheul.class);
    private  String picPath;

    public DelQRPicScheul(String path){
        this.picPath = path;
    }

    @Override
    public void run() {
        if (StringUtils.isNotEmpty(picPath)){
            File file = new File(picPath);
            if (!file.exists()){
                return;
            }
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }else {
            return;
        }
    }
}
