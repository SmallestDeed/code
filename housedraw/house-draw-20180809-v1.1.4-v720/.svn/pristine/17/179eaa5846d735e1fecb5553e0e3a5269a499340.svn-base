package com.sandu.test.draw;

import com.google.common.collect.Lists;
import com.sandu.api.house.service.DrawResFileService;
import com.sandu.api.space.bo.DrawSpaceFileBO;
import com.sandu.service.file.dao.DrawResFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DrawResFileTests {

    @Autowired
    DrawResFileService drawResFileService;

    @Autowired
    DrawResFileMapper drawResFileMapper;

    @Test
    public void test() {
        // drawResFileService.clearDrawResFileResource(Lists.newArrayList(4L), 1);
        List<DrawSpaceFileBO> drawSpaceFileBOS = drawResFileMapper.listDrawResFileByDrawSpaceId(Lists.newArrayList(22L), 1);
        log.info("{}", drawSpaceFileBOS);
    }
}
