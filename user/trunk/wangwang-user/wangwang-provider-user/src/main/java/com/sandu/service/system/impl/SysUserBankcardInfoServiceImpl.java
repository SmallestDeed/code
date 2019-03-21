package com.sandu.service.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.system.constant.SysUserBankcardInfoConstants;
import com.sandu.api.system.input.SysUserBankcardInfoDTO;
import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.model.SysUserBankcardInfo;
import com.sandu.api.system.output.SysUserBankcardInfoListVO;
import com.sandu.api.system.search.SysUserBankcardInfoSearch;
import com.sandu.api.system.service.RAMCacheService;
import com.sandu.api.system.service.SysUserBankcardInfoService;
import com.sandu.common.exception.BizException;
import com.sandu.common.model.output.CommonDTO;
import com.sandu.service.system.dao.SysUserBankcardInfoMapper;

import lombok.extern.log4j.Log4j2;

@Service("sysUserBankcardInfoService")
@Log4j2
public class SysUserBankcardInfoServiceImpl implements SysUserBankcardInfoService {

	private static final String LOGPREFIX = "[银行卡信息模块]: ";
	
	@Autowired
	private SysUserBankcardInfoMapper sysUserBankcardInfoMapper;
	
	@Autowired
	private RAMCacheService ramCacheService;
	
	@Override
	public List<SysUserBankcardInfoListVO> getSysUserBankcardInfoListVO(Long userId) {
		// ------参数验证 ->start
		if(userId == null) {
			throw new BizException("查询银行卡信息失败(userId = null)");
		}
		// ------参数验证 ->end
		
		SysUserBankcardInfoSearch sysUserBankcardInfoSearch = new SysUserBankcardInfoSearch();
		sysUserBankcardInfoSearch.setUserId(userId);
		sysUserBankcardInfoSearch.setIsDeleted(0);
		List<SysUserBankcardInfo> sysUserBankcardInfoList = this.getListBySearch(sysUserBankcardInfoSearch);
		
		List<SysUserBankcardInfoListVO> returnList = this.getSysUserBankcardInfoListVO(sysUserBankcardInfoList);
		return returnList;
	}

	@Override
	public List<SysUserBankcardInfo> getListBySearch(SysUserBankcardInfoSearch sysUserBankcardInfoSearch) {
		// ------参数验证 ->start
		if(sysUserBankcardInfoSearch == null) {
			log.error(LOGPREFIX + "sysUserBankcardInfoSearch = null");
			return null;
		}
		// ------参数验证 ->end
		
		return sysUserBankcardInfoMapper.selectListBySearch(sysUserBankcardInfoSearch);
	}

	@Override
	public void create(SysUserBankcardInfoDTO paramDTO, String creator) {
		// ------参数验证 ->start
		if(paramDTO == null) {
			throw new BizException("创建银行卡信息失败(paramDTO = null)");
		}
		if(paramDTO.getUserId() == null) {
			throw new BizException("创建银行卡信息失败(paramDTO.getUserId() = null)");
		}
		// ------参数验证 ->end
		
		// ------如果该用户已经创建了10条银行卡数据, 不允许再创建... ->start
		CommonDTO commonDTO = this.getIsAllowCreateResult(paramDTO.getUserId());
		if(!commonDTO.isFlag()) {
			throw new BizException(commonDTO.getMessage());
		}
		// ------如果该用户已经创建了10条银行卡数据, 不允许再创建... ->end
		
		SysUserBankcardInfo sysUserBankcardInfo = this.getSysUserBankcardInfo(paramDTO);
		int count = this.add(sysUserBankcardInfo, creator);
		
		if(count == 0) {
			throw new BizException("创建银行卡信息失败(count = 0)");
		}
	}

	@Override
	public int getCountByUserId(Long userId) {
		// ------参数验证 ->start
		if(userId == null) {
			log.error(LOGPREFIX + "userId = null");
			return 0;
		}
		// ------参数验证 ->end
		
		SysUserBankcardInfoSearch sysUserBankcardInfoSearch = new SysUserBankcardInfoSearch();
		sysUserBankcardInfoSearch.setUserId(userId);
		sysUserBankcardInfoSearch.setIsDeleted(0);
		return this.getCountBySearch(sysUserBankcardInfoSearch);
	}

	@Override
	public int getCountBySearch(SysUserBankcardInfoSearch sysUserBankcardInfoSearch) {
		// ------参数验证 ->start
		if(sysUserBankcardInfoSearch == null) {
			log.error(LOGPREFIX + "sysUserBankcardInfoSearch = null");
			return 0;
		}
		// ------参数验证 ->end
		
		return sysUserBankcardInfoMapper.selectCountBySearch(sysUserBankcardInfoSearch);
	}

	@Override
	public int add(SysUserBankcardInfo sysUserBankcardInfo, String creator) {
		// ------参数验证 ->start
		if(sysUserBankcardInfo == null) {
			log.error(LOGPREFIX + "sysUserBankcardInfo = null");
			return 0;
		}
		// ------参数验证 ->end
		
		// ------添加一些默认值 ->start
		Date now = new Date();
		if(sysUserBankcardInfo.getSubBranchInfo() == null) {
			sysUserBankcardInfo.setSubBranchInfo(null);
		}
		sysUserBankcardInfo.setCreator(creator);
		sysUserBankcardInfo.setGmtCreate(now);
		sysUserBankcardInfo.setModifier(creator);
		sysUserBankcardInfo.setGmtModified(now);
		sysUserBankcardInfo.setIsDeleted(0);
		// ------添加一些默认值 ->end
		
		return sysUserBankcardInfoMapper.insert(sysUserBankcardInfo);
	}

	@Override
	public void delete(Long id, Long userId) {
		// ------参数验证 ->start
		if(id == null) {
			throw new BizException("删除银行卡信息失败(id = null)");
		}
		if(userId == null) {
			throw new BizException("删除银行卡信息失败(userId = null)");
		}
		// ------参数验证 ->end
		
		int count = this.deleteByIdAndUserId(id, userId);
		
		if(count == 0) {
			throw new BizException("删除银行卡信息失败(银行卡信息找不到或者该数据不是你创建的)");
		}
	}
	
	@Override
	public int deleteByIdAndUserId(Long id, Long userId) {
		// ------参数验证 ->start
		if(id == null) {
			log.error(LOGPREFIX + "id = null");
			return 0;
		}
		if(userId == null) {
			log.error(LOGPREFIX + "userId = null");
			return 0;
		}
		// ------参数验证 ->end
		
		return sysUserBankcardInfoMapper.deleteByIdAndUserId(id, userId);
	}

	@Override
	public CommonDTO getIsAllowCreateResult(Long userId) {
		int cardCount = this.getCountByUserId(userId);
		if(cardCount >= 10) {
			return new CommonDTO(false, "创建银行卡信息失败(您已经创建了10张银行卡信息, 无法继续创建)");
		}
		
		return new CommonDTO(true, "");
	}
	
	/**
	 * SysUserBankcardInfo -> SysUserBankcardInfoListVO
	 * @param sysUserBankcardInfoList
	 * @return
	 */
	private List<SysUserBankcardInfoListVO> getSysUserBankcardInfoListVO(
			List<SysUserBankcardInfo> sysUserBankcardInfoList) {
		// ------参数验证 ->start
		if(sysUserBankcardInfoList == null) {
			log.error(LOGPREFIX + "sysUserBankcardInfoList == null");
			return null;
		}
		// ------参数验证 ->end
		
		List<SysUserBankcardInfoListVO> list = new ArrayList<SysUserBankcardInfoListVO>();
		
		for(SysUserBankcardInfo sysUserBankcardInfo : sysUserBankcardInfoList) {
			SysUserBankcardInfoListVO sysUserBankcardInfoListVO = new SysUserBankcardInfoListVO();
			sysUserBankcardInfoListVO.setCardType(SysUserBankcardInfoConstants.CARDTYPE_DEFAULT);
			sysUserBankcardInfoListVO.setIssuingBank(this.getIssuingBank(sysUserBankcardInfo.getIssuingBankValue()));
			sysUserBankcardInfoListVO.setCardNumberHide(this.getCardNumberHide(sysUserBankcardInfo.getCardNumber()));
			sysUserBankcardInfoListVO.setId(sysUserBankcardInfo.getId());
			sysUserBankcardInfoListVO.setInfo(sysUserBankcardInfoListVO.getIssuingBank() + " " + sysUserBankcardInfoListVO.getCardNumberHide());
			list.add(sysUserBankcardInfoListVO);
		}
		
		return list;
	}

	/**
	 * 银行卡卡号为了隐藏...将卡号转化成另一种格式
	 * eg: 1111111111111111 -> **** **** **** 1111
	 * 
	 * @author huangsongbo
	 * @param cardNumber
	 * @return
	 */
	private String getCardNumberHide(String cardNumber) {
		// ------参数验证 ->start
		if(StringUtils.isEmpty(cardNumber)) {
			return "";
		}
		// ------参数验证 ->end
		
		// ------银行卡号格式化配置 ->start
		// 每隔4个数字,加一个空格
		Integer spaceMultiple = 4;
		// 隐藏12位(前12位变成*)
		Integer maxHide = 12;
		// 隐藏字符, 要隐藏成什么样子
		String hideSign = "*";
		// ------银行卡号格式化配置 ->end
		
		StringBuffer stringBuffer = new StringBuffer();
		char[] chars = cardNumber.toCharArray();
		for (int index = 0; index < chars.length; index++) {
			
			if(index < maxHide) {
				stringBuffer.append(hideSign);
			} else {
				stringBuffer.append(chars[index]);
			}
			
			if(index != 0 && (index + 1) % spaceMultiple == 0) {
				stringBuffer.append(" ");
			}
		}
		
		return stringBuffer.toString();
	}

	/**
	 * select name from sys_dictionary where type = "issuingBank" and value = #{issuingBankValue}
	 * 
	 * @author huangsongbo
	 * @param issuingBankValue
	 * @return
	 */
	private String getIssuingBank(Integer issuingBankValue) {
		
		// ------参数验证 ->start
		if(issuingBankValue == null) {
			log.error(LOGPREFIX + "issuingBankValue = null");
			return SysUserBankcardInfoConstants.ISSUINGBANK_DEFAULT;
		}
		// ------参数验证 ->end
		
		SysDictionary sysDictionary = ramCacheService.getSysDictionaryByTypeAndValue("issuingBank", issuingBankValue);
		
		if(sysDictionary == null) {
			return SysUserBankcardInfoConstants.ISSUINGBANK_DEFAULT;
		}
		
		return sysDictionary.getName();
	}

	private SysUserBankcardInfo getSysUserBankcardInfo(SysUserBankcardInfoDTO paramDTO) {
		// ------参数验证 ->start
		if(paramDTO == null) {
			log.error(LOGPREFIX + "paramDTO = null");
			return null;
		}
		// ------参数验证 ->end
		
		SysUserBankcardInfo sysUserBankcardInfo = new SysUserBankcardInfo();
		sysUserBankcardInfo.setName(paramDTO.getName());
		sysUserBankcardInfo.setUserId(paramDTO.getUserId());
		sysUserBankcardInfo.setCardNumber(paramDTO.getCardNumber());
		sysUserBankcardInfo.setIssuingBankValue(paramDTO.getIssuingBankValue());
		sysUserBankcardInfo.setSubBranchInfo(paramDTO.getSubBranchInfo());
		return sysUserBankcardInfo;
	}

}
