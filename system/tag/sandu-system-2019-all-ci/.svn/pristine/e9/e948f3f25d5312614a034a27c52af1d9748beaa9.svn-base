package com.sandu.web.shop;

import com.sandu.annotation.DuplicateSubmitToken;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.company.common.FileUtils;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.output.ImgUploadVO;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.api.shop.common.constant.ShopResTypeConstant;
import com.sandu.api.shop.input.*;
import com.sandu.api.shop.model.CompanyShop;
import com.sandu.api.shop.output.CompanyShopDetailsVO;
import com.sandu.api.shop.output.CompanyShopVO;
import com.sandu.api.shop.output.CompanyTypeVO;
import com.sandu.api.shop.service.CompanyShopService;
import com.sandu.api.shopOffline.service.CompanyShopOfflineService;
import com.sandu.api.user.model.SysUser;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.commons.Utils;
import com.sandu.commons.constant.BusinessTypeConstant;
import com.sandu.commons.constant.SysDictionaryConstant;
import com.sandu.commons.util.StringUtils;
import com.sandu.constant.Constants;
import com.sandu.queue.SyncMessage;
import com.sandu.queue.service.QueueService;
import com.sandu.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 企业店铺-后台管理-控制层
 *
 * @auth xiaoxc
 * @data 2018-06-06
 */
@Api(tags = "companyShop", description = "店铺管理")
@RestController
@RequestMapping("/v1/company/shop")
public class CompanyShopAdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(CompanyShopAdminController.class);
    @Value("${upload.base.path}")
    private String rootPath;
    @Value("${company.shop.logoPic.upload.path}")
    private String logoPicUploadPath;
    @Value("${company.shop.coverPic.upload.path}")
    private String coverPicUploadPath;
    @Value("${company.shop.introducedPic.upload.path}")
    private String introducedPicUploadPath;
    @Autowired
    private CompanyShopService companyShopService;
    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private QueueService queueService;
    @Autowired
    private CompanyShopOfflineService companyShopOfflineService;


    @ApiOperation(value = "新增店铺", response = ResponseEnvelope.class)
    @DuplicateSubmitToken
    @PostMapping("/save")
    public ResponseEnvelope save(@Valid @RequestBody CompanyShopAdd shopAdd, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        Long franchiserId = shopAdd.getCompanyId();
        if (franchiserId == null || franchiserId <= 0) {
            return new ResponseEnvelope<>(false, "请先绑定企业，在添加店铺！");
        }
        if (shopAdd.getShopType() != null && shopAdd.getShopType() == 1) {
            // 校验企业店铺是否存在
            Integer count = companyShopService.checkCompanyShop(shopAdd.getCompanyId().intValue(), 1);
            if (count != null && 0 < count.intValue()) {
                return new ResponseEnvelope<>(false, "已有企业店铺！！！");
            }
        }
        // 校验店铺名称唯一性
        Integer count = companyShopService.checkShopName(shopAdd.getShopName(), null);
        if (count != null && 0 < count.intValue()) {
            return new ResponseEnvelope<>(false, "该店铺名已存在,请重新输入！");
        }

        //校验店铺名称是否与线下门店名称相同(只需校验未被认领的，已经认领的在店铺名称唯一性校验中已经校验)
        Integer count2 = companyShopOfflineService.checkShopName(shopAdd.getShopName());
        if (count2 != null && 0 < count2.intValue()) {
            return new ResponseEnvelope<>(false, "该店铺已存在，请在线下门店中认领或重新输入！");
        }

        //校验设计价格  add by WangHaiLin
        if (null != shopAdd.getDesignFeeStarting() && null != shopAdd.getDesignFeeEnding()) {
            if (shopAdd.getDesignFeeStarting() > shopAdd.getDesignFeeEnding()) {
                return new ResponseEnvelope<>(false, "设计最高价格 不能低于 设计最低价格！");
            }
        }
        // 新增数据
        int result = companyShopService.addShop(shopAdd, loginUser);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "创建成功!");
        } else {
            return new ResponseEnvelope<>(false, "创建失败!");
        }
    }

    @ApiOperation(value = "编辑店铺", response = ResponseEnvelope.class)
    @PutMapping("update")
    public ResponseEnvelope edit(@Valid @RequestBody CompanyShopUpdate shopUpdate, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        // 校验店铺名称唯一性
        Integer count = companyShopService.checkShopName(shopUpdate.getShopName(), shopUpdate.getShopId().longValue());
        if (count != null && 0 < count.intValue()) {
            return new ResponseEnvelope<>(false, "该店铺名已存在,请重新输入！");
        }

        //校验店铺名称是否与线下门店名称相同
        Integer count2 = companyShopOfflineService.checkShopName(shopUpdate.getShopName());
        if (count2 != null && 0 < count2.intValue()) {
            return new ResponseEnvelope<>(false, "该店铺已存在，请在线下门店中认领或重新输入!");
        }

        //校验设计价格  add by WangHaiLin
        if (null != shopUpdate.getDesignFeeStarting() && null != shopUpdate.getDesignFeeEnding()) {
            if (shopUpdate.getDesignFeeStarting() > shopUpdate.getDesignFeeEnding()) {
                return new ResponseEnvelope<>(false, "设计最高价格 不能低于 设计最低价格！");
            }
        }
        // 修改数据
        int result = companyShopService.updateShop(shopUpdate, loginUser);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "修改成功!");
        } else {
            return new ResponseEnvelope<>(false, "修改失败!");
        }
    }


    @ApiOperation(value = "删除店铺", response = ResponseEnvelope.class)
    @DeleteMapping("remove")
    public ResponseEnvelope remove(@RequestParam("shopId") Integer shopId) {
        // 参数验证
        if (shopId == null || shopId == 0) {
            return new ResponseEnvelope<>(false, "Param is empty！");
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        // 删除数据
        int result = 0;
        try {
            result = companyShopService.deleteShop(shopId, loginUser);
        } catch (SystemException e) {
            return new ResponseEnvelope(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseEnvelope(false, "系统异常");
        }
        if (result > 0) {
            List<Integer> idList = companyShopService.getShopDesignList(shopId, 0);
            if (idList != null && idList.size() > 0) {
                this.sycMessageDoSend(SyncMessage.ACTION_DELETE, idList, SyncMessage.PLAN_TYPE_RECOMMENDED);
            }
            return new ResponseEnvelope<>(true, "删除成功!");
        } else {
            return new ResponseEnvelope<>(false, "删除失败!");
        }
    }

    @ApiOperation(value = "店铺详情", response = ResponseEnvelope.class)
    @GetMapping("get")
    public ResponseEnvelope get(@RequestParam("shopId") Integer shopId) {
        // 参数验证
        if (shopId == null || shopId == 0) {
            return new ResponseEnvelope<>(false, "Param is empty！");
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        // 查询店铺详情
        CompanyShopDetailsVO detailsVO = companyShopService.getShopDetails(shopId);
        if (detailsVO != null) {
            return new ResponseEnvelope<>(true, detailsVO);
        } else {
            return new ResponseEnvelope<>(false, "查询异常!");
        }
    }

    @ApiOperation(value = "店铺列表", response = ResponseEnvelope.class)
    @PostMapping("list")
    public ResponseEnvelope list(@Valid @RequestBody CompanyShopQuery query, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        BaseCompany baseCompany = null;
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        } else {
            // 获取登录用户企业id
            Long franchiserId = query.getCompanyId();
            baseCompany = baseCompanyService.getCompanyById(franchiserId != null ? franchiserId : 0);
            if (baseCompany != null && 0 < baseCompany.getId()) {
                // 设置厂商查询店铺列表标志（和经销商条件不同）
                if (BusinessTypeConstant.BUSINESSTYPE_COMPANY == baseCompany.getBusinessType().intValue()) {
                    query.setCompanyType("true");
                    query.setUserId(null);
                }
            } else {
                logger.error("CompanyShopAdminController.list{}: userId=" + loginUser.getId() + ";companyId=" + franchiserId);
                return new ResponseEnvelope<>(false, "该用户企业信息为空！");
            }
            query.setCompanyId(franchiserId);
        }
        List<CompanyShopVO> shopVOList = new ArrayList<>();
        // 查询店铺数量
        logger.info("查询参数：companyId=" + query.getCompanyId());
        this.verifyCurrentUserIsAdminstartor(baseCompany.getAdminUserId(), loginUser.getId(), query);
        int total = companyShopService.getCount(query);
        if (total > 0) {
            // 查询店铺列表
            shopVOList = companyShopService.findList(query);
        }
        return new ResponseEnvelope(true, total, shopVOList);
    }

    private void verifyCurrentUserIsAdminstartor(Integer adminUserId, Integer userId, CompanyShopQuery query) {

        if (adminUserId != null && adminUserId.intValue() == userId.intValue()) {
            //用户为超级管理员,可以查看任意店铺
            query.setUserId(null);
            return;
        }
    }

    @SuppressWarnings("all")
    @ApiOperation(value = "上传店铺图片", response = ImgUploadVO.class)
    @PostMapping("/img/upload")
    public ResponseEnvelope uploadImg(@RequestParam("file") MultipartFile file, String type, Integer businessId) {
        // 参数验证
        if (file == null && StringUtils.isEmpty(type)) {
            return new ResponseEnvelope<>(false, "Param is empty！");
        }
        ImgUploadVO imgUploadVO = new ImgUploadVO();
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        String uploadPath = "";
        String picType = "";
        String uploadFileKey = "";
        if ("logo".equals(type)) {
            uploadPath = logoPicUploadPath;
            picType = ShopResTypeConstant.SHOP_RES_LOGO_PIC_TYPE;
            uploadFileKey = ShopResTypeConstant.SHOP_RES_LOGO_PIC_KEY;
        }
        if ("cover".equals(type)) {
            uploadPath = coverPicUploadPath;
            picType = ShopResTypeConstant.SHOP_RES_COVER_PIC_TYPE;
            uploadFileKey = ShopResTypeConstant.SHOP_RES_COVER_PIC_KEY;
        }
        if ("introduced".equals(type)) {
            uploadPath = introducedPicUploadPath;
            picType = ShopResTypeConstant.SHOP_RES_INTRODUCED_PIC_TYPE;
            uploadFileKey = ShopResTypeConstant.SHOP_RES_INTRODUCED_PIC_KEY;
        }
        if (StringUtils.isEmpty(uploadPath)) {
            return new ResponseEnvelope<>(false, "uploadPath is empty！");
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
        if (!("gif，jpg，jpeg，png".contains(suffix) || "GIF，JPG，JPEG，PNG".contains(suffix))) {
            return new ResponseEnvelope(false, "仅支持图片格式gif、jpg、jpeg、png");
        }
        if (size > 4 * 1024 * 1024) {
            return new ResponseEnvelope(false, "仅支持上传图片大小小于4M的图片");
        }
        try {
            uploadPath = StringUtil.replaceDate(uploadPath, null);
            File dir = new File(rootPath + uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //路径补全
            StringBuilder builder = new StringBuilder();
            builder.append(uploadPath);
            builder.append("/");
            builder.append(Utils.generateRandomDigitString(6));
            builder.append("_");
            builder.append(System.currentTimeMillis());
            builder.append("_1");
            builder.append(".");
            builder.append(suffix);
            uploadPath = builder.toString();

            //上传图片
            File picFile = new File(rootPath + uploadPath);
            file.transferTo(picFile);

            //获取上传文件信息
            Map<String, String> map = FileUtils.getMap(picFile, uploadFileKey, true);

            //2.新增resPic图片信息表
            Integer resId = resPicService.saveUploadImgPic(map, businessId, loginUser, picType);

            if (resId > 0) {
                imgUploadVO.setPicPath(uploadPath);
                imgUploadVO.setId(resId);
                return new ResponseEnvelope(true, "上传成功", imgUploadVO);
            } else {
                return new ResponseEnvelope(true, "上传失败", imgUploadVO);
            }
        } catch (Exception e) {
            logger.error("uploadImg 方法 系统异常", e);
            return new ResponseEnvelope(false, "系统异常", imgUploadVO);
        }
    }

    @ApiOperation(value = "品牌馆页面", response = ResponseEnvelope.class)
    @GetMapping("brandPavilion")
    public ResponseEnvelope brandPavilion(Long companyId) {
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        if (companyId == null || companyId == 0) {
            return new ResponseEnvelope<>(false, "用户没有企业信息！");
        }
        // 查询店铺详情
        CompanyShopDetailsVO detailsVO = companyShopService.findBrandPavilion(loginUser.getId(), companyId, SysDictionaryConstant.SHOP_TYPE_BRAND_PAVILION_VALUE);
        if (detailsVO != null) {
            return new ResponseEnvelope<>(true, detailsVO);
        } else {
            return new ResponseEnvelope<>(false, "查询异常!");
        }
    }

    @ApiOperation(value = "更新店铺显示状态", response = ResponseEnvelope.class)
    @PostMapping("updateDisplayStatus")
    public ResponseEnvelope edit(@RequestBody CompanyShopUpdate shopUpdate) {
        // 校验参数
        if (shopUpdate == null) {
            return new ResponseEnvelope<>(false, "shopUpdate is empty！");
        }
        Integer shopId = shopUpdate.getShopId();
        Integer displayStatus = shopUpdate.getDisplayStatus();
        if (shopId == null || displayStatus == null) {
            return new ResponseEnvelope<>(false, "Param is empty！");
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        // 设置参数
        CompanyShop companyShop = new CompanyShop();
        companyShop.setDisplayStatus(displayStatus);
        companyShop.setId(shopId);
        // 修改数据
        int result = companyShopService.updateCompanyShop(companyShop, loginUser);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "修改成功!");
        } else {
            return new ResponseEnvelope<>(false, "修改失败!");
        }
    }

    @ApiOperation(value = "获取企业类型", response = ResponseEnvelope.class)
    @PostMapping("/getCompanyType")
    public ResponseEnvelope getCompanyType(@RequestBody CompanyShopQuery query) {
        if (query == null || query.getCompanyId() == null) {
            return new ResponseEnvelope<>(false, "Param is empty!");
        }
        // 获取企业信息
        BaseCompany baseCompany = baseCompanyService.getCompanyById(query.getCompanyId());
        CompanyTypeVO vo = new CompanyTypeVO();
        if (baseCompany != null) {
            vo.setCompanyId(query.getCompanyId());
            vo.setCompanyType(baseCompany.getBusinessType());
            if (baseCompany.getBusinessType() == 1) {
                if (baseCompany.getAdminUserId() != null && baseCompany.getAdminUserId().intValue() == query.getUserId().intValue()) {
                    vo.setAmdinUser(1);
                }
            } else {
                vo.setAmdinUser(0);
            }
        }
        return new ResponseEnvelope<>(true, vo);
    }


    @ApiOperation(value = "设置拉黑店铺", response = ResponseEnvelope.class)
    @PostMapping("/setBlacklist")
    //@RequiresPermissions({"shop:blacklist"})
    public ResponseEnvelope setBlacklist(@RequestBody CompanyShopBlacklist setBlacklist) {
        CompanyShop shop = companyShopService.getShopById(setBlacklist.getShopId());
        if (null == shop) {
            return new ResponseEnvelope<>(false, "参数shopId无效！");
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        shop.setIsBlacklist(setBlacklist.getIsBlacklist());
        int result = companyShopService.updateCompanyShop(shop, loginUser);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "修改成功!");
        } else {
            return new ResponseEnvelope<>(false, "修改失败!");
        }
    }

    @ApiOperation(value = "获取该企业下的所有用户账号", response = ResponseEnvelope.class)
    @GetMapping("/companyUser")
    public ResponseEnvelope getCompanyUser(Integer companyId) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        List<SysUser> list = baseCompanyService.getCompanyUser(companyId);
        return new ResponseEnvelope(true, list);
    }

    @ApiOperation(value = "校验是否已有企业店铺", response = ResponseEnvelope.class)
    @GetMapping("/checkCompanyShop")
    public ResponseEnvelope getCheckCompanyShop(Integer companyId) {
        if (companyId == null || companyId == 0) {
            return new ResponseEnvelope(false, "参数缺失");
        }
        // 校验企业店铺是否存在
        Integer count = companyShopService.checkCompanyShop(companyId, 1);
        if (count != null && 0 < count.intValue()) {
            return new ResponseEnvelope<>(false, "已有企业店铺！！！");
        }
        return new ResponseEnvelope(true);
    }

    @ApiOperation(value = "个人店铺是否已经存在", response = ResponseEnvelope.class)
    @GetMapping("/isShopAlreadyExit")
    public ResponseEnvelope isShopAlreadyExit() {
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "从头部获取用户Id失败！");
        }
        List<CompanyShop> shopList = companyShopService.getShopListByUserId(loginUser.getId().longValue());
        List<CompanyShop> personalShop = new ArrayList<>();
        if (shopList.size() > 0) {
            shopList.forEach(shop -> {
                if (2 == shop.getShopType()) {
                    personalShop.add(shop);
                }
            });
            if (personalShop.size() > 0) {
                return new ResponseEnvelope<>(true, "个人店铺已经存在!", shopList.size());
            } else {
                return new ResponseEnvelope<>(false, "个人店铺不存在!");
            }
        } else {
            return new ResponseEnvelope<>(false, "个人店铺不存在!");
        }
    }

    @ApiOperation(value = "设置店铺发布", response = ResponseEnvelope.class)
    @PostMapping("/setRelease")
    public ResponseEnvelope setRelease(@RequestBody CompanyShopIsRelease isRelease) {
        //根据Id查询店铺信息
        CompanyShop shop = companyShopService.getShopById(isRelease.getShopId());
        if (null == shop) {
            return new ResponseEnvelope<>(false, "参数shopId有误！");
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        //修改发布状态
        int result = companyShopService.setRelease(shop, loginUser, isRelease.getIsRelease());
        if (result > 0) {
            List<Integer> idList = companyShopService.getShopDesignList(isRelease.getShopId(), null);
            if (idList != null && idList.size() > 0) {
                this.sycMessageDoSend(SyncMessage.ACTION_DELETE, idList, SyncMessage.PLAN_TYPE_RECOMMENDED);
            }
            return new ResponseEnvelope<>(true, "发布修改成功!", result);
        } else {
            return new ResponseEnvelope<>(false, "发布修改失败!");
        }
    }

    private void sycMessageDoSend(Integer messageAction, List<Integer> ids, Integer planType) {
        List<Map> content = ids.stream().map(item -> {
            HashMap<String, Integer> tmp = new HashMap<>(1);
            tmp.put("id", item);
            tmp.put("planTableType", planType);
            return tmp;
        }).collect(Collectors.toList());
        SyncMessage message = new SyncMessage();
        message.setAction(messageAction);
        message.setMessageId("P-" + System.currentTimeMillis());
        message.setModule(SyncMessage.MODULE_SOLUTION_RECOMMEND);
        message.setPlatformType(Constants.PLATFORM_CODE_MERCHANT_MANAGE);
        message.setObject(content);
        queueService.sendSyncSearch(message);
    }
}
