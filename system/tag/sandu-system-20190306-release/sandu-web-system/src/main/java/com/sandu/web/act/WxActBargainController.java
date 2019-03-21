package com.sandu.web.act;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.pointmall.model.Users;
import com.sandu.commons.PageHelper;
import io.swagger.annotations.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sandu.api.act.input.WxActBargainAdd;
import com.sandu.api.act.input.WxActBargainUpdate;
import com.sandu.api.act.model.WxActBargain;
import com.sandu.api.act.model.WxActBargainRegistration;
import com.sandu.api.act.output.WxActBargainAndRedDetailsVO;
import com.sandu.api.act.output.WxActBargainVO;
import com.sandu.api.act.service.WxActBargainRegistrationService;
import com.sandu.api.act.service.WxActBargainService;
import com.sandu.api.company.model.BaseCompanyMiniProgramConfig;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.util.UUIDUtil;
import com.sandu.web.BaseController;

import static com.sandu.constant.Punctuation.SLASH;

@Api(tags = "ActBargain", description = "砍价活动")
@RestController
@RequestMapping("/v1/act/bargain")
public class WxActBargainController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(WxActBargainController.class);
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    @Autowired
    private WxActBargainService wxActBargainService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WxActBargainRegistrationService wxActBargainRegistrationService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Value("${wx.act.bargain.url}")
    private String wxActBargainUrl;

    @Value("${upload.base.path}")
    private String rootPath;

    @Value("${act.img.default.path}")
    private String actImgDefaultPath;

    @ApiOperation(value = "查询砍价活动列表", response = ResponseEnvelope.class)
    @PostMapping("/getWxActBargainList")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",value = "token",required = true,dataType = "String",name = "Authorization"),
        @ApiImplicitParam(paramType = "query",required = true,dataType = "Integer",name="page"),
        @ApiImplicitParam(paramType = "query",required = true,dataType = "Integer",name="limit")
    })
    public ResponseEnvelope<WxActBargainVO> getWxActBargainList(Integer page,Integer limit) {
        if (Objects.isNull(page)){
            page = 1;
        }

        if (Objects.isNull(limit)){
            limit = 10;
        }

        page = (page - 1) * limit;

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {
            List<BaseCompanyMiniProgramConfig> configs = this.getBaseCompanyMiniProConfigInfo(loginUser.getId());
            if (Objects.nonNull(configs) || !configs.isEmpty()) {
                List<String> appids = configs.stream().map(BaseCompanyMiniProgramConfig::getAppId).collect(Collectors.toList());
                logger.info("company appids =>{}" + (appids == null ? 0 : appids.toString()));
                //根据小程序appids去查询wx_act_bargain砍价活动表,返回企业小程序配置的活动列表
                int total = wxActBargainService.countByAppids(appids);
                List<WxActBargainVO> vos = null;
                if (total > 0){
                    List<WxActBargain> wxActBargains =  wxActBargainService.selectByAppids(appids,page,limit);
                    //复制链接按钮:需要配置一个链接(前端给),到时返回列表的复制链接按钮使用.
                    vos = this.transformVO(wxActBargains);
                    //统计参与人数与成功砍价人数
                    this.countJoinANDSuccess(vos);
                }
                return new ResponseEnvelope(true, total,vos);
            }
            return new ResponseEnvelope(true, 0,Collections.EMPTY_LIST);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope(false, "系统错误");
        }
    }

    private void countJoinANDSuccess(List<WxActBargainVO> vos) {
        List<String> ids = vos.stream().map(WxActBargainVO::getId).collect(Collectors.toList());
        List<WxActBargainRegistration> wxActBargains =  wxActBargainRegistrationService.getBargainRegistrationsByActIds(ids);
        vos.stream().forEach(vo ->{
            //设置活动参与人数
            List<WxActBargainRegistration> wxActBargainRegistrations = wxActBargains.stream().filter(wx -> {
                return Objects.equals(wx.getActId(), vo.getId());
            }).collect(Collectors.toList());

            //统计砍价成功人数
            long bargainSuccessfulPeoples = wxActBargainRegistrations.stream().filter(item ->{
                return Objects.equals(item.getCompleteStatus(),10) && Objects.equals(item.getExceptionStatus(),0);
            }).count();

            vo.setActJoinPeoples(wxActBargainRegistrations.size());
            vo.setBargainSuccessfulPeoples(new Long(bargainSuccessfulPeoples).intValue());
        });
    }

    private List<BaseCompanyMiniProgramConfig> getBaseCompanyMiniProConfigInfo(Integer userId) {
        //获取用户企业id
        SysUser sysUser = sysUserService.get(userId);
        ////根据登录用户获到用户所属企业有几个小程序
        return baseCompanyService.getCompanyMiniProgramConfigs(sysUser.getCompanyId());

    }

    private List<WxActBargainVO> transformVO(List<WxActBargain> wxActBargains) {
        if (Objects.nonNull(wxActBargains) && !wxActBargains.isEmpty()) {
            wxActBargains.forEach(item -> item.setCopyUrl(wxActBargainUrl + "?actId=" + item.getId()));
            //转换VO
            List<WxActBargainVO> vos = wxActBargains.stream().map(model -> {
                model.setActStatus(wxActBargainService.getWxActBargainStatus(model));
                WxActBargainVO vo = WxActBargainVO.builder().build();
                BeanUtils.copyProperties(model, vo);
                return vo;
            }).collect(Collectors.toList());
            return vos;
        }
        return Collections.EMPTY_LIST;
    }

    @ApiOperation(value = "获取用户所属企业小程序列表", response = ResponseEnvelope.class)
    @PostMapping("/getWxMiniProgramList")
    @ApiImplicitParam(paramType = "header",value = "token",required = true,dataType = "String",name = "Authorization")
    public ResponseEnvelope getWxMiniProgramList() {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        //根据登录用户获到用户所属企业sys_user(companyId)
        //根据companyId查询表base_company_mini_program_config获取微信小程序列表
        //表base_company_mini_program_config需要加小程序名称字段
        try {
            List<BaseCompanyMiniProgramConfig> baseCompanyMiniProConfigInfo = this.getBaseCompanyMiniProConfigInfo(loginUser.getId());
            return new ResponseEnvelope(true,baseCompanyMiniProConfigInfo);
        } catch (Exception e) {
            logger.error("系统错误",e);
            return new ResponseEnvelope(false,"系统错误");
        }
    }

    @ApiOperation(value = "上传活动图片",response = ResponseEnvelope.class)
    @PostMapping(value = "/uploadActImg")
    public ResponseEnvelope uploadActImg(MultipartFile file){

        if (file == null || file.isEmpty()){
            return new ResponseEnvelope(false,"请选择要上传的图片");
        }
        try {
            Object imgPath = this.executeUploadFile(file);
            return new ResponseEnvelope(false,"上传文件成功",imgPath);
        } catch (IOException e) {
            logger.error("系统错误",e);
            return new ResponseEnvelope(false,"上传文件失败");
        }
    }

    private String executeUploadFile(MultipartFile file) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(rootPath);
        sb.append(actImgDefaultPath);
        //sb.append("C:\\Users\\Sandu\\Desktop\\压缩");
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

        //上传文件
        String filePath = sb.toString();
        // 1、生成缩略图，并写入磁盘
        File targetFile = new File(filePath);
        try {
            Thumbnails.of(file.getInputStream()).size(750, 750).toFile(targetFile);
        } catch (IOException e) {
            //发生异常,上传原图
            file.transferTo(targetFile);
        }

        //file.transferTo(targetFile);
        logger.info("文件上传的路劲"+filePath);

        StringBuffer sBuffer = new StringBuffer(actImgDefaultPath);
        sBuffer.append("/");
        sBuffer.append(millis);
        sBuffer.append(".");
        sBuffer.append(suffix);
        String savePath = sBuffer.toString();
        logger.error("数据库文件存储路径"+savePath);

        return savePath;
    }
    
    
    @ApiOperation(value = "创建砍价活动", response = ResponseEnvelope.class)
    @PostMapping("/createWxActBargain")
    public ResponseEnvelope createWxActBargain(@RequestBody @Valid WxActBargainAdd wxActBargainAdd, BindingResult validResult) {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {
            //验证参数
            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult,new ResponseEnvelope());
            }

            if (wxActBargainAdd.getMyCutPriceMax() < wxActBargainAdd.getMyCutPriceMin()){
                return new ResponseEnvelope(false,"自己砍价最高金额不能低于自己砍价最小金额");
            }

            if (wxActBargainAdd.getCutMethodPriceMax() < wxActBargainAdd.getCutMethodPriceMin()){
                return new ResponseEnvelope(false,"好友砍价最高金额不能低于好友砍价最低金额");
            }

            if (wxActBargainAdd.getEndTime().compareTo(wxActBargainAdd.getBegainTime()) < 0){
                return new ResponseEnvelope(false,"活动结束时间不能晚于活动开始时间");
            }

            if (wxActBargainAdd.getProductCount() <= 0){
                return new ResponseEnvelope(false,"请添加活动的商品库存");
            }
            
            if (wxActBargainAdd.getProductDisplayCount()<wxActBargainAdd.getProductCount()){
                return new ResponseEnvelope(false,"商品显示库存不得小于商品实际库存");
            }
            
            if(wxActBargainAdd.getOnlyAllowNew()==0) {
            	if(wxActBargainAdd.getHelpCutPerDay()==null && wxActBargainAdd.getHelpCutPerAct()==null) {
            		return new ResponseEnvelope(false,"请输入每人可帮砍价好友数");
            	}
            	if(wxActBargainAdd.getHelpCutPerAct()!=null && wxActBargainAdd.getHelpCutPerAct()<=0 ) {
            		return new ResponseEnvelope(false,"好友/整个活动期间必须大于0");
            	}else if(wxActBargainAdd.getHelpCutPerAct()==null) {
            		if(wxActBargainAdd.getHelpCutPerDay()!=null && wxActBargainAdd.getHelpCutPerDay()<=0) {
    	            	return new ResponseEnvelope(false,"好友/每天必须大于0");
                	}
            	} else {
            		wxActBargainAdd.setHelpCutPerDay(null);
            	}
            	
            }

//            WxActBargain wx = wxActBargainService.getWxActBargainByActName(wxActBargainAdd.getActName());
//            if (Objects.nonNull(wx)){
//                //同一小程序下名称不能重复
//                if(Objects.equals(wx.getAppId(),wxActBargainAdd.getAppId())){
//                    return new ResponseEnvelope(false,"活动名称已占用");
//                }
//            }
            WxActBargain wxActBargain = this.buildWxActBargain(wxActBargainAdd,loginUser);
            wxActBargainService.createWxActBargain(wxActBargain);
            return new ResponseEnvelope<>(true, "保存成功!");
        } catch (Exception ex) {
            logger.error("系统错误",ex);
            return new ResponseEnvelope<>(false, "保存失败!");
        }
    }

    private WxActBargain buildWxActBargain(WxActBargainAdd wxActBargainAdd, LoginUser loginUser) {
        WxActBargain wxActBargain = new WxActBargain();
        addSystemFiled(wxActBargainAdd,loginUser);
        addCompanyInfo(wxActBargainAdd);
        BeanUtils.copyProperties(wxActBargainAdd, wxActBargain);
        return wxActBargain;
    }

    private void addCompanyInfo(WxActBargainAdd wxActBargainAdd) {
        //获取小程序名称
        BaseCompanyMiniProgramConfig config = baseCompanyService.getBaseCompanyMiniProConfigByAppid(wxActBargainAdd.getAppId());
        //获取小程序所属企业名称
        BaseCompany baseCompany = baseCompanyService.getCompanyById(config.getCompanyId());
        wxActBargainAdd.setAppName(config.getMinProName());
        wxActBargainAdd.setCompanyId(config.getCompanyId().intValue());
        wxActBargainAdd.setCompanyName(baseCompany.getCompanyName());
    }

    private void addSystemFiled(WxActBargainAdd wxActBargainAdd, LoginUser loginUser) {
        SysUser sysUser = sysUserService.get(loginUser.getId());
        Date now = new Date();
        wxActBargainAdd.setCreator(sysUser.getNickName());
        wxActBargainAdd.setModifier(sysUser.getNickName());
        wxActBargainAdd.setGmtCreate(now);
        wxActBargainAdd.setGmtModified(now);
        wxActBargainAdd.setId(UUIDUtil.getUUID());
        wxActBargainAdd.setIsEnable(1);
        wxActBargainAdd.setProductRemainCount(wxActBargainAdd.getProductCount());
        wxActBargainAdd.setProductVitualCount(0);
        wxActBargainAdd.setIsDeleted(0);
        wxActBargainAdd.setRegistrationCount(0);
        if (Objects.isNull(wxActBargainAdd.getSysReduceNum())){
            wxActBargainAdd.setSysReduceNum(0);
        }
    }

    @ApiOperation(value = "修改砍价活动", response = ResponseEnvelope.class)
    @PostMapping("/modifyWxActBargain")
    public ResponseEnvelope modifyWxActBargain(@RequestBody@Valid WxActBargainUpdate wxActBargainUpdate,BindingResult validResult) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        //获取活动详情,如果活动已经开始,则不能修改
        if (validResult.hasErrors()) {
            return processValidError(validResult,new ResponseEnvelope());
        }
        try {
        	 
            WxActBargain wxActBargain = wxActBargainService.getWxActBargain(wxActBargainUpdate.getId());
            Integer actStatus = wxActBargainService.getWxActBargainStatus(wxActBargain);
            if (actStatus == WxActBargain.STATUS_UNBEGIN){
            	if(wxActBargainUpdate.getOnlyAllowNew()==0) {
            		  
                	if(wxActBargainUpdate.getHelpCutPerDay()==null && wxActBargainUpdate.getHelpCutPerAct()==null) {
                		return new ResponseEnvelope(false,"请输入每人可帮砍价好友数");
                	}
                	if(wxActBargainUpdate.getHelpCutPerAct()!=null && wxActBargainUpdate.getHelpCutPerAct()<=0 ) {
                  		return new ResponseEnvelope(false,"好友/整个活动期间必须大于0");
                  	}else if(wxActBargainUpdate.getHelpCutPerAct()==null) {
                  		if(wxActBargainUpdate.getHelpCutPerDay()!=null && wxActBargainUpdate.getHelpCutPerDay()<=0) {
          	            	return new ResponseEnvelope(false,"好友/每天必须大于0");
                      	}
                  	}
                	
                	if(wxActBargainUpdate.getHelpCutPerAct()!=null && wxActBargainUpdate.getHelpCutPerAct()>0) {
                		wxActBargainUpdate.setHelpCutPerDay(0);
                	}else if(wxActBargainUpdate.getHelpCutPerDay()!=null && wxActBargainUpdate.getHelpCutPerDay()>0) {
                		wxActBargainUpdate.setHelpCutPerAct(0);
                	}
                }else {
                	wxActBargainUpdate.setHelpCutPerAct(0);
                	wxActBargainUpdate.setHelpCutPerDay(0);
                }
                //活动还没有开始,可以更新
                WxActBargain updateWxActBargain = new WxActBargain();
                BeanUtils.copyProperties(wxActBargainUpdate, updateWxActBargain);
                wxActBargainService.modifyWxActBargain(updateWxActBargain);
                return new ResponseEnvelope(true,"更新成功!!!");
            }else if (actStatus == WxActBargain.STATUS_ONGOING){
            	
            	if(wxActBargainUpdate.getOnlyAllowNew()==0) {
                	if(wxActBargainUpdate.getHelpCutPerAct()==null && wxActBargainUpdate.getHelpCutPerDay()==null) {
                		return new ResponseEnvelope(false,"请输入每人可帮砍价好友数");
                	}
                	if(wxActBargainUpdate.getHelpCutPerAct()!=null && wxActBargainUpdate.getHelpCutPerAct()<=0 ) {
                  		return new ResponseEnvelope(false,"好友/整个活动期间必须大于0");
                  	}else if(wxActBargainUpdate.getHelpCutPerAct()==null) {
                  		if(wxActBargainUpdate.getHelpCutPerDay()!=null && wxActBargainUpdate.getHelpCutPerDay()<=0) {
          	            	return new ResponseEnvelope(false,"好友/每天必须大于0");
                      	}
                  	}
                	
                	if(wxActBargainUpdate.getHelpCutPerAct()!=null && wxActBargainUpdate.getHelpCutPerAct()>0) {
                		wxActBargainUpdate.setHelpCutPerDay(0);
                	}else if(wxActBargainUpdate.getHelpCutPerDay()!= null && wxActBargainUpdate.getHelpCutPerDay()>0) {
                		wxActBargainUpdate.setHelpCutPerAct(0);
                	}
                }else {
                	wxActBargainUpdate.setHelpCutPerAct(0);
                	wxActBargainUpdate.setHelpCutPerDay(0);
                }
            	WxActBargain updateWxActBargain = new WxActBargain();
            	updateWxActBargain.setMyCutPriceMax(wxActBargainUpdate.getMyCutPriceMax());
            	updateWxActBargain.setMyCutPriceMin(wxActBargainUpdate.getMyCutPriceMin());
            	updateWxActBargain.setCutMethodPriceMax(wxActBargainUpdate.getCutMethodPriceMax());
            	updateWxActBargain.setCutMethodPriceMin(wxActBargainUpdate.getCutMethodPriceMin());
            	updateWxActBargain.setOnlyAllowNew(wxActBargainUpdate.getOnlyAllowNew());
            	updateWxActBargain.setHelpCutPerAct(wxActBargainUpdate.getHelpCutPerAct());
            	updateWxActBargain.setHelpCutPerDay(wxActBargainUpdate.getHelpCutPerDay());
            	updateWxActBargain.setId(wxActBargainUpdate.getId());
            	wxActBargainService.modifyWxActBargain(updateWxActBargain);
                return new ResponseEnvelope(true,"更新成功!!!");
            }else{
                return new ResponseEnvelope(false,"活动已经结束,不能更新数据!!!");
            }
        } catch (Exception e) {
            logger.error("系统错误",e);
            return new ResponseEnvelope(false,"系统错误");
        }
    }


    @ApiOperation(value = "获取活动详情接口", response = ResponseEnvelope.class)
    @PostMapping("/getWxActBargainDetails")
    public ResponseEnvelope getWxActBargainDetails(String actId) {

        if (StringUtils.isEmpty(actId)){
            return new ResponseEnvelope(false,"parameter actId is null");
        }
        //LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        //根据actId 查询wx_act_bargain砍价活动表
        try {
            WxActBargainVO vo = null;
            WxActBargain wxActBargain = wxActBargainService.getWxActBargain(actId);
            if (Objects.nonNull(wxActBargain)){
                vo = WxActBargainVO.builder().build();
                transfromVO(wxActBargain,vo);
                if(vo.getHelpCutPerAct()!=null && vo.getHelpCutPerAct()==0) {
                	vo.setHelpCutPerAct(null);
                }
                if(vo.getHelpCutPerDay()!=null && vo.getHelpCutPerDay()==0) {
                	vo.setHelpCutPerDay(null);
                }
            }
            return new ResponseEnvelope(true,vo);
        } catch (Exception e) {
            logger.error("系统错误",e);
            return new ResponseEnvelope(false,"系统错误");
        }
    }

    public void transfromVO(Object currentObj,Object targetObj){
        BeanUtils.copyProperties(currentObj,targetObj);
    }

    @ApiOperation(value = "结束活动", response = ResponseEnvelope.class)
    @PostMapping("/finishWxActBargain")
    public ResponseEnvelope finishWxActBargain(String actId) {

        if (StringUtils.isEmpty(actId)){
            return new ResponseEnvelope(false,"parameter actId is null");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        //update wx_act_bargain is_enable = 0
        try {
            WxActBargain update = this.buildUpdateObjecet(loginUser.getId(),actId);
            update.setIsEnable(0);
            wxActBargainService.modifyWxActBargain(update);
            return new ResponseEnvelope(true,"结束活动成功");
        } catch (Exception e) {
            logger.error("系统错误",e);
            return new ResponseEnvelope(false,"系统错误");
        }
    }


    private WxActBargain buildUpdateObjecet(Integer userId,String actId) {
        SysUser sysUser = sysUserService.get(userId);
        Date now = new Date();
        WxActBargain update = new WxActBargain();
        update.setId(actId);
        update.setModifier(sysUser.getNickName());
        update.setGmtModified(now);
        return update;
    }

    @ApiOperation(value = "删除活动", response = ResponseEnvelope.class)
    @PostMapping("/removeWxActBargain")
    public ResponseEnvelope removeWxActBargain(String actId) {

        if (StringUtils.isEmpty(actId)){
            return new ResponseEnvelope(false,"parameter actId is null");
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {
            //如果活动正在进行,提示请先结束活动再删除
            WxActBargain wxActBargain = wxActBargainService.getWxActBargain(actId);
            if (Objects.isNull(wxActBargain)){
                return new ResponseEnvelope(false,"活动不存在");
            }
            //If(is_enable&&(endTime<currentTime||库存<=0))
            //update wx_act_bargain is_deleted = 1 where id =actId
            if (Objects.equals(wxActBargainService.getWxActBargainStatus(wxActBargain),WxActBargain.STATUS_ENDED)
                                                    ||
                Objects.equals(wxActBargainService.getWxActBargainStatus(wxActBargain),WxActBargain.STATUS_UNBEGIN)
            ){
                //满足删除的活动的条件
                WxActBargain update = buildUpdateObjecet(loginUser.getId(), actId);
                update.setIsDeleted(1);
                wxActBargainService.modifyWxActBargain(update);
                return new ResponseEnvelope(true,"删除成功!!!");
            }else{
                //不满足条件
                return new ResponseEnvelope(false,"提示请先结束活动再删除");
            }
        } catch (Exception e) {
            logger.error("系统错误",e);
            return new ResponseEnvelope(false,"系统错误");
        }
    }

    @ApiOperation(value = "设置虚拟库存扣除参数", response = ResponseEnvelope.class)
    @PostMapping("/setWxActBargainAwardMsg")
    public ResponseEnvelope setWxActBargainAwardMsg ( String actId, Integer sysDecreaseNum) {
        //update wx_act_bargain set is_ sys_decrease_num =? where id=actId
        if (StringUtils.isBlank(actId)||sysDecreaseNum==null){
            return new ResponseEnvelope<>(false, "请传入actId和sysDecreaseNum！");
        }
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        //查询待修改的活动详情
        WxActBargain wxActBargain = wxActBargainService.getWxActBargain(actId);
        //虚拟扣除参数要小于库存剩余数量
        if (sysDecreaseNum>wxActBargain.getProductRemainCount()){
            return new ResponseEnvelope<>(false, "虚拟扣除参数是:"+sysDecreaseNum+" 不能大于库存剩余数量："+wxActBargain.getProductRemainCount());
        }
        //处理要修改的数据
        wxActBargain.setSysReduceNum(sysDecreaseNum);
        wxActBargain.setModifier(loginUser.getUserName());
        wxActBargain.setGmtModified(new Date());
        int result = wxActBargainService.modifyWxActBargain(wxActBargain);
        if (result>0){
            return new ResponseEnvelope<>(true, "虚拟库存扣除参数设置成功！");
        }
		return new ResponseEnvelope<>(false, "虚拟库存扣除参数设置失败！");
    }

    @ApiOperation(value = "获取活动详情接口", response = ResponseEnvelope.class)
    @GetMapping("/getActAndRegDetails")
    public ResponseEnvelope getWxActBargainAndRegistrationDetails(String actId,String regId) {
		try {
			if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(false, "活动id不能为空!"); 
			}
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = sysUserService.get(loginUser.getId());
			//返回砍价金额
			WxActBargain actEntity = wxActBargainService.getWxActBargain(actId,user.getAppId());
			if(actEntity==null) {
				return new ResponseEnvelope<>(false, "活动不存在!"); 
			}
			WxActBargainRegistration regEntity = null;
			if(StringUtils.isNotBlank(regId)) {
				regEntity = wxActBargainRegistrationService.getWxActBargainRegistration(regId);
			}
			WxActBargainAndRedDetailsVO vo = this.buildResultVo(actEntity, regEntity);
			return new ResponseEnvelope<>(true, vo);
    	}catch(Exception ex) {
			logger.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }

    private WxActBargainAndRedDetailsVO buildResultVo(WxActBargain actEntity,WxActBargainRegistration regEntity) {
    	WxActBargainAndRedDetailsVO vo = new WxActBargainAndRedDetailsVO();
    	vo.setActName(actEntity.getActName());
    	vo.setActRule(actEntity.getActRule());
    	vo.setProductImg(actEntity.getProductImg());
    	vo.setProductDiscountPrice(actEntity.getProductDiscountPrice());
    	vo.setProductOriginalPrice(actEntity.getProductOriginalPrice());
    	vo.setShareImg(actEntity.getShareImg());
    	vo.setBegainTime(dateFormat.format(actEntity.getBegainTime()));
    	vo.setEndTime(dateFormat.format(actEntity.getEndTime()));
    	//倒计时:由服务端统一处理,客户端的当前时间可能不一样.返回一个部的秒数,给前端倒计时就可以了.
    	Long actRemainTime = (actEntity.getEndTime().getTime()-new Date().getTime())/1000;
    	actRemainTime = actRemainTime<0?0:actRemainTime;
    	vo.setActRemainTime(actRemainTime);
    	//参与人数=真实参加人数(registrationCount)+每小时递增的
    	vo.setRegistrationCount(actEntity.getRegistrationCount()+actEntity.getProductVitualCount());
    	if(WxActBargain.STATUS_ENDED==wxActBargainService.getWxActBargainStatus(actEntity)) {
    		vo.setProductRemainCount(0);
    		vo.setActRemainTime(0L);
    	}else {
    		vo.setProductRemainCount(actEntity.getProductDisplayCount());
    	}
    	
    	vo.setProductName(actEntity.getProductName());
    	//任务已开始
    	if(regEntity!=null) {
    		vo.setProductRemainPrice(regEntity.getProductRemainPrice());
    	}
    	//任务未开始
    	else {
    		vo.setProductRemainPrice(actEntity.getProductDiscountPrice()-actEntity.getProductMinPrice());
    	}
    	vo.setProductMinPrice(actEntity.getProductMinPrice());
    	return vo;
    }


}
