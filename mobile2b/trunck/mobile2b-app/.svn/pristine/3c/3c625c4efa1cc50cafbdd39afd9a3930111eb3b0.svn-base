package com.nork.design.service;


import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.RenderPicVO;

public interface SaveRenderPicService {

    /**
     * 保存720渲染图
     *
     * @param renderPic
     * @return
     */
    public boolean saveRenderPicOf720(RenderPicVO renderPic);

    /**
     * 保存照片级渲染图
     *
     * @param renderPic
     * @return
     */
    public boolean saveRenderPicOfPhoto(RenderPicVO renderPic);

    /**
     * 保存720渲染视频 add by yangzhun
     *
     * @param renderPic
     * @return
     */
    public boolean saveRenderPicOfVideo(RenderPicVO renderPic);


    public void saveTaskBussinessId(AutoRenderTaskState taskState);

    public AutoRenderTaskState getStateByTaskId(Integer taskId);

    public DesignPlanRenderScene getDesignPlanRenderScene(Integer businessId);

    /**
     * 处理渲染成功后的逻辑
     *
     * @param taskId
     */
    void handleSuccessfulRender(Integer taskId);
}
