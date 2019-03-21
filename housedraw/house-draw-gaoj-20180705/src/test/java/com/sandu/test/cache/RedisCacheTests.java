package com.sandu.test.cache;

import com.sandu.api.house.input.GroupProductQuery;
import com.sandu.api.product.input.BaseProductQuery;
import com.sandu.api.product.service.BaseProductService;
import com.sandu.api.product.service.GroupProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisCacheTests {

    @Autowired
    RedisTemplate<String, ?> redisTemplate;

    private final static String HOUSE_DRAW_SOFT_PRODUCT = "HouseDrawSoftProduct";

//    @Test
    public void testMap() {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        Boolean hasKey = hashOperations.hasKey(HOUSE_DRAW_SOFT_PRODUCT, "123456789");
        if (!hasKey) {
            hashOperations.put(HOUSE_DRAW_SOFT_PRODUCT, "123456789", "FFFFFFFFFFFFFFFFF");
            redisTemplate.expire(HOUSE_DRAW_SOFT_PRODUCT, 10000, TimeUnit.MILLISECONDS);
        } else {
            Object value = hashOperations.get(HOUSE_DRAW_SOFT_PRODUCT, "123456789");
            log.info("value => {}", value);
        }

        ValueOperations<String, ?> valueOperations = redisTemplate.opsForValue();
    }

    @Autowired
    GroupProductService groupProductService;

//    @Test
    public void testGroupProduct() {
        GroupProductQuery query = new GroupProductQuery();
        query.setPageNum(0);
        query.setPageSize(50);
        query.setGroupType(8);
        query.setPutawayState(new Integer[]{1, 3});

        Map<String, Object> map = groupProductService.listGroupProduct(query, true);
        log.info("{}", map);
    }

    @Autowired
    BaseProductService baseProductService;

//    @Test
    public void testProduct() {
        BaseProductQuery query = new BaseProductQuery();
        query.setPageNum(0);
        query.setPageSize(50);
        query.setProductCode("baimo");
        query.setProductTypeValue("13");
        query.setPutawayState(new Integer[]{1, 3});

        Map<String, Object> map = baseProductService.listBaimoProduct(query, true);
        log.info("{}", map);
    }
}
