package com.sandu.test.draw;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sandu.api.house.model.SystemDictionary;
import com.sandu.api.house.service.SystemDictionaryService;
import com.sandu.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/25
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SystemDictionaryServiceTests {

    @Autowired
    SystemDictionaryService systemDictionaryService;

//    @Test
    public void testDict() {
        SystemDictionary restaurant1 = systemDictionaryService.findOneByValueKey("restaurant");
        log.info("{}", restaurant1);

        SystemDictionary restaurant2 = systemDictionaryService.findOneByValueKey("restaurant");
        log.info("{}", restaurant2);
    }

//    @Test
    public void testArea() {
        SystemDictionary restaurant1 = systemDictionaryService.findOneByTypeAndArea("restaurant", 40D);
        log.info("{}", restaurant1);

        SystemDictionary restaurant2 = systemDictionaryService.findOneByTypeAndArea("restaurant", 40D);
        log.info("{}", restaurant2);
    }

//    @Test
    public void testJson() {
        String json = "[{\"id\":1645,\"name\":\"0~19\",\"type\":\"restaurant\",\"valuekey\":\"RT03\",\"value\":17,\"att1\":\"\",\"att2\":\"03\",\"att3\":null,\"att4\":\"0\",\"att5\":\"19.49\",\"att6\":\"C03\",\"items\":null}]";
        List<SystemDictionary> dicts = JSON.parse(json, new TypeReference<List<SystemDictionary>>() {
        });
    }
}
