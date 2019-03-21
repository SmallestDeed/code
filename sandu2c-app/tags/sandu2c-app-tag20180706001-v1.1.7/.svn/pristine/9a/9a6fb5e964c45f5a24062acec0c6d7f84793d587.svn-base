-- 新增供求信息表
DROP TABLE IF EXISTS `base_supply_demand`;
CREATE TABLE `base_supply_demand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11)  NOT NULL DEFAULT '1' COMMENT '信息类别(1:供应，2:需求)',
  `creator_id` int(11)  NOT NULL COMMENT '创建者id',
  `creator_type_value` int(11)  NOT NULL COMMENT '创建者类型()',
  `supply_demand_category_id` varchar(32) NOT NULL COMMENT '信息分类',
  `province` varchar(32) NOT NULL COMMENT '供求的省份',
  `city` varchar(32) NOT NULL COMMENT '供求的城市',
  `district` varchar(32) NOT NULL COMMENT '供求的地区',
  `street` varchar(32) NOT NULL COMMENT '街道',
  `address` varchar(100) NOT NULL COMMENT '供求的详细地址',
  `cover_pic_id` varchar(200) NOT NULL COMMENT '封面图',
  `title` varchar(100) NOT NULL COMMENT '信息标题',
  `description` varchar(4000) NOT NULL COMMENT '信息描述',
  `description_pic_id` varchar(512) DEFAULT NULL COMMENT '描述图片',
  `decoration_company` int(11)  NOT NULL DEFAULT '0' COMMENT '装修公司是否可见(0:否，1:是)',
  `designer` int(11)  NOT NULL DEFAULT '0' COMMENT '设计师是否可见(0:否，1:是)',
  `material_shop` int(11)  NOT NULL DEFAULT '0' COMMENT '建材门店是否可见(0:否，1:是)',
  `proprietor` int(11)  NOT NULL DEFAULT '0' COMMENT '业主是否可见(0:否，1:是)',
  `builder` int(11)  NOT NULL DEFAULT '0' COMMENT '施工单位是否可见(0:否，1:是)',
  `push_status` int(11)  NOT NULL DEFAULT '0' COMMENT '信息状态(0:上架中,1:已下架)',
  `business_status` int(11)  DEFAULT '0' COMMENT '信息状态(0:待报价,1:待签约,2:待支付,3:已完成)',
  `gmt_publish` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `sys_code` varchar(32) DEFAULT NULL COMMENT '系统编码',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` int(11)  DEFAULT '0' COMMENT '是否删除（0:否，1:是）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `view_num` int(11) DEFAULT '0' COMMENT '浏览次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='供求信息表';

-- 新增供求分类表
DROP TABLE IF EXISTS `supply_demand_category`;
CREATE TABLE `supply_demand_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL COMMENT '父级',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `is_leaf` int(11) DEFAULT NULL COMMENT '是否是子节点',
  `level` int(11) DEFAULT NULL COMMENT '等级',
  `ordering` int(11) DEFAULT NULL COMMENT '排序',
  `pic_id` int(11) DEFAULT NULL COMMENT '图片id',
  `sys_code` varchar(32) DEFAULT NULL COMMENT '系统编码',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` int(11)  DEFAULT '0' COMMENT '是否删除（0:否，1:是）',
   `tree_type` int(11) DEFAULT '0' COMMENT '类型：1供求信息2同城联盟',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='供求分类表';


-- 新增供求信息图片表
DROP TABLE IF EXISTS `supply_demand_pic`;
CREATE TABLE `supply_demand_pic` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pic_name` varchar(96) DEFAULT NULL COMMENT '图片名称',
  `pic_size` int(11) DEFAULT NULL COMMENT '图片大小',
  `pic_weight` varchar(16) DEFAULT NULL COMMENT '图片长',
  `pic_high` varchar(16) DEFAULT NULL COMMENT '图片高',
  `pic_format` varchar(32) DEFAULT NULL COMMENT '图片格式',
  `pic_path` varchar(512) DEFAULT NULL COMMENT '图片路径',
  `sys_code` varchar(32) DEFAULT NULL COMMENT '系统编码',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` int(11)  DEFAULT '0' COMMENT '是否删除（0:否，1:是）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='供求图片资源表';



-- 新增用户评论表
DROP TABLE IF EXISTS `user_reviews`;
CREATE TABLE `user_reviews` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `business_id` int(11) NOT NULL COMMENT '业务ID',
  `reviews_msg` varchar(512) DEFAULT NULL COMMENT '评论详情',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_deleted` int(11)  DEFAULT '0' COMMENT '是否删除（0:否，1:是）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='用户评论表';

DROP TABLE IF EXISTS `user_reply`;
CREATE TABLE `user_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reviews_id` int(11) NOT NULL COMMENT '评论id',
  `business_id` int(11) NOT NULL COMMENT '业务ID',
  `reply_type` varchar(32) NOT NULL COMMENT '回复类型comment/reply',
  `reply_reviews_id` int(11) NOT NULL COMMENT '回复评论ID',
  `reply_msg` varchar(512) DEFAULT NULL COMMENT '回复详情',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_deleted` int(11)  DEFAULT '0' COMMENT '是否删除（0:否，1:是）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='用户回复表';



-- 新增用户私信表
DROP TABLE IF EXISTS `user_private_message`;
CREATE TABLE `user_private_message` (
  `id` int(11) NOT NULL auto_increment COMMENT '主键Id',
  `user_id` int(11) NOT NULL COMMENT '发送者Id',
  `friend_id` int(11) NOT NULL COMMENT '接受者Id',
  `sender_id` int(11) NOT NULL COMMENT '发送者id',
  `receiver_id` int(11) NOT NULL COMMENT '接受者Id',
  `message_type` tinyint NOT NULL COMMENT '消息类型,1：普通消息 2：系统消息',
  `message_content` varchar(4000) NOT NULL COMMENT '消息内容',
  `send_time` timestamp NOT NULL COMMENT '消息发送时间',
  `status` tinyint NOT NULL default '1' COMMENT '消息状态 1：未读 2：已读 3：删除',
  `is_deleted` tinyint  DEFAULT '0' COMMENT '是否删除（0:否，1:是）',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB   COMMENT='用户私信表' ;




-- 新增Banner位置表
DROP TABLE IF EXISTS `base_banner_position`;
CREATE TABLE `base_banner_position`  (
  `id` bigint  NOT NULL AUTO_INCREMENT,
  `code` varchar(512)  NOT NULL COMMENT '位置编码:system:module:page:positon)',
  `name` varchar(512)  NOT NULL COMMENT 'banner位置名称',
  `remark` varchar(1024)  NOT NULL COMMENT '备注',
  `type` int(2) NOT NULL COMMENT '在哪使用此banner(1:平台--选装网,同城联盟;2:企业--企业小程序;3.店铺)',
  `sys_code` varchar(512)  NULL DEFAULT NULL COMMENT '系统编码',
  `creator` varchar(512)  NOT NULL COMMENT '创建者',
  `gmt_create` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(512)  NOT NULL COMMENT '修改人',
  `gmt_modified` timestamp  NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = 'banner位置';

-- 新增Banner管理表
DROP TABLE IF EXISTS `base_banner_ad`;
CREATE TABLE `base_banner`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `position_id` bigint NOT NULL COMMENT '所属位置',
  `name` varchar(512)  NOT NULL COMMENT 'banner名称',
  `res_banner_pic_id` bigint NOT NULL COMMENT '资源id',
  `ref_model_id` bigint NOT NULL COMMENT '如果位置类型为店铺,则此处为店铺id, 如果为企业,则为企业id,如果为平台,则为平台id',
  `status` tinyint NOT NULL COMMENT '状态(0:关闭;1:开启)',
  `sequence` tinyint NULL DEFAULT NULL COMMENT '排序',
  `sys_code` varchar(512)  NULL DEFAULT NULL COMMENT '系统编码',
  `creator` varchar(512)  NOT NULL COMMENT '创建者',
  `gmt_create` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(512)  NOT NULL COMMENT '修改人',
  `gmt_modified` timestamp  NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = 'banner管理';

-- 新增banner图片资源库表
DROP TABLE IF EXISTS `res_banner_pic`;
CREATE TABLE `res_banner_pic`  (
  `id` bigint  NOT NULL AUTO_INCREMENT,
  `pic_code` varchar(96)  NULL DEFAULT NULL COMMENT '图片编码',
  `pic_name` varchar(96)  NULL DEFAULT NULL COMMENT '图片名称',
  `pic_file_name` varchar(96)  NULL DEFAULT NULL COMMENT '图片文件名称',
  `pic_type` varchar(32)  NULL DEFAULT NULL COMMENT '图片类型',
  `pic_size` int(11) NULL DEFAULT NULL COMMENT '图片大小',
  `pic_weight` varchar(16)  NULL DEFAULT NULL COMMENT '图片长',
  `pic_high` varchar(16)  NULL DEFAULT NULL COMMENT '图片高',
  `pic_suffix` varchar(16)  NULL DEFAULT NULL COMMENT '图片后缀',
  `pic_level` varchar(16)  NULL DEFAULT NULL COMMENT '图片级别',
  `pic_format` varchar(32)  NULL DEFAULT NULL COMMENT '图片格式',
  `pic_path` varchar(512)  NULL DEFAULT NULL,
  `pic_desc` varchar(512)  NULL DEFAULT NULL COMMENT '图片描述',
  `sys_code` varchar(120)  NULL DEFAULT NULL,
  `creator` varchar(32)  NULL DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(32)  NULL DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp(0) NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint NULL DEFAULT NULL COMMENT '是否删除',
  `remark` varchar(4000)  NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB   COMMENT = 'banner图片资源库';




-- 新增我的邀请
DROP TABLE IF EXISTS `user_invite`;
CREATE TABLE `user_invite` (
  `id` int(11) NOT NULL auto_increment COMMENT '主键Id',
  `invite_id` int(11) NOT NULL COMMENT '邀请者Id',
  `fid` int(11) DEFAULT NULL COMMENT '被邀请人Id',
  `share_sign` varchar(500) NOT NULL COMMENT '分享程序标识',
  `share_type` int(11) DEFAULT '0' COMMENT '1:微信朋友圈,2:微信好友,3:QQ好友,4:QQ空间,5:微博',
  `status` int(11) DEFAULT '0' COMMENT '邀请状态0:未注册,1:已注册',
  `invite_time` timestamp NOT NULL COMMENT '邀请时间',
  `register_time` timestamp DEFAULT NULL COMMENT '注册时间',
  `is_deleted` int(11)  DEFAULT '0' COMMENT '是否删除（0:否，1:是）',
   `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  COMMENT = '我的邀请';
