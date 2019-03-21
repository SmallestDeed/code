package com.nork.home.controller.web;


import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.home.model.MyHouseVO;
import com.nork.home.model.search.MyHouseSearch;
import com.nork.home.service.DrawBaseHouseService;
import com.nork.home.service.UserHouseRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/{style}/web/home/myhouse")
public class WebMyHouseController
{
    private static Logger logger = Logger.getLogger(WebBaseHouseApplyController.class);

    @Autowired
    private DrawBaseHouseService drawBaseHouseService;
    @Autowired
    private UserHouseRecordService userHouseRecordService;

    /**
     * @Author: zhangchengda
     * @Description: 我的户型--我的绘制列表
     * @Date: 14:48 2018/6/13
     */
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/mydrawhouse", method = RequestMethod.GET)
    public Object myDrawHouse(String pageStr,String limitStr, HttpServletRequest request)
    {
        logger.info("-------------我的户型.我的绘制----------------");
        LoginUser user = SystemCommonUtil.getCurrentLoginUserInfo(request);
        if (user == null)
        {
            return new ResponseEnvelope<>(false,"请先登录...");
        }

        Integer page = isNumStr(pageStr)?Integer.parseInt(pageStr):null;
        Integer limit = isNumStr(limitStr)?Integer.parseInt(limitStr):null;

        MyHouseSearch myHouseSearch = new MyHouseSearch();
        myHouseSearch.setUserId(user.getId());
        if (page != null) myHouseSearch.setStart((page - 1) * (limit == null?myHouseSearch.getLimit():limit));
        if (limit != null) myHouseSearch.setLimit(limit);

        Integer total = drawBaseHouseService.getMyDrawHouseCount(myHouseSearch);
        List<MyHouseVO> myHouseVOS = drawBaseHouseService.getMyDrawHouseList(myHouseSearch);

        for (MyHouseVO myhouse : myHouseVOS)
        {
            myhouse.setHouseSpe(this.getHouseSpe(myhouse.getHouseSpe()));
        }
        return new ResponseEnvelope<>(total,myHouseVOS);
    }

    /**
     * @Author: zhangchengda
     * @Description: 我的户型--使用记录列表
     * @Date: 16:18 2018/6/13
     */
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/record", method = RequestMethod.GET)
    public Object usedRecord(String pageStr, String limitStr, HttpServletRequest request)
    {
        logger.info("-------------我的户型.使用记录----------------");
        LoginUser user = SystemCommonUtil.getCurrentLoginUserInfo(request);
        if (user == null)
        {
            return new ResponseEnvelope<>(false,"请先登录...");
        }

        Integer page = isNumStr(pageStr)?Integer.parseInt(pageStr):null;
        Integer limit = isNumStr(limitStr)?Integer.parseInt(limitStr):null;

        MyHouseSearch myHouseSearch = new MyHouseSearch();
        myHouseSearch.setUserId(user.getId());
        if (page != null) myHouseSearch.setStart((page - 1) * (limit == null?myHouseSearch.getLimit():limit));
        if (limit != null) myHouseSearch.setLimit(limit);

        Integer total = userHouseRecordService.getRecordCount(myHouseSearch);
        List<MyHouseVO> myHouseVOS = userHouseRecordService.getRecordList(myHouseSearch);

        for (MyHouseVO myhouse : myHouseVOS)
        {
            myhouse.setHouseSpe(this.getHouseSpe(myhouse.getHouseSpe()));
        }
        return new ResponseEnvelope<>(total,myHouseVOS);
    }

    private boolean isNumStr(String str)
    {
        if (str == null || str.equals(""))
        {
            return false;
        }
        char[] chars = str.toCharArray();
        for (char c : chars)
        {
            if(c < 48)
            {
                return false;
            }
            if (c > 57)
            {
                return false;
            }
        }
        return true;
    }

    private String getHouseSpe(String str)
    {
        if (str != null && !str.equals(""))
        {
            if (str.contains(","))
            {
                List<String> typeList = Arrays.asList(str.split(","));
                Map<String, Integer> elementsCount = new HashMap<String, Integer>();
                for (String s : typeList) {
                    Integer i = elementsCount.get(s);
                    if (i == null) {
                        elementsCount.put(s, 1);
                    } else {
                        elementsCount.put(s, i + 1);
                    }
                }
                return ((elementsCount.containsKey("3") ? elementsCount.get("3") : 0) + "厅"
                        + (elementsCount.containsKey("4") ? elementsCount.get("4") : 0)) + "室";
            }else
            {
               return (str.equals("3")?"1":"0") + "厅"
                        + (str.equals("4")?"1":"0") + "室";
            }
        }else
        {
            return "0室0厅";
        }
    }
}
