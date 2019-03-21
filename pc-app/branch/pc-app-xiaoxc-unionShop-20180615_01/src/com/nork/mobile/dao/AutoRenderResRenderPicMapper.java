package com.nork.mobile.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.DesignRenderRoam;
import com.nork.mobile.model.search.AutoRenderResRenderPic;

@Repository
@Transactional
public interface AutoRenderResRenderPicMapper {
	/**
	 * 根据id查询自动渲染的图
	 * @param record
	 * @return
	 */
	AutoRenderResRenderPic selectByPrimaryKey(Integer record);
	
	/**
     * 单点二维码图片。
     * get720SingleCode 得到720单点分享需要的code        
     */
    public AutoRenderResRenderPic get720SingleCode(AutoRenderResRenderPic resRenderPic);
    /**
     * 多点二维码图片
     * get720RomanCode.得到720多点分享的code    
     */
    public DesignRenderRoam get720RomanCode(AutoRenderResRenderPic resRenderPic);
}
