package com.sandu.service.restexture.dao;

import com.sandu.api.restexture.input.ResTextureQuery;
import com.sandu.api.restexture.model.ResTexture;
import com.sandu.api.restexture.model.bo.TreeBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author bvvy
 */
@Repository
public interface ResTextureDao {
    /**
     * 添加材质
     *
     * @param resTexture record
     * @return 1, 0
     */
    int addResTexture(ResTexture resTexture);

    /**
     * 修改材质
     *
     * @param resTexture record
     */
    void saveResTexture(ResTexture resTexture);

    /**
     * 获取材质详情
     *
     * @param resTextureId id
     * @return 材质详情
     */
    ResTexture getResTextureDetail(@Param("resTextureId") long resTextureId);

    /**
     * 删除材质
     *
     * @param resTextureId id
     * @return 1, 0
     */
    int deleteResTexture(@Param("resTextureId") long resTextureId);

    /**
     * (
     * 搜索材质
     *
     * @param resTextureQuery 条件
     * @return 结果
     * @deprecated
     */
    List<ResTexture> listResTexture(ResTextureQuery resTextureQuery);


    /**
     * 获取最大的id
     *
     * @return id
     */
    String getMaxId();

    /**
     * 获取textureAttr树
     * @return tree bo
     */
    List<TreeBO> textureAttrTree();

    /**
     * 类表材质option
     *
     * @return 树
     */
    List<TreeBO> listTextures();

    /**
     * 通过id集获取id和文件路径
     *
     * @param ids id集
     * @return 列表
     */
    List<ResTexture> getIdAndFilePathByIds(List<Integer> ids);

    /**
     * 搜索材质
     *
     * @param query 条件
     * @return 结果
     */
    List<ResTexture> queryResTexture(ResTextureQuery query);

    /**
     * 通过ids获取材质编码
     *
     * @param textureIds id集
     * @return 结果
     */
    List<ResTexture> getTextureCodeByIds(List<Integer> textureIds);

    /**
     * 获取模型的默认材质
     * @param idList
     * @param modelId
     * @return
     */
	List<Integer> getDefaultTextureIdsById(@Param("idList")List<Integer> idList, @Param("modelId")Integer modelId);
}
