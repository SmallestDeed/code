package com.sandu.api.proprietorinfo.service.biz;


import com.sandu.api.proprietorinfo.input.ProprietorInfoListQuery;
import com.sandu.api.proprietorinfo.input.ProprietorInfoUpdate;
import com.sandu.api.proprietorinfo.model.ProprietorInfo;
import com.sandu.api.proprietorinfo.output.CustomerTypeVo;
import com.sandu.api.proprietorinfo.output.ProprietorInfoDetailVo;
import com.sandu.api.proprietorinfo.output.ProprietorInfoEditVo;
import com.sandu.api.proprietorinfo.output.ProvinceVo;
import com.sandu.api.user.model.SysUser;

import java.util.List;
import java.util.Map;


/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * decorate_order
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-18 14:00
 */
public interface ProprietorInfoBizService {

    /**
     * 通过ID获取详情
     *
     * @param id
     * @return
     */
    ProprietorInfo getById(int id);

    /**
     * created by zhangchengda
     * 2018.10.27 18:25
     * 查询业主信息列表总数
     *
     * @param proprietorInfoListQuery
     * @return
     */
    Integer getVoListCount(ProprietorInfoListQuery proprietorInfoListQuery);

    /**
     * created by zhangchengda
     * 2018.10.18 21:04
     * 查询业主信息列表
     *
     * @param proprietorInfoListQuery
     * @return
     */
    List<ProprietorInfoDetailVo> getVoList(ProprietorInfoListQuery proprietorInfoListQuery);

    /**
     * created by zhangchengda
     * 2018.10.22 15:21
     * 获取业主信息编辑页面信息
     *
     * @param id
     * @return
     */
    ProprietorInfoEditVo getVoById(Integer id);

    /**
     * created by zhangchengda
     * 2018.10.22 19:43
     * 更新业主信息
     *
     * @param proprietorInfoUpdate
     * @return
     */
    Integer updateProprietorInfo(ProprietorInfoUpdate proprietorInfoUpdate);

    /**
     * created by zhangchengda
     * 2018.10.22 20:04
     * 获取省市列表
     *
     * @return
     */
    List<ProvinceVo> getProvinceVoList();

    /**
     * created by zhangchengda
     * 2018.10.22 20:09
     * 获取用户类型列表
     *
     * @return
     */
    List<CustomerTypeVo> getCustomerTypeList();

	Map<Integer, ProprietorInfo> getByIds(List<Integer> ids);

	SysUser getSysUserById(Integer id);

    Integer deleteByIds(List<Integer> ids);
}
