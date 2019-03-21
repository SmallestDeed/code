package com.sandu.web.act;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import com.sandu.api.act.model.WxActBargainDecorateRecord;
import com.sandu.api.act.model.WxActBargainInviteRecord;
import com.sandu.api.act.output.WxActBargainInviteRecordVO;
import com.sandu.api.act.service.WxActBargainDecorateRecordService;
import com.sandu.api.act.service.WxActBargainInviteRecordService;
import com.sandu.commons.ResponseEnvelope;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "ActBargain", description = "砍价活动")
@RestController
@RequestMapping("/v1/act/bargain/reg/invite")
public class WxActBargainInviteRecordController {
	
	private Logger logger = LoggerFactory.getLogger(WxActBargainInviteRecordController.class);
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
    @Autowired
    private WxActBargainInviteRecordService wxActBargainInviteRecordService;
    
    @Autowired
    private WxActBargainDecorateRecordService wxActBargainDecorateRecordService;
    
    @ApiOperation(value = "获取砍价详情列表", response = ResponseEnvelope.class)
    @GetMapping("/getInviteRecordList")
    public ResponseEnvelope getWxActBargainInviteRecordList(@RequestParam(value="regId") String regId,
			@RequestParam(value="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
		
    	try {
			if(StringUtils.isBlank(regId)) {
				return new ResponseEnvelope<>(false, "任务id不能为空!"); 
			}
			PageInfo<WxActBargainInviteRecord> pageList = wxActBargainInviteRecordService.getWxActBargainInviteRecordPageList(regId,pageNum,pageSize);
			PageInfo<WxActBargainInviteRecordVO> retPageList = buildPageResult(pageList);
			
			//如果是首页,则加上装进我家的记录
			if(pageNum == 1) {
				WxActBargainDecorateRecord decorateRecord =  wxActBargainDecorateRecordService.getWxActBargainDecorateRecordByRegId(regId);
				if(decorateRecord!=null) {
					//WxActBargainInviteRecordVO temp = new WxActBargainInviteRecordVO("装进我家("+decorateRecord.getHouseName()+")",decorateRecord.getHeadPic(),decorateRecord.getCutPrice(),sdf.format(decorateRecord.getGmtCreate()));
					WxActBargainInviteRecordVO temp = new WxActBargainInviteRecordVO("装进我家",decorateRecord.getHeadPic(),decorateRecord.getCutPrice(),sdf.format(decorateRecord.getGmtCreate()));
					retPageList.getList().add(0,temp);
				}
			}
			return new ResponseEnvelope<>(true, retPageList.getTotal(),retPageList.getList());
    	}catch(Exception ex) {
			logger.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    private PageInfo<WxActBargainInviteRecordVO> buildPageResult(PageInfo<WxActBargainInviteRecord> pageList){
    	PageInfo<WxActBargainInviteRecordVO> retList = new PageInfo<WxActBargainInviteRecordVO>();
    	List<WxActBargainInviteRecordVO> list = new ArrayList<WxActBargainInviteRecordVO>();
    	if(pageList!=null && pageList.getList()!=null&& pageList.getList().size()>0) {
    		for(WxActBargainInviteRecord record : pageList.getList()) {
    			list.add(new WxActBargainInviteRecordVO(record.getNickname(),record.getHeadPic(),record.getCutPrice(),sdf.format(record.getGmtCreate())));
    		}
    	}
    	retList.setTotal(pageList.getTotal());
    	retList.setList(list);
    	return retList;    	
    }

}
