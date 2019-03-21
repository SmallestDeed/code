package com.sandu.pano.model.scene;

import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */
public class GroupSceneVo extends SceneVo {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 场景列表
     **/
    public String Scenes;
    /**
     * 缩略图列表
     **/
    private List<Thumb> thumbList;

    @Override
    public String getScenes() {
        return Scenes;
    }

    @Override
    public void setScenes(String scenes) {
        Scenes = scenes;
    }

    public List<Thumb> getThumbList() {
        return thumbList;
    }

    public void setThumbList(List<Thumb> thumbList) {
        this.thumbList = thumbList;
    }

	@Override
	public String toString() {
		return "GroupSceneVo [Scenes=" + Scenes + ", thumbList=" + thumbList + "]";
	}
    
}
