/*
package TestHouse;

import com.sandu.HouseProvider;
import com.sandu.base.dao.BaseAreaMapper;
import com.sandu.base.model.BaseArea;
import com.sandu.base.model.BaseLiving;
import com.sandu.base.model.search.BaseLivingSearch;
import com.sandu.base.service.BaseAreaService;
import com.sandu.base.service.BaseLivingService;
import com.sandu.common.util.collections.Lists;
import com.sandu.designtemplate.model.DesignTempletPutawayState;
import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.BaseHouseResult;
import com.sandu.home.model.SpaceCommon;
import com.sandu.home.model.SpaceCommonStatus;
import com.sandu.home.model.search.BaseHouseSearch;
import com.sandu.home.service.BaseHouseService;
import com.sandu.home.service.HouseSpaceNewService;
import com.sandu.home.service.SpaceCommonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HouseProvider.class)
public class TestController {
    @Autowired
    private BaseAreaMapper baseAreaMapper;
    @Autowired
    private BaseAreaService baseAreaService;
    @Autowired
    private BaseLivingService baseLivingService;
    @Autowired
    private SpaceCommonService spaceCommonService;
    @Autowired
    private BaseHouseService baseHouseService;
    @Autowired
    private HouseSpaceNewService houseSpaceNewService;


    @Test
    public void testBaseArea() {
        BaseArea area = new BaseArea();
        area.setPid("0");
        area.setLevelId(1);
        area.setIsDeleted(0);
        List<BaseArea> list = baseAreaService.getList(area);
        for (BaseArea baseArea : list) {
            System.out.println(baseArea.toString() + "/t");
        }
        String s = "";
        System.out.println(s);
    }

    @Test
    public void testBaseLiving() {
        BaseLivingSearch baseLivingSearch = new BaseLivingSearch();
        baseLivingSearch.setLivingName("45");
        baseLivingSearch.setSch_AreaLongCode_("440330");
        List<BaseLiving> livingByName = baseLivingService.getLivingByName(baseLivingSearch);
        for (BaseLiving baseLiving : livingByName) {
            System.out.println(baseLiving);
        }
    }

    @Test
    public void testSpaceCommonMapper() {
        SpaceCommon spaceCommon = spaceCommonService.get(942);
        System.out.println(spaceCommon.toString());
    }

    @Test
    public void testSpaceCommonMapper2() {
        baseAreaMapper.selectCodeName("100010");
        System.out.println("sss");
    }

    @Test
    public void getAllSpaceCommon() {
        Long s1 = System.currentTimeMillis();
        BaseHouseSearch baseHouseSearch = new BaseHouseSearch();
        baseHouseSearch.setLivingId(22763);
        baseHouseSearch.setStart(0);
        baseHouseSearch.setLimit(20);
        this.internalPermissions(baseHouseSearch);// 判断该用户该环境 拥有 看到 哪些状态的空间和样板房，并且赋值
        int total = baseHouseService.getHaveSpaceCount(baseHouseSearch);
        if (total > 0) {
            List<BaseHouse> list = baseHouseService.getHaveSpaceList(baseHouseSearch);
            for (BaseHouse baseHouse : list) {
                // 取户型的空间定位类型
                List<String> spaceTypeList = houseSpaceNewService.getSpaceTypeListByHouseId(baseHouse.getId());
                if (Lists.isEmpty(spaceTypeList)) {
                    continue;
                }
                Map<String, Integer> elementsCount = new HashMap<String, Integer>();
                for (String s : spaceTypeList) {
                    Integer i = elementsCount.get(s);
                    if (i == null) {
                        elementsCount.put(s, 1);
                    } else {
                        elementsCount.put(s, i + 1);
                    }
                }
                baseHouse.setHouseTypeStr(((elementsCount.containsKey("3") ? elementsCount.get("3") : 0) + "厅"
                        + (elementsCount.containsKey("4") ? elementsCount.get("4") : 0)) + "室"
                        + (elementsCount.containsKey("6") ? elementsCount.get("6") : 0) + "卫"
                        + (elementsCount.containsKey("5") ? elementsCount.get("5") : 0) + "厨"
                        + (elementsCount.containsKey("7") ? elementsCount.get("7") : 0) + "书"
                        + (elementsCount.containsKey("8") ? elementsCount.get("8") : 0) + "衣"
                        + (elementsCount.containsKey("9") ? elementsCount.get("9") : 0) + "其他");

            }
        }
        Long s2 = System.currentTimeMillis();
        Long s3 = (s2 - s1) / 1000;
        String str = "";
    }


    public void internalPermissions(BaseHouseSearch baseHouseSearch) {
        Integer spaceCommonStatusList[] = null;// 存放空间状态的list 用于in 查询
        Integer designTempletPutawayStateList[] = null; // 存放样板房状态的list 用于in 查询
        spaceCommonStatusList = new Integer[]{SpaceCommonStatus.IS_RELEASE};
        designTempletPutawayStateList = new Integer[]{DesignTempletPutawayState.IS_RELEASE};
        baseHouseSearch.setSpaceCommonStatusList(spaceCommonStatusList);
        baseHouseSearch.setDesignTempletPutawayStateList(designTempletPutawayStateList);
    }


    @Test
    public void testBaseLiving2() {
        BaseHouseResult baseHouseResult = new BaseHouseResult();
        baseHouseResult.setLivingName("1");
        baseHouseResult.setAreaLongCode(".440300.");

        int count = baseHouseService.getHouseCount(baseHouseResult);
        List<BaseHouseResult> list = null;
        if (count > 0) {
            list = baseHouseService.getHouseList(baseHouseResult);
        }
        String str = "";
        System.out.println(str);
    }


}
*/
