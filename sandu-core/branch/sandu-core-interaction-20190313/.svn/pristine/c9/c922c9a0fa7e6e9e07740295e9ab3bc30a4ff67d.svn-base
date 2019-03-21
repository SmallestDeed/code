package com.sandu.service.fullhouse.impl.abstracts;

import com.google.gson.Gson;
import com.sandu.api.base.common.exception.BizExceptionEE;
import com.sandu.api.base.model.BaseBrand;
import com.sandu.api.base.model.BaseProductStyle;
import com.sandu.api.base.model.SysUser;
import com.sandu.api.base.service.BaseBrandService;
import com.sandu.api.base.service.BaseProductStyleService;
import com.sandu.api.base.service.SysUserService;
import com.sandu.api.designplan.common.constants.ResRenderPicConstant;
import com.sandu.api.designplan.service.ResRenderPicService;
import com.sandu.api.fullhouse.common.constant.FullHouseConstant;
import com.sandu.api.fullhouse.common.constant.VrMakeConstant;
import com.sandu.api.fullhouse.common.exception.FullHouseException;
import com.sandu.api.fullhouse.input.DesignPlanStyleQuery;
import com.sandu.api.fullhouse.input.FullHouseDesignPlanQuery;
import com.sandu.api.fullhouse.model.FullHouseDesignPlan;
import com.sandu.api.fullhouse.model.FullHouseDesignPlanDetail;
import com.sandu.api.fullhouse.output.DesignPlanStyleVO;
import com.sandu.api.fullhouse.output.FullHouseDesignPlanVO;
import com.sandu.api.fullhouse.output.MatchInfoVO;
import com.sandu.api.fullhouse.service.FullHouseDesignPlanService;
import com.sandu.api.fullhouse.service.biz.FullHouseBizService;
import com.sandu.common.model.LoginUser;
import com.sandu.panorama.model.input.DesignPlanStoreReleaseAdd;
import com.sandu.panorama.model.input.DesignPlanStoreReleaseDetailsAdd;
import com.sandu.panorama.model.output.MakeDesignPlanStoreReleaseResultVo;
import com.sandu.panorama.service.DesignPlanStoreReleaseService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2(topic = "[全屋方案业务服务]")
public abstract class AbstractFullHouseBizService implements FullHouseBizService {
    @Autowired
    private BaseProductStyleService baseProductStyleService;
    @Autowired
    private FullHouseDesignPlanService fullHouseDesignPlanService;
    @Autowired
    private DesignPlanStoreReleaseService designPlanStoreReleaseService;
    @Autowired
    private BaseBrandService baseBrandService;

    private static final int DEFAULT_CURRENT_PAGE = 1;
    private static final int DEFAULT_FULL_HOUSE_DESIGN_PLAN_PAGE_SIZE = 10;
    private static final int DEFAULT_START = 0;
    private static final Gson gson = new Gson();

    /**
     * created by zhangchengda
     * 2018/8/16 17:22
     * 查询任意空间类型下所有的方案风格
     *
     * @param query 查询参数
     * @return 该空间类型下的所有方案风格
     */
    @Override
    public List<DesignPlanStyleVO> selectDesignPlanStyle(DesignPlanStyleQuery query) {
        // 处理查询参数，找到对应空间类型的方案风格节点code
        // 例：客餐厅空间类型找到方案风格为客餐厅的code："kecanting"
        String parentCode = null;
        switch (query.getSpaceType()) {
            case FullHouseConstant.SPACE_TYPE_FULL_HOUSE:
                parentCode = FullHouseConstant.DESIGN_PLAN_STYLE_CODE_FULL_HOUSE;
                break;
            case FullHouseConstant.SPACE_TYPE_LIVING_DINING_ROOM:
                parentCode = FullHouseConstant.DESIGN_PLAN_STYLE_CODE_LIVING_DINING_ROOM;
                break;
            case FullHouseConstant.SPACE_TYPE_BEDROOM:
                parentCode = FullHouseConstant.DESIGN_PLAN_STYLE_CODE_BEDROOM;
                break;
            case FullHouseConstant.SPACE_TYPE_KITCHEN:
                parentCode = FullHouseConstant.DESIGN_PLAN_STYLE_CODE_KITCHEN;
                break;
            case FullHouseConstant.SPACE_TYPE_TOILET:
                parentCode = FullHouseConstant.DESIGN_PLAN_STYLE_CODE_TOILET;
                break;
            case FullHouseConstant.SPACE_TYPE_SCHOOLROOM:
                parentCode = FullHouseConstant.DESIGN_PLAN_STYLE_CODE_SCHOOLROOM;
                break;
            case FullHouseConstant.SPACE_TYPE_CLOAKROOM:
                parentCode = FullHouseConstant.DESIGN_PLAN_STYLE_CODE_CLOAKROOM;
                break;
            default:
                return null;
        }
        // 通过父节点的code查询当前空间类型方案的风格
        log.info("查询当前空间类型的所有方案风格,风格code:{}", parentCode);
        List<BaseProductStyle> list = baseProductStyleService.selectListByParentCode(parentCode);
        // 组装返回结果
        log.info("查询方案风格结果数:{}", list.size());
        List<DesignPlanStyleVO> designPlanStyleVOList = new ArrayList<>(list.size());
        list.forEach(style -> {
            DesignPlanStyleVO designPlanStyleVO = new DesignPlanStyleVO();
            designPlanStyleVO.setDesignPlanStyleId(style.getId().intValue());
            designPlanStyleVO.setDesignPlanStyleName(style.getName());
            designPlanStyleVOList.add(designPlanStyleVO);
        });
        return designPlanStyleVOList;
    }

    /**
     * created by zhangchengda
     * 2018/8/17 10:52
     * 查询当前登录用户制作的符合查询条件的全屋方案
     *
     * @param query 查询参数
     * @return 全屋方案集合
     */
    @Override
    public List<FullHouseDesignPlanVO> selectFullHouseDesignPlan(FullHouseDesignPlanQuery query) {
        // 处理分页
        if (query.getCurPage() == null || query.getCurPage() <= 0) {
            query.setCurPage(DEFAULT_CURRENT_PAGE);
        }
        if (query.getPageSize() == null || query.getPageSize() <= 0) {
            query.setPageSize(DEFAULT_FULL_HOUSE_DESIGN_PLAN_PAGE_SIZE);
        }
        if (query.getPageSize() != null && query.getCurPage() != null) {
            query.setStart((query.getCurPage() - 1) * query.getPageSize());
        } else {
            query.setStart(DEFAULT_START);
        }
        // 查询方案
        log.info("查询当前登录用户制作的全屋方案，查询参数:{}", gson.toJson(query));
        return fullHouseDesignPlanService.selectFullHouseDesignPlan(query);
    }

    /**
     * created by zhangchengda
     * 2018/8/17 11:32
     * 查询当前登录用户制作的符合查询条件的全屋方案总数
     *
     * @param query 查询参数
     * @return 全屋方案总数
     */
    @Override
    public Integer selectFullHouseDesignPlanCount(FullHouseDesignPlanQuery query) {
        // 处理分页
        query.setCurPage(null);
        query.setPageSize(null);
        query.setStart(null);
        // 查询方案总数
        log.info("查询当前登录用户制作的符合查询条件的全屋方案总数，查询参数:{}", gson.toJson(query));
        return fullHouseDesignPlanService.selectFullHouseDesignPlanCount(query);
    }

    @Override
    public List<MatchInfoVO> getMatchInfo(Integer houseId, Integer fullHousePlanId) throws BizExceptionEE {
        return fullHouseDesignPlanService.getMatchInfo(houseId, fullHousePlanId);
    }

    /**
     * created by zhangchengda
     * 2018/8/18 17:04
     * 生成方案编码,由前缀FH + 日期 + 时间 + 4位随机数组成
     *
     * @param salt 用来生成随机编码的盐值
     * @return 方案编码
     */
    protected String generateCode(long salt) {
        // 日期、时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        LocalDateTime date = LocalDateTime.now();
        // 4位随机数
        Random random = new Random(salt);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int nextInt = random.nextInt(10);
            sb.append(nextInt);
        }
        // 返回生成的编码
        return "FH_" + formatter.format(date) + sb.toString();
    }

    /**
     * created by zhangchengda
     * 2018/9/19 14:46
     * 设置全屋方案里的单空间方案的优先级:
     * 查找当前集合中是否有同空间类型的全屋方案详情数据，第一条加进来的数据优先级最高，第二条其次，依次递减
     *
     * @param detailList                全屋方案detail集合
     * @param spaceType                 空间类型
     * @param fullHouseDesignPlanDetail 当前全屋方案detail
     */
    protected void setPriorityLevel(List<FullHouseDesignPlanDetail> detailList, int spaceType, FullHouseDesignPlanDetail fullHouseDesignPlanDetail) {
        int num = detailList.stream().filter(detail -> detail.getSpaceType().equals(spaceType)).collect(Collectors.toList()).size();
        int priorityLevel = FullHouseConstant.FULL_HOUSE_DETAIL_PRIORITY_LEVEL_HEIGHEST - FullHouseConstant.FULL_HOUSE_DETAIL_PRIORITY_LEVEL_STEP * num;
        if (priorityLevel < FullHouseConstant.FULL_HOUSE_DETAIL_PRIORITY_LEVEL_LOWEST) {
            throw new FullHouseException("方案优先级异常，priorityLevel:" + priorityLevel);
        }
        fullHouseDesignPlanDetail.setPriorityLevel(priorityLevel);
    }

    /**
     * created by zhangchengda
     * 2018/9/20 11:07
     * 获取全屋的720UUID
     *
     * @param detailList 全屋方案详情数据集合
     * @param user       当前用户
     * @param fileKey    720资源fileKey(用于区分推荐方案和效果图方案)
     * @return 720UUID
     */
    protected String getVrUuid(List<FullHouseDesignPlanDetail> detailList, SysUser user, String fileKey, Integer houseId) {
        // 720制作对象创建
        log.info("获取全屋的720UUID,detailList:{}", gson.toJson(detailList));
        DesignPlanStoreReleaseAdd designPlanStoreReleaseAdd = new DesignPlanStoreReleaseAdd();
        List<DesignPlanStoreReleaseDetailsAdd> detailsAddList = new ArrayList<>(detailList.size());
        designPlanStoreReleaseAdd.setShareTitle("全屋方案");
        designPlanStoreReleaseAdd.setShareType(VrMakeConstant.SHARE_TYPE_FULL_HOUSE);
        designPlanStoreReleaseAdd.setDesignPlanStoreReleaseDetailsAddList(detailsAddList);
        designPlanStoreReleaseAdd.setHouseId(houseId);
        // 填入数据
        detailList.forEach(fullHouseDetail -> {
            DesignPlanStoreReleaseDetailsAdd designPlanStoreReleaseDetailsAdd = new DesignPlanStoreReleaseDetailsAdd();
            designPlanStoreReleaseDetailsAdd.setDesignPlanId(fullHouseDetail.getRenderScenePlanId());
            designPlanStoreReleaseDetailsAdd.setRecommendDesignPlanId(fullHouseDetail.getRecommendedPlanGroupPrimaryId());
            // 装进我家时用的是效果图方案，全屋制作时用的是推荐方案
            if (ResRenderPicConstant.FILE_KEY_DESIGN_DESIGNPLAN_RENDER_PIC.equals(fileKey)) {
                designPlanStoreReleaseDetailsAdd.setResourceId(fullHouseDetail.getPlanRenderResourceId());
            } else if (ResRenderPicConstant.FILE_KEY_DESIGN_RECOMMENDED_RENDER_PIC.equals(fileKey)) {
                designPlanStoreReleaseDetailsAdd.setResourceId(fullHouseDetail.getPlanRecommendedResourceId());
            }
            designPlanStoreReleaseDetailsAdd.setDesignPlanType(fullHouseDetail.getSpaceType());
            designPlanStoreReleaseDetailsAdd.setRenderingType(VrMakeConstant.RENDER_TYPE_SINGLE_POINT_VR);
            designPlanStoreReleaseDetailsAdd.setIsMain(fullHouseDetail.getSpaceType() ==
                    FullHouseConstant.SPACE_TYPE_LIVING_DINING_ROOM ? 1 : 0);
            detailsAddList.add(designPlanStoreReleaseDetailsAdd);
        });
        LoginUser loginUser = new LoginUser();
        loginUser.setId(user.getId().intValue());
        loginUser.setLoginName(user.getUserName());
        // 调用720制作服务
        log.info("调用720制作服务,designPlanStoreReleaseAdd:{}, loginUser:{}, fileKey:{}",
                gson.toJson(designPlanStoreReleaseAdd),
                gson.toJson(loginUser),
                fileKey);
        MakeDesignPlanStoreReleaseResultVo back = designPlanStoreReleaseService.makePanorama(designPlanStoreReleaseAdd, loginUser);
        if (back != null) {
            String vrUuid = back.getUuid();
            return vrUuid;
        } else {
            throw new FullHouseException("720制作失败,UUID为空");
        }
    }

    /**
     * created by zhangchengda
     * 2018/11/27 11:07
     * 设置全屋方案的基础属性
     *
     * @param fullHouseDesignPlan 全屋方案对象
     * @param fullHouseName       全屋方案名称
     * @param designPlanStyleId   方案风格ID
     * @param picId               全屋方案封面图ID
     * @param sourceType          全屋方案来源类型
     * @param sourcePlanId        全屋方案来源ID
     * @param user                当前登录用户
     * @return
     */
    protected FullHouseDesignPlan setBaseAttribute(FullHouseDesignPlan fullHouseDesignPlan,
                                                   String fullHouseName,
                                                   Integer designPlanStyleId,
                                                   Integer picId,
                                                   Integer sourceType,
                                                   Integer sourcePlanId,
                                                   SysUser user) {
        // 生成UUID
        UUID uuid = UUID.randomUUID();
        fullHouseDesignPlan.setUuid(uuid.toString());
        // 生成方案编码
        fullHouseDesignPlan.setPlanCode(generateCode(user.getId() + System.currentTimeMillis()));
        // 方案名称
        fullHouseDesignPlan.setPlanName(fullHouseName);
        // 方案风格
        if (designPlanStyleId == null || designPlanStyleId == 0) {
            fullHouseDesignPlan.setPlanStyleId(0);
            fullHouseDesignPlan.setPlanStyleName("");
        } else {
            BaseProductStyle style = baseProductStyleService.selectByPrimaryKey(designPlanStyleId.longValue());
            if (style == null) {
                throw new FullHouseException("没有找到全屋方案风格");
            }
            fullHouseDesignPlan.setPlanStyleId(style.getId().intValue());
            fullHouseDesignPlan.setPlanStyleName(style.getName());
        }
        // 方案缩略图，全屋方案缩略图为客餐厅方案组合的主方案的缩略图
        fullHouseDesignPlan.setPlanPicId(picId);
        // 方案描述
        fullHouseDesignPlan.setPlanDescribe(null);
        // 设计师，公司，品牌
        fullHouseDesignPlan.setUserId(user.getId().intValue());
        if (user.getCompanyId() != null) {
            List<BaseBrand> brandList = baseBrandService.selectByCompanyId(user.getCompanyId().longValue());
            StringBuilder sb = new StringBuilder();
            brandList.forEach(baseBrand -> sb.append(baseBrand.getId() + ","));
            fullHouseDesignPlan.setCompanyId(user.getCompanyId());
            if (!StringUtils.isBlank(sb)) {
                fullHouseDesignPlan.setBrandIds(sb.toString().substring(0, sb.toString().length() - 1));
            }
        }
        // 方案来源类型
        fullHouseDesignPlan.setSourceType(sourceType);
        // 来源方案ID，内部制作时没有来源方案
        fullHouseDesignPlan.setSourcePlanId(sourcePlanId);
        // 公开状态，默认未公开
        fullHouseDesignPlan.setOpenState(FullHouseConstant.FULL_HOUSE_OPEN_STATE_NO);
        // 方案版本，默认1
        fullHouseDesignPlan.setVersion(FullHouseConstant.FULL_HOUSE_VERSION_DEFAULT);
        // 系统信息
        Date date = new Date();
        fullHouseDesignPlan.setCreator(user.getNickName());
        fullHouseDesignPlan.setGmtCreate(date);
        fullHouseDesignPlan.setModifier(user.getNickName());
        fullHouseDesignPlan.setGmtModified(date);
        fullHouseDesignPlan.setIsDeleted(FullHouseConstant.FULL_HOUSE_IS_DELETED_NO);
        fullHouseDesignPlan.setRemark(null);
        return fullHouseDesignPlan;
    }
}
