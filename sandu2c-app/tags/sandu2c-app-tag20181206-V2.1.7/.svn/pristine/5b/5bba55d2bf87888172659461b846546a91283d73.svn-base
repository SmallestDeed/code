package com.sandu.decorateorderscore.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.decorateorderscore.model.DecorateOrder;
import com.sandu.decorateorderscore.model.DecorateOrderQuery;
import com.sandu.decorateorderscore.model.DecorateOrderScore;
import com.sandu.decorateorderscore.model.DecorateOrderVO;

@Repository
public interface DecorateOrderScoreMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(DecorateOrderScore record);

    int insertSelective(DecorateOrderScore record);

    DecorateOrderScore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DecorateOrderScore record);

    int updateByPrimaryKey(DecorateOrderScore record);

	List<DecorateOrderVO> selectDecorateOrderList(DecorateOrderQuery query);

	DecorateOrder selectDecorateOrderById(@Param("id")Integer taskId);

	int selectDecorateOrderCount();
}