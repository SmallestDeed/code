package com.nork.product.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nork.common.util.collections.Lists;
import com.nork.product.model.BrandIndustryModel;
import com.nork.product.model.ProductModelConstant;
import com.nork.product.service.BaseProductService;
import com.nork.system.model.bo.SysDictionaryBo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import com.nork.product.dao.BaseBrandMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;

/**   
 * @Title: BaseBrandServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品-品牌表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-06-16 10:03:47
 * @version V1.0   
 */
@Service("baseBrandService")
@Transactional
public class BaseBrandServiceImpl implements BaseBrandService {
	
	@Autowired
	private BaseBrandMapper baseBrandMapper;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private BaseProductService baseProductService;

	
	/**
	 * 新增数据
	 *
	 * @param baseBrand
	 * @return  int 
	 */
	@Override
	public int add(BaseBrand baseBrand) {
		baseBrandMapper.insertSelective(baseBrand);
		return baseBrand.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param baseBrand
	 * @return  int 
	 */
	@Override
	public int update(BaseBrand baseBrand) {
		return baseBrandMapper
				.updateByPrimaryKeySelective(baseBrand);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseBrandMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseBrand 
	 */
	@Override
	public BaseBrand get(Integer id) {
		return baseBrandMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseBrand
	 * @return   List<BaseBrand>
	 */
	@Override
	public List<BaseBrand> getList(BaseBrand baseBrand) {
	    return baseBrandMapper.selectList(baseBrand);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseBrand
	 * @return   int
	 */
	@Override
	public int getCount(BaseBrandSearch baseBrandSearch){
		return  baseBrandMapper.selectCount(baseBrandSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  baseBrand
	 * @return   List<BaseBrand>
	 */
	@Override
	public List<BaseBrand> getPaginatedList(
			BaseBrandSearch baseBrandSearch) {
		return baseBrandMapper.selectPaginatedList(baseBrandSearch);
	}

	/**
	 * 根据用户得到其序列号对应的品牌type
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public Set<String> getBrandTypeSet(Integer userId) {
		Set<String> set=new HashSet<String>();
		List<AuthorizedConfig> authorizedConfigList=authorizedConfigService.findAllByUserId(userId);
		for(AuthorizedConfig authorizedConfig:authorizedConfigList){
			if(StringUtils.isBlank(authorizedConfig.getBrandIds()))
				continue;
			List<String> strList=Utils.getListFromStr(authorizedConfig.getBrandIds());
			for(String str:strList){
				BaseBrand baseBrand=get(Integer.valueOf(str));
				if(baseBrand!=null&&baseBrand.getBrandStyleId()!=null){
					SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValue("brandType", baseBrand.getBrandStyleId());
					if(sysDictionary!=null)
						set.add(sysDictionary.getValuekey());
				}
			}
		}
		return set;
	}

	@Override
	public BaseBrand findOneByBrandReferred(String brandReferred) {
		BaseBrandSearch baseBrandSearch = new BaseBrandSearch();
		baseBrandSearch.setStart(0);
		baseBrandSearch.setLimit(1);
		baseBrandSearch.setIsDeleted(0);
		baseBrandSearch.setBrandReferred(brandReferred);
		List<BaseBrand> list = this.getPaginatedList(baseBrandSearch);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<BaseBrand> getListByIds(List<Integer> brandIds) {
		if(brandIds == null || brandIds.size() <= 0){
			return null;
		}
		BaseBrand baseBrand = new BaseBrand();
		baseBrand.setIsDeleted(0);
		baseBrand.setBrandIds(brandIds);
		return baseBrandMapper.getListByIds(baseBrand);
	}

	@Override
	public List<BaseBrand> listAllByPara(BaseBrandSearch baseBrandSearch) {
		return baseBrandMapper.findAllBrandInfoList(baseBrandSearch);
	}

	@Override
	public int getAllBrandInfoCount(BaseBrandSearch baseBrandSearch){
		return baseBrandMapper.findAllBrandInfoCount(baseBrandSearch);
	}

	/**
	 * 获取品牌公司所属行业和细分行业值
	 * @param userId
	 * @return
	 */
	@Override
	public List<BaseBrand> getBrandIndustryValueByUserId(Integer userId){
		return baseBrandMapper.findBrandIndustryValueByUserId(userId);
	}

	/**
	 * 获取授权码品牌过滤条件
	 * @param userId
	 * @return
	 */
	@Override
	public List<BrandIndustryModel> getAuthorizedBrandFilterCondition(Integer userId){

		List<BrandIndustryModel> brandIndustryModelList = new ArrayList<>();
		//设置品牌行业值，用户过滤同行业品牌产品
		List<BaseBrand> brandList = this.getBrandIndustryValueByUserId(userId);
		if (Lists.isNotEmpty(brandList)) {
			for (BaseBrand baseBrand : brandList) {
				BrandIndustryModel brandIndustryModel = new BrandIndustryModel();
				brandIndustryModel.setBrandId(baseBrand.getId());
				brandIndustryModel.setIndustryValue(baseBrand.getCompanyIndustry());
				brandIndustryModel.setSmallTypeValue(baseBrand.getCompanySmallType());
				if (baseBrand.getStatusShowWu() != null && baseBrand.getStatusShowWu() == ProductModelConstant.STATUSSHOWWU_SHOW) {
					BaseBrand brand = this.findOneByBrandReferred(ProductModelConstant.BRAND_REFERRED_WU);
					brandIndustryModel.setNonBrandId(brand==null?0:brand.getId());
				}
				//设置授权码过滤条件
				if (StringUtils.isNotEmpty(baseBrand.getAuthorizedBigType()) || StringUtils.isNotEmpty(baseBrand.getAuthorizedProductIds())) {
					baseProductService.getBrandIndustryModelbyAuthorizedInfo(baseBrand.getAuthorizedBigType(),
							baseBrand.getAuthorizedSmallTypeIds(),baseBrand.getAuthorizedProductIds());
				}
				//设置细分行业小分类条件
				String companyProductSmallValueKeys = baseBrand.getCompanyProductSmallValueKeys();
				if (StringUtils.isNotEmpty(companyProductSmallValueKeys)) {
					brandIndustryModel.setProductSmallValueKeys(companyProductSmallValueKeys);
					//转换value
					List<String> valueKeysList = Utils.getListFromStr(companyProductSmallValueKeys);
					List<SysDictionaryBo> sysDictionaryBoList = sysDictionaryService.getListByValueKeys(valueKeysList);
					brandIndustryModel.setProductSmallValueList(sysDictionaryBoList);
				}
				brandIndustryModelList.add(brandIndustryModel);
			}
		}
		return brandIndustryModelList;
	}

	@Override
	public int getIdByBrandName(String brandName) {
		int id = baseBrandMapper.getIdByBrandName(brandName);
		return id;
	}
	/**
	 * 通过用户id去查询品牌id
	 * 
	 * */
	@Override
	public BaseBrand findBrandIdByUserId(Integer id) {
		// TODO Auto-generated method stub
		return baseBrandMapper.findBrandIdByUserId(id);
	}
	/**
	 * 通过品牌ids去查询品牌信息
	 * 
	 * */
	@Override
	public BaseBrand findBrandInfoByUserId(String ids){
		return baseBrandMapper.findBrandInfoByUserId(ids);
	}
	/**
	 * 通过用户id去查询品牌id,//并且用户类型为厂商
	 * 
	 * */
	@Override
	public BaseBrand findBrandIdByUserIdAndUserType(LoginUser loginUser) {
		// TODO Auto-generated method stub
		return baseBrandMapper.findBrandIdByUserIdAndUserType(loginUser);
	}
	/**
	 * 通过品牌id与平台查询已上架方案id
	 * @param brandIds
	 * @return
	 */
	@Override
	public List<Integer> findPlanIdListByBrand(List<Integer> brandIds){
		return baseBrandMapper.findPlanIdListByBrand(brandIds);
	}
	/**
	 * 通过品牌id去查询用户id,并且用户类型为经销商
	 * 
	 * */
	@Override
	public List<LoginUser> selectBrandByUserId(Integer id){
		return baseBrandMapper.selectBrandByUserId(id);
	}

	@Override
	public List<BaseBrand> findBrandIdByUserIdList(Integer id) {
		// TODO Auto-generated method stub
		return baseBrandMapper.findBrandIdByUserIdList(id);
	}

	@Override
	public BaseBrand findBrandIdByUserIdBase(LoginUser loginUser) {
		// TODO Auto-generated method stub
		return baseBrandMapper.findBrandIdByUserIdBase(loginUser);
	}

	@Override
	public List<BaseBrand> findBrandIdByUserIdListB(Integer id) {
		// TODO Auto-generated method stub
		return baseBrandMapper.findBrandIdByUserIdListB(id);
	}

	@Override
	public List<BaseBrand> findBrandIdByUserIdListBase(Integer id) {
		// TODO Auto-generated method stub
		return baseBrandMapper.findBrandIdByUserIdListBase(id);
	}

	@Override
	public BaseBrand selectUserIdByBrandIds(LoginUser loginUser) {
		// TODO Auto-generated method stub
		return baseBrandMapper.selectUserIdByBrandIds(loginUser);
	}
	/**
	 * 经销商列表(根据厂商id去查询经销商的品牌id和经销商id)
	 * */
	@Override
	public List<BaseBrand> selectCompanyUserIdByDealerId(Integer id) {
		// TODO Auto-generated method stub
		return baseBrandMapper.selectCompanyUserIdByDealerId(id);
	}

	@Override
	public List<BaseBrand> selectCompanyByUserId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseBrandMapper.selectCompanyByUserId(map);
	}

	/**
	 * 通过企业ID查询品牌IDS
	 * @param companyId
	 * @return
	 */
	@Override
	public String selectBrandIdsByCompanyId(Integer companyId){
		// TODO Auto-generated method stub
		if( companyId == null ){
			return null;
		}
		return baseBrandMapper.selectBrandIdsByCompanyId(companyId);
	}
}
