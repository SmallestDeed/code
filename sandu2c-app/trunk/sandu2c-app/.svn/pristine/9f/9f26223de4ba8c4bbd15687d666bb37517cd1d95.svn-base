package com.sandu.system.service;

import com.nork.common.model.LoginUser;
import com.sandu.common.file.FileVO;
import com.sandu.system.model.ResPic;
import com.sandu.system.model.search.ResPicSearch;

import java.util.List;

/**
 * @version V1.0
 * @Title: ResPicService.java
 * @Package com.sandu.system.service
 * @Description:系统-图片资源库Service
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 16:06:59
 */
public interface ResPicService {
    /**
     * 新增数据
     *
     * @param resPic
     * @return int
     */
    int add(ResPic resPic);

    /**
     * 更新数据
     *
     * @param resPic
     * @return int
     */
    int update(ResPic resPic);


    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return ResPic
     */
    ResPic get(Integer id);


    /**
     * 所有数据
     *
     * @param resPic
     * @return List<ResPic>
     */
    List<ResPic> getList(ResPic resPic);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(ResPicSearch resPicSearch);

    /**
     * 分页获取数据
     *
     * @return List<ResPic>
     */
    List<ResPic> getPaginatedList(ResPicSearch resPictSearch);

    /**
     * 获取多条数据
     *
     * @return List<ResPic>
     */
    List<ResPic> getResPicByIds(List<Integer> ids);

    Long saveUserResPic(FileVO fileVO, String type, String absolutePath, LoginUser loginUser, String storagePath,String module);
}
