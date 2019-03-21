CREATE TABLE `wx_film_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID(关联wx_spring_activity)',
  `user_id` bigint(20) DEFAULT NULL COMMENT '分配用户ID',
  `receive_time` timestamp NULL DEFAULT NULL COMMENT '领取时间',
  `allot_time` timestamp NULL DEFAULT NULL COMMENT '分配时间',
  `mobile` varchar(30) DEFAULT NULL COMMENT '领取手机号码',
  `ticket_code` varchar(50) DEFAULT '0' COMMENT '影券码',
  `ticket_status` tinyint(4) DEFAULT NULL COMMENT '影券状态(0-待分配;1-待领取)',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户影票记录表';


CREATE TABLE `wx_red_packet_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID(关联wx_spring_activity)',
  `signin_date` date DEFAULT NULL COMMENT '签到日期(年-月-日)',
  `red_packet_day_num` decimal(10,2) DEFAULT NULL COMMENT '每日签到红包总金额',
  `red_packet_use_num` decimal(10,2) DEFAULT NULL COMMENT '每日签到红包已使用金额',
  `red_packet_remain_num` decimal(10,2) DEFAULT NULL COMMENT '每日签到红包剩余金额',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='签到每日红包汇总';

CREATE TABLE `wx_signin_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID(关联wx_spring_activity)',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `red_packet_num` decimal(10,2) DEFAULT NULL COMMENT '红包金额(累计)',
  `sign_times` int(11) DEFAULT NULL COMMENT '签到总次数(累计)',
  `lottery_times` int(11) DEFAULT NULL COMMENT '可抽奖次数(累计)',
  `lottery_use_times` int(11) DEFAULT NULL COMMENT '已使用抽奖次数',
  `lottery_remain_times` int(11) DEFAULT NULL COMMENT '剩余可抽奖次数',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户签到汇总表';


CREATE TABLE `wx_spring_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '活动ID',
  `wx_act_lucky_wheel_id` varchar(50) NOT NULL COMMENT '转盘ID(关联转盘wx_act_lucky_wheel表)',
  `film_toal_num` int(11) NOT NULL COMMENT '拼图电影票总数量',
  `film_use_num` int(11) NOT NULL COMMENT '已使用拼图电影票',
  `film_remain_num` int(11) NOT NULL COMMENT '剩余拼图电影票',
  `red_packet_day_num` decimal(10,2) DEFAULT NULL COMMENT '每日签到红包金额',
  `red_packet_num` decimal(10,2) DEFAULT NULL COMMENT '签到红包总金额',
  `red_packet_remain_num` decimal(10,2) DEFAULT NULL COMMENT '签到红包剩余金额',
  `red_packet_max_amount` decimal(10,2) DEFAULT '0.60' COMMENT '签到红包最大金额',
  `red_packet_min_amount` decimal(10,2) DEFAULT '0.30' COMMENT '签到红包最小金额',
  `new_year_eve_red_packet_max_amount` decimal(10,2) DEFAULT '0.80' COMMENT '除夕当天签到红包最大金额',
  `new_year_eve_red_packet_min_amount` decimal(10,2) DEFAULT '0.60' COMMENT '除夕当天签到红包最小金额',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='春节活动表';



CREATE TABLE `wx_user_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '卡片ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID(关联wx_spring_activity)',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `business_type` tinyint(4) DEFAULT '0' COMMENT '业务类型(0-绑定手机号;1-装修我家;2-产品替换;3-邀请好友)',
  `card_number` int(11) DEFAULT '0' COMMENT '卡片标识(1-12代表随选网拜大年免费看贺岁片)',
  `card_date` date DEFAULT NULL COMMENT '卡片生成日期',
  `card_status` tinyint(4) DEFAULT NULL COMMENT '卡片状态(0-待领取;1-已领取)',
  `receive_time` timestamp NULL DEFAULT NULL COMMENT '领取时间',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户卡片表';


CREATE TABLE `wx_user_invite_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID(关联wx_spring_activity)',
  `open_id` varchar(50) NOT NULL COMMENT 'open_id',
  `nick_name` varchar(50) NOT NULL COMMENT '昵称',
  `head_pic` varchar(200) NOT NULL COMMENT '头像',
  `user_id` bigint(20) NOT NULL COMMENT '邀请用户ID',
  `invite_user_id` bigint(20) NOT NULL COMMENT '被邀请用户ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态(0-有效;1无效)',
  `card_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否获得拼图卡片(0-是;1-否)',
  `card_Id` bigint(20) NOT NULL COMMENT '卡片ID(关联wx_user_card)',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户邀请记录表';


CREATE TABLE `wx_user_signin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID(关联wx_spring_activity)',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `signin_date` date DEFAULT NULL COMMENT '签到日期(年-月-日)',
  `signin_day` int(11) DEFAULT NULL COMMENT '签到日期(精确到天)',
  `red_packet_num` decimal(10,2) DEFAULT NULL COMMENT '领取的红包金额',
  `receive_status` tinyint(4) DEFAULT '0' COMMENT '领取状态(0-待领取;1-已领取)',
  `receive_time` timestamp NULL DEFAULT NULL COMMENT '领取时间',
  `have_lottery_chance` tinyint(4) DEFAULT '0' COMMENT '是否具有抽奖机会(0-否;1-是)',
  `is_lottery_flag` tinyint(4) DEFAULT '0' COMMENT '有抽奖机会的时候，是否已经兑换抽奖机会(0-否;1-是)',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user` (`activity_id`,`user_id`,`signin_date`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户签到详情表';


CREATE TABLE `wx_user_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID(关联wx_spring_activity)',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `open_id` varchar(100) NOT NULL COMMENT 'open_id',
  `task_one_status` tinyint(4) DEFAULT '0' COMMENT '任务一状态(0-未领取;1-已领取待完成;2-已完成;3-未领取且不可领取)',
  `task_one_receive_time` timestamp NULL DEFAULT NULL COMMENT '任务一领取时间',
  `task_one_finish_time` timestamp NULL DEFAULT NULL COMMENT '任务一完成时间',
  `task_two_status` tinyint(4) DEFAULT '0' COMMENT '任务二状态(0-未领取;1-已领取待完成;2-已完成;3-未领取且不可领取)',
  `task_two_receive_time` timestamp NULL DEFAULT NULL COMMENT '任务二领取时间',
  `task_two_finish_time` timestamp NULL DEFAULT NULL COMMENT '任务二完成时间',
  `task_three_status` tinyint(4) DEFAULT '0' COMMENT '任务三状态(0-未领取;1-已领取待完成;2-已完成;3-未领取且不可领取)',
  `task_three_receive_time` timestamp NULL DEFAULT NULL COMMENT '任务三领取时间',
  `task_three_finish_time` timestamp NULL DEFAULT NULL COMMENT '任务三完成时间',
  `lottery_rate` int(11) DEFAULT '5' COMMENT '中奖概率(任务2/任务3完成后+1)',
  `is_auto_complete` tinyint(4) DEFAULT '0' COMMENT '是否自动完成标识(0-否;1-是)',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='春节活动表';





