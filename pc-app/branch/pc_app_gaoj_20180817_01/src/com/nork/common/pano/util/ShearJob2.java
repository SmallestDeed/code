package com.nork.common.pano.util;

import com.nork.common.util.Utils;

import java.io.File;
import java.util.concurrent.Callable;

public class ShearJob2 implements Callable {

    private String resId;
    private String sourcePath;
    private String targetPath;

    public ShearJob2(String resId, String sourcePath, String targetPath){
//            System.out.println(resId + "提交任务");
        this.resId = resId;
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
    }

    @Override
    public Object call() throws Exception {
        System.out.println(Utils.getCurrentDateTime() + " 开始切割资源["+resId+"]");
        long startTime = System.currentTimeMillis();
        File file = new File(targetPath);
        if( !file.exists() ){
            file.mkdirs();
        }
        OpenCVUtil.doShear(sourcePath, targetPath);
        long endTime = System.currentTimeMillis();
        System.out.println("切割资源[" + resId + "] 总共耗时：" + (endTime - startTime)/1000);
        System.out.println("切割资源[" + resId + "] 结束时间：" + Utils.getCurrentDateTime());
        return null;
    }
}