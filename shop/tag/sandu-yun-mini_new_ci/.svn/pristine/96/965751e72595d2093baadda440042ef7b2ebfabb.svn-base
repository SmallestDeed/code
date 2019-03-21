package com.sandu.web.company.controller;

import com.sandu.common.exception.ElasticSearchException;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.company.model.query.CompanyShopDetailQuery;
import com.sandu.company.model.query.ProjectCaseQuery;
import com.sandu.company.model.query.ShopStyleQuery;
import com.sandu.company.model.vo.*;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sandu.company.model.query.CompanyShopQuery;
import com.sandu.company.service.CompanyShopService;
import com.sandu.matadata.Page;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;

@Api(description = "店铺服务", tags = "CompanyShop")
@RestController
@RequestMapping(value = "/v1/sandu/mini/companyshop")
public class CompanyShopController {
	private static final Logger logger = LoggerFactory.getLogger(CompanyShopController.class.getName());
	@Autowired
	private CompanyShopService companyShopService;
	
	@ApiOperation(value = "店铺详情", response = ResultMessage.class)
    @RequestMapping(value="/detail",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage detail(CompanyShopDetailQuery query) {
		ResultMessage result=new ResultMessage();
		//数据校验
		if (null==query.getShopId()||null==query.getPlatformValue()) {
			result.setCode(ResultCode.ParameterError);
			result.setMessage("必传参数不能为空");
			return result;
		}
    	CompanyShopDetailVo detail=companyShopService.get(query);
    	if(detail!=null) {
    		//如果店铺类型是设计师和设计公司,且有上传封面图，取最后上传的图片进行展示（取图片资源时是按创建时间倒叙获取，所有list中第一个就是最好上传）
			//任守则需求变更，展示全部封面图，update by WangHaiLin 2018-11-19
			/*if (detail.getBusinessType()==3||detail.getBusinessType()==4){
				if (null!=detail.getCoverResList()){
					String coverResPicPath = detail.getCoverResList().get(0);
					List<String> coverResPicList=new ArrayList<>();
					coverResPicList.add(coverResPicPath);
					detail.setCoverResList(coverResPicList);
				}
			}*/
    		result.setCode(ResultCode.Success);
    		result.setData(detail);
    	}
    	return result;
	}
	
	@ApiOperation(value = "查询店铺信息", response = ResultMessage.class)
    @RequestMapping(value="/list",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage list(CompanyShopQuery query, HttpServletRequest request) {
    	ResultMessage result=new ResultMessage();
        //query.setPlatformType(Integer.valueOf(PlatformType.MiniApp.value()));
		//用户及参数校验获取
    	String platformCode = request.getHeader("Platform-Code");
		if (StringUtils.isEmpty(platformCode)) {
			result.setCode(ResultCode.ParameterError);
			return result;
		}
		Page<CompanyShopVo> page= null;
		try {
			if (StringUtils.isNotBlank(query.getShopIds())) {
				query.setShopIdList(this.getIdListByIds(query.getShopIds()));
			}
			if (StringUtils.isNotBlank(query.getExceptShopIds())) {
				query.setExceptShopIdList(this.getIdListByIds(query.getExceptShopIds()));
			}
			if (StringUtils.isNotBlank(query.getBusinessTypes())) {
				query.setBusinessTypeList(this.getIdListByIds(query.getBusinessTypes()));
			}
			page = companyShopService.list(query);
		} catch (ElasticSearchException e) {
			logger.error("店铺列表获取数据异常，e:{}", e);
		}
		if (page!=null && page.getList()!=null && page.getList().size()>0) {
			result.setData(page);
			result.setCode(ResultCode.Success);
		}
    	return result;
	}

	@ApiOperation(value = "查询线下门店信息", response = ResultMessage.class)
	@RequestMapping(value = "/offline/list", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage listShopOffline(CompanyShopQuery query, HttpServletRequest request) {
		ResultMessage result = new ResultMessage();
		//query.setPlatformType(Integer.valueOf(PlatformType.MiniApp.value()));
		//用户及参数校验获取
		String platformCode = request.getHeader("Platform-Code");
		if (StringUtils.isEmpty(platformCode)) {
			result.setCode(ResultCode.ParameterError);
			return result;
		}
		Page<CompanyShopVo> page = null;
		try {
			page = companyShopService.listShopOffline(query);
		} catch (ElasticSearchException e) {
			logger.error("店铺列表获取数据异常，e:{}", e);
		}

		if (page != null && page.getList() != null && page.getList().size() > 0) {
			result.setData(page);
			result.setCode(ResultCode.Success);
		}

		return result;
	}

	@ApiOperation(value = "店铺详情", response = ResultMessage.class)
	@RequestMapping(value = "/offline/detail", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage offlineDetail(CompanyShopDetailQuery query) {
		ResultMessage result = new ResultMessage();
		if (null == query.getShopId() || null == query.getPlatformValue()) {
			result.setCode(ResultCode.ParameterError);
			result.setMessage("必传参数不能为空");
			return result;
		}

		CompanyShopDetailVo detail = companyShopService.getOfflineDetail(query);
		if (detail != null) {
			result.setCode(ResultCode.Success);
			result.setData(detail);
		}

		return result;
	}

	@ApiOperation(value = "动态店铺列表信息", response = ResultMessage.class)
	@RequestMapping(value="/dynamicShopList",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage dynamicShopList(CompanyShopQuery query) {
		ResultMessage result=new ResultMessage();
		query.setDisplayType("noPic");//不查询没图片的店铺标志
		Page<CompanyShopVo> page = companyShopService.dynamicShopList(query);
		if (page != null && page.getList() != null && page.getList().size()>0) {
			result.setData(page);
			result.setCode(ResultCode.Success);
		}
		return result;
	}
	
	@ApiOperation(value = "店铺推荐列表", response = ResultMessage.class)
	@RequestMapping(value="/recommendList",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage recommendList() {
		ResultMessage result=new ResultMessage();
		CompanyShopQuery query=new CompanyShopQuery();
		query.setOrderBy("hottest");
		List<CompanyShopVo> lstVo = null;
		try {
			lstVo = companyShopService.getRecommendList();
		} catch (ElasticSearchException e) {
			logger.error("店铺列表获取数据异常，e:{}", e);
		}
		if (lstVo != null && lstVo.size()>0) {
			result.setData(lstVo);
			result.setCode(ResultCode.Success);
		}
		return result;
	}

	@ApiOperation(value = "店铺人气列表信息", response = ResultMessage.class)
	@RequestMapping(value="/shopPopularityList",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage shopPopularityList(CompanyShopQuery query) {
		ResultMessage result=new ResultMessage();
		query.setDisplayType("noPic");//不查询没图片的店铺标志
		if(query.getBusinessType() != null) {
			Page<ShopPopularityListVo> page = companyShopService.findShopPopularityList(query);
			result.setData(page);
			result.setCode(ResultCode.Success);
		}else {
			Page<ShopPopularityInfoVO> page = companyShopService.getAllShopPopularityList(query);
			result.setData(page);
			result.setCode(ResultCode.Success);
		}

		return result;
	}
	
	@ApiOperation(value = "更新浏览次数", response = ResultMessage.class)
    @RequestMapping(value="/updateVisitCount",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage updateVisitCount(long id) {
    	ResultMessage result=new ResultMessage();
    	boolean isUpdated=companyShopService.updateVisitCount(id);
    	if(isUpdated) {
    		result.setCode(ResultCode.Success);
    	}
    	return result;
	}

	@ApiOperation(value = "获取工程案例列表", response = ResultMessage.class)
	@RequestMapping(value="/projectCaseList",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResponseEnvelope projectCaseList(ProjectCaseQuery query) {
		Integer count = companyShopService.getShopProjectCaseCount(query);
		if (count > 0) {
			List<ProjectCaseVo> listVo = companyShopService.getShopProjectCaseList(query);
			if (listVo != null && listVo.size()>0) {
				if (query.getCaseId() != null) {
					CompanyShopDetailQuery q = new CompanyShopDetailQuery();
					if (listVo.get(0).getSid() != null) {
						ProjectCaseVo detail = companyShopService.getProjectCaseDetail(listVo.get(0).getSid());
						if (detail != null) {
							q.setShopId(detail.getShopId().longValue());
						}
					}
					if (q.getShopId() == null) {
						q.setShopId(listVo.get(0).getShopId().longValue());
					}
					CompanyShopDetailVo shopDetail = companyShopService.get(q);
					listVo.get(0).setShopDetail(shopDetail);
				}
				return new ResponseEnvelope(true, count, listVo);
			}
		}
		return new ResponseEnvelope(false, "查询结果为空！！！");
	}


	@ApiOperation(value = "企业店铺风格列表", response = ResultMessage.class)
	@RequestMapping(value="/styleList",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage getShopStyleList(ShopStyleQuery query) {
		ResultMessage result=new ResultMessage();
		if (null==query.getStyleType()){
			result.setCode(ResultCode.ParameterError);
			result.setMessage("参数为空");
		}
		List<CompanyShopStyleListVO> listVo = companyShopService.getShopStyleList(query);
		if (listVo != null && listVo.size()>0) {
			result.setData(listVo);
			result.setCode(ResultCode.Success);
		}
		return result;
	}

	@ApiOperation(value = "查询店铺列表", response = ResultMessage.class)
	@RequestMapping(value="/shopList",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage getShopList(CompanyShopQuery query, HttpServletRequest request) {
		ResultMessage result = new ResultMessage();
		//用户及参数校验获取
		String platformCode = request.getHeader("Platform-Code");
		if (StringUtils.isEmpty(platformCode)) {
			result.setCode(ResultCode.ParameterError);
			return result;
		}
		Page<CompanyShopVo> page = companyShopService.getShopList(query);
		if (page!=null && page.getList()!=null && page.getList().size()>0) {
			result.setData(page);
			result.setCode(ResultCode.Success);
		}
		return result;
	}

	@ApiOperation(value = "查询最近联系店铺", response = ResultMessage.class)
	@GetMapping(value = "/getContactShop")
	public ResultMessage getContactShop(String shopName,
										String recommendShopIds,
										Integer userId,
										Integer pageNo,
										Integer pageSize,
										Integer nearestContactShopLimit,
										String businessTypes) {
		if (pageNo == null || pageSize == null || StringUtils.isBlank(businessTypes) || userId == null) {
			return new ResultMessage(ResultCode.Fail, "缺失参数!");
		}
		ResultMessage result = new ResultMessage();
		Map<String, Page<CompanyShopVo>> map = new HashMap<>();
		List<Integer> exceptShopIdList = new ArrayList<>();
		List<Integer> businessTypeList = this.getIdListByIds(businessTypes);
		if (StringUtils.isBlank(shopName)) {
			// 最近联系店铺
			List<Integer> shopIdList = companyShopService.getNearestContactShopId(userId, 0, nearestContactShopLimit, businessTypeList);
			if (shopIdList != null && shopIdList.size() > 0) {
				CompanyShopQuery query = new CompanyShopQuery();
				query.setShopIdList(shopIdList);
				query.setOrderBy("hottest");
				query.setPlatformType(2);
				query.setBusinessTypeList(businessTypeList);
				Page<CompanyShopVo> page = null;
				try {
					page = companyShopService.list(query);
					if (page != null && page.getList() != null) {
						page.setList(page.getList().stream().map(o -> {
							o.setStar(("," + recommendShopIds + ",").contains("," + o.getId() + ",") ? 5 : 0);
							return o;
						}).sorted(Comparator.comparingInt(o -> shopIdList.indexOf((int) o.getId()))).collect(Collectors.toList()));
					}
				} catch (ElasticSearchException e) {
					logger.error("最近联系店铺列表获取数据异常，e:{}", e);
				}
				map.put("nearest", page);
				exceptShopIdList.addAll(shopIdList);
			}
			// 系统推荐店铺
			if (StringUtils.isNotBlank(recommendShopIds)) {
				List<Integer> recommendIdList = this.getIdListByIds(recommendShopIds);
				CompanyShopQuery query = new CompanyShopQuery();
				query.setShopIdList(recommendIdList);
				query.setOrderBy("hottest");
				query.setPlatformType(2);
				query.setBusinessTypeList(businessTypeList);
				Page<CompanyShopVo> page = null;
				try {
					page = companyShopService.list(query);
					if (page != null && page.getList() != null) {
						page.setList(page.getList().stream().map(o -> {
							o.setStar(5);
							return o;
						}).collect(Collectors.toList()));
					}
				} catch (ElasticSearchException e) {
					logger.error("系统推荐店铺列表获取数据异常，e:{}", e);
				}
				map.put("recommend", page);
				exceptShopIdList.addAll(recommendIdList);
			}
		}
		// 其他店铺
		CompanyShopQuery query = new CompanyShopQuery();
		query.setShopName(shopName);
		query.setOrderBy("hottest");
		query.setPlatformType(2);
		query.setBusinessTypeList(businessTypeList);
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		query.setExceptShopIdList(exceptShopIdList);
		Page<CompanyShopVo> page= null;
		try {
			page = companyShopService.list(query);
			if (page != null && page.getList() != null) {
				page.setList(page.getList().stream().map(o -> {
					o.setStar(0);
					return o;
				}).collect(Collectors.toList()));
			}
		} catch (ElasticSearchException e) {
			logger.error("其他店铺列表获取数据异常，e:{}", e);
		}
		// 返回结果
		map.put("others", page);
		result.setCode(ResultCode.Success);
		result.setData(map);
		return result;
	}

	private List<Integer> getIdListByIds(String ids) {
		List<Integer> idList = new ArrayList<>();
		for (String s : ids.split(",")) {
			idList.add(Integer.parseInt(s));
		}
		return idList;
	}
}
