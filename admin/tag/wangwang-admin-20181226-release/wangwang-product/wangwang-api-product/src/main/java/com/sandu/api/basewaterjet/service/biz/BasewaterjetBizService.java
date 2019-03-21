package com.sandu.api.basewaterjet.service.biz;

import com.sandu.api.basewaterjet.input.BasewaterjetAdd;
import com.sandu.api.basewaterjet.input.BasewaterjetQuery;
import com.sandu.api.basewaterjet.input.BasewaterjetUpdate;
import com.sandu.api.basewaterjet.model.Basewaterjet;
import com.sandu.api.basewaterjet.output.BrandNameVO;
import com.sandu.commons.LoginUser;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * base_waterjet
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-09 10:03
 */
@Component
public interface BasewaterjetBizService {

    /**
     * 创建
     * @param input
     * @return
     */
    int create(BasewaterjetAdd input,LoginUser loginUser);

    /**
     * 更新
     * @param input
     * @return
     */
    int update(BasewaterjetUpdate input,LoginUser loginUser);

    /**
     * 删除
     */
    int delete(String basewaterjetId,LoginUser loginUser);

    /**
     * 通过ID获取详情
     * @param basewaterjetId
     * @return
     */
    Basewaterjet getById(int basewaterjetId);

    /**
     * 查询列表
     * @param query 查询条件
     * @return
     */
    List<Basewaterjet> query(BasewaterjetQuery query);
    int queryCount(BasewaterjetQuery query);

    /**
     * 获取当前所属品牌
     * @param userId 用户id
     * */
    List<BrandNameVO> getBrandNameList(Integer userId,LoginUser loginUser );

    /**
     * 上下架
     * @author chenqiang
     * @param basewaterjetIds
     * @return 
     * @date 2018/11/10 0010 16:20
     */
    int upperandlowerstatus(String basewaterjetIds,Integer templateStatus,LoginUser loginUser);

    /**
     * 将图片上传信息保存到resPic
     * @author chenqiang
     * @param map 上传服务器文件的详细信息
     * @param businessId 该资源所关联的表id
     * @param loginUser 登录名
     * @return 新增数据主键id
     */
    Integer saveUploadImgPic(Map map, Integer businessId, LoginUser loginUser, String mes);

    /**
     * 将文件上传信息保存到resfile
     * @author chenqiang
     * @param map 上传服务器文件的详细信息
     * @param businessId 该资源所关联的表id
     * @param loginUser 登录名
     * @return 新增数据主键id
     */
    Integer saveUploadFile(Map map, Integer businessId, LoginUser loginUser, String mes);
}
