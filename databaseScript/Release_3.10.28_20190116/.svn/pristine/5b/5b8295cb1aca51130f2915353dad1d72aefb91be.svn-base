##提交者:陈名
##提交时间: 2019-01-16
##COMMON-1685 处理线上名称和地址相同的小区数据  备份需求涉及表数据


## 将重复的小区数据备份保存下来
USE app_online_30;
DROP TABLE IF EXISTS his.base_living_repeated_temp_20190116;
CREATE TABLE his.base_living_repeated_temp_20190116
SELECT  IFNULL(h.house_count,0) AS baseHouseNum,L2.id,L2.`living_name` AS livingName,L2.area_long_code AS areaLongCode,L2.living_desc AS livingDesc,L1.landmarkId
FROM base_living L2
INNER JOIN (
SELECT COUNT(1),L.id landmarkId, L.living_name,L.area_long_code
FROM base_living L WHERE L.is_deleted =0
GROUP BY L.area_long_code,L.living_name
HAVING COUNT(1) > 1
)L1 ON L1.area_long_code = L2.area_long_code AND L2.living_name = L1.living_name
LEFT JOIN(
SELECT living_id,COUNT(1) house_count
FROM base_house h
WHERE  h.`is_deleted` =0
GROUP BY living_id
  ) h ON L2.id = h.living_id
  WHERE L2.is_deleted = 0
ORDER BY L2.living_name;

##备份会被修改的表的数据

DROP TABLE IF EXISTS his.base_living_temp_20190116_01;
CREATE TABLE his.base_living_temp_20190116_01
SELECT * FROM `app_online_30`.base_living;

DROP TABLE IF EXISTS his.design_plan_20190116_01;
CREATE TABLE his.design_plan_temp_20190116_01
SELECT * FROM `app_online_30`.design_plan;

DROP TABLE IF EXISTS his.base_house_temp_20190116_01;
CREATE TABLE his.base_house_temp_20190116_01
SELECT * FROM `app_online_30`.base_house;

DROP TABLE IF EXISTS his.design_plan_recommended_temp_20190116_01;
CREATE TABLE his.design_plan_recommended_temp_20190116_01
SELECT * FROM `app_online_30`.design_plan_recommended;

DROP TABLE IF EXISTS his.design_plan_render_scene_temp_20190116_01;
CREATE TABLE his.design_plan_render_scene_temp_20190116_01
SELECT * FROM `app_online_30`.design_plan_render_scene;

DROP TABLE IF EXISTS his.design_plan_template_temp_20190116_01;
CREATE TABLE his.design_plan_template_temp_20190116_01
SELECT * FROM `app_online_30`.design_plan_template;

DROP TABLE IF EXISTS his.onekey_decoration_design_plan_temp_20190116_01;
CREATE TABLE his.onekey_decoration_design_plan_temp_20190116_01
SELECT * FROM `app_online_30`.onekey_decoration_design_plan;

DROP TABLE IF EXISTS his.base_living_temp_20190116_01;
CREATE TABLE his.base_living_temp_20190116_01
SELECT * FROM house_draw.draw_base_house;

