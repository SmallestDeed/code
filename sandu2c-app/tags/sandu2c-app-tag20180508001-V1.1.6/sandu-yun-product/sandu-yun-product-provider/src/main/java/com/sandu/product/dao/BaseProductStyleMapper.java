package com.sandu.product.dao;

import com.sandu.product.model.BaseProductStyle;
import com.sandu.product.model.search.BaseProductStyleSearch;
import com.sandu.product.po.DesignProductStyleDicPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: BaseProductStyleMapper.java
 * @Package com.sandu.product.dao
 * @Description:产品管理-产品风格Mapper
 * @createAuthor pandajun
 * @CreateDate 2017-04-17 11:42:49
 */
@Repository
public interface BaseProductStyleMapper {
    int insertSelective(BaseProductStyle record);

    int updateByPrimaryKeySelective(BaseProductStyle record);

    int deleteByPrimaryKey(Integer id);

    BaseProductStyle selectByPrimaryKey(Integer id);

    int selectCount(BaseProductStyleSearch baseProductStyleSearch);

    List<BaseProductStyle> selectPaginatedList(BaseProductStyleSearch baseProductStyleSearch);

    List<BaseProductStyle> selectList(BaseProductStyle baseProductStyle);

    /**
     * Ajax数据验证
     **/
    BaseProductStyle findOne(BaseProductStyle baseProductStyle);

    /**
     * 组装产品属性树形结构数据
     *
     * @param pid
     * @return
     */
    List<BaseProductStyle> asyncLoadTree(@Param("pid") Integer pid, @Param("code") String code);

    List<BaseProductStyle> editOne(BaseProductStyle baseProductStyle);

    /**
     * 修改分类.如果code变动过，则需要修改分类下所有子节点的code
     *
     * @return
     */
    int recursionUpdate(Map<String, Object> params);

    List<BaseProductStyle> findLike(@Param("longCode") String longCode);

    int updateOne(@Param("longCode") String longCode, @Param("id") Integer id);

    List<Integer> getIdListByPid(@Param("pid") Integer pid);

    List<String> getNameByIdList(@Param("idList") List<Integer> idList);

    /**
     * 根据空间类型得到相应风格
     */
    List<BaseProductStyle> getStyleByHouseType(Integer functionId);

    /**
     * 查询设计方案字典数据
     * @return
     */
    List<DesignProductStyleDicPo> getDesignPlanStyleAllDic();

	String getNameById(Integer designRecommendedStyleId);

}
