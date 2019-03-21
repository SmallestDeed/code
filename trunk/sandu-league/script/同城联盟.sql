-- 新增供求信息表
DROP TABLE IF EXISTS `base_supply_demand`;
CREATE TABLE `base_supply_demand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '信息类别(1:供应，2:需求)',
  `creator_id` int(11) unsigned NOT NULL COMMENT '创建者id',
  `creator_type_value` int(11) unsigned NOT NULL COMMENT '创建者类型(数据字典的用户类型)',
  `category` int(11) NOT NULL COMMENT '信息分类',
  `province` varchar(32) NULL COMMENT '供求的省份',
  `city` varchar(32) NULL COMMENT '供求的城市',
  `district` varchar(32) NULL COMMENT '供求的地区',
  `address` varchar(32) NULL COMMENT '供求的详细地址',
  `cover_pic_id` int(11) NULL COMMENT '封面图',
  `title` varchar(256) NOT NULL COMMENT '信息标题',
  `description` varchar(2000) NOT NULL COMMENT '信息描述',
  `description_pic_id` varchar(512) NULL COMMENT '描述图片',
  `decoration_company` int(11) unsigned DEFAULT '0' COMMENT '装修公司是否可见(0:否，1:是)',
  `designer` int(11) unsigned DEFAULT '0' COMMENT '设计师是否可见(0:否，1:是)',
  `material_shop` int(11) unsigned DEFAULT '0' COMMENT '建材门店是否可见(0:否，1:是)',
  `proprietor` int(11) unsigned DEFAULT '0' COMMENT '业主是否可见(0:否，1:是)',
  `builder` int(11) unsigned DEFAULT '0' COMMENT '施工单位是否可见(0:否，1:是)',
  `push_status` int(11) unsigned DEFAULT '0' COMMENT '信息状态(0:上架中,1:已下架)',
  `business_status` int(11) unsigned DEFAULT '0' COMMENT '信息状态(0:待报价,1:待签约,2:待支付,3:已完成)',
  `gmt_publish` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `sys_code` varchar(32) DEFAULT NULL COMMENT '系统编码',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` int(11) unsigned DEFAULT '0' COMMENT '是否删除（0:否，1:是）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供求信息表';

-- 新增供求分类表
DROP TABLE IF EXISTS `supply_demand_category`;
CREATE TABLE `supply_demand_category` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
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
  `is_deleted` int(11) unsigned DEFAULT '0' COMMENT '是否删除（0:否，1:是）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供求分类表';


-- 新增供求信息图片表
DROP TABLE IF EXISTS `supply_demand_pic`;
CREATE TABLE `supply_demand_pic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `is_deleted` int(11) unsigned DEFAULT '0' COMMENT '是否删除（0:否，1:是）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供求图片资源表';
