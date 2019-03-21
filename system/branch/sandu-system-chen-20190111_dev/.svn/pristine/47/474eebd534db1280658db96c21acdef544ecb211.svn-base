package com.sandu.web.banner;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.banner.input.BaseBannerAdAdd;
import com.sandu.api.banner.input.BaseBannerAdUpdate;
import com.sandu.api.banner.input.BaseBannerQueryList;
import com.sandu.api.banner.model.BaseBannerAd;
import com.sandu.api.banner.model.BaseBannerPosition;
import com.sandu.api.banner.model.ResBannerPic;
import com.sandu.api.banner.model.po.BannerPO;
import com.sandu.api.banner.output.BaseBannerAdVO;
import com.sandu.api.banner.output.BaseBannerVO;
import com.sandu.api.banner.service.BaseBannerAdService;
import com.sandu.api.banner.service.BaseBannerPositionService;
import com.sandu.api.banner.service.ResBannerPicService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.PageHelper;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 基础-广告 后台管理 控制层
 * @author WangHaiLin
 * @date 2018/5/17  10:14
 */
@Api(tags = "baseBanner", description = "基础广告")
@RestController
@RequestMapping("/v1/base/banner/admin")
public class BaseBannerAdAdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(BaseBannerAdAdminController.class);

    @Autowired
    private BaseBannerAdService baseBannerAdService;

    @Autowired
    private BaseBannerPositionService baseBannerPositionService;

    @Autowired
    private ResBannerPicService resBannerPicService;


    @ApiOperation(value = "新增广告", response = ResponseEnvelope.class)
    @PostMapping
    public ResponseEnvelope addBaseBanner(@Valid @RequestBody BaseBannerAdAdd bannerAdd,BindingResult validResult){
        try {
            //1.校验参数
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }
            //2.判断位置Id和Pic资源Id是否存在
            BaseBannerPosition position = baseBannerPositionService.getPositionById(bannerAdd.getPositionId());
            if(null==position){
                return new ResponseEnvelope<>(false, "广告位置不存在!");
            }
            ResBannerPic pic = resBannerPicService.getPicById(bannerAdd.getResBannerPicId());
            if(null==pic){
                return new ResponseEnvelope<>(false, "广告图片资源不存在!");
            }
            //3.获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            //4.调取服务，进行数据新增
            int bannerId = baseBannerAdService.addBaseBanner(bannerAdd,loginUser);
            if (bannerId>0){
                return new ResponseEnvelope<>(true, "创建成功!");
            }
            return new ResponseEnvelope<>(false, "创建失败!");
        }catch (Exception ex){
            logger.error("系统异常:",ex);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }


    @ApiOperation(value = "删除广告信息", response = ResponseEnvelope.class)
    @DeleteMapping
    public ResponseEnvelope deleteBaseBanner(@RequestParam("bannerId")Integer bannerId){

        try{
            //1.校验参数
            if (bannerId==null){
                return new ResponseEnvelope<>(false, "bannerId不能为空!");
            }
            //2.查询要删除数据是否存在
            BaseBannerAd banner = baseBannerAdService.getBannerById(bannerId);
            if (banner==null){
                return new ResponseEnvelope<>(false, "无此Banner信息!");
            }
            //3.获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            //4.位置信息存在，进行删除
            int result = baseBannerAdService.deleteBanner(bannerId,loginUser);
            if(result>0){
                return new ResponseEnvelope<>(true, "删除成功!");
            }
            return new ResponseEnvelope<>(false, "删除失败!");
        }catch (Exception ex){
            logger.error("系统异常:",ex);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }


    @ApiOperation(value = "修改广告信息", response = ResponseEnvelope.class)
    @PutMapping
    public ResponseEnvelope updateBaseBanner(@Valid @RequestBody BaseBannerAdUpdate update, BindingResult validResult){

        try{
            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }
            //2.判断修改时 位置Id和Pic资源Id是否存在
            BaseBannerPosition position = baseBannerPositionService.getPositionById(update.getPositionId());
            if(null==position){
                return new ResponseEnvelope<>(false, "要修改的广告位置不存在!");
            }
            ResBannerPic pic = resBannerPicService.getPicById(update.getResBannerPicId());
            if(null==pic){
                return new ResponseEnvelope<>(false, "要修改的广告图片资源不存在!");
            }
            //3.获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

            //4.查询要修改数据是否存在
            BaseBannerAd banner = baseBannerAdService.getBannerById(update.getBannerId());
            if (banner==null){
                return new ResponseEnvelope<>(false, "无此Banner信息!");
            }
            //5.信息存在，调取服务，进行修改
            int result = baseBannerAdService.updateBanner(update,loginUser);
            if(result>0){
                return new ResponseEnvelope<>(true, "修改成功!");
            }
            return new ResponseEnvelope<>(false, "修改操作失败!");
        }catch (Exception ex){
            logger.error("系统异常:",ex);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }


    @ApiOperation(value = "通过Id查询广告", response = BaseBannerAdVO.class)
    @GetMapping
    public ResponseEnvelope getBaseBannerById(@RequestParam("bannerId")Integer bannerId){
        try{
            //校验参数
            if (bannerId==null){
                return new ResponseEnvelope<>(false, "bannerId不能为空!");
            }
            //进行查询
            BaseBannerAd banner = baseBannerAdService.getBannerById(bannerId);
            if (null!=banner){
                //输出转换
                BaseBannerAdVO bannerVO = banner.getBannerVoFromBanner(banner);
                return new ResponseEnvelope<>(true, "查询成功!",bannerVO);
            }
            return new ResponseEnvelope<>(true, "无此对应广告信息!",null);
        }catch (Exception ex){
            logger.error("系统异常:",ex);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }


    @ApiOperation(value = "条件查询广告列表", response = BaseBannerVO.class)
    @GetMapping("/list")
    public ResponseEnvelope getBaseList(@Valid BaseBannerQueryList queryList,BindingResult validResult) {
        try {
            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }
            //查询总数量
            BannerPO bannerPO = queryList.getBannerPOFromQueryList(queryList);
            Integer totalCount = baseBannerAdService.getCount(bannerPO);
            //处理分页
            PageHelper page = PageHelper.getPage(totalCount, queryList.getLimit(), queryList.getPage());
            bannerPO.setStart(page.getStart());
            bannerPO.setPageSize(page.getPageSize());
            //获取当前页数据
            List<BaseBannerVO> list = baseBannerAdService.getBannerList(bannerPO);
            if (list != null && totalCount != null) {
                return new ResponseEnvelope<>(true, totalCount, list);
            }
            return new ResponseEnvelope<>(true, 0, null);
        } catch (Exception ex) {
            logger.error("系统异常:", ex);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }

}
