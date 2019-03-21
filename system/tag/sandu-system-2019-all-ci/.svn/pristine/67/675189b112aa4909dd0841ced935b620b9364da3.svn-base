package com.sandu.web.act3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.sandu.api.act3.input.LuckyWheelLotteryRecordDeliver;
import com.sandu.api.act3.input.LuckyWheelLotteryRecordQuery;
import com.sandu.api.act3.output.LuckyWheelLotteryRecordListVO;
import com.sandu.api.area.service.BaseAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import com.sandu.api.act3.input.AwardInfo;
import com.sandu.api.act3.model.LuckyWheelLotteryRecord;
import com.sandu.api.act3.output.LuckyWheelLotteryRecordVO;
import com.sandu.api.act3.service.LuckyWheelLotteryRecordService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.common.exception.BizException;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/act3/luckywheel/lottery/record")
@Slf4j
public class LuckyWheelLotteryRecordController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(LuckyWheelLotteryRecordController.class);
		
    @Autowired
    private LuckyWheelLotteryRecordService luckyWheelLotteryRecordService;

    @Resource
    private SysUserService userService;

    @Autowired
    private BaseAreaService baseAreaService;
    
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @ApiOperation(value = "中奖记录列表", response = ResponseEnvelope.class)
    @GetMapping("/list") 
    public ResponseEnvelope list(@RequestParam(value="actId") String actId,
			@RequestParam(value="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
    	try {
			if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(true, 0,null);
			}
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			PageInfo<LuckyWheelLotteryRecord> pageList = luckyWheelLotteryRecordService.pageList(actId,user.getOpenId(), pageNum,pageSize);
			log.info("获取中奖纪录，pageList={}", pageList);
			PageInfo<LuckyWheelLotteryRecordVO> retPageList = buildPageResult(pageList);
			log.info("获取中奖纪录，total={},list={}", retPageList.getTotal(), retPageList.getList());
			return new ResponseEnvelope<>(true, retPageList.getTotal(), retPageList.getList());
		}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    private PageInfo<LuckyWheelLotteryRecordVO> buildPageResult(PageInfo<LuckyWheelLotteryRecord> pageList) {
    	PageInfo<LuckyWheelLotteryRecordVO> retList = new PageInfo<LuckyWheelLotteryRecordVO>();
    	List<LuckyWheelLotteryRecordVO> list = new ArrayList<LuckyWheelLotteryRecordVO>();
    	if(pageList!=null && pageList.getList()!=null&& pageList.getList().size()>0) {
    		for(LuckyWheelLotteryRecord record : pageList.getList()) {
    			list.add(new LuckyWheelLotteryRecordVO(record.getId(),
    					sdf.format(record.getLotteryTime()),record.getPrizeName(),
    					record.getAwardsStatus(),LuckyWheelLotteryRecord.getAwardsStatusText(record.getAwardsStatus())));
    		}
    	}
    	retList.setTotal(pageList.getTotal());
    	retList.setList(list);
    	return retList;
	}

	@ApiOperation(value = "领奖", response = ResponseEnvelope.class)
    @PostMapping("/award")
    public ResponseEnvelope award(AwardInfo awardInfo) {
    	try {
    		if (awardInfo == null) {
    			return new ResponseEnvelope<>(false, "参数不能为空!"); 
            }
    		if(StringUtils.isBlank(awardInfo.getAwardId())) {
    			return new ResponseEnvelope<>(false, "奖品Id不能为空!"); 
    		}
    		
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			
			/**
			 * 没有绑定手机号,则先绑定,否则跳过
			 */
			if(StringUtils.isBlank(user.getMobile())) {
				if(StringUtils.isBlank(awardInfo.getMobile())) {
					return new ResponseEnvelope<>(false, "手机号不能为空"); 
				}
				if(StringUtils.isBlank(awardInfo.getValidateCode())) {
					return new ResponseEnvelope<>(false, "验证码不能为空"); 
				}
				userService.modifyUserMobile2C(loginUser.getId().intValue(), awardInfo.getMobile(), awardInfo.getValidateCode());
			}
			
			int updCount = luckyWheelLotteryRecordService.award(awardInfo,user);
			if(updCount>0) {
				return new ResponseEnvelope<>(true, "领奖成功");
			}
			return new ResponseEnvelope<>(true, "领奖失败");
    	}catch (BizException e) {
            return new ResponseEnvelope<>(false, e.getMessage());
        }catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }




	@ApiOperation(value = "抽奖记录列表(运营平台接口)", response = ResponseEnvelope.class)
	@GetMapping("/pageList")
	public ResponseEnvelope pageList(LuckyWheelLotteryRecordQuery query) {
		try {
			//处理参数校验
			if(StringUtils.isBlank(query.getActId())) {
				return new ResponseEnvelope<>(false,"活动Id不能为空");
			}
			Date beginTime = null;
			Date endTime = null;
			if (null!=query.getLotteryTimeStart()){
				try {
					beginTime = sdf.parse(query.getLotteryTimeStart());
				}catch(Exception ex) {
					return new ResponseEnvelope(false,"开始时间不合法");
				}
			}
			if (null!=query.getLotteryTimeEnd()){
				try {
					endTime = sdf.parse(query.getLotteryTimeEnd());
				}catch(Exception ex) {
					return new ResponseEnvelope(false,"结束时间不合法");
				}
			}
			PageInfo<LuckyWheelLotteryRecord> pageList = luckyWheelLotteryRecordService.pageList(query,beginTime,endTime);
			//数据转换
			PageInfo<LuckyWheelLotteryRecordListVO> retPageList = buildPageListResult(pageList);
			return new ResponseEnvelope<>(true, retPageList.getTotal(),retPageList.getList());
		}catch(SystemException ex) {
			log.warn("业务异常:"+ex.getMessage());
			return new ResponseEnvelope<>(false, ex.getMessage());
		}catch(Exception ex) {
			log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
	}


	//运营平台接抽奖记录列表数据转换
	private PageInfo<LuckyWheelLotteryRecordListVO> buildPageListResult(PageInfo<LuckyWheelLotteryRecord> pageList) {
		PageInfo<LuckyWheelLotteryRecordListVO> retList = new PageInfo<>();
		List<LuckyWheelLotteryRecordListVO> list = new ArrayList<>();
		if(pageList!=null && pageList.getList()!=null&& pageList.getList().size()>0) {
			for(LuckyWheelLotteryRecord record : pageList.getList()) {
				LuckyWheelLotteryRecordListVO listVO = new LuckyWheelLotteryRecordListVO();
				listVO.setId(record.getId());
				listVO.setOpenId(record.getOpenId());
				listVO.setAddress(record.getAddress());
				listVO.setDeliverStatus(record.getShipmentStatus());
				listVO.setMobile(record.getMobile());
				listVO.setNickName(record.getNickname());
				listVO.setPrizeName(record.getPrizeName());
				listVO.setReceiver(record.getReceiver());
				listVO.setAwardsStatus(record.getAwardsStatus());
				listVO.setLotteryTime(sdf.format(record.getLotteryTime()));
				if (null!=record.getAreaCode()){
					String provinceCityAreaName = baseAreaService.getProviceCityAreaByCode(record.getAreaCode());
					listVO.setProvinceCityAreaName(provinceCityAreaName);
				}
				list.add(listVO);
			}
		}
		retList.setTotal(pageList.getTotal());
		retList.setList(list);
		return retList;
	}


	@ApiOperation(value = "运营平台发货/修改发货信息", response = ResponseEnvelope.class)
	@PostMapping("/deliver")
	public ResponseEnvelope deliver(@Valid @RequestBody LuckyWheelLotteryRecordDeliver deliver, BindingResult validResult) {

    	//1.数据校验
		if (validResult.hasErrors()) {
			return processValidError(validResult,new ResponseEnvelope());
		}
		try {
			//2.根据抽奖记录Id，查询抽奖记录信息
			logger.info("发货=====入参===抽奖记录Id:{}",deliver.getId());
			LuckyWheelLotteryRecord record = luckyWheelLotteryRecordService.get(deliver.getId());
			if (null==record){
				return new ResponseEnvelope(false,"参数Id有误，无此Id对应记录");
			}
			if (record.getAwardsStatus()==0){
				return new ResponseEnvelope(false,"未中奖记录,无奖品发放");
			}
			//给快递公司和运单号赋值
			record.setCarrier(deliver.getCarrier());
			record.setShipmentNo(deliver.getShipmentNo());
			int result = luckyWheelLotteryRecordService.modifyById(record);
			if(result>0) {
				return new ResponseEnvelope<>(true, "发货成功");
			}
			return new ResponseEnvelope<>(false, "发货失败");
		}catch (BizException e) {
			return new ResponseEnvelope<>(false, e.getMessage());
		}catch(SystemException ex) {
			log.warn("业务异常:"+ex.getMessage());
			return new ResponseEnvelope<>(false, ex.getMessage());
		}catch(Exception ex) {
			log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
	}



	
}
