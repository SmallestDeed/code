package com.sandu.service.base.impl;

import com.sandu.api.base.model.ResPic;
import com.sandu.api.base.output.ResPicVo;
import com.sandu.api.base.service.ResPicService;
import com.sandu.service.base.dao.ResPicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("resPicService")
public class ResPicServiceImpl implements ResPicService {

    private final ResPicMapper resPicMapper;

    @Autowired
    public ResPicServiceImpl(ResPicMapper resPicMapper) {
        this.resPicMapper = resPicMapper;
    }

    @Override
    public ResPic getResPicById(Integer id) {
        return resPicMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<String> getPathListByIdList(List<Integer> idList) {
        return resPicMapper.getPathListByIdList(idList);
    }

    @Override
    public List<ResPicVo> getByIdList(List<Integer> idList) {
        return resPicMapper.getByIdList(idList);
    }

}
