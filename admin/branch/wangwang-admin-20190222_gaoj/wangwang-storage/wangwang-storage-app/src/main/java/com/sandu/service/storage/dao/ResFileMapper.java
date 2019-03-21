package com.sandu.service.storage.dao;

import com.sandu.api.storage.model.ResFile;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Desc 资源文件dao持久层
 * @Auth xiaoxc
 * @Date 2018-06-20
 * @Modified By
 */
@Repository
public interface ResFileMapper {

    /**
     * 根据资源图片基础实体类 选择性 新增数据
     * @param record 资源图片基础实体类 对象
     * @return 受影响的行数
     */
    int insert(ResFile record);

    /**
     * 根据主键id 逻辑删除资源信息
     * @param id 主键id
     * @return 受影响的行数
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键 选择性 修改数据
     * @param record 资源图片基础实体类 对象
     * @return 受影响的行数
     */
    int updateByPrimaryKey(ResFile record);

    /**
     * 通过主键查询信息
     * @param id 主键ID
     * @return ResFile
     */
    ResFile findById(Long id);

	List<ResFile> listByIds(@Param("ids") List<Integer> contractIds);


}