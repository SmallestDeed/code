package com.sandu.service.user.dao;

import com.sandu.api.user.model.UserJurisdiction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserJurisdictionDao {

	UserJurisdiction selectByUserIdAndPlatformId(@Param("userId") Long userId,@Param("platformId") Long platformId);

	int countByUserIdAndJurisdictionStatus(@Param("userId")Long userId, @Param("jurisdictionStatus")int jurisdictionStatus);

    int insert(UserJurisdiction userJurisdiction);

    void delByPrimaryKey(Long id);

    void delByIds(@Param("userId")Integer userId);
    
    List<UserJurisdiction> selectByUserId(@Param("userId") Long userId);
    
    Integer batchUserJurisdictions(@Param("ujs") List<UserJurisdiction> ujs);

    Integer updateUserJurisdiction(@Param("isDeleted") Integer isDeleted,
                                @Param("list") List<UserJurisdiction> list);

    List<UserJurisdiction> listUserJurisdiction(@Param("userId") Integer userId,
                                                @Param("isDeleted") Integer isDeleted);

    List<UserJurisdiction> queryByUserIdANDplatformIds(@Param("userId") Long userId,@Param("adminPlatforms") List<Integer> adminPlatforms);

    void batchDelUserJurisdiction(@Param("userId")int userId, @Param("oldPlatformIds")Set<Long> oldPlatformIds);
}
