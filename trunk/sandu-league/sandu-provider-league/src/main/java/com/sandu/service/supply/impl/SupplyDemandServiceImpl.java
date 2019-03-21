package com.sandu.service.supply.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 6:02 2018/4/27 0027
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.api.supply.input.SupplyDemandAdd;
import com.sandu.api.supply.model.SupplyDemandPic;
import com.sandu.api.supply.model.user.LoginUser;
import com.sandu.api.supply.service.SupplyDemandService;
import com.sandu.service.supply.dao.BaseSupplyDemandMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Title: 供求信息接口实现类
 * @Package 供求信息接口实现类
 * @Description:
 * @author weisheng
 * @date 2018/4/27 0027PM 6:02
 */
@Slf4j
@Service("supplyDemandService")
public class SupplyDemandServiceImpl implements SupplyDemandService{

    @Autowired
    private BaseSupplyDemandMapper baseSupplyDemandMapper;

    @Override
    public int add(SupplyDemandAdd supplyDemandAdd, LoginUser loginUser, MultipartFile[] files) {
        if (loginUser == null) {
            return 0;
        }

        this.saveMultipartFile(files,loginUser);

        return 0;
    }

    public SupplyDemandPic saveMultipartFile(MultipartFile[] files, LoginUser loginUser) {

    }
}
