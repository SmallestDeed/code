package com.sandu.service.servicepurchase;

import com.sandu.api.servicepurchase.serivce.biz.ServicesPurchaseBizService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class ServicepurchaseTest {

    @Autowired
    private ServicesPurchaseBizService servicesPurchaseBizService;

    @Value("${msg.services.renewals.new.template}")
    private String msgRenewalsNewTemplate;

    @Test
    public void getValidRoles() {
        Set<Long> roleSet = new HashSet<Long>();
        roleSet.add(131L);
       // servicesPurchaseBizService.getValidRoles("C0000379EIU10000", roleSet);
    }

    @Test
    public void getFreeRenderDurationByUserid() {
        System.out.println(servicesPurchaseBizService.getFreeRenderDurationByUserid(7138L));
    }

    @Test
    public void updateRedisStatus() {
        System.out.println(servicesPurchaseBizService.updateRedisStatus("1212323", "success"));
    }

    @Test
    public void getTradeStatus() {
        System.out.println(servicesPurchaseBizService.getTradeStatus("1212323"));
    }

}
