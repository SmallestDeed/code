package com.sandu.service.base.impl;

import com.sandu.api.base.input.BasePlatformUpdate;
import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.base.service.BasePlatformService;
import com.sandu.api.user.constant.UserTypeConstant;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.model.UserJurisdiction;
import com.sandu.api.user.output.BasePlatformVO;
import com.sandu.api.user.output.UserPlatformDetailVO;
import com.sandu.common.constant.PlatformConstants;
import com.sandu.service.base.dao.BaseCompanyDao;
import com.sandu.service.base.dao.BasePlatformDao;
import com.sandu.service.user.dao.SysUserDao;
import com.sandu.service.user.dao.UserJurisdictionDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("basePlatformService")
public class BasePlatformServiceImpl implements BasePlatformService {

    @Autowired
    private BasePlatformDao basePlatformDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private BaseCompanyDao baseCompanyDao;

    @Autowired
    private UserJurisdictionDao userJurisdictionDao;

    @Override
    public BasePlatform queryByPlatformCode(String platformCode) {
        return basePlatformDao.selectByPlatformCode(platformCode);
    }

    @Override
    public List<BasePlatform> queryList() {
        return basePlatformDao.queryList();
    }

    @Override
    public List<BasePlatformVO> listPlatform(Integer userId) {
        SysUser sysUser = sysUserDao.selectByPrimaryKey(userId);
        if (sysUser == null || sysUser.getIsDeleted() == 1) {
            throw new RuntimeException("Error:用户不存在或被删除");
        }

        // 经销商使用 ToC 的平台
        return basePlatformDao.listPlatform((Objects.equals(sysUser.getFranchiserAccountType(),
                SysUser.FRANCHISER_ACCOUNT_TYPE_SUB)) ? PlatformConstants.TC : null);
    }

    @Override
    @Transactional
    public Integer updateUserPlatform(BasePlatformUpdate platform, LoginUser loginUser) {
        // 验证
        this.validUpdatePlatform(platform.getUserId(), platform.getPlatforms());

        // 去重
        platform.setPlatforms(platform.getPlatforms().stream().distinct().collect(Collectors.toList()));

        List<UserJurisdiction> listExistPlatform = userJurisdictionDao.listUserJurisdiction(platform.getUserId(), null);
        // 更新、删除处理
        // 如果之前有存在的授权平台，需要进行特殊的处理
        // 之前授权过的平台和本次需要授权的平台的两者的差集就是不需要的授权的平台，需要删除
        // 两者的交集是不需要重复授权的平台
        Integer updateCount = this.handlerPlatformForUpdate(listExistPlatform, platform);

        // 新增
        UserJurisdiction compare = new UserJurisdiction();
        List<UserJurisdiction> listPlatformNew = new ArrayList<>();
        for (Long item : platform.getPlatforms()) {
            // 不要动，有魔法！！！！！！！WTF
            compare.setPlatformId(item);
            compare.setUserId(platform.getUserId().longValue());
            if (CollectionUtils.isEmpty(listExistPlatform) || !listExistPlatform.contains(compare)) {
                listPlatformNew.add(UserJurisdiction.builder()
                        .userId(platform.getUserId().longValue())
                        .jurisdictionStatus(1)
                        .platformId(item)
                        .gmtCreate(new Date())
                        .gmtModified(new Date())
                        .modifier(loginUser.getLoginName())
                        .creator(loginUser.getLoginName())
                        .sysCode(System.currentTimeMillis() + "")
                        .isDeleted(0).build());
            }
        }

        if (!listPlatformNew.isEmpty()) {
            log.info("更新用户平台：userId => {} 删除授权的平台 => {}", platform.getUserId(), listPlatformNew);
            updateCount = userJurisdictionDao.batchUserJurisdictions(listPlatformNew);
        }

        return updateCount;
    }

    /**
     * 更新、删除处理
     *
     * @param listExistPlatform
     * @param platform
     * @return
     */
    private Integer handlerPlatformForUpdate(List<UserJurisdiction> listExistPlatform, BasePlatformUpdate platform) {
        if (listExistPlatform == null || listExistPlatform.isEmpty()) {
            return 0;
        }

        Integer updateCount = 0;

        // 下面两行Code不要动，有魔法（WTF!!!!!!!!）
        // removeAll处理会把原始的集合里的元素删除，需要Copy一份出来处理，否则后面执行 retainAll 逻辑会有错误！！！
        // SysUserRoleGroup 类里的 equals 和 hashCode 方法已被重写，不要动里面的逻辑（WTF!!!!!!!!），否则整段垮掉！！！！！！
        List<UserJurisdiction> deletePlatform = listExistPlatform.stream().filter(item -> !Objects.equals(item.getIsDeleted(), 1))
                .map(item -> UserJurisdiction.builder().id(item.getId()).platformId(item.getPlatformId())
                        .userId(item.getUserId()).build()).collect(Collectors.toList());
        // 需要处理的权限组
        List<UserJurisdiction> prepPlatform = platform.getPlatforms().stream().map(item -> UserJurisdiction.builder()
                .userId(platform.getUserId().longValue()).platformId(item).build()).collect(Collectors.toList());

        // 删除之前的授权平台，而且本次有不授权的平台
        deletePlatform.removeAll(prepPlatform);
        if (!deletePlatform.isEmpty()) {
            log.info("更新用户平台：userId => {} 删除授权的平台 => {}", platform.getUserId(), deletePlatform);
            updateCount += userJurisdictionDao.updateUserJurisdiction(1, deletePlatform);
        }

        // 删除之后，还是已存在的平台，下面的处理需要排除这些平台，不要重复添加
        listExistPlatform.retainAll(prepPlatform);
        List<UserJurisdiction> listPlatformUpdate = listExistPlatform.stream().filter(item ->
                !Objects.equals(item.getIsDeleted(), 0)).distinct().collect(Collectors.toList());
        if (listPlatformUpdate != null && !listPlatformUpdate.isEmpty()) {
            log.info("更新用户平台：userId => {} 更新了授权的平台 => {}", platform.getUserId(), listPlatformUpdate);
            updateCount += userJurisdictionDao.updateUserJurisdiction(0, listPlatformUpdate);
        }

        return updateCount;
    }

    @Override
    public UserPlatformDetailVO getUserPlatform(Integer userId) {
        List<UserJurisdiction> listPlatform = userJurisdictionDao.listUserJurisdiction(userId, 0);
        if (listPlatform == null || listPlatform.isEmpty()) {
            return UserPlatformDetailVO.builder().userId(userId).build();
        }

        List<Long> platforms = listPlatform.stream().map(UserJurisdiction::getPlatformId).collect(Collectors.toList());
        return UserPlatformDetailVO.builder().userId(userId).platforms(platforms).build();
    }

    @Override
    public List<BasePlatform> queryByIds(Set<Long> platformIds) {
        return basePlatformDao.queryByIds(platformIds);
    }

    /**
     * 验证数据
     *
     * @param userId
     * @param platforms
     */
    private void validUpdatePlatform(Integer userId, List<Long> platforms) {
        SysUser sysUser = sysUserDao.selectByPrimaryKey(userId);
        if (sysUser == null || sysUser.getIsDeleted() == 1) {
            log.error("更新用户平台：用户不存在或被删除， userId => {}, platforms => {}", userId, platforms);
            throw new RuntimeException("Error:用户不存在或被删除");
        }

        if (sysUser.getBusinessAdministrationId() == null
                || Objects.equals(sysUser.getUserType(), UserTypeConstant.USER_TYPE_COMPANY)) {
            return;
        }

        if (platforms == null || platforms.isEmpty()) {
            return;
        }

        // 主要，以下都是pc2b、mobile2b端的授权数量验证
        List<BasePlatform> listGrantMap = basePlatformDao.getPC2BAndMobile2BPlatform(platforms);
        if (listGrantMap == null || listGrantMap.isEmpty()) {
            return;
        }

        BaseCompany dealerCompany = baseCompanyDao.selectById(sysUser.getBusinessAdministrationId());
        if (dealerCompany == null) {
            log.error("更新用户平台：公司不存在或被删除， userId => {}, platforms => {}", userId, platforms);
            throw new RuntimeException("Error:公司不存在或被删除");
        }

        // 默认不需要验证
        if (dealerCompany.getPcCount() == null && dealerCompany.getMobileCount() == null) {
            log.info("更新用户平台: pcCount是空和mobileCount是空, {}", dealerCompany);
            return;
        }

        for (BasePlatform item : listGrantMap) {
            // 检查pc端可用数
            if (Objects.equals(item.getPlatformCode(), PlatformConstants.PC_2B) && platforms.contains(item.getId())) {
                Integer usedQtyOfPC = basePlatformDao.getUsedPlatformQty(sysUser.getBusinessAdministrationId(), PlatformConstants.PC_2B);
                if (dealerCompany.getPcCount() != null && (dealerCompany.getPcCount() <= (usedQtyOfPC == null ? 0 : usedQtyOfPC))) {
                    log.error("更新用户平台：PC端权限数量已满，PC端权限不能配置, userId => {}, platforms => {}", userId, platforms);
                    throw new RuntimeException("Error:PC端权限数量已满，PC端权限不能配置!");
                }
            }

            // 检查mobile端可用数
            if (Objects.equals(item.getPlatformCode(), PlatformConstants.MOBILE_2B) && platforms.contains(item.getId())) {
                Integer usedQtyOfMobile = basePlatformDao.getUsedPlatformQty(sysUser.getBusinessAdministrationId(), PlatformConstants.MOBILE_2B);
                if (dealerCompany.getMobileCount() != null && (dealerCompany.getMobileCount() <= (usedQtyOfMobile == null ? 0 : usedQtyOfMobile))) {
                    log.error("更新用户平台：PC端权限数量已满，PC端权限不能配置， userId => {}, platforms => {}", userId, platforms);
                    throw new RuntimeException("Error:PC端权限数量已满，PC端权限不能配置!");
                }
            }
        }
    }

	@Override
	public Integer getIdByPlatformCode(String platformCode) {
		BasePlatform basePlatform = basePlatformDao.selectByPlatformCode(platformCode);
		if (null != basePlatform) {
			return basePlatform.getId().intValue();
		}
		return 0;
	}
}
