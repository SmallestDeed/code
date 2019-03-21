package com.nork.common.pano;

import com.nork.common.pano.util.OpenCVUtil;
import com.nork.common.util.Utils;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.po.ResRenderPicPo;
import com.nork.system.service.ResRenderPicService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class ShearRenderPicJob implements Callable {

    Logger logger = LoggerFactory.getLogger(ShearRenderPicJob.class);

    private List<ResRenderPicPo> renderPicPoList;
    private ResRenderPicService resRenderPicService;
    private String jobName;

    public ShearRenderPicJob(ResRenderPicService resRenderPicService, List<ResRenderPicPo> renderPicPoList, String jobName){
        this.resRenderPicService = resRenderPicService;
        this.renderPicPoList = renderPicPoList;
        this.jobName = jobName;
    }

    @Override
    public Object call() throws Exception {
        logger.error("{} 线程{}：开始切割资源...", Utils.getCurrentDateTime(), jobName);
        long startTime = System.currentTimeMillis();
        for (ResRenderPicPo resPo : renderPicPoList) {
            long startDate = System.currentTimeMillis();
            //需切割的目标图片文件
            String picPath = resPo.getPicPath();
            if (!StringUtils.isEmpty(picPath)) {
                //追加根目录
                String dbPicPath = Utils.getAbsolutePath(picPath, null);
                File picfile = new File(dbPicPath);
                //不存在跳过
                if (!picfile.exists()) {
                    logger.error("全景图物理文件不存在！资源[{}]:picPath={}" ,resPo.getResRenderPicId() , dbPicPath);
                    continue;
                }
                //是文件夹跳过
                if (picfile.exists() && picfile.isDirectory()) {
                    continue;
                } else {
                    //相对路径 已切割图片地址制作新的文件夹地址
                    String renderPicDir = picPath.substring(0, picPath.lastIndexOf("."));
                    //绝对路径 追加根目录
                    String dbRenderPicDir = Utils.getAbsolutePath(renderPicDir+ "/", null);
                    if (!StringUtils.isEmpty(dbRenderPicDir)) {
                        File newFile = new File(dbRenderPicDir);
                        // 不存在则创建文件夹
                        if (!newFile.exists()) {
                            newFile.mkdir();
                        }
                        try {
                            logger.info("全景图dbPicPath:{}切割到dbRenderPicDir:{}", dbPicPath, dbRenderPicDir);
                            //ShearJob shearJob = new ShearJob(resRenderPicMapper, resPo, dbPicPath, dbRenderPicDir);
                            //ThreadPool threadPool = threadPoolManager.getThreadPool();
                            //threadPool.submit(shearJob);
                            OpenCVUtil.doShear(dbPicPath, dbRenderPicDir);
                            //TimeUnit.SECONDS.sleep(5);
                            //更新渲染图地址为全景图切割文件夹目录
                            ResRenderPic resRenderPic = new ResRenderPic();
                            resRenderPic.setId(resPo.getResRenderPicId());
                            resRenderPic.setPicPath(renderPicDir);
                            resRenderPic.setModifier("xiaoxc");
                            resRenderPic.setGmtModified(new Date());
                            resRenderPic.setRemark("update_pic_path");
                            int updateNumber = resRenderPicService.update(resRenderPic);
                            if (0 < updateNumber) {
                                logger.error("{} 线程{} 切割资源[{}]成功,数据更新OK！耗时：{}ms", Utils.getCurrentDateTime(), jobName, resPo.getResRenderPicId(), System.currentTimeMillis() - startDate);
                            }
                        } catch (Exception e) {
                            logger.error("全景图切割失败!资源[{}],Exception:{}", resPo.getResRenderPicId() , e);
                            continue;
                        }
                    }
                }
            } else {
                logger.error("渲染图地址为空！资源[{}]", resPo.getResRenderPicId());
                continue;
            }
        }

        logger.error("{} 切割资源结束 总共耗时：{}ms", Utils.getCurrentDateTime(), System.currentTimeMillis() - startTime);
        return null;
    }
}
