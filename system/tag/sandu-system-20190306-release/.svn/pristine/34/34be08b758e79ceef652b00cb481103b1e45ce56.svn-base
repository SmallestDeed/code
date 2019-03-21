package com.sandu.service.banner.impl;

import com.sandu.api.banner.input.ResBannerPicUpdate;
import com.sandu.api.banner.model.ResBannerPic;
import com.sandu.api.banner.service.ResBannerPicService;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.service.banner.dao.ResBannerPicMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/5/16  21:02
 */
@Service("resBannerPicService")
public class ResBannerPicServiceImpl implements ResBannerPicService{

    @Autowired
    private ResBannerPicMapper resBannerPicMapper;

    @Override
    public int addResBannerPic(ResBannerPic picAdd, LoginUser loginUser) {
       this.saveSystemInfo(picAdd,loginUser);
        int result = resBannerPicMapper.insertResBannerPic(picAdd);
        if(result>0){
           return picAdd.getId();
        }
        return 0;
    }

    @Override
    public int deleteBannerPic(Integer picId,LoginUser loginUser) {
        ResBannerPic pic = this.getPicById(picId);
        this.saveSystemInfo(pic,loginUser);
        return resBannerPicMapper.deleteResBannerPic(pic);
    }

    @Override
    public int updateBannerPic(ResBannerPicUpdate update,LoginUser loginUser) {
        ResBannerPic pic = this.getPicById(update.getPicId());
        pic = update.getPicFromPicUpdate(pic,update);
        this.saveSystemInfo(pic,loginUser);
        return resBannerPicMapper.updateResBannerPic(pic);
    }

    @Override
    public ResBannerPic getPicById(Integer picId) {
        return resBannerPicMapper.getPicById(picId);
    }

    @Override
    public List<ResBannerPic> getBannerPicList() {
        return null;
    }

    /**
     * 自动存储系统字段
     * @param pic 图片
     * @param loginUser 当前登录用户
     */
    public void saveSystemInfo(ResBannerPic pic, LoginUser loginUser) {
        if(pic != null){
            //新增
            if(pic.getId() == null){
                pic.setGmtCreate(new Date());
                if (null!=loginUser.getLoginName()){
                    pic.setCreator(loginUser.getLoginName());
                }
                pic.setIsDeleted(0);
                if(pic.getSysCode()==null || "".equals(pic.getSysCode())){
                    pic.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                }
            }
            //修改
            pic.setGmtModified(new Date());
            if (null!=loginUser.getLoginName()){
                pic.setModifier(loginUser.getLoginName());
            }
        }
    }

}
