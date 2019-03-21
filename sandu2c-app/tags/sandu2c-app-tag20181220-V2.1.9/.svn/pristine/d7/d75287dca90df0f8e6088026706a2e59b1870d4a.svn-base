package com.sandu.product.service.impl;

import com.sandu.common.util.Utils;
import com.sandu.designplan.vo.DesignPlanStyleVo;
import com.sandu.product.dao.BaseProductStyleMapper;
import com.sandu.product.model.BaseProductStyle;
import com.sandu.product.model.search.BaseProductStyleSearch;
import com.sandu.product.po.DesignProductStyleDicPo;
import com.sandu.product.service.BaseProductStyleService;
import com.sandu.product.vo.DesignProductStyleDicVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: BaseProductStyleServiceImpl.java
 * @Package com.sandu.product.service.impl
 * @Description:产品管理-产品风格ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2017-04-17 11:42:49
 */
@Service("baseProductStyleService")
public class BaseProductStyleServiceImpl implements BaseProductStyleService {

    @Autowired
    private BaseProductStyleMapper baseProductStyleMapper;

    /**
     * 新增数据
     *
     * @param baseProductStyle
     * @return int
     */
    @Override
    public int add(BaseProductStyle baseProductStyle) {
        baseProductStyleMapper.insertSelective(baseProductStyle);
        return baseProductStyle.getId();
    }

    /**
     * 更新数据
     *
     * @param baseProductStyle
     * @return int
     */
    @Override
    public int update(BaseProductStyle baseProductStyle) {
        return baseProductStyleMapper
                .updateByPrimaryKeySelective(baseProductStyle);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return baseProductStyleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseProductStyle
     */
    @Override
    public BaseProductStyle get(Integer id) {
        return baseProductStyleMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param baseProductStyle
     * @return List<BaseProductStyle>
     */
    @Override
    public List<BaseProductStyle> getList(BaseProductStyle baseProductStyle) {
        return baseProductStyleMapper.selectList(baseProductStyle);
    }

    /**
     * 获取数据数量
     *
     * @return int
     */
    @Override
    public int getCount(BaseProductStyleSearch baseProductStyleSearch) {
        return baseProductStyleMapper.selectCount(baseProductStyleSearch);
    }


    /**
     * 分页获取数据
     *
     * @return List<BaseProductStyle>
     */
    @Override
    public List<BaseProductStyle> getPaginatedList(
            BaseProductStyleSearch baseProductStyleSearch) {
        return baseProductStyleMapper.selectPaginatedList(baseProductStyleSearch);
    }

    @Override
    public List<String> getProductStyleInfo(JSONObject styleJson) {
        String isLeaf_1 = "";
        if (styleJson.containsKey("isLeaf_1")) {
            isLeaf_1 = styleJson.getString("isLeaf_1");
        }
        String isLeaf_0 = "";
        if (styleJson.containsKey("isLeaf_0")) {
            isLeaf_0 = styleJson.getString("isLeaf_0");
        }

        // 获取父节点中的子节点,并且与isLeaf_0取并集 ->start
        List<Integer> sonIdList = new ArrayList<Integer>();
        if (StringUtils.isNotBlank(isLeaf_1)) {
            List<Integer> parentIdList = Utils.getIntegerListFromStringList(isLeaf_1,",");
            for (Integer parentId : parentIdList) {
                List<Integer> sonIdListItem = new ArrayList<Integer>();
                this.getIdListByPidRecursion(parentId, sonIdListItem);
                if (sonIdListItem != null && sonIdListItem.size() > 0) {
                    sonIdList.addAll(sonIdListItem);
                }
            }
        }
        if (StringUtils.isNotBlank(isLeaf_0)) {
            sonIdList.addAll(Utils.getIntegerListFromStringList(isLeaf_0,","));
        }
        // 获取父节点中的子节点,并且与isLeaf_0取并集 ->end

        List<String> nameList = new ArrayList<String>();
        if (sonIdList.size() > 0) {
            nameList = this.getNameByIdList(sonIdList);
        }
        return nameList;
    }

    /**
     * 通过idList找到所有风格名称
     *
     * @return
     * @author huangsongbo
     */
    @Override
    public List<String> getNameByIdList(List<Integer> idList) {
        return baseProductStyleMapper.getNameByIdList(idList);
    }

    /**
     * 通过id查找所有子节点id
     *
     * @param parentId
     * @return
     * @author huangsongbo
     */
    @Override
    public List<Integer> getIdListByPid(Integer parentId) {
        return baseProductStyleMapper.getIdListByPid(parentId);
    }


    /**
     * 查询设计方案字典数据
     * @return
     */
    @Override
    public List<DesignProductStyleDicVo> getDesignPlanStyleAllDic() {

        List<DesignProductStyleDicVo> designProductStyleDicVoList = null;

        List<DesignProductStyleDicPo> designPlanStyleAllDicList = baseProductStyleMapper.getDesignPlanStyleAllDic();

        if (null != designPlanStyleAllDicList) {
            Map<Integer, DesignProductStyleDicVo> designProductStyleDicVoMap = new HashMap<>();
            //遍历结果集----遍历数据
            designPlanStyleAllDicList.forEach(designProductStyleDicPo -> {
                //子节点和父节点相同则为空间根节点
                if (designProductStyleDicPo.getHouseType().equals(designProductStyleDicPo.getBaseProductStyleId())) {
                    //根节点数据--检查根节点是否存在
                    if (designProductStyleDicVoMap.containsKey(designProductStyleDicPo.getHouseType())) {
                        //若已创建根节点则覆盖根节点名字
                        //获取已存入父节点数据
                        DesignProductStyleDicVo designProductStyleDicVo = designProductStyleDicVoMap.get(designProductStyleDicPo.getHouseType());
                        designProductStyleDicVo.setHouseName(designProductStyleDicPo.getStyleName());
                        //装回对象
                        designProductStyleDicVoMap.put(designProductStyleDicPo.getHouseType(), designProductStyleDicVo);
                    } else {
                        //创建根节点
                        DesignProductStyleDicVo designProductStyleDicVo = new DesignProductStyleDicVo();
                        designProductStyleDicVo.setHouseName(designProductStyleDicPo.getStyleName());
                        designProductStyleDicVo.setHouseType(designProductStyleDicPo.getHouseType());
                        designProductStyleDicVoMap.put(designProductStyleDicPo.getHouseType(), designProductStyleDicVo);
                    }
                } else {
                    //子节点数据---检查根节点是否存在
                    if (designProductStyleDicVoMap.containsKey(designProductStyleDicPo.getHouseType())) {
                        //获取已存在父节点数据
                        DesignProductStyleDicVo designProductStyleDicVo = designProductStyleDicVoMap.get(designProductStyleDicPo.getHouseType());
                        //获取风格数据组
                        List<DesignPlanStyleVo> designPlanStyleVoList = designProductStyleDicVo.getDesignPlanStyleVoList();
                        //初始化List
                        if (null == designPlanStyleVoList || 0 == designPlanStyleVoList.size()) {
                            designPlanStyleVoList = new ArrayList<>();
                        }
                        //设计方案风格Vo
                        DesignPlanStyleVo designPlanStyleVo = new DesignPlanStyleVo();
                        designPlanStyleVo.setStyleCode(designProductStyleDicPo.getBaseProductStyleId());
                        designPlanStyleVo.setStyleName(designProductStyleDicPo.getStyleName());
                        //装入List
                        designPlanStyleVoList.add(designPlanStyleVo);
                        //装入Map对象
                        designProductStyleDicVo.setDesignPlanStyleVoList(designPlanStyleVoList);
                        designProductStyleDicVoMap.put(designProductStyleDicPo.getHouseType(), designProductStyleDicVo);
                    } else {
                        //构造数据
                        DesignProductStyleDicVo designProductStyleDicVo = new DesignProductStyleDicVo();
                        //空间类型
                        designProductStyleDicVo.setHouseType(designProductStyleDicPo.getHouseType());
                        List<DesignPlanStyleVo> designPlanStyleVoList = new ArrayList<>();
                        //设计方案风格Vo
                        DesignPlanStyleVo designPlanStyleVo = new DesignPlanStyleVo();
                        designPlanStyleVo.setStyleCode(designProductStyleDicPo.getBaseProductStyleId());
                        designPlanStyleVo.setStyleName(designProductStyleDicPo.getStyleName());
                        //装入List
                        designPlanStyleVoList.add(designPlanStyleVo);
                        //装入对象
                        designProductStyleDicVo.setDesignPlanStyleVoList(designPlanStyleVoList);
                        //装入Map
                        designProductStyleDicVoMap.put(designProductStyleDicPo.getHouseType(), designProductStyleDicVo);
                    }
                }
            });

            //第二次遍历---替换housetype
            Map<Integer, DesignProductStyleDicVo> tempDesignProductStyleDicVoMap = new HashMap<>(designProductStyleDicVoMap.size());
            designPlanStyleAllDicList.forEach(designProductStyleDicPo -> {
                if (null != designProductStyleDicPo.getOrgHouseType()) {
                    //获取原Map数据
                    DesignProductStyleDicVo designProductStyleDicVo = designProductStyleDicVoMap.get(designProductStyleDicPo.getHouseType());
                    designProductStyleDicVo.setHouseType(designProductStyleDicPo.getOrgHouseType());
                    tempDesignProductStyleDicVoMap.put(designProductStyleDicPo.getOrgHouseType(), designProductStyleDicVo);
                }
            });

            //Map转List-初始化List
            designProductStyleDicVoList = new ArrayList<>(tempDesignProductStyleDicVoMap.size());
            for (Map.Entry<Integer, DesignProductStyleDicVo> entry : tempDesignProductStyleDicVoMap.entrySet()) {
                designProductStyleDicVoList.add(entry.getValue());
            }
        }

        return designProductStyleDicVoList;
    }


    /**
     * 递归查询子节点
     *
     * @param parentId
     * @return
     */
    private List<Integer> getIdListByPidRecursion(Integer parentId, List<Integer> returnList) {
        List<Integer> sonIdList = this.getIdListByPid(parentId);
        if (sonIdList != null && sonIdList.size() > 0) {
            for (Integer sonId : sonIdList) {
                this.getIdListByPidRecursion(sonId, returnList);
            }
        } else {
            returnList.add(parentId);
        }
        return returnList;
    }

	@Override
	public String getNameById(Integer designRecommendedStyleId) {
		
		return baseProductStyleMapper.getNameById(designRecommendedStyleId);
	}

    @Override
    public List<String> getProductStyleInfo(String[] split) {
        List<Integer> styleIds = new ArrayList<>();
        for (int i = 0; i < split.length;i++) {
            Integer styleId = Integer.parseInt(split[i]);
            styleIds.add(styleId);
        }
        return baseProductStyleMapper.getNameByIdList(styleIds);
    }
}
