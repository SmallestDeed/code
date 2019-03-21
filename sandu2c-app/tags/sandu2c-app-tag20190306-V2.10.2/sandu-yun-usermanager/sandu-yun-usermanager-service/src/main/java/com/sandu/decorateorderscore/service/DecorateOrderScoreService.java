package com.sandu.decorateorderscore.service;

import java.util.List;

import com.sandu.decorateorderscore.model.DecorateOrder;
import com.sandu.decorateorderscore.model.DecorateOrderQuery;
import com.sandu.decorateorderscore.model.DecorateOrderScore;
import com.sandu.decorateorderscore.model.DecorateOrderVO;

public interface DecorateOrderScoreService {

	public int insertDecorateOrderScore(DecorateOrderScore score);

	public List<DecorateOrderVO> getDecorateOrderList(DecorateOrderQuery query);

	public DecorateOrder getDecorateOrderById(Integer taskId);

	public int getDecorateOrderCount();
}
