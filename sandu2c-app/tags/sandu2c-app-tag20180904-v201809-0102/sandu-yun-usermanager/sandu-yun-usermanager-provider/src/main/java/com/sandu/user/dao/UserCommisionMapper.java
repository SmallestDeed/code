package com.sandu.user.dao;


import com.sandu.common.model.PageModel;
import com.sandu.user.model.UserCommision;
import com.sandu.user.model.view.CommissionTopDTO;
import com.sandu.user.model.view.NewestcommissioninfoDTO;
import com.sandu.user.model.view.UserCommissionInfoDTO;
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
}