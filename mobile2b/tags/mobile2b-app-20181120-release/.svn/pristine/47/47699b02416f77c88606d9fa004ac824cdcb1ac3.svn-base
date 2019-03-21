package com.nork.decorateOrder.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.decorateOrder.constant.DecorateSeckillOrderConstants;
import com.nork.decorateOrder.dao.DecorateSeckillOrderMapper;
import com.nork.decorateOrder.model.DecorateSeckill;
import com.nork.decorateOrder.model.DecorateSeckillOrder;
import com.nork.decorateOrder.service.DecorateSeckillOrderService;
import com.nork.system.model.SysUser;

@Service("decorateSeckillOrderService")
public class DecorateSeckillOrderServiceImpl implements DecorateSeckillOrderService {

	private final static Logger LOGGER = LoggerFactory.getLogger(DecorateSeckillOrderServiceImpl.class);
	
	private final static String LOGPREFIX = "[抢单订单被抢后的信息模块]:";
	
	@Autowired
	private DecorateSeckillOrderMapper decorateSeckillOrderMapper;
	
	@Override
	public DecorateSeckillOrder add(DecorateSeckill decorateSeckill, SysUser sysUser) {
		// ------参数验证 ->start
		if(decorateSeckill == null) {
			LOGGER.error(LOGPREFIX + "decorateSeckill = null");
			return null;
		}
		// ------参数验证 ->end
		
		// ------设置decorateSeckillOrder属性 ->start
		Date now = new Date();
		
		DecorateSeckillOrder decorateSeckillOrder = new DecorateSeckillOrder();
		decorateSeckillOrder.setCustomerId(decorateSeckill.getCustomerId());
		decorateSeckillOrder.setProprietorInfoId(decorateSeckill.getProprietorInfoId());
		decorateSeckillOrder.setDecorateSeckillId(decorateSeckill.getId());
		decorateSeckillOrder.setCompanyId(sysUser.getpCompanyId());
		decorateSeckillOrder.setUserId(sysUser.getId().longValue());
		decorateSeckillOrder.setPrice(decorateSeckill.getPrice());
		decorateSeckillOrder.setGmtCreate(now);
		decorateSeckillOrder.setModifier(sysUser.getNickName());
		decorateSeckillOrder.setGmtModified(now);
		decorateSeckillOrder.setIsDeleted(DecorateSeckillOrderConstants.DECORATESECKILLORDER_ISDELETED_DEFAULT);
		decorateSeckillOrder.setGmtSeckill(now);
		decorateSeckillOrder.setCreator(sysUser.getNickName());
		// ------设置decorateSeckillOrder属性 ->end
		
		this.add(decorateSeckillOrder);
		
		return decorateSeckillOrder;
	}

	@Override
	public void add(DecorateSeckillOrder decorateSeckillOrder) {
		// ------参数验证 ->start
		if(decorateSeckillOrder == null) {
			LOGGER.error(LOGPREFIX + "decorateSeckillOrder = null");
			return;
		}
		// ------参数验证 ->end
		
		decorateSeckillOrderMapper.insertSelective(decorateSeckillOrder);
	}

	@Override
	public DecorateSeckillOrder get(Long id) {
		// ------参数验证 ->start
		if(id == null) {
			LOGGER.error("{} id = null", LOGPREFIX);
		}
		// ------参数验证 ->end
		
		return decorateSeckillOrderMapper.selectByPrimaryKey(id);
	}
	
}
