package com.sandu.service.brand.impl;

import com.sandu.api.brand.input.CompanyBrandQuery;
import com.sandu.api.brand.model.BaseBrand;
import com.sandu.api.brand.output.CompanyBrandVO;
import com.sandu.api.brand.output.FranchiserBrandVO;
import com.sandu.api.brand.service.BaseBrandService;
import com.sandu.api.company.common.StringUtil;
import com.sandu.service.brand.dao.BaseBrandMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author chenqiang
 * @Description 品牌 基础 业务层
 * @Date 2018/6/1 0001 10:25
 * @Modified By
 */
@Service("baseBrandService")
public class BaseBrandServiceImpl implements BaseBrandService{

    @Autowired
    private BaseBrandMapper baseBrandMapper;

    /**
     * 根据主键id 物理删除品牌信息
     * @author chenqiang
     * @param id 主键id
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int deleteByPrimaryKey(Long id){
        return baseBrandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据主键id 逻辑删除品牌信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int deleteLogicByPrimaryKey(Long id, String loginName){
        return baseBrandMapper.deleteLogicByPrimaryKey(id,loginName);
    }

    /**
     * 根据品牌基础实体类 选择性 新增数据
     * @author chenqiang
     * @param record 品牌基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int insertSelective(BaseBrand record){
        int count = baseBrandMapper.insertSelective(record);
        if(count > 0)
            return record.getId().intValue();
        else
            return 0;
    }

    /**
     * 根据主键id 查询 品牌基础信息
     * @author chenqiang
     * @param id 品牌主键id
     * @return 品牌基础实体类
     * @date 2018/5/31 0031 18:21
     */
    public BaseBrand selectByPrimaryKey(Long id){
        return baseBrandMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键 选择性 修改数据
     * @author chenqiang
     * @param record 品牌基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int updateByPrimaryKeySelective(BaseBrand record){
        return baseBrandMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据企业id 查询 所属品牌信息
     * @author chenqiang
     * @param query 企业品牌查询实体类 对象
     * @return 企业所属品牌列表
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public List<CompanyBrandVO> getBrandByCompanyId(CompanyBrandQuery query){

        List<CompanyBrandVO> brandList = baseBrandMapper.selectBrandByCompanyId(query);

        return brandList != null ? brandList : new ArrayList<>();
    }

    /**
     * 根据企业id 查询 所属品牌信息
     * @author chenqiang
     * @param companyId 企业id
     * @return 企业所属品牌名称
     * @date 2018/5/31 0031 18:21
     */
    public String getBrandnamesByCompanyId(Integer companyId){
        return baseBrandMapper.selectBrandnamesByCompanyId(companyId);
    }


    /**
     * 根据企业id 查询 所属品牌名称
     * @author chenqiang
     * @param idList 品牌ids
     * @return 品牌名称字符串
     * @date 2018/5/31 0031 18:21
     */
    public String getBrandNamesByIds(List<Integer> idList){
        return baseBrandMapper.selectBrandNamesByIds(idList);
    }

    /**
     * 根据品牌ids 查询 品牌集合
     * @author chenqiang
     * @param idList ids
     * @return 品牌集合
     * @date 2018/6/12 0012 10:01
     */
    public List<FranchiserBrandVO> getBrandList(List<Integer> idList){
        return baseBrandMapper.selectBrandList(idList);
    }


    /**
     * 根据品牌ids 查询 品牌集合
     * @author chenqiang
     * @param brandIds ids
     * @param companyId 企业id
     * @return true:没问题
     * @date 2018/6/12 0012 10:01
     */
    public boolean checkBrandIds(String brandIds,Integer companyId){
        if(StringUtils.isEmpty(brandIds)||null == companyId)
            return true;
        List<Integer> idList = StringUtil.getListByString(brandIds);

        int count = baseBrandMapper.selectCountByCompanyIdList(idList,companyId);

        if(count != idList.size()){
            return false;
        }

        return true;
    }
}
