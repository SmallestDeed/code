package com.sandu.service.restexture.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.restexture.input.ResTextureAdd;
import com.sandu.api.restexture.input.ResTextureQuery;
import com.sandu.api.restexture.input.ResTextureUpdate;
import com.sandu.api.restexture.model.ResTexture;
import com.sandu.api.restexture.model.bo.TreeBO;
import com.sandu.api.restexture.output.Tree;
import com.sandu.api.restexture.service.ResTextureService;
import com.sandu.api.storage.model.ResPic;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.constant.Punctuation;
import com.sandu.service.restexture.dao.ResTextureDao;
import com.sandu.util.CodeUtil;
import com.sandu.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sandu.constant.Punctuation.DOT;
import static com.sandu.util.CodeUtil.formatCode;

/**
 * @author Sandu
 */
@Service("resTextureService")
public class ResTextureServiceImpl implements ResTextureService {
    @Autowired
    private ResTextureDao resTextureDao;

    @Autowired
    private ResPicService resPicService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(30);

    @Override
    public Long addResTexture(ResTextureAdd resTextureAdd) {
        ResTexture resTexture = ResTexture
                .builder()
                .brandId(resTextureAdd.getBrandId())
//                .fileCode(formatCode("C", resTextureDao.getMaxId()))
                .fileCode("C_")
                .name(resTextureAdd.getTextureName())
                .normalParam(resTextureAdd.getNormalParam())
                .filePath(resTextureAdd.getThumbnailPath())
                .picId(resTextureAdd.getThumbnailId())
                .fileDesc(Optional.ofNullable(resTextureAdd.getFileDesc()).orElse(""))
                .fileHeight(Optional.ofNullable(resTextureAdd.getFileWidth()).orElse(""))
                .fileWidth(Optional.ofNullable(resTextureAdd.getFileLength()).orElse(""))
                .normalPicId(resTextureAdd.getNormalPicId())
                .texture(resTextureAdd.getTexture())
                .remark(Optional.ofNullable(resTextureAdd.getRemark()).orElse(""))
                .textureAttrValue(resTextureAdd.getTextureAttrValue())
                .modelNumber(resTextureAdd.getModelNumber())
                .textureCode(resTextureAdd.getModelNumber())
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .isDeleted(0)
                .companyId(resTextureAdd.getCompanyId())
                .textureballFileId(resTextureAdd.getTextureballFileId())
                .initWithModelId(resTextureAdd.getInitWithModelId())
                .build();

        resTextureDao.addResTexture(resTexture);
        resTexture.setFileCode(formatCode("C", resTexture.getId() - 1 + ""));
        resTextureDao.saveResTexture(resTexture);

        return resTexture.getId();
    }

    @Override
    public Long saveResTexture(ResTextureUpdate resTextureUpdate) {
        ResTexture resTexture = ResTexture
                .builder()
                .brandId(resTextureUpdate.getBrandId())
                .id(Long.valueOf(resTextureUpdate.getTextureId()))
                .name(resTextureUpdate.getTextureName())
                .normalParam(resTextureUpdate.getNormalParam())
                .filePath(resTextureUpdate.getThumbnailPath())
                .fileDesc(resTextureUpdate.getFileDesc())
                .picId(resTextureUpdate.getThumbnailId())
                .remark(Optional.ofNullable(resTextureUpdate.getRemark()).orElse(""))
                .fileHeight(Optional.ofNullable(resTextureUpdate.getFileWidth()).orElse(""))
                .fileWidth(Optional.ofNullable(resTextureUpdate.getFileLength()).orElse(""))
                //.fileHeight(resTextureUpdate.getFileWidth().toString())
                //.fileWidth(resTextureUpdate.getFileLength())
                .normalPicId(resTextureUpdate.getNormalPicId())
                .texture(resTextureUpdate.getTexture())
                .textureAttrValue(resTextureUpdate.getTextureAttrValue())
                .modelNumber(resTextureUpdate.getModelNumber())
                .textureCode(resTextureUpdate.getModelNumber())
                .gmtModified(new Date())
                .textureballFileId(resTextureUpdate.getTextureballFileId())
                .build();
        resTextureDao.saveResTexture(resTexture);
        return resTexture.getId();
    }

    @Override
    public List<TreeBO> listTextures() {
        return resTextureDao.listTextures();
    }

    @Override
    public int deleteResTexture(Long id) {
        return resTextureDao.deleteResTexture(id);
    }

    @Override
    public ResTexture getResTextureDetail(Long id) {
        return resTextureDao.getResTextureDetail(id);
    }


    @Override
    public PageInfo<ResTexture> listResTexture(ResTextureQuery resTextureQuery) {
        PageHelper.startPage(resTextureQuery.getPage(), resTextureQuery.getLimit());
        return new PageInfo<>(resTextureDao.listResTexture(resTextureQuery));
    }


    @Override
    public List<Tree> textureAttrTree() {
        return TreeUtil.genTree(resTextureDao.textureAttrTree());
    }


    @Override
    public Map<Integer, ResTexture> getIdAndFilePathByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }

        List<ResTexture> textures = resTextureDao.getIdAndFilePathByIds(ids);
        Map<Integer, ResTexture> map = new HashMap<>(textures.size());
        textures.forEach(tmp -> map.put(tmp.getId().intValue(), tmp));
        return map;
    }

    @Override
    public PageInfo<ResTexture> queryResTexture(ResTextureQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<ResTexture> resTextures = resTextureDao.queryResTexture(query);
        return new PageInfo<>(resTextures);
    }

    @Override
    public Map<Integer, String> getTextureCodeByIds(List<Integer> textureIds) {
        if (textureIds.isEmpty()) {
            return Collections.emptyMap();
        }
        textureIds = textureIds.stream().distinct().collect(Collectors.toList());
        List<ResTexture> list = resTextureDao.getTextureCodeByIds(textureIds);
        Map<Integer, String> map = new HashMap<>(list.size());
        list.forEach(item -> map.put(item.getId().intValue(), item.getFileCode()));
        return map;
    }

    @Override
    public boolean importTexturesFromExcel(List<ResTextureAdd> beans) {
        CodeUtil.fixPartWithList(beans, 10, partList -> {
            this.executorService.execute(() -> {
                LocalTime begin = LocalTime.now();
                System.out.println(Thread.currentThread().getName() + "==>save start------->" + begin);
                partList.forEach(it -> {
                    if (StringUtils.isNoneBlank(it.getThumbnailPath())) {
                        ResPic resPic = this.initPics(it.getThumbnailPath(), it.getUserId(), true);
                        it.setThumbnailId(resPicService.addResPic(resPic).intValue());
                    }
                    if (StringUtils.isNoneBlank(it.getNormalPicPath())) {
                        ResPic resPic = this.initPics(it.getNormalPicPath(), it.getUserId(), false);
                        it.setNormalPicId(resPicService.addResPic(resPic).intValue());
                    }
                    this.addResTexture(it);
                });
                LocalTime end = LocalTime.now();
                System.out.println(Thread.currentThread().getName() + "==>save end------->" + end);
                System.out.println(Thread.currentThread().getName() + "==>user time------->" + Duration.between(begin, end));
            });
        });

        System.out.println("local end");
        return true;
    }

    private ResPic initPics(String texturePicPath, Integer userId, boolean initSmallPicFlag) {
        Date date = new Date();
        String suffix = texturePicPath.substring(texturePicPath.lastIndexOf(Punctuation.DOT) + 1);
        String picName = texturePicPath.substring(texturePicPath.lastIndexOf("/") + 1).replace(DOT + suffix, "");
        return ResPic
                .builder()
//                .picSize()
                .picPath(texturePicPath)
//                .picWeight(String.valueOf(image.getWidth()))
//                .picHigh(String.valueOf(image.getHeight()))
                .fileKey(initSmallPicFlag ? "system.resTexture.pic" : "system.resTexture.pic.texture")
                .gmtCreate(date)
                .gmtModified(date)
                .creator(userId.toString())
                .modifier(userId.toString())
                .picName(picName)
                .picFileName(picName)
                .picCode(picName)
                .picSuffix(DOT + suffix)
                .picFormat(suffix)
                .remark("from-merchant-manage-excel-import")
                .isDeleted(0)
                .build();

    }

	@Override
	public List<Integer> getDefaultTextureIdsById(List<Integer> idList, Integer modelId) {
		return resTextureDao.getDefaultTextureIdsById(idList,modelId);
	}


}

