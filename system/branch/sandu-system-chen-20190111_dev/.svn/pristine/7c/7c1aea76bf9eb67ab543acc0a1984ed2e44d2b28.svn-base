package com.sandu.service.shop.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.category.service.ProCategoryService;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.file.model.ResFile;
import com.sandu.api.file.service.ResFileService;
import com.sandu.api.pic.model.ResPic;
import com.sandu.api.pic.model.po.ResPicBusinessUpdate;
import com.sandu.api.pic.model.po.ResPicPO;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.api.shop.common.constant.ShopCodePrefixEnum;
import com.sandu.api.shop.common.constant.ShopCoverResTypeConstant;
import com.sandu.api.shop.common.constant.ShopResTypeConstant;
import com.sandu.api.shop.input.CompanyShopAdd;
import com.sandu.api.shop.input.CompanyShopArticleQuery;
import com.sandu.api.shop.input.CompanyShopQuery;
import com.sandu.api.shop.input.CompanyShopUpdate;
import com.sandu.api.shop.input.ProjectCaseQuery;
import com.sandu.api.shop.model.CompanyShop;
import com.sandu.api.shop.model.po.ShopPO;
import com.sandu.api.shop.output.CompanyShopDetailsVO;
import com.sandu.api.shop.output.CompanyShopVO;
import com.sandu.api.shop.service.CompanyShopArticleService;
import com.sandu.api.shop.service.CompanyShopDesignPlanService;
import com.sandu.api.shop.service.CompanyShopService;
import com.sandu.api.shop.service.ProjectCaseService;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.commons.constant.BusinessTypeConstant;
import com.sandu.commons.constant.SysDictionaryConstant;
import com.sandu.commons.util.StringUtils;
import com.sandu.constant.Constants;
import com.sandu.queue.SyncMessage;
import com.sandu.queue.service.QueueService;
import com.sandu.service.shop.dao.CompanyShopDao;

/**
 * 企业店铺service实现类
 *
 * @auth xiaoxc
 * @data 2018-06-04
 */
@Service("companyShopService")
public class CompanyShopServiceImpl implements CompanyShopService {

    private Logger logger = LoggerFactory.getLogger(CompanyShopServiceImpl.class);
    @Value("${upload.base.path}")
    private String rootPath;
    @Value("${company.shop.introducedFile.upload.path}")
    private String introducedUpdateFilePath;
    @Autowired
    private CompanyShopDao companyShopDao;
    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private ProCategoryService proCategoryService;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private BaseAreaService baseAreaService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private ResFileService resFileService;
    @Autowired
    private ProjectCaseService projectCaseService;
    @Autowired
    private CompanyShopArticleService articleService;

    @Autowired
    private CompanyShopDesignPlanService designPlanService;
    @Autowired
    private QueueService queueService;
    @Autowired
    private SysUserService userService;

    @Override
    public int addShop(CompanyShopAdd shopAdd, LoginUser loginUser) {
        CompanyShop companyShop = shopAdd.getCompanyShop();
        // 设置区域长编码
        companyShop.setLongAreaCode(this.getLongAreaCode(companyShop));
        // 设置用户Id
        companyShop.setUserId(loginUser.getId());
        // 获取企业信息
        BaseCompany baseCompany = null;
        // 获取登录用户企业id
        Long franchiserId = shopAdd.getCompanyId();
        if (franchiserId != null && franchiserId > 0) {
            // 设置企业Id
            companyShop.setCompanyId(franchiserId.intValue());
            baseCompany = baseCompanyService.getCompanyById(franchiserId);
            // 设置店铺编码、分类、类型
            setShopInfo(companyShop, baseCompany);
            // 经销商冗余厂商Id
            if (BusinessTypeConstant.BUSINESSTYPE_FRANCHISER == baseCompany.getBusinessType().intValue()) {
                companyShop.setCompanyPid(baseCompany.getPid());
            } else {
                companyShop.setCompanyPid(franchiserId.intValue());
            }
        } else {
            logger.error("addShop: userId={},companyId={}", loginUser.getId() , franchiserId);
            return 0;
        }
        // 存储店铺介绍富文本文件
        String content = companyShop.getShopIntroduced();
        if (StringUtils.isNotEmpty(content)) {
            Integer resId = resFileService.saveFile(introducedUpdateFilePath, content, loginUser, null,
                    ShopResTypeConstant.SHOP_RES_INTRODUCED_FILE_KEY, ShopResTypeConstant.SHOP_RES_INTRODUCED_FILE_TYPE);
            if (resId == null || resId == 0) {
                logger.error("CompanyShopServiceImpl.addShop{}:保存配置文件失败");
                return 0;
            } else {
                companyShop.setIntroducedFileId(resId.longValue());
            }
        }
        // 设置系统字段值
        saveSystemInfo(companyShop, loginUser);
        // 插入数据库
        int addResult = companyShopDao.insertShop(companyShop);
        if (addResult > 0) {
            // 回填资源业务ID
            boolean falg = backfill(companyShop);
            if (falg) {
                return companyShop.getId();
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public int deleteShop(Integer shopId, LoginUser loginUser) {
        CompanyShop companyShop = getShopById(shopId);
        if (companyShop == null) {
            return 0;
        }
        // 删除对应的工程案例
        ProjectCaseQuery query = new ProjectCaseQuery();
        query.setShopId(companyShop.getId());
        int total = projectCaseService.getCount(query);
        if (total > 0) {
            projectCaseService.batchDelByShopId(companyShop.getId(), loginUser);
        }

        //删除对应店铺博文  add by WangHaiLin
        CompanyShopArticleQuery articleQuery=new CompanyShopArticleQuery();
        articleQuery.setShopId(Long.valueOf(companyShop.getId()));
        Integer count = articleService.getCount(articleQuery);
        if (count>0){
            articleService.deleteByShopId(Long.valueOf(companyShop.getId()),loginUser);
        }

        //删除店铺发布的方案 add by WangHaiLin
        List<Integer> shopIds=new ArrayList<>();
        shopIds.add(companyShop.getId());
        Integer count2 = designPlanService.getDesignPlanByShopId(shopIds);
        if (count2>0){
            designPlanService.deleteDesignPlanByShopId(shopIds);
        }

        if (companyShop.getShopType() == null || companyShop.getShopType() == 2) {
            Integer mainShopId = articleService.getMainCompanyShopId(shopId);
            projectCaseService.updateMainShopCaseStatus(mainShopId, shopId, 1);
            articleService.updateMainShopArticleStatus(mainShopId, shopId, 1);
            designPlanService.updateMainShopDesignStatus(mainShopId, shopId, 1);
        }

        // 存储系统字段值
        saveSystemInfo(companyShop, loginUser);
        int result = companyShopDao.deleteShop(companyShop);
        if(result > 0) {
        	logger.info("deleteShop发送消息开始:id:{},时间:{}",shopId,new Date());
        	try {
            	sycMessageDoSend(SyncMessage.ACTION_DELETE, Arrays.asList(shopId));
            }catch(Exception e) {
            	logger.error("deleteShop发送消息失败,id:{},错误信息:{}",shopId,e.getMessage());
            }
        	logger.info("deleteShop发送消息结束:id:{},时间:{}",shopId,new Date());
        }
        return result;
    }

    @Override
    public int updateShop(CompanyShopUpdate shopUpdate, LoginUser loginUser) {
        // 未修改之前的值
        CompanyShop oldCompanyShop = getShopById(shopUpdate.getShopId());
		String oldPlatFormValues = oldCompanyShop.getReleasePlatformValues();
		String newPlatFormValues = shopUpdate.getPlatformValues();
        // 修改的值
        CompanyShop companyShop = shopUpdate.getCompanyShopFromCompanyShopUpdate(oldCompanyShop, shopUpdate);
        // 更新店铺介绍富文本内容
        Long oldResId = oldCompanyShop.getIntroducedFileId();
        String content = companyShop.getShopIntroduced();
        if (oldResId == null || oldResId.intValue() == 0) {
            if (StringUtils.isNotEmpty(content)) {
                Integer resId = resFileService.saveFile(introducedUpdateFilePath, content, loginUser, companyShop.getId(),
                        ShopResTypeConstant.SHOP_RES_INTRODUCED_FILE_KEY, ShopResTypeConstant.SHOP_RES_INTRODUCED_FILE_TYPE);
                if (resId == null || resId == 0) {
                    logger.error("CompanyShopServiceImpl.updateShop{}:保存配置文件失败");
                    return 0;
                } else {
                    companyShop.setIntroducedFileId(resId.longValue());
                }
            }
        } else {
            boolean falg = resFileService.updateFile(oldResId.longValue(), content);
            if (!falg) {
                logger.error("CompanyShopServiceImpl.resFileService.updateFile{}:更新配置文件失败");
                return 0;
            }
        }
        //存储系统字段值
        saveSystemInfo(companyShop, loginUser);
        int result =  companyShopDao.updateShop(companyShop);
    	if(result > 0) {
    		//发布平台有变动再推送消息
    		boolean flag = checkPlatformChange(oldPlatFormValues,newPlatFormValues);
			logger.info("oldPlatFormValues:{},newPlatFormValues:{},flag:{}",oldPlatFormValues,newPlatFormValues,flag);
    		if(flag) {
    			logger.info("updateShop发送消息开始:id:{},时间:{}",shopUpdate.getShopId(),new Date());
            	try {
                	sycMessageDoSend(SyncMessage.ACTION_UPDATE, Arrays.asList(shopUpdate.getShopId()));
                }catch(Exception e) {
                	logger.error("updateShop发送消息失败,id:{},错误信息:{}",shopUpdate.getShopId(),e.getMessage());
                }
            	logger.info("updateShop发送消息结束:id:{},时间:{}",shopUpdate.getShopId(),new Date());
    		}
        }
        return result;
    }

    /**
     * 校验发布平台是否有变动
     * @param oldPlatFormValues
     * @param newPlatFormValues
     * @return
     */
    private static boolean checkPlatformChange(String oldPlatFormValues, String newPlatFormValues) {
    	if(StringUtils.isEmpty(oldPlatFormValues)) {
    		if(StringUtils.isEmpty(newPlatFormValues)) {
    			return false;
    		}else {
    			return true;
    		}
    	}else {
    		if(StringUtils.isEmpty(newPlatFormValues)) {
    		   return true;
    		}
			List<String> oldPlatformList = new ArrayList<String>(Arrays.asList(oldPlatFormValues.split(",")));
			List<String> newPlatformList = new ArrayList<String>(Arrays.asList(newPlatFormValues.split(",")));
			if(oldPlatformList.size()!=newPlatformList.size()) {
				return true;
			}
			//判断是否存在差集
			oldPlatformList.removeAll(newPlatformList);
			if(oldPlatformList.size()> 0) {
				return true;
			}else {
				return false;
			}
    	}
    }

    @Override
    public int updateCompanyShop(CompanyShop companyShop, LoginUser loginUser) {
        companyShop.setGmtModified(new Date());
        companyShop.setModifier(loginUser.getLoginName());
        return companyShopDao.updateShop(companyShop);
    }

    @Override
    public CompanyShop getShopById(Integer shopId) {
        return companyShopDao.getShopById(shopId);
    }

    @Override
    public int getCount(CompanyShopQuery query) {
        ShopPO shopPO = query.getShopPOFromShopQuery(query);
        return companyShopDao.findCount(shopPO);
    }

    @Override
    public List<CompanyShopVO> findList(CompanyShopQuery query) {
        List<CompanyShopVO> shopVOList = new ArrayList<>();
        ShopPO shopPO = query.getShopPOFromShopQuery(query);
        List<CompanyShop> shopList = companyShopDao.findList(shopPO);
        if (shopList != null && 0 < shopList.size()) {
            shopList.forEach(companyShop -> {
                CompanyShopVO shopVO = new CompanyShopVO().getCompanyShopVOFromCompanyShop(companyShop);
                // 获取区域名称
                shopVO.setAreaName(getAreaNames(companyShop, null));
                // 获取平台名称
                shopVO.setReleasePlatformName(getPlatfromTypeNames(companyShop));
                if (Utils.isBlank(shopVO.getReleasePlatformName())){
                    shopVO.setReleaseStatus(0);
                }else{
                    shopVO.setReleaseStatus(1);
                }
                //获取创建人信息
                CompanyShopDetailsVO detailsVO = new CompanyShopDetailsVO();
                detailsVO.setCreator(shopVO.getCreator());
                SysUser userInfo = getCreatorInfo(detailsVO);
                shopVO.setCreatorAccount(userInfo==null?"":userInfo.getNickName());
                shopVO.setCreatorPhone(userInfo==null?"":userInfo.getMobile());
                // 存入列表
                shopVOList.add(shopVO);
            });
        }
        return shopVOList;
    }

    @Override
    public CompanyShopDetailsVO getShopDetails(Integer shopId) {
        CompanyShop companyShop = getShopById(shopId);
        CompanyShopDetailsVO detailsVO = new CompanyShopDetailsVO().getShopDetailsVOFromShop(companyShop);
        if (detailsVO != null && detailsVO.getShopId() != null) {
            // 获取区域名称
            getAreaNames(companyShop, detailsVO);
            // 获取店铺分类(数据字典擅长风格、施工类型名称)
            getCategoryNames(detailsVO);
            // 获取资源相对路径
            getResPath(detailsVO);
            // 获取创建人账号
            SysUser userInfo = getCreatorInfo(detailsVO);
            detailsVO.setCreatorAccount(userInfo==null?"":userInfo.getNickName());
            // 获取创建人手机
            detailsVO.setCreatorPhone(userInfo==null?"":userInfo.getMobile());
        }
        return detailsVO;
    }

    private SysUser getCreatorInfo(CompanyShopDetailsVO detailsVO) {
        if (detailsVO == null) {
            return null;
        }
        List<String> nickNames = new ArrayList<>();
        nickNames.add(detailsVO.getCreator());
        List<SysUser> user = userService.getUserByNickName(nickNames);
        if(user != null && user.size() > 0){
            return user.get(0);
        }
        return null;
    }

    @Override
    public CompanyShopDetailsVO findBrandPavilion(Integer userId, Long companyId, Integer businessType) {
        CompanyShop companyShop = companyShopDao.findBrandPavilion(userId, companyId, businessType);
        CompanyShopDetailsVO detailsVO = new CompanyShopDetailsVO().getShopDetailsVOFromShop(companyShop);
        if (detailsVO != null && detailsVO.getShopId() != null) {
            // 获取区域名称
            getAreaNames(companyShop, detailsVO);
            // 获取店铺分类(数据字典擅长风格、施工类型名称)
            //getCategoryNames(detailsVO); //品牌馆不需要
            // 获取资源相对路径
            getResPath(detailsVO);
        }
        return detailsVO;
    }

    @Override
    public Integer checkShopName(String shopName, Long shopId) {
        return companyShopDao.checkShopName(shopName, shopId);
    }

    @Override
    public boolean disposeShopCategory(Long companyId, String categoryIds) {
        CompanyShop companyShop = new CompanyShop();
        companyShop.setCompanyId(companyId.intValue());
        companyShop.setGmtModified(new Date());
        if (StringUtils.isNotEmpty(categoryIds)) {
            // 转换为二级分类Id
            companyShop.setCategoryIds(getCategoryIds(categoryIds));
            // 冗余产品一级分类Id
            companyShop.setFirstCategoryIds(getCategoryIds(companyShop.getCategoryIds()));
        } else {
            companyShop.setCategoryIds("");
        }
        try {
            this.updateShopCategoryByCompanyId(companyShop);
        } catch (Exception e) {
            logger.error("updateCategoryRelData{} e " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public int updateShopCategoryByCompanyId(CompanyShop companyShop) {
        return companyShopDao.updateShopCategoryByCompanyId(companyShop);
    }

    @Override
    public List<CompanyShop> getShopListByUserId(Long userId) {
        return companyShopDao.getShopListByUserId(userId);
    }

    @Override
    public Integer deleteShopByUserId(Long userId,String loginUserName) {
        return companyShopDao.deleteShopByUserId(userId,loginUserName);
    }

    @Override
    public Integer checkCompanyShop(Integer companyId, Integer shopType) {
        return companyShopDao.checkCompanyShop(companyId, shopType);
    }

    @Override
    public List<Integer> getShopDesignList(Integer shopId, Integer isDeleted) {
        return companyShopDao.getShopDesignList(shopId, isDeleted);
    }

    /**
     * 自动存储系统字段
     *
     * @param companyShop 店铺model对象
     * @param loginUser   当前用户对象
     */
    @SuppressWarnings("all")
    private void saveSystemInfo(CompanyShop companyShop, LoginUser loginUser) {
        if (companyShop != null) {
            //新增
            if (companyShop.getId() == null) {
                companyShop.setGmtCreate(new Date());
                companyShop.setCreator(loginUser.getLoginName());
                companyShop.setIsDeleted(0);
                if (companyShop.getSysCode() == null || "".equals(companyShop.getSysCode())) {
                    companyShop.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }
            companyShop.setGmtModified(new Date());
            companyShop.setModifier(loginUser.getLoginName());
        }
    }

    /**
     * 设置区域长编码
     *
     * @param companyShop
     * @return
     */
    private String getLongAreaCode(CompanyShop companyShop) {

        StringBuilder longAreaCodeBuilder = new StringBuilder("");
        if (StringUtils.isNotEmpty(companyShop.getProvinceCode())) {
            longAreaCodeBuilder.append(".").append(companyShop.getProvinceCode());
        }
        if (StringUtils.isNotEmpty(companyShop.getCityCode())) {
            longAreaCodeBuilder.append(".").append(companyShop.getCityCode());
        }
        if (StringUtils.isNotEmpty(companyShop.getAreaCode())) {
            longAreaCodeBuilder.append(".").append(companyShop.getAreaCode());
        }
        if (StringUtils.isNotEmpty(companyShop.getStreetCode())) {
            longAreaCodeBuilder.append(".").append(companyShop.getStreetCode());
        }
        if (longAreaCodeBuilder != null && 0 < longAreaCodeBuilder.length()) {
            longAreaCodeBuilder.append(".");
        } else {
            return "";
        }
        return longAreaCodeBuilder.toString();
    }

    /**
     * 1、设置店铺编码(编码规则标识+7位随机数)
     * 2、根据企业类型获取店铺类型
     * 3、根据企业类型获取店铺产品分类
     *
     * @param companyShop
     * @param baseCompany
     */
    private void setShopInfo(CompanyShop companyShop, BaseCompany baseCompany) {
        switch (baseCompany.getBusinessType()) {
            case BusinessTypeConstant.BUSINESSTYPE_COMPANY:
                // 店铺编码
                companyShop.setShopCode(ShopCodePrefixEnum.BRAND_PAVILION.getCode() + Utils.generateRandomDigitString(7));
                // 企业厂商---店铺品牌馆
                companyShop.setBusinessType(SysDictionaryConstant.SHOP_TYPE_BRAND_PAVILION_VALUE);
                // 通过企业可见范围产品分类获取父级分类(二级分类)
                companyShop.setCategoryIds(getCategoryIds(baseCompany.getProductVisibilityRange()));
                // 冗余产品分类一级分类
                companyShop.setFirstCategoryIds(getCategoryIds(companyShop.getCategoryIds()));
                break;
            case BusinessTypeConstant.BUSINESSTYPE_FRANCHISER:
                // 店铺编码
                companyShop.setShopCode(ShopCodePrefixEnum.BUILDING_MATERIALS_HOME.getCode() + Utils.generateRandomDigitString(7));
                // 经销商---家居建材
                companyShop.setBusinessType(SysDictionaryConstant.SHOP_TYPE_FURNITURE_VALUE);
                // 通过企业可见范围产品分类获取父级分类(二级分类)
                companyShop.setCategoryIds(getCategoryIds(baseCompany.getProductVisibilityRange()));
                // 冗余产品分类一级分类
                companyShop.setFirstCategoryIds(getCategoryIds(companyShop.getCategoryIds()));
                break;
            case BusinessTypeConstant.BUSINESSTYPE_DESIGN:
                // 店铺编码
                companyShop.setShopCode(ShopCodePrefixEnum.DESIGN_COMPANY.getCode() + Utils.generateRandomDigitString(7));
                // 设计公司---设计公司
                companyShop.setBusinessType(SysDictionaryConstant.SHOP_TYPE_DESIGNER_COMPANY_VALUE);
                break;
            case BusinessTypeConstant.BUSINESSTYPE_DECORATION:
                // 店铺编码
                companyShop.setShopCode(ShopCodePrefixEnum.DECORATION_COMPANY.getCode() + Utils.generateRandomDigitString(7));
                // 装修公司---装修公司
                companyShop.setBusinessType(SysDictionaryConstant.SHOP_TYPE_DECORATION_COMPANY_VALUE);
                break;
            case BusinessTypeConstant.BUSINESSTYPE_DESIGNER:
                // 店铺编码
                companyShop.setShopCode(ShopCodePrefixEnum.DESIGNER.getCode() + Utils.generateRandomDigitString(7));
                // 设计师---设计师
                companyShop.setBusinessType(SysDictionaryConstant.SHOP_TYPE_PROFESSION_DESIGNER_VALUE);
                break;
            case BusinessTypeConstant.BUSINESSTYPE_FOREMAN:
                // 店铺编码
                companyShop.setShopCode(ShopCodePrefixEnum.CONSTRUCTION_UNIT.getCode() + Utils.generateRandomDigitString(7));
                // 工长(施工单位)---工长(施工单位)
                companyShop.setBusinessType(SysDictionaryConstant.SHOP_TYPE_CONSTRUCTION_UNIT_VALUE);
                break;
            case BusinessTypeConstant.BUSINESSTYPE_FRANCHISER_D:
                // 店铺编码
                companyShop.setShopCode(ShopCodePrefixEnum.BUILDING_MATERIALS_HOME.getCode() + Utils.generateRandomDigitString(7));
                // 独立经销商---家居建材
                companyShop.setBusinessType(SysDictionaryConstant.SHOP_TYPE_FURNITURE_VALUE);
                // 通过企业可见范围产品分类获取父级分类(二级分类)
                companyShop.setCategoryIds(getCategoryIds(baseCompany.getProductVisibilityRange()));
                // 冗余产品分类一级分类
                companyShop.setFirstCategoryIds(getCategoryIds(companyShop.getCategoryIds()));
                break;
            default:
                break;
        }
    }

    /**
     * 通过企业可见范围产品分类获取父级
     *
     * @param categoryIds
     * @return
     */
    private String getCategoryIds(String categoryIds) {
        StringBuilder builder = new StringBuilder("");
        if (StringUtils.isNotEmpty(categoryIds)) {
            List<Integer> idList = StringUtil.getListByString(categoryIds);
            if (idList != null && 0 < idList.size()) {
                List<Integer> parentIds = proCategoryService.getParentId(idList);
                if (parentIds != null && 0 < parentIds.size()) {
                    for (Integer pid : parentIds) {
                        builder.append(pid).append(",");
                    }
                }
            }
        }
        if (builder.length() > 0) {
            return builder.substring(0, builder.length() - 1).toString();
        } else {
            return "";
        }
    }

    /**
     * 回填资源业务ID
     *
     * @param companyShop
     * @return
     */
    @SuppressWarnings("all")
    private boolean backfill(CompanyShop companyShop) {
        Integer logoId = companyShop.getLogoPicId();
        String coverIds = companyShop.getCoverResIds();
        Long introducedFileId = companyShop.getIntroducedFileId();
        String introducedPicIds = companyShop.getIntroducedPicIds();
        //logo
        if (logoId != null && logoId > 0) {
            ResPic resPic = new ResPic();
            resPic.setId(logoId.longValue());
            resPic.setBusinessId(companyShop.getId());
            try {
                resPicService.updateByPrimaryKeySelective(resPic);
            } catch (Exception e) {
                logger.error("backfill{}: shopId="+companyShop.getId()+";logoId="+logoId);
                return false;
            }
        }
        //封面
        if (StringUtils.isNotEmpty(coverIds)) {
            switch (companyShop.getCoverResType()) {
                //图片列表
                case ShopCoverResTypeConstant.COVER_PIC_LIST_TYPE:
                    List<Integer> idList = Utils.getIntegerListFromStringList(coverIds);
                    if (idList != null && 0 < idList.size()) {
                        ResPicBusinessUpdate resPic = new ResPicBusinessUpdate();
                        resPic.setIdList(idList);
                        resPic.setBusinessId(companyShop.getId());
                        try {
                            resPicService.batchUpdateBusinessId(resPic);
                        } catch (Exception e) {
                            logger.error("backfill{}: shopId="+companyShop.getId()+";coverIds="+coverIds);
                            return false;
                        }
                    }
                    break;
                //全景图
                case ShopCoverResTypeConstant.COVER_PANORAMA_TYPE:
                    ResPic resPic = new ResPic();
                    resPic.setId(logoId.longValue());
                    resPic.setBusinessId(companyShop.getId());
                    try {
                        resPicService.updateByPrimaryKeySelective(resPic);
                    } catch (Exception e) {
                        logger.error("backfill{}: shopId="+companyShop.getId()+";logoId="+logoId);
                        return false;
                    }
                    break;
                //视频
                case ShopCoverResTypeConstant.COVER_VIDEO_TYPE:
                    break;
                default:
                    break;
            }
        }
        //富文本文件
        if (introducedFileId != null && introducedFileId.intValue() > 0) {
            ResFile resFile = new ResFile();
            resFile.setId(introducedFileId.intValue());
            resFile.setBusinessId(companyShop.getId());
            try {
                resFileService.update(resFile);
            } catch (Exception e) {
                logger.error("backfill{}: shopId="+companyShop.getId()+";fileId="+introducedFileId);
                return false;
            }
        }
        //富文本图片ids
        if (StringUtils.isNotEmpty(introducedPicIds)) {
            List<Integer> idList = Utils.getIntegerListFromStringList(introducedPicIds);
            if (idList != null && 0 < idList.size()) {
                ResPicBusinessUpdate resPic = new ResPicBusinessUpdate();
                resPic.setIdList(idList);
                resPic.setBusinessId(companyShop.getId());
                try {
                    resPicService.batchUpdateBusinessId(resPic);
                } catch (Exception e) {
                    logger.error("backfill{}: shopId="+companyShop.getId()+";picIds="+introducedPicIds);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取当前企业 行政区域 名称
     *
     * @param detailsVO
     */
    private String getAreaNames(CompanyShop companyShop, CompanyShopDetailsVO detailsVO) {
        if (companyShop == null) {
            return "";
        }
        List<String> codeList = new ArrayList<>();
        codeList.add(companyShop.getProvinceCode());
        codeList.add(companyShop.getCityCode());
        codeList.add(companyShop.getAreaCode());
        codeList.add(companyShop.getStreetCode());
        String areaNames = baseAreaService.getAreaNames(codeList);
        if (detailsVO == null) {
            return areaNames;
        }
        String[] codeNames = StringUtils.isNotEmpty(areaNames) ? areaNames.split(",") : new String[4];
        for (int i=0; i<codeNames.length; i++) {
            switch (i) {
                case 0:
                    detailsVO.setProvinceName(codeNames[0]);
                    break;
                case 1:
                    detailsVO.setCityName(codeNames[1]);
                    break;
                case 2:
                    detailsVO.setAreaName(codeNames[2]);
                    break;
                case 3:
                    detailsVO.setStreetName(codeNames[3]);
                    break;
                default:
                    break;
            }
        }
        return areaNames;
    }

    /**
     * 通过type、value查询数据字典名称
     *
     * @param detailsVO
     */
    private void getCategoryNames(CompanyShopDetailsVO detailsVO) {
        if (detailsVO == null) {
            return;
        }
        Integer businessType = detailsVO.getBusinessTypeValue();
        String categoryIds = detailsVO.getCategoryIds();
        // 设计师、设计师公司、装修公司:擅长风格
        if (SysDictionaryConstant.SHOP_TYPE_PROFESSION_DESIGNER_VALUE == businessType
                || SysDictionaryConstant.SHOP_TYPE_DESIGNER_COMPANY_VALUE == businessType
                || SysDictionaryConstant.SHOP_TYPE_DECORATION_COMPANY_VALUE == businessType) {
            if (StringUtils.isNotEmpty(categoryIds)) {
                List<Integer> dicValueList = StringUtil.getListByString(categoryIds);
                if (dicValueList != null && 0 <dicValueList.size()) {
                    String dicNames = dictionaryService.getNameByTypeOrValues(SysDictionaryConstant.DIRECTION_GOOD_STYLE, dicValueList);
                    detailsVO.setCategoryName(dicNames);
                }
            }
        }
        // 工长（施工单位）：施工类型
        if (SysDictionaryConstant.SHOP_TYPE_CONSTRUCTION_UNIT_VALUE == businessType) {
            if (StringUtils.isNotEmpty(categoryIds)) {
                List<Integer> dicValueList = StringUtil.getListByString(categoryIds);
                if (dicValueList != null && 0 <dicValueList.size()) {
                    String dicNames = dictionaryService.getNameByTypeOrValues(SysDictionaryConstant.DIRECTION_CONSTRUCTION_TYPE, dicValueList);
                    detailsVO.setCategoryName(dicNames);
                }
            }
        }
    }

    /**
     * 获取店铺发布平台的名称
     * @param companyShop
     * @return
     */
    private String getPlatfromTypeNames(CompanyShop companyShop) {
        if (companyShop == null) {
            return "";
        }
        String platfromValues = companyShop.getReleasePlatformValues();
        if (StringUtils.isNotEmpty(platfromValues)) {
            List<Integer> dicValueList = StringUtil.getListByString(platfromValues);
            if (dicValueList != null && 0 < dicValueList.size()) {
                return dictionaryService.getNameByTypeOrValues(SysDictionaryConstant.DIRECTION_SHOP_RELEASE_PLATFORM, dicValueList);
            }
        }
        return "";
    }


    /**
     * 获取资源相对路径
     * @param detailsVO
     */
    private void getResPath(CompanyShopDetailsVO detailsVO) {
        if (detailsVO == null) {
            return;
        }
        Integer logoId = detailsVO.getLogoId();
        String coverIds = detailsVO.getCoverResIds();
        Long fileId = detailsVO.getIntroducedFileId();
        //logo资源地址
        if (logoId != null && 0 < logoId.intValue()) {
            ResPic resPic = resPicService.selectByPrimaryKey(logoId.longValue());
            detailsVO.setLogoUrl(resPic != null ? resPic.getPicPath() : "");
        }
        //封面资源地址
        if (StringUtils.isNotEmpty(coverIds)) {
            //图片列表和全景图
            if (ShopCoverResTypeConstant.COVER_PIC_LIST_TYPE == detailsVO.getCoverResType()
                    || ShopCoverResTypeConstant.COVER_PANORAMA_TYPE == detailsVO.getCoverResType()) {
                List<Integer> idList = Utils.getIntegerListFromStringList(coverIds);
                if (idList != null && 0 < idList.size()) {
                    List<ResPicPO> resPicPOList = resPicService.findByIds(idList);
                    if (resPicPOList != null && 0 < resPicPOList.size()) {
                        detailsVO.setCoverList(resPicPOList);
                    }
                }
            }
            //视频
            if (ShopCoverResTypeConstant.COVER_VIDEO_TYPE == detailsVO.getCoverResType()) {

            }

        }
        //店铺介绍富文本地址
        if (fileId != null && 0 < fileId) {
            ResFile resFile = resFileService.getById(fileId);
//            detailsVO.setIntroducedFilePath(resFile != null ? resFile.getFilePath() : "");
            String filePath = resFile != null ? resFile.getFilePath() : "";
            if (StringUtils.isNotEmpty(filePath)) {
                // 读取配置
                String fileContext = Utils.getFileContext(rootPath + filePath);
                detailsVO.setShopIntroduced(fileContext);
            }
        }
    }
    
    /**
     * 发送异步消息
     * @param messageAction
     * @param ids
     */
    private void sycMessageDoSend(Integer messageAction, List<Integer> ids) {
        List<Map> content = ids.stream().map(item -> {
            HashMap<String, Integer> tmp = new HashMap<>(1);
            tmp.put("shopId", item);
            return tmp;
        }).collect(Collectors.toList());
        SyncMessage message = new SyncMessage();
        message.setAction(messageAction);
        message.setMessageId("S-" + System.currentTimeMillis());
        message.setModule(SyncMessage.SHOP_MODULE);
        message.setPlatformType(Constants.PLATFORM_CODE_MERCHANT_MANAGE);
        message.setObject(content);
        queueService.sendSyncSearch(message);
    }

    @Override
    public int setRelease(CompanyShop shop, LoginUser loginUser, Integer isRelease) {
        switch (shop.getBusinessType()) {
            case SysDictionaryConstant.SHOP_TYPE_BRAND_PAVILION_VALUE: // 企业厂商---店铺品牌馆
                if (isRelease==1){//发布
                    //设置店铺发布平台(1:小程序2:选装网3:同城联盟)
                    shop.setReleasePlatformValues("1,2,3");
                }else{//取消发布
                    shop.setReleasePlatformValues(null);
                }
                break;
            case SysDictionaryConstant.SHOP_TYPE_FURNITURE_VALUE: // 经销商---家居建材  独立经销商---家居建材
                if (isRelease==1){//发布
                    //设置店铺发布平台(1:小程序2:选装网3:同城联盟)
                    shop.setReleasePlatformValues("1,2,3");
                }else{//取消发布
                    shop.setReleasePlatformValues(null);
                }
                break;
            case SysDictionaryConstant.SHOP_TYPE_DESIGNER_COMPANY_VALUE: // 设计公司---设计公司
                if (isRelease==1){//发布
                    //设置店铺发布平台(1:小程序2:选装网3:同城联盟)
                    shop.setReleasePlatformValues("2,3");
                }else{//取消发布
                    shop.setReleasePlatformValues(null);
                }
                break;
            case SysDictionaryConstant.SHOP_TYPE_DECORATION_COMPANY_VALUE:// 装修公司---装修公司
                if (isRelease==1){//发布
                    //设置店铺发布平台(2:选装网3:同城联盟)
                    shop.setReleasePlatformValues("2,3,4");
                }else{//取消发布
                    shop.setReleasePlatformValues(null);
                }
                break;
            case SysDictionaryConstant.SHOP_TYPE_PROFESSION_DESIGNER_VALUE: // 设计师---设计师
                if (isRelease==1){//发布
                    //设置店铺发布平台(2:选装网3:同城联盟)
                    shop.setReleasePlatformValues("2,3");
                }else{//取消发布
                    shop.setReleasePlatformValues(null);
                }
                break;
            case SysDictionaryConstant.SHOP_TYPE_CONSTRUCTION_UNIT_VALUE:// 工长(施工单位)---工长(施工单位)
                if (isRelease==1){//发布
                    //设置店铺发布平台(2:选装网3:同城联盟)
                    shop.setReleasePlatformValues("2,3");
                }else{//取消发布
                    shop.setReleasePlatformValues(null);
                }
                break;
           /* case SysDictionaryConstant.SHOP_TYPE_FURNITURE_VALUE:// 独立经销商---家居建材
                if (isRelease==1){//发布
                    //设置店铺发布平台(2:选装网3:同城联盟)
                    shop.setReleasePlatformValues("1,2,3");
                }else{//取消发布
                    shop.setReleasePlatformValues(null);
                }
                break;*/
            default:
                break;
        }
        //修改店铺信息
        shop.setGmtModified(new Date());
        shop.setModifier(loginUser.getLoginName());
        return companyShopDao.setRelease(shop);
    }


}
