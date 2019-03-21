package com.sandu.service.solution.impl;

import com.sandu.api.solution.model.DesignPlanBrand;
import com.sandu.api.solution.service.DesignPlanBrandService;
import com.sandu.service.solution.dao.DesignPlanBrandMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sandu.util.Commoner.isEmpty;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 11:37 2018/7/12
 */

@Service("designPlanBrandService")
public class DesignPlanBrandServiceImpl implements DesignPlanBrandService{

    @Resource
    private DesignPlanBrandMapper designPlanBrandMapper;

    @Override
    public Map<Integer, String> idAndSpaceTypeMap(List<Long> planIds) {
        planIds = planIds.stream().filter(Objects::nonNull).distinct().collect(toList());
        if (isEmpty(planIds)) {
            return Collections.emptyMap();
        }
        List<DesignPlanBrand> designPlanBrands = designPlanBrandMapper.listByIds(planIds);
        return designPlanBrands.stream().collect(Collectors.toMap(brand -> brand.getPlanId(), valueBrand -> {
            if (isEmpty(valueBrand.getAtt2()) || "-1".equals(valueBrand.getAtt2())) {
                return "";
            } else {
                return valueBrand.getAtt2();
            }
        }));
    }

	@Override
	public Map<Integer, String> idAndSpaceTypeMap(List<Long> planIds, Integer companyId) {
		planIds = planIds.stream().filter(Objects::nonNull).distinct().collect(toList());
        if (isEmpty(planIds)) {
            return Collections.emptyMap();
        }
        List<DesignPlanBrand> designPlanBrands = designPlanBrandMapper.listByIdsCompany(planIds,companyId);
        return designPlanBrands.stream().collect(Collectors.toMap(brand -> brand.getPlanId(), valueBrand -> {
            if (isEmpty(valueBrand.getAtt2())) {
                return "";
            } else {
                return valueBrand.getAtt2();
            }
        }));
	}
}
