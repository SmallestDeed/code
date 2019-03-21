CREATE TABLE `design_plan_recommended_superior` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_code` varchar(512) DEFAULT NULL COMMENT '方案编码',
  `plan_name` varchar(512) DEFAULT NULL COMMENT '方案名称',
	`design_plan_recommended_id` int(11) DEFAULT NULL COMMENT '方案ID',
	`ordering` int(11) DEFAULT NULL COMMENT '排序',
  `space_type` int(11) DEFAULT NULL COMMENT '空间类型',
  `sys_code` varchar(128) DEFAULT NULL COMMENT '系统编码',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `is_deleted` int(11) DEFAULT NULL COMMENT '是否删除',
  `remark` varchar(4000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=149262 DEFAULT CHARSET=utf8 COMMENT='精选推荐方案表';


-- 清空原数据
truncate design_plan_recommended_superior;
-- 插入推荐方案
insert into 
	design_plan_recommended_superior(plan_code,plan_name,design_plan_recommended_id,ordering,space_type,sys_code,creator,gmt_create,modifier,gmt_modified,is_deleted,remark)
(select
	t1.plan_code plan_code,
	t1.plan_name plan_name,
	t1.id design_plan_recommended_id,
	0 ordering,
	t2.space_function_id space_type,
	t1.sys_code sys_code,
	'system' creator,
	CURRENT_TIMESTAMP gmt_create,
	'system' modifier,
	CURRENT_TIMESTAMP gmt_modified,
	0 is_deleted,
	'' remark
from
	design_plan_recommended t1
	left join space_common t2 on t2.id = t1.space_common_id
where
	t1.id in (
		?,
		?,
		?,
		?,
		?
	) 
	and t1.is_deleted = 0
);
-- 插入全屋方案
insert into 
	design_plan_recommended_superior(plan_code,plan_name,design_plan_recommended_id,ordering,space_type,sys_code,creator,gmt_create,modifier,gmt_modified,is_deleted,remark)
(select 
	t3.plan_code plan_code,
	t3.plan_name plan_name,
	t3.id design_plan_recommended_id,
	0 ordering,
	13 space_type,
	t3.uuid sys_code,
	'system' creator,
	CURRENT_TIMESTAMP gmt_create,
	'system' modifier,
	CURRENT_TIMESTAMP gmt_modified,
	0 is_deleted,
	'' remark
from
	full_house_design_plan t3
where
	t3.id in (
		?,
		?,
		?
	)
);