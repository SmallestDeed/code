package com.sandu.service.act.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.act.input.WxActBargainRegistrationQuery;
import com.sandu.api.act.model.WxActBargain;
import com.sandu.api.act.model.WxActBargainDecorateRecord;
import com.sandu.api.act.model.WxActBargainInviteRecord;
import com.sandu.api.act.model.WxActBargainRegistration;
import com.sandu.api.act.output.RegistrationStatusVO;
import com.sandu.api.act.output.WxActBargainRegCutResultVO;
import com.sandu.api.act.output.WxActBargainRegDashBoardResultVO;
import com.sandu.api.act.output.WxActBargainRegShipmentInfoVO;
import com.sandu.api.act.output.WxActBargainRegistrationAnalyseResultVO;
import com.sandu.api.act.service.WxActBargainDecorateRecordService;
import com.sandu.api.act.service.WxActBargainInviteRecordService;
import com.sandu.api.act.service.WxActBargainRegistrationService;
import com.sandu.api.act.service.WxActBargainService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.user.model.SysUser;
import com.sandu.service.act.dao.WxActBargainRegistrationMapper;
import com.sandu.util.UUIDUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * registration
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:31
 */
@Slf4j
@Service("wxActBargainRegistrationService")
public class WxActBargainRegistrationServiceImpl implements WxActBargainRegistrationService {

	private final static Logger logger = LoggerFactory.getLogger(WxActBargainRegistrationServiceImpl.class);
    @Autowired
    private WxActBargainRegistrationMapper wxActBargainRegistrationMapper;

    @Autowired
    private WxActBargainService wxActBargainService;
    
    @Autowired
    private WxActBargainInviteRecordService wxActBargainInviteRecordService;
    
    @Autowired
    private WxActBargainDecorateRecordService wxActBargainDecorateRecordService;
    
    @Override
    public void createWxActBargainRegistration(WxActBargainRegistration registration) {
        wxActBargainRegistrationMapper.insertWxActBargainRegistration(registration);
    }

    @Override
    public int modifyWxActBargainRegistration(WxActBargainRegistration registration) {
        return wxActBargainRegistrationMapper.updateWxActBargainRegistrationById(registration);
    }

    @Override
    public int removeWxActBargainRegistration(String regId) {
        return wxActBargainRegistrationMapper.deleteWxActBargainRegistrationById(regId);
    }

    @Override
    public WxActBargainRegistration getWxActBargainRegistration(String regId) {
        return wxActBargainRegistrationMapper.selectWxActBargainRegistrationById(regId);
    }

    @Override
    public int refreshRegAwardStatusToWait(String regId) {
    	return wxActBargainRegistrationMapper.updateRegAwardStatusToWait(regId);
    }


	@Override
	public RegistrationStatusVO getWxActBargainRegistrationStatus(String actId, SysUser user) {
		WxActBargainRegistration regEntity = this.getWxActBargainRegistration(actId,user.getOpenId());
		if(regEntity!=null) {
			//如果任务已完成并且没出现异常,但是还没领奖,则前端显示领奖按钮,不考虑活动是否已结束
			if(regEntity.getCompleteStatus()==WxActBargainRegistration.COMPLETE_STATUS_FINISH
					&& regEntity.getAwardsStatus()==WxActBargainRegistration.AWARDS_STATUS_UNAWRD
					&& regEntity.getExceptionStatus()==WxActBargainRegistration.EXCEPTION_STATUS_OK) {
				return new RegistrationStatusVO(WxActBargainRegistration.STATUS_CODE_UNAWARD,regEntity.getId());
			}
			//如果任务已完成并且没出现异常,但是已领奖,则前端显示领奖成功按钮,不考虑活动是否已结束
			if(regEntity.getCompleteStatus()==WxActBargainRegistration.COMPLETE_STATUS_FINISH
					&&regEntity.getAwardsStatus() == WxActBargainRegistration.AWARDS_STATUS_WAIT_AWARD
					&&regEntity.getExceptionStatus() == WxActBargainRegistration.EXCEPTION_STATUS_OK) {
				return new RegistrationStatusVO(WxActBargainRegistration.STATUS_CODE_AWARDED,regEntity.getId());
			}
			
			//除上面情况,其他情况如果活动结束,则前端显示活动结束按钮
			Integer actStatus = wxActBargainService.getWxActBargainStatus(actId,user.getAppId());
			if(actStatus==WxActBargain.STATUS_UNBEGIN) {
				return new RegistrationStatusVO(WxActBargainRegistration.STATUS_CODE_ACT_UNBEGIN,regEntity.getId());
			}else if(actStatus==WxActBargain.STATUS_ENDED) {
				return new RegistrationStatusVO(WxActBargainRegistration.STATUS_CODE_ACT_ENDED,regEntity.getId());
			}
			
			//活动正常进行,则返回邀请状态,没有邀请记录前端显示"极速砍价",有邀请记录前端显示"邀请好友"
			if(regEntity.getInviteStatus()==WxActBargainRegistration.INVITE_STATUS_UNINVITE) {
				return new RegistrationStatusVO(WxActBargainRegistration.STATUS_CODE_UNINVITE,regEntity.getId());
			}else if(regEntity.getInviteStatus()==WxActBargainRegistration.INVITE_STATUS_INVITED) {
				return new RegistrationStatusVO(WxActBargainRegistration.STATUS_CODE_INVITING,regEntity.getId());
			}
			logger.error("活动状态异常!actId:"+actId+"openId:"+user.getOpenId());
			throw new RuntimeException("活动状态异常!actId:"+actId+"openId:"+user.getOpenId());
		}else {
			//除上面情况,其他情况如果活动结束,则前端显示活动结束按钮
			Integer actStatus = wxActBargainService.getWxActBargainStatus(actId,user.getAppId());
			if(actStatus==WxActBargain.STATUS_UNBEGIN) {
				return new RegistrationStatusVO(WxActBargainRegistration.STATUS_CODE_ACT_UNBEGIN,null);
			}else if(actStatus==WxActBargain.STATUS_ENDED) {
				return new RegistrationStatusVO(WxActBargainRegistration.STATUS_CODE_ACT_ENDED,null);
			}
			return new RegistrationStatusVO(WxActBargainRegistration.STATUS_CODE_UNINVITE,null);
		}
		
	}
	
	
	private WxActBargainRegistration getWxActBargainRegistration(String actId, String openId) {
		// TODO Auto-generated method stub
		return wxActBargainRegistrationMapper.selectWxActBargainRegistrationByActIdAndOpenId(actId,openId);
	}
	
	

	@Override
	public WxActBargainRegCutResultVO cutPriceByMyself(String actId, SysUser user) {
		WxActBargain actEntity = wxActBargainService.getWxActBargain(actId,user.getAppId());
		checkActStatus(actEntity);
		WxActBargainRegistration regEntity = this.getWxActBargainRegistration(actId, user.getOpenId());
		if(regEntity==null) {
			regEntity = this.buildRegEntityByMyselfCut(actEntity, user);
			this.createWxActBargainRegistration(regEntity);
		}else {
			setRegistrationInviteStatusToInvited(regEntity.getId());
		}
		
		if(wxActBargainInviteRecordService.isCut(regEntity.getId(),user.getOpenId())) {
			throw new SystemException("不能重复砍价!");
		}
		
		//计算随机金额
		Double cutPrice = this.calRandomCutPrice(actEntity.getMyCutPriceMax(),actEntity.getMyCutPriceMin());
		WxActBargainInviteRecord inviteRecord = this.buildInviteRecord(regEntity, user, cutPrice);
		
		//先增加砍价记录
		wxActBargainInviteRecordService.createWxActBargainInviteRecord(inviteRecord);
		
		//累计参与人数
		wxActBargainService.increaseParticipants(actEntity.getId());
		
		//执行砍价
		executeCutPrice(regEntity.getId(),cutPrice);
		
		//更新任务状态与扣减库存
		boolean isComplete = refreshRegCompleteStatusAndReduceProductInventory(actEntity.getId(),regEntity.getId());
		
		return new WxActBargainRegCutResultVO(regEntity.getOpenId(),regEntity.getId(),regEntity.getProductName(),cutPrice,isComplete);
	}
	
	/**
	 * 更新任务状态
	 * @param actId
	 * @param regId
	 * @return 是否已完成任务
	 */
	private boolean refreshRegCompleteStatusAndReduceProductInventory(String actId,String regId) {
		// TODO Auto-generated method stub
		WxActBargainRegistration newRegEntity = this.getWxActBargainRegistration(regId);
		//成功完成任务
		if(newRegEntity.getProductRemainPrice()<=0) {
			//更新报名参加的活动状态
			int updateCount = this.refreshRegCompleteStatusToFinish(newRegEntity.getId());
			//更新活动商品库存
			if(updateCount>0) {
				boolean isSuccess = wxActBargainService.reduceProductInventory(actId);
				//如果扣减失败
				if(!isSuccess) {
					logger.warn("没库存了:"+actId);
					this.recordNoProductInventoryException(regId);
				}else {
					return true;
				}
			}
		}
		return false;
	}
	
	
	private void recordNoProductInventoryException(String regId) {
		WxActBargainRegistration updateRegEntity = new WxActBargainRegistration();
		updateRegEntity.setId(regId);
		updateRegEntity.setExceptionStatus(WxActBargainRegistration.EXCEPTION_STATUS_NO_STOCK);
		wxActBargainRegistrationMapper.updateWxActBargainRegistrationById(updateRegEntity);
	}

	/**
	 * 更新任务状态为已完成
	 * @param regId
	 * @return
	 */
	private int refreshRegCompleteStatusToFinish(String regId) {
		return wxActBargainRegistrationMapper.updateRegCompleteStatusToFinish(regId);
	}

	/**
	 * 执行砍价
	 * @param regId
	 * @param cutPrice
	 */
	private void executeCutPrice(String regId, Double cutPrice) {
		wxActBargainRegistrationMapper.updateToReduceRegProductRemainPriceById(regId,cutPrice);
	}

	/**
	 * 计算随机砍掉金额
	 * @return
	 */
	private Double calRandomCutPrice(Double max,Double min) {
		if(max.doubleValue()==min.doubleValue()) {
			return max;
		}
		max = max*100;
		min = min*100;
		Random random = new Random();
		Double cutPrice = Double.valueOf(random.nextInt(max.intValue() - min.intValue())+ min.intValue())/100;
		return cutPrice;
	}
	
	
	
	private WxActBargainInviteRecord buildInviteRecord(WxActBargainRegistration regEntity,SysUser user,Double cutPrice) {
		WxActBargainInviteRecord inviteRecord = new WxActBargainInviteRecord();
		inviteRecord.setId(UUIDUtil.getUUID());
		inviteRecord.setRegistrationId(regEntity.getId());
		inviteRecord.setOpenId(user.getOpenId());
		inviteRecord.setNickname(user.getNickName());
		inviteRecord.setHeadPic(user.getHeadPic());
		inviteRecord.setCutPrice(cutPrice);
		inviteRecord.setRemainPrice(regEntity.getProductRemainPrice()-cutPrice);
		inviteRecord.setAppId(user.getAppId());
		inviteRecord.setGmtCreate(new Date());
		inviteRecord.setIsDeleted(0);
		return inviteRecord;
	}
	
	/**
	 * 将任务邀请状态设置为已邀请
	 * @param regId
	 */
	private void setRegistrationInviteStatusToInvited(String regId){
		WxActBargainRegistration updateRegEntity = new WxActBargainRegistration();
		updateRegEntity.setId(regId);
		updateRegEntity.setInviteStatus(WxActBargainRegistration.INVITE_STATUS_INVITED);
		wxActBargainRegistrationMapper.updateWxActBargainRegistrationById(updateRegEntity);
	}
	
	

	
	private WxActBargainRegistration buildRegEntityByMyselfCut(WxActBargain actEntity,SysUser user) {
		WxActBargainRegistration regEntity = initRegEntity(actEntity,user);
		regEntity.setInviteStatus(WxActBargainRegistration.INVITE_STATUS_INVITED);
		return regEntity;
	}
	
	private WxActBargainRegistration buildRegEntityByDecorate(WxActBargain actEntity,SysUser user) {
		WxActBargainRegistration regEntity = initRegEntity(actEntity,user);
		regEntity.setDecorateStatus(WxActBargainRegistration.DECORATE_STATUS_DECORATED);
		return regEntity;
		
	}
	/**
	 * 构建任务对象
	 * @param actEntity
	 * @param user
	 * @return
	 */
	private WxActBargainRegistration initRegEntity(WxActBargain actEntity,SysUser user) {
		Date now = new Date();
		WxActBargainRegistration regEntity = new WxActBargainRegistration();
		regEntity.setId(UUIDUtil.getUUID());
		regEntity.setActId(actEntity.getId());
		regEntity.setOpenId(user.getOpenId());
		regEntity.setNickname(user.getNickName());
		regEntity.setHeadPic(user.getHeadPic());
		regEntity.setProductName(actEntity.getProductName());
		regEntity.setProductPrice(actEntity.getProductDiscountPrice());
		regEntity.setProductMinPrice(actEntity.getProductMinPrice());
		//优惠价-底价=需要砍掉的金额
		regEntity.setProductRemainPrice(actEntity.getProductDiscountPrice()-actEntity.getProductMinPrice());
		regEntity.setCutMethodPriceMin(actEntity.getCutMethodPriceMin());
		regEntity.setCutMethodPriceMax(actEntity.getCutMethodPriceMax());
		regEntity.setInviteStatus(WxActBargainRegistration.INVITE_STATUS_UNINVITE);
		regEntity.setDecorateStatus(WxActBargainRegistration.DECORATE_STATUS_UNDECORATE);
		regEntity.setAwardsStatus(WxActBargainRegistration.AWARDS_STATUS_UNAWRD);
		regEntity.setExceptionStatus(WxActBargainRegistration.EXCEPTION_STATUS_OK);
		regEntity.setCompleteStatus(WxActBargainRegistration.COMPLETE_STATUS_UNFINISH);
		regEntity.setShipmentStatus(WxActBargainRegistration.SHIPMENT_STATUS_UNDELIVERED);
		regEntity.setInviteCutPriceSum(0);
		regEntity.setInviteCutRecordCount(0);
		regEntity.setAppId(user.getAppId());
		regEntity.setCreator(user.getNickName());
		regEntity.setGmtCreate(now);
		regEntity.setModifier(user.getNickName());
		regEntity.setGmtModified(now);
		regEntity.setIsDeleted(0);
		return regEntity;
	}

	
	@Override
	public WxActBargainRegCutResultVO cutPriceByDecorate(String actId, SysUser user,Long houseId,String houseName) {
		WxActBargain actEntity = wxActBargainService.getWxActBargain(actId,user.getAppId());
		checkActStatus(actEntity);
		WxActBargainRegistration regEntity = this.getWxActBargainRegistration(actId, user.getOpenId());
		if(regEntity==null) {
			regEntity = this.buildRegEntityByDecorate(actEntity, user);
			this.createWxActBargainRegistration(regEntity);
		}else {
			setRegistrationDecorateStatusToDecorated(regEntity.getId());
		}
		
		//判断之前是否已经装修了
		if(isDecorateBefore(regEntity.getId())) {
			return new WxActBargainRegCutResultVO(regEntity.getOpenId(),regEntity.getId(),regEntity.getProductName(),new Double(0),false);
		}else {
			//计算随机金额
			Double cutPrice = new Double(50);
			WxActBargainDecorateRecord decorateRecord = this.buildDecorateRecord(regEntity, user, cutPrice,houseId,houseName);
			
			//先增加体验装进我家记录
			wxActBargainDecorateRecordService.createWxActBargainDecorateRecord(decorateRecord);
					
			//执行砍价
			executeCutPrice(regEntity.getId(),cutPrice);
			
			//更新任务状态及库存
			boolean isComplete = refreshRegCompleteStatusAndReduceProductInventory(actEntity.getId(),regEntity.getId());
			
			return new WxActBargainRegCutResultVO(regEntity.getOpenId(),regEntity.getId(),regEntity.getProductName(),cutPrice,isComplete);
		}
	}
	
	/**
	 * 检查活动状态
	 */
	private void checkActStatus(WxActBargain actEntity) {
		Integer actStatus = wxActBargainService.getWxActBargainStatus(actEntity);
		if(actStatus==WxActBargain.STATUS_UNBEGIN) {
			throw new SystemException("活动未开始!");
		}else if(actStatus==WxActBargain.STATUS_ENDED) {
			throw new SystemException("活动已结束!");
		}
	}

	/**
	 * 是否已经进行了装进我家砍价
	 * @param regId
	 * @param openId
	 * @return
	 */
	private boolean isDecorateBefore(String regId) {
		WxActBargainDecorateRecord record =wxActBargainDecorateRecordService.getWxActBargainDecorateRecordByRegId(regId);
		if(record!=null) {
			return true;
		}
		return false;
	}

	private WxActBargainDecorateRecord buildDecorateRecord(WxActBargainRegistration regEntity, SysUser user,
			Double cutPrice,Long houseId,String houseName) {
		WxActBargainDecorateRecord decorateRecord = new WxActBargainDecorateRecord();
		decorateRecord.setId(UUIDUtil.getUUID());
		decorateRecord.setRegistrationId(regEntity.getId());
		decorateRecord.setOpenId(user.getOpenId());
		decorateRecord.setHeadPic(user.getHeadPic()); 
		decorateRecord.setHouseId(houseId);
		decorateRecord.setHouseName(houseName);
		decorateRecord.setCutPrice(cutPrice);
		decorateRecord.setRemainPrice(regEntity.getProductRemainPrice()-cutPrice);
		decorateRecord.setAppId(user.getAppId());
		decorateRecord.setCreator(user.getNickName());
		decorateRecord.setGmtCreate(new Date());
		decorateRecord.setIsDeleted(0);
		return decorateRecord;
	}

	/**
	 * 将任务邀请状态设置为已装修
	 * @param regId
	 */
	private void setRegistrationDecorateStatusToDecorated(String regId){
		WxActBargainRegistration updateRegEntity = new WxActBargainRegistration();
		updateRegEntity.setId(regId);
		updateRegEntity.setDecorateStatus(WxActBargainRegistration.DECORATE_STATUS_DECORATED);
		wxActBargainRegistrationMapper.updateWxActBargainRegistrationById(updateRegEntity);
	}

	@Override
	public String getWxActBargainInviteRecordCutStatus(String actId, String openId, String registrationId) {
		WxActBargain actEntity = wxActBargainService.getWxActBargain(actId);
		Integer actStatus = wxActBargainService.getWxActBargainStatus(actEntity);
		WxActBargainInviteRecord inviteRecord = wxActBargainInviteRecordService.getWxActBargainInviteRecord(registrationId,openId);
		if(actStatus==WxActBargain.STATUS_ENDED && inviteRecord==null) {
			return "ACT_ENDED_UN_CUT";
		}else if(actStatus==WxActBargain.STATUS_ENDED && inviteRecord!=null) {
			return "ACT_ENDED_CUT";
		}else if(actStatus==WxActBargain.STATUS_UNBEGIN) {
			return "ACT_UNBEGIN";
		}
		WxActBargainRegistration regEntity = wxActBargainRegistrationMapper.selectWxActBargainRegistrationById(registrationId);
		if(regEntity==null) {
			throw new SystemException("任务不存在!");
		}
		if(regEntity.getCompleteStatus()==WxActBargainRegistration.COMPLETE_STATUS_FINISH) {
			return "REG_COMPLETE";
		}
		
		
		
		if(inviteRecord==null) {
			return "UN_CUT";
		}else{
			return "CUT";
		}
	}

	@Override
	public WxActBargainRegCutResultVO cutPriceByInvite(String actId,String registrationId, SysUser user) {
		WxActBargain actEntity = wxActBargainService.getWxActBargain(actId,user.getAppId());
		checkActStatus(actEntity);
		WxActBargainRegistration regEntity = this.getWxActBargainRegistration(registrationId);
		if(regEntity==null) {
			throw new SystemException("任务不存在!");
		}
		
		if(regEntity.getCompleteStatus()==WxActBargainRegistration.COMPLETE_STATUS_FINISH) {
			throw new SystemException("REG_COMPLETE","任务已完成!");
		}
		
		//已帮好友砍价.
		if(wxActBargainInviteRecordService.isCut(registrationId,user.getOpenId())) {
			throw new SystemException("不能重复砍价!");
		}
		
		
		//计算随机金额
		Double cutPrice = this.calRandomCutPrice(regEntity.getCutMethodPriceMax(),regEntity.getCutMethodPriceMin());
		WxActBargainInviteRecord inviteRecord = this.buildInviteRecord(regEntity, user, cutPrice);
		//先增加砍价记录
		wxActBargainInviteRecordService.createWxActBargainInviteRecord(inviteRecord);
		
		//累计参与人数
		//wxActBargainService.increaseParticipants(actEntity.getId());
		
		//执行砍价
		executeCutPrice(regEntity.getId(),cutPrice);
		
		//更新任务状态与扣减库存
		boolean isComplete = refreshRegCompleteStatusAndReduceProductInventory(actEntity.getId(),regEntity.getId());
		
		return new WxActBargainRegCutResultVO(regEntity.getOpenId(),regEntity.getId(),regEntity.getProductName(),cutPrice,isComplete);
	}

	@Override
	public PageInfo<WxActBargainRegistrationAnalyseResultVO> getWxActBargainRegAnalyseResultList(
			WxActBargainRegistrationQuery query) {
		// TODO Auto-generated method stub
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<WxActBargainRegistrationAnalyseResultVO> list = wxActBargainRegistrationMapper.selectWxActBargainRegAnalyseResult(query);
		return new PageInfo<WxActBargainRegistrationAnalyseResultVO>(list);
	}

	@Override
	public int modifyShipmentNo(String regId, String carrier,String shipmentNo, SysUser user) {
		// TODO Auto-generated method stub
		WxActBargainRegistration wxActBargainRegistration = new WxActBargainRegistration();
		wxActBargainRegistration.setCarrier(carrier);
		wxActBargainRegistration.setShipmentNo(shipmentNo);
		wxActBargainRegistration.setShipmentStatus(WxActBargainRegistration.SHIPMENT_STATUS_DELIVERED);
		wxActBargainRegistration.setId(regId);
		wxActBargainRegistration.setModifier(user.getUserName());
		wxActBargainRegistration.setGmtModified(new Date());
		return wxActBargainRegistrationMapper.updateWxActBargainRegistrationById(wxActBargainRegistration);
	}

	@Override
	public WxActBargainRegDashBoardResultVO getWxActBargainRegDashBoardResultList(String actId,Date beginTime,Date endTime) {
		WxActBargainRegDashBoardResultVO retVo = new WxActBargainRegDashBoardResultVO();
		retVo.setRegList(wxActBargainRegistrationMapper.selectRegCount(actId,beginTime,endTime));
		retVo.setRegSuccessList(wxActBargainRegistrationMapper.selectRegSuccessCount(actId,beginTime,endTime));
		retVo.setCutList(wxActBargainRegistrationMapper.selectCutCount(actId,beginTime,endTime));
		return retVo;
	}
	

    @Override
    public List<WxActBargainRegistration> getBargainRegistrationsByActIds(List<String> ids) {
        return wxActBargainRegistrationMapper.getBargainRegistrationsByActIds(ids);
    }
    
    @Override
	public List<WxActBargainRegistration> getBargainRegistrationsByActId(String id) {
    	if(StringUtils.isBlank(id)) {
    		return null;
    	}
		List<String> ids = new ArrayList<String>();
		ids.add(id);
    	return wxActBargainRegistrationMapper.getBargainRegistrationsByActIds(ids);
    	
	}

	@Override
	public WxActBargainRegShipmentInfoVO getShipmentInfo(String regId) {
		// TODO Auto-generated method stub
		WxActBargainRegistration reg = wxActBargainRegistrationMapper.selectWxActBargainRegistrationById(regId);
		if(reg!=null) {
			WxActBargainRegShipmentInfoVO vo = new WxActBargainRegShipmentInfoVO();
			vo.setCarrier(reg.getCarrier());
			vo.setShipmentNo(reg.getShipmentNo());
			return vo;
		}
		return null;
	}

	@Override
	public List<WxActBargainRegistration> getWxActBargainRegList(List<String> regIdList) {
		return wxActBargainRegistrationMapper.selectWxActBargainRegistrationByIdList(regIdList);
	}
}
