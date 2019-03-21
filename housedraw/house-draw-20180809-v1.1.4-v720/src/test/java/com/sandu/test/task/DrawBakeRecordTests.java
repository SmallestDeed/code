package com.sandu.test.task;

import com.sandu.api.house.model.DrawBakeRecord;
import com.sandu.service.bake.dao.DrawBakeRecordMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DrawBakeRecordTests {

    @Autowired
    DrawBakeRecordMapper drawBakeRecordMapper;

    @Test
    public void test() {
        DrawBakeRecord drawBakeRecord = new DrawBakeRecord();
        drawBakeRecord.setTaskId(1L);
        drawBakeRecord.setGmtCreate(new Date());
        drawBakeRecord.setIpAddress("192.168.3.88");
        drawBakeRecordMapper.insertSelective(drawBakeRecord);
    }
}
