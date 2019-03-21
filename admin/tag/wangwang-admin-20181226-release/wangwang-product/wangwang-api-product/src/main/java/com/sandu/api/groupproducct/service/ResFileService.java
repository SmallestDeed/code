package com.sandu.api.groupproducct.service;

import java.util.List;
import java.util.Map;

import com.sandu.api.groupproducct.model.ResFile;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * wangwang-product
 *
 * @author Sandu
 * @datetime 2018/3/27 16:43
 */
public interface ResFileService {
    /**
     * 新增文件
     * @param resFile
     * @return
     */
    Integer addFile(ResFile resFile);

    /**
     * 获取文件
     * @param id
     * @return
     */
    ResFile getFileById(Integer id);

    boolean updateFileById(ResFile file);

	Map<Long, String> idAndUrlMap(List<Integer> contractIds);
}
