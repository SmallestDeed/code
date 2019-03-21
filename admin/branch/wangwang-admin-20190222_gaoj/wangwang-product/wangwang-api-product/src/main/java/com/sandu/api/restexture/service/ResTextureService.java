package com.sandu.api.restexture.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.restexture.input.ResTextureAdd;
import com.sandu.api.restexture.input.ResTextureQuery;
import com.sandu.api.restexture.input.ResTextureUpdate;
import com.sandu.api.restexture.model.ResTexture;
import com.sandu.api.restexture.model.bo.TreeBO;
import com.sandu.api.restexture.output.Tree;

import java.util.List;
import java.util.Map;

/**
 * @author bvvy
 */
public interface ResTextureService {

    /**
     * 添加材质
     *
     * @param resTextureAdd 添加内容
     * @return id
     */
    Long addResTexture(ResTextureAdd resTextureAdd);

    /**
     * 修改材质
     *
     * @param resTextureUpdate 修改内容
     * @return id
     */
    Long saveResTexture(ResTextureUpdate resTextureUpdate);

    List<TreeBO> listTextures();

    /**
     * 删除材质
     *
     * @param id id
     * @return 1, 0
     */
    int deleteResTexture(Long id);

    /**
     * 获取单个材质信息
     *
     * @param id id
     * @return record
     */
    ResTexture getResTextureDetail(Long id);

    /**
     * 分页查询材质
     *
     * @param resTextureQuery 条件
     * @return 分页信息
     * @deprecated
     */
    PageInfo<ResTexture> listResTexture(ResTextureQuery resTextureQuery);


    /**
     * 获取材质属性树
     *
     * @return 树
     */
    List<Tree> textureAttrTree();


    /**
     * 通过id集获取 MAP(id,filepath)
     *
     * @param ids id集
     * @return 树
     */
    Map<Integer, ResTexture> getIdAndFilePathByIds(List<Integer> ids);

    /**
     * 材质库搜索
     *
     * @param query 产需条件
     * @return 结果
     */
    PageInfo<ResTexture> queryResTexture(ResTextureQuery query);

    /**
     * 通过id集获取map(id , code )
     *
     * @param textureIds ids集
     * @return map(id, code)
     */
    Map<Integer, String> getTextureCodeByIds(List<Integer> textureIds);

    boolean importTexturesFromExcel(List<ResTextureAdd> beans);

    /**
     * 通过材质id和模型Id获取默认材质
     * @param idList
     * @param modelId
     * @return
     */
	List<Integer> getDefaultTextureIdsById(List<Integer> idList, Integer modelId);

}
