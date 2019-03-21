package com.sandu.web.user.controller;

import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.output.SysDictionaryVO;
import com.sandu.api.system.service.SysDictionaryService;
import com.sandu.common.ResponseEnvelope;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/9/13  11:42
 */
@Api(tags = "sysDictionary", description = "数据字典")
@RestController
@RequestMapping(value = "/v1/sys/dictionary")
public class SysDictionaryController {

    private Logger logger = LoggerFactory.getLogger(SysDictionaryController.class);

    @Autowired
    private SysDictionaryService dictionaryService;

    @GetMapping("/list")
    @ApiOperation(value = "根据类型获取数据字典列表")
    public ResponseEnvelope getDictionaryInfo(String type) {
        logger.info("获取数据字典信息");
        if (null == type) {
            return new ResponseEnvelope(false, "类型不能为空!");
        }
        logger.info("获取数据字典信息---参数type:{}",type);
        List<SysDictionary> list = dictionaryService.getListByType(type);
        logger.info("获取数据字典信息---查询结果list:{}",list);
        if(null != list&&list.size()>0) {
            return new ResponseEnvelope(true,list, "根据类型获取数据字典列表成功!");
        }else {
            return new ResponseEnvelope(false, "数据字典中无该类型信息!");
        }
    }

    @GetMapping("/getList")
    public Object getDictionaryList(String type) {
    	List<SysDictionaryVO> returnList = dictionaryService.getSysDictionaryVOList(type);
    	return new ResponseEnvelope<>(returnList);
    }
    
}
