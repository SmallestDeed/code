package com.sandu.service.designplan.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sandu.api.base.common.LoginUser;
import com.sandu.api.base.common.Utils;
import com.sandu.api.base.common.util.ClassReflectionUtils;
import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.base.model.SysDictionary;
import com.sandu.api.base.service.BasePlatformService;
import com.sandu.api.base.service.SysDictionaryService;
import com.sandu.api.designplan.input.PlanInput;
import com.sandu.api.designplan.input.PlanRenderSceneInput;
import com.sandu.api.designplan.model.*;
import com.sandu.api.designplan.output.DesignPlanRenderSceneVo;
import com.sandu.api.designplan.output.SingleDesignPlanVo;
import com.sandu.api.designplan.output.SinglePlanRenderSceneVo;
import com.sandu.api.designplan.service.DesignPlanRenderSceneService;
import com.sandu.api.fullhouse.common.exception.BizException;
import com.sandu.api.fullhouse.input.FullHouseDesignPlanAdd;
import com.sandu.api.fullhouse.service.biz.FullHouseDesignPlanBizService;
import com.sandu.api.task.model.AutoRenderTask;
import com.sandu.api.task.model.AutoRenderTaskState;
import com.sandu.api.task.service.AutoRenderTaskService;
import com.sandu.api.task.service.AutoRenderTaskStateService;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.properties.ResProperties;
import com.sandu.common.util.FileUploadUtils;
import com.sandu.design.model.DesignRenderRoam;
import com.sandu.home.model.SpaceCommon;
import com.sandu.render.model.RenderTypeCode;
import com.sandu.service.designplan.dao.DesignPlanRenderSceneMapper;
import com.sandu.service.fullhouse.dao.FullHouseDesignPlanMapper;
import com.sandu.system.model.ResDesign;
import com.sandu.system.model.ResDesignRenderScene;
import com.sandu.system.model.ResModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service("designPlanRenderSceneService")
public class DesignPlanRenderSceneServiceImpl implements DesignPlanRenderSceneService {
    @Autowired
    private DesignPlanRenderSceneMapper designPlanRenderSceneMapper;

    @Value("${app.upload.root}")
    private String APP_UPLOAD_ROOT;


    @Value("${yun.domain.name}")
    private String YUN_DOMAIN_NAME;
    private static final Gson gson = new Gson();


    @Autowired
    private FullHouseDesignPlanBizService fullHouseDesignPlanBizService;

    @Autowired
    private FullHouseDesignPlanMapper fullHouseDesignPlanMapper;

    @Autowired
    private AutoRenderTaskService autoRenderTaskService;

    @Autowired
    private BasePlatformService basePlatformService;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private AutoRenderTaskStateService autoRenderTaskStateService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return designPlanRenderSceneMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DesignPlanRenderScene record) {
        return designPlanRenderSceneMapper.insert(record);
    }

    @Override
    public int insertSelective(DesignPlanRenderScene record) {
        return designPlanRenderSceneMapper.insertSelective(record);
    }

    @Override
    public DesignPlanRenderScene selectByPrimaryKey(Long id) {
        return designPlanRenderSceneMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(DesignPlanRenderScene record) {
        return designPlanRenderSceneMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(DesignPlanRenderScene record) {
        return designPlanRenderSceneMapper.updateByPrimaryKey(record);
    }

    @Override
    public DesignPlanRenderSceneVo getDesignPlanRenderSceneList(Integer fullHousePlanId, Integer designTemplateId) {
        return designPlanRenderSceneMapper.selectDesignPlanRenderSceneListByfullHousePlanId(fullHousePlanId, designTemplateId);
    }

    @Override
    public List<SingleDesignPlanVo> getDesignPlanList(Integer demandId, Integer userId) {
        List<SingleDesignPlanVo> singleDesignPlanVoList = designPlanRenderSceneMapper.selectDesignPlanListByDemandId(demandId, userId);

        //移除未做720渲染的草图方案
        if (null != singleDesignPlanVoList && singleDesignPlanVoList.size() > 0) {
            List<SingleDesignPlanVo> renderPlanList = new ArrayList<>();
            List<Integer> businessIds = designPlanRenderSceneMapper.selectRender720ResourceByPlanId(singleDesignPlanVoList);
            if (null != businessIds && businessIds.size() > 0) {
                for (SingleDesignPlanVo singleDesignPlanVo : singleDesignPlanVoList) {
                    for (Integer businessId : businessIds) {
                        if (singleDesignPlanVo.getDesignPlanId().intValue() == businessId.intValue()) {
                            renderPlanList.add(singleDesignPlanVo);
                        }
                    }

                }
                return renderPlanList;
            }

            return null;
        }


        return singleDesignPlanVoList;
    }

    @Override
    public SinglePlanRenderSceneVo creatNewFullHousePlan(List<PlanRenderSceneInput> planRenderSceneInputList, List<PlanInput> planInputList, LoginUser loginUser, String planName, Integer planId, Integer houseId, String authorization) {
        //草图方案生成效果图方案
        log.info("开始输出效果图");
        CreaDesignPlanRenderScene creaDesignPlanRenderScene = new CreaDesignPlanRenderScene(planRenderSceneInputList, planInputList, loginUser, authorization).invoke();
        if (creaDesignPlanRenderScene.is()) {
            return null;
        }
        log.info("输出效果图完成");
        log.info("开始组装全屋方案");
        planRenderSceneInputList = creaDesignPlanRenderScene.getPlanRenderSceneInputList();
        if (planRenderSceneInputList == null || planRenderSceneInputList.size() == 0) {
            return null;
        }

        //组装全屋方案数据
        FullHouseDesignPlanAdd fullHouseDesignPlanAdd = new FullHouseDesignPlanAdd();
        if (null == planId || planId == 0) {
            fullHouseDesignPlanAdd.setFullHousePlanSourceId(0);
        } else {
            fullHouseDesignPlanAdd.setFullHousePlanSourceId(planId);
        }

        fullHouseDesignPlanAdd.setDesignPlanName(planName);
        fullHouseDesignPlanAdd.setHouseId(houseId);
        fullHouseDesignPlanAdd.setDesignPlanStyleId(planRenderSceneInputList.get(0).getDesignPlanStyleId());
        fullHouseDesignPlanAdd.setUserId(loginUser.getId());
        List<Integer> livingDiningRoom = new ArrayList<>(); //客餐厅
        List<Integer> bedroom = new ArrayList<>(); //卧室
        List<Integer> kitchen = new ArrayList<>(); //厨房
        List<Integer> toilet = new ArrayList<>(); //卫生间
        List<Integer> schoolroom = new ArrayList<>(); //书房
        for (PlanRenderSceneInput planRenderSceneInput : planRenderSceneInputList) {
            switch (planRenderSceneInput.getSpaceType()) {
                case 3:
                    livingDiningRoom.add(planRenderSceneInput.getDesignRenderSceneId());
                    break;
                case 4:
                    bedroom.add(planRenderSceneInput.getDesignRenderSceneId());
                    break;
                case 5:
                    kitchen.add(planRenderSceneInput.getDesignRenderSceneId());
                    break;
                case 6:
                    toilet.add(planRenderSceneInput.getDesignRenderSceneId());
                    break;
                case 7:
                    schoolroom.add(planRenderSceneInput.getDesignRenderSceneId());
                    break;
            }
        }
        Integer businessId = -1;
        if (livingDiningRoom != null && livingDiningRoom.size() > 0) {
            fullHouseDesignPlanAdd.setLivingDiningRoom(livingDiningRoom);
            businessId = livingDiningRoom.get(0);
        }
        if (bedroom != null && bedroom.size() > 0) {
            fullHouseDesignPlanAdd.setBedroom(bedroom);
            businessId = bedroom.get(0);
        }
        if (kitchen != null && kitchen.size() > 0) {
            fullHouseDesignPlanAdd.setKitchen(kitchen);
            businessId = kitchen.get(0);
        }
        if (toilet != null && toilet.size() > 0) {
            fullHouseDesignPlanAdd.setToilet(toilet);
            businessId = toilet.get(0);
        }
        if (schoolroom != null && schoolroom.size() > 0) {
            fullHouseDesignPlanAdd.setSchoolroom(schoolroom);
            businessId = schoolroom.get(0);
        }

        //生成全屋方案 返回ID和UUID用逗号分割
        String idAndUuid = "";
        try {
            idAndUuid = fullHouseDesignPlanBizService.addFullHouseDesignPlanScene(fullHouseDesignPlanAdd);
        } catch (BizException e) {
            log.error("生成全屋方案数据异常", e);
        }
        if (StringUtils.isBlank(idAndUuid)) {
            log.error("生成全屋方案失败，idAndUuid={}", idAndUuid);
            return null;
        }
        String[] ids = idAndUuid.split(",");
        int fullHousePlanId = Integer.parseInt(ids[0]);
        log.info("组装全屋完成");
        log.info("开始创建任务");
        //自动创建一条虚拟任务使数据与随选网保持一致
        creatAutoRenderTask(loginUser, planName, houseId, businessId, fullHousePlanId);
        log.info("创建任务完成");
        //返回数据
        if (fullHousePlanId > 0) {
            SinglePlanRenderSceneVo singlePlanRenderSceneVo = fullHouseDesignPlanMapper.selectDesignPlanRenderScene(fullHousePlanId);
            return singlePlanRenderSceneVo;
        }
        return null;
    }

    private void creatAutoRenderTask(LoginUser loginUser, String planName, Integer houseId, Integer businessId, int fullHousePlanId) {
        AutoRenderTask autoRenderTask = new AutoRenderTask();
        autoRenderTask.setPlanId(businessId);
        autoRenderTask.setTemplateId(-1);
        autoRenderTask.setRender720(0);
        autoRenderTask.setIsDeleted(1);
        Date date = new Date();
        autoRenderTask.setGmtCreate(date);
        autoRenderTask.setGmtModified(date);
        autoRenderTask.setCreator(loginUser.getId() + "");
        autoRenderTask.setModifier(loginUser.getId() + "");
        autoRenderTask.setOperationSource(0);
        autoRenderTask.setOperationUserId(loginUser.getId());
        autoRenderTask.setTaskType(0);
        autoRenderTask.setRenderTypesStr(2 + "");
        autoRenderTask.setDesignName(planName);
        autoRenderTask.setTaskSource(0);
        autoRenderTask.setPlatformId(2);
        autoRenderTask.setFullHousePlanId(fullHousePlanId);
        autoRenderTask.setPlanHouseType(3);
        autoRenderTask.setHouseId(houseId);
        autoRenderTask.setNewFullHousePlanId(fullHousePlanId);
        Integer mainTaskId = autoRenderTaskService.insertSelective(autoRenderTask);
        log.info("生成主任务完成，id为：{}", mainTaskId);

        AutoRenderTaskState autoRenderTaskState = new AutoRenderTaskState();
        autoRenderTaskState.setPlanId(businessId);
        autoRenderTaskState.setTemplateId(-1);
        autoRenderTaskState.setRender720(0);
        autoRenderTaskState.setState(1);
        autoRenderTaskState.setIsDeleted(0);
        autoRenderTaskState.setGmtCreate(date);
        autoRenderTaskState.setGmtModified(date);
        autoRenderTaskState.setCreator(loginUser.getId() + "");
        autoRenderTaskState.setModifier(loginUser.getId() + "");
        autoRenderTaskState.setBusinessId(businessId);
        autoRenderTaskState.setTaskId(mainTaskId);
        autoRenderTaskState.setOperationUserId(loginUser.getId());
        autoRenderTaskState.setTaskType(0);
        autoRenderTaskState.setRenderTypesStr(2 + "");
        autoRenderTaskState.setDesignName(planName);
        autoRenderTaskState.setTaskSource(0);
        autoRenderTaskState.setIsValid(1);
        autoRenderTaskState.setPlatformId(2);
        autoRenderTaskState.setFullHousePlanId(fullHousePlanId);
        autoRenderTaskState.setPlanHouseType(3);
        autoRenderTaskState.setMainTaskId(mainTaskId);
        autoRenderTaskState.setHouseId(houseId);
        autoRenderTaskState.setNewFullHousePlanId(fullHousePlanId);
        autoRenderTaskStateService.insertSelective(autoRenderTaskState);
        log.info("生成主任务状态完成：mainTaskId={}", mainTaskId);
    }

    @Override
    public DesignPlanRenderSceneVo getDesignPlanRenderSceneList(Integer planId) {
        return designPlanRenderSceneMapper.selectDesignPlanRenderSceneList(planId);
    }

    @Override
    public SinglePlanRenderSceneVo creatNewDesignPlan(List<PlanRenderSceneInput> planRenderSceneInputList, List<PlanInput> planInputList, LoginUser loginUser, String authorization) {
        //草图方案生成效果图方案
        CreaDesignPlanRenderScene creaDesignPlanRenderScene = new CreaDesignPlanRenderScene(planRenderSceneInputList, planInputList, loginUser, authorization).invoke();
        if (creaDesignPlanRenderScene.is()) {
            return null;
        }
        planRenderSceneInputList = creaDesignPlanRenderScene.getPlanRenderSceneInputList();
        if (planRenderSceneInputList != null && planRenderSceneInputList.size() > 0) {
            PlanRenderSceneInput planRenderSceneInput = planRenderSceneInputList.get(0);
            if (null != planRenderSceneInput) {
                SinglePlanRenderSceneVo singlePlanRenderSceneVo = new SinglePlanRenderSceneVo();
                singlePlanRenderSceneVo.setHouseId(planRenderSceneInput.getHouseId());
                singlePlanRenderSceneVo.setDesignPlanRenderSceneId(planRenderSceneInput.getDesignRenderSceneId());
                singlePlanRenderSceneVo.setPlanResourcPicPath(planRenderSceneInput.getPlanResourcPicPath());
                singlePlanRenderSceneVo.setPlanName(planRenderSceneInput.getFullHousePlanName());
                singlePlanRenderSceneVo.setSpaceName(planRenderSceneInput.getSpaceName());
                singlePlanRenderSceneVo.setSpaceAreas(planRenderSceneInput.getSpaceAreas());
                singlePlanRenderSceneVo.setDesignPlanStyleName(planRenderSceneInput.getDesignPlanStyleName());
                return singlePlanRenderSceneVo;
            }

        }
        return null;
    }


    private class CreaDesignPlanRenderScene {
        private boolean myResult;
        private List<PlanRenderSceneInput> planRenderSceneInputList;
        private List<PlanInput> planInputList;
        private LoginUser loginUser;

        private String authorization;

        public CreaDesignPlanRenderScene(List<PlanRenderSceneInput> planRenderSceneInputList, List<PlanInput> planInputList, LoginUser loginUser, String authorization) {
            this.planRenderSceneInputList = planRenderSceneInputList;
            this.planInputList = planInputList;
            this.loginUser = loginUser;
            this.authorization = authorization;

        }

        boolean is() {
            return myResult;
        }

        public List<PlanRenderSceneInput> getPlanRenderSceneInputList() {
            return planRenderSceneInputList;
        }

        public CreaDesignPlanRenderScene invoke() {
            //查询该草图方案是否存在
            List<DesignPlanRes> designPlanResList = new ArrayList<>();
            if (planInputList != null && planInputList.size() > 0) {
                List<DesignPlan> designplanList = designPlanRenderSceneMapper.getDesignPlan(planInputList);
                if (null == designplanList || 0 == designplanList.size() || planInputList.size() != designplanList.size()) {
                    log.error("输出为效果图方案,草图方案不存在");
                    myResult = true;
                    return this;
                }

                //获取模型文件信息
                List<ResModel> resModelList = designPlanRenderSceneMapper.getResModelByPlanModelId(designplanList);
                if (null == resModelList || resModelList.size() == 0 || resModelList.size() < designplanList.size()) {
                    log.error("输出为效果图方案,缺失模型文件");
                    myResult = true;
                    return this;
                }

                // 获取配置文件信息
                List<ResDesign> resDesignlist = designPlanRenderSceneMapper.getResDesignByConfigFileId(designplanList);
                if (null == resDesignlist || resDesignlist.size() == 0 || resDesignlist.size() < designplanList.size()) {
                    log.error("输出为效果图方案,缺失配置文件信息");
                    myResult = true;
                    return this;
                }

                // 获取设计方案产品列表
                List<DesignPlanProduct> designPlanProductList = designPlanRenderSceneMapper.getDesignPlanProductByPlanId(designplanList);
                if (null == designPlanProductList || designPlanProductList.size() == 0 || designPlanProductList.size() < designplanList.size()) {
                    log.error("输出为效果图方案,缺失设计方案产品列表");
                    myResult = true;
                    return this;
                }

                //组装数据
                for (DesignPlan designPlan : designplanList) {
                    DesignPlanRes designPlanRes = new DesignPlanRes();
                    if (designPlan.getRenderType() == null) {
                        designPlan.setRenderType(4);
                    }
                    if (designPlan.getDesignStyleId() == null) {
                        designPlan.setDesignStyleId(8);
                    }
                    designPlanRes.setDesignPlan(designPlan);
                    for (ResModel resModel : resModelList) {
                        if (designPlan.getModelId().intValue() == resModel.getId().intValue()) {
                            designPlanRes.setResModel(resModel);
                        }
                    }
                    for (ResDesign resDesign : resDesignlist) {
                        if (designPlan.getConfigFileId().intValue() == resDesign.getId().intValue()) {
                            designPlanRes.setResDesign(resDesign);
                        }
                    }
                    List<DesignPlanProduct> designPlanProducts = new ArrayList<>();
                    for (DesignPlanProduct designPlanProduct : designPlanProductList) {
                        if (designPlanProduct.getPlanId().intValue() == designPlan.getId().intValue()) {
                            designPlanProducts.add(designPlanProduct);
                        }
                    }
                    designPlanRes.setDesignPlanProductList(designPlanProducts);
                    if (null == designPlanRes.getDesignPlan() || null == designPlanRes.getResModel()
                            || null == designPlanRes.getResDesign() || null == designPlanRes.getDesignPlanProductList()
                            || 0 == designPlanRes.getDesignPlanProductList().size()) {
                        log.error("草图方案数据组装失败");
                        myResult = true;
                        return this;
                    }
                    designPlanResList.add(designPlanRes);
                }
                if (null == designPlanResList || designPlanResList.size() == 0) {
                    log.error("草图方案数据详情缺少");
                    myResult = true;
                    return this;
                }
                //远程调用生成效果图方案
     /*     String json ="";
                String url = YUN_DOMAIN_NAME+"/pc/web/design/designPlan/autocreateDesignPlanRenderScene.htm";
                String designPlanResListJson = new Gson().toJson(designPlanResList);
                log.info("远程调用生成效果图方案:"+url);
                DesignPlanResListJson designPlanResListJson1 = new DesignPlanResListJson();
                designPlanResListJson1.setDesignPlanResList(designPlanResListJson);
                designPlanResListJson1.setUserId(loginUser.getId());
                String result;
                try {
                    result = Utils.doPost(url, designPlanResListJson1,authorization);
                }catch (Exception e){
                    log.error("远程调用生成效果图方案失败1----- result="+e);
                    myResult = true;
                    return this;
                }
                if (StringUtils.isBlank(result)) {
                    log.error("远程调用生成效果图方案失败2----- result="+result);
                    myResult = true;
                    return this;
                }
                ResponseEnvelope responseEnvelope = new Gson().fromJson(result, ResponseEnvelope.class);
                log.info("远程调用生成效果图方案 3------ responseEnvelope="+responseEnvelope);
                if (responseEnvelope.getObj() != null && responseEnvelope.isSuccess()) {
                    json = responseEnvelope.getObj().toString();
                }else {
                    log.error("远程调用生成效果图方案失败3----- result="+result);
                    myResult = true;
                    return this;
                }
                log.info("效果图方案ID:"+json);
                List<Integer> renderSceneIds = new Gson().fromJson(json,List.class);*/
                List<Integer> renderSceneIds = this.autocreateDesignPlanRenderScene(designPlanResList, loginUser);
                if (null != renderSceneIds && renderSceneIds.size() > 0) {
                    log.info("效果图方案ID:" + new Gson().toJson(renderSceneIds));
                    List<PlanRenderSceneInput> renderSceneInputs = designPlanRenderSceneMapper.selectDesignPlanRenderSceneListByIds(renderSceneIds);
                    if (renderSceneInputs != null && renderSceneInputs.size() > 0) {
                        if (planRenderSceneInputList == null && planRenderSceneInputList.size() == 0) {
                            planRenderSceneInputList = new ArrayList<>();
                        }
                        for (PlanRenderSceneInput renderSceneInput : renderSceneInputs) {
                            planRenderSceneInputList.add(renderSceneInput);
                        }
                    }
                    log.info("草图方案批量生成效果图方案完成" + new Gson().toJson(renderSceneInputs));
                }
            }
            myResult = false;
            return this;

        }

        private List<Integer> autocreateDesignPlanRenderScene(List<DesignPlanRes> designPlanResList, LoginUser loginUser) {

            //批量创建新的效果图方案
            List<DesignPlanRenderScene> designPlanRenderSceneList = new ArrayList<>();
            for (DesignPlanRes designPlanRes : designPlanResList) {
                DesignPlan designPlan = designPlanRes.getDesignPlan();
                if (designPlan != null) {
                    DesignPlanRenderScene designPlanEctype = new DesignPlanRenderScene();
                    try {
                        ClassReflectionUtils.reflectionAttr(designPlan, designPlanEctype);
                    } catch (Exception e) {
                        log.error("草图方案对象到效果图方案对象转换失败");
                        return null;
                    }
                    Date now = new Date();
                    designPlanEctype.setGmtCreate(now);
                    designPlanEctype.setGmtModified(now);
                    designPlanEctype.setDesignPlanId(designPlan.getId());
                    designPlanEctype.setPlatformId(2);
                    designPlanEctype.setPlanName(designPlan.getPlanName());
                    designPlanEctype.setPlanDesc(designPlan.getPlanDesc());
                    if (designPlan.getDesignStyleId() != null && designPlan.getDesignStyleId() > 0) {
                        designPlanEctype.setDesignStyleId(designPlan.getDesignStyleId());
                    } else {
                        designPlanEctype.setDesignStyleId(8);
                    }
                    if (designPlan.getRenderType() != null && designPlan.getRenderType() > 0) {
                        designPlanEctype.setRenderType(designPlan.getRenderType());
                    } else {
                        designPlanEctype.setRenderType(4);
                    }
                    designPlanRenderSceneList.add(designPlanEctype);
                }
            }
            designPlanRenderSceneMapper.insertDesignPlanRenderSceneList(designPlanRenderSceneList);
            List<Integer> designPlanRenderSceneIdList = new ArrayList<>(designPlanRenderSceneList.size());
            Map<Integer, Integer> designPlanRenderSceneIdMap = new HashMap<>(designPlanRenderSceneList.size());


            for (DesignPlanRenderScene designPlanRenderScene : designPlanRenderSceneList) {
                if (designPlanRenderScene.getId() != null && designPlanRenderScene.getId() > 0) {
                    designPlanRenderSceneIdMap.put(designPlanRenderScene.getDesignPlanId(), designPlanRenderScene.getId().intValue());
                    designPlanRenderSceneIdList.add(designPlanRenderScene.getId().intValue());
                }
            }
            if (designPlanRenderSceneIdList == null || designPlanRenderSceneIdList.size() == 0) {
                log.error("新增效果图方案失败");
                return null;
            }


            //批量copy草图方案配置文件到效果图方案
            Map<Integer, Integer> configFileIdList = this.copyFromResDesign(designPlanResList, designPlanRenderSceneIdMap);
            if (configFileIdList == null || configFileIdList.size() == 0) {
                //copy失败删除关联的数据
                designPlanRenderSceneMapper.deleteList(designPlanRenderSceneIdList);
                log.error("copy草图方案到效果图方案失败");
                return null;
            }
            // 回填 configFileId
            for (DesignPlanRenderScene designPlanRenderScene : designPlanRenderSceneList) {
                designPlanRenderScene.setConfigFileId(configFileIdList.get(designPlanRenderScene.getId()));
            }

            //copy草图方案 拼花信息 到 效果图方案
            Map<Integer, Integer> spellingFlowerFileIdMap = new HashMap<>(designPlanRenderSceneList.size());
            for (DesignPlanRes designPlanRes : designPlanResList) {
                for (DesignPlanRenderScene designPlanRenderScene : designPlanRenderSceneList) {
                    if (designPlanRes.getDesignPlan().getId().intValue() == designPlanRenderScene.getDesignPlanId().intValue()) {
                        spellingFlowerFileIdMap.put(designPlanRenderScene.getId().intValue(), designPlanRes.getDesignPlan().getSpellingFlowerFileId());
                    }

                }

            }
            Map<Integer, Integer> spellingFlowerFileIdList = this.copySpellingFlowerFile(spellingFlowerFileIdMap, designPlanRenderSceneList);
            if (spellingFlowerFileIdList != null && spellingFlowerFileIdList.size() > 0) {
                for (DesignPlanRenderScene designPlanRenderScene : designPlanRenderSceneList) {
                    designPlanRenderScene.setSpellingFlowerFileId(spellingFlowerFileIdList.get(designPlanRenderScene.getId()));
                }
            }


            // 修改(回填configFileId/spellingFlowerFileId)
            designPlanRenderSceneMapper.updateList(designPlanRenderSceneList);


            for (DesignPlanRes designPlanRes : designPlanResList) {
                // copy草图方案产品列表到效果图方案
                List<DesignPlanProduct> designPlanProductList = designPlanRes.getDesignPlanProductList();
                if (designPlanProductList == null || designPlanProductList.size() == 0) {
                    log.error("草图方案产品列表数据异常");
                    return null;
                }
                List<DesignPlanProductRenderScene> designPlanProductRenderSceneList = new ArrayList<>(designPlanProductList.size());
                for (DesignPlanProduct designPlanProduct : designPlanProductList) {
                    DesignPlanProductRenderScene designPlanProductRenderScene = new DesignPlanProductRenderScene();
                    try {
                        ClassReflectionUtils.reflectionAttr(designPlanProduct, designPlanProductRenderScene);
                    } catch (Exception e) {
                        log.error("草图方案产品列表到效果图方案产品列表对象转换失败");
                        return null;
                    }
                    designPlanProductRenderScene.setId(null);
                    designPlanProductRenderScene.setPlanId(designPlanRenderSceneIdMap.get(designPlanProduct.getDesignPlanId()));
                    designPlanProductRenderScene.setGmtModified(new Date());
                    designPlanProductRenderSceneList.add(designPlanProductRenderScene);

                }
                designPlanRenderSceneMapper.addDesignPlanProductRenderSceneList(designPlanProductRenderSceneList);


                //获取所有草图方案的渲染信息并复制到新的效果图方案
                Map<Integer, DesignPlanRenderScene> designPlanRenderSceneMap = designPlanRenderSceneList.stream().collect(Collectors.toMap(DesignPlanRenderScene::getDesignPlanId, Function.identity()));
                Boolean b = this.copyDesignPlanResToRender(designPlanRenderSceneMap, designPlanRes.getDesignPlan().getId(), loginUser);
                log.info("获取所有草图方案的渲染信息并复制到新的效果图方案完成");
                if (!b) {
                    log.error("获取所有草图方案的渲染信息并复制到新的效果图方案失败");
                    return null;
                }

            }

            return designPlanRenderSceneIdList;

        }

        private Boolean copyDesignPlanResToRender(Map<Integer, DesignPlanRenderScene> designPlanRenderSceneMap, Integer designPlanId, LoginUser loginUser) {
            //查询该设计方案的全部渲染原图列表
            ResRenderPic res = new ResRenderPic();
            res.setBusinessId(designPlanId);
            res.setIsDeleted(0);
            res.setLimit(-1);
            res.setOrder(" gmt_create desc ");
            List<String> fileKeys = new ArrayList<String>();
            fileKeys.add(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY);
            fileKeys.add(ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER);
            res.setFileKeyList(fileKeys);
            List<ResRenderPic> picList = designPlanRenderSceneMapper.selectResRenderPicList(res);
            if (picList == null || picList.size() == 0) {
                log.error("获取所有草图方案的渲染信息失败");
                return false;
            }
            DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneMap.get(designPlanId);
            if (designPlanRenderScene == null) {
                log.error("复制渲染信息到效果图方案：效果图方案不存在");
                return false;
            }


            //判断草图方案是否有封面图
            if (null != designPlanRenderScene.getCoverPicId()) {
                boolean picBool = false;
                //判断封面图是否为草图方案渲染信息（因为出现了非草图方案渲染信息作为了封面图信息，固做此过滤）
                for (ResRenderPic renderPic : picList) {
                    if (designPlanRenderScene.getCoverPicId().equals(renderPic.getId())) {
                        picBool = true;
                        break;
                    }
                }
                //非草图方案渲染信息作为了封面图信息:清除封面id
                if (!picBool) {
                    designPlanRenderScene.setCoverPicId(null);
                }
            }


            int listSize = picList.size();
            int count = 0;
            boolean isCoverPic = false;
            for (ResRenderPic resRenderPic : picList) {
                count++;
                //判断渲染类型分别处理
                if (null != resRenderPic.getRenderingType()) {
                    //判断该渲染信息是否有封面图
                    if (!isCoverPic && null == designPlanRenderScene.getCoverPicId()) {
                        isCoverPic = true;
                    } else if (!isCoverPic && null != resRenderPic.getId() && null != designPlanRenderScene.getCoverPicId()
                            && resRenderPic.getId().equals(designPlanRenderScene.getCoverPicId())) {
                        isCoverPic = true;
                    }
                    //判断在最后一次循环是否copy封面图:没有，该渲染信息就作为效果图方案封面图
                    if (listSize == count && !isCoverPic) {
                        isCoverPic = true;
                    }
                    switch (resRenderPic.getRenderingType()) {
                        case RenderTypeCode.COMMON_PICTURE_LEVEL:
                            //copy 照片级 渲染信息
                            this.copyDesignPlanPICTUREToRender(resRenderPic, designPlanRenderScene, loginUser, isCoverPic);
                            break;
                        case RenderTypeCode.COMMON_720_LEVEL:
                            //copy 720 渲染信息
                            this.copyDesignPlan720ToRender(resRenderPic, designPlanRenderScene, loginUser, isCoverPic);
                            break;
                        case RenderTypeCode.ROAM_720_LEVEL:
                            //copy 多点720 渲染信息
                            this.copyDesignPlanN720ToRender(resRenderPic, designPlanRenderScene, loginUser, isCoverPic);
                            break;
                        case RenderTypeCode.COMMON_VIDEO:
                            //copy 视频 渲染信息
                            this.copyDesignVideoToRender(resRenderPic, designPlanRenderScene, loginUser, isCoverPic);
                            break;
                        default:
                            log.error("渲染信息类型不匹配id=" + resRenderPic.getId());
                            break;
                    }

                } else {
                    log.error("渲染信息类型为空id=" + resRenderPic.getId());
                }

            }

            return true;

        }

        private Integer copyDesignVideoToRender(ResRenderPic resRenderPic, DesignPlanRenderScene designPlanRenderScene, LoginUser loginUser, boolean isCoverPic) {
            String msg = "视频";
            Integer videoId = null;

            //copy 草图方案视频封面图 返回新的id 便于下方 维护视频截图与视频的关系
            Integer designPlanRenderScenePicId = copyDesignPlanPicToRender(resRenderPic, designPlanRenderScene, loginUser, isCoverPic, msg);

            //查询草图方案视频
            ResRenderVideo resRenderVideoSearch = new ResRenderVideo();
            resRenderVideoSearch.setSysTaskPicId(resRenderPic.getId());
            resRenderVideoSearch.setIsDeleted(0);
            List<ResRenderVideo> resRenderVideoList = designPlanRenderSceneMapper.selectResRenderVideoList(resRenderVideoSearch);
            if (null == resRenderVideoList || resRenderVideoList.size() <= 0)
                throw new RuntimeException("草图方案视频数据信息不存在id=" + resRenderPic.getId());

            //循环copy
            for (ResRenderVideo resRenderVideoI : resRenderVideoList) {

                //获取草图方案视频路径
                String originalVideoUrl = Utils.getRelativeUrlByAbsolutePath(resRenderVideoI.getVideoPath(), APP_UPLOAD_ROOT);
                //判断资源文件 是否存在
                File designVideoFile = new File(originalVideoUrl);
                if (!designVideoFile.exists()) {
                    throw new RuntimeException("草图方案视频资源文件不存在id=" + resRenderVideoI.getId());
                }

                /**-----------------效果图视频路径获取-----------------*/

                //根据草图方案渲染视频filekey 查询路径前缀
                String fileKey = resRenderVideoI.getFileKey() + ".upload.path";
                String designRenderVideoPathPrefix = Utils.getPropertyName("config/res", fileKey, null);
                if (StringUtils.isBlank(designRenderVideoPathPrefix)){
                    throw new RuntimeException("：草图方案渲染视频信息id=" + resRenderVideoI.getId() + ",fileKey在res配置文件不存在");
                }

                designRenderVideoPathPrefix = Utils.replaceDate(designRenderVideoPathPrefix.trim());

                //生成视频文件名称
                String fileName = System.currentTimeMillis() + "_" + Utils.generateRandomDigitString(6);

                //DB 路径
                String dbDesignRenderVideoPath = designRenderVideoPathPrefix + fileName + resRenderVideoI.getVideoSuffix();

                //组装全上传路径：获取配置在内部的存储前缀(线上：data001/mfs；其他：/data001/resource)
                String designRenderVideoPath = Utils.getRelativeUrlByAbsolutePath(dbDesignRenderVideoPath, APP_UPLOAD_ROOT);
                /**----------copy file----------*/
                File designRendervideoFile = new File(designRenderVideoPath);
                boolean bool = Utils.fileCopy(designVideoFile, designRendervideoFile);
                //判断是否copy成功
                if (!bool) {
                    throw new RuntimeException("copy草图方案渲染视频信息失败id=" + resRenderVideoI.getId() + "=originalVideoUrl=" + originalVideoUrl + "=designRenderVideoPath" + designRenderVideoPath);

                }


                /**------------------新增效果图渲染视频数据库信息------------------*/

                //复制对象
                ResRenderVideo renderVideo = new ResRenderVideo();
                try {
                    BeanUtils.copyProperties(resRenderVideoI, renderVideo);
                } catch (Exception e) {
                    throw new RuntimeException("渲染视频对象copy失败");
                }
                //重新赋值
                String sysCode = UUID.randomUUID().toString().replace("-", "");
                renderVideo.setId(null);                                            //赋值为null，表示新增
                renderVideo.setBusinessId(designPlanRenderScene.getId().intValue());           //关联效果图方案id
                renderVideo.setVideoCode(sysCode);                                  //视频编码
                renderVideo.setVideoName(fileName);                                 //视频名称
                renderVideo.setVideoFileName(fileName);                             //视频文件名称
                renderVideo.setVideoPath(dbDesignRenderVideoPath);                  //视频路径
                renderVideo.setSysTaskPicId(designPlanRenderScenePicId);            //关联效果图方案视频封面图id
                renderVideo.setSysCode(sysCode);
                sysSaveResRenderVideo(renderVideo, loginUser);
                //新增
                videoId = designPlanRenderSceneMapper.addResRenderVideo(renderVideo);
                if (null == videoId || videoId == 0) {
                    throw new RuntimeException("新增效果图渲染视频数据库信息失败");
                }

            }

            return videoId;
        }

        private void copyDesignPlanN720ToRender(ResRenderPic resRenderPic, DesignPlanRenderScene designPlanRenderScene, LoginUser loginUser, boolean isCoverPic) {
            String msg = "多点720";

            //copy 选中的多点720渲染截图
            Integer renderId = copyDesignPlanPicToRender(resRenderPic, designPlanRenderScene, loginUser, isCoverPic, msg);

            //封面id
            isCoverPic = false;

            //获取选中的多点720渲染截图的缩略图
            ResRenderPic resRenderPicSearch = new ResRenderPic();
            resRenderPicSearch.setPid(resRenderPic.getId());
            List<ResRenderPic> listSmall = designPlanRenderSceneMapper.selectResRenderPicList(resRenderPicSearch);
            if (null == listSmall || listSmall.size() <= 0) {
                throw new RuntimeException("选中的多点720渲染原图对应缩略图不存在id=" + resRenderPic.getId());
            }
            ResRenderPic resRenderPicSmall = listSmall.get(0);

            //copy 选中的多点720渲染截图的缩略图
            Integer renerSmallId = copyDesignPlanPicToRender(resRenderPicSmall, designPlanRenderScene, loginUser, isCoverPic, msg);

            //获取 选中的多点720的渲染原图
            resRenderPicSearch = new ResRenderPic();
            resRenderPicSearch.setSysTaskPicId(resRenderPic.getId());
            List<Integer> noIdList = new ArrayList<>();                 //去除已经copy完成渲染截图与渲染截图的缩略图
            noIdList.add(resRenderPic.getId());                         //渲染截图
            noIdList.add(resRenderPicSmall.getId());                    //渲染截图缩略图
            noIdList.add(renderId);                                     //还没有修复关系，所以要去掉新图id
            noIdList.add(renerSmallId);                                 //还没有修复关系，所以要去掉新图id
            resRenderPicSearch.setNoIdList(noIdList);
            List<ResRenderPic> list = designPlanRenderSceneMapper.selectResRenderPicList(resRenderPicSearch);
            if (null == list || list.size() <= 0) {
                throw new RuntimeException("选中的多点720渲染原图不存在id=" + resRenderPic.getId());
            }
            //循环copy
            List<Integer> idList = new ArrayList<>();
            Map<Integer, Integer> oldNewPlanMap = new HashMap<>();
            log.error("ResRenderPic size=" + list.size());
            log.debug("ResRenderPic list=" + new Gson().toJson(list));
            for (ResRenderPic renderPic : list) {
                //copy原图
                Integer id = copyDesignPlanPicToRender(renderPic, designPlanRenderScene, loginUser, isCoverPic, msg);
                //记录原图新id，便于维护新图的关系
                idList.add(id);
                //记录old原图与新原图关系
                oldNewPlanMap.put(renderPic.getId(), id);
            }

            //添加渲染截图与渲染截图的缩略图 id
            idList.add(renderId);
            idList.add(renerSmallId);

            //维护 渲染截图与渲染截图的缩略图 关系
            ResRenderPic renderPicSmall = designPlanRenderSceneMapper.selectResRenderPic(renerSmallId);
            renderPicSmall.setPid(renderId);
            designPlanRenderSceneMapper.updateResRenderPic(renderPicSmall);

            //维护 渲染原图与渲染截图与渲染截图的缩略图 关系
            designPlanRenderSceneMapper.updateResRenderPicSysTaskId(renderId, idList);

            //copy多点720配置信息
            this.copyDesignPlanN720ToRenderRoom(resRenderPic, renderId, loginUser, oldNewPlanMap);
        }

        private void copyDesignPlanN720ToRenderRoom(ResRenderPic resRenderPic, Integer renderId, LoginUser loginUser, Map<Integer, Integer> oldNewPlanMap) {
            //获取多点720位置信息
            DesignRenderRoam designRenderRoam = new DesignRenderRoam();
            designRenderRoam.setScreenShotId(resRenderPic.getId());
            designRenderRoam.setIsDeleted(0);
            List<DesignRenderRoam> renderRoams = designPlanRenderSceneMapper.getDesignRenderRoamList(designRenderRoam);
            if (null == renderRoams && renderRoams.size() <= 0) {
                throw new RuntimeException("草图方案多点720位置信息不存在");
            }
            DesignRenderRoam renderRoam = renderRoams.get(0);
            Integer roamConfig = renderRoam.getRoamConfig();
            if (null == roamConfig) {
                throw new RuntimeException("草图方案多点720位置信息配置文件信息不存在");
            }
            ResDesign resDesign = designPlanRenderSceneMapper.getResDesign(roamConfig);
            if (null == resDesign) {
                throw new RuntimeException("草图方案多点720位置信息配置文件信息不存在");
            }
            //新渲染截图信息
            ResRenderPic renderPic = designPlanRenderSceneMapper.selectResRenderPic(renderId);

            this.copyDesignPlanRoomToRender(renderPic, resDesign, loginUser, oldNewPlanMap, "n720配置");
        }

        private void copyDesignPlanRoomToRender(ResRenderPic renderPic, ResDesign resDesign, LoginUser loginUser, Map<Integer, Integer> oldNewPlanMap, String msg) {

            //新增新的多点720配置信息
            DesignRenderRoam designRenderRoam = new DesignRenderRoam();
            designRenderRoam.setCreator(loginUser.getLoginName());
            designRenderRoam.setGmtCreate(new Date());
            designRenderRoam.setModifier(loginUser.getLoginName());
            designRenderRoam.setGmtModified(designRenderRoam.getGmtCreate());
            designRenderRoam.setIsDeleted(0);
            designRenderRoam.setScreenShotId(renderPic.getId());
            designRenderRoam.setUuid(Utils.getUUID());
            Integer renderRoamId = designPlanRenderSceneMapper.addDesignRenderRoam(designRenderRoam);
            if (null == renderRoamId) {
                throw new RuntimeException(msg + "：新增多点720配置room信息失败");
            }

            //草图方案路径
            String designPicPath = Utils.getRelativeUrlByAbsolutePath(resDesign.getFilePath(), APP_UPLOAD_ROOT);
            //判断草图方案资源文件是否存在
            File designFile = new File(designPicPath);
            if (!designFile.exists()) {
                throw new RuntimeException(msg + "：草图方案多点720配置信息不存在id=" + resDesign.getId());
            }
            /**---------------构建 效果图方案 多点720关联关系---------------*/
            //存在、读取配置文件信息
            List<DesignPlanN720Model> list = null;
            try {
                String txtFile = Utils.readTxtFile(designPicPath);
                list = gson.fromJson(txtFile, new TypeToken<ArrayList<DesignPlanN720Model>>() {
                }.getType());
                if (null == list || list.size() <= 0) {
                    throw new RuntimeException("读取多点720关系配置文件_转换对象为空");
                }
            } catch (Exception e) {
                log.error("读取多点720关系配置文件失败{}", e);
                throw new RuntimeException("读取多点720关系配置文件失败");
            }


            //构建新的关联关系
            log.error("old多点720位置关系=" + new Gson().toJson(list));
            log.error("oldNew关系=" + new Gson().toJson(oldNewPlanMap));
            for (DesignPlanN720Model designPlanN720Model : list) {

                Integer oldPlanId = designPlanN720Model.getFieldName();
                Integer newPlanId = oldNewPlanMap.get(oldPlanId);
                designPlanN720Model.setFieldName(newPlanId);

            }
            log.error("new多点720位置关系=" + new Gson().toJson(list));


            /**---------------构建 效果图方案 路径---------------*/

            //根据草图方案原有filekey获取效果图方案渲染信息路径前缀
            String fileKey = resDesign.getFileKey() + ".upload.path";
            String designRenderPicPathPrefix = Utils.getPropertyName("config/res", fileKey, null);
            if (StringUtils.isBlank(designRenderPicPathPrefix)) {
                throw new RuntimeException(msg + "：草图方案多点720配置信息id=" + resDesign.getId() + ",fileKey在res配置文件不存在");
            }

            designRenderPicPathPrefix = Utils.replaceDate(designRenderPicPathPrefix.trim());

            //生成效果图方案渲染信息文件名称
            String fileName = System.currentTimeMillis() + "_" + Utils.generateRandomDigitString(6);

            //DB 路径
            String dbDesignRenderPicPath = designRenderPicPathPrefix + fileName + resDesign.getFileSuffix();

            //组装全上传路径：获取配置在内部的存储前缀(线上：data001/mfs；其他：/data001/resource)
            String designRenderPicPath = Utils.getRelativeUrlByAbsolutePath(dbDesignRenderPicPath, APP_UPLOAD_ROOT);


            /**--------根据新的关联关系、路径生成新的配置文件--------*/
            boolean resBool = Utils.writeTxtFile(designRenderPicPath, new Gson().toJson(list));     //配置文件

            if (!resBool) {
                throw new RuntimeException(msg + "：copy草图方案多点720配置信息失败id=" + resDesign.getId() + "=designPicPath=" + designPicPath + "=designRenderPicPath" + designRenderPicPath);
            }

            /**--------------新增效果图方案多点720配置信息数据库信息--------------*/

            //复制对象
            ResDesign resDesignNew = new ResDesign();
            try {
                BeanUtils.copyProperties(resDesign, resDesignNew);
            } catch (Exception e) {
                log.error("新增效果图多点720配置信息数据库信息出错" + e.getMessage(), e);
                throw new RuntimeException(msg + "：copy原渲染720配置信息到新对象失败");
            }
            //重新赋值
            String sysCode = UUID.randomUUID().toString().replace("-", "");
            resDesignNew.setId(null);                                                         //清空id表示新增
            resDesignNew.setBusinessId(renderRoamId);                                         //room文件id
            resDesignNew.setFileCode(sysCode);                                                //room配置文件编码
            resDesignNew.setFileName(sysCode);                                                //文案名称
            resDesignNew.setFileOriginalName(fileName);                                       //文件名称
            resDesignNew.setFilePath(dbDesignRenderPicPath);                                  //文件路径
            sysSaveResDesign(resDesignNew, loginUser);
            //新增
            Integer roomConfigId = designPlanRenderSceneMapper.addResDesign(resDesignNew);
            if (null == roomConfigId || roomConfigId == 0) {
                throw new RuntimeException(msg + "：新增效果图多点720配置信息渲染信息失败");
            }
            //回填配置信息
            DesignRenderRoam renderRoam = designPlanRenderSceneMapper.selectDesignRenderRoam(renderRoamId);
            renderRoam.setRoamConfig(roomConfigId);
            designPlanRenderSceneMapper.updateDesignRenderRoam(renderRoam);
        }

        private void copyDesignPlan720ToRender(ResRenderPic resRenderPic, DesignPlanRenderScene designPlanRenderScene, LoginUser loginUser, boolean isCoverPic) {
            String msg = "720";

            //copy 选中的720渲染原图 返回新的id
            Integer renderId = copyDesignPlanPicToRender(resRenderPic, designPlanRenderScene, loginUser, isCoverPic, msg);
            log.info("copy 选中的720渲染原图 返回新的id完成");
            //获取选中的720渲染原图的缩略图
            ResRenderPic resRenderPicSearch = new ResRenderPic();
            resRenderPicSearch.setPid(resRenderPic.getId());
            List<ResRenderPic> listSmall = designPlanRenderSceneMapper.selectResRenderPicList(resRenderPicSearch);
            if (null == listSmall || listSmall.size() <= 0) {
                throw new RuntimeException("选中的720渲染原图对应缩略图不存在id=" + resRenderPic.getId());
            }
            ResRenderPic resRenderPicSmall = listSmall.get(0);

            //copy 选中的720渲染原图的缩略图 返回新的id
            Integer renerSmallId = copyDesignPlanPicToRender(resRenderPicSmall, designPlanRenderScene, loginUser, isCoverPic, msg);
            log.info("copy 选中的720渲染原图的缩略图 返回新的id完成");

            //获取 选中的720渲染原图的渲染截图
            Integer sysTaskPicId = resRenderPic.getSysTaskPicId();
            ResRenderPic renderPicTask = designPlanRenderSceneMapper.selectResRenderPic(sysTaskPicId);

            //copy 选中的720渲染原图的渲染截图 返回新的id
            Integer renderTaskId = copyDesignPlanPicToRender(renderPicTask, designPlanRenderScene, loginUser, isCoverPic, msg);
            log.info("copy 选中的720渲染原图的渲染截图 返回新的id完成");

            //维护 720渲染原图与渲染截图的关系
            ResRenderPic renderPic = designPlanRenderSceneMapper.selectResRenderPic(renderId);
            renderPic.setSysTaskPicId(renderTaskId);
            designPlanRenderSceneMapper.updateResRenderPic(renderPic);

            //维护 720渲染原图缩略图与渲染原图与渲染截图的关系
            ResRenderPic renderPicSmall = designPlanRenderSceneMapper.selectResRenderPic(renerSmallId);
            renderPicSmall.setPid(renderId);
            renderPicSmall.setSysTaskPicId(renderTaskId);
            log.info("维护 720渲染原图缩略图与渲染原图与渲染截图的关系完成");
            designPlanRenderSceneMapper.updateResRenderPic(renderPicSmall);

        }

        private void copyDesignPlanPICTUREToRender(ResRenderPic resRenderPic, DesignPlanRenderScene designPlanRenderScene, LoginUser loginUser, boolean isCoverPic) {
            String msg = "照片级";
            //copy 选中的照片级原图 返回新的id
            Integer renderId = this.copyDesignPlanPicToRender(resRenderPic, designPlanRenderScene, loginUser, isCoverPic, msg);
            //封面图
            isCoverPic = false;
            //选中的照片级原图的缩略图
            ResRenderPic resRenderPicSearch = new ResRenderPic();
            resRenderPicSearch.setPid(resRenderPic.getId());
            List<ResRenderPic> listSmall = designPlanRenderSceneMapper.selectResRenderPicList(resRenderPicSearch);
            if (null == listSmall || listSmall.size() <= 0) {
                throw new RuntimeException("选中的照片级原图对应缩略图不存在id=" + resRenderPic.getId());
            }
            ResRenderPic resRenderPicSmall = listSmall.get(0);
            //copy 选中的照片级原图的缩略图 返回新的id
            Integer renerSmallId = copyDesignPlanPicToRender(resRenderPicSmall, designPlanRenderScene, loginUser, isCoverPic, msg);
            //维护 新照片级原图 与 新缩略图 关系
            ResRenderPic renderPicSmall = designPlanRenderSceneMapper.selectResRenderPic(renerSmallId);
            renderPicSmall.setPid(renderId);
            designPlanRenderSceneMapper.updateResRenderPic(renderPicSmall);
        }

        private Integer copyDesignPlanPicToRender(ResRenderPic resRenderPic, DesignPlanRenderScene designPlanRenderScene, LoginUser loginUser, boolean isCoverPic, String msg) {

            //效果图方案 渲染信息 新id
            Integer designPlanRenderScenePicId = null;

            //草图方案路径
            String designPicPath = Utils.getRelativeUrlByAbsolutePath(resRenderPic.getPicPath(), APP_UPLOAD_ROOT);

            //判断草图方案资源文件是否存在
            File designFile = new File(designPicPath);
            if (!designFile.exists()) {
                throw new RuntimeException(msg + "：草图方案渲染信息资源文件不存在id=" + resRenderPic.getId());
            }
            /**---------------构建 效果图方案 路径---------------*/
            //根据草图方案原有filekey获取效果图方案渲染信息路径前缀
            String fileKey = resRenderPic.getFileKey() + ".upload.path";
            String designRenderPicPathPrefix = Utils.getPropertyName("config/res", fileKey, null);
            log.info("根据filekey获取路径" + designRenderPicPathPrefix);
            if (StringUtils.isBlank(designRenderPicPathPrefix)) {
                throw new RuntimeException(msg + "：草图方案渲染信息id=" + resRenderPic.getId() + ",fileKey在res配置文件不存在");
            }

            designRenderPicPathPrefix = Utils.replaceDate(designRenderPicPathPrefix.trim());

            //生成效果图方案渲染信息文件名称
            String fileName = System.currentTimeMillis() + "_" + Utils.generateRandomDigitString(6);

            //DB 路径
            String dbDesignRenderPicPath = designRenderPicPathPrefix + fileName;

            //判断草图方案文件流是否为文件夹（720、n720原图为文件夹形式，没有后缀）
            if (!designFile.isDirectory()) {
                dbDesignRenderPicPath = dbDesignRenderPicPath + resRenderPic.getPicSuffix();
            }


            //组装全上传路径：获取配置在内部的存储前缀(线上：data001/mfs；其他：/data001/resource)
            String designRenderPicPath = Utils.getRelativeUrlByAbsolutePath(dbDesignRenderPicPath, APP_UPLOAD_ROOT);
            log.info("组装全上传路径" + designRenderPicPath);
            /**--------copy file--------*/
            String resBool = "true";
            try {
                if (designFile.isDirectory()) {
                    Utils.copyDirectory(designPicPath, designRenderPicPath);
                } else {
                    //文件夹：720、n720原图
                    resBool = Utils.copyfile(designPicPath, designRenderPicPath);     //照片级、截图、缩略图
                }

            } catch (Exception e) {
                log.error(msg + "：copy草图方案渲染信息失败", e);
                resBool = "";
            }
            if (StringUtils.isBlank(resBool)) {
                throw new RuntimeException(msg + "：copy草图方案渲染信息失败id=" + resRenderPic.getId() + "=designPicPath=" + designPicPath + "=designRenderPicPath" + designRenderPicPath);
            }
            log.info("草图方案信息copy完成");
            /**--------------新增效果图方案渲染数据库信息--------------*/
            //复制对象
            ResRenderPic designResRenderPic = new ResRenderPic();
            try {
                BeanUtils.copyProperties(resRenderPic, designResRenderPic);
            } catch (Exception e) {
                log.error("新增效果图方案渲染数据库信息出错" + e.getMessage(), e);
                throw new RuntimeException(msg + "：copy原渲染信息到新对象失败");
            }
            //重新赋值
            String sysCode = UUID.randomUUID().toString().replace("-", "");
            designResRenderPic.setId(null);                                                         //清空id表示新增
            designResRenderPic.setBusinessId(null);                                                 //清空草图方案id
            designResRenderPic.setPicCode(sysCode);                                                 //图片编码
            designResRenderPic.setPicName(fileName);                                                //文案名称
            designResRenderPic.setPicFileName(fileName);                                            //文件名称
            designResRenderPic.setPicPath(dbDesignRenderPicPath);                                   //文件名称
            designResRenderPic.setDesignPlanSceneId(designPlanRenderScene.getId());                 //关联效果图id
            sysSaveResRenderPic(designResRenderPic, loginUser);
            //新增
            designPlanRenderScenePicId = this.addDesignRender(designResRenderPic);
            if (null == designPlanRenderScenePicId || designPlanRenderScenePicId == 0) {
                throw new RuntimeException(msg + "：新增效果图方案渲染信息失败");
            }
            //判断该渲染信息是否为草图方案封面
            if (isCoverPic) {
                DesignPlanRenderScene designPlanRenderScenePic = new DesignPlanRenderScene();
                designPlanRenderScenePic.setCoverPicId(designPlanRenderScenePicId);
                designPlanRenderScenePic.setId(designPlanRenderScene.getId());
                designPlanRenderSceneMapper.updateDesignPlanRenderScene(designPlanRenderScenePic);
            }
            log.info("新增效果图方案渲染数据库信息完成");
            return designPlanRenderScenePicId;
        }

        private int addDesignRender(ResRenderPic resRenderPic) {
            if (null == resRenderPic || null == resRenderPic.getDesignPlanSceneId() || resRenderPic.getDesignPlanSceneId().intValue() <= 0) {
                throw new RuntimeException("渲染信息不全");
            }

            DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneMapper.selectByPrimaryKey(new Long(resRenderPic.getDesignPlanSceneId()));
            if (designPlanRenderScene == null) {
                throw new RuntimeException("渲染信息关联效果图方案不存在");
            }

            //重新赋值渲染信息
            resRenderPic.setDesignPlanName(designPlanRenderScene.getPlanName());
            resRenderPic.setDesignSceneId(resRenderPic.getDesignPlanSceneId());
            resRenderPic.setCreateUserId(designPlanRenderScene.getUserId());

            //空间信息
            SpaceCommon spaceCommon = designPlanRenderSceneMapper.selectSpaceCommonInfo(designPlanRenderScene.getSpaceCommonId());
            log.info("spaceCommon=" + (spaceCommon == null));
            if (spaceCommon != null) {
                resRenderPic.setArea(spaceCommon.getSpaceAreas());//空间面积大小
                SysDictionary query = new SysDictionary();
                query.setType("houseType");
                query.setValue(spaceCommon.getSpaceFunctionId());
                SysDictionary sysDictionary = sysDictionaryService.getNameByType(query);
                log.info("sysDictionary=" + (sysDictionary != null));
                if (sysDictionary != null) {
                    resRenderPic.setSpaceType(sysDictionary.getName());//设置房屋空间类型
                }
            }
            log.info("getSpaceType=" + (resRenderPic.getSpaceType()));

            //添加平台标识
            if (resRenderPic.getPlatformId() == null) {
                BasePlatform basePlatform = basePlatformService.findOneByPlatformCode("pc2b");//平台信息
                if (basePlatform == null) {
                    log.error("缺失PC端平台信息！");
                } else {
                    resRenderPic.setPlatformId(basePlatform.getId());
                }
            }
            designPlanRenderSceneMapper.insertResRenderPic(resRenderPic);
            log.info("resRenderPic.getId()=" + resRenderPic.getId());
            return resRenderPic.getId();

        }

        private Map<Integer, Integer> copySpellingFlowerFile(Map<Integer, Integer> spellingFlowerFileIdMap, List<DesignPlanRenderScene> designPlanRenderSceneList) {
            Map<Integer, Integer> spellingFlowerFileIdList = new HashMap<>(designPlanRenderSceneList.size());
            for (DesignPlanRenderScene designPlanRenderScene : designPlanRenderSceneList) {
                Integer spellingFlowerFileId = spellingFlowerFileIdMap.get(designPlanRenderScene.getId().intValue());
                if (spellingFlowerFileId == null || spellingFlowerFileId == 0) {
                    return null;
                }
                ResDesign resDesign = designPlanRenderSceneMapper.getResDesignByspellingFlowerFileId(spellingFlowerFileId);
                if (resDesign == null) {
                    return null;
                }
                ResDesignRenderScene resDesignRenderScene = resDesign.resDesignRenderSceneCopy();
                String resFilePath = resDesignRenderScene.getFilePath();
                String fileKey = "design.designPlanRenderScene.spellingFlowerFile";
                if (StringUtils.isBlank(resFilePath)) {
                    return null;
                }
                String srcPath = Utils.getRelativeUrlByAbsolutePath(resFilePath, APP_UPLOAD_ROOT);
                File srcresourcesFile = new File(srcPath);
                if (!srcresourcesFile.exists()) {
                    log.error("------拼花信息配置file.exists() = false,path:" + srcresourcesFile.getPath());
                    return null;
                }
                String newFileName =
                        Utils.generateRandomDigitString(6) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
                                + "_1" + srcPath.substring(srcPath.lastIndexOf("."));
                String newFilePath = srcPath.substring(0, srcPath.lastIndexOf("/") + 1) + newFileName;
                File newFile = new File(newFilePath);
                try {
                    FileUtils.copyFile(srcresourcesFile, newFile);
                } catch (IOException e) {
                    log.error("拷贝文件异常" + e.toString());
                    return null;
                }
                resDesignRenderScene.setSysCode(designPlanRenderScene.getPlanCode());
                resDesignRenderScene.setFilePath(resDesign.getFilePath().substring(0, resDesign.getFilePath().lastIndexOf("/") + 1) + newFileName);
                resDesignRenderScene.setFileKey(fileKey);
                resDesignRenderScene.setBusinessId(designPlanRenderScene.getId().intValue());
                resDesignRenderScene.setFileCode(designPlanRenderScene.getPlanCode());
                int newSpellingFlowerFileId = designPlanRenderSceneMapper.insertResdesignRenderScene(resDesignRenderScene);
                spellingFlowerFileIdList.put(designPlanRenderScene.getId().intValue(), newSpellingFlowerFileId);
            }

            return spellingFlowerFileIdList;
        }

        private Map<Integer, Integer> copyFromResDesign(List<DesignPlanRes> designPlanResList, Map<Integer, Integer> designPlanRenderSceneIdMap) {
            Map<Integer, Integer> configFileIdList = new HashMap<>(designPlanResList.size());
            for (DesignPlanRes designPlanRes : designPlanResList) {
                Integer designPlanId = designPlanRes.getDesignPlan().getId();
                ResDesign resDesign = designPlanRes.getResDesign();
                if (resDesign == null || StringUtils.isEmpty(resDesign.getFilePath()) || designPlanId == null || designPlanId == 0) {
                    log.error("草图方案配置文件缺失");
                    return null;
                }
                String oldPath = Utils.getRelativeUrlByAbsolutePath(resDesign.getFilePath(), APP_UPLOAD_ROOT);
                File oldFile = new File(oldPath);
                if (!oldFile.exists()) {
                    log.error("------草图方案配置文件file.exists() = false,path:" + oldFile.getPath() + "oldPath:" + oldPath);
                    return null;
                }
                String newFileName =
                        Utils.generateRandomDigitString(6) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
                                + "_1" + oldPath.substring(oldPath.lastIndexOf("."));
                String newFilePath = oldPath.substring(0, oldPath.lastIndexOf("/") + 1) + newFileName;
                File newFile = new File(newFilePath);
                try {
                    FileUtils.copyFile(oldFile, newFile);
                } catch (IOException e) {
                    log.error("拷贝文件异常" + e.toString());
                    return null;
                }
                //新建配置文件数据
                ResDesignRenderScene resDesignRenderScene = new ResDesignRenderScene();
                try {
                    ClassReflectionUtils.reflectionAttr(resDesign, resDesignRenderScene);
                } catch (Exception e) {
                    log.error("新建配置文件数据" + e.toString());
                    if (newFile.exists()) {
                        newFile.delete();
                    }
                    return null;
                }
                // 设置resDesignEctype属性
                resDesignRenderScene.setId(null);
                resDesignRenderScene.setFilePath(resDesign.getFilePath().substring(0, resDesign.getFilePath().lastIndexOf("/") + 1) + newFileName);
                int businessId = designPlanRenderSceneIdMap.get(designPlanId);
                if (businessId > 0) {
                    resDesignRenderScene.setBusinessId(businessId);
                }
                Date now = new Date();
                resDesignRenderScene.setGmtCreate(now);
                resDesignRenderScene.setGmtModified(now);
                int resDesignRenderSceneId = designPlanRenderSceneMapper.insertResdesignRenderScene(resDesignRenderScene);
                if (resDesignRenderSceneId == 0) {
                    log.error("配置文件数据保存到数据库异常");
                    return null;
                }
                configFileIdList.put(businessId, resDesignRenderSceneId);
            }
            return configFileIdList;
        }

        /**
         * ResRenderPic 自动存储系统字段
         */
        private void sysSaveResRenderPic(ResRenderPic model, LoginUser loginUser) {
            if (model != null) {
                if (model.getId() == null) {
                    model.setGmtCreate(new Date());
                    model.setCreator(loginUser.getLoginName());
                    model.setIsDeleted(0);
                    if (model.getSysCode() == null || "".equals(model.getSysCode())) {
                        model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                    }
                }

                model.setGmtModified(new Date());
                model.setModifier(loginUser.getLoginName());
            }
        }

        private void sysSaveResRenderVideo(ResRenderVideo model, LoginUser loginUser) {
            if (model != null) {
                if (model.getId() == null) {
                    model.setGmtCreate(new Date());
                    model.setCreator(loginUser.getLoginName());
                    model.setIsDeleted(0);
                    if (model.getSysCode() == null || "".equals(model.getSysCode())) {
                        model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                    }
                }

                model.setGmtModified(new Date());
                model.setModifier(loginUser.getLoginName());
            }
        }

        private void sysSaveResDesign(ResDesign model, LoginUser loginUser) {
            if (model != null) {
                if (model.getId() == null) {
                    model.setGmtCreate(new Date());
                    model.setCreator(loginUser.getLoginName());
                    model.setIsDeleted(0);
                    if (model.getSysCode() == null || "".equals(model.getSysCode())) {
                        model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                    }
                }

                model.setGmtModified(new Date());
                model.setModifier(loginUser.getLoginName());
            }
        }


    }
}
