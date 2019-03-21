package com.sandu.web.banner;

import java.io.File;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sandu.api.banner.input.ResBannerPicAdd;
import com.sandu.api.banner.input.ResBannerPicUpdate;
import com.sandu.api.banner.model.ResBannerPic;
import com.sandu.api.banner.output.ResBannerPicVO;
import com.sandu.api.banner.service.ResBannerPicService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 *广告图片-控制层
 * @author WangHaiLin
 * @date 2018/5/17  10:05
 */
@Api(tags = "bannerPic", description = "广告图片")
@RestController
@RequestMapping("/v1/base/banner/pic")
public class ResBannerPicController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(ResBannerPicController.class);
    @Autowired
    private ResBannerPicService resBannerPicService;

    @Value("${upload.base.path}")
    private String basePath;

    @Value("${upload.relative.path}")
    private String relativePath;

    @ApiOperation(value = "新增广告图片", response = ResponseEnvelope.class)
    @PostMapping
    public ResponseEnvelope addBannerPic(@RequestPart("file") MultipartFile file, ResBannerPicAdd picAdd){
        ResBannerPic pic=new ResBannerPic();
        int result=0;
        try {
            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            StringBuffer sb=new StringBuffer();
            sb.append(basePath);
            sb.append(relativePath);
            File dir = new File(sb.toString());
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String name=file.getOriginalFilename();
            //上传文件名
            String filename = StringUtils.substringBeforeLast(name, ".");
            //后缀
            String suffix=  StringUtils.substringAfterLast(name,".");
            //大小
            Long size =file.getSize();
            long millis = System.currentTimeMillis();
            sb.append("/");
            sb.append(millis);
            sb.append(".");
            sb.append(suffix);
            String filePath=sb.toString();
            logger.error("${}",filePath);
            File target = new File(filePath);
            //上传
            file.transferTo(target);

            //路径
            StringBuffer sBuffer=new StringBuffer(relativePath);
            sBuffer.append("/");
            sBuffer.append(millis);
            sBuffer.append(".");
            sBuffer.append(suffix);
            String savePath = sBuffer.toString();
            logger.error("${}",savePath);
            //为ResBannerPic添加 准备入参 1.ResBannerPicAdd转ResBannerPic  2.摄值(ResBannerPicAdd中未定义的属性值)
            pic = this.getPicFromPicAdd(pic,picAdd);
            pic.setPicFileName(filename);
            pic.setPicSize(size.intValue());
            pic.setPicSuffix(suffix);
            pic.setPicPath(savePath);
            result = resBannerPicService.addResBannerPic(pic,loginUser);
            if (result>0){
                pic.setId(result);
                return new ResponseEnvelope(true,"新增成功");
            }
            return new ResponseEnvelope(false,"添加失败");
        } catch (Exception e) {
            logger.error("系统异常",e);
            return new ResponseEnvelope(false,"系统异常");
        }

    }


    @ApiOperation(value = "删除广告图片信息", response = ResponseEnvelope.class)
    @DeleteMapping
    public ResponseEnvelope deleteBanner(@RequestParam("picId")Integer picId){
        try{
            //1.校验参数
            if (picId==null){
                return new ResponseEnvelope(false,"picId不能为空");
            }
            //2.查询要删除数据是否存在
            ResBannerPic pic = resBannerPicService.getPicById(picId);
            if (pic==null){
                return new ResponseEnvelope(false,"无此Pic信息");
            }
            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            //3.位置信息存在，进行删除
            int result = resBannerPicService.deleteBannerPic(picId,loginUser);
            if(result>0){
                return new ResponseEnvelope(true,"删除成功");
            }
            return new ResponseEnvelope(false,"删除失败");
        }catch (Exception e){
            logger.error("系统异常",e);
            return new ResponseEnvelope(false,"系统异常");
        }

    }


    @ApiOperation(value = "修改广告信息", response = ResponseEnvelope.class)
    @PutMapping
    public ResponseEnvelope updatePosition(@Valid @RequestBody ResBannerPicUpdate update, BindingResult validResult){
        try{
            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }
            //2.查询要修改数据是否存在
            ResBannerPic pic = resBannerPicService.getPicById(update.getPicId());
            if (pic==null){
                return new ResponseEnvelope(false,"无此Pic信息");
            }
            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            //3.信息存在，调取服务，进行修改
            int result = resBannerPicService.updateBannerPic(update,loginUser);
            if(result>0){
                return new ResponseEnvelope(true,"修改成功");
            }
            return new ResponseEnvelope(false,"修改操作失败");
        }catch (Exception e){
            logger.error("系统异常",e);
            return new ResponseEnvelope(false,"系统异常");
        }

    }


    @ApiOperation(value = "通过Id查询广告图片", response = ResBannerPicVO.class)
    @GetMapping
    public ResponseEnvelope getBannerPicById(@RequestParam("picId")Integer picId){
        try{
            //校验参数
            if (picId==null){
                return new ResponseEnvelope(false,"picI不能为空");
            }
            //进行查询
            ResBannerPic pic = resBannerPicService.getPicById(picId);
            //输出转换
            ResBannerPicVO picVO=new ResBannerPicVO().getPicVoFromPic(pic);
            if (null!=pic){
                return new ResponseEnvelope(true,"查询成功",picVO);
            }
            return new ResponseEnvelope(true,"无此对应图片");
        }catch (Exception e){
            logger.error("系统异常",e);
            return new ResponseEnvelope(false,"系统异常");
        }
    }

    private ResBannerPic getPicFromPicAdd(ResBannerPic pic,ResBannerPicAdd picAdd){
        pic.setPicCode(picAdd.getCode());
        pic.setPicName(picAdd.getName());
        pic.setPicType(picAdd.getType());
        pic.setPicWeight(picAdd.getWeight());
        pic.setPicHigh(picAdd.getHigh());
        pic.setPicLevel(picAdd.getLevel());
        pic.setPicFormat(picAdd.getFormat());
        pic.setPicDesc(picAdd.getDesc());
        pic.setSysCode(picAdd.getSysCode());
        pic.setRemark(picAdd.getRemark());
        return  pic;
    }
}
