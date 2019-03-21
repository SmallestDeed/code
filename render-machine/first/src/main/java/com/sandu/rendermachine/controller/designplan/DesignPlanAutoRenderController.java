package com.sandu.rendermachine.controller.designplan;

import com.sandu.rendermachine.model.render.AutoRenderTask;
import com.sandu.rendermachine.model.response.ResponseEnvelope;
import com.sandu.rendermachine.model.vo.RenderTaskVO;
import com.sandu.rendermachine.service.designplan.DesignPlanAutoRenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 4:29 2018/4/17 0017
 * @Modified By:
 */
@Controller
@RequestMapping("/{style}/web/design/autoRender")
public class DesignPlanAutoRenderController {

    @Autowired
    private DesignPlanAutoRenderService designPlanAutoRenderService;

    /**
     * 渲染机获取任务
     * @param request
     * @param response
     * @param renderTaskVO
     * @return
     * @throws UnknownHostException
     */
    @ResponseBody
    @RequestMapping("/getRenderTaskList")
    public ResponseEnvelope<AutoRenderTask> getRenderTaskList(HttpServletRequest request, HttpServletResponse response,
                                                              RenderTaskVO renderTaskVO) throws UnknownHostException{
        String token = request.getHeader("Authorization");
        List<AutoRenderTask> taskList = designPlanAutoRenderService.getRenderTaskList(renderTaskVO,token);

        return new ResponseEnvelope<>(true,renderTaskVO.getMsgId(),renderTaskVO.getMaxSize(),taskList);
    }


}
