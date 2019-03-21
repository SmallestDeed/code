package com.nork.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.model.DesignPlan;
import com.nork.product.cache.AuthorizedConfigCacher;
import com.nork.product.dao.AuthorizedConfigMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.search.AuthorizedConfigSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.system.cache.SysUserCacher;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;

/**
 * @Title: AuthorizedConfigServiceImpl.java
 * @Package com.nork.product.service.impl
 * @Description:产品-授权配置ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2016-04-27 14:07:34
 * @version V1.0
 */
@Service("authorizedConfigService")
public class AuthorizedConfigServiceImpl implements AuthorizedConfigService {

	private static Logger logger = Logger.getLogger(AuthorizedConfigServiceImpl.class);
	
	@Autowired
	private AuthorizedConfigMapper authorizedConfigMapper;
	
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysDictionaryService sysDictionaryService;

	/**
	 * 新增数据
	 *
	 * @param authorizedConfig
	 * @return int
	 */
	@Override
	public int add(AuthorizedConfig authorizedConfig) {
		authorizedConfigMapper.insertSelective(authorizedConfig);
		return authorizedConfig.getId();
	}

	/**
	 * 更新数据
	 *
	 * @param authorizedConfig
	 * @return int
	 */
	@Override
	public int update(AuthorizedConfig authorizedConfig) {
		return authorizedConfigMapper.updateByPrimaryKeySelective(authorizedConfig);
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return authorizedConfigMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return AuthorizedConfig
	 */
	@Override
	public AuthorizedConfig get(Integer id) {
		return authorizedConfigMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param authorizedConfig
	 * @return List<AuthorizedConfig>
	 */
	@Override
	public List<AuthorizedConfig> getList(AuthorizedConfig authorizedConfig) {
		if(authorizedConfig.getValidState()==null){
			authorizedConfig.setValidState(1);
		}
		if(authorizedConfig.getValidState()!=null&&authorizedConfig.getValidState()==3){/**等于3则查询所有*/
			authorizedConfig.setValidState(null);
		}
		return authorizedConfigMapper.selectList(authorizedConfig);
	}

	/**
	 * 获取数据数量
	 *
	 * @param authorizedConfigSearch
	 * @return int
	 */
	@Override
	public int getCount(AuthorizedConfigSearch authorizedConfigSearch) {
		if(authorizedConfigSearch.getValidState()==null){
			authorizedConfigSearch.setValidState(1);
		}
		if(authorizedConfigSearch.getValidState()!=null&&authorizedConfigSearch.getValidState()==3){/**等于3则查询所有*/
			authorizedConfigSearch.setValidState(null);
		}
		return authorizedConfigMapper.selectCount(authorizedConfigSearch);
	}

	/**
	 * 分页获取数据
	 *
	 * @param authorizedConfigSearch
	 * @return List<AuthorizedConfig>
	 */
	@Override
	public List<AuthorizedConfig> getPaginatedList(AuthorizedConfigSearch authorizedConfigSearch) {
		if(authorizedConfigSearch.getValidState()==null){
			authorizedConfigSearch.setValidState(1);
		}
		if(authorizedConfigSearch.getValidState()!=null&&authorizedConfigSearch.getValidState()==3){/**等于3则查询所有*/
			authorizedConfigSearch.setValidState(null);
		}
		if(authorizedConfigSearch.getValidState()!=null&&authorizedConfigSearch.getValidState()==4){/**等于4则查询  试用的和未过期的*/
			authorizedConfigSearch.setValidState(4);
		}
		return authorizedConfigMapper.selectPaginatedList(authorizedConfigSearch);
	}

	/**
	 * 验证修改序列号状态接口的参数
	 * 
	 * @author huangsongbo
	 * @param msgId
	 *            msgId
	 * @param userId
	 *            用户id
	 * @param terminalImei
	 *            设备IMEI
	 * @param authorizedCode
	 *            序列号
	 * @param password
	 *            密码
	 * @param status
	 *            状态值(只能填0,1)
	 * @return
	 */
	public Map<String, String> VerifyParams(String msgId, Integer userId, String terminalImei, String authorizedCode,
			String password, Integer status) {
		String success = "false";
		String msg = "";
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
		} else if (userId == null) {
			msg = "参数userId不能为空";
		} else if (StringUtils.isBlank(terminalImei)) {
			msg = "参数terminalImei不能为空";
		} else if (StringUtils.isBlank(authorizedCode)) {
			msg = "参数authorizedCode不能为空";
		} else if (StringUtils.isBlank(password)) {
			msg = "参数password不能为空";
		} else if (status == null) {
			msg = "参数status不能为空";
		} else if (!(new Integer(0).equals(status) || new Integer(1).equals(status))) {
			msg = "参数status只能填0或1";
		} else {
			success = "true";
		}
		map.put("success", success);
		map.put("msg", msg);
		return map;
	}

	@Override
	public List<AuthorizedConfig> getBigNum(AuthorizedConfig authorizedConfig) {

		return authorizedConfigMapper.selectBigNum(authorizedConfig);
	}

	/**
	 * 查询序列号表获得一个参数的集合(品牌id,大类value,小类value,产品id)
	 * 
	 * @author huangsongbo
	 * @param userId
	 * @return
	 */
	public Map<String, List<String>> getConfigParams(Integer userId) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Set<String> brandIdSet = new HashSet<String>();
		Set<String> typeValueSet = new HashSet<String>();
		Set<String> smallTypeIdSet = new HashSet<String>();
		Set<String> productIdSet = new HashSet<String>();
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setUserId(userId);
		authorizedConfigSearch.setState(new Integer(1));
		authorizedConfigSearch.setLimit(-1);
		authorizedConfigSearch.setStart(-1);
		List<AuthorizedConfig> authorizedConfigs = getPaginatedList(authorizedConfigSearch);
		for (AuthorizedConfig authorizedConfig : authorizedConfigs) {
			/* brand */
			List<String> brandIds = stringToList(authorizedConfig.getBrandIds());
			setAddList(brandIdSet, brandIds);
			/* typeValue */
			List<String> typeValues = stringToList(authorizedConfig.getBigType());
			setAddList(typeValueSet, typeValues);
			/* smallTypeId */
			List<String> smallTypeIds = stringToList(authorizedConfig.getSmallTypeIds());
			setAddList(smallTypeIdSet, smallTypeIds);
			/* productIds */
			List<String> productIds = stringToList(authorizedConfig.getProductIds());
			setAddList(productIdSet, productIds);
		}
		List<String> brandIdList = new ArrayList<String>(brandIdSet);
		List<String> typeValueList = new ArrayList<String>(typeValueSet);
		List<String> smallTypeIdList = new ArrayList<String>(smallTypeIdSet);
		List<String> productIdList = new ArrayList<String>(productIdSet);
		map.put("brandIdList", brandIdList);
		map.put("typeValueList", typeValueList);
		map.put("smallTypeIdList", smallTypeIdList);
		map.put("productIdList", productIdList);
		return map;
	}

	/**
	 * 将List添加到Set中
	 * 
	 * @author huangsongbo
	 * @param brandIdList
	 * @param brandIds
	 */
	private void setAddList(Set<String> brandIdList, List<String> brandIds) {
		brandIdList.addAll(brandIds);
	}

	/**
	 * 特殊格式String(200,100,850)转化为List
	 * 
	 * @author huangsongbo
	 * @param str
	 * @return
	 */
	private List<String> stringToList(String str) {
		if (StringUtils.isBlank(str))
			return new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		String[] strs = str.split(",");
		for (String value : strs) {
			list.add(value);
		}
		return list;
	}

	/**
	 * 返回按照品牌归类的AuthorizedConfig
	 * 
	 * @author huangsongbo
	 * @param userId
	 *            用户id
	 */
	public Map<Integer, List<AuthorizedConfig>> getEffectualauthorizedConfigs(Integer userId, String terminalImei) {
		Map<Integer, List<AuthorizedConfig>> map = new HashMap<Integer, List<AuthorizedConfig>>();
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setLimit(-1);
		authorizedConfigSearch.setStart(-1);
		authorizedConfigSearch.setUserId(userId);
		authorizedConfigSearch.setState(1);
		if (StringUtils.isNotBlank(terminalImei))
			authorizedConfigSearch.setTerminalImei(terminalImei);
		List<AuthorizedConfig> authorizedConfigs = getPaginatedList(authorizedConfigSearch);
		for (AuthorizedConfig authorizedConfig : authorizedConfigs) {
			String brandIdsStr = authorizedConfig.getBrandIds();
			for (String str : brandIdsStr.split(",")) {
				if (StringUtils.isNotEmpty(str)) {
					if (map.containsKey(Integer.valueOf(str))) {
						map.get(Integer.valueOf(str)).add(authorizedConfig);
					} else {
						List<AuthorizedConfig> list = new ArrayList<AuthorizedConfig>();
						list.add(authorizedConfig);
						map.put(Integer.valueOf(str), list);
					}
				} else {
				}
			}
		}
		return map;
	}

	/**
	 * 返回按照大类归类的AuthorizedConfig
	 * 
	 * @author huangsongbo
	 * @param userId
	 *            用户id
	 */
	public Map<String, List<AuthorizedConfig>> getEffectualauthorizedConfigs2(Integer userId, String terminalImei) {
		Map<String, List<AuthorizedConfig>> map = new HashMap<String, List<AuthorizedConfig>>();
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setLimit(-1);
		authorizedConfigSearch.setStart(-1);
		authorizedConfigSearch.setUserId(userId);
		authorizedConfigSearch.setState(1);
		if (StringUtils.isNotBlank(terminalImei))
			authorizedConfigSearch.setTerminalImei(terminalImei);
		List<AuthorizedConfig> authorizedConfigs=null;
		if(Utils.enableRedisCache()){
			authorizedConfigs = AuthorizedConfigCacher.getPageList(authorizedConfigSearch);
		}else{
			authorizedConfigs = getPaginatedList(authorizedConfigSearch);
		}
		
		for (AuthorizedConfig authorizedConfig : authorizedConfigs) {
			String bigTypesStr = authorizedConfig.getBigType();
			if (StringUtils.isBlank(bigTypesStr)) {
				String str = "all";
				if (map.containsKey(str)) {
					map.get(str).add(authorizedConfig);
				} else {
					List<AuthorizedConfig> list = new ArrayList<AuthorizedConfig>();
					list.add(authorizedConfig);
					map.put(str, list);
				}
			} else {
				for (String str : bigTypesStr.split(",")) {
					if (map.containsKey(str)) {
						map.get(str).add(authorizedConfig);
					} else {
						List<AuthorizedConfig> list = new ArrayList<AuthorizedConfig>();
						list.add(authorizedConfig);
						map.put(str, list);
					}
				}
			}
		}
		return map;
	}

	/**
	 * 通过authorizedCode查找AuthorizedConfig
	 * @author huangsongbo
	 * @param authorizedCode
	 * @return
	 */
	public AuthorizedConfig findOneByAuthorizedCode(String authorizedCode) {
		AuthorizedConfigSearch authorizedConfigSearch=new AuthorizedConfigSearch();
		authorizedConfigSearch.setStart(0);
		authorizedConfigSearch.setLimit(1);
		authorizedConfigSearch.setIsDeleted(0);
		authorizedConfigSearch.setAuthorizedCode(authorizedCode);
		//查询全部序列号（包括过期序列号）
        authorizedConfigSearch.setValidState(3);
		List<AuthorizedConfig> list=getPaginatedList(authorizedConfigSearch);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 通过userId和payModelValue查找AuthorizedConfig
	 * @param userId
	 * @param payModelValue
	 * @return
	 */
	public List<AuthorizedConfig> findAllByUserIdAndPayModelValue(Integer userId, Integer payModelValue) {
		AuthorizedConfigSearch authorizedConfigSearch=new AuthorizedConfigSearch();
		authorizedConfigSearch.setStart(-1);
		authorizedConfigSearch.setLimit(-1);
		authorizedConfigSearch.setUserId(userId);
		authorizedConfigSearch.setPayModelValue(payModelValue);
		authorizedConfigSearch.setState(new Integer(1));
		return getPaginatedList(authorizedConfigSearch);
	}

	/**
	 * 查找该用户绑定的序列号
	 * @param userId
	 * @return
	 */
	public List<AuthorizedConfig> findAllByUserId(Integer userId) {
		AuthorizedConfigSearch authorizedConfigSearch=new AuthorizedConfigSearch();
		authorizedConfigSearch.setStart(-1);
		authorizedConfigSearch.setLimit(-1);
		authorizedConfigSearch.setUserId(userId);
		authorizedConfigSearch.setState(new Integer(1));
		return getPaginatedList(authorizedConfigSearch);
	}

	@Override
	public List<AuthorizedConfig> findAllByUserIdAndCompanyId(Integer userId, String companyId) {
		AuthorizedConfigSearch authorizedConfigSearch=new AuthorizedConfigSearch();
		authorizedConfigSearch.setStart(-1);
		authorizedConfigSearch.setLimit(-1);
		authorizedConfigSearch.setUserId(userId);
		authorizedConfigSearch.setState(new Integer(1));
		authorizedConfigSearch.setCompanyId(companyId);
		return getPaginatedList(authorizedConfigSearch);
	}

	/**
	 * 去重复(品牌,大类,小类)
	 * @author huangsongbo
	 * @param authorizedConfig
	 */
	public void dealWithRepeat(AuthorizedConfig authorizedConfig) {
		String brandIds=authorizedConfig.getBrandIds();
		List<String> brandIdsList=Utils.getListFromStr(brandIds);
		if(brandIdsList!=null&&brandIdsList.size()>0)
			authorizedConfig.setBrandIds(brandIdsList.get(0));
		String bigType=authorizedConfig.getBigType();
		List<String> bigTypeList=Utils.getListFromStr(bigType);
		if(bigTypeList!=null&&bigTypeList.size()>0)
			authorizedConfig.setBigType(bigTypeList.get(0));
		String smallTypeIds=authorizedConfig.getSmallTypeIds();
		List<String> smallTypeIdList=Utils.getListFromStr(smallTypeIds);
		if(smallTypeIdList!=null&&smallTypeIdList.size()>0)
			authorizedConfig.setSmallTypeIds(smallTypeIdList.get(0));
	}

	/**
	 * T除序列号到期的登录用户
	 * @author xiaoxc
	 */
	public Object delAuthorizedPastDueUserCacheJob(){

		try{
			//查询此前一天的到期授权码
			List<AuthorizedConfig> list = authorizedConfigMapper.selectPastDueAuthorizedList();
			if( Lists.isNotEmpty(list) ){
				for (AuthorizedConfig authorizedConfig : list) {
					if( authorizedConfig.getUserId() > 0 ){
						//查询用户是否还有其他授权码未过期，如果有就不T除
						int notOutCount = authorizedConfigMapper.selectNotOutAuthorizedCount(authorizedConfig.getUserId());
						logger.warn("notOutCount:"+notOutCount);
						if( notOutCount == 0){
							if (authorizedConfig.getUserId() != null) {
								//清除用户缓存
								SysUserCacher.remove(authorizedConfig.getUserId());
								//清除用户超时信息
								SysUserCacher.removeTimeOutUser(authorizedConfig.getUserId());
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("" + e.getMessage());
		}
		return null;
	}

	/**
	 * 得到该用户关联的设计公司授权码的数量
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public int findCountWhereCompanyTypeIsDesignByUserId(Integer userId) {
		// 找到设计公司数据字典
		SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueKey("brandBusinessType", "designCompany");
		if(sysDictionary == null){
			return 0;
		}
		return authorizedConfigMapper.findCountByCompanyTypeAndUserId(sysDictionary.getValue(), userId);
	}
	/**
	 * 获取 该用户为厂商的所有 授权码数据
	 * @param authorizedConfig
	 * @return
	 */
	@Override
	public List<AuthorizedConfig> vendorAuthorizedConfigList(int userId) {
		if(userId == 0 || userId == -1){
			return null;
		}
		SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueKey("authorizedCodeType","authorizedCodeFirm");
        if(sysDictionary == null){
        	return null;
        }
        AuthorizedConfig authorizedConfig = new AuthorizedConfig();
        authorizedConfig.setUserId(userId);
        authorizedConfig.setType(sysDictionary.getValue());
//        authorizedConfig.setCompanyType(sysDictionary.getValue());
        authorizedConfig.setIsDeleted(0);
        authorizedConfig.setState(1);
		return authorizedConfigMapper.vendorAuthorizedConfigList(authorizedConfig);
	}

	
	/**
	 * 通过品牌集合 获取 所有经销商
	 * @param brandIds
	 * @return
	 */
	@Override
	public List<AuthorizedConfig> getDealersAuthorizedConfigByBrandId(List<Integer> brandIds) {
		if(brandIds == null || brandIds.size()<=0){
			return null;
		}
		SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueKey("authorizedCodeType","authorizedCodeDealers");
        if(sysDictionary == null){
        	return null;
        }
        AuthorizedConfig authorizedConfig = new AuthorizedConfig();
        authorizedConfig.setBrandIdList(brandIds);
        authorizedConfig.setType(sysDictionary.getValue());
//        authorizedConfig.setCompanyType(sysDictionary.getValue());
        return authorizedConfigMapper.getDealersAuthorizedConfigByBrandId(authorizedConfig);
	}

	/**
	 * 获取用户绑定的授权码品牌IDs
	 * @param userId 用户ID
	 * @author xiaoxc
	 * @return
	 */
	@Override
	public String getAuthorizedBrandIdsByUserId(Integer userId){

		StringBuilder brandIds = new StringBuilder("");
		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
		authorizedConfig.setUserId(userId);
		authorizedConfig.setState(1);
		List<AuthorizedConfig> authorizedList = this.getList(authorizedConfig);
		for(AuthorizedConfig ac : authorizedList){
			if( StringUtils.isNotBlank(ac.getBrandIds()) ){
				brandIds.append(ac.getBrandIds()+",");
			}
		}
		return brandIds.length()==0?"":brandIds.toString();
	}

}
