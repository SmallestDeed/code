/*
import com.google.gson.Gson;
import com.sandu.CacheProvider;
import com.sandu.cache.RedisKeyConstans;
import com.sandu.cache.service.DesignPlanCacheService;
import com.sandu.cache.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=CacheProvider.class)
public class CacheServiceTest {

    private static final Gson GSON = new Gson();
    @Autowired
    private RedisService redisService;
    @Autowired
    private DesignPlanCacheService designPlanCacheService;

    //测试查询设计方案
    @Test
    public void getDesignPlanTest() {
        boolean b = redisService.addMap(RedisKeyConstans.SESSION_USER_OBJECT_SET, "1111111111", "222222222222");
        String a = "";
    }
}


*/
