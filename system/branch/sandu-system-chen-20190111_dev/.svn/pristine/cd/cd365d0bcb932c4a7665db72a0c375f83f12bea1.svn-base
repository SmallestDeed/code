package com.sandu.service.brand.dao;

import com.sandu.api.brand.input.CompanyBrandQuery;
import com.sandu.api.brand.model.BaseBrand;
import com.sandu.api.brand.output.CompanyBrandVO;
import com.sandu.api.brand.output.FranchiserBrandVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author chenqiang
 * @Description 品牌 dao 持久层
 * @Date 2018/5/31 0031 18:18
 * @Modified By
 */
@Repository
public interface BaseBrandMapper {

    /**
     * 根据主键id 物理删除品牌信息
     * @author chenqiang
     * @param id 主键id
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键id 逻辑删除品牌信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteLogicByPrimaryKey(@Param("id") Long id, @Param("loginName")String loginName);

    /**
     * 根据品牌基础实体类 选择性 新增数据
     * @author chenqiang
     * @param record 品牌基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int insertSelective(BaseBrand record);

    /**
     * 根据主键id 查询 品牌基础信息
     * @author chenqiang
     * @param id 品牌主键id
     * @return 品牌基础实体类
     * @date 2018/5/31 0031 18:21
     */
    BaseBrand selectByPrimaryKey(Long id);

    /**
     * 根据主键 选择性 修改数据
     * @author chenqiang
     * @param record 品牌基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int updateByPrimaryKeySelective(BaseBrand record);

    /**
     * 根据企业id 查询 所属品牌信息
     * @author chenqiang
     * @param query 企业品牌查询实体类 对象
     * @return 企业所属品牌列表
     * @date 2018/5/31 0031 18:21
     */
    List<CompanyBrandVO> selectBrandByCompanyId(CompanyBrandQuery query);

    /**
     * 根据企业id 查询 所属品牌名称
     * @author chenqiang
     * @param companyId 企业id
     * @return 企业所属品牌名称
     * @date 2018/5/31 0031 18:21
     */
    String selectBrandnamesByCompanyId(@Param("companyId")Integer companyId);

    /**
     * 根据企业id 查询 所属品牌名称
     * @author chenqiang
     * @param idList 品牌ids
     * @return 品牌名称字符串
     * @date 2018/5/31 0031 18:21
     */
    String selectBrandNamesByIds(@Param("idList") List<Integer> idList);

    /**
     * 根据品牌ids 查询 品牌集合
     * @author chenqiang
     * @param idList ids
     * @return 品牌集合
     * @date 2018/6/12 0012 10:01
     */
    List<FranchiserBrandVO> selectBrandList(@Param("idList") List<Integer> idList);

    /**
     * 根据品牌ids 查询 品牌集合
     * @author chenqiang
     * @param idList ids
     * @param companyId 企业id
     * @return true:没问题
     * @date 2018/6/12 0012 10:01
     */
    int selectCountByCompanyIdList(@Param("idList") List<Integer> idList,@Param("companyId")Integer companyId);

}