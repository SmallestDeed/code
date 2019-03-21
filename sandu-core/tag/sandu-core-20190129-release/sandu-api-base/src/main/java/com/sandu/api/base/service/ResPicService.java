package com.sandu.api.base.service;

import com.sandu.api.base.model.ResPic;
import com.sandu.api.base.output.ResPicVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResPicService {

    ResPic getResPicById(Integer id);

    List<String> getPathListByIdList(List<Integer> idList);

    List<ResPicVo> getByIdList(List<Integer> idList);

    ResPic selectByPrimaryKey(Integer id);
}
