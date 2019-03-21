package com.nork.mobile.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.design.dao.DesignPlanAutoRenderMapper;
import com.nork.design.dao.DesignPlanRecommendedMapperV2;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.ThumbData;
import com.nork.design.service.DesignPlanAutoRenderService;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.UsedProductsService;
import com.nork.mobile.service.MobileDesignPlanService;
import com.nork.product.model.BaseProductStyle;
import com.nork.product.model.search.BaseProductStyleSearch;
import com.nork.product.service.BaseProductStyleService;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.service.SysUserService;
import com.sun.media.jfxmedia.logging.Logger;

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
	@Autowired
	private DesignPlanRenderSceneService designPlanRenderSceneService;
	@Autowired
	private DesignPlanAutoRenderMapper designPlanAutoRenderMapper;
	
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

	/**
     * 逻辑删除我的设计、我的任务、我的消息
     * @param planId
     * @return
     */
	@Override
	public Object deleteMyDesignPlanAndTask(Integer planId) {
		if(planId != null) {
			DesignPlanRenderScene scene = designPlanRenderSceneService.get(planId);
			AutoRenderTaskState taskState = designPlanAutoRenderMapper.selectTaskStateByBusinessId(planId);
			if(scene != null) {
				scene.setIsDeleted(new Integer(1));
			}else {
				return new ResponseEnvelope<>(false,"参数planId错误");
			}
			try {
				//逻辑删除我的设计
				designPlanRenderSceneService.update(scene);
				if(taskState != null) {
					//根据business_id 逻辑删除任务状态
					designPlanAutoRenderMapper.updateAutoRenderTaskStateByBusinessId(planId);
				}
			} catch (Exception e) {
				return new ResponseEnvelope<>(false,"删除失败");
			}
		}
		return new ResponseEnvelope<>(true,"删除我的设计、我的任务和我的消息成功！");
	}

}
