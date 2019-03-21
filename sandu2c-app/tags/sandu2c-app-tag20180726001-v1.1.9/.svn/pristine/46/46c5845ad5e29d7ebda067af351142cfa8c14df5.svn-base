package com.sandu.decorate.service.impl.biz;

import com.nork.common.model.LoginUser;
import com.sandu.common.exception.BizException;
import com.sandu.common.util.DecorateUtil;
import com.sandu.decorate.input.PlanDecoratePriceAdd;
import com.sandu.decorate.input.PlanDecoratePriceQuery;
import com.sandu.decorate.input.PlanDecoratePriceUpdate;
import com.sandu.decorate.model.PlanDecoratePrice;
import com.sandu.decorate.service.PlanDecoratePriceService;
import com.sandu.decorate.service.biz.PlanDecoratePriceBizService;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.SysDictionaryConstant;
import com.sandu.system.service.SysDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Aug-08 15:37
 */
@Slf4j
@Service("planDecoratePriceBizService")
public class PlanDecoratePriceBizServiceImpl implements PlanDecoratePriceBizService {

    @Autowired
    private PlanDecoratePriceService planDecoratePriceService;
    @Autowired
    private SysDictionaryService sysDictionaryService;


    @Override
    public List<PlanDecoratePrice> query(PlanDecoratePriceQuery query) {

        return planDecoratePriceService.findAll(query);
    }

    @Override
    public List<SysDictionary> getDecoratePriceType() {
        String decoratePriceType = SysDictionaryConstant.DECORATE_PRICE_TYPE;
        return sysDictionaryService.findAllByType(decoratePriceType);
    }

    @Override
    public List<SysDictionary> getAllDecoratePriceType() {
        String decoratePriceType = SysDictionaryConstant.DECORATE_PRICE_TYPE;
        return sysDictionaryService.getAllRelationshipData(decoratePriceType);
    }

    @Override
    @Transactional
    public int insertBatch(List<PlanDecoratePriceAdd> addList, LoginUser loginUser) throws BizException {
        PlanDecoratePrice.PlanDecoratePriceBuilder builder = PlanDecoratePrice.builder();
        PlanDecoratePrice planDecoratePrice;
        List<PlanDecoratePrice> insertList = new ArrayList<>();
        //获取第一个对象的效果图方案id作为基准，如果集合中有效果图方案id不和他一致，就抛出业务异常
        Integer renderSceneId = addList.get(0).getRenderSceneId();

        for (PlanDecoratePriceAdd input : addList) {
            if (!renderSceneId.equals(input.getRenderSceneId())) {
                throw new BizException("传入数据的效果图方案id不一致");
            }
            Date date = new Date();
            input.setGmtCreate(date);
            input.setGmtModified(date);
            input.setCreator(loginUser.getLoginPhone());
            input.setModifier(loginUser.getLoginPhone());
            input.setIsDeleted(0);

            //获取对应的价格区间并得到价格对应的区间的value值
            List<SysDictionary> priceRangeList = this.getPriceRangeByType(input.getDecoratePriceType());
            int priceRangeValue = DecorateUtil.getMatchPriceRangeOfDecorate(input.getDecoratePrice(), priceRangeList);
            input.setDecoratePriceRange(priceRangeValue);

            planDecoratePrice = builder.build();
            BeanUtils.copyProperties(input, planDecoratePrice);

            insertList.add(planDecoratePrice);
        }
        //根据效果图方案id删除对应的之前的数据
        planDecoratePriceService.deleteByRenderSceneId(renderSceneId);

        return planDecoratePriceService.insertBatch(insertList);

    }

    @Override
    public int updateBatch(List<PlanDecoratePriceUpdate> list, LoginUser loginUser) throws BizException {
        PlanDecoratePrice.PlanDecoratePriceBuilder builder = PlanDecoratePrice.builder();
        PlanDecoratePrice planDecoratePrice;
        List<PlanDecoratePrice> updateList = new ArrayList<>();

        for (PlanDecoratePriceUpdate update : list) {
            update.setModifier(loginUser.getLoginPhone());
            update.setGmtModified(new Date());
            //获取对应的价格区间并得到价格对应的区间的value值
            List<SysDictionary> priceRangeList = this.getPriceRangeByType(update.getDecoratePriceType());
            int priceRangeValue = DecorateUtil.getMatchPriceRangeOfDecorate(update.getDecoratePrice(), priceRangeList);
            update.setDecoratePriceRange(priceRangeValue);

            planDecoratePrice = builder.build();
            BeanUtils.copyProperties(update, planDecoratePrice);

            updateList.add(planDecoratePrice);
        }

        return planDecoratePriceService.updateBatch(updateList);
    }


    /**
     * 根据装修类型找出对应的报价区间
     *
     * @param decorateTypeValue
     * @return
     */
    private List<SysDictionary> getPriceRangeByType(Integer decorateTypeValue) throws BizException {
        if (decorateTypeValue <= 0) {
            throw new BizException("装修报价分类值错误，value=" + decorateTypeValue);
        }
        List<SysDictionary> priceRangeList = sysDictionaryService.getByTypeAndValue(SysDictionaryConstant.DECORATE_PRICE_TYPE, decorateTypeValue);
        if (priceRangeList == null || priceRangeList.size() == 0) {
            throw new BizException("装修报价区间列表为空，请检查对应的装修类型值，decorateType=" + decorateTypeValue);
        }
        return priceRangeList;
    }
}
