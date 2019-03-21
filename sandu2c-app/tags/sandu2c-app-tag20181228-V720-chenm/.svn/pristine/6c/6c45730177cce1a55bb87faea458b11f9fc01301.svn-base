package com.sandu.supplydemand.service.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 6:02 2018/4/27 0027
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.base.service.BaseAreaService;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.common.util.collections.Lists;
import com.sandu.design.model.DesignTemplet;
import com.sandu.designplan.model.DesignPlanRecommendedResult;
import com.sandu.designplan.model.ResRenderPic;
import com.sandu.designplan.service.DesignPlanRenderSceneService;
import com.sandu.designplan.vo.MydecorationPlanVo;
import com.sandu.designtemplate.service.DesignTempletService;
import com.sandu.fullhouse.model.FullHouseDesignPlan;
import com.sandu.fullhouse.service.FullHouseDesignPlanService;
import com.sandu.home.model.BaseHouse;
import com.sandu.home.service.HouseSpaceNewService;
import com.sandu.node.constant.NodeInfoConstant;
import com.sandu.product.model.BaseCompany;
import com.sandu.supplydemand.dao.BaseSupplyDemandMapper;
import com.sandu.supplydemand.dao.SupplyDemandCategoryMapper;
import com.sandu.supplydemand.dao.SupplyDemandPicMapper;
import com.sandu.supplydemand.input.BaseSupplyDemandAdd;
import com.sandu.supplydemand.input.DemandAdd;
import com.sandu.supplydemand.model.BaseSupplyDemand;
import com.sandu.supplydemand.model.DemandInfoRel;
import com.sandu.supplydemand.model.SupplyDemandPic;
import com.sandu.supplydemand.output.DemandOut;
import com.sandu.supplydemand.output.SupplyDemandCategoryVo;
import com.sandu.supplydemand.output.SupplyDemandDetailVo;
import com.sandu.supplydemand.service.SupplyDemandService;
import com.sandu.system.model.ResHousePic;
import com.sandu.system.service.NodeInfoBizService;
import com.sandu.system.service.ResHousePicService;
import com.sandu.system.service.ResRenderPicService;
import com.sandu.user.model.CompanyShopVo;
import com.sandu.user.model.UserRoleContants;
import com.sandu.user.model.UserVo;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserFinanceService;
import com.sandu.user.service.UserReviewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author weisheng
 * @Title: 供求信息接口实现类
 * @Package 供求信息接口实现类
 * @Description:
 * @date 2018/4/27 0027PM 6:02
 */
@Slf4j
@Service("supplyDemandService")
public class SupplyDemandServiceImpl implements SupplyDemandService {
    @Autowired
    private BaseSupplyDemandMapper baseSupplyDemandMapper;
    @Autowired
    private SupplyDemandCategoryMapper supplyDemandCategoryMapper;
    @Autowired
    private SupplyDemandPicMapper supplyDemandPicMapper;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserFinanceService userFinanceService;

    @Autowired
    private DesignPlanRenderSceneService designPlanRenderSceneService;

    @Autowired
    private FullHouseDesignPlanService fullHouseDesignPlanService;

    @Autowired
    private ResRenderPicService resRenderPicService;

    @Autowired
    private ResHousePicService resHousePicService;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private DesignTempletService designTempletService;

    @Autowired
    private NodeInfoBizService nodeInfoBizService;

    @Autowired
    private HouseSpaceNewService houseSpaceNewService;

    @Autowired
    private UserReviewsService userReviewsService;

    @Override
    public List<BaseSupplyDemand> getAllSupplyDemandInfo(BaseSupplyDemandAdd baseSupplyDemandAdd) {


        List<BaseSupplyDemand> baseSupplyDemandList = baseSupplyDemandMapper.selectList(baseSupplyDemandAdd);
        if (baseSupplyDemandList == null || baseSupplyDemandList.size() == 0) {
            return null;
        }
        for (BaseSupplyDemand baseSupplyDemand : baseSupplyDemandList) {
            //获取供求信息分类
            String supplyDemandCategoryId = baseSupplyDemand.getSupplyDemandCategoryId();
            List<Integer> categoryIdList = Utils.getIntegerListFromStringList(supplyDemandCategoryId, ",");
            if (categoryIdList != null && categoryIdList.size() > 0) {
                List<SupplyDemandCategoryVo> supplyDemandCategoryVoList = supplyDemandCategoryMapper.selectCategoryName(categoryIdList);
                for (SupplyDemandCategoryVo supplyDemandCategoryVo : supplyDemandCategoryVoList) {
                    if (supplyDemandCategoryVo.getLevel() == 1) {
                        baseSupplyDemand.setSupplyDemandCategoryName(supplyDemandCategoryVo.getName());
                        baseSupplyDemand.setSupplyDemandCategoryPicPath(supplyDemandCategoryVo.getPicPath());
                    } else if (supplyDemandCategoryVo.getLevel() == 2) {
                        baseSupplyDemand.setSupplyDemandSmallCategoryName(supplyDemandCategoryVo.getName());
                        baseSupplyDemand.setSupplyDemandCategoryPicPath(supplyDemandCategoryVo.getPicPath());
                    }
                }
            }

            //获取评论数量
            int reviewsCount = userReviewsService.getAllUserReviewsCount(baseSupplyDemand.getId(), 0,0);
            if(reviewsCount >0 ){
                baseSupplyDemand.setReviewsCount(reviewsCount);
            }

            //获取供求信息图片列表,取第一个为封面图,没有封面图就取方案封面图,否则取户型封面图
            List<String> picPaths = new ArrayList<>();
            String coverPicIds = baseSupplyDemand.getCoverPicId();
            Integer planId = 0;
            Integer planType = 0;
            Integer houseId = 0;
            ResRenderPic resRenderPic = new ResRenderPic();
            ResHousePic resHousePic = new ResHousePic();
            List<SupplyDemandPic> supplyDemandPicList = new ArrayList<>();
            List<Integer> coverPicIdList = Utils.getIntegerListFromStringList(coverPicIds, ",");

            //优先取供求信息图片作为封面图
            if (coverPicIdList != null && coverPicIdList.size() > 0) {
                supplyDemandPicList = supplyDemandPicMapper.selectSupplyDemandPic(coverPicIdList);
            }
            List<DemandInfoRel> demandInfoRels = baseSupplyDemandMapper.selectDemandInfoRelBySupplyDemandId(baseSupplyDemand.getId());
            if (demandInfoRels != null && demandInfoRels.size() > 0) {
                planId = demandInfoRels.get(0).getPlanId();
                planType = demandInfoRels.get(0).getPlanType();
                houseId = demandInfoRels.get(0).getHouseId();
            }

            if (planId != null && planId > 0 && planType > 0) {
                //1:单空间推荐方案.2:全屋单推荐方案,3:单空间我的设计方案,4:全屋我的设计方案
                switch (planType) {
                    case 1:
                        resRenderPic = resRenderPicService.getSingleSpaceCoverResRenderPic(planId);
                        break;

                    case 2:
                    case 4:
                        resRenderPic = resRenderPicService.getFullHouseCoverResRenderPic(planId);
                        break;

                    case 3:
                        resRenderPic = resRenderPicService.getMyPlanCoverResRenderPic(planId);
                        break;

                    default:
                        log.error("错误的方案类型:"+"planType:"+planType+"--------------"+"planId"+planId);
                        break;
                }

            }
            if (houseId != null && houseId > 0) {
                BaseHouse baseHouse = new BaseHouse();
                if (null != baseSupplyDemand.getHouseId() && baseSupplyDemand.getHouseId() > 0) {
                    baseHouse = userFinanceService.queryUserUsedHouseDetailById(baseSupplyDemand.getHouseId());
                }
                if (null != baseHouse) {
                    // 取户型的缩略图和大图,优先户型绘制的图
                    if (baseHouse.getSnapPicId() != null && baseHouse.getSnapPicId() > 0) {
                        // 截图图片处理
                       resHousePic = resHousePicService.get(baseHouse.getSnapPicId());
                    /*    if(null != resHousePic){
                            SupplyDemandPic supplyDemandPic = new SupplyDemandPic();
                            supplyDemandPic.setPicPath(resHousePic.getPicPath());
                            baseSupplyDemand.setSupplyDemandCoverPic(supplyDemandPic);
                            picPaths.add(resHousePic.getPicPath());
                        }*/

                    } else if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() > 0) {
                         resHousePic = resHousePicService.get(baseHouse.getPicRes1Id());
               /*         if(null != resHousePic){
                            SupplyDemandPic supplyDemandPic = new SupplyDemandPic();
                            supplyDemandPic.setPicPath(resHousePic.getPicPath());
                            baseSupplyDemand.setSupplyDemandCoverPic(supplyDemandPic);
                            picPaths.add(resHousePic.getPicPath());
                        }*/
                    }

                }
            }

            if( null != supplyDemandPicList && supplyDemandPicList.size() >0){
                baseSupplyDemand.setSupplyDemandCoverPic(supplyDemandPicList.get(0));
                baseSupplyDemand.setSupplyDemandPicList(supplyDemandPicList);
                for(SupplyDemandPic supplyDemandPic : supplyDemandPicList){
                    picPaths.add(supplyDemandPic.getPicPath());
                }

            }
            if( null != resRenderPic && StringUtils.isNotBlank(resRenderPic.getPicPath())){
                SupplyDemandPic renderPic = new SupplyDemandPic();
                renderPic.setPicPath(resRenderPic.getPicPath());
                baseSupplyDemand.setSupplyDemandCoverPic(renderPic);
                picPaths.add(resRenderPic.getPicPath());
            }

            if ( null != resHousePic && StringUtils.isNotBlank(resHousePic.getPicPath())){
                SupplyDemandPic housePic = new SupplyDemandPic();
                housePic.setPicPath(resHousePic.getPicPath());
                baseSupplyDemand.setSupplyDemandCoverPic(housePic);
                picPaths.add(resHousePic.getPicPath());
            }

            baseSupplyDemand.setPicPathList(picPaths);


            baseSupplyDemand.setPublisher(baseSupplyDemand.getContact());






            //发布者有店铺的，显示店铺logo。无店铺的显示个人头像，均无完善时，显示默认头像
            /*List<CompanyShopVo> companyShopVoList = sysUserService.getCompanyShop(baseSupplyDemand.getCreatorId());
            if (companyShopVoList != null && companyShopVoList.size() > 0) {
                baseSupplyDemand.setUserPicPath(companyShopVoList.get(0).getCompanyShopPicPath());
            }*/

            int userType = baseSupplyDemand.getCreatorTypeValue().intValue();
            //当用户类型为经销商,厂商,装修公司,设计公司,工长的时候查询公司信息
            if (userType == UserRoleContants.DEALERS ||
                    userType == UserRoleContants.FIRM ||
                    userType == UserRoleContants.DESIGNER_COMPANY ||
                    userType == UserRoleContants.DECORATE_COMPANY ||
                    userType == UserRoleContants.FOREMAN) {
                BaseCompany baseCompany = sysUserService.getCompanyInfo(baseSupplyDemand.getCreatorId());
                if (baseCompany != null) {
                    baseSupplyDemand.setNickName(baseCompany.getCompanyName());
                    if (StringUtils.isEmpty(baseSupplyDemand.getUserPicPath())) {
                        baseSupplyDemand.setUserPicPath(baseCompany.getCompanyLogoPicPath());
                    }
                }

                //当用户类型为设计师,业主,中介的时候查询个人信息
            } else if (userType == UserRoleContants.DESIGNER ||
                    userType == UserRoleContants.COMMON ||
                    userType == UserRoleContants.MEDIATION) {
                UserVo userVo = sysUserService.getUserInfo(baseSupplyDemand.getCreatorId());
                if (userVo != null) {
                    baseSupplyDemand.setNickName(userVo.getUserName());
                    if (StringUtils.isEmpty(baseSupplyDemand.getUserPicPath())) {
                        baseSupplyDemand.setUserPicPath(userVo.getPicPath());
                    }
                }

            }

            //获取创建者的uuid
            UserVo userVo = sysUserService.getUserInfo(baseSupplyDemand.getCreatorId());
            if (userVo != null) {
                baseSupplyDemand.setSessionId(userVo.getUuid());
                baseSupplyDemand.setUserPicPath(userVo.getPicPath());
                baseSupplyDemand.setUserHeadPic(userVo.getPicPath());
            }

            if (StringUtils.isEmpty(baseSupplyDemand.getUserHeadPic()) || StringUtils.isEmpty(baseSupplyDemand.getUserPicPath())) {

                if (baseSupplyDemand.getSex() == null || baseSupplyDemand.getSex() == 0 || baseSupplyDemand.getSex() == 1) {
                    String senderPic = sysUserService.getUserDefaultPic(1);
                    baseSupplyDemand.setUserHeadPic(senderPic);
                    baseSupplyDemand.setUserPicPath(senderPic);
                } else if (baseSupplyDemand.getSex() != null && baseSupplyDemand.getSex() == 2) {
                    String senderPic = sysUserService.getUserDefaultPic(2);
                    baseSupplyDemand.setUserHeadPic(senderPic);
                    baseSupplyDemand.setUserPicPath(senderPic);
                }

            }


        }
        return baseSupplyDemandList;
    }

    @Override
    public Integer getAllSupplyDemandCount(BaseSupplyDemandAdd baseSupplyDemandAdd) {

        return baseSupplyDemandMapper.selectListCount(baseSupplyDemandAdd);
    }

    @Override
    @Transactional
    public Integer updateMySupplyDemandStatus(BaseSupplyDemandAdd baseSupplyDemandAdd) {
        BaseSupplyDemand baseSupplyDemand = new BaseSupplyDemand();
        baseSupplyDemand.setPushStatus(baseSupplyDemandAdd.getPushStatus());
        baseSupplyDemand.setId(baseSupplyDemandAdd.getSupplyDemandId());
        baseSupplyDemand.setCreatorId(baseSupplyDemandAdd.getCreatorId());
        Date date = new Date();
        baseSupplyDemand.setGmtModified(date);
        baseSupplyDemand.setGmtPublish(date);
        return baseSupplyDemandMapper.updateByPrimaryKeySelective(baseSupplyDemand);
    }

    @Override
    public BaseSupplyDemand getSupplyDemandInfo(Integer supplyDemandId) {
        BaseSupplyDemand baseSupplyDemand = getBaseSupplyDemandInfo(supplyDemandId);
        if (baseSupplyDemand == null) {
            return null;
        }
        return baseSupplyDemand;
    }

    private BaseSupplyDemand getBaseSupplyDemandInfo(Integer supplyDemandId) {
        BaseSupplyDemand baseSupplyDemand = baseSupplyDemandMapper.selectSupplyDemandInfoByid(supplyDemandId);
        if (baseSupplyDemand == null) {
            return null;
        }
        //获取供求信息分类
        String supplyDemandCategoryId = baseSupplyDemand.getSupplyDemandCategoryId();
        List<Integer> categoryIdList = Utils.getIntegerListFromStringList(supplyDemandCategoryId, ",");
        if (categoryIdList != null && categoryIdList.size() > 0) {
            List<SupplyDemandCategoryVo> supplyDemandCategoryVoList = supplyDemandCategoryMapper.selectCategoryName(categoryIdList);
            for (SupplyDemandCategoryVo supplyDemandCategoryVo : supplyDemandCategoryVoList) {
                if (supplyDemandCategoryVo.getLevel() == 1) {
                    baseSupplyDemand.setSupplyDemandCategoryName(supplyDemandCategoryVo.getName());
                    baseSupplyDemand.setSupplyDemandCategoryPicPath(supplyDemandCategoryVo.getPicPath());
                } else if (supplyDemandCategoryVo.getLevel() == 2) {
                    baseSupplyDemand.setSupplyDemandSmallCategoryName(supplyDemandCategoryVo.getName());
                    baseSupplyDemand.setSupplyDemandCategoryPicPath(supplyDemandCategoryVo.getPicPath());
                }
            }
        }

        //获取供求信息图片列表,取第一个为封面图
        String coverPicIds = baseSupplyDemand.getCoverPicId();
        List<Integer> coverPicIdList = Utils.getIntegerListFromStringList(coverPicIds, ",");
        if (coverPicIdList != null && coverPicIdList.size() > 0) {
            List<SupplyDemandPic> supplyDemandPicList = supplyDemandPicMapper.selectSupplyDemandPic(coverPicIdList);
            if (supplyDemandPicList != null && supplyDemandPicList.size() > 0) {
                baseSupplyDemand.setSupplyDemandPicList(supplyDemandPicList);
                baseSupplyDemand.setSupplyDemandCoverPic(supplyDemandPicList.get(0));
            }
        }
        baseSupplyDemand.setPublisher(baseSupplyDemand.getContact());
        int userType = baseSupplyDemand.getCreatorTypeValue().intValue();
        //当用户类型为经销商,厂商,装修公司,设计公司,工长的时候查询公司信息
        if (userType == UserRoleContants.DEALERS ||
                userType == UserRoleContants.FIRM ||
                userType == UserRoleContants.DESIGNER_COMPANY ||
                userType == UserRoleContants.DECORATE_COMPANY ||
                userType == UserRoleContants.FOREMAN) {
            BaseCompany baseCompany = sysUserService.getCompanyInfo(baseSupplyDemand.getCreatorId());
            if (null != baseCompany) {
                baseSupplyDemand.setNickName(baseCompany.getCompanyName());
                baseSupplyDemand.setUserPicPath(baseCompany.getCompanyLogoPicPath());
            }

            //当用户类型为设计师,业主,中介的时候查询个人信息
        } else if (userType == UserRoleContants.DESIGNER ||
                userType == UserRoleContants.COMMON ||
                userType == UserRoleContants.MEDIATION) {
            UserVo userVo = sysUserService.getUserInfo(baseSupplyDemand.getCreatorId());
            if (null != userVo) {
                baseSupplyDemand.setNickName(userVo.getNickName());
                baseSupplyDemand.setUserPicPath(userVo.getPicPath());
            }


        }

        //取默认头像
        if (StringUtils.isEmpty(baseSupplyDemand.getUserPicPath())) {

            if (baseSupplyDemand.getSex() == null || baseSupplyDemand.getSex() == 0 || baseSupplyDemand.getSex() == 1) {
                String senderPic = sysUserService.getUserDefaultPic(1);
                baseSupplyDemand.setUserHeadPic(senderPic);
                baseSupplyDemand.setUserPicPath(senderPic);
            } else if (baseSupplyDemand.getSex() != null && baseSupplyDemand.getSex() == 2) {
                String senderPic = sysUserService.getUserDefaultPic(2);
                baseSupplyDemand.setUserHeadPic(senderPic);
                baseSupplyDemand.setUserPicPath(senderPic);
            }

        }

        return baseSupplyDemand;
    }

    @Override
    @Transactional
    public Integer updateSupplyDemandInfo(BaseSupplyDemand baseSupplyDemand) {
        Date date = new Date();
        baseSupplyDemand.setPushStatus(0);
        baseSupplyDemand.setGmtModified(date);
        baseSupplyDemand.setGmtPublish(date);
        baseSupplyDemandMapper.updateByPrimaryKeySelective(baseSupplyDemand);

        //记录供求信息与方案和户型关联的数据
        if (baseSupplyDemand.getId().intValue() > 0 && ( (baseSupplyDemand.getPlanId() !=null && baseSupplyDemand.getPlanId()>0)   ||
                (baseSupplyDemand.getHouseId() !=null  &&  baseSupplyDemand.getHouseId()>0 ) )) {
            //先判断是否记录过此供求信息的关联记录,记录过就修改,没有就新增
            List<DemandInfoRel> demandInfoRelList = baseSupplyDemandMapper.selectDemandInfoRelBySupplyDemandId(baseSupplyDemand.getId());
            if (null != demandInfoRelList && demandInfoRelList.size() > 0) {
                DemandInfoRel demandInfoRel = demandInfoRelList.get(0);
                demandInfoRel.setGmtModified(date);
                demandInfoRel.setModifier(baseSupplyDemand.getModifier());
                if (baseSupplyDemand.getPlanId() != null && baseSupplyDemand.getPlanId() > 0) {
                    demandInfoRel.setPlanId(baseSupplyDemand.getPlanId());
                    demandInfoRel.setPlanType(baseSupplyDemand.getPlanType());
                }
                if (baseSupplyDemand.getHouseId() != null && baseSupplyDemand.getHouseId() > 0) {
                    demandInfoRel.setHouseId(baseSupplyDemand.getHouseId());
                }
                baseSupplyDemandMapper.updateDemandInfoRel(demandInfoRel);
            } else {
                DemandInfoRel demandInfoRel = new DemandInfoRel();
                demandInfoRel.setModifier(baseSupplyDemand.getModifier());
                demandInfoRel.setGmtCreate(date);
                demandInfoRel.setGmtModified(date);
                demandInfoRel.setCreator(baseSupplyDemand.getModifier());
                demandInfoRel.setSupplyDemandId(baseSupplyDemand.getId());
                demandInfoRel.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                if (baseSupplyDemand.getPlanId() != null && baseSupplyDemand.getPlanId() > 0) {
                    demandInfoRel.setPlanId(baseSupplyDemand.getPlanId());
                    demandInfoRel.setPlanType(baseSupplyDemand.getPlanType());
                }
                if (baseSupplyDemand.getHouseId() != null && baseSupplyDemand.getHouseId() > 0) {
                    demandInfoRel.setHouseId(baseSupplyDemand.getHouseId());
                }
                baseSupplyDemandMapper.insertDemandInfoRel(demandInfoRel);
            }
        }

        return baseSupplyDemand.getId();
    }

    @Override
    public Integer updateSupplyDemandInfoInMiniProgram(BaseSupplyDemand baseSupplyDemand) {
        Date date = new Date();
        baseSupplyDemand.setPushStatus(0);
        baseSupplyDemand.setGmtModified(date);
        baseSupplyDemand.setGmtPublish(date);
        baseSupplyDemandMapper.updateByPrimaryKeySelective(baseSupplyDemand);

        List<DemandInfoRel> demandInfoRelList = baseSupplyDemandMapper.selectDemandInfoRelBySupplyDemandId(baseSupplyDemand.getId());
        if (demandInfoRelList != null && demandInfoRelList.size() > 0){
            DemandInfoRel demandInfoRel = demandInfoRelList.get(0);
            demandInfoRel.setGmtModified(date);
            demandInfoRel.setModifier(baseSupplyDemand.getModifier());
            demandInfoRel.setPlanId(baseSupplyDemand.getPlanId() == null ? 0 : baseSupplyDemand.getPlanId());
            demandInfoRel.setPlanType(baseSupplyDemand.getPlanType() == null ? 0 : baseSupplyDemand.getPlanType());
            demandInfoRel.setHouseId(baseSupplyDemand.getHouseId() == null ? 0 : baseSupplyDemand.getHouseId());
            baseSupplyDemandMapper.updateDemandInfoRel(demandInfoRel);
        }else {
            if ((baseSupplyDemand.getPlanId() != null && baseSupplyDemand.getPlanType() != null) ||
                    baseSupplyDemand.getHouseId() != null){
                DemandInfoRel demandInfoRel = new DemandInfoRel();
                demandInfoRel.setModifier(baseSupplyDemand.getModifier());
                demandInfoRel.setGmtCreate(date);
                demandInfoRel.setGmtModified(date);
                demandInfoRel.setCreator(baseSupplyDemand.getModifier());
                demandInfoRel.setSupplyDemandId(baseSupplyDemand.getId());
                demandInfoRel.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                demandInfoRel.setPlanId(baseSupplyDemand.getPlanId());
                demandInfoRel.setHouseId(baseSupplyDemand.getHouseId());
                demandInfoRel.setPlanType(baseSupplyDemand.getPlanType());
                baseSupplyDemandMapper.insertDemandInfoRel(demandInfoRel);
            }
        }
        return 1;
    }

    @Override
    public Integer addSupplyDemandInfo(BaseSupplyDemand baseSupplyDemand) {
        Date date = new Date();
        baseSupplyDemand.setPushStatus(0);
        baseSupplyDemand.setGmtCreate(date);
        baseSupplyDemand.setIsDeleted(0);
        baseSupplyDemand.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        baseSupplyDemand.setGmtModified(date);
        baseSupplyDemand.setGmtPublish(date);
        baseSupplyDemandMapper.insert(baseSupplyDemand);
        return baseSupplyDemand.getId();
    }

    @Override
    public List<String> getAllSupplyDemandCategoryByOneCategory(Integer integer) {
        return supplyDemandCategoryMapper.getAllSupplyDemandCategoryByOneCategory(integer);
    }

    @Override
    public Integer updateSupplyDemandInfoViewNum(BaseSupplyDemand newBaseSupplyDemand) {
        baseSupplyDemandMapper.updateByPrimaryKeySelective(newBaseSupplyDemand);
        return newBaseSupplyDemand.getId();
    }

    @Override
    @Transactional
    public DemandOut publishSupplyDemandInfo(DemandAdd demandAdd, UserVo userVo) {

        DemandOut out = new DemandOut();

        //保存基础供求信息到数据库
        BaseSupplyDemand baseSupplyDemand = this.buildBaseSupplyDemandInfo(demandAdd, userVo);
        baseSupplyDemandMapper.insert(baseSupplyDemand);

        //封装供求信息ID到返回实体
        if (null != baseSupplyDemand && baseSupplyDemand.getId() > 0) {
            out.setSupplyDemandId(baseSupplyDemand.getId());
        } else {
            return null;
        }

        //当传入方案ID或户型ID存在时保存需求需求信息详情到数据库
        if ((null != demandAdd.getPlanId() && demandAdd.getPlanId() > 0) || (null != demandAdd.getHouseId() && demandAdd.getHouseId() > 0)) {
            DemandInfoRel demandInfoRel = this.buildDemandInfoRel(demandAdd, userVo, baseSupplyDemand.getId());
            baseSupplyDemandMapper.insertDemandInfoRel(demandInfoRel);
            //封装需求信息详情到返回实体
            if (null != demandInfoRel && demandInfoRel.getId() > 0) {
                out.setDemandInfoRelId(demandInfoRel.getId());
            } else {
                return null;
            }
        }

        return out;
    }

    @Override
    public SupplyDemandDetailVo getSupplyDemandDetailVo(Integer supplyDemandId, Integer userId) {
        BaseSupplyDemand baseSupplyDemand = getBaseSupplyDemandInfo(supplyDemandId);


        if (baseSupplyDemand == null) {
            log.info("该帖子不存在"+supplyDemandId);
            return null;
        }





        //封装返回信息
        SupplyDemandDetailVo supplyDemandDetailVo = new SupplyDemandDetailVo();
        supplyDemandDetailVo.setType(baseSupplyDemand.getSupplyDemandCategoryName());
        String address = (baseSupplyDemand.getProvinceName() == null ? "" : baseSupplyDemand.getProvinceName())
                + (baseSupplyDemand.getCityName() == null ? "" : baseSupplyDemand.getCityName())
                + (baseSupplyDemand.getDistrictName() == null ? "" : baseSupplyDemand.getDistrictName())
                + (baseSupplyDemand.getStreetName() == null ? "" : baseSupplyDemand.getStreetName())
                + (baseSupplyDemand.getAddress() == null ? "" : baseSupplyDemand.getAddress());
        supplyDemandDetailVo.setAddress(address);
        if(StringUtils.isBlank(supplyDemandDetailVo.getAddress())){
            supplyDemandDetailVo.setAddress("全国");
        }
        supplyDemandDetailVo.setTitle(baseSupplyDemand.getTitle());
        supplyDemandDetailVo.setId(baseSupplyDemand.getId());
        supplyDemandDetailVo.setDescription(baseSupplyDemand.getDescription());
        supplyDemandDetailVo.setGmtPublish(baseSupplyDemand.getGmtModified());
        supplyDemandDetailVo.setNickName(baseSupplyDemand.getNickName());
        supplyDemandDetailVo.setUserPicPath(baseSupplyDemand.getUserPicPath());
        supplyDemandDetailVo.setUserId(baseSupplyDemand.getCreatorId());
        if(baseSupplyDemand.getPlanType()!=null && baseSupplyDemand.getPlanType()>0){
            supplyDemandDetailVo.setPlanType(baseSupplyDemand.getPlanType());
        }
        supplyDemandDetailVo.setViewNum(baseSupplyDemand.getViewNum());

        //处理该登录用户是否具备删除和编辑此信息权限
        if(baseSupplyDemand.getCreatorId() == null || baseSupplyDemand.getCreatorId() == 0 ||  baseSupplyDemand.getCreatorId().intValue() !=userId.intValue()){
            supplyDemandDetailVo.setEditAuthor(false);
            supplyDemandDetailVo.setDelAuthor(false);
        } else{
            supplyDemandDetailVo.setEditAuthor(true);
            supplyDemandDetailVo.setDelAuthor(true);
        }


        // 点赞收藏
      Map<String, Object> map = nodeInfoBizService.getNodeData(supplyDemandDetailVo.getId(), NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_SUPPLY_DEMAND, userId);
        if(null !=map && map.size() > 0){
            supplyDemandDetailVo.setFavoriteNum((Integer)map.get("setFavoriteNum"));
            supplyDemandDetailVo.setLikeNum((Integer)map.get("setLikeNum"));
            supplyDemandDetailVo.setCommentNum((Integer)map.get("setCommentNum"));
            supplyDemandDetailVo.setIsFavorite((Integer)map.get("setIsFavorite"));
            supplyDemandDetailVo.setIsLike((Integer)map.get("setIsLike"));
        }

        //获取方案详情
        DesignPlanRecommendedResult designPlanRecommendedResult;
        if (baseSupplyDemand.getPlanType() != null && baseSupplyDemand.getPlanId() != null && baseSupplyDemand.getPlanId() > 0) {
            //方案类型,1:单空间推荐方案.2:全屋推荐方案,3:单空间我的设计方案,4:全屋我的设计方案
            switch (baseSupplyDemand.getPlanType()) {
                case 1:
                    designPlanRecommendedResult = baseSupplyDemandMapper.getDesignPlanRecommendedInfo(baseSupplyDemand.getPlanId());
                    if (null != designPlanRecommendedResult) {
                        //获取用户购买方案版权记录
                        List<Integer> designPlanIds = baseSupplyDemandMapper.selectDesignPlanIdsFromRecord(userId);
                        if (designPlanRecommendedResult.getChargeType().intValue() == 1 && !designPlanIds.contains(designPlanRecommendedResult.getPlanRecommendedId())) {
                            designPlanRecommendedResult.setCopyRightPermission(1);
                        } else {
                            //免费方案,无需购买版权
                            designPlanRecommendedResult.setCopyRightPermission(0);
                        }
                        if (designPlanIds.contains(designPlanRecommendedResult.getPlanRecommendedId())) {
                            designPlanRecommendedResult.setHavePurchased(1);
                        } else {
                            designPlanRecommendedResult.setHavePurchased(0);
                        }
                        //获取店铺ID已经设计师头像
                        if (designPlanRecommendedResult.getShops() != null && designPlanRecommendedResult.getShops().size() > 0) {
                            designPlanRecommendedResult.setShopId(designPlanRecommendedResult.getShops().get(0).getShopId());
                            designPlanRecommendedResult.setDesignerHeadPic(designPlanRecommendedResult.getShops().get(0).getLogo());
                        }

                        supplyDemandDetailVo.setDesignPlanRecommendedResult(designPlanRecommendedResult);
                    }


                    break;
                case 2:
                case 4:
                    designPlanRecommendedResult = fullHouseDesignPlanService.selectFullHouseDesignPlanDetailById(baseSupplyDemand.getPlanId(), userId);
                    supplyDemandDetailVo.setDesignPlanRecommendedResult(designPlanRecommendedResult);
                    break;
                case 3:
                    MydecorationPlanVo mydecorationPlanVo = designPlanRenderSceneService.selectDesignPlanInfo(baseSupplyDemand.getPlanId());
                    if (null == mydecorationPlanVo) {
                        break;
                    }
                    //处理渲染状态
                    mydecorationPlanVo.setPlanRenderType(1);
                    if ("1".equals(mydecorationPlanVo.getRenderTypesStr())) {
                        mydecorationPlanVo.setIsSuccess(mydecorationPlanVo.getRenderPic());
                    } else if ("2".equals(mydecorationPlanVo.getRenderTypesStr())) {
                        mydecorationPlanVo.setIsSuccess(mydecorationPlanVo.getRender720());
                    } else if ("4".equals(mydecorationPlanVo.getRenderTypesStr())) {
                        mydecorationPlanVo.setIsSuccess(mydecorationPlanVo.getRenderVideo());
                    } else if ("3".equals(mydecorationPlanVo.getRenderTypesStr())) {
                        mydecorationPlanVo.setIsSuccess(mydecorationPlanVo.getRenderN720());
                    }
                    //查询资源
                    ResRenderPic resRenderPic = fullHouseDesignPlanService.getResRenderCoverPicById(mydecorationPlanVo.getBusinessId());
                    if (null != resRenderPic) {
                        mydecorationPlanVo.setResRenderPicId(resRenderPic.getId());
                        mydecorationPlanVo.setPlanPicPath(resRenderPic.getPicPath());
                    }
                    //查找该方案面积
                    if(null != mydecorationPlanVo.getTemplateId()){
                        DesignTemplet designTemplet = designTempletService.getPlanStyleAndAreaByTempletId(mydecorationPlanVo.getTemplateId());
                        if(null != designTemplet){
                            mydecorationPlanVo.setSpaceArea(designTemplet.getSpaceAreas());
                        }
                    }


                    supplyDemandDetailVo.setMydecorationPlanVo(mydecorationPlanVo);
                    break;
                default:
                    log.error("该空间类型不存在" + baseSupplyDemand.getPlanType());
                    break;
            }

        }


        //获取户型详情
        BaseHouse baseHouse;
        if (null != baseSupplyDemand.getHouseId() && baseSupplyDemand.getHouseId() > 0) {
            baseHouse = userFinanceService.queryUserUsedHouseDetailById(baseSupplyDemand.getHouseId());
        } else {
            baseHouse = null;
        }
        if (null != baseHouse) {
            // 取户型的缩略图和大图,优先户型绘制的图
            if (baseHouse.getSnapPicId() != null && baseHouse.getSnapPicId() > 0) {
                // 截图图片处理
                ResHousePic resHousePic = resHousePicService.get(baseHouse.getSnapPicId());
                if(resHousePic !=null ){
                    baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
                    baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
                }

            } else if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() > 0) {
                ResHousePic resHousePic = resHousePicService.get(baseHouse.getPicRes1Id());
                if(resHousePic != null ){
                    baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getThumbnailPath() : "");
                    baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getLargeThumbnailPath() : "");
                }

            } else {
                baseHouse.setThumbnailPath("/default/huxing_bg_pic_no.png");
                baseHouse.setLargeThumbnailPath("/default/huxing_bg_pic_no.png");
            }
            String longCodeName = "";
            //查询小区的地址
            if (org.apache.commons.lang3.StringUtils.isNotBlank(baseHouse.getAreaLongCode())) {
                String longCode = baseHouse.getAreaLongCode();
                String str = longCode.substring(1, longCode.length() - 1);
                String[] split = str.split("\\.");
                List<String> list = new ArrayList<String>();
                for (String code : split) {
                    if (!list.contains(code)) {
                        list.add(code);
                        String codeName = baseAreaService.getCodeName(code);
                        longCodeName += codeName;
                    }
                }
            }
            baseHouse.setHouseAddress(longCodeName);
            // 取户型的空间定位类型
            List<String> spaceTypeList = houseSpaceNewService.getSpaceTypeListByHouseId(baseHouse.getId());
            if (!Lists.isEmpty(spaceTypeList)) {
                Map<String, Integer> elementsCount = new HashMap<String, Integer>();
                for (String s : spaceTypeList) {
                    Integer i = elementsCount.get(s);
                    if (i == null) {
                        elementsCount.put(s, 1);
                    } else {
                        elementsCount.put(s, i + 1);
                    }
                }
                baseHouse.setHouseTypeStr(((elementsCount.containsKey("3") ? elementsCount.get("3") : 0) + "厅"
                        + (elementsCount.containsKey("4") ? elementsCount.get("4") : 0)) + "室"
                        + (elementsCount.containsKey("6") ? elementsCount.get("6") : 0) + "卫"
                        + (elementsCount.containsKey("5") ? elementsCount.get("5") : 0) + "厨"
                        + (elementsCount.containsKey("7") ? elementsCount.get("7") : 0) + "书"
                        + (elementsCount.containsKey("8") ? elementsCount.get("8") : 0) + "衣"
                        + (elementsCount.containsKey("9") ? elementsCount.get("9") : 0) + "其他");

            }

            supplyDemandDetailVo.setBaseHouse(baseHouse);

        }


        //查询封面图和列表图
        String coverPicIds = baseSupplyDemand.getCoverPicId();
        List<Integer> coverPicIdList = Utils.getIntegerListFromStringList(coverPicIds, ",");
        if (coverPicIdList != null && coverPicIdList.size() > 0) {
            List<SupplyDemandPic> supplyDemandPicList = supplyDemandPicMapper.selectSupplyDemandPic(coverPicIdList);
            if (supplyDemandPicList != null && supplyDemandPicList.size() > 0) {
                supplyDemandDetailVo.setSupplyDemandPicList(supplyDemandPicList);
                supplyDemandDetailVo.setSupplyDemandCoverPic(supplyDemandPicList.get(0));
            }
        }


        return supplyDemandDetailVo;
    }

    private DemandInfoRel buildDemandInfoRel(DemandAdd demandAdd, UserVo userVo, Integer id) {
        DemandInfoRel demandInfoRel = new DemandInfoRel();
        Date date = new Date();
        demandInfoRel.setSupplyDemandId(id);
        demandInfoRel.setPlanType(demandAdd.getPlanType());
        demandInfoRel.setPlanId(demandAdd.getPlanId());
        demandInfoRel.setHouseId(demandAdd.getHouseId());
        demandInfoRel.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        demandInfoRel.setCreator(userVo.getNickName());
        demandInfoRel.setGmtCreate(date);
        demandInfoRel.setGmtModified(date);
        demandInfoRel.setModifier(userVo.getNickName());
        return demandInfoRel;
    }

    private BaseSupplyDemand buildBaseSupplyDemandInfo(DemandAdd demandAdd, UserVo userVo) {
        BaseSupplyDemand baseSupplyDemand = new BaseSupplyDemand();
        Date date = new Date();
        baseSupplyDemand.setType(2);//默认为需求
        baseSupplyDemand.setCreatorId(userVo.getUserId());
        baseSupplyDemand.setCreatorTypeValue(userVo.getUserType());
        baseSupplyDemand.setSupplyDemandCategoryId("2,5");//默认发布到家装服务下面的装修设计
        if (StringUtils.isNotBlank(demandAdd.getProvince())) {
            baseSupplyDemand.setProvince(demandAdd.getProvince());
        }
        if (StringUtils.isNotBlank(demandAdd.getCity())) {
            baseSupplyDemand.setCity(demandAdd.getCity());
        }
        baseSupplyDemand.setDistrict("");
        baseSupplyDemand.setStreet("");
        baseSupplyDemand.setAddress("");
        if (StringUtils.isNotBlank(demandAdd.getCoverPicId())) {
            baseSupplyDemand.setCoverPicId(demandAdd.getCoverPicId());
        }
        baseSupplyDemand.setTitle(demandAdd.getTitle());
        baseSupplyDemand.setDescription(demandAdd.getDescription());
        baseSupplyDemand.setDecorationCompany(1);
        baseSupplyDemand.setDesigner(1);
        baseSupplyDemand.setMaterialShop(1);
        baseSupplyDemand.setProprietor(1);
        baseSupplyDemand.setBuilder(1);
        baseSupplyDemand.setPushStatus(0);
        baseSupplyDemand.setGmtPublish(date);
        baseSupplyDemand.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        baseSupplyDemand.setCreator(userVo.getNickName());
        baseSupplyDemand.setGmtCreate(date);
        baseSupplyDemand.setModifier(userVo.getNickName());
        baseSupplyDemand.setGmtModified(date);
        baseSupplyDemand.setIsDeleted(0);
        baseSupplyDemand.setContact(userVo.getNickName());
        baseSupplyDemand.setPhone(userVo.getMobile());
        baseSupplyDemand.setRecommendedTime(date.getTime());
        return baseSupplyDemand;
    }

    @Override
    public List<DemandInfoRel> selectDemandInfoRelBySupplyDemandId(Integer businessId) {
        return baseSupplyDemandMapper.selectDemandInfoRelBySupplyDemandId(businessId);
    }

    @Override
    public List<SupplyDemandPic> selectSupplyDemandPic(List<Integer> coverPicIdList) {
        return supplyDemandPicMapper.selectSupplyDemandPic(coverPicIdList);
    }

    @Override
    public int delSupplyDemandById(Integer supplyDemandId, Integer userId) {
        return baseSupplyDemandMapper.delSupplyDemandById(supplyDemandId,userId);
    }
}
