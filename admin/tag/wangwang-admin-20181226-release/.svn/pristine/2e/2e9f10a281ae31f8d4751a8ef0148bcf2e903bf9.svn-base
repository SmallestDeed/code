package com.sandu.api.storage.service;

import com.sandu.api.storage.model.ResFile;
import com.sandu.commons.LoginUser;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 资源配置文件 service接口
 *
 * @auth xiaoxc
 * @data 2018-06-20
 */
@Component
public interface ResFileService {

    /**
     * 根据资源图片基础实体类 选择性 新增数据
     *
     * @param record 资源图片基础实体类 对象
     * @return 受影响的行数
     */
    int add(ResFile record);

    /**
     * 根据主键id 逻辑删除资源信息
     *
     * @param id 主键id
     * @return 受影响的行数
     */
    int delete(Long id);

    /**
     * 根据主键 选择性 修改数据
     *
     * @param record 资源图片基础实体类 对象
     * @return 受影响的行数
     */
    int update(ResFile record);

    /**
     * 通过主键查询信息
     *
     * @param id 主键ID
     * @return ResFile
     */
    ResFile getById(Long id);


    /**
     * 更新配置文件内容
     *
     * @param resId   file_id
     * @param content content
     * @return file_id
     */
    Long updateFile(Integer resId, String content);

    String readFile(@NotNull Integer resId);

	Map<Integer, String> idAndUrlMap(List<Integer> contractIds);

    /**
     * 保存配置文件及数据库数据
     * @param filePath
     * @param content
     * @return
     */
    Integer saveFile(String filePath, String content, LoginUser loginUser, Integer businessId, String fileKey, String fileType);

    /**
     * 更新配置文件内容
     * @param resId
     * @param content
     * @return
     */
    boolean updateFileFromSystem(Long resId, String content);
}
