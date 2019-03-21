package com.sandu.api.company.service;

import com.sandu.api.company.model.CompanyCategoryRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @Author chenqiang
 * @Description 企业产品分类 基础 业务层
 * @Date 2018/6/8 0008 11:22
 * @Modified By
 */
@Component
public interface CompanyCategoryRelService {
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
    int deleteLogicByPrimaryKey(Long id, String loginName);

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
     * 新增企业产品分类表
     * @author chenqiang
     * @param companyCategoryRelList 对象集合
     * @return 影响行数
     * @date 2018/5/31 0031 18:21
     */
    int addListCompanyCategoryRel(List<CompanyCategoryRel> companyCategoryRelList);

    /**
     * 根据企业id  删除 企业产品分类表
     * @author chenqiang
     * @param companyId 企业id
     * @return 影响行数
     * @date 2018/5/31 0031 18:21
     */
    int delCompanyCategoryRelByCompanyId(Integer companyId);

    /**
     * 根据企业id  获取 企业产品分类信息
     * @author chenqiang
     * @param companyId 企业id
     * @return CategoryIds 分类信息
     * @date 2018/5/31 0031 18:21
     */
    String getCompanyCategoryIds( Integer companyId);
}
