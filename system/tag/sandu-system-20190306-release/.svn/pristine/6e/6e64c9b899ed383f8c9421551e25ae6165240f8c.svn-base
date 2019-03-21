package com.sandu.service.pic.dao;

import com.sandu.api.pic.model.ResPic;
import com.sandu.api.pic.model.po.ResPicBusinessUpdate;
import com.sandu.api.pic.model.po.ResPicPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author chenqiang
 * @Description 资源图片 dao 持久层
 * @Date 2018/5/31 0031 18:18
 * @Modified By
 */
@Repository
public interface ResPicMapper {

    /**
     * 根据主键id 物理删除资源图片信息
     * @author chenqiang
     * @param id 主键id
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键id 逻辑删除资源图片信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteLogicByPrimaryKey(@Param("id") Long id, @Param("loginName")String loginName);

    /**
     * 根据资源图片基础实体类 选择性 新增数据
     * @author chenqiang
     * @param record 资源图片基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int insertSelective(ResPic record);

    /**
     * 根据主键id 查询 资源图片基础信息
     * @author chenqiang
     * @param id 资源图片主键id
     * @return 资源图片基础实体类
     * @date 2018/5/31 0031 18:21
     */
    ResPic selectByPrimaryKey(Long id);

    /**
     * 根据主键 选择性 修改数据
     * @author chenqiang
     * @param record 资源图片基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int updateByPrimaryKeySelective(ResPic record);

    /**
     * 查询该数据是否有共享一个文件
     * @author chenqiang
     * @param picPath 路径
     * @return 新增数据主键id
     */
    int picPathCount(String picPath);

    /**
     * 批量更新业务Id
     * @author xiaoxc
     * @param businessUpdate 业务/资源Id
     */
    int batchUpdateBusinessId(ResPicBusinessUpdate businessUpdate);

    /**
     * 通过ID集合查询
     * @author xiaoxc
     * @return List<ResPicPO> PO集合
     */
    List<ResPicPO> findByIds(@Param("idList") List<Integer> idList);

    List<com.sandu.system.model.ResPic> getResPicByIds(List<Integer> picIdList);

    com.sandu.system.model.ResPic get(Integer id);

    List<ResPic> listResPic(List<Long> listPhoto);
}