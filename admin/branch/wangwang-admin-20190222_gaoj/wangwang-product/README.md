# Product Module

**Current Include:** 
  - Product Sub Module
  - Category Sub Module
  - Company Sub Module
  - Brand Sub Module

**Port**
- Product Provider: `12002`

```mysql
ALTER TABLE design_plan_recommended ADD contains_not_open_product VARCHAR(10) DEFAULT 'Y' NULL;
ALTER TABLE design_templet ADD contains_not_open_product VARCHAR(10) DEFAULT 'Y' NULL;
ALTER TABLE design_plan_render_scene ADD contains_not_open_product VARCHAR(10) DEFAULT 'Y' NULL;

ALTER TABLE design_plan_recommended ADD shelf_status VARCHAR(20) DEFAULT '' COMMENT '商家后台上下架状态 ONEKEY 一键方案   OPEN 公开方案  多个用 , 隔开 ONEKEY,OPEN';
ALTER TABLE design_plan_render_scene ADD shelf_status VARCHAR(20) DEFAULT '' NULL COMMENT '普通方案商家后台上架 TEMPLATE 模板方案';
```
ALTER TABLE sys_dictionary ADD pid INT NULL COMMENT '父级id 目前只用于材质属性和表面属性的父子关系';