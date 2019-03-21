package com.nork.product.service2;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.nork.common.model.AuthorizedVO;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.search.AuthorizedConfigSearch;

public interface AuthorizedConfigService {

	/**
	 * 所有数据
	 * 
	 * @param  authorizedConfig
	 * @return   List<AuthorizedConfig>
	 */
	public List<AuthorizedConfig> getList(AuthorizedConfig authorizedConfig);
	
	/**
	 * 所有数据(mobile)
	 * add by xiaozp
	 * @param  authorizedConfig
	 * @return   List<AuthorizedConfig>
	 */
	public List<AuthorizedConfig> getListByMobile(AuthorizedConfig authorizedConfig);

	/**
	 *    获取数据数量
	 *
	 * @param  authorizedConfig
	 * @return   int
	 */
	public int getCount(AuthorizedConfigSearch authorizedConfigSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  authorizedConfig
	 * @return   List<AuthorizedConfig>
	 */
	public List<AuthorizedConfig> getPaginatedList( AuthorizedConfigSearch authorizedConfigtSearch);
	
	public ResponseEnvelope<AuthorizedConfig> authorizedBrandList(AuthorizedConfigSearch authorizedConfigSearch, LoginUser loginUser);
	
	/**
	 *    更新数据
	 *
	 * @param authorizedConfig
	 * @return  int 
	 */
	public int update(AuthorizedConfig authorizedConfig);
	
	/**
	 * 通过userId和companyId查找AuthorizedConfig
	 * @param userId
	 * @param companyId
	 * @return
	 */
	public List<AuthorizedConfig> findAllByUserIdAndCompanyId(Integer userId, String companyId);
	
	/**
	 * 查找该用户绑定的序列号
	 * @param userId
	 * @return
	 */
	public List<AuthorizedConfig> findAllByUserId(Integer userId);
	
	/**
	 * 验证修改序列号状态接口的参数
	 * @author huangsongbo
	 * @param msgId 
	 * @param userId 用户id
	 * @param terminalImei 设备IMEI
	 * @param authorizedCode 序列号
	 * @param password 密码
	 * @param status 状态值
	 * @return
	 */
	public Map<String, String> VerifyParams(String msgId, Integer userId, String terminalImei, String authorizedCode, String password, Integer status);
	
	/***
	 * 增加 、绑定、解绑
	 * @param loginUser
	 * @param authorizedVO
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> updateAuthorizedConfigStatus(LoginUser loginUser,AuthorizedVO authorizedVO) throws Exception;
	
	/**
	 * 新增数据
	 *
	 * @param authorizedConfig
	 * @return  int 
	 */
	public int add(AuthorizedConfig authorizedConfig);
 
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  AuthorizedConfig 
	 */
	public AuthorizedConfig getAuthorizedConfig(Integer id);
 
	/**
	 * 查询序列号表获得一个参数的集合(品牌id,大类value,小类value,产品id)
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public Map<String, List<String>> getConfigParams(Integer id,LoginUser loginUser);
	
	/**
	 * 获取该序列号的最大序号
	 * 
	 * @param  authorizedConfig
	 * @return  
	 */
	public List<AuthorizedConfig> getBigNum(AuthorizedConfig authorizedConfig);
	
	/**
	 * 返回按照品牌归类的AuthorizedConfig
	 * @author huangsongbo
	 * @param userId 用户id
	 * @param terminalImei 
	 */
	public Map<Integer, List<AuthorizedConfig>> getEffectualauthorizedConfigs(Integer userId, String terminalImei);
	
	/**
	 * 返回按照大类归类的AuthorizedConfig
	 * @author huangsongbo
	 * @param userId 用户id
	 * @param terminalImei 
	 */
	public Map<String, List<AuthorizedConfig>> getEffectualauthorizedConfigs2(Integer userId, String terminalImei);

	/**
	 * 通过authorizedCode查找AuthorizedConfig
	 * @author huangsongbo
	 * @param authorizedCode
	 * @return
	 */
	public AuthorizedConfig findOneByAuthorizedCode(String authorizedCode);

	/**
	 * 通过userId和payModelValue查找AuthorizedConfig
	 * @param userId
	 * @param value
	 * @return
	 */
	public List<AuthorizedConfig> findAllByUserIdAndPayModelValue(Integer userId, Integer payModelValue);
	
	/**
	 * 去重复(品牌,大类,小类)
	 * @author huangsongbo
	 * @param authorizedConfigItem
	 */
	public void dealWithRepeat(AuthorizedConfig authorizedConfig);

	public Object  delAuthorizedPastDueUserCacheJob();
	/**
	 * 得到该用户关联的设计公司授权码的数量
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public int findCountWhereCompanyTypeIsDesignByUserId(Integer id,LoginUser loginUser);
	
	/***
	 * 序列号续费
	 * @param request
	 * @param msgId
	 * @return
	 * @throws IOException
	 */
	public Object continueCost(AuthorizedVO authorizedVO) ;
	
	/**
	 * 购买序列号
	 * @param msgId
	 * @param terminalImei
	 * @param authorizedCode
	 * @param  
	 * @return
	 */
	public Object binding( String msgId,String terminalImei,String authorizedCode,  LoginUser loginUser );
	
	
	/**
	 * 序列号购买之前的验证
	 * @author huangsongbo
	 * @param authorizedCode
	 * @return
	 */
	 
	public Object verify(String authorizedCode,String msgId,Integer userId);
}
