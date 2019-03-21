package com.sandu.service.storage.impl;

import com.sandu.api.storage.model.ResModel;
import com.sandu.api.storage.service.ResModelService;
import com.sandu.common.constant.TransStatus;
import com.sandu.service.storage.dao.ResModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.sandu.util.Commoner.isNotEmpty;

/**
 * creator by bvvy
 */
@Service("resModelService")
public class ResModelServiceImpl implements ResModelService {


    @Resource
    private ResModelMapper resModelMapper;

    @Override
    public int addResModel(ResModel resModel) {
        return resModelMapper.insertSelective(resModel);
    }

    @Override
    public Optional<ResModel> getResModelDetail(long resModelId) {
        return Optional.ofNullable(resModelMapper.selectByPrimaryKey(resModelId));
    }

    @Override
    public int saveResModel(ResModel resModel) {
        return resModelMapper.updateByPrimaryKeySelective(resModel);
    }

    @Override
    public List<ResModel> listResModel(ResModel resModel) {
        return resModelMapper.selectResModel();
    }

    @Override
    public ResModel getQueue() {
        ResModel resModel = resModelMapper.getFirstNoneTransModel();
        if(isNotEmpty(resModel)) {
            ResModel tempModel = resModelMapper.selectByPrimaryKey(resModel.getId());
            tempModel.setTransStatus(TransStatus.ING.getCode());
            resModelMapper.updateByPrimaryKeySelective(tempModel);
        }
        return resModel;
    }

    @Override
    public int deleteResModel(long resModelId) {
        return resModelMapper.deleteByPrimaryKey(resModelId);
    }
}
