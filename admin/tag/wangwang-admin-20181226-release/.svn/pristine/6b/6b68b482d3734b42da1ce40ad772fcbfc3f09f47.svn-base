package com.sandu.service.mobileArea.impl.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.sandu.api.mobileArea.input.MobileAreaAdd;
import com.sandu.api.mobileArea.input.MobileAreaQuery;
import com.sandu.api.mobileArea.input.MobileAreaUpdate;
import com.sandu.api.mobileArea.model.MobileArea;
import com.sandu.api.mobileArea.service.MobileAreaService;
import com.sandu.api.mobileArea.service.biz.MobileAreaBizService;
import com.sandu.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * mobileArea
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Jul-31 14:14
 */
@Slf4j
@Service("mobileAreaBizService")
public class MobileAreaBizServiceImpl implements MobileAreaBizService {

    @Autowired
    private MobileAreaService mobileAreaService;

    @Override
    public int create(MobileAreaAdd input) {
        MobileArea.MobileAreaBuilder builder = MobileArea.builder();

        MobileArea mobileArea = builder.build();
        BeanUtils.copyProperties(input, mobileArea);

        return mobileAreaService.insert(mobileArea);
    }

    @Override
    public int update(MobileAreaUpdate input) {
        MobileArea.MobileAreaBuilder builder = MobileArea.builder();
        MobileArea mobileArea = builder.build();

        BeanUtils.copyProperties(input, mobileArea);
        //转换原字段ID
        mobileArea.setId(input.getMobileAreaId());
        return mobileAreaService.update(mobileArea);
    }



    @Override
    public MobileArea getById(int mobileAreaId) {
        return mobileAreaService.getById(mobileAreaId);
    }


}
