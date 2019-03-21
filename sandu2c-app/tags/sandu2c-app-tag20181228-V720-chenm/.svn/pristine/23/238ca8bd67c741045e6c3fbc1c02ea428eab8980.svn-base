package com.sandu.user.dao;


import com.sandu.common.model.PageModel;
import com.sandu.user.model.OrderManage;
import com.sandu.user.model.UserCommision;
import com.sandu.user.model.search.CommissionInfoSearch;
import com.sandu.user.model.search.MyCommissionSearch;
import com.sandu.user.model.view.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserCommisionMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(UserCommision record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(UserCommision record);

    /**
     *
     * @mbggenerated
     */
    UserCommision selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserCommision record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserCommision record);

    UserCommissionInfoDTO selectUserCommissionInfoByUserId(@Param("userId") Integer id);

    List<NewestcommissioninfoDTO> selectNewestcommissioninfo(PageModel pageModel);

    List<CommissionTopDTO> selectCommissionTop(PageModel pageModel);

    int selectMyCommisionTotalByUserId(Integer userId);

    int selectMyCommisionMonthTotalByUserId(@Param("userId") Integer userId,@Param("year")Integer year,@Param("month")Integer month);

    int selectMonthCommissionCountByUserId(@Param("userId") Integer userId, @Param("year") Integer year,@Param("month") Integer month);

    List<MonthCommissionVo> selectMonthCommissionVoListByUserId(@Param("userId") Integer userId, @Param("year") Integer year
            ,@Param("month") Integer month   ,@Param("start") Integer start   ,@Param("limit") Integer limit);

    int selectCommisionInfoCount(@Param("userId")Integer userId, @Param("year") Integer year,@Param("month") Integer month);

    List<CommisionInfoVo> selectCommissionInfoListVoList(@Param("userId") Integer userId, @Param("year") Integer year
            ,@Param("month") Integer month   ,@Param("start") Integer start   ,@Param("limit") Integer limit);

    int selectExpensesCommissionTotalByUserId(@Param("userId") Integer userId, @Param("year") Integer year,@Param("month") Integer month);

    Integer selectTotalCommission(Integer userId);

    int countOrderManageByUserId(Integer id);

    List<OrderManage> selectOrderManageListByIntermediaryId(Integer id);
}