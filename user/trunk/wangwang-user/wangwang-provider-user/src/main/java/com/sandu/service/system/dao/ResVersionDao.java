package com.sandu.service.system.dao;

import com.sandu.api.system.model.ResVersion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResVersionDao {
    ResVersion selectByPrimaryKey(Integer patchFileId);

    List<ResVersion> selectList(ResVersion resVersion);
}
