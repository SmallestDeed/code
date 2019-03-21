package com.sandu.api.pic.service;

import com.sandu.api.pic.model.ResPic;
import com.sandu.api.pic.model.po.ResPicBusinessUpdate;
import com.sandu.api.pic.model.po.ResPicPO;
import com.sandu.api.shop.model.ProjectCase;
import com.sandu.commons.LoginUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author chenqiang
 * @Description 资源图片 基础 业务层
 * @Date 2018/6/1 0001 10:30
 * @Modified By
 */
@Component
public interface ResPicService {
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
    int deleteLogicByPrimaryKey(Long id, String loginName);

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
     * 删除图片信息/支持批量删除
     * @author chenqiang
     * @param ids 主键ids
     * @return 是否成功Boolean
     */
    boolean deletePic(String ids,LoginUser loginUser);

    /**
     * 查询该数据是否有共享一个文件
     * @author chenqiang
     * @param picPath 路径
     * @return 新增数据主键id
     */
    int picPathCount(String picPath);

    /**
     * 将图片上传信息保存到resPic
     * @author chenqiang
     * @param map 上传服务器文件的详细信息
     * @param businessId 该资源所关联的表id
     * @param loginUser 登录名
     * @return 新增数据主键id
     */
    Integer saveUploadImgPic(Map map, Integer businessId, LoginUser loginUser,String mes);

    /**
     * 自动存储系统字段
     * @author chenqiang
     * @param pic 图片
     * @param loginUser 当前登录用户
     */
    void saveSystemInfo(ResPic pic, LoginUser loginUser);

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
    List<ResPicPO> findByIds(List<Integer> idList);

    List<com.sandu.system.model.ResPic> getResPicByIds(List<Integer> picIdList);

    com.sandu.system.model.ResPic get(Integer defaultPicId);

    Map<Long, String> mapPic(List<Long> listPhoto);
}
