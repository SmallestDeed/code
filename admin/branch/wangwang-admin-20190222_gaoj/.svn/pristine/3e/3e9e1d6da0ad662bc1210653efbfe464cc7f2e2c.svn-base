package com.sandu.service.resmodel.impl;

import com.sandu.api.resmodel.model.ModelAreaRel;
import com.sandu.api.resmodel.service.ModelAreaRelService;
import com.sandu.service.resmodel.dao.ModelAreaRelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.sandu.util.Commoner.isEmpty;

/**
 * @author Sandu
 */
@Service("modelAreaRelService")
public class ModelAreaRelServiceImpl implements ModelAreaRelService {
    @Autowired
    private ModelAreaRelDao modelAreaRelDao;
    @Override
    public int saveModelAreaRelList(List<ModelAreaRel> list) {
        if(list.isEmpty()){
            return 0;
        }
        list.forEach(modelAreaRelDao::insert);
        return 1;
//        return modelAreaRelDao.insertList(list);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ModelAreaRel> listByModelId(Integer modelId) {
        return modelAreaRelDao.listByModelId(modelId);
    }


    @Transactional(readOnly = true)
    @Override
    public List<ModelAreaRel> listByModelIds(List<Long> ids) {
        if(isEmpty(ids)) {
            return Collections.emptyList();
        }
        return modelAreaRelDao.listByModelIds(ids);
    }

    @Override
    public void deleteAreaByModelId(Integer modelId) {
        modelAreaRelDao.deleteAreaByModelId(modelId);
    }

	@Override
	public int updateModelAreaRel(ModelAreaRel rel) {
		return modelAreaRelDao.updateByPrimaryKeySelective(rel);
	}
}
