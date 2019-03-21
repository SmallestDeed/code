package com.sandu.common.objectconvert.payOrder;

import com.sandu.common.util.Utils;
import com.sandu.render.model.vo.RenderTypeVO;
import com.sandu.system.model.SysDictionary;

/**
 * Created by Administrator on 2017/10/23 0023.
 */
public class RenderTypeObject {

    /**
     * 将系统字典数据对象转换为渲染类型VO对象
     *
     * @param sysDictionary 系统字典数据
     * @return RenderTypeVO 渲染类型VO
     */
    public static RenderTypeVO parseToRenderTypeVOBySysDictionary(SysDictionary sysDictionary) {

        //创建对象
        RenderTypeVO renderTypeVO = null;

        if (null != sysDictionary) {
            //初始化对象
            renderTypeVO = new RenderTypeVO();
            renderTypeVO.setRenderKey(sysDictionary.getValuekey());
            renderTypeVO.setRenderName(sysDictionary.getName());
            renderTypeVO.setRenderValue(sysDictionary.getValue());
            renderTypeVO.setRenderIntegral(String.valueOf(Utils.getIntValue(sysDictionary.getAtt1())/10));
        }

        return renderTypeVO;
    }
}
