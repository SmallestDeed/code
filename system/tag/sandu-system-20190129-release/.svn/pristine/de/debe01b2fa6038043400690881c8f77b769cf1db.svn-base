package com.sandu.service.company.impl;

import com.sandu.api.company.model.CompanyCategoryRel;
import com.sandu.api.company.service.CompanyCategoryRelService;
import com.sandu.service.company.dao.CompanyCategoryRelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author chenqiang
 * @Description 企业产品分类 基础 业务层
 * @Date 2018/6/8 0008 11:24
 * @Modified By
 */
@Service("companyCategoryRelService")
public class CompanyCategoryRelServiceImpl implements CompanyCategoryRelService {

    @Autowired
    private CompanyCategoryRelMapper companyCategoryRelMapper;

    /**
     * 根据主键id 物理删除企业信息
     * @author chenqiang
     * @param id 主键id
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int deleteByPrimaryKey(Long id){
        return companyCategoryRelMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据主键id 逻辑删除企业信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int deleteLogicByPrimaryKey(Long id, String loginName){
        return companyCategoryRelMapper.deleteLogicByPrimaryKey(id,loginName);
    }

    /**
     * 根据企业基础实体类 选择性 新增数据
     * @author chenqiang
     * @param record 企业基础实体类 对象
     * @return 主键id
     * @date 2018/5/31 0031 18:21
     */
    public int insertSelective(CompanyCategoryRel record){
        int count = companyCategoryRelMapper.insertSelective(record);
        if(count > 0 )
            return record.getId().intValue();
        else
            return 0;
    }

    /**
     * 根据主键id 查询 企业基础信息
     * @author chenqiang
     * @param id 企业主键id
     * @return 企业基础实体类
     * @date 2018/5/31 0031 18:21
     */
    public CompanyCategoryRel selectByPrimaryKey(Long id){
        return companyCategoryRelMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键 选择性 修改数据
     * @author chenqiang
     * @param record 企业基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int updateByPrimaryKeySelective(CompanyCategoryRel record){
        return companyCategoryRelMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 批量新增 企业产品分类表
     * @author chenqiang
     * @param companyCategoryRelList 对象集合
     * @return 影响行数
     * @date 2018/5/31 0031 18:21
     */
    public int addListCompanyCategoryRel(List<CompanyCategoryRel> companyCategoryRelList){
        return companyCategoryRelMapper.insertListCompanyCategoryRel(companyCategoryRelList);
    }

    /**
     * 根据企业id  删除 企业产品分类表
     * @author chenqiang
     * @param companyId 企业id
     * @return 影响行数
     * @date 2018/5/31 0031 18:21
     */
    public int delCompanyCategoryRelByCompanyId(Integer companyId){
        return companyCategoryRelMapper.deleteCompanyCategoryRelByCompanyId(companyId);
    }

    /**
     * 根据企业id  获取 企业产品分类信息
     * @author chenqiang
     * @param companyId 企业id
     * @return CategoryIds 分类信息
     * @date 2018/5/31 0031 18:21
     */
    public String getCompanyCategoryIds( Integer companyId){
        return companyCategoryRelMapper.selectCompanyCategoryIds(companyId);
    }
}
