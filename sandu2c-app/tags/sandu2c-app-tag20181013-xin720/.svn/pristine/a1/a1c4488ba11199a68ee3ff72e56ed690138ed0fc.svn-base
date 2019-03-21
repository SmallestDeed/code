package com.sandu.user.service.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:15 2018/7/23 0023
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.common.model.PageModel;
import com.sandu.user.dao.UserCommisionMapper;
import com.sandu.user.model.input.UserCommisionTopAdd;
import com.sandu.user.model.search.CommissionInfoSearch;
import com.sandu.user.model.search.MyCommissionSearch;
import com.sandu.user.model.view.*;
import com.sandu.user.service.UserCommisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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




}
