package com.nork.common.pano;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.nork.common.exception.GeneratePanoramaException;
import com.nork.common.model.LoginUser;
import com.nork.common.util.Constants;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.RenderUtil;
import com.nork.common.util.SendEmail;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.ProductsCostType;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseCompany;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseCompanyService;
import com.nork.render.model.RenderPanoLevel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;

/**
 * Created by Administrator on 2017/4/11.
 */
public class GeneratePanoramaTask implements Runnable {

    Logger logger = Logger.getLogger(GeneratePanoramaTask.class);

    private final String RESOURCE_URL = Utils.getPropertyName("app","app.resources.url","http://tests.sanduspace.cn");// 资源访问根目录
    private final String OUTPUT_PATH =  Utils.getPropertyName("render","app.pano.outPutPath","/sz/sdkj/nork_resource/vr720/");// 720页面输出目录

    // 任务ID
    private Integer taskId;
    // 任务截图ID
    private Integer taskPicId;
    // 设计方案ID
    private Integer planId;
    // 设计方案code
    private String businessCode;
    // 720全景图地址
    private String picPath;
    // 原图ID
    private Integer picId;
    // 720缩略图ID
    private Integer smallResPicId;
    // 720图片清晰度级别
    private Integer panoLevel;

    private ResRenderPicService resRenderPicService= SpringContextHolder.getBean(ResRenderPicService.class);

    private DesignPlanService designPlanService = SpringContextHolder.getBean(DesignPlanService.class);

    private SysUserService sysUserService = SpringContextHolder.getBean(SysUserService.class);

    private ResPicService resPicService = SpringContextHolder.getBean(ResPicService.class);

    private AuthorizedConfigService authorizedConfigService = SpringContextHolder.getBean(AuthorizedConfigService.class);

    private BaseCompanyService baseCompanyService = SpringContextHolder.getBean(BaseCompanyService.class);

    private SpaceCommonService spaceCommonService = SpringContextHolder.getBean(SpaceCommonService.class);

    private SysDictionaryService sysDictionaryService = SpringContextHolder.getBean(SysDictionaryService.class);

    private DesignPlanProductService designPlanProductService = SpringContextHolder.getBean(DesignPlanProductService.class);

    /**
     * 构造函数
     * @param taskId 任务ID
     * @param businessCode 设计方案编码
     * @param taskPicId 任务截图ID
     * @param picPath 720全景图地址
     * @param picId 720原图ID
     * @param smallResPicId 720缩略图ID
     * @param panoLevel 720图片清晰度级别
     */
    public GeneratePanoramaTask(Integer taskId, Integer planId, String businessCode,Integer taskPicId, String picPath,
                                Integer picId, Integer smallResPicId, Integer panoLevel) throws GeneratePanoramaException{
        if( taskId == null ){
            throw new GeneratePanoramaException("taskId不能为空");
        }
        if( planId == null ){
            throw new GeneratePanoramaException("planId不能为空");
        }
        if( businessCode == null ){
            throw new GeneratePanoramaException("businessCode不能为空");
        }
        /*if( taskPicId == null ){
            throw new GeneratePanoramaException("taskPicId不能为空");
        }*/
        if( picPath == null ){
            throw new GeneratePanoramaException("picPath不能为空");
        }
        if( picId == null ){
            throw new GeneratePanoramaException("picId不能为空");
        }
        if( panoLevel == null ){
            throw new GeneratePanoramaException("panoLevel不能为空");
        }
//        if( smallResPicId == null ){
//            throw new GeneratePanoramaException("smallResPicId不能为空");
//        }
        this.taskId = taskId;
        this.planId = planId;
        this.picPath = picPath;
        this.businessCode = businessCode;
        this.taskPicId = taskPicId;
        this.picId = picId;
        this.smallResPicId = smallResPicId;
        this.panoLevel = panoLevel;
    }
    public GeneratePanoramaTask(int i){
        this.taskId = i;
    }

    @Override
    public void run() {
        JSONObject panoramaJson = RenderUtil.generatePano(picPath, businessCode);
        if( panoramaJson != null && panoramaJson.getBoolean("success") ){
            String panoramaPath = "/" + this.getBusinessCode() + "/" + this.getBusinessCode() + ".html";
            try {
                // 生成720全景页面详细信息（费用清单、公司、头像等）
                generatePanoramaDetails(this.getBusinessCode());
            } catch (GeneratePanoramaException e) {
                e.printStackTrace();
                logger.error(Utils.getLineNumber() + e.getMessage());
            }

            // 回填720页面访问地址
            ResRenderPic resRenderPic = new ResRenderPic();
            resRenderPic.setId(this.picId);
            resRenderPic.setPanoPath(panoramaPath+"#weixin.qq.com");
            resRenderPic.setSysTaskPicId(this.getTaskPicId());//保存渲染截图信息
            resRenderPicService.update(resRenderPic);

            //更新缩略图（保存截图id）
            /*ResRenderPic resRenderSmallPic = new ResRenderPic();
            resRenderSmallPic.setId(smallResPicId);
//            resRenderSmallPic.setSysTaskPicId(this.getTaskPicId());//保存渲染截图信息
            resRenderPicService.update(resRenderSmallPic);*/
        }else{
            SendEmail.massSend(new String[]{"124302412@qq.com"},"【生成720页面异常】",panoramaJson.getString("message"));
        }
    }

    /**
     * 生成720全景页面详细信息（费用清单、公司、头像等）
     * @param businessCode 设计方案code
     */
    public void generatePanoramaDetails(String businessCode) throws GeneratePanoramaException{
        DesignPlan designPlan = designPlanService.get(planId);
        // 获取用户名
        String userName = "";
        Integer userId = designPlan.getUserId();
        if( userId == null || userId.intValue() < 0 ){
            throw new GeneratePanoramaException("对应设计方案中的userId值为空或小于0!planId="+planId);
        }
        SysUser sysUser = sysUserService.get(userId);
        if( sysUser == null ){
            throw new GeneratePanoramaException("没有找到对应的用户信息!userId="+userId);
        }
        userName = sysUser.getUserName();

        // 获取用户公司
        String companyName = "";
        AuthorizedConfig authorizedConfig = new AuthorizedConfig();
        authorizedConfig.setUserId(sysUser.getId());
        authorizedConfig.setState(1);
        authorizedConfig.setIsDeleted(0);
        List<AuthorizedConfig> authorizedConfigs = authorizedConfigService.getList(authorizedConfig);
        if( authorizedConfigs == null || authorizedConfigs.size() == 0 ){
            throw new GeneratePanoramaException("没有找到用户绑定的序列号!userId="+userId);
        }
        authorizedConfig = authorizedConfigs.get(0);
        String companyId = authorizedConfig.getCompanyId();
        if( StringUtils.isBlank(companyId) ){
            throw new GeneratePanoramaException("序列号没有绑定公司!authorizedCode="+authorizedConfig.getAuthorizedCode());
        }
        BaseCompany baseCompany = baseCompanyService.get(Integer.valueOf(companyId));
        if( baseCompany == null ){
            throw new GeneratePanoramaException("没有找到序列号绑定的企业信息!authorizedCode="+authorizedConfig.getAuthorizedCode()+",companyId="+companyId);
        }
        companyName = baseCompany.getCompanyName();

        // 获取用户公司LOGO
        String companyLogo = "";
        if( baseCompany.getCompanyLogo() != null ){
            ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
            if( resPic != null ){
                /*File file = new File(Constants.UPLOAD_ROOT + resPic.getPicPath());*/
            	File file = new File(Utils.getAbsolutePath(resPic.getPicPath(), null));
                if( file.exists() ){
                    companyLogo = file.getName();
                    File targetFile = new File(Utils.getPropertyName("render","app.pano.outPutPath","") + businessCode + "/" + companyLogo);
                    FileUploadUtils.fileCopy(file,targetFile);
                }
            }
        }

        // 获取房型类型
        String htmlTitle = "";
        if( designPlan.getSpaceCommonId() != null ){
            SpaceCommon spaceCommon = spaceCommonService.get(designPlan.getSpaceCommonId());
            if( spaceCommon != null && spaceCommon.getSpaceFunctionId() != null ){
                SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType",spaceCommon.getSpaceFunctionId());
                htmlTitle = "我设计的" + sysDictionary.getName();
            }
        }

        // 随即获取一首背景音乐
        String musicName = "";
        String musicPath = Utils.getPropertyName("render","app.pano.background.music.path","");
        File musicDir = new File(musicPath);
        if( musicDir.exists() ){
            File[] files = musicDir.listFiles();
            if( files != null && files.length > 0 ) {
                Random random = new Random();
                // 随即获取一首歌曲名称
                int index = random.nextInt(files.length);
                File file = files[index];
                if( file != null && file.exists() ){
                    musicName = file.getName();
                }
            }
        }else{
            logger.error(Utils.getLineNumber()+"720背景音乐目录没有找到！"+musicPath);
        }

        // 获取产品清单
        LoginUser loginUser = new LoginUser();
        loginUser.setId(sysUser.getId());
        loginUser.setUserType(sysUser.getUserType());
        DesignPlanProduct designPlanProduct = new DesignPlanProduct();
        designPlanProduct.setPlanId(planId);
        designPlanProduct.setUserId(1);
//        List<ProductsCostType> costList = designPlanProductService.costList(loginUser, designPlanProduct, costListEnum.designPlan);
        List<ProductsCostType> costList = new ArrayList<>();
        // 费用清单总价
        StringBuffer costHtml = new StringBuffer();
        BigDecimal totalPrice = new BigDecimal(0.0);
        if( costList != null && costList.size() > 0 ) {
            for ( ProductsCostType cost : costList ) {
                totalPrice = totalPrice.add(cost.getTotalPrice());
                costHtml.append(cost.toString());
            }
        }

        // 根据720图片不同清晰度调整窗口大小
        String windowsPercent = "width:100%;height:100%;";
        if( panoLevel != null ){
            if( panoLevel == RenderPanoLevel.LEVEL_ONE ){
                windowsPercent = "width:35%;height:35%;";
            }else if( panoLevel == RenderPanoLevel.LEVEL_TWO ){
                windowsPercent = "width:70%;height:70%;";
            }else if( panoLevel == RenderPanoLevel.LEVEL_THREE ){
                windowsPercent = "width:90%;height:90%;";
            }
        }


        // 替换到720页面中
        String htmlPath = "/" + businessCode + "/" + businessCode + ".html";
        String htmlContext = FileUploadUtils.getFileContext(OUTPUT_PATH + htmlPath);
        htmlContext = htmlContext.replaceAll("%HTML_TITLE%",htmlTitle).replaceAll("%USER_NAME%",userName);// 页面title和用户名
        htmlContext = htmlContext.replaceAll("%USER_COMPANY%",companyName).replaceAll("%COMPANY_LOGO%",companyLogo);// 公司名称和logo
        htmlContext = htmlContext.replaceAll("%WINDOW_PERCENT%",windowsPercent);// pano窗口大小
        // 替换到xml文件中
        String xmlPath = "/" + this.getBusinessCode() + "/tour.xml";
        String xmlContext = FileUploadUtils.getFileContext(OUTPUT_PATH + xmlPath);
        xmlContext = xmlContext.replaceAll("%MUSIC_NAME%",musicName);
        htmlContext = htmlContext.replaceAll("%TOTAL_PRICE%",totalPrice.doubleValue()+"").replaceAll("%PRODUCT_COST%",costHtml.toString());

        // 重新写入文件
        FileUploadUtils.writeTxtFile(OUTPUT_PATH + htmlPath,htmlContext);
        FileUploadUtils.writeTxtFile(OUTPUT_PATH + xmlPath,xmlContext);
    }


    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskPicId() {
        return taskPicId;
    }

    public void setTaskPicId(Integer taskPicId) {
        this.taskPicId = taskPicId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getSmallResPicId() {
        return smallResPicId;
    }

    public void setSmallResPicId(Integer smallResPicId) {
        this.smallResPicId = smallResPicId;
    }

    public Integer getPanoLevel() {
        return panoLevel;
    }

    public void setPanoLevel(Integer panoLevel) {
        this.panoLevel = panoLevel;
    }
}
