package com.sandu.web.supplydemand;


import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.base.model.BaseArea;
import com.sandu.base.service.BaseAreaService;
import com.sandu.common.LoginContext;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.BadWordUtil;
import com.sandu.common.util.EmojiUtil;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.node.constant.NodeInfoConstant;
import com.sandu.supplydemand.convert.BaseSupplyDemandConvert;
import com.sandu.supplydemand.input.BaseSupplyDemandAdd;
import com.sandu.supplydemand.model.BaseSupplyDemand;
import com.sandu.supplydemand.model.SupplyDemandPic;
import com.sandu.supplydemand.output.SupplyDemandCategoryVo;
import com.sandu.supplydemand.service.SupplyDemandCategoryService;
import com.sandu.supplydemand.service.SupplyDemandPicService;
import com.sandu.supplydemand.service.SupplyDemandService;
import com.sandu.system.service.NodeInfoBizService;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.UserRoleContants;
import com.sandu.user.model.UserVo;
import com.sandu.user.service.SysUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author weisheng
 * @Title:
 * @Package
 * @Description:
 * @date 2018/4/27 0027PM 5:37
 */
@Slf4j
@RestController
@RequestMapping("/v1/union/supplydemand")
public class SupplyDemandController {
    private final static String CLASS_LOG_PREFIX = "[供求基础服务]";


    private static Map<Integer, Long> validMap = new HashMap<>();

    private static Gson gson = new Gson();

    @Autowired
    private SupplyDemandService supplyDemandService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SupplyDemandPicService supplyDemandPicService;

    @Autowired
    private SupplyDemandCategoryService supplyDemandCategoryService;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private NodeInfoBizService nodeInfoBizService;

    /**
     * @Title: 供求信息列表
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/getallsupplydemandinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope getAllSupplyDemandInfo(@RequestBody BaseSupplyDemandAdd baseSupplyDemandAdd, HttpServletRequest request) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请先登录");
        }
        baseSupplyDemandAdd.setCreatorId(loginUser.getId());

        if (StringUtils.isEmpty(baseSupplyDemandAdd.getMark())) {
            //根据登录用户的角色对查询的供求信息范围进行过滤
            SysUser sysUser = sysUserService.get(loginUser.getId());
            if(null == sysUser){
                return new ResponseEnvelope(false, "该角色没用用户类型");
            }
            switch (sysUser.getUserType().intValue()) {
                //门店角色
                case UserRoleContants.DEALERS:
                case UserRoleContants.FIRM:
                case UserRoleContants.D_DEALERS:
                    baseSupplyDemandAdd.setMaterialShop(1);

                    break;

                //设计师
                case UserRoleContants.DESIGNER:
                    baseSupplyDemandAdd.setDesigner(1);
                    break;

                //装修公司
                case UserRoleContants.DECORATE_COMPANY:
                    baseSupplyDemandAdd.setDecorationCompany(1);
                    break;

                //设计公司
                case UserRoleContants.DESIGNER_COMPANY:
                    baseSupplyDemandAdd.setDesigner(1);
                    break;

                //业主,内部用户,学校
                case UserRoleContants.COMMON:
                case UserRoleContants.USER1:
                case UserRoleContants.SCHOOL:
                    baseSupplyDemandAdd.setProprietor(1);
                    break;

                //施工单位
                case UserRoleContants.FOREMAN:
                    baseSupplyDemandAdd.setBuilder(1);
                    break;


                default:
                    return new ResponseEnvelope(false, "该用户角色无法查看供求信息");

            }


        }

        if (StringUtils.isNotEmpty(baseSupplyDemandAdd.getPublisherType())) {
            List<Integer> creatorTypes = Utils.getIntegerListFromStringList(baseSupplyDemandAdd.getPublisherType(), ",");
            baseSupplyDemandAdd.setCreatorTypes(creatorTypes);
        }

        //当分类为一级分类,查询分类下的所有信息
        if(StringUtils.isNotBlank(baseSupplyDemandAdd.getSupplyDemandCategoryId())){
            String supplyDemandCategoryId = baseSupplyDemandAdd.getSupplyDemandCategoryId();
            List<Integer> categoryIdList = Utils.getIntegerListFromStringList(supplyDemandCategoryId, ",");
            if(categoryIdList!=null &&categoryIdList.size() ==1){
                List<String> supplyDemandCategoryIdList = supplyDemandService.getAllSupplyDemandCategoryByOneCategory(categoryIdList.get(0));
                baseSupplyDemandAdd.setSupplyDemandCategoryIdList(supplyDemandCategoryIdList);
            }
        }

        //处理多个街道过滤数据
        String street = baseSupplyDemandAdd.getStreet();
        if(StringUtils.isNotBlank(street)){
            List<String> streets = Utils.getListFromStr(street);
            baseSupplyDemandAdd.setStreets(streets);
        }

        // 如果是收藏帖子页面
        if (baseSupplyDemandAdd.getFromFavorite() != null && baseSupplyDemandAdd.getFromFavorite() == 1){
            List<Integer> supplyDemandIds = nodeInfoBizService.getContentIdList(loginUser.getId(),
                    NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_SUPPLY_DEMAND,
                    NodeInfoConstant.SYSTEM_DICTIONARY_DETAIL_TYPE_FAVORITE);
            if (supplyDemandIds == null || supplyDemandIds.size() <= 0){
                return new ResponseEnvelope(true,"没有收藏帖子!", null, 0);
            }
            baseSupplyDemandAdd.setSupplyDemandIds(supplyDemandIds);
        }

        //查询所有供求信息总数
        int total = supplyDemandService.getAllSupplyDemandCount(baseSupplyDemandAdd);
        if (total == 0) {
            return new ResponseEnvelope(false, "没有查询到供求信息");
        }
        //查询所有供求信息详情
        List<BaseSupplyDemand> baseSupplyDemandList = supplyDemandService.getAllSupplyDemandInfo(baseSupplyDemandAdd);
        baseSupplyDemandList = nodeInfoBizService.getNodeData(baseSupplyDemandList, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_SUPPLY_DEMAND, loginUser.getId());

        return new ResponseEnvelope(true, "", BaseSupplyDemandConvert.parseToBaseSupplyDemandVoList(baseSupplyDemandList),
                total);

    }


    /**
     * @Title: 发布供求信息
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/addallsupplydemandinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope addSupplyDemandInfo(@Valid @RequestBody BaseSupplyDemand baseSupplyDemand, HttpServletRequest request, BindingResult bindingResult) throws IOException {
        /*返回校验失败信息*/
        if (bindingResult.hasErrors()) {
            return new ResponseEnvelope(false, bindingResult.getFieldError().getDefaultMessage());
        }
         /*检查用户是否登录*/
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请先登录");
        }
        //校验该用户是否重复提交(一分钟同一个用户只能发布一条)
        Integer userId = loginUser.getId();
        if (userId > 0 && validMap.containsKey(userId)) {
            if (System.currentTimeMillis() - validMap.get(userId) - 60 * 1000 <= 0) {
                return new ResponseEnvelope(false, "同一用户一分钟只能发布一条供求信息");
            }
        }

          /*检查发布范围是否勾选*/
        if (baseSupplyDemand.getDecorationCompany() == 0 && baseSupplyDemand.getDesigner() == 0 &&
                baseSupplyDemand.getMaterialShop() == 0 && baseSupplyDemand.getProprietor() == 0 && baseSupplyDemand.getBuilder() == 0) {
            return new ResponseEnvelope(false, "必须选择一个发布范围");
        }

        //校验手机号是否正确
        if(StringUtils.isNotBlank(baseSupplyDemand.getPhone())){
            if(!Utils.isMobile(baseSupplyDemand.getPhone())){
                return new ResponseEnvelope(false,"该手机号不存在");
            }

        }

        //校验图片是否存在
        if (StringUtils.isNotBlank(baseSupplyDemand.getCoverPicId())) {
            List<Integer> coverPicIdList = Utils.getIntegerListFromStringList(baseSupplyDemand.getCoverPicId(), ",");
            if (coverPicIdList != null && coverPicIdList.size() > 0) {
                List<SupplyDemandPic> supplyDemandPicList = supplyDemandPicService.selectSupplyDemandPic(coverPicIdList);
                if (supplyDemandPicList == null || supplyDemandPicList.size() == 0 ) {
                    return new ResponseEnvelope(false, "上传图片不存在");
                }
            } else {
                return new ResponseEnvelope(false, "上传图片不存在");
            }
        }

        //校验省市区编码是否正确
        if(StringUtils.isNotBlank(baseSupplyDemand.getProvince()) && StringUtils.isNotBlank(baseSupplyDemand.getCity())
                && StringUtils.isNotBlank( baseSupplyDemand.getDistrict())){
            BaseArea province = baseAreaService.getBaseAreaByAreaCode(baseSupplyDemand.getProvince());
            BaseArea city = baseAreaService.getBaseAreaByAreaCode(baseSupplyDemand.getCity());
            BaseArea district = baseAreaService.getBaseAreaByAreaCode(baseSupplyDemand.getDistrict());
            if(province==null || city == null || district ==null ||
                    province.getLevelId() !=1 || city.getLevelId() !=2 || district.getLevelId() !=3 ){
                return new ResponseEnvelope(false, "区域编码不存在");
            }

            if(StringUtils.isNotBlank(baseSupplyDemand.getStreet())){
                BaseArea street = baseAreaService.getBaseAreaByAreaCode(baseSupplyDemand.getStreet());
                if(street == null || street.getLevelId() != 4){
                    return new ResponseEnvelope(false, "区域编码不存在");
                }
            }

        }

        //校验分类信息是否正确
        if (StringUtils.isNotBlank(baseSupplyDemand.getSupplyDemandCategoryId())) {
            List<Integer> categoryIdList = Utils.getIntegerListFromStringList(baseSupplyDemand.getSupplyDemandCategoryId(), ",");
            if (categoryIdList != null && categoryIdList.size() ==2) {
                List<SupplyDemandCategoryVo> supplyDemandCategoryVoList = supplyDemandCategoryService.selectCategoryName(categoryIdList);
                if(supplyDemandCategoryVoList ==null || supplyDemandCategoryVoList.size() ==0
                        || supplyDemandCategoryVoList.get(0).getLevel()!=1 || supplyDemandCategoryVoList.get(1).getLevel()!=2){
                    return new ResponseEnvelope(false, "上传分类信息不存在");
                }
            }else {
                return new ResponseEnvelope(false, "上传分类信息不存在");
            }
        }

        //校验两个内容字段不能同时为空
        if (StringUtils.isBlank(baseSupplyDemand.getDescription()) && StringUtils.isBlank(baseSupplyDemand.getContent())) {
            return new ResponseEnvelope(false, "信息内容不能为空");
        }

        //校验地址,标题,联系人,详情是否含有敏感词汇
        if(StringUtils.isNotBlank(baseSupplyDemand.getAddress())){
            boolean containtBadWord = BadWordUtil.isContaintBadWord(baseSupplyDemand.getAddress(), 2);
            if(containtBadWord){
                return new ResponseEnvelope(false,"详细地址含有敏感词汇");
            }
        }
        if(StringUtils.isNotBlank(baseSupplyDemand.getContact())){
            boolean containtBadWord = BadWordUtil.isContaintBadWord(baseSupplyDemand.getContact(), 2);
            if(containtBadWord){
                return new ResponseEnvelope(false,"联系人含有敏感词汇");
            }
        }
        if(StringUtils.isNotBlank(baseSupplyDemand.getTitle())){
            boolean containtBadWord = BadWordUtil.isContaintBadWord(baseSupplyDemand.getTitle(), 2);
            if(containtBadWord){
                return new ResponseEnvelope(false,"标题含有敏感词汇");
            }
        }
        if(StringUtils.isNotBlank(baseSupplyDemand.getDescription())){
            boolean containtBadWord = BadWordUtil.isContaintBadWord(baseSupplyDemand.getDescription(), 2);
            if(containtBadWord){
                baseSupplyDemand.setDescription(BadWordUtil.replaceBadWord(baseSupplyDemand.getDescription(),2,"*"));
            }
        }


        UserVo userInfo = sysUserService.getUserInfo(userId);
        if(userInfo!=null){
            String userName = userInfo.getUserName();
            String nickName = userInfo.getNickName();
            if(StringUtils.isEmpty(userName) && StringUtils.isNotBlank(nickName) && Utils.isMobile(nickName)){
                baseSupplyDemand.setContact(nickName.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
            }

            if(StringUtils.isEmpty(userName) && StringUtils.isNotBlank(nickName) && !Utils.isMobile(nickName)){
                baseSupplyDemand.setContact(nickName);
            }

            if(StringUtils.isNotBlank(userName) && Utils.isMobile(userName)){
                baseSupplyDemand.setContact(userName.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
            }
            if(StringUtils.isNotBlank(userName) && !Utils.isMobile(userName)){
                baseSupplyDemand.setContact(userName);
            }

        }

        baseSupplyDemand.setCreator(loginUser.getName());
        baseSupplyDemand.setCreatorId(loginUser.getId());
        baseSupplyDemand.setModifier(loginUser.getName());
        baseSupplyDemand.setCreatorTypeValue(loginUser.getUserType());
        int supplyDemandId = supplyDemandService.addSupplyDemandInfo(baseSupplyDemand);
        if (supplyDemandId == 0) {
            return new ResponseEnvelope(false, "新增供求信息失败");
        }
        //将用户发布信息的时间存入静态变量中.
        if (userId > 0) {
            validMap.put(loginUser.getId(), System.currentTimeMillis());
        }

        return new ResponseEnvelope(true, "新增供求信息成功", supplyDemandId);


    }


    /**
     * @Title: 编辑供求信息
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/updatemysupplydemandinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope updateMySupplyDemandInfo(@RequestBody BaseSupplyDemand baseSupplyDemand, HttpServletRequest request) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请先登录");
        }
        baseSupplyDemand.setModifier(loginUser.getName());
        if(null != baseSupplyDemand.getPlanId() && baseSupplyDemand.getPlanId()>0){
            if(baseSupplyDemand.getPlanType() ==null || baseSupplyDemand.getPlanType() ==0 ){
                log.warn(CLASS_LOG_PREFIX+"方案ID和方案类型必须同时传入"+gson.toJson(baseSupplyDemand));
                return new ResponseEnvelope(false,"缺失参数PlanType或PlanId");
            }
        }


        if (null == baseSupplyDemand.getId() || baseSupplyDemand.getId() == 0) {
            return new ResponseEnvelope(false, "缺少参数供求信息ID");
        }

        //校验该供求信息是否存在
        BaseSupplyDemand supplyDemandInfo = supplyDemandService.getSupplyDemandInfo(baseSupplyDemand.getId());
        if(supplyDemandInfo==null){
            return new ResponseEnvelope(false, "这条供求信息不存在");
        }

        //校验图片是否存在
        if (StringUtils.isNotBlank(baseSupplyDemand.getCoverPicId())) {
            List<Integer> coverPicIdList = Utils.getIntegerListFromStringList(baseSupplyDemand.getCoverPicId(), ",");
            if (coverPicIdList != null && coverPicIdList.size() > 0) {
                List<SupplyDemandPic> supplyDemandPicList = supplyDemandPicService.selectSupplyDemandPic(coverPicIdList);
                if (supplyDemandPicList == null || supplyDemandPicList.size() == 0 ) {
                    return new ResponseEnvelope(false, "上传图片不存在");
                }
            } else {
                return new ResponseEnvelope(false, "上传图片不存在");
            }
        }


        //校验分类信息是否正确
        if (StringUtils.isNotBlank(baseSupplyDemand.getSupplyDemandCategoryId())) {
            List<Integer> categoryIdList = Utils.getIntegerListFromStringList(baseSupplyDemand.getSupplyDemandCategoryId(), ",");
            if (categoryIdList != null && categoryIdList.size() ==2) {
                List<SupplyDemandCategoryVo> supplyDemandCategoryVoList = supplyDemandCategoryService.selectCategoryName(categoryIdList);
                if(supplyDemandCategoryVoList ==null || supplyDemandCategoryVoList.size() ==0
                        || supplyDemandCategoryVoList.get(0).getLevel()!=1 || supplyDemandCategoryVoList.get(1).getLevel()!=2){
                    return new ResponseEnvelope(false, "上传分类信息不存在");
                }
            }else {
                return new ResponseEnvelope(false, "上传分类信息不存在");
            }
        }

        //校验敏感词汇
        if(StringUtils.isNotBlank(baseSupplyDemand.getAddress())){
            boolean containtBadWord = BadWordUtil.isContaintBadWord(baseSupplyDemand.getAddress(), 2);
            if(containtBadWord){
                return new ResponseEnvelope(false,"详细地址含有敏感词汇");
            }
        }
        if(StringUtils.isNotBlank(baseSupplyDemand.getContact())){
            boolean containtBadWord = BadWordUtil.isContaintBadWord(baseSupplyDemand.getContact(), 2);
            if(containtBadWord){
                return new ResponseEnvelope(false,"联系人含有敏感词汇");
            }
        }
        if(StringUtils.isNotBlank(baseSupplyDemand.getTitle())){
            boolean containtBadWord = BadWordUtil.isContaintBadWord(baseSupplyDemand.getTitle(), 2);
            if(containtBadWord){
                return new ResponseEnvelope(false,"标题含有敏感词汇");
            }
        }
        if(StringUtils.isNotBlank(baseSupplyDemand.getDescription())){
            boolean containtBadWord = BadWordUtil.isContaintBadWord(baseSupplyDemand.getDescription(), 2);
            if(containtBadWord){
                baseSupplyDemand.setDescription(BadWordUtil.replaceBadWord(baseSupplyDemand.getDescription(),2,"*"));
            }
        }

        UserVo userInfo = sysUserService.getUserInfo(loginUser.getId());
        if(userInfo!=null){
            String userName = userInfo.getUserName();
            String nickName = userInfo.getNickName();
            if(StringUtils.isEmpty(userName) && StringUtils.isNotBlank(nickName) && Utils.isMobile(nickName)){
                baseSupplyDemand.setContact(nickName.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
            }

            if(StringUtils.isEmpty(userName) && StringUtils.isNotBlank(nickName) && !Utils.isMobile(nickName)){
                baseSupplyDemand.setContact(nickName);
            }

            if(StringUtils.isNotBlank(userName) && Utils.isMobile(userName)){
                baseSupplyDemand.setContact(userName.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
            }
            if(StringUtils.isNotBlank(userName) && !Utils.isMobile(userName)){
                baseSupplyDemand.setContact(userName);
            }

        }

        //处理表情
        if(StringUtils.isNotBlank(baseSupplyDemand.getDescription())){
            String newDescription = EmojiUtil.emojiConverterToAlias(baseSupplyDemand.getDescription());
            baseSupplyDemand.setDescription(newDescription);
        }
        if(StringUtils.isNotBlank(baseSupplyDemand.getTitle())){
            String newTitle = EmojiUtil.emojiConverterToAlias(baseSupplyDemand.getTitle());
            baseSupplyDemand.setTitle(newTitle);
        }

        String platformCode = request.getHeader("Platform-Code");
        if ("miniProgram".equals(platformCode)){
            int i = supplyDemandService.updateSupplyDemandInfoInMiniProgram(baseSupplyDemand);
            if (i <= 0){
                return new ResponseEnvelope(false, "供求信息修改失败");
            }
            return new ResponseEnvelope(true, "供求信息修改成功");
        }
        int supplyDemandId = supplyDemandService.updateSupplyDemandInfo(baseSupplyDemand);
        if (supplyDemandId == 0) {
            return new ResponseEnvelope(false, "供求信息修改失败");
        }
        return new ResponseEnvelope(true, "供求信息修改成功", supplyDemandId);
    }


    /**
     * @Title: 供求信息上下架
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/updatemysupplydemandstatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope updateMySupplyDemandStatus(@RequestBody BaseSupplyDemandAdd baseSupplyDemandAdd, HttpServletRequest request) {
       LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser==null){
            return new ResponseEnvelope(false,"请先登录");
        }
        baseSupplyDemandAdd.setCreatorId(loginUser.getId());
        if (baseSupplyDemandAdd.getSupplyDemandId() == null || baseSupplyDemandAdd.getSupplyDemandId() == 0) {
            return new ResponseEnvelope(false, "缺失参数SupplyDemandId");
        }
        //(0:上架中,1:已下架)
        if (baseSupplyDemandAdd.getPushStatus() == null) {
            return new ResponseEnvelope(false, "缺失参数pushStatus");
        }
        BaseSupplyDemand supplyDemandInfo = supplyDemandService.getSupplyDemandInfo(baseSupplyDemandAdd.getSupplyDemandId());
        if (supplyDemandInfo == null) {
            return new ResponseEnvelope(false, "未知的供求信息");

        }
        String statusStr = baseSupplyDemandAdd.getPushStatus() == 0 ? "上架" : "下架"+"成功";
        if (supplyDemandInfo.getPushStatus().intValue() == baseSupplyDemandAdd.getPushStatus().intValue()) {
            return new ResponseEnvelope(false, "这条供求信息已经" + statusStr);
        }
        //修改供求信息数据
        Integer statusId = supplyDemandService.updateMySupplyDemandStatus(baseSupplyDemandAdd);
        if (statusId == 0) {
            return new ResponseEnvelope(false, statusStr);
        }

        return new ResponseEnvelope(true, statusStr);

    }


    /**
     * @Title: 记录供求信息浏览次数
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/addsupplydemandviewnum", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "供求信息浏览", response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "供求信息ID", required = true, paramType = "query", dataType = "int")
    })
    public ResponseEnvelope addSupplyDemandViewNum(@RequestBody BaseSupplyDemand baseSupplyDemand, HttpServletRequest request) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请先登录");
        }
        if (baseSupplyDemand.getId() == null || baseSupplyDemand.getId() == 0) {
            return new ResponseEnvelope(false, "缺失参数Id");
        }
        BaseSupplyDemand supplyDemandInfo = supplyDemandService.getSupplyDemandInfo(baseSupplyDemand.getId());
        if (supplyDemandInfo == null) {
            return new ResponseEnvelope(false, "未查询到改供求信息");
        }
        Integer viewNum = supplyDemandInfo.getViewNum();
        viewNum++;
        BaseSupplyDemand newBaseSupplyDemand = new BaseSupplyDemand();
        newBaseSupplyDemand.setId(supplyDemandInfo.getId());
        newBaseSupplyDemand.setViewNum(viewNum);
        Integer supplyDemandId = supplyDemandService.updateSupplyDemandInfoViewNum(newBaseSupplyDemand);
        if (supplyDemandId == 0) {
            new ResponseEnvelope(false, "浏览失败");
        }
        return new ResponseEnvelope(true, "浏览成功", viewNum);
    }

    /**
     * @Title: 通过供求信息ID查询详情
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */

    @RequestMapping(value = "/getsupplydemandinfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getSupplyDemandInfo(@RequestParam(required = true) Integer supplyDemandId, HttpServletRequest request) {
        BaseSupplyDemand baseSupplyDemand = supplyDemandService.getSupplyDemandInfo(supplyDemandId);

        if (baseSupplyDemand == null) {
            return new ResponseEnvelope(false, "没有获取到该供求信息");
        }


        return new ResponseEnvelope(true, "", BaseSupplyDemandConvert.parseToBaseSupplyDemandVo(baseSupplyDemand));
    }






}
