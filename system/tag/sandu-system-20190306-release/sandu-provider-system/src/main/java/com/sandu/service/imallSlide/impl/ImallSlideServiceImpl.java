package com.sandu.service.imallSlide.impl;

import com.sandu.api.imallSlide.model.ImallSlide;
import com.sandu.api.imallSlide.service.ImallSlideService;
import com.sandu.service.imallSlide.dao.ImallSlideDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/28 18:41
 */
@Service("imallSlideService")
@Slf4j
public class ImallSlideServiceImpl implements ImallSlideService {

    @Autowired
    private ImallSlideDao imallSlideDao;

    @Override
    public int insert(ImallSlide model) {
        int result = imallSlideDao.insert(model);
        if (result > 0) {
            ImallSlide imallSlide = imallSlideDao.getByFileName(model.getFileName());
            if (null!=imallSlide){
                return imallSlide.getId();
            }
        }
        return 0;
    }

    @Override
    public int deleteImallSlide(int id) {
        return imallSlideDao.deleteImallSlide(id);
    }

    @Override
    public List<ImallSlide> getList(int type){
        return imallSlideDao.getList(type);
    }
}
