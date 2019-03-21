package com.sandu.supplydemand.service;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 1:45 2018/5/4 0004
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.nork.common.model.LoginUser;
import com.sandu.common.file.FileVO;
import com.sandu.supplydemand.model.SupplyDemandPic;

import java.util.List; /**
 * @Title: 供求信息图片接口
 * @Package 供求信息图片接口
 * @Description: 供求信息图片接口
 * @author weisheng
 * @date 2018/5/4 0004PM 1:45
 */
public interface SupplyDemandPicService {
    Long saveResPic(FileVO fileVO, String type, String absolutePath, LoginUser loginUser,String storagePath);

    List<SupplyDemandPic> selectSupplyDemandPic(List<Integer> coverPicIdList);
}
