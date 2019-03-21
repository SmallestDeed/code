package com.sandu.service.task.impl;

import com.sandu.api.house.service.DrawResFileService;
import com.sandu.api.space.bo.DrawSpaceFileBO;
import com.sandu.api.task.input.CleanResTaskConfigUpdate;
import com.sandu.api.task.service.CleanDrawResFileTaskService;
import com.sandu.util.FileUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/24
 */

@Slf4j
@Setter
@Getter
@Service
public class CleanDrawResFileTaskServiceImpl implements CleanDrawResFileTaskService {

    @Value("${clean.query.limit:10000}")
    private Integer limit;

    @Value("${clean.min.file-id:0}")
    private Long minFileId;

    @Value("${clean.max.file-id:15000000}")
    private Long maxFileId;

    @Value("${clean.enable:true}")
    private Boolean enable;

    private final static Integer DEFAULTS_EXEC_COUNT = 10;

    @Autowired
    DrawResFileService drawResFileService;

    @Override
    @Transactional
    public void cleanDrawResFile() {
        log.info("enable={}, limit={}, minFileId={}, maxFileId={}", enable, limit, minFileId, maxFileId);
        if (enable) {
            for (int index = 0; index < DEFAULTS_EXEC_COUNT; index++) {
                this.cleanDrawResFile0();
            }
        }
    }

    @Override
    public void updateCleanResTaskConfig(CleanResTaskConfigUpdate updateConf) {
        if (updateConf.getEnable() != null) {
            this.enable = updateConf.getEnable();
            log.info("更新 enable = {}", updateConf.getEnable());
        }
        if (updateConf.getLimit() != null) {
            this.limit = updateConf.getLimit();
            log.info("更新 limit = {}", updateConf.getLimit());
        }
        if (updateConf.getMinFileId() != null) {
            this.minFileId = updateConf.getMinFileId();
            log.info("更新 minFileId = {}", updateConf.getMinFileId());
        }
        if (updateConf.getMaxFileId() != null) {
            this.maxFileId = updateConf.getMaxFileId();
            log.info("更新 maxFileId = {}", updateConf.getMaxFileId());
        }
    }

    private void cleanDrawResFile0() {
        List<DrawSpaceFileBO> drawSpaceFiles = drawResFileService.listTaskCleanDrawResFile(limit, minFileId, maxFileId);
        if (drawSpaceFiles == null || drawSpaceFiles.isEmpty()) {
            log.info("没有需要清除的文件资源");
            return;
        }

        // delete file
        FileUtils.delete(drawSpaceFiles);

        // delete db
        drawResFileService.batchDeleteDrawResFile2(drawSpaceFiles);
    }
}
