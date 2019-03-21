package com.sandu.service.banner.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.banner.input.ActShareImgAdminAdd;
import com.sandu.api.banner.input.MiniProIndexDialogAdd;
import com.sandu.api.banner.model.ActShareImgAdmin;
import com.sandu.api.banner.model.MiniProIndexDialog;
import com.sandu.api.banner.service.ActShareImgAdminService;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.service.banner.dao.ActShareImgAdminMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@Service("actShareImgAdminService")
@Slf4j
public class ActShareImgAdminServiceImpl implements ActShareImgAdminService {

    @Autowired
    private ActShareImgAdminMapper actShareImgAdminMapper;

    @Override
    public int addActShareImgAdmin(ActShareImgAdminAdd actShareImgAdminAd, LoginUser loginUser) {

        ActShareImgAdmin actShareImgAdmin = transferBean(actShareImgAdminAd,loginUser);
        return actShareImgAdminMapper.insert(actShareImgAdmin);
    }

    @Override
    public int updateActShareImgAdmin(ActShareImgAdmin actShareImgAdmin, LoginUser loginUser) {
        saveSystemInfo(actShareImgAdmin,loginUser);
        return actShareImgAdminMapper.update(actShareImgAdmin);
    }

    @Override
    public int deltedActShareImg(Long id, LoginUser loginUser) {
        ActShareImgAdmin build = ActShareImgAdmin.builder().id(id).isDeleted(1).build();
        saveSystemInfo(build,loginUser);
        build.setIsDeleted(1);
        return actShareImgAdminMapper.update(build);
    }

    @Override
    public PageInfo<ActShareImgAdmin> getList(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<ActShareImgAdmin> lists =  actShareImgAdminMapper.select(page,limit);
        return new PageInfo<>(lists);
    }

    @Override
    public List<ActShareImgAdmin> getSXWActShareImg(Long bannerId) {
        return actShareImgAdminMapper.getActShareImgByBannerId(bannerId);
    }

    private ActShareImgAdmin transferBean(ActShareImgAdminAdd actShareImgAdminAd, LoginUser loginUser) {
        ActShareImgAdmin actShareImgAdmin = ActShareImgAdmin.builder().build();
        BeanUtils.copyProperties(actShareImgAdminAd,actShareImgAdmin);
        saveSystemInfo(actShareImgAdmin,loginUser);
        //对URL进行编码
        return actShareImgAdmin;
    }


    /**
     * 自动存储系统字段
     * @param actShareImgAdmin MiniProIndexDialog实体
     * @param loginUser 当前登录用户
     */
    public void saveSystemInfo(ActShareImgAdmin actShareImgAdmin, LoginUser loginUser) {
        if(actShareImgAdmin != null){
            //新增
            if(actShareImgAdmin.getId() == null){
                actShareImgAdmin.setGmtCreate(new Date());
                if (null!=loginUser.getLoginName()){
                    actShareImgAdmin.setCreator(loginUser.getLoginName());
                }
                actShareImgAdmin.setIsDeleted(0);
            }
            //修改
            actShareImgAdmin.setGmtModified(new Date());
            if (null!=loginUser.getLoginName()){
                actShareImgAdmin.setModifier(loginUser.getLoginName());
            }
        }
    }
}
