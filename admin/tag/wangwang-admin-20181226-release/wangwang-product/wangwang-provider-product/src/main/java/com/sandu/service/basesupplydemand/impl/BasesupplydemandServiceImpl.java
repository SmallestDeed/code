package com.sandu.service.basesupplydemand.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.basesupplydemand.input.BasesupplydemandQuery;
import com.sandu.api.basesupplydemand.model.Basesupplydemand;
import com.sandu.api.basesupplydemand.model.ResRenderPic;
import com.sandu.api.basesupplydemand.model.SupplyDemandCategory;
import com.sandu.api.basesupplydemand.model.SupplyDemandPic;
import com.sandu.api.basesupplydemand.service.BaseSupplydemandService;
import com.sandu.service.basesupplydemand.dao.BasesupplydemandMapper;
import com.sandu.service.basesupplydemand.dao.ResRenderPicMapper;
import com.sandu.service.basesupplydemand.dao.SupplyDemandCategoryMapper;
import com.sandu.service.basesupplydemand.dao.SupplyDemandPicMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * supply_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 10:46
 */
@Slf4j
@Service("basesupplydemandService")
public class BasesupplydemandServiceImpl implements BaseSupplydemandService {

    @Autowired
    private BasesupplydemandMapper basesupplydemandMapper;

    @Autowired
    private SupplyDemandCategoryMapper supplyDemandCategoryMapper;

    @Autowired
    private SupplyDemandPicMapper supplyDemandPicMapper;

    @Autowired
    private ResRenderPicMapper resRenderPicMapper;

    @Override
    public int insert(Basesupplydemand basesupplydemand) {
        int result = basesupplydemandMapper.insert(basesupplydemand);
        if (result > 0) {
            return basesupplydemand.getId();
        }
        return 0;
    }

    @Override
    public int update(Basesupplydemand basesupplydemand) {
        StringBuilder sb = new StringBuilder();
        for (Integer num : basesupplydemand.getCategoryId()) {
            sb.append(num).append(",");
        }
        basesupplydemand.setSupplyDemandCategoryId(sb.toString().substring(0, sb.toString().lastIndexOf(",")));
        return basesupplydemandMapper.updateByPrimaryKey(basesupplydemand);
    }

    @Override
    public int delete(Set<Integer> basesupplydemandIds) {
        return basesupplydemandMapper.deleteByPrimaryKey(basesupplydemandIds);
    }

    @Override
    public Basesupplydemand getById(int basesupplydemandId) {
        Basesupplydemand result = basesupplydemandMapper.selectByPrimaryKey(basesupplydemandId);
        List<SupplyDemandCategory> categoryList = supplyDemandCategoryMapper.querySupplyDemandCategory();
        String[] categoryIds = result.getSupplyDemandCategoryId().split(",");
        StringBuilder sb = new StringBuilder();
        Integer[] categoryIdArr = new Integer[categoryIds.length];
        int num = 0;
        for (String index : categoryIds) {
            categoryIdArr[num] = Integer.valueOf(index);
            num++;
            categoryList.forEach(s -> {
                if (s.getId().toString().equals(index)) {
                    sb.append(s.getName()).append(",");
                }
            });
        }
        result.setSupplyDemandCategoryName(sb.toString().substring(0, sb.toString().lastIndexOf(",")));
        result.setCategoryId(categoryIdArr);

        if (result.getCoverPicId() != null) {
            List<String> coverPicPaths = new ArrayList<>();
            String[] picIds = result.getCoverPicId().split(",");
            for (String picId : picIds) {
                SupplyDemandPic renderPic = supplyDemandPicMapper.selectByPrimaryKey(Long.valueOf(picId));
                if (renderPic != null) {
                    coverPicPaths.add(renderPic.getPicPath());
                }
            }
            result.setCoverPicPaths(coverPicPaths);
        }
        return result;
    }

    @Override
    public PageInfo<Basesupplydemand> findAll(BasesupplydemandQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<Basesupplydemand> results = basesupplydemandMapper.findAll(query);
        List<SupplyDemandCategory> categoryList = supplyDemandCategoryMapper.querySupplyDemandCategory();
        for (Basesupplydemand result : results) {
            if (result.getRecommendedTime() > 0) {
                //时间戳
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long lt = new Long(result.getRecommendedTime());
                Date date = new Date(lt);
                result.setRecommendedDate(date);
            } else {
                result.setRecommendedDate(null);
            }
            String lastCategory = result.getSupplyDemandCategoryId().substring(
                    result.getSupplyDemandCategoryId().lastIndexOf(',') + 1);
            categoryList.forEach(s -> {
                if (s.getId().toString().equals(lastCategory)) {
                    result.setSupplyDemandCategoryName(s.getName());
                }
            });
        }
        return new PageInfo<>(results);
    }

    @Override
    public int baseSupplyToTop(String basesupplydemandId, String topId) {
        if ("1".equals(topId)) {
            //置顶
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            String newDate = simpleDateFormat.format(new Date());
            try {
                date = simpleDateFormat.parse(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long ts = date.getTime();
            return basesupplydemandMapper.baseSupplyToTop(basesupplydemandId, ts.toString());
        } else {
            return basesupplydemandMapper.baseSupplyToTop(basesupplydemandId, "0");
        }
    }

    @Override
    public int baseSupplyToRefresh(String basesupplydemandId, String topId) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            String newDate = simpleDateFormat.format(new Date());
            try {
                date = simpleDateFormat.parse(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long ts = date.getTime();
            return basesupplydemandMapper.baseSupplyToRefresh(basesupplydemandId);
    }
}
