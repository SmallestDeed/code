package com.sandu.service.resmodel.impl;

import com.sandu.api.resmodel.model.ModelCopyLog;
import com.sandu.api.resmodel.service.ModelCopyLogService;
import com.sandu.service.resmodel.dao.ModelCopyLogDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by bvvy
 * @date 2018/4/13
 */
@Service("modelCopyLogService")
public class ModelCopyLogServiceImpl implements ModelCopyLogService {

    @Resource
    private ModelCopyLogDao modelCopyLogDao;

    @Override
    public void addModelCopyLog(ModelCopyLog modelCopyLog) {
        modelCopyLogDao.addModelCopyLog(modelCopyLog);
    }

    @Override
    public void deleteBySourceId(Long modelId) {
        modelCopyLogDao.deleteBySourceId(modelId);
    }

    @Override
    public void deleteByCompanyIdAndSourceId(Long companyId, Long modelId) {
        modelCopyLogDao.deleteByCompanyIdAndSourceId(companyId, modelId);
    }

    @Override
    public List<Long> listDeliveredModelIds(Long modelId,Long companyId) {
        return modelCopyLogDao.listDeliveredModelIds(modelId,companyId);
    }

}
