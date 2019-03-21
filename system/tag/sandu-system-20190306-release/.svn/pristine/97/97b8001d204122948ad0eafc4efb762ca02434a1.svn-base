package com.sandu.service.gift.impl.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.sandu.api.gift.input.GiftAdd;
import com.sandu.api.gift.input.GiftQuery;
import com.sandu.api.gift.input.GiftUpdate;
import com.sandu.api.gift.model.Gift;
import com.sandu.api.gift.model.GiftImage;
import com.sandu.api.gift.model.GiftPO;
import com.sandu.api.gift.service.GiftBizService;
import com.sandu.api.gift.service.GiftService;
import com.sandu.commons.util.StringUtils;
import com.sandu.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/28 18:42
 */
@Slf4j
@Service("giftBizService")
public class GiftBizServiceImpl implements GiftBizService {

    @Autowired
    private GiftService giftService;

    @Override
    public int create(GiftAdd input) {
        Gift gift = new Gift();
        gift.setName(input.getName());
        gift.setExplain(input.getExplain());
        gift.setIntroduce(input.getIntroduce());
        gift.setIsPutaway(input.getIsPutaway());
        gift.setInventory(input.getInventory());
        gift.setPrice(input.getPrice());
        gift.setPoint(input.getPoint());
        gift.setExpressFee(input.getExpressFee());
        gift.setCreator(input.getCreator());
        gift.setModifier(input.getCreator());
        //添加礼品编码，add by WangHL
        gift.setSysCode(input.getSysCode());
        String code = this.getCode();
        gift.setCode(code);
        return giftService.insert(gift);
    }

    /**
     * 生成礼编码
     * @return 礼品编码
     */
    public  String getCode(){
        StringBuffer stringCodePrefix=new StringBuffer("GIFT");
        stringCodePrefix.append("_");//编码前半
        StringBuffer stringCodeSuffix = new StringBuffer();//编码后半

        String maxCode = giftService.getGiftByCode(stringCodePrefix.toString());
        Integer code=0;
        if (StringUtils.isNotBlank(maxCode)){
            code=Integer.parseInt(maxCode.substring(stringCodePrefix.toString().length(),maxCode.length()));
            code++;
            stringCodeSuffix.append(code);
        }else{
            stringCodeSuffix.append("1");
        }
        int k = stringCodeSuffix.length();
        for (int i = 0; i < 4 - k; i++) {
            stringCodeSuffix.insert(0, "0");
        }
        String stringCode=stringCodePrefix.append(stringCodeSuffix).toString();
        return stringCode;
    }


    @Override
    public int update(GiftUpdate input) {
        Gift gift = new Gift();
        gift.setId(input.getId());
        gift.setCode(input.getCode());
        gift.setName(input.getName());
        gift.setExplain(input.getExplain());
        gift.setIntroduce(input.getIntroduce());
        gift.setIsPutaway(input.getIsPutaway());
        gift.setInventory(input.getInventory());
        gift.setPrice(input.getPrice());
        gift.setPoint(input.getPoint());
        gift.setExpressFee(input.getExpressFee());
        gift.setModifier(input.getModifier());
        return giftService.update(gift);
    }

    @Override
    public int updatePutaway(String ids,int putaway,String modifier) {
        Set<Integer> giftIds = new HashSet<>();
        List<String> list =Arrays.asList(ids.split(","));// Stringer.splitToList(ids, ",");
        list.stream().forEach(id -> giftIds.add(Integer.valueOf(id)));

        if (giftIds.size() == 0) {
            return 0;
        }
        return giftService.updatePutaway(giftIds,putaway,modifier);
    }

    @Override
    public int delete(String ids,String modifier) {
        if (Strings.isNullOrEmpty(ids)) {
            return 0;
        }

        Set<Integer> giftIds = new HashSet<>();
        List<String> list = Arrays.asList(ids.split(","));
        list.stream().forEach(id -> giftIds.add(Integer.valueOf(id)));

        if (giftIds.size() == 0) {
            return 0;
        }
        return giftService.delete(giftIds,modifier);
    }

    @Override
    public Gift getDetailById(int id) {
        return giftService.getById(id);
    }

    @Override
    public PageInfo<Gift> queryList(GiftQuery query) {
        GiftPO param = new GiftPO();
        param.setCode(query.getCode());
        param.setName(query.getName());
        param.setIsPutaway(query.getIsPutaway());
        param.setPage(Utils.getPage(query.getPage()));
        param.setLimit(Utils.getLimit(query.getLimit()));
        param.setOrder(query.getOrder());
        param.setSort(query.getSort());
        return giftService.findAll(param);
    }

    @Override
    public int createGiftImage(GiftImage model){
        return giftService.insertGiftImage(model);
    }

    @Override
    public List<GiftImage> getGiftImages(int id){
        return giftService.getGiftImages(id);
    }

    @Override
    public int deleteGiftImageById(int id,String modifier){
        return giftService.deleteGiftImageById(id,modifier);
    }

    @Override
    public int updateCover(int id,int giftId,String modifier){
        return giftService.updateCover(id,giftId,modifier);
    }

    @Override
    public int updateSmall(int id,int giftId,String modifier){
        return giftService.updateSmall(id,giftId,modifier);
    }
}
