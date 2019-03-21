package com.sandu.service.companyshop.impl.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.sandu.api.companyshop.input.CompanyshopAdd;
import com.sandu.api.companyshop.input.CompanyshopQuery;
import com.sandu.api.companyshop.input.CompanyshopUpdate;
import com.sandu.api.companyshop.model.Companyshop;
import com.sandu.api.companyshop.service.CompanyshopService;
import com.sandu.api.companyshop.service.biz.CompanyshopBizService;
import com.sandu.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * store_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-22 16:51
 */
@Slf4j
@Service("companyshopBizService")
public class CompanyshopBizServiceImpl implements CompanyshopBizService {

    @Autowired
    private CompanyshopService companyshopService;

    @Override
    public int create(CompanyshopAdd input) {

        Companyshop companyshop = new Companyshop();
        BeanUtils.copyProperties(input, companyshop);

        return companyshopService.insert(companyshop);
    }

    @Override
    public int update(CompanyshopUpdate input) {
        Companyshop companyshop = new Companyshop();

        BeanUtils.copyProperties(input, companyshop);
        //转换原字段ID
//        companyshop.setId(input.getCompanyshopId());
        return companyshopService.update(companyshop);
    }

    @Override
    public int delete(String companyshopId) {
        if (Strings.isNullOrEmpty(companyshopId)) {
            return 0;
        }

        Set<Integer> companyshopIds = new HashSet<>();
        List<String> list = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(companyshopId));
        list.stream().forEach(id -> companyshopIds.add(Integer.valueOf(id)));

        if (companyshopIds.size() == 0) {
            return 0;
        }
        return companyshopService.delete(companyshopIds);
    }

    @Override
    public Companyshop getById(int companyshopId) {
        return companyshopService.getById(companyshopId);
    }

    @Override
    public PageInfo<Companyshop> query(CompanyshopQuery query) {

        return companyshopService.findAll(query);
    }

    @Override
    public int companyshopToTop(String shopId, String topId) {
        return companyshopService.companyshopToTop(shopId,topId);
    }

    @Override
    public int companyshopToRefresh(String companyshopId,String topId) {
        return companyshopService.companyshopToRefresh(companyshopId,topId);
    }
}
