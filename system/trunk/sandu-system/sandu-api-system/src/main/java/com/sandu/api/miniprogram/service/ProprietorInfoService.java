package com.sandu.api.miniprogram.service;

import com.sandu.api.miniprogram.input.ProprietorInfoAdd;
import com.sandu.api.miniprogram.input.ProprietorInfoQuery;
import com.sandu.api.miniprogram.input.UnionPlatformUpdate;
import com.sandu.api.miniprogram.model.ProprietorInfo;
import com.sandu.api.miniprogram.output.ProprietorInfoVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Yuxc
 * @Description:
 * @Date: Created in 18:20 2018/9/4
 */
@Component
public interface ProprietorInfoService {

    /**
     * 新增业主信息
     * @param add
     * @return
     */
    int addProprietor(ProprietorInfoAdd add);

    /**
     * 根据手机查询业主信息
     * @param mobile
     * @return
     */
    ProprietorInfo queryProprietorByPhone(String mobile,Integer businessType,Integer type);

    /**
     * 查询业主信息列表
     * @return
     */
    List<ProprietorInfo> queryProprietor();

    /**
     * 修改平台点击数量
     * @param update
     * @return
     */
    int updatePlatform(UnionPlatformUpdate update);

    /**
     * 查询预约信息
     * @param query
     * @return
     */
    List<ProprietorInfoVO> getProprietorInfoList(ProprietorInfoQuery query);

    /**
     * 查询预约信息总数
     * @param query
     * @return
     */
    Integer getProprietorInfoListCount(ProprietorInfoQuery query);

    /**
     * 更新预约信息处理结果
     * @param idList
     * @return
     */
    Integer updateProprietorInfoProcess(List<Integer> idList);
}
