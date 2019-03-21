package com.sandu.web.company;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.category.service.ProCategoryService;
import com.sandu.api.company.input.BaseCompanyNonUpdate;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.commons.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sandu.api.company.common.FileUtils;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.input.BaseCompanyUpdate;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.output.BaseCompanyDetailsVO;
import com.sandu.api.company.output.CompanyFranchiserVO;
import com.sandu.api.company.output.ImgUploadVO;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.company.service.biz.BaseCompanyBizService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.manage.SysUserManageService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author chenqiang
 * @Description 企业 控制层
 * @Date 2018/6/1 0001 10:13
 * @Modified By
 */
@Api(tags = "baseManufacturer", description = "企业厂商")
@RestController
@RequestMapping("/v1/base/manufacturer")
public class BaseManufacturerController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(BaseManufacturerController.class);

    @Value("${upload.base.path}")
    private String rootPath;
    @Value("${company.baseCompany.pic.upload.path}")
    private String uploadPath;

    @Autowired
    private BaseCompanyBizService baseCompanyBizService;

    @Autowired
    private BaseCompanyService baseCompanyService;
    
    @Resource
    private SysUserManageService sysUserManageService;

    @Autowired
    private ResPicService resPicService;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private ProCategoryService proCategoryService;

    @Autowired
    private DictionaryService dictionaryService;

    @ApiOperation(value = "企业经销商列表", response = CompanyFranchiserVO.class)
    @GetMapping("/franchiser/list")
    public ResponseEnvelope getFranchiserListByCompany(HttpServletRequest request, HttpServletResponse response,
                                                       @RequestParam("companyId")Long companyId) {

        try {
            //数据校验
            if(null == companyId)
                return new ResponseEnvelope<>(false, "企业id不能为空!");

            //获取list数据
            List<CompanyFranchiserVO> franchiserVOList = baseCompanyBizService.getFranchiserListByCompany(companyId);

            //数据返回
            return new ResponseEnvelope<>(true, franchiserVOList.size(), franchiserVOList);

        } catch (Exception e) {

            logger.error("getFranchiserListByCompany 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }
    
    @ApiOperation(value = "通过账号和密码查询企业经销商列表", response = CompanyFranchiserVO.class)
    @PostMapping("/franchiser/getList")
    public ResponseEnvelope getFranchiserList(HttpServletRequest request, HttpServletResponse response,String account,String password,Long companyId,String msgId) {

        try {
            //数据校验
            if(StringUtils.isEmpty(account)||StringUtils.isEmpty(password))
                return new ResponseEnvelope<>(false, "参数有误!",msgId);

        	SysUser user = sysUserManageService.get2BUser(account, password,companyId);
        	if(user==null) {
        		return new ResponseEnvelope<>(false, "账号不存在!",msgId);
        	}
        	if(user.getCompanyId()==null) {
        		return new ResponseEnvelope<>(false, "用户企业为空!",msgId);
        	}
        		
            //获取list数据
            List<CompanyFranchiserVO> franchiserVOList = baseCompanyBizService.getFranchiserList(user.getCompanyId());

            if(null == franchiserVOList)
            	 return new ResponseEnvelope<>(true, 0, null,user.getCompanyId(),msgId);
            else
                return new ResponseEnvelope<>(true, franchiserVOList.size(), franchiserVOList,user.getCompanyId(),msgId);

        } catch (Exception e) {
            logger.error("getFranchiserList 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @ApiOperation(value = "企业详细信息", response = BaseCompanyDetailsVO.class)
    @GetMapping("/details")
    public ResponseEnvelope getCompanyInfoDetails(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam("companyId")Long companyId) {
        try {
            //数据校验
            if(null == companyId)
                return new ResponseEnvelope<>(false, "企业id不能为空!");

            //获取企业详细数据
            BaseCompanyDetailsVO baseCompanyDetailsVO = baseCompanyBizService.getCompanyInfoDetails(companyId);

            return new ResponseEnvelope<>(true, "企业详细信息获取成功", baseCompanyDetailsVO);

        } catch (Exception e) {

            logger.error("getFranchiserListByCompany 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @ApiOperation(value = "修改厂商信息", response = ResponseEnvelope.class)
    @PutMapping
    public ResponseEnvelope update(HttpServletRequest request, HttpServletResponse response,
                                   @Valid @RequestBody BaseCompanyUpdate companyUpdate, BindingResult validResult) {
        try {
            //数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }

            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

            //数据转换
            BaseCompany baseCompany = companyUpdate.getBaseCompany();

            /******** 数据校验 ********/
            //企业名称唯一校验
            int count = baseCompanyService.checkCompanyName(baseCompany.getCompanyName(),new Long(baseCompany.getId()));
            if(count > 0){
                return new ResponseEnvelope<>(false, "存在相同的企业名称");
            }
            //区域校验
            Map<String,String> map = baseAreaService.checkAreaCode(baseCompany.getProvinceCode(),baseCompany.getCityCode(),baseCompany.getAreaCode(),baseCompany.getStreetCode());
            if(map.get("success").equals("false"))
                return new ResponseEnvelope<>(false, map.get("message"));
            //产品分类校验
            if(StringUtils.isNotEmpty(baseCompany.getProductVisibilityRange())){
                boolean bool = proCategoryService.checkCategoryIds(baseCompany.getProductVisibilityRange(),"3");
                if(!bool)
                    return new ResponseEnvelope<>(false, "可见产品范围有误");
            }
            //号码格式校验
            if(StringUtils.isNotEmpty(baseCompany.getPhone())){
                boolean bool = StringUtil.checkPhoneRegex(baseCompany.getPhone(),"1");
                if(!bool)
                    return new ResponseEnvelope<>(false, "号码格式错误");
            }
            /**add by wanghl**/
            //企业所属行业校验
            /*if (StringUtils.isNotBlank(baseCompany.getCompanyIndustrys())){
                List<Integer> valueList = StringUtil.getListByString(baseCompany.getCompanyIndustrys());
                List<DictionaryTypeListVO> industrys = dictionaryService.getListByTypeOrValues("industry", valueList);
                if (valueList.size()!=industrys.size()){
                    return new ResponseEnvelope<>(false, "所属行业参数有误");
                }
            }*/

            //log判断
            boolean boolLogo = false;
            if(null == baseCompany.getCompanyLogo())
                boolLogo = true;

            //修改厂商
            boolean bool = baseCompanyBizService.updateComPanyDetails(baseCompany,loginUser,boolLogo);

            if(bool)
                return new ResponseEnvelope<>(true, "修改厂商成功");
            else
                return new ResponseEnvelope<>(false, "修改厂商失败");

        } catch (Exception e) {

            logger.error("update 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }
    @ApiOperation(value = "修改非厂商企业信息", response = ResponseEnvelope.class)
    @PutMapping("/non/update")
    public ResponseEnvelope updateISC(HttpServletRequest request, HttpServletResponse response,
                                      @Valid @RequestBody BaseCompanyNonUpdate companyUpdate, BindingResult validResult) {
        try {
            //数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }

            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

            //数据转换
            BaseCompany baseCompany = companyUpdate.getBaseCompany();

            /******** 数据校验 ********/
            //企业名称唯一校验
            int count = baseCompanyService.checkCompanyName(baseCompany.getCompanyName(),baseCompany.getId());
            if(count > 0){
                return new ResponseEnvelope<>(false, "存在相同的企业名称");
            }
            //区域校验
            Map<String,String> map = baseAreaService.checkAreaCode(baseCompany.getProvinceCode(),baseCompany.getCityCode(),baseCompany.getAreaCode(),baseCompany.getStreetCode());
            if(map.get("success").equals("false"))
                return new ResponseEnvelope<>(false, map.get("message"));
            //号码格式校验
            if(StringUtils.isNotEmpty(baseCompany.getPhone())){
                boolean bool = StringUtil.checkPhoneRegex(baseCompany.getPhone(),"1");
                if(!bool)
                    return new ResponseEnvelope<>(false, "号码格式错误");
            }

            //log判断
            boolean boolLogo = false;
            if(null == baseCompany.getCompanyLogo())
                boolLogo = true;

            //修改厂商
            boolean bool = baseCompanyBizService.updateComPanyNonDetails(baseCompany,loginUser,boolLogo);

            if(bool)
                return new ResponseEnvelope<>(true, "修改非厂商企业成功");
            else
                return new ResponseEnvelope<>(false, "修改非厂商企业失败");

        } catch (Exception e) {

            logger.error("updateISC 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }


    @ApiOperation(value = "上传图片", response = ImgUploadVO.class)
    @PostMapping("/img/upload")
    public ResponseEnvelope uploadImg(@RequestParam("file") MultipartFile file) {
        ImgUploadVO imgUploadVO = new ImgUploadVO();

        try {

            //流验证
            if (file.isEmpty() || StringUtils.isBlank(file.getOriginalFilename())) {
                return new ResponseEnvelope(false, "流为空", imgUploadVO);
            }

            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

            //补全路径
            uploadPath = StringUtil.replaceDate(uploadPath, null);

            //创建流与验证
            File dir = new File(rootPath + uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            /** 获取上传图片信息 */
            //上传文件名
            String oldName = file.getOriginalFilename();
            //上传文件名
            String filename = StringUtils.substringBeforeLast(oldName, ".");
            //后缀
            String suffix = StringUtils.substringAfterLast(oldName, ".");
            //大小
            Long size = file.getSize();

            /** 校验 */
            if (!("gif，jpg，jpeg，png".contains(suffix)||"GIF，JPG，JPEG，PNG".contains(suffix))) {
                return new ResponseEnvelope(false, "仅支持图片格式gif、jpg、jpeg、png");
            }
            if (size > 500 * 1024) {
                return new ResponseEnvelope(false, "仅支持上传图片大小500kb");
            }

            //路径补全
            String filePath = "";
            StringBuffer sb = new StringBuffer();
            sb.append(uploadPath);
            sb.append("/");
            sb.append(Utils.generateRandomDigitString(6));
            sb.append("_");
            sb.append(System.currentTimeMillis());
            sb.append(".");
            sb.append(suffix);
            filePath = sb.toString();

            //上传图片
            File picFile = new File(rootPath+filePath);
            file.transferTo(picFile);

            //获取上传文件信息
            Map<String, String> map = FileUtils.getMap(picFile,"company.baseCompany.pic.upload.path",true);

            //新增resPic图片信息表
            Integer resPicId = resPicService.saveUploadImgPic(map,null,loginUser,"企业loggo图片");

            if(resPicId > 0) {
                imgUploadVO.setPicPath(filePath);
                imgUploadVO.setId(resPicId);
                return new ResponseEnvelope(true, "新增企业logo成功", imgUploadVO);
            }else{
                return new ResponseEnvelope(false, "新增企业logo失败", imgUploadVO);
            }
        } catch (Exception e) {

            logger.error("uploadImg 方法 系统异常", e);
            return new ResponseEnvelope(false, "系统异常", imgUploadVO);
        }
    }

    @ApiOperation(value = "删除厂商logo", response = ResponseEnvelope.class)
    @DeleteMapping("/logo")
    public ResponseEnvelope deleteCompanyLogo(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(value = "picIds") String picIds,@RequestParam("companyId") Integer companyId) {
        try {
            //数据校验
            if(StringUtils.isEmpty(picIds))
                return new ResponseEnvelope<>(false, "图片ids不能为空");
            if(null == companyId)
                return new ResponseEnvelope(true, "企业id不能为空");

            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

            //删除企业logo图片
            boolean bool = resPicService.deletePic(picIds,loginUser);

            //判断返回
            if(bool)
                return new ResponseEnvelope<>(true, "企业logo删除成功");
            else
                return new ResponseEnvelope<>(false, "企业logo删除失败");

        } catch (Exception e) {

            logger.error("deleteCompanyLogo 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }
}