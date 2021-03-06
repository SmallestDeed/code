package com.nork.task.controller;

import com.nork.common.model.ResponseEnvelope;
import com.nork.task.service.RefreshPicService;
import com.nork.threadpool.ThreadPool;
import com.nork.threadpool.ThreadPoolManager;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Future;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 4:08 2018/6/5 0005
 * @Modified By:
 */
@Controller
@RequestMapping("/{style}/refreshpic")
public class RefreshPicController {

    private static Logger logger = LoggerFactory.getLogger(RefreshPicController.class);
    private static final String KEY = "ADRERFFTFR112335852_dfrefFGGG514568135";

    @Autowired
    private ThreadPoolManager threadPoolManager;
    @Autowired
    private RefreshPicService refreshPicService;


    @RequestMapping("/refreshResidualPic")
    @ResponseBody
    public ResponseEnvelope refreshResidualPic(String key,HttpServletRequest request) {
        if (StringUtils.isBlank(key)) {
            return new ResponseEnvelope(false,"秘钥为空!");
        }
        if (!KEY.equals(key)) {
            return new ResponseEnvelope(false,"秘钥不正确");
        }

        Boolean result;
        try {
            result = refreshPicService.refreshResidualPic();
        } catch (Exception e) {
            result = false;
        }

        return new ResponseEnvelope(result,"修复剩余缩略图数据:"+(result==true?"success":"fail"));
    }


    @RequestMapping("/refreshpicdata")
    @ResponseBody
    public ResponseEnvelope refreshPicData(String key,HttpServletRequest request) {

        if (StringUtils.isBlank(key)) {
            return new ResponseEnvelope(false,"秘钥为空!");
        }
        if (!KEY.equals(key)) {
            return new ResponseEnvelope(false,"秘钥不正确");
        }

        //生产环境 共需刷54696条数据 2018.07.02 15:43
//        int a = 50;
//        for (int i = 0; i < a; i++) {
//            Integer start = 1100*i;
//            Integer limit = 1100;
//            syncSaveRenderPic(refreshPicService,start,limit);
//        }

        //集成环境 共需刷74801条数据 2018.07.02 15:43
        //集成环境 共需刷116488条数据 2018.07.09 17.02
        int a = 50;
        for (int i = 0; i < a; i++) {
            Integer start = 1800*i;
            Integer limit = 1800;
            syncSaveRenderPic(refreshPicService,start,limit);
        }

        //开发环境 共需刷22852条数据 2018.07.02 15:43
//        int a = 50;
//        for (int i = 0; i < a; i++) {
//            Integer start = 500*i;
//            Integer limit = 500;
//            syncSaveRenderPic(refreshPicService,start,limit);
//        }

        //刷100个测试
//        int a = 10;
//        for (int i = 0; i < a; i++) {
//            Integer start = 10 * i;
//            Integer limit = 10;
//            syncSaveRenderPic(refreshPicService, start, limit);
//        }
        return new ResponseEnvelope(true, "创建线程结束");
    }


    private ResponseEnvelope syncSaveRenderPic(RefreshPicService refreshPicService, Integer start, Integer limit) {
        ResponseEnvelope result = new ResponseEnvelope();
        ThreadPool threadPool = threadPoolManager.getThreadPool();// 取得线程池实列
        // 创建重新生成缩略图的任务
        RefreshSmallPicJob job = new RefreshSmallPicJob(start, limit, refreshPicService);
        try {
            Future<Boolean> future = threadPool.submit(job);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("RefreshPicController====>syncSaveRenderPic=====>start:" + start + ", exception:" + e);
        }

        return result;
    }


    public static void main(String[] args) {

        String largePicPath = "http://show.dev.sanduspace.com/AA/c_basedesign/2017/08/17/20/design/designPlanRecommended/render/369018_1502973270657.jpg";
        String smallPicPath = "C:\\Users\\Administrator\\Desktop\\888.jpg";

        try {
            URL fileUrl = new URL(largePicPath);
            InputStream is = fileUrl.openConnection().getInputStream();
            Thumbnails.of(fileUrl)
                    .outputQuality(0.9)
                    .size(1080, 1080)
                    .toFile(new File(smallPicPath));
        } catch (Exception e) {
            logger.error("报错了。。。。。。。。。。。。。e:" + e);
            e.printStackTrace();
        }
    }
}
