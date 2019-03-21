package com.sandu.home.service.impl;


import com.sandu.common.util.Utils;
import com.sandu.home.dao.BaseHouseApplyMapper;
import com.sandu.home.model.BaseHouseApply;
import com.sandu.home.service.BaseHouseApplyService;
import com.sandu.user.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @version V1.0
 * @Title: BaseHouseApplyServiceImpl.java
 * @Package com.sandu.home.service.impl
 * @Description:户型房型-户型申请表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2016-10-13 11:45:31
 */
@Service("baseHouseApplyService")
public class BaseHouseApplyServiceImpl implements BaseHouseApplyService {

    @Autowired
    private BaseHouseApplyMapper baseHouseApplyMapper;

    /**
     * 新增数据
     *
     * @param baseHouseApply
     * @return int
     */
    @Override
    public int add(BaseHouseApply baseHouseApply) {
        baseHouseApplyMapper.insertSelective(baseHouseApply);
        return baseHouseApply.getId();
    }

    /**
     * 更新数据
     *
     * @param baseHouseApply
     * @return int
     */
    @Override
    public int update(BaseHouseApply baseHouseApply) {
        return baseHouseApplyMapper
                .updateByPrimaryKeySelective(baseHouseApply);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return baseHouseApplyMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseHouseApply
     */
    @Override
    public BaseHouseApply get(Integer id) {
        return baseHouseApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public BaseHouseApply sysSave(BaseHouseApply model, SysUser loginUser) {
        if (model != null) {
            if (model.getId() == null) {
                model.setUserId(loginUser.getId());
                model.setGmtCreate(new Date());
                model.setApplyTime(model.getGmtCreate());
                model.setCreator(loginUser.getNickName());
                model.setIsDeleted(0);
                if (model.getSysCode() == null || "".equals(model.getSysCode())) {
                    model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }

            model.setGmtModified(new Date());
            model.setModifier(loginUser.getNickName());
            //查询用户半个小时前是否已经提交过改户型
            int isExist =  baseHouseApplyMapper.isExistSubmit30Min(model.getLivingInfo(),loginUser.getId());
            if(isExist <= 0){
                model.setEnteringAdviceMsgFlag(1);
            }
        }
        return model;
    }

}
