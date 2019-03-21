package com.sandu.api.springFestivalActivity.service.biz;

import com.sandu.api.springFestivalActivity.input.UserInviteRecordBo;
import com.sandu.api.springFestivalActivity.model.WxFilmTicket;
import com.sandu.api.springFestivalActivity.output.*;
import com.sandu.api.user.model.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface FilmTicketActivityService {
    /**
     * 获取红点标识
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return hasTaskFlag 是否有可领取的任务，activityOverFlag 活动是否结束，cardNumFlag 今日是否还有可领的卡片
     */
    RedPointFlagVo getRedPointFlag(Long userId, Long activityId);

    /**
     * 助力
     *
     * @param giveUser   助力的用户
     * @param getUser    获得助力的用户
     * @param activityId 活动ID
     * @return success 助力是否成功, message 助力消息
     */
    GiveMeFiveVo giveMeFive(SysUser giveUser, SysUser getUser, Long activityId);

    /**
     * 获取邀请好友记录总数
     *
     * @param search 查询参数
     * @return 邀请记录列表
     */
    Integer getInviteRecordCount(UserInviteRecordBo search);

    /**
     * 获取邀请好友记录列表
     *
     * @param search 查询参数
     * @return 邀请记录列表
     */
    List<UserInviteRecordVo> getInviteRecord(UserInviteRecordBo search);

    /**
     * 获取用户任务状态
     *
     * @param user       用户
     * @param activityId 活动ID
     * @return
     */
    UserTaskVo getUserTaskState(SysUser user, Long activityId);

    /**
     * 领取任务
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @param taskId     任务ID
     * @return
     */
    Boolean doTask(Long userId, Long activityId, Long taskId);

    /**
     * 完成任务
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return 返回完成任务获得的卡片
     */
    String doneTask(Long userId, Long activityId);

    /**
     * 查询拼图页面数据
     *
     * @param user       用户
     * @param activityId 活动ID
     * @return
     */
    CardPageVo getCardPageVo(SysUser user, Long activityId);

    /**
     * 获取电影票信息
     *
     * @param user       用户
     * @param activityId 活动ID
     * @return
     */
    FilmTicketVo getFilmTicket(SysUser user, Long activityId);

    /**
     * 增加中奖几率
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return
     */
    Integer increaseLotteryRate(Long userId, Long activityId);

    /**
     * 校验验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return
     */
    Boolean checkPermission(String phone, String code);

    /**
     * 功能描述: 获取未领取卡片
     *
     * @param activityId 活动ID
     * @param sysUser    当前用户
     * @return java.util.List<com.sandu.api.springFestivalActivity.output.WXUserCardVo>
     * @throws
     * @author gaoj
     * @date 2019/1/26 15:40
     */
    List<WxUserCardVo> getUnReceivedCard(Long activityId, SysUser sysUser);

    /**
     * 查询已被用户获取的电影票列表
     *
     * @param activityId 活动ID
     * @param limit      滚动条数
     * @return
     */
    List<WxFilmTicket> getNotEmptyTicketList(Long activityId, Integer limit);

    /**
     * 获取正在做任务或者有任务可领的用户ID集合
     *
     * @param activityId 活动ID
     * @return
     */
    List<Long> getUserListWhoDoingTask(Long activityId);

    /**
     * 获取今天获得卡片小于三张的用户集合
     *
     * @param activityId 活动ID
     * @return
     */
    Map<Long, Integer> getUserListWhoCardNumLessThanThree(Long activityId);

    /**
     * 构建模板消息数据
     *
     * @param strings 消息
     * @return
     */
    Map<String, Map<String, String>> buildData(String... strings);

    /**
     * 获取活动的开始时间和结束时间
     *
     * @param activityId 活动ID
     * @return
     */
    DateVo getActivityTime(Long activityId);

    /**
     * 获取春节活动详情
     *
     * @param activityId 活动ID
     * @return
     */
    WxSpringActivityVo getSpringActivityDetail(Long activityId);
}
