package com.sandu.service.companyshop.impl.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.company.model.Company;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.companyshop.input.CompanyShopOfflineClaim;
import com.sandu.api.companyshop.input.CompanyshopofflineAdd;
import com.sandu.api.companyshop.input.CompanyshopofflineQuery;
import com.sandu.api.companyshop.input.CompanyshopofflineUpdate;
import com.sandu.api.companyshop.model.Companyshop;
import com.sandu.api.companyshop.model.Companyshopoffline;
import com.sandu.api.companyshop.service.CompanyshopService;
import com.sandu.api.companyshop.service.CompanyshopofflineService;
import com.sandu.api.companyshop.service.biz.CompanyshopofflineBizService;
import com.sandu.api.shop.common.constant.ShopCodePrefixEnum;
import com.sandu.api.storage.model.ResFile;
import com.sandu.api.storage.model.ResPic;
import com.sandu.api.storage.service.ResFileService;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.model.User;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.ReturnData;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.commons.constant.SysDictionaryConstant;
import com.sandu.constant.Punctuation;
import com.sandu.service.user.dao.UserDao;
import com.sandu.util.CodeUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.sandu.constant.Punctuation.DOT;
import static com.sandu.constant.ResponseEnum.ERROR;
import static com.sandu.constant.ResponseEnum.SUCCESS;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/11/29 14:04
 * @since 1.8
 */

@Slf4j
@Service("companyshopofflineBizService")
public class CompanyshopofflineBizServiceImpl implements CompanyshopofflineBizService {

    @Autowired
    private CompanyshopofflineService companyshopofflineService;

    @Resource
    private ResPicService resPicService;

    @Resource
    private BaseAreaService baseAreaService;

    @Resource
    CompanyshopService companyshopService;

    @Resource
    SysUserService sysUserService;

    @Resource
    CompanyService companyService;

    @Resource
    private ResFileService resFileServiceImpl;

    @Resource
    private UserDao userDao;

    @Value("${upload.base.path}")
    private String rootPath;
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(30);


    final int AGENCY = 2;

    @Override
    public int create(CompanyshopofflineAdd input) {
        Companyshopoffline.CompanyshopofflineBuilder builder = Companyshopoffline.builder();

        Companyshopoffline companyshopoffline = builder.build();
        BeanUtils.copyProperties(input, companyshopoffline);

        User creator = userDao.selectById(Long.valueOf(input.getCreator()));
        companyshopoffline.setCreator(creator.getUserName());
        companyshopoffline.setLongAreaCode(this.setLongAreaCode(companyshopoffline));

        // 存储店铺介绍富文本文件
        String content = input.getShopIntroduced();
        if (StringUtils.isNotEmpty(content)) {
            LoginUser loginUser = new LoginUser();
            loginUser.setUserName(creator.getUserName());
            Integer resId = resFileServiceImpl.saveFile("/AA/d_companyShop/[yyyy]/[MM]/[dd]/[HH]/company/shop/introducedFile/",
                    content, loginUser, null,
                    "company.shop.introducedFile.upload.path",
                    "店铺介绍文本");
            if (resId == null || resId == 0) {
                log.error("CompanyShopServiceImpl.addShop{}:保存配置文件失败");
                return 0;
            } else {
                companyshopoffline.setIntroducedFileId(resId);
            }
        }
        return companyshopofflineService.insert(companyshopoffline);
    }

    @Override
    public Companyshopoffline query(Long id) {
        return null;
    }

    @Override
    public PageInfo<Companyshopoffline> listShopOffline(CompanyshopofflineQuery query) {
        PageInfo<Companyshopoffline> pageInfo = companyshopofflineService.listShopOffline(query);
        if (pageInfo == null || pageInfo.getTotal() <= 0
                || pageInfo.getList() == null || pageInfo.getList().isEmpty()) {
            log.info("通过没有检索到数据, query => {}", query);
            return null;
        }

        // 获取所有店铺的logo图片
        List<Integer> listLogoPic = pageInfo.getList().stream().map(Companyshopoffline::getLogoPicId).collect(Collectors.toList());
        Map<Integer, String> mapLogoPic = resPicService.idAndPathMap(listLogoPic);

        // 数据量大时(防止SB每次每页查询500行数据)，可以降低对DB的查询次数(作用有限)！！
        Map<String, String> shopAreaCache = new HashMap<>();

        for (Companyshopoffline shopOffline : pageInfo.getList()) {
            // 图片处理
            if (shopOffline.getLogoPicId() != null && mapLogoPic.get(shopOffline.getLogoPicId()) != null) {
                shopOffline.setLogoPicPath(mapLogoPic.get(shopOffline.getLogoPicId()));
            }

            // 店铺区域
            shopOffline.setLongAreaName(getShopArea(shopAreaCache, Lists.newArrayList(shopOffline.getProvinceCode(),
                    shopOffline.getCityCode(), shopOffline.getAreaCode(), shopOffline.getStreetCode())));
        }

        return pageInfo;
    }

    /**
     * 数据量大时(防止SB每次每页查询500行数据)，可以降低对DB的查询次数(作用有限)！！
     *
     * @param shopAreaCache
     * @param codes
     * @return
     */
    private String getShopArea(Map<String, String> shopAreaCache, List<String> codes) {
        String key = Joiner.on("@").join(codes);
        if (shopAreaCache.containsKey(key)) {
            return shopAreaCache.get(key);
        }

        String areaNames = baseAreaService.getAreaNames(codes);
        areaNames = StringUtils.isEmpty(areaNames) ? areaNames : areaNames.replaceAll(",", "-");
        shopAreaCache.put(key, areaNames);

        return areaNames;
    }

    @Override
    @Transactional
    public ReturnData claimShop(CompanyShopOfflineClaim claim) {
        // 店铺是否存在
        Companyshopoffline shopOffline = companyshopofflineService.getById(claim.getShopOfflineId().intValue());
        if (shopOffline == null || shopOffline.getIsDeleted() == 1) {
            log.error("数据异常：认领的店铺(shopOfflineId => {})不存在或被删除， claim => {}", claim.getShopOfflineId(), claim);
            return ReturnData.builder().code(ERROR).success(false).message("认领的店铺不存在或被删除");
        }

        // 店铺是否被其他人认证
        Integer hasCount = companyshopService.checkHasOfflineShop(claim.getShopOfflineId());
        if (Objects.equals(shopOffline.getClaimStatus(), Companyshopoffline.ClaimStatus.CLAIMED.getStatus())
                || hasCount >= 1) {
            log.error("数据异常：店铺(shopOfflineId => {}) 已被认领， claim => {}", claim.getShopOfflineId(), claim);
            return ReturnData.builder().code(ERROR).success(false).message(shopOffline.getShopName() + "店铺已被认领!!!");
        }

        // 认领人是否存在
        SysUser sysUser = sysUserService.getUserById(claim.getUserId().longValue());
        if (sysUser == null || sysUser.getBusinessAdministrationId() == null) {
            return ReturnData.builder().code(ERROR).success(false).message("找不到认领人的用户信息");
        }

        // 认领人是否已有店铺
        Integer hasCount2 = companyshopService.checkHasOfflineShop2(claim.getUserId().longValue());
        if (hasCount2 >= 1) {
            log.error("数据异常：经销商(userId => {}) 已认领过店铺已被认领， claim => {}", claim.getUserId(), claim);
            return ReturnData.builder().code(ERROR).success(false).message("一个账号只能认领一个店铺，您已经有对应的店铺，无法进行认领！");
        }

        // 认领人是否经销商
        Company company = companyService.getCompanyById(sysUser.getBusinessAdministrationId());
        if (company == null || company.getBusinessType() != AGENCY) {
            return ReturnData.builder().code(ERROR).success(false).message("认领人不是经销商用户，不能认领店铺");
        }

        // 添加店铺
        this.addShop(claim, shopOffline, sysUser);

        // update company shop offline status
        Companyshopoffline shopOfflineUpdate = new Companyshopoffline();
        shopOfflineUpdate.setId(claim.getShopOfflineId().intValue());
        shopOfflineUpdate.setClaimTime(new Date());
        shopOfflineUpdate.setClaimCompanyId(sysUser.getBusinessAdministrationId().intValue());
        shopOfflineUpdate.setClaimStatus(Companyshopoffline.ClaimStatus.CLAIMED.getStatus());
        shopOfflineUpdate.setClaimUserId(claim.getUserId());
        companyshopofflineService.update(shopOfflineUpdate);

        return ReturnData.builder().code(SUCCESS).success(true).message("认领成功");
    }

    /**
     * 添加店铺
     *
     * @param claim
     * @param shopOffline
     * @param sysUser
     */
    private void addShop(CompanyShopOfflineClaim claim, Companyshopoffline shopOffline, SysUser sysUser) {
        Companyshop shopNew = new Companyshop();
        // 经销商
        shopNew.setCompanyId(sysUser.getBusinessAdministrationId().intValue());
        // 经销商厂商Id
        shopNew.setCompanyPid(shopOffline.getCompanyId());
        shopNew.setShopName(shopOffline.getShopName());
        shopNew.setShopCode(ShopCodePrefixEnum.BUILDING_MATERIALS_HOME.getCode() + Utils.generateRandomDigitString(7));
        shopNew.setUserId(claim.getUserId());
        shopNew.setBusinessType(SysDictionaryConstant.SHOP_TYPE_FURNITURE_VALUE);
        shopNew.setProvinceCode(shopOffline.getProvinceCode());
        shopNew.setCityCode(shopOffline.getCityCode());
        shopNew.setAreaCode(shopOffline.getAreaCode());
        shopNew.setStreetCode(shopOffline.getStreetCode());
        shopNew.setLongAreaCode(Joiner.on(".").join(Lists.newArrayList("", shopOffline.getProvinceCode(),
                shopOffline.getCityCode(), shopOffline.getAreaCode(), shopOffline.getStreetCode(), "")));
        shopNew.setContactName(shopOffline.getContactName());
        shopNew.setContactPhone(shopOffline.getContactPhone());
        shopNew.setShopAddress(shopOffline.getShopAddress());
        // logo pic
        shopNew.setLogoPicId(shopOffline.getLogoPicId());
        if (!StringUtils.isEmpty(shopOffline.getCoverPicId())) {
            shopNew.setCoverResIds(shopOffline.getCoverPicId());
            // 封面资源类型1:图片列表,2:全景图,3:视频
            shopNew.setCoverResType(1);
        }

        shopNew.setReleasePlatformValues(Objects.equals(shopOffline.getIsRelease(), 1) ? "1,2,3" : null);
        // shopNew.setDisplayStatus(shopOffline.getIsRelease());
        shopNew.setDisplayStatus(1);
        shopNew.setSysCode(System.currentTimeMillis() + "_" + Utils.generateRandomDigitString(6));
        shopNew.setCreator(sysUser.getNickName());
        shopNew.setIsDeleted(0);
        shopNew.setGmtCreate(new Date());
        // 兼容前端NaN 问题
        shopNew.setCategoryIds("");

        // 领取的店铺
        shopNew.setOfflineShopId(claim.getShopOfflineId());
        shopNew.setShopType(2);
        // 门店介绍
        shopNew.setIntroducedFileId(shopOffline.getIntroducedFileId());

        // add company shop
        companyshopService.insert(shopNew);
    }

    @Override
    public int update(CompanyshopofflineUpdate input) {
        Companyshopoffline.CompanyshopofflineBuilder builder = Companyshopoffline.builder();
        Companyshopoffline companyshopoffline = builder.build();

        BeanUtils.copyProperties(input, companyshopoffline);
        User creator = userDao.selectById(Long.valueOf(input.getModifier()));
        companyshopoffline.setCreator(creator.getUserName());
        companyshopoffline.setGmtModified(new Date());
        companyshopoffline.setLongAreaCode(this.setLongAreaCode(companyshopoffline));
        Companyshopoffline oldShop = companyshopofflineService.getById(input.getId());

        // 更新店铺介绍富文本内容
        Long oldResId = oldShop.getIntroducedFileId().longValue();
        String content = input.getShopIntroduced();
        if (oldResId == null || oldResId.intValue() == 0) {
            if (StringUtils.isNotEmpty(content)) {
                LoginUser loginUser = new LoginUser();
                loginUser.setUserName(creator.getUserName());
                Integer resId = resFileServiceImpl.saveFile(
                        "/AA/d_companyShop/[yyyy]/[MM]/[dd]/[HH]/company/shop/introducedFile/",
                        content, loginUser, input.getId(),
                        "company.shop.introducedFile.upload.path",
                        "店铺介绍文本");
                if (resId == null || resId == 0) {
                    log.error("CompanyShopServiceImpl.updateShop{}:保存配置文件失败");
                    return 0;
                } else {
                    companyshopoffline.setIntroducedFileId(resId);
                }
            }
        } else {
            boolean falg = resFileServiceImpl.updateFileFromSystem(oldResId.longValue(), content);
            if (!falg) {
                log.error("CompanyShopServiceImpl.resFileService.updateFile{}:更新配置文件失败");
                return 0;
            }
        }
        //转换原字段ID
//        companyshopoffline.setId(input.getCompanyshopofflineId());
        return companyshopofflineService.update(companyshopoffline);
    }

    @Override
    public int delete(String companyshopofflineId) {
        if (Strings.isNullOrEmpty(companyshopofflineId)) {
            return 0;
        }

        Set<Integer> companyshopofflineIds = new HashSet<>();
        List<String> list = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(companyshopofflineId));
        list.stream().forEach(id -> companyshopofflineIds.add(Integer.valueOf(id)));

        if (companyshopofflineIds.size() == 0) {
            return 0;
        }
        return companyshopofflineService.delete(companyshopofflineIds);
    }

    @Override
    public Companyshopoffline getById(int companyshopofflineId) {
        Companyshopoffline companyshopoffline = companyshopofflineService.getById(companyshopofflineId);
        List<Integer> picIds = new ArrayList<>();
        picIds.add(companyshopoffline.getLogoPicId());
//        String[] coverIds = companyshopoffline.getCoverPicId().split(",");
//        for(String id : coverIds) {
//            picIds.add(Integer.valueOf(id));
//        }
//        Map<Integer, String> picMap = resPicService.idAndPathMap(picIds);
//        companyshopoffline.setLogoPicPath(picMap.get(companyshopoffline.getLogoPicId()));
//        List<String> coverPicPaths = new ArrayList<>();
//        for(String id : coverIds) {
//            coverPicPaths.add(picMap.get(Integer.valueOf(id)));
//        }
//        companyshopoffline.setCoverPicPath(coverPicPaths);

        // 店铺封面
        // 修复coverPicId NullPointerException 问题
        List<Integer> coverPicIds = Splitter.on(",").omitEmptyStrings().splitToList(Optional.ofNullable(companyshopoffline.getCoverPicId()).orElse(""))
                .stream().map(Integer::valueOf).collect(Collectors.toList());
        picIds.addAll(coverPicIds);

        Map<Integer, String> picMap = resPicService.idAndPathMap(picIds);
        companyshopoffline.setLogoPicPath(picMap.get(companyshopoffline.getLogoPicId()));
        List<String> coverPicPaths = new ArrayList<>();
        coverPicIds.forEach(id -> coverPicPaths.add(picMap.get(id)));
        companyshopoffline.setCoverPicPath(coverPicPaths);

        List<String> codes = Splitter.on(".").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(companyshopoffline.getLongAreaCode()));
        companyshopoffline.setLongAreaName(baseAreaService.getAreaNames(codes).replace(",",""));

        Long fileId = companyshopoffline.getIntroducedFileId().longValue();
        if (fileId != null && 0 < fileId) {
            ResFile resFile = resFileServiceImpl.getById(fileId);
            String filePath = resFile != null ? resFile.getFilePath() : "";
            if (StringUtils.isNotEmpty(filePath)) {
                // 读取配置
                String fileContext = Utils.getFileContext(rootPath + filePath);
                log.info("#################配置{}",fileContext);
                companyshopoffline.setShopIntroduced(fileContext);
            }
        }

        return companyshopoffline;
    }

    @Override
    public PageInfo<Companyshopoffline> query(CompanyshopofflineQuery query) {
        List<Companyshopoffline> lists = companyshopofflineService.findAll(query).getList();
//        List<Integer> picIds = new ArrayList<>();
//        lists.forEach(result ->
//                picIds.add(result.getLogoPicId())
//        );
        List<Integer> picIds = lists.stream().map(Companyshopoffline::getLogoPicId).collect(Collectors.toList());
        Map<Integer, String> picMap = resPicService.idAndPathMap(picIds);
        lists.forEach(result -> {
            result.setLogoPicPath(picMap.get(result.getLogoPicId()));
            List<String> codes = Splitter.on(".").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(result.getLongAreaCode()));
            result.setLongAreaName(baseAreaService.getAreaNames(codes).replace(",",""));
        });
        return new PageInfo<>(lists);
    }

    public String setLongAreaCode(Companyshopoffline Companyshopoffline) {
        StringBuilder longAreaCodeBuilder = new StringBuilder("");
        if (StringUtils.isNotEmpty(Companyshopoffline.getProvinceCode())) {
            longAreaCodeBuilder.append(".").append(Companyshopoffline.getProvinceCode());
        }
        if (StringUtils.isNotEmpty(Companyshopoffline.getCityCode())) {
            longAreaCodeBuilder.append(".").append(Companyshopoffline.getCityCode());
        }
        if (StringUtils.isNotEmpty(Companyshopoffline.getAreaCode())) {
            longAreaCodeBuilder.append(".").append(Companyshopoffline.getAreaCode());
        }
        if (StringUtils.isNotEmpty(Companyshopoffline.getStreetCode())) {
            longAreaCodeBuilder.append(".").append(Companyshopoffline.getStreetCode());
        }
        if (longAreaCodeBuilder != null && 0 < longAreaCodeBuilder.length()) {
            longAreaCodeBuilder.append(".");
        } else {
            return "";
        }
        return longAreaCodeBuilder.toString();
    }

    @Override
    public Companyshopoffline checkShopName(String shopName, Integer companyId) {
        return companyshopofflineService.checkShopName(shopName, companyId);
    }

    @Override
    public int toRelease(String id, String isRelease) {
        return companyshopofflineService.toRelease(id, isRelease);
    }

	@Override
	public void importCompanyShopFromExcel(List<CompanyshopofflineAdd> beans) {
		 CodeUtil.fixPartWithList(beans, 10, partList -> {
	            this.executorService.execute(() -> {
	                LocalTime begin = LocalTime.now();
	                System.out.println(Thread.currentThread().getName() + "==>save start------->" + begin);
	                partList.forEach(it -> {
	                    if (StringUtils.isNoneBlank(it.getLogoPicPath())) {
	                        ResPic resPic = this.initPics(it.getLogoPicPath(), Integer.parseInt(it.getCreator()), true);
	                        it.setLogoPicId(resPicService.addResPic(resPic).intValue());
	                    }
	                    if (StringUtils.isNoneBlank(it.getCoverPicPath())) {
	                    	String[] paths = it.getCoverPicPath().split(",");
	                    	StringBuffer picIds = new StringBuffer(); 
	                    	for(String path : paths) {
		                        ResPic resPic = this.initPics(path, Integer.parseInt(it.getCreator()), false);
		                        Long picId = resPicService.addResPic(resPic);
		                        picIds.append(picId+"").append(",");	
	                    	}
	                        it.setCoverPicId(StringUtils.isEmpty(picIds.toString()) ? "" :picIds.toString().substring(0,picIds.toString().length()-1));
	                    }
	                    it.setIsRelease(1);
	                    this.create(it);
	                });
	                LocalTime end = LocalTime.now();
	                System.out.println(Thread.currentThread().getName() + "==>save end------->" + end);
	                System.out.println(Thread.currentThread().getName() + "==>user time------->" + Duration.between(begin, end));
	            });
	        });
	        System.out.println("local end");
	}
	
	 private ResPic initPics(String texturePicPath, Integer userId, boolean initSmallPicFlag) {
	        Date date = new Date();
	        String suffix = texturePicPath.substring(texturePicPath.lastIndexOf(Punctuation.DOT) + 1);
	        String picName = texturePicPath.substring(texturePicPath.lastIndexOf("/") + 1).replace(DOT + suffix, "");
	        return ResPic
	                .builder()
//	                .picSize()
	                .picPath(texturePicPath)
//	                .picWeight(String.valueOf(image.getWidth()))
//	                .picHigh(String.valueOf(image.getHeight()))
	                .fileKey(initSmallPicFlag ? "system.resTexture.pic" : "system.resTexture.pic.texture")
	                .gmtCreate(date)
	                .gmtModified(date)
	                .creator(userId.toString())
	                .modifier(userId.toString())
	                .picName(picName)
	                .picFileName(picName)
	                .picCode(picName)
	                .picSuffix(DOT + suffix)
	                .picFormat(suffix)
	                .remark("from-merchant-manage-excel-import")
	                .isDeleted(0)
	                .build();

	    }
}
