package com.nork.onekeydesign.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.Utils;
import com.nork.onekeydesign.dao.TempletPlanOperationHistoryMapper;
import com.nork.onekeydesign.model.TempletPlanOperationHistory;
import com.nork.onekeydesign.model.search.TempletPlanOperationHistorySearch;
import com.nork.onekeydesign.service.TempletPlanOperationHistoryService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;


@Service("templetPlanOperationHistoryService")
public class TempletPlanOperationHistoryServiceImpl implements TempletPlanOperationHistoryService {

	private TempletPlanOperationHistoryMapper templetPlanOperationHistoryMapper;

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	public void setTempletPlanOperationHistoryMapper(
			TempletPlanOperationHistoryMapper templetPlanOperationHistoryMapper) {
		this.templetPlanOperationHistoryMapper = templetPlanOperationHistoryMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param templetPlanOperationHistory
	 * @return  int 
	 */
	@Override
	public int add(TempletPlanOperationHistory templetPlanOperationHistory) {
		templetPlanOperationHistoryMapper.insertSelective(templetPlanOperationHistory);
		return templetPlanOperationHistory.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param templetPlanOperationHistory
	 * @return  int 
	 */
	@Override
	public int update(TempletPlanOperationHistory templetPlanOperationHistory) {
		return templetPlanOperationHistoryMapper
				.updateByPrimaryKeySelective(templetPlanOperationHistory);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return templetPlanOperationHistoryMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  TempletPlanOperationHistory 
	 */
	@Override
	public TempletPlanOperationHistory get(Integer id) {
		return templetPlanOperationHistoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  templetPlanOperationHistory
	 * @return   List<TempletPlanOperationHistory>
	 */
	@Override
	public List<TempletPlanOperationHistory> getList(TempletPlanOperationHistory templetPlanOperationHistory) {
	    return templetPlanOperationHistoryMapper.selectList(templetPlanOperationHistory);
	}

	
	/**
	 *    获取数据数量
	 *
	 * @param  templetPlanOperationHistory
	 * @return   int
	 */
	@Override
	public int getCount(TempletPlanOperationHistorySearch templetPlanOperationHistorySearch){
		return  templetPlanOperationHistoryMapper.selectCount(templetPlanOperationHistorySearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  templetPlanOperationHistory
	 * @return   List<TempletPlanOperationHistory>
	 */
	@Override
	public List<TempletPlanOperationHistory> getPaginatedList(
			TempletPlanOperationHistorySearch templetPlanOperationHistorySearch) {
		return templetPlanOperationHistoryMapper.selectPaginatedList(templetPlanOperationHistorySearch);
	}

	/**
	 * 其他
	 * 
	 */
	

//	@Override
//	public void saveOrUpdate(Integer templetId, Integer designTempletId, Integer userId) {
//		TempletPlanOperationHistory tp = new TempletPlanOperationHistory();
//		tp.setDesignPlanRecommendedId(templetId);
//		tp.setDesignTempletId(designTempletId);
//		tp.setUserId(userId);
//		List<TempletPlanOperationHistory> list = templetPlanOperationHistoryMapper.selectList(tp);
//		if(CustomerListUtils.isNotEmpty(list)) {
//			TempletPlanOperationHistory t = list.get(0);
//			t.setNumber(t.getNumber() + 1);
//			t.setGmtModified(new Date());
//			templetPlanOperationHistoryMapper.updateByPrimaryKeySelective(t);
//		} else {
//			templetPlanOperationHistoryMapper.insertSelective(tp);
//		}
//	}
	@Override
	public void saveOrUpdate(Integer templetId, Integer designTempletId, Integer userId,Integer isAllreplace) {
		TempletPlanOperationHistory tp = new TempletPlanOperationHistory();
		tp.setDesignPlanRecommendedId(templetId);
		tp.setDesignTempletId(designTempletId);
		tp.setUserId(userId);
		tp.setIsDeleted(0);
		if(isAllreplace == 0){
			tp.setAtt1("硬装替换");
		}else{
			tp.setAtt1("全屋替换");
		}
		SysUser loginUser = sysUserService.get(userId);
		tp.setSysCode(Utils
				.getCurrentDateTime(Utils.DATETIMESSS)
				+ "_"
				+ Utils.generateRandomDigitString(6));
		tp.setGmtCreate(new Date());
		tp.setCreator(loginUser.getUserName());
		templetPlanOperationHistoryMapper.insertSelective(tp);
	}
	


}
