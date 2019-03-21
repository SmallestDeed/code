package com.sandu.service.base.dao;

import com.sandu.api.base.model.UserCardAccessOperationLog;
import com.sandu.api.base.model.UserCardAccessRecord;
import com.sandu.api.base.model.UserCardAccessRecordCount;
import com.sandu.api.base.output.UserCardAccessRecordVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:30 2019/1/8 0008
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
@Repository
public interface UserCardAccessMapper {
    int insertUserCardAccessRecord(UserCardAccessRecord userCardAccessRecord);

    int insertUserCardAccessOperationLog(UserCardAccessOperationLog userCardAccessOperationLog);

    int getUserCardAccessCount(@Param("userAccessId") Integer userAccessId, @Param("userId") Integer userId);

    List<UserCardAccessRecordVo> getUserCardAccessList(@Param("userAccessId") Integer userAccessId, @Param("userId") Integer userId, @Param("start") int start,@Param("limit") int limit);

    int selectUserCardAccessOperationLogCount(@Param("userAccessId") Integer userAccessId,@Param("userId") Integer userId, @Param("purposeType") Integer purposeType);

    List<UserCardAccessRecordVo> selectUserCardAccessOperationLogList(@Param("userAccessId") Integer userAccessId, @Param("userId") Integer userId, @Param("purposeType") Integer purposeType, @Param("start") int start,@Param("limit") int limit);

    UserCardAccessRecordCount selectUserCardAccessRecordCountInfo(@Param("userAccessId") Integer userAccessId);

    int updateUserCardAccessRecordToIsRead(@Param("userAccessId") Integer userAccessId, @Param("userId") Integer userId, @Param("start") int start,@Param("limit") int limit);

    int updateUserCardAccessOperationLogToIsRead(@Param("userAccessId") Integer userAccessId, @Param("userId") Integer userId, @Param("purposeType") Integer purposeType, @Param("start") int start,@Param("limit") int limit);

    int selectUnReadUserCardAccessRecord(Integer userCardId);

    int selectUnReadUserCardAccessOperationLog(Integer userCardId);
}
