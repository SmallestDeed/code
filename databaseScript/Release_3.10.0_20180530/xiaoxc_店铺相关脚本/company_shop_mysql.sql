drop table company_shop;
create table company_shop
(
id bigint not null AUTO_INCREMENT 
,company_id int DEFAULT 0 comment '企业Id'
,company_pid int DEFAULT 0 COMMENT '企业父Id(经销商-->厂商)'
,user_id int DEFAULT 0 comment '用户Id'
,shop_name varchar(64) DEFAULT '' comment '店铺名称'
,business_type int DEFAULT 0 comment '店铺类型 设计师、设计公司、装修公司、工长、品牌馆、家居建材'
,category_ids varchar(100) DEFAULT '' '根据店铺类型不同，值含义不一样，如：设计师--风格，家居建材--产品分类'
,province_code varchar(32) DEFAULT '' comment '省编码'
,city_code varchar(32) DEFAULT '' comment '市编码'
,area_code varchar(32) DEFAULT '' comment '区编码'
,street_code varchar(32) DEFAULT '' comment '街道编码'
,long_area_code varchar(64) DEFAULT '' comment '地区长编码'
,shop_address varchar(128) DEFAULT '' comment '详细地址'
,contact_phone varchar(16) DEFAULT '' comment '联系电话'
,contact_name varchar(64) DEFAULT '' comment '联系人姓名'
,logo_pic_id int DEFAULT 0 comment 'logo图ID'
,cover_pic_id int DEFAULT 0 comment '封面图ID'
,release_platform_values varchar(64) DEFAULT '' comment '发布平台类型 1:小程序2:选装网3:同城联盟'
,display_status int DEFAULT 1 comment '显示状态(1是0否)'
,visit_count int DEFAULT 0 comment '访问次数'
,praise_rate decimal(3,2) DEFAULT '0.98' comment '好评率'
,shop_introduced varchar(500) DEFAULT '' comment '店铺介绍'
,sys_code varchar(32) DEFAULT '' comment '系统编码'
,creator varchar(32) DEFAULT '' comment '创建者'
,gmt_create timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' comment '创建时间'
,modifier varchar(32) DEFAULT '' comment '修改人'
,gmt_modified timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' comment '修改时间'
,is_deleted int DEFAULT 0 comment '是否删除'
,remark varchar(256) DEFAULT '' comment '备注'
,primary key (id) );
ALTER TABLE company_shop COMMENT='企业店铺';


