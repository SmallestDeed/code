package com.sandu.api.system.service;

import com.sandu.api.system.model.ResVersion;

import java.util.List;

public interface ResVersionService {

    ResVersion get(Integer patchFileId);

    List<ResVersion> getList(ResVersion exeFile_search);
}
