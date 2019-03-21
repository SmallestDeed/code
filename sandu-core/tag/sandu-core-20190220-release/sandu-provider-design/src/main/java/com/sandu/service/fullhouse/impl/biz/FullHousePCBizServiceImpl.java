package com.sandu.service.fullhouse.impl.biz;

import com.google.gson.Gson;
import com.sandu.api.base.common.util.EntityCopyUtils;
import com.sandu.api.base.model.SysUser;
import com.sandu.api.base.service.SysUserService;
import com.sandu.api.designplan.common.constants.ResRenderPicConstant;
import com.sandu.api.designplan.model.DesignPlanRecommended;
import com.sandu.api.designplan.model.ResRenderPic;
import com.sandu.api.designplan.service.DesignPlanRecommendedService;
import com.sandu.api.designplan.service.ResRenderPicService;
import com.sandu.api.designplan.service.biz.DesignPlanRecommendedBizService;
import com.sandu.api.fullhouse.common.constant.FullHouseConstant;
import com.sandu.api.fullhouse.common.exception.BizException;
import com.sandu.api.fullhouse.common.exception.FullHouseException;
import com.sandu.api.fullhouse.input.DesignPlanQuery;
import com.sandu.api.fullhouse.input.FullHouseDesignPlanAdd;
import com.sandu.api.fullhouse.input.FullHouseDesignPlanUpdate;
import com.sandu.api.fullhouse.model.FullHouseDesignPlan;
import com.sandu.api.fullhouse.model.FullHouseDesignPlanDetail;
import com.sandu.api.fullhouse.output.DesignPlanVO;
import com.sandu.api.fullhouse.output.FullHouseDesignPlanEditPageVO;
import com.sandu.api.fullhouse.service.FullHouseDesignPlanDetailService;
import com.sandu.api.fullhouse.service.FullHouseDesignPlanService;
import com.sandu.api.fullhouse.service.biz.FullHousePCBizService;
import com.sandu.service.fullhouse.impl.abstracts.AbstractFullHouseBizService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Log4j2(topic = "[全屋方案PC端业务服务]")
@Service("fullHousePCBizService")
public class FullHousePCBizServiceImpl extends AbstractFullHouseBizService implements FullHousePCBizService {
    @Autowired
    private DesignPlanRecommendedBizService designPlanRecommendedBizService;
    @Autowired
    private FullHouseDesignPlanService fullHouseDesignPlanService;
    @Autowired
    private FullHouseDesignPlanDetailService fullHouseDesignPlanDetailService;
    @Autowired
    private DesignPlanRecommendedService designPlanRecommendedService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ResRenderPicService resRenderPicService;

    private static final int DEFAULT_CURRENT_PAGE = 1;
    private static final int DEFAULT_DESIGN_PLAN_PAGE_SIZE = 20;
    private static final int DEFAULT_START = 0;
    private static final Gson gson = new Gson();


    /**
     * created by zhangchengda
     * 2018/8/16 12:52
     * 查询用于全屋方案制作的组合方案主方案/一键方案（空间类型为厨房时）
     *
     * @param query 查询参数
     * @return 可选方案集合
     */
    @Override
    public List<DesignPlanVO> selectSingleDesignPlan(DesignPlanQuery query) {
        // 处理分页
        if (query.getCurPage() == null || query.getCurPage() <= 0) {
            query.setCurPage(DEFAULT_CURRENT_PAGE);
        }
        if (query.getPageSize() == null || query.getPageSize() <= 0) {
            query.setPageSize(DEFAULT_DESIGN_PLAN_PAGE_SIZE);
        }
        if (query.getPageSize() != null && query.getCurPage() != null) {
            query.setStart((query.getCurPage() - 1) * query.getPageSize());
        } else {
            query.setStart(DEFAULT_START);
        }
        // 查询方案
        log.info("查询用于全屋方案制作的组合方案主方案/一键方案（空间类型为厨房时），查询参数:{}", gson.toJson(query));
        return designPlanRecommendedBizService.selectGroupPrimaryDesignPlan(query);
    }

    /**
     * created by zhangcheng
     * 2018/8/16 16:18
     * 查询用于全屋方案制作的组合方案主方案/一键方案（空间类型为厨房时）总数
     *
     * @param query 查询参数
     * @return 方案总数
     */
    @Override
    public Integer selectSingleDesignPlanCount(DesignPlanQuery query) {
        // 处理分页
        query.setCurPage(null);
        query.setPageSize(null);
        query.setStart(null);
        // 查询方案总数
        log.info("查询用于全屋方案制作的组合方案主方案/一键方案（空间类型为厨房时）总数,查询参数:{}", gson.toJson(query));
        return designPlanRecommendedBizService.selectGroupPrimaryDesignPlanCount(query);
    }

    /**
     * created by zhangchengda
     * 2018/8/17 13:56
     * 制作全屋方案
     *
     * @param fullHouseDesignPlanAdd 制作全屋方案参数
     */
    @Transactional
    @Override
    public String addFullHouseDesignPlan(FullHouseDesignPlanAdd fullHouseDesignPlanAdd) {
        //------------------------- 新建全屋方案数据 -------------------------//
        // 开始时间
        long startTime = System.currentTimeMillis();
        // 查询当前登录用户
        SysUser user = sysUserService.selectByPrimaryKey(fullHouseDesignPlanAdd.getUserId().longValue());
        if (user == null) {
            throw new FullHouseException("找不到用户信息");
        }
        // 构造全屋方案模型
        FullHouseDesignPlan fullHouseDesignPlan = new FullHouseDesignPlan();
        fullHouseDesignPlan = this.setBaseAttribute(fullHouseDesignPlan, fullHouseDesignPlanAdd, user);
        // 创建全屋方案
        log.info("创建全屋方案，fullHouseDesignPlan:{}", gson.toJson(fullHouseDesignPlan));
        int influenced = fullHouseDesignPlanService.insertSelective(fullHouseDesignPlan);
        if (influenced <= 0) {
            throw new FullHouseException("创建全屋方案失败");
        }
        log.info("创建全屋方案耗时:{}ms", System.currentTimeMillis() - startTime);
        //------------------------- end -------------------------//
        //------------------------- 新建全屋方案详情数据 -------------------------//
        // 开始时间
        startTime = System.currentTimeMillis();
        // 获取全屋方案ID
        fullHouseDesignPlan = fullHouseDesignPlanService.selectFullHouseDesignPlanByUuid(fullHouseDesignPlan.getUuid());
        int fullHouseDesignPlanId = fullHouseDesignPlan.getId();
        log.info("全屋方案ID:{}", fullHouseDesignPlanId);
        // 获取需要插入详情表的条数
        int listSize = fullHouseDesignPlanAdd.getLivingDiningRoom().size() +
                fullHouseDesignPlanAdd.getBedroom().size() +
                fullHouseDesignPlanAdd.getKitchen().size() +
                fullHouseDesignPlanAdd.getToilet().size() +
                fullHouseDesignPlanAdd.getSchoolroom().size();
        // 待插入集合
        List<FullHouseDesignPlanDetail> detailList = new ArrayList<>(listSize);
        // 将各个空间的方案放入待插入集合中
        for (Integer designPlanRecommendedId : fullHouseDesignPlanAdd.getLivingDiningRoom()) {
            addFullHouseDesignPlanDetail(detailList,
                    fullHouseDesignPlanId,
                    designPlanRecommendedId,
                    FullHouseConstant.SPACE_TYPE_LIVING_DINING_ROOM,
                    user);
        }
        for (Integer designPlanRecommendedId : fullHouseDesignPlanAdd.getBedroom()) {
            addFullHouseDesignPlanDetail(detailList,
                    fullHouseDesignPlanId,
                    designPlanRecommendedId,
                    FullHouseConstant.SPACE_TYPE_BEDROOM,
                    user);
        }
        for (Integer designPlanRecommendedId : fullHouseDesignPlanAdd.getKitchen()) {
            addFullHouseDesignPlanDetail(detailList,
                    fullHouseDesignPlanId,
                    designPlanRecommendedId,
                    FullHouseConstant.SPACE_TYPE_KITCHEN,
                    user);
        }
        for (Integer designPlanRecommendedId : fullHouseDesignPlanAdd.getToilet()) {
            addFullHouseDesignPlanDetail(detailList,
                    fullHouseDesignPlanId,
                    designPlanRecommendedId,
                    FullHouseConstant.SPACE_TYPE_TOILET,
                    user);
        }
        for (Integer designPlanRecommendedId : fullHouseDesignPlanAdd.getSchoolroom()) {
            addFullHouseDesignPlanDetail(detailList,
                    fullHouseDesignPlanId,
                    designPlanRecommendedId,
                    FullHouseConstant.SPACE_TYPE_SCHOOLROOM,
                    user);
        }
        // 插入数据
        influenced = fullHouseDesignPlanDetailService.insertList(detailList);
        if (influenced != listSize) {
            throw new FullHouseException("创建全屋方案详情失败");
        }
        log.info("创建全屋方案详情耗时:{}ms", System.currentTimeMillis() - startTime);
        //------------------------- end -------------------------//
        //------------------------- 更新全屋方案的720UUID -------------------------//
        // 开始时间
        startTime = System.currentTimeMillis();
        // 720UUID
        String vrUuid = getVrUuid(detailList, user, ResRenderPicConstant.FILE_KEY_DESIGN_RECOMMENDED_RENDER_PIC, null);
        if (vrUuid == null || "".equals(vrUuid)) {
            throw new FullHouseException("720制作失败,UUID为空");
        }
        fullHouseDesignPlan.setVrResourceUuid(vrUuid);
        influenced = fullHouseDesignPlanService.updateByPrimaryKeySelective(fullHouseDesignPlan);
        if (influenced <= 0) {
            throw new FullHouseException("保存720UUID失败");
        }
        log.info("获取720UUID成功,720UUID:{},耗时:{}ms", vrUuid, System.currentTimeMillis() - startTime);
        return null;
        //------------------------- end -------------------------//
    }

    /**
     * created by zhangchengda
     * 2018/8/18 18:57
     * 删除全屋方案
     *
     * @param id 要删除的全屋方案ID
     * @throws BizException
     */
    @Override
    @Transactional
    public void deleteFullHouseDesignPlan(Integer id) {
        // 删除全屋方案
        int success = fullHouseDesignPlanService.deleteFullHouseDesignPlan(id);
        if (success <= 0) {
            // 删除失败抛出异常，事务回滚
            throw new FullHouseException("删除全屋方案失败");
        }
        log.info("删除全屋方案成功，id:{}", id);
        // 删除全屋方案详情
        success = fullHouseDesignPlanDetailService.deleteByFullHouseDesignPlanId(id);
        if (success <= 0) {
            // 删除失败抛出异常，事务回滚
            throw new FullHouseException("删除全屋方案详情失败");
        }
        log.info("删除全屋方案详情成功，删除条数:{}", success);
    }

    /**
     * created by zhangchengda
     * 2018/8/20 9:38
     * 查询全屋方案更换方案时显示的单空间方案列表
     *
     * @param fullHouseId 全屋方案ID
     * @return 全屋方案更换方案页面的数据
     */
    @Override
    public FullHouseDesignPlanEditPageVO selectFullHouseDesignPlanDetailList(Integer fullHouseId) {
        FullHouseDesignPlanEditPageVO pageVO = new FullHouseDesignPlanEditPageVO();
        pageVO.setFullHouseId(fullHouseId);
        // 查询出当前全屋方案的所有单空间方案
        List<DesignPlanVO> designPlanVOList = fullHouseDesignPlanDetailService.selectSingleSpaceDesignPlanList(fullHouseId);
        // 查出单空间方案的缩略图
        for (DesignPlanVO designPlanVO : designPlanVOList) {
            List<ResRenderPic> picList = resRenderPicService.selectByRecommendedIdAndKeyAndRenderType(designPlanVO.getDesignPlanId(),
                    ResRenderPicConstant.FILE_KEY_DESIGN_RECOMMENFED_SMALL_PIC,
                    null);
            if (picList != null && picList.size() > 0) {
                designPlanVO.setPicPath(picList.get(0).getPicPath());
            }
        }
        // 将所有单空间方案放入VO类中
        designPlanVOList.forEach(designPlanVO -> {
            switch (designPlanVO.getSpaceType()) {
                case FullHouseConstant.SPACE_TYPE_LIVING_DINING_ROOM:
                    List<DesignPlanVO> livingDiningRoom = pageVO.getLivingDiningRoom();
                    if (livingDiningRoom == null) {
                        livingDiningRoom = new ArrayList<>();
                        pageVO.setLivingDiningRoom(livingDiningRoom);
                    }
                    livingDiningRoom.add(designPlanVO);
                    break;
                case FullHouseConstant.SPACE_TYPE_BEDROOM:
                    List<DesignPlanVO> bedroom = pageVO.getBedroom();
                    if (bedroom == null) {
                        bedroom = new ArrayList<>();
                        pageVO.setBedroom(bedroom);
                    }
                    bedroom.add(designPlanVO);
                    break;
                case FullHouseConstant.SPACE_TYPE_KITCHEN:
                    List<DesignPlanVO> kitchen = pageVO.getKitchen();
                    if (kitchen == null) {
                        kitchen = new ArrayList<>();
                        pageVO.setKitchen(kitchen);
                    }
                    kitchen.add(designPlanVO);
                    break;
                case FullHouseConstant.SPACE_TYPE_TOILET:
                    List<DesignPlanVO> toilet = pageVO.getToilet();
                    if (toilet == null) {
                        toilet = new ArrayList<>();
                        pageVO.setToilet(toilet);
                    }
                    toilet.add(designPlanVO);
                    break;
                case FullHouseConstant.SPACE_TYPE_SCHOOLROOM:
                    List<DesignPlanVO> schoolRoom = pageVO.getSchoolRoom();
                    if (schoolRoom == null) {
                        schoolRoom = new ArrayList<>();
                        pageVO.setSchoolRoom(schoolRoom);
                    }
                    schoolRoom.add(designPlanVO);
                    break;
                default:
                    break;
            }
        });
        return pageVO;
    }

    /**
     * created by zhangchengda
     * 2018/8/20 12:11
     * 更新全屋方案
     *
     * @param obj 更新参数
     */
    @Transactional
    @Override
    public String updateFullHouseDesignPlan(Object obj) {
        FullHouseDesignPlanUpdate update = new FullHouseDesignPlanUpdate();
        if (obj instanceof FullHouseDesignPlanUpdate) {
            EntityCopyUtils.copyData(obj, update);
        } else {
            throw new FullHouseException("更新参数异常，obj:" + gson.toJson(obj));
        }
        //------------------------- 删除旧的全屋方案详情 -------------------------//
        // 逻辑删除当前全屋方案的所有详情表数据,更新数据时再把新的数据is_deleted字段更新为0
        int influence = fullHouseDesignPlanDetailService.logicDeletedByFullHouseDesignPlanId(update.getFullHouseId());
        if (influence <= 0) {
            throw new FullHouseException("更新全屋方案失败");
        }
        //------------------------- end -------------------------//
        //------------------------- 更新全屋方案详情 -------------------------//
        // 开始时间
        long startTime = System.currentTimeMillis();
        // 待更新列表
        int listSize = update.getLivingDiningRoom().size() +
                update.getBedroom().size() +
                update.getKitchen().size() +
                update.getToilet().size() +
                update.getSchoolroom().size();
        List<FullHouseDesignPlanDetail> updateList = new ArrayList<>(listSize);
        // 当前登录用户
        SysUser user = sysUserService.selectByPrimaryKey(update.getUserId().longValue());
        // 加入待更新列表
        for (Integer designPlanRecommendedId : update.getLivingDiningRoom()) {
            updateFullHouseDesignPlanDetail(updateList,
                    update.getFullHouseId(),
                    designPlanRecommendedId,
                    FullHouseConstant.SPACE_TYPE_LIVING_DINING_ROOM,
                    user);
        }
        for (Integer designPlanRecommendedId : update.getBedroom()) {
            updateFullHouseDesignPlanDetail(updateList,
                    update.getFullHouseId(),
                    designPlanRecommendedId,
                    FullHouseConstant.SPACE_TYPE_BEDROOM,
                    user);
        }
        for (Integer designPlanRecommendedId : update.getKitchen()) {
            updateFullHouseDesignPlanDetail(updateList,
                    update.getFullHouseId(),
                    designPlanRecommendedId,
                    FullHouseConstant.SPACE_TYPE_KITCHEN,
                    user);
        }
        for (Integer designPlanRecommendedId : update.getToilet()) {
            updateFullHouseDesignPlanDetail(updateList,
                    update.getFullHouseId(),
                    designPlanRecommendedId,
                    FullHouseConstant.SPACE_TYPE_TOILET,
                    user);
        }
        for (Integer designPlanRecommendedId : update.getSchoolroom()) {
            updateFullHouseDesignPlanDetail(updateList,
                    update.getFullHouseId(),
                    designPlanRecommendedId,
                    FullHouseConstant.SPACE_TYPE_SCHOOLROOM,
                    user);
        }
        // 更新全屋方案详情表，根据fullHouseDesignPlanId,spaceType,priorityLevel定位数据位置
        for (FullHouseDesignPlanDetail updateData : updateList) {
            log.info("更新全屋方案详情表,data:{}", gson.toJson(updateData));
            int success = fullHouseDesignPlanDetailService.updateDetail(updateData);
            if (success <= 0) {
                updateData.setCreator(user.getNickName());
                updateData.setGmtCreate(updateData.getGmtModified());
                updateData.setRemark("");
                success = fullHouseDesignPlanDetailService.insertSelective(updateData);
                if (success <= 0) {
                    throw new FullHouseException("更新全屋方案失败");
                }
            }
        }
        log.info("更新全屋方案详情成功，更新条数:{}，耗时:{}ms", updateList.size(), System.currentTimeMillis() - startTime);
        //------------------------- end -------------------------//
        //------------------------- 更新全屋方案720UUID和缩略图 -------------------------//
        // 开始时间
        startTime = System.currentTimeMillis();
        // 更新全屋方案表
        FullHouseDesignPlan fullHouseDesignPlan = new FullHouseDesignPlan();
        fullHouseDesignPlan.setId(update.getFullHouseId());
        // 更新全屋方案缩略图
        List<ResRenderPic> picList = resRenderPicService.selectByRecommendedIdAndKeyAndRenderType(update.getLivingDiningRoom().get(0),
                ResRenderPicConstant.FILE_KEY_DESIGN_RECOMMENFED_SMALL_PIC,
                ResRenderPicConstant.RES_RENDER_PIC_REDERING_TYPE_720_SINGLE_POINT);
        if (picList == null || picList.size() <= 0) {
            throw new FullHouseException("没有找到方案缩略图");
        }
        fullHouseDesignPlan.setPlanPicId(picList.get(0).getId());
        // 720UUID
        String vrUuid = getVrUuid(updateList, user, ResRenderPicConstant.FILE_KEY_DESIGN_RECOMMENDED_RENDER_PIC, null);
        if (vrUuid == null || "".equals(vrUuid)) {
            throw new FullHouseException("720制作失败,UUID为空");
        }
        log.info("720制作成功,720UUID:{}", vrUuid);
        fullHouseDesignPlan.setVrResourceUuid(vrUuid);
        // 系统信息
        fullHouseDesignPlan.setModifier(user.getNickName());
        fullHouseDesignPlan.setGmtModified(new Date());
        log.info("更新全屋方案表，更新数据:{}", gson.toJson(fullHouseDesignPlan));
        int success = fullHouseDesignPlanService.updateByPrimaryKeySelective(fullHouseDesignPlan);
        if (success <= 0) {
            throw new FullHouseException("更新全屋方案失败");
        }
        log.info("更新全屋方案表耗时:{}ms", System.currentTimeMillis() - startTime);
        return null;
        //------------------------- end -------------------------//
    }

    /**
     * created by zhangchengda
     * 2018/8/18 15:38
     * 增加全屋方案详情到待插入集合中
     *
     * @param detailList              待插入集合
     * @param fullHouseDesignPlanId   全屋方案ID
     * @param designPlanRecommendedId 单空间推荐方案ID
     * @param spaceType               空间类型
     * @param user                    当前登录用户
     */
    private void addFullHouseDesignPlanDetail(List<FullHouseDesignPlanDetail> detailList,
                                              int fullHouseDesignPlanId,
                                              int designPlanRecommendedId,
                                              int spaceType,
                                              SysUser user) {
        FullHouseDesignPlanDetail fullHouseDesignPlanDetail = new FullHouseDesignPlanDetail();
        // UUID
        UUID uuid = UUID.randomUUID();
        fullHouseDesignPlanDetail.setUuid(uuid.toString());
        // 全屋方案ID
        fullHouseDesignPlanDetail.setFullHousePlanId(fullHouseDesignPlanId);
        // 单空间推荐方案ID
        fullHouseDesignPlanDetail.setRecommendedPlanGroupPrimaryId(designPlanRecommendedId);
        // 推荐方案对应的效果图方案ID，推荐方案表中的plan_id就是效果图方案ID
        DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.selectByPrimaryKey(designPlanRecommendedId);
        fullHouseDesignPlanDetail.setRenderScenePlanId(designPlanRecommended.getPlanId());
        // 空间类型
        fullHouseDesignPlanDetail.setSpaceType(spaceType);
        // 优先级
        // 查找当前集合中是否有同空间类型的全屋方案详情数据，第一条加进来的数据优先级最高，第二条其次，依次递减
        setPriorityLevel(detailList, spaceType, fullHouseDesignPlanDetail);
        // 效果图方案的720资源ID
        List<ResRenderPic> resRenderPicList = resRenderPicService.selectBySceneIdAndKeyAndRenderType(designPlanRecommended.getPlanId(),
                ResRenderPicConstant.FILE_KEY_DESIGN_DESIGNPLAN_RENDER_PIC,
                ResRenderPicConstant.RES_RENDER_PIC_REDERING_TYPE_720_SINGLE_POINT);
        if (resRenderPicList != null && resRenderPicList.size() > 0) {
            fullHouseDesignPlanDetail.setPlanRenderResourceId(resRenderPicList.get(0).getId());
        } else {
            fullHouseDesignPlanDetail.setPlanRenderResourceId(0);
        }
        // 推荐方案的720资源ID
        resRenderPicList = resRenderPicService.selectByRecommendedIdAndKeyAndRenderType(designPlanRecommended.getId(),
                ResRenderPicConstant.FILE_KEY_DESIGN_RECOMMENDED_RENDER_PIC,
                ResRenderPicConstant.RES_RENDER_PIC_REDERING_TYPE_720_SINGLE_POINT);
        if (resRenderPicList != null && resRenderPicList.size() > 0) {
            fullHouseDesignPlanDetail.setPlanRecommendedResourceId(resRenderPicList.get(0).getId());
        } else {
            fullHouseDesignPlanDetail.setPlanRecommendedResourceId(0);
        }
        // 系统信息
        Date date = new Date();
        fullHouseDesignPlanDetail.setCreator(user.getNickName());
        fullHouseDesignPlanDetail.setGmtCreate(date);
        fullHouseDesignPlanDetail.setModifier(user.getNickName());
        fullHouseDesignPlanDetail.setGmtModified(date);
        fullHouseDesignPlanDetail.setIsDeleted(FullHouseConstant.FULL_HOUSE_IS_DELETED_NO);
        fullHouseDesignPlanDetail.setRemark("");
        // 加入到集合中
        log.info("增加全屋方案详情到待插入集合中，fullHouseDesignPlanDetail:{}", gson.toJson(fullHouseDesignPlanDetail));
        detailList.add(fullHouseDesignPlanDetail);
    }

    /**
     * created by zhangchengda
     * 2018/8/20 16:53
     * 增加全屋方案详情到待更新集合中
     *
     * @param detailList              待更新集合
     * @param fullHouseDesignPlanId   全屋方案ID
     * @param designPlanRecommendedId 单空间方案ID
     * @param spaceType               空间类型
     * @param user                    当前登录用户
     */
    private void updateFullHouseDesignPlanDetail(List<FullHouseDesignPlanDetail> detailList,
                                                 int fullHouseDesignPlanId,
                                                 int designPlanRecommendedId,
                                                 int spaceType,
                                                 SysUser user) {
        FullHouseDesignPlanDetail fullHouseDesignPlanDetail = new FullHouseDesignPlanDetail();
        // UUID
        UUID uuid = UUID.randomUUID();
        fullHouseDesignPlanDetail.setUuid(uuid.toString());
        // 全屋方案ID
        fullHouseDesignPlanDetail.setFullHousePlanId(fullHouseDesignPlanId);
        // 单空间推荐方案ID
        fullHouseDesignPlanDetail.setRecommendedPlanGroupPrimaryId(designPlanRecommendedId);
        // 推荐方案对应的效果图方案ID，推荐方案表中的plan_id就是效果图方案ID
        DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.selectByPrimaryKey(designPlanRecommendedId);
        fullHouseDesignPlanDetail.setRenderScenePlanId(designPlanRecommended.getPlanId());
        // 空间类型
        fullHouseDesignPlanDetail.setSpaceType(spaceType);
        // 优先级
        // 查找当前集合中是否有同空间类型的全屋方案详情数据，第一条加进来的数据优先级最高，第二条其次，依次递减
        setPriorityLevel(detailList, spaceType, fullHouseDesignPlanDetail);
        // 效果图方案的720ID
        List<ResRenderPic> resRenderPicList = resRenderPicService.selectBySceneIdAndKeyAndRenderType(designPlanRecommended.getPlanId(),
                ResRenderPicConstant.FILE_KEY_DESIGN_DESIGNPLAN_RENDER_PIC,
                ResRenderPicConstant.RES_RENDER_PIC_REDERING_TYPE_720_SINGLE_POINT);
        if (resRenderPicList != null && resRenderPicList.size() > 0) {
            fullHouseDesignPlanDetail.setPlanRenderResourceId(resRenderPicList.get(0).getId());
        } else {
            fullHouseDesignPlanDetail.setPlanRenderResourceId(0);
        }
        // 推荐方案的720ID
        resRenderPicList = resRenderPicService.selectByRecommendedIdAndKeyAndRenderType(designPlanRecommended.getId(),
                ResRenderPicConstant.FILE_KEY_DESIGN_RECOMMENDED_RENDER_PIC,
                ResRenderPicConstant.RES_RENDER_PIC_REDERING_TYPE_720_SINGLE_POINT);
        if (resRenderPicList != null && resRenderPicList.size() > 0) {
            fullHouseDesignPlanDetail.setPlanRecommendedResourceId(resRenderPicList.get(0).getId());
        } else {
            fullHouseDesignPlanDetail.setPlanRecommendedResourceId(0);
        }
        // 系统信息
        Date date = new Date();
        fullHouseDesignPlanDetail.setModifier(user.getNickName());
        fullHouseDesignPlanDetail.setGmtModified(date);
        fullHouseDesignPlanDetail.setIsDeleted(FullHouseConstant.FULL_HOUSE_IS_DELETED_NO);
        // 加入到集合中
        log.info("增加全屋方案详情到待更新集合中，fullHouseDesignPlanDetail:{}", gson.toJson(fullHouseDesignPlanDetail));
        detailList.add(fullHouseDesignPlanDetail);
    }

    /**
     * created by zhangchengda
     * 2018/11/27 12:32
     * 设置全屋方案的基础属性
     *
     * @param fullHouseDesignPlan    全屋方案对象
     * @param fullHouseDesignPlanAdd 新增全屋方案的参数对象
     * @param user                   当前登录用户
     * @return
     */
    private FullHouseDesignPlan setBaseAttribute(FullHouseDesignPlan fullHouseDesignPlan,
                                                 FullHouseDesignPlanAdd fullHouseDesignPlanAdd,
                                                 SysUser user) {
        // 方案缩略图，全屋方案缩略图为客餐厅方案组合的主方案的缩略图
        Integer groupPrimaryId = fullHouseDesignPlanAdd.getLivingDiningRoom().get(0);
        List<ResRenderPic> picList = resRenderPicService.selectByRecommendedIdAndKeyAndRenderType(groupPrimaryId,
                ResRenderPicConstant.FILE_KEY_DESIGN_RECOMMENFED_SMALL_PIC,
                ResRenderPicConstant.RES_RENDER_PIC_REDERING_TYPE_720_SINGLE_POINT);
        if (picList == null || picList.size() <= 0) {
            throw new FullHouseException("没有找到全屋方案缩略图");
        }
        return setBaseAttribute(fullHouseDesignPlan,
                fullHouseDesignPlanAdd.getDesignPlanName(),
                fullHouseDesignPlanAdd.getDesignPlanStyleId(),
                picList.get(0).getId(),
                FullHouseConstant.FULL_HOUSE_SOURCE_TYPE_INTERNAL_PRODUCTION,
                null,
                user);
    }
}
