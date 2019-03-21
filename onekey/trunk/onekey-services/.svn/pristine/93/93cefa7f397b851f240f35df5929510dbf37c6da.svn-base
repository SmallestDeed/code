package com.nork.mobile.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.onekeydesign.dao.DesignPlanRecommendedMapperV2;
import com.nork.onekeydesign.model.DesignPlanRecommended;
import com.nork.onekeydesign.model.ThumbData;
import com.nork.onekeydesign.service.DesignPlanAutoRenderService;
import com.nork.onekeydesign.service.DesignPlanService;
import com.nork.onekeydesign.service.UsedProductsService;
import com.nork.mobile.service.MobileDesignPlanService;
import com.nork.product.model.BaseProductStyle;
import com.nork.product.model.search.BaseProductStyleSearch;
import com.nork.product.service.BaseProductStyleService;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.service.SysUserService;

@Service("mobileDesignPlanService")
public class MobileDesignPlanServiceImpl implements MobileDesignPlanService{
	
	
	@Resource
	private SysUserService sysUserService;
	@Resource
	private DesignPlanService designPlanService;
	@Resource
	private UsedProductsService usedProductsService;
	@Autowired
	private BaseProductStyleService baseProductStyleService;
	@Autowired
	private ResRenderPicMapper resRenderPicMapper;
	@Autowired
	private DesignPlanRecommendedMapperV2 designPlanRecommendedMapperV2;
	@Autowired
	private DesignPlanAutoRenderService designPlanAutoRenderService;
	
	/**
	 * 移动端我的设计获取所有效果图
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEnvelope getMyDesignPlanListMobile(ThumbData thumbData) {
		//调用PC端的serviceImpl里的方法
		return designPlanAutoRenderService.getrenderPicByPage(thumbData);
	}


	/**
	 * 获取所有房间类型
	 */
	@Override
	public List<BaseProductStyle> getSpace(BaseProductStyleSearch baseProductStyleSearch) {
		
		return baseProductStyleService.getPaginatedList(baseProductStyleSearch);
	}
	
	/**
	 * 移动端我的设计    获取720的全景图
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@Override
	public ResponseEnvelope get720renderPicByPage(ThumbData thumbData) {
		ResponseEnvelope envelope = new ResponseEnvelope();
		//查询该用户共有多少渲染图
		int count = resRenderPicMapper.countRenderPicByPage(thumbData);
		if (count <= 0) {
			envelope.setTotalCount(count);
			return envelope;
		}
		if (thumbData.getStart() > count) {
			envelope.setDatalist(new ArrayList<>());
			return envelope;
		}
		//查询该用户的效果图列表
		List<ThumbData> list = resRenderPicMapper.getRenderPicByPage(thumbData);
		if (list == null || list.size() <= 0) {
			envelope.setDatalist(list);
			return envelope;
		}

		List<Long> ids = new ArrayList<Long>();
		for(ThumbData temp : list) {
			ids.add(temp.getCpId());
		}

		List<DesignPlanRecommended> recommendedList = designPlanRecommendedMapperV2
				.getStatusByIds(ids);
		if (recommendedList == null || recommendedList.size() <= 0) {
			envelope.setTotalCount(count);
			envelope.setDatalist(list);
			return envelope;
		}

		for (int i = 0; i < list.size(); i++) {
			ThumbData temp = (ThumbData) list.get(i);
			for (int j = 0; j < recommendedList.size(); j++) {
				DesignPlanRecommended recommended = recommendedList.get(j);
				if (recommended.getPlanId().longValue() != temp.getCpId())
					continue;

				if (Constants.RECOMMENDED_TYPE_SHARE == recommended
						.getRecommendedType().intValue()) {
					temp.setPubSt(recommended.getIsRelease());
					continue;
				}
				if (Constants.RECOMMENDED_TYPE_ONE_KEY_PUB == recommended
						.getRecommendedType().intValue()) {
					temp.setOneKeySt(recommended.getIsRelease());
					continue;
				}
			}
		}
//		count = list720.size();
		envelope.setTotalCount(count);
		envelope.setDatalist(list);
		return envelope;
	}

}
