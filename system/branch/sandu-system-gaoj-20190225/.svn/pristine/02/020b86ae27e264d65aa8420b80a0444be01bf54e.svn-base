package com.sandu.service.banner.impl;

import com.sandu.api.banner.input.BaseBannerPositionAdd;
import com.sandu.api.banner.input.BaseBannerPositionIsExist;
import com.sandu.api.banner.input.BaseBannerPositionUpdate;
import com.sandu.api.banner.model.BaseBannerPosition;
import com.sandu.api.banner.service.BaseBannerPositionService;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.service.banner.dao.BaseBannerPositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 广告-位置 Service接口实现类
 * @author WangHaiLin
 * @date 2018/5/16  14:48
 */
@Service("baseBannerPositionService")
public class BaseBannerPositionServiceImpl implements BaseBannerPositionService {

    @Autowired
    private BaseBannerPositionMapper positionMapper;

    @Override
    public int addBannerPosition(BaseBannerPositionAdd positionAdd, LoginUser loginUser) {
        //数据转换
        BaseBannerPosition position = positionAdd.getPositionFromPositionAdd(positionAdd);
        //添加系统字段
        this.saveSystemInfo(position,loginUser);
        int result = positionMapper.insertBannerPosition(position);
        if(result!=0){
            return position.getId();
        }
        return 0;
    }

    @Override
    public int deletePosition(Integer positionId, LoginUser loginUser) {
        BaseBannerPosition position = this.getPositionById(positionId);
        this.saveSystemInfo(position,loginUser);
        return positionMapper.deletePosition(position);
    }

    @Override
    public int updateBannerPosition(BaseBannerPositionUpdate positionUpdate, LoginUser loginUser) {
        BaseBannerPosition position = this.getPositionById(positionUpdate.getPositionId());
        position = positionUpdate.getPositionFromPositionUpdate(position,positionUpdate);
        this.saveSystemInfo(position,loginUser);
        return positionMapper.updateBannerPosition(position);
    }

    @Override
    public BaseBannerPosition getPositionIsExist(BaseBannerPositionIsExist isExist) {
        return positionMapper.getPositionIsExist(isExist);
    }

    @Override
    public BaseBannerPosition getPositionById(Integer positionId) {
        return positionMapper.getPositionById(positionId);
    }

    @Override
    public List<BaseBannerPosition> getPositionListByType(Integer type) {
        return positionMapper.getPositionListByType(type);
    }


    /**
     * 自动存储系统字段
     * @param position 位置
     * @param loginUser 当前登录用户
     */
    public void saveSystemInfo(BaseBannerPosition position, LoginUser loginUser) {
        if(position != null){
            //新增
            if(position.getId() == null){
                position.setGmtCreate(new Date());
                position.setCreator(loginUser.getLoginName());
                position.setIsDeleted(0);
                if(position.getSysCode()==null || "".equals(position.getSysCode())){
                    position.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                }
            }
            //修改
            position.setGmtModified(new Date());
            position.setModifier(loginUser.getLoginName());
        }
    }

}
