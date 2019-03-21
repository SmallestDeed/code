package com.sandu.service.goods.impl.biz;

import com.sandu.api.goods.common.constant.GoodsTypeEnum;
import com.sandu.api.goods.model.bo.BizGoodsBO;
import com.sandu.api.goods.model.po.BizGoodsQueryPO;
import com.sandu.api.goods.output.PresellGoodsVO;
import com.sandu.api.goods.output.SpecialGoodsVO;
import com.sandu.api.goods.service.biz.BaseGoodsBizService;
import com.sandu.service.goods.dao.BaseGoodsBizMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service("baseGoodsBizService")
public class BaseGoodsBizServiceImpl implements BaseGoodsBizService {
    @Autowired
    private BaseGoodsBizMapper baseGoodsBizMapper;

    @Override
    public List getMainPageGoodsList(GoodsTypeEnum goodsType, Integer companyId) {
        return this.getGoodsList(goodsType, companyId,true);
    }

    @Override
    public List getBizGoodsList(GoodsTypeEnum goodsType, Integer companyId) {
        return this.getGoodsList(goodsType, companyId,false);
    }

    @Override
    public Integer updateMainPageState(GoodsTypeEnum goodsType, Integer spuId, Integer state) {
        if (spuId == null || state == null){
            return 0;
        }
        switch (goodsType){
            case SPECIAL_GOODS:
                return baseGoodsBizMapper.updateMainPageState(2, spuId, state);
            case PRESELL_GOODS:
                return baseGoodsBizMapper.updateMainPageState(1, spuId, state);
        }
        return 0;
    }

    private <T, S> T setFieldValueFromAnotherClass(T target, S source){
        Class<T> clzT = (Class<T>) target.getClass();
        Class<S> clzS = (Class<S>) source.getClass();

        Field[] tFieldList = clzT.getDeclaredFields();
        Field[] sFieldList = clzS.getDeclaredFields();

        for (Field sField : sFieldList) {
            for (Field tField : tFieldList) {
                if (tField.getName().equals(sField.getName())){
                    String sFieldName = sField.getName();
                    sFieldName = sFieldName.toUpperCase().substring(0,1) + sFieldName.substring(1);
                    try {
                        Object sFieldValue = clzS.getMethod("get" + sFieldName).invoke(source);
                        tField.setAccessible(true);
                        tField.set(target, sFieldValue);
                    } catch (Exception e) {
                        throw new RuntimeException("方法setFieldValueFromAnotherClass异常:\n" + e.toString());
                    }
                }
            }
        }
        return target;
    }

    private List getGoodsList(GoodsTypeEnum goodsType, Integer companyId, boolean isMainPage){
        BizGoodsQueryPO bizGoodsQueryPO = new BizGoodsQueryPO();
        if (goodsType == null){
            return null;
        }else {
            switch (goodsType){
                case BASE_GOODS:
                    break;
                case PRESELL_GOODS:
                    bizGoodsQueryPO.setIsPresell(1);
                    if (isMainPage){
                        bizGoodsQueryPO.setPresellMainPageState(1);
                    }else {
                        bizGoodsQueryPO.setPresellMainPageState(0);
                    }
                    break;
                case SPECIAL_GOODS:
                    bizGoodsQueryPO.setIsSpecialSell(1);
                    if (isMainPage){
                        bizGoodsQueryPO.setSpecialMainPageState(1);
                    }else {
                        bizGoodsQueryPO.setSpecialMainPageState(0);
                    }
                    break;
            }
            bizGoodsQueryPO.setCompanyId(companyId);
        }
        List<BizGoodsBO> boList = baseGoodsBizMapper.getMainPageGoodsBOList(bizGoodsQueryPO);
        if (goodsType == GoodsTypeEnum.PRESELL_GOODS){
            List<PresellGoodsVO> list = new ArrayList<>();
            for (BizGoodsBO bizGoodsBO : boList) {
                PresellGoodsVO presellGoodsVO = new PresellGoodsVO();
                presellGoodsVO = setFieldValueFromAnotherClass(presellGoodsVO,bizGoodsBO);
                list.add(presellGoodsVO);
            }
            return list;
        }else if (goodsType == GoodsTypeEnum.SPECIAL_GOODS){
            List<SpecialGoodsVO> list = new ArrayList<>();
            for (BizGoodsBO bizGoodsBO : boList) {
                SpecialGoodsVO specialGoodsVO = new SpecialGoodsVO();
                specialGoodsVO = setFieldValueFromAnotherClass(specialGoodsVO,bizGoodsBO);
                list.add(specialGoodsVO);
            }
            return list;
        }else {
            return null;
        }
    }
}
