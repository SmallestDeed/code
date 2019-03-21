package com.sandu.service.product.impl;

import com.sandu.api.product.model.BaseProductStyle;
import com.sandu.api.product.model.bo.DesignPlanStyleBO;
import com.sandu.api.product.service.BaseProductStyleService;
import com.sandu.service.product.dao.BaseProductStyleDao;
import com.sandu.util.Commoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


/**
 * @author Sandu
 */
@Service("baseProductStyleService")
public class BaseProductStyleServiceImpl implements BaseProductStyleService {
    @Autowired
    private BaseProductStyleDao baseProductStyleDao;

    @Override
    public int saveBaseProductStyle(BaseProductStyle baseProductStyle) {
        return baseProductStyleDao.save(baseProductStyle);
    }

    @Override
    public int deleteBaseProductStyleById(long id) {
        return baseProductStyleDao.deleteById(id);
    }

    @Override
    public void updateBaseProductStyle(BaseProductStyle baseProductStyle) {
        baseProductStyleDao.update(baseProductStyle);
    }

    @Override
    public BaseProductStyle getBaseProductStyleById(long id) {
        return baseProductStyleDao.getById(id);
    }

    @Override
    public List<BaseProductStyle> getBaseProductStyleByIds(List<Long> ids) {
        if (Commoner.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return baseProductStyleDao.getByIds(ids);
    }

    @Override
    public List<String> getBaseProductStyleNameByIds(List<Long> ids) {
        if (Commoner.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return baseProductStyleDao.getNamesByIds(ids);
    }

    @Override
    public List<BaseProductStyle> listBaseProductStyleIdAndName() {
        return baseProductStyleDao.listBaseProductStyleIdAndName();
    }

    @Override
    public List<BaseProductStyle> listBasePlanStyleIdAndName(Integer numa) {
        List<BaseProductStyle> lists = baseProductStyleDao.listBasePlanStyleIdAndName(numa, null);
        if (lists != null && lists.size() > 0) {
            return baseProductStyleDao.listBasePlanStyleIdAndName(null, lists.get(0).getId().intValue());
        }
        return null;
    }

    @Override
    public List<DesignPlanStyleBO> listDesignPlanStyle() {
        return baseProductStyleDao.listDesignPlanStyle();
    }
}
