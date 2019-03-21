package com.nork.home.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.onekeydesign.model.RecommendationSpaceResult;
import com.nork.home.dao.DesignRecommendationMapper;
import com.nork.home.model.DesignProgramDiy;
import com.nork.home.model.DesignRecommendation;
import com.nork.home.model.search.DesignProgramDiySearch;
import com.nork.home.model.search.DesignRecommendationSearch;
import com.nork.home.service.DesignRecommendationService;
import com.nork.system.service.SysDictionaryService;

/**   
 * @Title: DesignRecommendationServiceImpl.java 
 * @Package com.nork.home.service.impl
 * @Description:户型房型-设计推荐表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-07-03 16:15:50
 * @version V1.0   
 */
@Service("designRecommendationService")
@Transactional
public class DesignRecommendationServiceImpl implements DesignRecommendationService {

	private DesignRecommendationMapper designRecommendationMapper;

	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Autowired
	public void setDesignRecommendationMapper(
			DesignRecommendationMapper designRecommendationMapper) {
		this.designRecommendationMapper = designRecommendationMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param designRecommendation
	 * @return  int 
	 */
	@Override
	public int add(DesignRecommendation designRecommendation) {
		designRecommendationMapper.insertSelective(designRecommendation);
		return designRecommendation.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param designRecommendation
	 * @return  int 
	 */
	@Override
	public int update(DesignRecommendation designRecommendation) {
		return designRecommendationMapper
				.updateByPrimaryKeySelective(designRecommendation);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return designRecommendationMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignRecommendation 
	 */
	@Override
	public DesignRecommendation get(Integer id) {
		return designRecommendationMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  designRecommendation
	 * @return   List<DesignRecommendation>
	 */
	@Override
	public List<DesignRecommendation> getList(DesignRecommendation designRecommendation) {
	    return designRecommendationMapper.selectList(designRecommendation);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  designRecommendation
	 * @return   int
	 */
	@Override
	public int getCount(DesignRecommendationSearch designRecommendationSearch){
		return  designRecommendationMapper.selectCount(designRecommendationSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  designRecommendation
	 * @return   List<DesignRecommendation>
	 */
	@Override
	public List<DesignRecommendation> getPaginatedList(
			DesignRecommendationSearch designRecommendationSearch) {
		return designRecommendationMapper.selectPaginatedList(designRecommendationSearch);
	}

	@Override
	public int getdpdCount(DesignProgramDiySearch designProgramDiySearch) {
		return 0;
	}

	@Override
	public List<DesignProgramDiy> getDesignProgramDiyList(DesignProgramDiy dpd) {
		return null;
	}

	@Override
	public DesignProgramDiy getDesignProgramDiyByCode(DesignProgramDiy dpd) {
		return designRecommendationMapper.getDesignProgramDiyByCode(dpd);
	}

	@Override
	public DesignProgramDiy getSampleroomSourceByCode(DesignProgramDiy dpd) {
		return designRecommendationMapper.getSampleroomSourceyByCode(dpd);
	}

	@Override
	public List<DesignProgramDiy> getDropDownBoxList(DesignProgramDiy dpd) {
		return designRecommendationMapper.dropDownBoxList(dpd);
	}

	@Override
	public RecommendationSpaceResult getSpaceRender(Integer id) {
		return designRecommendationMapper.getSpaceRender(id);
	}

	@Override
	public int getTotal(DesignProgramDiy dpd) {
		return designRecommendationMapper.selectTotal(dpd);
	}

}
