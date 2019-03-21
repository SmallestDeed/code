package com.sandu.service.imallSlide.impl.biz;

import com.sandu.api.imallSlide.model.ImallSlide;
import com.sandu.api.imallSlide.service.ImallSlideBizService;
import com.sandu.api.imallSlide.service.ImallSlideService;
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
 * @datetime 2018/4/28 18:42
 */
@Slf4j
@Service("imallSlideBizService")
public class ImallSlideBizServiceImpl implements ImallSlideBizService {

    @Autowired
    private ImallSlideService imallSlideService;

    @Override
    public int insert(ImallSlide input) {
        return imallSlideService.insert(input);
    }

    @Override
    public int delete(int id) {
        return imallSlideService.deleteImallSlide(id);
    }

    @Override
    public List<ImallSlide> getList(int type){
        return imallSlideService.getList(type);
    }
}
