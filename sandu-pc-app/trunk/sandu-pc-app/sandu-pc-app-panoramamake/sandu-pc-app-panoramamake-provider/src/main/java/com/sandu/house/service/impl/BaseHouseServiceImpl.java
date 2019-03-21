package com.sandu.house.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.common.util.collections.CustomerListUtils;
import com.sandu.home.model.constant.HouseTypeConstant;
import com.sandu.home.model.po.BaseHouseSpaceNumPO;
import com.sandu.house.dao.BaseHouseMapper;
import com.sandu.house.model.input.BaseHouseSearch;
import com.sandu.house.model.output.BaseHouseVo;
import com.sandu.house.service.BaseHouseService;
import com.sandu.panorama.dao.DesignPlanStoreReleaseDetailsMapper;
import com.sandu.system.model.BaseArea;
import com.sandu.system.model.bo.HouseAreaBo;
import com.sandu.system.service.BaseAreaService;
import com.sun.swing.internal.plaf.synth.resources.synth_sv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("baseHouseService")
@Transactional
@Slf4j
public class BaseHouseServiceImpl implements BaseHouseService {

    @Autowired
    private BaseHouseMapper baseHouseMapper;
    @Autowired
    private DesignPlanStoreReleaseDetailsMapper designPlanStoreReleaseDetailsMapper;
    @Autowired
    private BaseAreaService baseAreaService;

    /**
     * 获取渲染过的户型
     * @param search
     * @return
     */
    @Override
    public int getMyUsedCount(BaseHouseSearch search) {
        List<Integer> livingIds = new ArrayList<>();
        if( search != null && StringUtils.isNotBlank(search.getLivingName()) && search.getLivingId() == null ){
            livingIds = baseHouseMapper.getIdsByName(search.getLivingName());
            search.setLivingIds(livingIds);
        }
        return baseHouseMapper.getMyUsedCount(search);
    }

    /**
     * 获取渲染过的户型
     * @param search
     * @return
     */
    @Override
    public List<BaseHouseVo> getMyUsed(BaseHouseSearch search) {

        List<Integer> livingIds = new ArrayList<>();
        // 如果小区不确定（livingId = null）,那么则用输入的小区名称模糊查询
        if( search != null && StringUtils.isNotBlank(search.getLivingName()) && search.getLivingId() == null ){
            livingIds = baseHouseMapper.getIdsByName(search.getLivingName());
            search.setLivingIds(livingIds);
        }
        List<BaseHouseVo> list = baseHouseMapper.getMyUsed(search);

        List<Integer> houseIdList = new ArrayList<Integer>(list.size());//户型Id集合
        Map<Integer,String> houseAreaInfoMap = new HashMap<Integer,String>();//户型Id与区域长编码集合
        if( list != null && list.size() > 0 ){
            for( BaseHouseVo baseHouseVo : list ){
                houseIdList.add(baseHouseVo.getId());
                if( baseHouseVo.getHousePicId() == null ){
                    // TEMP
                    String picPath = designPlanStoreReleaseDetailsMapper.getResourcePathById(baseHouseVo.getOldHousePicId(), "res_house_pic");
                    baseHouseVo.setPicPath(picPath);
                }
                //获得户型区域信息
               /* baseHouseVo = this.getHouseAreaInfo(baseHouseVo);*/
                houseAreaInfoMap.put(baseHouseVo.getId(),baseHouseVo.getAreaLongCode());
            }
        }
        //得到户型下各房型空间数量
        Map<Integer,String> houseSpaceNumMap = this.jointHouseSpaceNum(houseIdList);
        //得到户型区域信息
        Map<Integer,HouseAreaBo> houseAreaBoMap = this.getHouseAreaInfoMap(houseAreaInfoMap);

        for (BaseHouseVo baseHouseVo : list){

            if(houseSpaceNumMap != null && houseSpaceNumMap.size() > 0) {
                if (houseSpaceNumMap.containsKey(baseHouseVo.getId())) {
                    baseHouseVo.setHouseSpaceNumStr(houseSpaceNumMap.get(baseHouseVo.getId()));
                }
            }
            if(houseAreaBoMap != null && houseAreaBoMap.size() > 0){
                if(houseAreaBoMap.containsKey(baseHouseVo.getId())){
                    HouseAreaBo areaBo = (HouseAreaBo)houseAreaBoMap.get(baseHouseVo.getId());
                    if(areaBo == null){
                        continue;
                    }
                    baseHouseVo.setProvince(areaBo.getProvince());
                    baseHouseVo.setCity(areaBo.getCity());
                    baseHouseVo.setDistrict(areaBo.getDistrict());
                }
            }
        }
        return list;
    }


    @Override
    public Map<Integer,String> jointHouseSpaceNum(List<Integer> houseIds){
        if(CustomerListUtils.isEmpty(houseIds)){
            return null;
        }
        Map<Integer,String> houseSpaceNumStrMap = new HashMap<Integer,String>(houseIds.size());//户型对应不同房型数量说明
        Map<Integer,Map<Integer,Integer>> houseSpaceNumMap = new HashMap<Integer,Map<Integer,Integer>>(houseIds.size());//户型对应不同房型数量数据
        //查询得到各户型不同房型对应空间数量
        List<BaseHouseSpaceNumPO> houseSpaceNumPOList = baseHouseMapper.getHouseSpaceNumInfo(houseIds);
        if(CustomerListUtils.isEmpty(houseSpaceNumPOList)){
            return null;
        }
        for(BaseHouseSpaceNumPO houseSpaceNumPO : houseSpaceNumPOList){
            if(!houseSpaceNumMap.containsKey(houseSpaceNumPO.getHouseId())){
                Map<Integer,Integer> functionNumMap = new HashMap<Integer,Integer>();
                functionNumMap.put(houseSpaceNumPO.getSpaceFunctionId(),houseSpaceNumPO.getSpaceNum());
                houseSpaceNumMap.put(houseSpaceNumPO.getHouseId(),functionNumMap);
            }else{
                Map<Integer,Integer> functionNumMap = new HashMap<Integer,Integer>();
                functionNumMap = houseSpaceNumMap.get(houseSpaceNumPO.getHouseId());
                if(functionNumMap == null){
                    functionNumMap = new HashMap<Integer,Integer>();
                    functionNumMap.put(houseSpaceNumPO.getSpaceFunctionId(),houseSpaceNumPO.getSpaceNum());
                    houseSpaceNumMap.put(houseSpaceNumPO.getHouseId(),functionNumMap);
                }else{
                    if(!functionNumMap.containsKey(houseSpaceNumPO.getSpaceFunctionId())){
                        functionNumMap.put(houseSpaceNumPO.getSpaceFunctionId(),houseSpaceNumPO.getSpaceNum());
                        houseSpaceNumMap.put(houseSpaceNumPO.getHouseId(),functionNumMap);
                    }
                }
            }
        }

        //拼接房型数量信息
        for (Integer houseId : houseSpaceNumMap.keySet()){
            StringBuffer spaceNumStr = new StringBuffer();
            Map<Integer,Integer> functionNumMap = new HashMap<Integer,Integer>();
            functionNumMap = houseSpaceNumMap.get(houseId);
            /*int anotherTypeCount = 0;*///其他房型的房间数量(阳台,入户花园,天井,其他房型都归为其他)
            for (Integer functionValue : functionNumMap.keySet()){
                Integer count = functionNumMap.get(functionValue);
                switch (functionValue){
                    case HouseTypeConstant.HOUSE_TYPE_RESTAURANT:
                        spaceNumStr.append(count + "厅");
                        break;
                    case HouseTypeConstant.HOUSE_TYPE_BED_ROOM:
                        spaceNumStr.append(count + "室");
                        break;
                    case HouseTypeConstant.HOUSE_TYPE_KITCHEN:
                        spaceNumStr.append(count + "厨");
                        break;
                    case HouseTypeConstant.HOUSE_TYPE_TOILET:
                        spaceNumStr.append(count + "卫");
                        break;
                    case HouseTypeConstant.HOUSE_TYPE_STUDY_ROOM:
                        spaceNumStr.append(count + "书");
                        break;
                    case HouseTypeConstant.HOUSE_TYPE_CLOAK_ROOM:
                        spaceNumStr.append(count + "衣");
                        break;
                    default:
                     /*   anotherTypeCount += count;*/
                        break;
                }
            }
           /* if(anotherTypeCount > 0){
                spaceNumStr.append("其他:" + anotherTypeCount);
            }*/
            houseSpaceNumStrMap.put(houseId , spaceNumStr.toString());
        }

        return houseSpaceNumStrMap;
    }

    @Override
    public Map<Integer,HouseAreaBo> getHouseAreaInfoMap(Map<Integer,String> houseAreaInfoMap){
        if(houseAreaInfoMap == null || houseAreaInfoMap.size() <= 0){
            return null;
        }
        Map<Integer,HouseAreaBo> houseAreaBoMap = new HashMap<Integer,HouseAreaBo>();
        for (Integer houseId : houseAreaInfoMap.keySet()){
            String areaLongCode = houseAreaInfoMap.get(houseId);
            HouseAreaBo areaBo = new HouseAreaBo();
            if(StringUtils.isBlank(areaLongCode)){
                continue;
            }
            //获得省市区信息
            BaseArea areaSearch = new BaseArea();
            areaSearch.setLongCode(areaLongCode);
            areaBo = baseAreaService.selectAreaInfoByAreaLongCode(areaSearch);
            houseAreaBoMap.put(houseId,areaBo);
        }
        return houseAreaBoMap;
    }

}
