package com.sandu.web.banner;

import com.google.common.base.Strings;
import com.sandu.api.banner.input.BaseBannerAdAdd;
import com.sandu.api.banner.input.BaseBannerAdUpdate;
import com.sandu.api.banner.input.BaseBannerPositionAdd;
import com.sandu.api.banner.input.BaseBannerPositionIsExist;
import com.sandu.api.banner.model.BaseBannerAd;
import com.sandu.api.banner.model.BaseBannerPosition;
import com.sandu.api.banner.model.ResBannerPic;
import com.sandu.api.banner.output.MiniProgramBannerVO;
import com.sandu.api.banner.service.BaseBannerAdService;
import com.sandu.api.banner.service.BaseBannerPositionService;
import com.sandu.api.banner.service.ResBannerPicService;
import com.sandu.api.banner.service.manage.BaseBannerManageService;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * 微官网小程序首页Banner后台管理接口
 * @author WangHaiLin
 * @date 2018/6/23  15:27
 */
@Api(tags = "weiGuanWangBanner", description = "微官网后台Banner管理")
@RestController
@RequestMapping("/v1/banner/manage/weiguanwang")
public class WeiGuanWangBannerController extends BaseController{
    private final static Logger logger = LoggerFactory.getLogger(WeiGuanWangBannerController.class);
    @Autowired
    private ResBannerPicService resBannerPicService;

    @Autowired
    private BaseBannerAdService baseBannerAdService;

    @Autowired
    private BaseBannerPositionService positionService;

    @Autowired
    private BaseBannerManageService baseBannerManageService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Value("${upload.base.path}")
    private String basePath;

    @Value("${upload.relative.path}")
    private String relativePath;

    @ApiOperation(value = "新增广告信息", response = ResponseEnvelope.class)
    @PostMapping("/pic/add")
    public ResponseEnvelope addBannerAd(@RequestPart("file") MultipartFile file,String skipPath){
        try{
            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            if (null!=loginUser.getBusinessAdministrationId()){
                logger.info("新增广告信息：登录用户的BusinessAdministrationId:{}",loginUser.getBusinessAdministrationId());
                BaseCompany baseCompany = baseCompanyService.getCompanyById(loginUser.getBusinessAdministrationId());
                logger.info("新增广告信息：登录用户所属企业:{}",baseCompany);
                if(null!=baseCompany){
                    /*List<BaseBannerAd> bannerList =null;
                    if (null!=baseCompany.getPid()){
                        bannerList = baseBannerAdService.getBaseBannerByRefModelId(baseCompany.getPid());
                    }else{
                        bannerList = baseBannerAdService.getBaseBannerByRefModelId(baseCompany.getId().intValue());
                    }
                    if (bannerList.size()>6){
                        return new ResponseEnvelope(false,"首页广告数量超过上限");
                    }*/
                    ResBannerPic pic = this.addResBannerPic(file);
                    //1. 添加一张照片
                    int result = resBannerPicService.addResBannerPic(pic,loginUser);
                    logger.info("新增广告信息：添加图片返回结果：picId={}",result);
                    if (result>0){
                        pic.setId(result);
                        //2. 判断首页顶部广告位置信息是否存在
                        BaseBannerPositionIsExist isExist=new BaseBannerPositionIsExist();
                        isExist.setCode("company:weiguanwang:home:top");
                        //1:平台--选装网,同城联盟;2:企业--企业小程序;3.店铺
                        isExist.setType(2);
                        logger.info("新增广告信息：判断位置信息是否存在入参:{}",isExist);
                        BaseBannerPosition position = positionService.getPositionIsExist(isExist);
                        logger.info("新增广告信息：判断位置信息是否存在:{}",position);
                        //3.1 首页顶部广告位置不存在则创建首页顶部广告位置信息
                        Integer positionId=0;
                        if (position==null){
                            BaseBannerPositionAdd positionAdd=new BaseBannerPositionAdd();
                            positionAdd.setPositionCode("company:weiguanwang:home:top");
                            positionAdd.setPositionName("微官网首页Banner");
                            //1:平台--选装网,同城联盟;2:企业--企业小程序;3.店铺
                            positionAdd.setPositionType(2);
                            positionId= positionService.addBannerPosition(positionAdd, loginUser);
                            logger.info("新增广告信息：位置信息不否存在时新增位置信息结果{}",position);
                            if (0==positionId){
                                return new ResponseEnvelope(false,"添加position信息失败");
                            }
                        }
                        //3.2 首页顶部广告位置存在则获取首页顶部广告位置信息Id
                        positionId=position.getId();
                        //4. 添加广告信息(base_banner_ad)
                        BaseBannerAdAdd bannerAdAdd=new BaseBannerAdAdd();
                        //用户为经销商时，refModelId为查询到的企业的Pid；不是经销商用户时，refModelId就是登录用户的BusinessAdministrationId
                        if (null!=baseCompany.getPid()){
                            bannerAdAdd.setRefModelId(baseCompany.getPid());
                        }else{
                            bannerAdAdd.setRefModelId(baseCompany.getId().intValue());
                        }
                        logger.info("新增广告信息：RefModelId:{}",bannerAdAdd.getRefModelId());
                        bannerAdAdd.setPositionId(positionId);
                        bannerAdAdd.setResBannerPicId(result);
                        bannerAdAdd.setBannerName("");
                        bannerAdAdd.setStatus(1);
                        bannerAdAdd.setUrl(Strings.nullToEmpty(skipPath));
                        logger.info("新增广告信息：向baseBannerAd添加数据入参{}",bannerAdAdd);
                        int bannerId = baseBannerAdService.addBaseBanner(bannerAdAdd, loginUser);
                        logger.info("新增广告信息：向baseBannerAd添加数据结果{}",position);
                        if (bannerId==0){
                            return new ResponseEnvelope(false,"添加BannerAd失败");
                        }
                        //完成新增将MiniProgramBannerVO返回
                        MiniProgramBannerVO bannerVO=new MiniProgramBannerVO();
                        bannerVO.setImageId(result);
                        bannerVO.setImagePath(pic.getPicPath());
                        bannerVO.setBannerAdId(bannerId);
                        return new ResponseEnvelope(true,"新增成功",bannerVO);
                    }
                    return new ResponseEnvelope(false,"添加BannerPic失败");
                }
                return new ResponseEnvelope(false,"获取企业信息失败");
            }
            return new ResponseEnvelope(false,"从loginUser获取BusinessAdministrationId信息失败");
        } catch (Exception e) {
            logger.error("系统异常",e);
            return new ResponseEnvelope(false,"系统异常");
        }
    }



    @ApiOperation(value = "修改广告图片信息", response = ResponseEnvelope.class)
    @PostMapping("/pic/update")
    public ResponseEnvelope updateBannerPic(@RequestPart("file") MultipartFile file, MiniProgramBannerVO programBannerVO){
        try{
            if (programBannerVO.getImageId()==null||programBannerVO.getBannerAdId()==null){
                return new ResponseEnvelope(false,"参数Id不能为空");
            }
            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            //1.添加一张新图片
            ResBannerPic pic = this.addResBannerPic(file);
            int result = resBannerPicService.addResBannerPic(pic,loginUser);
            logger.info("修改广告图片信息：添加图片返回结果：picId{}",result);
            //2.修改base_banner_ad中的resBannerPicId字段
            if (result>0){
                //删除res_banner_pic表中的老图
                resBannerPicService.deleteBannerPic(programBannerVO.getImageId(),loginUser);
                BaseBannerAdUpdate update=new BaseBannerAdUpdate();
                update.setBannerId(programBannerVO.getBannerAdId());
                update.setResBannerPicId(result);
                update.setSkipPath(Strings.nullToEmpty(programBannerVO.getSkipPath()));
                logger.error("修改广告图片信息：修改baseBannerAd的picId字段入参{}",update);
                int i = baseBannerAdService.updateBanner(update, loginUser);
                logger.info("修改广告图片信息：修改baseBannerAd的picId字段结果{}",i);
                if (i>0){
                    //构造新的输出参数
                    MiniProgramBannerVO newBannerVo=new MiniProgramBannerVO();
                    newBannerVo.setBannerAdId(programBannerVO.getBannerAdId());
                    newBannerVo.setImageId(result);
                    newBannerVo.setImagePath(pic.getPicPath());
                    newBannerVo.setSkipPath(Strings.nullToEmpty(programBannerVO.getSkipPath()));
                    return new ResponseEnvelope(true,"修改成功",newBannerVo);
                }
                return new ResponseEnvelope(false,"修改数据库失败");
            }
            return new ResponseEnvelope(false,"新图添加数据库失败");
        }catch (Exception e){
            logger.error("系统异常",e);
            return new ResponseEnvelope(false,"系统异常");
        }

    }

    @ApiOperation(value = "修改广告跳转路径", response = MiniProgramBannerVO.class)
    @PutMapping("/skip/update")
    public ResponseEnvelope updaterBannerSkipPath(@RequestBody MiniProgramBannerVO bannerVO){
        try{
            if (bannerVO.getBannerAdId()==null||bannerVO.getImageId()==null||bannerVO.getImagePath()==null){
                return new ResponseEnvelope(false,"参数异常");
            }
            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            BaseBannerAdUpdate update=new BaseBannerAdUpdate();
            update.setBannerId(bannerVO.getBannerAdId());
            update.setSkipPath(Strings.nullToEmpty(bannerVO.getSkipPath()));
            logger.error("修改广告跳转路径：修改baseBannerAd的跳转路径入参{}",update);
            int result = baseBannerAdService.updateBanner(update, loginUser);
            logger.error("修改广告跳转路径：修改baseBannerAd的跳转路径结果{}",result);
            if (result>0){
                return new ResponseEnvelope(true,"修改跳转路径成功",bannerVO);
            }
            return new ResponseEnvelope(false,"修改跳转路径失败");
        }catch (Exception e){
            logger.error("系统异常",e);
            return new ResponseEnvelope(false,"系统异常");
        }
    }

    @ApiOperation(value = "查询首页广告列表", response = MiniProgramBannerVO.class)
    @ApiImplicitParam(name = "positionCode",value = "位置编码(类似：company:weiguanwang:home:top)",paramType = "query",dataType = "String",required = true)
    @GetMapping("/get/list")
    public ResponseEnvelope getHomeBannerList(@RequestParam("positionCode") String positionCode){
        try{
            if (positionCode==null){
                return new ResponseEnvelope(false,"必传参数不全");
            }
            //获取登录用户
            LoginUser loginUser=LoginContext.getLoginUser(LoginUser.class);
            //从loginUser获取企业Id，作为列表查询参数refModelId
            //用户为经销商时，refModelId为查询到的企业的Pid；不是经销商用户时，refModelId就是登录用户的BusinessAdministrationId
            Integer refModelId=null;
            if (null!=loginUser&&null!=loginUser.getBusinessAdministrationId()){
                logger.info("查询首页广告列表：登录用户的BusinessAdministrationId:{}",loginUser.getBusinessAdministrationId());
                BaseCompany baseCompany = baseCompanyService.getCompanyById(loginUser.getBusinessAdministrationId());
                if(baseCompany!=null){
                    logger.info("查询首页广告列表：根据登录用户的BusinessAdministrationId获取企业信息：{}",baseCompany);
                    if (null!=baseCompany.getPid()){
                        refModelId=baseCompany.getPid();
                    }else{
                        refModelId=baseCompany.getId().intValue();
                    }
                }else{
                    return new ResponseEnvelope(false,"获取企业信息失败");
                }
            }else{
                return new ResponseEnvelope(false,"从loginUser获取BusinessAdministrationId信息失败");
            }
            BaseBannerPositionIsExist isExist=new BaseBannerPositionIsExist();
            isExist.setCode(positionCode);
            //1:平台--选装网,同城联盟;2:企业--企业小程序;3.店铺
            isExist.setType(2);
            BaseBannerPosition position = positionService.getPositionIsExist(isExist);
            logger.info("查询首页广告列表：判断广告位置信息是否存在结果：{}",position);
            if (position!=null){
                List<MiniProgramBannerVO> bannerList = baseBannerManageService.getBannerList(refModelId, position.getId());
                logger.info("查询首页广告列表：查询结果：{}",bannerList);
                if (bannerList!=null&&bannerList.size()>0){
                    return new ResponseEnvelope(true,"列表查询成功",bannerList);
                }
                return new ResponseEnvelope(false,"查询结果为空");
            }
            return new ResponseEnvelope(false,"位置编码对应位置信息不存在");
        }catch (Exception e){
            logger.error("系统异常",e);
            return new ResponseEnvelope(false,"系统异常");
        }
    }


    /**
     * 添加广告图片
     * @param file 文件
     * @return ResBannerPic 广告图片实体类
     * @throws Exception
     */
    private ResBannerPic addResBannerPic(MultipartFile file) throws Exception{
        ResBannerPic pic=new ResBannerPic();
        StringBuffer sb = new StringBuffer();
        sb.append(basePath);
        sb.append(relativePath);
        File dir = new File(sb.toString());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = file.getOriginalFilename();
        //上传文件名
        String filename = StringUtils.substringBeforeLast(name, ".");
        //后缀
        String suffix = StringUtils.substringAfterLast(name, ".");
        //大小
        Long size = file.getSize();
        long millis = System.currentTimeMillis();
        sb.append("/");
        sb.append(millis);
        sb.append(".");
        sb.append(suffix);
        String filePath = sb.toString();
        logger.error("${}", filePath);
        File target = new File(filePath);
        //上传
        file.transferTo(target);

        //路径
        StringBuffer sBuffer = new StringBuffer(relativePath);
        sBuffer.append("/");
        sBuffer.append(millis);
        sBuffer.append(".");
        sBuffer.append(suffix);
        String savePath = sBuffer.toString();
        logger.error("${}", savePath);
        //为ResBannerPic添加 准备入参 1.ResBannerPicAdd转ResBannerPic  2.摄值(ResBannerPicAdd中未定义的属性值)
        pic.setPicFileName(filename);
        pic.setPicSize(size.intValue());
        pic.setPicSuffix(suffix);
        pic.setPicPath(savePath);
        return pic;
    }

    @ApiOperation(value = "删除轮播广告", response = MiniProgramBannerVO.class)
    @DeleteMapping("/delete")
    public ResponseEnvelope deleteBanner(@RequestParam("bannerAdId") Integer bannerAdId){
        try{
            if (bannerAdId==null){
                return new ResponseEnvelope(false,"参数异常");
            }
            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            BaseBannerAd bannerAd = baseBannerAdService.getBannerById(bannerAdId);
            if (null!=bannerAd){
                int result1 = resBannerPicService.deleteBannerPic(bannerAd.getResBannerPicId(), loginUser);
                if (result1<=0){
                    return new ResponseEnvelope(false,"删除广告图片");
                }
                int result2 = baseBannerAdService.deleteBanner(bannerAd.getId(), loginUser);
                if (result2<=0){
                    return new ResponseEnvelope(false,"删除广告信息失败");
                }
                if (result1>0&&result2>0){
                    return new ResponseEnvelope(true,"删除成功");
                }
            }
            return new ResponseEnvelope(false,"数据库查询无此banner");

        }catch (Exception e){
            logger.error("系统异常",e);
            return new ResponseEnvelope(false,"系统异常");
        }
    }



}
