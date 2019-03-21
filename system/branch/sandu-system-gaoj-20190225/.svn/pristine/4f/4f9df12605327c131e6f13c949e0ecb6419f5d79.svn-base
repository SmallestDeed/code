package com.sandu.service.act.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act.model.WxActBargain;
import com.sandu.api.act.service.WxActBargainService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.service.act.dao.WxActBargainMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * bargain
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:24
 */
@Slf4j
@Service("wxActBargainService")
public class WxActBargainServiceImpl implements WxActBargainService {

    @Autowired
    private WxActBargainMapper wxActBargainMapper;

    @Override
    public void createWxActBargain(WxActBargain wxActBargain) {
        wxActBargainMapper.insertWxActBargain(wxActBargain);
    }

    @Override
    public int modifyWxActBargain(WxActBargain wxActBargain) {
        return wxActBargainMapper.updateWxActBargainById(wxActBargain);
    }

    @Override
    public int removeWxActBargain(String actId) {
        return wxActBargainMapper.deleteWxActBargainById(actId);
    }

    @Override
    public WxActBargain getWxActBargain(String actId) {
        return wxActBargainMapper.selectWxActBargainById(actId);
    }
    
    @Override
	public WxActBargain getWxActBargain(String actId, String appId) {
		// TODO Auto-generated method stub
		return wxActBargainMapper.selectWxActBargainByIdAndAppId(actId, appId);
	}

	@Override
	public Integer getWxActBargainStatus(WxActBargain actEntity) {
		Date now = new Date();
		if(actEntity!=null) {

			//已结束(过时,没库存,结束活动)
			if(actEntity.getProductRemainCount()==0
					||actEntity.getEndTime().compareTo(now)<=0
					|| actEntity.getIsEnable()==0) {
				return WxActBargain.STATUS_ENDED;
			}
			//未开始
			if(now.compareTo(actEntity.getBegainTime())<=0) {
				return WxActBargain.STATUS_UNBEGIN;
			}
			//进行中
			return WxActBargain.STATUS_ONGOING;
		}
		
		throw new SystemException("ACT_NOT_EXIST","活动不存在"); //活动不存在
	}

	@Override
	public Integer getWxActBargainStatus(String actId,String appId) {
		WxActBargain actEntity = this.getWxActBargain(actId,appId);
		if(actEntity==null) {
			throw new SystemException("ACT_NOT_EXIST","活动不存在");//活动不存在
		}
		return this.getWxActBargainStatus(actEntity);
	}

	@Override
	public boolean reduceProductInventory(String actId) {
		int flag = wxActBargainMapper.updateToReduceProductRemainCount(actId);
		if(flag>0) {
			return true;
		}
		return false;
	}

	@Override
	public void increaseParticipants(String actId) {
		// TODO Auto-generated method stub
		wxActBargainMapper.updateToIncreaseRegistrationCount(actId);
	}


	@Override
	public List<WxActBargain> getAllEffectiveBargain(Date currentTime) {
		return wxActBargainMapper.selectAllEffectiveBargainAward(currentTime);
	}
	
	@Override
	public List<WxActBargain> getWillExpireList() {
		return wxActBargainMapper.selectWillExpireList();
	}

	@Override
	public void modifyVitualCount() {
		wxActBargainMapper.updateVitualCount();
	}


	@Override
	public List<WxActBargain> selectByAppids(List<String> appids, Integer page, Integer limit) {
		return wxActBargainMapper.selectByAppids(appids,page,limit);
	}

    @Override
    public int countByAppids(List<String> appids) {
        return wxActBargainMapper.countByAppids(appids);
    }

    @Override
    public WxActBargain selectByActName(String actName) {
        return wxActBargainMapper.selectByActName(actName);
    }

    @Override
    public WxActBargain getWxActBargainByActName(String actName) {
        return this.selectByActName(actName);
    }
}
