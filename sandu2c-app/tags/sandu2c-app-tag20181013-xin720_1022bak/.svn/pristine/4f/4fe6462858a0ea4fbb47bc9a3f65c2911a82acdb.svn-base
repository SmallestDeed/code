package com.sandu.user.service;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:13 2018/7/23 0023
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.common.model.PageModel;
import com.sandu.user.model.input.UserCommisionTopAdd;
import com.sandu.user.model.search.CommissionInfoSearch;
import com.sandu.user.model.search.MyCommissionSearch;
import com.sandu.user.model.view.*;

import java.util.List;

/**
 * @Title: 用户拥挤接口
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/7/23 0023PM 3:13
 */
public interface UserCommisionService {
    UserCommissionInfoDTO getUserCommissionInfoByUserId(Integer id);

    List<NewestcommissioninfoDTO> getNewestcommissioninfo(PageModel pageModel);

    List<CommissionTopDTO> getCommissionTop(UserCommisionTopAdd userCommisionTopAdd);

    int getMyCommisionTotalByUserId(Integer id);

    int getMyCommisionMonthTotalByUserId(Integer userId,Integer year,Integer month);

    int getMonthCommissionCountByUserId(Integer id, MyCommissionSearch myCommissionSearch);

    List<MonthCommissionVo> getMonthCommissionVoListByUserId(Integer id, MyCommissionSearch myCommissionSearch);

    int getCommisionInfoCount(Integer id, CommissionInfoSearch commissionInfoSearch);

    List<CommisionInfoVo> getCommissionInfoListVoList(Integer id, CommissionInfoSearch commissionInfoSearch);

    int getExpensesCommissionTotalByUserId(Integer id, Integer year, Integer month);


}
