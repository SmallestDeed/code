-- 新增用户佣金表
DROP TABLE IF EXISTS `user_commision`;
CREATE TABLE `user_commision` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_id` int(20) NOT NULL COMMENT '用户Id',
  `commision` DECIMAL(9,0) DEFAULT 0 COMMENT '佣金金额',
  `business_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '业务Id',
  `create_time` timestamp DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` timestamp  DEFAULT NULL COMMENT '修改时间',
  `creator` varchar(512) DEFAULT NULL COMMENT '创建者',
  `modifier` varchar(512) DEFAULT NULL COMMENT '修改人',
  `is_deleted` int(11) unsigned DEFAULT '0' COMMENT '是否删除（0:否，1:是）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;