package com.nork.home.service.impl;

import com.nork.home.dao.UserHouseRecordMapper;
import com.nork.home.model.MyHouseVO;
import com.nork.home.model.UserHouseRecord;
import com.nork.home.model.search.MyHouseSearch;
import com.nork.home.model.search.OldRecordSearch;
import com.nork.home.service.UserHouseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserHouseRecordServiceImpl implements UserHouseRecordService
{
    @Autowired
    private UserHouseRecordMapper userHouseRecordMapper;

    @Override
    public Integer getRecordCount(MyHouseSearch myHouseSearch)
    {
        return userHouseRecordMapper.getRecordCount(myHouseSearch);
    }

    @Override
    public List<MyHouseVO> getRecordList(MyHouseSearch myHouseSearch)
    {
        return userHouseRecordMapper.getRecordList(myHouseSearch);
    }

    @Override
    public Integer insertRecord(UserHouseRecord userHouseRecord)
    {
        OldRecordSearch oldRecordSearch = new OldRecordSearch();
        oldRecordSearch.setUserId(userHouseRecord.getUserId());
        oldRecordSearch.setHouseId(userHouseRecord.getHouseId());
        oldRecordSearch.setPlatformId(userHouseRecord.getPlatformId());
        oldRecordSearch.setRenderType(userHouseRecord.getRenderType());
        UserHouseRecord oldRecord = userHouseRecordMapper.getOldRecord(oldRecordSearch);
        if(oldRecord == null)
        {
            Date curDate = new Date();
            userHouseRecord.setUseCount(1);
            userHouseRecord.setSysCode("" + userHouseRecord.getUserId() + curDate.getTime());
            userHouseRecord.setGmtCreate(curDate);
            userHouseRecord.setGmtModified(curDate);
            return userHouseRecordMapper.insertSelective(userHouseRecord);
        }else
        {
            UserHouseRecord updateRecord = new UserHouseRecord();
            updateRecord.setUseCount(oldRecord.getUseCount() + 1);
            updateRecord.setId(oldRecord.getId());
            updateRecord.setGmtModified(new Date());
            updateRecord.setModifier(userHouseRecord.getModifier());
            return userHouseRecordMapper.updateByPrimaryKeySelective(userHouseRecord);
        }
    }
}
