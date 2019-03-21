package com.sandu.api.base.service;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:17 2019/1/8 0008
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.api.base.common.LoginUser;
import com.sandu.api.base.input.UserCardAccessOperationLogAdd;
import com.sandu.api.base.input.UserCardAccessRecordAdd;
import com.sandu.api.base.model.UserCardAccessRecordCount;
import com.sandu.api.base.output.UserCardAccessRecordVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2019/1/8 0008AM 10:17
 */
@Component
public interface UserCardAccessService {
    int addUserCardAccessRecord(UserCardAccessRecordAdd userCardAccessRecordAdd, LoginUser loginUser);

    void sendUserCardAccessRecordWebSocket(Integer userCardId);

    int addUserCardAccessOperationLog(UserCardAccessOperationLogAdd userCardAccessOperationLogAdd, LoginUser loginUser);

    void sendUserCardAccessOperationWebSocket(Integer userCardId);

    int getUserCardAccessCount(Integer userAccessId, LoginUser loginUser);

    List<UserCardAccessRecordVo> getUserCardAccessList(Integer userAccessId, LoginUser loginUser, int start, int limit);

    int getUserCardAccessOperationLogCount(Integer userAccessId, LoginUser loginUser, Integer purposeType);

    List<UserCardAccessRecordVo> getUserCardAccessOperationLogList(Integer userAccessId, LoginUser loginUser, Integer purposeType, int start, int limit);

    UserCardAccessRecordCount getUserCardAccessRecordCountInfo(Integer userAccessId);

    int updateUserCardAccessRecordToIsRead(Integer userAccessId, LoginUser loginUser, int start, int limit);

    int updateUserCardAccessOperationLogToIsRead(Integer userAccessId, LoginUser loginUser, Integer purposeType, int start, int limit);

    boolean checkPhoneAndCode(UserCardAccessOperationLogAdd userCardAccessOperationLogAdd);
}
