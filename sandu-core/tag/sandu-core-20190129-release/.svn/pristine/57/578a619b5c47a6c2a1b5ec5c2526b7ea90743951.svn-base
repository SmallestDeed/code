package com.sandu.web.product.controller;

import com.google.gson.Gson;
import com.sandu.api.base.common.ResponseEnvelope;
import com.sandu.api.base.common.util.GsonUtil;
import com.sandu.api.base.common.util.SpringContextHolder;
import com.sandu.api.base.model.BaseProductCategory;
import com.sandu.api.base.model.BaseProductCategorySearch;
import com.sandu.api.base.service.CompressImageService;
import com.sandu.interceptor.NeedNotLogin;
import com.sandu.job.DesignPlanJob;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/11/23
 * @since : sandu_yun_1.0
 */
@Log4j2(topic = "[ImageRefreshActionController]")
@RestController
@RequestMapping("/v1/core/groupProduct")
@Api(tags = "ProductCategoryController")
public class ImageRefreshActionController {

    private static String keys = "3d990d2276917dfac04467df11fff26d";

    @Autowired
    private CompressImageService compressImageService;

    @RequestMapping(value="/compressGroupProductImage",method = RequestMethod.GET)
    @NeedNotLogin
    public ResponseEnvelope compressGroupProductImage(@RequestParam("keys") String keys) {
        if(keys.equals(keys)){
            compressImageService.compressImage();
        }
        return new ResponseEnvelope(true, "OK");
    }

    @RequestMapping(value="/startDesignPlanJob", method = RequestMethod.GET)
    @NeedNotLogin
    public ResponseEnvelope startDesignPlanJob(@RequestParam Map<String, String> map) {
        DesignPlanJob.map = map;
        return new ResponseEnvelope(true, "ok", this.getResultMap(map));
    }

    @RequestMapping(value = "/setShopTimer", method = RequestMethod.GET)
    @NeedNotLogin
    public ResponseEnvelope setShopTimer(@RequestParam Map<String, String> map) {
        if (map.containsKey("a")) {
            String a = map.get("a");
            List<String> aList = Arrays.asList(a.split(","));
            DesignPlanJob.aShopList = this.decodeList(aList);
            map.remove("a");
        }
        if (map.containsKey("b")) {
            String b = map.get("b");
            List<String> bList = Arrays.asList(b.split(","));
            DesignPlanJob.bShopList = this.decodeList(bList);
            map.remove("b");
        }
        DesignPlanJob.shopMap = map;
        Map<String, String> re = this.getResultMap(map);
        re.put("a", GsonUtil.bean2Json(DesignPlanJob.aShopList));
        re.put("b", GsonUtil.bean2Json(DesignPlanJob.bShopList));
        return new ResponseEnvelope(true, "ok", re);
    }

    private void sum(int[] t, int[] s, int tStart, int sStart){
        for (int i = 0; i < t.length; i++) {
            t[tStart + i] += s[sStart + i];
        }
    }

    private List<String> decodeList(List<String> list){
        List<String> decodeList = new ArrayList<>(list.size());
        for (String s : list) {
            try {
                decodeList.add(URLDecoder.decode(s,"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("URI解码错误!!!");
            }
        }
        return decodeList;
    }

    private Map<String, String> getResultMap(Map<String, String> map){
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        int[] a = new int[]{0,0,0,0,0,0};
        int[] b = new int[]{0,0,0,0,0,0};
        int[] c = new int[]{0,0,0,0,0,0};
        for (Map.Entry<String, String> entry : entrySet) {
            int num = entry.getKey().trim().split(",").length;
            String value = entry.getValue();
            String[] values = value.trim().split(",");
            int[] nums = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                nums[i] = Integer.parseInt(values[i]) * num;
            }
            this.sum(a, nums, 0, 0);
            this.sum(b, nums, 0, 6);
            this.sum(c, nums, 0, 12);
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("等级A每天增加数量","收藏" + a[0] + "-" + a[1] + ",点赞" + a[2] + "-" + a[3] + ",浏览" + a[4] +"-" + a[5]);
        resultMap.put("等级B每天增加数量","收藏" + b[0] + "-" + b[1] + ",点赞" + b[2] + "-" + b[3] + ",浏览" + b[4] +"-" + b[5]);
        resultMap.put("等级C每天增加数量","收藏" + c[0] + "-" + c[1] + ",点赞" + c[2] + "-" + c[3] + ",浏览" + c[4] +"-" + c[5]);
        return resultMap;
    }
}
