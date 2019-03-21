package com.sandu.web.panorama.controller;

import com.sandu.cityunion.model.UnionSpecialOffer;
import com.sandu.common.LoginContext;
import com.sandu.common.model.LoginUser;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.panorama.model.input.UnionSpecialOfferSearch;
import com.sandu.panorama.model.output.UnionSpecialOfferVo;
import com.sandu.panorama.service.UnionSpecialOfferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by chenm on 2018/8/2.
 */
@Api(value="优惠活动",tags="Panorama-unionSpecialOffer",description = "720制作-优惠活动")
@RestController
@RequestMapping("/v1/panorama/unionSpecialOffer")
public class UnionSpecialOfferController {

    @Autowired
    private UnionSpecialOfferService unionSpecialOfferService;
    private Logger logger = LoggerFactory.getLogger(UnionSpecialOfferController.class);

    @ApiOperation(value = "优惠活动列表", response = UnionSpecialOfferVo.class)
    @PostMapping("/list")
    public Object list(@RequestBody UnionSpecialOfferSearch unionSpecialOfferSearch){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null){
            return  new ResponseEnvelope<UnionSpecialOffer>(false,"当前登录失效，请重新登录");
        }
        if(unionSpecialOfferSearch == null){
            unionSpecialOfferSearch = new UnionSpecialOfferSearch();
            unionSpecialOfferSearch.setStart(0);
            unionSpecialOfferSearch.setLimit(20);
        }else{
            if(unionSpecialOfferSearch.getLimit() == null || unionSpecialOfferSearch.getStart() == null){
                unionSpecialOfferSearch.setStart(0);
                unionSpecialOfferSearch.setLimit(20);
            }
        }
        unionSpecialOfferSearch.setUserId(loginUser.getId());
        unionSpecialOfferSearch.setIsDisplayed(1);
        int count = unionSpecialOfferService.getCount(unionSpecialOfferSearch);
        List<UnionSpecialOfferVo> list = null;
        if(count > 0){
            list = unionSpecialOfferService.selectUnionSpecialOfferVoListBySearch(unionSpecialOfferSearch);
        }
        return new ResponseEnvelope<UnionSpecialOfferVo>(count,list);
    }

}
