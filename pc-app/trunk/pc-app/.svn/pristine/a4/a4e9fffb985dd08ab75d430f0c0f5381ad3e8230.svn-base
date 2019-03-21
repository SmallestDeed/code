package com.nork.cityunion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.common.util.Constants;
import com.nork.common.util.Utils;
import com.nork.product.model.vo.BaseBrandVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionGroupMapper;
import com.nork.cityunion.model.UnionGroup;
import com.nork.cityunion.model.search.UnionGroupSearch;
import com.nork.cityunion.service.UnionGroupService;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.service.impl.DesignPlanRecommendedServiceImplV2;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.service.BaseBrandService;

/**   
 * @Title: UnionGroupServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟分组表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:23:22
 * @version V1.0   
 */
@Service("unionGroupService")
public class UnionGroupServiceImpl implements UnionGroupService {
	
	private static Logger logger = Logger.getLogger(UnionGroupServiceImpl.class);
	private UnionGroupMapper unionGroupMapper;

	@Autowired
	public void setUnionGroupMapper(
			UnionGroupMapper unionGroupMapper) {
		this.unionGroupMapper = unionGroupMapper;
	}
	@Autowired
	private BaseBrandService baseBrandService;

	/**
	 * 新增数据
	 *
	 * @param unionGroup
	 * @return  int 
	 */
	@Override
	public int add(UnionGroup unionGroup) {
		unionGroupMapper.insertSelective(unionGroup);
		return unionGroup.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionGroup
	 * @return  int 
	 */
	@Override
	public int update(UnionGroup unionGroup) {
		return unionGroupMapper
				.updateByPrimaryKeySelective(unionGroup);
	}
	@Override
	public int deleteById(Integer id) {
		return unionGroupMapper.deleteById(id);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionGroupMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionGroup 
	 */
	@Override
	public UnionGroup get(Integer id) {
		return unionGroupMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionGroup
	 * @return   List<UnionGroup>
	 */
	@Override
	public List<UnionGroup> getList(UnionGroup unionGroup) {
	    return unionGroupMapper.selectList(unionGroup);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionGroupSearch
	 * @return   int
	 */
	@Override
	public int getCount(UnionGroupSearch unionGroupSearch){
		return  unionGroupMapper.selectCount(unionGroupSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionGroupSearch
	 * @return   List<UnionGroup>
	 */
	@Override
	public List<UnionGroup> getPaginatedList(
			UnionGroupSearch unionGroupSearch) {
		return unionGroupMapper.selectPaginatedList(unionGroupSearch);
	}

	/**
	 * 品牌列表
	 * @param msgId
	 * @return
	 */
	@Override
	public ResponseEnvelope<BaseBrand> myBrandList(String msgId,BaseBrandSearch baseBrandSearch) {
		if (baseBrandSearch == null) {
			baseBrandSearch = new BaseBrandSearch();
		}
		baseBrandSearch.setLimit(50);
		baseBrandSearch.setIsDeleted(Constants.DATA_DELETED_STATUS_zero);
		int count = baseBrandService.getCount(baseBrandSearch);
		List<BaseBrandVO> brandVOList = new ArrayList<>();
		if (count > 0) {
			List<BaseBrand> brandList = baseBrandService.getPaginatedList(baseBrandSearch);
			for (BaseBrand baseBrand : brandList) {
				BaseBrandVO brandVO = new BaseBrandVO();
				brandVO.setBrandId(baseBrand.getId());
				brandVO.setBrandName(baseBrand.getBrandName());
				brandVOList.add(brandVO);
			}
		}
		return new ResponseEnvelope(count,brandVOList, baseBrandSearch.getMsgId());
	}

	@Override
	public List<UnionGroup> getListByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return unionGroupMapper.getListByUserId(userId);
	}

	/**
	 * 自动存储系统字段
	 */
	@Override
	public void sysSave(UnionGroup model,LoginUser loginUser){
		if(model != null){
			if (loginUser == null) {
				loginUser = new LoginUser();
				loginUser.setLoginName("nologin");
			}
			if(model.getId() == null){
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if(model.getSysCode()==null || "".equals(model.getSysCode())){
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				}
			}
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}




}
