package com.sandu.service.restexture.impl;

import com.github.pagehelper.PageInfo;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.service.BrandService;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.resmodel.model.ResModel;
import com.sandu.api.resmodel.service.ResModelService;
import com.sandu.api.restexture.input.ResTextureQuery;
import com.sandu.api.restexture.model.ResTexture;
import com.sandu.api.restexture.model.bo.TreeBO;
import com.sandu.api.restexture.output.ResTextureVO;
import com.sandu.api.restexture.service.ResTextureService;
import com.sandu.api.restexture.service.biz.ResTextureBizService;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.constant.Punctuation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.util.Commoner.isNotEmpty;

/**
 * @author Sandu
 */
@Slf4j
@Service("resTextureBizService")
public class ResTextureBizServiceImpl implements ResTextureBizService {
    private final ResTextureService resTextureService;
    private final DictionaryService dictionaryService;

    private final BrandService brandService;
    @Resource
    private ResPicService resPicService;

    private final com.sandu.api.resmodel.service.ResModelService resModelService;

    @Autowired
    public ResTextureBizServiceImpl(ResTextureService resTextureService, DictionaryService dictionaryService, BrandService brandService, ResModelService resModelService) {
        this.resTextureService = resTextureService;
        this.dictionaryService = dictionaryService;
        this.brandService = brandService;
        this.resModelService = resModelService;
    }

    @Override
    public PageInfo<ResTextureVO> queryResTextureList(ResTextureQuery resTextureQuery) {
        PageInfo<ResTexture> res = resTextureService.queryResTexture(resTextureQuery);
        List<ResTextureVO> vos = new ArrayList<>();
        List<Dictionary> dictionaries = dictionaryService.listByType("textureAttr");
        Map<Integer, String> idPathMap = resPicService.idAndPathMap(res.getList().stream().map(resTexture -> resTexture.getId().intValue()).collect(Collectors.toList()));
        Map<Integer, ResModel> models = resModelService.getModelByIds(res.getList().stream().map(ResTexture::getTextureballFileId).collect(Collectors.toList()));
        res.getList().forEach(item -> {
            ResTextureVO tmp = ResTextureVO.builder().build();
            BeanUtils.copyProperties(item, tmp);
            tmp.setTextureName(item.getName());
            tmp.setTextureCode(item.getFileCode());
            tmp.setThumbnailPath(item.getFilePath());
            tmp.setTextureAttrValue(item.getTextureAttrValue());
            tmp.setGmtModified(item.getGmtModified());
            tmp.setRemark(item.getRemark());
            tmp.setFileDesc(item.getFileDesc());
            tmp.setTextureId(item.getId());
            tmp.setFileLength(item.getFileWidth());
            tmp.setFileWidth(item.getFileHeight());
            tmp.setNormalPicPath(idPathMap.get(item.getNormalPicId()));
            tmp.setTextureballFileId(item.getTextureballFileId());
            if (models.get(item.getTextureballFileId()) != null) {
                tmp.setTransStatus(models.get(item.getTextureballFileId()).getTransStatus());
            }
            dictionaries.forEach(dic -> {
                if (dic.getValue().equals(tmp.getTextureAttrValue())) {
                    tmp.setTextureAttrValueName(dic.getName());
                }
            });
            vos.add(tmp);
        });
        PageInfo<ResTextureVO> ret = new PageInfo<>(vos);
        ret.setTotal(res.getTotal());
        return ret;
    }

    @Override
    public ResTextureVO getResTextureDetail(long id) {
        ResTexture res = resTextureService.getResTextureDetail(id);
        ResTextureVO vo = ResTextureVO.builder().build();
        BeanUtils.copyProperties(res, vo);
        vo.setFileLength(res.getFileWidth());
        vo.setFileWidth(res.getFileHeight());
        vo.setTextureName(res.getName());
        vo.setThumbnailPath(res.getFilePath());
        //型号
        vo.setModelNumber(res.getTextureCode());
        //编码
        vo.setTextureCode(res.getFileCode());
        if (res.getTextureballFileId() != null) {
            Map<Integer, ResModel> modelByIds = resModelService.getModelByIds(Collections.singletonList(res.getTextureballFileId()));
            vo.setTextureballFileId(res.getTextureballFileId());
            if (modelByIds.get(res.getTextureballFileId()) != null) {
                String path = modelByIds.get(res.getTextureballFileId()).getFile3duPath();
                vo.setTextureBallPath(path);
                vo.setTransStatus(modelByIds.get(res.getTextureballFileId()).getTransStatus());
                vo.setBallName(path.substring(path.lastIndexOf(Punctuation.SLASH) + 1));
            }
        }
        if (isNotEmpty(res.getNormalPicId())) {
            vo.setNormalPicPath(resPicService.getPathById(res.getNormalPicId()));
        }
        if (res.getTextureAttrValue() == 0) {
            res.setTextureAttrValue(null);
            vo.setTextureAttrValue(null);
        }
        if (res.getTextureAttrValue() != null) {
            Dictionary dic = dictionaryService.getByTypeAndValue("textureAttr", res.getTextureAttrValue());
            if (dic != null) {
                vo.setTextureAttrValueName(dic.getName());
            }
        }
        Optional.ofNullable(vo.getBrandId()).ifPresent(brandId -> {
                    Brand brand = brandService.getBrandById(Long.valueOf(brandId));
                    Optional.ofNullable(brand).ifPresent(item -> vo.setBrandName(item.getBrandName()));
                }
        );

        List<TreeBO> trees = resTextureService.listTextures();
        trees.stream().filter(node -> String.valueOf(node.getValue()).equals(vo.getTexture())).findFirst().ifPresent(node -> vo.setTextureLabel(node.getLabel()));
        return vo;
    }
}
