package com.sandu.test.draw;

import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.house.service.DesignTempletJumpPositionRelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class V720Tests {

    @Autowired
    DesignTempletJumpPositionRelService designTempletJumpPositionRelService;

    @Test
    public void test() {
        DrawBaseHouse drawHouse = new DrawBaseHouse();
        drawHouse.setId(35L);
        designTempletJumpPositionRelService.transformJumpPositionRel(drawHouse);
    }
}
