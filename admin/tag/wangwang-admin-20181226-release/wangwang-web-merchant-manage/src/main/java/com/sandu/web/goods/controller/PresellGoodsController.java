package com.sandu.web.goods.controller;

import com.google.gson.Gson;
import com.sandu.api.goods.common.constant.GoodsTypeEnum;
import com.sandu.api.goods.service.biz.BaseGoodsBizService;
import com.sandu.api.queue.SyncMessage;
import com.sandu.api.queue.service.QueueService;
import com.sandu.common.ReturnData;
import com.sandu.constant.Constants;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sandu.constant.ResponseEnum.*;

@Api(tags = "PresellGoodsController", description = "预售新品管理")
@RestController
@Slf4j
@RequestMapping("/v1/presell/goods")
public class PresellGoodsController {
    @Autowired
    private BaseGoodsBizService baseGoodsBizService;

    private static Gson gson = new Gson();

    @Autowired
    private QueueService queueService;

    @GetMapping("/getMainPageGoodsList")
    public Object getMainPageGoodsList(Integer companyId){
        if (companyId == null){
            return ReturnData.builder().code(ERROR).message("公司ID为空");
        }
        List<Object> list = baseGoodsBizService.getMainPageGoodsList(GoodsTypeEnum.PRESELL_GOODS, companyId);
        return ReturnData.builder().code(SUCCESS).list(list);
    }

    @GetMapping("/getNewGoodsList")
    public Object getPresellGoodsList(Integer companyId){
        if (companyId == null){
            return ReturnData.builder().code(ERROR).message("公司ID为空");
        }
        List<Object> list = baseGoodsBizService.getBizGoodsList(GoodsTypeEnum.PRESELL_GOODS, companyId);
        return ReturnData.builder().code(SUCCESS).list(list);
    }

    @PutMapping("/updateMainPageState/{spuId}/{state}")
    public Object updateMainPageState(@PathVariable("spuId") Integer spuId, @PathVariable("state") Integer state){
        if (spuId == null){
            return ReturnData.builder().code(ERROR).message("商品ID为空");
        }
        if (state == null){
            return ReturnData.builder().code(ERROR).message("state参数为空");
        }
        int success = baseGoodsBizService.updateMainPageState(GoodsTypeEnum.PRESELL_GOODS, spuId, state);
        if (success > 0){
            ArrayList<Integer> ids = new ArrayList<>();
            ids.add(spuId);
            sycMessageDoSend(SyncMessage.ACTION_UPDATE,ids);
            log.info("更新商品spu预售信息发送消息完成,商品ID列表:"+gson.toJson(ids));
            return ReturnData.builder().code(SUCCESS).message("状态修改成功");
        }else {
            return ReturnData.builder().code(ERROR).message("状态修改失败");
        }
    }




    private void sycMessageDoSend(Integer messageAction, List<Integer> ids) {
        List<Map> content = ids.stream().map(item -> {
            HashMap<String, Integer> tmp = new HashMap<>(1);
            tmp.put("id", item);
            return tmp;
        }).collect(Collectors.toList());
        SyncMessage message = new SyncMessage();
        message.setAction(messageAction);
        message.setMessageId("G-" + System.currentTimeMillis());
        message.setModule(SyncMessage.GOODS_MODULE);
        message.setPlatformType(Constants.PLATFORM_CODE_MINI_APP);
        message.setObject(content);
        queueService.send(message);
    }

}
