package com.nork.home.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.home.dao.DrawBaseHouseMapper;
import com.nork.home.model.BaseHouse;
import com.nork.home.model.DrawBaseHouse;
import com.nork.home.model.search.DrawBaseHouseQuery;
import com.nork.home.service.DrawBaseHouseService;
import com.nork.system.service.BaseLivingService;

/**
 * 
 * @author songjianming@sanduspace.cn
 * 2018 2018年1月27日
 *
 */
@Service("drawBaseHouseService")
@Transactional
public class DrawBaseHouseServiceImpl implements DrawBaseHouseService {
	
	/**
	 * 审核角色
	 */
	public final static String HOUSE_DRAW_APPROVER = "HOUSE_DRAW_APPROVER";
	
	/**
	 * 绘制角色
	 */
	public final static String INTERNAL_HOUSE_DRAW = "INTERNAL_HOUSE_DRAW";
	
	@Autowired
	private DrawBaseHouseMapper drawBaseHouseMapper;
	
	@Autowired
	private BaseLivingService baseLivingService;
	
	static final String BAKE_SUCCESS = "5";

	@Override
	public Map<String, Object> listDrawHouse(DrawBaseHouseQuery query, LoginUser login) {
		List<DrawBaseHouse> list = new ArrayList<>(0);
		Map<String, Object> map = new HashMap<String, Object>(2);
		
		// 权限处理
		query.setRoleType(getRoleType(login));
		Long total = drawBaseHouseMapper.countDrawHouse(query);
		if (total > 0) {
			list = drawBaseHouseMapper.listDrawHouse(query);
			if (list != null && !list.isEmpty()) {
				list.forEach(house -> {
					if (StringUtils.isNotBlank(house.getAreaLongCode())) {
						String[] split = house.getAreaLongCode().substring(1, house.getAreaLongCode().length() - 1).split("\\.");
						house.setDetailAddress(baseLivingService.getDetailAddress(split) + getValue(house.getLivingName()));
					}
					
					// 户型名=小区名+户型名
					house.setHouseName(getValue(house.getLivingName()) + getValue(house.getHouseName()));
					
					// 部分空间烘焙完，app可以装修
					if (!BAKE_SUCCESS.equals(house.getDealStatus())) {
						int dealCount = drawBaseHouseMapper.getDealSpaceByHouseId(house.getId());
						if (dealCount > 0) {
							house.setDealStatus(BAKE_SUCCESS);
						}
					}
				});
			}
		}
		
		map.put("total", total);
		map.put("list", list);
		
		return map;
	}
	
	public final static String VOID_VALUE = "";
	
	public static String getValue(String... args) {
		if (args != null && args.length >= 2) {
			return StringUtils.isNotBlank(args[0]) ? args[0] : args[1];
		} else if (args != null && args.length >= 1) {
			return StringUtils.isNotBlank(args[0]) ? args[0] : VOID_VALUE;
		}
		return VOID_VALUE;
	}
	
	/**
	 * 1、绘制员
	 * null(2) 外部用户
	 * @param login
	 * @return
	 */
	Integer getRoleType(LoginUser login) {
		Set<String> roles = login.getRoles();
		if (roles != null && roles.contains(INTERNAL_HOUSE_DRAW)) {
			return 1;
		}
		return null;
	}

	@Override
	public ResponseEnvelope<?> deleteDrawHouse(LoginUser user, String houseId) {
		BaseHouse house = new BaseHouse();
		house.setId(Integer.valueOf(houseId));
		house.setModifier(user.getLoginName());
		house.setGmtModified(new Date());
		house.setIsDeleted(1); // 删除
		house.setIsPublic(0); // 不公开
		Integer deleteDrawHouse = drawBaseHouseMapper.deleteDrawHouse(house);
		boolean flag = deleteDrawHouse > 0;
		return new ResponseEnvelope<>(flag, flag ? "删除成功" : "删除失败");
	}
	
	enum HouseStatus {
		HOUSE_INIT(1, "未提交的户型 (草稿)"), 
		HOUSE_APPLY(2, "待审核"), 
		HOUSE_REJECT(3, "审核驳回 "), 
		BAKE_WAIT(4, "待烘焙 (审核成功)"),
		BAKE_PROCESSING(5, "烘焙中"), 
		BAKE_SUCCESS(6, "烘焙成功"), 
		BAKE_FAIL(7, "烘焙失败");
		
		private int value;
		private String remark;
		
		public int getValue() {
			return value;
		}
		public String getRemark() {
			return remark;
		}
		
		HouseStatus(int value, String remak) {
			this.value = value;
			this.remark = remak;
		}
	}
}
