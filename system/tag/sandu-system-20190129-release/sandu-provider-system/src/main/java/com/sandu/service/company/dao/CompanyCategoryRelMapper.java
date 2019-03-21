package com.sandu.service.company.dao;

import com.sandu.api.company.model.CompanyCategoryRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyCategoryRelMapper {
    /**
     * 根据主键id 物理删除企业信息
     * @author chenqiang
     * @param id 主键id
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键id 逻辑删除企业信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteLogicByPrimaryKey(@Param("id") Long id, @Param("loginName")String loginName);

    /**
     * 根据企业基础实体类 选择性 新增数据
     * @author chenqiang
     * @param record 企业基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int insertSelective(CompanyCategoryRel record);

    /**
     * 根据主键id 查询 企业基础信息
     * @author chenqiang
     * @param id 企业主键id
     * @return 企业基础实体类
     * @date 2018/5/31 0031 18:21
     */
    CompanyCategoryRel selectByPrimaryKey(Long id);

    /**
     * 根据主键 选择性 修改数据
     * @author chenqiang
     * @param record 企业基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int updateByPrimaryKeySelective(CompanyCategoryRel record);

    /**
     * 批量新增 企业产品分类表
     * @author chenqiang
     * @param companyCategoryRelList 对象集合
     * @return 影响行数
     * @date 2018/5/31 0031 18:21
     */
    int insertListCompanyCategoryRel(@Param("companyCategoryRelList") List<CompanyCategoryRel> companyCategoryRelList);

    /**
     * 根据企业id  删除 企业产品分类表
     * @author chenqiang
     * @param companyId 企业id
     * @return 影响行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteCompanyCategoryRelByCompanyId(@Param("companyId") Integer companyId);

    /**
     * 根据企业id  获取 企业产品分类信息
     * @author chenqiang
     * @param companyId 企业id
     * @return CategoryIds 分类信息
     * @date 2018/5/31 0031 18:21
     */
    String selectCompanyCategoryIds(@Param("companyId") Integer companyId);
}