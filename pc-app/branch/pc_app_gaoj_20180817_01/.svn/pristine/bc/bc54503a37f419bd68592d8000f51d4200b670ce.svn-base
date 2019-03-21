package com.nork.common.pano.util;

import com.nork.common.util.Utils;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.po.ResRenderPicPo;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.impl.ResRenderPicServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.concurrent.Callable;

public class ShearJob implements Callable {

    Logger logger = LoggerFactory.getLogger(ShearJob.class);

    private ResRenderPicPo renderPic;
    private String sourcePath;
    private String targetPath;
    private ResRenderPicMapper resRenderPicMapper;

    public ShearJob(ResRenderPicMapper resRenderPicMapper, ResRenderPicPo renderPic, String sourcePath, String targetPath){
        this.resRenderPicMapper = resRenderPicMapper;
        this.renderPic = renderPic;
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
    }

    @Override
    public Object call() throws Exception {
        logger.error("{} 开始切割资源[id:{}]", renderPic.getIndex(), renderPic.getResRenderPicId());
        long startTime = System.currentTimeMillis();
        File file = new File(targetPath);
        if( !file.exists() ){
            file.mkdirs();
        }
        OpenCVUtil.doShear(sourcePath, targetPath);
//        TimeUnit.SECONDS.sleep(5);
        //更新渲染图地址为全景图切割文件夹目录
        ResRenderPic resRenderPic = new ResRenderPic();
        resRenderPic.setId(renderPic.getResRenderPicId());
        resRenderPic.setPicPath(renderPic.getPicPath());
        resRenderPic.setModifier("xiaoxc");
        resRenderPic.setGmtModified(new Date());
        resRenderPic.setRemark("update_pic_path");
        int updateNumber = resRenderPicMapper.updateByPrimaryKeySelective(resRenderPic);
        long endTime = System.currentTimeMillis();
        logger.error("{} 切割资源[id:{}]结束 总共耗时：{}ms", renderPic.getIndex(), renderPic.getResRenderPicId(), endTime - startTime);
        return null;
    }
}
