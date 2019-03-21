package com.sandu.service.solution.impl;

import com.sandu.api.solution.model.ResRenderPic;
import com.sandu.api.solution.service.ResRenderPicService;
import com.sandu.service.solution.dao.ResRenderPicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sandu.util.Commoner.isEmpty;

/**
 * ResRenderPicServiceImpl class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Service("resRenderPicService")
public class ResRenderPicServiceImpl implements ResRenderPicService {

    @Resource
    private ResRenderPicMapper resRenderPicMapper;

    @Override
    public Map<Integer, String> idAndPathMap(List<Integer> ids) {
        ids = ids.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<ResRenderPic> resRenderPics = resRenderPicMapper.listByIds(ids);
        return resRenderPics.stream().collect(Collectors.toMap(resPic -> resPic.getId().intValue(), resRender -> {
            if (isEmpty(resRender.getPicPath())) {
                return "";
            } else {
                return resRender.getPicPath();
            }
        }));
    }

    @Override
    public String getPathById(Integer id) {
        return idAndPathMap(Collections.singletonList(id)).get(id);
    }

    @Override
    public void batchAddResRenderPic(List<ResRenderPic> renderPics, Long planId) {
        if (renderPics.size() > 0) {
            resRenderPicMapper.batchAddResRenderPic(renderPics, planId);
        }
    }

    @Override
    public int addResRenderPic(ResRenderPic resRenderPic) {
        resRenderPicMapper.insertSelective(resRenderPic);
        return resRenderPic.getId().intValue();
    }

    @Override
    public int updateResRenderPic(ResRenderPic resRenderPic) {
        return resRenderPicMapper
                .updateByPrimaryKeySelective(resRenderPic);
    }

    @Override
    public List<ResRenderPic> selectResRenderPicByParam(String plandId, String renderingType, String fileKey, String picType) {
        return resRenderPicMapper.selectResRenderPicByParam(plandId, renderingType, fileKey, picType);
    }

}
