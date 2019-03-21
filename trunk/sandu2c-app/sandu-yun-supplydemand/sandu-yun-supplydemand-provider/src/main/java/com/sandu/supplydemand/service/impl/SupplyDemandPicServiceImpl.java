package com.sandu.supplydemand.service.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 1:46 2018/5/4 0004
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.nork.common.model.LoginUser;
import com.sandu.common.file.FileVO;
import com.sandu.common.file.util.file.FilePathUtil;
import com.sandu.common.util.Utils;
import com.sandu.supplydemand.dao.SupplyDemandPicMapper;
import com.sandu.supplydemand.model.SupplyDemandPic;
import com.sandu.supplydemand.service.SupplyDemandPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.sandu.constant.Punctuation.SLASH;
import static org.apache.commons.io.FilenameUtils.getBaseName;

/**
 * @Title: 供求信息图片实现类
 * @Package 供求信息图片实现类
 * @Description: 供求信息图片实现类
 * @author weisheng
 * @date 2018/5/4 0004PM 1:46
 */
@Service("supplyDemandPicService")
public class SupplyDemandPicServiceImpl implements SupplyDemandPicService{

    @Autowired
    private SupplyDemandPicMapper supplyDemandPicMapper;

    @Override
    public Long saveResPic(FileVO fileVO, String type, String absolutePath,LoginUser loginUser,String storagePath) {
        if("image".equals(type)){
            SupplyDemandPic supplyDemandPic = new SupplyDemandPic();
            Date date = new Date();
            supplyDemandPic.setGmtCreate(date);
            supplyDemandPic.setGmtModified(date);
            supplyDemandPic.setCreator(loginUser.getName());
            supplyDemandPic.setModifier(loginUser.getName());
            supplyDemandPic.setIsDeleted(0);
            supplyDemandPic.setPicFormat(FilePathUtil.getExtension(absolutePath));
            supplyDemandPic.setPicName(getBaseName(absolutePath));
            supplyDemandPic.setPicHigh(fileVO.getHeight()+"");
            supplyDemandPic.setPicWeight(fileVO.getWidth()+"");
            supplyDemandPic.setPicSize( fileVO.getSize().intValue());
            supplyDemandPic.setPicPath(SLASH +FilePathUtil.relativePath(storagePath,absolutePath));
            supplyDemandPic.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
            supplyDemandPicMapper.insertSelective(supplyDemandPic);
            return supplyDemandPic.getId();
        }
        return 0L;
    }

    @Override
    public List<SupplyDemandPic> selectSupplyDemandPic(List<Integer> coverPicIdList) {
        return supplyDemandPicMapper.selectSupplyDemandPic(coverPicIdList);
    }


}
