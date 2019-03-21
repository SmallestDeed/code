package com.sandu.service.goods.impl;

import com.sandu.api.goods.input.GoodsListQuery;
import com.sandu.api.goods.input.GoodsSPUAdd;
import com.sandu.api.goods.model.bo.PutAwayBO;
import com.sandu.api.goods.model.po.GoodsListQueryPO;
import com.sandu.api.goods.model.BaseGoodsSPU;
import com.sandu.api.goods.output.*;
import com.sandu.api.goods.service.BaseGoodsSKUService;
import com.sandu.api.goods.service.BaseGoodsSPUService;
import com.sandu.api.goods.service.PicService;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.common.ReturnData;
import com.sandu.constant.ResponseEnum;
import com.sandu.service.goods.dao.BaseGoodsSPUMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("baseGoodsSPUService")
public class BaseGoodsSPUServiceImpl implements BaseGoodsSPUService {
    @Autowired
    private BaseGoodsSPUMapper baseGoodsSPUMapper;
    @Autowired
    private PicService picService;

    @Override
    public BaseGoodsSPU selectByPrimaryKey(Integer id) {
        return baseGoodsSPUMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<GoodsVO> getGoodsList(GoodsListQuery goodsListQuery) {
        // 创建查询对象
        GoodsListQueryPO goodsListQueryPO = new GoodsListQueryPO();
        // 处理查询条件，商品名称、商品编号、产品型号两端增加通配符%，使用like模糊查询
        if (goodsListQuery.getName() != null && !"".equals(goodsListQuery.getName().trim())) {
            goodsListQueryPO.setSpuName("%" + goodsListQuery.getName().trim() + "%");
        }
        if (goodsListQuery.getCode() != null && !"".equals(goodsListQuery.getCode().trim())) {
            goodsListQueryPO.setSpuCode("%" + goodsListQuery.getCode().trim() + "%");
        }
        if (goodsListQuery.getProductModelNumber() != null && !"".equals(goodsListQuery.getProductModelNumber().trim())) {
            goodsListQueryPO.setProductModelNumber("%" + goodsListQuery.getProductModelNumber().trim() + "%");
        }
        // 设置查询条件到查询对象中
        goodsListQueryPO.setTypeCode(goodsListQuery.getTypeCode());
        goodsListQueryPO.setChildTypeCode(goodsListQuery.getChildTypeCode());
        goodsListQueryPO.setCompanyId(goodsListQuery.getCompanyId());
        goodsListQueryPO.setIsPresell(goodsListQuery.getPresell());
        goodsListQueryPO.setIsPutaway(goodsListQuery.getPutaway());
        // 处理分页参数
        if (goodsListQuery.getLimit() != null) {
            goodsListQueryPO.setLimit(goodsListQuery.getLimit());
        }
        if (goodsListQuery.getPage() != null) {
            goodsListQueryPO.setStart((goodsListQuery.getPage() - 1) * goodsListQuery.getLimit());
        }
        List<GoodsVO> goodsVOList = baseGoodsSPUMapper.getGoodsList(goodsListQueryPO);

        return goodsVOList;
    }

    @Override
    public Integer goodsPutawayUp(PutAwayBO putAwayBO) {
        Integer number = baseGoodsSPUMapper.goodsPutawayUp(putAwayBO.getIds());
        return number;
    }

    @Override
    public Integer goodsPutawayDown(List<Integer> ids) {
        Integer number = baseGoodsSPUMapper.goodsPutawayDown(ids);
        return number;
    }

    @Override
    public GoodsDetailVO getGoodsInfo(Integer id) {
        GoodsDetailVO goodsDetail = baseGoodsSPUMapper.getGoodsDetail(id);
        // 处理商品图片列表
        // 主缩略图不展示，默认pic_ids的第一张图为主缩略图的原图
        List<Integer> idList = new ArrayList();
        if (goodsDetail != null) {
            String picIds = goodsDetail.getPicIds();
            // 拆分pic_ids
            if (picIds != null && !picIds.equals("")) {
                String[] picIdArr = picIds.split(",");

                for (String str : picIdArr) {
                    idList.add(Integer.parseInt(str));
                }
            }
            // 获取图片
            List<PicVO> picList = picService.getPicsByIds(idList);
            // 图片按pic_ids的顺序展示
            List<PicVO> picListSort = new ArrayList<>();
            if (picList != null){
                for (int i = 0; i < idList.size(); i++) {
                    for (PicVO picVO : picList) {
                        if (i == 0){
                            if (picVO.getPicId().equals(idList.get(i))){
                                // 第一张图为主缩略图的原图
                                goodsDetail.setMainPic(picVO);
                                goodsDetail.setMainPicId(idList.get(i));
                                break;
                            }
                        }else {
                            if (picVO.getPicId().equals(idList.get(i))){
                                // 其余图片按顺序展示
                                picListSort.add(picVO);
                                break;
                            }
                        }
                    }
                }
            }
            goodsDetail.setPicList(picListSort);
            return goodsDetail;
        } else {
            return null;
        }
    }

    @Override
    public ReturnData updateGoods(GoodsSPUAdd goodsSPUAdd) {
        /*if (goodsSPUAdd.getPresell() == null || goodsSPUAdd.getPresell() == 0) {
            goodsSPUAdd.setPresellDDL(null);
            goodsSPUAdd.setPayTailStarttime(null);
            goodsSPUAdd.setPayTailEndtime(null);
        } else {
            if (goodsSPUAdd.getPresellDDL() == null || goodsSPUAdd.getPayTailStarttime() == null || goodsSPUAdd.getPayTailEndtime() == null
                    || goodsSPUAdd.getEarnest() == null || goodsSPUAdd.getShipmentsPresell() == null) {
                return ReturnData.builder().message("预售时间未填写完整").code(ResponseEnum.ERROR);
            }
            if (goodsSPUAdd.getPayTailEndtime().before(goodsSPUAdd.getPresellDDL())
                    || goodsSPUAdd.getPayTailStarttime().before(goodsSPUAdd.getPresellDDL())) {
                return ReturnData.builder().message("尾款支付时间不能早于预售截止时间").code(ResponseEnum.ERROR);
            }else {
                if (goodsSPUAdd.getPayTailEndtime().before(goodsSPUAdd.getPayTailStarttime())) {
                    return ReturnData.builder().message("尾款支付结束时间不能早于开始时间").code(ResponseEnum.ERROR);
                }
            }
        }*/
        if (goodsSPUAdd.getDescribe() != null && !"".equals(goodsSPUAdd.getDescribe())){
            if (goodsSPUAdd.getDescribe().getBytes().length > 65535){
                return ReturnData.builder().message("商品描述字数过多").code(ResponseEnum.ERROR);
            }
        }
        if (baseGoodsSPUMapper.updateSpu(goodsSPUAdd) == 0) {
            return ReturnData.builder().message("保存失败").code(ResponseEnum.ERROR);
        } else {
            if (baseGoodsSPUMapper.updateSpuSaleInfo(goodsSPUAdd) == 0) {
                goodsSPUAdd.setCreator(goodsSPUAdd.getModifier());
                goodsSPUAdd.setGmtCreate(goodsSPUAdd.getGmtModified());
                goodsSPUAdd.setIsDeleted(0);
                baseGoodsSPUMapper.insertSpuSaleInfo(goodsSPUAdd);
            }
        }
        return ReturnData.builder().message("保存成功").code(ResponseEnum.SUCCESS);
    }

    @Override
    public List<GoodsTypeVO> getGoodsType(Integer companyId) {
        List<GoodsTypeVO> goodsTypeVOList = baseGoodsSPUMapper.getGoodsType(companyId);
        return goodsTypeVOList;
    }

    @Override
    public Integer totalCount(GoodsListQuery goodsListQuery) {
        GoodsListQueryPO goodsListQueryPO = new GoodsListQueryPO();
        if (goodsListQuery.getName() != null && !"".equals(goodsListQuery.getName().trim())) {
            goodsListQueryPO.setSpuName("%" + goodsListQuery.getName().trim() + "%");
        }
        if (goodsListQuery.getCode() != null && !"".equals(goodsListQuery.getCode().trim())) {
            goodsListQueryPO.setSpuCode("%" + goodsListQuery.getCode().trim() + "%");
        }
        if (goodsListQuery.getProductModelNumber() != null && !"".equals(goodsListQuery.getProductModelNumber().trim())) {
            goodsListQueryPO.setProductModelNumber("%" + goodsListQuery.getProductModelNumber().trim() + "%");
        }
        goodsListQueryPO.setTypeCode(goodsListQuery.getTypeCode());
        goodsListQueryPO.setChildTypeCode(goodsListQuery.getChildTypeCode());
        goodsListQueryPO.setCompanyId(goodsListQuery.getCompanyId());
        goodsListQueryPO.setIsPresell(goodsListQuery.getPresell());
        goodsListQueryPO.setIsPutaway(goodsListQuery.getPutaway());
        return baseGoodsSPUMapper.getTotalCount(goodsListQueryPO);
    }

    @Override
    public BaseGoodsSPU get(Integer id) {
        return baseGoodsSPUMapper.selectByPrimaryKey(id);
    }
}
