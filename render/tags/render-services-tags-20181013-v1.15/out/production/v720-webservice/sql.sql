-- 用户表中增加状态(中文名称及解释存在数据字典中）
INSERT INTO `sys_dictionary` ( `sys_code`, `creator`, `gmt_create`, `modifier`
	, `gmt_modified`, `is_deleted`, `type`, `valuekey`, `value`
	, `name`, `ordering`)
VALUES (CONCAT(date_format('2008-08-08 22:23:01', '%Y%m%d%h%i%s'),"_", FLOOR((RAND() * 1000000))), 'ljx', NOW(), 'ljx'
	, NOW(), 0, 'deviceLimit', 'pc_limit_1', 1
	, '用户级别现在pc登录的个数为1', 2);

INSERT INTO `sys_dictionary` ( `sys_code`, `creator`, `gmt_create`, `modifier`
	, `gmt_modified`, `is_deleted`, `type`, `valuekey`, `value`
	, `name`, `ordering`)
VALUES (CONCAT(date_format('2008-08-08 22:23:01', '%Y%m%d%h%i%s'),"_", FLOOR((RAND() * 1000000))), 'ljx', NOW(), 'ljx'
	, NOW(), 0, 'deviceLimit', 'mobile_limit_1', 1
	, '用户级别-限制移动端数量为1', 1);


-- 用户等级与业务的关联表
DROP TABLE IF EXISTS  sys_user_level_config;
CREATE TABLE `sys_user_level_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'DB id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_level_id` bigint(20) NOT NULL COMMENT '用户级别（1级/2级）',
  `business_type_id` tinyint(4) DEFAULT '0' COMMENT '业务类型（1，设备数量 2 资源数量）常量也行',
  `business_id` int(11) DEFAULT '0' COMMENT '设备资源表id（数据字典）与sys_product_level_cfg表的主键',
  PRIMARY KEY (`id`),
  KEY `idx_user_level_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户级别配置表';

-- 数据字典表
-- 设备数量，key值=userDeviceNumConfigm,value=1,value1=1

-- 资费基础表
DROP TABLE IF EXISTS  sys_user_level_price;
CREATE TABLE `sys_user_level_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'DB id',
  `end_user_group_name` tinyint(4) NOT NULL COMMENT '终端用户群（店面/设计师等）',
  `user_pay_type` varchar(8) NOT NULL DEFAULT '' COMMENT '用户付费类型（0基础版/1升级版/2白金版）',
  `price` int(11) NOT NULL DEFAULT '0' COMMENT '收费标准,单位为分',
  `price_mode` int(11) NOT NULL DEFAULT '0' COMMENT '收费方式(0月、1季度、2年）',
  `remark` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='用户等级收费标准';

DROP TABLE IF EXISTS  sys_res_level_cfg;
CREATE TABLE `sys_res_level_cfg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'DB id',
  `res_big_type_id` int(11) NOT NULL DEFAULT '0' COMMENT '资源大类（0 硬装产品,1软装产品,2电器产品,3资源大类空间）',
  `res_small_type_id` int(11) NOT NULL DEFAULT '0' COMMENT '资源小类（客餐厅/卧室/厨房；表中资源，在数据字典中存在）',
  `res_num_mothod` varchar(8) NOT NULL DEFAULT '' COMMENT '1百分比/0数字',
  `res_num` varchar(8) NOT NULL DEFAULT '' COMMENT '值百分比整数，数字整数,100%写为1,20%写为0.2',
  `user_group_type` tinyint(3) unsigned DEFAULT '0' COMMENT '用户群体类型',
  `version` tinyint(3) unsigned DEFAULT '0' COMMENT '用户版本类型',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=703 DEFAULT CHARSET=utf8 COMMENT='系统资源级别配置表';



INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (1, 6, '0', 880, 2, '厂商基础版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (2, 6, '1', 1880, 2, '厂商升级版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (3, 6, '2', 2880, 2, '厂商白金版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (4, 1, '0', 3880, 2, '学校(培训机构)基础版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (5, 1, '1', 4880, 2, '学校(培训机构)升级版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (6, 1, '2', 880, 2, '学校(培训机构)白金版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (7, 2, '0', 1880, 2, '装修公司基础版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (8, 2, '1', 2880, 2, '装修公司升级版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (9, 2, '2', 880, 2, '装修公司白金版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (10, 3, '0', 1880, 2, '设计公司升级版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (11, 3, '2', 2880, 2, '设计公司白金版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (12, 4, '0', 880, 2, '设计师基础版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (13, 4, '1', 1880, 2, '设计师升级版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (14, 4, '2', 2880, 2, '设计师白金版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (15, 5, '0', 880, 2, '经销商基础版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (17, 5, '2', 2880, 2, '经销商白金版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (18, 0, '0', 880, 2, '装修用户基础版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (19, 0, '1', 1880, 2, '装修用户升级版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (20, 0, '2', 2880, 2, '装修用户白金版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (21, 8, '0', 880, 2, '店面代理商基础版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (22, 8, '1', 1880, 2, '店面代理商升级版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (23, 8, '2', 2880, 2, '店面代理商白金版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (24, 9, '0', 880, 2, '网店代理商基础版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (25, 9, '1', 1880, 2, '网店代理商升级版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (26, 9, '2', 2880, 2, '网店代理商白金版');
INSERT INTO `sys_user_level_price` (`id`, `end_user_group_name`, `user_pay_type`, `price`, `price_mode`, `remark`) VALUES (27, 10, '2', 9880, 2, '内部用户白金版');


INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (1, 0, 0, '0', '-1', 10, 2, '内部用户白金版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (2, 1, 0, '0', '-1', 10, 2, '内部用户白金版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (3, 2, 0, '0', '-1', 10, 2, '内部用户白金版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (4, 3, 0, '0', '-1', 10, 2, '内部用户白金版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (5, 4, 0, '0', '-1', 10, 2, '内部用户白金版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (6, 5, 0, '0', '-1', 10, 2, '内部用户白金版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (7, 6, 0, '0', '-1', 10, 2, '内部用户白金版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (8, 7, 0, '0', '-1', 10, 2, '内部用户白金版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (9, 8, 0, '0', '-1', 10, 2, '内部用户白金版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (10, 9, 0, '0', '-1', 10, 2, '内部用户白金版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (11, 10, 0, '0', '-1', 10, 2, '内部用户白金版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (12, 11, 0, '0', '-1', 10, 2, '内部用户白金版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (13, 12, 0, '0', '-1', 10, 2, '内部用户白金版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (14, 13, 0, '0', '-1', 10, 2, '内部用户白金版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (15, 14, 0, '0', '-1', 10, 2, '内部用户白金版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (16, 15, 0, '0', '-1', 10, 2, '内部用户白金版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (17, 16, 0, '0', '-1', 10, 2, '内部用户白金家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (18, 17, 0, '0', '-1', 10, 2, '内部用户白金版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (19, 18, 0, '0', '-1', 10, 2, '内部用户白金版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (20, 19, 0, '0', '-1', 10, 2, '内部用户白金版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (21, 20, 0, '0', '-1', 10, 2, '内部用户白金版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (22, 21, 0, '0', '-1', 10, 2, '内部用户白金版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (23, 22, 0, '0', '-1', 10, 2, '内部用户白金版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (24, 23, 0, '0', '-1', 10, 2, '内部用户白金版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (25, 24, 0, '0', '-1', 10, 2, '内部用户白金版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (26, 25, 0, '0', '-1', 10, 2, '内部用户白金版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (27, 0, 0, '0', '-1', 6, 2, '厂商白金版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (28, 1, 0, '0', '-1', 6, 2, '厂商白金版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (29, 2, 0, '0', '-1', 6, 2, '厂商白金版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (30, 3, 0, '0', '-1', 6, 2, '厂商白金版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (31, 4, 0, '0', '-1', 6, 2, '厂商白金版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (32, 5, 0, '0', '-1', 6, 2, '厂商白金版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (33, 6, 0, '0', '-1', 6, 2, '厂商白金版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (34, 7, 0, '0', '-1', 6, 2, '厂商白金版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (35, 8, 0, '0', '-1', 6, 2, '厂商白金版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (36, 9, 0, '0', '-1', 6, 2, '厂商白金版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (37, 10, 0, '0', '-1', 6, 2, '厂商白金版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (38, 11, 0, '0', '-1', 6, 2, '厂商白金版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (39, 12, 0, '0', '-1', 6, 2, '厂商白金版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (40, 13, 0, '0', '-1', 6, 2, '厂商白金版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (41, 14, 0, '0', '-1', 6, 2, '厂商白金版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (42, 15, 0, '0', '-1', 6, 2, '厂商白金版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (43, 16, 0, '0', '-1', 6, 2, '厂商白金家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (44, 17, 0, '0', '-1', 6, 2, '厂商白金版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (45, 18, 0, '0', '-1', 6, 2, '厂商白金版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (46, 19, 0, '0', '-1', 6, 2, '厂商白金版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (47, 20, 0, '0', '-1', 6, 2, '厂商白金版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (48, 21, 0, '0', '-1', 6, 2, '厂商白金版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (49, 22, 0, '0', '-1', 6, 2, '厂商白金版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (50, 23, 0, '0', '-1', 6, 2, '厂商白金版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (51, 24, 0, '0', '-1', 6, 2, '厂商白金版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (52, 25, 0, '0', '-1', 6, 2, '厂商白金版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (53, 0, 0, '0', '-1', 6, 1, '厂商升级版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (54, 1, 0, '0', '-1', 6, 1, '厂商升级版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (55, 2, 0, '0', '-1', 6, 1, '厂商升级版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (56, 3, 0, '0', '-1', 6, 1, '厂商升级版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (57, 4, 0, '0', '-1', 6, 1, '厂商升级版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (58, 5, 0, '0', '-1', 6, 1, '厂商升级版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (59, 6, 0, '0', '-1', 6, 1, '厂商升级版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (60, 7, 0, '0', '-1', 6, 1, '厂商升级版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (61, 8, 0, '0', '-1', 6, 1, '厂商升级版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (62, 9, 0, '0', '-1', 6, 1, '厂商升级版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (63, 10, 0, '0', '-1', 6, 1, '厂商升级版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (64, 11, 0, '0', '-1', 6, 1, '厂商升级版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (65, 12, 0, '0', '-1', 6, 1, '厂商升级版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (66, 13, 0, '0', '-1', 6, 1, '厂商升级版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (67, 14, 0, '0', '-1', 6, 1, '厂商升级版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (68, 15, 0, '0', '-1', 6, 1, '厂商升级版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (69, 16, 0, '0', '-1', 6, 1, '厂商升级家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (70, 17, 0, '0', '-1', 6, 1, '厂商升级版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (71, 18, 0, '0', '-1', 6, 1, '厂商升级版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (72, 19, 0, '0', '-1', 6, 1, '厂商升级版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (73, 20, 0, '0', '-1', 6, 1, '厂商升级版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (74, 21, 0, '0', '-1', 6, 1, '厂商升级版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (75, 22, 0, '0', '-1', 6, 1, '厂商升级版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (76, 23, 0, '0', '-1', 6, 1, '厂商升级版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (77, 24, 0, '0', '-1', 6, 1, '厂商升级版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (78, 25, 0, '0', '-1', 6, 1, '厂商升级版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (79, 0, 0, '0', '-1', 6, 0, '厂商基础版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (80, 1, 0, '0', '-1', 6, 0, '厂商基础版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (81, 2, 0, '0', '-1', 6, 0, '厂商基础版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (82, 3, 0, '0', '-1', 6, 0, '厂商基础版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (83, 4, 0, '0', '-1', 6, 0, '厂商基础版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (84, 5, 0, '0', '-1', 6, 0, '厂商基础版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (85, 6, 0, '0', '-1', 6, 0, '厂商基础版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (86, 7, 0, '0', '-1', 6, 0, '厂商基础版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (87, 8, 0, '0', '-1', 6, 0, '厂商基础版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (88, 9, 0, '0', '-1', 6, 0, '厂商基础版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (89, 10, 0, '0', '-1', 6, 0, '厂商基础版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (90, 11, 0, '0', '-1', 6, 0, '厂商基础版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (91, 12, 0, '0', '-1', 6, 0, '厂商基础版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (92, 13, 0, '0', '-1', 6, 0, '厂商基础版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (93, 14, 0, '0', '-1', 6, 0, '厂商基础版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (94, 15, 0, '0', '-1', 6, 0, '厂商基础版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (95, 16, 0, '0', '-1', 6, 0, '厂商基础家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (96, 17, 0, '0', '-1', 6, 0, '厂商基础版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (97, 18, 0, '0', '-1', 6, 0, '厂商基础版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (98, 19, 0, '0', '-1', 6, 0, '厂商基础版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (99, 20, 0, '0', '-1', 6, 0, '厂商基础版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (100, 21, 0, '0', '-1', 6, 0, '厂商基础版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (101, 22, 0, '0', '-1', 6, 0, '厂商基础版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (102, 23, 0, '0', '-1', 6, 0, '厂商基础版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (103, 24, 0, '0', '-1', 6, 0, '厂商基础版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (104, 25, 0, '0', '-1', 6, 0, '厂商基础版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (105, 0, 0, '0', '-1', 5, 2, '经销商白金版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (106, 1, 0, '0', '-1', 5, 2, '经销商白金版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (107, 2, 0, '0', '-1', 5, 2, '经销商白金版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (108, 3, 0, '0', '-1', 5, 2, '经销商白金版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (109, 4, 0, '0', '-1', 5, 2, '经销商白金版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (110, 5, 0, '0', '-1', 5, 2, '经销商白金版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (111, 6, 0, '0', '-1', 5, 2, '经销商白金版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (112, 7, 0, '0', '-1', 5, 2, '经销商白金版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (113, 8, 0, '0', '-1', 5, 2, '经销商白金版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (114, 9, 0, '0', '-1', 5, 2, '经销商白金版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (115, 10, 0, '0', '-1', 5, 2, '经销商白金版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (116, 11, 0, '0', '-1', 5, 2, '经销商白金版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (117, 12, 0, '0', '-1', 5, 2, '经销商白金版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (118, 13, 0, '0', '-1', 5, 2, '经销商白金版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (119, 14, 0, '0', '-1', 5, 2, '经销商白金版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (120, 15, 0, '0', '-1', 5, 2, '经销商白金版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (121, 16, 0, '0', '-1', 5, 2, '经销商白金家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (122, 17, 0, '0', '-1', 5, 2, '经销商白金版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (123, 18, 0, '0', '-1', 5, 2, '经销商白金版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (124, 19, 0, '0', '-1', 5, 2, '经销商白金版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (125, 20, 0, '0', '-1', 5, 2, '经销商白金版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (126, 21, 0, '0', '-1', 5, 2, '经销商白金版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (127, 22, 0, '0', '-1', 5, 2, '经销商白金版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (128, 23, 0, '0', '-1', 5, 2, '经销商白金版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (129, 24, 0, '0', '-1', 5, 2, '经销商白金版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (130, 25, 0, '0', '-1', 5, 2, '经销商白金版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (131, 0, 0, '0', '-1', 5, 0, '经销商基础版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (132, 1, 0, '0', '-1', 5, 0, '经销商基础版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (133, 2, 0, '0', '-1', 5, 0, '经销商基础版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (134, 3, 0, '0', '-1', 5, 0, '经销商基础版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (135, 4, 0, '0', '-1', 5, 0, '经销商基础版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (136, 5, 0, '0', '-1', 5, 0, '经销商基础版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (137, 6, 0, '0', '-1', 5, 0, '经销商基础版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (138, 7, 0, '0', '-1', 5, 0, '经销商基础版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (139, 8, 0, '0', '-1', 5, 0, '经销商基础版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (140, 9, 0, '0', '-1', 5, 0, '经销商基础版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (141, 10, 0, '0', '-1', 5, 0, '经销商基础版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (142, 11, 0, '0', '-1', 5, 0, '经销商基础版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (143, 12, 0, '0', '-1', 5, 0, '经销商基础版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (144, 13, 0, '0', '-1', 5, 0, '经销商基础版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (145, 14, 0, '0', '-1', 5, 0, '经销商基础版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (146, 15, 0, '0', '-1', 5, 0, '经销商基础版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (147, 16, 0, '0', '-1', 5, 0, '经销商基础家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (148, 17, 0, '0', '-1', 5, 0, '经销商基础版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (149, 18, 0, '0', '-1', 5, 0, '经销商基础版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (150, 19, 0, '0', '-1', 5, 0, '经销商基础版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (151, 20, 0, '0', '-1', 5, 0, '经销商基础版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (152, 21, 0, '0', '-1', 5, 0, '经销商基础版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (153, 22, 0, '0', '-1', 5, 0, '经销商基础版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (154, 23, 0, '0', '-1', 5, 0, '经销商基础版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (155, 24, 0, '0', '-1', 5, 0, '经销商基础版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (156, 25, 0, '0', '-1', 5, 0, '经销商基础版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (157, 0, 0, '0', '-1', 4, 2, '设计师白金版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (158, 1, 0, '0', '-1', 4, 2, '设计师白金版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (159, 2, 0, '0', '-1', 4, 2, '设计师白金版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (160, 3, 0, '0', '-1', 4, 2, '设计师白金版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (161, 4, 0, '0', '-1', 4, 2, '设计师白金版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (162, 5, 0, '0', '-1', 4, 2, '设计师白金版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (163, 6, 0, '0', '-1', 4, 2, '设计师白金版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (164, 7, 0, '0', '-1', 4, 2, '设计师白金版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (165, 8, 0, '0', '-1', 4, 2, '设计师白金版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (166, 9, 0, '0', '-1', 4, 2, '设计师白金版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (167, 10, 0, '0', '-1', 4, 2, '设计师白金版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (168, 11, 0, '0', '-1', 4, 2, '设计师白金版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (169, 12, 0, '0', '-1', 4, 2, '设计师白金版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (170, 13, 0, '0', '-1', 4, 2, '设计师白金版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (171, 14, 0, '0', '-1', 4, 2, '设计师白金版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (172, 15, 0, '0', '-1', 4, 2, '设计师白金版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (173, 16, 0, '0', '-1', 4, 2, '设计师白金家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (174, 17, 0, '0', '-1', 4, 2, '设计师白金版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (175, 18, 0, '0', '-1', 4, 2, '设计师白金版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (176, 19, 0, '0', '-1', 4, 2, '设计师白金版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (177, 20, 0, '0', '-1', 4, 2, '设计师白金版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (178, 21, 0, '0', '-1', 4, 2, '设计师白金版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (179, 22, 0, '0', '-1', 4, 2, '设计师白金版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (180, 23, 0, '0', '-1', 4, 2, '设计师白金版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (181, 24, 0, '0', '-1', 4, 2, '设计师白金版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (182, 25, 0, '0', '-1', 4, 2, '设计师白金版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (183, 0, 0, '0', '-1', 4, 1, '设计师升级版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (184, 1, 0, '0', '-1', 4, 1, '设计师升级版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (185, 2, 0, '0', '-1', 4, 1, '设计师升级版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (186, 3, 0, '0', '-1', 4, 1, '设计师升级版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (187, 4, 0, '0', '-1', 4, 1, '设计师升级版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (188, 5, 0, '0', '-1', 4, 1, '设计师升级版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (189, 6, 0, '0', '-1', 4, 1, '设计师升级版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (190, 7, 0, '0', '-1', 4, 1, '设计师升级版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (191, 8, 0, '0', '-1', 4, 1, '设计师升级版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (192, 9, 0, '0', '-1', 4, 1, '设计师升级版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (193, 10, 0, '0', '-1', 4, 1, '设计师升级版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (194, 11, 0, '0', '-1', 4, 1, '设计师升级版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (195, 12, 0, '0', '-1', 4, 1, '设计师升级版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (196, 13, 0, '0', '-1', 4, 1, '设计师升级版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (197, 14, 0, '0', '-1', 4, 1, '设计师升级版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (198, 15, 0, '0', '-1', 4, 1, '设计师升级版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (199, 16, 0, '0', '-1', 4, 1, '设计师升级家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (200, 17, 0, '0', '-1', 4, 1, '设计师升级版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (201, 18, 0, '0', '-1', 4, 1, '设计师升级版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (202, 19, 0, '0', '-1', 4, 1, '设计师升级版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (203, 20, 0, '0', '-1', 4, 1, '设计师升级版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (204, 21, 0, '0', '-1', 4, 1, '设计师升级版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (205, 22, 0, '0', '-1', 4, 1, '设计师升级版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (206, 23, 0, '0', '-1', 4, 1, '设计师升级版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (207, 24, 0, '0', '-1', 4, 1, '设计师升级版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (208, 25, 0, '0', '-1', 4, 1, '设计师升级版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (209, 0, 0, '0', '-1', 4, 0, '设计师基础版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (210, 1, 0, '0', '-1', 4, 0, '设计师基础版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (211, 2, 0, '0', '-1', 4, 0, '设计师基础版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (212, 3, 0, '0', '-1', 4, 0, '设计师基础版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (213, 4, 0, '0', '-1', 4, 0, '设计师基础版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (214, 5, 0, '0', '-1', 4, 0, '设计师基础版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (215, 6, 0, '0', '-1', 4, 0, '设计师基础版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (216, 7, 0, '0', '-1', 4, 0, '设计师基础版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (217, 8, 0, '0', '-1', 4, 0, '设计师基础版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (218, 9, 0, '0', '-1', 4, 0, '设计师基础版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (219, 10, 0, '0', '-1', 4, 0, '设计师基础版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (220, 11, 0, '0', '-1', 4, 0, '设计师基础版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (221, 12, 0, '0', '-1', 4, 0, '设计师基础版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (222, 13, 0, '0', '-1', 4, 0, '设计师基础版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (223, 14, 0, '0', '-1', 4, 0, '设计师基础版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (224, 15, 0, '0', '-1', 4, 0, '设计师基础版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (225, 16, 0, '0', '-1', 4, 0, '设计师基础家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (226, 17, 0, '0', '-1', 4, 0, '设计师基础版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (227, 18, 0, '0', '-1', 4, 0, '设计师基础版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (228, 19, 0, '0', '-1', 4, 0, '设计师基础版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (229, 20, 0, '0', '-1', 4, 0, '设计师基础版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (230, 21, 0, '0', '-1', 4, 0, '设计师基础版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (231, 22, 0, '0', '-1', 4, 0, '设计师基础版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (232, 23, 0, '0', '-1', 4, 0, '设计师基础版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (233, 24, 0, '0', '-1', 4, 0, '设计师基础版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (234, 25, 0, '0', '-1', 4, 0, '设计师基础版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (235, 0, 0, '0', '-1', 3, 2, '设计公司白金版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (236, 1, 0, '0', '-1', 3, 2, '设计公司白金版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (237, 2, 0, '0', '-1', 3, 2, '设计公司白金版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (238, 3, 0, '0', '-1', 3, 2, '设计公司白金版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (239, 4, 0, '0', '-1', 3, 2, '设计公司白金版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (240, 5, 0, '0', '-1', 3, 2, '设计公司白金版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (241, 6, 0, '0', '-1', 3, 2, '设计公司白金版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (242, 7, 0, '0', '-1', 3, 2, '设计公司白金版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (243, 8, 0, '0', '-1', 3, 2, '设计公司白金版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (244, 9, 0, '0', '-1', 3, 2, '设计公司白金版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (245, 10, 0, '0', '-1', 3, 2, '设计公司白金版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (246, 11, 0, '0', '-1', 3, 2, '设计公司白金版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (247, 12, 0, '0', '-1', 3, 2, '设计公司白金版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (248, 13, 0, '0', '-1', 3, 2, '设计公司白金版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (249, 14, 0, '0', '-1', 3, 2, '设计公司白金版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (250, 15, 0, '0', '-1', 3, 2, '设计公司白金版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (251, 16, 0, '0', '-1', 3, 2, '设计公司白金家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (252, 17, 0, '0', '-1', 3, 2, '设计公司白金版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (253, 18, 0, '0', '-1', 3, 2, '设计公司白金版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (254, 19, 0, '0', '-1', 3, 2, '设计公司白金版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (255, 20, 0, '0', '-1', 3, 2, '设计公司白金版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (256, 21, 0, '0', '-1', 3, 2, '设计公司白金版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (257, 22, 0, '0', '-1', 3, 2, '设计公司白金版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (258, 23, 0, '0', '-1', 3, 2, '设计公司白金版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (259, 24, 0, '0', '-1', 3, 2, '设计公司白金版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (260, 25, 0, '0', '-1', 3, 2, '设计公司白金版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (261, 0, 0, '0', '-1', 3, 1, '设计公司升级版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (262, 1, 0, '0', '-1', 3, 1, '设计公司升级版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (263, 2, 0, '0', '-1', 3, 1, '设计公司升级版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (264, 3, 0, '0', '-1', 3, 1, '设计公司升级版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (265, 4, 0, '0', '-1', 3, 1, '设计公司升级版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (266, 5, 0, '0', '-1', 3, 1, '设计公司升级版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (267, 6, 0, '0', '-1', 3, 1, '设计公司升级版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (268, 7, 0, '0', '-1', 3, 1, '设计公司升级版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (269, 8, 0, '0', '-1', 3, 1, '设计公司升级版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (270, 9, 0, '0', '-1', 3, 1, '设计公司升级版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (271, 10, 0, '0', '-1', 3, 1, '设计公司升级版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (272, 11, 0, '0', '-1', 3, 1, '设计公司升级版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (273, 12, 0, '0', '-1', 3, 1, '设计公司升级版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (274, 13, 0, '0', '-1', 3, 1, '设计公司升级版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (275, 14, 0, '0', '-1', 3, 1, '设计公司升级版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (276, 15, 0, '0', '-1', 3, 1, '设计公司升级版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (277, 16, 0, '0', '-1', 3, 1, '设计公司升级家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (278, 17, 0, '0', '-1', 3, 1, '设计公司升级版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (279, 18, 0, '0', '-1', 3, 1, '设计公司升级版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (280, 19, 0, '0', '-1', 3, 1, '设计公司升级版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (281, 20, 0, '0', '-1', 3, 1, '设计公司升级版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (282, 21, 0, '0', '-1', 3, 1, '设计公司升级版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (283, 22, 0, '0', '-1', 3, 1, '设计公司升级版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (284, 23, 0, '0', '-1', 3, 1, '设计公司升级版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (285, 24, 0, '0', '-1', 3, 1, '设计公司升级版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (286, 25, 0, '0', '-1', 3, 1, '设计公司升级版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (287, 0, 0, '0', '-1', 3, 0, '设计公司基础版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (288, 1, 0, '0', '-1', 3, 0, '设计公司基础版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (289, 2, 0, '0', '-1', 3, 0, '设计公司基础版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (290, 3, 0, '0', '-1', 3, 0, '设计公司基础版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (291, 4, 0, '0', '-1', 3, 0, '设计公司基础版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (292, 5, 0, '0', '-1', 3, 0, '设计公司基础版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (293, 6, 0, '0', '-1', 3, 0, '设计公司基础版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (294, 7, 0, '0', '-1', 3, 0, '设计公司基础版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (295, 8, 0, '0', '-1', 3, 0, '设计公司基础版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (296, 9, 0, '0', '-1', 3, 0, '设计公司基础版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (297, 10, 0, '0', '-1', 3, 0, '设计公司基础版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (298, 11, 0, '0', '-1', 3, 0, '设计公司基础版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (299, 12, 0, '0', '-1', 3, 0, '设计公司基础版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (300, 13, 0, '0', '-1', 3, 0, '设计公司基础版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (301, 14, 0, '0', '-1', 3, 0, '设计公司基础版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (302, 15, 0, '0', '-1', 3, 0, '设计公司基础版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (303, 16, 0, '0', '-1', 3, 0, '设计公司基础家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (304, 17, 0, '0', '-1', 3, 0, '设计公司基础版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (305, 18, 0, '0', '-1', 3, 0, '设计公司基础版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (306, 19, 0, '0', '-1', 3, 0, '设计公司基础版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (307, 20, 0, '0', '-1', 3, 0, '设计公司基础版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (308, 21, 0, '0', '-1', 3, 0, '设计公司基础版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (309, 22, 0, '0', '-1', 3, 0, '设计公司基础版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (310, 23, 0, '0', '-1', 3, 0, '设计公司基础版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (311, 24, 0, '0', '-1', 3, 0, '设计公司基础版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (312, 25, 0, '0', '-1', 3, 0, '设计公司基础版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (313, 0, 0, '0', '-1', 2, 2, '装修公司白金版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (314, 1, 0, '0', '-1', 2, 2, '装修公司白金版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (315, 2, 0, '0', '-1', 2, 2, '装修公司白金版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (316, 3, 0, '0', '-1', 2, 2, '装修公司白金版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (317, 4, 0, '0', '-1', 2, 2, '装修公司白金版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (318, 5, 0, '0', '-1', 2, 2, '装修公司白金版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (319, 6, 0, '0', '-1', 2, 2, '装修公司白金版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (320, 7, 0, '0', '-1', 2, 2, '装修公司白金版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (321, 8, 0, '0', '-1', 2, 2, '装修公司白金版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (322, 9, 0, '0', '-1', 2, 2, '装修公司白金版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (323, 10, 0, '0', '-1', 2, 2, '装修公司白金版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (324, 11, 0, '0', '-1', 2, 2, '装修公司白金版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (325, 12, 0, '0', '-1', 2, 2, '装修公司白金版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (326, 13, 0, '0', '-1', 2, 2, '装修公司白金版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (327, 14, 0, '0', '-1', 2, 2, '装修公司白金版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (328, 15, 0, '0', '-1', 2, 2, '装修公司白金版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (329, 16, 0, '0', '-1', 2, 2, '装修公司白金家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (330, 17, 0, '0', '-1', 2, 2, '装修公司白金版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (331, 18, 0, '0', '-1', 2, 2, '装修公司白金版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (332, 19, 0, '0', '-1', 2, 2, '装修公司白金版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (333, 20, 0, '0', '-1', 2, 2, '装修公司白金版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (334, 21, 0, '0', '-1', 2, 2, '装修公司白金版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (335, 22, 0, '0', '-1', 2, 2, '装修公司白金版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (336, 23, 0, '0', '-1', 2, 2, '装修公司白金版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (337, 24, 0, '0', '-1', 2, 2, '装修公司白金版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (338, 25, 0, '0', '-1', 2, 2, '装修公司白金版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (339, 0, 0, '0', '-1', 2, 1, '装修公司升级版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (340, 1, 0, '0', '-1', 2, 1, '装修公司升级版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (341, 2, 0, '0', '-1', 2, 1, '装修公司升级版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (342, 3, 0, '0', '-1', 2, 1, '装修公司升级版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (343, 4, 0, '0', '-1', 2, 1, '装修公司升级版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (344, 5, 0, '0', '-1', 2, 1, '装修公司升级版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (345, 6, 0, '0', '-1', 2, 1, '装修公司升级版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (346, 7, 0, '0', '-1', 2, 1, '装修公司升级版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (347, 8, 0, '0', '-1', 2, 1, '装修公司升级版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (348, 9, 0, '0', '-1', 2, 1, '装修公司升级版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (349, 10, 0, '0', '-1', 2, 1, '装修公司升级版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (350, 11, 0, '0', '-1', 2, 1, '装修公司升级版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (351, 12, 0, '0', '-1', 2, 1, '装修公司升级版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (352, 13, 0, '0', '-1', 2, 1, '装修公司升级版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (353, 14, 0, '0', '-1', 2, 1, '装修公司升级版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (354, 15, 0, '0', '-1', 2, 1, '装修公司升级版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (355, 16, 0, '0', '-1', 2, 1, '装修公司升级家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (356, 17, 0, '0', '-1', 2, 1, '装修公司升级版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (357, 18, 0, '0', '-1', 2, 1, '装修公司升级版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (358, 19, 0, '0', '-1', 2, 1, '装修公司升级版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (359, 20, 0, '0', '-1', 2, 1, '装修公司升级版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (360, 21, 0, '0', '-1', 2, 1, '装修公司升级版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (361, 22, 0, '0', '-1', 2, 1, '装修公司升级版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (362, 23, 0, '0', '-1', 2, 1, '装修公司升级版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (363, 24, 0, '0', '-1', 2, 1, '装修公司升级版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (364, 25, 0, '0', '-1', 2, 1, '装修公司升级版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (365, 0, 0, '0', '-1', 2, 0, '装修公司基础版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (366, 1, 0, '0', '-1', 2, 0, '装修公司基础版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (367, 2, 0, '0', '-1', 2, 0, '装修公司基础版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (368, 3, 0, '0', '-1', 2, 0, '装修公司基础版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (369, 4, 0, '0', '-1', 2, 0, '装修公司基础版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (370, 5, 0, '0', '-1', 2, 0, '装修公司基础版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (371, 6, 0, '0', '-1', 2, 0, '装修公司基础版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (372, 7, 0, '0', '-1', 2, 0, '装修公司基础版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (373, 8, 0, '0', '-1', 2, 0, '装修公司基础版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (374, 9, 0, '0', '-1', 2, 0, '装修公司基础版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (375, 10, 0, '0', '-1', 2, 0, '装修公司基础版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (376, 11, 0, '0', '-1', 2, 0, '装修公司基础版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (377, 12, 0, '0', '-1', 2, 0, '装修公司基础版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (378, 13, 0, '0', '-1', 2, 0, '装修公司基础版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (379, 14, 0, '0', '-1', 2, 0, '装修公司基础版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (380, 15, 0, '0', '-1', 2, 0, '装修公司基础版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (381, 16, 0, '0', '-1', 2, 0, '装修公司基础家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (382, 17, 0, '0', '-1', 2, 0, '装修公司基础版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (383, 18, 0, '0', '-1', 2, 0, '装修公司基础版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (384, 19, 0, '0', '-1', 2, 0, '装修公司基础版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (385, 20, 0, '0', '-1', 2, 0, '装修公司基础版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (386, 21, 0, '0', '-1', 2, 0, '装修公司基础版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (387, 22, 0, '0', '-1', 2, 0, '装修公司基础版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (388, 23, 0, '0', '-1', 2, 0, '装修公司基础版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (389, 24, 0, '0', '-1', 2, 0, '装修公司基础版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (390, 25, 0, '0', '-1', 2, 0, '装修公司基础版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (391, 0, 0, '0', '-1', 1, 2, '学校（培训机构）白金版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (392, 1, 0, '0', '-1', 1, 2, '学校（培训机构）白金版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (393, 2, 0, '0', '-1', 1, 2, '学校（培训机构）白金版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (394, 3, 0, '0', '-1', 1, 2, '学校（培训机构）白金版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (395, 4, 0, '0', '-1', 1, 2, '学校（培训机构）白金版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (396, 5, 0, '0', '-1', 1, 2, '学校（培训机构）白金版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (397, 6, 0, '0', '-1', 1, 2, '学校（培训机构）白金版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (398, 7, 0, '0', '-1', 1, 2, '学校（培训机构）白金版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (399, 8, 0, '0', '-1', 1, 2, '学校（培训机构）白金版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (400, 9, 0, '0', '-1', 1, 2, '学校（培训机构）白金版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (401, 10, 0, '0', '-1', 1, 2, '学校（培训机构）白金版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (402, 11, 0, '0', '-1', 1, 2, '学校（培训机构）白金版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (403, 12, 0, '0', '-1', 1, 2, '学校（培训机构）白金版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (404, 13, 0, '0', '-1', 1, 2, '学校（培训机构）白金版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (405, 14, 0, '0', '-1', 1, 2, '学校（培训机构）白金版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (406, 15, 0, '0', '-1', 1, 2, '学校（培训机构）白金版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (407, 16, 0, '0', '-1', 1, 2, '学校（培训机构）白金家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (408, 17, 0, '0', '-1', 1, 2, '学校（培训机构）白金版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (409, 18, 0, '0', '-1', 1, 2, '学校（培训机构）白金版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (410, 19, 0, '0', '-1', 1, 2, '学校（培训机构）白金版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (411, 20, 0, '0', '-1', 1, 2, '学校（培训机构）白金版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (412, 21, 0, '0', '-1', 1, 2, '学校（培训机构）白金版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (413, 22, 0, '0', '-1', 1, 2, '学校（培训机构）白金版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (414, 23, 0, '0', '-1', 1, 2, '学校（培训机构）白金版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (415, 24, 0, '0', '-1', 1, 2, '学校（培训机构）白金版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (416, 25, 0, '0', '-1', 1, 2, '学校（培训机构）白金版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (417, 0, 0, '0', '-1', 1, 1, '学校（培训机构）升级版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (418, 1, 0, '0', '-1', 1, 1, '学校（培训机构）升级版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (419, 2, 0, '0', '-1', 1, 1, '学校（培训机构）升级版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (420, 3, 0, '0', '-1', 1, 1, '学校（培训机构）升级版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (421, 4, 0, '0', '-1', 1, 1, '学校（培训机构）升级版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (422, 5, 0, '0', '-1', 1, 1, '学校（培训机构）升级版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (423, 6, 0, '0', '-1', 1, 1, '学校（培训机构）升级版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (424, 7, 0, '0', '-1', 1, 1, '学校（培训机构）升级版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (425, 8, 0, '0', '-1', 1, 1, '学校（培训机构）升级版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (426, 9, 0, '0', '-1', 1, 1, '学校（培训机构）升级版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (427, 10, 0, '0', '-1', 1, 1, '学校（培训机构）升级版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (428, 11, 0, '0', '-1', 1, 1, '学校（培训机构）升级版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (429, 12, 0, '0', '-1', 1, 1, '学校（培训机构）升级版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (430, 13, 0, '0', '-1', 1, 1, '学校（培训机构）升级版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (431, 14, 0, '0', '-1', 1, 1, '学校（培训机构）升级版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (432, 15, 0, '0', '-1', 1, 1, '学校（培训机构）升级版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (433, 16, 0, '0', '-1', 1, 1, '学校（培训机构）升级家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (434, 17, 0, '0', '-1', 1, 1, '学校（培训机构）升级版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (435, 18, 0, '0', '-1', 1, 1, '学校（培训机构）升级版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (436, 19, 0, '0', '-1', 1, 1, '学校（培训机构）升级版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (437, 20, 0, '0', '-1', 1, 1, '学校（培训机构）升级版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (438, 21, 0, '0', '-1', 1, 1, '学校（培训机构）升级版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (439, 22, 0, '0', '-1', 1, 1, '学校（培训机构）升级版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (440, 23, 0, '0', '-1', 1, 1, '学校（培训机构）升级版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (441, 24, 0, '0', '-1', 1, 1, '学校（培训机构）升级版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (442, 25, 0, '0', '-1', 1, 1, '学校（培训机构）升级版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (443, 0, 0, '0', '-1', 1, 0, '学校（培训机构）基础版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (444, 1, 0, '0', '-1', 1, 0, '学校（培训机构）基础版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (445, 2, 0, '0', '-1', 1, 0, '学校（培训机构）基础版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (446, 3, 0, '0', '-1', 1, 0, '学校（培训机构）基础版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (447, 4, 0, '0', '-1', 1, 0, '学校（培训机构）基础版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (448, 5, 0, '0', '-1', 1, 0, '学校（培训机构）基础版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (449, 6, 0, '0', '-1', 1, 0, '学校（培训机构）基础版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (450, 7, 0, '0', '-1', 1, 0, '学校（培训机构）基础版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (451, 8, 0, '0', '-1', 1, 0, '学校（培训机构）基础版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (452, 9, 0, '0', '-1', 1, 0, '学校（培训机构）基础版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (453, 10, 0, '0', '-1', 1, 0, '学校（培训机构）基础版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (454, 11, 0, '0', '-1', 1, 0, '学校（培训机构）基础版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (455, 12, 0, '0', '-1', 1, 0, '学校（培训机构）基础版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (456, 13, 0, '0', '-1', 1, 0, '学校（培训机构）基础版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (457, 14, 0, '0', '-1', 1, 0, '学校（培训机构）基础版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (458, 15, 0, '0', '-1', 1, 0, '学校（培训机构）基础版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (459, 16, 0, '0', '-1', 1, 0, '学校（培训机构）基础家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (460, 17, 0, '0', '-1', 1, 0, '学校（培训机构）基础版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (461, 18, 0, '0', '-1', 1, 0, '学校（培训机构）基础版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (462, 19, 0, '0', '-1', 1, 0, '学校（培训机构）基础版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (463, 20, 0, '0', '-1', 1, 0, '学校（培训机构）基础版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (464, 21, 0, '0', '-1', 1, 0, '学校（培训机构）基础版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (465, 22, 0, '0', '-1', 1, 0, '学校（培训机构）基础版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (466, 23, 0, '0', '-1', 1, 0, '学校（培训机构）基础版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (467, 24, 0, '0', '-1', 1, 0, '学校（培训机构）基础版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (468, 25, 0, '0', '-1', 1, 0, '学校（培训机构）基础版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (469, 0, 0, '0', '-1', 0, 2, '装修用户白金版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (470, 1, 0, '0', '-1', 0, 2, '装修用户白金版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (471, 2, 0, '0', '-1', 0, 2, '装修用户白金版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (472, 3, 0, '0', '-1', 0, 2, '装修用户白金版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (473, 4, 0, '0', '-1', 0, 2, '装修用户白金版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (474, 5, 0, '0', '-1', 0, 2, '装修用户白金版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (475, 6, 0, '0', '-1', 0, 2, '装修用户白金版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (476, 7, 0, '0', '-1', 0, 2, '装修用户白金版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (477, 8, 0, '0', '-1', 0, 2, '装修用户白金版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (478, 9, 0, '0', '-1', 0, 2, '装修用户白金版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (479, 10, 0, '0', '-1', 0, 2, '装修用户白金版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (480, 11, 0, '0', '-1', 0, 2, '装修用户白金版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (481, 12, 0, '0', '-1', 0, 2, '装修用户白金版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (482, 13, 0, '0', '-1', 0, 2, '装修用户白金版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (483, 14, 0, '0', '-1', 0, 2, '装修用户白金版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (484, 15, 0, '0', '-1', 0, 2, '装修用户白金版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (485, 16, 0, '0', '-1', 0, 2, '装修用户白金家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (486, 17, 0, '0', '-1', 0, 2, '装修用户白金版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (487, 18, 0, '0', '-1', 0, 2, '装修用户白金版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (488, 19, 0, '0', '-1', 0, 2, '装修用户白金版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (489, 20, 0, '0', '-1', 0, 2, '装修用户白金版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (490, 21, 0, '0', '-1', 0, 2, '装修用户白金版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (491, 22, 0, '0', '-1', 0, 2, '装修用户白金版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (492, 23, 0, '0', '-1', 0, 2, '装修用户白金版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (493, 24, 0, '0', '-1', 0, 2, '装修用户白金版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (494, 25, 0, '0', '-1', 0, 2, '装修用户白金版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (495, 0, 0, '0', '-1', 0, 1, '装修用户升级版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (496, 1, 0, '0', '-1', 0, 1, '装修用户升级版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (497, 2, 0, '0', '-1', 0, 1, '装修用户升级版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (498, 3, 0, '0', '-1', 0, 1, '装修用户升级版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (499, 4, 0, '0', '-1', 0, 1, '装修用户升级版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (500, 5, 0, '0', '-1', 0, 1, '装修用户升级版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (501, 6, 0, '0', '-1', 0, 1, '装修用户升级版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (502, 7, 0, '0', '-1', 0, 1, '装修用户升级版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (503, 8, 0, '0', '-1', 0, 1, '装修用户升级版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (504, 9, 0, '0', '-1', 0, 1, '装修用户升级版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (505, 10, 0, '0', '-1', 0, 1, '装修用户升级版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (506, 11, 0, '0', '-1', 0, 1, '装修用户升级版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (507, 12, 0, '0', '-1', 0, 1, '装修用户升级版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (508, 13, 0, '0', '-1', 0, 1, '装修用户升级版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (509, 14, 0, '0', '-1', 0, 1, '装修用户升级版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (510, 15, 0, '0', '-1', 0, 1, '装修用户升级版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (511, 16, 0, '0', '-1', 0, 1, '装修用户升级家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (512, 17, 0, '0', '-1', 0, 1, '装修用户升级版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (513, 18, 0, '0', '-1', 0, 1, '装修用户升级版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (514, 19, 0, '0', '-1', 0, 1, '装修用户升级版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (515, 20, 0, '0', '-1', 0, 1, '装修用户升级版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (516, 21, 0, '0', '-1', 0, 1, '装修用户升级版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (517, 22, 0, '0', '-1', 0, 1, '装修用户升级版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (518, 23, 0, '0', '-1', 0, 1, '装修用户升级版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (519, 24, 0, '0', '-1', 0, 1, '装修用户升级版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (520, 25, 0, '0', '-1', 0, 1, '装修用户升级版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (521, 0, 0, '0', '-1', 0, 0, '装修用户基础版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (522, 1, 0, '0', '-1', 0, 0, '装修用户基础版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (523, 2, 0, '0', '-1', 0, 0, '装修用户基础版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (524, 3, 0, '0', '-1', 0, 0, '装修用户基础版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (525, 4, 0, '0', '-1', 0, 0, '装修用户基础版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (526, 5, 0, '0', '-1', 0, 0, '装修用户基础版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (527, 6, 0, '0', '-1', 0, 0, '装修用户基础版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (528, 7, 0, '0', '-1', 0, 0, '装修用户基础版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (529, 8, 0, '0', '-1', 0, 0, '装修用户基础版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (530, 9, 0, '0', '-1', 0, 0, '装修用户基础版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (531, 10, 0, '0', '-1', 0, 0, '装修用户基础版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (532, 11, 0, '0', '-1', 0, 0, '装修用户基础版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (533, 12, 0, '0', '-1', 0, 0, '装修用户基础版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (534, 13, 0, '0', '-1', 0, 0, '装修用户基础版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (535, 14, 0, '0', '-1', 0, 0, '装修用户基础版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (536, 15, 0, '0', '-1', 0, 0, '装修用户基础版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (537, 16, 0, '0', '-1', 0, 0, '装修用户基础家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (538, 17, 0, '0', '-1', 0, 0, '装修用户基础版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (539, 18, 0, '0', '-1', 0, 0, '装修用户基础版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (540, 19, 0, '0', '-1', 0, 0, '装修用户基础版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (541, 20, 0, '0', '-1', 0, 0, '装修用户基础版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (542, 21, 0, '0', '-1', 0, 0, '装修用户基础版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (543, 22, 0, '0', '-1', 0, 0, '装修用户基础版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (544, 23, 0, '0', '-1', 0, 0, '装修用户基础版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (545, 24, 0, '0', '-1', 0, 0, '装修用户基础版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (546, 25, 0, '0', '-1', 0, 0, '装修用户基础版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (547, 0, 0, '0', '-1', 8, 2, '店面代理商白金版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (548, 1, 0, '0', '-1', 8, 2, '店面代理商白金版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (549, 2, 0, '0', '-1', 8, 2, '店面代理商白金版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (550, 3, 0, '0', '-1', 8, 2, '店面代理商白金版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (551, 4, 0, '0', '-1', 8, 2, '店面代理商白金版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (552, 5, 0, '0', '-1', 8, 2, '店面代理商白金版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (553, 6, 0, '0', '-1', 8, 2, '店面代理商白金版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (554, 7, 0, '0', '-1', 8, 2, '店面代理商白金版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (555, 8, 0, '0', '-1', 8, 2, '店面代理商白金版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (556, 9, 0, '0', '-1', 8, 2, '店面代理商白金版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (557, 10, 0, '0', '-1', 8, 2, '店面代理商白金版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (558, 11, 0, '0', '-1', 8, 2, '店面代理商白金版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (559, 12, 0, '0', '-1', 8, 2, '店面代理商白金版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (560, 13, 0, '0', '-1', 8, 2, '店面代理商白金版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (561, 14, 0, '0', '-1', 8, 2, '店面代理商白金版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (562, 15, 0, '0', '-1', 8, 2, '店面代理商白金版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (563, 16, 0, '0', '-1', 8, 2, '店面代理商白金家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (564, 17, 0, '0', '-1', 8, 2, '店面代理商白金版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (565, 18, 0, '0', '-1', 8, 2, '店面代理商白金版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (566, 19, 0, '0', '-1', 8, 2, '店面代理商白金版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (567, 20, 0, '0', '-1', 8, 2, '店面代理商白金版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (568, 21, 0, '0', '-1', 8, 2, '店面代理商白金版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (569, 22, 0, '0', '-1', 8, 2, '店面代理商白金版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (570, 23, 0, '0', '-1', 8, 2, '店面代理商白金版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (571, 24, 0, '0', '-1', 8, 2, '店面代理商白金版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (572, 25, 0, '0', '-1', 8, 2, '店面代理商白金版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (573, 0, 0, '0', '-1', 8, 1, '店面代理商升级版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (574, 1, 0, '0', '-1', 8, 1, '店面代理商升级版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (575, 2, 0, '0', '-1', 8, 1, '店面代理商升级版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (576, 3, 0, '0', '-1', 8, 1, '店面代理商升级版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (577, 4, 0, '0', '-1', 8, 1, '店面代理商升级版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (578, 5, 0, '0', '-1', 8, 1, '店面代理商升级版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (579, 6, 0, '0', '-1', 8, 1, '店面代理商升级版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (580, 7, 0, '0', '-1', 8, 1, '店面代理商升级版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (581, 8, 0, '0', '-1', 8, 1, '店面代理商升级版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (582, 9, 0, '0', '-1', 8, 1, '店面代理商升级版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (583, 10, 0, '0', '-1', 8, 1, '店面代理商升级版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (584, 11, 0, '0', '-1', 8, 1, '店面代理商升级版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (585, 12, 0, '0', '-1', 8, 1, '店面代理商升级版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (586, 13, 0, '0', '-1', 8, 1, '店面代理商升级版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (587, 14, 0, '0', '-1', 8, 1, '店面代理商升级版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (588, 15, 0, '0', '-1', 8, 1, '店面代理商升级版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (589, 16, 0, '0', '-1', 8, 1, '店面代理商升级家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (590, 17, 0, '0', '-1', 8, 1, '店面代理商升级版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (591, 18, 0, '0', '-1', 8, 1, '店面代理商升级版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (592, 19, 0, '0', '-1', 8, 1, '店面代理商升级版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (593, 20, 0, '0', '-1', 8, 1, '店面代理商升级版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (594, 21, 0, '0', '-1', 8, 1, '店面代理商升级版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (595, 22, 0, '0', '-1', 8, 1, '店面代理商升级版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (596, 23, 0, '0', '-1', 8, 1, '店面代理商升级版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (597, 24, 0, '0', '-1', 8, 1, '店面代理商升级版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (598, 25, 0, '0', '-1', 8, 1, '店面代理商升级版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (599, 0, 0, '0', '-1', 8, 0, '店面代理商基础版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (600, 1, 0, '0', '-1', 8, 0, '店面代理商基础版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (601, 2, 0, '0', '-1', 8, 0, '店面代理商基础版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (602, 3, 0, '0', '-1', 8, 0, '店面代理商基础版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (603, 4, 0, '0', '-1', 8, 0, '店面代理商基础版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (604, 5, 0, '0', '-1', 8, 0, '店面代理商基础版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (605, 6, 0, '0', '-1', 8, 0, '店面代理商基础版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (606, 7, 0, '0', '-1', 8, 0, '店面代理商基础版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (607, 8, 0, '0', '-1', 8, 0, '店面代理商基础版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (608, 9, 0, '0', '-1', 8, 0, '店面代理商基础版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (609, 10, 0, '0', '-1', 8, 0, '店面代理商基础版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (610, 11, 0, '0', '-1', 8, 0, '店面代理商基础版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (611, 12, 0, '0', '-1', 8, 0, '店面代理商基础版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (612, 13, 0, '0', '-1', 8, 0, '店面代理商基础版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (613, 14, 0, '0', '-1', 8, 0, '店面代理商基础版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (614, 15, 0, '0', '-1', 8, 0, '店面代理商基础版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (615, 16, 0, '0', '-1', 8, 0, '店面代理商基础家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (616, 17, 0, '0', '-1', 8, 0, '店面代理商基础版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (617, 18, 0, '0', '-1', 8, 0, '店面代理商基础版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (618, 19, 0, '0', '-1', 8, 0, '店面代理商基础版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (619, 20, 0, '0', '-1', 8, 0, '店面代理商基础版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (620, 21, 0, '0', '-1', 8, 0, '店面代理商基础版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (621, 22, 0, '0', '-1', 8, 0, '店面代理商基础版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (622, 23, 0, '0', '-1', 8, 0, '店面代理商基础版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (623, 24, 0, '0', '-1', 8, 0, '店面代理商基础版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (624, 25, 0, '0', '-1', 8, 0, '店面代理商基础版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (625, 0, 0, '0', '-1', 9, 2, '网店代理商白金版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (626, 1, 0, '0', '-1', 9, 2, '网店代理商白金版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (627, 2, 0, '0', '-1', 9, 2, '网店代理商白金版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (628, 3, 0, '0', '-1', 9, 2, '网店代理商白金版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (629, 4, 0, '0', '-1', 9, 2, '网店代理商白金版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (630, 5, 0, '0', '-1', 9, 2, '网店代理商白金版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (631, 6, 0, '0', '-1', 9, 2, '网店代理商白金版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (632, 7, 0, '0', '-1', 9, 2, '网店代理商白金版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (633, 8, 0, '0', '-1', 9, 2, '网店代理商白金版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (634, 9, 0, '0', '-1', 9, 2, '网店代理商白金版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (635, 10, 0, '0', '-1', 9, 2, '网店代理商白金版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (636, 11, 0, '0', '-1', 9, 2, '网店代理商白金版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (637, 12, 0, '0', '-1', 9, 2, '网店代理商白金版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (638, 13, 0, '0', '-1', 9, 2, '网店代理商白金版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (639, 14, 0, '0', '-1', 9, 2, '网店代理商白金版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (640, 15, 0, '0', '-1', 9, 2, '网店代理商白金版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (641, 16, 0, '0', '-1', 9, 2, '网店代理商白金家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (642, 17, 0, '0', '-1', 9, 2, '网店代理商白金版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (643, 18, 0, '0', '-1', 9, 2, '网店代理商白金版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (644, 19, 0, '0', '-1', 9, 2, '网店代理商白金版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (645, 20, 0, '0', '-1', 9, 2, '网店代理商白金版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (646, 21, 0, '0', '-1', 9, 2, '网店代理商白金版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (647, 22, 0, '0', '-1', 9, 2, '网店代理商白金版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (648, 23, 0, '0', '-1', 9, 2, '网店代理商白金版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (649, 24, 0, '0', '-1', 9, 2, '网店代理商白金版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (650, 25, 0, '0', '-1', 9, 2, '网店代理商白金版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (651, 0, 0, '0', '-1', 9, 1, '网店代理商升级版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (652, 1, 0, '0', '-1', 9, 1, '网店代理商升级版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (653, 2, 0, '0', '-1', 9, 1, '网店代理商升级版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (654, 3, 0, '0', '-1', 9, 1, '网店代理商升级版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (655, 4, 0, '0', '-1', 9, 1, '网店代理商升级版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (656, 5, 0, '0', '-1', 9, 1, '网店代理商升级版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (657, 6, 0, '0', '-1', 9, 1, '网店代理商升级版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (658, 7, 0, '0', '-1', 9, 1, '网店代理商升级版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (659, 8, 0, '0', '-1', 9, 1, '网店代理商升级版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (660, 9, 0, '0', '-1', 9, 1, '网店代理商升级版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (661, 10, 0, '0', '-1', 9, 1, '网店代理商升级版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (662, 11, 0, '0', '-1', 9, 1, '网店代理商升级版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (663, 12, 0, '0', '-1', 9, 1, '网店代理商升级版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (664, 13, 0, '0', '-1', 9, 1, '网店代理商升级版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (665, 14, 0, '0', '-1', 9, 1, '网店代理商升级版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (666, 15, 0, '0', '-1', 9, 1, '网店代理商升级版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (667, 16, 0, '0', '-1', 9, 1, '网店代理商升级家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (668, 17, 0, '0', '-1', 9, 1, '网店代理商升级版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (669, 18, 0, '0', '-1', 9, 1, '网店代理商升级版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (670, 19, 0, '0', '-1', 9, 1, '网店代理商升级版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (671, 20, 0, '0', '-1', 9, 1, '网店代理商升级版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (672, 21, 0, '0', '-1', 9, 1, '网店代理商升级版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (673, 22, 0, '0', '-1', 9, 1, '网店代理商升级版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (674, 23, 0, '0', '-1', 9, 1, '网店代理商升级版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (675, 24, 0, '0', '-1', 9, 1, '网店代理商升级版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (676, 25, 0, '0', '-1', 9, 1, '网店代理商升级版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (677, 0, 0, '0', '-1', 9, 0, '网店代理商基础版空间数量');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (678, 1, 0, '0', '-1', 9, 0, '网店代理商基础版天花');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (679, 2, 0, '0', '-1', 9, 0, '网店代理商基础版地面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (680, 3, 0, '0', '-1', 9, 0, '网店代理商基础版墙面');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (681, 4, 0, '0', '-1', 9, 0, '网店代理商基础版门');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (682, 5, 0, '0', '-1', 9, 0, '网店代理商基础版沙发');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (683, 6, 0, '0', '-1', 9, 0, '网店代理商基础版桌子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (684, 7, 0, '0', '-1', 9, 0, '网店代理商基础版椅子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (685, 8, 0, '0', '-1', 9, 0, '网店代理商基础版几类');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (686, 9, 0, '0', '-1', 9, 0, '网店代理商基础版床架');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (687, 10, 0, '0', '-1', 9, 0, '网店代理商基础版柜子');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (688, 11, 0, '0', '-1', 9, 0, '网店代理商基础版灯具');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (689, 12, 0, '0', '-1', 9, 0, '网店代理商基础版电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (690, 13, 0, '0', '-1', 9, 0, '网店代理商基础版卫浴');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (691, 14, 0, '0', '-1', 9, 0, '网店代理商基础版厨房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (692, 15, 0, '0', '-1', 9, 0, '网店代理商基础版饰品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (693, 16, 0, '0', '-1', 9, 0, '网店代理商基础家纺');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (694, 17, 0, '0', '-1', 9, 0, '网店代理商基础版小家电');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (695, 18, 0, '0', '-1', 9, 0, '网店代理商基础版摆件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (696, 19, 0, '0', '-1', 9, 0, '网店代理商基础版样板房');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (697, 20, 0, '0', '-1', 9, 0, '网店代理商基础版床垫');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (698, 21, 0, '0', '-1', 9, 0, '网店代理商基础版床品');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (699, 22, 0, '0', '-1', 9, 0, '网店代理商基础版浴室配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (700, 23, 0, '0', '-1', 9, 0, '网店代理商基础版厨房配件');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (701, 24, 0, '0', '-1', 9, 0, '网店代理商基础版厨房电器');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (702, 25, 0, '0', '-1', 9, 0, '网店代理商基础版楼梯');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (703, 26, 0, '0', '-1', 10, 2, '内部用户白金版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (704, 26, 0, '0', '-1', 6, 2, '厂商白金版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (705, 26, 0, '0', '-1', 6, 1, '厂商升级版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (706, 26, 0, '0', '-1', 6, 0, '厂商基础版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (707, 26, 0, '0', '-1', 5, 2, '经销商白金版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (708, 26, 0, '0', '-1', 5, 0, '经销商基础版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (709, 26, 0, '0', '-1', 4, 2, '设计师白金版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (710, 26, 0, '0', '-1', 4, 1, '设计师升级版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (711, 26, 0, '0', '-1', 4, 0, '设计师基础版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (712, 26, 0, '0', '-1', 3, 2, '设计公司白金版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (713, 26, 0, '0', '-1', 3, 1, '设计公司升级版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (714, 26, 0, '0', '-1', 3, 0, '设计公司基础版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (715, 26, 0, '0', '-1', 2, 2, '装修公司白金版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (716, 26, 0, '0', '-1', 2, 1, '装修公司升级版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (717, 26, 0, '0', '-1', 2, 0, '装修公司基础版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (718, 26, 0, '0', '-1', 1, 2, '学校（培训机构）白金版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (719, 26, 0, '0', '-1', 1, 1, '学校（培训机构）升级版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (720, 26, 0, '0', '-1', 1, 0, '学校（培训机构）基础版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (721, 26, 0, '0', '-1', 0, 2, '装修用户白金版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (722, 26, 0, '0', '-1', 0, 1, '装修用户升级版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (723, 26, 0, '0', '-1', 0, 0, '装修用户基础版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (724, 26, 0, '0', '-1', 8, 2, '店面代理商白金版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (725, 26, 0, '0', '-1', 8, 1, '店面代理商升级版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (726, 26, 0, '0', '-1', 8, 0, '店面代理商基础版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (727, 26, 0, '0', '-1', 9, 2, '网店代理商白金版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (728, 26, 0, '0', '-1', 9, 1, '网店代理商升级版隔断屏风');
INSERT INTO `sys_res_level_cfg` (`id`, `res_big_type_id`, `res_small_type_id`, `res_num_mothod`, `res_num`, `user_group_type`, `version`, `remark`) VALUES (729, 26, 0, '0', '-1', 9, 0, '网店代理商基础版隔断屏风');



-- 修复历史用户数据
DROP PROCEDURE IF EXISTS `user_level_init`;

CREATE PROCEDURE `user_level_init`()
BEGIN
#Routine body goes here...
    declare stop_flag int DEFAULT 0; -- 声明一个标记，当游标状态为最后一条记录时，修改该变量  
    declare userId int default 0;  
    declare userType int default 0; 
	declare user_Level int default 1;   -- 默认用户级别
	declare res_id int default 0;
	declare mobile_login_limit_id int default 0;-- 用户移动端设备登录数量限制
	declare pc_login_limit_id int default 0;-- 用户pc设备登录数量限制


	declare cur1 cursor for SELECT id userId,user_type userType FROM sys_user;
	declare CONTINUE HANDLER FOR SQLSTATE '02000' SET stop_flag=1;    -- 在 FETCH 语句中引用的游标位置处于结果表最后一行之后


	SELECT id into mobile_login_limit_id FROM sys_dictionary WHERE  type="deviceLimit" AND valuekey="mobile_limit_1"  limit 1;
	SELECT id into pc_login_limit_id FROM sys_dictionary WHERE  type="deviceLimit" AND valuekey="pc_limit_1" limit 1;

	open cur1;-- 打开游标  

		while stop_flag<>1 DO-- 若游标有下一条记录，循环 
			fetch cur1 into userId,userType; 
			 
			if(userType=1) then  --  内部用户
					SET  res_id=0;
			elseif(userType=3) then  -- 注册用户（B2B）
					SET  res_id=130;
			ELSE 
				SET  res_id=-1;
			END IF;
					
			IF(res_id=0 OR res_id=130) THEN
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id) values(userId,user_Level,1,mobile_login_limit_id);-- 插入允许设备数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id) values(userId,user_Level,1,pc_login_limit_id);-- 插入允许设备数,1代表设备数，2代表资源数

				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+1)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+2)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+3)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+4)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+5)); -- 插入允许资源数,1代表设备数，2代表资源数

		     insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+6)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+7)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+8)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+9)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+10)); -- 插入允许资源数,1代表设备数，2代表资源数

		     insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+11)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+12)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+13)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+14)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+15)); -- 插入允许资源数,1代表设备数，2代表资源数

		     	 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+16)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+17)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+18)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+19)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+20)); -- 插入允许资源数,1代表设备数，2代表资源数

		     	 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+21)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+22)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+23)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+24)); -- 插入允许资源数,1代表设备数，2代表资源数
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+25)); -- 插入允许资源数,1代表设备数，2代表资源数

		     insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,(res_id+26)); -- 插入允许资源数,1代表设备数，2代表资源数
				
			IF(res_id=0) THEN
					insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,703); -- 插入允许资源数,1代表设备数，2代表资源数
			END IF;

			IF(res_id=130) THEN
				insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id)  values(userId,user_Level,2,708); -- 插入允许资源数,1代表设备数，2代表资源数
			END IF ;



	END IF;

		end while;  

	close cur1;-- 关闭游标 


END


ALTER TABLE sys_res_level_cfg ADD COLUMN res_big_type_num VARCHAR(16) DEFAULT "" COMMENT"产品大类在数据字典中点编号"; 

UPDATE sys_res_level_cfg SET res_big_type_num ="tianh" WHERE res_big_type_id=1	;
UPDATE sys_res_level_cfg SET res_big_type_num ="qiangm" WHERE res_big_type_id=3	;
UPDATE sys_res_level_cfg SET res_big_type_num ="dim" WHERE res_big_type_id=2		;
UPDATE sys_res_level_cfg SET res_big_type_num ="ki" WHERE res_big_type_id=14		;
UPDATE sys_res_level_cfg SET res_big_type_num ="ba" WHERE res_big_type_id=13		;
UPDATE sys_res_level_cfg SET res_big_type_num ="li" WHERE res_big_type_id=11		;
UPDATE sys_res_level_cfg SET res_big_type_num ="pe" WHERE res_big_type_id=15		;
UPDATE sys_res_level_cfg SET res_big_type_num ="el" WHERE res_big_type_id=12		;
UPDATE sys_res_level_cfg SET res_big_type_num ="sf" WHERE res_big_type_id=5		;
UPDATE sys_res_level_cfg SET res_big_type_num ="bd" WHERE res_big_type_id=9		;
UPDATE sys_res_level_cfg SET res_big_type_num ="de" WHERE res_big_type_id=6		;
UPDATE sys_res_level_cfg SET res_big_type_num ="ch" WHERE res_big_type_id=7		;
UPDATE sys_res_level_cfg SET res_big_type_num ="ca" WHERE res_big_type_id=10		;
UPDATE sys_res_level_cfg SET res_big_type_num ="ta" WHERE res_big_type_id=8		;
UPDATE sys_res_level_cfg SET res_big_type_num ="ho" WHERE res_big_type_id=16		;
UPDATE sys_res_level_cfg SET res_big_type_num ="meng" WHERE res_big_type_id=4		;
UPDATE sys_res_level_cfg SET res_big_type_num ="se" WHERE res_big_type_id=17		;
UPDATE sys_res_level_cfg SET res_big_type_num ="bk" WHERE res_big_type_id=18		;
UPDATE sys_res_level_cfg SET res_big_type_num ="space" WHERE res_big_type_id=0	;
UPDATE sys_res_level_cfg SET res_big_type_num ="template" WHERE res_big_type_id=19;
UPDATE sys_res_level_cfg SET res_big_type_num ="bd2" WHERE res_big_type_id=20		;
UPDATE sys_res_level_cfg SET res_big_type_num ="bd3" WHERE res_big_type_id=21		;
UPDATE sys_res_level_cfg SET res_big_type_num ="bp" WHERE res_big_type_id=22		;
UPDATE sys_res_level_cfg SET res_big_type_num ="kp" WHERE res_big_type_id=23		;
UPDATE sys_res_level_cfg SET res_big_type_num ="kiel" WHERE res_big_type_id=24	;
UPDATE sys_res_level_cfg SET res_big_type_num ="louti" WHERE res_big_type_id=25	;
UPDATE sys_res_level_cfg SET res_big_type_num ="pf" WHERE res_big_type_id=26		;




--用户注册   开始
drop table IF EXISTS sys_user_register_info;
create table sys_user_register_info
(
id bigint(20) not null AUTO_INCREMENT
,bid varchar(32) comment '注册信息业务id'
,user_type TINYINT  comment '用户类型'
,name varchar(100) comment '个人（企业）名称'
,user_id bigint(20) comment '用户id'
,sys_code varchar(32) comment '系统编码'
,creator varchar(32) comment '创建者'
,gmt_create timestamp comment '创建时间'
,modifier varchar(32) comment '修改人'
,gmt_modified timestamp comment '修改时间'
,is_deleted TINYINT comment '是否删除'
,att1 varchar(32) comment '字符备用1'
,att2 varchar(32) comment '字符备用2'
,numa1 int comment '整数备用1'
,numa2 int comment '整数备用2'
,remark varchar(4000) comment '备注'
,primary key (id) ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户注册信息';

##时间：2017-08-17
##提交人：yanghuanzhi
##说明：添加字段 sys_user_register_info 用户审核状态
alter table sys_user_register_info  add COLUMN audit_state TINYINT NOT NULL DEFAULT 0 COMMENT '用户审核状态0：未通过,1：通过';

--初始化用户等级数据
drop procedure if exists sp_user_level_config_init;
create procedure sp_user_level_config_init()
BEGIN
#Routine body goes here...
    declare stop_flag int DEFAULT 0; -- 声明一个标记，当游标状态为最后一条记录时，修改该变量

	declare userId int default 0;
	declare userType int default 0;
	declare user_Level int default 1;   -- 默认用户级别
	declare business_id int default 0;
	declare level_price_id int default 0;
	declare i int default 0;



	declare cur1 cursor for SELECT id userId FROM sys_user where  user_type=3;
	SELECT id INTO level_price_id from sys_user_level_price where end_user_group_name=0 and user_pay_type=0;
	SELECT COUNT(id) into i FROM sys_user where  user_type=3;

	open cur1;-- 打开游标
		while i>0 DO-- 若游标有下一条记录，循环
			fetch cur1 into userId;
				 insert into sys_user_level_config(user_id,user_level_id,business_type_id,business_id) values(userId,user_Level,3,level_price_id);
					set i = i-1;
		end while;

	close cur1;-- 关闭游标
END

--用户类型数据字典
INSERT INTO `sys_dictionary` (`sys_code`, `creator`, `gmt_create`, `modifier`, `gmt_modified`, `is_deleted`, `type`, `valuekey`, `value`, `name`, `ordering`, `att1`, `att2`, `att3`, `att4`, `att5`, `att6`, `date_att1`, `date_att2`, `num_att1`, `pic_id`, `num_att3`, `num_att4`, `remark`, `att1_info`, `att2_info`, `att3_info`, `att4_info`, `att5_info`, `att6_info`, `att7`, `att7_info`, `show_u3d_model`) VALUES ('20170807194207410_504795', 'yanghz', '2017-08-07 19:42:07', 'yanghz', '2017-08-07 19:42:07', '0', 'userRegisterType', 'userType_common', '0', '装修用户', '7', NULL, NULL, '', NULL, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dictionary` (`sys_code`, `creator`, `gmt_create`, `modifier`, `gmt_modified`, `is_deleted`, `type`, `valuekey`, `value`, `name`, `ordering`, `att1`, `att2`, `att3`, `att4`, `att5`, `att6`, `date_att1`, `date_att2`, `num_att1`, `pic_id`, `num_att3`, `num_att4`, `remark`, `att1_info`, `att2_info`, `att3_info`, `att4_info`, `att5_info`, `att6_info`, `att7`, `att7_info`, `show_u3d_model`) VALUES ('20170807194137908_684207', 'yanghz', '2017-08-07 19:41:38', 'yanghz', '2017-08-07 19:42:07', '0', 'userRegisterType', 'userType_school', '1', '学校(培训机构)', '6', NULL, NULL, '', NULL, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dictionary` (`sys_code`, `creator`, `gmt_create`, `modifier`, `gmt_modified`, `is_deleted`, `type`, `valuekey`, `value`, `name`, `ordering`, `att1`, `att2`, `att3`, `att4`, `att5`, `att6`, `date_att1`, `date_att2`, `num_att1`, `pic_id`, `num_att3`, `num_att4`, `remark`, `att1_info`, `att2_info`, `att3_info`, `att4_info`, `att5_info`, `att6_info`, `att7`, `att7_info`, `show_u3d_model`) VALUES ('20170807194032698_414966', 'yanghz', '2017-08-07 19:40:33', 'yanghz', '2017-08-07 19:42:07', '0', 'userRegisterType', 'userType_decorate ', '2', '装修公司', '5', NULL, NULL, '', NULL, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dictionary` (`sys_code`, `creator`, `gmt_create`, `modifier`, `gmt_modified`, `is_deleted`, `type`, `valuekey`, `value`, `name`, `ordering`, `att1`, `att2`, `att3`, `att4`, `att5`, `att6`, `date_att1`, `date_att2`, `num_att1`, `pic_id`, `num_att3`, `num_att4`, `remark`, `att1_info`, `att2_info`, `att3_info`, `att4_info`, `att5_info`, `att6_info`, `att7`, `att7_info`, `show_u3d_model`) VALUES ('20170807194000152_826759', 'yanghz', '2017-08-07 19:40:00', 'yanghz', '2017-08-07 19:42:07', '0', 'userRegisterType', 'userType_designer_company', '3', '设计公司', '4', NULL, NULL, '', NULL, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dictionary` (`sys_code`, `creator`, `gmt_create`, `modifier`, `gmt_modified`, `is_deleted`, `type`, `valuekey`, `value`, `name`, `ordering`, `att1`, `att2`, `att3`, `att4`, `att5`, `att6`, `date_att1`, `date_att2`, `num_att1`, `pic_id`, `num_att3`, `num_att4`, `remark`, `att1_info`, `att2_info`, `att3_info`, `att4_info`, `att5_info`, `att6_info`, `att7`, `att7_info`, `show_u3d_model`) VALUES ('20170807193921069_280891', 'yanghz', '2017-08-07 19:39:21', 'yanghz', '2017-08-07 19:42:07', '0', 'userRegisterType', 'userType_designer', '4', '设计师', '3', NULL, NULL, '', NULL, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dictionary` (`sys_code`, `creator`, `gmt_create`, `modifier`, `gmt_modified`, `is_deleted`, `type`, `valuekey`, `value`, `name`, `ordering`, `att1`, `att2`, `att3`, `att4`, `att5`, `att6`, `date_att1`, `date_att2`, `num_att1`, `pic_id`, `num_att3`, `num_att4`, `remark`, `att1_info`, `att2_info`, `att3_info`, `att4_info`, `att5_info`, `att6_info`, `att7`, `att7_info`, `show_u3d_model`) VALUES ('20170807193854907_163999', 'yanghz', '2017-08-07 19:38:55', 'yanghz', '2017-08-07 19:42:07', '0', 'userRegisterType', 'userType_dealers', '5', '经销商', '2', NULL, NULL, '', NULL, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dictionary` (`sys_code`, `creator`, `gmt_create`, `modifier`, `gmt_modified`, `is_deleted`, `type`, `valuekey`, `value`, `name`, `ordering`, `att1`, `att2`, `att3`, `att4`, `att5`, `att6`, `date_att1`, `date_att2`, `num_att1`, `pic_id`, `num_att3`, `num_att4`, `remark`, `att1_info`, `att2_info`, `att3_info`, `att4_info`, `att5_info`, `att6_info`, `att7`, `att7_info`, `show_u3d_model`) VALUES ('20170807193757793_452097', 'yanghz', '2017-08-07 19:37:58', 'yanghz', '2017-08-07 19:42:07', '0', 'userRegisterType', 'userType_company', '6', '厂商', '1', NULL, NULL, '', NULL, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_dictionary` (`sys_code`, `creator`, `gmt_create`, `modifier`, `gmt_modified`, `is_deleted`, `type`, `valuekey`, `value`, `name`, `ordering`, `att1`, `att2`, `att3`, `att4`, `att5`, `att6`, `date_att1`, `date_att2`, `num_att1`, `pic_id`, `num_att3`, `num_att4`, `remark`, `att1_info`, `att2_info`, `att3_info`, `att4_info`, `att5_info`, `att6_info`, `att7`, `att7_info`, `show_u3d_model`) VALUES ('20170807193300753_362466', 'yanghz', '2017-08-07 19:33:01', 'yanghz', '2017-08-07 19:33:01', '0', 'direction', 'userRegisterType', '121', '用户注册类型', '129', NULL, NULL, '', NULL, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

--用户注册   结束