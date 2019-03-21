package com.sandu.service.restexture.impl;

import com.sandu.api.resmodel.model.ModelAreaTextureRel;
import com.sandu.api.restexture.service.ModelAreaTextureRelService;
import com.sandu.service.resmodel.dao.ModelAreaTextureRelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Sandu
 */
@Service
public class ModelAreaTextureRelImpl implements ModelAreaTextureRelService {
    @Autowired
    private ModelAreaTextureRelDao modelAreaTextureRelDao;
    @Override
    public int insertList(List<ModelAreaTextureRel> list) {
        if(list== null || list.isEmpty()){
            return -1;
        }
        return modelAreaTextureRelDao.insertList(list);
    }

    @Override
    public int deleteByAreaIds(List<Integer> areaIds) {
        if(areaIds== null || areaIds.isEmpty()){
            return -1;
        }
        return modelAreaTextureRelDao.deleteByAreaIds(areaIds);
    }

    @Override
    public List<Integer> getIdsByAreaIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return modelAreaTextureRelDao.getIdsByAreaIds(ids);
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        return modelAreaTextureRelDao.deleteByIds(ids);
    }

	@Override
	public List<Integer> getTextureIdsById(List<Integer> idList) {
		if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }
		return modelAreaTextureRelDao.getTextureIdsById(idList);
	}

	@Override
	public List<ModelAreaTextureRel> getModelAreaTextureRelList(List<Integer> areaIds) {
		return modelAreaTextureRelDao.getModelAreaTextureRelList(areaIds);
	}

	@Override
	public void updateModelTextureInfo(ModelAreaTextureRel item) {
		modelAreaTextureRelDao.updateByPrimaryKeySelective(item);
	}
}
