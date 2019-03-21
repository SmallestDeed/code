-- 新增全屋户型坐标信息与渲染任务关联表
DROP TABLE IF EXISTS `base_house_pic_full_house_plan_rel`;
CREATE TABLE `base_house_pic_full_house_plan_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `house_guide_pic_info_id` bigint(20) NOT NULL COMMENT '全屋户型文件ID',
  `pic_id` bigint(20) NOT NULL,
  `house_id` bigint(20) DEFAULT NULL,
  `space_type` smallint(6) DEFAULT NULL COMMENT '空间类型',
  `space_common_id` bigint(20) DEFAULT NULL,
  `design_templet_id` bigint(20) DEFAULT NULL,
  `full_house_plan_id` bigint(20) DEFAULT NULL,
  `main_task_id` bigint(20) DEFAULT NULL,
  `state` bigint(20) NOT NULL DEFAULT '2' COMMENT '空间状态，0：任务失败，1，任务成功，2,任务渲染中',
  `task_id` bigint(20) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `x_coordinate` varchar(64) DEFAULT NULL,
  `y_coordinate` varchar(64) DEFAULT NULL,
  `coordinate_info` varchar(32) DEFAULT NULL,
  `is_deleted` smallint(6) NOT NULL DEFAULT '0',
  `creator` varchar(64) NOT NULL DEFAULT 'housedraw' COMMENT '创建者',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(64) NOT NULL DEFAULT 'housedraw' COMMENT '修改人',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_pic_id` (`pic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1185 DEFAULT CHARSET=utf8 COMMENT='全屋户型坐标信息与渲染任务关联表';


-- 添加字段脚本
alter table auto_render_task add column `new_full_house_plan_id` integer(11) comment "新全屋方案id";
alter table auto_render_task add column `pre_render_scene_id` integer(11) comment "全屋方案需替换的效果图方案id";
alter table auto_render_task add column `full_house_plan_action` integer(11) comment "全屋方案操作(1:创建全屋方案,2:修改全屋方案,3:修改全屋方案生成新的全屋方案)";

alter table auto_render_task_state add column `pre_render_scene_id` integer(11) comment "全屋方案需替换的效果图方案id";
alter table auto_render_task_state add column `full_house_plan_action` integer(11) comment "全屋方案操作(1:创建全屋方案,2:修改全屋方案,3:修改全屋方案生成新的全屋方案)";

-- 全屋方案表新增字段
ALTER TABLE full_house_design_plan ADD COLUMN render_state int(11) DEFAULT 2 COMMENT '渲染状态(1:渲染成功,2:渲染中)';