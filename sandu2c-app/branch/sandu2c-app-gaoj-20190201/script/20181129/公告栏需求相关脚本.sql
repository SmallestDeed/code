USE `app_online_30`;
ALTER TABLE `auto_render_task` ADD COLUMN `supply_demand_id` INTEGER COMMENT "供求信息id" ;
ALTER TABLE `auto_render_task_state` ADD COLUMN `supply_demand_id` INTEGER COMMENT "供求信息id" ;
ALTER TABLE `full_house_design_plan` ADD COLUMN `is_update` INTEGER DEFAULT 0 COMMENT "复制的方案是否被修改(0:没有修改,1:已修改)" ;
ALTER TABLE `full_house_design_plan`
MODIFY COLUMN `source_type`  int(11) NOT NULL COMMENT '方案来源类型(1:内部制作,2:装进我家,3:交付,4:分享,5:复制)' AFTER `user_id`;
ALTER TABLE user_reviews ADD COLUMN  `supply_demand_publisher_id` int(11) DEFAULT NULL COMMENT '需求信息发布用户ID';
ALTER TABLE user_reviews ADD COLUMN  `fid` int(11) DEFAULT NULL COMMENT '评论的目标用户ID';
ALTER TABLE user_reviews ADD COLUMN  `pic_ids` varchar(200) DEFAULT NULL COMMENT '评论图片';
ALTER TABLE user_reviews ADD COLUMN  `plan_id` int(11) DEFAULT NULL COMMENT '评论方案ID';
ALTER TABLE user_reviews ADD COLUMN  `plan_type` int(11) DEFAULT NULL COMMENT '方案类型,1:单空间推荐方案.2:全屋单推荐方案,3:单空间我的设计方案,4:全屋我的设计方案';
ALTER TABLE user_reviews ADD COLUMN  `house_id` int(11) DEFAULT NULL COMMENT '评论户型ID';
ALTER TABLE user_reviews ADD COLUMN  `is_read` int(11) DEFAULT '0' COMMENT '0:未读,1:已读';
ALTER TABLE base_supply_demand  MODIFY COLUMN  `province` varchar(32) DEFAULT NULL COMMENT '供求的省份';
ALTER TABLE base_supply_demand  MODIFY COLUMN  `city` varchar(32) DEFAULT NULL COMMENT '供求的城市';
ALTER TABLE base_supply_demand  MODIFY COLUMN  `street` varchar(32) DEFAULT NULL COMMENT '街道';
ALTER TABLE base_supply_demand  MODIFY COLUMN  `district` varchar(32) DEFAULT NULL COMMENT '供求的地区';
ALTER TABLE base_supply_demand  MODIFY COLUMN  `cover_pic_id` varchar(200) DEFAULT NULL COMMENT '封面图';
ALTER TABLE base_supply_demand  MODIFY COLUMN  `description_pic_id` varchar(512) DEFAULT NULL COMMENT '描述图片';

/*==============================================================*/
/* Table: demand_info_rel*/
/*==============================================================*/
use app_online_30;
CREATE TABLE `demand_info_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `supply_demand_id` int(11) NOT NULL COMMENT '需求ID',
  `plan_type` int(11) DEFAULT NULL COMMENT '方案类型,1:单空间推荐方案.2:全屋单推荐方案,3:单空间我的设计方案,4:全屋我的设计方案',
  `plan_id` int(11) DEFAULT NULL COMMENT '方案ID',
  `house_id` int(11) DEFAULT NULL COMMENT '户型ID',
  `sys_code` varchar(32) DEFAULT NULL COMMENT '系统编码',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除（0:否，1:是）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=886 DEFAULT CHARSET=utf8 COMMENT='需求信息与户型方案关联表';

use app_online_30;
CREATE TABLE `design_plan_json_data` (
  `id` 									int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `json_data` 					varchar(3000) NOT NULL COMMENT 'json数据',
	`creator`             varchar(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `gmt_create`          timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier`            varchar(32) NOT NULL DEFAULT '' COMMENT '更新者',
  `gmt_modified`        timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间，自动更新',
  `is_deleted`          tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除字段,0:正常 1:已删除',
  `remark`              varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分享方案数据表';



/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/11/8 11:15:58                           */
/*==============================================================*/

use app_online_30;

/*==============================================================*/
/* Table: node_info                                             */
/*==============================================================*/
CREATE TABLE `node_info` (
  `id`                  int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid`                varchar(36) NOT NULL COMMENT '唯一标识',
  `content_id`          int(10) unsigned NOT NULL COMMENT '内容ID（方案ID、发布信息ID等）',
  `node_type`           tinyint(3) unsigned NOT NULL COMMENT '节点类型(数据字典type为nodeType的value)',
  `creator`             varchar(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `gmt_create`          timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier`            varchar(32) NOT NULL DEFAULT '' COMMENT '更新者',
  `gmt_modified`        timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间，自动更新',
  `is_deleted`          tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除字段,0:正常 1:已删除',
  `remark`              varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_content_id_node_type` (`content_id`,`node_type`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节点信息表';

/*==============================================================*/
/* Table: node_info_detail                                      */
/*==============================================================*/
CREATE TABLE `node_info_detail` (
  `id`                int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid`              varchar(36) NOT NULL COMMENT '唯一标识',
  `node_id`           int(10) unsigned NOT NULL COMMENT '节点ID（node_info表的ID）',
  `detail_type`       tinyint(3) unsigned NOT NULL COMMENT '类型(数据字典type为detailType的value)',
  `value`             int(10) unsigned NOT NULL DEFAULT '0' COMMENT '值',
  `creator`           varchar(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `gmt_create`        timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier`          varchar(32) NOT NULL DEFAULT '' COMMENT '更新者',
  `gmt_modified`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间，自动更新',
  `is_deleted`        tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除字段,0:正常 1:已删除',
  `remark`            varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_node_id_detail_type` (`node_id`,`detail_type`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节点信息详情表';

/*==============================================================*/
/* Table: user_node_info_rel                                    */
/*==============================================================*/
CREATE TABLE `user_node_info_rel` (
  `id`                   int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid`                 varchar(36) NOT NULL COMMENT '唯一标识',
  `user_id`              int(10) unsigned NOT NULL COMMENT '用户ID',
  `node_id`              int(10) unsigned NOT NULL COMMENT '节点ID（node_info表的ID）',
  `detail_type`          tinyint(3) unsigned NOT NULL COMMENT '类型(数据字典type为detailType的value)',
  `status`               tinyint(3) unsigned NOT NULL COMMENT '状态(0:无效,1:激活)',
  `creator`              varchar(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `gmt_create`           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier`             varchar(32) NOT NULL DEFAULT '' COMMENT '更新者',
  `gmt_modified`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间，自动更新',
  `is_deleted`           tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除字段,0:正常 1:已删除',
  `remark`               varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_if_node_id_detail_type` (`user_id`,`node_id`,`detail_type`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-节点关系表';
