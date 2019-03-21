package com.sandu.panorama.service.impl;

import com.google.gson.reflect.TypeToken;
import com.sandu.cityunion.model.UnionContact;
import com.sandu.common.model.BaseModel;
import com.sandu.common.model.LoginUser;
import com.sandu.common.util.FileUploadUtils;
import com.sandu.common.util.JsonUtil;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.common.util.collections.CustomerListUtils;
import com.sandu.design.dao.DesignPlanRecommendedMapper;
import com.sandu.design.dao.DesignRenderRoamMapper;
import com.sandu.design.model.DesignRenderRoam;
import com.sandu.house.dao.BaseHouseMapper;
import com.sandu.house.service.BaseHouseService;
import com.sandu.panorama.dao.DesignPlanStoreReleaseDetailsMapper;
import com.sandu.panorama.dao.DesignPlanStoreReleaseMapper;
import com.sandu.panorama.dao.UnionContactMapper;
import com.sandu.panorama.model.DesignPlanStoreRelease;
import com.sandu.panorama.model.DesignPlanStoreReleaseDetails;
import com.sandu.panorama.model.input.DesignPlanStoreReleaseAdd;
import com.sandu.panorama.model.input.DesignPlanStoreReleaseDetailsAdd;
import com.sandu.panorama.model.input.DesignPlanStoreReleaseSearch;
import com.sandu.panorama.model.output.*;
import com.sandu.panorama.model.roam.Roam;
import com.sandu.panorama.service.DesignPlanStoreReleaseService;
//import com.sandu.system.service.ResPicService;
import com.sandu.system.model.bo.HouseAreaBo;
import com.sandu.util.PanoramaUtil;
import com.sandu.system.model.ResPic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service("designPlanStoreReleaseService")
@Transactional
public class DesignPlanStoreReleaseServiceImpl implements DesignPlanStoreReleaseService {

    @Autowired
    private DesignPlanStoreReleaseMapper designPlanStoreReleaseMapper;
    @Autowired
    private DesignPlanStoreReleaseDetailsMapper designPlanStoreReleaseDetailsMapper;
    @Autowired
    private UnionContactMapper unionContactMapper;
    @Autowired
    private DesignRenderRoamMapper designRenderRoamMapper;
    @Autowired
    private DesignPlanRecommendedMapper designPlanRecommendedMapper;
    @Autowired
    private BaseHouseMapper baseHouseMapper;
    @Autowired
    private BaseHouseService baseHouseService;

    /*@Autowired
    private ResPicService resPicService;*/

    @Override
    public int add(DesignPlanStoreRelease designPlanStoreRelease) {
        return designPlanStoreReleaseMapper.insertSelective(designPlanStoreRelease);
    }

    @Override
    public int update(DesignPlanStoreRelease designPlanStoreRelease) {
        return designPlanStoreReleaseMapper.updateByPrimaryKeySelective(designPlanStoreRelease);
    }

    @Override
    public int delete(Integer id) {
        return designPlanStoreReleaseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DesignPlanStoreRelease get(Integer id) {
        return designPlanStoreReleaseMapper.selectByPrimaryKey(id);
    }

    /**
     * 720制作
     * @param makePanorama
     * @param loginUser
     * @return
     */
    public MakeDesignPlanStoreReleaseResultVo makePanorama(DesignPlanStoreReleaseAdd makePanorama, LoginUser loginUser){
        MakeDesignPlanStoreReleaseResultVo resultVo = new MakeDesignPlanStoreReleaseResultVo();
        if( makePanorama == null ){
            return null;
        }
        if(makePanorama.getId() == null && CustomerListUtils.isEmpty(makePanorama.getDesignPlanStoreReleaseDetailsAddList())){
            return null;
        }

        DesignPlanStoreRelease designPlanStoreRelease = new DesignPlanStoreRelease();
        designPlanStoreRelease.setShareTitle(makePanorama.getShareTitle());
        designPlanStoreRelease.setHouseId(makePanorama.getHouseId());
        designPlanStoreRelease.setShareType(makePanorama.getShareType());
        designPlanStoreRelease.setUnionGroupId(makePanorama.getUnionGroupId() == null ? 0 : makePanorama.getUnionGroupId());
        designPlanStoreRelease.setUnionContactId(makePanorama.getUnionContactId() == null ? 0 : makePanorama.getUnionContactId());
        designPlanStoreRelease.setUnionSpecialOfferId(makePanorama.getUnionSpecialOfferId() == null ? 0 : makePanorama.getUnionSpecialOfferId());
        designPlanStoreRelease.setUserId(loginUser.getId());
        if(makePanorama.getId() != null && makePanorama.getId() > 0){
            //编辑720制作信息
            designPlanStoreRelease.setId(makePanorama.getId());
            BaseModel.sysSave(designPlanStoreRelease,loginUser);
            designPlanStoreReleaseMapper.updateByPrimaryKeySelective(designPlanStoreRelease);
            DesignPlanStoreRelease planStoreRelease = this.get(makePanorama.getId());
            designPlanStoreRelease.setUuid(planStoreRelease.getUuid());
        }else{
            //新增720制作信息
            designPlanStoreRelease.setUuid(Utils.getUUID());
            BaseModel.sysSave(designPlanStoreRelease,loginUser);
            designPlanStoreReleaseMapper.insertSelective(designPlanStoreRelease);
        }
        //编辑720分享附加信息时不用修改关联详情，则不传designPlanStoreReleaseDetailsAddList对象
        if(CustomerListUtils.isNotEmpty(makePanorama.getDesignPlanStoreReleaseDetailsAddList())){
            DesignPlanStoreReleaseDetails designPlanStoreReleaseDetails = null;
            for( DesignPlanStoreReleaseDetailsAdd designPlanStoreReleaseDetailsAdd : makePanorama.getDesignPlanStoreReleaseDetailsAddList() ){
                designPlanStoreReleaseDetails = new DesignPlanStoreReleaseDetails();
                designPlanStoreReleaseDetails.setDesignTemplateId(designPlanStoreReleaseDetailsAdd.getDesignTemplateId());
                designPlanStoreReleaseDetails.setDesignPlanId(designPlanStoreReleaseDetailsAdd.getDesignPlanId());
                designPlanStoreReleaseDetails.setStoreReleaseId(designPlanStoreRelease.getId());
                designPlanStoreReleaseDetails.setRenderingType(designPlanStoreReleaseDetailsAdd.getRenderingType());
                designPlanStoreReleaseDetails.setIsMain(designPlanStoreReleaseDetailsAdd.getIsMain());
                designPlanStoreReleaseDetails.setResourceId(designPlanStoreReleaseDetailsAdd.getResourceId());
                designPlanStoreReleaseDetails.setDesignPlanType(designPlanStoreReleaseDetailsAdd.getDesignPlanType());
                designPlanStoreReleaseDetails.setId(designPlanStoreReleaseDetailsAdd.getId());
                if(designPlanStoreReleaseDetailsAdd.getRecommendDesignPlanId() != null) {
                    designPlanStoreReleaseDetails.setRecommendDesignPlanId(designPlanStoreReleaseDetailsAdd.getRecommendDesignPlanId());
                }
                BaseModel.sysSave(designPlanStoreReleaseDetails, loginUser);
                if(designPlanStoreReleaseDetails.getId() == null || designPlanStoreReleaseDetails.getId() <= 0){
                    designPlanStoreReleaseDetailsMapper.insertSelective(designPlanStoreReleaseDetails);
                }else{
                    designPlanStoreReleaseDetailsMapper.updateByPrimaryKeySelective(designPlanStoreReleaseDetails);
                }
            }
        }
        resultVo.setId(designPlanStoreRelease.getId());
        resultVo.setUuid(designPlanStoreRelease.getUuid());
        return resultVo;
    }

    /**
     * 获取720分享
     * @param uuid
     * @return
     */
    @Override
    public DesignPlanStoreReleaseVo getPanorama(String uuid){
        //1.获取720分享主体信息
        DesignPlanStoreReleaseVo designPlanStoreReleaseVo = designPlanStoreReleaseMapper.getDesignPlanStoreRelease(uuid);
        if( designPlanStoreReleaseVo == null ){
            return null;
        }
        String housePicPath = designPlanStoreReleaseDetailsMapper.getResourcePathById(designPlanStoreReleaseVo.getHousePicId(), "res_house_pic");
        designPlanStoreReleaseVo.setHousePicPath(housePicPath);

        // 2.组装联系人信息.如果制作时没有选择联系人信息，则把方案创建人作为联系人信息
        Integer contactId = designPlanStoreReleaseVo.getUnionContactId();
        if( contactId != null ){
            UnionContact unionContact = unionContactMapper.selectByPrimaryKey(contactId);
            designPlanStoreReleaseVo.setContactName(unionContact.getName());
            designPlanStoreReleaseVo.setContactPhone(unionContact.getPhone());
            // LOGO
            String logo = designPlanStoreReleaseDetailsMapper.getResourcePathById(unionContact.getPicId(), "res_pic");
            designPlanStoreReleaseVo.setLogo(logo);
            /*ResPic resPic = resPicService.get(unionContact.getPicId());
            if( resPic != null ){
                designPlanStoreReleaseVo.setLogo(resPic.getPicPath());
            }*/
        }else{
            Integer userId = designPlanStoreReleaseVo.getUserId();
            String mobile = designPlanStoreReleaseDetailsMapper.getColumnValueFromTableById("sys_user", "mobile", userId);// TEMP
            String userName = designPlanStoreReleaseDetailsMapper.getColumnValueFromTableById("sys_user", "user_name", userId);// TEMP
            designPlanStoreReleaseVo.setContactName(userName);
            designPlanStoreReleaseVo.setContactPhone(mobile);
        }

        // 3.获取分享详情
        designPlanStoreReleaseVo = getDetailsListByReleaseVo(designPlanStoreReleaseVo);

        return designPlanStoreReleaseVo;
    }

    /**
     * 生成热点位置信息
     * @param designPlanStoreReleaseDetailsVo 当前空间
     * @param designPlanStoreReleaseDetailsList 所有其他空间
     * @param shareType
     * @return
     */
    public DesignPlanStoreReleaseDetailsVo generateCoordinate(DesignPlanStoreReleaseDetailsVo designPlanStoreReleaseDetailsVo, List<DesignPlanStoreReleaseDetails> designPlanStoreReleaseDetailsList, Integer shareType) {
        if( DesignPlanStoreReleaseDetails.RenderingType.ROAM_RENDER == designPlanStoreReleaseDetailsVo.getRenderingType().intValue() ){
            // 行走坐标信息
            Integer screenShotId = designPlanStoreReleaseDetailsVo.getResourceId();
            List<Roam> roamList = this.getWalkCoordinate(screenShotId);

            // 只有全户型分享的时候才可以空间穿透
            if( DesignPlanStoreRelease.ShareType.FULL_HOUSE == shareType.intValue() ) {
                for (Roam roam : roamList) {
                    String renderCoordinatePosition = roam.getRenderPosition();// 渲染相机位置
                    List<CoordinateVo> penetrationCoordinateList = this.getPenetrationCoordinate(roam.getFieldName(), renderCoordinatePosition, designPlanStoreReleaseDetailsList);
                    roam.setPenetrationCoordinateList(penetrationCoordinateList);
                }
            }
            designPlanStoreReleaseDetailsVo.setRoamList(roamList);
        }else if( DesignPlanStoreReleaseDetails.RenderingType.PANORAMA_RENDER == designPlanStoreReleaseDetailsVo.getRenderingType().intValue() ){
            Integer resRenderPicId = designPlanStoreReleaseDetailsVo.getResourceId();
            // 只有全屋分享才可以空间穿透
            if( DesignPlanStoreRelease.ShareType.FULL_HOUSE == shareType.intValue() ){
                // 渲染相机位置信息
                String renderCoordinatePosition = designRenderRoamMapper.getRenderCoordinatePosition(resRenderPicId);// TEMP，以后直接用resRenderPicService.get(id)，取渲染坐标字段就可以了
                List<CoordinateVo> penetrationCoordinateList = this.getPenetrationCoordinate(resRenderPicId, renderCoordinatePosition, designPlanStoreReleaseDetailsList);
                designPlanStoreReleaseDetailsVo.setCoordinateList(penetrationCoordinateList);
            }
            String resourcePath = designPlanStoreReleaseDetailsMapper.getResourcePathById(resRenderPicId, "res_render_pic");// TEMP
            designPlanStoreReleaseDetailsVo.setResourcePath(resourcePath);
        }
        return designPlanStoreReleaseDetailsVo;
    }

    /**
     * 获取穿透热点位置信息
     * @param resRenderPicId
     * @param designPlanStoreReleaseDetailsList
     * @return
     */
    public List<CoordinateVo> getPenetrationCoordinate(Integer resRenderPicId, String renderCoordinatePosition, List<DesignPlanStoreReleaseDetails> designPlanStoreReleaseDetailsList){
        List<CoordinateVo> penetrationCoordinateList = new ArrayList<>();;
        CoordinateVo coordinateVo = null;
        /** TODO 通过样板房找到可以穿透的其他空间，并生成热点位置信息 **/
        Integer designTemplateId = designRenderRoamMapper.getDesignTemplateIdByResRenderPicId(resRenderPicId);// TEMP
        // 没有渲染相机的位置信息则算不出穿透的坐标位置
        if( StringUtils.isBlank(renderCoordinatePosition) ){
            return null;
        }
        for( DesignPlanStoreReleaseDetails designPlanStoreReleaseDetails : designPlanStoreReleaseDetailsList ){
            Integer targetDesignTemplateId = designRenderRoamMapper.getDesignTemplateIdByResRenderPicId(designPlanStoreReleaseDetails.getResourceId());// TEMP
            // 获取两个样板房之间门的坐标位置
            String doorCoordinatePosition = designRenderRoamMapper.getCoordinatePosition(designTemplateId, targetDesignTemplateId);// TEMP，以后加了design_templet_jump_position_rel相关的代码后换成相应service查询
            if( StringUtils.isNotBlank(doorCoordinatePosition) ){
                coordinateVo = new CoordinateVo();
                coordinateVo.setTargetResourceId(designPlanStoreReleaseDetails.getResourceId());
                Roam roam1 = JsonUtil.fromJson(renderCoordinatePosition, Roam.class);
                Roam roam2 = JsonUtil.fromJson(doorCoordinatePosition, Roam.class);
                coordinateVo.setAtv(PanoramaUtil.getAtv(roam1, roam2));
                coordinateVo.setAth(PanoramaUtil.getAth(roam1, roam2));
                // 空间类型 TEMP
                String spaceType = designPlanRecommendedMapper.getFunctionName(designPlanStoreReleaseDetails.getDesignPlanId(), "design_plan_render_scene");
                coordinateVo.setSpaceType(spaceType);
                penetrationCoordinateList.add(coordinateVo);
            }
        }
        return penetrationCoordinateList.size()==0?null:penetrationCoordinateList;
    }

    /**
     * 获取行走热点位置信息
     * @return
     */
    public List<Roam> getWalkCoordinate(Integer screenShotId){
        List<Roam> roamList = new ArrayList<>();
        DesignRenderRoam designRenderRoam = designRenderRoamMapper.selectByScreenShotId(screenShotId);
        String roamConfigContext = "";
        if( designRenderRoam != null ){
            String roamConfigPath = designPlanStoreReleaseDetailsMapper.getResourcePathById(designRenderRoam.getRoamConfig(), "res_design");
            if( StringUtils.isNotBlank(roamConfigPath) ){
                roamConfigContext = FileUploadUtils.getFileContext(Utils.getAbsolutePath(roamConfigPath, null));
            }

            if( StringUtils.isNotBlank(roamConfigContext) ){
                roamList = JsonUtil.fromJson(roamConfigContext, new TypeToken<List<Roam>>(){}.getType());

                List<CoordinateVo> walkCoordinateList = null;
                CoordinateVo walkCoordinateVo = null;
                for( Roam roam : roamList ){
                    String fieldResourcePath = designPlanStoreReleaseDetailsMapper.getResourcePathById(roam.getFieldName(), "res_render_pic");// TEMP
                    roam.setFieldResourcePath(fieldResourcePath);
                    walkCoordinateList = new ArrayList<>();
                    for( Roam roam1 : roamList ){
                        if( roam.getFieldName() != roam1.getFieldName() ){
                            walkCoordinateVo = new CoordinateVo();
                            walkCoordinateVo.setTargetResourceId(roam1.getFieldName());
//                            String targetResourcePath = designPlanStoreReleaseDetailsMapper.getResourcePathById(roam1.getFieldName(), "res_render_pic");
//                            walkCoordinateVo.setTargetResourcePath(targetResourcePath);
                            walkCoordinateVo.setAth(PanoramaUtil.getAth(roam, roam1));
                            walkCoordinateVo.setAtv(PanoramaUtil.getAtv(roam, roam1));
                            walkCoordinateList.add(walkCoordinateVo);
                        }
                    }
                    roam.setWalkCoordinateList(walkCoordinateList);
                }
            }
        }
        return roamList;
    }

    /**
     * 更新浏览量
     * @param uuid
     * @return
     */
    @Override
    public int updatePv(String uuid){
        if( StringUtils.isBlank(uuid) ){
            return 0;
        }
        DesignPlanStoreReleaseVo designPlanStoreReleaseVo = designPlanStoreReleaseMapper.getDesignPlanStoreRelease(uuid);
        if( designPlanStoreReleaseVo == null ){
            return 0;
        }
        Integer pv = designPlanStoreReleaseVo.getPv();
        if( pv == null ){
            pv = 1;
        }else if( pv.intValue() < 20 ){
            pv += Utils.getRandomInt();
        }else if( pv.intValue() >= 20 ){
            pv += 1;
        }
        DesignPlanStoreRelease designPlanStoreRelease = new DesignPlanStoreRelease();
        designPlanStoreRelease.setId(designPlanStoreReleaseVo.getId());
        designPlanStoreRelease.setPv(pv);
        return designPlanStoreReleaseMapper.updateByPrimaryKeySelective(designPlanStoreRelease);
    }

    /**
     * 通过用户ID获取分享列表
     * @param search
     * @return
     */
    @Override
    public int getCountByUserId(DesignPlanStoreReleaseSearch search){
        if( search == null ){
            return 0;
        }
        //使用小区名称搜索
        if( search != null && StringUtils.isNotBlank(search.getLivingName())){
            List<Integer> livingIds = baseHouseMapper.getIdsByName(search.getLivingName());
            search.setLivingIds(livingIds);
        }

        return designPlanStoreReleaseMapper.getCountByUserId(search);
    }

    /**
     * 通过用户ID获取分享列表
     * @param search
     * @return
     */
    @Override
    public List<DesignPlanStoreReleaseVo> getListByUserId(DesignPlanStoreReleaseSearch search){
        if( search == null ){
            return null;
        }
        //使用小区名称搜索
        if( search != null && StringUtils.isNotBlank(search.getLivingName())){
            List<Integer> livingIds = baseHouseMapper.getIdsByName(search.getLivingName());
            search.setLivingIds(livingIds);
        }

        List<DesignPlanStoreReleaseVo> designPlanStoreReleaseVoList = designPlanStoreReleaseMapper.getListByUserId(search);
        List<Integer> houseIdList = new ArrayList<Integer>();
        Map<Integer,String> houseAreaMap = new HashMap<Integer,String>();

        if( designPlanStoreReleaseVoList != null && designPlanStoreReleaseVoList.size() > 0 ){
            for( DesignPlanStoreReleaseVo designPlanStoreReleaseVo : designPlanStoreReleaseVoList ){
                String coverPicPath = designPlanStoreReleaseVo.getCoverPicPath();
                // 如果方案没有设置封面图，则取主图方案最新的渲染缩略图。如果连渲染缩略图都没有，则取样板房的俯视图
                if( StringUtils.isBlank(coverPicPath) ){
                    Integer mainDesignPlanId = designPlanStoreReleaseVo.getMainDesignPlanId();
                    // 获取缩略图。目前其他模块代码不全的情况先通过sql直接查（临时写法）TEMP
                    coverPicPath = designPlanStoreReleaseMapper.getCoverPicPath(mainDesignPlanId);
                    designPlanStoreReleaseVo.setCoverPicPath(coverPicPath);
                }
                //获取详情信息
//                designPlanStoreReleaseVo = this.getDetailsListByReleaseVo(designPlanStoreReleaseVo);
                if(designPlanStoreReleaseVo.getHouseId() != null){
                    houseIdList.add(designPlanStoreReleaseVo.getHouseId());
                    houseAreaMap.put(designPlanStoreReleaseVo.getHouseId(),designPlanStoreReleaseVo.getAreaLongCode());
                }
            }
        }

        //得到户型下各房型空间数量
        Map<Integer,String> houseSpaceNumMap = baseHouseService.jointHouseSpaceNum(houseIdList);
        //得到户型区域信息
        Map<Integer,HouseAreaBo> houseAreaBoMap = baseHouseService.getHouseAreaInfoMap(houseAreaMap);

        for( DesignPlanStoreReleaseVo designPlanStoreReleaseVo : designPlanStoreReleaseVoList ){
            if(designPlanStoreReleaseVo.getHouseId() == null){
                continue;
            }
            if(houseSpaceNumMap != null && houseSpaceNumMap.size() > 0){
                if(houseSpaceNumMap.containsKey(designPlanStoreReleaseVo.getHouseId())){
                    designPlanStoreReleaseVo.setHouseSpaceNumStr(houseSpaceNumMap.get(designPlanStoreReleaseVo.getHouseId()));
                }
            }

            if(houseAreaBoMap != null && houseAreaBoMap.size() > 0){
                if(houseAreaBoMap.containsKey(designPlanStoreReleaseVo.getHouseId())){
                    HouseAreaBo areaBo = (HouseAreaBo)houseAreaBoMap.get(designPlanStoreReleaseVo.getHouseId());
                    if(areaBo == null){
                        continue;
                    }
                    designPlanStoreReleaseVo.setProvince(areaBo.getProvince());
                    designPlanStoreReleaseVo.setCity(areaBo.getCity());
                    designPlanStoreReleaseVo.setDistrict(areaBo.getDistrict());
                }
            }

        }
        return designPlanStoreReleaseVoList;
    }

    /**
     * TEMP
     */
	@Override
	public int createResPic(ResPic resPic) {
		return designPlanStoreReleaseMapper.createResPic(resPic);
	}

    /**
     * 删除我的分享
     * @param id
     * @param userId
     * @return
     */
	@Override
	public Boolean delStoreRelease(Integer id, Integer userId){
	    if( id == null || userId == null ){
            return false;
        }
        DesignPlanStoreRelease designPlanStoreRelease = designPlanStoreReleaseMapper.selectByPrimaryKey(id);
	    if( designPlanStoreRelease == null ){
            return false;
        }
        if( userId.intValue() != designPlanStoreRelease.getUserId().intValue() ){
            return false;
        }
        // 删除详情
        designPlanStoreReleaseDetailsMapper.deleteByStoreReleaseId(id);
	    // 删除主体
        designPlanStoreReleaseMapper.deleteByPrimaryKey(id);
	    return true;
    }

    /**
     * 获得720分享制作详情信息
     * @return
     */
    private DesignPlanStoreReleaseVo getDetailsListByReleaseVo(DesignPlanStoreReleaseVo designPlanStoreReleaseVo){
        if(designPlanStoreReleaseVo == null || designPlanStoreReleaseVo.getId() == null){
            return null;
        }
        List<DesignPlanStoreReleaseDetails> designPlanStoreReleaseDetailsList = designPlanStoreReleaseDetailsMapper.selectListByStoreReleaseId(designPlanStoreReleaseVo.getId());
        if( designPlanStoreReleaseDetailsList != null && designPlanStoreReleaseDetailsList.size() > 0 ) {
            List<DesignPlanStoreReleaseDetailsVo> designPlanStoreReleaseDetailsVoList = new ArrayList<>();
            DesignPlanStoreReleaseDetailsVo designPlanStoreReleaseDetailsVo = null;
            for (DesignPlanStoreReleaseDetails designPlanStoreReleaseDetails : designPlanStoreReleaseDetailsList) {
                designPlanStoreReleaseDetailsVo = new DesignPlanStoreReleaseDetailsVo();
                designPlanStoreReleaseDetailsVo.setIsMain(designPlanStoreReleaseDetails.getIsMain());
                if (DesignPlanStoreReleaseDetails.IsMain.YES == designPlanStoreReleaseDetails.getIsMain().intValue()) {
                    designPlanStoreReleaseVo.setMainDesignPlanId(designPlanStoreReleaseDetails.getDesignPlanId());
                }
                designPlanStoreReleaseDetailsVo.setDesignPlanType(designPlanStoreReleaseDetails.getDesignPlanType());
                designPlanStoreReleaseDetailsVo.setDesignPlanId(designPlanStoreReleaseDetails.getDesignPlanId());
                designPlanStoreReleaseDetailsVo.setRenderingType(designPlanStoreReleaseDetails.getRenderingType());
                designPlanStoreReleaseDetailsVo.setResourceId(designPlanStoreReleaseDetails.getResourceId());
                designPlanStoreReleaseDetailsVo.setDesignTemplateId(designPlanStoreReleaseDetails.getDesignTemplateId());
                // 获取缩略图路径。目前其他模块代码不全的情况先通过sql直接查（临时写法）TEMP
                String thumbPicPath = designPlanStoreReleaseDetailsMapper.getResourceThumbPicPath(designPlanStoreReleaseDetails.getResourceId());
                designPlanStoreReleaseDetailsVo.setThumbPicPath(thumbPicPath);

                // 获取资源路径。目前其他模块代码不全的情况先通过sql直接查（临时写法）TEMP
                String resourcePath = "";
                if (DesignPlanStoreReleaseDetails.RenderingType.VIDEO == designPlanStoreReleaseDetails.getRenderingType().intValue()) {// 视频需要去另外的res_render_video表取路径
                    resourcePath = designPlanStoreReleaseDetailsMapper.getResourcePathById(designPlanStoreReleaseDetails.getResourceId(), "res_render_video");
                    designPlanStoreReleaseDetailsVo.setResourcePath(resourcePath);
                } else if (DesignPlanStoreReleaseDetails.RenderingType.ROAM_RENDER == designPlanStoreReleaseDetails.getRenderingType().intValue()
                        || DesignPlanStoreReleaseDetails.RenderingType.PANORAMA_RENDER == designPlanStoreReleaseDetails.getRenderingType().intValue()) {// 多点720和单点720需要生成行走和穿透的热点坐标信息
                    // 生成行走和穿透坐标信息
                    designPlanStoreReleaseDetailsVo = this.generateCoordinate(designPlanStoreReleaseDetailsVo, designPlanStoreReleaseDetailsList, designPlanStoreReleaseVo.getShareType());
                } else {// 照片级直接取资源路径
                    resourcePath = designPlanStoreReleaseDetailsMapper.getResourcePathById(designPlanStoreReleaseDetails.getResourceId(), "res_render_pic");
                    designPlanStoreReleaseDetailsVo.setResourcePath(resourcePath);
                }

                // 空间类型 TEMP
                String spaceType = designPlanRecommendedMapper.getFunctionName(designPlanStoreReleaseDetailsVo.getDesignPlanId(), "design_plan_render_scene");// TEMP
                designPlanStoreReleaseDetailsVo.setSpaceType(spaceType);

                designPlanStoreReleaseDetailsVoList.add(designPlanStoreReleaseDetailsVo);
            }
            designPlanStoreReleaseVo.setDetails(designPlanStoreReleaseDetailsVoList);
        }
        return designPlanStoreReleaseVo;
    }

}
