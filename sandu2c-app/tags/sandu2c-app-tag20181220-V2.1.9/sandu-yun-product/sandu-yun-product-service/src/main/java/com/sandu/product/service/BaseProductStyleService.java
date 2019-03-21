package com.sandu.product.service;

import com.sandu.product.model.BaseProductStyle;
import com.sandu.product.model.search.BaseProductStyleSearch;
import com.sandu.product.vo.DesignProductStyleDicVo;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @version V1.0
 * @Title: BaseProductStyleService.java
 * @Package com.sandu.product.service
 * @Description:产品管理-产品风格Service
 * @createAuthor pandajun
 * @CreateDate 2017-04-17 11:42:49
 */
public interface BaseProductStyleService {
    /**
     * 新增数据
     *
     * @param baseProductStyle
     * @return int
     */
    int add(BaseProductStyle baseProductStyle);

    /**
     * 更新数据
     *
     * @param baseProductStyle
     * @return int
     */
    int update(BaseProductStyle baseProductStyle);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseProductStyle
     */
    BaseProductStyle get(Integer id);

    /**
     * 所有数据
     *
     * @param baseProductStyle
     * @return List<BaseProductStyle>
     */
    List<BaseProductStyle> getList(BaseProductStyle baseProductStyle);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(BaseProductStyleSearch baseProductStyleSearch);

    /**
     * 分页获取数据
     *
     * @return List<BaseProductStyle>
     */
    List<BaseProductStyle> getPaginatedList(BaseProductStyleSearch baseProductStyletSearch);

    /**
     * 通过styleJson获取材质中文名
     *
     * @param styleJson
     * @return
     */
    List<String> getProductStyleInfo(JSONObject styleJson);

    List<String> getNameByIdList(List<Integer> idList);

    List<Integer> getIdListByPid(Integer parentId);

    /**
     * 查询设计方案字典数据
     *
     * @return
     */
    List<DesignProductStyleDicVo> getDesignPlanStyleAllDic();

    String getNameById(Integer designRecommendedStyleId);

    List<String> getProductStyleInfo(String[] split);
}
