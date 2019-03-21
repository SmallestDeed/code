package com.sandu.service.companyshop.impl.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.companyshop.input.ShopidentificationAdd;
import com.sandu.api.companyshop.input.ShopidentificationQuery;
import com.sandu.api.companyshop.input.ShopidentificationUpdate;
import com.sandu.api.companyshop.model.Shopidentification;
import com.sandu.api.companyshop.service.ShopidentificationService;
import com.sandu.api.companyshop.service.biz.ShopidentificationBizService;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.storage.service.ResPicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * shop_identification
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-22 11:50
 */
@Slf4j
@Service("shopidentificationBizService")
public class ShopidentificationBizServiceImpl implements ShopidentificationBizService {

    @Autowired
    private ShopidentificationService shopidentificationService;

    @Resource
    private ResPicService resPicService;

    @Resource
    private DictionaryService dictionaryService;

    @Override
    public int create(ShopidentificationAdd input) {
        Shopidentification.ShopidentificationBuilder builder = Shopidentification.builder();

        Shopidentification shopidentification = builder.build();
        BeanUtils.copyProperties(input, shopidentification);

        return shopidentificationService.insert(shopidentification);
    }

    @Override
    public int update(ShopidentificationUpdate input) {
        Shopidentification.ShopidentificationBuilder builder = Shopidentification.builder();
        Shopidentification shopidentification = builder.build();

        BeanUtils.copyProperties(input, shopidentification);
        //转换原字段ID
//        shopidentification.setId(input.getShopidentificationId());
        return shopidentificationService.update(shopidentification);
    }

    @Override
    public int delete(String shopidentificationId) {
        if (Strings.isNullOrEmpty(shopidentificationId)) {
            return 0;
        }

        Set<Integer> shopidentificationIds = new HashSet<>();
        List<String> list = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(shopidentificationId));
        list.stream().forEach(id -> shopidentificationIds.add(Integer.valueOf(id)));

        if (shopidentificationIds.size() == 0) {
            return 0;
        }
        return shopidentificationService.delete(shopidentificationIds);
    }

    @Override
    public Shopidentification getById(int shopidentificationId) {
        return shopidentificationService.getById(shopidentificationId);
    }

    @Override
    public PageInfo<Shopidentification> query(ShopidentificationQuery query) {

        return shopidentificationService.findAll(query);
    }

    @Override
    public Shopidentification getByShopId(int shopId) {
        Shopidentification shopidentification = shopidentificationService.getByShopId(shopId);
        List<Integer> picIds = new ArrayList<>();
        picIds.add(shopidentification.getFrontPicId());
        picIds.add(shopidentification.getBackendPicId());
        picIds.add(shopidentification.getHandPicId());
        Map<Integer,String> picMap = resPicService.idAndPathMap(picIds);
        shopidentification.setFrontPicPath(picMap.get(shopidentification.getFrontPicId()));
        shopidentification.setBackendPicPath(picMap.get(shopidentification.getBackendPicId()));
        shopidentification.setHandPicPath(picMap.get(shopidentification.getHandPicId()));

        String industryNames = this.getIndustryNames(shopidentification.getCategoryIds());
        if (null != industryNames) {
            shopidentification.setCategoryNames(industryNames);
        }
        return shopidentification;
    }

    private String getIndustryNames(String industryValues) {
        List<Integer> integerList = StringUtil.getListByString(industryValues);
        StringBuilder sb = new StringBuilder();
        if (null != integerList) {
            List<DictionaryTypeListVO> industrys = dictionaryService.getListByTypeOrValues("industry", integerList);
            if (null != industrys) {
                for (DictionaryTypeListVO dictionary : industrys) {
                    sb.append(dictionary.getName());
                    sb.append(",");
                }
            }
            String names = sb.toString();
            String substring = names.substring(0, names.length() - 1);
            return substring;
        }
        return null;
    }
}
