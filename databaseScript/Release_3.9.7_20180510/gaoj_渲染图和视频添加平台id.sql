##自动渲染视频资源添加平台id
ALTER TABLE auto_render_res_render_video ADD COLUMN platform_id BIGINT(20) NULL COMMENT '平台ID' ;
##历史数据归属到移动端B端
UPDATE auto_render_res_render_video SET platform_id =1;

##回滚
#alter table auto_render_res_render_video drop column platform_id ;


##自动渲染图片资源添加平台id
ALTER TABLE auto_render_res_render_pic ADD COLUMN platform_id BIGINT(20) NULL COMMENT '平台ID' ;
##历史数据归属到移动端B端
UPDATE auto_render_res_render_pic SET platform_id =1;

##回滚
#ALTER TABLE auto_render_res_render_pic DROP COLUMN platform_id ;