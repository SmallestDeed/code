package com.nork.mgr.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.mgr.dao.MgrRechargeLogMapper;
import com.nork.mgr.model.MgrRechargeLog;
import com.nork.mgr.model.search.MgrRechargeLogSearch;
import com.nork.mgr.service.MgrRechargeLogService;
import com.nork.pay.model.PayOrder;
import com.nork.pay.service.PayOrderService;
import com.nork.system.model.SysUser;

/**   
 * @Title: MgrRechargeLogServiceImpl.java 
 * @Package com.nork.mgr.service.impl
 * @Description:日常工作-充值记录ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-03-26 05:15:26
 * @version V1.0   
 */
@Service("mgrRechargeLogService")
public class MgrRechargeLogServiceImpl implements MgrRechargeLogService {

	@Autowired
	private MgrRechargeLogMapper mgrRechargeLogMapper;

	@Autowired
	private PayOrderService payOrderService;
	
	private static Logger logger = Logger.getLogger(MgrRechargeLogServiceImpl.class);
	
	/**
	 * 新增数据
	 *
	 * @param mgrRechargeLog
	 * @return  int 
	 */
	@Override
	public int add(MgrRechargeLog mgrRechargeLog) {
		return mgrRechargeLogMapper.insertSelective(mgrRechargeLog);
	}

	/**
	 *    更新数据
	 *
	 * @param mgrRechargeLog
	 * @return  int 
	 */
	@Override
	public int update(MgrRechargeLog mgrRechargeLog) {
		return mgrRechargeLogMapper
				.updateByPrimaryKeySelective(mgrRechargeLog);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return mgrRechargeLogMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  MgrRechargeLog 
	 */
	@Override
	public MgrRechargeLog get(Integer id) {
		return mgrRechargeLogMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  mgrRechargeLog
	 * @return   List<MgrRechargeLog>
	 */
	@Override
	public List<MgrRechargeLog> getList(MgrRechargeLog mgrRechargeLog) {
	    return mgrRechargeLogMapper.selectList(mgrRechargeLog);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  mgrRechargeLog
	 * @return   int
	 */
	@Override
	public int getCount(MgrRechargeLogSearch mgrRechargeLogSearch){
		return  mgrRechargeLogMapper.selectCount(mgrRechargeLogSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  mgrRechargeLog
	 * @return   List<MgrRechargeLog>
	 */
	@Override
	public List<MgrRechargeLog> getPaginatedList(
			MgrRechargeLogSearch mgrRechargeLogSearch) {
		return mgrRechargeLogMapper.selectPaginatedList(mgrRechargeLogSearch);
	}

	/**
	 * 条件搜索,得到更多信息
	 * @author huangsongbo
	 * @param mgrRechargeLogSearch
	 * @return
	 */
	public List<MgrRechargeLog> getMoreInfoBySearch(MgrRechargeLogSearch mgrRechargeLogSearch) {
		return mgrRechargeLogMapper.getMoreInfoBySearch(mgrRechargeLogSearch);
	}

	@SuppressWarnings("unused")
	@Override
	public String saveLogAndUpdateUserBalance(
			MgrRechargeLog mgrRechargeLog,
			SysUser sysUser) {
		mgrRechargeLog.setRechargeStatus(1);
		mgrRechargeLog.setAdministratorId(sysUser.getId());
		// 计算/更新用户余额 ->start
		if(sysUser == null){
			return "充值用户不存在,充值失败";
		}
		PayOrder payOrder = payOrderService.get(mgrRechargeLog.getOrderNo());
		
		Double blance = sysUser.getBalanceAmount() == null ? 0.00 : sysUser.getBalanceAmount();
		Double currentBalance = (blance + payOrder.getAdjustFee()) / 100 + mgrRechargeLog.getRechargeAmount();
		// sys_user表中记录的余额单位是分
		mgrRechargeLog.setCurrentBalance(currentBalance);
		if (mgrRechargeLog.getId() == null) {
			int id = this.add(mgrRechargeLog);
			logger.info("add:id=" + id);
			mgrRechargeLog.setId(id);
		} else {
			int id = this.update(mgrRechargeLog);
			logger.info("update:id=" + id);
		}
		return "success";
	}

	
	
}
