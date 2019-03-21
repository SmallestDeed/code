package com.sandu.user.service.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:15 2018/7/23 0023
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.common.exception.BizException;
import com.sandu.common.model.PageModel;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.user.dao.UserCommisionMapper;
import com.sandu.user.model.OrderManage;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.input.UserCommisionTopAdd;
import com.sandu.user.model.search.CommissionInfoSearch;
import com.sandu.user.model.search.MyCommissionSearch;
import com.sandu.user.model.view.*;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserCommisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Title: 用户佣金接口实现类
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/7/23 0023PM 3:15
 */
@Service("userCommisionService")
public class UserCommisionServiceImpl implements UserCommisionService{

    @Autowired
    private UserCommisionMapper userCommisionMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDictionaryService sysDictionaryService;


    @Override
    public UserCommissionInfoDTO getUserCommissionInfoByUserId(Integer id) {
        return userCommisionMapper.selectUserCommissionInfoByUserId(id);
    }

    @Override
    public List<NewestcommissioninfoDTO> getNewestcommissioninfo(PageModel pageModel) {
        return userCommisionMapper.selectNewestcommissioninfo(pageModel);
    }

    @Override
    public List<CommissionTopDTO> getCommissionTop(UserCommisionTopAdd userCommisionTopAdd) {
        return userCommisionMapper.selectCommissionTop(userCommisionTopAdd);
    }

    @Override
    public int getMyCommisionTotalByUserId(Integer userId) {

        return userCommisionMapper.selectMyCommisionTotalByUserId(userId);
    }

    @Override
    public int getMyCommisionMonthTotalByUserId(Integer userId,Integer year,Integer month) {
        return userCommisionMapper.selectMyCommisionMonthTotalByUserId(userId,year,month);
    }

    @Override
    public int getMonthCommissionCountByUserId(Integer id, MyCommissionSearch myCommissionSearch) {
        return userCommisionMapper.selectMonthCommissionCountByUserId(id,myCommissionSearch.getYear(),myCommissionSearch.getMonth());
    }

    @Override
    public List<MonthCommissionVo> getMonthCommissionVoListByUserId(Integer id, MyCommissionSearch myCommissionSearch) {
        return userCommisionMapper.selectMonthCommissionVoListByUserId(id,myCommissionSearch.getYear(),myCommissionSearch.getMonth(),myCommissionSearch.getStart()
        ,myCommissionSearch.getLimit());
    }

    @Override
    public int getCommisionInfoCount(Integer userId, CommissionInfoSearch commissionInfoSearch) {
        return userCommisionMapper.selectCommisionInfoCount(userId,commissionInfoSearch.getYear(),commissionInfoSearch.getMonth());
    }

    @Override
    public List<CommisionInfoVo> getCommissionInfoListVoList(Integer userId, CommissionInfoSearch commissionInfoSearch) {
        return userCommisionMapper.selectCommissionInfoListVoList(userId,commissionInfoSearch.getYear(),commissionInfoSearch.getMonth(),commissionInfoSearch.getStart()
                ,commissionInfoSearch.getLimit());
    }

    @Override
    public int getExpensesCommissionTotalByUserId(Integer userId, Integer year, Integer month) {
        return userCommisionMapper.selectExpensesCommissionTotalByUserId(userId,year,month);
    }

    @Override
    public CommisionDetailsBO obtainUserCommissionDetails(Integer id) {

        CommisionDetailsBO commisionDetailsBO = new CommisionDetailsBO();

        //get UserInfo
        SysUser sysUser = sysUserService.get(id);

        if (Objects.isNull(sysUser)){
            throw new RuntimeException("get userInfo fail !!!");
        }

        String userPic;
        //get userPicPath
        if (Objects.isNull(sysUser.getPicId())){
            userPic = sysUserService.getUserDefaultPic(id);
        }else {
            userPic = sysUserService.getUserPic(sysUser.getPicId());
        }

        commisionDetailsBO.setUserPicPath(userPic);

        //get sourceCompany
        if (Objects.nonNull(sysUser.getSourceCompany())){
            SysDictionary sysDictionary = sysDictionaryService.selectOneByTypeAndValue("userSourceCompany",sysUser.getSourceCompany());
            commisionDetailsBO.setSourceCompany(sysDictionary.getName());
        }

        //获取用户返佣佣金
        Integer totalCommission = userCommisionMapper.selectTotalCommission(sysUser.getId());
        commisionDetailsBO.setTotalCommision(totalCommission);

        //get invitationPeople number
        int totalinvitationNum =  userCommisionMapper.countOrderManageByUserId(sysUser.getId());
        commisionDetailsBO.setInvitationPeople(totalinvitationNum);

        if (totalinvitationNum != 0){
            //get invitationPeople list
            List<OrderManage> orderManages = userCommisionMapper.selectOrderManageListByIntermediaryId(sysUser.getId());
            commisionDetailsBO.setOrderManages(orderManages);
        }
        return commisionDetailsBO;
    }
}
