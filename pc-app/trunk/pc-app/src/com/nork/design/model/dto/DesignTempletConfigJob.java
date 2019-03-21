package com.nork.design.model.dto;

import com.google.gson.Gson;
import com.nork.common.util.AESUtilN;
import com.nork.common.util.Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

public class DesignTempletConfigJob implements Callable {
    Logger logger = LoggerFactory.getLogger(DesignTempletConfigJob.class);
    private List<DesignPlanTem> temList;
    private String jobName;

    public DesignTempletConfigJob(List<DesignPlanTem> temList, String jobName){
        this.temList = temList;
        this.jobName = jobName;
    }

    @Override
    public Object call() throws Exception {

        int size = null!= temList ? temList.size() : 0;
        if(size > 0) {

            logger.error("开始处理样板房配置文件");
            int count = 0;
            for (DesignPlanTem designPlanTem : temList) {
                count++;
                logger.error("jobName=" + jobName + "当前处理数据第：" + count +  "条,还剩：" + (size-count) + "条");

                //配置文件路径
                String filePath = designPlanTem.getFilePath();
                String designPicPath = Utils.getAbsolutePath(filePath, null);

                //判断是否存在
                if(StringUtils.isBlank(designPicPath)){
                    logger.error("jobName=" + jobName + "出现错误当前样板房不存在配置文件id=" + designPlanTem.getPlanId());
                    continue;
                }
                File designFile = new File(designPicPath);
                if (!designFile.exists()) {
                    logger.error("jobName=" + jobName + "出现错误当前样板房配置文件资源不存在id=" + designPlanTem.getPlanId());
                    continue;
                }

                //读取配置文件信息
                String txtFile = FileUtils.readFileToString(designFile);

                //配置文件解密
                try {
                    txtFile = AESUtilN.getInstance().decrypt(txtFile);
                } catch (Exception e) {
                    logger.error("jobName=" + jobName + "出现错误当前样板房配置文件解密异常id=" + designPlanTem.getPlanId());
                    continue;
                }

                //转换JSONO
                try {
                    Object jsonObject = new Gson().fromJson(txtFile,Object.class);
                } catch (Exception e) {
                    logger.error("jobName=" + jobName + "出现错误当前样板房配置文件JSON转换异常id=" + designPlanTem.getPlanId());
                    continue;
                }
            }
        }

        logger.error("该线程执行完毕jobName="+jobName+",解析数据"+size+"条");
        return null;
    }
}
