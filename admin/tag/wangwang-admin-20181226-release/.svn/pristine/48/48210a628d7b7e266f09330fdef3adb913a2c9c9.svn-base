package com.sandu.api.proprietorinfo.service;

import com.sandu.api.area.model.BaseArea;
import com.sandu.api.area.output.BaseAreaListVO;
import com.sandu.api.proprietorinfo.input.ProprietorInfoUpdate;
import com.sandu.api.proprietorinfo.model.ProprietorInfo;
import com.sandu.api.shop.model.CompanyShop;
import com.sandu.api.user.model.SysUser;
import com.sandu.system.model.SysDictionary;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * decorate_order
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-18 14:00
 */
public interface ProprietorInfoService {

    /**
     * 插入
     *
     * @param proprietorInfo
     * @return
     */
    int insert(ProprietorInfo proprietorInfo);

    /**
     * 更新
     *
     * @param proprietorInfo
     * @return
     */
    int update(ProprietorInfo proprietorInfo);

    /**
     * 通过ID获取详情
     *
     * @param id
     * @return
     */
    ProprietorInfo getById(int id);

    /**
     * created by zhangchengda
     * 2018.10.18 21:04
     * 查询业主基础信息集合
     * last modified by zhangchengda
     * 2018.10.22 15:18
     * 增加分页参数
     *
     * @param proprietorInfoQuery 查询参数
     * @param start               分页start
     * @param limit               分页limit
     * @return
     */
    List<ProprietorInfo> getList(ProprietorInfo proprietorInfoQuery, Integer start, Integer limit);

    /**
     * created by zhangchengda
     * 2018.10.22 17:07
     * 根据levelId获取地区列表
     *
     * @param levelId
     * @return
     */
    List<BaseArea> getBaseAreaListByLevelId(Integer levelId);

    /**
     * created by zhangchengda
     * 2018.10.22 19:43
     * 更新业主信息
     *
     * @param proprietorInfoUpdate
     * @return
     */
    Integer updateProprietorInfo(ProprietorInfoUpdate proprietorInfoUpdate);

	List<ProprietorInfo> getById(List<Integer> ids);

    /**
     * created by zhangchengda
     * 2018.10.23 14:43
     * 获取效果图方案封面图
     *
     * @param designplanId
     * @return
     */
    String getRenderScenePlanPic(Integer designplanId);

    /**
     * created by zhangchengda
     * 2018.10.23 14:43
     * 获取全屋方案封面图
     *
     * @param designplanId
     * @return
     */
    String getFullHousePlanPic(Integer designplanId);

    CompanyShop getShopById(Integer id);

    List<SysDictionary> findAllByType(String type);

    SysDictionary findOneByTypeAndValue(String type, Integer value);

    BaseAreaListVO queryAreaByCode(String provinceCode);

    List<ProprietorInfo> listWithCustomerType(List<Integer> objects);

    Integer getListCount(ProprietorInfo proprietorInfoQuery);

    SysUser getSysUserById(Integer id);

    Integer deleteByIds(List<Integer> ids);
}
