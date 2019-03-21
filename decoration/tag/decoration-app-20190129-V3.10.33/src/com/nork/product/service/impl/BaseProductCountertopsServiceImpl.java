package com.nork.product.service.impl;

import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.UnityPlanProduct;
import com.nork.product.dao.BaseProductCountertopsMapper;
import com.nork.product.model.BaseProductCountertops;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.SplitTextureDTO;
import com.nork.product.service.BaseProductCountertopsService;
import com.nork.product.service.BaseProductService;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("baseProductCountertopsService")
public class BaseProductCountertopsServiceImpl implements BaseProductCountertopsService {

    @Autowired
    private BaseProductCountertopsMapper baseProductCountertopsMapper;

    @Autowired
    private ResModelService resModelService;

    @Autowired
    private ResTextureService resTextureService;

    @Autowired
    private BaseProductService baseProductService;

    @Autowired
    private ResPicService resPicService;

    /**
     *
     * @mbggenerated
     */
    public int deleteByPrimaryKey(Long id){
        return baseProductCountertopsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据主键id 逻辑删除数据字典信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int deleteLogicByPrimaryKey(@Param("id") Long id, @Param("loginName") String loginName){
        return baseProductCountertopsMapper.deleteLogicByPrimaryKey(id,loginName);
    }

    /**
     *
     * @mbggenerated
     */
    public int insertSelective(BaseProductCountertops record){
        baseProductCountertopsMapper.insertSelective(record);
        if(null != record.getId())
            return record.getId().intValue();
        else
            return 0;
    }

    /**
     *
     * @mbggenerated
     */
    public BaseProductCountertops selectByPrimaryKey(Long id){
        return baseProductCountertopsMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @mbggenerated
     */
    public int updateByPrimaryKeySelective(BaseProductCountertops record){
        return baseProductCountertopsMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据产品id 获取 橱柜 信息
     * @author chenqiang
     * @param productId 产品id
     * @return BaseProductCountertops 集合
     * @date 2018/7/5 0005 14:54
     */
    public List<BaseProductCountertops> getListByProductId(Integer productId){
        return baseProductCountertopsMapper.selectListByProductId(productId);
    }



    /**
     * 处理产品 橱柜 详情
     * @author chenqiang
     * @param unityPlanProduct 产品
     * @date 2018/7/5 0005 14:54
     */
    public void setCountertopsDetails(UnityPlanProduct unityPlanProduct,DesignPlanProduct planProduct){

        if(null != unityPlanProduct && null != unityPlanProduct.getPlanProductId()) {

            List<BaseProductCountertops> baseProductCountertopsList = baseProductCountertopsMapper.selectListByProductId(planProduct.getProductId());

            for (BaseProductCountertops baseProductCountertops : baseProductCountertopsList) {
                this.dealWithTextureInfo(baseProductCountertops);
            }

            unityPlanProduct.setBaseProductCountertops(baseProductCountertopsList);
        }
    }

    /**
     * 处理材质信息,填装材质信息(默认材质)
     * @param baseProductCountertops
     */
    @SuppressWarnings("unchecked")
    public void dealWithTextureInfo(BaseProductCountertops baseProductCountertops) {

        List<SplitTextureDTO> splitTextureDTOList = new ArrayList<>();
        Integer isSplit = 0;
        if(null != baseProductCountertops.getTextureId() && -1 != baseProductCountertops.getTextureId()){

            ResModel textureBallModel = null;
            String materialPath = "";
            ResPic normalPic = null;
            String normalParam = "";
            String normalPath = "";
            ResTexture resTexture = resTextureService.get(baseProductCountertops.getTextureId().intValue());
            if(resTexture != null && resTexture.getTextureBallFileId() != null){
                textureBallModel = resModelService.get(resTexture.getTextureBallFileId());
                if(textureBallModel != null){
                    materialPath = textureBallModel.getModelPath();
                    materialPath = Utils.dealWithPath(materialPath, "linux");
                }
            }
            if(resTexture!=null && resTexture.getNormalPicId()!=null){
                normalParam = resTexture.getNormalParam();
                normalPic =  resPicService.get(resTexture.getNormalPicId());
                if(normalPic!=null){
                    normalPath = normalPic.getPicPath();
                    normalPath = Utils.dealWithPath(normalPath, "linux");
                }
            }

            List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<>();
            SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureService.fromResTexture(resTexture);
            resTextureDTO.setKey("1");
            resTextureDTO.setProductId(baseProductCountertops.getProductId().intValue());
            resTextureDTO.setNormalPath(normalPath);
            resTextureDTO.setNormalParam(normalParam);
            resTextureDTO.setMaterialPath(materialPath);
            resTextureDTOList.add(resTextureDTO);

            // 单材质产品
            SplitTextureDTO splitTextureDTO = new SplitTextureDTO("1", "", null);
            splitTextureDTO.setList(resTextureDTOList);
            splitTextureDTOList.add(splitTextureDTO);
        }

        baseProductCountertops.setIsSplit(isSplit);
        baseProductCountertops.setSplitTexturesChoose(splitTextureDTOList);
    }

}