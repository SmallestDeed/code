package com.sandu.test.draw;

import com.sandu.api.house.input.DrawBaseProductDTO;
import com.sandu.api.house.model.DrawBaseProduct;
import com.sandu.api.house.model.DrawDesignTempletProduct;
import com.sandu.api.house.service.DrawDesignTempletProductService;
import com.sandu.api.product.service.DrawBaseProductService;
import com.sandu.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class V2CallbackTests {

    @Autowired
    DrawBaseProductService drawBaseProductService;

    @Autowired
    DrawDesignTempletProductService drawDesignTempletProductService;

//    @Test
    public void testListDrawBaseProductByIds() {
        List<DrawBaseProduct> drawBaseProducts = drawBaseProductService.listDrawBaseProductByIds(Arrays.asList(11123L, 12312L, 39399L));
        Map<Long, DrawBaseProduct> map = drawBaseProducts.stream().collect(Collectors.toMap(DrawBaseProduct::getId, dp -> dp, (p, n) -> n));
        log.debug("{}", map);
    }

//    @Test
    public void testBatchUpdateDtp() {
        DrawDesignTempletProduct drawDesignTempletProduct1 = new DrawDesignTempletProduct();
        drawDesignTempletProduct1.setId(1L);
        drawDesignTempletProduct1.setPosIndexPath("test挂节点信息更新111");
        drawDesignTempletProduct1.setPosName("test挂节点信息更新2");
        drawDesignTempletProduct1.setProductSequence(Utils.replaceString("test挂节点信息更新3", "/"));
        // 产品空间坐标，烘焙完成后获取
        drawDesignTempletProduct1.setProPosition("test挂节点信息更新4");

        DrawDesignTempletProduct drawDesignTempletProduct2 = new DrawDesignTempletProduct();
        // 挂节点信息更新
        drawDesignTempletProduct2.setId(2L);
        drawDesignTempletProduct2.setPosIndexPath("test挂节点信息更新");
        drawDesignTempletProduct2.setPosName("test挂节点信息更新");
        drawDesignTempletProduct2.setProductSequence(Utils.replaceString("test挂节点信息更新", "/"));
        // 产品空间坐标，烘焙完成后获取
        drawDesignTempletProduct2.setProPosition("test挂节点信息更新");


        ArrayList<DrawDesignTempletProduct> drawDesignTempletProducts = new ArrayList<>();
        drawDesignTempletProducts.add(drawDesignTempletProduct1);
        drawDesignTempletProducts.add(drawDesignTempletProduct2);

        // 批量更新挂节点、埋点信息
        drawDesignTempletProductService.batchUpdateDesignTempletProduct(drawDesignTempletProducts);
        log.debug("{}", drawDesignTempletProducts);
    }

//    @Test
    public void testBatchUpdateBp() {
        // 批量更新windows u3d model 资源
        DrawBaseProduct drawBaseProduct1 = new DrawBaseProduct();
        drawBaseProduct1.setId(1L);
        drawBaseProduct1.setWindowsU3dmodelId(55555);

        DrawBaseProduct drawBaseProduct2 = new DrawBaseProduct();
        drawBaseProduct2.setId(2L);
        drawBaseProduct2.setWindowsU3dmodelId(999999);

        ArrayList<DrawBaseProduct> drawBaseProducts = new ArrayList<>();
        drawBaseProducts.add(drawBaseProduct1);
        drawBaseProducts.add(drawBaseProduct2);

        drawBaseProductService.batchUpdateDrawBaseProduct(drawBaseProducts);
        log.debug("{}", drawBaseProducts);
    }
}
