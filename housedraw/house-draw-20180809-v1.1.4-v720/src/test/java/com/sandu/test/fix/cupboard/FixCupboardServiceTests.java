package com.sandu.test.fix.cupboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.fix.model.FixCupboardCallbackBO;
import com.sandu.api.fix.service.FixCupboardService;
import com.sandu.api.house.service.DrawSpaceCommonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class FixCupboardServiceTests {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FixCupboardService fixCupboardService;

    @Autowired
    DrawSpaceCommonService drawSpaceCommonService;

    // @Test
    public void test() {
        // DrawSpaceCommon drawSpaceCommon = drawSpaceCommonService.get(232726L);
        // drawSpaceCommonService.handlerSpaceCommon(drawSpaceCommon);

        fixCupboardService.callback(new FixCupboardCallbackBO());
    }
}
