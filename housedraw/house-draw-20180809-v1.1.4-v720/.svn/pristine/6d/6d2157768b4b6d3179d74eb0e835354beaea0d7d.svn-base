package com.sandu.service.file.dao;

import java.util.List;

import com.sandu.api.space.bo.DrawSpaceFileBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.house.model.DrawResFile;

/**
 * Description:
 * 绘制资源文件数据层
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/28
 */
@Repository
public interface DrawResFileMapper {
    /**
     * 删除资源文件
     * @param restoreFileId 户型编码
     * @param fileType 文件类型
     */
    void deleteDrawResFile(@Param("restoreFileId") Long restoreFileId, @Param("fileType") String fileType);

    /**
     * 保存户型绘制资源文件
     * @param file
     * @return
     */
    Long saveDrawResFile(DrawResFile file);

    /**
     *  维护未烘焙文件和已烘焙文件的关系 （产品文件）
     * @param resModelId 烘焙后的模型文件
     * @param fileId 未烘焙前的文件
     */
    void updateFileRelation(@Param("resModelId") String resModelId, @Param("fileId") Long fileId);

    /**
     * 保存资源文件
     * @param configFile
     */
    Long saveResFile(DrawResFile configFile);

    /**
     * 批量保存文件
     * @param allFiles
     */
    void batchSave(@Param("files") List<DrawResFile> allFiles);

	DrawResFile selectByPrimaryKey(@Param("id") Long id);

	void addBatchFile(List<DrawResFile> files);

    Integer updateByPrimaryKeySelective(DrawResFile drawResFile);

    List<DrawResFile> listDrawResFileById(List<Integer> u3dOfWindows);

    Integer clearDrawResFileResource(@Param("files") List<Long> files);

    List<DrawSpaceFileBO> listDrawResFileByDrawSpaceId(@Param("emptySpaces") List<Long> emptySpaces,
                                                       @Param("isModel") Integer isModel);

    List<DrawSpaceFileBO> listDrawResFileByFileIds(@Param("fileIds") List<Long> fileIds);

    List<DrawSpaceFileBO> listTaskCleanDrawResFile(@Param("limit") Integer limit,
                                                   @Param("minFileId") Long minFileId,
                                                   @Param("maxFileId") Long maxFileId);
}
