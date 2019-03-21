package com.sandu.web.act3;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.sandu.api.act3.input.LuckyWheelPrizeUpdate;
import com.sandu.api.act3.model.LuckyWheel;
import com.sandu.api.act3.model.LuckyWheelPrize;
import com.sandu.api.act3.output.LuckyWheelPrizeListBO;
import com.sandu.api.act3.output.LuckyWheelPrizeListVO;
import com.sandu.api.act3.service.LuckyWheelService;
import com.sandu.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.sandu.api.act3.output.LuckyWheelPrizeVO;
import com.sandu.api.act3.service.LuckyWheelPrizeService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/act3/luckywheel/prize")
@Slf4j
public class LuckyWheelPrizeController extends BaseController {
		
    @Autowired
    private LuckyWheelPrizeService luckyWheelPrizeService;

    @Resource
    private SysUserService userService;

	@Autowired
	private LuckyWheelService luckyWheelService;
    
    
    @ApiOperation(value = "前台转盘奖品列表", response = ResponseEnvelope.class)
    @GetMapping("/list")
    public ResponseEnvelope list(String actId) {
    	try {
			if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(false, "活动id不能为空!"); 
			}
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			List<LuckyWheelPrizeVO> retList = luckyWheelPrizeService.getPrizeList(actId,user);
			
			return new ResponseEnvelope<>(true, retList.size(),retList);
			
		}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    
    @ApiOperation(value = "后台转盘奖品列表", response = ResponseEnvelope.class)
    @GetMapping("/pageList")
    public ResponseEnvelope pageList(String actId) {
    	//1.校验参数
		if(StringUtils.isBlank(actId)) {
			return new ResponseEnvelope<>(false, "活动id不能为空!");
		}
		//2.获取活动名称和状态
		LuckyWheel wheel = luckyWheelService.get(actId);
		if (null==wheel){
			return new ResponseEnvelope<>(false, "活动id无效!");
		}
		try {
			LuckyWheelPrizeListVO vo=new LuckyWheelPrizeListVO();
			vo.setActId(actId);
			vo.setActName(wheel.getName());
			//活动状态
			vo.setStatusCode(luckyWheelService.getStatus(wheel));
            //3.查询活动奖品列表
			LuckyWheelPrize prize=new LuckyWheelPrize();
			prize.setActId(actId);
			//当奖品列表为空时，直接返回空，不为空时，数据转换之后返回
			List<LuckyWheelPrize> prizeList = luckyWheelPrizeService.list(prize);
			if (null==prizeList){
				vo.setPrizeList(null);
			}else{
				List<LuckyWheelPrizeListBO> prizeListBO= this.dataTransform(prizeList);
				vo.setPrizeList(prizeListBO);
			}

			return new ResponseEnvelope<>(true,vo);
		}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }


    private List<LuckyWheelPrizeListBO> dataTransform(List<LuckyWheelPrize> prizeList){
		List<LuckyWheelPrizeListBO> list=new ArrayList<>();
		for (LuckyWheelPrize prize:prizeList) {
			LuckyWheelPrizeListBO bo=new LuckyWheelPrizeListBO();
			bo.setId(prize.getId());
			bo.setActId(prize.getActId());
			bo.setImg(prize.getImg());
			bo.setName(prize.getName());
			bo.setNum(prize.getNum());
			bo.setRemainNum(prize.getRemainNum());
			bo.setTodayRemainNum(prize.getTodayRemainNum());
			bo.setNumPerDay(prize.getNumPerDay());
			bo.setProbability(prize.getProbability());
			bo.setOrderSeq(prize.getOrderSeq());
			bo.setType(prize.getType());
			bo.setValue(prize.getValue());
			list.add(bo);
		}
		return list;
	}


    @ApiOperation(value = "保存奖品信息", response = ResponseEnvelope.class)
    @PostMapping("/save")
    public ResponseEnvelope save(@RequestBody LuckyWheelPrizeUpdate update) {
    	try {
    		//校验参数
			if(StringUtils.isBlank(update.getActId())) {
				return new ResponseEnvelope<>(false, "活动id不能为空!");
			}
			//通过活动Id查询数据库，是否存在奖品信息，如果存在则说明是修改活动奖品，如果不存在奖品信息，则说明是新增奖品信息
			//列表查询时会返回活动状态，前端根据活动状态限定指定字段不能修改
			LuckyWheelPrize prize=new LuckyWheelPrize();
			prize.setActId(update.getActId());
			List<LuckyWheelPrize> list = luckyWheelPrizeService.list(prize);
			//list为空，则说明是新增活动奖品
			if (null==list||list.size()==0){
				this.insertPrize(update.getActId(), update.getPrizeList());
			}else {//修改奖品信息
				this.updatePrize(update.getPrizeList());
			}
			return new ResponseEnvelope<>(true, "保存成功");
		}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }


    private void insertPrize(String actId,List<LuckyWheelPrizeListBO> prizeList){
		for (LuckyWheelPrizeListBO bo:prizeList) {
			LuckyWheelPrize prize=new LuckyWheelPrize();
			prize.setId(UUIDUtil.getUUID());
			prize.setActId(actId);
			prize.setGmtCreate(new Date());
			prize.setIsDeleted(0);
			prize.setCompanyId(2501L);
			prize.setAppId("wx42e6b214e6cdaed3");
			prize.setName(bo.getName());
			prize.setNum(bo.getNum());
            prize.setImg(bo.getImg());
            prize.setRemainNum(bo.getNum());
			if (bo.getNum()>bo.getNumPerDay()){//总数量>每日最大数量,"每日剩余数量"=“每日最大数量”
				prize.setTodayRemainNum(bo.getNumPerDay());
			}else{//否则,"每日剩余数量"=“总数”
				prize.setTodayRemainNum(bo.getNum());
			}
			prize.setNumPerDay(bo.getNumPerDay());
			prize.setProbability(bo.getProbability());
			prize.setOrderSeq(bo.getOrderSeq()==null?1:bo.getOrderSeq());
			prize.setType(bo.getType());
			prize.setValue(bo.getValue());
			luckyWheelPrizeService.create(prize);
		}
	}

	private void updatePrize(List<LuckyWheelPrizeListBO> prizeList){
		for (LuckyWheelPrizeListBO bo:prizeList) {
			LuckyWheelPrize prize=new LuckyWheelPrize();
			prize.setId(bo.getId());
			prize.setActId(bo.getActId());
			prize.setName(bo.getName());
			prize.setNum(bo.getNum());
			prize.setName(bo.getName());
			prize.setNum(bo.getNum());
			prize.setImg(bo.getImg());
			prize.setRemainNum(bo.getRemainNum());
			if (bo.getNum()>bo.getNumPerDay()){//总数量>每日最大数量,"每日剩余数量"=“每日最大数量”
				prize.setTodayRemainNum(bo.getNumPerDay());
			}else{//否则,"每日剩余数量"=“总数”
				prize.setTodayRemainNum(bo.getNum());
			}
			prize.setNumPerDay(bo.getNumPerDay());
			prize.setProbability(bo.getProbability());
			prize.setOrderSeq(bo.getOrderSeq());
			prize.setType(bo.getType());
			prize.setValue(bo.getValue());
			luckyWheelPrizeService.modifyById(prize);
		}
	}

}
