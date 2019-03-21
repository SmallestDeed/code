package com.sandu.service.product.impl.biz;

import com.sandu.api.product.model.bo.ProductBO;
import com.sandu.api.product.model.po.ProductQueryPO;
import com.sandu.api.product.output.ProductVO;
import com.sandu.api.product.service.biz.ProductBizService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class ProductBizServiceImplTest {

    @Autowired
    private ProductBizService productBizService;

    @Test
    public void findProductIdsByCategoryCodes() {
    }

    @Test
    public void saveProductBiz() {
    }
//    @Test
//    public void saveProductBiz() {
//        productBizService.syncProductTypeMark();
//    }

    @Test
    public void findProductInfoByProductId() throws IOException {
        ProductBO bo = productBizService.findProductInfoByProductId(176, "test");
        ProductVO vo = new ProductVO();
        BeanUtils.copyProperties(bo, vo);
        vo.setSaleUnitId(bo.getSaleUnitValue());
        System.out.println("-------------");

    }

    @Test
    public void allotProduct() {
    }

    @Test
    public void putawayProduct() {

    }

    @Test
    public void queryProductByParam() {
        ProductQueryPO po = new ProductQueryPO();
        po.setPage(0);
        po.setLimit(20);
        po.setProductIds(new ArrayList<>());
        po.getProductIds().add(924);
        po.getProductIds().add(938);
        po.getProductIds().add(942);
        po.getProductIds().add(13977);
//        List<SolutionProductListBO> solutionProductListBOS = productBizService.querySolutionProduct(po);
        //po.setProductName("test");
        //List<ProductListBO> productListBOS = productBizService.queryProductByParam(po);
        System.out.println("---------------------");
    }

    @Test
    public void testSchedule() {
        productBizService.synProductToPlatform();
    }


    @Test
    public void testDeleted() {
        productBizService.deleteProductsByIds(Collections.singletonList(9915690),"library");
    }

    @Test
    public void testThreadLocal() throws InterruptedException {

        class T {
            final private ThreadLocal<Long> local = new ThreadLocal<>();

            private void get() {
                log.info("{}", local);
            }

            private void set(Long var) {
                local.set(var);
            }

        }

        final T t = new T();
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long now = System.currentTimeMillis();
                    log.info("T addr-------{}", t);
                    log.info("t.get()---------set pre----{}", t.local.get());
                    t.local.set(now);
                    log.info("t.set()----{}", now);
                    log.info("t.get()---------set post---{}", t.local.get());
                    try {
                        Thread.sleep((long) 500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("t.get()---------in end---{}", t.local.get());
                }
            }).start();
        }
        Thread.sleep(2000);
        System.out.println("end----------");


    }
}