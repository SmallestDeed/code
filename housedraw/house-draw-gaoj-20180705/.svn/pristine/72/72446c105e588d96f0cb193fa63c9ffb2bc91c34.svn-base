package com.sandu.service.templet.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.sandu.api.house.model.*;
import com.sandu.common.constant.house.DrawSpaceCommonConstant;
import com.sandu.exception.BusinessException;
import com.sandu.service.house.dao.BaseHouseMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.input.DrawBaseProductDTO;
import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.house.service.DrawDesignTempletService;
import com.sandu.api.house.service.DrawResFileService;
import com.sandu.api.house.service.DrawResHousePicService;
import com.sandu.api.house.service.DrawSpaceCommonService;
import com.sandu.api.res.service.ResFileService;
import com.sandu.api.res.service.ResModelService;
import com.sandu.common.constant.DesignTempletStatusConstant;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.SpaceLayoutType;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.common.constant.house.DrawDesignTempletConstant;
import com.sandu.common.constant.house.HouseType;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.templet.dao.DesignTempletMapper;
import com.sandu.service.templet.dao.DrawDesignTempletMapper;
import com.sandu.util.Regex;
import com.sandu.util.Utils;
import com.sandu.util.auth.HouseAuthUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("drawDesignTempletService")
public class DrawDesignTempletServiceImpl implements DrawDesignTempletService {

	@Autowired
	private DrawSpaceCommonService drawSpaceCommonService;

	@Autowired
	private DrawDesignTempletMapper drawDesignTempletMapper;
	
	@Autowired
	private DesignTempletMapper designTempletMapper;

	@Autowired
	private DrawResFileService drawResFileService;

	@Autowired
	private ResFileService resFileService;

	@Autowired
	private ResModelService resModelService;

	@Autowired
	private DrawResHousePicService drawResHousePicService;

	@Autowired
	private BaseHouseMapper baseHouseMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DrawBaseHouseSubmitDTO saveDrawDesignTempletBySubmit(
			DrawBaseHouseSubmitDTO dto, UserLoginBO userLoginBO) {
		// 参数验证 ->start
		if (dto == null) {
			return null;
		}

		if (userLoginBO == null) {
			userLoginBO = HouseAuthUtils.getUnknownUser();
		}

		// TODO 可以优化的地方
		for (DrawSpaceCommonDTO drawSpaceCommonDTO : dto.getSpaceCommonDTOList()) {
			if (drawSpaceCommonDTO.getSpaceCommonId() == null) {
				return null;
			}
			DrawSpaceCommon drawSpaceCommon = drawSpaceCommonService.getSpaceCommon(drawSpaceCommonDTO.getSpaceCommonId());
			if (drawSpaceCommon == null) {
				return null;
			}
			drawSpaceCommonDTO.setDrawSpaceCommon(drawSpaceCommon);
		}
		// TODO 可以优化的地方

		// 参数验证 ->end
		
		// 填充drawDesignTemplet参数
		for (DrawSpaceCommonDTO drawSpaceCommonDTO : dto.getSpaceCommonDTOList()) {
			DrawSpaceCommon drawSpaceCommon = drawSpaceCommonDTO.getDrawSpaceCommon();
			Date now = new Date();
			DrawDesignTemplet drawDesignTemplet = new DrawDesignTemplet();
			drawDesignTemplet.setDesignCode(getDesignCode(drawSpaceCommon.getSpaceCode()));
			drawDesignTemplet.setDesignName(drawSpaceCommon.getSpaceName() + "户型绘制样板房");
			drawDesignTemplet.setUserId(Integer.valueOf(userLoginBO.getId() + ""));
			drawDesignTemplet.setSpaceCommonId(Integer.valueOf(drawSpaceCommon.getId() + ""));
			drawDesignTemplet.setIsRecommend(1);
			drawDesignTemplet.setSysCode(Utils.getSysCode(6));
			drawDesignTemplet.setCreator(userLoginBO.getLoginName());
			drawDesignTemplet.setGmtCreate(now);
			drawDesignTemplet.setModifier(userLoginBO.getLoginName());
			drawDesignTemplet.setGmtModified(now);
			drawDesignTemplet.setIsDeleted(DrawDesignTempletConstant.DESIGNTEMPLET_ISDELETE_DEFAULT);
			drawDesignTemplet.setSyncStatus("NOT_SYNCHRONIZED");
			// 样板房图
			drawDesignTemplet.setPicId(drawSpaceCommon.getPicId());
			drawDesignTemplet.setEffectPic(Objects.toString(drawSpaceCommon.getPicId(), ""));
			drawDesignTemplet.setEffectPlanIds(Objects.toString(drawSpaceCommon.getPicId(), ""));
			// 是否有天花区域；0、否(defaults)；1是
			drawDesignTemplet.setIsRegionalCeiling(drawSpaceCommonDTO.getIsRegionalCeiling());

			// 0、平台数据；1、个人数据
			drawDesignTemplet.setDataType(dto.getDataType());

			// 获取发布状态
			drawDesignTemplet.setPutawayState(getPutawayState(dto.getDrawHouse()));

			// 卫生间处理 space_layout_type
			if (HouseType.TOILET.toString().equalsIgnoreCase(drawSpaceCommonDTO.getSpaceCommonFunctionValueKey())) {
				String spaceLayoutType = getSpaceLayoutType(drawSpaceCommonDTO.getDrawBaseProductDTOList());
				drawDesignTemplet.setSpaceLayoutType(spaceLayoutType);
				log.info("卫生间 spaceLayoutType 处理 ==> {}", spaceLayoutType);
			}

			// save
			this.save(drawDesignTemplet);
			drawSpaceCommonDTO.setDesignTempletId(drawDesignTemplet.getId());
		}

		return dto;
	}
	
	/**
	 * 根据space code 生成DesignCode
	 * 
	 * @param arg
	 * @return
	 */
	private String getDesignCode(String arg) {
		if (arg == null) {
			return "unknown_" + Utils.random();
		}

		String[] split = arg.split("_");
		if (split.length > 0) {
			split[split.length - 1] = Utils.generateRandomDigitString(6);
			return Arrays.stream(split).collect(Collectors.joining("_"));
		}

		return "unknown_" + Utils.random();
	}
	
	/**
	 * 处理卫生间 space layout type 值
	 * </p>
	 * {@link com.sandu.common.constant.SpaceLayoutType}
	 * </p>
	 *
	 * @param dtp
	 * @return (A, B, AB, N)
	 */
	private String getSpaceLayoutType(List<DrawBaseProductDTO> dtp) {
		if (dtp == null || dtp.isEmpty()) {
			return SpaceLayoutType.N.toString();
		}

		Set<String> set = new HashSet<>(3);
		for (DrawBaseProductDTO p : dtp) {
			// A => basic_edba, basic_baba
			// B => basic_ahba, basic_dzas, basic_asba
			// AB => A + B 都含有
			// N => 啥都没有
			SpaceLayoutType layoutType = SpaceLayoutType.contains(p.getSmallTypeValueKey());
			set.add(layoutType.toString());
		}

		if (set.isEmpty()) {
			return SpaceLayoutType.N.toString();
		}

		String layout = set.stream().collect(Collectors.joining());
		if (layout.length() >= 2 && layout.contains(SpaceLayoutType.N.toString())) {
			layout = layout.replace(SpaceLayoutType.N.toString(), "");
		}

		return layout.matches(Regex.SPACE_LAYOUT_B_TYPE.getValue()) ? StringUtils.reverse(layout) : layout;
	}

	/**
	 * 获取发布状态
	 *
	 * @param drawHouse
	 * @return
	 */
	private Integer getPutawayState(DrawBaseHouse drawHouse) {
		if (DrawBaseHouseConstant.DATA_PERSONAL.equals(drawHouse.getDataType())) {
			return DesignTempletStatusConstant.HAS_BEEN_RELEASE;
		}

		// 检查原先是否发布，如果发布继续为发布状态
		if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() > 0) {
			Integer putawayState = baseHouseMapper.getPutawayState(drawHouse.getBaseHouseId());
			return DesignTempletStatusConstant.HAS_BEEN_RELEASE.equals(putawayState) ? DesignTempletStatusConstant.HAS_BEEN_RELEASE : DesignTempletStatusConstant.TESTING;
			// return (putawayState == null) ? DesignTempletStatusConstant.TESTING : putawayState;
		}

		return DesignTempletStatusConstant.TESTING;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long save(DrawDesignTemplet drawDesignTemplet) {
		// 参数验证 ->start
		if (drawDesignTemplet == null) {
			return null;
		}
		// 参数验证 ->end

		drawDesignTempletMapper.insertSelective(drawDesignTemplet);
		return drawDesignTemplet.getId();
	}

	@Override
	public List<DrawDesignTemplet> findAllBySpaceCommonId(Long drawSpaceCommonId) {
		// 参数验证 ->start
		if (drawSpaceCommonId == null) {
			return null;
		}
		// 参数验证 ->end

		return drawDesignTempletMapper.findAllBySpaceCommonId(drawSpaceCommonId);
	}

	@Override
	public DrawDesignTemplet getDrawDesignTempletBySpaceCommonId(Long drawSpaceCommonId) {
		if (drawSpaceCommonId == null) {
			return null;
		}

		return drawDesignTempletMapper.getDrawDesignTempletBySpaceCommonId(drawSpaceCommonId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(DrawDesignTemplet drawDesignTemplet) {
		// 参数验证 ->start
		if (drawDesignTemplet == null) {
			return;
		}
		if (drawDesignTemplet.getId() == null) {
			return;
		}
		// 参数验证 ->end

		drawDesignTempletMapper.updateByPrimaryKeySelective(drawDesignTemplet);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<Long, Long> transformToDesignTemplet(List<DrawDesignTemplet> drawDesignTempletList, final Long spaceCommonId) throws DrawBusinessException {
		Map<Long, Long> returnMap = new HashMap<Long, Long>();

		// 参数验证 ->start
		if (drawDesignTempletList == null || drawDesignTempletList.size() == 0) {
			log.error("没有需要转换的样板房数据, drawDesignTempletList ==> {}", drawDesignTempletList);
			return returnMap;
		}

		if (spaceCommonId == null || spaceCommonId <= 0) {
			log.error("参数 spaceCommonId 是空");
			throw new BusinessException(false, ResponseEnum.SPACE_NOT_FOUND);
		}
		// 参数验证 ->end
		
		log.info("开发转化design_templet信息数据");

		for (DrawDesignTemplet drawDesignTemplet : drawDesignTempletList) {
			drawDesignTemplet.setSpaceCommonId(Integer.valueOf(spaceCommonId + ""));

			// 样板房模型/配置文件转换 ->start
			Utils.requireGreaterZero(drawDesignTemplet.getConfigFileId(), "样板房" + drawDesignTemplet.getId() + "的配置文件不能为空");
			DrawResFile drawResFileConfig = drawResFileService.get(Long.valueOf(drawDesignTemplet.getConfigFileId() + ""));
			Long configFileId = resFileService.createByDrawResFile(drawResFileConfig, SystemConfigEnum.DESIGNTEMPLET_CONFIGFILE_UPLOAD.getFileKey(), true);
			if (configFileId == null) {
				throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
			}
			drawDesignTemplet.setConfigFileId(Integer.valueOf(configFileId + ""));

			Utils.requireGreaterZero(drawDesignTemplet.getPcModelU3dId(), "样板房" + drawDesignTemplet.getId() + "的PC模型文件不能为空");
			DrawResFile drawResFileModel = drawResFileService.get(Long.valueOf(drawDesignTemplet.getPcModelU3dId() + ""));
			Long pcModelU3dId = resModelService.createByDrawResFile(drawResFileModel, SystemConfigEnum.DESIGNTEMPLET_U3DMODEL_WINDOWSPC_UPLOAD.getFileKey(), true);
			if (pcModelU3dId == null) {
				throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
			}
			drawDesignTemplet.setPcModelU3dId(Integer.valueOf(pcModelU3dId + ""));
			// 样板房模型/配置文件转换 ->end
			
			// 处理样板房图片
			this.handlerDesigmTempletPic(drawDesignTemplet);
			
			// 标识绘制数据
			drawDesignTemplet.setOrigin(DesignTempletStatusConstant.ORIGIN_DRAW);

			// 已存在样板房
			this.handlerDesignTemplet(drawDesignTemplet);
//			DesignTemplet designTemplet = designTempletMapper.getDesignTempletByDesignCode(drawDesignTemplet.getDesignCode());
//			if (designTemplet == null) {
//				drawDesignTempletMapper.transformToDesignTemplet(drawDesignTemplet);
//				log.debug("新增样板房{}信息", drawDesignTemplet.getDesignCode());
//			} else {
//				log.info("更新样板房{}信息", drawDesignTemplet.getDesignCode());
//				DesignTemplet designTemplet2 = new DesignTemplet();
//				try {
//					BeanUtils.copyProperties(designTemplet2, drawDesignTemplet);
//					designTemplet2.setId(designTemplet.getId());
//					// 恢复数据
//					designTemplet2.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
//				} catch (IllegalAccessException | InvocationTargetException e) {
//					throw new DrawBusinessException(false, ResponseEnum.ERROR);
//				}
//
//				designTempletMapper.updateByPrimaryKeySelective(designTemplet2);
//				drawDesignTemplet.setDesignTempletId(designTemplet.getId());
//			}

			drawDesignTemplet.setSpaceCommonId(null);
			drawDesignTemplet.setConfigFileId(null);
			drawDesignTemplet.setPcModelU3dId(null);
			drawDesignTemplet.setPutawayState(null);
			// update design_templet_id
			this.update(drawDesignTemplet);
			returnMap.put(drawDesignTemplet.getId(), drawDesignTemplet.getDesignTempletId());

			// 回填文件 ->start
			List<Integer> resFileIdList = new ArrayList<Integer>();
			resFileIdList.add(Integer.valueOf(configFileId + ""));
			resFileIdList.add(Integer.valueOf(pcModelU3dId + ""));
			resFileService.updateBusinessId(resFileIdList, Integer.valueOf(drawDesignTemplet.getDesignTempletId() + ""));
			// 回填文件 ->end
		}
		
		log.info("转化design_templet信息数据结束");
		
		return returnMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void handlerDesignTemplet(DrawDesignTemplet drawDesignTemplet) {
		// 已存在样板房
		DesignTemplet designTemplet = designTempletMapper.getDesignTempletByDesignCode(drawDesignTemplet.getDesignCode());
		if (designTemplet == null) {
			drawDesignTempletMapper.transformToDesignTemplet(drawDesignTemplet);
			log.debug("新增样板房{}信息", drawDesignTemplet.getDesignCode());
		} else {
			log.info("更新样板房{}信息", drawDesignTemplet.getDesignCode());
			DesignTemplet designTemplet2 = new DesignTemplet();
			try {
				BeanUtils.copyProperties(designTemplet2, drawDesignTemplet);
				designTemplet2.setId(designTemplet.getId());
				// 恢复数据
				designTemplet2.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new DrawBusinessException(false, ResponseEnum.ERROR);
			}

			designTempletMapper.updateByPrimaryKeySelective(designTemplet2);
			drawDesignTemplet.setDesignTempletId(designTemplet.getId());
		}
	}
	
	/**
	 * 处理样板房图片
	 * 
	 * @param designTemplet
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void handlerDesigmTempletPic(DrawDesignTemplet designTemplet) {
		log.debug("同步样板房图片处理");
		// 样板房picId
		DrawResHousePic pic = drawResHousePicService.getDrawResHousePic(Long.valueOf(Objects.toString(designTemplet.getEffectPic(), "-1")));
		if (pic != null) {
			Long picId = drawResHousePicService.copyFile(pic, SystemConfigEnum.DESIGN_TEMPLET_PIC_UPLOAD.getFileKey(), false);
			designTemplet.setPicId(picId == null ? -1 : picId.intValue());
		}
		
		// 样板房effectPic
		DrawResHousePic effectPic = drawResHousePicService.getDrawResHousePic(Long.valueOf(Objects.toString(designTemplet.getEffectPic(), "-1")));
		if (effectPic != null) {
			Long effectPicId = drawResHousePicService.copyFile(effectPic, SystemConfigEnum.DESIGN_TEMPLET_PIC_UPLOAD.getFileKey(), true);
			designTemplet.setEffectPic(Objects.toString(effectPicId, ""));
		}

		// 样板房effectPlanPic
		DrawResHousePic effectPlanPic = drawResHousePicService.getDrawResHousePic(Long.valueOf(Objects.toString(designTemplet.getEffectPlanIds(), "-1")));
		if (effectPic != null) {
			Long effectPlanPicId = drawResHousePicService.copyFile(effectPlanPic, SystemConfigEnum.DESIGN_TEMPLET_PIC_UPLOAD.getFileKey(), true);
			designTemplet.setEffectPlanIds(Objects.toString(effectPlanPicId, ""));
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteDrawDesignTemplet(Integer status, List<Long> emptySpaces) {
		if (emptySpaces == null || emptySpaces.isEmpty()) {
			return 0;
		}
		return drawDesignTempletMapper.deleteDrawDesignTemplet(status, emptySpaces);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteDesignTemplet(Integer status, List<Long> emptySpaces) {
		if (emptySpaces == null || emptySpaces.isEmpty()) {
			return 0;
		}
		return drawDesignTempletMapper.deleteDesignTemplet(status, emptySpaces);
	}

	@Override
	public List<Long> getDeleteDesignTemplet(List<Long> args) {
		if (args == null || args.isEmpty()) {
			return null;
		}
		return drawDesignTempletMapper.getDeleteDesignTemplet(args);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer emptyDesignTemplet(Integer status, List<Long> emptySpaces) {
		if (emptySpaces == null || emptySpaces.isEmpty()) {
			return -1;
		}
		return drawDesignTempletMapper.deleteDesignTemplet(status, emptySpaces);
	}

	@Override
	public List<Long> getEmptyDesignTemplet(List<Long> emptySpaces) {
		if (emptySpaces == null || emptySpaces.isEmpty()) {
			return null;
		}
		return drawDesignTempletMapper.getEmptyDesignTemplet(emptySpaces);
	}

	@Override
	public List<Long> getEmptyDrawDesignTemplet(List<Long> emptySpaces) {
		if (emptySpaces == null || emptySpaces.isEmpty()) {
			return null;
		}
		return drawDesignTempletMapper.getEmptyDrawDesignTemplet(emptySpaces);
	}
}
