package com.sandu.web.decorateorderscore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nork.common.model.LoginUser;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.decorateorderscore.model.DecorateOrder;
import com.sandu.decorateorderscore.model.DecorateOrderQuery;
import com.sandu.decorateorderscore.model.DecorateOrderScore;
import com.sandu.decorateorderscore.model.DecorateOrderVO;
import com.sandu.decorateorderscore.service.DecorateOrderScoreService;
import com.sandu.goods.output.GoodsDetailVO;
import com.sandu.product.model.UserProductCollect;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "DecorateOrder", tags = "DecorateOrder", description = "DecorateOrder")
@RestController
@RequestMapping("/v1/miniprogram/decorateOrder")
@Slf4j
public class DecorateOrderController extends BaseController {

	@Autowired
	private DecorateOrderScoreService decorateOrderScoreService;

	@ApiOperation(value = "评价打分", response = ResponseEnvelope.class)
	@PostMapping("/score")
	public ResponseEnvelope getDecoratePriceType(@RequestBody DecorateOrderScore decorateOrderScore,HttpServletRequest request) {
		if (null == decorateOrderScore) {
			return new ResponseEnvelope(false, "参数为空！");
		}
		if (null == decorateOrderScore.getScore() || decorateOrderScore.getScore() == null) {
			return new ResponseEnvelope(false, "请选择评分");
		}
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (null == loginUser || null == loginUser.getId()){
            return new ResponseEnvelope(false,"请登录！");
        }
        Integer userId =loginUser.getId();
        decorateOrderScore.setUserId(Long.parseLong(userId+""));
        decorateOrderScore.setCreator(userId+"");
        decorateOrderScore.setGmtCreate(new Date());
        decorateOrderScore.setModifier(userId+"");
        decorateOrderScore.setGmtModified(new Date());
        decorateOrderScore.setIsDeleted(0);
        int id = decorateOrderScoreService.insertDecorateOrderScore(decorateOrderScore);
		if (id > 0) {
			return new ResponseEnvelope(true, "保存成功!");
		} else {
			return new ResponseEnvelope(true, "保存失败!");
		}
	}
	
    @ApiOperation(value = "获取公司名称", response = ResponseEnvelope.class)
    @GetMapping("/detail")
    public ResponseEnvelope getGoodsDetail(Integer taskId) {
        if (taskId == null){
            return new ResponseEnvelope(false, "必要参数为空");
        }
        log.info("查看公司ID,id:{}", taskId);
        DecorateOrder order = decorateOrderScoreService.getDecorateOrderById(taskId);
        if (order == null) {
        	order = new DecorateOrder();
        	order.setCityName("无");
            return new ResponseEnvelope(true, "查询商品详情错误");
        }
        return new ResponseEnvelope(true, "success", order);
    }
	
	  /**
     * @Description: 获取订单列表
     * @Date: 18:08 2018/5/30
     */
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "获取订单列表", response = ResponseEnvelope.class)
    @GetMapping("/list")
    public ResponseEnvelope getOrderList(DecorateOrderQuery query, HttpServletRequest request) {
        List<DecorateOrderVO> orderList = new ArrayList<DecorateOrderVO>();
        if (null == query) {
			return new ResponseEnvelope(false, "参数为空！");
		}
		if (null == query.getPageNo() || query.getPageSize() == null) {
			return new ResponseEnvelope(false, "请传入分页信息");
		}
		//如果是第一页
		if(query.getPageNo() == 1) {
			int pageSize = query.getPageSize();
			//获取已签约的3条信息
			query.setPageSize(3);
			query.setOrderBy(" ORDER BY t.orderNum ASC,t.gmtCreate DESC ");
			List<DecorateOrderVO> signOrderList = decorateOrderScoreService.getDecorateOrderList(query);
			query.setOrderBy(" ORDER BY t.orderNum DESC,t.gmtCreate DESC ");
			if(signOrderList == null ||signOrderList.isEmpty()) {
				query.setPageSize(query.getPageSize());
			}else {
				query.setPageSize(pageSize-3);
			}
			List<DecorateOrderVO> unSignOrderList = decorateOrderScoreService.getDecorateOrderList(query);
			orderList.addAll(signOrderList);
			orderList.addAll(unSignOrderList);
		}else {
			query.setOrderBy(" ORDER BY t.orderNum DESC,t.gmtCreate DESC ");
			orderList = decorateOrderScoreService.getDecorateOrderList(query);
		}
		//计算总数
		int count = decorateOrderScoreService.getDecorateOrderCount();
        return new ResponseEnvelope(true, "查询成功",orderList,count);
    }
}
