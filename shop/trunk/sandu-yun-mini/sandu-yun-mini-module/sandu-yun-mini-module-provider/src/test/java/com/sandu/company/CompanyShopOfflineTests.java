package com.sandu.company;

import com.sandu.ModuleProvider;
import com.sandu.common.exception.ElasticSearchException;
import com.sandu.company.model.query.CompanyShopQuery;
import com.sandu.company.model.vo.CompanyShopVo;
import com.sandu.company.service.CompanyShopService;
import com.sandu.matadata.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/6 13:50
 * @since 1.8
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyShopOfflineTests {

    @Autowired
    CompanyShopService companyShopService;

    @Test
    public void testListShopOffline() {
        CompanyShopQuery query = new CompanyShopQuery();
        query.setCompanyId(new BigInteger("1003"));
        query.setPageNo(1);
        query.setPageSize(10);
        query.setBusinessType(2);
        query.setPlatformType(1);
        query.setShopName("");
        query.setCityCode("");
        query.setLongitude("114.03175");
        query.setLatitude("22.530525");

        Page<CompanyShopVo> listShopOffline = null;
        try {
            listShopOffline = companyShopService.listShopOffline(query);
        } catch (ElasticSearchException e) {
            e.printStackTrace();
        }

        log.debug("############ => {}", listShopOffline);
    }
}
