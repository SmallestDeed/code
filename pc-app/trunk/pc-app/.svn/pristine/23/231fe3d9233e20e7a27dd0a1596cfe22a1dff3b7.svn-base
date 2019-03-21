package com.nork.base.service.impl;

import com.nork.base.dao.BasePlatformMapper;
import com.nork.base.model.BasePlatform;
import com.nork.base.service.BasePlatformService;
import com.nork.common.util.collections.Lists;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author yzw
 * @Date 2018/1/8 15:43
 */
@Service("basePlatformService")
public class BasePlatformServiceImpl implements BasePlatformService {

    private static final Logger logger = LoggerFactory.getLogger(BasePlatformServiceImpl.class);

    @Autowired
    private BasePlatformMapper basePlatformMapper;

    private static String logPrefixClass = "function:BasePlatformServiceImpl.";
    
    /**
     * 添加
     *
     * @param record
     * @return
     */
    public BasePlatform add(BasePlatform record) {
        if (this.basePlatformMapper.insertSelective(record) == 1)
            return record;
        return null;
    }

    /**
     * 删除
     *
     * @param id 平台id
     * @return
     */
    public boolean delete(Integer id) {
        return this.basePlatformMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 更新
     *
     * @param record 平台id
     * @return
     */
    public BasePlatform update(BasePlatform record) {
        if (this.basePlatformMapper.updateByPrimaryKeySelective(record) == 1)
            return record;
        return null;
    }

    /**
     * 获取
     *
     * @param id 平台id
     * @return
     */
    public BasePlatform get(Integer id) {
        return this.basePlatformMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取平台信息
     *
     * @param platformCode 平台编码
     * @return
     */
    @Override
    public BasePlatform getBasePlatformByCode(String platformCode) {
        return this.basePlatformMapper.getBasePlatformByCode(platformCode);
    }

	@Override
	public BasePlatform findOneByPlatformCode(String code) {
		String logPrefixFunction = logPrefixClass + "findOneByPlatformCode -> ";
		
		// 参数验证 ->start
		if(StringUtils.isEmpty(code)) {
			logger.error(logPrefixFunction + "StringUtils.isEmpty(code) = true");
			return null;
		}
		// 参数验证 ->end
		
		List<BasePlatform> basePlatformList = basePlatformMapper.findAllByPlatformCode(code);
		if(Lists.isEmpty(basePlatformList)) {
			return null;
		}else {
			return basePlatformList.get(0);
		}
	}
}
