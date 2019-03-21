package com.sandu.service.system.impl;

import com.sandu.api.system.model.ResPic;
import com.sandu.api.system.service.ResPicService;
import com.sandu.service.system.dao.ResPicDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resPicService")
public class ResPicServiceImpl implements ResPicService {

    @Autowired
    private ResPicDao resPicDao;

    /**
     * 获取数据详情
     *
     * @param id
     * @return ResPic
     */
    @Override
    public ResPic get(Long id) {
        return resPicDao.selectByPrimaryKey(id);
    }

	@Override
	public List<ResPic> getByIds(List<Long> ids) {
		// TODO Auto-generated method stub
		return resPicDao.selectByIds(ids);
	}

    @Override
    public int save(ResPic resPic) {
        resPicDao.insertResPic(resPic);
        return resPic.getId();
    }

}
