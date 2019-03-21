package com.nork.system.service.impl;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.search.AuthorizedConfigSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.system.common.EquipmentConstants;
import com.nork.system.dao.SysUserEquipmentMapper;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUserEquipment;
import com.nork.system.service.SysResLevelCfgService;
import com.nork.system.service.SysUserEquipmentService;

@Service("sysUserEquipmentService")
@Transactional
public class SysUserEquipmentServiceImpl implements SysUserEquipmentService{

	@Autowired
	AuthorizedConfigService authorizedConfigService;
	@Autowired
	SysResLevelCfgService sysResLevelCfgService;
	
	SysUserEquipmentMapper sysUserEquipmentMapper;
 
	@Autowired
	public void setSysUserEquipmentMapper(SysUserEquipmentMapper sysUserEquipmentMapper) {
		this.sysUserEquipmentMapper = sysUserEquipmentMapper;
	}

	@Override
	public int add(SysUserEquipment sysUserEquipment) {
		return sysUserEquipmentMapper.insertSelective(sysUserEquipment);
	}

	@Override
	public int update(SysUserEquipment sysUserEquipment) {
		return sysUserEquipmentMapper.updateByPrimaryKeySelective(sysUserEquipment);
	}

	@Override
	public int delete(Integer id) {
		return sysUserEquipmentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public SysUserEquipment get(Integer id) {
		return sysUserEquipmentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int getCount(SysUserEquipment sysUserEquipment) {
		return sysUserEquipmentMapper.selectCount(sysUserEquipment);
	}

	@Override
	public List<SysUserEquipment> getList(SysUserEquipment sysUserEquipment) {
		return sysUserEquipmentMapper.selectList(sysUserEquipment);
	}

	/**
	 * 通过用户id,和设备类型  删除设备号
	 * @param userId
	 * @param equipmentType
	 */
	@Override
	public void deleteByUserId(Integer userId,String equipmentType) {
		sysUserEquipmentMapper.deleteByUserId(userId,equipmentType);
	}
	
	/**
	 * 检测设备号 
	 * @param userId 用户id
	 * @param deviceRestrict 设备限制值
	 * @param userImei  PCI网卡Mac地址(PCI设备号)
	 * @param usbTerminalImei USB网卡Mac地址(USB设备号)
	 * @param equipmentType 登录设备类型  移动端 、pc端？
	 * @return
	 */
	@Override
	public Map<String,String> equipmentCheck(Integer userId, Integer deviceRestrict, String userImei, String usbTerminalImei, String equipmentType){

		Map<String,String> resultMap = new HashMap<>(2);

		//读取配置文件
		ResourceBundle app = ResourceBundle.getBundle("app");
		//用户登录设备校验开关
		int checkSwitch = Integer.valueOf(app.getString(SysUserEquipment.DEVICE_USER_CHECK_CONFIG_NAME));

		//关闭校验直接跳过后续校验方法----全局跳过或单用户跳过
		if (SysUserEquipment.CLOSE_DEVICE_USER_CHECK == checkSwitch || deviceRestrict == SysUserEquipment.CANCEL_NETWORD_CARD_DEVICE_RESTRICT) {
			resultMap.put("success", "true");
			return resultMap;
		}

		//禁用设备不允许登录
		if (deviceRestrict == SysUserEquipment.NOT_ALLOW_ALL_NETWORD_CARD) {
			resultMap.put("success", "false");
			resultMap.put("data", "此用户已被限制登录，请联系客服!");
			return resultMap;
		}

		//参数校验
		if(!EquipmentConstants.MOBILE_EQUIPMENT.equals(equipmentType) && !EquipmentConstants.PC_EQUIPMENT.equals(equipmentType)){
			resultMap.put("success", "false");
			resultMap.put("data", "Param equipmentType is not found " + equipmentType);
			return resultMap;
		}
		if(userId == null || userId.intValue() <= 0){
			resultMap.put("success", "false");
			resultMap.put("data", "Param userId is null or less than 0 "+userId);
			return resultMap;
		}
		if(StringUtils.isEmpty(userImei) && StringUtils.isEmpty(usbTerminalImei)){
			resultMap.put("success", "false");
			resultMap.put("data", "Param userImei and usbTerminalImei  is null! userImei:" + userImei + " ,usbTerminalImei:" + usbTerminalImei);
			return resultMap;
		}

		//可用设备数
		Integer equipmentNum = 0;
		//获取限制设备数量
		List<SysDictionary> equipmentNumList = sysResLevelCfgService.getEquipmentNum(userId,equipmentType);
		if(equipmentNumList == null || equipmentNumList.size()<=0 ){
			resultMap.put("success", "false");
			resultMap.put("data", "数据初始化有问题,请联系客服");
			return resultMap;
		}
		if(equipmentNumList.size() != 1 ){
			resultMap.put("success", "false");
			resultMap.put("data", "设备登录异常,请联系客服");
			return resultMap;
		}
		equipmentNum = equipmentNumList.get(0).getValue();
		if(equipmentNum == null || equipmentNum.intValue()<=0){
			resultMap.put("success", "false");
			resultMap.put("data", "无设备可登录,请联系客服");
			return resultMap;
		}

		//用户设备信息
		SysUserEquipment userEquipment = new SysUserEquipment();
		userEquipment.setUserId(userId);
		userEquipment.setEquipmentType(equipmentType);
		userEquipment.setIsDeleted(0);

		//获取已登记设备数
		int beenUsed = this.getCount(userEquipment);
		if(beenUsed > equipmentNum){ 	//已使用设备 大于 设备上限 说明用户被降级,清理掉该用户所有设备号,让其重新绑定
			this.deleteByUserId(userId,equipmentType);
		}

		//获取已登记设备列表
		List<SysUserEquipment> equipmentList = this.getList(userEquipment);
		if(equipmentList == null || equipmentList.size()<=0 ){  //为null说明为首次登录
			//检查用户网卡设备限制--超出限制不允许注册设备
			switch (null == deviceRestrict ? 0 : deviceRestrict) {
				case SysUserEquipment.ALLOW_ALL_NETWORD_CARD :
					//允许所有设备
					if (!StringUtils.isEmpty(userImei) || !StringUtils.isEmpty(usbTerminalImei)) {
						userEquipment.setUserImei(userImei);
						userEquipment.setUsbTerminalImei(usbTerminalImei);
						userEquipment.setGmtCreate(new Date());
						//新增设备
						this.add(userEquipment);
						resultMap.put("success", "true");
						return resultMap;
					}
					break;
				case SysUserEquipment.ONLY_ALLOW_PCI_NETWORD_CARD :
					//检查PCI网卡
					if (!StringUtils.isEmpty(userImei)) {
						userEquipment.setUserImei(userImei);
						userEquipment.setUsbTerminalImei(usbTerminalImei);
						userEquipment.setGmtCreate(new Date());
						//新增设备
						this.add(userEquipment);
						resultMap.put("success", "true");
						return resultMap;
					}
					break;
				case SysUserEquipment.ONLY_ALLOW_USB_NETWORD_CARD :
					//检查USB网卡
					if (!StringUtils.isEmpty(usbTerminalImei)) {
						userEquipment.setUserImei(userImei);
						userEquipment.setUsbTerminalImei(usbTerminalImei);
						userEquipment.setGmtCreate(new Date());
						//新增设备
						this.add(userEquipment);
						resultMap.put("success", "true");
						return resultMap;
					}
					break;
				case SysUserEquipment.NOT_ALLOW_ALL_NETWORD_CARD :
					//不允许登录
					resultMap.put("success", "false");
					resultMap.put("data", "此用户已被限制登录，请联系客服!");
					return resultMap;
				default:
					//不允许登录
					resultMap.put("success", "false");
					resultMap.put("data", "设备限制类型异常，请联系客服!");
					return resultMap;
			}

			//设备限制类型与设备类型不匹配
			resultMap.put("success", "false");
			resultMap.put("data", "设备类型不匹配，请重新登录!");
			return resultMap;
		}

		//对比设备，检查是否是已登记设备校验
		for (SysUserEquipment sysUserEquipment : equipmentList) { //非首次登录，判断设备号是否存在
			//检查用户网卡设备限制
			switch (null == deviceRestrict ? 0 : deviceRestrict) {

				case SysUserEquipment.ALLOW_ALL_NETWORD_CARD :
					//检查PCI网卡
					if (!StringUtils.isEmpty(userImei)) {
						if (userImei.equals(sysUserEquipment.getUserImei())) {
							resultMap.put("success", "true");
							return resultMap;
						}
					} else if (!StringUtils.isEmpty(usbTerminalImei)) {
						//检查USB网卡
						if (usbTerminalImei.equals(sysUserEquipment.getUsbTerminalImei())) {
							resultMap.put("success", "true");
							return resultMap;
						}
					}
					break;
				case SysUserEquipment.ONLY_ALLOW_PCI_NETWORD_CARD :
					//检查PCI网卡
					if (!StringUtils.isEmpty(userImei)) {
						if (userImei.equals(sysUserEquipment.getUserImei())) {
							resultMap.put("success", "true");
							return resultMap;
						}
					}
					break;
				case SysUserEquipment.ONLY_ALLOW_USB_NETWORD_CARD :
					//检查USB网卡
					if (!StringUtils.isEmpty(usbTerminalImei)) {
						if (usbTerminalImei.equals(sysUserEquipment.getUsbTerminalImei())) {
							resultMap.put("success", "true");
							return resultMap;
						}
					}
					break;
				case SysUserEquipment.NOT_ALLOW_ALL_NETWORD_CARD :
					//不允许登录
					resultMap.put("success", "false");
					resultMap.put("data", "此用户已被限制登录，请联系客服!");
					return resultMap;
				default:
					resultMap.put("success", "false");
					resultMap.put("data", "网卡设备限制数据类型异常!");
					return resultMap;
			}
		}

		//检查剩余位
		if(equipmentNum.intValue() > equipmentList.size()){ // 没有从数据库中得到设备号，判断设备限制是否有剩余位置
			//设备数量限制有剩余设备位
			userEquipment.setUserImei(userImei);
			userEquipment.setUsbTerminalImei(usbTerminalImei);
			userEquipment.setGmtCreate(new Date());
			//登记设备
			this.add(userEquipment);
			resultMap.put("success", "true");
			return resultMap;
		}else{
			//设备数量已超出系统预设值
			resultMap.put("success", "false");
			resultMap.put("data", "该帐号已绑定其他设备,只能使用初始设备登录");
			return resultMap;
		}
	}
 
	
	 /**
     * 添加授权码的时候绑定设备
     * @param userId
     * @param terminalImei
     * @return
     */
	public Map<String,String> authorizedConfigBindingEquipment(Integer userId,String terminalImei) {
		Map<String,String>resMap = new HashMap<String,String>();
		if(userId == null || userId.intValue()<=0 || StringUtils.isEmpty(terminalImei)) {
			resMap.put("success", "false");
			resMap.put("data", "绑定设备失败");
			return resMap;	
		}
		SysUserEquipment userEquipment = new SysUserEquipment();
		userEquipment.setUserId(userId);
		userEquipment.setIsDeleted(0);
		int userEquipmentCount = this.getCount(userEquipment);
		if(userEquipmentCount <= 0) {
			AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
			authorizedConfigSearch.setUserId(userId);
			authorizedConfigSearch.setState(1);
			authorizedConfigSearch.setIsDeleted(0);
			int authorizedConfigCount = authorizedConfigService.getCount(authorizedConfigSearch);
			if(authorizedConfigCount == 1) {
				userEquipment.setEquipmentType(EquipmentConstants.PC_EQUIPMENT);
				userEquipment.setUserImei(terminalImei);
				userEquipment.setGmtCreate(new Date());
				this.add(userEquipment);
			}
		}
		resMap.put("success", "true");
		return resMap;
	}
 
}
