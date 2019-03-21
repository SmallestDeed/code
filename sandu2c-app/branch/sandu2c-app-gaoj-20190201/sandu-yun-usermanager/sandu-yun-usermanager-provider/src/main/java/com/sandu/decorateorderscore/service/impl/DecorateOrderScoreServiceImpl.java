package com.sandu.decorateorderscore.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.decorateorderscore.dao.DecorateOrderScoreMapper;
import com.sandu.decorateorderscore.model.DecorateOrder;
import com.sandu.decorateorderscore.model.DecorateOrderQuery;
import com.sandu.decorateorderscore.model.DecorateOrderScore;
import com.sandu.decorateorderscore.model.DecorateOrderVO;
import com.sandu.decorateorderscore.service.DecorateOrderScoreService;
import com.sandu.user.dao.SysUserMessageMapper;
import com.sandu.user.model.SysUserMessage;

@Service("decorateOrderScoreService")
public class DecorateOrderScoreServiceImpl implements DecorateOrderScoreService {

	@Resource
	private DecorateOrderScoreMapper decorateOrderScoreMapper;
	
	@Resource
	private SysUserMessageMapper sysUserMessageMapper;
	
	@Override
	@Transactional
	public int insertDecorateOrderScore(DecorateOrderScore score) {
		SysUserMessage message = new SysUserMessage();
		message.setId(score.getMessageId());
		message.setCommentFlag(1);
		//插入打分评价表
		int id = decorateOrderScoreMapper.insertSelective(score);
		if(id > 0) {
			//标记消息为已评价
			sysUserMessageMapper.updateByPrimaryKeySelective(message);
		}
		return id;
	}

	@Override
	public List<DecorateOrderVO> getDecorateOrderList(DecorateOrderQuery query) {
		return decorateOrderScoreMapper.selectDecorateOrderList(query);
	}

	@Override
	public DecorateOrder getDecorateOrderById(Integer taskId) {
		return decorateOrderScoreMapper.selectDecorateOrderById(taskId);
	}

	@Override
	public int getDecorateOrderCount() {
		return decorateOrderScoreMapper.selectDecorateOrderCount();
	}

}
