package com.nork.system.model.vo;

import java.io.Serializable;

/**
 * 统计数量
 */
public class RenderPicOpenCVResult implements Serializable {

    private static final long serialVersionUID = -63810382120597777L;

    //总条数
    private int total;
    //成功数量
    private int successCount;
    //失败数量
    private int failedCount;
    //之前已处理
    private int processedCount;

    public RenderPicOpenCVResult(){}

    public RenderPicOpenCVResult(int total, int successCount, int failedCount, int processedCount) {
        this.total = total;
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.processedCount = processedCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public int getProcessedCount() {
        return processedCount;
    }

    public void setProcessedCount(int processedCount) {
        this.processedCount = processedCount;
    }
}
