package com.sandu.rendermachine.controller.system;

import com.sandu.rendermachine.common.cache.SysDictionaryCacher;
import com.sandu.rendermachine.common.util.Utils;
import com.sandu.rendermachine.model.response.ResponseEnvelope;
import com.sandu.rendermachine.model.system.SysDicitonaryOptimize;
import com.sandu.rendermachine.model.system.SysDictionary;
import com.sandu.rendermachine.model.system.SysDictionarySearch;
import com.sandu.rendermachine.model.system.SysDictionarySmall;
import com.sandu.rendermachine.service.common.JsonDataService;
import com.sandu.rendermachine.service.common.impl.JsonDataServiceImpl;
import com.sandu.rendermachine.service.system.SysDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 9:51 2018/5/14 0014
 * @Modified By:
 */
@Slf4j
@Controller
@RequestMapping("/{style}/web/system/sysDictionary")
public class SysDictionaryController {

    @Autowired
    private JsonDataService JsonUtil;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 获取所有小类
     * @param style
     * @param sysDictionarySearch
     * @return
     * @throws Exception
     */
    public ResponseEnvelope listAll(@PathVariable String style,
                          @ModelAttribute("sysDictionarySearch") SysDictionarySearch sysDictionarySearch) throws Exception {
        Long startTime = System.currentTimeMillis();

        if (sysDictionarySearch == null) {
            return new ResponseEnvelope<>(false, "传参异常!", "none");
        }

        List<SysDicitonaryOptimize> list;
        int total = 0;
        try {
            if (Utils.enableRedisCache()) {
                list = SysDictionaryCacher.getListOptimize();
            } else {
                sysDictionarySearch.setOrder("ordering");
                sysDictionarySearch.setOrderNum("asc");
                sysDictionarySearch.setType("spaceShape");
                list = sysDictionaryService.getListOptimize(sysDictionarySearch);
            }
            if (list != null && list.size() > 0) {
                // 先获取所有大类
                List<SysDictionary> productTypeList = sysDictionaryService.findAllByType("productType");
                //获取大类下的所有小类
                SysDictionaryCacher.getAllSmallTypeByType(list, productTypeList);

                if ("small".equals(style)) {
                    String sysDictionaryJsonList = JsonUtil.getListToJsonData(list);
                    List<SysDictionarySmall> smallList = JsonUtil.getJsonToBeanList(sysDictionaryJsonList, SysDictionarySmall.class);
                    return new ResponseEnvelope<>(total, smallList, sysDictionarySearch.getMsgId());
                }
            }
        } catch (Exception e) {
            log.error("system-->SysDictionaryController--->listAll--->exception:"+e);
            e.printStackTrace();
            return new ResponseEnvelope<SysDictionary>(false, "数据异常!", sysDictionarySearch.getMsgId());
        }
        Long endTime = System.currentTimeMillis();

        SysDicitonaryOptimize SysDicitonaryOptimize = new SysDicitonaryOptimize();
        SysDicitonaryOptimize.setTimeConsuming("---------方法耗时-------" + (endTime - startTime) + "");
        list.add(SysDicitonaryOptimize);
        return new ResponseEnvelope<>(total, list, sysDictionarySearch.getMsgId());
    }
}
