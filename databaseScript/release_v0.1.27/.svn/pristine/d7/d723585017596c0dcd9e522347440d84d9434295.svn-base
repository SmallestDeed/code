## 处理所有店铺方案
##bak
create table design_plan_recommended_bak_2019_0308
    select plan.*
    from company_shop_design_plan shopp,
         design_plan_recommended plan
    where shopp.plan_id = plan.id
      and shopp.is_deleted = 0;
##script
update design_plan_recommended_bak_2019_0308 bak, design_plan_recommended plan
set plan.show_In_SXW_Flag = 1
where bak.id = plan.id;



##方案新增字段
ALTER TABLE design_plan_recommended
  ADD sort int DEFAULT 0 NULL
COMMENT '方案排序1`';
ALTER TABLE design_plan_recommended
add shop_in720_page int default '0' null
comment '720中显示的店铺';
ALTER TABLE design_plan_recommended
add show_In_SXW_Flag int default '0' null
comment '是否在谁选网显示 , 1:显示，2：不显示'



##bak
create table design_plan_recommended_bak_190309
    select plan.*
    from design_plan_recommended plan,
         (SELECT SUBSTRING_INDEX(GROUP_CONCAT(a.shop_id ORDER BY c.shop_type asc, c.id asc), ",", 1) as shopId,
                 drp.plan_name,
                 drp.id
          FROM company_shop_design_plan a
                 INNER JOIN company_shop c ON a.shop_id = c.id
                 inner join design_plan_recommended drp on drp.id = a.plan_id
         where a.is_deleted = 0
         and c.is_deleted = 0
          GROUP BY a.plan_id, shop_id) as t
    where plan.id = t.id;

##处理店铺
explain
update design_plan_recommended plan
inner join
(SELECT SUBSTRING_INDEX(GROUP_CONCAT(a.shop_id ORDER BY c.shop_type asc, a.gmt_modified asc), ",", 1) as shopId,
        drp.id
 FROM company_shop_design_plan a
        INNER JOIN company_shop c ON a.shop_id = c.id
        inner join design_plan_recommended drp on drp.id = a.plan_id
  where a.is_deleted = 0
         and c.is_deleted = 0
 GROUP BY a.plan_id, shop_id) as t
on plan.id = t.id
inner join design_plan_recommended_bak_190309 bak on plan.id = bak.id
set plan.shop_in720_page = t.shopId;