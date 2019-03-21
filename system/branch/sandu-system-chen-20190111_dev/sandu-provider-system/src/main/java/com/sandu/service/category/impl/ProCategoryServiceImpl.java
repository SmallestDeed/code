package com.sandu.service.category.impl;

import com.sandu.api.category.input.ProCategoryListQuery;
import com.sandu.api.category.model.ProCategory;
import com.sandu.api.category.output.CategoryListVO;
import com.sandu.api.category.output.ProCategoryListVO;
import com.sandu.api.category.service.ProCategoryService;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.dictionary.output.FranchiserCategoryListVO;
import com.sandu.commons.util.StringUtils;
import com.sandu.service.category.dao.ProCategoryMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author chenqiang
 * @Description 产品分类 基础 业务层
 * @Date 2018/6/1 0001 10:26
 * @Modified By
 */
@Service("proCategoryService")
public class ProCategoryServiceImpl implements ProCategoryService {

    @Autowired
    private ProCategoryMapper proCategoryMapper;

    /**
     * 根据主键id 物理删除产品分类信息
     * @author chenqiang
     * @param id 主键id
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int deleteByPrimaryKey(Long id){
        return proCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据主键id 逻辑删除产品分类信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int deleteLogicByPrimaryKey(Long id, String loginName){
        return proCategoryMapper.deleteLogicByPrimaryKey(id,loginName);
    }

    /**
     * 根据产品分类基础实体类 选择性 新增数据
     * @author chenqiang
     * @param record 产品分类基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int insertSelective(ProCategory record){
        int count = proCategoryMapper.insertSelective(record);
        if(count > 0)
            return record.getId().intValue();
        else
            return 0;
    }

    /**
     * 根据主键id 查询 产品分类基础信息
     * @author chenqiang
     * @param id 产品分类主键id
     * @return 产品分类基础实体类
     * @date 2018/5/31 0031 18:21
     */
    public ProCategory selectByPrimaryKey(Long id){
        return proCategoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键 选择性 修改数据
     * @author chenqiang
     * @param record 产品分类基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int updateByPrimaryKeySelective(ProCategory record){
        return proCategoryMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据分类id 查询 下级 分类列表
     * @author chenqiang
     * @param query ProCategoryListQuery 对象
     * @return ProCategoryListVO 列表
     * @date 2018/5/31 0031 18:21
     */
    public List<ProCategoryListVO> getCategoryListByPidLevel(ProCategoryListQuery query){

        List<ProCategoryListVO> categoryList = proCategoryMapper.getCategoryListByPidLevel(query);

        return categoryList != null ? categoryList : new ArrayList<>();
    }

    /**
     * 根据id集合 查询 名称 字符串
     * @author chenqiang
     * @param idList id集合
     * @return 分类名称字符串
     * @date 2018/5/31 0031 18:21
     */
    public String getCategoryNames(List<Integer> idList){
        return proCategoryMapper.selectCategoryNames(idList);
    }


    @Override
    public List<Integer> getParentId(List<Integer> idList) {
        return proCategoryMapper.findParentId(idList);
    }


    /**
     * 根据idList 获取 上级 id 集合
     * @author chenqiang
     * @param idList id集合
     * @return 上级 id 集合
     * @date 2018/5/31 0031 18:21
     */
    public List<Integer> getPCategoryListByIdList(List<Integer> idList){
        return proCategoryMapper.selectPCategoryListByIdList(idList);
    }

    /**
     * 根据pid 与 idList 获取下级 集合
     * @author chenqiang
     * @param idList 下级id集合
     * @param pid 父id
     * @return 下级 分类 集合
     * @date 2018/5/31 0031 18:21
     */
    public List<ProCategoryListVO> getListByIdListPid(List<Integer> idList,Integer pid){
        return proCategoryMapper.selectListByIdListPid(idList,pid);
    }

    /**
     * 根据 idList 集合
     * @author chenqiang
     * @param idList 下级id集合
     * @return 分类 集合
     * @date 2018/6/12
     */
    public List<CategoryListVO> getListByIdList(List<Integer> idList){
        return proCategoryMapper.selectListByIdList(idList);
    }

    /**
     * 根据参数 查询 是否存在这些分类id
     * @author chenqiang
     * @param categoryIds id
     * @param level 级别
     * @return true:没问题
     * @date 2018/6/12
     */
    public boolean checkCategoryIds(String categoryIds,String level){

        if(StringUtils.isEmpty(categoryIds)||StringUtils.isEmpty(level))
            return true;

        List<Integer> idList = StringUtil.getListByString(categoryIds);

        int count = proCategoryMapper.selectCountByIdList(idList,Integer.valueOf(level));

        if(idList.size() != count){
            return false;
        }

        return true;
    }


    /**
    * @description： 根据产品分类编码集合,等级 查询产品分类集合
    * @author : WangHaiLin
    * @date 2018/7/23 15:47
    * @param codeList 产品分类编码集合
    * @param level 分类等级
    * @return: java.util.List<com.sandu.api.category.output.CategoryListVO>
    *
    */
    @Override
    public List<CategoryListVO> getListByCodeList(List<String> codeList, String level) {
        if(null!=codeList&& org.apache.commons.lang3.StringUtils.isNotBlank(level)){
            return proCategoryMapper.selectListByCodeList(codeList,Integer.valueOf(level));
        }else{
            return null;
        }
    }


    /**
     * wangHl
     * 通过Pid查询可见产品范围
     * @param pId pid
     * @return 可见产品集合
     */
    @Override
    public List<CategoryListVO> getCategoryByPId(Long pId) {
        if (null!=pId){
            return proCategoryMapper.selectCategoryByPid(pId);
        }
        return null;
    }

}
